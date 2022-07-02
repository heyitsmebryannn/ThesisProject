package com.report;


import com.thesis.ConnectDatabase;
import com.userInfo.AllUsers;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AuditTrailPanel extends JPanel {
    JPanel panelNorth,panelWest,panelEast,panelSouth,panelCenter;
    JLabel lblTitle;
    JTable tblAudit;
    String[] tableHead = {"Audit ID","User ID","User Position","Action","Product/Transaction ID","Date and Time"};
    DefaultTableModel defaultTableModel = new DefaultTableModel(null,tableHead);


    public AuditTrailPanel(){
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(202,170,86));
        this.setBorder(new LineBorder(Color.BLACK,5,false));
        this.setPreferredSize(new Dimension(1116,638));
        createUIComponents();



    }

    private void createUIComponents() {
        panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout());
        panelNorth.setPreferredSize(new Dimension(this.getPreferredSize().width,100));
        panelNorth.setBackground(this.getBackground());
        add(panelNorth,BorderLayout.NORTH);


        lblTitle = new JLabel();
        lblTitle.setText("  Activity Log");
        lblTitle.setFont(new Font("Poppins ExtraBold",Font.BOLD,30));
        lblTitle.setHorizontalTextPosition(SwingConstants.LEFT);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        lblTitle.setPreferredSize(new Dimension(panelNorth.getPreferredSize().width,panelNorth.getPreferredSize().height));
        panelNorth.add(lblTitle);
        panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension(50,this.getPreferredSize().height));
        panelWest.setBackground(this.getBackground());
        this.add(panelWest,BorderLayout.WEST);

        panelEast = new JPanel();
        panelEast.setPreferredSize(new Dimension(50,this.getPreferredSize().height));
        panelEast.setBackground(this.getBackground());
        this.add(panelEast,BorderLayout.EAST);

        panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(this.getPreferredSize().width,50));
        panelSouth.setBackground(this.getBackground());
        this.add(panelSouth,BorderLayout.SOUTH);

        panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(this.getPreferredSize().width-200,this.getPreferredSize().height-200));
        panelCenter.setLayout(new BorderLayout());
        panelCenter.setBackground(this.getBackground());
        this.add(panelCenter,BorderLayout.CENTER);

        tblAudit = new JTable(defaultTableModel){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        for (int i = 0; i < tblAudit.getColumnModel().getColumnCount(); i++) {
            tblAudit.getColumnModel().getColumn(i).setResizable(false);
            tblAudit.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        tblAudit.getTableHeader().setReorderingAllowed(false);
        tblAudit.setFont(new Font("Arial",Font.BOLD,18));
        tblAudit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAudit.setAutoCreateRowSorter(true);
        showDataInTable();
        getModel();
        tblAudit.setRowHeight(60);
        JScrollPane scrollPane = new JScrollPane(tblAudit);
        tblAudit.getTableHeader().setFont(new Font("Arial",Font.BOLD,15));
        tblAudit.getTableHeader().setBackground(Color.GRAY);
        tblAudit.getTableHeader().setForeground(Color.WHITE);
        tblAudit.getTableHeader().setPreferredSize(new Dimension(scrollPane.getHeight(),50));
        panelCenter.add(scrollPane);
        getModel();
        showDataInTable();

    }

    public ArrayList<AuditTrail> getAuditList(){
        ArrayList<AuditTrail> auditList = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String auditQuery;
        if(!AllUsers.position.equalsIgnoreCase("Admin")){
            auditQuery = "SELECT * FROM audit_trail where user_ID ='"+AllUsers.userID+"' order by Audit_ID DESC";
        }
        else {
            auditQuery = "SELECT * FROM audit_trail order by Audit_ID DESC";
        }

            Statement statement;
            ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(auditQuery);
            AuditTrail auditTrail;
            while(resultSet.next()){
                auditTrail = new AuditTrail(resultSet.getInt("Audit_ID"),resultSet.getString("user_ID"),resultSet.getString("user_position"),resultSet.getString("Action"),resultSet.getString("Transact/Product_ID"),resultSet.getString("Time_and_Date"));
                auditList.add(auditTrail);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return auditList;
    }
    public void getModel(){
        DefaultTableModel tableModel = (DefaultTableModel) tblAudit.getModel();
        tableModel.setRowCount(0);
        showDataInTable();
    }
    public void showDataInTable(){

        ArrayList<AuditTrail> auditList = getAuditList();
        defaultTableModel.setRowCount(0);
        Object[] column = new Object[6];

        for (AuditTrail auditLists : auditList) {
            column[0] = auditLists.getAuditID();
            column[1] = auditLists.getUserID();
            column[2] = auditLists.getPosition();
            column[3] = auditLists.getAction();
            column[4] = auditLists.getTransactUserID();
            column[5] = auditLists.getDate();
            defaultTableModel.addRow(column);
        }
    }
}
