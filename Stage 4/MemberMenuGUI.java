
/**
 *
 * @author 
 */
import javax.swing.table.DefaultTableModel;
import java.util.Map;
public class MemberMenuGUI extends javax.swing.JFrame {
    
    //bring in information and call method from other classes for the program.
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MemberMenuGUI.class.getName());
    private CreditCardManager ccManager;
    private MembershipManager membershipManager;
    private Facility facility;
    private Member loggedMember;
    private MemberManager memberManager;
    private Login myObject;
    private java.util.LinkedHashMap<Item, Integer> cart = new java.util.LinkedHashMap<>();
    
    /**
     * Creates new form MemberMenuGUI
     */
    public MemberMenuGUI() {
        initComponents();
        
    }
    
    //constructor to refresh information, update and a welcome message for the user.
    public MemberMenuGUI(Member member, MemberManager memberManager,MembershipManager membershipManager,CreditCardManager ccManager,Facility facility)
    {
        this();
        this.loggedMember = member;
        this.memberManager = memberManager;
        this.membershipManager = membershipManager;
        this.ccManager = ccManager;
        this.facility = facility;
        lblWelcome.setText("Welcome " + member.getName() + " to the Member Portal");
        loadMemberInfo(); //bring in member information.
        initMembershipComboBox();
        refreshMembershipDisplay(); //refresh for refresh buttons. 
        loadSavedCardIntoFields(); //bring in information for saved credit card information.
        loadShopTable(); //populate the shop table  
        refreshCartDisplay();
    }
    
    private void loadMemberInfo() {
    lblInfoName.setText(loggedMember.getName());
    lblInfoUsername.setText(loggedMember.getUsername());

    //get current membership for the member to display. 
    if (loggedMember.getMembership() != null) {
        lblInfoMembership.setText(loggedMember.getMembership().getType());
    } else {
        lblInfoMembership.setText("(None)");
    }

        CreditCard savedCard = ccManager.findCard(loggedMember);
        if (savedCard != null) {
            lblInfoCard.setText(savedCard.toString());
        } else {
            lblInfoCard.setText("(None)");
        }
    }
    
    //Method to display and add membership status of member. 
    private void initMembershipComboBox() {
    cmbMemberships.removeAllItems();

    if (membershipManager == null) return;

    for (Membership m : membershipManager.getMemberships()) {
        cmbMemberships.addItem(m.getType());
    }
}
    //Method to select the membership with fail conditions. 
    private Membership getSelectedMembership() {
        if (membershipManager == null) return null;

        //return null to avoid error.
        String selectedType = (String) cmbMemberships.getSelectedItem();
        if (selectedType == null) return null;

        for (Membership m : membershipManager.getMemberships()) {
            if (m.getType().equalsIgnoreCase(selectedType)) {
                return m;
            }
        }
        return null;
    }
    
    //refresh the display information, returns None if the member does not have a current membership for errors.
    private void refreshMembershipDisplay() {
        // show current membership
        if (loggedMember != null && loggedMember.getMembership() != null) {
            lblCurrentMembership.setText("Current Membership: " + loggedMember.getMembership().getType());
        } else {
            lblCurrentMembership.setText("Current Membership: (None)");
        }

        // show selected membership price
        Membership selected = getSelectedMembership();
        if (selected != null) {
            lblMembershipPrice.setText("$" + String.format("%.2f", selected.getCost()));
        } else {
            lblMembershipPrice.setText("$0.00");
        }
    }
    
    //Load in the cards information saved.
    private void loadSavedCardIntoFields() {
    if (loggedMember == null || ccManager == null) return;

    CreditCard saved = ccManager.findCard(loggedMember);
    if (saved == null) {
        lblCardStatus.setText("Saved card: (None)");
        txtCardNumber.setText("");
        txtCardHolder.setText("");
        txtExpiry.setText("");
        return;
    }
    
    //methods to call the getters. 
    txtCardNumber.setText(saved.getCardNumber());
    txtCardHolder.setText(saved.getCardHolderName());
    txtExpiry.setText(saved.getExpiryDate());

    lblCardStatus.setText("Saved card: " + saved.toString()); // masked display
}
    
    private void saveMembersToFile() {
    if (memberManager == null) return;

    java.util.ArrayList<String> lines = new java.util.ArrayList<>();

    for (Member m : memberManager.getMembers()) {
        String membership = "Basic"; // default
        if (m.getMembership() != null) {
            membership = m.getMembership().getType();
        }

        lines.add(
            m.getName() + "," +
            m.getUsername() + "," +
            m.getPassword() + "," +
            membership
        );
    }

    memberManager.saveMemberFile("members.txt", lines);
}

    //double check to make sure that the card information is put correctly. 
