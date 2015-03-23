<!DOCTYPE html>

<HTML>

<!--A simple example to demonstrate how to use JSP to 
    connect and query a database. 
    @author  Hong-Yu Zhang, University of Alberta

    Modified by Daniel Ristic-Petrovic
 -->
<%@ page import="com.LoginLayer" %>
<% 
	boolean goodLogin = false;
	LoginLayer login = new LoginLayer();		

    if(request.getParameter("Submit") != null)
    {
	    /* Retrieve the user input from the login page. */

      	String userName = (request.getParameter("USERID")).trim();
	    String passwd = (request.getParameter("PASSWD")).trim();

		if((goodLogin = login.validateLogin(userName, passwd)) == false)
		{
			if(login.failedWithError())
				out.println(login.error_printout);
		}		
    }
	
	else
	{
		response.sendRedirect("login.jsp");
	}
%>

<HEAD>
    <TITLE>Login</TITLE>
</HEAD>

<BODY>

<!--This is the login page-->

<H1>Radiology Information System</H1>

<FORM NAME="LoginForm" ACTION="login.jsp" METHOD="post" >

    <% if(!goodLogin) { %>
    <P>Bad username and/or password. Please try again.</P>

    <% } else {%>
    <P>Success! This should have gone to the search page but it doesn't exist quite yet.</P>

    <% } %>

    

    <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Userid:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="USERID" VALUE="userid"><BR></TD>
	</TR>

	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Password:</I></B></TD>
	    <TD><INPUT TYPE="password" NAME="PASSWD" VALUE="password"></TD>
	</TR>
    </TABLE>

    <INPUT TYPE="submit" NAME="Submit" VALUE="LOGIN">
</FORM>

</BODY>

</HTML>
