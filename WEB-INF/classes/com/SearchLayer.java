package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;




//*******************************************************
//               SEARCH MODULE
// java code for Searching the database.
//*******************************************************
public class SearchLayer extends BaseLayer {


	private boolean failure;
	private Connection conn;
	public String error_printout;

	public SearchLayer(){
		failure = false;
	}

  	public boolean FailedWithError()
    	{
		return failure;
    	}


	public String Display(){

		String addItemError = "";
		String toReturn = "";
      
      		Connection m_con;
      		String createString;
      		String selectString = "select * from radiology_record";
      		Statement stmt;


		ResultSet rset = null;

		try
	    {
		openConnection();
	    }
		catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return "1";
	    }





		try{

       			rset = GetQueryResult(selectString);

		}
		catch(Exception ex)
		{
			return "2";
		}

		try{
        		toReturn = toReturn + "<table border=1>";
        		toReturn = toReturn + "<tr>";
        		toReturn = toReturn + "<th>Record ID</th>";
        		toReturn = toReturn + "<th>Test Type</th>";
        		toReturn = toReturn + "<th>Prescribing Date</th>";
        		toReturn = toReturn + "<th>Test Date</th>";
        		toReturn = toReturn + "<th>diagnosis</th>";
        		toReturn = toReturn + "<th>Description</th>";
        		toReturn = toReturn + "</tr>"; 
        	    while(rset.next()) { 
        		  toReturn = toReturn + "<tr>";
        		  toReturn = toReturn + "<td>"; 
        		  toReturn = toReturn + rset.getString("record_id");
        		  toReturn = toReturn + "</td>";
        		  toReturn = toReturn + "<td>"; 
        		  toReturn = toReturn + rset.getString("test_type"); 
        		  toReturn = toReturn + "</td>";
        		  toReturn = toReturn + "<td>"; 
        		  toReturn = toReturn + rset.getString("prescribing_date"); 
        		  toReturn = toReturn + "</td>";
        		  toReturn = toReturn + "<td>"; 
        		  toReturn = toReturn + rset.getString("test_date"); 
        		  toReturn = toReturn + "</td>";
        		  toReturn = toReturn + "<td>"; 
        		  toReturn = toReturn + rset.getString("diagnosis"); 
        		  toReturn = toReturn + "</td>";
        		  toReturn = toReturn + "<td>"; 
        		  toReturn = toReturn + rset.getString("description"); 
        		  toReturn = toReturn + "</td>";
        		  toReturn = toReturn + "</tr>"; 
        		}
       		 toReturn = toReturn + "</table>";

		}
		
		catch(Exception ex)
		{
			return "3";
		}
       		   
       
	return toReturn;

	}


	public String DescriptionSearch(String query){



		String toReturn = "";
      
      		String createString;
      		Statement stmt;


		ResultSet rset2 = null;

		try
	    {
		openConnection();
	    }
		catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return "<hr>" + ex.getMessage() + "<hr>";
	    }




              String doSearch = "SELECT score(1), record_id, description FROM radiology_record WHERE contains(description, '"+query+"', 1) > 0 order by score(1) desc";

             
		try{
	      		rset2 = GetQueryResult(doSearch);
		}
		catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		} 


              toReturn = toReturn + "<table border=1>";
              toReturn = toReturn + "<tr>";
              toReturn = toReturn + "<th>Record ID</th>";
              toReturn = toReturn + "<th>Description</th>";
              toReturn = toReturn + "<th>Score</th>";
              toReturn = toReturn + "</tr>";
             

	      try{
	      while(rset2.next())
              {
                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset2.getString(2);
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset2.getString(3); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset2.getObject(1);
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "</tr>";
              } 
              toReturn = toReturn + "</table>";
              }
	      catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		}                
          

	return toReturn;
	}




}
