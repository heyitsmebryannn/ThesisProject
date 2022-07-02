package com.thesis;

import javax.swing.*;
import java.sql.*;
public class ConnectDatabase {
    public static Connection connectDB(){
        Connection conn;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_pos", "root", "");
            return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());

        }
        return null;
    }

}

