package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;




//*******************************************************
//               SEARCH MODULE
// java code for Searching the database.
//*******************************************************
public class SearchLayer extends BaseLayer {


    private boolean failure;
    private Connection conn;
    public String error_printout;
    public ArrayList<RecordShelf> shelvedRecords;

    public SearchLayer(){
	shelvedRecords = new ArrayList<RecordShelf>();
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
	    toReturn = toReturn + "<th>Patient ID</th>";
	    toReturn = toReturn + "<th>Doctor ID</th>";
	    toReturn = toReturn + "<th>Radiologist ID</th>";
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
		toReturn = toReturn + rset.getString("patient_id");
		toReturn = toReturn + "</td>";
		toReturn = toReturn + "<td>"; 
		toReturn = toReturn + rset.getString("doctor_id");
		toReturn = toReturn + "</td>";
		toReturn = toReturn + "<td>"; 
		toReturn = toReturn + rset.getString("radiologist_id");
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


    public ArrayList SearchAll(String query, String order) throws BaseLayerException, SQLException
    {



	String toReturn = "";
      
	String createString;
	Statement stmt;


	ResultSet rset_desc = null;
	ResultSet rset_diag = null;
	ResultSet rset_rank = null;


	openConnection();





            
	String searchDesc = "CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) as score, record_id,  patient_id, doctor_id, radiologist_id, test_type, prescribing_date, test_date, diagnosis, description FROM radiology_record WHERE (contains(description, '"+query+"', 1) >= 0)";
	String searchDiag = "CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id, patient_id, diagnosis, description FROM radiology_record WHERE (contains(diagnosis, '"+query+"', 1) >= 0)";
		

	String searchFname = "CREATE OR REPLACE VIEW tempFname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.first_name, '"+query+"', 1) >= 0";

	String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.last_name, '"+query+"', 1) >= 0";

	//String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons, radiology_record WHERE (contains(last_name, '"+query+"', 1) >= 0) AND person_id = patient_id";


	//String rank = "CREATE OR REPLACE VIEW tempRank AS SELECT DE.record_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score, DE.patient_id, DE.doctor_id, DE.radiologist_id, DE.test_type, DE.prescribing_date, DE.test_date,  DE.description, DI.diagnosis, PF.first_name, PL.last_name FROM tempDesc DE, tempDiag DI, tempFname PF, tempLname PL WHERE DE.record_id = DI.record_id AND DE.record_id = PF.record_id AND DE.record_id = PL.record_id AND ((DE.score + DI.score + PF.score + PL.score) > 0) order by aggregate_score desc";

	String rank = "CREATE OR REPLACE VIEW tempRank AS SELECT DE.record_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score, DE.patient_id, DE.doctor_id, DE.radiologist_id, DE.test_type, DE.prescribing_date, DE.test_date,  DE.description, DI.diagnosis, PF.first_name, PL.last_name FROM tempDesc DE, tempDiag DI, tempFname PF, tempLname PL WHERE DE.record_id = DI.record_id AND DE.record_id = PF.record_id AND DE.record_id = PL.record_id AND ((DE.score + DI.score + PF.score + PL.score) > 0) " + order;


	GetQueryResult(searchDesc);
	GetQueryResult(searchDiag);
	GetQueryResult(searchFname);
	GetQueryResult(searchLname);

	GetQueryResult(rank);
	rset_rank = GetQueryResult("SELECT * FROM tempRank A LEFT JOIN pacs_images P ON A.record_id = P.record_id WHERE A.aggregate_score > 0");



		


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
             



	while(rset_rank.next())
	    {
		String[] params = new String[8];
		params[0] = rset_rank.getString("record_id");
		params[1] = rset_rank.getString("test_type");
		params[2] = rset_rank.getString("prescribing_date"); 
		params[3] = rset_rank.getString("test_date"); 
		params[4] = rset_rank.getString("diagnosis"); 
		params[5] = rset_rank.getString("description"); 
		params[6] = rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
		params[7] = Integer.toString(rset_rank.getInt("aggregate_score"));
		shelvedRecords.add(new RecordShelf(params));

                toReturn = toReturn + "<tr>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_rank.getString("record_id");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_rank.getString("test_type");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_rank.getString("prescribing_date"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_rank.getString("test_date"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_rank.getString("diagnosis"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_rank.getString("description"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>"; 
                toReturn = toReturn + rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "<td>";
                toReturn = toReturn + rset_rank.getObject("aggregate_score");
                toReturn = toReturn + "</td>";
                toReturn = toReturn + "</tr>";

	    }










		closeConnection();

		return shelvedRecords;
    }









