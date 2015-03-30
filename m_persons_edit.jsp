<!DOCTYPE html>
<%@ include file="../../includes.jsp" %>

<HTML>

<!-- Management module jsp page. -->

<%@ include file="../../navbar.jsp" %>

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

<INPUT TYPE="submit" NAME="Edit" VALUE="Edit">

</FORM>

<FORM NAME="Return" ACTION="../../UserManagement.jsp" METHOD="post" >
<INPUT TYPE="submit" NAME="Submit" VALUE="Return Home">
</FORM>

</BODY>








<%@ page import="com.ManagementModule" %>
<% 

	ManagementModule module = new ManagementModule();

        	String temp = null; // temporary holder for sql statements



	// only execute after user has clicked Edit button
	if(request.getParameter("Edit") != null)
        {
		//creates an array containing all the checked elements from the form
		String select[] = request.getParameterValues("toEdit"); 
        	String p_id = (request.getParameter("PERSONID")).trim();

		//if nothing is checked off or person id is blank, dont execute
		if (select != null && select.length != 0 && !p_id.equals("")) {
			for (int i = 0; i < select.length; i++) { // iterate through the checked boxes
				
				if(select[i].equals("first") && !(request.getParameter("FIRSTNAME")).trim().equals("")){
						%><%
						String FirstName = (request.getParameter("FIRSTNAME")).trim();
						temp = "UPDATE persons SET first_name = '" + FirstName + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing First Name</P><%
						else
							%><P>First name changed successfully</P><%
						}	

				if(select[i].equals("last") && !(request.getParameter("LASTNAME")).trim().equals("")){
	     				    	String LastName = (request.getParameter("LASTNAME")).trim();
						temp = "UPDATE persons SET last_name = '" + LastName + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing Last Name</P><%
						else
							%><P>Last name changed successfully</P><%
						}	

				if(select[i].equals("address") && !(request.getParameter("ADDRESS")).trim().equals("")){
	         				String Address = (request.getParameter("ADDRESS")).trim();
						temp = "UPDATE persons SET address = '" + Address + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing address</P><%
						else
							%><P>Address changed successfully</P><%
						}	

				if(select[i].equals("email") && !(request.getParameter("EMAIL")).trim().equals("")){
					        String Email = (request.getParameter("EMAIL")).trim();
						temp = "UPDATE persons SET email = '" + Email + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing Email</P><%
						else
							%><P>Email changed successfully</P><%
						}	

				if(select[i].equals("phone") && !(request.getParameter("PHONE")).trim().equals("")){
					        String Phone = (request.getParameter("PHONE")).trim();
						temp = "UPDATE persons SET phone = '" + Phone + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing Phone Number</P><%
						else
							%><P>Phone changed successfully</P><%
						}		
			}

		}

		else {
			%><P>No values selected or person id left blank</P><%
		}



		
        }
	
%>



</HTML>
