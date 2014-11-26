import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.History;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;


public class HistoryTest {
	private StockItem stockItem1;
	private StockItem stockItem2;
	private SoldItem soldItem1;
	private SoldItem soldItem2;

	@Before
	public void setUp() {
		
		stockItem1 = new StockItem((long) 1, "Test item1", "For testing", 9.1, 3);
		stockItem2 = new StockItem((long) 2, "Test item2", "For testing", 6.0, 3);
		soldItem1 = new SoldItem(stockItem1, 3);
		soldItem2 = new SoldItem(stockItem2, 1);
	}
	
	@Test
    public void testAddSoldItem(){
		List<SoldItem> goods = new ArrayList<SoldItem>();
		goods.add(soldItem1);
		History log = new History();
		log.setOrderDetails(goods);
		//Dont know what to expect here
    }
	@Test
    public void testGetSumWithNoItems(){
    	List<SoldItem> goods = new ArrayList<SoldItem>();
		History log = new History();
		assertEquals(0.0,log.getTotalSum(),0.0);
    	
    }
	@Test
    public void testGetSumWithOneItem(){
    	List<SoldItem> goods = new ArrayList<SoldItem>();
		goods.add(soldItem1);
		History log = new History();
		log.setOrderDetails(goods);
		assertEquals((stockItem1.getPrice()*soldItem1.getQuantity()),log.getTotalSum(),0.0);
    }
	@Test
    public void testGetSumWithMultipleItems(){
    	List<SoldItem> goods = new ArrayList<SoldItem>();
		goods.add(soldItem1);
		goods.add(soldItem2);
		History log = new History();
		log.setOrderDetails(goods);
		double expected = (stockItem1.getPrice()*soldItem1.getQuantity()) + (stockItem2.getPrice()*soldItem2.getQuantity());
		assertEquals(expected,log.getTotalSum(),0.0);
    	
    }

}
