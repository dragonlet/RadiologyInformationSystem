<!DOCTYPE html>
<%@ include file="includes.jsp" %>


<%@ page import="java.sql.*, java.util.*" %>

<HTML>
<%@ include file="navbar.jsp" %>





  <head>
  <meta http-equiv="content-type" content="text/html; charset=windows-1250">
  <title>Search</title>
  </head>
  <body>
    
    <%@ page import="com.SearchLayer" %>
    <%



	SearchLayer module = new SearchLayer();
	session = request.getSession();

	out.println(module.Display());

     
        if(request.getParameter("updateIndex") != null)
        {
        }

    %>
    
    
      <!--Every time you add or update data you need to update the index. <br>
      <input type=submit name="updateIndex" value="Update Index">
    	<br>
    	<br>
    	<br>
    -->
    <form>
      Query the database to see relevant items
      <table>
        <tr>
          <td>
            <input type=text name=query>
          </td>
          <td>
            <input type=submit value="Search" name="search">
          </td>
        </tr>
      </table>
      <%
        
          if (request.getParameter("search") != null)
          {
          
          	out.println("<br>");
          	out.println("Query is " + request.getParameter("query"));
          	out.println("<br>");
          

		char privilege = (Character) session.getAttribute("privileges");
		String ID = (String) session.getAttribute("person_id");

		if (privilege == 'a')
		{%><P>admin</P><%
		out.println(module.SearchAll(request.getParameter("query")));
		}

		if (privilege == 'd')
		{%><P>doctor</P><%
		out.println(module.SearchDoctor(request.getParameter("query"), ID));
		}

		if (privilege == 'p')
		{%><P>patient</P><%
		out.println(module.SearchPatient(request.getParameter("query"), ID));
		}

		if (privilege == 'r')
		{%><P>radiologist</P><%
		out.println(module.SearchRadiologist(request.getParameter("query"), ID));
		}


              
          }

      %>
    </form>
  </body>
</html>
