package com.timcolonel.SignUtilities;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;

public class Utils 
{
	public static Boolean isASign(Block block)
	{
		if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Boolean isAnActivator(Block block)
	{
		/*If the block is either a 
			-button
			-plate(stone,wood)
			-lever
		*/
		if (block.getType() == Material.STONE_BUTTON 
				|| block.getType() == Material.STONE_PLATE 
				|| block.getType() == Material.WOOD_PLATE
				|| block.getType() == Material.LEVER) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static int containsCmdLinks(String[] lines)
	{
		int i = 0;
		for(String line: lines)
		{
			if(line.contains("[") && line.contains("]"))
			{
				String str;
				str = Colors.removeColor(line);
				str = str.substring(1, str.length() -1);

				if(SignUtilities.plugin.config.cmdLinks.containsKey(str))
				{	
					return i; 
				}
			}

			i++;
		}
		
		return -1; //Not found
	}
	
	
	/**
	 * Return the first line that is a weblink
	 * @param lines
	 * @return
	 */
	public static int containsWebLinks(String[] lines)
	{
		int i = 0;
		for(String line: lines)
		{
			if(line.contains("(") && line.contains(")"))
			{
				String str;
				str = Colors.removeColor(line);
				str = str.substring(1, str.length() -1);

				if(SignUtilities.plugin.config.webLinks.containsKey(str))
				{	
					return i; 
				}
			}

			i++;
		}
		
		return -1; //Not found
	}
	
	public static Boolean isActivate(Block block, Action action)
	{
		/*If the block is either a 
			-button
			-plate(stone,wood)
			-lever
		*/
		if ((block.getType() == Material.LEVER || block.getType() == Material.STONE_BUTTON) && (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK))
		{
			return true;
		}
		else if((block.getType() == Material.WOOD_PLATE || block.getType() == Material.STONE_PLATE)  && action == Action.PHYSICAL)
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
	
	public static String blockToString(Block b)
	{
		return "(" + b.getX() + "," + b.getY() + ","+  b.getZ() + "," + b.getWorld().getName() + ")";
	}
}
