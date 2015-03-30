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

<FORM NAME="Doctorsform" ACTION="m_doctors_delete.jsp" METHOD="post" >

<P>Enter the Doctor ID and Patient ID of the pair you wish to delete</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Doctor ID:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="DOCTORID" VALUE=""><BR></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Patient ID:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PATIENTID" VALUE=""><BR></TD>
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

        	String d_id = (request.getParameter("DOCTORID")).trim();
        	String p_id = (request.getParameter("PATIENTID")).trim();

		String delete_pair = "DELETE FROM family_doctor WHERE doctor_id = " + d_id + " and patient_id = " + p_id;

		module.executequery(delete_pair);

		if (module.FailedWithError()){
			%><P>Failed to delete Doctor/Patient Pair.</P><%}

		else{
			%><P>Successfully deleted.</P><%}
		
        }

	
%>


</HTML>
