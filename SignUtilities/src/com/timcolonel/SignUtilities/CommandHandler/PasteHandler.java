package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SUEditor;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.SignUtilitiesClipBoard;

public class PasteHandler extends CommandHandler
{
	public PasteHandler(SignUtilities instance, CommandSender sender, String[] args) 
	{
		super(instance, sender, args);
		permission = "signutils.paste";
	}
	
	@Override
	public boolean execute() 
	{
		//Permissions
		if (!hasPermission()) 
		{
			return false;
		}
		
		//Get the clipboard of the player
		if (!plugin.playerClipBoard.containsKey((Player) sender))
		{
			sender.sendMessage(ChatColor.RED + "No content in clipBoard(Copy a sign first)");
			return false;
		}
		SignUtilitiesClipBoard cb = plugin.playerClipBoard.get((Player) sender);
				
		if(plugin.signSelMgr.hasPlayerSelected((Player) sender))
		{
			//Check if the sign is there
			Sign s = (Sign) plugin.signSelMgr.getSelection((Player) sender).getState();
			if (s.getType() == Material.SIGN_POST || s.getType() == Material.WALL_SIGN)
			{
			
			
				if(args.length == 0)
				{
					
					for(int i = 0; i < cb.lines.size(); i++)
					{
						int lineNb = cb.lines.get(i);
						String line = cb.content.get(i);
						SUEditor.changeText(s, line, lineNb,(Player) sender);
					
					}
					plugin.playerClipBoard.get((Player) sender).paste = true;
					sender.sendMessage(ChatColor.BLUE + "Content paste");
				}
				else if(args.length == 1) //Copy only the specified line
				{
					try 
					{
						int l = Integer.parseInt(args[0]);
						l--;
						if (l < 0 || l > 4 ) //Check if the line number is real
						{
							sender.sendMessage(ChatColor.RED + "There is only four lines on a sign");
							return false;
						}
		
						//If the player copied more than one line, ignore the argument
						if (cb.lines.size() != 1)
						{
							for(int i = 0; i < cb.lines.size(); i++)
							{
								int lineNb = cb.lines.get(i);
								String line = cb.content.get(i);
								SUEditor.changeText(s, line, lineNb, (Player) sender);
		
							}
						}
						else
						{
							String line = cb.content.get(0);
							SUEditor.changeText(s, line, l, (Player) sender);
		
						}
		
						plugin.playerClipBoard.get((Player) sender).paste = true;
		
					}
					catch (Exception e) 
					{
						sender.sendMessage(ChatColor.RED + "Number Expected");
						return false;
					}
					sender.sendMessage(ChatColor.BLUE + "Content paste");	
				}	
				else //Mean there is too much args
				{
					sender.sendMessage(ChatColor.RED + "Too much args");
					return false;
				}
			}
			else //Mean there is too much args
			{
	
			sender.sendMessage(ChatColor.RED + "No sign selected (The sign may have been removed)");
			return false;
			}
			
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "No sign selected (The sign may have been removed)");
			return false;
		}
		return false;
	}
}
