package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {
    
    private HistoryTableModel historyModel;
    private SalesSystemModel model;

    public HistoryTab(SalesSystemModel model) {
    	this.model = model;
    } 
    
    public Component draw() {
        JPanel panel = new JPanel();
        historyModel = model.getCurrentHistoryModel();
        JTable table = new JTable(historyModel);
        
        final PurchaseInfoTableModel itemsTableModel = new PurchaseInfoTableModel();
        JTable itemsTable = new JTable(itemsTableModel);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        
        
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane scrollPane2 = new JScrollPane(itemsTable);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					itemsTableModel.populateWithData(historyModel.getLog(selectedRow).getOrderDetails());
				}
			}

		});
        

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        
        
        panel.setLayout(gb);
        panel.add(scrollPane, gc);
        panel.add(scrollPane2,gc);

        panel.setBorder(BorderFactory.createTitledBorder("Log of all sales"));
        return panel;
    }
}