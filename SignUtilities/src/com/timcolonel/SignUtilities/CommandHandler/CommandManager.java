package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.timcolonel.SignUtilities.SignUtilities;


public class CommandManager 
{

	public static SignUtilities plugin;
	public CommandManager(SignUtilities instance)
	{
		plugin = instance;
		
	}
	
	public Boolean manage(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("setColor"))
		{	
			return setColor(sender, cmd, commandLabel, args);		
		}	
		else if (cmd.getName().equalsIgnoreCase("setText"))
		{
			return setText(sender, cmd, commandLabel, args);
		}
		else if (cmd.getName().equalsIgnoreCase("scopy"))
		{	
			return copy(sender, cmd, commandLabel, args);
		}	
		else if (cmd.getName().equalsIgnoreCase("spaste"))
		{	
			return paste(sender, cmd, commandLabel, args);
		}	
		else if (cmd.getName().equalsIgnoreCase("snews"))
		{
			return news(sender, cmd, commandLabel, args);

		}		
		else if (cmd.getName().equalsIgnoreCase("snewsP"))
		{
			return newsP(sender, cmd, commandLabel, args);

		}	
		else if (cmd.getName().equalsIgnoreCase("DirectEdit") || cmd.getName().equalsIgnoreCase("DE"))
		{
			return directEdit(sender, cmd, commandLabel, args);
		}
		else if (cmd.getName().equalsIgnoreCase("swa"))
		{
			return wireless(sender, cmd, commandLabel, args);

		}	
		else
		{
			return false;
		}
		
	}

	/********************************************************************************************
	 								Manage the setColor command
	 ********************************************************************************************/
	
	public Boolean setColor(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		SetColorHandler handler = new SetColorHandler(plugin, sender, args);
		handler.execute();
		return true;
	}
	

	/********************************************************************************************
	 								Manage the setText command
	 ********************************************************************************************/
	public Boolean setText(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		SetTextHandler handler = new SetTextHandler(plugin, sender, args);
		handler.execute();
		return true;
	}
	

	/********************************************************************************************
	 								Manage the scopy command
	 ********************************************************************************************/
	public Boolean copy(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		CopyHandler handler = new CopyHandler(plugin, sender, args);
		handler.execute();
		return true;
		
	}
	
	/********************************************************************************************
								Manage the spaste command
	********************************************************************************************/
	public Boolean paste(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		PasteHandler handler = new PasteHandler(plugin, sender, args);
		handler.execute();
		return true;
	}
	
	/********************************************************************************************
								Manage the news command
	********************************************************************************************/

	public Boolean news(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{

		PersoNewsHandler handler = new PersoNewsHandler(plugin, sender, args);
		handler.execute();
		return true;
	}
	
	
	/********************************************************************************************
									Manage the newsP command
	*********************************************************************************************/

	public Boolean newsP(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		
		NewsHandler handler = new NewsHandler(plugin, sender, args);
		handler.execute();
		return true;
	}
	
	
	/********************************************************************************************
								Manage the directedit command
	 *********************************************************************************************/
	public Boolean directEdit(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		DirectEditHandler handler = new DirectEditHandler(plugin, sender, args);
		handler.execute();
		return true;
	}
	
	/********************************************************************************************
									Manage the wireless command
	 *********************************************************************************************/
	public Boolean wireless(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		WirelessHandler handler = new WirelessHandler(plugin, sender, args);
		handler.execute();
		return true;
				
	}
}
