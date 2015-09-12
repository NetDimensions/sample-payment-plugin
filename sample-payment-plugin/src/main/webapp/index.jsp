<%@page import="com.netdimensions.servlet.Servlets"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Currency"%>
<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	final BigDecimal amount = new BigDecimal(
			request.getParameter("amount"));
	final NumberFormat format = NumberFormat.getCurrencyInstance();
	format.setCurrency(Currency.getInstance(request
			.getParameter("currency")));
%>
<!DOCTYPE html>
<html>
<head>
<link href="default.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment Plugin</title>
</head>
<body>
<h1>Payment Plugin</h1>
<%--
In a more realistic example we would prompt for payment information such as a
credit card number here.
 --%>
<p>Do you promise to send me <%=format.format(amount)%>?</p>
<form action="paymenthandler" method="POST">
<input name="amount" type="hidden" value="<%=request.getParameter("amount")%>">
<input name="currency" type="hidden" value="<%=request.getParameter("currency")%>">
<input name="orderid" type="hidden" value="<%=request.getParameter("orderid")%>">
<input name="orderid" type="hidden" value="<%=session.getAttribute(Servlets.ATTRIBUTE_NAME_CSRF_TOKEN)%>">
<input name="yes" type="submit" value="Yes">
<input name="no" type="submit" value="No">
</form>
</body>
</html>