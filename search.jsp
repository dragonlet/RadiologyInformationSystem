<!DOCTYPE html>
<%@ include file="includes.jsp" %>


<%@ page import="java.sql.*, java.util.*" %>

<HTML>
<%@ include file="navbar.jsp" %>





  <head>
  <meta http-equiv="content-type" content="text/html; charset=windows-1250">
  <title>Search</title>
	<style>
		.resTABLE, .resTD {
    	border: 2px solid black;
    	border-collapse: collapse;
		}
		.resTD {
    	padding: 15px;
		}
	</style>
  </head>
  <body>
    
    <%@ page import="com.SearchLayer" %>
    <%@ page import="com.RecordShelf" %>
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
    </form>
      <%
        
          if (request.getParameter("search") != null)
          {
		ArrayList<RecordShelf> shelvedRecords = new ArrayList<RecordShelf>();
          
          	out.println("<br>");
          	out.println("Query is " + request.getParameter("query"));
          	out.println("<br>");
      %>
		<TABLE class = "resTABLE">
			<TR>
			<TD class = "resTD" >record_id</TD>
			<TD class = "resTD" >test_type</TD>
			<TD class = "resTD" >prescribing_date</TD>
			<TD class = "resTD" >test_date</TD>
			<TD class = "resTD" >diagnosis</TD>
			<TD class = "resTD" >description</TD>
			<TD class = "resTD" >name</TD>
			<TD class = "resTD" >agg_score</TD>
			</TR>
      <%
		char privilege = (Character) session.getAttribute("privileges");
		String ID = (String) session.getAttribute("person_id");

		if (privilege == 'a')
		{
		shelvedRecords = module.SearchAll(request.getParameter("query"), request.getParameter("order"));
		}

		if (privilege == 'd')
		{
		shelvedRecords = module.SearchDoctor(request.getParameter("query"), request.getParameter("order"), ID);
		}

		if (privilege == 'p')
		{
		shelvedRecords = module.SearchPatient(request.getParameter("query"), request.getParameter("order"), ID);
		}

		if (privilege == 'r')
		{
		shelvedRecords = module.SearchRadiologist(request.getParameter("query"), request.getParameter("order"), ID);
		}

		for(int i = 0;i < shelvedRecords.size();i++)
		{
%>
			<TR>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).record_id); %></TD>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).test_type); %></TD>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).prescribing_date); %></TD>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).test_date); %></TD>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).diagnosis); %></TD>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).description); %></TD>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).name); %></TD>
			<TD class = "resTD" ><% out.println(shelvedRecords.get(i).agg_score); %></TD>
			<TD class = "resTD" ><FORM action = "view.jsp" method = POST><input type="hidden" value="<%= shelvedRecords.get(i).record_id %>"><input type="submit" value="view"> </FORM></TD>
			</TR>
<%
		}
%>
</TABLE>
<%

              
          }

      %>

  </body>
</html>
