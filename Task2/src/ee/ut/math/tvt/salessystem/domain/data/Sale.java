package ee.ut.math.tvt.salessystem.domain.data;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Sale object. Contains client and sold items.
 */
@Entity
@Table(name = "SALE")
public class Sale implements DisplayableItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(targetEntity = SoldItem.class, mappedBy = "sale", cascade = CascadeType.ALL)
	private List<SoldItem> soldItems;
	private Date sellingTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CLIENT_ID")
	private Client client;

	/** Empty constructors are used by hibernate */
	public Sale() {
		this.soldItems = new ArrayList<>();
	}

	public Client getClient() {
		return client;
	}

	public Sale(Client client) {
		this.client = client;
		this.soldItems = new ArrayList<>();
	}

	public Sale(List<SoldItem> stuff) {
		this.soldItems = stuff;
		this.sellingTime = new Date();
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getSellingTime() {
		return sellingTime;
	}

	public void setSellingTime(Date sellingTime) {
		this.sellingTime = sellingTime;
	}

	public List<SoldItem> getSoldItems() {
		return soldItems;
	}

	public void setSoldItems(List<SoldItem> soldItems) {
		this.soldItems = soldItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addSoldItem(SoldItem item) {
		item.setSale(this);
		soldItems.add(item);
	}

	public void addItem(StockItem item, int quantity) {
		for (SoldItem sitem : soldItems) {
			if (sitem.getStockItem() == item) {
				sitem.setQuantity(sitem.getQuantity() + quantity);
				return;
			}
		}
		SoldItem sitem = new SoldItem(item, quantity);
		soldItems.add(sitem);
	}

	public double getSum() {
		double sum = 0.0;
		for (SoldItem item : soldItems) {
			sum = sum + item.getPrice() * ((double) item.getQuantity());
		}
		return sum;
	}
}
