package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DataAnalysisModule extends BaseLayer{

    public DataAnalysisModule(){}

    public String sent_sql = "None";

    public ArrayList<OlapShelf> countCorrespondingImages(String patient, String test_type, String time_block)
	throws BaseLayerException, SQLException
    {
	/* time_block is one of {week, month, year}. */

	ResultSet rset = null;
	ArrayList<OlapShelf> shelvedRows = new ArrayList<OlapShelf>();
	String year = "";
	int count = 0, month = 0, week = 0, qType;

	if((qType = getQType(patient, test_type)) == 0)
	    {
		throw new BaseLayerException("Error retrieving query type in DataAnalysisModule.");
	    }
	
	openConnection();

	String time_spec = getTimeSpec(time_block);

	/* Parameters should be checked and ready for appending when passed to this method. */

	String sql = genSQL(qType, patient, test_type, time_block, time_spec);

	sent_sql = sql;


	rset = GetQueryResult(sql);

	while(rset != null && rset.next())
	    {
		count = Integer.parseInt((rset.getString("Img_Count")).trim());

		if(time_block.matches("month"))
		    {
			month = Integer.parseInt((rset.getString("month")).trim());
		    }
		else if(time_block.matches("week"))
		    {
			week = Integer.parseInt((rset.getString("week")).trim());
		    }
		year = rset.getString("year1");
		shelvedRows.add(new OlapShelf(count, year, month, week));
	    }

	closeConnection();

	return shelvedRows;
    }       

    public ArrayList queryTestTypes()
	throws BaseLayerException, SQLException
    {
	ResultSet rset = null;
	ArrayList<String> test_types = new ArrayList<String>();

	openConnection();

	String sql = "SELECT test_type FROM radiology_record";

	rset = GetQueryResult(sql);	

	while(rset != null && rset.next())
	    {
		test_types.add(rset.getString("test_type").trim());
	    }

	closeConnection();

	return test_types;
    }

    private String getTimeSpec(String time_block)
    {
	if(time_block.matches("week"))
	    {
		return "to_number(to_char(R.test_date,'WW'))";
	    }
	else if(time_block.matches("month"))
	    {
		return "to_number(to_char(R.test_date,'MM'))";
	    }
	else
	    {
		return "to_number(to_char(R.test_date,'YYYY'))";
	    }
    }

    private String genSQL(int type, String patient, String test_type, String time_period, String time_spec)
    {
	/* 
	   Values of type and the corresponding queries they indicate: 
	   1 - All patients, all tests
	   2 - Specific patient only
	   3 - Specific test only
	   4 - Specific patient and specific test
	*/

	String sql = "none";

	switch(type)
	    {
	    case 1:
		{
		    sql = "SELECT to_number(to_char(R.test_date,'YYYY')) AS year1, "+time_spec+" AS "+time_period+", count(I.image_id) AS Img_Count FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id GROUP BY to_number(to_char(R.test_date,'YYYY')), "+time_spec;
		    break;
		}
	    case 2:
		{
		    sql = "SELECT to_number(to_char(R.test_date,'YYYY')) AS year1, "+time_spec+" AS "+time_period+", count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.patient_id ='"+patient+"' GROUP BY to_number(to_char(R.test_date,'YYYY')), "+time_spec;
		    break;
		}
	    case 3:
		{
		    sql = "SELECT to_number(to_char(R.test_date,'YYYY')) AS year1, "+time_spec+" AS "+time_period+", count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.test_type ='"+test_type+"' GROUP BY to_number(to_char(R.test_date,'YYYY')), "+time_spec;
		    break;
		}
	    case 4:
		{
		    sql = "SELECT to_number(to_char(R.test_date,'YYYY')) AS year1, "+time_spec+" AS "+time_period+", count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.patient_id ='"+patient+"' AND R.test_type ='"+test_type+"' GROUP BY to_number(to_char(R.test_date,'YYYY')), "+time_spec;
		    break;
		}
	    }
	return sql;
    }


    private int getQType(String patient, String test_type)
    {
	/* Identifying the specific set of parameter conditions. */

	if(patient.matches("all") && test_type.matches("all"))
	    return 1;
	if(!patient.matches("all") && test_type.matches("all"))
	    return 2;
	if(patient.matches("all") && !test_type.matches("all"))
	    return 3;
	if(!patient.matches("all") && !test_type.matches("all"))
	    return 4;

	return 0;
    }
}
