package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;


public class StockTab {

  private JButton addItem;
  private JButton confirm;
  private JButton cancel;
  private JFrame frame;
  private JPanel panel;
  
  /*
   * Product info
   */
  private JTextField id;
  private JTextField name;
  private JTextField price;
  private JTextField description;
  private JTextField quantity;
  

  private SalesSystemModel model;

  public StockTab(SalesSystemModel model) {
    this.model = model;
  }

  // warehouse stock tab - consists of a menu and a table
  public Component draw() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();
    panel.setLayout(gb);

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    panel.add(drawStockMenuPane(), gc);

    gc.weighty = 1.0;
    gc.fill = GridBagConstraints.BOTH;
    panel.add(drawStockMainPane(), gc);
    return panel;
  }

  // warehouse menu
  private Component drawStockMenuPane() {
    JPanel panel = new JPanel();

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();

    panel.setLayout(gb);

    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 0;

    addItem = new JButton("Add");
    addItem.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			drawAddWindow();
			
			
		}
	});
    
    gc.gridwidth = GridBagConstraints.RELATIVE;
    gc.weightx = 1.0;
    panel.add(addItem, gc);

    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    return panel;
  }


  // table of the wareshouse stock
  private Component drawStockMainPane() {
    JPanel panel = new JPanel();

    JTable table = new JTable(model.getWarehouseTableModel());
    JTableHeader header = table.getTableHeader();
    header.setReorderingAllowed(false);
    JScrollPane scrollPane = new JScrollPane(table);

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();
    gc.fill = GridBagConstraints.BOTH;
    gc.weightx = 1.0;
    gc.weighty = 1.0;

    panel.setLayout(gb);
    panel.add(scrollPane, gc);

    panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
    return panel;
  }
  
  private void drawAddWindow(){
	  JFrame popup = new JFrame();
	  frame = new JFrame();
	  
	  //Will Enable Add button again if add stock item window is closed from [X]
	  frame.addWindowListener(new WindowAdapter() {
		  @Override
		public void windowClosing(WindowEvent e) {
			addItem.setEnabled(true);
			super.windowClosing(e);
		}
	});
	  
	  panel = new JPanel();
	  panel.setLayout(new GridLayout(6,2));
	  frame.setTitle("New item");
	  
	  //Buttons
	  confirm = new JButton("Confirm");
	  confirm.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (validInput()) {
				boolean idVerified = checkId();
				boolean priceVerified = checkPrice();
				boolean quantityVerified = checkQuantity();
				
				if (idVerified == false) {
					JOptionPane.showMessageDialog(popup, "ID must be a long number!");
				} else {
					if (priceVerified == false) {
						JOptionPane.showMessageDialog(popup, "Price must be a double or an integer!");
					} else {
						if (quantityVerified == false) {
							JOptionPane.showMessageDialog(popup, "Quantity must be an integer!");
						} else {
							StockItem newItem = buildItem();	
							try {
								Session session = HibernateUtil.currentSession();
								session.beginTransaction();
								session.persist(newItem);
								session.getTransaction().commit();
							} catch (NonUniqueObjectException u) {
								StockItem item = model.getWarehouseTableModel().getItemById(newItem.getId());
								item.setQuantity(item.getQuantity() + newItem.getQuantity());
								HibernateUtil.closeSession();
								Session session = HibernateUtil.currentSession();
								session.beginTransaction();
								session.merge(item);
								session.getTransaction().commit();

							}
							model.getWarehouseTableModel().addItem(newItem);
							frame.dispose();
							addItem.setEnabled(true);
						}
					}

				}
				
			}
			else{
				JOptionPane.showMessageDialog(popup, "You must fill all the fields!");
				
			}
		}
	});
	  
	  cancel = new JButton("Cancel");
	  
	  cancel.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			addItem.setEnabled(true);
		}
	});
	  
	  //Stock item buttons and text fields
	  panel.add(new JLabel("ID:"));
	  id = new JTextField();
	  panel.add(id);
	  
	  panel.add(new JLabel("Name:"));
	  name = new JTextField();
	  panel.add(name);
	  
	  panel.add(new JLabel("Price:"));
	  price = new JTextField();
	  panel.add(price);
	  
	  panel.add(new JLabel("Description:"));
	  description = new JTextField();
	  panel.add(description);
	  
	  panel.add(new JLabel("Quantity:"));
	  quantity = new JTextField();
	  panel.add(quantity);
	  
	  panel.add(confirm);
	  panel.add(cancel);
	  frame.add(panel);
	  
	  //Window properties
	  int width = 300;
	  int height = 200;
	  frame.setSize(width, height);
	  Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	  frame.setLocation((screen.width - width) / 2, (screen.height - height) / 2);
	  
	  
	  frame.setResizable(false);
	  addItem.setEnabled(false);
	  frame.setVisible(true);
	  
  }
  
  private boolean checkId(){
	  try {
		  long idStock = Long.valueOf(id.getText()).longValue();
	} catch (Exception e) {
		return false;
	}
	  return true;
  }
  
  private boolean checkPrice(){
	  try {
		  double priceStock = Double.valueOf(price.getText()).doubleValue();
	} catch (Exception e) {
		return false;
	}
	  return true;
  }
  
  private boolean checkQuantity(){
	  try {
		  int quantityStock = Integer.valueOf(quantity.getText()).intValue();
	} catch (Exception e) {
		return false;
	}
	  return true;
  }
  
  private boolean validInput(){
	  Component[] components = panel.getComponents();
	  
	  for (Component component : components) {
		if (component.getClass() == id.getClass()) {
			JTextField temp = (JTextField) component;
			String fieldText = temp.getText();
			if (fieldText.length() == 0) {
				return false;
			}
		}
	}
	  
	  return true;
  }

  
  private StockItem buildItem(){
	  long idStock;
	  String nameStock;
	  double priceStock;
	  String descriptionStock = "";
	  int quantityStock;
		
		
	  idStock = Long.valueOf(id.getText()).longValue();
	  nameStock = name.getText();
	  priceStock = Double.valueOf(price.getText()).doubleValue();
	  descriptionStock = description.getText();
	  quantityStock = Integer.valueOf(quantity.getText()).intValue();
	  
	  return new StockItem(idStock, nameStock, descriptionStock, priceStock, quantityStock);
		
  }
}
