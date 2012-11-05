package com.timcolonel.SignUtilities.Listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;

import com.timcolonel.SignUtilities.SignAction;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Utils;

public class SUPlayerListener implements Listener//extends PlayerListener
{
	public static SignUtilities plugin;
	
	public SUPlayerListener(SignUtilities instance) 
	{
		plugin = instance;
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		//If the interact is a block
		if(event.hasBlock())
		{
			Player p = event.getPlayer();
			boolean cancelEvent = false;
			
			//////////////////////////////////////////////////////////////
			//					WIRELESS SELECTION 						//
			//////////////////////////////////////////////////////////////
			
			if (p.getItemInHand().getTypeId() == plugin.config.itemSel)
			{
				if(event.getAction() == Action.LEFT_CLICK_BLOCK)
				{
					p.sendMessage(ChatColor.GREEN + "Fisrt block selected");
					plugin.linkMgr.setPointA(p, event.getClickedBlock().getLocation());
					cancelEvent = true;
				}
				else if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					
					p.sendMessage(ChatColor.GREEN + "Second block selected");
					plugin.linkMgr.setPointB(p, event.getClickedBlock().getLocation());
					cancelEvent = true;
				}	
			}		
						
			//////////////////////////////////////////////////////////////
			//						ACTIVATOR 		  					//
			//////////////////////////////////////////////////////////////
			Block clickedBlock = event.getClickedBlock();
			//Create the block that will be activated
			Block blockToActivate = event.getClickedBlock();
			if (!cancelEvent && clickedBlock.getType() == Material.STONE_PLATE  && event.getAction() == Action.PHYSICAL)
			{
				blockToActivate = clickedBlock.getRelative(0,-2,0);
				
			}
			if (!cancelEvent && clickedBlock.getType() == Material.STONE_BUTTON  
					&& (event.getAction() == Action.LEFT_CLICK_BLOCK 
					|| event.getAction() == Action.RIGHT_CLICK_BLOCK))
			{
				
				byte data = clickedBlock.getData();
	            Button btn = new Button(clickedBlock.getType(), data);
	            
	            BlockFace face = btn.getAttachedFace();
	            if(face != null)
	            {
	            	blockToActivate = clickedBlock.getRelative(face, 2);
	            }
	            
	           
	           
			}
			String str = Utils.blockToString(event.getClickedBlock());
			if(!cancelEvent && Utils.isActivate(event.getClickedBlock(), event.getAction()) 
					&& plugin.wlSignMgr.blockHasConnection(str))
			{
				ArrayList<String> list = plugin.wlSignMgr.wirelessSigns.get(str);
				for(String signStr: list)
				{
					Block b = plugin.stringToBlock(signStr);
					if(b != null)
					{
						if(Utils.isASign(b))
						{
					
							plugin.signMgr.runSignCommand(p, b, event.getClickedBlock(), event.getBlockFace(), SignAction.LeftClick);
							
						}
					}
				}
			}
			
			//Add the block to the player selection
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
				plugin.signSelMgr.addSelection(p, clickedBlock);

			
			//If the block is a sign
			if (!cancelEvent && Utils.isASign(blockToActivate))
			{
				boolean signCmd = false;
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK)	
				{
					signCmd = plugin.signMgr.runSignCommand(p, blockToActivate, event.getClickedBlock(), event.getBlockFace(), SignAction.RightClick);
				}
				else
				{
					signCmd = plugin.signMgr.runSignCommand(p, blockToActivate, event.getClickedBlock(), event.getBlockFace(), SignAction.LeftClick);
				}
				
				
				displayMessage(p, signCmd);

			}
	
		}

	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		try
		{
			Minecart m = (Minecart) event.getRightClicked();
			if(plugin.minecartMgr.minecarts.containsKey(m))
			{
				m.setVelocity(event.getPlayer().getLocation().getDirection());
			}
		}
		catch (Exception e)
		{
			
		}

	}
	
	private void displayMessage(Player player, boolean commandSign)
	{
		if (plugin.config.dispMsg >= 0)
		{
			if((!commandSign || plugin.config.dispMsg == 1)  && player.getItemInHand().getTypeId() != 323)
			{
				player.sendMessage(ChatColor.BLUE + "You selected a sign");
			
			}
		
		}
	}
	
	
	
}

