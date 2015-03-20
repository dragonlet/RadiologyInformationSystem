<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title> Index </title>
</head>
<body>
<%@page import="com.RISBusinessLayer" %>
<%
  //Business layer is class to handle all interaction between webserver and oracle
  RISBusinessLayer _bl = new RISBusinessLayer();
  String user_name = (String) session.getAttribute("user_name");
  if ( _bl.validUser(user_name) ){
  //if( session.isNew() ){
%>
	<jsp:forward page="refresh.jsp" />
<%
  }
  else {
%>
	<jsp:forward page="login.html" />
<%
  }
%>
</body>
</html>
