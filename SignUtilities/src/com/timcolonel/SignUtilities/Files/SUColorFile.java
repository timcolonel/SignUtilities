package com.timcolonel.SignUtilities.Files;

import java.io.File;
import java.util.List;


import com.timcolonel.SignUtilities.SignUtilities;

public class SUColorFile extends SUFile
{

	    protected static SignUtilities plugin;
	    protected static String fileName;
	    protected static File file;

	    public SUColorFile(SignUtilities instance, String name) 
	    {
	    	super(instance, name);
	    }
	    
	/*
	       public String directory = "plugins" + File.separator +plugin.getDescription().getName();
	       File file = new File(directory + File.separator + "config.yml");

	*/
	    public void loadConfig()
	    {
	    	super.loadConfig();
	    }
	    
	    public List<String> getColorList(String root)
	    {
	        return config.getStringList(root);
	    }
	   		 
	 
}

