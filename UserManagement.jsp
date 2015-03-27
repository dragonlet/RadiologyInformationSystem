<!DOCTYPE html>
<%@ include file="includes.jsp" %>
<HTML>
<%@ include file="navbar.jsp" %>
<HEAD>
    <TITLE>User Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<P>What whould you like to edit?</P>

<FORM NAME="UsersForm" ACTION="UserManagement/users/m_users.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Edit Users">
</FORM>

<FORM NAME="PersonsForm" ACTION="UserManagement/persons/m_persons.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Edit Persons">
</FORM>

<FORM NAME="DoctorsForm" ACTION="UserManagement/doctors/m_doctors.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Edit Family Doctors">
</FORM>

</BODY>

</HTML>
