package com;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class LoginLayer extends BaseLayer{
    private Character user_privilege_level;
    private boolean logged_in;
    private boolean failure;
	private String user_id = "none";

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

	public String getUserID()
	{
		return user_id;
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

	while(rset != null && rset.next())
	    {
		truepwd = (rset.getString("password")).trim();
		user_class = (rset.getString("class")).trim();
		user_id = (rset.getString("person_id")).trim();
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
		return "select password, class, person_id from users where user_name =" + "'" + username + "'";
    }
}
