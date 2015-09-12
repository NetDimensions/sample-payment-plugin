package com.netdimensions.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.io.BaseEncoding;
import com.netdimensions.net.NameValuePair;

public class PaymentHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ALGORITHM = "HmacMD5";

	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final String location = handle(request);
		response.sendRedirect(getServletContext().getInitParameter("com.netdimensions.client.servlet.TALENT_SUITE_BASE_URL") + location);
	}

	private String handle(final HttpServletRequest request) {
		final String amount = request.getParameter("amount");
		final String currency = request.getParameter("currency");
		final String orderId = request.getParameter("orderid");

		if (request.getParameter("no") == null) {
			/*
			 * Normally we would read the payment information from the request here, pass it on to the actual payment gateway, and validate the response. Here
			 * we simply assume the payment was successful and construct the response to pass to Talent Suite.
			 */
			final ImmutableList<NameValuePair> sigBase = ImmutableList.of(new NameValuePair("amount", amount), new NameValuePair("currency", currency),
					new NameValuePair("orderid", orderId));
			final byte[] sigBytes = newMac().doFinal(NameValuePair.toString(sigBase).getBytes(StandardCharsets.UTF_8));
			final String sig = BaseEncoding.base16().lowerCase().encode(sigBytes);

			return NameValuePair.url("servlet/ekp/orderprocessor", Iterables.concat(sigBase, ImmutableList.of(new NameValuePair("sig", sig))));
		} else {
			// Payment cancelled
			return NameValuePair.url("servlet/ekp/externalpaymentcancel", ImmutableList.of(new NameValuePair("orderid", orderId)));
		}
	}

	private Mac newMac() {
		try {
			final Mac result = Mac.getInstance(ALGORITHM);
			result.init(new SecretKeySpec(getServletContext().getInitParameter("com.netdimensions.client.servlet.PAYMENT_PLUGIN_KEY").getBytes(
					StandardCharsets.UTF_8), ALGORITHM));
			return result;
		} catch (final NoSuchAlgorithmException | InvalidKeyException e) {
			throw new AssertionError("Required MAC algorithm is unavailable", e);
		}
	}
}
