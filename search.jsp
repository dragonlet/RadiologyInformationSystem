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

	SearchLayer search = new SearchLayer();

	out.println(search.Display());

      String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
      String m_driverName = "oracle.jdbc.driver.OracleDriver";
      
      String m_userName = "mrgallag"; //supply username
      String m_password = "iCaprica6"; //supply password
      
      String addItemError = "";
      
      Connection m_con;
      String createString;
      String selectString = "select * from radiology_record";
      Statement stmt;
      
      try
      {
      
        Class drvClass = Class.forName(m_driverName);
        DriverManager.registerDriver((Driver)
        drvClass.newInstance());
        m_con = DriverManager.getConnection(m_url, m_userName, m_password);
        
      } 
      catch(Exception e)
      {      
        out.print("Error displaying data: ");
        out.println(e.getMessage());
        return;
      }

      try
      {
             
       
        if(request.getParameter("updateIndex") != null)
        {
        }
        
        stmt = m_con.createStatement();
        
        stmt.close();     
       
      
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
          
		out.println(search.DescriptionSearch(request.getParameter("query")));


            if(false)
            {
              PreparedStatement doSearch = m_con.prepareStatement("SELECT score(1), record_id, description FROM radiology_record WHERE contains(description, ?, 1) > 0 order by score(1) desc");
              doSearch.setString(1, request.getParameter("query"));
              ResultSet rset2 = doSearch.executeQuery();
              out.println("<table border=1>");
              out.println("<tr>");
              out.println("<th>Record ID</th>");
              out.println("<th>Description</th>");
              out.println("<th>Score</th>");
              out.println("</tr>");
              while(rset2.next())
              {
                out.println("<tr>");
                out.println("<td>"); 
                out.println(rset2.getString(2));
                out.println("</td>");
                out.println("<td>"); 
                out.println(rset2.getString(3)); 
                out.println("</td>");
                out.println("<td>");
                out.println(rset2.getObject(1));
                out.println("</td>");
                out.println("</tr>");
              } 
              out.println("</table>");
            }
            else
            {
              out.println("<br><b>Please enter text for quering</b>");
            }            
          }
          m_con.close();
        }
        catch(SQLException e)
        {
          out.println("SQLException: " +
          e.getMessage());
			m_con.rollback();
        }
      %>
    </form>
  </body>
</html>
