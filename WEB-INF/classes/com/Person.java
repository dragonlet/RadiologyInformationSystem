package com;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class Person
{
	public final static String ID_LABEL = "person_id";
	public final static String FN_LABEL = "first_name";
	public final static String LN_LABEL = "last_name";
	public final static String AD_LABEL = "address";
	public final static String EM_LABEL = "email";
	public final static String PH_LABEL = "phone";
	
	private Integer id;
	private String first_name;
	private String last_name;
	private String address;
	private String email;
	private String phone;
	
	private Person(){};
	
	public Person(ResultSet rs)
	{
		try{
			id = rs.getInt(ID_LABEL);
		}catch(Exception ex){
			id = null;
		}
		
		try{
			first_name = rs.getString(FN_LABEL);
		}catch(Exception ex){
			first_name = null;
		}
		
		try{
			last_name = rs.getString(LN_LABEL);
		}catch(Exception ex){
			last_name = null;
		}
		
		try{
			address = rs.getString(AD_LABEL);
		}catch(Exception ex){
			address = null;
		}
		
		try{
			email = rs.getString(EM_LABEL);
		}catch(Exception ex){
			email = null;
		}
		
		try{
			phone = rs.getString(PH_LABEL);
		}catch(Exception ex){
			phone = null;
		}	
	}
	
	public Integer getID(){
		return id;
	}
	
	public String getFirstName(){
		return first_name;
	}
	
	public String getLastName(){
		return last_name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPhone(){
		return phone;
	}
}