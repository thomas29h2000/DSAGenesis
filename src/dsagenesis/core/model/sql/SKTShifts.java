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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dsagenesis.core.model.xml.AbstractGenesisModel;

/**
 * SKTShifts
 * 
 * SQL Model Class.
 * 
 * this class is only used by Core Data Editor (for generating the JTable), 
 * SKTShifts are executed by SKTMatrix
 */
public class SKTShifts
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
	 * 
	 * @param rs	
	 * @throws SQLException 
	 */
	public SKTShifts(ResultSet rs) 
			throws SQLException 
	{
		super(rs);
		
		this.dbColumnNames = new Vector<String>();
		this.dbColumnNames.addElement("ID");
		this.dbColumnNames.addElement("skts_ref_source_ID");
		this.dbColumnNames.addElement("skts_target_table_name");
		this.dbColumnNames.addElement("skts_target_column_name");
		this.dbColumnNames.addElement("skts_target_value");
		this.dbColumnNames.addElement("skts_is_down_shift");
		this.dbColumnNames.addElement("skts_is_up_shift");
		this.dbColumnNames.addElement("skts_shift_factor");
		this.dbColumnNames.addElement("skts_is_absolute_shift");
		this.dbColumnNames.addElement("skts_skt_column");
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================

	/**
	 * for accessing the SKTShifts use class SKTMatrix. 
	 */
	@Override
	public AbstractGenesisModel getRow(String id) 
	{
		return null;
	}	
	
	@Override
	public Vector<Class<?>> getTableColumnClasses()
	{
		Vector<Class<?>> vec = new Vector<Class<?>>(this.dbColumnNames.size());
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(String.class);
		vec.add(Boolean.class);
		vec.add(Boolean.class);
		vec.add(Integer.class);
		vec.add(Boolean.class);
		vec.add(String.class);
		
		return vec;
	}

}
