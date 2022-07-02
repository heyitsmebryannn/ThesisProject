package com.POS;

import com.thesis.ConnectDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CartTable {

    public ArrayList<CartRecord> getCartList(){
        ArrayList<CartRecord> cartRecords = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();

        String refundItemQuery  = "Select * from cart";
        Statement statement;
        ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(refundItemQuery);
            CartRecord cartRecord;
            while(resultSet.next()){
                cartRecord = new CartRecord(resultSet.getString("Product_ID"),resultSet.getString("Product_Name"),resultSet.getDouble("Product_Price"),resultSet.getDouble("Product_Quantity"),resultSet.getDouble("Total_Amount"),resultSet.getString("Product_Warranty"));
                cartRecords.add(cartRecord);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cartRecords;
    }
    public void getModel(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
    }

    void cartTable(JTable table) {

        ArrayList<CartRecord> cartRecords = getCartList();
        DefaultTableModel cartTableModel = (DefaultTableModel) table.getModel();
        getModel(table);

        Object[] column = new Object[6];

        for (CartRecord cartRecord : cartRecords) {
            column[0] = cartRecord.productID();
            column[1] = cartRecord.ProductName();
            column[2] = cartRecord.productPrice();
            column[3] = cartRecord.productQuantity();
            column[4] = cartRecord.productAmount();
            column[5] = cartRecord.warranty();

            cartTableModel.addRow(column);
        }
    }
}
