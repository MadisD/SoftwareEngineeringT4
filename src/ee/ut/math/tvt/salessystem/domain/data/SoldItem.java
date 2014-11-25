package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving
 * history.
 */

@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {
	@Id
	@Column(name = "ID")
	//@NotNull
	private long id;

	@ManyToOne
	@JoinColumn(name = "STOCKITEM_ID", nullable = false)
	private StockItem stockItem;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "ITEMPRICE")
	private double price;
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="SALE_ID", nullable=false)
	private History history;
	
	

	public SoldItem(StockItem stockItem, int quantity) {
		this.stockItem = stockItem;
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
		this.id = stockItem.getId();
		this.quantity = quantity;

	}
	
	public SoldItem(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getSum() {
		return price * ((double) quantity);
	}

	public StockItem getStockItem() {
		return stockItem;
	}

	public History getSale() {
		return history;
	}

	public void setSale(History sale) {
		this.history = sale;
	}


}
