package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.History;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItemsLog;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;


public class HistoryTableModel  extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	protected final String[] headers;
	protected List<History> rows;
	
	
	public HistoryTableModel() {
		this.headers = new String[] {"Date","Time","Price"};
		this.rows = new ArrayList<>();
		

	}
	public History getLog(int index){
		return rows.get(index);
	}
	
	public void addLog(History sale){
		rows.add(sale);
		fireTableDataChanged();
	}
	
	protected Object getColumnValue(History item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getTotalPrice();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public String[] getHeaders(){
		return this.headers;
	}
	
	@Override
	public int getRowCount() {
		return this.rows.size();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

    @Override
    public String getColumnName(final int columnIndex) {
        return headers[columnIndex];
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getColumnValue(rows.get(rowIndex), columnIndex);
	}
	
	 public void populateWithData(final List<History> data) {
	        rows.clear();
	        rows.addAll(data);
	        fireTableDataChanged();
	    }



	

	
	
}
