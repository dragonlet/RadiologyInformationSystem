package com;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class UploadLayer extends BaseLayer{
	private final static String DOC_QUERY =	"select * from persons where person_id IN (select doctor_id from family_doctor)";
	private final static String PAT_QUERY = "select * from persons";
	private final static String NEW_RECORD = "INSERT into radiology_record VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public UploadLayer() {}
	
	public void addRecord(Integer record_id,
		Integer doctor_id,
		Integer patient_id,
		Integer radiologist_id,
		String test_type,
		java.util.Date p_date,
		java.util.Date t_date,
		String diagnosis,
		String description)
	throws BaseLayerException
	{
		openConnection();
		
		try{
			java.sql.Date prescription_date = new java.sql.Date(p_date.getTime());
			java.sql.Date test_date = new java.sql.Date(t_date.getTime());
			PreparedStatement stmt = prepareStmt(NEW_RECORD);
			
			stmt.setInt(1, record_id);
			stmt.setInt(2, doctor_id);
			stmt.setInt(3, patient_id);
			stmt.setInt(4, radiologist_id);
			stmt.setString(5, test_type);
			stmt.setDate(6, prescription_date);
			stmt.setDate(7, test_date);
			stmt.setString(8, diagnosis);
			stmt.setString(9, description);
			
			stmt.executeUpdate();
		}catch(Exception ex){
			throw new BaseLayerException("Error with updating DB set: " + ex.getMessage(), ex);
		}
		
		closeConnection();
	}
	
	public ArrayList<Person> getDoctors()
	throws BaseLayerException
	{
		return getPersons(DOC_QUERY);
	}
	
	public ArrayList<Person> getPatients()
	throws BaseLayerException
	{
		return getPersons(PAT_QUERY);
	}
	
	private ArrayList<Person> getPersons(String query)
	throws BaseLayerException
	{
		ArrayList<Person> ret = new ArrayList<Person>();
		openConnection();
		ResultSet rs = GetQueryResult(query);
		
		try{
			while(rs.next())
			{
				ret.add(new Person(rs));
			}
		}catch(Exception ex){
			throw new BaseLayerException("Error with reading result set: " + ex.getMessage(), ex);
		}
		
		closeConnection();
		return ret;
	}
}