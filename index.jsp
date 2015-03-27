<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title> Index </title>
</head>
<body>
<%@page import="com.RISBusinessLayer" %>
<%
	session = request.getSession(false);
	RISBusinessLayer _bl = new RISBusinessLayer();
	if( session != null
		&& _bl.validUser((String) session.getAttribute("user_name"),
			(Integer) session.getAttribute("person_id")) )
	{
%>
	<jsp:forward page="search.jsp" />
<%
	}
	else
	{
%>
	<jsp:forward page="login.jsp" />
<%
	}
%>
</body>
</html>
