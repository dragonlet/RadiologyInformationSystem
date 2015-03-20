<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title> Index </title>
</head>
<body>
<%@page import="com.RISBusinessLayer" %>
<%
  if( session.isNew() ){
%>
	<jsp:forward page="login.html" />
<%
  }
  else {
%>
	<jsp:forward page="search.jsp" />
<%
  }
%>
</body>
</html>
