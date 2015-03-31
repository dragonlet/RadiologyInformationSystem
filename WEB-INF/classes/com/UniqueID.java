package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.Random;

/* 
   To generate a unique 5-digit ID number (integer type) for
   any of the tables, for example when inserting a row,
   instantiate this class and call generate(<type>) on it,
   where type is a String (one of "person", "image", or "record").
   The returned value is the integer.
   
   Note that you also need to modify this file with your own username/password
   because it logs in seperately to check existing ids.
*/

public class UniqueID extends BaseLayer{

    public UniqueID(){}	
	/* Container class for ID generation method. 
	   generate returns 0 if there is an error and
	   stores the exception data in error_printout. 
	   Returns a new unique id otherwise. */

    public int generate(String type) throws BaseLayerException, SQLException
    {
	Random rand = new Random();

	/*  Establish connectionn to DB.*/
	openConnection();

	Statement stmt = null;
	ResultSet rset = null;

	/* Generates an id between 10000 and 99999. (Enough id's for 89999 different rows in each table.) */
	int new_id = rand.nextInt(89999) + 10000;
	String select_data = "";	

	/* There are unique ID's for persons, images, and records. */
	if(type.compareTo("person") == 0)
	    {
		select_data = "select person_id from persons";
	    }
	else if(type.compareTo("image") == 0)
	    {
		select_data = "select image_id from pacs_images";
	    }
	else if(type.compareTo("record") == 0)
	    {
		select_data = "select record_id from radiology_record";
	    }

	/* Query the DB to obtain the set of existing IDs. */
	rset = GetQueryResult(select_data);

	int extant_id;

	while(rset != null && rset.next())
	    {
		/* Obtain existing id from row and compare to generated integer. */
		extant_id = Integer.parseInt(rset.getString(type+"_id").trim());
		if(extant_id == new_id)
		    {
			/* If this number already exists in the table, create a new id and try again. */
			new_id = rand.nextInt(89999) + 10000;
			rset.beforeFirst();
		    }
	    }

	return new_id;
    }
}
