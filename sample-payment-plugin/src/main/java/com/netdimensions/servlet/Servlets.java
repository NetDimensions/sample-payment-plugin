package com.netdimensions.servlet;

import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import com.google.common.io.BaseEncoding;

// Noninstantiable utility class
public final class Servlets {
	public static final String ATTRIBUTE_NAME_CSRF_TOKEN = "CSRFToken";

	// Suppress default constructor for noninstantiability
	private Servlets() {
		throw new AssertionError();
	}

	private static byte[] randomBytes() {
		final byte[] result = new byte[20];
		new SecureRandom().nextBytes(result);
		return result;
	}

	static void initCsrfToken(final HttpSession session) {
		session.setAttribute(ATTRIBUTE_NAME_CSRF_TOKEN, BaseEncoding.base16().lowerCase().encode(randomBytes()));
	}

	/**
	 * Tomcat normalizes path info by collapsing contiguous slashes. This method attempts to restore the original value if it is known to be an absolute URL.
	 */
	public static String fixUrlFromPathInfo(final String pathInfo) {
		return pathInfo.replaceAll(":/([^/])", "://$1");
	}
}
