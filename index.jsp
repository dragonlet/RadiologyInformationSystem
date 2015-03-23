<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title> Index </title>
</head>
<body>
<%@page import="com.RISBusinessLayer" %>
<%
	RISBusinessLayer _bl = new RISBusinessLayer();
	if( _bl.validUser((String) session.getAttribute("user_name"), (Integer) session.getAttribute("person_id")) )
	{
%>
	<jsp:forward page="search.jsp" />
<%
	}
	else
	{
%>
	<jsp:forward page="login.html" />
<%
	}
%>
</body>
</html>
