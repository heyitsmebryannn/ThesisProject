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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RevenuePanel extends JPanel {
    JLabel lblRevenueSale,lblTotalRevenue,lblTotal;
    JTable tblRevenueSale;
    DefaultTableModel defaultTableModel1;
    String[] revenueHead = {"Date", "Total Quantity", "Total Amount"};
    String dateTo1,dateFrom1;
    Format simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
    DecimalFormat decimalFormat;

    public  RevenuePanel(JDateChooser dateFrom,JDateChooser dateTo){
        this.setLayout(null);
        this.setOpaque(false);
        this.setSize(1050,300);
        createUIComponents(dateFrom,dateTo);
    }

    private void createUIComponents(JDateChooser dateFrom,JDateChooser dateTo){
        Font font = new Font("Poppins SemiBold", Font.PLAIN, 20);
        lblRevenueSale = new JLabel();
        lblRevenueSale.setText("Revenue");
        lblRevenueSale.setFont(font);
        lblRevenueSale.setBounds(10, 20, 200, 20);
        this.add(lblRevenueSale);

        lblTotalRevenue = new JLabel();
        lblTotalRevenue.setText("Total Revenue Sale: ");
        lblTotalRevenue.setFont(font);
        lblTotalRevenue.setBounds(300,20,250,20);
        this.add(lblTotalRevenue);

        lblTotal = new JLabel();
        lblTotal.setFont(font);
        lblTotal.setBounds(510,20,200,20);
        this.add(lblTotal);
        
        
        defaultTableModel1 = new DefaultTableModel(null, revenueHead);
        tblRevenueSale = new JTable(defaultTableModel1) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblRevenueSale.getColumnModel().getColumnCount(); i++) {
            tblRevenueSale.getColumnModel().getColumn(i).setResizable(false);
            tblRevenueSale.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblRevenueSale.getTableHeader().setReorderingAllowed(false);
        tblRevenueSale.setFont(new Font("Poppins SemiBold", Font.PLAIN, 12));
        tblRevenueSale.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblRevenueSale.setRowHeight(20);
        for (int i = 0; i < tblRevenueSale.getColumnCount(); i++) {
            tblRevenueSale.getColumnModel().getColumn(i).setPreferredWidth(120);
        }
        JScrollPane scrollPane = new JScrollPane(tblRevenueSale);
        tblRevenueSale.getTableHeader().setFont(new Font("Poppins SemiBold", Font.PLAIN, 12));
        tblRevenueSale.getTableHeader().setBackground(Color.GRAY);
        tblRevenueSale.getTableHeader().setForeground(Color.WHITE);
        tblRevenueSale.getTableHeader().setPreferredSize(new Dimension(scrollPane.getHeight(), 25));
        scrollPane.setBounds(10, 40, 1000, 200);
        this.add(scrollPane);
        showSaleRevenueInTable(dateFrom,dateTo);
        lblTotal.setText(totalRevenue());
    }
    public void getModel(JTable table){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
    }

    public ArrayList<SaleRevenue> getSaleRevenue(JDateChooser dateFrom,JDateChooser dateTo) {
        ArrayList<SaleRevenue> saleRevenues = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        dateFrom1 = simpleDateFormat.format(dateFrom.getDate());
        dateTo1 = simpleDateFormat.format(dateTo.getDate());
        String saleRevenueQuery = "Select transactionreport.Transaction_Date as 'Date' , sum(transaction_list.Product_Quantity) as 'Total Quantity', sum(transaction_list.Product_Amount) as 'Total Amount' from transactionreport INNER JOIN transaction_list ON transactionreport.Transaction_ID = transaction_list.Transaction_ID where transactionreport.Transaction_Date BETWEEN '" + dateFrom1 + "' and '" + dateTo1 + "' GROUP by transactionreport.Transaction_Date";
        Statement statement;
        ResultSet resultSet;
        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(saleRevenueQuery);
            SaleRevenue saleRevenue;
            while (resultSet.next()) {
                saleRevenue = new SaleRevenue(resultSet.getString("Date"), resultSet.getString("Total Quantity"), resultSet.getString("Total Amount"));
                saleRevenues.add(saleRevenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saleRevenues;
    }

    public void showSaleRevenueInTable(JDateChooser dateFrom,JDateChooser dateTo) {
        ArrayList<SaleRevenue> saleRevenues = getSaleRevenue(dateFrom,dateTo);
        getModel(tblRevenueSale);
        Object[] column = new Object[3];
        for (SaleRevenue saleRevenue : saleRevenues) {
            column[0] = saleRevenue.date();
            column[1] = saleRevenue.totalQuantity();
            column[2] = saleRevenue.totalAmount();
            defaultTableModel1.addRow(column);
        }
        lblTotal.setText(totalRevenue());

    }

    private String totalRevenue(){
        double amount;
        double total = 0;

        for (int i = 0; i < tblRevenueSale.getRowCount(); i++) {
            amount = Double.parseDouble(tblRevenueSale.getValueAt(i,2).toString());
            total = total + amount;
        }
        decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(total);
    }

}
