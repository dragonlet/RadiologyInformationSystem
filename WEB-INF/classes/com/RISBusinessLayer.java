package com;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class RISBusinessLayer {
	BaseLayer _bl;
	
	public RISBusinessLayer()
	{
		_bl = new BaseLayer();
	}
	
	public boolean validUser(String user_name, Integer person_id) throws BaseLayerException{
		if(user_name == null || user_name.isEmpty() || person_id == null)
			return false;
		
		try
		{
			ResultSet rset = _bl.GetQueryResult( genValidUserSql(user_name, person_id) );
			return (rset != null)? true : false;
		}
		catch(Exception ex)
		{
			throw new BaseLayerException("Error in querying DB: " + ex.getMessage(), ex);
		}
	}
	
	private String genValidUserSql(String user_name, Integer person_id)
	{
		return "select * from users where user_name =" + "'" + user_name + "'"
			+ "AND person_id =" + "'" + person_id + "'";
	}
}
