package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;

public class CommandHandler 
{
	public static SignUtilities plugin;
	protected CommandSender sender;
	protected String args[];
	protected  String permission;
	
	//////////////////////////////////////////////////////////////////////////
	//							 CONSTRUCTOR								//
	//////////////////////////////////////////////////////////////////////////

	public CommandHandler(SignUtilities instance, CommandSender sender, String args[])
	{
		permission = "signutils.*";
		plugin = instance;
		this.sender = sender;
		this.args = args;
	}

	public boolean execute()
	{
		return true;
	}
	
	public boolean hasPermission()
	{
		if(plugin.pluginsMgr.hasPermission((Player) sender, permission))
		{			
			return true;
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "You don't have acces to that command!");
			return false;
		}
	}
	
	//////////////////////////////////////////////////////////////////////////
	//							SETTERS-GETTERS								//
	//////////////////////////////////////////////////////////////////////////
	
	public CommandSender getSender() 
	{
		return sender;
	}

	public void setSender(CommandSender sender) 
	{
		this.sender = sender;
	}

	public String[] getArgs() 
	{
		return args;
	}

	public void setArgs(String[] args) 
	{
		this.args = args;
	}
	
}
