<!DOCTYPE html>
<%@ include file="../../includes.jsp" %>
<HTML>

<%@ include file="../../navbar.jsp" %>

<HEAD>
    <TITLE>User Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<FORM NAME="Doctorsform" ACTION="m_doctors_add.jsp" METHOD="post" >

<P>Enter new persons information</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Doctor ID:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="DOCTORID" VALUE=""><BR></TD>
	</TR>

	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Patient ID:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PATIENTID" VALUE=""></TD>
	</TR>


    </TABLE>

<INPUT TYPE="submit" NAME="Add" VALUE="Add">

</FORM>

<FORM NAME="Return" ACTION="../../UserManagement.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Return Home">
</FORM>

</BODY>




<!-- Management module jsp page. -->


<%@ page import="com.ManagementModule" %>
<% 

	ManagementModule module = new ManagementModule();


	if(request.getParameter("Add") != null)
        {

	        /* Retrieve the new pairs data. */

        	String doctor_id = (request.getParameter("DOCTORID")).trim();
        	String patient_id = (request.getParameter("PATIENTID")).trim();


		String new_pair = "INSERT INTO family_doctor VALUES("+doctor_id+", "+patient_id+")";

		module.executequery(new_pair);

		if (module.FailedWithError()){
			%><P>Failed to Add Doctor/Patient Pair.</P><%}

		else{
			%><P>Successfully Added.</P><%}
		
        }
	
%>



</HTML>
