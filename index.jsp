<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title> Index </title>
</head>
<body>
<%
  //RISBusinessLayer _bl = new RISBusinessLayer();
  //if ( _bl.validUser(session.getAttribute("user_name")) ){
  if( session.isNew() ){
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
