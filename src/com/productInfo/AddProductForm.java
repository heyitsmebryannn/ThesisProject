package com.productInfo;

import com.thesis.ConnectDatabase;
import com.userInfo.AllUsers;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static java.awt.Cursor.HAND_CURSOR;


public class AddProductForm extends JPanel implements ActionListener, KeyListener {
    JTextField txtProductID,txtProductName,txtProductBrand,txtProductSize,txtProductPrice,txtProductQuantity;
    JLabel lblTitle,lblPID,lblPName,lblPBrand,lblPType,lblPSize,lblImage,lblPDescription,lblPrice,lblPQuantity;
    JLabel lblPNameWarning;
    JComboBox<String> cmbProductType,cmbProductSize,cmbSecondSize,cmbQuantity;
    JButton btnUpload,btnAdd,btnClear,btnSelectBarCode;
    JRadioButton rdbLength,rdbSizes;
    ButtonGroup btnGroupSize;
    JTextArea txtDescription;
    Font txtFont = new Font("Poppins SemiBold",Font.BOLD,15);
    Font titleFont = new Font("Poppins SemiBold",Font.BOLD,20);
    Font lblFont = new Font("Poppins SemiBold",Font.BOLD,18);
    String[] types;
    String[] size;
    String[] secondSize;
    String[] quantity;
    JPanel centerPanel,eastPanel;
    JCheckBox chkWarranty;

    byte[] product_image = null;
    String filename = new File("images/white.png").getAbsolutePath();

    PreparedStatement preparedStatement;
    public AddProductForm(){

        InventoryForm inventoryForm = new InventoryForm();
        this.setBackground(inventoryForm.centerPanel.getBackground());
        this.setLayout(new BorderLayout());
        this.setBorder(new LineBorder(Color.BLACK,5,false));
        this.setPreferredSize(new Dimension(inventoryForm.centerPanel.getPreferredSize().width, inventoryForm.centerPanel.getPreferredSize().height));
        Dimension dimension = this.getPreferredSize();

        centerPanel = new JPanel();
        centerPanel.setBackground(this.getBackground());
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(dimension.width-500,dimension.height));

        eastPanel = new JPanel();
        eastPanel.setBackground(this.getBackground());
        eastPanel.setLayout(new FlowLayout());
        eastPanel.setPreferredSize(new Dimension(dimension.width-centerPanel.getPreferredSize().width,dimension.height));


