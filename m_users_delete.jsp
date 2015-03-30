<!DOCTYPE html>
<%@ include file="includes.jsp" %>
<HTML>

<!-- Management module jsp page. -->

<%@ include file="navbar.jsp" %>
<HEAD>
    <TITLE>User Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<FORM NAME="Personsform" ACTION="m_users_delete.jsp" METHOD="post" >

<P>Enter the person ID of the user you wish to delete</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Person ID:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PERSONID" VALUE=""><BR></TD>
	</TR>

    </TABLE>

<INPUT TYPE="submit" NAME="Delete" VALUE="Delete">

</FORM>

<FORM NAME="Return" ACTION="UserManagement.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Return Home">
</FORM>

</BODY>




<%@ page import="com.ManagementModule" %>
<% 

	ManagementModule module = new ManagementModule();


	if(request.getParameter("Delete") != null)
        {

	        /* Retrieve the new persons data. */

        	String p_id = (request.getParameter("PERSONID")).trim();

		String delete_user = "DELETE FROM users WHERE person_id = " + p_id;

		module.executequery(delete_user);

		if (module.FailedWithError()){
			%><P>Failed to delete user.</P><%}

		else{
			%><P>Successfully deleted.</P><%}
		
        }

// INSERT INTO users VALUES('jack1', 'redrum', 'a', 15, TO_DATE('19801202','YYYYMMDD'));
//INSERT INTO users VALUES('Matt333', 'test1', 'r', 13, TO_DATE('19910227','YYYYMMDD')); 
	
%>


</HTML>
