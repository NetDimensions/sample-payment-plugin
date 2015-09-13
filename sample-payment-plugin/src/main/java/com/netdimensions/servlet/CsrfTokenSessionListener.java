package com.netdimensions.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public final class CsrfTokenSessionListener implements HttpSessionListener {

	@Override
	public final void sessionCreated(final HttpSessionEvent se) {
		Servlets.initCsrfToken(se.getSession());
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent se) {
	}
}
