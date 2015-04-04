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
          
		out.println(module.Search(request.getParameter("query")));
              
          }

      %>
    </form>
  </body>
</html>
