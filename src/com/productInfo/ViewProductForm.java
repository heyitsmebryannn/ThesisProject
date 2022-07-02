package com.productInfo;


import com.userInfo.AllUsers;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class ViewProductForm extends JDialog {
    JTextField txtProductID,txtProductName,txtProductBrand,txtProductSize,txtProductPrice,txtProductQuantity;
    JLabel lblTitle,lblPID,lblPName,lblPBrand,lblPType,lblPSize,lblImage,lblPDescription,lblPrice,lblPQuantity;
    JComboBox<String> cmbProductType,cmbProductSize,cmbSecondSize,cmbQuantity;
    JRadioButton rdbLength,rdbSizes;
    ButtonGroup btnGroupSize;
    JTextArea txtDescription;
    Font txtFont = new Font("Poppins SemiBold",Font.BOLD,15);
    Font titleFont = new Font("Poppins SemiBold",Font.BOLD,20);
    JPanel centerPanel,eastPanel;
    Font lblFont = new Font("Poppins SemiBold",Font.BOLD,18);
    String[] types;
    String[] size;
    String[] secondSize;
    String[] quantity;


    public ViewProductForm(){

        InventoryForm inventoryForm = new InventoryForm();
        this.setTitle("Product " + AllUsers.productID);
        this.setBackground(inventoryForm.centerPanel.getBackground());
        this.setLayout(new BorderLayout());
        this.setModal(true);
        this.setResizable(false);
        this.setSize(1066,620);
        this.setLocationRelativeTo(new InventoryForm());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        centerPanel = new JPanel();
        centerPanel.setBackground(this.getBackground());
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(dimension.width-500,dimension.height));

        eastPanel = new JPanel();
        eastPanel.setBackground(this.getBackground());
        eastPanel.setLayout(new FlowLayout());
        eastPanel.setPreferredSize(new Dimension(dimension.width-centerPanel.getPreferredSize().width,dimension.height));


        lblTitle = new JLabel("View Product");
        lblTitle.setFont(titleFont);
        lblTitle.setHorizontalAlignment(JLabel.LEFT);
        lblTitle.setPreferredSize(new Dimension(250,60));
        centerPanel.add(lblTitle);
        spacingPanel(296,60,centerPanel);

        txtField(txtProductID = new JTextField(),"Enter Product ID",300);
        spacingPanel(200,20,centerPanel);
        LabelName(lblPID = new JLabel(),"Product ID",300);
        spacingPanel(200,20,centerPanel);
        spacingPanel(centerPanel.getPreferredSize().width,10,centerPanel);


        txtField(txtProductName = new JTextField(),"Enter Product Name",460);
        spacingPanel(50,20,centerPanel);
        LabelName(lblPName = new JLabel(),"Product Name",460);
        spacingPanel(50,20,centerPanel);
        spacingPanel(centerPanel.getPreferredSize().width,10,centerPanel);
        txtField(txtProductBrand = new JTextField(), "Enter Product Brand",460);
        spacingPanel(50,20,centerPanel);
        LabelName(lblPBrand = new JLabel(),"Product Brand",460);
        spacingPanel(50,20,centerPanel);
        spacingPanel(centerPanel.getPreferredSize().width,10,centerPanel);

        types = new String[]{"Handy tools", "Welding Tools", "Cleaning Tools","Equipment Tools"};
        cmbProductType = new JComboBox<>(types);
        cmbProductType.setFont(txtFont);
        cmbProductType.setToolTipText("Choose a Type");
        cmbProductType.setSelectedItem(null);
        cmbProductType.setPreferredSize(new Dimension(460,25));
        centerPanel.add(cmbProductType);

        spacingPanel(50,20,centerPanel);
        LabelName(lblPType = new JLabel(),"Product Type",460);
        spacingPanel(50,20,centerPanel);
        spacingPanel(centerPanel.getPreferredSize().width,10,centerPanel);

        txtField(txtProductSize = new JTextField(), "Enter Product Size",100);
        size = new String[]{"Millimeter","Centimeter","Inch","Meter","Feet","Yard","Ounces","Pieces Inside"};
        cmbProductSize = new JComboBox<>(size);
        cmbProductSize.setFont(txtFont);
        cmbProductSize.setToolTipText("Choose a Type");
        cmbProductSize.setSelectedItem(null);
        cmbProductSize.setEnabled(false);
        cmbProductSize.setPreferredSize(new Dimension(150,25));
        centerPanel.add(cmbProductSize);
        spacingPanel(50,20,centerPanel);

        secondSize = new String[]{"Small","Medium","Big"};
        cmbSecondSize = new JComboBox<>(secondSize);
        cmbSecondSize.setFont(txtFont);
        cmbSecondSize.setToolTipText("Choose a Type");
        cmbSecondSize.setSelectedItem(null);
        cmbSecondSize.setPreferredSize(new Dimension(150,25));
        cmbSecondSize.setEnabled(false);
        centerPanel.add(cmbSecondSize);
        spacingPanel(50,20,centerPanel);
        spacingPanel(100,20,centerPanel);

        rdbLength = new JRadioButton("By Length");
        rdbLength.setFont(txtFont);
        rdbLength.setPreferredSize(new Dimension(120,25));
        rdbLength.setOpaque(false);
        centerPanel.add(rdbLength);
        spacingPanel(100,20,centerPanel);


        rdbSizes = new JRadioButton("By Sizes");
        rdbSizes.setFont(txtFont);
        rdbSizes.setPreferredSize(new Dimension(100,30));
        rdbSizes.setOpaque(false);
        centerPanel.add(rdbSizes);
        spacingPanel(70,20,centerPanel);

        LabelName(lblPSize = new JLabel(),"Product Size",460);
        spacingPanel(50,20,centerPanel);
        spacingPanel(centerPanel.getPreferredSize().width,10,centerPanel);

        txtField(txtProductQuantity = new JTextField(),"Enter Quantity",200);
        spacingPanel(50,20,centerPanel);

        quantity = new String[]{"Piece(s)","Kilo(s)","Gram(s)","Milligram(s)"};
        cmbQuantity = new JComboBox<>(quantity);
        cmbQuantity.setFont(txtFont);
        cmbQuantity.setToolTipText("Choose a Type");
        cmbQuantity.setPreferredSize(new Dimension(100,25));
        cmbQuantity.setSelectedItem(null);
        centerPanel.add(cmbQuantity);

        spacingPanel(150,20,centerPanel);
        LabelName(lblPQuantity = new JLabel(),"Product Stock",460);
        spacingPanel(50,20,centerPanel);

        spacingPanel(100,100,eastPanel);
        lblImage = new JLabel();
        lblImage.setOpaque(true);
        lblImage.setBackground(Color.WHITE);
        lblImage.setBorder(new LineBorder(Color.BLACK,2,false));
        lblImage.setPreferredSize(new Dimension(200,150));
        lblImage.setVerticalAlignment(JLabel.CENTER);
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        eastPanel.add(lblImage);
        spacingPanel(100,100,eastPanel);
        spacingPanel(eastPanel.getPreferredSize().width,25,eastPanel);

        txtProductPrice = new JTextField();
        txtProductPrice.setFont(txtFont);
        txtProductPrice.setHorizontalAlignment(JTextField.LEADING);
        txtProductPrice.setToolTipText("Product Price");
        txtProductPrice.setPreferredSize(new Dimension(400,30));
        txtProductPrice.setBorder(new LineBorder(Color.BLACK,2,false));
        eastPanel.add(txtProductPrice);

        lblPrice = new JLabel();
        lblPrice.setText("Product Price");
        lblPrice.setFont(lblFont);
        lblPrice.setVerticalTextPosition(JLabel.CENTER);
        lblPrice.setHorizontalAlignment(JLabel.LEADING);
        lblPrice.setPreferredSize(new Dimension(400,30));
        eastPanel.add(lblPrice);

        spacingPanel(eastPanel.getPreferredSize().width,10,eastPanel);


        txtDescription = new JTextArea();
        txtDescription.setToolTipText("Enter Description Here");
        txtDescription.setFont(txtFont);
        txtDescription.setPreferredSize(new Dimension(400,120));
        txtDescription.setBorder(new LineBorder(Color.BLACK,2,false));
        eastPanel.add(txtDescription);

        lblPDescription = new JLabel();
        lblPDescription.setText("Product Description");
        lblPDescription.setFont(lblFont);
        lblPDescription.setVerticalTextPosition(JLabel.CENTER);
        lblPDescription.setHorizontalAlignment(JLabel.LEADING);
        lblPDescription.setPreferredSize(new Dimension(400,30));
        eastPanel.add(lblPDescription);

        txtProductID.setEditable(false);
        txtProductName.setEditable(false);
        txtProductBrand.setEditable(false);
        cmbProductType.setFocusable(false);
        txtProductSize.setEditable(false);
        txtProductQuantity.setEditable(false);
        txtProductPrice.setEditable(false);
        txtDescription.setEditable(false);

        btnGroupSize = new ButtonGroup();
        btnGroupSize.add(rdbLength);
        btnGroupSize.add(rdbSizes);

        this.add(centerPanel,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);



    }
    private void spacingPanel(int x,int y,JPanel panel1){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(x,y));
        panel.setBackground(this.getBackground());
        panel1.add(panel);
    }

    private void txtField(JTextField txtField,String text,int x){
        txtField.setFont(txtFont);
        txtField.setHorizontalAlignment(JTextField.LEADING);
        txtField.setToolTipText(text);
        txtField.setPreferredSize(new Dimension(x,20));
        centerPanel.add(txtField);

    }
    private void LabelName(JLabel lbl,String text,int x){
        lbl.setText(text);
        lbl.setFont(lblFont);
        lbl.setVerticalTextPosition(JLabel.CENTER);
        lbl.setHorizontalAlignment(JLabel.LEADING);
        lbl.setPreferredSize(new Dimension(x,20));
        centerPanel.add(lbl);
    }
}
