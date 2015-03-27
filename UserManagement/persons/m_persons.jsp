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

<FORM NAME="Addform" ACTION="m_persons_add.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Add Person">
</FORM>

<FORM NAME="Editform" ACTION="m_persons_edit.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Edit Person">
</FORM>

<FORM NAME="Deleteform" ACTION="m_persons_delete.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Delete Person">
</FORM>

</BODY>

</HTML>
