package com.userInfo;

import com.thesis.MainMenuForm;
import com.thesis.ConnectDatabase;



import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;


public class UserInformationForm extends JFrame implements ActionListener {

     JPanel centerPanel;
     JButton btnBack;
     JButton btnAdd;
     JButton btnUpdate;
     JButton btnDelete;
     JButton btnView;
    JButton btnPrint;
     JTextField txtSearch;
    JButton btnSearch;
    private final JTable tblUsers;
    private final Font font = new Font("Poppins SemiBold",Font.PLAIN,15);
    private final String[][] data ={{null,null,null,null}};
    private final String[] column ={"ID","First Name","Last Name", "Position"};
    private final DefaultTableModel tableModel = new DefaultTableModel(data,column);
    JLabel lblBanner;
    JPanel northPanel;
    JPanel westPanel;
    JPanel centerNorthPanel;
    JPanel centerSouthPanel;
    JPanel currentPanel;

    ImageIcon searchIcon,viewIcon,addIcon,updateIcon,deleteIcon,backIcon,printIcon;


    public UserInformationForm(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(AllUsers.image.getImage());
        setResizable(false);
        setUndecorated(true);
        setLayout(new BorderLayout());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(dimension.width,200));
        northPanel.setBackground(new Color(202,170,86));
        northPanel.setBorder(new LineBorder(Color.BLACK,5,false));

        String logo = new File("images/banner-1.jpg").getAbsolutePath();
        lblBanner = new JLabel();
        lblBanner.setPreferredSize(new Dimension(dimension.width,200));
        ImageIcon newImage = new ImageIcon(new ImageIcon(logo).getImage().getScaledInstance(lblBanner.getPreferredSize().width,lblBanner.getPreferredSize().height, Image.SCALE_DEFAULT));
        lblBanner.setIcon(newImage);
        lblBanner.setOpaque(true);
        northPanel.add(lblBanner,BorderLayout.CENTER);

        westPanel = new JPanel();
        westPanel.setLayout(new GridLayout(7,1));
        westPanel.setBackground(new Color(202,170,86));
        westPanel.setPreferredSize(new Dimension(300,dimension.height));
        westPanel.setBorder(northPanel.getBorder());

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setOpaque(true);
        centerPanel.setBackground(westPanel.getBackground());
        centerPanel.setBorder(new LineBorder(Color.BLACK,5,false));
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setPreferredSize(new Dimension(dimension.width-westPanel.getPreferredSize().width,dimension.height-northPanel.getPreferredSize().height));


