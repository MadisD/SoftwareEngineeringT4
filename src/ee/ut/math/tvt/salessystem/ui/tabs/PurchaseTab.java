package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItemsLog;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

//import sun.security.mscapi.KeyStore.MY;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

  private static final Logger log = Logger.getLogger(PurchaseTab.class);

  private final SalesDomainController domainController;

  private JButton newPurchase;

  private JButton submitPurchase;

  private JButton cancelPurchase;
  
  private JButton acceptPayment;
  
  private JButton cancelPayment;

  private PurchaseItemPanel purchasePane;

  private SalesSystemModel model;
  
  private JFrame myFrame;
  
  private	 float sumFinal = 0;
  
  //private JTextField sumField;


  public PurchaseTab(SalesDomainController controller,
      SalesSystemModel model)
  {
    this.domainController = controller;
    this.model = model;
  }


  /**
   * The purchase tab. Consists of the purchase menu, current purchase dialog and
   * shopping cart table.
   */
  public Component draw() {
    JPanel panel = new JPanel();

    // Layout
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.setLayout(new GridBagLayout());

    // Add the purchase menu
    panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

    // Add the main purchase-panel
    purchasePane = new PurchaseItemPanel(model);
    panel.add(purchasePane, getConstraintsForPurchasePanel());

    return panel;
  }




  // The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
  private Component getPurchaseMenuPane() {
    JPanel panel = new JPanel();

    // Initialize layout
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gc = getConstraintsForMenuButtons();

    // Initialize the buttons
    newPurchase = createNewPurchaseButton();
    submitPurchase = createConfirmButton();
    cancelPurchase = createCancelButton();

    // Add the buttons to the panel, using GridBagConstraints we defined above
    panel.add(newPurchase, gc);
    panel.add(submitPurchase, gc);
    panel.add(cancelPurchase, gc);

    return panel;
  }


  // Creates the button "New purchase"
  private JButton createNewPurchaseButton() {
    JButton b = new JButton("New purchase");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newPurchaseButtonClicked();
      }
    });

    return b;
  }

  // Creates the "Confirm" button
  private JButton createConfirmButton() {
    JButton b = new JButton("Confirm");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }


  // Creates the "Cancel" button
  private JButton createCancelButton() {
    JButton b = new JButton("Cancel");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }
  
  // Creates the "Accept" button for Payment window
  private JButton createPaymentAcceptButton() {
	  JButton acceptPayment = new JButton("Accept");
	  acceptPayment.setEnabled(false);
	  acceptPayment.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			  acceptPaymentButtonClicked();
		  }
	  });
	  return acceptPayment;
  }
  
  // Creates the "Cancel" button for Payment window
  private JButton createPaymentCancelButton() {
	  JButton cancelPayment = new JButton("Cancel");
	  cancelPayment.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			  cancelPaymentButtonClicked();
		  }
	  });
	  //accept.setEnabled(false);
	  return cancelPayment;
  }
  
  // Creates the "OK" button for Payment window
  /**private JButton createOkButton() {
	  JButton okButton = new JButton("OK");
	  okButton.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  okButtonClicked();
		  }
	  });
	  //okButton.setEnabled(false);
	  return okButton;
  }*/

  /* === Event handlers for the menu buttons
   *     (get executed when the buttons are clicked)
   */

  /** Event handler for accepted payment event. */
  protected void acceptPaymentButtonClicked() {
	  log.info("Payment accepted");
	  
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  Date date = new Date();
	  List<SoldItem> soldItems = model.getCurrentPurchaseTableModel().getTableRows();
	  
	  
	  SoldItemsLog sold = new SoldItemsLog<>(date, sumFinal, soldItems);
	  
	  myFrame.dispose();
	  endSale();
	  model.getCurrentPurchaseTableModel().clear();
	  
  }
  
  /** Event handler for canceled payment event */
  protected void cancelPaymentButtonClicked() {
	  log.info("Payment canceled");
	  myFrame.dispose();
	  
  }
  
  /** Event handler for payment amount "OK" button
  protected void okButtonClicked() {
	  log.info("Payment Amount entered");
	  
  }*/

  /** Event handler for the <code>new purchase</code> event. */
  protected void newPurchaseButtonClicked() {
    log.info("New sale process started");
    try {
      domainController.startNewPurchase();
      startNewSale();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /**  Event handler for the <code>cancel purchase</code> event. */
  protected void cancelPurchaseButtonClicked() {
    log.info("Sale cancelled");
    try {
      domainController.cancelCurrentPurchase();
      endSale();
      model.getCurrentPurchaseTableModel().clear();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }

  
  /** Event handler for the <code>submit purchase</code> event. */
  protected void submitPurchaseButtonClicked() {
      
      GridBagConstraints gc = new GridBagConstraints();
      gc.fill = GridBagConstraints.HORIZONTAL;
      
      myFrame = new JFrame("Payment");
      myFrame.setLayout(new GridBagLayout());
      
      /** order sum text*/
      gc.gridx = 0;
      gc.gridy = 0;
      myFrame.add(new JLabel("Order Sum:", SwingConstants.LEFT), gc);
      
      /** order sum value*/
      List<SoldItem> stock = model.getCurrentPurchaseTableModel().getTableRows();
      double sum = 0;
      for (SoldItem soldItem : stock) {
    	  sum += soldItem.getQuantity()*soldItem.getPrice();
	}	
      sumFinal = (float)sum;
      gc.gridx = 1;
      gc.gridy = 0;
      myFrame.add(new JLabel(String.valueOf(sum)), gc);
      
      /** payment amount text*/
      gc.gridx = 0;
      gc.gridy = 1;
      myFrame.add(new JLabel("Payment Amount:", SwingConstants.LEFT), gc);
      
      /** payment amount value */
      gc.gridx = 1;
      gc.gridy = 1;
      JTextField paymentAmountField = new JTextField();
      myFrame.add(paymentAmountField, gc);
      //myFrame.add(new JLabel(String.valueOf(paymentAmount)), gc);
      
      /** change text */
      gc.gridx = 0;
      gc.gridy = 2;
      myFrame.add(new JLabel("Change:", SwingConstants.LEFT), gc);
      
      /** change value */
      //gc.gridx = 1;
      //gc.gridy = 2;
      //double changeAmount = (paymentAmount - sum);
      //myFrame.add(new JLabel(String.valueOf(changeAmount)), gc); 
      
      /** Buttons */
      // Accept button
      gc.gridx = 0;
      gc.gridy = 3;
      acceptPayment = createPaymentAcceptButton();
      myFrame.add(acceptPayment, gc);
      
      // Cancel button
      gc.gridx = 1;
      gc.gridy = 3;
      cancelPayment = createPaymentCancelButton();
      myFrame.add(cancelPayment, gc);   
      
      // OK button
      gc.gridx = 2;
      gc.gridy = 1;
      JButton okButton = new JButton("OK");
      okButton.addActionListener( new ActionListener()
      {
    	  public void actionPerformed(ActionEvent e)
    	  {	
    		  try {
    			  String strPaymentAmount = paymentAmountField.getText();
    			  double paymentAmount = Double.parseDouble(strPaymentAmount);
    			  if (paymentAmount<sumFinal) {
    				  JOptionPane.showMessageDialog(new JFrame(), "You don't have enough money!");
    			  } else {
    				  gc.gridx = 1;
    				  gc.gridy = 2;
    				  double changeAmount = (paymentAmount - sumFinal);
    				  myFrame.add(new JLabel(String.valueOf(changeAmount)), gc);
    				  myFrame.revalidate();
    				  myFrame.repaint();
    				  acceptPayment.setEnabled(true);
    			  }
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(new JFrame(), "Do not leave payment empty!");
			}
    		  
    	      
    	  }
      });
      myFrame.add(okButton, gc);
      
      myFrame.pack();
      myFrame.setSize(200,150);
      myFrame.setLocationRelativeTo(null);
      myFrame.setVisible(true);
	    
    log.info("Sale complete");
    try {
      log.debug("Contents of the current basket:\n" + model.getCurrentPurchaseTableModel());
      domainController.submitCurrentPurchase(
          model.getCurrentPurchaseTableModel().getTableRows()
      );
      
      endSale();
      model.getCurrentPurchaseTableModel().clear();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }



  /* === Helper methods that bring the whole purchase-tab to a certain state
   *     when called.
   */

  // switch UI to the state that allows to proceed with the purchase
  private void startNewSale() {
    purchasePane.reset();

    purchasePane.setEnabled(true);
    submitPurchase.setEnabled(true);
    cancelPurchase.setEnabled(true);
    newPurchase.setEnabled(false);
  }

  // switch UI to the state that allows to initiate new purchase
  private void endSale() {
    purchasePane.reset();
    
    cancelPurchase.setEnabled(false);
    submitPurchase.setEnabled(false);
    newPurchase.setEnabled(true);
    purchasePane.setEnabled(false);
  }




  /* === Next methods just create the layout constraints objects that control the
   *     the layout of different elements in the purchase tab. These definitions are
   *     brought out here to separate contents from layout, and keep the methods
   *     that actually create the components shorter and cleaner.
   */

  private GridBagConstraints getConstraintsForPurchaseMenu() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    return gc;
  }


  private GridBagConstraints getConstraintsForPurchasePanel() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 1.0;

    return gc;
  }


  // The constraints that control the layout of the buttons in the purchase menu
  private GridBagConstraints getConstraintsForMenuButtons() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 0;
    gc.anchor = GridBagConstraints.CENTER;
    gc.gridwidth = GridBagConstraints.RELATIVE;

    return gc;
  }

}
