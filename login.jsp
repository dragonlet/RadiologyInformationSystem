<!DOCTYPE html>

<HTML>

<!--A simple example to demonstrate how to use JSP to 
    connect and query a database. 
    @author  Hong-Yu Zhang, University of Alberta

    Modified by Daniel Ristic-Petrovic
 -->
<%@ page import="java.sql.*" %>
<% 
	boolean goodLogin = false;	

        if(request.getParameter("Submit") != null)
        {
		

	        //get the user input from the login page
        	String userName = (request.getParameter("USERID")).trim();
	        String passwd = (request.getParameter("PASSWD")).trim();

	        //establish the connection to the underlying database
        	Connection conn = null;
	
	        String driverName = "oracle.jdbc.driver.OracleDriver";
            	String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	
	        try{
		        //load and register the driver
        		Class drvClass = Class.forName(driverName); 
	        	DriverManager.registerDriver((Driver) drvClass.newInstance());
        	}
	        catch(Exception ex){
		        out.println("<hr>" + ex.getMessage() + "<hr>");
	
	        }
	
        	try{
	        	//establish the connection 
		        conn = DriverManager.getConnection(dbstring,"risticpe","compsci1"); /* Oracle login info here */
        		conn.setAutoCommit(false);
	        }
        	catch(Exception ex){
	        
		        out.println("<hr>" + ex.getMessage() + "<hr>");
        	}
	

	        //select the user table from the underlying db and validate the user name and password
        	Statement stmt = null;
	        ResultSet rset = null;
        	String sql = "select password from users where user_name =" + "'" + userName + "'";

        	try{
	        	stmt = conn.createStatement();
		        rset = stmt.executeQuery(sql);
        	}
	
	        catch(Exception ex){
		        out.println("<hr>" + ex.getMessage() + "<hr>");
        	}

	        String truepwd = "";
	
        	while(rset != null && rset.next())
	        	truepwd = (rset.getString(1)).trim();
	
		//Redirect based on user privileges

	        if(truepwd.length() > 0 && passwd.equals(truepwd))
			{
		        	goodLogin = true;
			}

                try
		{
                        conn.close();
                }
                catch(Exception ex)
		{
                        out.println("<hr>" + ex.getMessage() + "<hr>");
                }
        }
	else
	{
		response.sendRedirect("login.html");
	}
%>

<HEAD>
    <TITLE>Login</TITLE>
</HEAD>

<BODY>

<!--This is the login page-->

<H1>Radiology Information System</H1>

<FORM NAME="LoginForm" ACTION="login.jsp" METHOD="post" >

    <% if(!goodLogin) { %>
    <P>Bad username and/or password. Please try again.</P>

    <% } else {%>
    <P>Success! This should have gone to the search page but it doesn't exist quite yet.</P>

    <% } %>

    

    <TABLE>
	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Userid:</I></B></TD>
	    <TD><INPUT TYPE="text" NAME="USERID" VALUE="userid"><BR></TD>
	</TR>

	<TR VALIGN=TOP ALIGN=LEFT>
	    <TD><B><I>Password:</I></B></TD>
	    <TD><INPUT TYPE="password" NAME="PASSWD" VALUE="password"></TD>
	</TR>
    </TABLE>

    <INPUT TYPE="submit" NAME="Submit" VALUE="LOGIN">
</FORM>

</BODY>

</HTML>



