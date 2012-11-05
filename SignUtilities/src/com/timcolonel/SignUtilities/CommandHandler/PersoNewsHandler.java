package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.command.CommandSender;


import com.timcolonel.SignUtilities.SignUtilities;

public class PersoNewsHandler extends NewsHandler
{
	
	public PersoNewsHandler(SignUtilities instance, CommandSender sender, String[] args) 
	{
		super(instance, sender, args);
		permission = "signutils.news.cmd";
		
		if(args.length >= 1)
		{
			action = args[0];
		}
		newsPaperName = sender.getName();
		
		this.args = new String[args.length -1];
		
		for(int i = 1; i <args.length; i++)
		{
			this.args[i - 1] = args[i];	
		}
	}
	@Override
	public boolean execute() 
	{
		return super.execute();
	}
	
}