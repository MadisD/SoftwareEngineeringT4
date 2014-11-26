package ee.ut.math.tvt.salessystem.domain.data;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	private SoldItem item;

	@Before
	public void setUp() {
		item = new SoldItem(new StockItem(12L,"Jaatis", "Magus", 4.59, 12), 4);
	}

	@Test
	public void testGetSum() {
		assertEquals(item.getSum(), item.getPrice() * item.getQuantity(),
				0.0001);
	}

	@Test
	public void testGetSumWithZeroQuantity() {
		item.setQuantity(0);
		assertEquals(item.getSum(), 0.0, 0.0001);
	}
}