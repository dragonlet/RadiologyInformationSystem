<!DOCTYPE html>
<%@ include file="includes.jsp" %>

<HTML>
<%@ include file="navbar.jsp" %>

<HEAD>
    <TITLE>User Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<P>Would you like to Add or Delete?</P>

<FORM NAME="Addform" ACTION="m_doctors_add.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Add Doctor/Patient pair">
</FORM>


<FORM NAME="Deleteform" ACTION="m_doctors_delete.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Delete Doctor/Patient pair">
</FORM>

</BODY>

<%@ page import="com.ManagementModule" %>

<%		ManagementModule module = new ManagementModule();

		%><P> </P><%
		out.println(module.getDoctorsTable());
%>

</HTML>
