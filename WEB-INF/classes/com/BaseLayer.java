package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import com.BaseLayerException;

public class BaseLayer {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String DB_CONN_STRING = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	private final String ORA_USERNAME = "USERNAME";
	private final String ORA_PASSWORD = "PASSWORD";
	
	public BaseLayer(){}
	
	public ResultSet GetQueryResult(String sql) throws BaseLayerException
	{
		try
		{
			//load and register the driver
			Class drvClass = Class.forName(DRIVER_NAME); 
			DriverManager.registerDriver((Driver) drvClass.newInstance());

			//establish the connection 
			Connection conn = DriverManager.getConnection(DB_CONN_STRING,ORA_USERNAME,ORA_PASSWORD); /* Oracle login info here */
			conn.setAutoCommit(false);

			/* Query the user table to determine if a vaid username/password combination has been entered. */
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery(sql);

			conn.close();
			return rset;
		}
		catch(Exception ex)
		{
			throw new BaseLayerException("Error in querying DB: " + ex.getMessage(), ex);
		}
    }
}
