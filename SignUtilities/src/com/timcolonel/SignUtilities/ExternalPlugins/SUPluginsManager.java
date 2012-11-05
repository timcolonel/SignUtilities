package com.timcolonel.SignUtilities.ExternalPlugins;

import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;


import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Response.TransactionResponse;

public class SUPluginsManager 
{
	public static SignUtilities plugin;
	//public static PermissionHandler permissionHandler;
	//public static iConomy ecoHandeler;
	public static WorldGuardPlugin wgHandeler;
	
	public static Permission perms = null;
    public static Economy econ  = null;

    private boolean setupPermissions() 
    {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private boolean setupEconomy() 
    {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
	public SUPluginsManager(SignUtilities instance)
	{
		plugin = instance;
	}
	
	public void setupPlugins() 
	{
		 if (!setupEconomy() ) 
		 {
	            plugin.logger.log(Level.SEVERE, String.format("[%s] - Disabled due to no Vault dependency found!", plugin.getDescription().getName()));
	            plugin.getServer().getPluginManager().disablePlugin(plugin);
	            return;
	    }
	    setupPermissions();
		
	    Plugin wgPlugin = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
	  
	    if (wgPlugin == null) 
	    {
	        plugin.logger.info("World Guard system not detected!");
	    }
	    else
	    {
	    	wgHandeler = (WorldGuardPlugin) wgPlugin;
	    }
 
	}

	
	public Boolean hasPermission(Player player, String s)
	{
		return perms.has(player, s);
	}
	
	public boolean canColorSign(Player player)
	{		
		if(!hasPermission(player, "signutils.setcolor"))
		{
			return false;
		}
		
		if(wgHandeler != null)
		{
			if(!wgHandeler.getGlobalRegionManager().canBuild(player, plugin.signSelMgr.getSelection(player)))
			{
				player.sendMessage(ChatColor.RED + "You cant edit this sign");
				return false;
			}
			else 
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	public boolean canEditSign(Player player)
	{
		if(!hasPermission(player, "signutils.settext"))
		{
			return false;
		}
		
		if(wgHandeler != null)
		{
			if(!wgHandeler.getGlobalRegionManager().canBuild(player, plugin.signSelMgr.getSelection(player)))
			{
				player.sendMessage(ChatColor.RED + "You cant edit this sign");
				return false;
			}
			else 
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	public TransactionResponse ecoPay(Player sender, String reciverName, double amount)
	{
		if(econ != null && amount > 0)
		{
			EconomyResponse withdrawResponse = econ.withdrawPlayer(sender.getName(), amount);
			
			if(withdrawResponse.transactionSuccess())
			{
				EconomyResponse depositResponse =econ.depositPlayer(reciverName, amount);
				
				if(depositResponse.transactionSuccess())
				{
					return new TransactionResponse(true, "Transaction success", amount);
				}
				else
				{
					return new TransactionResponse(false, depositResponse.errorMessage, 0);
				}
				
			}
			else
			{
				return new TransactionResponse(false, withdrawResponse.errorMessage, 0);
			}
		}
		else
		{
			if(amount > 0)
				return new TransactionResponse(false, "No economy plugin found", 0);
			else
			{
				return new TransactionResponse(false, "Transction amount is 0", amount);
			}
		}
	}
}
