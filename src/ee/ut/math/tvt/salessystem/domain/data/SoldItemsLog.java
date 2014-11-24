package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

public class SoldItemsLog<T> {
	private Date date;
	private float sum;
	private List<T> soldItems;
	
	
	
	
	
	public SoldItemsLog(Date date, float sum, List<T> soldItems) {
		super();
		this.date = date;
		this.sum = sum;
		this.soldItems = soldItems;
	}
	
	
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getSum() {
		return sum;
	}
	public void setSum(float sum) {
		this.sum = sum;
	}
	public List<T> getSoldItems() {
		return soldItems;
	}
	public void setSoldItems(List<T> soldItems) {
		this.soldItems = soldItems;
	}
	
	
	
	
}
