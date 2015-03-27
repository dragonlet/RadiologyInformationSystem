<!DOCTYPE html>
<%@include file="includes.jsp" %>

<%
	HttpSession session = request.getSession(false);
	if(session == null)
	{
%>
	<c:redirect url="login.jsp">
<%
	}
	
	Char privileges = (Char) session.getAttribute("privileges");
%>

<nav>
	<a href="search.jsp">Search</a>
	<%switch(privileges)
	{
		case 'a':
	%>
	<a href="UserManagement.jsp">User Management</a>
	<a href="ReportGen.jsp">Reports</a>
	<a href="DataAnalysis.jsp">Analysis</a>
	<%
		break;
		case 'p':
	%>
	<%
		break;
		case 'd':
	%>
	<%
		break;
		case 'r':
	%>
	<a href="Upload.jsp">Upload</a>
	<%
		break;
	}
	%>
	<a href="logout.jsp">Logout</a>
</nav>