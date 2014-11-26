package ee.ut.math.tvt.salessystem.service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private static final Logger log = Logger
			.getLogger(HibernateDataService.class);

	private Session session = HibernateUtil.currentSession();

	public List<SoldItem> getSoldItems() {
		List<SoldItem> result = new ArrayList<SoldItem>();
		try {
			result = session.createQuery("from SOLDITEM").list();
		} catch (Throwable ex) {
			log.error("No database connection!");
			JOptionPane.showMessageDialog(null, "Unable to connect to the database!");
		}

		return result;
	}

	public List<StockItem> getStockItems() {
		List<StockItem> result = new ArrayList<StockItem>();
		try {
			result = session.createQuery("from STOCKITEM").list();
		} catch (Throwable ex) {
			log.error("No database connection!");
			JOptionPane.showMessageDialog(null, "Unable to connect to the database!");
		}
		return result;
	}
	

}