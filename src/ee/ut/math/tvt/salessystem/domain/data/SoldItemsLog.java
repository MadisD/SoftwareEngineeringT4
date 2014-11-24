package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class SoldItemsLog<T> {
	private float sum;
	private List<SoldItem> soldItems;
	private String date;
	private String time;
	private PurchaseInfoTableModel purchaseModel;
	
	public SoldItemsLog(String dateFormat, float sum, List<SoldItem> soldItems,PurchaseInfoTableModel purchaseModel) {
		super();
		this.sum = sum;
		this.soldItems = soldItems;
		
		String[] datePieces = dateFormat.split(" ");
		this.date = datePieces[0];
		this.time = datePieces[1];
		this.purchaseModel = purchaseModel;

	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getTime(){
		return this.time;
	}

	
	public float getSum() {
		return sum;
	}
	public void setSum(float sum) {
		this.sum = sum;
	}
	public List<SoldItem> getSoldItems() {
		return soldItems;
	}
	public void setSoldItems(List<SoldItem> soldItems) {
		this.soldItems = soldItems;
	}
	
	
	
	
}
