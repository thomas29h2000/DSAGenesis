/*
 *  __  __      
 * /\ \/\ \  __________   
 * \ \ \_\ \/_______  /\   
 *  \ \  _  \  ____/ / /  
 *   \ \_\ \_\ \ \/ / / 
 *    \/_/\/_/\ \ \/ /  
 *             \ \  /
 *              \_\/
 *
 * -----------------------------------------------------------------------------
 * @author: Herbert Veitengruber 
 * @version: 1.0.0
 * -----------------------------------------------------------------------------
 *
 * Copyright (c) 2013 Herbert Veitengruber 
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/mit-license.php
 */
package dsagenesis.core.config;

import java.util.Enumeration;

import jhv.util.config.AbstractConfig;


public class GenesisConfig 
		extends AbstractConfig 
		implements IGenesisConfigKeys
{

	// ============================================================================
	//  Variables
	// ============================================================================
		
	/**
	 * application version.
	 */
	public static String APP_VERSION = "0.1.0";
	
	
	// ============================================================================
	//  Constructors
	// ============================================================================
	
	private GenesisConfig() 
	{
		this.loadSystem();
		this.loadUser();
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * getInstance
	 * 
	 * Access the instance.
	 * 
	 * @return
	 */
	public static GenesisConfig getInstance()
	{
		if( GenesisConfig.instance == null )
			GenesisConfig.instance = new GenesisConfig();
		
		return (GenesisConfig)GenesisConfig.instance;
	}
	
	/**
	 * located in installation dir.
	 * 
	 * @return
	 */
	public String getPathData()
	{
		return  System.getProperty("user.dir") 
				+ System.getProperty("file.separator")
				+ getString(GenesisConfig.KEY_PATH_DATA) 
				+ System.getProperty("file.separator");
	}
	
	/**
	 * located in users home dir.
	 * 
	 * @return
	 */
	public String getPathUserHome()
	{
		return  System.getProperty("user.home") 
				+ System.getProperty("file.separator")
				+ getString(GenesisConfig.KEY_PATH_USER_HOME) 
				+ System.getProperty("file.separator");
	}
	
	public String getPathTemplate()
	{
		return  getPathData() 
				+ getString(GenesisConfig.KEY_PATH_TEMPLATE) 
				+ System.getProperty("file.separator");
	}
	
	public String getPathTemplateHome()
	{
		return  getPathUserHome() 
				+ getString(GenesisConfig.KEY_PATH_TEMPLATE) 
				+ System.getProperty("file.separator");
	}
	
	public String getPathHero()
	{
		return  getPathUserHome() 
				+ getString(GenesisConfig.KEY_PATH_HERO) 
				+ System.getProperty("file.separator");
	}
	
	public String getPathArchtype()
	{
		return  getPathData() 
				+ getString(GenesisConfig.KEY_PATH_ARCHTYPE) 
				+ System.getProperty("file.separator");
	}
	
	public String getPathRace()
	{
		return  getPathData() 
				+ getString(GenesisConfig.KEY_PATH_RACE) 
				+ System.getProperty("file.separator");
	}
	
	public String getPathCulture()
	{
		return  getPathData() 
				+ getString(GenesisConfig.KEY_PATH_CULTURE) 
				+ System.getProperty("file.separator");
	}
	
	public String getPathProfession()
	{
		return  getPathData() 
				+ getString(GenesisConfig.KEY_PATH_PROFESSION) 
				+ System.getProperty("file.separator");
	}
		
	public String getPathRandomNameGenerator()
	{
		return  getPathData() 
				+ getString(GenesisConfig.KEY_PATH_NAME) 
				+ System.getProperty("file.separator");
	}
	
	public String getDBFile()
	{
		return  getPathData() 
				+ getString(GenesisConfig.KEY_DB_FILE);
	}
	
	
	/**
	 * for the first launch the system defaults are created.
	 */
	@Override
	protected void setSystemDefaults() 
	{
		// Global defaults
		this.setSystemProperty(KEY_APPICON, "images/icons/appIcon64.png");
		this.setSystemProperty(KEY_APPTITLE, "DSA Genesis");
		this.setSystemProperty(KEY_DEBUG_LEVEL, "2");
		this.setSystemProperty(KEY_IS_LOGGER_ENABLED, "true");
		this.setSystemProperty(KEY_LANGUAGE, "de_DE");
		this.setSystemProperty(KEY_IS_FIRST_LAUNCH,"true");
		
		// Core Editor defaults
		this.setSystemProperty(KEY_WIN_BASE+"."+KEY_SIZE, "800,600");
		this.setSystemProperty(KEY_WIN_BASE+"."+KEY_ISFULLSCREEN, "false");
		this.setSystemProperty(KEY_WIN_BASE+"."+KEY_POSITION, "0,0");
		
		// Meta Editor defaults
		this.setSystemProperty(KEY_WIN_META+"."+KEY_SIZE, "800,600");
		this.setSystemProperty(KEY_WIN_META+"."+KEY_ISFULLSCREEN, "false");
		this.setSystemProperty(KEY_WIN_META+"."+KEY_POSITION, "0,0");
				
		// Meta Editor defaults
		this.setSystemProperty(KEY_WIN_HERO+"."+KEY_SIZE, "800,600");
		this.setSystemProperty(KEY_WIN_HERO+"."+KEY_ISFULLSCREEN, "false");
		this.setSystemProperty(KEY_WIN_HERO+"."+KEY_POSITION, "0,0");
		
		// path defaults
		this.setSystemProperty(KEY_PATH_USER_HOME, "DSAGenesis");
		this.setSystemProperty(KEY_PATH_DATA, "data");
		this.setSystemProperty(KEY_PATH_TEMPLATE, "templates");
		this.setSystemProperty(KEY_PATH_HERO, "helden");
		this.setSystemProperty(KEY_PATH_ARCHTYPE, "archetypen");
		this.setSystemProperty(KEY_PATH_RACE, "rassen");
		this.setSystemProperty(KEY_PATH_CULTURE, "kulturen");
		this.setSystemProperty(KEY_PATH_PROFESSION, "professionen");
		this.setSystemProperty(KEY_PATH_NAME, "namen");
		
		// database file
		this.setSystemProperty(KEY_DB_FILE, "core_de_DE.s3db");
		
		// default settings hero editor
		this.setSystemProperty(KEY_DEFAULT_START_CP, "110");
		this.setSystemProperty(KEY_DEFAULT_MAX_DISADVANTAGE_CP, "50");
		this.setSystemProperty(KEY_DEFAULT_MAX_ATTRIBUTE_CP, "100");
		this.setSystemProperty(KEY_DEFAULT_MAX_NEGATIVEATTRIBUTE_CP, "30");
	}

	@Override
	protected void setUserDefaults() 
	{
		Enumeration<?> propertyNames = propertiesSystem.propertyNames();
		
		while( propertyNames.hasMoreElements() )
		{
			String key = (String)propertyNames.nextElement();
			
			if( key.startsWith(KEY_DEBUG_LEVEL) 
					|| key.startsWith(KEY_IS_LOGGER_ENABLED)
					|| key.startsWith(KEY_LANGUAGE)
					|| key.startsWith(KEY_WIN)
					|| key.startsWith(KEY_DB)
					|| key.startsWith(KEY_DEFAULT)
				)
				propertiesUser.put(key, propertiesSystem.get(key));
		}
	}

	@Override
	public void resetUser() 
	{
		setUserDefaults();
		saveUser();
	}


}
