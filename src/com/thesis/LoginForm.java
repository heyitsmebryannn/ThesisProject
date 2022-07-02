package com.thesis;


import com.POS.POSForm;
import com.userInfo.AllUsers;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame implements ActionListener, KeyListener {
    JLabel lblBackGround;
    JTextField txtUser;
    JPasswordField txtPassword;
    JLabel lblUser,lblPassword;
    JButton btnLogin, btnCancel;
    Font font = new Font("Arial",Font.BOLD,20);
    Connection conn = ConnectDatabase.connectDB();
    ImageIcon background = new ImageIcon("images/bg-1.jpg");
    int attempt = 5;
    JPanel panelNav;
    JButton btnExit,btnMinimize;
    int dX, dY;

    public LoginForm(){
        setTitle("Ephraim POS System");
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setIconImage(AllUsers.image.getImage());
        setSize(760,600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.GRAY);
        setResizable(false);
        setUndecorated(true);


        panelNav = new JPanel();
        panelNav.setOpaque(true);
        panelNav.setLayout(null);
        panelNav.setBackground(new Color(202,170,86));
        panelNav.setBounds(0,0,760,30);
        panelNav.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dX  = e.getX();
                dY = e.getY();
            }
        });

       panelNav.addMouseMotionListener(new MouseAdapter() {
           @Override
           public void mouseDragged(MouseEvent e) {
                mouseDraggedPanel(e);
           }
       });
        add(panelNav);



        btnMinimize = new JButton();
        btnMinimize.setText("_");
        btnMinimize.setFocusable(false);
        btnMinimize.setFont(new Font("Poppins ExtraBold",Font.BOLD,15));
        btnMinimize.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btnMinimize.setBackground(new Color(242,242,242));
        btnMinimize.setBounds(640,5,50,20);
        btnMinimize.addActionListener(e -> this.setState(JFrame.ICONIFIED));
        panelNav.add(btnMinimize);

        btnExit = new JButton();
        btnExit.setText("x");
        btnExit.setFocusable(false);
        btnExit.setFont(new Font("Poppins ExtraBold",Font.BOLD,15));
        btnExit.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btnExit.setBackground(new Color(242,242,242));
        btnExit.setBounds(700,5,50,20);
        btnExit.addActionListener(e -> cancel());
        panelNav.add(btnExit);

        lblBackGround = new JLabel();
        lblBackGround.setIcon(background);
        lblBackGround.setOpaque(true);

        lblUser = new JLabel("Username");
        lblUser.setFont(font);
        lblUser.setForeground(Color.WHITE);
        lblUser.setBounds(420,330,100,20);

        txtUser = new JTextField(30);
        txtUser.setFont(font);
        txtUser.setHorizontalAlignment(SwingConstants.LEADING);
        txtUser.setBorder(new LineBorder(Color.BLACK,1,true));
        txtUser.setBounds(420,350,230,30);
        txtUser.addKeyListener(this);


        lblPassword = new JLabel("Password");
        lblPassword.setFont(font);
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(420,400,100,20);


        txtPassword = new JPasswordField(20);
        txtPassword.setFont(font);
        txtPassword.setHorizontalAlignment(SwingConstants.LEADING);
        txtPassword.setBorder(new LineBorder(Color.BLACK,1,true));
        txtPassword.setBounds(420,420,230,30);
        txtPassword.addKeyListener(this);
        btnLogin = new JButton("Login");
        btnLogin.setFont(font);
        btnLogin.setHorizontalAlignment(JButton.CENTER);
        btnLogin.setBorder(new LineBorder(Color.BLACK));
        btnLogin.setFocusable(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btnLogin.setBounds(420,470,100,30);
        btnLogin.addActionListener(this);


        btnCancel = new JButton("Exit");
        btnCancel.setFont(font);
        btnCancel.setHorizontalAlignment(JButton.CENTER);
        btnCancel.setBorder(new LineBorder(Color.BLACK,1,true));
        btnCancel.setFocusable(false);
        btnCancel.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btnCancel.setBounds(550,470,100,30);
        btnCancel.addActionListener(this);

        add(lblUser);
        add(txtUser);
        add(lblPassword);
        add(txtPassword);
        add(btnLogin);
        add(btnCancel);
        add(lblBackGround);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == btnLogin){
            login();
        }
        if(source == btnCancel){
            cancel();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == txtUser || e.getSource() == txtPassword) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                login();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }


    private void login(){
        String user = txtUser.getText();
        String password = txtPassword.getText();

                if (user.isEmpty() && password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please insert ID and Password", "Empty Username and Password",                  JOptionPane.ERROR_MESSAGE);
                } else if (user.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please insert ID", "Empty Username", JOptionPane.ERROR_MESSAGE);
                } else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please insert Password", "Empty Password", JOptionPane.ERROR_MESSAGE);
                } else {
                    if(!(attempt<=1)) {
                        String query =
                                "Select * from users_list inner join user_info on users_list.user_id = user_info.user_id where " +
                                        "users_list.username = '" + user + "' and " +
                                        "users_list.password =" +
                                        " '" + password +
                                        "'";
                        try {
                            PreparedStatement preparedStatement = conn.prepareStatement(query);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            if (resultSet.next()) {
                                AllUsers.firstName = resultSet.getString("user_info.user_fname");
                                AllUsers.middleName = resultSet.getString("user_info.user_middle");
                                AllUsers.LastName = resultSet.getString("user_info.user_lname");
                                AllUsers.position = resultSet.getString("users_list.user_position");
                                AllUsers.userID = resultSet.getString("users_list.user_ID");

                                String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`Transact/Product_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','LOGGED IN','" + AllUsers.userID + "')";
                                assert conn != null;
                                preparedStatement = conn.prepareStatement(auditQuery);
                                preparedStatement.execute();

                                JOptionPane.showMessageDialog(this, "Successfully Logged In as " + AllUsers.position);
                                this.dispose();
                                if (AllUsers.position.equalsIgnoreCase("Admin") || AllUsers.position.equalsIgnoreCase("For Reports")) {
                                    new MainMenuForm().setVisible(true);
                                } else if (AllUsers.position.equalsIgnoreCase("For POS/Inventory")) {
                                    new POSForm().setVisible(true);
                                }
                            } else {
                                attempt = attempt - 1;
                                JOptionPane.showMessageDialog(this, "Incorrect ID or Password, You have " + attempt + " attempt(s) left. ", "Incorrect", JOptionPane.ERROR_MESSAGE);
                                txtUser.setText("");
                                txtPassword.setText("");
                                txtUser.grabFocus();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Incorrect ID or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                            Runtime runtime = Runtime.getRuntime();
                            JOptionPane.showMessageDialog(null,"The PC or Laptop will shutdown in 10 seconds due to 5 Wrong Attempts","System Exit",JOptionPane.WARNING_MESSAGE);
                        try {
                            runtime.exec("shutdown -s -t 10");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            }
    }

    private void mouseDraggedPanel(java.awt.event.MouseEvent e){
        int  x = e.getXOnScreen();
        int  y = e.getYOnScreen();

        this.setLocation(x-dX,y-dY);
    }
    private void cancel(){
        int a = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit?","Exit",JOptionPane.YES_NO_OPTION,                JOptionPane.WARNING_MESSAGE);
        if(a == 0){
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        new LoginForm();
        new LoginForm().setVisible(true);
        new LoginForm().pack();
    }

}
