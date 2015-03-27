<!DOCTYPE html>
<%@ include file="../../includes.jsp" %>

<HTML>
<%@ include file="../../navbar.jsp" %>

<HEAD>
    <TITLE>User Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<P>Would you like to Add Edit or Delete?</P>

<FORM NAME="Addform" ACTION="m_users_add.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Add User">
</FORM>

<FORM NAME="Editform" ACTION="m_users_edit.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Edit User">
</FORM>

<FORM NAME="Deleteform" ACTION="m_users_delete.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Delete User">
</FORM>

</BODY>

</HTML>
