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
	
	Character privileges = (Character) session.getAttribute("privileges");
	if(privileges == null)
	{
%>
	<c:redirect url="login.jsp" />
<%
	}
%>

<nav>
	<a href="search.jsp">Search</a>
	<%switch(privileges)
	{
		case 'a':
	%>
	<a href="UserManagement.jsp">User Management</a>
	<a href="reportGen.jsp">Reports</a>
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
	<a href="myAccount.jsp">My Account</a>
	<a href="logout.jsp">Logout</a>
</nav>
