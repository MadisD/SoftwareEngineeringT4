package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.History;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	private Session session = HibernateUtil.currentSession();
	
	
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		Iterator<SoldItem> it = goods.iterator();
		SoldItem item;
		session.beginTransaction();
		
		try {
			History newHistoryItem = new History(History.timeDate(), goods);
			session.save(newHistoryItem);
			
			while(it.hasNext()) {
				item = it.next();
				item.setSale(newHistoryItem);
				session.update(item.getStockItem());
				session.save(item);
			}
			
			session.getTransaction().commit();
			
		} catch(Throwable e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new VerificationFailedException("DB Failure!");
		}
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		List<StockItem> dataset = HibernateUtil.currentSession().createQuery("from StockItem").list();
		return dataset;
	}
	
	@SuppressWarnings("unchecked")
	public List<History> loadHistoryState() {
		session.createQuery("from SoldItem").list();
		return (List<History>)(session.createQuery("from History").list());
	}
	
	public void endSession() {
		HibernateUtil.closeSession();
	}
}
