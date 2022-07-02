package com.POS;

import com.thesis.ConnectDatabase;
import com.userInfo.AllUsers;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;



public class PaymentForm extends JDialog {
        JPanel mainPanel;
        JPanel northPanel;
        JPanel northCenterPanel;
        JPanel centerPanel;
        JPanel southPanel;
        JPanel mainCenter;

        JTextField txtCustName;
        JLabel lblCustName;
        JTextField txtCustAddress;
        JLabel lblCustAddress;
        String[] columnName={"ID","Name", "Price", "Qty","Amount","Warranty"};
        JTable tblOrder;
        JLabel lblTransactionID;
        JTextField txtTransactionID;
        JButton btnPrint;
        Font txtFont = new Font("Arial",Font.BOLD,14);
        Font font = new Font("Arial",Font.BOLD,14);
        Connection connection = ConnectDatabase.connectDB();
        PreparedStatement preparedStatement;

        private final DefaultTableModel tableModel = new DefaultTableModel(null,columnName);

    public PaymentForm(JTable table){
        setTitle("Payment");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setBackground(new POSForm().getContentPane().getBackground());
        setSize(400,750);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        getContentPane().setFont(txtFont);

        mainPanel = new JPanel();
        mainPanel.setBackground(this.getContentPane().getBackground());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(this.getContentPane().getWidth(),this.getContentPane().getWidth()));
        add(mainPanel,BorderLayout.CENTER);

        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(mainPanel.getPreferredSize().width,300));
        northPanel.setBackground(mainPanel.getBackground());
        northPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED), BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED)));
        mainPanel.add(northPanel,BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setBackground(mainPanel.getBackground());
        centerPanel.setPreferredSize(new Dimension(600,550));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED), BorderFactory.createSoftBevelBorder(SoftBevelBorder.LOWERED)));
        mainPanel.add(centerPanel,BorderLayout.CENTER);

        mainCenter = new JPanel();
        mainCenter.setBackground(mainPanel.getBackground());
        mainCenter.setPreferredSize(new Dimension(600,550));
        mainCenter.setLayout(new BorderLayout());
        mainCenter.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Order List", TitledBorder.LEFT, TitledBorder.TOP, txtFont, Color.BLACK));
        centerPanel.add(mainCenter,BorderLayout.CENTER);

        northCenterPanel = new JPanel();
        northCenterPanel.setPreferredSize(new Dimension(400,northPanel.getPreferredSize().height));
        northCenterPanel.setBackground(mainPanel.getBackground());
        northCenterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 4, false), "Customer Information", TitledBorder.LEFT, TitledBorder.TOP, txtFont, Color.BLACK));
        northCenterPanel.setLayout(new GridLayout(8,1,5,0));
        northPanel.add(northCenterPanel,BorderLayout.CENTER);

        txtTransactionID = new JTextField();
        txtTransactionID.setFont(font);
        txtTransactionID.setPreferredSize(new Dimension(160,30));
        txtTransactionID.setEditable(false);
        txtTransactionID.setBorder(new LineBorder(Color.BLACK,2,false));
        northCenterPanel.add(txtTransactionID);

        lblTransactionID = new JLabel();
        lblTransactionID.setText("Transaction ID");
        lblTransactionID.setFont(font);
        lblTransactionID.setPreferredSize(new Dimension(110,30));
        lblTransactionID.setHorizontalTextPosition(SwingConstants.LEFT);
        northCenterPanel.add(lblTransactionID);

        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        northCenterPanel.add(panel1);

        txtCustName = new JTextField();
        txtCustName.setFont(txtFont);
        txtCustName.setBorder(new LineBorder(Color.BLACK,2,false));
        txtCustName.setToolTipText("Enter Customer Name");
        txtCustName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                txtCustName.setEditable(Character.isLetter(c)|| e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_PERIOD || e.getExtendedKeyCode() == KeyEvent.VK_DECIMAL || e.getExtendedKeyCode() == KeyEvent.VK_DELETE );
            }
        });
        northCenterPanel.add(txtCustName);
        
        lblCustName = new JLabel();
        lblCustName.setText("Customer Name");
        lblCustName.setLabelFor(txtCustName);
        lblCustName.setFont(txtFont);
        lblCustName.setHorizontalTextPosition(JLabel.LEFT);
        lblCustName.setVerticalTextPosition(JLabel.CENTER);
        northCenterPanel.add(lblCustName);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        northCenterPanel.add(panel);
        
        txtCustAddress = new JTextField();
        txtCustAddress.setFont(txtFont);
        txtCustAddress.setBorder(new LineBorder(Color.BLACK,2,false));
        txtCustAddress.setToolTipText("Enter Customer Address");
        northCenterPanel.add(txtCustAddress);

        lblCustAddress = new JLabel();
        lblCustAddress.setText("Customer Address");
        lblCustAddress.setLabelFor(txtCustAddress);
        lblCustAddress.setFont(txtFont);
        lblCustAddress.setHorizontalTextPosition(JLabel.LEFT);
        lblCustAddress.setVerticalTextPosition(JLabel.CENTER);
        northCenterPanel.add(lblCustAddress);


        tblOrder = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblOrder.getColumnModel().getColumnCount(); i++) {
            tblOrder.getColumnModel().getColumn(i).setResizable(false);
            tblOrder.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblOrder.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblOrder.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblOrder.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblOrder.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblOrder.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblOrder.getTableHeader().setReorderingAllowed(false);
        tblOrder.setFont(new Font("Arial", Font.PLAIN, 15));
        tblOrder.setOpaque(false);
        tblOrder.getTableHeader().setBackground(Color.cyan);
        tblOrder.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        tblOrder.setShowGrid(false);
        tblOrder.setIntercellSpacing(new Dimension(0, 0));
        tblOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOrder.setFont(txtFont);
        JScrollPane scrollPane = new JScrollPane(tblOrder);
        scrollPane.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width,centerPanel.getPreferredSize().height));
        scrollPane.getViewport().setBackground(Color.WHITE);
        mainCenter.add(scrollPane,BorderLayout.CENTER);

        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.setPreferredSize(new Dimension(800,70));
        southPanel.setBackground(mainPanel.getBackground());
        mainPanel.add(southPanel,BorderLayout.SOUTH);

        btnPrint = new JButton();
        btnPrint.setText("Print Receipt");
        btnPrint.setFont(new Font("Arial",Font.BOLD,30));
        btnPrint.setFocusable(false);
        btnPrint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPrint.addActionListener(e -> buttonPrint(table));
        southPanel.add(btnPrint,BorderLayout.CENTER);
    }
    private void buttonPrint(JTable table){
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String transactionID = txtTransactionID.getText();
        String custName = txtCustName.getText();
        String custAddress = txtCustAddress.getText();
        String paymentMethod = AllUsers.paymentMethod;
        String referenceNumber = AllUsers.referenceNumber;
        String subTotal = AllUsers.subTotal;
        String total = AllUsers.grandTotal;
        String discount = AllUsers.discount;
        String transactionDate = format.format(date);
        if (txtCustName.getText().isEmpty()){
            AllUsers.custName = "________________";
        }
        else {
            AllUsers.custName = txtCustName.getText();
        }
        if(txtCustAddress.getText().isEmpty()){
            AllUsers.custAddress = "_________________";
        }
        else{
            AllUsers.custAddress = txtCustAddress.getText();
        }

            AllUsers.transactionID = txtTransactionID.getText();

            int a = JOptionPane.showConfirmDialog(this, "Print the Receipt??", "Print", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                connection = ConnectDatabase.connectDB();
                try{
                    String transactionQuery = "INSERT INTO `transactionReport`(`Transaction_ID`,`User_ID`, `Customer_Name`, `Customer_Address`, `Payment_Method`, `Reference_Number`, `Transaction_Subtotal`, `Transaction_Total`, `Transaction_Discount`, `Transaction_Date`,`Transact_Cash`,`Transact_change`) VALUES ('"+transactionID+"','"+AllUsers.userID+"','"+custName+"','"+custAddress+"','"+paymentMethod+"','"+referenceNumber+"','"+subTotal+"','"+total+"','"+discount+"','"+transactionDate+"','"+AllUsers.cash+"','"+AllUsers.change+"')";
                    assert connection != null;
                    preparedStatement = connection.prepareStatement(transactionQuery);
                    preparedStatement.execute();

                    String id;
                    String name;
                    double price;
                    double quantity;
                    double amount;
                    String warranty;
                    String warrantyDuration;
                    String warrantyStatus;

                    DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                       id = model.getValueAt(i,0).toString();
                       name = model.getValueAt(i,1).toString();
                       price = Double.parseDouble(model.getValueAt(i,2).toString());
                       quantity = Double.parseDouble(model.getValueAt(i,3).toString());
                       amount = Double.parseDouble(model.getValueAt(i,4).toString());
                       warranty = model.getValueAt(i,5).toString();
                       if(warranty.equalsIgnoreCase("5 month warranty")){
                           warrantyDuration = "DATE_ADD(CURRENT_DATE, INTERVAL 5 MONTH)";
                           warrantyStatus = "Active";
                       } else {
                           warrantyDuration = "DATE_ADD(CURRENT_DATE, INTERVAL 0 DAY)";
                           warrantyStatus = "N/A";
                       }

                        String transListQuery = "INSERT INTO `transaction_list`(`Transaction_ID`,`Product_ID`, `Product_Name`, `Product_Price`, `Product_Quantity`, `Product_Amount`,`Product_warranty`,`Warranty_Duration`,`Warranty_status`) VALUES ('"+transactionID+"','"+id+"','"+name+"','"+price+"','"+quantity+"','"+amount+"','"+warranty+"',"+warrantyDuration+",'"+warrantyStatus+"')";
                       preparedStatement = connection.prepareStatement(transListQuery);
                       preparedStatement.execute();
                   }
                    String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','SALE','"+txtTransactionID.getText()+"')";
                    preparedStatement = connection.prepareStatement(auditQuery);
                    preparedStatement.execute();

                    String deleteQuery = "Delete From cart";
                    preparedStatement = connection.prepareStatement(deleteQuery);
                    preparedStatement.execute();
                    Code128Bean code128Bean = new Code128Bean();
                    code128Bean.setHeight(15f);
                    code128Bean.setModuleWidth(0.3);
                    code128Bean.setQuietZone(10);
                    code128Bean.doQuietZone(true);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BitmapCanvasProvider bitmapCanvasProvider = new BitmapCanvasProvider(byteArrayOutputStream,"image/x-png",300, BufferedImage.TYPE_BYTE_BINARY,false,0);
                    code128Bean.generateBarcode(bitmapCanvasProvider, txtTransactionID.getText());
                    bitmapCanvasProvider.finish();
                    FileOutputStream fileOutputStream = new FileOutputStream("images/transactionBarCode/"+txtTransactionID.getText()+".png");
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    PrintSupport ps = new PrintSupport();
                    Object[][] printItem = ps.getTableData(tblOrder);
                    PrintSupport.setItems(printItem);
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(new PrintSupport.MyPrintable(), PrintSupport.getPageFormat(pj));
                    try {
                        pj.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                    JLabel lblTrans = new JLabel("<html><b>Transaction Completed</b></html>");
                    lblTrans.setFont(new Font("Poppins",Font.BOLD,18));
                    JOptionPane.showMessageDialog(this,lblTrans,"Transaction Successful",JOptionPane.INFORMATION_MESSAGE);
                    DefaultTableModel tableModel1 = (DefaultTableModel) table.getModel();
                    tableModel1.setRowCount(0);
                    this.dispose();
                } catch (SQLException | IOException exception){
                    exception.printStackTrace();
                } finally {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

        }
    }
}
