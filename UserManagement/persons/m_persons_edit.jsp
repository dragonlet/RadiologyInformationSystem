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
	        String FirstName = (request.getParameter("FIRSTNAME")).trim();
	        String LastName = (request.getParameter("LASTNAME")).trim();
	        String Address = (request.getParameter("ADDRESS")).trim();
	        String Email = (request.getParameter("EMAIL")).trim();
	        String Phone = (request.getParameter("PHONE")).trim();

		String new_person = "INSERT INTO persons VALUES("+p_id+", '"+FirstName+"', '"+LastName+"', '"+Address+"', '"+Email+"', "+Phone+")";
		//String new_person = "INSERT INTO persons VALUES(15, 'Jack', 'Torrence', 'Overlook', 'jack@overlook.com', 7804848435)";
		String edit_person = "UPDATE persons SET " + "first_name = '" + FirstName + "'" + "WHERE person_id = " + p_id;

		

		module.executequery(edit_person);

		if (module.FailedWithError()){
			%><P>Failed</P><%}
		
        }
	
%>

<HEAD>
    <TITLE>Person Management</TITLE>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<FORM NAME="Personsform" ACTION="m_persons_edit.jsp" METHOD="post" >

<P><B>Enter the information you would like to change.</B></P>
<P>Uncheck any attributes that you wish to leave unchanged.</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>person id:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PERSONID" VALUE=""></TD>
	    <TD>ID must not be blank</TD>
	</TR>

	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>First Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="FIRSTNAME" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="first" checked></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Last Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="LASTNAME" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="last" checked></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Address:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="ADDRESS" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="address" checked></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Email:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="EMAIL" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="email" checked></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Phone:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PHONE" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="phone" checked></TD>
	</TR>
    </TABLE>

<INPUT TYPE="submit" NAME="Add" VALUE="Add">

</FORM>

<FORM NAME="Return" ACTION="../m_module.html" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Return Home">
</FORM>

</BODY>

</HTML>
