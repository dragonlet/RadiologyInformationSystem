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
             <select name=order>
  		<option value="order by aggregate_score desc">Rank</option>
  		<option value="order by test_date desc">Date Descending</option>
  		<option value="order by test_date asc">Date Ascending</option>
	     </select> 
          </td>
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
		{
		out.println(module.SearchAll(request.getParameter("query"), request.getParameter("order")));
		}

		if (privilege == 'd')
		{
		out.println(module.SearchDoctor(request.getParameter("query"), request.getParameter("order"), ID));
		}

		if (privilege == 'p')
		{
		out.println(module.SearchPatient(request.getParameter("query"), request.getParameter("order"), ID));
		}

		if (privilege == 'r')
		{
		out.println(module.SearchRadiologist(request.getParameter("query"), request.getParameter("order"), ID));
		}


              
          }

      %>
    </form>
  </body>
</html>
