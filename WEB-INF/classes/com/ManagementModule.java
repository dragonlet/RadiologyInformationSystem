
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



	private boolean oracle_login(){
		/* Establish connectionn to DB.*/
		 conn = null;
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String dbstring = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
		try{
			//load and register the driver
			Class drvClass = Class.forName(driverName);
			DriverManager.registerDriver((Driver) drvClass.newInstance());
		}
		catch(Exception ex){
			error_printout = "<hr>" + ex.getMessage() + "<hr>";
			failure = true;
			return false;
		}
		try{
			//establish the connection
			conn = DriverManager.getConnection(dbstring,"mrgallag","iCaprica6"); /* Oracle login info here */
			conn.setAutoCommit(false);
		}
		catch(Exception ex){
			error_printout = "<hr>" + ex.getMessage() + "<hr>";
			failure = true;
			return false;
		}

		return true;
	}


	private boolean oracle_logout(){
		try{
			conn.close();
		}
		catch(Exception ex){
			error_printout = "<hr>" + ex.getMessage() + "<hr>";
			failure = true;
			return false;
		}
		return true;
	}


	public Boolean NewUser(String user_name, String password, String user_class, String p_ID, String date){ 
		// Add a new user entry into the Users table based on the information provided
		//INSERT INTO users VALUES("'"+user_name+"'", "'"+password+"'", "'"+user_class+"'", p_ID, "TO_DATE('"+date+"','YYYYMMDD')"); 
		
		
		oracle_login();




		Statement stmt = null;
		ResultSet rset = null;

		String new_user = "INSERT INTO users VALUES('"+user_name+"', '"+password+"', '"+user_class+"'," +p_ID+", TO_DATE('"+date+"','YYYYMMDD'));";

		/* Query the user table to determine if a vaid username/password combination has been entered. */
		try{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset = stmt.executeQuery(new_user);
		}
		catch(Exception ex){
			error_printout = "<hr>" + ex.getMessage() + "<hr>";
			failure = true;
			return false;
		}

		

		oracle_logout();
	

		if (failure)
			return false;
		else
			return true;	
	}



	public Boolean UpdateUser(String user_name, String password, String user_class, String p_ID, String date){ 
	// Update any given user in the Users table (assume anything left blank remains constant)
		return true;	
	}
	public Boolean NewPerson(String p_id, String first_name, String last_name, String address, String email, String phone){ 
	// new person to Persons table
	//INSERT INTO persons VALUES(13,'Matt','Gallagher','1234abc','m@1234.com', 7801234567);
		
		oracle_login();


		Statement stmt = null;
		ResultSet rset = null;
		
		// create the query using the information passed to the function
		String new_user = "INSERT INTO persons VALUES("+p_id+", '"+first_name+"', '"+last_name+"', '"+address+"', '"+email+"', "+phone+")";

		
		// try to execute the query
		try{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset = stmt.executeQuery(new_user);
		}
		// catch any error if the query failed
		catch(Exception ex){
			error_printout = "<hr> Line:135 " + ex.getMessage() + "<hr>";
			failure = true; //confirm that a failure occured
		}

		

		oracle_logout();
	

		if (failure)
			return false;
		else
			return true;	
	}
	public Boolean UpdatePerson(String p_id, String first_name, String last_name, String address, String email, String phone){ 
	// update Persons table, same criteria as above
		return true;	
	}
	public Boolean NewDoctor(String doctor_id, String patient_id){  
	// new to family_doctor table
		return true;	
	}
	public Boolean UpdateDoctor(String doctor_id, String patient_id){ 
	// update to family_doctor table
		return true;	
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







}


//INSERT INTO persons VALUES(13,'Matt','Gallagher','1234abc','m@1234.com', 7801234567);
//INSERT INTO users VALUES('Matt333', 'test1', 'r', 13, TO_DATE('19910227','YYYYMMDD')); 

/*

UPDATE users SET user_name = '', password = 'new' WHERE person_id = 13;


*/