        lblTitle = new JLabel("Add Product");
        lblTitle.setFont(titleFont);
        lblTitle.setHorizontalAlignment(JLabel.LEFT);
        lblTitle.setPreferredSize(new Dimension(250,60));
        centerPanel.add(lblTitle);


        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(296,60));
        panel.setOpaque(false);
        panel.setLayout(null);
        centerPanel.add(panel);


        chkWarranty = new JCheckBox();
        chkWarranty.setFont(new Font("Poppins SemiBold",Font.PLAIN,14));
        chkWarranty.setText("Check if this Product Has Warranty");
        chkWarranty.setFocusable(false);
        chkWarranty.setOpaque(false);
        chkWarranty.setBounds(0,20,280,30);
        panel.add(chkWarranty);



        txtField(txtProductID = new JTextField(),"Enter Product ID",300);

        spacingPanel(20,20,centerPanel);
        btnSelectBarCode = new JButton();
        btnSelectBarCode.setPreferredSize(new Dimension(140,30));
        btnSelectBarCode.setText("Generate ID");
        btnSelectBarCode.setFocusable(false);
        btnSelectBarCode.setOpaque(false);
        btnSelectBarCode.setHorizontalAlignment(SwingConstants.CENTER);
        Date date = new Date();
        btnSelectBarCode.addActionListener(e -> {
            txtProductID.setText((date.getYear()+""+generateRandom(10)));
            if(checkProductID(txtProductID.getText())){
                txtProductID.setText((date.getYear()+""+generateRandom(10)));
            }
        });
        centerPanel.add(btnSelectBarCode);
        spacingPanel(35,20,centerPanel);

        LabelName(lblPID = new JLabel(),"Product ID",300);
        spacingPanel(200,20,centerPanel);
        spacingPanel(centerPanel.getPreferredSize().width,10,centerPanel);


        txtField(txtProductName = new JTextField(),"Enter Product Name",460);
        spacingPanel(50,20,centerPanel);
        LabelName(lblPName = new JLabel(),"Product Name",250);

        lblPNameWarning = new JLabel();
        lblPNameWarning.setPreferredSize(new Dimension(250,20));
        lblPNameWarning.setText("");
        lblPNameWarning.setFont(new Font("Poppins SemiBold",Font.BOLD,10));

        centerPanel.add(lblPNameWarning);
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
        txtProductSize.setEnabled(false);
        size = new String[]{"Millimeter","Centimeter","Inch","Meter","Feet","Yard","Ounces","Pieces","Bundle"};
        cmbProductSize = new JComboBox<>(size);
        cmbProductSize.setFont(txtFont);
        cmbProductSize.setToolTipText("Choose a Type");
        cmbProductSize.setSelectedItem(null);
        cmbProductSize.setEnabled(false);
        cmbProductSize.setPreferredSize(new Dimension(150,25));
        centerPanel.add(cmbProductSize);
        spacingPanel(50,20,centerPanel);

        secondSize = new String[]{"Small","Medium","Big","Large"};
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
        rdbLength.addActionListener(this);
        centerPanel.add(rdbLength);
        spacingPanel(100,20,centerPanel);


        rdbSizes = new JRadioButton("By Sizes");
        rdbSizes.setFont(txtFont);
        rdbSizes.setPreferredSize(new Dimension(100,30));
        rdbSizes.setOpaque(false);
        rdbSizes.addActionListener(this);
        centerPanel.add(rdbSizes);
        spacingPanel(70,20,centerPanel);

        LabelName(lblPSize = new JLabel(),"Product Size",460);
        spacingPanel(50,20,centerPanel);
        spacingPanel(centerPanel.getPreferredSize().width,10,centerPanel);

        txtField(txtProductQuantity = new JTextField(),"Enter Quantity",200);
        spacingPanel(50,20,centerPanel);

        quantity = new String[]{"Kilo(s)","Gram(s)","Milligram(s)","Millimeter","Centimeter","Inch","Meter","Feet","Yard","Ounces","Piece","Bundle"};
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


        btnUpload = new JButton("Upload Image");
        btnUpload.setFont(new Font("Poppins SemiBold",Font.BOLD,15));
        btnUpload.setFocusable(false);
        btnUpload.setBorder(new LineBorder(Color.BLACK,2,false));
        btnUpload.setPreferredSize(new Dimension(200,25));
        btnUpload.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btnUpload.addActionListener(this);
        eastPanel.add(btnUpload);
        spacingPanel(eastPanel.getPreferredSize().width,10,eastPanel);


        txtProductPrice = new JTextField();
        txtProductPrice.setFont(txtFont);
        txtProductPrice.setHorizontalAlignment(JTextField.LEADING);
        txtProductPrice.setToolTipText("Product Price");
        txtProductPrice.setPreferredSize(new Dimension(400,30));
        txtProductPrice.setBorder(new LineBorder(Color.BLACK,2,false));
        txtProductPrice.addKeyListener(this);
        eastPanel.add(txtProductPrice);

        lblPrice = new JLabel();
        lblPrice.setText("Product Price");
        lblPrice.setFont(lblFont);
        lblPrice.setVerticalTextPosition(JLabel.CENTER);
        lblPrice.setHorizontalAlignment(JLabel.LEADING);
        lblPrice.setPreferredSize(new Dimension(400,30));
        eastPanel.add(lblPrice);

        spacingPanel(eastPanel.getPreferredSize().width,10,eastPanel);


        txtDescription = new JTextArea(10,10);
        txtDescription.setToolTipText("Enter Description Here");
        txtDescription.setFont(txtFont);
        JScrollPane scrollPane = new JScrollPane(txtDescription, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400,140));
        scrollPane.setBorder(new LineBorder(Color.BLACK,1,false));
        eastPanel.add(scrollPane);

        lblPDescription = new JLabel();
        lblPDescription.setText("Product Description");
        lblPDescription.setFont(lblFont);
        lblPDescription.setVerticalTextPosition(JLabel.CENTER);
        lblPDescription.setHorizontalAlignment(JLabel.LEADING);
        lblPDescription.setPreferredSize(new Dimension(400,30));
        eastPanel.add(lblPDescription);

        spacingPanel(eastPanel.getPreferredSize().width,20,eastPanel);
        button(btnAdd= new JButton(),"Add");
        spacingPanel(70,50,eastPanel);
        button(btnClear = new JButton(),"Clear");




        btnGroupSize = new ButtonGroup();
        btnGroupSize.add(rdbLength);
        btnGroupSize.add(rdbSizes);


        this.add(centerPanel,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);



        try{
            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
            product_image = bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }

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
        txtField.setBorder(new LineBorder(Color.BLACK,1,false));
        txtField.addKeyListener(this);
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
    private void button(JButton btn,String text) {
        btn.setFont(lblFont);
        btn.setText(text);
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(150,50));
        btn.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btn.addActionListener(this);
        eastPanel.add(btn);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnUpload){
            try {
                ImageIcon imageIcon;
                JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")+System.getProperty("file.separator")+ "Downloads"));
                chooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileChooser = new FileNameExtensionFilter("Images", "jpeg","jpg","png","bmp");
                chooser.addChoosableFileFilter(fileChooser);
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String filenames= f.getName();

                File file = new File("images/white.png");
                filename = Objects.requireNonNullElse(f, file).getAbsolutePath();

                imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight              (), Image.SCALE_DEFAULT));
                if (filename == null) {
                    lblImage.setIcon(new ImageIcon("images/white.png"));
                } else {
                    lblImage.setIcon(imageIcon);
                }

                File image = new File(filename);
                FileInputStream fis = new FileInputStream(image);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum);
                }
                product_image = bos.toByteArray();

                Image image1 = new ImageIcon(filename).getImage();


                BufferedImage bufferedImage = new BufferedImage(image1.getWidth(null),image1.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics2D = bufferedImage.createGraphics();
                graphics2D.drawImage(image1,0,0,null);
                graphics2D.dispose();

                File file1 = new File("images/product_images/"+filenames);
                ImageIO.write(bufferedImage,"png",file1);

            }catch (NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No Image Selected","No Image",JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        if(e.getSource() == rdbLength){
            txtProductSize.setEnabled(true);
            cmbProductSize.setEnabled(true);
            cmbSecondSize.setEnabled(false);
            cmbProductSize.setSelectedItem(null);
        }
        if(e.getSource() == rdbSizes){
            txtProductSize.setEnabled(false);
            cmbProductSize.setEnabled(false);
            cmbSecondSize.setEnabled(true);
            cmbProductSize.setSelectedItem(null);
            txtProductSize.setText("");
        }

        if(e.getSource() == btnAdd){

          if(     txtProductID.getText().isEmpty() ||
                  txtProductPrice.getText().isEmpty() ||
                          txtProductName.getText().isEmpty() ||
                          txtProductBrand.getText().isEmpty() ||
                          cmbProductType.getSelectedItem() == null ||
                          txtProductQuantity.getText().isEmpty() ||
                          txtDescription.getText().isEmpty()

          ){
                JOptionPane.showMessageDialog(new InventoryForm(),"There's an Empty Field,Please fill out","Warning",JOptionPane.WARNING_MESSAGE);
          }
          else{
              try{
              String prodID = txtProductID.getText();
              String prodName = txtProductName.getText();
              String prodBrand = txtProductBrand.getText();
              String prodType = String.valueOf(cmbProductType.getSelectedItem());
              String prodSize = "";
              String prodLength = "";
              String sizeType = "";
              if(rdbLength.isSelected()){
                  prodSize = txtProductSize.getText();
                  prodLength = String.valueOf(cmbProductSize.getSelectedItem());
                  sizeType = "By Length";
              }
              else if(rdbSizes.isSelected()){
                  prodSize = String.valueOf(cmbSecondSize.getSelectedItem());
                  sizeType = "By Size";
              }
              String checkWarranty;
              if(chkWarranty.isSelected()){
                  checkWarranty = "5 Month Warranty";
              }
              else {
                  checkWarranty = "No Warranty";
              }
              String prodQuantity = txtProductQuantity.getText();
              String prodQuantityType = String.valueOf(cmbQuantity.getSelectedItem());
              String prodPrice = txtProductPrice.getText();
              String prodDescription = txtDescription.getText();

              String productQuery = "INSERT INTO products (Product_ID,Product_Name,Product_Brand,Product_Type,Product_Size,Product_Length,Size_Type,Product_Quantity,Product_Quantity_Type,Product_Price,Product_Description,Product_Image,hasWarranty) values ('"+prodID+"','"+prodName+"','"+prodBrand+"','"+prodType+"','"+prodSize+"','"+prodLength+"','"+sizeType+"','"+prodQuantity+"','"+prodQuantityType+"','"+prodPrice+"','"+prodDescription+"',?,'"+checkWarranty+"')";
              Connection connection = ConnectDatabase.connectDB();
              int a = JOptionPane.showConfirmDialog(new InventoryForm(),"Are you sure?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
              if(a == 0){
                  assert connection != null;
                  preparedStatement = connection.prepareStatement(productQuery);
                  preparedStatement.setBytes(1,product_image);
                  preparedStatement.execute();

                  String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','ADD PRODUCT','"+txtProductID.getText()+"')";
                  preparedStatement = connection.prepareStatement(auditQuery);
                  preparedStatement.execute();

                  String deleteIdQuery = "Delete from barcode_number where barcode_Number = '"+txtProductID.getText()+"' ";
                  preparedStatement = connection.prepareStatement(deleteIdQuery);
                  preparedStatement.execute();
                  AllUsers.prodID = txtProductID.getText();
                  AllUsers.prodName = txtProductName.getText();
                  AllUsers.prodSize = prodSize;

                  String pathname = "images/productBarcode/";
                  generateBarcode(pathname, prodID);


                  JOptionPane.showMessageDialog(new InventoryForm(),"Product Added Successfully!");
                  txtProductID.setText(null);
                  txtProductName.setText(null);
                  txtProductBrand.setText(null);
                  cmbProductType.setSelectedItem(null);
                  cmbProductSize.setSelectedItem(null);
                  cmbProductSize.setEnabled(false);
                  txtProductSize.setText(null);
                  txtProductSize.setEnabled(false);
                  cmbSecondSize.setSelectedItem(null);
                  cmbSecondSize.setEnabled(false);
                  txtProductQuantity.setText(null);
                  cmbQuantity.setSelectedItem(null);
                  txtProductPrice.setText(null);
                  txtDescription.setText(null);
                  lblImage.setIcon(new ImageIcon("images/white.png"));
                  rdbSizes.setSelected(false);
                  rdbLength.setSelected(false);
                  }else if(a == JOptionPane.NO_OPTION){
                      JOptionPane.showMessageDialog(new InventoryForm(),"Product Not Added","Not Added",JOptionPane.ERROR_MESSAGE);
                  }

              }catch (Exception ex){
                  ex.printStackTrace();
                  JOptionPane.showMessageDialog(this,"There's an Empty field,Please fill out","Empty Field",JOptionPane.WARNING_MESSAGE);
              } finally {
                  try {
                      preparedStatement.close();
                  } catch (Exception ignored) {

                  }
              }
          }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        final boolean d =
                e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE;
        if(e.getSource() == txtProductPrice){
                txtProductPrice.setEditable(d ||Character.isDigit(c)||e.getExtendedKeyCode() == KeyEvent.VK_PERIOD);
        } if(e.getSource() == txtProductID){
            String phoneNumber = txtProductID.getText();
            int length = phoneNumber.length();

            if(e.getKeyChar()>='0' && e.getKeyChar()<='9'){
                txtProductID.setEditable(length < 15);
            } else {
                txtProductID.setEditable(d);
            }
        }
        if(e.getSource() == txtProductSize){
            String sizeNumber = txtProductSize.getText();
            int length = sizeNumber.length();

            if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
                txtProductSize.setEditable(length < 5);
            }
            else{
                txtProductSize.setEditable(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() ==KeyEvent.VK_DELETE||Character.isDigit(c)||e.getExtendedKeyCode() == KeyEvent.VK_PERIOD);
            }
        }

        if(e.getSource() == txtProductQuantity){
            String sizeNumber = txtProductQuantity.getText();
            int length = sizeNumber.length();

            if(e.getKeyChar()>='0' && e.getKeyChar()<='9') {
                txtProductQuantity.setEditable(length < 5);
            }
            else{
                txtProductQuantity.setEditable(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() ==KeyEvent.VK_DELETE||Character.isDigit(c)||e.getExtendedKeyCode() == KeyEvent.VK_PERIOD);
            }


        }


    }
    @Override
    public void keyReleased(KeyEvent e) {

            if(e.getSource() == txtProductName){
                char c = e.getKeyChar();
                if(Character.isDigit(c)){
                    btnAdd.setEnabled(false);
                } else if (Character.isLetterOrDigit(c)) {
                    btnAdd.setEnabled(true);
                }

                if(!checkUsername(txtProductName.getText().trim())){
                    btnAdd.setEnabled(true);
                    txtProductName.setBorder(new LineBorder(Color.BLACK,1,false));
                    lblPNameWarning.setText("");
                }else{
                    btnAdd.setEnabled(false);
                    txtProductName.setBorder(new LineBorder(Color.RED,1,false));
                    lblPNameWarning.setText("Product already Registered");
                }
            }
    }
    private boolean checkUsername(String username){
        PreparedStatement pst;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM products WHERE product_name =?";

        try {
            Connection con = ConnectDatabase.connectDB();
            assert con != null;
            pst = con.prepareStatement(query);
            pst.setString(1,username);
            rs = pst.executeQuery();
            if(rs.next())
            {
                checkUser = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return checkUser;
    }



    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public boolean checkProductID(String userid) {
        PreparedStatement pst;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM products WHERE product_ID =?";

        try {
            Connection con = ConnectDatabase.connectDB();
            assert con != null;
            pst = con.prepareStatement(query);
            pst.setString(1,userid);
            rs = pst.executeQuery();
            if(rs.next())
            {
                checkUser = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return checkUser;
    }

    private void generateBarcode(String pathname,String barcodeNumber){
        try {
            Code128Bean code128Bean = new Code128Bean();
            code128Bean.setHeight(15f);
            code128Bean.setModuleWidth(0.3);
            code128Bean.setQuietZone(10);
            code128Bean.doQuietZone(true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitmapCanvasProvider bitmapCanvasProvider = new BitmapCanvasProvider(byteArrayOutputStream,"image/x-png",300, BufferedImage.TYPE_BYTE_BINARY,false,0);
            code128Bean.generateBarcode(bitmapCanvasProvider, barcodeNumber);
            bitmapCanvasProvider.finish();
            FileOutputStream fileOutputStream = new FileOutputStream(pathname+barcodeNumber+".png");
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
