package com.report;


import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.thesis.ConnectDatabase;
import com.toedter.calendar.JDateChooser;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;


public class TransactionReportPanel extends JPanel {
    JPanel panelNorth, panelWest, panelEast, panelSouth, panelCenter;
    JLabel lblTitle;
    JTable tblTransaction, tblTransactionList,tblWarrantySalePanel;
    String[] tableHead = {"Transaction ID", "Cashier ID", "Customer Name", "Total Amount", "Transaction Date"};
    DefaultTableModel defaultTableModel = new DefaultTableModel(null, tableHead);
    JButton btnViewTransact, btnReport, btnExport,btnWarrantyTable;
    String transactID;
    String[] tableHead2 = {"Product ID", "Product Name", "Product Price", "Quantity", "Total Amount","Product Warranty","Warranty Duration","Warranty Status"};
    DefaultTableModel transactionListModel = new DefaultTableModel(null, tableHead2);
    JTextField txtSearch;
    Font txtFont = new Font("Poppins SemiBold", Font.PLAIN, 15);
    JLabel lblChoose, lblDateFrom, lblDateTo;
    JDateChooser dateFrom, dateTo;
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    WarrantyTablePanel warrantyTablePanel;



    public TransactionReportPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(202, 170, 86));
        this.setBorder(new LineBorder(Color.BLACK, 5, false));
        this.setPreferredSize(new Dimension(1116, 638));
        createUIComponents();
    }
    //Components in JFrame
    private void createUIComponents() {
        warrantyTablePanel = new WarrantyTablePanel();
        panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout(10, 20));
        panelNorth.setPreferredSize(new Dimension(this.getPreferredSize().width, 90));
        panelNorth.setBackground(this.getBackground());
        add(panelNorth, BorderLayout.NORTH);

        lblTitle = new JLabel();
        lblTitle.setText("  Transaction Report");
        lblTitle.setFont(new Font("Poppins ExtraBold", Font.BOLD, 30));
        lblTitle.setHorizontalTextPosition(SwingConstants.LEFT);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        lblTitle.setPreferredSize(new Dimension(panelNorth.getPreferredSize().width, panelNorth.getPreferredSize().height));
        panelNorth.add(lblTitle);
        panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension(30, this.getPreferredSize().height));
        panelWest.setBackground(this.getBackground());
        this.add(panelWest, BorderLayout.WEST);

        panelEast = new JPanel();
        panelEast.setPreferredSize(new Dimension(30, this.getPreferredSize().height));
        panelEast.setBackground(this.getBackground());
        this.add(panelEast, BorderLayout.EAST);

        panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(this.getPreferredSize().width, 120));
        panelSouth.setLayout(new BorderLayout());
        panelSouth.setBackground(this.getBackground());
        this.add(panelSouth, BorderLayout.SOUTH);

        panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(this.getPreferredSize().width - 200, this.getPreferredSize().height - 200));
        panelCenter.setLayout(new BorderLayout());
        panelCenter.setBackground(this.getBackground());
        this.add(panelCenter, BorderLayout.CENTER);

        JPanel panelCenter1 = new JPanel();
        panelCenter1.setPreferredSize(new Dimension(panelCenter.getPreferredSize().width, 80));
        panelCenter1.setBackground(panelCenter.getBackground());
        panelCenter1.setLayout(new BorderLayout(20, 20));
        panelCenter.add(panelCenter1, BorderLayout.NORTH);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(300, 40));
        txtSearch.setFont(txtFont);
        panelCenter1.add(txtSearch, BorderLayout.NORTH);
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                warrantyTablePanel.searchTransact(dateFrom,dateTo,txtSearch,tblTransaction);
            }
        });

        JPanel panelCenter2 = new JPanel();
        panelCenter2.setBackground(panelCenter.getBackground());
        panelCenter2.setLayout(new BorderLayout());
        panelCenter2.setPreferredSize(new Dimension(panelCenter1.getPreferredSize().width, 40));
        panelCenter1.add(panelCenter2, BorderLayout.SOUTH);

        JPanel panelCenter21 = new JPanel();
        panelCenter21.setLayout(null);
        panelCenter21.setPreferredSize(new Dimension(800, 40));
        panelCenter21.setBackground(panelCenter.getBackground());
        panelCenter2.add(panelCenter21, BorderLayout.WEST);

        lblChoose = new JLabel();
        lblChoose.setText("Choose ");
        lblChoose.setFont(txtFont);
        lblChoose.setBounds(10,5,100,30);
        lblChoose.setHorizontalAlignment(SwingConstants.LEFT);
        panelCenter21.add(lblChoose);

        lblDateFrom = new JLabel();
        lblDateFrom.setText("Date From ");
        lblDateFrom.setFont(txtFont);
        lblDateFrom.setBounds(200,5,100,30);
        lblDateFrom.setHorizontalAlignment(SwingConstants.RIGHT);
        panelCenter21.add(lblDateFrom);

        Date date4 = new Date();
        date4.setDate(1);
        dateFrom = new JDateChooser();
        dateFrom.setFont(txtFont);
        dateFrom.setDate(date4);
        dateFrom.setDateFormatString("yyyy-MM-dd");
        dateFrom.setBounds(320,5,150,30);
        dateFrom.setFocusable(false);
        panelCenter21.add(dateFrom);
        dateFrom.addPropertyChangeListener(evt -> warrantyTablePanel.searchTransact(dateFrom,dateTo,txtSearch,tblTransaction));

        lblDateTo = new JLabel();
        lblDateTo.setText("Date To ");
        lblDateTo.setFont(txtFont);
        lblDateTo.setBounds(500,5,100,30);
        lblDateTo.setHorizontalAlignment(SwingConstants.RIGHT);
        panelCenter21.add(lblDateTo);

        dateTo = new JDateChooser();
        dateTo.setFont(txtFont);
        dateTo.setDate(date);
        dateTo.setDateFormatString("yyyy-MM-dd");
        dateTo.setFocusable(false);
        dateTo.setBounds(620,5,150,30);
        panelCenter21.add(dateTo);

        dateTo.addPropertyChangeListener(evt -> warrantyTablePanel.searchTransact(dateFrom,dateTo,txtSearch,tblTransaction));


        tblTransaction = new JTable(defaultTableModel) {
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblTransaction.getColumnModel().getColumnCount(); i++) {
            tblTransaction.getColumnModel().getColumn(i).setResizable(false);
            tblTransaction.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblTransaction.getTableHeader().setReorderingAllowed(false);
        tblTransaction.setFont(txtFont);
        tblTransaction.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblTransaction.setRowHeight(40);
        tblTransaction.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < tblTransaction.getColumnCount(); i++) {
            tblTransaction.getColumnModel().getColumn(i).setPreferredWidth(250);

        }
        JScrollPane scrollPane = new JScrollPane(tblTransaction, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tblTransaction.getTableHeader().setFont(txtFont);
        tblTransaction.getTableHeader().setBackground(Color.GRAY);
        tblTransaction.getTableHeader().setForeground(Color.WHITE);
        tblTransaction.getTableHeader().setPreferredSize(new Dimension(scrollPane.getHeight(), 50));
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        warrantyTablePanel.showDataInTable(tblTransaction);

        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(panelSouth.getPreferredSize().width, 30));
        panel1.setBackground(panelCenter.getBackground());
        panelSouth.add(panel1, BorderLayout.NORTH);

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(30, 30));
        panel2.setBackground(panelCenter.getBackground());
        panelSouth.add(panel2, BorderLayout.WEST);

        JPanel panel3 = new JPanel();
        panel3.setBackground(panelCenter.getBackground());
        panel3.setLayout(new BorderLayout());
        panel3.setPreferredSize(new Dimension(panelSouth.getPreferredSize().width - 60, 90));
        panelSouth.add(panel3, BorderLayout.CENTER);

        JPanel panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(panelSouth.getPreferredSize().width - 60, 40));
        panel4.setBackground(panelCenter.getBackground());
        panelSouth.add(panel4, BorderLayout.SOUTH);

        JPanel panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(30, 30));
        panel5.setBackground(panelCenter.getBackground());
        panelSouth.add(panel5, BorderLayout.EAST);

        JPanel panel6 = new JPanel();

        panel6.setLayout(new GridLayout(1, 4, 20, 0));
        panel6.setPreferredSize(new Dimension(800, 50));
        panel6.setOpaque(false);
        panel3.add(panel6, BorderLayout.WEST);

        btnViewTransact = new JButton();
        btnViewTransact.setText("View Transaction");
        btnViewTransact.setFocusable(false);
        btnViewTransact.setPreferredSize(new Dimension(100, 50));
        btnViewTransact.setFont(txtFont);
        btnViewTransact.addActionListener(e -> buttonView());
        panel6.add(btnViewTransact);

        btnReport = new JButton();
        btnReport.setText("Generate Report");
        btnReport.setFocusable(false);
        btnReport.setPreferredSize(new Dimension(100, 50));
        btnReport.setFont(txtFont);
        btnReport.addActionListener(e -> generateReport());
        panel6.add(btnReport);

        btnExport = new JButton();
        btnExport.setText("Export to Excel");
        btnExport.setFocusable(false);
        btnExport.setPreferredSize(new Dimension(100, 50));
        btnExport.setFont(txtFont);
        btnExport.addActionListener(e -> excelExport());
        panel6.add(btnExport);

        btnWarrantyTable = new JButton();
        btnWarrantyTable.setText("Warranty Table");
        btnWarrantyTable.setFocusable(false);
        btnWarrantyTable.setPreferredSize(new Dimension(100, 50));
        btnWarrantyTable.setFont(txtFont);

        btnWarrantyTable.addActionListener(e -> warrantyTable());
        panel6.add(btnWarrantyTable);


    }


    //Search Transaction Report


    private void buttonView() {
        JDialog jDialog = new JDialog();
        JPanel panel = new JPanel();
        JLabel lblName, lblAddress, lblMethod, lblReference, lblSub, lblTotal, lblDate, lblExpired, lblCash, lblChange;
        JLabel lblCustName, lblCustAddress, lblPaymentMethod, lblReferenceNumber, lblSubtotal, lblGrandTotal, lblTransactDate, lblWarrantyExpired, lblTotalCash, lblTotalChange;

        try {
            TableModel model1 = tblTransaction.getModel();
            int selectedRow = tblTransaction.getSelectedRow();
            transactID = model1.getValueAt(selectedRow, 0).toString();

            Connection connection = ConnectDatabase.connectDB();
            String custName = "";
            String custAddress = "";
            String paymentMethod = "";
            String referenceNumber = "";
            String transactionTotal = "";
            String totalItem = "";
            String transactionDate = "";
            String warrantyExpired = "";
            String cash = "";
            String change = "";

            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String transactQuery = "Select transactionReport.*,count(transaction_list.Product_ID) as totalItem from transactionReport inner join transaction_list on transactionReport.Transaction_ID = transaction_list.Transaction_ID where transactionReport.Transaction_ID ='" + transactID + "'";


            assert connection != null;
            preparedStatement = connection.prepareStatement(transactQuery);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                custName = resultSet.getString("customer_Name");
                custAddress = resultSet.getString("customer_address");
                paymentMethod = resultSet.getString("Payment_method");
                referenceNumber = resultSet.getString("reference_number");
                totalItem = resultSet.getString("totalItem");
                transactionTotal = resultSet.getString("transaction_Total");
                transactionDate = resultSet.getString("transaction_date");
                cash = resultSet.getString("Transact_Cash");
                change = resultSet.getString("Transact_Change");


                if (referenceNumber.isEmpty()) {
                    referenceNumber = "N\\A";
                }

            }
            panel.setLayout(new BorderLayout());
            panel.setBackground(panelCenter.getBackground());
            panel.setPreferredSize(new Dimension(800, 500));
            panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Transaction Report     - " + transactID, TitledBorder.LEFT, TitledBorder.TOP, new Font("Poppins SemiBold", Font.BOLD, 17), Color.BLACK));


            JPanel panel1 = new JPanel();
            panel1.setPreferredSize(new Dimension(790, 100));
            panel1.setLayout(new GridLayout(5, 2, 5, 5));
            panel1.setBackground(panelCenter.getBackground());
            panel.add(panel1, BorderLayout.NORTH);

            JPanel panel11 = new JPanel();
            panel11.setLayout(new GridLayout(1, 2, 5, 5));
            panel11.setOpaque(false);
            panel1.add(panel11);

            lblName = new JLabel();
            lblName.setText("Customer Name ");
            lblName.setFont(txtFont);
            lblName.setHorizontalAlignment(SwingConstants.LEFT);
            lblName.setForeground(Color.BLACK.brighter());
            panel11.add(lblName);

            lblCustName = new JLabel();
            lblCustName.setText(custName);
            lblCustName.setFont(txtFont);
            lblCustName.setHorizontalAlignment(SwingConstants.LEFT);
            lblCustName.setForeground(Color.BLACK.brighter());
            panel11.add(lblCustName);

            JPanel panel12 = new JPanel();
            panel12.setLayout(new GridLayout(1, 2, 5, 5));
            panel12.setBackground(panelCenter.getBackground());
            panel1.add(panel12);

            lblAddress = new JLabel();
            lblAddress.setText("Customer Address ");
            lblAddress.setFont(txtFont);
            lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
            lblAddress.setForeground(Color.BLACK.brighter());
            panel12.add(lblAddress);

            lblCustAddress = new JLabel();
            lblCustAddress.setText(custAddress);
            lblCustAddress.setFont(txtFont);
            lblCustAddress.setHorizontalAlignment(SwingConstants.LEFT);
            lblCustAddress.setForeground(Color.BLACK.brighter());
            panel12.add(lblCustAddress);


            JPanel panel13 = new JPanel();
            panel13.setLayout(new GridLayout(1, 2, 5, 5));
            panel13.setBackground(panelCenter.getBackground());
            panel1.add(panel13);

            lblMethod = new JLabel();
            lblMethod.setText("Payment Method ");
            lblMethod.setFont(txtFont);
            lblMethod.setHorizontalAlignment(SwingConstants.LEFT);
            lblMethod.setForeground(Color.BLACK.brighter());
            panel13.add(lblMethod);

            lblPaymentMethod = new JLabel();
            lblPaymentMethod.setText(paymentMethod);
            lblPaymentMethod.setFont(txtFont);
            lblPaymentMethod.setHorizontalAlignment(SwingConstants.LEFT);
            lblPaymentMethod.setForeground(Color.BLACK.brighter());
            panel13.add(lblPaymentMethod);

            JPanel panel14 = new JPanel();
            panel14.setLayout(new GridLayout(1, 2, 5, 5));
            panel14.setBackground(panelCenter.getBackground());
            panel1.add(panel14);

            lblReference = new JLabel();
            lblReference.setText("Reference Number ");
            lblReference.setFont(txtFont);
            lblReference.setHorizontalAlignment(SwingConstants.LEFT);
            lblReference.setForeground(Color.BLACK.brighter());
            panel14.add(lblReference);

            lblReferenceNumber = new JLabel();
            lblReferenceNumber.setText(referenceNumber);
            lblReferenceNumber.setFont(txtFont);
            lblReferenceNumber.setHorizontalAlignment(SwingConstants.LEFT);
            lblReferenceNumber.setForeground(Color.BLACK.brighter());
            panel14.add(lblReferenceNumber);

            JPanel panel15 = new JPanel();
            panel15.setLayout(new GridLayout(1, 2, 5, 5));
            panel15.setBackground(panelCenter.getBackground());
            panel1.add(panel15);

            lblSub = new JLabel();
            lblSub.setText("Purchased Item ");
            lblSub.setFont(txtFont);
            lblSub.setHorizontalAlignment(SwingConstants.LEFT);
            lblSub.setForeground(Color.BLACK.brighter());
            panel15.add(lblSub);

            lblSubtotal = new JLabel();
            lblSubtotal.setText(totalItem);
            lblSubtotal.setFont(txtFont);
            lblSubtotal.setHorizontalAlignment(SwingConstants.LEFT);
            lblSubtotal.setForeground(Color.BLACK.brighter());
            panel15.add(lblSubtotal);

            JPanel panel16 = new JPanel();
            panel16.setLayout(new GridLayout(1, 2, 5, 5));
            panel16.setBackground(panelCenter.getBackground());
            panel1.add(panel16);

            lblTotal = new JLabel();
            lblTotal.setText("Total ");
            lblTotal.setFont(txtFont);
            lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
            lblTotal.setForeground(Color.BLACK.brighter());
            panel16.add(lblTotal);

            lblGrandTotal = new JLabel();
            lblGrandTotal.setText(transactionTotal);
            lblGrandTotal.setFont(txtFont);
            lblGrandTotal.setHorizontalAlignment(SwingConstants.LEFT);
            lblGrandTotal.setForeground(Color.BLACK.brighter());
            panel16.add(lblGrandTotal);

            JPanel panel30 = new JPanel();
            panel30.setLayout(new GridLayout(1, 2, 5, 5));
            panel30.setOpaque(false);
            panel1.add(panel30);

            lblCash = new JLabel();
            lblCash.setText("Cash ");
            lblCash.setFont(txtFont);
            lblCash.setHorizontalAlignment(SwingConstants.LEFT);
            lblCash.setForeground(Color.BLACK.brighter());
            panel30.add(lblCash);

            lblTotalCash = new JLabel();
            lblTotalCash.setText(cash);
            lblTotalCash.setFont(txtFont);
            lblTotalCash.setHorizontalAlignment(SwingConstants.LEFT);
            lblTotalCash.setForeground(Color.BLACK.brighter());
            panel30.add(lblTotalCash);

            JPanel panel31 = new JPanel();
            panel31.setLayout(new GridLayout(1, 2, 5, 5));
            panel31.setOpaque(false);
            panel1.add(panel31);

            lblChange = new JLabel();
            lblChange.setText("Change ");
            lblChange.setFont(txtFont);
            lblChange.setHorizontalAlignment(SwingConstants.LEFT);
            lblChange.setForeground(Color.BLACK.brighter());
            panel31.add(lblChange);

            lblTotalChange = new JLabel();
            lblTotalChange.setText(change);
            lblTotalChange.setFont(txtFont);
            lblTotalChange.setHorizontalAlignment(SwingConstants.LEFT);
            lblTotalChange.setForeground(Color.BLACK.brighter());
            panel31.add(lblTotalChange);

            JPanel panel17 = new JPanel();
            panel17.setLayout(new GridLayout(1, 2, 5, 5));
            panel17.setBackground(panelCenter.getBackground());
            panel1.add(panel17);

            lblDate = new JLabel();
            lblDate.setText("Transaction Date ");
            lblDate.setFont(txtFont);
            lblDate.setHorizontalAlignment(SwingConstants.LEFT);
            lblDate.setForeground(Color.BLACK.brighter());
            panel17.add(lblDate);

            lblTransactDate = new JLabel();
            lblTransactDate.setText(transactionDate);
            lblTransactDate.setFont(txtFont);
            lblTransactDate.setHorizontalAlignment(SwingConstants.LEFT);
            lblTransactDate.setForeground(Color.BLACK.brighter());
            panel17.add(lblTransactDate);

            JPanel panel18 = new JPanel();
            panel18.setLayout(new GridLayout(1, 2, 5, 5));
            panel18.setBackground(panelCenter.getBackground());
            panel1.add(panel18);

            lblExpired = new JLabel();
            lblExpired.setText("Warranty Expired at ");
            lblExpired.setFont(txtFont);
            lblExpired.setHorizontalAlignment(SwingConstants.LEFT);
            lblExpired.setForeground(Color.BLACK.brighter());
            panel18.add(lblExpired);

            lblWarrantyExpired = new JLabel();
            lblWarrantyExpired.setText(warrantyExpired);
            lblWarrantyExpired.setFont(txtFont);
            lblWarrantyExpired.setHorizontalAlignment(SwingConstants.LEFT);
            lblWarrantyExpired.setForeground(Color.BLACK.brighter());
            panel18.add(lblWarrantyExpired);

            JPanel panel4 = new JPanel();
            panel4.setPreferredSize(new Dimension(790, 80));
            panel4.setLayout(new BorderLayout());
            panel.add(panel4, BorderLayout.SOUTH);

            JPanel panel41 = new JPanel();
            panel41.setPreferredSize(new Dimension(790, 100));
            panel41.setLayout(new BorderLayout());
            panel41.setBackground(panelCenter.getBackground());
            panel4.add(panel41, BorderLayout.NORTH);

            JPanel panel412 = new JPanel();
            panel412.setPreferredSize(new Dimension(panel41.getPreferredSize().width, 20));
            panel412.setBackground(panelCenter.getBackground());
            panel41.add(panel412, BorderLayout.NORTH);

            JPanel panel42 = new JPanel();
            panel42.setPreferredSize(new Dimension(30, 50));
            panel42.setBackground(panelCenter.getBackground());
            panel41.add(panel42, BorderLayout.WEST);

            JPanel panel43 = new JPanel();
            panel43.setPreferredSize(new Dimension(panel41.getPreferredSize().width - 30, 50));
            panel43.setOpaque(false);
            panel43.setLayout(null);
            panel41.add(panel43, BorderLayout.CENTER);

            JPanel panel44 = new JPanel();
            panel44.setBackground(panelCenter.getBackground());
            panel44.setPreferredSize(new Dimension(panel41.getPreferredSize().width, 30));
            panel41.add(panel44, BorderLayout.SOUTH);


            JButton btnUpdate = new JButton();
            btnUpdate.setText("Update Transaction");
            btnUpdate.setFont(new Font("Arial", Font.BOLD, 15));
            btnUpdate.setFocusable(false);
            btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnUpdate.setBounds(10, 0, 200, 40);
            panel43.add(btnUpdate);
            btnUpdate.addActionListener(e -> {
                jDialog.setVisible(false);
                updateTransact();
            });

            JButton btnWarranty = new JButton();
            btnWarranty.setText("Request Warranty");
            btnWarranty.setFont(new Font("Arial", Font.BOLD, 15));
            btnWarranty.setFocusable(false);
            btnWarranty.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnWarranty.setBounds(220, 0, 200, 40);
            warrantyTablePanel = new WarrantyTablePanel();


            panel43.add(btnWarranty);

            tblTransactionList = new JTable(transactionListModel) {
                public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
                }
            };

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < tblTransactionList.getColumnModel().getColumnCount(); i++) {
                tblTransactionList.getColumnModel().getColumn(i).setResizable(false);
                tblTransactionList.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                tblTransactionList.getColumnModel().getColumn(i).setPreferredWidth(150);
            }

            tblTransactionList.getTableHeader().setReorderingAllowed(false);
            tblTransactionList.setFont(txtFont);
            tblTransactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tblTransactionList.setRowHeight(60);
            tblTransactionList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane scrollPane = new JScrollPane(tblTransactionList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(panel.getPreferredSize().width, 200));
            scrollPane.setBackground(panelCenter.getBackground());
            tblTransactionList.getTableHeader().setFont(txtFont);
            tblTransactionList.getTableHeader().setBackground(Color.GRAY);
            tblTransactionList.getTableHeader().setForeground(Color.WHITE);
            tblTransactionList.getTableHeader().setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 30));
            warrantyTablePanel = new WarrantyTablePanel();
            warrantyTablePanel.transactionTable(tblTransactionList,transactID);
            panel.add(scrollPane, BorderLayout.CENTER);

            btnWarranty.addActionListener(e -> {
                requestWarranty(lblCustName.getText(), lblCustAddress.getText());
                warrantyTablePanel.transactionTable(tblTransactionList,transactID);
            });

            jDialog.setTitle("Transaction Report");
            jDialog.setSize(new Dimension(800, 600));
            jDialog.setModal(true);
            jDialog.setLocationRelativeTo(this);
            jDialog.setResizable(false);
            jDialog.setLayout(new BorderLayout());
            jDialog.add(panel, BorderLayout.CENTER);

            jDialog.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            JLabel lblWarning = new JLabel();
            lblWarning.setText("Please Select a Transaction in the Table");
            JOptionPane.showMessageDialog(this, lblWarning, "Select Transaction", JOptionPane.WARNING_MESSAGE);
        }
    }


    //Get the table in the specific transaction


    //Show the specific transaction in the table


    //Generate a Report in PDF form
    private void generateReport() {
        JFileChooser dialog = new JFileChooser();
        dialog.setDialogTitle("Transaction Report");
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("PDF", "pdf", "pdf");
        dialog.setFileFilter(fileNameExtensionFilter);
        dialog.addChoosableFileFilter(fileNameExtensionFilter);
        dialog.showSaveDialog(this);
        File saveFile = dialog.getSelectedFile();


        try {
            if (saveFile.exists() && dialog.getDialogType() == JFileChooser.SAVE_DIALOG) {
                int result = JOptionPane.showConfirmDialog(this, "File Already Exist, Overwrite this file?", "Overwrite", JOptionPane.YES_NO_OPTION);
                switch (result) {
                    case JOptionPane.YES_OPTION -> savePDF(dialog);
                    case JOptionPane.NO_OPTION ->
                            JOptionPane.showMessageDialog(this, "File not Saved", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                savePDF(dialog);
            }
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(this, "File not Generate", "Failed", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void savePDF(JFileChooser chooser) {

        String dateSave = simpleDateFormat.format(dateFrom.getDate());
        String dateSave2 = simpleDateFormat.format(dateTo.getDate());
        File file = chooser.getSelectedFile();


        if (file != null) {
            file = new File(file + ".pdf");
            try {
                String filePath = file.getPath();
                Document myDocument = new Document(PageSize.LEGAL.rotate());
                PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
                PdfPTable table = new PdfPTable(5);
                myDocument.open();


                float[] columnWidths = new float[]{8, 8, 7, 5, 7};
                table.setWidths(columnWidths);

                table.setWidthPercentage(100);
                //set table width to 100%
                Format simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                String dateToday = simpleDateFormat.format(new Date());
                myDocument.add(new Paragraph("Transaction Report from " + dateSave + " to " + dateSave2, FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.PLAIN)));
                myDocument.add(new Paragraph("Exported Report date: "+dateToday,FontFactory.getFont(FontFactory.TIMES_ROMAN,15,Font.PLAIN)));
                myDocument.add(new Paragraph(" "));
                myDocument.add(new Paragraph(" "));

                Font newFont = new Font("Poppins Bold", Font.PLAIN, 11);
                for (String s : tableHead) {
                    table.addCell(new PdfPCell(new Paragraph(s, FontFactory.getFont(String.valueOf(newFont))))).setBorder(com.itextpdf.text.Rectangle.BOX);

                }
                Font newFont1 = new Font("Poppins SemiBold", Font.PLAIN, 10);

                for (int j = 0; j < tblTransaction.getRowCount(); j++) {
                    for (int k = 0; k < tblTransaction.getColumnCount(); k++) {
                        table.addCell(new PdfPCell(new Paragraph(tblTransaction.getValueAt(j, k).toString(), FontFactory.getFont(String.valueOf(newFont1))))).setBorder(com.itextpdf.text.Rectangle.BOX);

                    }
                }
                JOptionPane.showMessageDialog(this, "Report was successfully generated", "Report Saved", JOptionPane.INFORMATION_MESSAGE);
                myDocument.add(table);
                myDocument.newPage();
                myDocument.close();
                openFile(file.toString());
                try {
                    Connection connection = ConnectDatabase.connectDB();
                    PreparedStatement preparedStatement;
                    String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','GENERATE PDF','TRANSACTION REPORT')";
                    assert connection != null;
                    preparedStatement = connection.prepareStatement(auditQuery);
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    private void excelExport() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Excel", "xlsx", "xls");
            jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);
            File saveFile = jFileChooser.getSelectedFile();
            String dateSave = simpleDateFormat.format(dateFrom.getDate());
            String dateSave2 = simpleDateFormat.format(dateTo.getDate());
            if (saveFile != null) {
                saveFile = new File(saveFile + ".xlsx");
                Workbook workbook = new XSSFWorkbook();

                Sheet sheet = workbook.createSheet("Transaction from " + dateSave + " to " + dateSave2);

                Row row = sheet.createRow(0);
                for (int i = 0; i < tblTransaction.getColumnCount(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(tblTransaction.getColumnName(i));
                }
                for (int j = 0; j < tblTransaction.getRowCount(); j++) {
                    Row row1 = sheet.createRow(j + 1);
                    for (int k = 0; k < tblTransaction.getColumnCount(); k++) {
                        Cell cell = row1.createCell(k);
                        if (tblTransaction.getValueAt(j, k) != null) {
                            cell.setCellValue(tblTransaction.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile.toString());
                workbook.write(fileOutputStream);
                workbook.close();
                fileOutputStream.close();
                JOptionPane.showMessageDialog(this, "File successfully Exported", "Success", JOptionPane.INFORMATION_MESSAGE);
                openFile(saveFile.toString());
                try {
                    Connection connection = ConnectDatabase.connectDB();
                    PreparedStatement preparedStatement;
                    String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','GENERATE EXCEL','TRANSACTION REPORT')";
                    assert connection != null;
                    preparedStatement = connection.prepareStatement(auditQuery);
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(this, "File not Export", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransact() {
        JDialog jDialog = new JDialog();
        JPanel panel = new JPanel();
        JLabel lblName, lblAddress, lblMethod, lblReference, lblSub, lblTotal, lblDate, lblExpired, lblCash, lblChange;
        JLabel lblCustName, lblCustAddress, lblPaymentMethod, lblReferenceNumber, lblSubtotal, lblGrandTotal, lblTransactDate, lblWarrantyExpired, lblTotalChange;
        JTextField txtCash;

        try {
            TableModel model1 = tblTransaction.getModel();
            int selectedRow = tblTransaction.getSelectedRow();
            transactID = model1.getValueAt(selectedRow, 0).toString();

            Connection connection = ConnectDatabase.connectDB();
            String custName = "";
            String custAddress = "";
            String paymentMethod = "";
            String referenceNumber = "";
            String transactionTotal = "";
            String totalItem = "";
            String transactionDate = "";
            String warrantyExpired = "";
            String cash = "";
            String change = "";

            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String transactQuery = "Select transactionReport.*,count(transaction_list.Product_ID) as totalItem from transactionReport inner join transaction_list on transactionReport.Transaction_ID = transaction_list.Transaction_ID where transactionReport.Transaction_ID ='" + transactID + "'";

            assert connection != null;
            preparedStatement = connection.prepareStatement(transactQuery);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                custName = resultSet.getString("customer_Name");
                custAddress = resultSet.getString("customer_address");
                paymentMethod = resultSet.getString("Payment_method");
                referenceNumber = resultSet.getString("reference_number");
                totalItem = resultSet.getString("totalItem");
                transactionTotal = resultSet.getString("transaction_Total");
                transactionDate = resultSet.getString("transaction_date");
                cash = resultSet.getString("Transact_Cash");
                change = resultSet.getString("Transact_Change");

                if (referenceNumber.isEmpty()) {
                    referenceNumber = "N\\A";
                }
            }
            panel.setLayout(new BorderLayout());
            panel.setBackground(panelCenter.getBackground());
            panel.setPreferredSize(new Dimension(800, 500));
            panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Transaction Report     - " + transactID, TitledBorder.LEFT, TitledBorder.TOP, new Font("Poppins SemiBold", Font.BOLD, 17), Color.BLACK));

            JPanel panel1 = new JPanel();
            panel1.setPreferredSize(new Dimension(790, 100));
            panel1.setLayout(new GridLayout(5, 2, 5, 5));
            panel1.setBackground(panelCenter.getBackground());
            panel.add(panel1, BorderLayout.NORTH);

            JPanel panel11 = new JPanel();
            panel11.setLayout(new GridLayout(1, 2, 5, 5));
            panel11.setOpaque(false);
            panel1.add(panel11);

            lblName = new JLabel();
            lblName.setText("Customer Name ");
            lblName.setFont(txtFont);
            lblName.setHorizontalAlignment(SwingConstants.LEFT);
            lblName.setForeground(Color.BLACK.brighter());
            panel11.add(lblName);

            lblCustName = new JLabel();
            lblCustName.setText(custName);
            lblCustName.setFont(txtFont);
            lblCustName.setHorizontalAlignment(SwingConstants.LEFT);
            lblCustName.setForeground(Color.BLACK.brighter());
            panel11.add(lblCustName);

            JPanel panel12 = new JPanel();
            panel12.setLayout(new GridLayout(1, 2, 5, 5));
            panel12.setBackground(panelCenter.getBackground());
            panel1.add(panel12);

            lblAddress = new JLabel();
            lblAddress.setText("Customer Address ");
            lblAddress.setFont(txtFont);
            lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
            lblAddress.setForeground(Color.BLACK.brighter());
            panel12.add(lblAddress);

            lblCustAddress = new JLabel();
            lblCustAddress.setText(custAddress);
            lblCustAddress.setFont(txtFont);
            lblCustAddress.setHorizontalAlignment(SwingConstants.LEFT);
            lblCustAddress.setForeground(Color.BLACK.brighter());
            panel12.add(lblCustAddress);


            JPanel panel13 = new JPanel();
            panel13.setLayout(new GridLayout(1, 2, 5, 5));
            panel13.setBackground(panelCenter.getBackground());
            panel1.add(panel13);

            lblMethod = new JLabel();
            lblMethod.setText("Payment Method ");
            lblMethod.setFont(txtFont);
            lblMethod.setHorizontalAlignment(SwingConstants.LEFT);
            lblMethod.setForeground(Color.BLACK.brighter());
            panel13.add(lblMethod);

            lblPaymentMethod = new JLabel();
            lblPaymentMethod.setText(paymentMethod);
            lblPaymentMethod.setFont(txtFont);
            lblPaymentMethod.setHorizontalAlignment(SwingConstants.LEFT);
            lblPaymentMethod.setForeground(Color.BLACK.brighter());
            panel13.add(lblPaymentMethod);

            JPanel panel14 = new JPanel();
            panel14.setLayout(new GridLayout(1, 2, 5, 5));
            panel14.setBackground(panelCenter.getBackground());
            panel1.add(panel14);

            lblReference = new JLabel();
            lblReference.setText("Reference Number ");
            lblReference.setFont(txtFont);
            lblReference.setHorizontalAlignment(SwingConstants.LEFT);
            lblReference.setForeground(Color.BLACK.brighter());
            panel14.add(lblReference);

            lblReferenceNumber = new JLabel();
            lblReferenceNumber.setText(referenceNumber);
            lblReferenceNumber.setFont(txtFont);
            lblReferenceNumber.setHorizontalAlignment(SwingConstants.LEFT);
            lblReferenceNumber.setForeground(Color.BLACK.brighter());
            panel14.add(lblReferenceNumber);

            JPanel panel15 = new JPanel();
            panel15.setLayout(new GridLayout(1, 2, 5, 5));
            panel15.setBackground(panelCenter.getBackground());
            panel1.add(panel15);

            lblSub = new JLabel();
            lblSub.setText("Purchased Item ");
            lblSub.setFont(txtFont);
            lblSub.setHorizontalAlignment(SwingConstants.LEFT);
            lblSub.setForeground(Color.BLACK.brighter());
            panel15.add(lblSub);

            lblSubtotal = new JLabel();
            lblSubtotal.setText(totalItem);
            lblSubtotal.setFont(txtFont);
            lblSubtotal.setHorizontalAlignment(SwingConstants.LEFT);
            lblSubtotal.setForeground(Color.BLACK.brighter());
            panel15.add(lblSubtotal);

            JPanel panel16 = new JPanel();
            panel16.setLayout(new GridLayout(1, 2, 5, 5));
            panel16.setBackground(panelCenter.getBackground());
            panel1.add(panel16);

            lblTotal = new JLabel();
            lblTotal.setText("Total ");
            lblTotal.setFont(txtFont);
            lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
            lblTotal.setForeground(Color.BLACK.brighter());
            panel16.add(lblTotal);

            lblGrandTotal = new JLabel();
            lblGrandTotal.setText(transactionTotal);
            lblGrandTotal.setFont(txtFont);
            lblGrandTotal.setHorizontalAlignment(SwingConstants.LEFT);
            lblGrandTotal.setForeground(Color.BLACK.brighter());
            panel16.add(lblGrandTotal);

            JPanel panel30 = new JPanel();
            panel30.setLayout(new GridLayout(1, 2, 5, 5));
            panel30.setOpaque(false);
            panel1.add(panel30);

            lblCash = new JLabel();
            lblCash.setText("Cash ");
            lblCash.setFont(txtFont);
            lblCash.setHorizontalAlignment(SwingConstants.LEFT);
            lblCash.setForeground(Color.BLACK.brighter());
            panel30.add(lblCash);

            txtCash = new JTextField();
            txtCash.setText(cash);
            txtCash.setFont(txtFont);
            txtCash.setHorizontalAlignment(SwingConstants.LEFT);
            txtCash.setForeground(Color.BLACK.brighter());
            txtCash.setEditable(false);

            panel30.add(txtCash);

            JPanel panel31 = new JPanel();
            panel31.setLayout(new GridLayout(1, 2, 5, 5));
            panel31.setOpaque(false);
            panel1.add(panel31);

            lblChange = new JLabel();
            lblChange.setText("Change ");
            lblChange.setFont(txtFont);
            lblChange.setHorizontalAlignment(SwingConstants.LEFT);
            lblChange.setForeground(Color.BLACK.brighter());
            panel31.add(lblChange);

            lblTotalChange = new JLabel();
            lblTotalChange.setText(change);
            lblTotalChange.setFont(txtFont);
            lblTotalChange.setHorizontalAlignment(SwingConstants.LEFT);
            lblTotalChange.setForeground(Color.BLACK.brighter());
            panel31.add(lblTotalChange);


            JPanel panel17 = new JPanel();
            panel17.setLayout(new GridLayout(1, 2, 5, 5));
            panel17.setBackground(panelCenter.getBackground());
            panel1.add(panel17);

            lblDate = new JLabel();
            lblDate.setText("Transaction Date ");
            lblDate.setFont(txtFont);
            lblDate.setHorizontalAlignment(SwingConstants.LEFT);
            lblDate.setForeground(Color.BLACK.brighter());
            panel17.add(lblDate);

            lblTransactDate = new JLabel();
            lblTransactDate.setText(transactionDate);
            lblTransactDate.setFont(txtFont);
            lblTransactDate.setHorizontalAlignment(SwingConstants.LEFT);
            lblTransactDate.setForeground(Color.BLACK.brighter());
            panel17.add(lblTransactDate);

            JPanel panel18 = new JPanel();
            panel18.setLayout(new GridLayout(1, 2, 5, 5));
            panel18.setBackground(panelCenter.getBackground());
            panel1.add(panel18);

            lblExpired = new JLabel();
            lblExpired.setText("Warranty Expired at ");
            lblExpired.setFont(txtFont);
            lblExpired.setHorizontalAlignment(SwingConstants.LEFT);
            lblExpired.setForeground(Color.BLACK.brighter());
            panel18.add(lblExpired);

            lblWarrantyExpired = new JLabel();
            lblWarrantyExpired.setText(warrantyExpired);
            lblWarrantyExpired.setFont(txtFont);
            lblWarrantyExpired.setHorizontalAlignment(SwingConstants.LEFT);
            lblWarrantyExpired.setForeground(Color.BLACK.brighter());
            panel18.add(lblWarrantyExpired);

            JPanel panel4 = new JPanel();
            panel4.setPreferredSize(new Dimension(790, 80));
            panel4.setLayout(new BorderLayout());
            panel.add(panel4, BorderLayout.SOUTH);

            JPanel panel41 = new JPanel();
            panel41.setPreferredSize(new Dimension(790, 100));
            panel41.setLayout(new BorderLayout());
            panel41.setBackground(panelCenter.getBackground());
            panel4.add(panel41, BorderLayout.NORTH);

            JPanel panel412 = new JPanel();
            panel412.setPreferredSize(new Dimension(panel41.getPreferredSize().width, 20));
            panel412.setBackground(panelCenter.getBackground());
            panel41.add(panel412, BorderLayout.NORTH);

            JPanel panel42 = new JPanel();
            panel42.setPreferredSize(new Dimension(30, 50));
            panel42.setBackground(panelCenter.getBackground());
            panel41.add(panel42, BorderLayout.WEST);

            JPanel panel43 = new JPanel();
            panel43.setPreferredSize(new Dimension(panel41.getPreferredSize().width - 30, 50));
            panel43.setBackground(panelCenter.getBackground());
            panel43.setLayout(new BorderLayout());
            panel41.add(panel43, BorderLayout.CENTER);

            JPanel panel44 = new JPanel();
            panel44.setBackground(panelCenter.getBackground());
            panel44.setPreferredSize(new Dimension(panel41.getPreferredSize().width, 30));
            panel41.add(panel44, BorderLayout.SOUTH);


            JButton btnClose = new JButton();
            btnClose.setText("Update");
            btnClose.setFont(new Font("Arial", Font.BOLD, 15));
            btnClose.setFocusable(false);
            btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnClose.setPreferredSize(new Dimension(200, 50));
            panel43.add(btnClose, BorderLayout.WEST);
            btnClose.setEnabled(false);
            btnClose.addActionListener(e -> updateNow(txtCash, lblTotalChange, jDialog));

            txtCash.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    enterCash(txtCash, lblGrandTotal, lblTotalChange, btnClose);
                }
            });

            tblTransactionList = new JTable(transactionListModel) {
                public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
                }
            };

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < tblTransactionList.getColumnModel().getColumnCount(); i++) {
                tblTransactionList.getColumnModel().getColumn(i).setResizable(false);
                tblTransactionList.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            tblTransactionList.getTableHeader().setReorderingAllowed(false);
            tblTransactionList.setFont(txtFont);
            tblTransactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tblTransactionList.setRowHeight(60);
            JScrollPane scrollPane = new JScrollPane(tblTransactionList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(panel.getPreferredSize().width, 200));
            scrollPane.setBackground(panelCenter.getBackground());
            tblTransactionList.getTableHeader().setFont(txtFont);
            tblTransactionList.getTableHeader().setBackground(Color.GRAY);
            tblTransactionList.getTableHeader().setForeground(Color.WHITE);
            tblTransactionList.getTableHeader().setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 30));
            warrantyTablePanel = new WarrantyTablePanel();
            warrantyTablePanel.transactionTable(tblTransactionList,transactID);
            panel.add(scrollPane, BorderLayout.CENTER);

            jDialog.setTitle("Transaction Report");
            jDialog.setSize(new Dimension(800, 600));
            jDialog.setModal(true);
            jDialog.setLocationRelativeTo(this);
            jDialog.setResizable(false);
            jDialog.setLayout(new BorderLayout());
            jDialog.add(panel, BorderLayout.CENTER);

            jDialog.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            JLabel lblWarning = new JLabel();
            lblWarning.setText("<html><u>Please Select a Transaction in the Table</u></html>");
            lblWarning.setForeground(Color.RED);
            lblWarning.setFont(new Font("Poppins ExtraBold", Font.PLAIN, 20));
            JOptionPane.showMessageDialog(this, lblWarning, "Select Transaction", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void enterCash(JTextField txtCash, JLabel lblTotal, JLabel lblChange, JButton btn) {
        try {
            String cash1 = JOptionPane.showInputDialog(null, "Enter Cash", txtCash.getText());

            double cash = Double.parseDouble(cash1);
            double total = Double.parseDouble(lblTotal.getText());

            if (total > cash) {
                JOptionPane.showMessageDialog(this, "Insufficient Cash, Please Enter Again", "Insufficient Cash", JOptionPane.WARNING_MESSAGE);
            } else {
                txtCash.setText(cash1);
                lblChange.setText(String.valueOf(cash - total));
                btn.setEnabled(true);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input! Please Try Again", "Invalid", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ex) {
            txtCash.setText(txtCash.getText());
        }
    }
    private void updateNow(JTextField txtCash, JLabel lblChange, JDialog jDialog) {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this transaction?", "Update Transact?", JOptionPane.YES_NO_OPTION);
        Connection connection = ConnectDatabase.connectDB();
        PreparedStatement preparedStatement = null;
        if (result == JOptionPane.YES_OPTION) {
            try {
                String updateQuery = "Update transactionReport set transact_cash = '" + txtCash.getText() + "', transact_change = '" + lblChange.getText() + "' where transaction_ID = '" + transactID + "'";
                assert connection != null;
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.execute();
                String auditQuery = "INSERT INTO `audit_trail`( `user_ID`, `user_position`, `Action` ,`Transact/Product_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','UPDATE TRANSACTION','"+transactID+"')";
                preparedStatement = connection.prepareStatement(auditQuery);
                preparedStatement.execute();
                JOptionPane.showMessageDialog(this, "Transaction Updated Successfully", "Transact Updated", JOptionPane.INFORMATION_MESSAGE);
                jDialog.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    assert preparedStatement != null;
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    private void useWarranty(String userID, String transactID, String custName, String custAddress) {
        try {
            JDialog dialog = new JDialog();
            WarrantyPanel warrantyPanel = new WarrantyPanel(dialog);
            int selectedRow = tblTransactionList.getSelectedRow();
            warrantyPanel.txtProductID.setText(tblTransactionList.getValueAt(selectedRow, 0).toString());
            warrantyPanel.txtProductName.setText(tblTransactionList.getValueAt(selectedRow, 1).toString());
            warrantyPanel.txtProductPrice.setText(tblTransactionList.getValueAt(selectedRow, 2).toString());
            warrantyPanel.txtProductQuantity.setText(tblTransactionList.getValueAt(selectedRow, 3).toString());
            warrantyPanel.txtProductAmount.setText(tblTransactionList.getValueAt(selectedRow, 4).toString());
            warrantyPanel.txtUserID.setText(userID);
            warrantyPanel.txtCustomerName.setText(custName);
            warrantyPanel.txtCustomerAddress.setText(custAddress);
            warrantyPanel.txtTransactionID.setText(transactID);
            dialog.setTitle("Warranty");
            dialog.setSize(900, 600);
            dialog.setModal(true);
            dialog.setResizable(false);
            dialog.setLayout(new BorderLayout());
            dialog.setLocationRelativeTo(null);
            dialog.getContentPane().setBackground(new Color(202, 170, 86));
            dialog.add(warrantyPanel, BorderLayout.CENTER);
            dialog.setVisible(true);

        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "Please select an item you want to use a warranty");
        }
    }
    private void requestWarranty(String custName, String custAddress) {
      try{
          int selectedRow = tblTransactionList.getSelectedRow();
          String status = tblTransactionList.getValueAt(selectedRow,7).toString();

          if (status.equalsIgnoreCase("Pending")) {
              JOptionPane.showMessageDialog(null, "The item has a pending request. Please see warranty Table for more details", "Warranty Status", JOptionPane.ERROR_MESSAGE);
          }else if (!status.equalsIgnoreCase("Active")) {
              JOptionPane.showMessageDialog(null,"The item has no warranty or was already expired","Warranty Status",JOptionPane.ERROR_MESSAGE);
          }
          else{
              String userID = tblTransaction.getValueAt(tblTransaction.getSelectedRow(), 1).toString();
              String transactionID = tblTransaction.getValueAt(tblTransaction.getSelectedRow(), 0).toString();
              useWarranty(userID, transactionID, custName, custAddress);
          }
      }catch (ArrayIndexOutOfBoundsException ex){
          JOptionPane.showMessageDialog(null,"Select an item you want to used a warranty","Select an Item",JOptionPane.ERROR_MESSAGE);
      }
    }
    private void warrantyTable(){
        warrantyTablePanel = new WarrantyTablePanel();
        JDialog dialog = new JDialog();
        dialog.setTitle("Warranty Table");
        dialog.setSize(800,600);
        dialog.setLayout(new BorderLayout());
        dialog.setModal(true);
        dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().setBackground(new Color(202,170,86));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(null);
        JLabel lblWarrantySalePanel;
        dialog.add(panel,BorderLayout.CENTER);

        String[] columnHead = {"Warranty ID", "Cashier ID", "Transaction ID", "Customer Name", "Customer Address", "Product ID", "Product Name", "Product Quantity", "Product Price", "Product Amount", "Warranty Reason", "Remark", "ETC", "Date Used"};
        DefaultTableModel defaultTableModel;

        lblWarrantySalePanel = new JLabel();
        lblWarrantySalePanel.setText("Warranty List");
        lblWarrantySalePanel.setFont(new Font("Poppins SemiBold",Font.PLAIN,20));
        lblWarrantySalePanel.setBounds(10,10,200,30);
        panel.add(lblWarrantySalePanel);

        JTextField txtSearchField = new JTextField();
        txtSearchField.setFont(txtFont);
        txtSearchField.setBounds(10,40,760,40);
        panel.add(txtSearchField);

        JLabel dateFromLabel = new JLabel();
        dateFromLabel.setFont(txtFont);
        dateFromLabel.setText("Date From");
        dateFromLabel.setBounds(80,90,150,30);
        panel.add(dateFromLabel);

        JDateChooser warrantyDateFrom = new JDateChooser();
        Date date = new Date();
        date.setDate(1);
        warrantyDateFrom.setFont(txtFont);
        warrantyDateFrom.setDate(date);
        warrantyDateFrom.setDateFormatString("yyyy-MM-dd");
        warrantyDateFrom.setBounds(180,90,200,30);
        warrantyDateFrom.setFocusable(false);
        panel.add(warrantyDateFrom);

        JLabel dateToLabel = new JLabel();
        dateToLabel.setFont(txtFont);
        dateToLabel.setText("Date To");
        dateToLabel.setBounds(440,90,150,30);
        panel.add(dateToLabel);

        JDateChooser warrantyDateTo = new JDateChooser();
        date = new Date();
        warrantyDateTo.setFont(txtFont);
        warrantyDateTo.setDate(date);
        warrantyDateTo.setDateFormatString("yyyy-MM-dd");
        warrantyDateTo.setBounds(510,90,200,30);
        warrantyDateTo.setFocusable(false);
        panel.add(warrantyDateTo);

        txtSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                warrantyTablePanel.showWarrantyListInTable(tblWarrantySalePanel,txtSearchField,dateFrom,dateTo);
            }
        });

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
        tblWarrantySalePanel.setRowHeight(40);
        tblWarrantySalePanel.setAutoCreateRowSorter(true);
        tblWarrantySalePanel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < tblWarrantySalePanel.getColumnCount(); i++) {
            tblWarrantySalePanel.getColumnModel().getColumn(i).setPreferredWidth(120);
        }
        JScrollPane scrollPane = new JScrollPane(tblWarrantySalePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tblWarrantySalePanel.getTableHeader().setFont(new Font("Poppins SemiBold",Font.PLAIN,12));
        tblWarrantySalePanel.getTableHeader().setBackground(Color.GRAY);
        tblWarrantySalePanel.getTableHeader().setForeground(Color.WHITE);
        tblWarrantySalePanel.getTableHeader().setPreferredSize(new Dimension(scrollPane.getHeight(),35));
        scrollPane.setBounds(10,140,760,300);

        warrantyTablePanel.showWarrantyListInTable(tblWarrantySalePanel,txtSearchField,dateFrom,dateTo);
        warrantyDateFrom.addPropertyChangeListener(evt -> warrantyTablePanel.showWarrantyListInTable(tblWarrantySalePanel,txtSearchField,warrantyDateFrom,warrantyDateTo));
        warrantyDateTo.addPropertyChangeListener(evt -> warrantyTablePanel.showWarrantyListInTable(tblWarrantySalePanel,txtSearchField,warrantyDateFrom,warrantyDateTo));

        panel.add(scrollPane);

        JButton btnUpdateRemark = new JButton();
        btnUpdateRemark.setText("Update Remark");
        btnUpdateRemark.setFocusable(false);
        btnUpdateRemark.setBounds(10,500,150,40);
        btnUpdateRemark.addActionListener(e-> updateRemarks(tblWarrantySalePanel,txtSearchField,warrantyDateFrom,warrantyDateTo));
        panel.add(btnUpdateRemark);

        dialog.setVisible(true);

    }

    private void updateRemarks(JTable table,JTextField txtSearch,JDateChooser dateFrom,JDateChooser dateTo){
        try{
            JDialog dialog = new JDialog();
            WarrantyUpdatePanel warrantyUpdatePanel = new WarrantyUpdatePanel(dialog,table,txtSearch,dateFrom,dateTo);
            dialog.setTitle("Update Warranty");
            dialog.setSize(900, 600);
            dialog.setModal(true);
            dialog.setResizable(false);
            dialog.setLayout(new BorderLayout());
            dialog.setLocationRelativeTo(null);
            dialog.getContentPane().setBackground(new Color(202, 170, 86));
            dialog.add(warrantyUpdatePanel, BorderLayout.CENTER);

            int selectedRow = table.getSelectedRow();
            String warrantyID = table.getValueAt(selectedRow,0).toString();
            String cashierID = table.getValueAt(selectedRow,1).toString();
            String transactionID = table.getValueAt(selectedRow,2).toString();
            String customerName = table.getValueAt(selectedRow,3).toString();
            String customerAddress = table.getValueAt(selectedRow,4).toString();
            String productId = table.getValueAt(selectedRow,5).toString();
            String productName = table.getValueAt(selectedRow,6).toString();
            String productQuantity = table.getValueAt(selectedRow,7).toString();
            String productPrice = table.getValueAt(selectedRow,8).toString();
            String productAmount = table.getValueAt(selectedRow,9).toString();
            String warrantyReason = table.getValueAt(selectedRow,10).toString();
            String remark = table.getValueAt(selectedRow,11).toString();
            String etc = table.getValueAt(selectedRow,12).toString();

            if(remark.equalsIgnoreCase("Repaired")){
                JOptionPane.showMessageDialog(null,"The item was Already Repaired, This cannot update now!");
            }
            else if(remark.equalsIgnoreCase("Replaced")){
                JOptionPane.showMessageDialog(null,"The item was Already Replaced, This cannot update now!");
            }else{
                warrantyUpdatePanel.txtUserID.setText(cashierID);
                warrantyUpdatePanel.txtTransactionID.setText(transactionID);
                warrantyUpdatePanel.txtCustomerName.setText(customerName);
                warrantyUpdatePanel.txtCustomerAddress.setText(customerAddress);
                warrantyUpdatePanel.txtProductID.setText(productId);
                warrantyUpdatePanel.txtProductName.setText(productName);
                warrantyUpdatePanel.txtProductQuantity.setText(productQuantity);
                warrantyUpdatePanel.txtProductPrice.setText(productPrice);
                warrantyUpdatePanel.txtProductAmount.setText(productAmount);
                warrantyUpdatePanel.cmbWarrantyReason.setSelectedItem(warrantyReason);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(etc);
                warrantyUpdatePanel.dateETA.setDate(date);
                warrantyUpdatePanel.cmbWarrantyReason.setSelectedItem(remark);
                warrantyUpdatePanel.warrantyID = warrantyID;
                dialog.setVisible(true);
            }
        }catch (IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,"Please select a warranty you want to update.","Update Table",JOptionPane.WARNING_MESSAGE);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


}
