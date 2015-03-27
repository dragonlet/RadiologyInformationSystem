<!DOCTYPE html>
<%@ include file="includes.jsp" %>

<%
	session = request.getSession(false);
	if(session != null)
		session.invalidate();
%>

<c:redirect url="login.jsp" />