	public ArrayList SearchPatient(String query, String order, String ID) throws BaseLayerException, SQLException
	    {



		String toReturn = "";
      
      		String createString;
      		Statement stmt;


		ResultSet rset_desc = null;
		ResultSet rset_diag = null;
		ResultSet rset_rank = null;


		openConnection();




            
		String searchDesc = "CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) as score, record_id,  patient_id, doctor_id, radiologist_id, test_type, prescribing_date, test_date, diagnosis, description FROM radiology_record WHERE (contains(description, '"+query+"', 1) >= 0) AND patient_id = '" + ID + "'";
		String searchDiag = "CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id, patient_id, diagnosis, description FROM radiology_record WHERE (contains(diagnosis, '"+query+"', 1) >= 0)  AND patient_id = '" + ID + "'";
		

		String searchFname = "CREATE OR REPLACE VIEW tempFname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.first_name, '"+query+"', 1) >= 0  AND patient_id = '" + ID + "'";

		String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.last_name, '"+query+"', 1) >= 0  AND patient_id = '" + ID + "'";

		//String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons, radiology_record WHERE (contains(last_name, '"+query+"', 1) >= 0) AND person_id = patient_id";


		String rank = "CREATE OR REPLACE VIEW tempRank AS SELECT DE.record_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score, DE.patient_id, DE.doctor_id, DE.radiologist_id, DE.test_type, DE.prescribing_date, DE.test_date,  DE.description, DI.diagnosis, PF.first_name, PL.last_name FROM tempDesc DE, tempDiag DI, tempFname PF, tempLname PL WHERE DE.record_id = DI.record_id AND DE.record_id = PF.record_id AND DE.record_id = PL.record_id AND ((DE.score + DI.score + PF.score + PL.score) > 0)  " + order;


             

		GetQueryResult(searchDesc);
		GetQueryResult(searchDiag);
		GetQueryResult(searchFname);
		GetQueryResult(searchLname);



		GetQueryResult(rank);
		rset_rank = GetQueryResult("SELECT * FROM tempRank A LEFT JOIN pacs_images P ON A.record_id = P.record_id WHERE A.aggregate_score > 0");



		


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
             