        centerNorthPanel = new JPanel();
        centerNorthPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width-50,70));
        centerNorthPanel.setBackground(centerPanel.getBackground());
        centerNorthPanel.setLayout(new FlowLayout());
        centerPanel.add(centerNorthPanel, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("User Information");
        lblTitle.setHorizontalAlignment(JLabel.LEADING);
        lblTitle.setFont(new Font("Poppins SemiBold", Font.BOLD,30));
        lblTitle.setVerticalAlignment(JLabel.TOP);
        lblTitle.setPreferredSize(new Dimension(300,50));
        centerNorthPanel.add(lblTitle);

        JPanel panel0 = new JPanel();
        panel0.setBackground(centerPanel.getBackground());
        panel0.setPreferredSize(new Dimension(700,centerNorthPanel.getPreferredSize().height));
        centerNorthPanel.add(panel0);


        JPanel centerMid = new JPanel();
        centerMid.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width-50,50));
        centerMid.setLayout(new FlowLayout());
        centerMid.setBackground(centerPanel.getBackground());
        centerPanel.add(centerMid,BorderLayout.CENTER);

        txtSearch = new JTextField();
        txtSearch.setBorder(new LineBorder(Color.BLACK,1,false));
        txtSearch.setFont(font);
        txtSearch.setPreferredSize(new Dimension(570,30));
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchUser();
            }
        });
        txtSearch.setEditable(AllUsers.position.equalsIgnoreCase("Admin"));
        centerMid.add(txtSearch);


        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(400,50));
        panel1.setBackground(centerPanel.getBackground());
        centerMid.add(panel1);


        centerSouthPanel = new JPanel();
        centerSouthPanel.setPreferredSize(new Dimension(centerPanel.getPreferredSize().width-50,centerPanel.getPreferredSize().height-180));
        centerSouthPanel.setBackground(centerPanel.getBackground());
        centerSouthPanel.setLayout(new BorderLayout());
        centerPanel.add(centerSouthPanel);


        tblUsers = new JTable(tableModel){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        for (int i = 0; i < tblUsers.getColumnModel().getColumnCount(); i++) {
            tblUsers.getColumnModel().getColumn(i).setResizable(false);
            tblUsers.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblUsers.getTableHeader().setReorderingAllowed(false);
        tblUsers.setFont(font);
        tblUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        showDataInTable();
        getModel();
        tblUsers.setRowHeight(60);
        JScrollPane scrollPane = new JScrollPane(tblUsers);

        centerSouthPanel.add(scrollPane);


        searchIcon = new ImageIcon(new ImageIcon("images/search-user.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnSearch = new JButton("User's List");
        button(btnSearch,searchIcon);


        viewIcon = new ImageIcon(new ImageIcon("images/view-user.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnView = new JButton("View User");
        button(btnView,viewIcon);


        addIcon = new ImageIcon(new ImageIcon("images/add-user.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnAdd = new JButton("Add User");
        button(btnAdd,addIcon);

        updateIcon = new ImageIcon(new ImageIcon("images/edit-user.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnUpdate = new JButton("Update User");
        button(btnUpdate,updateIcon);

        deleteIcon = new ImageIcon(new ImageIcon("images/remove-user.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnDelete = new JButton("Delete User");
        button(btnDelete,deleteIcon);

        printIcon = new ImageIcon(new ImageIcon("images/print.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnPrint= new JButton("Print User");
        button(btnPrint,printIcon);



        backIcon = new ImageIcon(new ImageIcon("images/log-out.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        btnBack = new JButton("Back");
        button(btnBack,backIcon);



        add(northPanel, BorderLayout.NORTH);
        add(westPanel,BorderLayout.WEST);

        openPanel(centerPanel);
        btnActive(btnSearch);
    }

    private void button (JButton btn,ImageIcon icon){
        btn.setVerticalAlignment(JButton.CENTER);
        btn.setHorizontalAlignment(JButton.LEFT);
        btn.setVerticalTextPosition(JButton.CENTER);
        btn.setIcon(icon);
        btn.setIconTextGap(50);
        btn.setFont(font);
        btn.setFocusable(false);
        btn.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btn.setBackground(new Color(202,170,86));
        btn.addActionListener(this);

        btn.setPreferredSize(new Dimension(westPanel.getPreferredSize().width-10,100));
        westPanel.add(btn);
    }


    public ArrayList<UsersInfo> getUserList(){
        ArrayList<UsersInfo> usersList = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String userQuery;
        if(!AllUsers.position.equalsIgnoreCase("Admin")){
            userQuery = "SELECT * FROM user_info inner join users_list on user_info.user_id = users_list.user_id where user_info.user_ID ='"+AllUsers.userID+"'";
        }else{
            userQuery = "SELECT * FROM user_info inner join users_list on user_info.user_id = users_list.user_id";
        }
        Statement statement;
        ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(userQuery);
            UsersInfo usersInfo;
            while(resultSet.next()){
                usersInfo = new UsersInfo(resultSet.getString("user_info.user_ID"),resultSet.getString("user_info.user_fname"),resultSet.getString("user_info.user_lname"),resultSet.getString("users_list.user_position"));
                usersList.add(usersInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return usersList;
    }


    public ArrayList<UsersInfo> getUserList2(){
        ArrayList<UsersInfo> usersList = new ArrayList<>();
        Connection con = ConnectDatabase.connectDB();
        String userQuery = "SELECT * FROM user_info inner join users_list on user_info.user_id = users_list.user_id where users_list.user_id = '"+ AllUsers.selectedUser+"'";
        Statement statement;
        ResultSet resultSet;

        try {
            assert con != null;
            statement = con.createStatement();
            resultSet = statement.executeQuery(userQuery);
            UsersInfo usersInfo;
            while(resultSet.next()){
                usersInfo = new UsersInfo(resultSet.getString("user_info.user_ID"),resultSet.getString("user_info." +
                        "user_fname"),resultSet.getString("user_info.user_middle"),
                        resultSet.getString("user_info.user_lname"),resultSet.getString(
                        "user_info.user_contact"),resultSet.getString("user_info.user_address"),
                        resultSet.getString("users_list.username"),resultSet.getString("users_list.password"),
                        resultSet.getString("users_list.user_position"),
                        resultSet.getBytes("user_info.user_image"));
                usersList.add(usersInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return usersList;
    }



    public void getModel(){
        DefaultTableModel tableModel = (DefaultTableModel) tblUsers.getModel();
        tableModel.setRowCount(0);
        showDataInTable();
    }
    public void showDataInTable(){

        ArrayList<UsersInfo> userLists = getUserList();
        tableModel.setRowCount(0);
        Object[] column = new Object[4];

        for (UsersInfo userList : userLists) {
            column[0] = userList.getUserID();
            column[1] = userList.getFirstName();
            column[2] = userList.getLastName();
            column[3] = userList.getPosition();

            tableModel.addRow(column);
        }


    }
    private void executeSQLQuery(String query,String query2) {
        Connection con = ConnectDatabase.connectDB();
        Statement statement;
        PreparedStatement preparedStatement;

        try {
            int a = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (a == 0) {
                assert con != null;
                statement = con.createStatement();
                if (statement.executeUpdate(query) == 1 && statement.executeUpdate(query2)==1) {
                    JOptionPane.showMessageDialog(null, "User Deleted Successfully");
                    int selectedRow = tblUsers.getSelectedRow();
                    String user_id = tblUsers.getValueAt(selectedRow,0).toString();
                    String auditQuery = "INSERT INTO AUDIT_TRAIL(USER_ID,USER_POSITION,ACTION,`TRANSACT/PRODUCT_ID`) VALUES ('"+AllUsers.userID+"','"+AllUsers.position+"','DELETE USER','"+user_id+"')";
                    preparedStatement = con.prepareStatement(auditQuery);
                    preparedStatement.execute();
                }
            } else if (a == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "User not Deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void searchUser(){
        ArrayList<UsersInfo> usersList = new ArrayList<>();
        String searchQuery = "SELECT * FROM user_info inner join users_list on user_info.user_id = users_list.user_id where " +
                "users_list.user_id like '%' '"+txtSearch.getText()+"' '%' or user_info.user_fname like '%' '"+txtSearch.getText()+"' '%'" +
                " or user_info.user_lname like '%' '"+txtSearch.getText()+"' '%' or user_info.user_middle like '%' '"+txtSearch.getText()+"' '%'" +
                "or users_list.user_position like '%' '"+txtSearch.getText()+"' '%'";
        try{

            Connection con = ConnectDatabase.connectDB();
            assert con != null;
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(searchQuery);
            UsersInfo usersInfo;
            while(resultSet.next()) {
                usersInfo = new UsersInfo(resultSet.getString("user_info.user_ID"), resultSet.getString("user_info.user_fname"), resultSet.getString("user_info.user_lname"), resultSet.getString("users_list.user_position"));
                usersList.add(usersInfo);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        DefaultTableModel tableModel = (DefaultTableModel) tblUsers.getModel();
        tableModel.setRowCount(0);
        Object[] column = new Object[4];
        for (UsersInfo userList : usersList) {
            column[0] = userList.getUserID();
            column[1] = userList.getFirstName();
            column[2] = userList.getLastName();
            column[3] = userList.getPosition();
            tableModel.addRow(column);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnBack){
            this.dispose();
            new MainMenuForm().show();
        }
        if(e.getSource() == btnAdd){
            if(!AllUsers.position.equalsIgnoreCase("Admin")){
                JOptionPane.showMessageDialog(this,"You are not authorized to add an user.Please login as authorized user to access this form");
            }
            else{
                AddUserForm addUserForm = new AddUserForm();
                openPanel(addUserForm);
                btnInactive(btnDelete,btnUpdate,btnView,btnSearch);
                btnActive(btnAdd);
                revalidate();
                repaint();
            }
        }
        if(e.getSource() == btnSearch){
            openPanel(centerPanel);
            btnInactive(btnDelete,btnUpdate,btnView,btnAdd);
            btnActive(btnSearch);
            getModel();
            showDataInTable();
            revalidate();
            repaint();
        }
        if(e.getSource() == btnUpdate) {
            UpdateUserForm updateUserForm = new UpdateUserForm();
            try {
                int i = tblUsers.getSelectedRow();
                TableModel model = tblUsers.getModel();
                AllUsers.selectedUser = model.getValueAt(i, 0).toString();


                ArrayList<UsersInfo> usersInfo = getUserList2();

                for(UsersInfo usersInfo1: usersInfo){
                    updateUserForm.txtFirstName.setText(usersInfo1.getFirstName());
                    updateUserForm.txtMiddleName.setText(usersInfo1.getMiddleName());
                    updateUserForm.txtLastName.setText(usersInfo1.getLastName());
                    updateUserForm.txtAddress.setText(usersInfo1.getAddress());
                    updateUserForm.txtContact.setText("0"+usersInfo1.getContactNumber());
                    updateUserForm.txtUserID.setText(String.valueOf(usersInfo1.getUserID()));
                    updateUserForm.txtUsername.setText(usersInfo1.getUserName());
                    updateUserForm.cmbPosition.setSelectedItem(usersInfo1.getPosition());
                    updateUserForm.txtPassword.setText(usersInfo1.getPassword());
                    updateUserForm.txtConfirmPassword.setText(usersInfo1.getPassword());
                    ImageIcon icon = new ImageIcon(new ImageIcon(usersInfo1.getImage()).getImage().getScaledInstance(updateUserForm.lblImage.getPreferredSize().width,updateUserForm.lblImage.getPreferredSize().height,Image.SCALE_SMOOTH));
                    updateUserForm.lblImage.setIcon(icon);
                    updateUserForm.person_image = usersInfo1.getImage();
                }

                openPanel(updateUserForm);
                btnInactive(btnDelete,btnSearch,btnView,btnAdd);
                btnActive(btnUpdate);
                revalidate();
                repaint();

            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Please select a user on the table to Update", "Select User",
                        JOptionPane.WARNING_MESSAGE);

            }

        }
        if(e.getSource() == btnView){

            try {
                int i = tblUsers.getSelectedRow();
                TableModel model = tblUsers.getModel();
                AllUsers.selectedUser = model.getValueAt(i, 0).toString();
                ViewUserForm viewUserForm = new ViewUserForm();

                ArrayList<UsersInfo> usersInfo = getUserList2();
                for(UsersInfo usersInfo1: usersInfo){
                    viewUserForm.txtFirstName.setText(usersInfo1.getFirstName());
                    viewUserForm.txtMiddleName.setText(usersInfo1.getMiddleName());
                    viewUserForm.txtLastName.setText(usersInfo1.getLastName());
                    viewUserForm.txtAddress.setText(usersInfo1.getAddress());
                    viewUserForm.txtContact.setText(usersInfo1.getContactNumber());
                    viewUserForm.txtUserID.setText(String.valueOf(usersInfo1.getUserID()));
                    viewUserForm.txtUsername.setText(usersInfo1.getUserName());
                    viewUserForm.cmbPosition.setSelectedItem(usersInfo1.getPosition());
                    viewUserForm.txtPassword.setText(usersInfo1.getPassword());
                    viewUserForm.txtConfirmPassword.setText(usersInfo1.getPassword());
                    ImageIcon icon = new ImageIcon(new ImageIcon(usersInfo1.getImage()).getImage().getScaledInstance(viewUserForm.lblImage.getPreferredSize().width,viewUserForm.lblImage.getPreferredSize().height,Image.SCALE_SMOOTH));
                    viewUserForm.lblImage.setIcon(icon);
                }
                openPanel(viewUserForm);
                btnInactive(btnDelete,btnSearch,btnUpdate,btnAdd);
                btnActive(btnView);
                revalidate();
                repaint();


            }catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(this,"Please select a user in the table to view","Select User",JOptionPane.WARNING_MESSAGE);
            }
        }

        if(e.getSource() == btnDelete){


               if(!AllUsers.position.equalsIgnoreCase("Admin")){
                   JOptionPane.showMessageDialog(null,"You are not authorized user to access this form.Please login as authorized user.");
               }
               else{
                   int i = tblUsers.getSelectedRow();
                   TableModel model = tblUsers.getModel();

                   try {

                       String user_id = model.getValueAt(i,0).toString();
                       if(model.getValueAt(i,3).toString().equalsIgnoreCase("Admin")){
                          JOptionPane.showMessageDialog(null,"You cannot delete an Admin Account","Cannot Delete",JOptionPane.ERROR_MESSAGE);
                       }
                       else{
                           String deleteQuery = "DELETE FROM user_info where user_id = '"+user_id+"' ";
                           String deleteQuery2 = "DELETE FROM users_list where user_id = '"+user_id+"' ";


                           executeSQLQuery(deleteQuery,deleteQuery2);
                           getModel();
                           showDataInTable();
                       }


                   }catch (ArrayIndexOutOfBoundsException ex){
                       JOptionPane.showMessageDialog(this,"Please select user in the table to delete","Select User",JOptionPane.WARNING_MESSAGE);
                   }
               }
        }
        if(e.getSource() == btnPrint){
            try{
                int selectedRow = tblUsers.getSelectedRow();
                AllUsers.printID = tblUsers.getValueAt(selectedRow,0).toString();
                AllUsers.printFirstName = tblUsers.getValueAt(selectedRow,1).toString();
                AllUsers.printLastName = tblUsers.getValueAt(selectedRow,2).toString();
                AllUsers.printPosition = tblUsers.getValueAt(selectedRow,3).toString();
                PrintUserID ps = new PrintUserID();
                Object[][] printItem = ps.getTableData(new UserInformationForm().tblUsers);
                PrintUserID.setItems(printItem);
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintable(new PrintUserID.MyPrintable(), PrintUserID.getPageFormat(pj));
                try {
                    pj.print();

                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }

            }catch (ArrayIndexOutOfBoundsException exception){
                JOptionPane.showMessageDialog(null,"Please select user to Print");
            }



        }
    }

    public void openPanel(JPanel panel){

        if(currentPanel != null){
            currentPanel.hide();
        }
        currentPanel = panel;
        this.add(panel,BorderLayout.CENTER);
        panel.show();
    }

    public void btnActive(JButton btn){
        btn.setBackground(new Color (8, 84, 155));
        btn.setForeground(new Color(215, 215, 37));

    }
    public void btnInactive(JButton btn1,JButton btn2,JButton btn3,JButton btn4){

        btn1.setBackground(westPanel.getBackground());
        btn1.setForeground(Color.BLACK);
        btn2.setBackground(westPanel.getBackground());
        btn2.setForeground(Color.BLACK);
        btn3.setBackground(westPanel.getBackground());
        btn3.setForeground(Color.BLACK);
        btn4.setBackground(westPanel.getBackground());
        btn4.setForeground(Color.BLACK);
    }


}


