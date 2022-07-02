package com.thesis;


import com.userInfo.AllUsers;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckPosition {
    public void loginAsDialog(String position,JFrame jFrame,JFrame jFrame1){
        JDialog loginDialog = new JDialog();
        loginDialog.setTitle("Login as");
        loginDialog.setSize(400,400);
        loginDialog.setLayout(new BorderLayout());
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setModal(true);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400,400));
        panel.setBackground(new Color(202,170,86));
        loginDialog.add(panel,BorderLayout.CENTER);

        Font font = new Font("Poppins SemiBold",Font.PLAIN,15);

        JLabel lblTitle = new JLabel();
        lblTitle.setText("Login as");
        lblTitle.setFont(new Font("Poppins SemiBold",Font.PLAIN,30));
        lblTitle.setBounds(50,10,250,50);
        panel.add(lblTitle);

        JLabel lblUserName = new JLabel();
        lblUserName.setText("Username");
        lblUserName.setFont(font);
        lblUserName.setBounds(50,180,230,30);
        panel.add(lblUserName);

        JTextField txtUserName = new JTextField();
        txtUserName.setFont(font);
        txtUserName.setBorder(new LineBorder(Color.BLACK,1,true));
        txtUserName.setBounds(50,210,230,30);

        panel.add(txtUserName);

        JLabel lblPassword = new JLabel();
        lblPassword.setText("Password");
        lblPassword.setFont(font);
        lblPassword.setBounds(50,240,230,30);
        panel.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(font);
        txtPassword.setBorder(new LineBorder(Color.BLACK,1,true));
        txtPassword.setBounds(50,270,230,30);


        txtUserName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    checkUser(txtUserName,txtPassword,loginDialog,position,jFrame,jFrame1);
                }
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    checkUser(txtUserName,txtPassword,loginDialog,position,jFrame,jFrame1);
                }
            }
        });
        panel.add(txtPassword);

        loginDialog.setVisible(true);


    }
    private void checkUser(JTextField txtUser,JPasswordField txtPassword,JDialog jDialog,String otherPosition,JFrame frame,JFrame frame2){
        Connection conn = ConnectDatabase.connectDB();

        String user = txtUser.getText();
        String password = txtPassword.getText();
        String position;

        if (user.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please insert ID and Password", "Empty Username and Password",                  JOptionPane.ERROR_MESSAGE);
        } else if (user.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please insert ID", "Empty Username", JOptionPane.ERROR_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please insert Password", "Empty Password", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String query =
                    "Select * from users_list inner join user_info on users_list.user_id = user_info.user_id where " +
                            "users_list.username = '" + user + "' and " +
                            "users_list.password =" +
                            " '" + password +
                            "'";
            try {
                assert conn != null;
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    position = resultSet.getString("users_list.user_position");
                    if(!position.equalsIgnoreCase("Admin") && (!position.equalsIgnoreCase(otherPosition))){
                        JOptionPane.showMessageDialog(null,"The account you enter is not authorized for this form,Please try another Account","Not Authorized",JOptionPane.WARNING_MESSAGE);

                    }
                    else{

                        String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`TRANSACT/PRODUCT_ID`) VALUES ('"+ AllUsers.userID+"','"+AllUsers.position+"','LOGGED OUT','"+AllUsers.userID+"')";
                        preparedStatement = conn.prepareStatement(auditQuery);
                        preparedStatement.execute();

                        AllUsers.firstName = resultSet.getString("user_info.user_fname");
                        AllUsers.middleName= resultSet.getString("user_info.user_middle");
                        AllUsers.LastName = resultSet.getString("user_info.user_lname");
                        AllUsers.position = resultSet.getString("users_list.user_position");
                        AllUsers.userID = resultSet.getString("users_list.user_ID");

                        auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`TRANSACT/PRODUCT_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','LOGGED IN','"+AllUsers.userID+"')";

                        preparedStatement = conn.prepareStatement(auditQuery);
                        preparedStatement.execute();

                        JOptionPane.showMessageDialog(null,"Successfully Logged In as "+AllUsers.position);
                        jDialog.setVisible(false);
                        frame.dispose();
                        frame2.setVisible(true);
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Incorrect ID or Password", "Incorrect", JOptionPane                             .ERROR_MESSAGE);
                    txtUser.setText("");
                    txtPassword.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Incorrect ID or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
