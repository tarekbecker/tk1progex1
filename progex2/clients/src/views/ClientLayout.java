package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import clients.Client;

public class ClientLayout extends JFrame {
  private static final long serialVersionUID = 1L;

  private Client ctrl;
  private String windowTitle;
  private JLabel UUID;
  private JComboBox<ComboBoxObject> productSelect;
  private JSpinner quantitySpinner;
  private JButton addProductBtn;
  private JButton checkoutBtn;
  private JTable cart;
  private JLabel infoLabel;

  public ClientLayout(Client ctrl, String title) {
    this.ctrl = ctrl;
    this.windowTitle = title;
    this.UUID = new JLabel(ctrl.getUuid());
    this.productSelect = new JComboBox<ComboBoxObject>(new ComboBoxObject[0]);
    this.quantitySpinner = new JSpinner();
    this.quantitySpinner.setValue(1);
    this.addProductBtn = new JButton("Add to cart");
    this.checkoutBtn = new JButton("Checkout");
    this.cart = new JTable();
    this.infoLabel = new JLabel();

    this.addProductBtn.addActionListener(new AddProductActionListener());
    this.checkoutBtn.addActionListener(new CheckoutActionListener());

    initGui();
  }

  private class AddProductActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      ctrl.onAddProduct(
          ((ComboBoxObject) productSelect.getSelectedItem()).getObject(),
          (Integer) quantitySpinner.getValue());
    }
  }

  private class CheckoutActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      ctrl.onCheckout();
    }
  }

  private void initGui() {
    // Layout
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(2, 5, 2, 5);

    // Display UUID
    c.gridx = 0;
    this.add(new JLabel("UUID:"));
    c.gridx = 1;
    this.add(this.UUID, c);

    // Select Product
    c.gridy = 1;

    c.gridx = 0;
    this.add(new JLabel("Select a Product"), c);

    c.gridx = 1;
    c.weightx = 0.8;
    this.add(productSelect, c);
    c.weightx = 0;

    // Select Quantity
    c.gridy = 2;

    c.gridx = 0;
    this.add(new JLabel("Select a quantity"), c);

    c.gridx = 1;
    c.weightx = 0.8;
    this.add(this.quantitySpinner, c);
    c.weightx = 0;

    // Add to cart
    c.gridy = 3;
    c.gridx = 1;
    this.add(this.addProductBtn, c);

    // Cart summary
    c.gridy = 4;
    c.gridx = 0;
    c.gridwidth = 2;
    this.add(new JLabel("Your shopping cart:"), c);

    c.gridy = 5;
    this.add(new JScrollPane(this.cart), c);

    // Checkout
    c.gridy = 6;
    this.add(this.checkoutBtn, c);

    // Info label
    c.gridy = 7;
    this.add(this.infoLabel, c);
    c.gridwidth = 1;

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle(this.windowTitle);
    this.setSize(780, 680);
  }

  public void removeAllProducts() {
    this.productSelect.removeAllItems();
  }

  public void addProduct(ComboBoxObject item) {
    this.productSelect.addItem(item);
  }

  public void setCartModel(TableModel dataModel) {
    this.cart.setModel(dataModel);
  }

  public void setInfoLabel(String info) {
    this.infoLabel.setText(info);
  }
}