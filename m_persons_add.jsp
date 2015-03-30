<!DOCTYPE html>
<%@ include file="includes.jsp" %>
<HTML>

<%@ include file="navbar.jsp" %>

<HEAD>
    <TITLE>Person Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<FORM NAME="Personsform" ACTION="m_persons_add.jsp" METHOD="post" >

<P>Enter new persons information</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>First Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="FIRSTNAME" VALUE=""></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Last Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="LASTNAME" VALUE=""></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Address:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="ADDRESS" VALUE=""></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Email:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="EMAIL" VALUE=""></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Phone:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PHONE" VALUE=""></TD>
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
<%@ page import="com.UniqueID" %>
<% 

	ManagementModule module = new ManagementModule();
	UniqueID idGen = new UniqueID();


	if(request.getParameter("Add") != null)
        {

		if (request.getParameter("FIRSTNAME") == "")
			{
			%><P>Perfect!! Firstname is NULL!!!!</P><%}
		

	        /* Retrieve the new persons data. */

        	int    p_id = idGen.generate("person");

		if (p_id != 0){

	        	String FirstName = (request.getParameter("FIRSTNAME")).trim();
	        	String LastName = (request.getParameter("LASTNAME")).trim();
	        	String Address = (request.getParameter("ADDRESS")).trim();
	        	String Email = (request.getParameter("EMAIL")).trim();
	        	String Phone = (request.getParameter("PHONE")).trim();

			String new_person = "INSERT INTO persons VALUES("+p_id+", '"+FirstName+"', '"+LastName+"', '"+Address+"', '"+Email+"', "+Phone+")";
			//String new_person = "INSERT INTO persons VALUES(15, 'Jack', 'Torrence', 'Overlook', 'jack@overlook.com', 7804848435)";
		

			module.executequery(new_person);

			if (module.FailedWithError()){
				%><P>Failed to Add person.</P><%}

			else{
				%><P>Successfully Added.</P><%}

		}

		else
			%><P>Failed generating unique ID.</P><%
		
        }
	
%>



</HTML>
