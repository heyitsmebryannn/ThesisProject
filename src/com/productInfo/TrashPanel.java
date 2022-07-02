package com.productInfo;

import com.thesis.ConnectDatabase;
import com.userInfo.AllUsers;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;


public class TrashPanel extends JPanel {


    JPanel centerPanel,southPanel;
    JButton btnView,btnRestore,btnDelete,btnEmpty;
    JTextField txtSearch;
    JTable tblProducts;
    Font font = new Font("Poppins SemiBold",Font.PLAIN,15);
    JScrollPane scrollPane;
    String[] columnName ={"Product ID","Product Name","Product Brand"," Product Type","Product Size","Length Type","Stock","Type","Price","Amount"};
    private final DefaultTableModel tableModel = new DefaultTableModel(null,columnName);
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    byte[] product_image = null;



    public TrashPanel(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(dimension.width-300,dimension.height-200));
        setBackground(new Color(202,170,86));
        createUIComponents();
    }
    private void createUIComponents(){

        centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setOpaque(true);
        centerPanel.setBackground(new Color(202,170,86));
        centerPanel.setBorder(new LineBorder(Color.BLACK,5,false));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(dimension.width-300,dimension.height-200));

        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.setPreferredSize(new Dimension(dimension.width-300,100));


        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width,100));
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(centerPanel.getBackground());
        centerPanel.add(panel1,BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("      Trash Product");
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
//        txtSearch.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                searchProduct();
//            }
//        });
        panel2.add(txtSearch,BorderLayout.CENTER);


        JPanel panel6 = new JPanel();
        panel6.setPreferredSize(new Dimension(400,30));
        panel6.setBackground(centerPanel.getBackground());
        panel2.add(panel6,BorderLayout.EAST);


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



        tblProducts = new JTable(tableModel){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        for (int i = 0;i<tblProducts.getColumnCount();i++){
            tblProducts.getColumnModel().getColumn(i).setPreferredWidth(150);
        }


        tblProducts.setRowHeight(50);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        for (int i = 0; i < tblProducts.getColumnModel().getColumnCount(); i++) {
            tblProducts.getColumnModel().getColumn(i).setResizable(false);
            tblProducts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        tblProducts.getTableHeader().setReorderingAllowed(false);
        tblProducts.getTableHeader().setFont(new Font("Poppins SemiBold",Font.BOLD,11));
        tblProducts.setFont(new Font("Poppins SemiBold",Font.BOLD,13));
        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProducts.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getModel();
        showDataInTable();
        tblProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblProducts.getSelectedRow();
                TableModel model = tblProducts.getModel();
                AllUsers.productID = model.getValueAt(selectedRow,0).toString();
            }
        });

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
        panel17.add(btnView);


        btnDelete = new JButton();
        btnDelete.setText("Delete Product");
        btnDelete.setFocusable(false);
        btnDelete.setFont(font);
        btnDelete.setHorizontalAlignment(SwingConstants.CENTER);
        btnDelete.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,"The product will delete permanently.Continue?","Delete Product",JOptionPane.YES_NO_OPTION);
            if(result == 0){
                deleteProduct();
            }
        });
        panel17.add(btnDelete);

        btnRestore = new JButton();
        btnRestore.setText("Restore Product");
        btnRestore.setFocusable(false);
        btnRestore.setFont(font);
        btnRestore.setHorizontalAlignment(SwingConstants.CENTER);
        btnRestore.addActionListener(e ->{
            int result = JOptionPane.showConfirmDialog(this,"Are you sure you want to restore this Product?","Restore",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);

            if(result == 0){
                restoreProduct();
            }
            else{
                JOptionPane.showMessageDialog(this,"Product not Restored","Restore Product",JOptionPane.WARNING_MESSAGE);
            }
        });
        panel17.add(btnRestore);

        btnEmpty = new JButton();
        btnEmpty.setText("Empty Trash");
        btnEmpty.setFont(font);
        btnEmpty.setHorizontalAlignment(SwingConstants.CENTER);
        panel17.add(btnEmpty);
        btnEmpty.addActionListener(e-> {
            int result = JOptionPane.showConfirmDialog(this,"Are you sure you want to empty trash?","Empty Trash",JOptionPane.YES_NO_OPTION);
            if(result == 0){
                emptyTrash();
            }
        });

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel17.add(panel);

        this.add(centerPanel);
    }

    public void getModel(){
        DefaultTableModel tableModel = (DefaultTableModel) tblProducts.getModel();
        tableModel.setRowCount(0);
    }
    private void showDataInTable(){
        ArrayList<Products> productList = getProductList();
        tableModel.setRowCount(0);
        Object[] column = new Object[10];

        for(Products listProduct:productList){
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


            tableModel.addRow(column);
        }
    }
    public ArrayList<Products> getProductList(){
        ArrayList<Products> productList = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String productQuery = "SELECT *,product_quantity*product_price as 'Product Amount' FROM trash_item";
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
    public ArrayList<Products> getProductList1(){
        ArrayList<Products> productList = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String userQuery = "SELECT *,product_quantity*product_price as 'Product Amount' FROM trash_item where product_id ='"+AllUsers.productID+"'";
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

    private void restoreProduct(){
        try{
            DefaultTableModel defaultTableModel = (DefaultTableModel) tblProducts.getModel();
            int selectedRow = tblProducts.getSelectedRow();
            String productID = defaultTableModel.getValueAt(selectedRow,0).toString();

                String getQuery = "select * from trash_item where product_id = '"+productID+"'";

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

                String moveQuery = "INSERT INTO `products`(`Product_ID`, `Product_Name`, `Product_Brand`, `Product_Type`, `Product_Size`, `Product_Length`, `Size_Type`, `Product_Quantity`, `Product_Quantity_Type`, `Product_Price`, `Product_Description`, `Product_Created_at`, `Product_Updated_at`,`Product_Image`,`hasWarranty`) VALUES " +
                        "('"+prodID+"','"+prodName+"','"+prodBrand+"','"+prodType+"','"+prodSize+"','"+prodLength+"','"+sizeType+"','"+prodQuantity+"','"+prodQtyType+"','"+prodPrice+"','"+prodDesc+"','"+prodCreated+"','"+productUpdate+"',?,'"+hasWarranty+"')";

                preparedStatement = connection.prepareStatement(moveQuery);
                preparedStatement.setBytes(1,product_image);
                preparedStatement.execute();

                String deleteQuery = "Delete from trash_item where product_ID = '"+productID+"'";
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.execute();

                String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','RESTORE PRODUCT','"+productID+"')";
                preparedStatement = connection.prepareStatement(auditQuery);
                preparedStatement.execute();

                getModel();
                showDataInTable();


                JOptionPane.showMessageDialog(this,"Product Restored Successfully","Restored",JOptionPane.INFORMATION_MESSAGE);



        } catch (SQLException | IOException ex){
            ex.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException exception){
            JOptionPane.showMessageDialog(this,"Select a product you want to restore!","Select Product",JOptionPane.WARNING_MESSAGE);
        }
    }


    private void emptyTrash(){
        Connection connection = ConnectDatabase.connectDB();
        PreparedStatement preparedStatement;

        String emptyQuery = "Delete from trash_item";

        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(emptyQuery);
            preparedStatement.execute();
            String auditQuery = "INSERT INTO AUDIT_TRAIL(USER_ID,USER_POSITION,ACTION,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','EMPTIED TRASH','TRASH TABLE')";
            preparedStatement = connection.prepareStatement(auditQuery);
            preparedStatement.execute();

            getModel();
            showDataInTable();


            JOptionPane.showMessageDialog(this,"Trash Emptied","Emptied",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void deleteProduct(){
        try{
            DefaultTableModel defaultTableModel = (DefaultTableModel) tblProducts.getModel();
            int selectedRow = tblProducts.getSelectedRow();
            String productID = defaultTableModel.getValueAt(selectedRow,0).toString();
            Connection connection = ConnectDatabase.connectDB();
            PreparedStatement preparedStatement;

            String deleteQuery = "Delete from trash_item where product_ID = '"+productID+"'";
            assert connection != null;
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.execute();
            String auditQuery = "INSERT INTO AUDIT_TRAIL(USER_ID,USER_POSITION,ACTION,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','DELETE PRODUCT','"+productID+"')";
            preparedStatement = connection.prepareStatement(auditQuery);
            preparedStatement.execute();
            getModel();
            showDataInTable();
            JOptionPane.showMessageDialog(this,"Product deleted permanently!","Deleted Product",JOptionPane.INFORMATION_MESSAGE);
        }catch (SQLException e){
            e.printStackTrace();

        }catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            JOptionPane.showMessageDialog(this,"Please select a product you want to delete","Select Product",JOptionPane.WARNING_MESSAGE);
        }

    }

}
