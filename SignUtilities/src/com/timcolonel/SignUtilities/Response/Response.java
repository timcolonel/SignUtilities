package com.timcolonel.SignUtilities.Response;

public class Response 
{
	boolean success;
	String message;
	
	public Response()
	{
		this(true, "");
	}
	
	public Response(boolean success)
	{
		this.success = success;
	}
	
	public Response(boolean success, String message)
	{
		this.success = success;
		this.message = message;
	}
	
	public boolean success()
	{
		return success;
	}
	
	public String getErrorMessage()
	{
		return message;
	}
}
