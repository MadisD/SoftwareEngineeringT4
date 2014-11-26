package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest{

	StockItem stockItem1;
	StockItem stockItem2;
	SoldItem soldItem1;
	SoldItem soldItem2;
	
	@Before
	public void setUp(){
		stockItem1 = new StockItem((long) 1, "Ginger Joe", "Parim", 5.0, 2);
		stockItem2 = new StockItem((long) 2, "A Le Coq Lager", "Uus", 5.0, 1);
		soldItem1 = new SoldItem(stockItem1, 2);
		soldItem2 = new SoldItem(stockItem2, 1);
	}
	
	@Test
	public void testGetColumnValue(){
		PurchaseInfoTableModel pitm= new PurchaseInfoTableModel();
		pitm.addItem(soldItem1);
		System.out.println(pitm.toString());
		assertEquals(pitm.getColumnValue(soldItem1, 0), (long)1); //Item ID
		assertEquals(pitm.getColumnValue(soldItem1, 1),"Ginger Joe"); //Item name
		assertEquals(pitm.getColumnValue(soldItem1, 2), 5.0); //Item price
		assertEquals(pitm.getColumnValue(soldItem1, 3), 2); //Quantity
		assertEquals(pitm.getColumnValue(soldItem1, 4), new Double(2 * 5.0));//Sum
	}

	@Test
	public void testAddItem(){
		PurchaseInfoTableModel pitm= new PurchaseInfoTableModel();
		pitm.addItem(soldItem1);
		int rowCount=pitm.getRowCount();
		assertEquals(pitm.getTableRows().get(rowCount-1), soldItem1);
		pitm.addItem(soldItem2);
		rowCount=pitm.getRowCount();
		assertEquals(pitm.getTableRows().get(rowCount-1), soldItem2);
		
	}
}