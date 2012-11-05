package com.timcolonel.SignUtilities.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.timcolonel.SignUtilities.Colors;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.SignUtilitiesClipBoard;
import com.timcolonel.SignUtilities.Utils;


public class SUBlockListener implements Listener//extends BlockListener
{
	public static SignUtilities plugin;
	
	public SUBlockListener(SignUtilities instance) 
	{
		plugin = instance;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) 
	{
		Player player = event.getPlayer();

		//Check if the user add colors 
		for (int i = 0; i < 4; i++)
		{
			String line = event.getLine(i);
			if(plugin.pluginsMgr.canColorSign(player))
				event.setLine(i, Colors.findColor(line));
			else
				event.setLine(i, line);
		}
		String firstLine = event.getLine(0);
		
        if(firstLine.contains("info:"))
        {														   
        	if(plugin.pluginsMgr.hasPermission(player, "signutils.news.sign.create"))
        	{
        		event.setLine(0, ChatColor.DARK_BLUE + firstLine);
        	}
        	else //Destroy the sign
        	{
        		event.getBlock().setTypeId(0);
        		event.getPlayer().setItemInHand(new ItemStack(323,1));
        		return;
        	}
        }
        else if (firstLine.contains("[cmd]"))
        {
        	if(plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.cmd.create"))
        	{ 	
        		for(int i = 0; i <4;i++)
        		{
        			String color = plugin.config.cmdSignColor[i];
        			if(color.equals("") && i == 0)
        			{
        				event.setLine(0, ChatColor.DARK_BLUE + firstLine);
        			}
        			if(color.length() == 2 && color.charAt(0) == '&')
        			{
        				event.setLine(i, color + event.getLine(i));
        			}
        			
        		}
        	}
        	else //Destroy the sign
        	{
        		event.getBlock().setTypeId(0);
        		event.getPlayer().setItemInHand(new ItemStack(323,1));
        		return;
        	}
        }
        else if(firstLine.contains("[minecart]") || firstLine.contains("[minecart:rem]"))
        {
        	if(plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.minecart.create"))
        	{
        		event.setLine(0, ChatColor.DARK_BLUE + firstLine);
        	}
        	else //Destroy the sign
        	{
        		event.getBlock().setTypeId(0);
        		event.getPlayer().setItemInHand(new ItemStack(323,1));
        		return;
        	}
        }
        else if(firstLine.contains("[activator]"))
        {
        	if(plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.activator.create"))
        	{
        		event.setLine(0, ChatColor.DARK_BLUE + firstLine);
        	}
        	else //Destroy the sign
        	{
        		event.getBlock().setTypeId(0);
        		event.getPlayer().setItemInHand(new ItemStack(323,1));
        		return;
        	}
        }
        /*else if(firstLine.contains("[") && firstLine.contains("]")) //CUSTOM CMD SIGNS
        {
        	int i =0;
        	for(String line: event.getLines())
        	{
	        	String str = Colors.removeColor(line);
	        	String key = str.substring(1, str.length() -1);
	        	//Custom cmd link found
				if(plugin.config.cmdLinks.containsKey(key))
				{
					if(plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.cmd.custom.create"))
					{
						event.setLine(i, ChatColor.DARK_BLUE + firstLine);
						break;
					}
					else //Destroy the sign
					{
						event.getBlock().setTypeId(0);
						event.getPlayer().setItemInHand(new ItemStack(323,1));
		        		return;
					}
				}
				i++;
        	}
        }
        else if(firstLine.contains("(") && firstLine.contains(")")) //CUSTOM CMD SIGNS
        {
        	int i =0;
        	for(String line: event.getLines())
        	{
	        	String str = Colors.removeColor(line);
	        	String key = str.substring(1, str.length() -1);
	        	//Custom cmd link found
				if(plugin.config.cmdLinks.containsKey(key))
				{
					if(plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.cmd.custom.create"))
					{
						event.setLine(i, ChatColor.DARK_BLUE + firstLine);
						break;
					}
					else //Destroy the sign
					{
						event.getBlock().setTypeId(0);
						event.getPlayer().setItemInHand(new ItemStack(323,1));
		        		return;
					}
				}
				i++;
        	}
        }*/
        int result = Utils.containsCmdLinks(event.getLines());
		if(result != -1)
		{
			if(plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.cmd.custom.create"))
			{
				event.setLine(result, ChatColor.DARK_BLUE + event.getLine(result));
			}
			else //Destroy the sign
			{
				event.getBlock().setTypeId(0);
				event.getPlayer().setItemInHand(new ItemStack(323,1));
	    		return;
			}
		}
		
        result = Utils.containsWebLinks(event.getLines());
        if(result != -1)
		{
			if(plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.web.create"))
			{
				event.setLine(result, ChatColor.DARK_BLUE + event.getLine(result));
			}
			else //Destroy the sign
			{
				event.getBlock().setTypeId(0);
				event.getPlayer().setItemInHand(new ItemStack(323,1));
	    		return;
			}
		}
		
		
        
		
        
        Sign sign = (Sign) event.getBlock().getState();
        String str = sign.getX() + ";" + sign.getY() + ";" + sign.getZ();
        if (plugin.signMgr.tempSigns.containsKey(str))
        {
        	//Put all the content of the temp sign in the sign to be edited
        	Sign editSign = (Sign) plugin.signMgr.tempSigns.get(str).getState();
        	String[] lines = event.getLines();
        	
        	for (int i = 0; i < 4; i++)
        	{
        		editSign.setLine(i, lines[i]);
        	}
        	
        	editSign.update();
        	//Delete the temp Sign
        	event.getBlock().setTypeId(0);
        	plugin.signMgr.tempSigns.remove(str);
        	event.getPlayer().setItemInHand(new ItemStack(323,1));
        }
                
    }
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		//SET a boolean to say if the player has enable directedit mode
		Boolean enable;
		if(plugin.signMgr.directEditEnable.containsKey(event.getPlayer()))
		{
			enable = plugin.signMgr.directEditEnable.get(event.getPlayer());
		}
		else //If the player is not found put the default value
		{
			enable = plugin.config.directEditDefault;
		}
		
		//////////////////////////////////////////////////////////////////////
		//			TO DISABLE THE DIRECT EDIT FUNCTION						//
		//////////////////////////////////////////////////////////////////////
		//enable = false;
		////////////////////////////////////////////////////////////////////
		
		//If the block put and the block to which he is
		if(Utils.isASign(event.getBlock()))
        {	
			//Check if it should be a temp sign.
			if (Utils.isASign(event.getBlockAgainst()) && enable && plugin.pluginsMgr.hasPermission(event.getPlayer(), "signutils.directedit"))
			{
				player.sendMessage("against");
				Sign tempSign = (Sign) event.getBlock().getState();
				Sign editSign = (Sign) event.getBlockAgainst().getState();
				String[] lines = editSign.getLines();
				
				
				//Get all the sign content which need to be edited and put it on the temporary sign.
				for (int i = 0; i <4; i++)
				{
					
					String str = lines[i];
					String newLine =  Colors.decodeColor(str);
					tempSign.setLine(i, newLine);
					player.sendMessage("new: " + tempSign.getLine(i));
				}	

				//Update the tempsign so the content of the editSign display on it
	        	Location l = new Location(event.getBlock().getWorld(),event.getBlock().getX(),event.getBlock().getY(),event.getBlock().getZ());
	        	player.sendMessage("id: " + l.getBlock().getState().getTypeId());
	        	
				//tempSign.update();
	      
				event.getBlockPlaced().getState().update();
				
				//Add it to the list of tempSign so we can delete it when done
				String str = tempSign.getX() + ";" + tempSign.getY() + ";" + tempSign.getZ();
				plugin.signMgr.tempSigns.put(str, event.getBlockAgainst());
        	}
			
			//PAste the player clipboard if he has one on the sign
			if (plugin.playerClipBoard.containsKey(event.getPlayer()))
			{
				
				SignUtilitiesClipBoard cb = plugin.playerClipBoard.get(event.getPlayer());
				Sign sign = (Sign) event.getBlock().getState();
				
				if (!cb.paste)
				{
					for(int i = 0; i < cb.lines.size(); i++)
					{
						int lineNb = cb.lines.get(i);
						String line = Colors.decodeColor(cb.content.get(i));
						sign.setLine(lineNb, line);
					
					}				
					
					if(!plugin.config.keepSignCopy)
						plugin.playerClipBoard.get(event.getPlayer()).paste = true;
					
				}
				
			}
        }
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) 
	{
		Player p = event.getPlayer();
		
		if (p.getItemInHand().getTypeId() == plugin.config.itemSel)
		{
			event.setCancelled(true);
		}
	}
	
	public void onBlockRedstoneChange(BlockRedstoneEvent event)
    {
		
    }
	
}