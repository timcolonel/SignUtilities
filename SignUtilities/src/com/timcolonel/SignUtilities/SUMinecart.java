package com.timcolonel.SignUtilities;


import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Minecart;
import org.bukkit.inventory.ItemStack;

public class SUMinecart {

	private Minecart minecart;
	Chest nativeChest;
	public static SignUtilities plugin;
	public SUMinecart()
	{
		setMinecart(null);
		nativeChest = null;
	}
	
	public SUMinecart(Minecart m)
	{
		setMinecart(m);
		nativeChest = null;
	}
	
	public SUMinecart(Minecart m, Chest c,SignUtilities instance)
	{
		setMinecart(m);
		nativeChest = c;
		plugin = instance;
		removeMinecartFromChest();
	}
	
	public void minecartDestroyed(Boolean b)
	{
		if(nativeChest != null)
		{
			nativeChest.getInventory().addItem(new ItemStack(Material.MINECART.getId(), 1));	
		}
	}
	
	public boolean removeMinecartFromChest()
	{

		if(nativeChest != null)
		{
			//If the chest contain at least one minecart we remove one
			if(nativeChest.getInventory().contains(Material.MINECART.getId()))
			{
				int slot = nativeChest.getInventory().first(Material.MINECART.getId());
				ItemStack itStack = nativeChest.getInventory().getItem(slot);
				if (itStack.getAmount() > 1)
				{
					
					itStack.setAmount(itStack.getAmount() - 1);
					nativeChest.getInventory().setItem(slot,itStack);
				}
				else
				{
					nativeChest.getInventory().clear(slot);
					
				}
				
				//nativeChest.getInventory().remove(Material.CHEST.getId());
				return true;
			}
			else
			{
				return false;
			}
			
		}
		else return false;
	}

	/********************************************************************************************
	 *                                	SETTER - GETTERS                                       	*
	 ********************************************************************************************/
	public Chest getNativeChest() 
	{
		return nativeChest;
	}

	public void setNativeChest(Chest nativeChest) 
	{
		this.nativeChest = nativeChest;
	}

	public Minecart getMinecart() 
	{
		return minecart;
	}
	
	public void setMinecart(Minecart minecart)
	{
		this.minecart = minecart;
	}
	
	
}
