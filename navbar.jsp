<!DOCTYPE html>
<%@include file="includes.jsp" %>

<%
	session = request.getSession(false);
	if(session == null)
	{
%>
	<c:redirect url="login.jsp" />
<%
	}
	
	String privileges = (String) session.getAttribute("privileges");
	if(privileges == null || privileges.isEmpty() )
	{
%>
	<c:redirect url="login.jsp" />
<%
	}
	char priv = privileges.charAt(0);
%>

<nav>
	<a href="search.jsp">Search</a>
	<%switch(priv)
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
		default:
	%>
		c:redirect url="login.jsp" />
	<%
	}
	%>
	<a href="logout.jsp">Logout</a>
</nav>