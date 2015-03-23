package com;
import java.lang.*;

class BaseLayerException extends Exception
{
	public BaseLayerException(){}
	
 	public BaseLayerException(String message)
	{
		super(message);
	}
	
	public BaseLayerException(String message, Throwable cause)
	{
		super(message, cause);
	}
}