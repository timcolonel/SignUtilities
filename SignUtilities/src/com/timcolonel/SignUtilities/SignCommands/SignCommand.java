package com.timcolonel.SignUtilities.SignCommands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;

public class SignCommand 
{
	public static SignUtilities plugin;
	
	protected Sign sign;
	protected Player player;
	protected Block selectedBlock;  //Contain the block from where the sign has been activated(Sign it self or a button for example) 
	protected  String permission;

	
	public SignCommand(SignUtilities instance, Player player, Sign sign, Block block)
	{
		plugin = instance;
		this.player = player;
		this.sign = sign;
		this.selectedBlock = block;
		this.permission = "signutils.*";
	}
	
	
	


	public boolean run()
	{
		 return true;
	}
	
	public boolean hasPermission()
	{
		if(plugin.pluginsMgr.hasPermission(player, permission))
		{			
			return true;
		}
		else
		{
			player.sendMessage(ChatColor.RED + "You can't use that sign");
			return false;
		}
	}
	/********************************************************************************************
	 *                                	SETTER - GETTERS                                       	*
	 ********************************************************************************************/
	public void setPlayer(Player player)
	{
		this.player = player;
		
	}

	public void setSign(Sign sign)
	{
		this.sign = sign;	
	}
	
	public Player getPlayer()
	{	
		return this.player;
	}
	
	public Sign getSign()
	{	
		return this.sign;
	}
	
	public Block getSelectedBlock() 
	{
		return this.selectedBlock;
	}


	public void setSelectedBlock(Block selectedBlock) 
	{
		this.selectedBlock = selectedBlock;
	}
}
