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


		ResultSet rset_desc = null;
		ResultSet rset_diag = null;
		ResultSet rset_temp = null;

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




             // String doSearch = "SELECT score(1), record_id, description FROM radiology_record WHERE contains(description, '"+query+"', 1) > 0 order by score(1) desc";
              String searchDesc = "SELECT score(1), score(2), record_id, diagnosis, description FROM radiology_record WHERE (contains(description, '"+query+"', 1) > 0) OR (contains(diagnosis, '"+query+"', 2) > 0)";
		String searchDiag = "SELECT score(1), record_id, diagnosis, description FROM radiology_record WHERE contains(diagnosis, '"+query+"', 1) > 0";
		//String searchDesc = "SELECT score(1), record_id, diagnosis, description FROM radiology_record WHERE contains(description, '"+query+"', 1) > 0";

		String tempDesc = "CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) as score, record_id FROM radiology_record WHERE contains(description, '"+query+"', 1) > 0";

		String tempDiag = "CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id FROM radiology_record WHERE contains(diagnosis, '"+query+"', 1) > 0";



             
		try{
	      		rset_desc = GetQueryResult(searchDesc);
	      		rset_diag = GetQueryResult(searchDiag);
			//Resultset rset3 = GetQueryResult("");
			rset_temp = GetQueryResult(tempDesc);
			rset_temp = GetQueryResult(tempDiag);
		}
		catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		} 


		


              toReturn = toReturn + "<table border=1>";
              toReturn = toReturn + "<tr>";
              toReturn = toReturn + "<th>Record ID</th>";
              toReturn = toReturn + "<th>Diagnosis</th>";
              toReturn = toReturn + "<th>Description</th>";
              toReturn = toReturn + "<th>Score(1)</th>";
              toReturn = toReturn + "<th>Score(2)</th>";
              toReturn = toReturn + "</tr>";
             

	      try{
	      while(rset_desc.next())
              {
                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_desc.getString("record_id");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_desc.getString("diagnosis"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_desc.getString("description"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_desc.getObject("score(1)");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_desc.getObject("score(2)");
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
