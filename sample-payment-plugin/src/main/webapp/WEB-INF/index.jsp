<%@page import="com.netdimensions.servlet.Servlets"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Currency"%>
<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Sample payment plugin for NetDimensions Learning">
    <meta name="author" content="NetDimensions">
    <link rel="icon" href="<%=request.getContextPath()%>/favicon.ico">

    <title>Sample payment plugin</title>

    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/theme.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body role="document">

    <div class="container theme-showcase" role="main">

      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div class="jumbotron">
        <h1>Sample payment plugin</h1>
        <p>The sample payment plugin is a placeholder for a real payment gateway.</p>
        <p>It demonstrates the checkout flow and the protocol by which a a payment gateway communicates with NetDimensions Learning,
        but does <strong>not</strong> collect payment information from the user.</p>
<%
final String amount = request.getParameter("amount");
final String currency = request.getParameter("currency");
if (amount == null) {
%>
        <p>The sample payment plugin is intended to be invoked called as part of the checkout process, so <strong>go buy some courses</strong>!</p>
<%
} else {
	final NumberFormat format = NumberFormat.getCurrencyInstance();
	format.setCurrency(Currency.getInstance(currency));
	final String formatted = format.format(new BigDecimal(amount));
%>
        <p>A real payment gateway would collect and validate billing information here.</p>
        <p>The amount to be paid is <strong><%=formatted%></strong>.</p>
        <form action="" method="POST">
          <input name="amount" type="hidden" value="<%=amount%>">
          <input name="currency" type="hidden" value="<%=currency%>">
          <input name="orderid" type="hidden" value="<%=request.getParameter("orderid")%>">
          <input name="CSRFToken" type="hidden" value="<%=session.getAttribute(Servlets.ATTRIBUTE_NAME_CSRF_TOKEN)%>">
          <button name="cancel" type="submit" class="btn btn-lg btn-default">Cancel</button>
          <button name="pay" type="submit" class="btn btn-lg btn-success">Pay <%=formatted%></button>
        </form>
<%
}
%>
      </div>


    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  </body>
</html>