package com;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class RISBusinessLayer extends BaseLayer{
	
	public RISBusinessLayer(){}
	
	public boolean validUser(String user_name, Integer person_id)
	throws BaseLayerException
	{
		if(user_name == null || user_name.isEmpty() || person_id == null)
			return false;
	
		ResultSet rset = null;
		Boolean valid = false;
	
		openConnection();
		rset = GetQueryResult(genValidUserSql(user_name, person_id));
		valid = (rset != null) ? true : false;
		closeConnection();
		
		return valid;
	}
	
	
	private String genValidUserSql(String user_name, Integer person_id)
	{
		return "select * from users where user_name =" + "'" + user_name + "'"
			+ "AND person_id =" + "'" + person_id + "'";
	}
}
