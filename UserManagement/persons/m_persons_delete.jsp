<!DOCTYPE html>

<HTML>

<!-- Management module jsp page. -->


<%@ page import="com.ManagementModule" %>
<% 

	ManagementModule module = new ManagementModule();


	if(request.getParameter("Add") != null)
        {

		if (request.getParameter("FIRSTNAME") == "")
			{
			%><P>Perfect!! Firstname is NULL!!!!</P><%}
		

	        /* Retrieve the new persons data. */

        	String p_id = (request.getParameter("PERSONID")).trim();

		String delete_person = "DELETE FROM persons WHERE PERSON_ID = " + p_id;

		module.executequery(delete_person);

		if (module.FailedWithError()){
			%><P>Failed to delete person.</P><%}

		else{
			%><P>Successfully deleted.</P><%}
		
        }
	
%>

<HEAD>
    <TITLE>Person Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<FORM NAME="Personsform" ACTION="m_persons_delete.jsp" METHOD="post" >

<P>Enter the ID of the person you wish to delete</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>person id:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PERSONID" VALUE=""><BR></TD>
	</TR>

    </TABLE>

<INPUT TYPE="submit" NAME="Add" VALUE="Delete">

</FORM>

<FORM NAME="Return" ACTION="../m_module.html" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Return Home">
</FORM>

</BODY>

</HTML>
