package com.report;

import com.thesis.ConnectDatabase;
import com.toedter.calendar.JDateChooser;
import com.userInfo.AllUsers;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class WarrantyPanel extends JPanel {
        JLabel lblUserID,lblTransactionID,lblCustomerName, lblCustomerAddress,lblProductID,lblProductName;
        JLabel lblProductQuantity,lblProductPrice,lblProductAmount,lblWarrantyReason,lblRemark,lblETA;
        JTextField txtUserID,txtTransactionID,txtCustomerName,txtProductID,txtProductName;
        JTextField txtProductQuantity,txtProductPrice,txtProductAmount;
        JTextArea txtCustomerAddress;
        JComboBox<String> cmbWarrantyReason,cmbRemark;
        JButton btnSubmit,btnCancel;
        JDateChooser dateETA;

        Font font = new Font("Poppins SemiBold",Font.PLAIN,15);
        Font titleFont = new Font("Poppins SemiBold",Font.PLAIN,25);



    public WarrantyPanel(JDialog jDialog){
        this.setPreferredSize(new Dimension(900,600));
        this.setOpaque(false);
        this.setLayout(null);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Warranty", TitledBorder.LEFT, TitledBorder.TOP, titleFont, Color.BLACK));
        createUIComponents(jDialog);
    }
    private void createUIComponents(JDialog dialog){
        lblUserID = new JLabel();
        lblUserID.setText("Cashier ID");
        lblUserID.setFont(font);
        lblUserID.setBounds(20,50,200,30);
        this.add(lblUserID);
        
        txtUserID = new JTextField();
        txtUserID.setFont(font);
        txtUserID.setEditable(false);
        txtUserID.setBounds(20,80,250,30);
        this.add(txtUserID);

        lblTransactionID = new JLabel();
        lblTransactionID.setText("Transaction ID");
        lblTransactionID.setFont(font);
        lblTransactionID.setBounds(320,50,200,30);
        this.add(lblTransactionID);

        txtTransactionID = new JTextField();
        txtTransactionID.setFont(font);
        txtTransactionID.setBounds(320,80,250,30);
        txtTransactionID.setEditable(false);
        this.add(txtTransactionID);

        lblCustomerName = new JLabel();
        lblCustomerName.setText("Customer Name");
        lblCustomerName.setFont(font);
        lblCustomerName.setBounds(20,160,250,30);
        this.add(lblCustomerName);

        txtCustomerName = new JTextField();
        txtCustomerName.setFont(font);
        txtCustomerName.setBounds(20,190,250,30);
        txtCustomerName.setEditable(false);
        this.add(txtCustomerName);

        lblCustomerAddress = new JLabel();
        lblCustomerAddress.setText("Customer Address");
        lblCustomerAddress.setFont(font);
        lblCustomerAddress.setBounds(20,230,200,30);
        this.add(lblCustomerAddress);

        txtCustomerAddress = new JTextArea();
        txtCustomerAddress.setFont(font);
        txtCustomerAddress.setEditable(false);
        txtCustomerAddress.setWrapStyleWord(true);
        txtCustomerAddress.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(txtCustomerAddress,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(20,260,250,200);
        this.add(scrollPane);

        lblProductID = new JLabel();
        lblProductID.setText("Product ID");
        lblProductID.setFont(font);
        lblProductID.setBounds(320,160,200,30);
        this.add(lblProductID);

        txtProductID = new JTextField();
        txtProductID.setFont(font);
        txtProductID.setBounds(320,190,250,30);
        txtProductID.setEditable(false);
        this.add(txtProductID);

        lblProductName = new JLabel();
        lblProductName.setText("Product Name");
        lblProductName.setFont(font);
        lblProductName.setBounds(320,240,200,30);
        this.add(lblProductName);

        txtProductName = new JTextField();
        txtProductName.setFont(font);
        txtProductName.setBounds(320,270,250,30);
        txtProductName.setEditable(false);
        this.add(txtProductName);


        lblProductQuantity = new JLabel();
        lblProductQuantity.setText("Product Quantity");
        lblProductQuantity.setFont(font);
        lblProductQuantity.setBounds(320,310,200,30);
        this.add(lblProductQuantity);

        txtProductQuantity = new JTextField();
        txtProductQuantity.setFont(font);
        txtProductQuantity.setBounds(320,340,250,30);
        txtProductQuantity.setEditable(false);
        this.add(txtProductQuantity);


        lblProductPrice = new JLabel();
        lblProductPrice.setText("Product Price");
        lblProductPrice.setFont(font);
        lblProductPrice.setBounds(320,380,200,30);
        this.add(lblProductPrice);

        txtProductPrice = new JTextField();
        txtProductPrice.setFont(font);
        txtProductPrice.setBounds(320,410,250,30);
        txtProductPrice.setEditable(false);
        this.add(txtProductPrice);

        lblProductAmount = new JLabel();
        lblProductAmount.setText("Product Amount");
        lblProductAmount.setFont(font);
        lblProductAmount.setBounds(320,450,200,30);
        this.add(lblProductAmount);

        txtProductAmount = new JTextField();
        txtProductAmount.setFont(font);
        txtProductAmount.setBounds(320,480,250,30);
        txtProductAmount.setEditable(false);
        this.add(txtProductAmount);


        lblWarrantyReason = new JLabel();
        lblWarrantyReason.setText("Warranty Reason");
        lblWarrantyReason.setFont(font);
        lblWarrantyReason.setBounds(620,50,200,30);
        this.add(lblWarrantyReason);
        
        String[] reason = {"Defected","Need Repair"};
        cmbWarrantyReason = new JComboBox<>(reason);
        cmbWarrantyReason.setFont(font);
        cmbWarrantyReason.setSelectedItem(null);
        cmbWarrantyReason.setFocusable(false);
        cmbWarrantyReason.setBounds(620,80,250,30);
        this.add(cmbWarrantyReason);

        lblETA = new JLabel();
        lblETA.setText("Estimated Time to Complete");
        lblETA.setFont(font);
        lblETA.setBounds(620,160,250,30);
        this.add(lblETA);

        Date date = new Date();
        dateETA = new JDateChooser();
        dateETA.setFont(font);
        dateETA.setDate(date);
        dateETA.setDateFormatString("yyyy-MM-dd");
        dateETA.setBounds(620,190,250,30);
        dateETA.setMinSelectableDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);
        dateETA.setMaxSelectableDate(calendar.getTime());
        this.add(dateETA);

        lblRemark = new JLabel();
        lblRemark.setText("Remark");
        lblRemark.setFont(font);
        lblRemark.setBounds(620,220,200,30);
        this.add(lblRemark);

        String[] remarks ={"Pending","Replaced","Repaired"};
        cmbRemark = new JComboBox<>(remarks);
        cmbRemark.setFont(font);
        cmbRemark.setBounds(620,250,250,30);
        this.add(cmbRemark);

        btnSubmit = new JButton();
        btnSubmit.setText("Submit");
        btnSubmit.setFocusable(false);
        btnSubmit.setFont(font);
        btnSubmit.setBounds(660,410,150,40);
        btnSubmit.addActionListener(e -> submitButton(dialog));
        this.add(btnSubmit);

        btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.setFocusable(false);
        btnCancel.setFont(font);
        btnCancel.setBounds(660,470,150,40);
        btnCancel.addActionListener(e -> dialog.setVisible(false));
        this.add(btnCancel);
    }


    private void submitButton(JDialog dialog) {
        try {

            if (!Objects.equals(cmbRemark.getSelectedItem(), null) && (!Objects.equals(cmbWarrantyReason.getSelectedItem(), null))) {
                int result = JOptionPane.showConfirmDialog(null, "Proceed?", "Confirm Submit", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    String cashierID = txtUserID.getText();
                    String transactID = txtTransactionID.getText();
                    String custName = txtCustomerName.getText();
                    String custAddress = txtCustomerAddress.getText();
                    String prodID = txtProductID.getText();
                    String prodName = txtProductName.getText();
                    String prodQuantity = txtProductQuantity.getText();
                    String prodPrice = txtProductPrice.getText();
                    String prodAmount = txtProductAmount.getText();
                    String warrantyReason = cmbWarrantyReason.getSelectedItem().toString();
                    String remark = cmbRemark.getSelectedItem().toString();
                    Format format = new SimpleDateFormat("yyyy-MM-dd");
                    String dateFormat = format.format(dateETA.getDate());


                    Connection connection = ConnectDatabase.connectDB();
                    PreparedStatement preparedStatement;
                    Date date = new Date();
                    String dateUsed = format.format(date);

                    String submitQuery = "INSERT INTO `pos_warranty`(`user_ID`, `TransactionID`, `Customer_Name`, `Customer_Address`, `Product_ID`, `ProductName`, `Product_Quantity`, `Product_Price`, `Product_Amount`, `Warranty_Reason`, `remarks`,`Warranty_ETA`,`dateUsed`) VALUES ('" + cashierID + "','" + transactID + "','" + custName + "','" + custAddress + "','" + prodID + "','" + prodName + "','" + prodQuantity + "','" + prodPrice + "','" + prodAmount + "','" + warrantyReason + "','" + remark + "','"+dateFormat+"','"+dateUsed+"')";
                    assert connection != null;
                    preparedStatement = connection.prepareStatement(submitQuery);
                    preparedStatement.execute();

                    if(cmbRemark.getSelectedItem().equals("Replaced")){
                        updateQuantity(prodID,prodQuantity);
                        updateWarrantyDuration(transactID,prodID);
                    }
                    else{
                        updateWarrantyStatus(transactID,prodID);
                    }

                    saveToAudit();



                    JOptionPane.showMessageDialog(null,"The item has now being processed,Please see Warranty table for Updates","Warranty Used",JOptionPane.INFORMATION_MESSAGE);
                    dialog.setVisible(false);


                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill up the empty fields");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateQuantity(String productID,String currentQuantity){
        try{
            Connection connection = ConnectDatabase.connectDB();
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String prodQuantity;
            double currentProductQuantity = Double.parseDouble(currentQuantity);
            double updateQuantity = 0;

            String getProductQuantityQuery = "Select Product_Quantity from products where product_ID = '"+productID+"'";
            assert connection != null;
            preparedStatement = connection.prepareStatement(getProductQuantityQuery);
            resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                prodQuantity = resultSet.getString("Product_quantity");
                updateQuantity = Double.parseDouble(prodQuantity) - currentProductQuantity;
            }
            String updateProductQuery = "Update products set product_quantity = '"+updateQuantity+"' where product_ID = '"+productID+"'";

            preparedStatement = connection.prepareStatement(updateProductQuery);
            preparedStatement.execute();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void saveToAudit(){
        try {
            Connection connection = ConnectDatabase.connectDB();
            PreparedStatement preparedStatement;
            String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+ AllUsers.userID+"','"+AllUsers.position+"','Warranty Used','"+txtProductID.getText()+"')";
            assert connection != null;
            preparedStatement = connection.prepareStatement(auditQuery);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    private void updateWarrantyDuration(String transactionID,String productID){
        try{
            Connection connection = ConnectDatabase.connectDB();
            PreparedStatement preparedStatement;

            String  warrantyDuration = "DATE_ADD(CURRENT_DATE, INTERVAL 5 MONTH)";

            String updateWarrantyStatusQuery = "Update transaction_list set Warranty_Duration = '"+warrantyDuration+"', where transaction_ID = '"+transactionID+"' and Product_ID ='"+productID+"'";
            assert connection != null;
            preparedStatement = connection.prepareStatement(updateWarrantyStatusQuery);
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }private void updateWarrantyStatus(String transactionID,String productID){
        try{
            Connection connection = ConnectDatabase.connectDB();
            PreparedStatement preparedStatement;

            String  warrantyStatus = "Pending";

            String updateWarrantyStatusQuery = "Update transaction_list set Warranty_Status = '"+warrantyStatus+"' where transaction_ID = '"+transactionID+"' and Product_ID ='"+productID+"'";
            assert connection != null;
            preparedStatement = connection.prepareStatement(updateWarrantyStatusQuery);
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
