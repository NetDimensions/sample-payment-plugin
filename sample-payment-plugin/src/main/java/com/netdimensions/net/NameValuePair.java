package com.netdimensions.net;

import static java.util.Objects.requireNonNull;

import com.google.common.base.Joiner;
import com.google.common.net.UrlEscapers;

public final class NameValuePair {
	public final String name;
	public final String value;

	public NameValuePair(final String name, final String value) {
		this.name = requireNonNull(name);
		this.value = requireNonNull(value);
	}

	@Override
	public final String toString() {
		return escape(name) + "=" + escape(value);
	}

	public static String toString(final Iterable<NameValuePair> pairs) {
		return Joiner.on('&').join(pairs);
	}

	public static String url(final String action, final Iterable<NameValuePair> pairs) {
		return action + "?" + toString(pairs);
	}

	private static String escape(final String string) {
		return UrlEscapers.urlFormParameterEscaper().escape(string);
	}
}
