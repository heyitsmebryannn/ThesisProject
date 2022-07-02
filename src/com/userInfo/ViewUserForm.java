package com.userInfo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;



public class ViewUserForm extends JPanel {
    JPanel centerPanel,eastPanel,southPanel,westPanel,southCenterPanel,southNorthPanel,southEastPanel,southWestPanel;
    JLabel lblTitle1,lblTitle2,
            lblFirstName,lblLastName,lblMiddleName,
            lblAddress,lblContact,lblPosition,lblImage,
            lblUserID,lblUsername,lblPassword,lblConfirmPassword,lblEmail;
    JTextField txtFirstName,txtLastName,txtMiddleName,
            txtAddress,txtContact,
            txtUserID,txtUsername,txtEmail;
    JPasswordField txtPassword,txtConfirmPassword;
    JComboBox<String> cmbPosition;

    Font fontTitle = new Font("Poppins SemiBold",Font.BOLD,25);
    Font fontLabel = new Font("Poppins SemiBold",Font.PLAIN,15);
    String filename = new File("images/user-icon.png").getAbsolutePath();
    byte[] person_image = null;
    ImageIcon lblIcon;
    Dimension dimension;

    public ViewUserForm(){
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

        textField(txtUserID = new JTextField(),"Enter a User ID");
        panel2.add(txtUserID);

        textField(txtUsername = new JTextField(), "Enter Username");
        panel2.add(txtUsername);

        Label(lblUserID = new JLabel(),"User ID");
        panel2.add(lblUserID);


        Label(lblUsername = new JLabel(),"Username");
        panel2.add(lblUsername);


        JPanel panel14 = new JPanel();
        panel14.setOpaque(false);
        panel2.add(panel14);
        JPanel panel15 = new JPanel();
        panel15.setOpaque(false);
        panel2.add(panel15);


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

        JPanel panel21 = new JPanel();
        panel21.setOpaque(false);
        panel2.add(panel21);

        spacingPanel(20,20,panel2);

        textField(txtConfirmPassword = new JPasswordField(),"Confirm the Password");
        panel2.add(txtConfirmPassword);

        spacingPanel(20,20,panel2);

        Label(lblConfirmPassword = new JLabel(),"Confirm Password");
        panel2.add(lblConfirmPassword);


        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        panel2.add(panel3);

        JPanel panel31 = new JPanel();
        panel31.setOpaque(false);
        panel2.add(panel31);


        lblImage = new JLabel();
        lblImage.setOpaque(true);
        lblImage.setBorder(new LineBorder(Color.BLACK,2,false));
        lblImage.setPreferredSize(new Dimension(200,200));
        lblIcon = new ImageIcon(new ImageIcon("images/user-icon.png").getImage().getScaledInstance(lblImage.getPreferredSize().width,lblImage.getPreferredSize().height,Image.SCALE_SMOOTH));
        lblImage.setIcon(lblIcon);
        lblImage.setVerticalAlignment(JLabel.CENTER);
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        eastPanel.add(lblImage);
        spacingPanel(eastPanel.getPreferredSize().width,1,eastPanel);




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
    }

    private void textField(JTextField txt,String text){
        txt.setFont(fontLabel);
        txt.setHorizontalAlignment(JTextField.LEADING);
        txt.setToolTipText(text);
        txt.setBorder(new LineBorder(Color.BLACK,2,false));
        txt.setEditable(false);
    }
    private void Label(JLabel lbl,String text){
        lbl.setText(text);
        lbl.setFont(fontLabel);
        lbl.setHorizontalAlignment(JLabel.CENTER);
    }
    private void spacingPanel(int x,int y,JPanel panel1){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(x,y));
        panel.setBackground(this.getBackground());
        panel1.add(panel);
    }
}
