package com.Sales;

import com.thesis.ConnectDatabase;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProductSalePanel extends JPanel {
    JLabel lblProductSale, lblTotalSale, lblTotal;
    JTable tblProductSale;
    DefaultTableModel defaultTableModel;
    String[] productHead = {"Product Name","Date","Total Quantity","Price","Total Amount"};
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateFrom1,dateTo1;
    DecimalFormat decimalFormat;
    public ProductSalePanel(JDateChooser dateFrom,JDateChooser dateTo){
        this.setLayout(null);
        this.setOpaque(false);
        this.setSize(1050,300);
        createUIComponents(dateFrom,dateTo);

    }
    private void createUIComponents(JDateChooser dateFrom,JDateChooser dateTo){
        Font font = new Font("Poppins SemiBold",Font.PLAIN,20);
        lblProductSale = new JLabel();
        lblProductSale.setText("Product Sale");
        lblProductSale.setFont(font);
        lblProductSale.setBounds(10,20,200,20);

        lblTotalSale = new JLabel();
        lblTotalSale.setText("Total Product Sale: ");
        lblTotalSale.setFont(font);
        lblTotalSale.setBounds(300,20,200,20);

        lblTotal = new JLabel();
        lblTotal.setFont(font);
        lblTotal.setBounds(510,20,200,20);

        this.add(lblProductSale);
        this.add(lblTotalSale);
        this.add(lblTotal);
        defaultTableModel = new DefaultTableModel(null,productHead);
        tblProductSale = new JTable(defaultTableModel){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < tblProductSale.getColumnModel().getColumnCount(); i++) {
            tblProductSale.getColumnModel().getColumn(i).setResizable(false);
            tblProductSale.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblProductSale.getTableHeader().setReorderingAllowed(false);
        tblProductSale.setFont(new Font("Poppins SemiBold",Font.PLAIN,12));
        tblProductSale.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductSale.setRowHeight(20);
        for (int i = 0; i < tblProductSale.getColumnCount(); i++) {
            tblProductSale.getColumnModel().getColumn(i).setPreferredWidth(120);
        }
        JScrollPane scrollPane = new JScrollPane(tblProductSale);
        tblProductSale.getTableHeader().setFont(new Font("Poppins SemiBold",Font.PLAIN,12));
        tblProductSale.getTableHeader().setBackground(Color.GRAY);
        tblProductSale.getTableHeader().setForeground(Color.WHITE);
        tblProductSale.getTableHeader().setPreferredSize(new Dimension(scrollPane.getHeight(),25));
        scrollPane.setBounds(10,40,1000,200);
        this.add(scrollPane);
        showProductSaleInTable(dateFrom,dateTo);

        lblTotal.setText(totalProductSale());
    }
    public void getModel(JTable table){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
    }
    public ArrayList<ProductSale> getProductSale(JDateChooser dateFrom,JDateChooser dateTo){
        ArrayList<ProductSale> productSales = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        dateFrom1 = simpleDateFormat.format(dateFrom.getDate());
        dateTo1 = simpleDateFormat.format(dateTo.getDate());
        String productSaleQuery = "SELECT transaction_list.Product_Name as 'Product Name' ,transactionreport.Transaction_Date as 'Date', transaction_list.Product_Price as 'Product Price', sum(transaction_list.Product_Quantity) as 'Product Quantity', sum(transaction_list.Product_Amount) as 'Total Amount' from transactionreport INNER join transaction_list on transactionreport.Transaction_ID = transaction_list.Transaction_ID WHERE transactionreport.Transaction_Date BETWEEN '"+dateFrom1+"' and '"+dateTo1+"' GROUP by transaction_list.Product_Name;";
        Statement statement;
        ResultSet resultSet;
        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(productSaleQuery);
            ProductSale productSale;
            while(resultSet.next()){
                productSale = new ProductSale(resultSet.getString("Product Name"),resultSet.getString("Date"),resultSet.getString("Product Quantity"),resultSet.getString("Product Price"),resultSet.getString("Total Amount"));
                productSales.add(productSale);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return productSales;
    }

    public void showProductSaleInTable(JDateChooser dateFrom,JDateChooser dateTo){
        ArrayList<ProductSale> productSales = getProductSale(dateFrom,dateTo);
        getModel(tblProductSale);
        Object[] column = new Object[8];
        for (ProductSale productSale : productSales) {
            column[0] = productSale.productName();
            column[1] = productSale.date();
            column[2] = productSale.productQuantity();
            column[3] = productSale.productPrice();
            column[4] = productSale.totalAmount();
            defaultTableModel.addRow(column);
        }
        lblTotal.setText(totalProductSale());
    }
    private String totalProductSale(){
        double total = 0;
        double amount;
        for (int i = 0; i < tblProductSale.getRowCount(); i++) {
            amount = Double.parseDouble(tblProductSale.getValueAt(i,4).toString());
            total = total+amount;
        }
        decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(total);
    }
}
