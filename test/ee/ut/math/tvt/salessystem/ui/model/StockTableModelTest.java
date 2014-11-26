package ee.ut.math.tvt.salessystem.ui.model;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;


public class StockTableModelTest {

	private StockItem stockItem1;
    private StockItem stockItem2;
    StockTableModel stockModel;
    
    @Before
    public void setUp(){
    	stockItem1 = new StockItem((long) 1, "Kartul", "toores", 10.0, 100);
    	stockItem2 = new StockItem((long)2,"Sibu","tervislik",5.0,20);
    	stockModel = new StockTableModel();
    }
    
	@Test
    public void testValidateNameUniqueness() {
    	stockModel.addItem(stockItem1);
    	assertFalse(stockModel.validateNameUniqueness("Kartul"));
    }
	
    @Test
    public void testHasEnoughInStock(){
    	stockModel.addItem(stockItem1);
    	assertTrue(stockModel.hasEnoughInStock(stockItem1, 99));
    }
    @Test
    public void testGetItemByIdWhenItemExists(){
    	stockModel.addItem(stockItem1);
    	assertEquals(stockItem1,stockModel.getItemById(stockItem1.getId()));
    }
    @Test (expected = NoSuchElementException.class)
    public void testGetItemByIdWhenThrowsException() {
    	stockModel.addItem(stockItem1);
    	assertEquals(stockItem1,stockModel.getItemById((long)10));
    }

}
