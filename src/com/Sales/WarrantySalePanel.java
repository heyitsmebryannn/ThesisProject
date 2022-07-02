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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WarrantySalePanel extends JPanel {
    JLabel lblWarrantySalePanel;
    JTable tblWarrantySalePanel;
    String[] columnHead = {"Warranty ID",
                            "Cashier ID",
                            "Transaction ID",
                            "Customer Name",
                            "Customer Address",
                            "Product ID",
                            "Product Name",
                            "Product Quantity",
                            "Product Price",
                            "Product Amount",
                            "Warranty Reason",
                            "Remark",
                            "ETC",
                            "Date Used"};
    String dateFrom1,dateTo1;
    Format simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    DefaultTableModel defaultTableModel = new DefaultTableModel(null,columnHead);
    public WarrantySalePanel(JDateChooser dateFrom,JDateChooser dateTo){
        this.setLayout(null);
        this.setOpaque(false);
        this.setSize(1050,300);
        createUIComponents(dateFrom,dateTo);
    }
    public void createUIComponents(JDateChooser dateFrom,JDateChooser dateTo){
        
            lblWarrantySalePanel = new JLabel();
            lblWarrantySalePanel.setText("Warranty List");
            lblWarrantySalePanel.setFont(new Font("Poppins SemiBold",Font.PLAIN,20));
            lblWarrantySalePanel.setBounds(10,10,200,20);
            this.add(lblWarrantySalePanel);

            defaultTableModel = new DefaultTableModel(null,columnHead);
            tblWarrantySalePanel = new JTable(defaultTableModel){
                public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
                }
            };
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            for (int i = 0; i < tblWarrantySalePanel.getColumnModel().getColumnCount(); i++) {
                tblWarrantySalePanel.getColumnModel().getColumn(i).setResizable(false);
                tblWarrantySalePanel.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            tblWarrantySalePanel.getTableHeader().setReorderingAllowed(false);
            tblWarrantySalePanel.setFont(new Font("Poppins SemiBold",Font.PLAIN,12));
            tblWarrantySalePanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tblWarrantySalePanel.setRowHeight(20);
            tblWarrantySalePanel.setAutoCreateRowSorter(true);
            tblWarrantySalePanel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            for (int i = 0; i < tblWarrantySalePanel.getColumnCount(); i++) {
                tblWarrantySalePanel.getColumnModel().getColumn(i).setPreferredWidth(120);
            }
            JScrollPane scrollPane = new JScrollPane(tblWarrantySalePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            tblWarrantySalePanel.getTableHeader().setFont(new Font("Poppins SemiBold",Font.PLAIN,12));
            tblWarrantySalePanel.getTableHeader().setBackground(Color.GRAY);
            tblWarrantySalePanel.getTableHeader().setForeground(Color.WHITE);
            tblWarrantySalePanel.getTableHeader().setPreferredSize(new Dimension(scrollPane.getHeight(),25));
            scrollPane.setBounds(10,30,1000,200);
            this.add(scrollPane);
            showWarrantyListInTable(dateFrom,dateTo);

    }
    public void getModel(JTable table){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
    }
            public void showWarrantyListInTable(JDateChooser dateFrom,JDateChooser dateTo){
                ArrayList<WarrantyList> warrantyLists = getWarrantyList(dateFrom,dateTo);
                getModel(tblWarrantySalePanel);
                Object[] column = new Object[14];

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
                    defaultTableModel.addRow(column);
                }
    }
    public ArrayList<WarrantyList> getWarrantyList(JDateChooser dateFrom,JDateChooser dateTo){
        ArrayList<WarrantyList> warrantyLists = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        dateFrom1 = simpleDateFormat.format(dateFrom.getDate());
        dateTo1 = simpleDateFormat.format(dateTo.getDate());

        String refundItemQuery  = "Select * from pos_warranty where dateUsed between '"+dateFrom1+"' and '"+dateTo1+"' ";
        Statement statement;
        ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(refundItemQuery);
            WarrantyList warrantyList;
            while(resultSet.next()){
                warrantyList = new WarrantyList(resultSet.getString("warranty_ID"),resultSet.getString("user_ID"),resultSet.getString("TransactionID"),resultSet.getString("Customer_Name"),resultSet.getString("Customer_Address"),resultSet.getString("Product_ID"),resultSet.getString("ProductName"),resultSet.getString("Product_quantity"),resultSet.getString("Product_Price"),resultSet.getString("Product_Amount"),resultSet.getString("Warranty_Reason"),resultSet.getString("Remarks"),resultSet.getString("warranty_ETA"),resultSet.getString("dateUsed"));
                warrantyLists.add(warrantyList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return warrantyLists;
    }
}




