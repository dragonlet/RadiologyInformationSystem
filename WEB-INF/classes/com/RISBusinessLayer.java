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
	
	public boolean validUser(String user_name, int id) throws BaseLayerException{
		if(user_name == null || user_name.isEmpty())
			return false;
		
		try
		{
			ResultSet rset = _bl.GetQueryResult( genValidUserSql(user_name) );
			return (rset != null)? true : false;
		}
		catch(Exception ex)
		{
			throw new BaseLayerException(ex);
		}
	}
	
	private String genValidUserSql(String user_name)
	{
		return "select * from users where user_name =" + "'" + user_name + "'";
	}
}