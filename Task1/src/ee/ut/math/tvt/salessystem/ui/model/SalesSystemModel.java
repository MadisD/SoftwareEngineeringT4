package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {
    
    private static final Logger log = Logger.getLogger(SalesSystemModel.class);
    //History model
    private HistoryTableModel historyTableModel;
    
    // Warehouse model
    private StockTableModel warehouseTableModel;
    
    // Current shopping cart model
    private PurchaseInfoTableModel currentPurchaseTableModel;

    private final SalesDomainController domainController;

    /**
     * Construct application model.
     * @param domainController Sales domain controller.
     */
    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        
        warehouseTableModel = new StockTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        historyTableModel = new HistoryTableModel();

        // populate stock model with data from the warehouse
        warehouseTableModel.populateWithData(domainController.loadWarehouseState());
        historyTableModel.populateWithData(domainController.loadHistoryState());
        

    }

    public StockTableModel getWarehouseTableModel() {	
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }
    
    public HistoryTableModel getCurrentHistoryModel(){
    	return historyTableModel;	
    }
    
}
