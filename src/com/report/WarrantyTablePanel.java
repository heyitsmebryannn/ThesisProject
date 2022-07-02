package com.report;

import com.Sales.WarrantyList;
import com.thesis.ConnectDatabase;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WarrantyTablePanel {
    public void showWarrantyListInTable(JTable table,JTextField txtSearch,JDateChooser dateFrom,JDateChooser dateTo){
        ArrayList<WarrantyList> warrantyLists = getWarrantyList(txtSearch,dateFrom,dateTo);
        getModel(table);
        Object[] column = new Object[14];
        DefaultTableModel warrantyTableModel = (DefaultTableModel) table.getModel();

        for (WarrantyList warrantyList : warrantyLists) {
            column[0] = warrantyList.warrantyID();
            column[1] = warrantyList.cashierID();
            column[2] = warrantyList.transactionID();
            column[3] = warrantyList.custName();
            column[4] = warrantyList.custAddress();
            column[5] = warrantyList.prodID();
            column[6] = warrantyList.prodName();
            column[7] = warrantyList.prodQuantity();
            column[8] = warrantyList.prodPrice();
            column[9] = warrantyList.prodAmount();
            column[10] = warrantyList.warrantyReason();
            column[11] = warrantyList.remarks();
            column[12] = warrantyList.warrantyETA();
            column[13] = warrantyList.dateUsed();
            warrantyTableModel.addRow(column);
        }
    }
    public ArrayList<WarrantyList> getWarrantyList(JTextField txtSearch,JDateChooser dateFrom,JDateChooser dateTo){
        ArrayList<WarrantyList> warrantyLists = new ArrayList<>();

        try {
            Connection con = ConnectDatabase.connectDB();
            Format format = new SimpleDateFormat("yyyy-MM-dd");
            String dateFrom1 = format.format(dateFrom.getDate());
            String dateTo1 = format.format(dateTo.getDate());
            String refundItemQuery  = "Select * from pos_warranty where  `TransactionID` like '%' '"+txtSearch.getText()+"' '%' or Customer_Name like '%' '"+txtSearch.getText()+"' '%' or Remarks like '%' '"+txtSearch.getText()+"' '%' having dateUsed between '"+dateFrom1+"' and '"+dateTo1+"' ";
            Statement statement;
            ResultSet resultSet;

            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(refundItemQuery);
            WarrantyList warrantyList;
            while(resultSet.next()){
                warrantyList = new WarrantyList(resultSet.getString("warranty_ID"),resultSet.getString("user_ID"),resultSet.getString("TransactionID"),resultSet.getString("Customer_Name"),resultSet.getString("Customer_Address"),resultSet.getString("Product_ID"),resultSet.getString("ProductName"),resultSet.getString("Product_quantity"),resultSet.getString("Product_Price"),resultSet.getString("Product_Amount"),resultSet.getString("Warranty_Reason"),resultSet.getString("Remarks"),resultSet.getString("warranty_ETA"),resultSet.getString("dateUsed"));
                warrantyLists.add(warrantyList);
            }
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(null,"Invalid Date!, Please select another Date");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return warrantyLists;
    }
    public void getModel(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
    }
    void transactionTable(JTable table, String transactID) {

        ArrayList<TransactionDetail> transactionDetails = getTransactionList(transactID);
        DefaultTableModel transactionListModel = (DefaultTableModel) table.getModel();
        getModel(table);

        Object[] column = new Object[8];

        for (TransactionDetail transactionDetail : transactionDetails) {
            column[0] = transactionDetail.getProductID();
            column[1] = transactionDetail.getProductName();
            column[2] = transactionDetail.getProductPrice();
            column[3] = transactionDetail.getProductQuantity();
            column[4] = transactionDetail.getTotalAmount();
            column[5] = transactionDetail.getWarranty();
            column[6] = transactionDetail.getWarrantyDuration();
            column[7] = transactionDetail.getWarrantyStatus();
            transactionListModel.addRow(column);
        }
    }

    public ArrayList<TransactionDetail> getTransactionList(String transactID) {
        ArrayList<TransactionDetail> transactionDetails = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String TransactionQuery = "SELECT * FROM Transaction_list where transaction_ID = '" + transactID + "'";
        Statement statement;
        ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(TransactionQuery);
            TransactionDetail transactionDetail;
            while (resultSet.next()) {
                transactionDetail = new TransactionDetail(resultSet.getString("Product_ID"), resultSet.getString("Product_Name"), resultSet.getInt("Product_Price"), resultSet.getDouble("Product_Quantity"), resultSet.getInt("Product_Amount"),resultSet.getString("Product_Warranty"),resultSet.getString("Warranty_Duration"),resultSet.getString("Warranty_status"));
                transactionDetails.add(transactionDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionDetails;
    }

    public void showDataInTable(JTable table) {

        ArrayList<TransactionReport> transactionReports = getTransactionReport();
        getModel(table);
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        Object[] column = new Object[5];

        for (TransactionReport transactionReport : transactionReports) {
            column[0] = transactionReport.getTransactionID();
            column[1] = transactionReport.getUserID();
            column[2] = transactionReport.getCustName();
            column[3] = transactionReport.getGrandTotal();
            column[4] = transactionReport.getTransactionDate();

            defaultTableModel.addRow(column);
        }
    }
    public ArrayList<TransactionReport> getTransactionReport() {
        ArrayList<TransactionReport> transactionReports = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String TransactionQuery = "SELECT * FROM TransactionReport order by transaction_date DESC";
        Statement statement;
        ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(TransactionQuery);
            TransactionReport transactionReport;
            while (resultSet.next()) {
                transactionReport = new TransactionReport(resultSet.getString("Transaction_ID"), resultSet.getString("user_ID"), resultSet.getString("Customer_Name"), resultSet.getInt("Transaction_Total"), resultSet.getString("Transaction_Date"));
                transactionReports.add(transactionReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionReports;
    }

    void searchTransact(JDateChooser dateFrom,JDateChooser dateTo,JTextField txtSearch,JTable table) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        ArrayList<TransactionReport> transactReport = new ArrayList<>();
        try {
            Connection con = ConnectDatabase.connectDB();
            Format format = new SimpleDateFormat("yyyy-MM-dd");
            String dateFrom1 = format.format(dateFrom.getDate());
            String dateTo1 = format.format(dateTo.getDate());
            String TransactionQuery = "SELECT * FROM TransactionReport where transaction_ID like '%' '" + txtSearch.getText() + "' '%' or user_ID like '%' '" + txtSearch.getText() + "' '%' or customer_Name like '%' '" + txtSearch.getText() + "' '%' having transaction_date between '" + dateFrom1 + "' and '" + dateTo1 + "' order by transaction_date DESC";

            Statement statement;
            ResultSet resultSet;
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(TransactionQuery);
            TransactionReport transactReports;
            while (resultSet.next()) {

                transactReports = new TransactionReport(resultSet.getString("Transaction_ID"), resultSet.getString("user_ID"), resultSet.getString("Customer_Name"), resultSet.getInt("Transaction_Total"), resultSet.getString("Transaction_Date"));
                transactReport.add(transactReports);


            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Invalid date! Please select another date", "Invalid", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getModel(table);
        Object[] column = new Object[5];

        for (TransactionReport transactionReport : transactReport) {
            column[0] = transactionReport.getTransactionID();
            column[1] = transactionReport.getUserID();
            column[2] = transactionReport.getCustName();
            column[3] = transactionReport.getGrandTotal();
            column[4] = transactionReport.getTransactionDate();
            defaultTableModel.addRow(column);
        }

    }

}
