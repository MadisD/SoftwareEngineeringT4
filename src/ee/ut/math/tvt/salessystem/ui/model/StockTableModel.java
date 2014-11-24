package ee.ut.math.tvt.salessystem.ui.model;

import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel() {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 */
	public void addItem(final StockItem stockItem) {
		try {
			StockItem item = getItemById(stockItem.getId());
			item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			
			Session session = HibernateUtil.currentSession();
			session.beginTransaction();
			session.merge(item);
			session.getTransaction().commit();
			
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		}
		catch (NoSuchElementException e) {
			try{
			Session session = HibernateUtil.currentSession();
			session.beginTransaction();
			session.persist(stockItem);
			session.getTransaction().commit();
			
			rows.add(stockItem);
			}
			
			catch (PersistentObjectException t) {
				log.error("Could not save item to the database");
				t.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Database transaction failed", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
			
			log.debug("Added " + stockItem.getName()
					+ " quantity of " + stockItem.getQuantity());
		}
		fireTableDataChanged();
	}

	
	/**
	 * Removes items from stock after purchase. If stock doesn't run empty, will only decrease quantity
	 */
	public void removeItem(final SoldItem sold){
		try {
			int currentQuantity = getItemById(sold.getId()).getQuantity();
			int removed = sold.getQuantity();
			if (currentQuantity-removed != 0) {
				getItemById(sold.getId()).setQuantity(currentQuantity-removed);
				
				
				Session session = HibernateUtil.currentSession();
				session.beginTransaction();
				session.merge(getItemById(sold.getId()));
				session.getTransaction().commit();
				
				
				
			} else {
				
				Session session = HibernateUtil.currentSession();
				session.beginTransaction();
				session.delete(getItemById(sold.getId()));
				session.getTransaction().commit();
				
				
				removeItem(sold.getId());
			}
		} catch (NoSuchElementException e) {
			System.out.println(e);
		}
		fireTableDataChanged();
	}
	
	
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

}
