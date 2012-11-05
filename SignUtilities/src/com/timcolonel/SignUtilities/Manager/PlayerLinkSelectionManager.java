package com.timcolonel.SignUtilities.Manager;


import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.Selection;
import com.timcolonel.SignUtilities.SignUtilities;


public class PlayerLinkSelectionManager 
{
	public static SignUtilities plugin;

	
	public final HashMap<Player ,Selection> savedBlockSelection = new HashMap<Player,Selection>();

	public PlayerLinkSelectionManager(SignUtilities instance)
	{		
		plugin = instance;

	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                          SELECTION                                              //
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public Selection getSelection(Player p)
	{
		if(savedBlockSelection.containsKey(p))
		{
			Selection s = savedBlockSelection.get(p);
			if(s.getPointA() != null && s.getPointB() != null)
			{
				return s;
			}
		}
		return null;
	}
		
	public void setSelection(Player p, Selection sel)
	{
		savedBlockSelection.put(p, sel);
	}
		
		
	public void setPointA(Player p, Location l)
	{
		if(savedBlockSelection.containsKey(p))
		{
			Selection sel = savedBlockSelection.get(p);
			sel.setPointA(l);
			savedBlockSelection.put(p, sel);
		}
		else
		{
			Selection sel = new Selection();
			sel.setPointA(l);
			sel.setWorld(l.getWorld());
			savedBlockSelection.put(p, sel);
		}
	}
		
	public void setPointB(Player p, Location l)
	{
		if(savedBlockSelection.containsKey(p))
		{
			Selection sel = savedBlockSelection.get(p);
			sel.setPointB(l);
			savedBlockSelection.put(p, sel);
		
		}
		else
		{
			Selection sel = new Selection();
			sel.setPointA(l);
			sel.setWorld(l.getWorld());
			savedBlockSelection.put(p, sel);
		
		}
	}
	
}
