package com.userInfo;


import com.thesis.ConnectDatabase;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Objects;

import static java.awt.Cursor.HAND_CURSOR;

public class UpdateUserForm extends JPanel implements ActionListener, KeyListener {
    JButton btnUpdate,btnUpload;
    JPanel centerPanel,eastPanel,southPanel,westPanel,southCenterPanel,southNorthPanel,southEastPanel,southWestPanel;
    JLabel lblTitle1,lblTitle2,
            lblFirstName,lblLastName,lblMiddleName,
            lblAddress,lblContact,lblPosition,lblImage,
            lblUserID,lblUsername,lblPassword,lblConfirmPassword;
    JLabel lblUserIDWarning,lblUserNameWarning,lblEmailWarning,lblConfirmPasswordWarning,lblPasswordWarning;
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


    public UpdateUserForm(){
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
        txtContact.setEditable(AllUsers.position.equalsIgnoreCase("Admin"));
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

        textField(txtUserID = new JTextField(),"Enter a User ID");
        txtUserID.setEditable(false);
        panel2.add(txtUserID);

        textField(txtUsername = new JTextField(), "Enter Username");
        txtUsername.setEditable(false);
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
        cmbPosition.setEnabled(AllUsers.position.equalsIgnoreCase("Admin"));
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

        spacingPanel(20,20,panel2);
        textField(txtConfirmPassword = new JPasswordField(),"Confirm the Password");
        panel2.add(txtConfirmPassword);

        spacingPanel(20,20,panel2);
        Label(lblConfirmPassword = new JLabel(),"Confirm Password");
        panel2.add(lblConfirmPassword);


        lblWarning(lblEmailWarning = new JLabel());
        panel2.add(lblEmailWarning);

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
        btnUpload.setEnabled(AllUsers.position.equalsIgnoreCase("Admin"));
        eastPanel.add(btnUpload);


        spacingPanel(southEastPanel.getPreferredSize().width,30,southEastPanel);
        button(btnUpdate = new JButton());


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

        if(!AllUsers.position.equalsIgnoreCase("Admin")){
            txtFirstName.setEditable(false);
            txtLastName.setEditable(false);
            txtMiddleName.setEditable(false);
        }

        this.add(centerPanel,BorderLayout.CENTER);
        this.add(eastPanel,BorderLayout.EAST);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(westPanel,BorderLayout.WEST);
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
    private void button(JButton btn){
        btn.setFont(fontLabel);
        btn.setText("Update");
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
                JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Downloads"));
                chooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileChooser = new FileNameExtensionFilter("Images", "jpeg", "jpg", "png", "bmp");
                chooser.addChoosableFileFilter(fileChooser);
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                filename = f.getAbsolutePath();
                imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblImage.getPreferredSize().width, lblImage.getPreferredSize().height, Image.SCALE_SMOOTH));
                lblImage.setIcon(imageIcon);

                if(filename != null){
                    File image = new File(filename);
                    FileInputStream fis = new FileInputStream(image);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                        bos.write(buf, 0, readNum);
                    }
                    person_image = bos.toByteArray();
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(new UserInformationForm(),"No Image Selected","No image",JOptionPane.WARNING_MESSAGE);
            }
        }


        if(e.getSource() == btnUpdate){
            if(txtFirstName.getText().equals("") ||
                    txtMiddleName.getText().isEmpty() ||
                    txtLastName.getText().isEmpty() ||
                    txtContact.getText().isEmpty() ||
                    txtAddress.getText().isEmpty() ||
                    txtUserID.getText().isEmpty() ||
                    txtUsername.getText().isEmpty() ||
                    cmbPosition.getSelectedItem() == null ||
                    txtPassword.getText().isEmpty() ||
                    txtConfirmPassword.getText().isEmpty() ){
                JOptionPane.showMessageDialog(new UserInformationForm(),"There's an Empty Field. Please fill out");
            }else if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
                String firstName = txtFirstName.getText();
                String middleName = txtMiddleName.getText();
                String lastName = txtLastName.getText();
                String contact = txtContact.getText();
                String address = txtAddress.getText();
                String userID = txtUserID.getText();

                String position = Objects.requireNonNull(cmbPosition.getSelectedItem()).toString();
                String pass = txtPassword.getText();

                String updateQuery = "update users_list set password ='"+pass+"',user_position ='"+position+"',"+
                        "updated_at = CURRENT_TIMESTAMP where user_id ='"+userID+"'";
                String updateUserInfo =
                        "update user_info set user_fname ='"+firstName+"',user_middle = '"+middleName+"',user_lname =" +
                                " '"+lastName+"', user_contact = '"+contact+"',user_address = '"+address+"'," +
                                "user_image = ? where user_id = '"+userID+"' ";
                try{
                    Connection connection = ConnectDatabase.connectDB();
                    int confirm = JOptionPane.showConfirmDialog(new UserInformationForm(),"Confirm Update?","Confirm",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirm == 0){
                        assert connection != null;
                        preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.execute();

                        preparedStatement = connection.prepareStatement(updateUserInfo);
                        preparedStatement.setBytes(1,person_image);
                        preparedStatement.execute();

                        String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`Transact/Product_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','UPDATE USER','"+txtUserID.getText()+"')";
                        preparedStatement = connection.prepareStatement(auditQuery);
                        preparedStatement.execute();


                        JOptionPane.showMessageDialog(new UserInformationForm(),"User Updated Successfully");
                        AllUsers.selectedUser = null;

                    }else if(confirm == 1){
                        JOptionPane.showMessageDialog(new UserInformationForm(),"Not Updated","User not Updated",
                                JOptionPane.WARNING_MESSAGE);

                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
                finally {
                    try {
                        preparedStatement.close();
                    }
                    catch (Exception ignored){

                    }
                }
            }else{
                JOptionPane.showMessageDialog(new UserInformationForm(),"Password not match","Invalid",JOptionPane.ERROR_MESSAGE);
            }
        }

    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        final boolean b = Character.isLetter(c) || Character.isISOControl(c);
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

        if(e.getSource() == txtPassword){
            txtPassword.setEditable(txtPassword.getText().length()<12||d);
        }
        if(e.getSource() == txtConfirmPassword){
            txtConfirmPassword.setEditable(txtConfirmPassword.getText().length()<12||d);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == txtConfirmPassword){
            if(!txtPassword.getText().equals(txtConfirmPassword.getText())){
                txtConfirmPassword.setBorder(new LineBorder(Color.RED,3,false));
                lblConfirmPasswordWarning.setText("Password does not match");
            }
            else if(txtPassword.getText().equals(txtConfirmPassword.getText())){
                txtConfirmPassword.setBorder(new LineBorder(Color.GREEN,3,false));
                lblConfirmPasswordWarning.setText("Password matched");
            }
        }

    }

    private void spacingPanel(int x,int y,JPanel panel1){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(x,y));
        panel.setBackground(this.getBackground());
        panel1.add(panel);
    }
}
