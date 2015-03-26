package com;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class BaseLayer{
    /* Any class extending the base layer should call open connection vefore executing
       queries using GetQueryResult(). Once finished, closeConnection should be called. */


    private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    private final String DB_CONN_STRING = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
    private final String ORA_USERNAME = "";
    private final String ORA_PASSWORD = "";
    private Connection conn;
	
    public BaseLayer(){}

    public void openConnection() throws BaseLayerException
    {
	try
	    {
		//load and register the driver
		Class drvClass = Class.forName(DRIVER_NAME); 
		DriverManager.registerDriver((Driver) drvClass.newInstance());

		//establish the connection 
		conn = DriverManager.getConnection(DB_CONN_STRING,ORA_USERNAME,ORA_PASSWORD); /* Oracle login info here */
		conn.setAutoCommit(false);
	    }
	catch(Exception ex)
	    {
		throw new BaseLayerException("Error in connecting to DB: " + ex.getMessage(), ex);
	    }
    }

    public void closeConnection() throws BaseLayerException
    {
	try
	    {
		conn.close();
	    }
	catch(Exception ex)
	    {
		throw new BaseLayerException("Error in querying DB: " + ex.getMessage(), ex);
	    }
    }
	
    public ResultSet GetQueryResult(String sql)
	throws BaseLayerException
    {
	try
	    {
		/* Query the user table to determine if a vaid username/password combination has been entered. */
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rset = stmt.executeQuery(sql);

		return rset;
	    }
	catch(Exception ex)
	    {
		throw new BaseLayerException("Error in closing connection to DB: " + ex.getMessage(), ex);
	    }
    }
}
