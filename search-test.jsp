<!DOCTYPE html>
<%@ include file="includes.jsp" %>
<%@ page import="java.sql.*, java.util.*" %>

<HTML>
<%@ include file="navbar.jsp" %>





  <head>
  <meta http-equiv="content-type" content="text/html; charset=windows-1250">
  <title>Inverted Index example</title>
  </head>
  <body>
    <p> Suppose we have the following table <br> 
    <table border=1>
      <tr>
        <th>Column Name</th>
        <th>Column Type</th>
      </tr>
      <tr>
        <td>ItemId</td>
        <td>Integer</td>
      </tr>
      <tr>
        <td>ItemName</td>
        <td>Varchar</td>
      </tr>
      <tr>
        <td>Description</td>
        <td>Varchar</td>
      </tr>
    </table>
    
    <p>The <a href=item.sql>sql</a> for the above table</p>
    <br>
    
    <%
      String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
      String m_driverName = "oracle.jdbc.driver.OracleDriver";
      
      String m_userName = ""; //supply username
      String m_password = ""; //supply password
      
      String addItemError = "";
      
      Connection m_con;
      String createString;
      String selectString = "select itemName, description from item";
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
             
        //first try to see if we are adding any items to the database
        if (request.getParameter("addRecord") != null)
        {
          if(!(request.getParameter("itemName").equals("") || request.getParameter("description").equals("")))
          {
             //disable the auto commit mode
             m_con.setAutoCommit(false);
            
            Statement stmt2 = m_con.createStatement();
            ResultSet rset2 = stmt2.executeQuery("select item_seq.nextVal from dual");
            int nextItemId;
            if(rset2.next())
            {
              nextItemId = rset2.getInt(1);
            }
            else    
            {
                m_con.close();
                out.println("<b>Error: item_seq does not exist</b>");
                return;       
            }
            
            PreparedStatement addItem = m_con.prepareStatement("insert into item values(?, ?, ?)");
            addItem.setInt(1, nextItemId);
            addItem.setString(2, request.getParameter("itemName"));
            addItem.setString(3, request.getParameter("description"));
            addItem.executeUpdate();
            m_con.commit();
            stmt2.close();
            addItem.close();
            //enable the auto commit mode
            m_con.setAutoCommit(true);
          }
          else
          {
            addItemError = "Item name or description is missing\n";
          }
        }
        else if(request.getParameter("updateIndex") != null)
        {
        }
        
        stmt = m_con.createStatement();
        ResultSet rset = stmt.executeQuery(selectString);
        out.println("<table border=1>");
        out.println("<tr>");
        out.println("<th>Item Name</th>");
        out.println("<th>Item Description</th>");
        out.println("</tr>"); 
        while(rset.next()) { 
          out.println("<tr>");
          out.println("<td>"); 
          out.println(rset.getString(1));
          out.println("</td>");
          out.println("<td>"); 
          out.println(rset.getString(2)); 
          out.println("</td>");
          out.println("</tr>"); 
        } 
        out.println("</table>");
        stmt.close();     
       
      
    %>
    
    <br><br>
    We can create an inverted index on the column description using the following sql:
    
    <table border=1>
      <tr>
        <td face=Courier>
          CREATE INDEX index_name ON item(column_name) INDEXTYPE IS CTXSYS.CONTEXT;
          <br>
        </td>
      </tr>
    </table>
    <p>The <a href=myIndex.sql>sql</a> for creating the index</p>
        Once the index is created we need to tell oracle to keep updating the index as new data is entered (this is turned off by default). To do this run the this <a href=drjobdml.sql>sql file</a>. This sql command takes two parameters: index name and rate of update (in seconds). 
    
    <br><br><br>
    We can now query the table to find all documents and their relvance for a certain list of words
    through the following query:
    <table border=1>
      <tr>
        <td face=Courier>
          SELECT score(1), itemName FROM item WHERE contains(description, 'database', 1) > 0 order by score(1) desc;
          <br>
        </td>
      </tr>
    </table>
    This query returns all the item names along with their relevance score sorted descendingly by the relevance score
    <br>
    <br>
    <br>
    You can add data to the table and then try querying it <br>
    <b><%=addItemError%></b><br>
    <form name=insertData method=post action=search-test.jsp> 
      <table>
        <tr>
          <td>Item Name</td>
          <td><input type=text name=itemName maxlengh=100> </td>
        </tr>
        <tr>
          <td>Item description</td>
          <td><textarea name=description cols=40 rows=6></textarea>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td><input type=submit name="addRecord" value="Add record">
        </tr>
      </table>
    <br>
    <br>
    <br>
      <!--Every time you add or update data you need to update the index. <br>
      <input type=submit name="updateIndex" value="Update Index">
    	<br>
    	<br>
    	<br>
    -->
    
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
          
            if(!(request.getParameter("query").equals("")))
            {
              PreparedStatement doSearch = m_con.prepareStatement("SELECT score(1), itemName, description FROM item WHERE contains(description, ?, 1) > 0 order by score(1) desc");
              doSearch.setString(1, request.getParameter("query"));
              ResultSet rset2 = doSearch.executeQuery();
              out.println("<table border=1>");
              out.println("<tr>");
              out.println("<th>Item Name</th>");
              out.println("<th>Item Description</th>");
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
