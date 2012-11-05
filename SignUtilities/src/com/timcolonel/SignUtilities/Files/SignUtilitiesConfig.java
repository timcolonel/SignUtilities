package com.timcolonel.SignUtilities.Files;


import java.util.HashMap;
import java.util.Set;

import com.timcolonel.SignUtilities.SignUtilities;

public class SignUtilitiesConfig extends SUFile
{
    
    //public Configuration config;
    

    //Variables which need to be load
    public int dispMsg;
    public int itemSel;
    public Boolean directEditDefault;
    public boolean autoLineCut;
    public boolean keepSignCopy = false;
    public final String[] cmdSignColor = new String[4];
    public final HashMap<String ,String > cmdLinks = new HashMap<String ,String>();
    public final HashMap<String ,String > webLinks = new HashMap<String ,String>();

    
    public SignUtilitiesConfig(SignUtilities instance) 
    {
        super(instance);
    }
    
    public void loadConfig()
    {

    	super.loadConfig();
    	dispMsg = config.getInt("DisplayMsg", 0);
    	directEditDefault = config.getBoolean("directEditDefault", true);
    	autoLineCut = config.getBoolean("autoLineCut", false);
    	keepSignCopy = config.getBoolean("keepSignCopy", false);
    	itemSel = config.getInt("itemForSelection", 280);
    	cmdSignColor[0] = config.getString("cmdSign.line1", "");
    	cmdSignColor[1] = config.getString("cmdSign.line2", "");
    	cmdSignColor[2] = config.getString("cmdSign.line3", "");
    	cmdSignColor[3] = config.getString("cmdSign.line4", "");
    	
    	if(config.isConfigurationSection("cmdLinks"))
    	{
	    	Set<String> keys = config.getConfigurationSection("cmdLinks").getKeys(false);
			if(keys != null)
			{
				for(String key:keys)
				{
					String cmdStr = config.getString("cmdLinks." + key, "");
					cmdLinks.put(key, cmdStr);
				}
			}
			
    	}
    	else
    	{
    		plugin.logger.info("[SU:Error]No cmd Links Directory in the config file, creating it.");

    		config.createSection("cmdLinks");
    		save();
    	}
    	
    	if(config.isConfigurationSection("webLinks"))
    	{
	    	Set<String> keys = config.getConfigurationSection("webLinks").getKeys(false);
			if(keys != null)
			{
				for(String key:keys)
				{
					String cmdStr = config.getString("webLinks." + key, "");
					webLinks.put(key, cmdStr);
				}
			}
			
    	}
    	else
    	{
    		plugin.logger.info("[SU:Error]No cmd Links Directory in the config file, creating it.");

    		config.createSection("webLinks");
    		save();
    	}

    }

}