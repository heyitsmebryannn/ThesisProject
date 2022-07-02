package com.POS;
import com.productInfo.Products;
import com.thesis.ConnectDatabase;
import com.thesis.MainMenuForm;
import com.userInfo.AllUsers;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class POSForm extends JFrame implements MouseListener, KeyListener {
    JPanel northPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    JPanel posPanel;
    JPanel itemPanel;
    JPanel itemNorthPanel;
    JPanel itemCenterPanel;
    JPanel orderPanel;
    JPanel bottomLeftPanel;
    JPanel bottomCenterPanel;
    JPanel bottomRightPanel;
    JPanel userPanel;
    JTextField txtSubTotal;
    JTextField txtVAT;
    JTextField txtGrandTotal;
    JTextField txtReferenceNumber;
    JTextField txtCash;
    JTextField txtChange;
    JTextField txtProductID;
    JTextField txtQuantity;
    JTextField txtSearch;
    JLabel lblBanner;
    JLabel lblID;
    JLabel lblSubTotal;
    JLabel lblVAT;

    JLabel lblGrandTotal;
    JLabel lblCash;
    JLabel lblReference;
    JLabel lblChange;
    JLabel lblOrder;
    JLabel lblPaymentMethod;
    JLabel lblQuantity;
    JLabel lblUser;
    JLabel lblUserName;
    JLabel lblPosition;
    JLabel lblPositionName;
    JLabel lblSearch;
    JTable tblOrder;
    JTable tblProducts;
    JScrollPane scrollPane;
    ImageIcon orderIcon;
    JComboBox<String> cmbPayment;
    JButton btnVoidProduct;
    JButton btnUpdateProduct;
    JButton btnPay;
    JButton btnExit;
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    Font borderFont = new Font("Poppins SemiBold",Font.PLAIN,15);
    Font fontLabel = new Font("Poppins SemiBold",Font.PLAIN,15);
    String[] columnName={"ID","Name", "Price", "Qty","Amnt","Wrnty"};
    DefaultTableModel tableModel = new DefaultTableModel(null,columnName);
    String[] columnNames ={"Product ID","Name","Brand","Type","Qty","Qty Type","Price","Amount","Warranty","Date Added"};
    ImageIcon lblIcon;
    JDialog jDialog;
    private final DefaultTableModel tableModels = new DefaultTableModel(null,columnNames);
    public POSForm() {
        super("Point-Of-Sale System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1366,768);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(1366,600));
        setMaximumSize(new Dimension(1920,1080));
        getContentPane().setBackground(new Color(202,170,86));
        setIconImage(AllUsers.image.getImage());
        setUndecorated(true);

        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(this.getContentPane().getBackground());
        northPanel.setPreferredSize(new Dimension(dimension.width,150));

        String logo = new File("images/banner-1.jpg").getAbsolutePath();
        lblBanner = new JLabel();
        lblBanner.setPreferredSize(new Dimension(northPanel.getPreferredSize().width,northPanel.getPreferredSize().height));
        ImageIcon newImage = new ImageIcon(new ImageIcon(logo).getImage().getScaledInstance(lblBanner.getPreferredSize().width,lblBanner.getPreferredSize().height, Image.SCALE_DEFAULT));
        lblBanner.setIcon(newImage);
        lblBanner.setVerticalAlignment(JLabel.CENTER);
        lblBanner.setHorizontalAlignment(JLabel.CENTER);
        lblBanner.setBackground(northPanel.getBackground());
        lblBanner.setOpaque(true);
        northPanel.add(lblBanner);

        centerPanel = new JPanel();
        centerPanel.setBackground(this.getContentPane().getBackground());
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension((int) dimension.getWidth(), (int) dimension.getHeight() - 200));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED), BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED)));
        centerPanel.setFont(fontLabel);
        Dimension center = centerPanel.getPreferredSize();

        posPanel = new JPanel();
        posPanel.setLayout(new BorderLayout());
        posPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Point-Of-Sale", TitledBorder.LEFT, TitledBorder.TOP, borderFont, Color.BLACK));
        posPanel.setPreferredSize(new Dimension(600, center.height));
        posPanel.setBackground(this.getContentPane().getBackground());
        centerPanel.add(posPanel, BorderLayout.CENTER);
        Dimension posDim = posPanel.getPreferredSize();

        orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        orderPanel.setPreferredSize(new Dimension(200, posDim.height - 210));
        posPanel.add(orderPanel,BorderLayout.WEST);

        lblOrder = new JLabel();
        lblOrder.setOpaque(true);

        lblOrder.setPreferredSize(new Dimension(orderPanel.getPreferredSize().width, 220));
        lblOrder.setHorizontalAlignment(JLabel.CENTER);
        lblOrder.setVerticalAlignment(JLabel.CENTER);
        orderIcon = new ImageIcon(new ImageIcon("images/white.png").getImage().getScaledInstance(lblOrder.getPreferredSize().width, lblOrder.getPreferredSize().height, Image.SCALE_SMOOTH));
        lblOrder.setIcon(orderIcon);
        orderPanel.add(lblOrder,BorderLayout.NORTH);

        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(4,1));
        userPanel.setOpaque(true);
        userPanel.setPreferredSize(new Dimension(orderPanel.getPreferredSize().width - 20, 150));
        userPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false), "User", TitledBorder.LEFT, TitledBorder.TOP, borderFont, Color.BLACK));
        orderPanel.add(userPanel,BorderLayout.SOUTH);

        lblUser = new JLabel("User ID:");
        lblUser.setOpaque(true);
        lblUser.setFont(fontLabel);
        lblUser.setHorizontalAlignment(JLabel.LEFT);
        lblUser.setPreferredSize(new Dimension(orderPanel.getPreferredSize().width - 60, 20));
        userPanel.add(lblUser);

        lblUserName = new JLabel(AllUsers.userID);
        lblUserName.setOpaque(true);
        lblUserName.setFont(fontLabel);
        lblUserName.setHorizontalAlignment(JLabel.CENTER);
        lblUserName.setPreferredSize(new Dimension(orderPanel.getPreferredSize().width - 60, 20));
        userPanel.add(lblUserName);

        lblPosition = new JLabel("Position:");
        lblPosition.setOpaque(true);
        lblPosition.setFont(fontLabel);
        lblPosition.setHorizontalAlignment(JLabel.LEFT);
        lblPosition.setPreferredSize(new Dimension(orderPanel.getPreferredSize().width - 60, 20));
        userPanel.add(lblPosition);

        lblPositionName = new JLabel(AllUsers.position);
        lblPositionName.setOpaque(true);
        lblPositionName.setFont(fontLabel);
        lblPositionName.setHorizontalAlignment(JLabel.CENTER);
        lblPositionName.setPreferredSize(new Dimension(orderPanel.getPreferredSize().width - 60, 20));
        userPanel.add(lblPositionName);

        JPanel posMidPanel = new JPanel();
        posMidPanel.setLayout(new BorderLayout());
        posMidPanel.setBackground(centerPanel.getBackground());
        posMidPanel.setPreferredSize(new Dimension(300,posPanel.getPreferredSize().height));
        posPanel.add(posMidPanel,BorderLayout.CENTER);

        JPanel panelNorth = new JPanel();
        panelNorth.setBackground(centerPanel.getBackground());
        panelNorth.setPreferredSize(new Dimension(posMidPanel.getPreferredSize().width,40));
        panelNorth.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        posMidPanel.add(panelNorth,BorderLayout.NORTH);

        lblID = new JLabel("Scan ID: ");
        lblID.setFont(new Font("Poppins SemiBold",Font.PLAIN,12));
        lblID.setHorizontalAlignment(JLabel.LEFT);
        lblID.setPreferredSize(new Dimension(55, 20));
        panelNorth.add(lblID,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        txtProductID = new JTextField();
        txtProductID.setFont(fontLabel);
        txtProductID.setToolTipText("Enter Product ID or Scan ID");
        txtProductID.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtProductID.setPreferredSize(new Dimension(150, 30));
        txtProductID.requestFocus();
        txtProductID.grabFocus();
        txtProductID.addKeyListener(this);
        panelNorth.add(txtProductID,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;

        JPanel panel = new JPanel();
        panel.setBackground(centerPanel.getBackground());
        panel.setPreferredSize(new Dimension(20,30));
        panelNorth.add(panel,gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;

        lblQuantity = new JLabel("Qty: ");
        lblQuantity.setFont(new Font("Poppins SemiBold",Font.PLAIN,12));
        lblQuantity.setHorizontalAlignment(JLabel.RIGHT);
        lblQuantity.setPreferredSize(new Dimension(30, 20));
        panelNorth.add(lblQuantity,gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;

        txtQuantity = new JTextField("1.0");
        txtQuantity.setFont(fontLabel);
        txtQuantity.setToolTipText("Enter Quantity");
        txtQuantity.setHorizontalAlignment(JTextField.CENTER);
        txtQuantity.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtQuantity.setPreferredSize(new Dimension(50, 30));
        txtQuantity.setEditable(false);
        txtQuantity.addKeyListener(this);
        txtQuantity.addMouseListener(this);
        panelNorth.add(txtQuantity,gbc);

        tblOrder = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblOrder.getColumnModel().getColumnCount(); i++) {
            tblOrder.getColumnModel().getColumn(i).setResizable(false);
        }

        tblOrder.getTableHeader().setReorderingAllowed(false);
        tblOrder.setFont(new Font("Poppins SemiBold",Font.PLAIN,13));
        tblOrder.setOpaque(false);
        tblOrder.getTableHeader().setBackground(Color.cyan);
        tblOrder.getTableHeader().setFont(fontLabel);
        tblOrder.setShowGrid(false);
        tblOrder.setIntercellSpacing(new Dimension(0, 0));
        tblOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOrder.addMouseListener(this);

        JScrollPane scrollPanes = new JScrollPane(tblOrder);
        scrollPanes.setPreferredSize(new Dimension(posMidPanel.getPreferredSize().width,100));
        scrollPanes.getViewport().setBackground(Color.WHITE);
        posMidPanel.add(scrollPanes,BorderLayout.CENTER);

        itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Items", TitledBorder.LEFT, TitledBorder.TOP, borderFont, Color.BLACK));
        itemPanel.setPreferredSize(new Dimension(800, center.height));
        itemPanel.setBackground(new Color(202, 170, 86));
        centerPanel.add(itemPanel, BorderLayout.EAST);
        Dimension itemDim = itemPanel.getPreferredSize();

        itemNorthPanel = new JPanel();
        itemNorthPanel.setBackground(itemPanel.getBackground());
        itemNorthPanel.setLayout(new FlowLayout());
        itemNorthPanel.setPreferredSize(new Dimension(itemDim.width,50));
        itemPanel.add(itemNorthPanel,BorderLayout.NORTH);

        itemCenterPanel = new JPanel();
        itemCenterPanel.setBackground(itemPanel.getBackground());
        itemCenterPanel.setLayout(new BorderLayout());
        itemCenterPanel.setPreferredSize(new Dimension(itemDim.width-itemNorthPanel.getPreferredSize().width,itemDim.height-itemNorthPanel.getPreferredSize().height));
        itemPanel.add(itemCenterPanel,BorderLayout.CENTER);

        lblSearch = new JLabel("Search Product ID: ");
        lblSearch.setFont(fontLabel);
        lblSearch.setHorizontalAlignment(JLabel.LEFT);
        lblSearch.setPreferredSize(new Dimension(200, 30));
        itemNorthPanel.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setFont(fontLabel);
        txtSearch.setToolTipText("Search Product ID");
        txtSearch.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtSearch.setPreferredSize(new Dimension(300, 30));
        txtSearch.addKeyListener(this );
        itemNorthPanel.add(txtSearch);

        JPanel blankPanel2 = new JPanel();
        blankPanel2.setBackground(new Color(202, 170, 86));
        blankPanel2.setPreferredSize(new Dimension(250, 20));
        itemNorthPanel.add(blankPanel2);

        tblProducts = new JTable(tableModels) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        for (int i = 0; i < tblProducts.getColumnCount(); i++) {
            tblProducts.getColumnModel().getColumn(i).setPreferredWidth(150);
        }

        tblProducts.setRowHeight(60);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblProducts.getColumnModel().getColumnCount(); i++) {
            tblProducts.getColumnModel().getColumn(i).setResizable(false);
            tblProducts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblProducts.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblProducts.getTableHeader().setReorderingAllowed(false);
        tblProducts.setFont(new Font("Poppins SemiBold",Font.PLAIN,13));
        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getModel();
        showDataInTable();
        tblProducts.addMouseListener(this);
        scrollPane = new JScrollPane(tblProducts, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension((int) itemDim.getWidth() - 30, 320));
        itemCenterPanel.add(scrollPane);

        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(202, 170, 86));
        bottomPanel.setPreferredSize(new Dimension((int) dimension.getWidth(), 200));
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED), BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED)));
        bottomPanel.setLayout(new BorderLayout());
        Dimension bottomSize = bottomPanel.getPreferredSize();

        bottomLeftPanel = new JPanel();
        bottomLeftPanel.setLayout(new GridLayout(4,2,5,5));
        bottomLeftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Total", TitledBorder.LEFT, TitledBorder.TOP, borderFont, Color.BLACK));
        bottomLeftPanel.setPreferredSize(new Dimension(300, center.height));
        bottomLeftPanel.setBackground(new Color(202, 170, 86));
        bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);

        lblSubTotal = new JLabel("Sub - Total:");
        lblSubTotal.setFont(fontLabel);
        lblSubTotal.setHorizontalAlignment(JLabel.LEFT);
        lblSubTotal.setPreferredSize(new Dimension(125, 30));
        lblSubTotal.setLabelFor(txtSubTotal);
        bottomLeftPanel.add(lblSubTotal);

        txtSubTotal = new JTextField("0.0");
        txtSubTotal.setFont(fontLabel);
        txtSubTotal.setToolTipText("SubTotal");
        txtSubTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtSubTotal.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtSubTotal.setPreferredSize(new Dimension(145, 30));
        txtSubTotal.setFocusable(false);
        bottomLeftPanel.add(txtSubTotal);

        lblVAT = new JLabel("VAT:");
        lblVAT.setFont(fontLabel);
        lblVAT.setHorizontalAlignment(JLabel.LEFT);
        lblVAT.setPreferredSize(new Dimension(125, 30));
        lblVAT.setLabelFor(txtVAT);
        bottomLeftPanel.add(lblVAT);

        txtVAT = new JTextField("0.0");
        txtVAT.setFont(fontLabel);
        txtVAT.setToolTipText("VAT");
        txtVAT.setHorizontalAlignment(JTextField.RIGHT);
        txtVAT.setEditable(false);
        txtVAT.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtVAT.setPreferredSize(new Dimension(145, 30));
        txtVAT.setFocusable(false);
        bottomLeftPanel.add(txtVAT);

        lblGrandTotal = new JLabel("Grand Total:");
        lblGrandTotal.setFont(fontLabel);
        lblGrandTotal.setHorizontalAlignment(JLabel.LEFT);
        lblGrandTotal.setPreferredSize(new Dimension(125, 30));
        lblGrandTotal.setLabelFor(txtGrandTotal);
        bottomLeftPanel.add(lblGrandTotal);

        txtGrandTotal = new JTextField("0.0");
        txtGrandTotal.setFont(fontLabel);
        txtGrandTotal.setToolTipText("Grand Total");
        txtGrandTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtGrandTotal.setEditable(false);
        txtGrandTotal.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtGrandTotal.setPreferredSize(new Dimension(145, 30));
        txtGrandTotal.setFocusable(false);
        bottomLeftPanel.add(txtGrandTotal);

        bottomCenterPanel = new JPanel();
        bottomCenterPanel.setLayout(new GridLayout(4,2,5,5));
        bottomCenterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Payment", TitledBorder.LEFT, TitledBorder.TOP, borderFont, Color.BLACK));
        bottomCenterPanel.setBackground(new Color(202, 170, 86));
        bottomCenterPanel.setPreferredSize(new Dimension(bottomSize.width - 500, bottomSize.height));
        bottomPanel.add(bottomCenterPanel, BorderLayout.CENTER);

        lblPaymentMethod = new JLabel("Method of Payment: ");
        lblPaymentMethod.setFont(fontLabel);
        lblPaymentMethod.setHorizontalAlignment(JLabel.LEFT);
        lblPaymentMethod.setPreferredSize(new Dimension(250, 30));
        lblPaymentMethod.setLabelFor(cmbPayment);
        bottomCenterPanel.add(lblPaymentMethod);

        String[] payment = {"Cash", "Gcash"};
        cmbPayment = new JComboBox<>(payment);
        cmbPayment.setFont(fontLabel);
        cmbPayment.setSelectedItem("Cash");
        cmbPayment.setPreferredSize(new Dimension(300, 30));
        cmbPayment.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        cmbPayment.setRenderer(new MyListCellRenderer());
        cmbPayment.setFocusable(false);
        cmbPayment.addActionListener(e -> setCmbPayment());
        bottomCenterPanel.add(cmbPayment);

        lblReference = new JLabel("Reference Number:");
        lblReference.setFont(fontLabel);
        lblReference.setHorizontalAlignment(JLabel.LEFT);
        lblReference.setPreferredSize(new Dimension(250, 30));
        lblReference.setLabelFor(txtReferenceNumber);
        bottomCenterPanel.add(lblReference);

        txtReferenceNumber = new JTextField();
        txtReferenceNumber.setFont(fontLabel);
        txtReferenceNumber.setToolTipText("Reference Number");
        txtReferenceNumber.setHorizontalAlignment(JTextField.RIGHT);
        txtReferenceNumber.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtReferenceNumber.setPreferredSize(new Dimension(300, 30));
        txtReferenceNumber.setEnabled(false);
        txtReferenceNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();

                txtReferenceNumber.setEditable(!Character.isDigit(c));
                String phoneNumber = txtReferenceNumber.getText();
                int length = phoneNumber.length();

                if(e.getKeyChar()>='0' && e.getKeyChar()<='9'){
                    txtReferenceNumber.setEditable(length < 13);
                } else {
                    txtReferenceNumber.setEditable(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE);
                }
            }
        });
        bottomCenterPanel.add(txtReferenceNumber);

        lblCash = new JLabel("Cash:");
        lblCash.setFont(fontLabel);
        lblCash.setHorizontalAlignment(JLabel.LEFT);
        lblCash.setPreferredSize(new Dimension(250, 30));
        lblCash.setLabelFor(txtCash);
        bottomCenterPanel.add(lblCash);

        txtCash = new JTextField("0.0");
        txtCash.setFont(fontLabel);
        txtCash.setToolTipText("Cash");
        txtCash.setHorizontalAlignment(JTextField.RIGHT);
        txtCash.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtCash.setPreferredSize(new Dimension(300, 30));
        txtCash.setEditable(false);
        txtCash.addMouseListener(this);
        bottomCenterPanel.add(txtCash);

        lblChange = new JLabel("Change:");
        lblChange.setFont(fontLabel);
        lblChange.setHorizontalAlignment(JLabel.LEFT);
        lblChange.setPreferredSize(new Dimension(250, 30));
        lblChange.setLabelFor(txtChange);
        bottomCenterPanel.add(lblChange);

        txtChange = new JTextField("0.0");
        txtChange.setFont(fontLabel);
        txtChange.setToolTipText("Change");
        txtChange.setHorizontalAlignment(JTextField.RIGHT);
        txtChange.setBorder(new LineBorder(Color.BLACK, 2, false));
        txtChange.setPreferredSize(new Dimension(300, 30));
        txtChange.setEditable(false);
        bottomCenterPanel.add(txtChange);

        bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new GridLayout(2,2,3,3));
        bottomRightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Options", TitledBorder.LEFT, TitledBorder.TOP, borderFont, Color.BLACK));
        bottomRightPanel.setBackground(new Color(202, 170, 86));
        bottomRightPanel.setPreferredSize(new Dimension(450, bottomSize.height));
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);

        btnPay = new JButton();
        btnPay.setText("<HTML>Print<br>Receipt</HTML>");
        btnPay.setFont(fontLabel);
        btnPay.setFocusable(false);
        btnPay.setBackground(new Color(202, 170, 86));
        btnPay.setBorder(new LineBorder(Color.BLACK, 3, false));
        btnPay.setPreferredSize(new Dimension(200, 70));
        btnPay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPay.addActionListener(e -> buttonPay());
        bottomRightPanel.add(btnPay);

        btnUpdateProduct = new JButton();
        btnUpdateProduct.setText("Update");
        btnUpdateProduct.setFont(fontLabel);
        btnUpdateProduct.setFocusable(false);
        btnUpdateProduct.setBackground(new Color(202, 170, 86));
        btnUpdateProduct.setBorder(new LineBorder(Color.BLACK, 3, false));
        btnUpdateProduct.setPreferredSize(new Dimension(200, 70));
        btnUpdateProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnUpdateProduct.addActionListener(e -> updateButton());
        bottomRightPanel.add(btnUpdateProduct);

        btnVoidProduct = new JButton();
        btnVoidProduct.setText("Void");
        btnVoidProduct.setFont(fontLabel);
        btnVoidProduct.setFocusable(false);
        btnVoidProduct.setBackground(new Color(202, 170, 86));
        btnVoidProduct.setBorder(new LineBorder(Color.BLACK, 3, false));
        btnVoidProduct.setPreferredSize(new Dimension(200, 70));
        btnVoidProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoidProduct.addActionListener(e -> buttonVoid());
        bottomRightPanel.add(btnVoidProduct);

        btnExit = new JButton();
        btnExit.setText("Exit");
        btnExit.setFont(fontLabel);
        btnExit.setFocusable(false);
        btnExit.setBackground(new Color(202, 170, 86));
        btnExit.setBorder(new LineBorder(Color.BLACK, 3, false));
        btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnExit.setPreferredSize(new Dimension(200, 70));
        btnExit.addActionListener(e -> buttonExit());
        bottomRightPanel.add(btnExit);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        centerPanel.addMouseListener(this);
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {txtProductID.requestFocus();txtProductID.grabFocus();revalidate();}
        });
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {txtProductID.requestFocus();txtProductID.grabFocus();revalidate();}
        });
        this.addWindowFocusListener(new WindowAdapter() {
        @Override
        public void windowGainedFocus(WindowEvent e) {
        txtProductID.requestFocus();
        txtProductID.grabFocus();
        }});
        revalidate();
        CartTable cartTable = new CartTable();
        cartTable.cartTable(tblOrder);
        subTotal();

    }
    public ArrayList<Products> getProductList(){
        ArrayList<Products> productList = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String productQuery = "SELECT *,product_quantity*product_price as 'Product Amount' FROM products order by product_name ASC";
        Statement statement;
        ResultSet resultSet;
        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(productQuery);
            Products productsLists;
            while(resultSet.next()){
                productsLists = new Products(resultSet.getString("product_ID"),resultSet.getString("product_Name"),resultSet.getString("product_brand"),resultSet.getString("product_type"),resultSet.getString("product_Size"),resultSet.getString("product_length"),resultSet.getString("Size_Type"),resultSet.getDouble("product_Quantity"),resultSet.getString("Product_Quantity_Type"),resultSet.getDouble("Product_Price"),resultSet.getDouble("Product Amount"),resultSet.getString("Product_Description"),resultSet.getBytes("Product_Image"),resultSet.getString("hasWarranty"),resultSet.getString("product_created_at"));
                productList.add(productsLists);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return productList;
    }
    public void getModel(){
        DefaultTableModel tableModel = (DefaultTableModel) tblProducts.getModel();
        tableModel.setRowCount(0);
    }
    private void showDataInTable(){
        ArrayList<Products> productList = getProductList();
        tableModels.setRowCount(0);
        Object[] column = new Object[10];
        for(Products listProduct:productList){
            column[0] = listProduct.getProductID();
            column[1] = listProduct.getProductName();
            column[2] = listProduct.getProductBrand();
            column[3] = listProduct.getProductType();
            column[4] = listProduct.getProductQuantity();
            column[5] = listProduct.getQuantityType();
            column[6] = listProduct.getProductPrice();
            column[7] = listProduct.getAmount();
            column[8] = listProduct.getHasWarranty();
            column[9] = listProduct.dateAdded();
            tableModels.addRow(column);
        }
    }
    private void updateButton(){
        JLabel lblEnterQuantity = new JLabel();
        lblEnterQuantity.setText("Enter New Quantity");
        lblEnterQuantity.setFont(fontLabel);
        int selectedRow = tblOrder.getSelectedRow();
        TableModel model = tblOrder.getModel();
        double quantity;
        Connection connection = ConnectDatabase.connectDB();
        boolean check;

        do {
            check = true;
            try{
                String prodID = model.getValueAt(selectedRow,0).toString();
                int update = JOptionPane.showConfirmDialog(this,"You are going to update "+prodID+". Proceed?","Update Product",JOptionPane.YES_NO_OPTION);
                if(update == 0){
                    String quantityQuery = "Select product_quantity from products where product_ID ='"+prodID+"'";
                    double quantity1 = Double.parseDouble(model.getValueAt(selectedRow,3).toString());
                    double prodQuantity;

                    quantity = Double.parseDouble(JOptionPane.showInputDialog(this, lblEnterQuantity,"Enter Quantity",JOptionPane.INFORMATION_MESSAGE));

                    assert connection != null;
                    PreparedStatement preparedStatement = connection.prepareStatement(quantityQuery);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()){
                        double compareQuantity = resultSet.getDouble("product_Quantity");

                        if(quantity>compareQuantity){
                            JOptionPane.showMessageDialog(this,"New Quantity is greater than the current Stock,Please Enter new Quantity","Not enough Stock",JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            prodQuantity = resultSet.getDouble("Product_quantity")+quantity1;
                            model.setValueAt(quantity,selectedRow,3);
                            String reProdQuantityQuery = "update products set product_quantity = '"+prodQuantity+"' where product_ID ='"+prodID+"'";
                            double price = Double.parseDouble(model.getValueAt(selectedRow,2).toString());
                            model.setValueAt(quantity*price,selectedRow,4);
                            preparedStatement = connection.prepareStatement(reProdQuantityQuery);
                            preparedStatement.execute();
                            double newQuantity = Double.parseDouble(model.getValueAt(selectedRow,3).toString());
                            String newQuantityQuery = "update products set product_quantity = '"+(prodQuantity-newQuantity)+"' where product_ID ='"+prodID+"'";
                            preparedStatement = connection.prepareStatement(newQuantityQuery);
                            preparedStatement.execute();

                            String updateCart = "Update cart set product_quantity ='"+quantity+"',total_Amount = "+quantity+"*product_price where product_ID ='"+txtProductID.getText()+"'";
                            preparedStatement = connection.prepareStatement(updateCart);
                            preparedStatement.execute();
                            subTotal();
                            getModel();
                            showDataInTable();

                            txtQuantity.setText("1.0");
                            txtProductID.grabFocus();
                        }
                    }
                }
            }catch (ArrayIndexOutOfBoundsException exception){
                JOptionPane.showMessageDialog(this,"Please Select an Item in Order Table you want to Update","Update Item",JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this,"Invalid input","Invalid",JOptionPane.ERROR_MESSAGE);
               nfe.printStackTrace();
                check = false;
            }catch(NullPointerException ignored){
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        while (!check);
    }
    private void buttonExit() {
        int totalRow = tblOrder.getRowCount();
        if (totalRow > 0) {
            JOptionPane.showMessageDialog(this, "Please end the ongoing transaction before exit!", "Transaction Ongoing", JOptionPane.WARNING_MESSAGE);
        } else {
            int a = JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (a == 0) {
                this.dispose();
                new MainMenuForm().setVisible(true);
            }
        }
    }
    private void setCmbPayment(){
        if(Objects.requireNonNull(cmbPayment.getSelectedItem()).equals("Cash")){
            txtReferenceNumber.setText("");
            txtReferenceNumber.setEnabled(false);
            txtProductID.requestFocus();
            txtProductID.grabFocus();
            revalidate();
        }else if(Objects.requireNonNull(cmbPayment.getSelectedItem()).equals("Gcash")){
            txtReferenceNumber.setEnabled(true);
        }
    }
    private void buttonVoid(){
        try {
            Connection connection = ConnectDatabase.connectDB();
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
            int prodID = tblOrder.getSelectedRow();
            String selectQuery = "Select * from products where Product_ID ='"+model.getValueAt(prodID,0)+"'";
            double prodQuantity = Double.parseDouble(model.getValueAt(prodID,3).toString());
            double currentProdQuantity = 0;
            assert connection != null;
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                currentProdQuantity = resultSet.getDouble("Product_Quantity");
            }
            double nextProductQuantity = currentProdQuantity + prodQuantity;
            int a = JOptionPane.showConfirmDialog(this,"Void this item?","Void Item",JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                String voidQuery = "Update products set Product_Quantity ='" + nextProductQuantity + "' where product_ID = '" + model.getValueAt(prodID, 0) + "'";
                preparedStatement = connection.prepareStatement(voidQuery);
                preparedStatement.execute();
                model.removeRow(prodID);
                String deleteCart = "Delete from cart where product_ID ='"+txtProductID.getText()+"'";
                preparedStatement = connection.prepareStatement(deleteCart);
                preparedStatement.execute();
                getModel();
                showDataInTable();
                txtProductID.setText("");
                txtQuantity.setText("1.0");
                txtCash.setText("0.0");
                txtChange.setText("0.0");
                subTotal();
                ImageIcon icon = new ImageIcon(new ImageIcon("images/white.png").getImage().getScaledInstance(lblOrder.getPreferredSize().width, lblOrder.getPreferredSize().height, Image.SCALE_SMOOTH));
                lblOrder.setIcon(icon);
                txtProductID.grabFocus();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException exception) {
            JOptionPane.showMessageDialog(this, "Select an Item you want to Void", "Void Item", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void buttonPay(){
      if(txtCash.getText().equals("0.0")){
          JOptionPane.showMessageDialog(this,"Please Enter Cash before proceed to Print Receipt","Please Pay",JOptionPane.WARNING_MESSAGE);
      }else{
          PaymentForm paymentForm = new PaymentForm(tblOrder);
          if(AllUsers.isChecked){
              paymentForm.setVisible(true);
          }
          else{
              JCheckBox rememberChk = new JCheckBox("Do not show this message again today.");
              rememberChk.setSelected(AllUsers.isChecked);
              String msg = "Proceeding to Print Receipt it means the customer paid already. Continue?";
              JLabel lblMsg = new JLabel(msg);
              lblMsg.setFont(fontLabel);
              Object[] msgContent = {lblMsg, rememberChk};
              int n = JOptionPane.showConfirmDialog (this,msgContent,"Proceed to Receipt", JOptionPane.YES_NO_OPTION);
              if (n == 0){
                  if(rememberChk.isSelected()){
                      AllUsers.isChecked = true;
                  }
                  DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
                  DefaultTableModel payModel = (DefaultTableModel) paymentForm.tblOrder.getModel();
                  String id;
                  String name;
                  String price;
                  String qty;
                  String amount;
                  String warranty;
                  String[] order;

                  for (int i = 0; i < model.getRowCount(); i++) {
                      id = model.getValueAt(i,0).toString();
                      name = model.getValueAt(i,1).toString();
                      price = model.getValueAt(i,2).toString();
                      qty = model.getValueAt(i,3).toString();
                      amount = model.getValueAt(i,4).toString();
                      warranty = model.getValueAt(i,5).toString();
                      order = new String[]{id,name,price,qty,amount,warranty};
                      payModel.addRow(order);
                  }
                  String transactionID = String.valueOf(generateRandom(12));
                  boolean checkId;
                    do{
                        checkId = true;
                        if(checkTransactionID(transactionID)){
                            transactionID = String.valueOf(generateRandom(12));
                            checkId = false;
                        }

                    }while (!checkId);
                    AllUsers.referenceNumber = txtReferenceNumber.getText();
                    AllUsers.subTotal = txtSubTotal.getText();
                    AllUsers.grandTotal = txtGrandTotal.getText();
                    AllUsers.paymentMethod = Objects.requireNonNull(cmbPayment.getSelectedItem()).toString();
                    AllUsers.cash = txtCash.getText();
                    AllUsers.change = txtChange.getText();
                    AllUsers.purchasedItem = model.getRowCount();
                    AllUsers.vat = txtVAT.getText();

                    txtSubTotal.setText("0.0");
                    txtVAT.setText("0.0");
                    txtGrandTotal.setText("0.0");
                    txtReferenceNumber.setText("");
                    txtCash.setText("0.0");
                    txtChange.setText("0.0");
                    cmbPayment.setSelectedItem("Cash");
                    lblOrder.setIcon(new ImageIcon(new ImageIcon("images/white.png").getImage().getScaledInstance(lblOrder.getPreferredSize().width, lblOrder.getPreferredSize().height, Image.SCALE_SMOOTH)));

                  paymentForm.txtTransactionID.setText(transactionID);
                  paymentForm.setVisible(true);


              }
          }
      }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == txtQuantity) {
            try {
                    JLabel lbl = new JLabel("Enter Quantity");
                    lbl.setFont(fontLabel);
                    lbl.setHorizontalAlignment(SwingConstants.CENTER);
                    lbl.setPreferredSize(new Dimension(200,40));
                    JTextField quantityEntered = new JTextField();
                    quantityEntered.setFont(fontLabel);
                    quantityEntered.setPreferredSize(new Dimension(300,40));
                    quantityEntered.setHorizontalAlignment(SwingConstants.CENTER);
                    quantityEntered.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            char c = e.getKeyChar();
                            final boolean d = e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE;
                            final boolean dot = e.getExtendedKeyCode() == KeyEvent.VK_PERIOD || e.getExtendedKeyCode() == KeyEvent.VK_DECIMAL;
                            quantityEntered.setEditable(dot || d ||Character.isDigit(c)||e.getExtendedKeyCode()==KeyEvent.VK_ENTER);
                            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                                if(quantityEntered.getText().isEmpty()){
                                    txtQuantity.setText("1.0");
                                }else{
                                    txtQuantity.setText(quantityEntered.getText());

                                }
                                jDialog.setVisible(false);
                            }
                        }
                    });
                    JButton btnOK = new JButton();
                    btnOK.setText("OK");
                    btnOK.setFont(fontLabel);
                    btnOK.setPreferredSize(new Dimension(300,40));
                    btnOK.addActionListener(e1 -> {
                        if(quantityEntered.getText().isEmpty()){
                            txtQuantity.setText("1.0");
                        }else{
                            txtQuantity.setText(quantityEntered.getText());
                        }
                        jDialog.setVisible(false);

                    });
                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    panel.setBackground(centerPanel.getBackground());
                    panel.setPreferredSize(new Dimension(300,120));
                    panel.add(lbl,BorderLayout.NORTH);
                    panel.add(quantityEntered,BorderLayout.CENTER);
                    panel.add(btnOK,BorderLayout.SOUTH);
                    jDialog = new JDialog();
                    jDialog.setTitle("Enter Quantity");
                    jDialog.setModal(true);
                    jDialog.setSize(300,100);
                    jDialog.add(panel);
                    jDialog.pack();
                    jDialog.setLocationRelativeTo(new POSForm());
                    jDialog.setVisible(true);
            }catch(NullPointerException | NumberFormatException ex){
            txtCash.setText("0.0");
            }
        }
        if(e.getSource() == txtCash){
            try {
                if (tblOrder.getRowCount() < 1) {
                    JOptionPane.showMessageDialog(this, "Table Order is Empty,Unable to Insert a Cash");
                } else {
                    JLabel lbl = new JLabel("Enter Cash");
                    lbl.setFont(fontLabel);
                    lbl.setHorizontalAlignment(SwingConstants.CENTER);
                    lbl.setPreferredSize(new Dimension(200,40));
                    JTextField cashEntered = new JTextField();
                    cashEntered.setFont(fontLabel);
                    cashEntered.setPreferredSize(new Dimension(300,40));
                    cashEntered.setHorizontalAlignment(SwingConstants.CENTER);
                    cashEntered.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            char c = e.getKeyChar();
                            final boolean d = e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE;
                            final boolean dot = e.getExtendedKeyCode() == KeyEvent.VK_PERIOD || e.getExtendedKeyCode() == KeyEvent.VK_DECIMAL;
                            cashEntered.setEditable(d ||Character.isDigit(c)||e.getExtendedKeyCode()==KeyEvent.VK_ENTER|| dot);
                            if(e.getKeyChar() == KeyEvent.VK_ENTER) {
                                try{
                                    if (cashEntered.getText().isEmpty()) {
                                        txtCash.setText("0.0");
                                    } else {
                                        double grandTotal = Double.parseDouble(txtGrandTotal.getText());
                                        double cash = Double.parseDouble(cashEntered.getText());
                                        double change;
                                        if (grandTotal > cash) {
                                            JOptionPane.showMessageDialog(new POSForm(), "Insufficient Cash", "Error", JOptionPane.WARNING_MESSAGE);
                                            jDialog.setVisible(true);
                                        } else {
                                            change = cash - grandTotal;
                                            txtChange.setText(String.valueOf(change));
                                            txtCash.setText(cashEntered.getText());
                                            jDialog.setVisible(false);
                                        }
                                    }
                                    jDialog.setVisible(false);
                                }catch(NumberFormatException ex){
                                    txtCash.setText("0.0");
                                }
                    }}
                        });

                    JButton btnOK = new JButton();
                    btnOK.setText("OK");
                    btnOK.setFont(fontLabel);
                    btnOK.setPreferredSize(new Dimension(300,40));
                    btnOK.addActionListener(e1 -> {
                        try{
                            if(cashEntered.getText().isEmpty()){
                                txtCash.setText("0.0");
                            }
                            else{
                                double grandTotal = Double.parseDouble(txtGrandTotal.getText());
                                double cash = Double.parseDouble(cashEntered.getText());
                                double change;
                                if (grandTotal > cash) {
                                    JOptionPane.showMessageDialog(new POSForm(), "Insufficient Cash", "Error", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    change = cash - grandTotal;
                                    txtChange.setText(String.valueOf(change));
                                    txtCash.setText(cashEntered.getText());
                                    jDialog.setVisible(false);
                                }

                            }

                        }catch (NumberFormatException ex){
                            txtCash.setText("0.0");
                            jDialog.setVisible(false);
                        }
                    });

                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    panel.setBackground(centerPanel.getBackground());
                    panel.setPreferredSize(new Dimension(300,120));
                    panel.add(lbl,BorderLayout.NORTH);
                    panel.add(cashEntered,BorderLayout.CENTER);
                    panel.add(btnOK,BorderLayout.SOUTH);

                    jDialog = new JDialog();
                    jDialog.setTitle("Enter Cash");
                    jDialog.setModal(true);
                    jDialog.setSize(300,100);
                    jDialog.add(panel);
                    jDialog.pack();
                    jDialog.setLocationRelativeTo(new POSForm());
                    jDialog.setVisible(true);


                }
            }catch(NullPointerException | NumberFormatException ex){
                txtCash.setText("0.0");
            }
        }
        if(e.getSource() == tblOrder){
            Connection connection = ConnectDatabase.connectDB();
            int selectedRow = tblOrder.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
            txtProductID.setText(model.getValueAt(selectedRow,0).toString());
            txtQuantity.setText(model.getValueAt(selectedRow,3).toString());
            String imageQuery = "Select Product_Image from products where product_ID = '"+txtProductID.getText()+"'";
            try {
                assert connection != null;
                PreparedStatement preparedStatement = connection.prepareStatement(imageQuery);
                ResultSet resultSet= preparedStatement.executeQuery();
                if(resultSet.next()){
                    lblIcon = new ImageIcon(new ImageIcon(resultSet.getBytes("Product_Image")).getImage().getScaledInstance(lblOrder.getPreferredSize().width,lblOrder.getPreferredSize().height,Image.SCALE_SMOOTH));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            lblOrder.setIcon(lblIcon);

        }

        if(e.getSource() == tblProducts){
            int selectedRow = tblProducts.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
            txtSearch.setText(model.getValueAt(selectedRow,0).toString());

            int a = JOptionPane.showConfirmDialog(this,"Add this item?","Add Item",JOptionPane.YES_NO_OPTION);
            if (a == 0){
                enterProduct(txtSearch);
            }
            else{
                txtSearch.setText("");
                JOptionPane.showMessageDialog(this,"Item not added in the order", "Item not Added",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        if(e.getSource()== txtProductID){
            char c = e.getKeyChar();
            final boolean d = e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE;
            if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' ){
                txtProductID.setEditable(txtProductID.getText().length()<20);
            }else{
                txtProductID.setEditable(d ||Character.isDigit(c)||e.getExtendedKeyCode()==KeyEvent.VK_ENTER);
            }
            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                enterProduct(txtProductID);
            }
        }
        if(e.getSource() == txtQuantity){
            char c = e.getKeyChar();
            final boolean d = e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE;

            if(e.getKeyChar() >= '0' && e.getKeyChar() <= '9' ){
                txtQuantity.setEditable(txtQuantity.getText().length()<4);
            }else{
                txtQuantity.setEditable(d ||Character.isDigit(c)||e.getExtendedKeyCode()==KeyEvent.VK_ENTER||e.getExtendedKeyCode() == KeyEvent.VK_PERIOD || e.getExtendedKeyCode() == KeyEvent.VK_DECIMAL);
            }
        }
    }
    private void subTotal(){
        TableModel model = tblOrder.getModel();
        if(model.getRowCount() == 0){
            txtSubTotal.setText("0.0");
            txtVAT.setText("0.0");
//            txtDiscount.setText("0.0");
            txtGrandTotal.setText("0.0");
        }
        double amount;
        double total = 0;
        double subTotal;
        double vat;
//        double discount = Double.parseDouble(txtDiscount.getText());
        for (int i = 0; i < model.getRowCount(); i++) {
            amount = Double.parseDouble(model.getValueAt(i,4).toString());
            total = amount + total /*- discount*/;
            subTotal = total*.88;
            vat = total*.12;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String strSub = decimalFormat.format(subTotal);
            String strVat = decimalFormat.format(vat);
            String strGrand = decimalFormat.format(total);
            txtSubTotal.setText(strSub);
            txtVAT.setText(strVat);
            txtGrandTotal.setText(strGrand);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == txtSearch){
            searchProduct();
        }
    }
    private static class MyListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            component.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            return component;
        }
    }
    private void searchProduct(){
        Connection con = ConnectDatabase.connectDB();
        String query = "select *,product_quantity*product_price as 'Product Amount' from products where Product_ID like '%' '"+txtSearch.getText()+"' '%' order by product_name ASC";
        Statement statement;
        ResultSet resultSet;
        ArrayList<Products> searchProducts = new ArrayList<>();
        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            Products productsLists;
            while(resultSet.next()){
                productsLists = new Products(resultSet.getString("product_ID"),resultSet.getString("product_Name"),resultSet.getString("product_brand"),resultSet.getString("product_type"),resultSet.getString("product_Size"),resultSet.getString("product_length"),resultSet.getString("Size_Type"),resultSet.getDouble("product_Quantity"),resultSet.getString("Product_Quantity_Type"),resultSet.getDouble("Product_Price"),resultSet.getDouble("Product Amount"),resultSet.getString("Product_Description"),resultSet.getBytes("Product_Image"),resultSet.getString("hasWarranty"),resultSet.getString("product_created_at"));
                searchProducts.add(productsLists);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        DefaultTableModel tableModel = (DefaultTableModel) tblProducts.getModel();
        tableModel.setRowCount(0);
        Object[] column = new Object[10];
        for(Products listProduct:searchProducts){
            column[0] = listProduct.getProductID();
            column[1] = listProduct.getProductName();
            column[2] = listProduct.getProductBrand();
            column[3] = listProduct.getProductType();
            column[4] = listProduct.getProductQuantity();
            column[5] = listProduct.getQuantityType();
            column[6] = listProduct.getProductPrice();
            column[7] = listProduct.getAmount();
            column[8] = listProduct.getHasWarranty();
            column[9] = listProduct.dateAdded();
            tableModel.addRow(column);
        }
    }
    private void enterProduct(JTextField txtField){
        DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
        String id = txtField.getText();
        String existID = null;
        Connection connection = ConnectDatabase.connectDB();
        ResultSet resultSet;
        String[] data;
        ImageIcon iconOrder;
        double buyStock = Double.parseDouble(txtQuantity.getText());

        double availStock;


            try {
                String query = "Select * from products where Product_ID = '"+id+"'";
                assert connection != null;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    for(int i = 0;i<model.getRowCount();i++) {
                        existID = model.getValueAt(i, 0).toString();
                        if (id.equals(existID)) {
                            break;
                        }
                    }
                    if (id.equals(existID)) {
                        JOptionPane.showMessageDialog(this, "Item "+id+" already punch,Please update the item if you want to add more", "Already Punched", JOptionPane.WARNING_MESSAGE);
                        txtField.setText("");
                    }
                    else{
                        availStock = Double.parseDouble(resultSet.getString("Product_Quantity"));
                        if(buyStock>availStock){
                            JOptionPane.showMessageDialog(this,"Our Stock is current low");
                            txtSearch.setText("");
                            txtProductID.setText("");
                        }
                        else{
                            String prodID = resultSet.getString("Product_ID");
                            String prodName = resultSet.getString("Product_Name");
                            double prodPrice = resultSet.getDouble("Product_Price");
                            double prodQuantity = Double.parseDouble(txtQuantity.getText());
                            double prodAmount = prodPrice*prodQuantity;
                            String warranty = resultSet.getString("hasWarranty");
                            double currentProdQuantity = resultSet.getDouble("Product_Quantity");
                            double nextProdQuantity = currentProdQuantity - prodQuantity;
                            String updateQuantityQuery = "update products set Product_Quantity = '"+nextProdQuantity+"' where Product_ID = '"+id+"'";
                            preparedStatement = connection.prepareStatement(updateQuantityQuery);
                            preparedStatement.execute();
                            String cartQuery = "INSERT INTO `cart`(`Product_ID`, `Product_Name`, `Product_Price`,`Product_Quantity`, `Total_amount`, `Product_Warranty`) VALUES ('"+prodID+"','"+prodName+"','"+prodPrice+"','"+prodQuantity+"','"+prodAmount+"','"+warranty+"')";
                            preparedStatement = connection.prepareStatement(cartQuery);
                            preparedStatement.execute();

                            getModel();
                            showDataInTable();
                            data = new String[]{prodID,prodName, String.valueOf(prodPrice), String.valueOf(prodQuantity), String.valueOf(prodAmount),warranty};
                            model.addRow(data);
                            txtField.setText("");
                            txtQuantity.setText("1.0");
                            iconOrder = new ImageIcon(new ImageIcon(resultSet.getBytes("Product_Image")).getImage().getScaledInstance(lblOrder.getPreferredSize().width,lblOrder.getPreferredSize().height,Image.SCALE_SMOOTH));
                            lblOrder.setIcon(iconOrder);
                            subTotal();
                        }

                    }
                }
                else{
                    JOptionPane.showMessageDialog(this,"No Product ID "+txtField.getText()+" found","No Product",JOptionPane.WARNING_MESSAGE);
                    txtField.setText("");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(this,"Please Enter a Quantity before Scanning");
                txtProductID.setText("");
                txtSearch.setText("");
                txtQuantity.setText("1.0");
            }
    }
    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
    public boolean checkTransactionID(String userid) {
        PreparedStatement pst;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT transaction_ID FROM transactionReport WHERE transaction_ID =?";

        try {
            Connection con = ConnectDatabase.connectDB();
            assert con != null;
            pst = con.prepareStatement(query);
            pst.setString(1,userid);
            rs = pst.executeQuery();
            if(rs.next())
            {
                checkUser = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return checkUser;
    }
}
