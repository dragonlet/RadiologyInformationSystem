
package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


//*******************************************************
//               My Account Layer
// java code for user with management privilages.
// TO DO: -discuss if delete is required (project 
//	   specification only references update and new)
//*******************************************************
public class MyAccountLayer extends BaseLayer {

	private boolean failure;
	private Connection conn;
	public String error_printout;

	public MyAccountLayer(){
		failure = false;
	}

  	public boolean FailedWithError()
    	{
		return failure;
    	}

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


	public String getID(String user_name) throws BaseLayerException, SQLException

	{
		
		ResultSet rset = null;
		String ID = "";

		openConnection();
		rset = GetQueryResult("select person_id from users where user_name = '" + user_name +"'");// select only the class of the given user

		while(rset != null && rset.next())
		    {
			ID = (rset.getString("person_id")).trim();
		    }


		closeConnection();

		return ID;

	}

}




