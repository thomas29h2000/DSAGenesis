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
package dsagenesis.core.model.sql;

import java.util.Vector;

import dsagenesis.core.model.xml.AbstractGenesisModel;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.CoreEditorTable;

/**
 * TableColumnLabels
 * 
 * SQL Model Class.
 * 
 * This table is used to store the labels for each non-system
 * table column.
 */
public class TableColumnLabels
		extends AbstractSQLTableModel 
{
	// ============================================================================
	//  Variables
	// ============================================================================
		
	// ============================================================================
	//  Constructors
	// ============================================================================
			
	/**
	 * Constructor.	
	 */
	public TableColumnLabels()
	{
		super();
		
		this.prefix = "tcl_";
		this.usesPrefix = false;
		
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("tcl_table_name");
		this.dbColumnNames.addElement("tcl_column_name");
		this.dbColumnNames.addElement("tcl_label");
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	public String getDBTableName() 
	{
		return "TableColumnLabels";
	}

	@Override
	public boolean isEditable() 
	{
		return true;
	}

	@Override
	public void setupJTableColumnModels(
			CoreEditorFrame ceframe,
			CoreEditorTable cetable
		)
	{
		super.setupJTableColumnModels(ceframe, cetable);
		
		//TODO 
	}
	
	/**
	 * since this is a system table there is no AbstractGenesisModel 
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		return null;
	}

}
