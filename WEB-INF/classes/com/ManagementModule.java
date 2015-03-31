
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







}





