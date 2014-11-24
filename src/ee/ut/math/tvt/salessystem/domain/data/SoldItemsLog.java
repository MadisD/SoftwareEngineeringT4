package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

@Entity
@Table(name = "HISTORY")
public class SoldItemsLog<T> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name ="TOTAL")
	private float sum;
	
	@OneToMany(mappedBy = "history")
	private List<SoldItem> soldItems;
	
	@Column(name = "SALE_TIME")
	private Calendar timeStamp;
	
	
	private String date;
	
	
	private String time;
	
	
	private PurchaseInfoTableModel purchaseModel;
	
	public SoldItemsLog(Calendar cal,String dateFormat, float sum, List<SoldItem> soldItems,PurchaseInfoTableModel purchaseModel) {
		super();
		this.sum = sum;
		this.timeStamp = cal;
		this.soldItems = soldItems;
		String[] datePieces = dateFormat.split(" ");
		this.date = datePieces[0];
		this.time = datePieces[1];
		this.purchaseModel = purchaseModel;
		this.id = 0;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
