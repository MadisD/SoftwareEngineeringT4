package ee.ut.math.tvt.salessystem.domain.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HISTORY")
public class History implements Cloneable, DisplayableItem{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(name = "SALE_TIME")
	private String dateTime;
	
	@Column(name = "TOTAL")
	private double totalPrice;

	@OneToMany(mappedBy = "history")
	private List<SoldItem> orderDetails;

	public History(String dateTime, List<SoldItem> orderDetails) {
		this.dateTime = dateTime;
		this.setOrderDetails(orderDetails);
		this.totalPrice = getTotalSum();
	}
	
	public History(String dateTime) {
		this.dateTime = dateTime;
		this.totalPrice = getTotalSum();
		this.setOrderDetails(new ArrayList<SoldItem>());
	}
	
	public History() {
		this(timeDate());
	}


	
	public List<SoldItem> getOrderDetails() {
		return orderDetails;
	}


	public String getDateTime() {
		return dateTime;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return getDateTime();
	}
	
	
	public String getDate(){
		return dateTime.split(" ")[0];
	}
	
	public String getTime(){
		return dateTime.split(" ")[1];
	}
	
	public static String timeDate() {

		Date now = new Date();
		SimpleDateFormat upDate = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		String dateTime = upDate.format(now);
		return dateTime;
	}

	
	public double getTotalSum() {
		
		if (getOrderDetails() == null) {
			return 0;
		}
		
		double totalSum = 0;
		
		Iterator<SoldItem> it = getOrderDetails().iterator();
		
		while (it.hasNext()) {
			totalSum += it.next().getSum();
		}
		
		return totalSum;
	}

	@Override
	public int getQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setOrderDetails(List<SoldItem> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
