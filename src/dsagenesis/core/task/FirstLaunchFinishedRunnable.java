/*
 *  ___  ___   _      ___                 _    
 * |   \/ __| /_\    / __|___ _ _  ___ __(_)___
 * | |) \__ \/ _ \  | (_ / -_) ' \/ -_|_-< (_-<
 * |___/|___/_/ \_\  \___\___|_||_\___/__/_/__/
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
package dsagenesis.core.task;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jhv.util.debug.logger.ApplicationLogger;
import dsagenesis.core.GenesisLauncher;
import dsagenesis.core.config.GenesisConfig;
import dsagenesis.core.sqlite.DBConnector;

/**
 * FirstLaunchFinishedRunnable
 */
public class FirstLaunchFinishedRunnable 
		extends AbstractTaskRunnable 
{
	
	/**
	 * the panel with the buttons
	 */
	private JPanel panel;
	
	/**
	 * Constructor
	 * 
	 * @param launcher
	 */
	public FirstLaunchFinishedRunnable(
			GenesisLauncher launcher,
			JLabel statusLabel,
			String statusMessage,
			JPanel panel
		) 
	{
		super(
				launcher,
				statusLabel,
				statusMessage
			);
		
		this.panel = panel;
	}
	
	
	@Override
	public void run()
	{
		super.run();
		
		DBConnector.getInstance().closeConnection();
		GenesisConfig.getInstance().setFirstLaunchDone();	
		
		// enable the buttons
		for( int i=0; i<panel.getComponentCount(); i++ )
		{
			if( panel.getComponent(i) instanceof JButton )
			{
				JButton btn = (JButton)panel.getComponent(i);
				btn.setEnabled(true);
			}
		}
		
		ApplicationLogger.logDebug("First launch finished.");
	}

}
