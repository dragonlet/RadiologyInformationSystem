<HTML>

<%@ include file="navbar.jsp" %>

<HEAD>
    <TITLE>My Account</TITLE>
</HEAD>

<BODY>

<H1>Account Info</H1>

<FORM NAME="ChangeInfo" ACTION="myAccount.jsp" METHOD="post" >

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>User Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="USERNAME" VALUE=""></TD>
	    <TD><INPUT TYPE="submit" NAME="UpdateUsername" VALUE="Update"></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Password:</I></B></TD>
	    <TD><INPUT TYPE="password" NAME="PASSWORD" VALUE=""></TD>
	    <TD><INPUT TYPE="submit" NAME="UpdatePassword" VALUE="Update"></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>First Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="FIRSTNAME" VALUE=""></TD>
	    <TD><INPUT TYPE="submit" NAME="UpdateFirstName" VALUE="Update"></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Last Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="LASTNAME" VALUE=""></TD>
	    <TD><INPUT TYPE="submit" NAME="UpdateLastName" VALUE="Update"></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Address:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="ADDRESS" VALUE=""></TD>
	    <TD><INPUT TYPE="submit" NAME="UpdateAddress" VALUE="Update"></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Email:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="EMAIL" VALUE=""></TD>
	    <TD><INPUT TYPE="submit" NAME="UpdateEmail" VALUE="Update"></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Phone:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PHONE" VALUE=""></TD>
	    <TD><INPUT TYPE="submit" NAME="UpdatePhone" VALUE="Update"></TD>
	</TR>
    </TABLE>

</FORM>

<FORM NAME="Return" ACTION="UserManagement.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Return Home">
</FORM>

</BODY>


<%@ page import="com.MyAccountLayer" %>
<%

	session = request.getSession();

	MyAccountLayer account = new MyAccountLayer();

	String user_name = (String) session.getAttribute("user_name");


	if(request.getParameter("UpdateFirstName") != null)
	{
		String ID = account.getID(user_name);
		String first_name = (request.getParameter("FIRSTNAME")).trim();	
		String temp = "UPDATE persons SET first_name = '" + first_name + "' WHERE person_id = " + ID;
		account.executequery(temp);
		if (account.FailedWithError()) 
			%><P>Failed changing First Name</P><%
		else
			%><P>First Name changed successfully</P><%
	}

	if(request.getParameter("UpdateLastName") != null)
	{
		String ID = account.getID(user_name);
		String last_name = (request.getParameter("LASTNAME")).trim();	
		String temp = "UPDATE persons SET last_name = '" + last_name + "' WHERE person_id = " + ID;
		account.executequery(temp);
		if (account.FailedWithError()) 
			%><P>Failed changing Last Name</P><%
		else
			%><P>Last Name changed successfully</P><%	
	}

	if(request.getParameter("UpdateAddress") != null)
	{
		String ID = account.getID(user_name);
		String address = (request.getParameter("ADDRESS")).trim();	
		String temp = "UPDATE persons SET address = '" + address + "' WHERE person_id = " + ID;
		account.executequery(temp);
		if (account.FailedWithError()) 
			%><P>Failed changing Address</P><%
		else
			%><P>Address changed successfully</P><%
	}

	if(request.getParameter("UpdateEmail") != null)
	{
		String ID = account.getID(user_name);
		String email = (request.getParameter("EMAIL")).trim();	
		String temp = "UPDATE persons SET email = '" + email + "' WHERE person_id = " + ID;
		account.executequery(temp);
		if (account.FailedWithError()) 
			%><P>Failed changing Email</P><%
		else
			%><P>Email changed successfully</P><%	
	}

	if(request.getParameter("UpdatePhone") != null)
	{
		String ID = account.getID(user_name);
		String phone = (request.getParameter("PHONE")).trim();	
		String temp = "UPDATE persons SET phone = '" + phone + "' WHERE person_id = " + ID;
		account.executequery(temp);
		if (account.FailedWithError()) 
			%><P>Failed changing Phone Number</P><%
		else
			%><P>Phone Number changed successfully</P><%
	}

	if(request.getParameter("UpdateUsername") != null)
	{
		String ID = account.getID(user_name);
		String temp_name = (request.getParameter("USERNAME")).trim();	
		String temp = "UPDATE users SET user_name = '" + temp_name + "' WHERE person_id = " + ID;
		account.executequery(temp);
		if (account.FailedWithError()) 
			%><P>Failed changing User Name</P><%
		else{
			%><P>User Name changed successfully</P><%
			session.setAttribute("user_name", temp_name);}
	}

	if(request.getParameter("UpdatePassword") != null)
	{
		String ID = account.getID(user_name);
		String password = (request.getParameter("PASSWORD")).trim();	
		String temp = "UPDATE users SET password = '" + password + "' WHERE person_id = " + ID;
		account.executequery(temp);
		if (account.FailedWithError()) 
			%><P>Failed changing Password</P><%
		else
			%><P>Password changed successfully</P><%
	}



%>



</HTML>
