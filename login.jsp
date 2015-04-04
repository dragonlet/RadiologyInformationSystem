<!DOCTYPE html>
<%@ include file="includes.jsp" %>
<%@ page import="com.LoginLayer" %>

<HTML>
<!-- Login module jsp page. -->
<% 
	session = request.getSession();
	boolean goodLogin = false;
	boolean attempted = false;

	LoginLayer login = new LoginLayer();		

	if(request.getParameter("Submit") != null)
	{
		/* Retrieve the user input from the login page. */
		String userName = (request.getParameter("USERID")).trim();
		String passwd = (request.getParameter("PASSWD")).trim();
		
		try {
			if((goodLogin = login.validateLogin(userName, passwd)))
			{
				/* username and user_id must be cast to String, privileges to Character. 
				i.e. String username = (String) session.getAttribute("user_name"); */
				session.setAttribute("user_name", userName);
				session.setAttribute("privileges", login.getPrivs());
				session.setAttribute("person_id", login.getUserID());
			}
		}
		catch (Exception ex){
			/* Redirect to error page. */
		}
		attempted = true;		
	}
%>

<HEAD>
    <TITLE>Login</TITLE>
</HEAD>

<BODY>

<!--This is the login page-->

<H1>Radiology Information System</H1>

<FORM NAME="LoginForm" ACTION="login.jsp" METHOD="post" >

    <% if(!goodLogin && attempted) { %>
    <P>Bad username and/or password. Please try again.</P>

    <% } else if(goodLogin && attempted){%>
    <c:redirect url="search.jsp" />

    <% } else { %>
    <P>Please Log In.</p>
    <% } %>

    

    <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><INPUT TYPE="text" NAME="USERID" placeholder="username"><BR></TD>
	</TR>

	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><INPUT TYPE="password" NAME="PASSWD" placeholder="password"></TD>
	</TR>
    </TABLE>

    <INPUT TYPE="submit" NAME="Submit" VALUE="LOGIN">
</FORM>

</BODY>

</HTML>
