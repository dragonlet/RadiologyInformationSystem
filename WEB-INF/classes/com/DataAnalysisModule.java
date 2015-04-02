package com;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DataAnalysisModule extends BaseLayer{

    public DataAnalysisModule(){}

    public int countCorrespondingImages(String patient, String test_type, String time_period, String time_block)
	throws BaseLayerException, SQLException
    {
	ResultSet rset = null;
	int count = 0, qType;

	if((qType = getQType(patient, test_type, time_period)) == 0)
	    {
		throw new BaseLayerException("Error retrieving query type in DataAnalysisModule.");
	    }
	
	openConnection();

	String parsed_time = parseTime(time_period, time_block);

	/* Parameters should be checked and ready for appending when passed to this method. */

	String sql = genSQL(qType, patient, test_type, parsed_time);

	rset = GetQueryResult(sql);

	if(rset != null && rset.next())
	    {
		count = Integer.parseInt((rset.getString("imgcount")).trim());
	    }

	closeConnection();

	return count;
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

    private String parseTime(String time_period, String time_block)
    {
	String parsed_time = "";
	
	if(time_block.matches("week"))
	    {
		/* time_period will be in the format ww/yyyy */
		String[] date_parts = time_period.split("/");
		parsed_time = "to_number(to_char(R.test_date,'WW')) = "+date_parts[0]+" AND to_number(to_char(R.test_date,'YYYY')) = "+date_parts[1];
	    }
	else if(time_block.matches("month"))
	    {
		/* time_period will be in the format mm/yyyy */
		String[] date_parts = time_period.split("/");
		parsed_time = "to_number(to_char(R.test_date,'MM')) = "+date_parts[0]+" AND to_number(to_char(R.test_date,'YYYY')) = "+date_parts[1];
	    }
	else if(time_block.matches("year"))
	    {
		/* time_period will be in the format yyyy */
		parsed_time = "to_number(to_char(R.test_date,'YYYY')) = "+time_period;
	    }
	return parsed_time;
    }

    private String genSQL(int type, String patient, String test_type, String time_period)
    {
	/* 
	   Values of type and the corresponding queries they indicate: 
	   1 - All patients, all times, all tests
	   2 - Specific patient only
	   3 - Specific test only
	   4 - Specific time only
	   5 - Specific patient and specific time
	   6 - Specific patient and specific test
	   7 - Specific time and specific test
	   8 - Specific patient, time, and test
	*/

	String sql = "none";

	switch(type)
	    {
	    case 1:
		{
		    sql = "SELECT count(image_id) AS imgcount FROM pacs_images";
		    break;
		}
	    case 2:
		{
		    sql = "SELECT count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.patient_id ='"+patient+"'";
		    break;
		}
	    case 3:
		{
		    sql = "SELECT count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.test_type ='"+test_type+"'";
		    break;
		}
	    case 4:
		{
		    sql = "SELECT count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND "+time_period;
		    break;
		}
	    case 5:
		{
		    sql = "SELECT count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.patient_id ='"+patient+"' AND "+time_period;
		    break;
		}
	    case 6:
		{
		    sql = "SELECT count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.patient_id ='"+patient+"' AND R.test_type ='"+test_type+"'";
		    break;
		}
	    case 7:
		{
		    sql = "SELECT count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.test_type ='"+test_type+"' AND "+time_period;
		    break;
		}
	    case 8:
		{
		    sql = "SELECT count(I.image_id) AS imgcount FROM pacs_images I, radiology_record R WHERE I.record_id = R.record_id AND R.patient_id ='"+patient+"' AND R.test_type ='"+test_type+"' AND "+time_period;
		    break;
		}
	    }
	return sql;
    }


    private int getQType(String patient, String test_type, String time_period)
    {
	/* Identifying the specific set of parameter conditions. */

	if(patient.matches("all") && test_type.matches("all") && time_period.matches("all"))
	    return 1;
	if(!patient.matches("all") && test_type.matches("all") && time_period.matches("all"))
	    return 2;
	if(patient.matches("all") && !test_type.matches("all") && time_period.matches("all"))
	    return 3;
	if(patient.matches("all") && test_type.matches("all") && !time_period.matches("all"))
	    return 4;
	if(!patient.matches("all") && test_type.matches("all") && !time_period.matches("all"))
	    return 5;
	if(!patient.matches("all") && !test_type.matches("all") && time_period.matches("all"))
	    return 6;
	if(patient.matches("all") && !test_type.matches("all") && !time_period.matches("all"))
	    return 7;
	if(!patient.matches("all") && !test_type.matches("all") && !time_period.matches("all"))
	    return 8;

	return 0;
    }
}
