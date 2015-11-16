package com.netdimensions.servlet;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.netdimensions.servlet.Servlets.initCsrfToken;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.io.BaseEncoding;
import com.netdimensions.net.NameValuePair;

public class PaymentHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		verifyCsrfToken(req);
		final String location = handle(req);
		resp.sendRedirect(merchantUrl(req) + location);
	}

	private String merchantUrl(final HttpServletRequest req) {
		return req.getPathInfo() == null ? initParameter("merchantUrl", "com.netdimensions.client.servlet.TALENT_SUITE_BASE_URL")
				: Servlets.fixUrlFromPathInfo(req.getPathInfo().substring(1));
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}

	/**
	 * @param fallbackName
	 *            for backward compatibility.
	 */
	private String initParameter(final String preferredName, final String fallbackName) {
		return firstNonNull(initParameter(preferredName), initParameter(fallbackName));
	}

	private String initParameter(final String name) {
		return getServletContext().getInitParameter(name);
	}

	private String handle(final HttpServletRequest req) {
		final NameValuePair orderId = nameValuePair(req, "orderid");
		if (req.getParameter("cancel") == null) {
			/*
			 * Normally we would read the payment information from the request here, pass it on to the actual payment gateway, and validate the response. Here
			 * we simply assume the payment was successful and construct the response to pass to Talent Suite.
			 */
			final ImmutableList<NameValuePair> sigBase = ImmutableList.of(nameValuePair(req, "amount"), nameValuePair(req, "currency"), orderId);
			return NameValuePair
					.url("servlet/ekp/orderprocessor",
							Iterables
									.concat(sigBase,
											ImmutableList
													.of(new NameValuePair("sig",
															mac("HmacMD5",
																	key() == null
																			? initParameter("merchantKey",
																					"com.netdimensions.client.servlet.PAYMENT_PLUGIN_KEY")
																			: mac("HmacSHA256", key(), merchantUrl(req)),
																	NameValuePair.toString(sigBase))))));
		} else {
			// Payment cancelled
			return NameValuePair.url("servlet/ekp/externalpaymentcancel", ImmutableList.of(orderId));
		}
	}

	private String key() {
		return initParameter("key");
	}

	private static String mac(final String algorithm, final String key, final String input) {
		try {
			final Mac mac = Mac.getInstance(algorithm);
			mac.init(new SecretKeySpec(key.getBytes(UTF_8), algorithm));
			return BaseEncoding.base16().lowerCase().encode(mac.doFinal(input.getBytes(UTF_8)));
		} catch (final NoSuchAlgorithmException | InvalidKeyException e) {
			throw new AssertionError("Required MAC algorithm is unavailable", e);
		}
	}

	private static void verifyCsrfToken(final HttpServletRequest req) {
		final Object expectedToken = req.getSession().getAttribute(Servlets.ATTRIBUTE_NAME_CSRF_TOKEN);
		final String actualToken = req.getParameter("CSRFToken");
		if (actualToken == null) {
			initCsrfToken(req.getSession());
			throw new RuntimeException("Missing CSRFToken: expected '" + expectedToken + "'");
		} else if (!expectedToken.equals(actualToken)) {
			initCsrfToken(req.getSession());
			throw new RuntimeException("Incorrect CSRFToken: expected '" + expectedToken + "'; actual '" + actualToken + "'");
		}
	}

	private static NameValuePair nameValuePair(final HttpServletRequest req, final String name) {
		return new NameValuePair(name, req.getParameter(name));
	}
}
