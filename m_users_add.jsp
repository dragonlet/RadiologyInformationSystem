<!DOCTYPE html>
<%@ include file="includes.jsp" %>
<HTML>

<%@ include file="navbar.jsp" %>

<HEAD>
    <TITLE>User Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<FORM NAME="Usersform" ACTION="m_users_add.jsp" METHOD="post" >

<P>Enter new Users information</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>person id:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PERSONID" VALUE=""><BR></TD>
	</TR>

	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>User Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="USERNAME" VALUE=""></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Password:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PASSWORD" VALUE=""></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Class:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="CLASS" VALUE=""></TD>
	</TR>

    </TABLE>

<INPUT TYPE="submit" NAME="Add" VALUE="Add">

</FORM>

<FORM NAME="Return" ACTION="UserManagement.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Return Home">
</FORM>

</BODY>




<!-- Management module jsp page. -->


<%@ page import="com.ManagementModule" %>
<% 

	ManagementModule module = new ManagementModule();


	if(request.getParameter("Add") != null)
        {

	        /* Retrieve the new persons data. */

        	String p_id = (request.getParameter("PERSONID")).trim();
	        String UserName = (request.getParameter("USERNAME")).trim();
	        String Password = (request.getParameter("PASSWORD")).trim();
	        String Class = (request.getParameter("CLASS")).trim();

		String date = module.getDate();

		String new_user = "INSERT INTO users VALUES('"+UserName+"', '"+Password+"', '"+Class+"', "+p_id+", TO_DATE('"+date+"','YYYYMMDD'))";

		module.executequery(new_user);

		if (module.FailedWithError()){
			%><P>Failed to Add user.</P><%}

		else{
			%><P>Successfully Added.</P><%}
		
        }
	
%>



</HTML>
