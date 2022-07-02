package com.Sales;

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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;



public class SalesReportPanel extends JPanel {
    JPanel panelNorth, panelWest, panelEast, panelSouth, panelCenter;
    JLabel lblTitle;
    Font txtFont = new Font("Poppins SemiBold", Font.PLAIN, 15);
    JLabel  lblDateFrom, lblDateTo, lblCategory;
    JComboBox<String> cmbCategory;
    JDateChooser dateFrom, dateTo;
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    JPanel panel;
    JButton btnPDF, btnExcel;

    ProductSalePanel productSalePanel;
    RevenuePanel revenuePanel;
    WarrantySalePanel warrantySalePanel;

    public SalesReportPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(202, 170, 86));
        this.setPreferredSize(new Dimension(1116, 638));
        createUIComponents();
    }

    private void createUIComponents() {
        panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout(10, 20));
        panelNorth.setPreferredSize(new Dimension(this.getPreferredSize().width, 120));
        panelNorth.setOpaque(false);
        this.add(panelNorth, BorderLayout.NORTH);
        lblTitle = new JLabel();
        lblTitle.setText("  Sales Report");
        lblTitle.setFont(new Font("Poppins ExtraBold", Font.BOLD, 30));
        lblTitle.setHorizontalTextPosition(SwingConstants.LEFT);
        lblTitle.setVerticalAlignment(SwingConstants.CENTER);
        lblTitle.setPreferredSize(new Dimension(300, 90));
        panelNorth.add(lblTitle, BorderLayout.WEST);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 90));
        panel.setLayout(null);
        panel.setOpaque(false);
        panelNorth.add(panel, BorderLayout.CENTER);
        panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension(30, this.getPreferredSize().height));
        panelWest.setOpaque(false);
        this.add(panelWest, BorderLayout.WEST);
        panelEast = new JPanel();
        panelEast.setPreferredSize(new Dimension(30, this.getPreferredSize().height));
        panelEast.setOpaque(false);
        this.add(panelEast, BorderLayout.EAST);
        panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(this.getPreferredSize().width, 90));
        panelSouth.setLayout(null);
        panelSouth.setOpaque(false);
        this.add(panelSouth, BorderLayout.SOUTH);
        panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(this.getPreferredSize().width - 200, 750));
        panelCenter.setLayout(null);
        panelCenter.setBackground(this.getBackground());
        JScrollPane scrollPane = new JScrollPane(panelCenter, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(this.getPreferredSize().width - 200, this.getPreferredSize().height - 180));
        this.add(scrollPane, BorderLayout.CENTER);

        lblCategory = new JLabel();
        lblCategory.setText("Category ");
        lblCategory.setFont(txtFont);
        lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCategory.setBounds(110, 30, 100, 30);
        panel.add(lblCategory);

        String[] category = {"All", "Product Sale", "Revenue", "Warranty List"};
        cmbCategory = new JComboBox<>(category);
        cmbCategory.setFont(txtFont);
        cmbCategory.setSelectedItem("All");
        cmbCategory.setBounds(220, 30, 150, 30);
        cmbCategory.setFocusable(false);
        panel.add(cmbCategory);

        lblDateFrom = new JLabel();
        lblDateFrom.setText("Date From ");
        lblDateFrom.setFont(txtFont);
        lblDateFrom.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDateFrom.setBounds(110, 70, 100, 30);
        panel.add(lblDateFrom);
        Date date4 = new Date();
        date4.setDate(1);
        dateFrom = new JDateChooser();
        dateFrom.setFont(txtFont);
        dateFrom.setDate(date4);
        dateFrom.setDateFormatString("yyyy-MM-dd");
        dateFrom.setFocusable(false);
        dateFrom.setBounds(220, 70, 150, 30);
        panel.add(dateFrom);
        dateFrom.addPropertyChangeListener(evt -> searchTable());
        lblDateTo = new JLabel();
        lblDateTo.setText("Date To ");
        lblDateTo.setFont(txtFont);
        lblDateTo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDateTo.setBounds(380, 70, 100, 30);
        panel.add(lblDateTo);
        dateTo = new JDateChooser();
        dateTo.setFont(txtFont);
        dateTo.setDate(date);
        dateTo.setDateFormatString("yyyy-MM-dd");
        dateTo.setFocusable(false);
        dateTo.setBounds(490, 70, 150, 30);
        panel.add(dateTo);
        dateTo.addPropertyChangeListener(evt -> searchTable());

        productSalePanel = new ProductSalePanel(dateFrom, dateTo);
        productSalePanel.setBounds(0, 0, 1010, 300);

        revenuePanel = new RevenuePanel(dateFrom,dateTo);
        revenuePanel.setBounds(0,270,1010,300);

        warrantySalePanel = new WarrantySalePanel(dateFrom,dateTo);
        warrantySalePanel.setBounds(0, 510, 1010, 300);

        cmbCategory.addItemListener(e -> searchTable());

        panelCenter.add(productSalePanel);
        panelCenter.add(revenuePanel);
        panelCenter.add(warrantySalePanel);
        btnPDF = new JButton();
        btnPDF.setText("Generate Report");
        btnPDF.setFocusable(false);
        btnPDF.setFont(txtFont);
        btnPDF.setBounds(30, 20, 200, 40);
        btnPDF.addActionListener(e -> generateReport());
        panelSouth.add(btnPDF);
        btnExcel = new JButton();
        btnExcel.setText("Export to Excel");
        btnExcel.setFocusable(false);
        btnExcel.setFont(txtFont);
        btnExcel.setBounds(250, 20, 200, 40);
        btnExcel.addActionListener(e -> excelExport());
        panelSouth.add(btnExcel);
    }
    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void generateReport() {
        JFileChooser dialog = new JFileChooser();
        dialog.setDialogTitle("Sales Report");
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
            JOptionPane.showMessageDialog(this, "File not Generated", "Failed", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }
    }
    private void savePDF(JFileChooser chooser) {
        String dateSave = simpleDateFormat.format(dateFrom.getDate());
        String dateSave2 = simpleDateFormat.format(dateTo.getDate());
        File file = chooser.getSelectedFile();

        TableModel tableModel = productSalePanel.tblProductSale.getModel();
        int i = 0;
        double amount;
        double total = 0;
        while (i < productSalePanel.tblProductSale.getRowCount()) {
            amount = Double.parseDouble(productSalePanel.tblProductSale.getValueAt(i, 4).toString());
            total = total + amount;
            i++;
        }
        if (file != null) {
            file = new File(file + ".pdf");
            try {
                String filePath = file.getPath();
                Document myDocument = new Document(PageSize.LEGAL);
                PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));
                PdfPTable table = new PdfPTable(5);
                myDocument.open();
                float[] columnWidths = new float[]{8, 8, 7, 5, 7};
                table.setWidths(columnWidths);
                //set table width to 100%
                table.setWidthPercentage(100);
                Format simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                String dateToday = simpleDateFormat.format(new Date());
                i = 0;
                myDocument.add(new Paragraph("Sales Report from " + dateSave + " to " + dateSave2, FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.PLAIN)));
                myDocument.add(new Paragraph("Exported Report date: "+dateToday,FontFactory.getFont(FontFactory.TIMES_ROMAN,15,Font.PLAIN)));
                myDocument.add(new Paragraph(" "));
                myDocument.add(new Paragraph(" "));
                myDocument.add(new Paragraph("Product Sale"));
                myDocument.add(new Paragraph(" "));
                Font newFont = new Font("Poppins Bold", Font.PLAIN, 11);
                while (i < productSalePanel.productHead.length) {
                    table.addCell(new PdfPCell(new Paragraph(productSalePanel.productHead[i], FontFactory.getFont(String.valueOf(newFont))))).setBorder(com.itextpdf.text.Rectangle.BOX);
                    i++;
                }
                Font newFont1 = new Font("Poppins SemiBold", Font.PLAIN, 10);
                for (int j = 0; j < productSalePanel.tblProductSale.getRowCount(); j++) {
                    for (int k = 0; k < productSalePanel.tblProductSale.getColumnCount(); k++) {
                        table.addCell(new PdfPCell(new Paragraph(tableModel.getValueAt(j, k).toString(), FontFactory.getFont(String.valueOf(newFont1))))).setBorder(com.itextpdf.text.Rectangle.BOX);
                    }
                }
                myDocument.add(table);
                myDocument.add(new Paragraph("Total Product Sale: " + total, FontFactory.getFont(String.valueOf(newFont))));
                myDocument.add(new Paragraph(" "));
                myDocument.add(new Paragraph(" "));
                myDocument.add(new Paragraph("Revenue"));
                myDocument.add(new Paragraph(" "));
                table = new PdfPTable(3);
                columnWidths = new float[]{8, 8, 7};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100);
                i = 0;
                tableModel = revenuePanel.tblRevenueSale.getModel();
                newFont = new Font("Poppins Bold", Font.PLAIN, 11);
                while (i < revenuePanel.revenueHead.length) {
                    table.addCell(new PdfPCell(new Paragraph(revenuePanel.revenueHead[i], FontFactory.getFont(String.valueOf(newFont))))).setBorder(com.itextpdf.text.Rectangle.BOX);
                    i++;
                }
                for (int j = 0; j < revenuePanel.tblRevenueSale.getRowCount(); j++) {
                    for (int k = 0; k < revenuePanel.tblRevenueSale.getColumnCount(); k++) {
                        table.addCell(new PdfPCell(new Paragraph(tableModel.getValueAt(j, k).toString(), FontFactory.getFont(String.valueOf(newFont1))))).setBorder(com.itextpdf.text.Rectangle.BOX);
                    }
                }
                myDocument.add(table);
                newFont = new Font("Poppins SemiBold", Font.PLAIN, 20);
                myDocument.add(new Paragraph("Total Revenue: " + total, FontFactory.getFont(String.valueOf(newFont))));
                myDocument.setPageSize(PageSize.LEGAL.rotate());
                myDocument.newPage();
                newFont = new Font("Poppins Bold", Font.PLAIN, 11);
                myDocument.add(new Paragraph("Warranty Lists From "+dateSave+" to "+dateSave2+" .",FontFactory.getFont(String.valueOf(newFont)) ));
                table = new PdfPTable(14);
                columnWidths = new float[]{7, 8,8,8,8,8,8,8,8,7,7,7,7,7};
                table.setWidths(columnWidths);
                table.setWidthPercentage(100);
                myDocument.add(new Paragraph(" "));
                i = 0;
                newFont = new Font("Poppins Bold", Font.PLAIN, 9);
                tableModel = warrantySalePanel.tblWarrantySalePanel.getModel();

                while (i<warrantySalePanel.columnHead.length){
                    table.addCell(new PdfPCell(new Paragraph(warrantySalePanel.columnHead[i], FontFactory.getFont(String.valueOf(newFont))))).setBorder(com.itextpdf.text.Rectangle.BOX);
                    i++;
                }
                for (int j = 0; j < warrantySalePanel.tblWarrantySalePanel.getRowCount(); j++) {
                    for (int k = 0; k < warrantySalePanel.tblWarrantySalePanel.getColumnCount(); k++) {
                        table.addCell(new PdfPCell(new Paragraph(tableModel.getValueAt(j,k).toString(), FontFactory.getFont(String.valueOf(newFont))))).setBorder(com.itextpdf.text.Rectangle.BOX);
                    }
                }
                myDocument.add(table);
                JOptionPane.showMessageDialog(this, "Report was successfully generated", "Report Saved", JOptionPane.INFORMATION_MESSAGE);
                myDocument.newPage();

                myDocument.close();
                AllUsers.count += 1;
                openFile(file.toString());
                try {
                    Connection connection = ConnectDatabase.connectDB();
                    PreparedStatement preparedStatement;
                    String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','GENERATE PDF','SALES REPORT')";
                    assert connection != null;
                    preparedStatement = connection.prepareStatement(auditQuery);
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            WarrantySalePanel warrantySalePanel = new WarrantySalePanel(dateFrom,dateTo);
            if (saveFile != null) {
                saveFile = new File(saveFile + ".xlsx");
                Workbook workbook = new XSSFWorkbook();
                createSheet(workbook, "Product Sale", productSalePanel.tblProductSale);
                createSheet(workbook, "Revenue Sale", revenuePanel.tblRevenueSale);
                createSheet(workbook, "Warranty List", warrantySalePanel.tblWarrantySalePanel);
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile.toString());
                workbook.write(fileOutputStream);
                workbook.close();
                fileOutputStream.close();
                JOptionPane.showMessageDialog(this, "File successfully Exported", "Success", JOptionPane.INFORMATION_MESSAGE);
                openFile(saveFile.toString());
                try {
                    Connection connection = ConnectDatabase.connectDB();
                    PreparedStatement preparedStatement;
                    String auditQuery = "INSERT INTO `audit_trail`(`user_ID`, `user_position`, `Action`,`Transact/Product_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','GENERATE EXCEL','SALES REPORT')";
                    assert connection != null;
                    preparedStatement = connection.prepareStatement(auditQuery);
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "File not Exported", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void createSheet(Workbook workbook, String sheetName, JTable table) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }
        for (int j = 0; j < table.getRowCount(); j++) {
            Row row1 = sheet.createRow(j + 1);
            for (int k = 0; k < table.getColumnCount(); k++) {
                Cell cell = row1.createCell(k);
                if (table.getValueAt(j, k) != null) {
                    cell.setCellValue(table.getValueAt(j, k).toString());
                }
            }
        }
    }
    private void searchTable() {
        try {
            int selectedIndex = cmbCategory.getSelectedIndex();

            switch (selectedIndex) {
                case 0 -> {
                    revenuePanel.showSaleRevenueInTable(dateFrom,dateTo);
                    productSalePanel.showProductSaleInTable(dateFrom, dateTo);
                    warrantySalePanel.showWarrantyListInTable(dateFrom,dateTo);
                }
                case 1 -> productSalePanel.showProductSaleInTable(dateFrom, dateTo);
                case 2 -> revenuePanel.showSaleRevenueInTable(dateFrom,dateTo);
                case 3 -> warrantySalePanel.showWarrantyListInTable(dateFrom,dateTo);
            }

        }catch(NullPointerException e){
                JOptionPane.showMessageDialog(null, "Invalid Date! Please Select new Date");
                dateTo.setDate(new Date());
            }
        }
}
