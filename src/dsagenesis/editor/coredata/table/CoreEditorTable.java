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
package dsagenesis.editor.coredata.table;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import dsagenesis.core.model.sql.AbstractSQLTableModel;
import dsagenesis.core.sqlite.TableHelper;
import dsagenesis.core.util.logic.Formula;
import dsagenesis.core.util.logic.Selection;
import dsagenesis.editor.coredata.CoreEditorFrame;
import dsagenesis.editor.coredata.table.cell.BasicCellRenderer;
import dsagenesis.editor.coredata.table.cell.CheckBoxCellEditor;
import dsagenesis.editor.coredata.table.cell.CheckBoxCellRenderer;
import dsagenesis.editor.coredata.table.cell.CommitButtonCell;
import dsagenesis.editor.coredata.table.cell.NumericCellRenderer;

/**
 * CoreEditorTable
 * 
 * JTable implementation that is filled with AbstractSQLTabelModel's.
 */
public class CoreEditorTable 
		extends JTable 
		implements TableModelListener
{
	// ============================================================================
	//  Constants
	// ============================================================================
		
	private static final long serialVersionUID = 1L;

	
	// ============================================================================
	//  Variables
	// ============================================================================
		
	private AbstractSQLTableModel sqlTable;
	
	private CoreEditorFrame jframe;
	
	private CommitButtonCell btnCommit;
	
	// ============================================================================
	//  Constructors
	// ============================================================================
			
	/**
	 * Constructor.
	 * 
	 * @param sqlTable
	 */
	public CoreEditorTable(CoreEditorFrame frame, AbstractSQLTableModel sqlTable)
	{
		super();
		
		this.sqlTable = sqlTable;
		this.jframe = frame;
		setup();
	}
	
	// ============================================================================
	//  Functions
	// ============================================================================
	
	/**
	 * setup
	 * 
	 * the defaults.
	 */
	private void setup()
	{
		// this is better for larger tables
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
		this.setName(sqlTable.getDBTableName());
					
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.getTableHeader().setReorderingAllowed(false);
		
		// renderer
		this.setDefaultRenderer(String.class, new BasicCellRenderer());
		this.setDefaultRenderer(Boolean.class, new CheckBoxCellRenderer());
		this.setDefaultRenderer(Number.class, new NumericCellRenderer());
		this.setDefaultRenderer(Object.class, new BasicCellRenderer());
		
		// editors
		this.setDefaultEditor(Boolean.class, new CheckBoxCellEditor());
		
		this.setRowHeight(22);
	}
	
	/**
	 * getFrame
	 * 
	 * @return
	 */
	public CoreEditorFrame getFrame()
	{
		return jframe;
	}
	
	/**
	 * getSQLTable
	 * 
	 * @return
	 */
	public AbstractSQLTableModel getSQLTable()
	{
		return this.sqlTable;
	}
	
	/**
	 * getNote
	 * 
	 * @return
	 */
	public String getNote()
	{
		return this.sqlTable.getNote();
	}
	
	/**
	 * getLabel
	 * 
	 * @return
	 */
	public String getLabel()
	{
		return this.sqlTable.getDBTableLabel();
	}
	
	/**
	 * isReadOnly
	 * 
	 * @return
	 */
	public boolean isReadOnly()
	{
		return ((CoreEditorTableModel)this.getModel()).isReadOnly();
	}
	
	/**
	 * containsUncommitedData
	 * 
	 * @return
	 */
	public boolean containsUncommitedData()
	{
		if( btnCommit == null )
			return false;
		
		if( btnCommit.getChangedRows().contains(true) )
			return true;
		
		return false;
	}
	
	/**
	 * getUncommitedRowIndices
	 * 
	 * @return
	 */
	public Vector<Integer> getUncommitedRowIndices()
	{
		Vector<Integer> indices = new Vector<Integer>();
		
		Vector<Boolean> changedRows = btnCommit.getChangedRows();
		for( int i=0; i<changedRows.size(); i++ )
		{
			if( changedRows.elementAt(i) == true )
				indices.add(i);		
		}
		
		return indices;
	}
	
	/**
	 * initCommitButtonCell
	 * 
	 * @param data
	 */
	public void initCommitButtonCell(Vector<Vector<Object>> data)
	{
		if( sqlTable.isEditable() )
		{
			// setup commit button and add listener
			TableColumn currColumn = this.getColumnModel().getColumn(this.getColumnCount()-1);
			currColumn.setMaxWidth(25);
			currColumn.setMinWidth(25);
			this.btnCommit = new CommitButtonCell(this);
			this.btnCommit.initRows(data.size());
			
			currColumn.setCellEditor(this.btnCommit);
			currColumn.setCellRenderer(this.btnCommit);
			
			// add listener
			this.getModel().addTableModelListener(this);
			this.getModel().addTableModelListener(this.jframe);
		}
	}
	
	/**
	 * addEmptyRow
	 * 
	 * adds an empty row to the table.
	 * but not to the DB.
	 */
	public void addEmptyRow()
	{
		Vector<Class<?>> classes = this.sqlTable.getTableColumnClasses();
		final Vector<Object> row = new Vector<Object>();
		final CoreEditorTableModel model = ((CoreEditorTableModel)this.getModel());
		
		// generate id
		row.addElement(TableHelper.createNewID(this.sqlTable.getDBTableName()));
		
		for(int i=1; i< classes.size(); i++ )
		{
			if( classes.elementAt(i).equals(Vector.class) )
			{
				row.addElement(new Vector<Object>());
			
			} else if( classes.elementAt(i).equals(Formula.class) ) {
				row.addElement(new Formula());
				
			} else if( classes.elementAt(i).equals(Selection.class) ) {
				row.addElement(new Selection());
				
			} else if( classes.elementAt(i).equals(String.class) ) {
				row.addElement("");
				
			} else if( classes.elementAt(i).equals(Integer.class) ) {
				row.addElement(0);
				
			} else if( classes.elementAt(i).equals(Float.class) ) {
				row.addElement(1.0);
				
			} else if( classes.elementAt(i).equals(Boolean.class) ) {
				row.addElement(0);
				
			} else {
				row.addElement(null);
			}
		}
		// commit button cell
		row.addElement(null);
		btnCommit.addRow();
		
		// sorter needs to be disable otherwise a NullPointer is fired.
		this.setAutoCreateRowSorter(false);
		this.setRowSorter(null);
		model.addRow(row);
		setAutoCreateRowSorter(true);
		
	}
	
	/**
	 * removeRowFromTable
	 * 
	 * called by RemoveTableRowTask
	 * 
	 * removes only the row from the JTable.
	 * 
	 * @param row
	 */
	public void removeRowFromTable(int row)
	{
		// sorter needs to be disable otherwise a NullPointer is fired.
		this.setAutoCreateRowSorter(false);
		this.setRowSorter(null);
		((CoreEditorTableModel)this.getModel()).removeRow(row);
		setAutoCreateRowSorter(true);
		
		btnCommit.deleteRow(row);
		
		while( this.getRowCount() <= row )
			row--;
		
		if( row > -1 )
			this.setRowSelectionInterval(row,row);
	}
	
	/**
	 * commitRow
	 * 
	 * called by the commit button of this row
	 * starts the commit task in our frame
	 * 
	 * @param row
	 */
	public void commitRow(int row)
	{
		jframe.startCommitTableRowTask(this, row);
	}
	
	/**
	 * disableCommitButtonFor
	 * 
	 * called after a successful commit
	 * 
	 * @param row
	 */
	public void disableCommitButtonFor(int row)
	{
		this.btnCommit.setEnabled(row, false);
	}

	/**
	 * overridden for handling the commit button updates
	 */
	@Override
	public void tableChanged(TableModelEvent e)
	{
		super.tableChanged(e);
		
		// now we check if the commit button needs to be enabled
		int row = e.getFirstRow();
		int column = e.getColumn();
        
		if( e.getType() != TableModelEvent.UPDATE )
			return;
		
		if ( row == -1 )
			return;
		
		// this occurs if button caused the event
		if( column == (this.getColumnCount()-1)
				&& this.getValueAt(row, column) == null 
			)
			return;
		
        if( this.btnCommit == null )
        	return;
        
        // now something changed enable button
        this.btnCommit.setEnabled(row, true);
        
		// to update the icons on the buttons
        SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					CoreEditorTable.this.repaint();
				}
			});
	}
}
