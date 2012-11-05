package com.timcolonel.SignUtilities.Manager;

import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;

public class PlayerSignSelectionManager 
{
	public final HashMap<Player,Block> playerSelection = new HashMap<Player, Block>();
	
	public static SignUtilities plugin;

	
	public PlayerSignSelectionManager(SignUtilities instance)
	{		
		plugin = instance;
	}
	
	public boolean hasPlayerSelected(Player player)
	{
		return playerSelection.containsKey(player);
	}
	
	public Block getSelection(Player player)
	{
		return playerSelection.get(player);
	}
	
	public void addSelection(Player player, Block block)
	{
		playerSelection.put(player, block);
	}
}
