package com;
import java.lang.*;
import java.io.*;
import java.sql.*;


public class ReportModule extends BaseLayer
{
    public boolean found = false;
    public String first_name, last_name, address, phone, test_date;

    public ReportModule()
    {
	super();
    }


    public void generateReport(String diagnosis, String start_date, String end_date) throws BaseLayerException
    {
	ResultSet rset = null;
	String q = query(diagnosis, start_date, end_date);

	/* Open and query. */		
	openConnection();
	rset = GetQueryResult(q);

	/* Process data and set variables for external access.*/
	try
	    {
		if(rset != null && rset.next())
		    {
			first_name = (rset.getString("first_name")).trim();
			last_name = (rset.getString("last_name")).trim();
			address = (rset.getString("address")).trim();
			phone = (rset.getString("phone")).trim();
			test_date = (rset.getString("test_date")).trim();
			found = true;
		    }
	    }
	catch(Exception ex)
	    {
		throw new BaseLayerException(ex.getMessage(), ex);
	    }

	/* Close the connection to the database. */
	closeConnection();
    }
    

    /* Helper function to create the sql query string. */
    private String query(String diagnosis, String start_date, String end_date)
    {
	/*
	  SELECT P.first_name, P.last_name, P.address, P.phone, R.test_date
	  FROM radiology_record R, persons P
	  WHERE R.diagnosis = '<user input>' AND R.test_date BETWEEN '<user entered date 1>' AND '<user entered date 2>';
	*/

	String q = "SELECT P.first_name, P.last_name, P.address, P.phone, R.test_date FROM radiology_record R, persons P WHERE R.diagnosis = " + stringize(diagnosis) + " AND R.patient_id = P.person_id AND R.test_date BETWEEN TO_DATE(" + stringize(start_date) + ", " + "'YYYYMMDD') AND TO_DATE(" + stringize(end_date) + ", " + "'YYYYMMDD')";

	return q;
    }

    String stringize(String s)
    {
	return "'"+s+"'";
    }


}
