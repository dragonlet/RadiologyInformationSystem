package com;
import java.lang.*;
import java.io.*;
import java.sql.*;

<<<<<<< HEAD
public class LoginLayer extends BaseLayer{
=======
public class LoginLayer {
>>>>>>> master
    private char user_privilege_level;
    private boolean logged_in;
    private boolean failure;
    public String error_printout;
	

    public LoginLayer()
    {		
		user_privilege_level = '0';
		logged_in = false;
		failure = false;
    }

    public char getPrivs()
    {
		return user_privilege_level;
    }
    
    public boolean isLoggedIn()
    {
		return logged_in;
    }

    public boolean failedWithError()
    {
		return failure;
    }

    public boolean validateLogin(String username, String password)
    {
<<<<<<< HEAD
	ResultSet rset = null;
	String truepwd = null;
	String user_class = null;
	
	try
	    {
		openConnection();
		rset = GetQueryResult(genValidateUserSql(username));
	    }
	catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return false;
	    }
		
	try
	    {
		while(rset != null && rset.next())
		    {
			truepwd = (rset.getString("password")).trim();
			user_class = (rset.getString("class")).trim();
		    }
	    }
	catch(Exception ex)
	    {
		error_printout = "<hr>" + ex.getMessage() + "<hr>";
		failure = true;
		return false;
	    }

	if(truepwd == null)
	    {
		/* No matching rows were found => Password is incorrect. */
		return false;
	    }
			
	/* If valid, log the user in with the appropriate priviliges. */
	if(truepwd.length() > 0 && password.equals(truepwd))
	    {
		logged_in = true;
		user_privilege_level = user_class.charAt(0);
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

	return logged_in;
    }
	
    private String genValidateUserSql(String username)
    {
	return "select password, class from users where user_name =" + "'" + username + "'";
    }
=======
		BaseLayer _baseLayer = new BaseLayer();
		ResultSet rset = null;
		String truepwd = null;
		String user_class = null;
		
		try
		{
			rset = _baseLayer.GetQueryResult(genValidateUserSql(username));
		}
		catch(Exception ex)
		{
			error_printout = "<hr>" + ex.getMessage() + "<hr>";
			failure = true;
			return false;
		}
		
		try
		{
			while(rset != null && rset.next())
			{
				truepwd = (rset.getString("password")).trim();
				user_class = (rset.getString("class")).trim();
			}
		}
		catch(Exception ex)
		{
			error_printout = "<hr>" + ex.getMessage() + "<hr>";
			failure = true;
			return false;
		}
			
		/* If valid, log the user in with the appropriate priviliges. */
		if(truepwd.length() > 0 && password.equals(truepwd))
		{
			logged_in = true;
			user_privilege_level = user_class.charAt(0);
		}

		return logged_in;
    }
	
	private String genValidateUserSql(String username)
	{
		return "select password, class from users where user_name =" + "'" + username + "'";
	}
>>>>>>> master
}
