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

		try
	    {
		closeConnection();
	    }
		catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return "<hr>" + ex.getMessage() + "<hr>";
	    }
       		   
       
	return toReturn;

	}


	public String Search(String query){



		String toReturn = "";
      
      		String createString;
      		Statement stmt;


		ResultSet rset_desc = null;
		ResultSet rset_diag = null;
		ResultSet rset_records = null;
		ResultSet rset_rank = null;

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
              String searchDesc = "CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) as score, record_id, patient_id, diagnosis, description FROM radiology_record WHERE (contains(description, '"+query+"', 1) >= 0)";
	      String searchDiag = "CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id, patient_id, diagnosis, description FROM radiology_record WHERE (contains(diagnosis, '"+query+"', 1) >= 0)";
		//String searchDiag = "SELECT score(1), record_id, diagnosis, description FROM radiology_record WHERE contains(diagnosis, '"+query+"', 1) > 0";
		//String searchDesc = "SELECT score(1), record_id, diagnosis, description FROM radiology_record WHERE contains(description, '"+query+"', 1) > 0";

		//String tempDesc = "INSERT INTO temporary AS SELECT score(1), record_id FROM radiology_record WHERE contains(description, '"+query+"', 1) > 0";

		//String tempDesc = "SELECT score(1) as score, record_id INTO temporary FROM radiology_record WHERE contains(description, '"+query+"', 1) > 0";

		String searchFname = "CREATE OR REPLACE VIEW tempFname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons, radiology_record WHERE (contains(first_name, '"+query+"', 1) >= 0) AND person_id = patient_id";

		String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons, radiology_record WHERE (contains(last_name, '"+query+"', 1) >= 0) AND person_id = patient_id";


String rank = "CREATE OR REPLACE VIEW tempRank AS SELECT DE.record_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score FROM tempDesc DE, tempDiag DI, tempFname PF, tempLname PL WHERE DE.record_id = DI.record_id AND DE.record_id = PF.record_id AND DE.record_id = PL.record_id AND ((DE.score + DI.score + PF.score + PL.score) > 0) order by aggregate_score desc";

String records = "SELECT * FROM radiology_record, tempRank, tempFname, tempLname WHERE radiology_record.record_id = tempRank.record_id AND radiology_record.record_id = tempFname.record_id AND radiology_record.record_id = tempLname.record_id";

             
		try{
	      		GetQueryResult(searchDesc);
	      		GetQueryResult(searchDiag);
	      		GetQueryResult(searchFname);
	      		GetQueryResult(searchLname);
			GetQueryResult(rank);
			rset_rank = GetQueryResult("SELECT * FROM tempRank");
			rset_records = GetQueryResult(records);
		}
		catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		} 


		


              toReturn = toReturn + "<table border=1>";
              toReturn = toReturn + "<tr>";
              toReturn = toReturn + "<th>Record ID</th>";
              toReturn = toReturn + "<th>Test Type</th>";
              toReturn = toReturn + "<th>Prescribing Date</th>";
              toReturn = toReturn + "<th>Test Date</th>";
              toReturn = toReturn + "<th>Diagnosis</th>";
              toReturn = toReturn + "<th>Description</th>";
              toReturn = toReturn + "<th>Patient Name</th>";
              toReturn = toReturn + "<th>Score</th>";
              toReturn = toReturn + "</tr>";
             


		try{
	      while(rset_records.next())
              {
                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_records.getString("record_id");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_records.getString("test_type");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_records.getString("prescribing_date"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_records.getString("test_date"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_records.getString("diagnosis"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_records.getString("description"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_records.getString("first_name") + " " + rset_records.getString("last_name"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_records.getObject("aggregate_score");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "</tr>";
              } 
              toReturn = toReturn + "</table>";
              }
	      catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		}  









/*
	      try{
	      while(rset_temp1.next())
              {
                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp1.getString("record_id");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp1.getString("diagnosis"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp1.getString("description"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_temp1.getObject("score1");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "</tr>";
              } 
              //toReturn = toReturn + "</table>";
              }
	      catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		}  


		try{
	      while(rset_temp2.next())
              {
                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp2.getString("record_id");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp2.getString("diagnosis"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp2.getString("description"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_temp2.getObject("score2");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "</tr>";
              } 

              }
	      catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		}  




		try{
	      while(rset_temp3.next())
              {
                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp3.getString("person_id");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp3.getString("First_name"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_temp3.getObject("score3");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "</tr>";
              } 

              }
	      catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		}  







		try{
	      while(rset_temp4.next())
              {
                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp4.getString("person_id");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_temp4.getString("last_name"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_temp4.getObject("score4");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "</tr>";
              } 
              toReturn = toReturn + "</table>";
              }
	      catch(Exception ex)
		{
			return "<hr>" + ex.getMessage() + "<hr>";
		}  







*/




		try
	    {
		closeConnection();
	    }
		catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return "<hr>" + ex.getMessage() + "<hr>";
	    }             
          

	return toReturn;
	}




}







// INSERT INTO radiology_record values(1008, 6, 7, 4, 'MRI', TO_DATE('20140915','YYYYMMDD'), TO_DATE('20150107','YYYYMMDD'), 'This is a test.', 'test 1 is test successfully');


