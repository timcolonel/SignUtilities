package com.timcolonel.SignUtilities.Response;

public class TransactionResponse extends Response
{
	double amount;
	
	//////////////////////////////////////////////////////////////////////
	//							CONSTRUCTORS							//
	//////////////////////////////////////////////////////////////////////
	
	public TransactionResponse()
	{
		this(true, "",0);
	}
	
	public TransactionResponse(boolean success)
	{
		this(success, "",0);
	}
	
	
	public TransactionResponse(boolean success, String message)
	{
		this(true, message,0);
	}
	
	public TransactionResponse(boolean success, double amount)
	{
		this(true, "",amount);
	}
	
	public TransactionResponse(boolean success, String message, double amount)
	{
		this.success = success;
		this.message = message;
		this.amount = amount;
	}
	
	

	//////////////////////////////////////////////////////////////////////
	//							GETTERS									//
	//////////////////////////////////////////////////////////////////////
	
	public double getAmountTransfered()
	{
		return amount;
	}
}
