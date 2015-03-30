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

<FORM NAME="Usersform" ACTION="m_users_edit.jsp" METHOD="post" >

<P><B>Enter the information you would like to change.</B></P>
<P>Uncheck any attributes that you wish to leave unchanged.</P>

 <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>person id:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PERSONID" VALUE=""></TD>
	    <TD>ID must not be blank</TD>
	</TR>

	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>User Name:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="USERNAME" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="username" checked></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Password:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="PASSWORD" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="password" checked></TD>
	</TR>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Class:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="CLASS" VALUE=""></TD>
	    <TD><input type="checkbox" name="toEdit" value="class" checked></TD>
	</TR>
    </TABLE>

<INPUT TYPE="submit" NAME="Edit" VALUE="Edit">

</FORM>

<FORM NAME="Return" ACTION="UserManagement.jsp" METHOD="post" >
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
				
				if(select[i].equals("username") && !(request.getParameter("USERNAME")).trim().equals("")){
						%><%
						String Username = (request.getParameter("USERNAME")).trim();
						temp = "UPDATE users SET user_name = '" + Username + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing User Name</P><%
						else
							%><P>User name changed successfully</P><%
						}	

				if(select[i].equals("password") && !(request.getParameter("PASSWORD")).trim().equals("")){
	     				    	String Password = (request.getParameter("PASSWORD")).trim();
						temp = "UPDATE users SET password = '" + Password + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing Password</P><%
						else
							%><P>Password changed successfully</P><%
						}	

				if(select[i].equals("class") && !(request.getParameter("CLASS")).trim().equals("")){
	         				String Class = (request.getParameter("CLASS")).trim();
						temp = "UPDATE users SET class = '" + Class + "' WHERE person_id = " + p_id;
						module.executequery(temp);
						if (module.FailedWithError()) 
							%><P>Failed changing class</P><%
						else
							%><P>Class changed successfully</P><%
						}	
		
			}

		}

		else {
			%><P>No values selected or person id left blank</P><%
		}




		
        }
	
%>



</HTML>
