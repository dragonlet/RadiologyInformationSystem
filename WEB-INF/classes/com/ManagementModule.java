
package com;
import java.lang.*;
import java.io.*;
import java.sql.*;


//*******************************************************
//               MANAGEMENT MODULE
// java code for user with management privilages.
// TO DO: -discuss if delete is required (project 
//	   specification only references update and new)
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







}