		while(rset_rank.next())
		    {
			String[] params = new String[8];
			params[0] = rset_rank.getString("record_id");
			params[1] = rset_rank.getString("test_type");
			params[2] = rset_rank.getString("prescribing_date"); 
			params[3] = rset_rank.getString("test_date"); 
			params[4] = rset_rank.getString("diagnosis"); 
			params[5] = rset_rank.getString("description"); 
			params[6] = rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
			params[7] = Integer.toString(rset_rank.getInt("aggregate_score"));
			shelvedRecords.add(new RecordShelf(params));

			toReturn = toReturn + "<tr>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("record_id");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("test_type");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("prescribing_date"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>";
			toReturn = toReturn + rset_rank.getString("test_date"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("diagnosis"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("description"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>";
			toReturn = toReturn + rset_rank.getObject("aggregate_score");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "</tr>";
		    }












		closeConnection();


		return shelvedRecords;
	    }








	public ArrayList SearchDoctor(String query, String order, String ID) throws BaseLayerException, SQLException
	    {
		


		String toReturn = "";
      
      		String createString;
      		Statement stmt;


		ResultSet rset_desc = null;
		ResultSet rset_diag = null;
		ResultSet rset_rank = null;

		openConnection();




            
		String searchDesc = "CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) as score, record_id,  patient_id, doctor_id, radiologist_id, test_type, prescribing_date, test_date, diagnosis, description FROM radiology_record WHERE (contains(description, '"+query+"', 1) >= 0) AND doctor_id = '" + ID + "'";
		String searchDiag = "CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id, patient_id, diagnosis, description FROM radiology_record WHERE (contains(diagnosis, '"+query+"', 1) >= 0)  AND doctor_id = '" + ID + "'";
		

		String searchFname = "CREATE OR REPLACE VIEW tempFname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.first_name, '"+query+"', 1) >= 0  AND doctor_id = '" + ID + "'";

		String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.last_name, '"+query+"', 1) >= 0  AND doctor_id = '" + ID + "'";

		//String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons, radiology_record WHERE (contains(last_name, '"+query+"', 1) >= 0) AND person_id = patient_id";


		String rank = "CREATE OR REPLACE VIEW tempRank AS SELECT DE.record_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score, DE.patient_id, DE.doctor_id, DE.radiologist_id, DE.test_type, DE.prescribing_date, DE.test_date,  DE.description, DI.diagnosis, PF.first_name, PL.last_name FROM tempDesc DE, tempDiag DI, tempFname PF, tempLname PL WHERE DE.record_id = DI.record_id AND DE.record_id = PF.record_id AND DE.record_id = PL.record_id AND ((DE.score + DI.score + PF.score + PL.score) > 0)  " + order;


		GetQueryResult(searchDesc);
		GetQueryResult(searchDiag);
		GetQueryResult(searchFname);
		GetQueryResult(searchLname);


		GetQueryResult(rank);
		rset_rank = GetQueryResult("SELECT * FROM tempRank A LEFT JOIN pacs_images P ON A.record_id = P.record_id WHERE A.aggregate_score > 0");


		


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
             



		while(rset_rank.next())
		    {
			String[] params = new String[8];
			params[0] = rset_rank.getString("record_id");
			params[1] = rset_rank.getString("test_type");
			params[2] = rset_rank.getString("prescribing_date"); 
			params[3] = rset_rank.getString("test_date"); 
			params[4] = rset_rank.getString("diagnosis"); 
			params[5] = rset_rank.getString("description"); 
			params[6] = rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
			params[7] = Integer.toString(rset_rank.getInt("aggregate_score"));
			shelvedRecords.add(new RecordShelf(params));

			toReturn = toReturn + "<tr>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("record_id");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("test_type");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("prescribing_date"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>";
			toReturn = toReturn + rset_rank.getString("test_date"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("diagnosis"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("description"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>";
			toReturn = toReturn + rset_rank.getObject("aggregate_score");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "</tr>";
		    } 











		closeConnection();

         
          

		return shelvedRecords;
	    }






	public ArrayList SearchRadiologist(String query, String order, String ID) throws BaseLayerException, SQLException
	    {



		String toReturn = "";
      
      		String createString;
      		Statement stmt;


		ResultSet rset_desc = null;
		ResultSet rset_diag = null;
		ResultSet rset_rank = null;

		openConnection();



            
		String searchDesc = "CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) as score, record_id,  patient_id, doctor_id, radiologist_id, test_type, prescribing_date, test_date, diagnosis, description FROM radiology_record WHERE (contains(description, '"+query+"', 1) >= 0) AND radiologist_id = '" + ID + "'";
		String searchDiag = "CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id, patient_id, diagnosis, description FROM radiology_record WHERE (contains(diagnosis, '"+query+"', 1) >= 0)  AND radiologist_id = '" + ID + "'";
		

		String searchFname = "CREATE OR REPLACE VIEW tempFname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.first_name, '"+query+"', 1) >= 0  AND radiologist_id = '" + ID + "'";

		String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.last_name, '"+query+"', 1) >= 0  AND radiologist_id = '" + ID + "'";

		//String searchLname = "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons, radiology_record WHERE (contains(last_name, '"+query+"', 1) >= 0) AND person_id = patient_id";


		String rank = "CREATE OR REPLACE VIEW tempRank AS SELECT DE.record_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score, DE.patient_id, DE.doctor_id, DE.radiologist_id, DE.test_type, DE.prescribing_date, DE.test_date,  DE.description, DI.diagnosis, PF.first_name, PL.last_name FROM tempDesc DE, tempDiag DI, tempFname PF, tempLname PL WHERE DE.record_id = DI.record_id AND DE.record_id = PF.record_id AND DE.record_id = PL.record_id AND ((DE.score + DI.score + PF.score + PL.score) > 0) " + order;



		GetQueryResult(searchDesc);
		GetQueryResult(searchDiag);
		GetQueryResult(searchFname);
		GetQueryResult(searchLname);

		GetQueryResult(rank);
		rset_rank = GetQueryResult("SELECT * FROM tempRank A LEFT JOIN pacs_images P ON A.record_id = P.record_id WHERE A.aggregate_score > 0");
	


		


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
             


		while(rset_rank.next())
		    {
			String[] params = new String[8];
			params[0] = rset_rank.getString("record_id");
			params[1] = rset_rank.getString("test_type");
			params[2] = rset_rank.getString("prescribing_date"); 
			params[3] = rset_rank.getString("test_date"); 
			params[4] = rset_rank.getString("diagnosis"); 
			params[5] = rset_rank.getString("description"); 
			params[6] = rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
			params[7] = Integer.toString(rset_rank.getInt("aggregate_score"));
			shelvedRecords.add(new RecordShelf(params));

			toReturn = toReturn + "<tr>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("record_id");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("test_type");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("prescribing_date"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>";
			toReturn = toReturn + rset_rank.getString("test_date"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("diagnosis"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("description"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>"; 
			toReturn = toReturn + rset_rank.getString("first_name") + " " + rset_rank.getString("last_name"); 
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "<td>";
			toReturn = toReturn + rset_rank.getObject("aggregate_score");
			toReturn = toReturn + "</td>";
			toReturn = toReturn + "</tr>";
		    } 










		closeConnection();



		return shelvedRecords;
	    }






    }





