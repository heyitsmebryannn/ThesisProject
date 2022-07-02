package com.userInfo;

import com.thesis.ConnectDatabase;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.util.Date;
import java.util.Objects;
import java.util.Random;


import static java.awt.Cursor.HAND_CURSOR;

public class AddUserForm extends JPanel implements ActionListener,KeyListener {
    JButton btnClear,btnAdd,btnUpload;
    JPanel centerPanel,eastPanel,southPanel,westPanel,southCenterPanel,southNorthPanel,southEastPanel,southWestPanel;
    JLabel lblTitle1,lblTitle2,
           lblFirstName,lblLastName,lblMiddleName,
           lblAddress,lblContact,lblPosition,lblImage,
            lblUserID,lblUsername,lblPassword,lblConfirmPassword;
    JLabel lblUserIDWarning,lblUserNameWarning,lblConfirmPasswordWarning,lblPasswordWarning;
    JTextField txtFirstName,txtLastName,txtMiddleName,
                txtAddress,txtContact,
                txtUserID,txtUsername;
    JPasswordField txtPassword,txtConfirmPassword;
    JComboBox<String> cmbPosition;


    Font fontTitle = new Font("Poppins SemiBold",Font.BOLD,25);
    Font fontLabel = new Font("Poppins SemiBold",Font.PLAIN,15);
    String filename = new File("images/user-icon.png").getAbsolutePath();
    byte[] person_image = null;
    PreparedStatement preparedStatement;
    ImageIcon lblIcon;
    Dimension dimension;
    Date date;
    public AddUserForm(){
        UserInformationForm userInformationForm = new UserInformationForm();
        this.setBackground(userInformationForm.centerPanel.getBackground());
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(userInformationForm.centerPanel.getPreferredSize().width,userInformationForm.centerPanel.getPreferredSize().height));
        this.setBorder(userInformationForm.centerPanel.getBorder());
       createUIComponents();
    }
    private void createUIComponents(){
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE.darker().darker());
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(740,400));

        eastPanel = new JPanel();
        eastPanel.setBackground(this.getBackground());
        eastPanel.setLayout(new FlowLayout());
        eastPanel.setOpaque(false);
        eastPanel.setPreferredSize(new Dimension(400,dimension.height));

        westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setLayout(new FlowLayout());
        westPanel.setPreferredSize(new Dimension(30,dimension.height));

        southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setLayout(new BorderLayout());
        southPanel.setPreferredSize(new Dimension(dimension.width-400,340));

        southNorthPanel = new JPanel();
        southNorthPanel.setPreferredSize(new Dimension(740,40));
        southNorthPanel.setOpaque(false);
        southNorthPanel.setLayout(new BorderLayout());
        southPanel.add(southNorthPanel,BorderLayout.NORTH);

        southCenterPanel = new JPanel();
        southCenterPanel.setOpaque(false);
        southCenterPanel.setLayout(new FlowLayout());
        southCenterPanel.setPreferredSize(new Dimension(740,300));
        southPanel.add(southCenterPanel,BorderLayout.CENTER);

        southEastPanel = new JPanel();
        southEastPanel.setBackground(this.getBackground());
        southEastPanel.setLayout(new FlowLayout());
        southEastPanel.setPreferredSize(new Dimension(400,dimension.height-600));
        southPanel.add(southEastPanel,BorderLayout.EAST);

        southWestPanel = new JPanel();
        southWestPanel.setOpaque(false);
        southWestPanel.setLayout(new FlowLayout());
        southWestPanel.setPreferredSize(new Dimension(30,dimension.height-600));
        southPanel.add(southWestPanel,BorderLayout.WEST);


        lblTitle1 = new JLabel("Personal Information");
        lblTitle1.setFont(fontTitle);
        lblTitle1.setPreferredSize(new Dimension(300,40));
        lblTitle1.setHorizontalAlignment(JLabel.LEFT);
        centerPanel.add(lblTitle1);
        spacingPanel(300,40,centerPanel);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,3,50,5));
        panel.setBackground(this.getBackground());
        panel.setPreferredSize(new Dimension(600,65));
        centerPanel.add(panel);

        textField(txtFirstName = new JTextField(),"Enter First Name");
        panel.add(txtFirstName);

        textField(txtMiddleName = new JTextField(),"Enter Middle Name");
        panel.add(txtMiddleName);

        textField(txtLastName = new JTextField(),"Enter Last Name");
        panel.add(txtLastName);
        Label(lblFirstName = new JLabel(),"First Name");
        panel.add(lblFirstName);

        Label(lblMiddleName = new JLabel(),"Middle Name");
        panel.add(lblMiddleName);

        Label(lblLastName = new JLabel(),"Last Name");
        panel.add(lblLastName);

        spacingPanel(600,5,centerPanel);

        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(600,30));
        panel1.setLayout(new BorderLayout(30,0));
        panel1.setOpaque(false);
        centerPanel.add(panel1);
        textField(txtAddress = new JTextField(),"Enter Address");
        txtAddress.setPreferredSize(new Dimension(400,30));
        panel1.add(txtAddress,BorderLayout.WEST);

        JPanel panel11 = new JPanel();
        panel11.setBackground(this.getBackground());
        panel1.add(panel11,BorderLayout.CENTER);

        textField(txtContact = new JTextField(),"Enter Contact Number");
        txtContact.setPreferredSize(new Dimension(180,30));
        panel1.add(txtContact,BorderLayout.EAST);


        JPanel panel12 = new JPanel();
        panel12.setPreferredSize(new Dimension(600,30));
        panel12.setLayout(new BorderLayout(30,0));
        panel12.setOpaque(false);
        centerPanel.add(panel12);

        Label(lblAddress = new JLabel(),"Complete Address");
        lblAddress.setPreferredSize(new Dimension(400,30));
        panel12.add(lblAddress,BorderLayout.WEST);

        JPanel panel13 = new JPanel();
        panel11.setBackground(this.getBackground());
        panel1.add(panel13,BorderLayout.CENTER);

        Label(lblContact = new JLabel(),"Contact Number");
        lblContact.setPreferredSize(new Dimension(170,30));
        panel12.add(lblContact,BorderLayout.EAST);
        spacingPanel(600,20,centerPanel);

        lblTitle2 = new JLabel("        Account Credentials");
        lblTitle2.setFont(fontTitle);
        lblTitle2.setPreferredSize(new Dimension(300,40));
        lblTitle2.setHorizontalAlignment(JLabel.LEFT);
        southNorthPanel.add(lblTitle2,BorderLayout.NORTH);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(9,2,50,5));
        panel2.setPreferredSize(new Dimension(570,300));
        panel2.setOpaque(false);
        southCenterPanel.add(panel2);

       textField(txtUserID = new JTextField(),"User ID");
       txtUserID.setEditable(false);
       panel2.add(txtUserID);

        textField(txtUsername = new JTextField(), "Enter Username");
        panel2.add(txtUsername);

        Label(lblUserID = new JLabel(),"User ID");
        panel2.add(lblUserID);


        Label(lblUsername = new JLabel(),"Username");
        panel2.add(lblUsername);


        lblWarning(lblUserIDWarning = new JLabel());
        panel2.add(lblUserIDWarning);
        lblWarning(lblUserNameWarning= new JLabel());
        panel2.add(lblUserNameWarning);


        String[] position = {"Admin","For POS/Inventory","For Reports"};
        cmbPosition = new JComboBox<>(position);
        cmbPosition.setFont(fontLabel);
        cmbPosition.setToolTipText("Choose a Position");
        cmbPosition.setPreferredSize(new Dimension(200,30));
        cmbPosition.setBorder(new LineBorder(Color.BLACK,2,true));
        cmbPosition.setSelectedItem(null);
        panel2.add(cmbPosition);


        textField(txtPassword = new JPasswordField(),"Enter Password");
        panel2.add(txtPassword);

        Label(lblPosition = new JLabel(),"Position");
        panel2.add(lblPosition);

        Label(lblPassword = new JLabel(),"Password");
        panel2.add(lblPassword);

        JPanel freePanel = new JPanel();
        freePanel.setOpaque(false);
        panel2.add(freePanel);

        lblWarning(lblPasswordWarning = new JLabel());
        panel2.add(lblPasswordWarning);

       spacingPanel(100,40,panel2);

        textField(txtConfirmPassword = new JPasswordField(),"Confirm the Password");
        panel2.add(txtConfirmPassword);

        spacingPanel(100,40,panel2);

        Label(lblConfirmPassword = new JLabel(),"Confirm Password");
        panel2.add(lblConfirmPassword);


        spacingPanel(100,40,panel2);

        lblWarning(lblConfirmPasswordWarning = new JLabel());
        panel2.add(lblConfirmPasswordWarning);


        lblImage = new JLabel();
        lblImage.setOpaque(true);
        lblImage.setBorder(new LineBorder(Color.BLACK,2,false));
        lblImage.setPreferredSize(new Dimension(150,150));
        lblIcon = new ImageIcon(new ImageIcon("images/user-icon.png").getImage().getScaledInstance(lblImage.getPreferredSize().width,lblImage.getPreferredSize().height,Image.SCALE_SMOOTH));
        lblImage.setIcon(lblIcon);
        lblImage.setVerticalAlignment(JLabel.CENTER);
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        eastPanel.add(lblImage);
        spacingPanel(eastPanel.getPreferredSize().width,1,eastPanel);

        btnUpload = new JButton("Upload Image");
        btnUpload.setFont(fontLabel);
        btnUpload.setFocusable(false);
        btnUpload.setBackground(new Color(195,195,195));
        btnUpload.setBorder(new LineBorder(Color.BLACK,2,false));
        btnUpload.setPreferredSize(new Dimension(150,30));
        btnUpload.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btnUpload.addActionListener(this);
        eastPanel.add(btnUpload);


        spacingPanel(southEastPanel.getPreferredSize().width,30,southEastPanel);
        button(btnAdd = new JButton(),"Add");
        spacingPanel(southEastPanel.getPreferredSize().width,30,southEastPanel);
        button(btnClear = new JButton(),"Clear");


        try{
            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
            person_image = bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }

        this.add(centerPanel,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(westPanel,BorderLayout.WEST);

        date = new Date();
        txtUserID.setText(date.getYear()+""+generateRandom(10));
    }
    private void textField(JTextField txt,String text){
        txt.setFont(fontLabel);
        txt.setHorizontalAlignment(JTextField.LEADING);
        txt.setToolTipText(text);
        txt.setBorder(new LineBorder(Color.BLACK,2,false));
        txt.addKeyListener(this);

    }
    private void Label(JLabel lbl,String text){
        lbl.setText(text);
        lbl.setFont(fontLabel);
        lbl.setHorizontalAlignment(JLabel.CENTER);


    }
    private void button(JButton btn,String text){
        btn.setFont(fontLabel);
        btn.setText(text);
        btn.setFocusable(false);
        btn.setBackground(new Color(202,170,86));
        btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED),BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED)));
        btn.setPreferredSize(new Dimension(150,70));
        btn.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btn.addActionListener(this);
        southEastPanel.add(btn);
    }
    private void lblWarning(JLabel warning){
        warning.setPreferredSize(new Dimension(200,30));
        warning.setFont(new Font("Arial",Font.BOLD,10));

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnUpload) {
            try {
                ImageIcon imageIcon;
                JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")+System.getProperty("file.separator")+ "Downloads"));
                chooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileChooser = new FileNameExtensionFilter("Images", "jpeg","jpg","png","bmp");
                chooser.addChoosableFileFilter(fileChooser);
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                File file = new File("images/user-icon.png");
                filename = Objects.requireNonNullElse(f, file).getAbsolutePath();
                imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight              (), Image.SCALE_DEFAULT));
                if (filename == null) {
                    lblImage.setIcon(new ImageIcon("images/user-icon.png"));
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
                person_image = bos.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == btnAdd){

                if(txtFirstName.getText().equals("") ||
                        txtMiddleName.getText().isEmpty() ||
                        txtLastName.getText().isEmpty() ||
                        txtContact.getText().isEmpty() ||
                        txtAddress.getText().isEmpty() ||
                        txtUsername.getText().isEmpty() ||
                        cmbPosition.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null,"There's an Empty Field. Please fill out");
                } else if (txtPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Don't leave the password field empty");
                } else if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                    String firstName = txtFirstName.getText();
                    String middleName = txtMiddleName.getText();
                    String lastName = txtLastName.getText();
                    String contact = txtContact.getText();
                    String address = txtAddress.getText();
                    String userID = txtUserID.getText();
                    String userName = txtUsername.getText();
                    String position = Objects.requireNonNull(cmbPosition.getSelectedItem()).toString();
                    String pass = txtPassword.getText();
                    String UsersQuery = "INSERT INTO users_list (user_id,username,password,user_position) values ('" + userID + "',                        '" + userName + "','" + pass + "','" + position + "')";

                    String userInfoQuery = "INSERT INTO USER_INFO (user_id, user_fname, user_middle, user_lname, user_contact," +
                            "user_address, user_image) values ('" + userID + "','" + firstName + "','" + middleName + "','" + lastName +
                            "','" + contact + "','" + address + "',?) ";
                    try {
                        Connection con = ConnectDatabase.connectDB();
                        int confirm = JOptionPane.showConfirmDialog(null, "Confirm Add?", "Confirm",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (confirm == 0) {
                            assert con != null;
                            preparedStatement = con.prepareStatement(UsersQuery);
                            preparedStatement.execute();

                            preparedStatement = con.prepareStatement(userInfoQuery);
                            preparedStatement.setBytes(1, person_image);
                            preparedStatement.execute();

                            String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','ADD USER','"+txtUserID.getText()+"')";

                            preparedStatement = con.prepareStatement(auditQuery);
                            preparedStatement.execute();

                            String pathname = "images/userBarcode/";
                            generateBarcode(pathname, userID);

                            JOptionPane.showMessageDialog(null, "User Added Successfully");
                            clearData();



                        } else if (confirm == 1) {
                            JOptionPane.showMessageDialog(this, "Not Added", "User not Added", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    } finally {
                        try {
                            preparedStatement.close();
                        } catch (Exception ignored) {

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Password not match", "Invalid", JOptionPane.ERROR_MESSAGE);
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                }

        }
        if(e.getSource()== btnClear){
            clearData();
        }

    }
    public boolean checkUserID(String userid) {
        PreparedStatement pst;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM users_list WHERE user_id =?";

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
    private boolean checkUsername(String username){
        PreparedStatement pst;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM users_list WHERE username =?";

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



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        final boolean b = Character.isLetter(c) || Character.isISOControl(c) || e.getExtendedKeyCode() == KeyEvent.VK_SPACE;
        final boolean d =
                e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE;
        if(e.getSource() == txtContact){
            String phoneNumber = txtContact.getText();
            int length = phoneNumber.length();

            if(e.getKeyChar()>='0' && e.getKeyChar()<='9'){
                txtContact.setEditable(length < 11);
            } else {
                txtContact.setEditable(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE);
            }
        }
        if(e.getSource() == txtFirstName){
            txtFirstName.setEditable(b);
        }
        if(e.getSource() == txtLastName){
            txtLastName.setEditable(b);
        }
        if(e.getSource() == txtMiddleName){
            txtMiddleName.setEditable(b);
        }


        if(e.getSource() == txtUsername){
            txtUsername.setEditable(Character.isLetter(c)||Character.isISOControl(c)||Character.isDigit(c));
            txtUsername.setEditable(txtUsername.getText().length()<15||d);
        }
        if(e.getSource() == txtPassword){
            txtPassword.setEditable(txtPassword.getText().length()<12||d);
        }
        if(e.getSource() == txtConfirmPassword){
           txtConfirmPassword.setEditable(txtConfirmPassword.getText().length()<12||d);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == txtUserID){
            String userID = txtUserID.getText();
            if(checkUserID(userID))
            {
                txtUserID.setBackground(Color.red);
                lblUserIDWarning.setText("This User ID Already Exist");
                btnAdd.setEnabled(false);
            }
            else {
                lblUserIDWarning.setText("");
                txtUserID.setBackground(Color.white);
                Border redBorder = new LineBorder(Color.RED,2,false);
                btnAdd.setEnabled(txtUsername.getBackground() != Color.RED || !txtConfirmPassword.getBorder().equals(redBorder) || txtUserID.getBackground() != Color.RED);
            }
        }
        if(e.getSource() == txtUsername){
            String username = txtUsername.getText();
            if(checkUsername(username))
            {
                txtUsername.setBackground(Color.red);
                lblUserNameWarning.setText("This Username Already Exist");
                btnAdd.setEnabled(false);
            }
            else {
                lblUserNameWarning.setText("");
                txtUsername.setBackground(Color.white);
                btnAdd.setEnabled(true);
            }
        }
        if(e.getSource() == txtConfirmPassword) {
            if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
                txtConfirmPassword.setBorder(new LineBorder(Color.RED, 3, false));
                lblConfirmPasswordWarning.setText("Password does not match");
            } else if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                txtConfirmPassword.setBorder(new LineBorder(Color.GREEN, 3, false));
                lblConfirmPasswordWarning.setText("Password matched");
            }
        }
    }
    private void clearData(){
        date = new Date();
        txtUserID.setText(date.getYear()+""+generateRandom(10));
        txtFirstName.setText(null);
        txtMiddleName.setText(null);
        txtLastName.setText(null);
        txtContact.setText(null);
        txtAddress.setText(null);
        txtUsername.setText(null);
        cmbPosition.setSelectedItem(null);
        txtPassword.setText(null);
        txtConfirmPassword.setText(null);
        lblImage.setIcon(lblIcon);
        txtConfirmPassword.setBorder(new LineBorder(Color.BLACK,2,false));
        lblConfirmPasswordWarning.setText("");
        lblPasswordWarning.setText("");
        lblUserNameWarning.setText("");
        txtUsername.setBackground(null);


    }
    private void spacingPanel(int x,int y,JPanel panel1){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(x,y));
        panel.setBackground(this.getBackground());
        panel1.add(panel);
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

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

}