private boolean isValidCardInput(String number, String holder, String expiry) {
    if (number == null || holder == null || expiry == null) return false;

    //trim methods to avoid whitespaces. 
    number = number.trim();
    holder = holder.trim();
    expiry = expiry.trim();

    // 16 digits
    if (number.length() != 16) return false;
    for (int i = 0; i < number.length(); i++) {
        if (!Character.isDigit(number.charAt(i))) return false;
    }

    //make sure that the fields have something. 
    if (holder.isEmpty()) return false;

    // MM/YY
    if (!expiry.matches("\\d{2}/\\d{2}")) return false;

    //finally return true if all the other methods do not display an error or fault.
    return true;
}

    //make error catch if program does not have a current facility.
    private Inventory getShopInventory() {
        if (facility == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Facility is null (not passed into MemberMenuGUI).");
        return null;
    }
     
    // methods for error catching to find an area or facility called "Shop" 
    Area shop = facility.findArea("Shop");
    if (shop == null) {
        StringBuilder sb = new StringBuilder("Shop area not found.\nAreas loaded:\n");
        for (Area a : facility.getAreas()) {
            sb.append("- ").append(a.getName()).append("\n");
        }
        javax.swing.JOptionPane.showMessageDialog(this, sb.toString());
        return null;
    }
    
    //return the inventory currently in shop. 
    return shop.getInventory();
}
    //display the shop table and populate. 
    private void loadShopTable() {
        
    Inventory shopInv = getShopInventory();
    if (shopInv == null) return;
    
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    Inventory shopInventory = facility.findArea("Shop").getInventory();
    for (Item item : shopInventory.getItems()) { // :contentReference[oaicite:5]{index=5}
        model.addRow(new Object[]{
            item.getId(),
            item.getName(),
            item.getQuantity(),
            String.format("%.2f", item.getPrice())
        });
    }
}
    //load the avaliable classes from the staff management menu. 
    private void loadAvailableClassesTable() {
    if (facility == null) return;

    facility.loadClasses("classes.txt"); // same pattern staff uses
    DefaultTableModel model = (DefaultTableModel) tblAvailableClasses.getModel();
    model.setRowCount(0);

    for (Area a : facility.getAreas()) {
        if (!a.hasClasses()) continue;

        for (Class c : a.getClasses()) {
            String status;
            if (!c.isScheduled()) status = "Not scheduled";
            else if (c.isFull()) status = "Full";
            else status = "Open";

            model.addRow(new Object[]{
                c.getClassName(),
                c.getStartTime(),
                c.getEndTime(),
                c.getMaxCapacity(),
                c.spotsLeft(),
                a.getName(),
                status
            });
        }
    }
}
    //get the classes currently in the table. 
    private Class getSelectedClassFromTable() {
    int row = tblAvailableClasses.getSelectedRow();
    if (row < 0) return null;

    //get the information displayed in the table from staff memebrship menu. 
    String className = tblAvailableClasses.getValueAt(row, 0).toString();
    String start     = tblAvailableClasses.getValueAt(row, 1).toString();
    String end       = tblAvailableClasses.getValueAt(row, 2).toString();
    String location  = tblAvailableClasses.getValueAt(row, 5).toString();

    Area area = facility.findArea(location);
    if (area == null || !area.hasClasses()) return null;

    for (Class c : area.getClasses()) {
        if (c.getClassName().equalsIgnoreCase(className)
                && String.valueOf(c.getStartTime()).equals(start)
                && String.valueOf(c.getEndTime()).equals(end)) {
            return c;
        }
    }
    return null;
}
    
    //refresh the current cart that is being displayed in the table if any more
    //items are added.
    private void refreshCartDisplay() {
    double total = 0.0;
    StringBuilder sb = new StringBuilder();

    for (Map.Entry<Item, Integer> e : cart.entrySet()) {
        Item item = e.getKey();
        int qty = e.getValue();
        double line = qty * item.getPrice();
        total += line;

        sb.append(item.getId()).append(" - ")
          .append(item.getName())
          .append(" x").append(qty)
          .append("  ($").append(String.format("%.2f", line)).append(")")
          .append("\n");
    }

    txtCart.setText(sb.toString());
    lblCartTotal.setText("Total: $" + String.format("%.2f", total));
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLogout = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblMName = new javax.swing.JLabel();
        lblMUsername = new javax.swing.JLabel();
        lblMMembership = new javax.swing.JLabel();
        lblMPassword = new javax.swing.JLabel();
        lblInfoName = new javax.swing.JLabel();
        lblInfoUsername = new java.awt.Label();
        lblInfoMembership = new javax.swing.JLabel();
        lblInfoCard = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cmbMemberships = new javax.swing.JComboBox<>();
        lblMembershipPrice = new javax.swing.JLabel();
        btnPurchaseMembership = new javax.swing.JButton();
        lblCurrentMembership = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtCardNumber = new javax.swing.JTextField();
        txtCardHolder = new javax.swing.JTextField();
        txtExpiry = new javax.swing.JTextField();
        btnClearCard = new java.awt.Button();
        btnSaveCard1 = new java.awt.Button();
        lblCardStatus = new java.awt.Label();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        jPanel5 = new javax.swing.JPanel();
        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtShopQty = new javax.swing.JTextField();
        btnRefreshShop = new java.awt.Button();
        btnAddToCart1 = new java.awt.Button();
        scrollPane2 = new java.awt.ScrollPane();
        txtCart = new java.awt.TextArea();
        lblCartTotal = new java.awt.Label();
        btnClearCart = new java.awt.Button();
        btnCheckout = new java.awt.Button();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAvailableClasses = new javax.swing.JTable();
        btnRefreshClasses = new java.awt.Button();
        btnAttendClass = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Member Menu");

        btnLogout.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dashboard", jPanel1);

        lblMName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMName.setText("Name:");

        lblMUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMUsername.setText("Username:");

        lblMMembership.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMMembership.setText("Membership:");

        lblMPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMPassword.setText("My Saved Cards:");

        lblInfoName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfoName.setText("lblInfoName");
        lblInfoName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblInfoUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfoUsername.setText("lblInfoUsername");

        lblInfoMembership.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfoMembership.setText("lblInfoMembership");
        lblInfoMembership.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblInfoCard.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfoCard.setText("lblInfoCard");
        lblInfoCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMName, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                        .addGap(521, 521, 521))
                    .addComponent(lblMMembership, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInfoCard, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInfoName, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInfoUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInfoMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblMName, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(lblInfoName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(lblInfoUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(lblMMembership, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfoMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfoCard, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("My Info", jPanel2);

        cmbMemberships.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbMemberships.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMemberships.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMembershipsActionPerformed(evt);
            }
        });

        lblMembershipPrice.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMembershipPrice.setText("Price:");

        btnPurchaseMembership.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPurchaseMembership.setText("Purchase");
        btnPurchaseMembership.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPurchaseMembershipActionPerformed(evt);
            }
        });

        lblCurrentMembership.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCurrentMembership.setText("jLabel1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(lblCurrentMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbMemberships, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMembershipPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPurchaseMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(cmbMemberships, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCurrentMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMembershipPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPurchaseMembership, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );

        jTabbedPane1.addTab("Membership", jPanel3);

        txtCardNumber.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCardNumber.setText("jTextField1");
        txtCardNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCardNumberActionPerformed(evt);
            }
        });

        txtCardHolder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCardHolder.setText("jTextField1");

        txtExpiry.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtExpiry.setText("jTextField1");

        btnClearCard.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnClearCard.setLabel("Clear Fields");
        btnClearCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearCardActionPerformed(evt);
            }
        });

        btnSaveCard1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSaveCard1.setLabel("Save / Update Card");
        btnSaveCard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveCard1ActionPerformed(evt);
            }
        });

        lblCardStatus.setName(""); // NOI18N
        lblCardStatus.setText("label1");

        label1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        label1.setText("Enter 16 digit Credit Card Number");

        label2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        label2.setText("Enter Name on Card");

        label3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        label3.setText("Enter Expiration Date (MM/YY)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(label3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtExpiry)
                            .addComponent(txtCardHolder)
                            .addComponent(txtCardNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                        .addComponent(label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClearCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCardStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addComponent(btnSaveCard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(452, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(txtCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCardHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblCardStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnClearCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(229, Short.MAX_VALUE)
                    .addComponent(btnSaveCard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Saved Payment", jPanel4);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Quantity", "Price"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        scrollPane1.add(jScrollPane1);

        txtShopQty.setText("1");

        btnRefreshShop.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnRefreshShop.setLabel("Refresh");
        btnRefreshShop.setName(""); // NOI18N
        btnRefreshShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshShopActionPerformed(evt);
            }
        });

        btnAddToCart1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAddToCart1.setLabel("Add to Cart");
        btnAddToCart1.setName(""); // NOI18N
        btnAddToCart1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCart1ActionPerformed(evt);
            }
        });

        txtCart.setEditable(false);
        scrollPane2.add(txtCart);

        lblCartTotal.setText("Total:");

        btnClearCart.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnClearCart.setLabel("Clear Cart");
        btnClearCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearCartActionPerformed(evt);
            }
        });

        btnCheckout.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnCheckout.setLabel("Checkout");
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAddToCart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtShopQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefreshShop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(btnClearCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCartTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(btnCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(145, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtShopQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(btnAddToCart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(btnRefreshShop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblCartTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClearCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Shop", jPanel5);

        tblAvailableClasses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Class", "Start", "End", "Capacity", "Spots Left", "Location", "Status"
            }
        ));
        jScrollPane2.setViewportView(tblAvailableClasses);

        btnRefreshClasses.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnRefreshClasses.setLabel("Refresh");
        btnRefreshClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshClassesActionPerformed(evt);
            }
        });

        btnAttendClass.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAttendClass.setLabel("Attend");
        btnAttendClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttendClassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(btnRefreshClasses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAttendClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRefreshClasses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAttendClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Classes", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(379, 379, 379))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        this.dispose();
        new LoginGUI().setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void cmbMembershipsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMembershipsActionPerformed
        refreshMembershipDisplay();    
    }//GEN-LAST:event_cmbMembershipsActionPerformed

    private void btnPurchaseMembershipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPurchaseMembershipActionPerformed
        // Tif (loggedMember == null) return;

    Membership selected = getSelectedMembership();
    if (selected == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select a membership.");
        return;
    }

    int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Purchase " + selected.getType() + " for $" + String.format("%.2f", selected.getCost()) + "?",
            "Confirm Purchase",
            javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

    // Assign membership
    loggedMember.setMembership(selected);
    saveMembersToFile();

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Membership purchased! You are now a " + selected.getType() + " member."
    );

    // Update labels on this tab
    refreshMembershipDisplay();
    loadMemberInfo();

    }//GEN-LAST:event_btnPurchaseMembershipActionPerformed

    private void btnSaveCard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveCard1ActionPerformed

        if (loggedMember == null || ccManager == null) return;

        String number = txtCardNumber.getText();
        String holder = txtCardHolder.getText();
        String expiry = txtExpiry.getText();

        if (!isValidCardInput(number, holder, expiry)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Invalid card info.\n\nCard number must be 16 digits.\nExpiry must be MM/YY.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        CreditCard newCard = new CreditCard(
            number.trim(),
            holder.trim(),
            expiry.trim(),
            loggedMember.getUsername()
        );

        // Save (or update, depending on how your CreditCardManager handles duplicates)
        ccManager.removeCard(loggedMember);
        ccManager.addCard(loggedMember, newCard);

        javax.swing.JOptionPane.showMessageDialog(this, "Card saved successfully!");

        loadSavedCardIntoFields();
        loadMemberInfo(); // updates your My Info tab too
    }//GEN-LAST:event_btnSaveCard1ActionPerformed

    private void btnClearCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCardActionPerformed
        txtCardNumber.setText("");
        txtCardHolder.setText("");
        txtExpiry.setText("");
    }//GEN-LAST:event_btnClearCardActionPerformed

    private void txtCardNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCardNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCardNumberActionPerformed

    private void btnRefreshShopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshShopActionPerformed
        loadShopTable();
    }//GEN-LAST:event_btnRefreshShopActionPerformed

    private void btnAddToCart1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCart1ActionPerformed
            int row = jTable1.getSelectedRow();
    if (row < 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Select an item first.");
        return;
    }

    int qtyWanted;
    try {
        qtyWanted = Integer.parseInt(txtShopQty.getText().trim());
    } catch (Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Enter a valid quantity.");
        return;
    }

    if (qtyWanted <= 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Quantity must be at least 1.");
        return;
    }

    int itemId = Integer.parseInt(jTable1.getValueAt(row, 0).toString());

    Inventory shopInv = getShopInventory();
    if (shopInv == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Shop inventory not found.");
        return;
    }

    Item invItem = shopInv.findItemByID(itemId); // :contentReference[oaicite:4]{index=4}
    if (invItem == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Item not found.");
        return;
    }

    if (qtyWanted > invItem.getQuantity()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Not enough stock.");
        return;
    }

    int current = cart.getOrDefault(invItem, 0);
    cart.put(invItem, current + qtyWanted);

    refreshCartDisplay();
    }//GEN-LAST:event_btnAddToCart1ActionPerformed

    private void btnClearCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCartActionPerformed
     cart.clear();
    refreshCartDisplay();
    }//GEN-LAST:event_btnClearCartActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
         if (cart.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Your cart is empty.");
        return;
    }

    Inventory shopInv = getShopInventory();
    if (shopInv == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Shop inventory not found.");
        return;
    }

    // Verify stock still available
    for (Map.Entry<Item, Integer> e : cart.entrySet()) {
        Item invItem = shopInv.findItemByID(e.getKey().getId());
        if (invItem == null || e.getValue() > invItem.getQuantity()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Checkout failed: not enough stock for " + e.getKey().getName());
            return;
        }
    }

    int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Confirm checkout?",
            "Checkout",
            javax.swing.JOptionPane.YES_NO_OPTION
    );
    if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

    // Complete purchase (reduce inventory)
    for (Map.Entry<Item, Integer> e : cart.entrySet()) {
        shopInv.purchaseItem(e.getKey().getId(), e.getValue()); 
    }

    cart.clear();
    refreshCartDisplay();
    loadShopTable();

    javax.swing.JOptionPane.showMessageDialog(this, "Purchase successful!");
    }//GEN-LAST:event_btnCheckoutActionPerformed

    private void btnRefreshClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshClassesActionPerformed
        loadAvailableClassesTable();
    }//GEN-LAST:event_btnRefreshClassesActionPerformed

    private void btnAttendClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttendClassActionPerformed

    if (loggedMember == null || facility == null) return;

    Class c = getSelectedClassFromTable();
    if (c == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Select a class first.");
        return;
    }

    // These checks match your Class.register rules
    if (!c.isScheduled()) {
        javax.swing.JOptionPane.showMessageDialog(this, "That class is not scheduled yet.");
        return;
    }
    if (c.isFull()) {
        javax.swing.JOptionPane.showMessageDialog(this, "That class is full.");
        return;
    }

    boolean ok = c.register(loggedMember); // uses your existing logic
    if (!ok) {
        javax.swing.JOptionPane.showMessageDialog(this, "Could not attend (maybe already enrolled).");
        return;
    }

    javax.swing.JOptionPane.showMessageDialog(this, "You are registered for: " + c.getClassName());
    loadAvailableClassesTable();
    }//GEN-LAST:event_btnAttendClassActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MemberMenuGUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btnAddToCart1;
    private java.awt.Button btnAttendClass;
    private java.awt.Button btnCheckout;
    private java.awt.Button btnClearCard;
    private java.awt.Button btnClearCart;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPurchaseMembership;
    private java.awt.Button btnRefreshClasses;
    private java.awt.Button btnRefreshShop;
    private java.awt.Button btnSaveCard1;
    private javax.swing.JComboBox<String> cmbMemberships;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label lblCardStatus;
    private java.awt.Label lblCartTotal;
    private javax.swing.JLabel lblCurrentMembership;
    private javax.swing.JLabel lblInfoCard;
    private javax.swing.JLabel lblInfoMembership;
    private javax.swing.JLabel lblInfoName;
    private java.awt.Label lblInfoUsername;
    private javax.swing.JLabel lblMMembership;
    private javax.swing.JLabel lblMName;
    private javax.swing.JLabel lblMPassword;
    private javax.swing.JLabel lblMUsername;
    private javax.swing.JLabel lblMembershipPrice;
    private javax.swing.JLabel lblWelcome;
    private java.awt.ScrollPane scrollPane1;
    private java.awt.ScrollPane scrollPane2;
    private javax.swing.JTable tblAvailableClasses;
    private javax.swing.JTextField txtCardHolder;
    private javax.swing.JTextField txtCardNumber;
    private java.awt.TextArea txtCart;
    private javax.swing.JTextField txtExpiry;
    private javax.swing.JTextField txtShopQty;
    // End of variables declaration//GEN-END:variables
}
