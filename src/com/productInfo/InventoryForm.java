package com.productInfo;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.thesis.ConnectDatabase;
import com.thesis.MainMenuForm;
import com.userInfo.AllUsers;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InventoryForm extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    JPanel centerPanel,northPanel,westPanel,currentPanel,southPanel;
    JButton btnBack,btnAdd,btnUpdate,btnView,btnSearch,btnAddStock,btnTrash,btnPdf,btnExcel,btnMove,btnPrint;
    JTextField txtSearch;
    JTable tblProducts;
    Font font = new Font("Poppins SemiBold",Font.PLAIN,15);
    JLabel lblBanner,lblPrice,lblTotal;
    JScrollPane scrollPane;
    String[] columnName ={"Product ID","Product Name","Product Brand","Product Type","Product Size","Length Type","Stock","Type","Price","Amount","Warranty","Date Added"};
    String[][] data = {{null,null,null,null,null,null,null,null,null}};
    ImageIcon searchIcon,addIcon,updateIcon,backIcon,addStockIcon,trashIcon,printIcon;
    byte[] product_image = null;
    private final DefaultTableModel tableModel = new DefaultTableModel(data,columnName);
    public InventoryForm(){
        super("Inventory");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setIconImage(AllUsers.image.getImage());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setResizable(false);
        setUndecorated(true);

        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(dimension.width,200));
        northPanel.setBackground(new Color(202,170,86));
        northPanel.setBorder(new LineBorder(Color.BLACK,5,false));
        westPanel = new JPanel();
        westPanel.setLayout(new GridLayout(7,1));
        westPanel.setBackground(new Color(202,170,86));
        westPanel.setPreferredSize(new Dimension(300,dimension.height));
        westPanel.setBorder(northPanel.getBorder());
        centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setOpaque(true);
        centerPanel.setBackground(westPanel.getBackground());
        centerPanel.setBorder(new LineBorder(Color.BLACK,5,false));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(dimension.width-westPanel.getPreferredSize().width,dimension.height-northPanel.getPreferredSize().height));
        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.setPreferredSize(new Dimension(dimension.width,100));
        
        String logo = new File("images/banner-1.jpg").getAbsolutePath();
        lblBanner = new JLabel();
        lblBanner.setPreferredSize(new Dimension(dimension.width,200));
        ImageIcon newImage = new ImageIcon(new ImageIcon(logo).getImage().getScaledInstance(lblBanner.getPreferredSize().width,lblBanner.getPreferredSize().height, Image.SCALE_DEFAULT));
        lblBanner.setIcon(newImage);
        lblBanner.setBackground(centerPanel.getBackground());
        lblBanner.setHorizontalAlignment(JLabel.CENTER);
        lblBanner.setOpaque(true);
        northPanel.add(lblBanner,BorderLayout.CENTER);

        searchIcon = new ImageIcon(new ImageIcon("images/search-item.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnSearch = new JButton("Product List");
        button(btnSearch,searchIcon);
        addIcon = new ImageIcon(new ImageIcon("images/add-item.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnAdd = new JButton("Add Product");
        button(btnAdd,addIcon);
        updateIcon = new ImageIcon(new ImageIcon("images/edit-item.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnUpdate = new JButton("Update Product");
        button(btnUpdate,updateIcon);
        addStockIcon = new ImageIcon(new ImageIcon("images/add-stock.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnAddStock = new JButton("Add Stock");
        button(btnAddStock,addStockIcon);
        trashIcon = new ImageIcon(new ImageIcon("images/trash-item.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnTrash = new JButton("Trash");
        button(btnTrash,trashIcon);
        printIcon = new ImageIcon(new ImageIcon("images/print.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnPrint = new JButton("Print Product ID");
        button(btnPrint,printIcon);
        backIcon = new ImageIcon(new ImageIcon("images/log-out.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnBack = new JButton("Back");
        button(btnBack,backIcon);

        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width,100));
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(centerPanel.getBackground());
        centerPanel.add(panel1,BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("      Inventory");
        lblTitle.setHorizontalAlignment(JLabel.LEFT);
        lblTitle.setFont(new Font("Poppins SemiBold", Font.BOLD,30));
        lblTitle.setVerticalAlignment(JLabel.CENTER);
        lblTitle.setVerticalTextPosition(JLabel.CENTER);
        lblTitle.setPreferredSize(new Dimension(350,100));
        panel1.add(lblTitle,BorderLayout.NORTH);

        JPanel panel2 = new JPanel();
        panel2.setBackground(centerPanel.getBackground());
        panel2.setLayout(new BorderLayout());
        panel2.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width,30));
        panel1.add(panel2,BorderLayout.SOUTH);

        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(50,30));
        panel3.setBackground(centerPanel.getBackground());
        panel2.add(panel3,BorderLayout.WEST);

        txtSearch = new JTextField();
        txtSearch.setBorder(new LineBorder(Color.BLACK,1,false));
        txtSearch.setFont(font);
        txtSearch.setPreferredSize(new Dimension(570,30));
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchProduct();
            }
        });
        panel2.add(txtSearch,BorderLayout.CENTER);

        tblProducts = new JTable(tableModel){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        JPanel panel6 = new JPanel();
        panel6.setPreferredSize(new Dimension(400,30));
        panel6.setLayout(new GridLayout(0,2));
        panel6.setOpaque(false);
        panel2.add(panel6,BorderLayout.EAST);

        lblTotal = new JLabel();
        lblTotal.setText("Total Price of All Items:");
        lblTotal.setFont(font);
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setVerticalAlignment(SwingConstants.CENTER);
        panel6.add(lblTotal);

        lblPrice = new JLabel();
        lblPrice.setText(totalAmount()+" PHP");
        lblPrice.setFont(font);
        lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrice.setVerticalAlignment(SwingConstants.CENTER);
        panel6.add(lblPrice);

        JPanel panel7 = new JPanel();
        panel7.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width,500));
        panel7.setBackground(centerPanel.getBackground());
        panel7.setLayout(new BorderLayout());
        centerPanel.add(panel7,BorderLayout.SOUTH);

        JPanel panel8 = new JPanel();
        panel8.setPreferredSize(new Dimension(50,530));
        panel8.setBackground(centerPanel.getBackground());
        panel7.add(panel8,BorderLayout.WEST);

        JPanel panel9 = new JPanel();
        panel9.setPreferredSize(new Dimension(50,530));
        panel9.setBackground(centerPanel.getBackground());
        panel7.add(panel9,BorderLayout.EAST);

        JPanel panel10 = new JPanel();
        panel10.setPreferredSize(new Dimension(panel7.getPreferredSize().width,50));
        panel10.setBackground(centerPanel.getBackground());
        panel7.add(panel10,BorderLayout.NORTH);

        panel7.add(southPanel,BorderLayout.SOUTH);


        tblProducts.setRowHeight(50);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        for (int i = 0; i < tblProducts.getColumnModel().getColumnCount(); i++) {
            tblProducts.getColumnModel().getColumn(i).setResizable(false);
            tblProducts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            tblProducts.getColumnModel().getColumn(i).setPreferredWidth(200);

        }
        tblProducts.getTableHeader().setReorderingAllowed(false);
        tblProducts.getTableHeader().setFont(new Font("Poppins SemiBold",Font.BOLD,11));
        tblProducts.setFont(new Font("Poppins SemiBold",Font.BOLD,13));
        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProducts.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getModel();
        searchProduct();
        tblProducts.addMouseListener(this);
        scrollPane = new JScrollPane(tblProducts,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width-200,centerPanel.getPreferredSize().height-200));
        panel7.add(scrollPane);

        JPanel panel12 = new JPanel();
        panel12.setPreferredSize(new Dimension(50,100));
        panel12.setOpaque(false);
        southPanel.add(panel12,BorderLayout.WEST);

        JPanel panel13 = new JPanel();
        panel13.setPreferredSize(new Dimension(50,100));
        panel13.setOpaque(false);
        southPanel.add(panel13,BorderLayout.EAST);

        JPanel panel14 = new JPanel();
        panel14.setPreferredSize(new Dimension(southPanel.getPreferredSize().width,20));
        panel14.setOpaque(false);
        southPanel.add(panel14,BorderLayout.NORTH);

        JPanel panel15 = new JPanel();
        panel15.setPreferredSize(new Dimension(southPanel.getPreferredSize().width,30));
        panel15.setOpaque(false);
        southPanel.add(panel15,BorderLayout.SOUTH);

        JPanel panel16 = new JPanel();
        panel16.setPreferredSize(new Dimension(southPanel.getPreferredSize().width-100,50));
        panel16.setOpaque(false);
        panel16.setLayout(new BorderLayout());
        southPanel.add(panel16,BorderLayout.CENTER);

        JPanel panel17 = new JPanel();
        panel17.setLayout(new GridLayout(1,5,20,0));
        panel17.setBackground(Color.BLACK.brighter().brighter());
        panel17.setOpaque(false);
        panel16.add(panel17);

        btnView = new JButton();
        btnView.setText("View Product");
        btnView.setFocusable(false);
        btnView.setFont(font);
        btnView.setHorizontalAlignment(SwingConstants.CENTER);
        btnView.addActionListener(e-> viewItem());
        panel17.add(btnView);
        
        btnPdf = new JButton();
        btnPdf.setText("Generate Report");
        btnPdf.setFocusable(false);
        btnPdf.setFont(font);
        btnPdf.setHorizontalAlignment(SwingConstants.CENTER);
        btnPdf.addActionListener(e -> generateReport());
        panel17.add(btnPdf);

        btnExcel = new JButton();
        btnExcel.setText("Export to Excel");
        btnExcel.setFocusable(false);
        btnExcel.setFont(font);
        btnExcel.setHorizontalAlignment(SwingConstants.CENTER);
        btnExcel.addActionListener(e -> excelExport());
        panel17.add(btnExcel);

        btnMove = new JButton();
        btnMove.setText("Move to Trash");
        btnMove.setFocusable(false);
        btnMove.setFont(font);
        btnMove.setHorizontalAlignment(SwingConstants.CENTER);
        btnMove.addActionListener(e -> moveToTrash());
        panel17.add(btnMove);

        JPanel panel18 = new JPanel();
        panel18.setOpaque(false);
        panel18.setLayout(new GridLayout(0,2));
        panel17.add(panel18);
        add(northPanel, BorderLayout.NORTH);
        add(westPanel,BorderLayout.WEST);
        openPanel(centerPanel);
        btnActive(btnSearch);
    }

    public void openPanel(JPanel panel){
        if(currentPanel != null){
            currentPanel.hide();
        }
        currentPanel = panel;
        this.add(panel,BorderLayout.CENTER);
        panel.show();
    }
    public void btnActive(JButton btn){
        btn.setBackground(new Color (8, 84, 155));
        btn.setForeground(new Color(215, 215, 37));
    }
    public void btnInactive(JButton btn1,JButton btn2,JButton btn3){
        btn1.setBackground(westPanel.getBackground());
        btn1.setForeground(Color.BLACK);
        btn2.setBackground(westPanel.getBackground());
        btn2.setForeground(Color.BLACK);
        btn3.setBackground(westPanel.getBackground());
        btn3.setForeground(Color.BLACK);
    }
    private void button (JButton btn,ImageIcon icon){
        btn.setVerticalAlignment(JButton.CENTER);
        btn.setHorizontalAlignment(JButton.LEFT);
        btn.setVerticalTextPosition(JButton.CENTER);
        btn.setIcon(icon);
        btn.setForeground(Color.BLACK);
        btn.setIconTextGap(50);
        btn.setFont(font);
        btn.setFocusable(false);
        btn.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btn.setBackground(new Color(202,170,86));
        btn.addActionListener(this);
        btn.setPreferredSize(new Dimension(westPanel.getPreferredSize().width-10,100));
        westPanel.add(btn);
    }


    public void getModel(){
        DefaultTableModel tableModel = (DefaultTableModel) tblProducts.getModel();
        tableModel.setRowCount(0);
    }

    
    private void searchProduct(){
        Connection con = ConnectDatabase.connectDB();
        String query = "select *,product_quantity*product_price as 'Product Amount' from products where Product_ID like '%' '"+txtSearch.getText()+"' '%' or Product_Name like '%' '"+txtSearch.getText()+"' '%' or Product_Brand like '%' '"+txtSearch.getText()+"' '%' or Product_Type like '%' '"+txtSearch.getText()+"' '%' order by product_name ASC";
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
        Object[] column = new Object[12];
        for(Products listProduct:searchProducts){
            column[0] = listProduct.getProductID();
            column[1] = listProduct.getProductName();
            column[2] = listProduct.getProductBrand();
            column[3] = listProduct.getProductType();
            column[4] = listProduct.getProductSize();
            column[5] = listProduct.getProductLength();
            column[6] = listProduct.getProductQuantity();
            column[7] = listProduct.getQuantityType();
            column[8] = listProduct.getProductPrice();
            column[9] = listProduct.getAmount();
            column[10] = listProduct.getHasWarranty();
            column[11] = listProduct.dateAdded();
            tableModel.addRow(column);
        }
        lblPrice.setText(totalAmount()+" PHP");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSearch){
            totalAmount();
            getModel();
            searchProduct();
            openPanel(centerPanel);
            btnInactive(btnUpdate,btnAdd,btnTrash);
            btnActive(btnSearch);
            revalidate();
        }
        if (e.getSource() == btnAdd) {
            AddProductForm add = new AddProductForm();
            openPanel(add);
            btnInactive(btnUpdate,btnSearch,btnTrash);
            btnActive(btnAdd);
            revalidate();
        }
        if (e.getSource() == btnBack) {
            this.dispose();
            new MainMenuForm().setVisible(true);
        }
        if(e.getSource() == btnTrash){
            TrashPanel trashPanel = new TrashPanel();
//            trashPanel.btnDelete.addActionListener(e1-> deleteProduct());
            trashPanel.btnView.addActionListener(e1-> viewItemInTrash());
            openPanel(trashPanel);
            btnInactive(btnUpdate,btnSearch,btnAdd);
            btnActive(btnTrash);
            revalidate();
            AllUsers.productID = null;
        }
        if (e.getSource() == btnUpdate) {
            try {
                int i = tblProducts.getSelectedRow();
                TableModel model = tblProducts.getModel();
                AllUsers.productID = model.getValueAt(i, 0).toString();
                UpdateProductForm updateProductForm = new UpdateProductForm();
                ArrayList<Products> products = getProductList1();

                for(Products products1:products){
                    updateProductForm.txtProductID.setText(products1.getProductID());
                    updateProductForm.txtProductName.setText(products1.getProductName());
                    updateProductForm.txtProductBrand.setText(products1.getProductBrand());
                    updateProductForm.cmbProductType.setSelectedItem(products1.getProductType());

                    if(products1.getSizeType().equals("By Length")){
                        updateProductForm.txtProductSize.setText(String.valueOf(products1.getProductSize()));
                        updateProductForm.cmbProductSize.setSelectedItem(products1.getProductLength());
                        updateProductForm.cmbSecondSize.setEnabled(false);
                        updateProductForm.rdbLength.setSelected(true);
                    }else if(products1.getSizeType().equals("By Size")){
                        updateProductForm.cmbSecondSize.setSelectedItem(products1.getProductSize());
                        updateProductForm.txtProductSize.setEnabled(false);
                        updateProductForm.cmbProductSize.setEnabled(false);
                        updateProductForm.rdbSizes.setSelected(true);
                    }
                    updateProductForm.chkWarranty.setSelected(products1.getHasWarranty().equalsIgnoreCase("5 Month Warranty"));
                    updateProductForm.txtProductQuantity.setText(String.valueOf(products1.getProductQuantity()));
                    updateProductForm.cmbQuantity.setSelectedItem(products1.getQuantityType());
                    ImageIcon productImage = new ImageIcon(new ImageIcon(products1.getImage()).getImage().getScaledInstance(updateProductForm.lblImage.getPreferredSize().width,updateProductForm.lblImage.getPreferredSize().height,Image.SCALE_SMOOTH));
                    updateProductForm.lblImage.setIcon(productImage);
                    updateProductForm.txtProductPrice.setText(String.valueOf(products1.getProductPrice()));
                    updateProductForm.txtDescription.setText(products1.getProductDescription());
                   updateProductForm.product_image = products1.getImage();

                }
                openPanel(updateProductForm);
                btnInactive(btnAdd,btnSearch,btnTrash);
                btnActive(btnUpdate);
                revalidate();
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Please select a product on the table to Update", "Select Product",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        if(e.getSource() == btnAddStock){
            buttonAddStock();
        }
        if(e.getSource() == btnPrint) {

            printButton();
        }
    }
    public ArrayList<Products> getProductList1(){
        ArrayList<Products> productList = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String userQuery = "SELECT *,product_quantity*product_price as 'Product Amount'  FROM products where product_id ='"+ AllUsers.productID+"' order by product_name ASC";
        Statement statement;
        ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(userQuery);
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
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {

        if(e.getSource() == tblProducts){
            tblProducts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}


    private void buttonAddStock(){

        try {
            int selectedRow = tblProducts.getSelectedRow();
            TableModel tableModel = tblProducts.getModel();
            AllUsers.productID = tableModel.getValueAt(selectedRow,0).toString();
            Connection connection = ConnectDatabase.connectDB();
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String stockQuery = "Select Product_quantity from products where product_ID = '"+AllUsers.productID+"'";

            assert connection != null;
            preparedStatement = connection.prepareStatement(stockQuery);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                JDialog jDialog = new JDialog();
                jDialog.setSize(new Dimension(400,500));
                jDialog.setModal(true);
                jDialog.setLocationRelativeTo(this);
                jDialog.setResizable(false);
                jDialog.setLayout(new BorderLayout());

                JLabel lblAddStock = new JLabel();
                JLabel lblProdID = new JLabel();
                JLabel lblProdID2 = new JLabel();
                JTextField txtStock = new JTextField();
                JButton btnOk = new JButton();
                JLabel lblCurrentStock = new JLabel();
                JLabel numOfStock = new JLabel();
                JPanel addStockPanel = new JPanel();
                JLabel lblUpdatedStock = new JLabel();
                JLabel lblUpdateNumOfStock = new JLabel();

                lblAddStock.setText("Enter Number of Stock you want to add");
                lblAddStock.setFont(font);
                lblAddStock.setVerticalAlignment(SwingConstants.CENTER);
                lblAddStock.setHorizontalTextPosition(SwingConstants.CENTER);
                lblAddStock.setHorizontalAlignment(SwingConstants.CENTER);
                lblAddStock.setPreferredSize(new Dimension(400,30));

                lblProdID.setFont(font);
                lblProdID.setText(AllUsers.productID);
                lblProdID.setHorizontalAlignment(SwingConstants.CENTER);

                lblProdID2.setFont(font);
                lblProdID2.setText("Product ID: ");
                lblProdID2.setHorizontalAlignment(SwingConstants.CENTER);

                txtStock.setFont(font);
                txtStock.setPreferredSize(new Dimension(300,30));
                txtStock.setHorizontalAlignment(SwingConstants.CENTER);
                txtStock.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        try{
                            double stock = Double.parseDouble(numOfStock.getText());
                            double addStock;
                            if(txtStock.getText().isEmpty()){
                                addStock = 0.0;
                            }
                            else{
                                addStock = Double.parseDouble(txtStock.getText());
                            }
                            double updatedStock = stock+addStock;

                            if(updatedStock < 0){
                                JOptionPane.showMessageDialog(null,"Incorrect Input, Please Try Again");
                                txtStock.setText("");
                            }
                            else{
                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                lblUpdateNumOfStock.setText(String.valueOf(decimalFormat.format(updatedStock)));
                            }
                        }catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null,"Error");
                            txtStock.setText("");
                        }
                    }
                    }
                });
                txtStock.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {

                        String phoneNumber = txtStock.getText();
                        int length = phoneNumber.length();

                        if(e.getKeyChar()>='0' && e.getKeyChar()<='9'){
                            txtStock.setEditable(length < 5);
                        } else {
                            txtStock.setEditable(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE || e.getExtendedKeyCode() == KeyEvent.VK_DECIMAL || e.getExtendedKeyCode() == KeyEvent.VK_PERIOD || e.getExtendedKeyCode() == KeyEvent.VK_MINUS || e.getExtendedKeyCode() == KeyEvent.VK_SUBTRACT);
                        }

                        if(e.getKeyChar() == KeyEvent.VK_DECIMAL ||e.getKeyChar() == KeyEvent.VK_PERIOD){
                            if(txtStock.getText().contains(".")){
                                txtStock.setText(txtStock.getText());
                            }
                        }
                    }
                });
                btnOk.setText("Add");
                btnOk.setPreferredSize(new Dimension(500,50));
                btnOk.setFocusable(false);
                btnOk.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
                btnOk.setFont(font);
                btnOk.addActionListener(e -> {

                    JLabel lblConfirm = new JLabel();
                    lblConfirm.setText("Are you sure you want to Add "+txtStock.getText()+"?");
                    lblConfirm.setFont(font);
                    int update = JOptionPane.showConfirmDialog(this,lblConfirm,"Confirm Add",JOptionPane.YES_NO_OPTION);

                    if(update == JOptionPane.YES_OPTION){
                       Connection conn = ConnectDatabase.connectDB();
                       String updateStock = "Update products set Product_quantity = '"+lblUpdateNumOfStock.getText()+"' where product_ID = '"+AllUsers.productID+"'";
                        try {
                            assert conn != null;
                            PreparedStatement preparedStatement1 = conn.prepareStatement(updateStock);
                            preparedStatement1.execute();

                            String audit = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','ADDED STOCK','"+lblProdID.getText()+"')";
                            preparedStatement1 = conn.prepareStatement(audit);
                            preparedStatement1.execute();
                            lblConfirm.setText("Stock Added Successfully");
                            JOptionPane.showMessageDialog(this, lblConfirm,"Stock Added",JOptionPane.INFORMATION_MESSAGE);
                            getModel();
                            searchProduct();
                            lblPrice.setText(totalAmount()+" PHP");
                            revalidate();
                            repaint();
                            jDialog.setVisible(false);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

                });
                JPanel panelNorth = new JPanel();
                panelNorth.setLayout(new GridLayout(1,2));
                panelNorth.setBackground(centerPanel.getBackground());
                panelNorth.setPreferredSize(new Dimension(400,60));
                panelNorth.add(lblProdID2);
                panelNorth.add(lblProdID);

                JPanel panelCenter = new JPanel();
                panelCenter.setLayout(new GridLayout(8,1));
                panelCenter.setPreferredSize(new Dimension(400,300));
                panelCenter.setBackground(centerPanel.getBackground());

                JPanel panel1 = new JPanel();
                panel1.setBackground(centerPanel.getBackground());
                panelCenter.add(panel1);

                JPanel panel2 = new JPanel();
                panel2.setLayout(new GridLayout(1,2));
                panel2.setBackground(centerPanel.getBackground());
                panelCenter.add(panel2);

                lblCurrentStock.setFont(font);
                lblCurrentStock.setText("Current Stock:");
                lblCurrentStock.setHorizontalAlignment(SwingConstants.CENTER);
                panel2.add(lblCurrentStock);

                numOfStock.setFont(font);
                numOfStock.setText(resultSet.getString("Product_Quantity"));
                numOfStock.setHorizontalAlignment(SwingConstants.CENTER);
                panel2.add(numOfStock);

                addStockPanel.setLayout(new BorderLayout());
                addStockPanel.setPreferredSize(new Dimension(500,105));
                addStockPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Add Stock", TitledBorder.LEFT, TitledBorder.TOP, font, Color.BLACK));
                addStockPanel.setBackground(centerPanel.getBackground());
                addStockPanel.add(panelNorth,BorderLayout.NORTH);
                addStockPanel.add(panelCenter,BorderLayout.CENTER);
                addStockPanel.add(btnOk,BorderLayout.SOUTH);

                JPanel panel3 = new JPanel();
                panel3.setBackground(centerPanel.getBackground());
                panelCenter.add(panel3);

                JPanel panel4 = new JPanel();
                panel4.setBackground(centerPanel.getBackground());
                panel4.add(lblAddStock);
                panelCenter.add(panel4);

                JPanel panel5 = new JPanel();
                panel5.setBackground(centerPanel.getBackground());
                panel5.add(txtStock);
                panelCenter.add(panel5);

                JPanel panel6 = new JPanel();
                panel6.setBackground(centerPanel.getBackground());
                panelCenter.add(panel6);

                JPanel panel7 = new JPanel();
                panel7.setBackground(centerPanel.getBackground());
                lblUpdatedStock.setFont(font);
                lblUpdatedStock.setText("Update Stock");
                lblUpdatedStock.setHorizontalAlignment(SwingConstants.CENTER);
                panel7.add(lblUpdatedStock);
                panelCenter.add(panel7);

                JPanel panel8 = new JPanel();
                panel8.setBackground(centerPanel.getBackground());
                lblUpdateNumOfStock.setFont(font);
                lblUpdateNumOfStock.setText(numOfStock.getText());
                panel8.add(lblUpdateNumOfStock);
                panelCenter.add(panel8);
                jDialog.add(addStockPanel,BorderLayout.CENTER);
                jDialog.setVisible(true);

            }else{
              JOptionPane.showMessageDialog(this,"No Product ID: "+AllUsers.productID+" Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException e){
            JLabel lblAddStock = new JLabel();
            lblAddStock.setText("Please select an Item you want to add a Stock");
            lblAddStock.setFont(font);
            lblAddStock.setVerticalAlignment(SwingConstants.CENTER);
            lblAddStock.setHorizontalTextPosition(SwingConstants.CENTER);
            lblAddStock.setHorizontalAlignment(SwingConstants.CENTER);
            lblAddStock.setPreferredSize(new Dimension(500,30));
            JOptionPane.showMessageDialog(this, lblAddStock,"Add Stock",JOptionPane.WARNING_MESSAGE);
        }
    }
    private void generateReport(){
        JFileChooser dialog = new JFileChooser();
        dialog.setDialogTitle("Inventory Report");
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("PDF", "pdf","pdf");
        dialog.setFileFilter(fileNameExtensionFilter);
        dialog.addChoosableFileFilter(fileNameExtensionFilter);
        dialog.showSaveDialog(this);
        File saveFile = dialog.getSelectedFile();
        try{
            if (saveFile.exists() && dialog.getDialogType() == JFileChooser.SAVE_DIALOG) {
                int result = JOptionPane.showConfirmDialog(this, "File Already Exist, Overwrite this file?", "Overwrite", JOptionPane.YES_NO_OPTION);
                switch (result){
                    case JOptionPane.YES_OPTION -> savePDF(dialog);
                    case JOptionPane.NO_OPTION -> JOptionPane.showMessageDialog(this,"File not Saved","Failed",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                savePDF(dialog);
            }
        }catch (NullPointerException exception){
            JOptionPane.showMessageDialog(this,"File not Generated","Failed",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void savePDF(JFileChooser chooser){
        File file = chooser.getSelectedFile();
        TableModel tableModel = tblProducts.getModel();
        if(file != null){
            file = new File(file+".pdf");
            try {
                String filePath = file.getPath();
                Document myDocument = new Document(PageSize.LEGAL.rotate());
                PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
                PdfPTable table = new PdfPTable(columnName.length);
                myDocument.open();
                float[] columnWidths = new float[]{7, 6, 6, 7, 4, 6, 4,5,4,7,7,8};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100);
                //set table width to 100%
                Format simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                String dateToday = simpleDateFormat.format(new Date());
                myDocument.add(new Paragraph("Inventory Report", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.PLAIN)));
                myDocument.add(new Paragraph(dateToday,FontFactory.getFont(FontFactory.TIMES_ROMAN,15,Font.PLAIN)));
                myDocument.add(new Paragraph(" "));
                myDocument.add(new Paragraph("Total Price of Item List: "+lblPrice.getText(), FontFactory.getFont(String.valueOf(font))));
                myDocument.add(new Paragraph(" "));
                Font newFont = new Font("Poppins Bold", Font.PLAIN, 11);
                for (String s : columnName) {
                    table.addCell(new PdfPCell(new Paragraph(s, FontFactory.getFont(String.valueOf(newFont))))).setBorder(com.itextpdf.text.Rectangle.BOX);

                }
                Font newFont1 = new Font("Poppins SemiBold", Font.PLAIN, 10);

                for (int j = 0; j < tblProducts.getRowCount(); j++) {
                    for (int k = 0; k < tblProducts.getColumnCount(); k++) {
                        table.addCell(new PdfPCell(new Paragraph(tableModel.getValueAt(j, k).toString(), FontFactory.getFont(String.valueOf(newFont1))))).setBorder(com.itextpdf.text.Rectangle.BOX);
                    }

                }
                table.setHorizontalAlignment(SwingConstants.CENTER);
                myDocument.add(table);
                myDocument.add(new Paragraph(" "));
                myDocument.newPage();
                myDocument.close();
                JOptionPane.showMessageDialog(this, "Report was successfully generated","Report Saved",JOptionPane.INFORMATION_MESSAGE);

                openFile(file.toString());
                Connection connection = ConnectDatabase.connectDB();
                PreparedStatement preparedStatement;
                String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','GENERATE PDF','INVENTORY')";
                assert connection != null;
                preparedStatement = connection.prepareStatement(auditQuery);
                preparedStatement.execute();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }
        }
    }
    private void openFile(String file){
        try{
            File path = new File(file);
            Desktop.getDesktop().open(path);

        }catch (IOException ex){
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void excelExport(){
        try{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            jFileChooser.setDialogTitle("Export to Excel");
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Excel","xlsx","xls");
            jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);
            File saveFile = jFileChooser.getSelectedFile();

            if(saveFile != null){
                saveFile = new File(saveFile+".xlsx");
                Workbook workbook = new XSSFWorkbook();

                Sheet sheet = workbook.createSheet("Inventory");

                Row row = sheet.createRow(0);
                for (int i = 0; i < tblProducts.getColumnCount(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(tblProducts.getColumnName(i));
                }
                for (int j = 0; j < tblProducts.getRowCount(); j++) {
                    Row row1 = sheet.createRow(j+1);
                    for (int k = 0; k < tblProducts.getColumnCount(); k++) {
                        Cell cell = row1.createCell(k);
                        if(tblProducts.getValueAt(j,k)!= null){
                            cell.setCellValue(tblProducts.getValueAt(j,k).toString());
                        }
                    }
                }
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile.toString());
                workbook.write(fileOutputStream);
                workbook.close();
                fileOutputStream.close();
                JOptionPane.showMessageDialog(this,"File successfully Exported","Success",JOptionPane.INFORMATION_MESSAGE);
                openFile(saveFile.toString());
                Connection connection = ConnectDatabase.connectDB();
                PreparedStatement preparedStatement;
                String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','GENERATE EXCEL','INVENTORY')";
                assert connection != null;
                preparedStatement = connection.prepareStatement(auditQuery);
                preparedStatement.execute();

            }else{
                JOptionPane.showMessageDialog(this,"File not Export","Error",JOptionPane.ERROR_MESSAGE);
            }
        }catch (IOException exception){
            JOptionPane.showMessageDialog(this,exception.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void moveToTrash(){
        try{
        DefaultTableModel defaultTableModel = (DefaultTableModel) tblProducts.getModel();
        int selectedRow = tblProducts.getSelectedRow();
        String productID = defaultTableModel.getValueAt(selectedRow,0).toString();

        int result = JOptionPane.showConfirmDialog(this,"Are you sure you want to move this item to trash?","Move to Trash",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);

        if(result == 0){
            String getQuery = "select * from products where product_id = '"+productID+"'";
                String prodID = "";
                String prodName = "";
                String prodBrand = "";
                String prodType = "";
                String prodSize = "";
                String prodLength = "";
                String sizeType = "";
                double prodQuantity = 0;
                String prodQtyType = "";
                double prodPrice = 0;
                String prodDesc = "";
                String prodCreated = "";
                String productUpdate = "";
                Blob productImage = null;
                String hasWarranty = "";
                Connection connection = ConnectDatabase.connectDB();
                assert connection != null;
                PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    prodID = resultSet.getString("product_ID");
                    prodName = resultSet.getString("Product_Name");
                    prodBrand = resultSet.getString("Product_Brand");
                    prodType = resultSet.getString("Product_Type");
                    prodSize = resultSet.getString("Product_Size");
                    prodLength = resultSet.getString("Product_Length");
                    sizeType = resultSet.getString("Size_Type");
                    prodQuantity = resultSet.getDouble("Product_Quantity");
                    prodQtyType = resultSet.getString("Product_Quantity_Type");
                    prodPrice = resultSet.getDouble("Product_Price");
                    prodDesc = resultSet.getString("Product_Description");
                    prodCreated = resultSet.getString("Product_Created_at");
                    productUpdate = resultSet.getString("Product_Updated_at");
                    productImage = resultSet.getBlob("Product_Image");
                    hasWarranty = resultSet.getString("hasWarranty");


                }
            assert productImage != null;
            InputStream inputStream = productImage.getBinaryStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[4096];
            int length;
            while ((length = inputStream.read(buff)) != -1){
                byteArrayOutputStream.write(buff,0,length);
            }
            product_image = byteArrayOutputStream.toByteArray();
            String moveQuery = "INSERT INTO `trash_item`(`Product_ID`, `Product_Name`, `Product_Brand`, `Product_Type`, `Product_Size`, `Product_Length`, `Size_Type`, `Product_Quantity`, `Product_Quantity_Type`, `Product_Price`, `Product_Description`, `Product_Created_at`, `Product_Updated_at`,`Product_Image`,hasWarranty) VALUES " +
                        "('"+prodID+"','"+prodName+"','"+prodBrand+"','"+prodType+"','"+prodSize+"','"+prodLength+"','"+sizeType+"','"+prodQuantity+"','"+prodQtyType+"','"+prodPrice+"','"+prodDesc+"','"+prodCreated+"','"+productUpdate+"',?,'"+hasWarranty+"')";
            preparedStatement = connection.prepareStatement(moveQuery);
            preparedStatement.setBytes(1,product_image);
            preparedStatement.execute();
            String deleteQuery = "Delete from products where product_ID = '"+productID+"'";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.execute();
            String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','TRASHED PRODUCT','"+productID+"')";
            preparedStatement = connection.prepareStatement(auditQuery);
            preparedStatement.execute();
            getModel();
            searchProduct();
            lblPrice.setText(totalAmount()+" PHP");
            JOptionPane.showMessageDialog(this,"Product move to trash Successfully");

        }
            } catch (SQLException | IOException ex){
                ex.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException exception){
                JOptionPane.showMessageDialog(this,"Select an Item you want to move in trash","Select Item",JOptionPane.WARNING_MESSAGE);
            }
    }

    private void viewItem(){
        try {
            int i = tblProducts.getSelectedRow();
            TableModel model = tblProducts.getModel();
            AllUsers.productID = model.getValueAt(i, 0).toString();
            ViewProductForm viewProductForm = new ViewProductForm();

            ArrayList<Products> products = getProductList1();

            for(Products products1:products){
                viewProductForm.txtProductID.setText(products1.getProductID());
                viewProductForm.txtProductName.setText(products1.getProductName());
                viewProductForm.txtProductBrand.setText(products1.getProductBrand());
                viewProductForm.cmbProductType.setSelectedItem(products1.getProductType());

                if(products1.getSizeType().equals("By Length")){
                    viewProductForm.txtProductSize.setText(String.valueOf(products1.getProductSize()));
                    viewProductForm.cmbProductSize.setSelectedItem(products1.getProductLength());
                    viewProductForm.rdbLength.setSelected(true);
                }else if(products1.getSizeType().equals("By Size")){
                    viewProductForm.cmbSecondSize.setSelectedItem(products1.getProductSize());
                    viewProductForm.rdbSizes.setSelected(true);
                }
                viewProductForm.txtProductQuantity.setText(String.valueOf(products1.getProductQuantity()));
                viewProductForm.cmbQuantity.setSelectedItem(products1.getQuantityType());
                ImageIcon productImage = new ImageIcon(new ImageIcon(products1.getImage()).getImage().getScaledInstance(viewProductForm.lblImage.getPreferredSize().width,viewProductForm.lblImage.getPreferredSize().height,Image.SCALE_SMOOTH));
                viewProductForm.lblImage.setIcon(productImage);
                viewProductForm.txtProductPrice.setText(String.valueOf(products1.getProductPrice()));
                viewProductForm.txtDescription.setText(products1.getProductDescription());
            }
            viewProductForm.setVisible(true);
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Please select a product on the Search table to View", "Select Product",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    public void viewItemInTrash(){
        try {
            if(AllUsers.productID== null){
                JOptionPane.showMessageDialog(this,"Please select a product on the Trash Table to View","Select Product",JOptionPane.WARNING_MESSAGE);
            }else{
                ViewProductForm viewProductForm = new ViewProductForm();
                ArrayList<Products> products = new TrashPanel().getProductList1();

                for(Products products1:products){
                    viewProductForm.txtProductID.setText(products1.getProductID());
                    viewProductForm.txtProductName.setText(products1.getProductName());
                    viewProductForm.txtProductBrand.setText(products1.getProductBrand());
                    viewProductForm.cmbProductType.setSelectedItem(products1.getProductType());
                    if(products1.getSizeType().equals("By Length")){
                        viewProductForm.txtProductSize.setText(String.valueOf(products1.getProductSize()));
                        viewProductForm.cmbProductSize.setSelectedItem(products1.getProductLength());
                        viewProductForm.rdbLength.setSelected(true);
                    }else if(products1.getSizeType().equals("By Size")){
                        viewProductForm.cmbSecondSize.setSelectedItem(products1.getProductSize());
                        viewProductForm.rdbSizes.setSelected(true);
                    }
                    viewProductForm.txtProductQuantity.setText(String.valueOf(products1.getProductQuantity()));
                    viewProductForm.cmbQuantity.setSelectedItem(products1.getQuantityType());

                    ImageIcon productImage = new ImageIcon(new ImageIcon(products1.getImage()).getImage().getScaledInstance(viewProductForm.lblImage.getPreferredSize().width,viewProductForm.lblImage.getPreferredSize().height,Image.SCALE_SMOOTH));
                    viewProductForm.lblImage.setIcon(productImage);
                    viewProductForm.txtProductPrice.setText(String.valueOf(products1.getProductPrice()));
                    viewProductForm.txtDescription.setText(products1.getProductDescription());

                }

                viewProductForm.setVisible(true);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Please select a product on the Trash table to View", "Select Product",
                    JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }
    }

    public String totalAmount(){

        Connection connection = ConnectDatabase.connectDB();
        PreparedStatement preparedStatement;
        String totalPrice = "";
        double price;
        double stock;
        double amount;
        double total = 0;

//
        String totalQuery = "select * from products where Product_ID like '%' '"+txtSearch.getText()+"' '%' or Product_Name like '%' '"+txtSearch.getText()+"' '%' or Product_Brand like '%' '"+txtSearch.getText()+"' '%' or Product_Type like '%' '"+txtSearch.getText()+"' '%' ";

        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(totalQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                price = resultSet.getDouble("Product_Price");
                stock = resultSet.getDouble("Product_Quantity");
                amount = price*stock;
                total = amount + total;

            }
            totalPrice = String.valueOf(total);
    }   catch (SQLException e) {
            e.printStackTrace();

        }
        return totalPrice;
    }
    private void printButton() {

            try {
                int selectedRow = tblProducts.getSelectedRow();
                AllUsers.prodID = tblProducts.getValueAt(selectedRow, 0).toString();
                AllUsers.prodName = tblProducts.getValueAt(selectedRow, 1).toString();
                AllUsers.prodSize = tblProducts.getValueAt(selectedRow, 4).toString();
                AllUsers.productDescription = getDescription(AllUsers.prodID);

                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to print?", "Proceed", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                PrintID ps = new PrintID();
                Object[][] printItem = ps.getTableData(tblProducts);
                PrintID.setItems(printItem);
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintable(new PrintID.MyPrintable(), PrintID.getPageFormat(pj));
                try {
                    pj.print();

                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
                }
            } catch (IndexOutOfBoundsException exception) {
                JOptionPane.showMessageDialog(null, "Please select a product to print");
            }

    }
    private String getDescription(String id){
        String description = "";
        String descQuery = "Select product_description from products where product_ID ='"+id+"'";

        Connection connection = ConnectDatabase.connectDB();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(descQuery);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                description = resultSet.getString("product_description");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  description;
    }

}
