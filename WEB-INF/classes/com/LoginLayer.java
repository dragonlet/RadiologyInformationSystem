package com;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class LoginLayer extends BaseLayer{
    private char user_privilege_level;
    private boolean logged_in;
    private boolean failure;

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
	throws BaseLayerException, SQLException
    {
		ResultSet rset = null;
		String truepwd = null;
		String user_class = null;
	
		openConnection();
		rset = GetQueryResult(genValidateUserSql(username));
		
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
			//Should we make a Login Layer Exception for this??
			throw ex;
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

		closeConnection();

		return logged_in;
    }
	
    private String genValidateUserSql(String username)
    {
		return "select password, class from users where user_name =" + "'" + username + "'";
    }
}
