package com.timcolonel.SignUtilities.SignCommands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SUEditor;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Utils;

public class NewsSign extends SignCommand
{
	protected BlockFace face;
	
	public NewsSign(SignUtilities instance, Player player, Sign sign, Block block, BlockFace face) 
	{
		super(instance, player, sign, block);
		this.face = face;
		this.permission = "signutils.news.sign.use";
	}

	@Override
	public boolean run() 
	{
		if (!hasPermission()) 
		{
			return true;
		}
		
		String newsText = "";
		String oldNewsText = "";
		//All the sign near each other are here
		ArrayList<Sign> signs = getAllAdjSign(sign, face);
		for(Sign csign : signs)
		{
			for(int i = 1;i < 4; i++)
			{
				if(csign.getLine(i) != "")
				oldNewsText += csign.getLine(i) + ";";
		
			}
		}
		oldNewsText = oldNewsText.substring(0, oldNewsText.length() - 1);
		
		Boolean findNews = true;
		//While the second line of the sign is the same we try to find an other news exept if tehr is only one news 
		String name = signs.get(0).getLine(0).split(":",2)[1];
		int newsNb = plugin.newsMgr.getNewsNumber(name);
		while(findNews)
		{
			
			newsText = plugin.newsMgr.getNextNews(name);
			if(!newsText.equalsIgnoreCase(oldNewsText) 
				|| newsText.split(";", 2)[0].equalsIgnoreCase(ChatColor.RED + "NO NEWS")
				|| newsNb == 1)
				findNews = false;
		    
		}
		    
		if(plugin.config.autoLineCut)
			newsText = autoLine(newsText);
		    
		String[] newsLines = newsText.split(";");
		
		int i = 0;
		for(Sign csign : signs)
		{
			for(int j = 0;j < 3; j++)
			{
				
				if(newsLines.length > 3*i +j)
				{
					SUEditor.changeText(csign, newsLines[3*i+j],j + 1, player);
		  		}
				else
				{
					SUEditor.changeText(csign, "",j + 1, player);
				}
			}
			i++;
		}
		return true;
	}
	
	
	public ArrayList<Sign> getAllAdjSign(Sign mainSign, BlockFace face)
	{
		ArrayList<Sign> signs = new ArrayList<Sign>();
		BlockFace left  = getLeftBlockFace(face);
		BlockFace right  = left.getOppositeFace();
		
		boolean running = true;
		Block firstBlock = mainSign.getBlock();  
		
		//Search for the first sign on the line
		while(running)
		{
			Block leftBlock = firstBlock.getRelative(left);
			if(Utils.isASign(leftBlock))
			{
				Sign leftSign = (Sign) leftBlock.getState();
				if (leftSign.getLine(0).equalsIgnoreCase(mainSign.getLine(0)))
				{
					firstBlock = leftBlock;
				}
				else
				{
					running = false;
				}
			}
			else
			{
				running = false;
			}
		}
		
		running = true;
		Block rightBlock = firstBlock;
		while(running)
		{
			
			if(Utils.isASign(rightBlock))
			{
				Sign rightSign = (Sign) rightBlock.getState();
				if (rightSign.getLine(0).equalsIgnoreCase(mainSign.getLine(0)))
				{
					signs.add(rightSign);
				}
				else
				{
					running = false;
				}
			}
			else
			{
				running = false;
			}
			rightBlock = rightBlock.getRelative(right);
		}
		
	
		return signs;
	}
	
	public BlockFace getLeftBlockFace(BlockFace face)
	{
		BlockFace left  = null;
		if(face == BlockFace.NORTH)
		{
			left = BlockFace.EAST;
		}
		else if (face == BlockFace.EAST)
		{			
			left = BlockFace.SOUTH;
		}
		else if (face == BlockFace.SOUTH)
		{
			left = BlockFace.WEST;
		}
		else if (face == BlockFace.WEST)
		{
			left = BlockFace.NORTH;
		}
		return left;
	}

	
	public String autoLine(String str)
	{
		String[] oldLines = str.split(";");
		String newStr = "";
		for(String line : oldLines)
		{
			while(line.length() > 15)
			{
				newStr += line.substring(0,15)+ ";";
				line = line.substring(15, line.length());
			
			}
			newStr += line + ";";
		}
		newStr = newStr.substring(0, newStr.length() - 1);
		return newStr;
	}
}
