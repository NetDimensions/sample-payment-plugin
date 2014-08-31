<%@page import="javax.crypto.KeyGenerator"%>
<%@page import="org.apache.commons.codec.binary.Hex"%>
<%@page import="java.security.Key"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="javax.crypto.Mac"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String amount = request.getParameter("amount");
	String currency = request.getParameter("currency");
	String orderId = request.getParameter("orderid");

	if (request.getParameter("no") == null) {
		/*
		 * Normally we would read the payment information from the request here,
		 * pass it on to the actual payment gateway, and validate the response. Here
		 * we simply assume the payment was successful and construct the response to
		 * pass to EKP.
		 */
		String sigBase = "amount=" + URLEncoder.encode(amount, "UTF-8")
				+ "&currency=" + URLEncoder.encode(currency, "UTF-8")
				+ "&orderid=" + URLEncoder.encode(orderId, "UTF-8");
		String algorithm = "HmacMD5";
		Mac mac = Mac.getInstance(algorithm);
		mac.init(new SecretKeySpec(application.getInitParameter("key")
				.getBytes("UTF-8"), algorithm));
		byte[] sigBytes = mac.doFinal(sigBase.getBytes("UTF-8"));
		String sig = Hex.encodeHexString(sigBytes);

		response.sendRedirect(application.getInitParameter("ekpBase")
				+ "servlet/ekp/orderprocessor?" + sigBase + "&sig="
				+ URLEncoder.encode(sig, "UTF-8"));
	} else {
		// Payment cancelled
		response.sendRedirect(application.getInitParameter("ekpBase")
				+ "servlet/ekp/externalpaymentcancel?orderid="
				+ URLEncoder.encode(orderId, "UTF-8"));
	}
%>