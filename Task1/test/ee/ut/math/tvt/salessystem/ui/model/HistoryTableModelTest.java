package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.History;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryTableModelTest {
	private StockItem Alexander;
	private SoldItem solditem1;
	private HistoryTableModel historyTable;
	private History history;
	private String date;
	private List<SoldItem> list;

	@Before
	public void setUp() {
		historyTable = new HistoryTableModel();
		date = "2014.11.26 17:16";
		Alexander = new StockItem(1l, "Alexander", "olu", 2.0, 8);
	}

	@Test
	public void testGetSum() {
		solditem1 = new SoldItem(Alexander, 3);
		history = new History();
		list = new ArrayList<SoldItem>();
		list.add(solditem1);
		history = new History(date, list);
		assertEquals((Double) historyTable.getColumnValue(history, 2), 6.0, 0.0001);
	}

	@Test
	public void testGetSumWithZeroQuantity() {
		solditem1 = new SoldItem(Alexander, 0);
		history = new History();
		list = new ArrayList<SoldItem>();
		list.add(solditem1);
		history = new History(date, list);
		assertEquals((Double) historyTable.getColumnValue(history, 2), 0.0, 0.0001);
	}
}