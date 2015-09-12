package com.netdimensions.servlet;

// Noninstantiable utility class
public final class Servlets {
	public static final String ATTRIBUTE_NAME_CSRF_TOKEN = "CSRFToken";

	// Suppress default constructor for noninstantiability
	private Servlets() {
		throw new AssertionError();
	}
}
