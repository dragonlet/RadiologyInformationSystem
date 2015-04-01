
package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


//*******************************************************
//               MANAGEMENT MODULE
// java code for user with management privilages.
//*******************************************************
public class ManagementModule extends BaseLayer {

	private boolean failure;
	private Connection conn;
	public String error_printout;

	public ManagementModule(){
		failure = false;
	}

  	public boolean FailedWithError()
    	{
		return failure;
    	}

	// takes in a query statment, establishes connection, and executes the query;
	public Boolean executequery(String query){ 

		

		
		ResultSet rset = null;

		try
	    {
		openConnection();
		rset = GetQueryResult(query);
	    }
		catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return false;
	    }

		try
	    {
		closeConnection();
	    }
		catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return false;
	    }


	return true;
	}

	// returns the user class given the passed ID
	public String getClass(String ID) throws BaseLayerException, SQLException

	{
		
		ResultSet rset = null;
		String ID_class = "";

		openConnection();
		rset = GetQueryResult("select class from users where person_id = " + ID);// select only the class of the given user

		while(rset != null && rset.next())
		    {
			ID_class = (rset.getString("class")).trim();
		    }


		closeConnection();

		return ID_class;

	}


	public String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		//get current date time with Date()
		Date date = new Date();
		return dateFormat.format(date);

	}



	public String getPersonsTable() throws BaseLayerException, SQLException
	{
		ResultSet rset = null;
		String results = "";
		openConnection();
		rset = GetQueryResult("select * from persons");

		if (rset != null){
			results = "<table border=1> <tr> <th>ID</th> <th>First Name</th> <th>Last Name</th> <th>Address</th> <th>Email</th> <th>Phone Number</th> </tr>" + "<tr>";
			while(rset.next())
			    {
				results = results + "<td>" + rset.getString(1) + "</td>";
				results = results + "<td>" + rset.getString(2) + "</td>";
				results = results + "<td>" + rset.getString(3) + "</td>";
				results = results + "<td>" + rset.getString(4) + "</td>";
				results = results + "<td>" + rset.getString(5) + "</td>";
				results = results + "<td>" + rset.getString(6) + "</td>";
				results = results + "</tr>";
			    }
			results = results + "</table>";

		}


		closeConnection();


		return results;
	}


	public String getUsersTable() throws BaseLayerException, SQLException
	{
		ResultSet rset = null;
		String results = "";
		openConnection();
		rset = GetQueryResult("select * from users");

		if (rset != null){
			results = "<table border=1> <tr> <th>User Name</th> <th>Class</th> <th>ID</th> <th>Date Registered</th> </tr>" + "<tr>";
			while(rset.next())
			    {
				results = results + "<td>" + rset.getString(1) + "</td>";
				results = results + "<td>" + rset.getString(3) + "</td>";
				results = results + "<td>" + rset.getString(4) + "</td>";
				results = results + "<td>" + rset.getString(5) + "</td>";
				results = results + "</tr>";
			    }
			results = results + "</table>";

		}


		closeConnection();


		return results;
	}


	public String getDoctorsTable() throws BaseLayerException, SQLException
	{
		ResultSet rset = null;
		String results = "";
		openConnection();
		rset = GetQueryResult("select * from family_doctor");

		if (rset != null){
			results = "<table border=1> <tr> <th>Doctor ID</th> <th>Patient ID</th> </tr>" + "<tr>";
			while(rset.next())
			    {
				results = results + "<td>" + rset.getString(1) + "</td>";
				results = results + "<td>" + rset.getString(2) + "</td>";
				results = results + "</tr>";
			    }
			results = results + "</table>";

		}


		closeConnection();


		return results;
	}





}




