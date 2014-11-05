package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

public class SoldItemsLog<T> {
	private float sum;
	private List<T> soldItems;
	private String date;
	private String time;
	
	
	
	
	
	public SoldItemsLog(String dateFormat, float sum, List<T> soldItems) {
		super();
		this.sum = sum;
		this.soldItems = soldItems;
		String[] datePieces = dateFormat.split(" ");
		this.date = datePieces[0];
		this.time = datePieces[1];

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
	public List<T> getSoldItems() {
		return soldItems;
	}
	public void setSoldItems(List<T> soldItems) {
		this.soldItems = soldItems;
	}
	
	
	
	
}
