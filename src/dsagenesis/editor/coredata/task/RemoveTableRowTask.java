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
package dsagenesis.editor.coredata.task;


import java.lang.reflect.InvocationTargetException;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import dsagenesis.core.model.sql.AbstractSQLTableModel;
import dsagenesis.core.sqlite.DBConnector;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.editor.coredata.table.CoreEditorTable;
import dsagenesis.editor.coredata.table.CoreEditorTableModel;

import jhv.swing.task.AbstractSerialTask;
import jhv.util.debug.logger.ApplicationLogger;

/**
 * RemoveTableRowTask
 * 
 * SwingWorker Task for deleting table rows and junctions
 */
public class RemoveTableRowTask 
		extends AbstractSerialTask 
{

	// ============================================================================
	//  Variables
	// ============================================================================

	private CoreEditorTable table;
	
	private String statusMsg;
	
	// ============================================================================
	//  Constructors
	// ============================================================================

	/**
	 * Constructor.
	 * 
	 * @param lbl
	 * @param pb
	 * @param table
	 * @param msg
	 */
	public RemoveTableRowTask(
			JLabel lbl,
			JProgressBar pb,
			CoreEditorTable table,
			String msg
		) 
	{
		super(lbl,pb);
		this.table = table;
		this.statusMsg = msg;
		
	}
	
	
	// ============================================================================
	//  Functions
	// ============================================================================

	@Override
	protected String prepareNextStep() 
	{
		return statusMsg;
	}

	@Override
	protected void doNextStep() 
			throws Exception 
	{
		int row = table.getSelectedRow();
		
		CoreEditorTableModel model = ((CoreEditorTableModel)table.getModel());
		Object id = model.getValueAt(row, 0);
		AbstractSQLTableModel sqlTable =  table.getSQLTable();
		
		if( id == null
				|| !TableHelper.idExists(id.toString(), sqlTable.getDBTableName()) 
			)
		{
			this.removeRowFromTable(row);
			return;
		}
		
		// here we need also delete all db entries
		String delete = "DELETE FROM "
				+ sqlTable.getDBTableName()
				+ " WHERE ID='"+id+"'";
		
		sqlTable.updateReferencesFor(id,-1,model);
		long querytime = DBConnector.getInstance().getQueryTime();
			
		DBConnector.getInstance().executeUpdate(delete);
		querytime += DBConnector.getInstance().getQueryTime();
			
		ApplicationLogger.logInfo(
				sqlTable.getDBTableName() 
					+ " delete time " + querytime + " ms"
			);
			
		this.removeRowFromTable(row);
	}

	/**
	 * removeRowFromTable
	 * 
	 * @param row
	 */
	protected void removeRowFromTable(final int row) 
	{
		// must be done in EDT
		try
		{
			SwingUtilities.invokeAndWait(new Runnable(){
					public void run() 
					{
						table.removeRowFromTable(row);
					}
				});
		} catch (InvocationTargetException | InterruptedException e) {
			// ignore
		}
	}
	

}
