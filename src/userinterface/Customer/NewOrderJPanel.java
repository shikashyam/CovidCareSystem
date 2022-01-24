/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.Customer;

import Business.Customer.Customer;
import Business.EcoSystem;
import Business.Restaurant.Item;
import Business.Restaurant.Menu;
import Business.Restaurant.Restaurant;
import Business.Restaurant.RestaurantDirectory;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.FoodOrder;
import Business.WorkQueue.OrderWorkRequest;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shshyam
 */
public class NewOrderJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Customer customer;
    private DefaultTableModel menuTable;
    private DefaultTableModel cartTable;
    private int index = -1;
    private int row = 0;
    private int column = 0;
    private int quantity = 0;
    private EcoSystem ecosystem;
    private RestaurantDirectory resturantDirectory;
    private List<FoodOrder> itemsWithQuantityList = new ArrayList<FoodOrder>();

    /**
     * Creates new form RequestLabTestJPanel
     */
    public NewOrderJPanel(JPanel userProcessContainer, UserAccount account, EcoSystem ecosystem) {
        initComponents();
        initListners();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.ecosystem = ecosystem;
        customer = (Customer) account;
        resturantDirectory = ecosystem.getRestaurantDirectory();
        menuTable = (DefaultTableModel) tblOrder.getModel();
        cartTable = (DefaultTableModel) tblCart.getModel();
        populateRestaurantsList(resturantDirectory.getRestaurantList());
        if (resturantDirectory.getRestaurantList().size() > 0) {
            index = 0;
            populateMenu();
        }
        txtTotalAmount.setEditable(false);

    }

    public boolean isItemSelected(Item item) {
        int nRow = tblOrder.getRowCount();
        int count1 = 0;
        boolean s = true;

        for (int i = 0; i < nRow; i++) {
            if (null != tblOrder.getValueAt(i, 2)) {
                if ((Boolean) tblOrder.getValueAt(i, 2) == false) {
                    count1++;
                }
            }
        }
        if (count1 == nRow) {
            s = false;
            JOptionPane.showMessageDialog(null, "Please select an item");
        }
        return s;
    }

    public void populateRestaurantsList(ArrayList<Restaurant> restaurantList) {
        for (Restaurant restaurant : restaurantList) {
            restaurantComboBox.addItem(restaurant.getName());
        }
    }

    private void populateMenu() {
        menuTable.setRowCount(0);
        //createAddToCartButton();
        Restaurant restaurant = resturantDirectory.getRestaurantList().get(index);
        Menu menu = restaurant.getMenu();
        List<Item> items = menu.getItemList();
        for (Item item : items) {
            Object[] row = new Object[menuTable.getColumnCount()];
            row[0] = item;
            row[1] = item.getPrice();
            menuTable.addRow(row);
        }
    }

    private void initListners() {
        tblOrder.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int selectedRow = tblOrder.getSelectedRow();
                if (selectedRow >= 0) {
                    Item item = (Item) tblOrder.getValueAt(selectedRow, 0);
                    if (item != null) {
                        String response = JOptionPane.showInputDialog("Please provide Quantity");
                        try {
                            quantity = Integer.parseInt(response);
                        } catch (NumberFormatException e) {
                                     JOptionPane.showMessageDialog(null, "Please enter integer number for quantity. Try again!");
                        }
                        if (quantity != 0) {
                            FoodOrder itemWithQuantity = new FoodOrder(item, quantity);
                            itemsWithQuantityList.add(itemWithQuantity); 
                            populateItemsWithQuantityTable();
                        }

                    }
                }
            }
        });
    }

    private void populateItemsWithQuantityTable() {
        double total = 0.0;
        cartTable.setRowCount(0);
        for (FoodOrder itemWithQuantity : itemsWithQuantityList) {
            Object[] row = new Object[cartTable.getColumnCount()];
            row[0] = itemWithQuantity;
            row[1] = itemWithQuantity.getQuantity();
            row[2] = itemWithQuantity.getItem().getPrice() * itemWithQuantity.getQuantity();
            total += itemWithQuantity.getItem().getPrice() * itemWithQuantity.getQuantity();
            cartTable.addRow(row);
        }
        txtTotalAmount.setText(total + "");
    }

    private boolean createOrder() {

        if (itemsWithQuantityList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please add items to cart, it cannot be empty");
            return false;
        } else {
            OrderWorkRequest orderWorkRequest = new OrderWorkRequest();
            orderWorkRequest.setItemsWithQuatityList(itemsWithQuantityList);
            orderWorkRequest.setMessage(txtMessage.getText());
            if (customer != null) {
                orderWorkRequest.setCustomer(customer);
            } else {
                return false;
            }
            Restaurant restaurant = resturantDirectory.getRestaurantList().get(index);
            if (restaurant != null) {
                orderWorkRequest.setRestaurant(restaurant);
            } else {
                return false;
            }
            orderWorkRequest.setRequestDate(new Date());
            orderWorkRequest.setStatus("Ordered");
            ecosystem.getWorkQueue().addWorkRequest(orderWorkRequest);
            return true;

        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        requestTestJButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtMessage = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        restaurantComboBox = new javax.swing.JComboBox<>();
        enterpriseLabel1 = new javax.swing.JLabel();
        cartScrollPane = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTotalAmount = new javax.swing.JTextField();

        setBackground(new java.awt.Color(182, 216, 242));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        requestTestJButton.setBackground(new java.awt.Color(255, 255, 255));
        requestTestJButton.setFont(new java.awt.Font("Optima", 1, 18)); // NOI18N
        requestTestJButton.setForeground(new java.awt.Color(87, 132, 186));
        requestTestJButton.setText("Confirm Order");
        requestTestJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestTestJButtonActionPerformed(evt);
            }
        });
        add(requestTestJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 560, 190, 50));

        jLabel1.setText("Message");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 520, -1, -1));
        add(txtMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 240, 30));

        jLabel2.setFont(new java.awt.Font("Optima", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Restaurant Menu");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 790, -1));

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Item Name", "Item Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblOrder);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 660, 130));

        restaurantComboBox.setBackground(new java.awt.Color(255, 255, 255));
        restaurantComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restaurantComboBoxActionPerformed(evt);
            }
        });
        add(restaurantComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 510, -1));

        enterpriseLabel1.setFont(new java.awt.Font("Optima", 0, 14)); // NOI18N
        enterpriseLabel1.setText("Select Restaurant  :");
        add(enterpriseLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 130, 20));

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Item Qty", "Item Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cartScrollPane.setViewportView(tblCart);

        add(cartScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 660, 190));

        jLabel3.setFont(new java.awt.Font("Optima", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("My Cart");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 650, -1));

        jLabel4.setText("Total Cart Amount :");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 520, -1, -1));

        jLabel5.setFont(new java.awt.Font("Optima", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("CREATE ORDER");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 740, -1));

        txtTotalAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalAmountActionPerformed(evt);
            }
        });
        add(txtTotalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 510, 150, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void requestTestJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestTestJButtonActionPerformed
        if (createOrder()) {
            JOptionPane.showMessageDialog(null, "Ordered Placed Successfully");
            OrderHistoryJPanel orderStatusJPanel = new OrderHistoryJPanel(userProcessContainer, ecosystem, customer);
            userProcessContainer.add("OrderStatusJPanel", orderStatusJPanel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        }


    }//GEN-LAST:event_requestTestJButtonActionPerformed

    private void restaurantComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restaurantComboBoxActionPerformed
        index = restaurantComboBox.getSelectedIndex();
        //clear of the selected items
        itemsWithQuantityList.clear();
        populateMenu();
        populateItemsWithQuantityTable();
    }//GEN-LAST:event_restaurantComboBoxActionPerformed

    private void txtTotalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalAmountActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane cartScrollPane;
    private javax.swing.JLabel enterpriseLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton requestTestJButton;
    private javax.swing.JComboBox<String> restaurantComboBox;
    private javax.swing.JTable tblCart;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtMessage;
    private javax.swing.JTextField txtTotalAmount;
    // End of variables declaration//GEN-END:variables
}
