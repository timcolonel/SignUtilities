package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;

public class NewsHandler extends CommandHandler
{
	
	protected String action;
	protected String newsPaperName;
	protected String newsTitle;
	protected String newsContent;
	
	
	
	public NewsHandler(SignUtilities instance, CommandSender sender, String[] args) 
	{
		super(instance, sender, args);
		
		permission = "signutils.news_other.cmd";
				
		if(args.length >= 2)
		{
			action = args[0];
			newsPaperName = args[1];
		}
		
		if(args.length >= 3)
		{
			this.args = new String[args.length -2];
			
			for(int i = 2; i <args.length; i++)
			{
				this.args[i - 2] += args[i];	
			}
		}
	}
	
	@Override
	public boolean execute() 
	{
		if (!hasPermission()) 
		{
			return false;
		}
		
		if(newsPaperName.isEmpty() || action.isEmpty())
		{
			sender.sendMessage(ChatColor.RED + "Too few arguments");
			return false;
		}
		
		if(action.equalsIgnoreCase("ADD"))
		{
			if(args.length >= 2)
			{
				newsTitle = args[0];
				newsContent = "";
				for(int i = 1; i <args.length; i++)
				{
					newsContent += args[i] + " ";	
				}
				
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Too few arguments for adding a news");
			}
			
			plugin.newsMgr.addNews(newsPaperName, newsTitle, newsContent);
			sender.sendMessage(ChatColor.BLUE + "News " 
					+ ChatColor.GREEN + "\"" + newsTitle + "\""
					+ ChatColor.BLUE + " succelfuly added in the newsPaper "
					+ ChatColor.GREEN + "\"" + newsPaperName + "\"");
			return true;		
			
		}
		else if (action.equalsIgnoreCase("DEL"))
		{
			if(args.length >= 1)
			{
				newsTitle = args[0];	
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Too few arguments for deleting a news");
			}
			
			if(plugin.newsMgr.delNews(newsPaperName, newsTitle))
			{
				sender.sendMessage(ChatColor.BLUE + "News " 
						+ ChatColor.GREEN + "\"" + newsTitle + "\""
						+ ChatColor.BLUE + " succelfuly deleted in the newsPaper "
						+ ChatColor.GREEN + "\"" + newsPaperName + "\"");
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "News "
									+ ChatColor.DARK_RED + "\"" + newsTitle + "\""
									+ ChatColor.RED + " does not exit in "
									+ ChatColor.DARK_RED + "\"" + newsPaperName +"\"");
			}
			
			return true;
		}
		else if(action.equalsIgnoreCase("LIST"))
		{	
			if(args.length >= 1)
			{
				try
				{
					plugin.newsMgr.listNews(newsPaperName, (Player) sender, Integer.parseInt(args[0]));
				}
				catch(Exception e)
				{
					sender.sendMessage(ChatColor.RED + "Arguments 3 must be a page number");	
					return false;
				}
			}
			else
			{
				plugin.newsMgr.listNews(newsPaperName, (Player) sender, 1);
			}
			return true;
		}
		else
		{
			return false;
		}
		

	}
	
	

	

}
