package com.thesis;

import com.POS.POSForm;
import com.Sales.SalesReportPanel;
import com.productInfo.InventoryForm;
import com.report.AuditTrailPanel;
import com.report.TransactionReportPanel;
import com.userInfo.AllUsers;
import com.userInfo.UserInformationForm;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainMenuForm extends JFrame implements MouseListener, MouseMotionListener, ActionListener {

    JLabel lblBanner;
    JPanel currentPanel;
    JPanel centerPanel, northPanel, westPanel;
    JButton btnUserInfo, btnPOS, btnInventory, btnTransaction, btnActivity, btnSalesReport, btnLogout, btnDashboard;
    JButton btnToggle;
    Font font = new Font("Poppins SemiBold", Font.PLAIN, 15);
    ImageIcon userLogo = new ImageIcon("images/user-logo.png");
    ImageIcon POSLogo = new ImageIcon("images/pos-logo.jpg");
    ImageIcon InventoryLogo = new ImageIcon("images/inventory-logo.png");
    ImageIcon saleReportLogo = new ImageIcon("images/sales_icon.png");
    ImageIcon transactionLogo = new ImageIcon("images/transaction-logo.png");
    ImageIcon activityLogo = new ImageIcon("images/audit-logo.png");
    ImageIcon dashboard = new ImageIcon("images/dashboard.png");
    //    ImageIcon home = new ImageIcon("images/home.png");
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    ImageIcon logout = new ImageIcon("images/log-out.png");
    JPanel northWestPanel;

    Connection connection = ConnectDatabase.connectDB();
    PreparedStatement preparedStatement;

    public MainMenuForm() {
        super("Main Menu");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(AllUsers.image.getImage());
        setSize(1366, 768);
        setMinimumSize(new Dimension(1100, 600));
        setMaximumSize(new Dimension(1366, 768));
        setLocationRelativeTo(new LoginForm());
        setLayout(new BorderLayout());
        setUndecorated(true);
        getContentPane().setBackground(new Color(202, 170, 86));
        showUIComponents();
    }

    private void showUIComponents() {

        northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(dimension.width, 130));
        northPanel.setBackground(this.getContentPane().getBackground());
        northPanel.setLayout(new BorderLayout());
        northPanel.setBorder(new LineBorder(Color.BLACK, 5, false));

        String logo = new File("images/banner-1.jpg").getAbsolutePath();
        lblBanner = new JLabel();
        lblBanner.setSize((int) dimension.getWidth(), 150);
        ImageIcon newImage = new ImageIcon(new ImageIcon(logo).getImage().getScaledInstance(lblBanner.getWidth(), lblBanner.getHeight(), Image.SCALE_DEFAULT));
        lblBanner.setIcon(newImage);
        lblBanner.setOpaque(true);
        northPanel.add(lblBanner, BorderLayout.CENTER);

        northWestPanel = new JPanel();
        northWestPanel.setPreferredSize(new Dimension(100, northPanel.getPreferredSize().height));
        northWestPanel.setBackground(this.getContentPane().getBackground());
        northWestPanel.setLayout(new GridBagLayout());
        northPanel.add(northWestPanel, BorderLayout.WEST);


        btnToggle = new JButton();
        ImageIcon navIcon = new ImageIcon(new ImageIcon("images/navIcon.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        btnToggle.setIcon(navIcon);
        btnToggle.setPreferredSize(new Dimension(50, 50));
        btnToggle.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        btnToggle.setHorizontalAlignment(SwingConstants.CENTER);
        btnToggle.setHorizontalTextPosition(SwingConstants.CENTER);
        btnToggle.setFont(font);
        btnToggle.setIconTextGap(40);
        btnToggle.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        btnToggle.addActionListener(this);
        btnToggle.setFocusable(false);
        northWestPanel.add(btnToggle);


        westPanel = new JPanel();
        westPanel.setLayout(new GridLayout(8, 1));
        westPanel.setPreferredSize(new Dimension(250, dimension.height - northPanel.getPreferredSize().height));
        westPanel.setBackground(northPanel.getBackground());
        westPanel.setBorder(new LineBorder(Color.BLACK, 5, false));

        btnDashboard = new JButton();
        button(btnDashboard, dashboard, "Dashboard");
        btnUserInfo = new JButton();
        button(btnUserInfo, userLogo, "<html>User<br>Information</html>");
        btnPOS = new JButton();
        button(btnPOS, POSLogo, "<html>PoS<br>System</html>");
        btnInventory = new JButton();
        button(btnInventory, InventoryLogo, "<html>Inventory</html>");
        btnSalesReport = new JButton();
        button(btnSalesReport, saleReportLogo, "<html>Sales<br>Report</html>");
        btnTransaction = new JButton();
        button(btnTransaction, transactionLogo, "<html>Transaction<br>Report</html>");
        btnActivity = new JButton();
        button(btnActivity, activityLogo, "<html>Activity<br>Log</html>");
        btnLogout = new JButton();
        button(btnLogout, logout, "Log out");

        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(dimension.width - westPanel.getPreferredSize().width, dimension.height - northPanel.getPreferredSize().height));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(new LineBorder(Color.BLACK, 5, false));
        centerPanel.setBackground(this.getContentPane().getBackground());

//        btnHome.addActionListener(this);
        btnDashboard.addActionListener(this);
        btnUserInfo.addActionListener(this);
        btnPOS.addActionListener(this);
        btnSalesReport.addActionListener(this);
        btnTransaction.addActionListener(this);
        btnActivity.addActionListener(this);
        btnInventory.addActionListener(this);
        btnLogout.addActionListener(this);

        add(westPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        if (!AllUsers.position.equalsIgnoreCase("For Reports")) {
            openPanel(new DashBoardPanel());
            btnActive(btnDashboard);
        } else {
            openPanel(new SalesReportPanel());
            btnActive(btnSalesReport);
        }
    }

    public void openPanel(JPanel panel) {


        if (currentPanel != null) {
            currentPanel.hide();
        }
        currentPanel = panel;
        this.add(panel, BorderLayout.CENTER);
        panel.show();
    }

    public void btnActive(JButton btn) {
        btn.setBackground(new Color(8, 84, 155));
        btn.setForeground(new Color(215, 215, 37));
    }

    public void btnInactive(JButton btn1, JButton btn2, JButton btn3, JButton btn4) {
        btn1.setBackground(westPanel.getBackground());
        btn1.setForeground(Color.BLACK);
        btn2.setBackground(westPanel.getBackground());
        btn2.setForeground(Color.BLACK);
        btn3.setBackground(westPanel.getBackground());
        btn3.setForeground(Color.BLACK);
        btn4.setBackground(westPanel.getBackground());
        btn4.setForeground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUserInfo) {
            this.dispose();
            new UserInformationForm().show();
        }
        if (e.getSource() == btnInventory) {
            String position = AllUsers.position;
            if ((!position.equalsIgnoreCase("Admin")) && (!position.equalsIgnoreCase("For POS/Inventory"))) {
                int result = JOptionPane.showConfirmDialog(null, "You are not authorized to access this form. Do you want to login into another account?", "Login to another account", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    CheckPosition checkPosition = new CheckPosition();
                    checkPosition.loginAsDialog("For POS/Inventory", this, new InventoryForm());
                }
            } else {
                this.dispose();
                new InventoryForm().setVisible(true);
            }
        }
        if (e.getSource() == btnLogout) {
            connection = ConnectDatabase.connectDB();
            int logOut = JOptionPane.showConfirmDialog(this, "Are you sure you want to Log out now?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (logOut == 0) {
                try {
                    String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`Transact/Product_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','LOGGED OUT','" + AllUsers.userID + "')";
                    assert connection != null;
                    preparedStatement = connection.prepareStatement(auditQuery);
                    preparedStatement.execute();

                    this.dispose();
                    new LoginForm().setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        preparedStatement.close();
                        connection.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }
        if (e.getSource() == btnPOS) {
            String position = AllUsers.position;
            if ((!position.equalsIgnoreCase("Admin")) && (!position.equalsIgnoreCase("For POS/Inventory"))) {
                int result = JOptionPane.showConfirmDialog(null, "You are not authorized to access this form. Do you want to login into another account?", "Login to another account", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    CheckPosition checkPosition = new CheckPosition();
                    checkPosition.loginAsDialog("For POS/Inventory", this, new POSForm());
                }
            } else {
                this.dispose();
                new POSForm().setVisible(true);
            }
        }
        if (e.getSource() == btnTransaction) {
            if (!AllUsers.position.equalsIgnoreCase("Admin") && (!AllUsers.position.equalsIgnoreCase("For Reports"))) {
                int result = JOptionPane.showConfirmDialog(null, "You are not authorized to access this form. Do you want to login into another account?", "Login to another account", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    loginAsDialog();
                }
            } else {
                openTransaction();
            }
        }
        if (e.getSource() == btnSalesReport) {
            if (!AllUsers.position.equalsIgnoreCase("Admin") && (!AllUsers.position.equalsIgnoreCase("For Reports"))) {
                int result = JOptionPane.showConfirmDialog(null, "You are not authorized to access this form. Do you want to login into another account?", "Login to another account", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    loginAsReport();
                }
            } else {
                openSaleReport();
            }
        }
        if (e.getSource() == btnActivity) {
            AuditTrailPanel auditTrailPanel = new AuditTrailPanel();
            openPanel(auditTrailPanel);
            btnActive(btnActivity);
            btnInactive(btnInventory, btnDashboard, btnTransaction, btnSalesReport);
            revalidate();
        }
        if (e.getSource() == btnDashboard) {
            DashBoardPanel dashBoardPanel = new DashBoardPanel();
            openPanel(dashBoardPanel);
            btnActive(btnDashboard);
            btnInactive(btnInventory, btnActivity, btnTransaction, btnSalesReport);
            revalidate();
        }

        if (e.getSource() == btnToggle) {
            if (westPanel.getPreferredSize().width == 250) {
                westPanel.setPreferredSize(new Dimension(100, dimension.height - northPanel.getPreferredSize().height));
                toolTip(btnDashboard, "Dashboard");
                toolTip(btnUserInfo, "<html>User<br>Information</html>");
                toolTip(btnPOS, "<html>PoS<br>System</html>");
                toolTip(btnInventory, "<html>Inventory</html>");
                toolTip(btnSalesReport, "<html>Sales<br>Report</html>");
                toolTip(btnTransaction, "<html>Transaction<br>Report</html>");
                toolTip(btnActivity, "<html>Activity<br>Log</html>");
            } else {
                westPanel.setPreferredSize(new Dimension(250, dimension.height - northPanel.getPreferredSize().height));
                haveText(btnDashboard, "Dashboard");
                haveText(btnUserInfo, "<html>User<br>Information</html>");
                haveText(btnPOS, "<html>PoS<br>System</html>");
                haveText(btnInventory, "<html>Inventory</html>");
                haveText(btnSalesReport, "<html>Sales<br>Report</html>");
                haveText(btnTransaction, "<html>Transaction<br>Report</html>");
                haveText(btnActivity, "<html>Activity<br>Log</html>");
            }
            revalidate();
        }
    }

    private void button(JButton btn, ImageIcon icon, String text) {
        btn.setText(text);
        btn.setFont(font);
        btn.setIcon(icon);
        btn.setIconTextGap(20);
        btn.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        btn.setPreferredSize(new Dimension(300, 50));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn.setForeground(Color.BLACK);
        btn.setFocusable(false);
        btn.setBackground(new Color(202, 170, 86));
        btn.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        westPanel.add(btn);
        btn.addMouseListener(this);
    }

    private void toolTip(JButton btn, String toolTipText) {
        btn.setText("");
        btn.setToolTipText(toolTipText);
    }

    private void haveText(JButton btn, String text) {
        btn.setText(text);
        btn.setToolTipText(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private void loginAsDialog() {
        JDialog loginDialog = new JDialog();
        loginDialog.setTitle("Login as");
        loginDialog.setSize(400, 400);
        loginDialog.setLayout(new BorderLayout());
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setModal(true);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(new Color(202, 170, 86));
        loginDialog.add(panel, BorderLayout.CENTER);


        Font font = new Font("Poppins SemiBold", Font.PLAIN, 15);

        JLabel lblTitle = new JLabel();
        lblTitle.setText("Login as");
        lblTitle.setFont(new Font("Poppins SemiBold", Font.PLAIN, 30));
        lblTitle.setBounds(50, 10, 250, 50);
        panel.add(lblTitle);

        JLabel lblUserName = new JLabel();
        lblUserName.setText("Username");
        lblUserName.setFont(font);
        lblUserName.setBounds(50, 180, 230, 30);
        panel.add(lblUserName);

        JTextField txtUserName = new JTextField();
        txtUserName.setFont(font);
        txtUserName.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtUserName.setBounds(50, 210, 230, 30);

        panel.add(txtUserName);

        JLabel lblPassword = new JLabel();
        lblPassword.setText("Password");
        lblPassword.setFont(font);
        lblPassword.setBounds(50, 240, 230, 30);
        panel.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(font);
        txtPassword.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtPassword.setBounds(50, 270, 230, 30);


        txtUserName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkUser(txtUserName, txtPassword, loginDialog);
                }
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkUser(txtUserName, txtPassword, loginDialog);
                }
            }
        });
        panel.add(txtPassword);
        loginDialog.setVisible(true);


    }

    public void checkUser(JTextField txtUser, JPasswordField txtPassword, JDialog jDialog) {
        Connection conn = ConnectDatabase.connectDB();

        String user = txtUser.getText();
        String password = txtPassword.getText();
        String position;

        if (user.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please insert ID and Password", "Empty Username and Password", JOptionPane.ERROR_MESSAGE);
        } else if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please insert ID", "Empty Username", JOptionPane.ERROR_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please insert Password", "Empty Password", JOptionPane.ERROR_MESSAGE);
        } else {
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
                    if (!position.equalsIgnoreCase("Admin") && (!position.equalsIgnoreCase("For Reports"))) {
                        JOptionPane.showMessageDialog(null, "The account you enter is not authorized for this form,Please try another Account", "Not Authorized", JOptionPane.WARNING_MESSAGE);
                    } else {

                        String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`TRANSACT/PRODUCT_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','LOGGED OUT','" + AllUsers.userID + "')";
                        preparedStatement = conn.prepareStatement(auditQuery);
                        preparedStatement.execute();

                        AllUsers.firstName = resultSet.getString("user_info.user_fname");
                        AllUsers.middleName = resultSet.getString("user_info.user_middle");
                        AllUsers.LastName = resultSet.getString("user_info.user_lname");
                        AllUsers.position = resultSet.getString("users_list.user_position");
                        AllUsers.userID = resultSet.getString("users_list.user_ID");

                        auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`TRANSACT/PRODUCT_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','LOGGED IN','" + AllUsers.userID + "')";

                        preparedStatement = conn.prepareStatement(auditQuery);
                        preparedStatement.execute();

                        JOptionPane.showMessageDialog(this, "Successfully Logged In as " + AllUsers.position);
                        jDialog.setVisible(false);
                        openTransaction();

                    }

                } else {

                    JOptionPane.showMessageDialog(this, "Incorrect ID or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
                    txtUser.setText("");
                    txtPassword.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Incorrect ID or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void openTransaction() {
        TransactionReportPanel transactionReportPanel = new TransactionReportPanel();
        openPanel(transactionReportPanel);
        btnActive(btnTransaction);
        btnInactive(btnInventory, btnDashboard, btnActivity, btnSalesReport);
        revalidate();
        connection = ConnectDatabase.connectDB();

    }

    private void openSaleReport() {
        SalesReportPanel salesReportPanel = new SalesReportPanel();
        openPanel(salesReportPanel);
        btnActive(btnSalesReport);
        btnInactive(btnInventory, btnDashboard, btnTransaction, btnActivity);
        revalidate();

    }

    private void loginAsReport() {
        JDialog loginDialog = new JDialog();
        loginDialog.setTitle("Login as");
        loginDialog.setSize(400, 400);
        loginDialog.setLayout(new BorderLayout());
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setModal(true);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(new Color(202, 170, 86));
        loginDialog.add(panel, BorderLayout.CENTER);

        Font font = new Font("Poppins SemiBold", Font.PLAIN, 15);

        JLabel lblTitle = new JLabel();
        lblTitle.setText("Login as");
        lblTitle.setFont(new Font("Poppins SemiBold", Font.PLAIN, 30));
        lblTitle.setBounds(50, 10, 250, 50);
        panel.add(lblTitle);

        JLabel lblUserName = new JLabel();
        lblUserName.setText("Username");
        lblUserName.setFont(font);
        lblUserName.setBounds(50, 180, 230, 30);
        panel.add(lblUserName);

        JTextField txtUserName = new JTextField();
        txtUserName.setFont(font);
        txtUserName.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtUserName.setBounds(50, 210, 230, 30);

        panel.add(txtUserName);

        JLabel lblPassword = new JLabel();
        lblPassword.setText("Password");
        lblPassword.setFont(font);
        lblPassword.setBounds(50, 240, 230, 30);
        panel.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(font);
        txtPassword.setBorder(new LineBorder(Color.BLACK, 1, true));
        txtPassword.setBounds(50, 270, 230, 30);

        txtUserName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkSale(txtUserName, txtPassword, loginDialog);
                }
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkSale(txtUserName, txtPassword, loginDialog);
                }
            }
        });
        panel.add(txtPassword);

        loginDialog.setVisible(true);
    }

    public void checkSale(JTextField txtUser, JPasswordField txtPassword, JDialog jDialog) {
        Connection conn = ConnectDatabase.connectDB();
        String user = txtUser.getText();
        String password = txtPassword.getText();
        String position;
        if (user.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please insert ID and Password", "Empty Username and Password", JOptionPane.ERROR_MESSAGE);
        } else if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please insert ID", "Empty Username", JOptionPane.ERROR_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please insert Password", "Empty Password", JOptionPane.ERROR_MESSAGE);
        } else {
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
                    if (!position.equalsIgnoreCase("Admin") && (!position.equalsIgnoreCase("For Reports"))) {
                        JOptionPane.showMessageDialog(null, "The account you enter is not authorized for this form,Please try another Account", "Not Authorized", JOptionPane.WARNING_MESSAGE);
                    } else {

                        String auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`TRANSACT/PRODUCT_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','LOGGED OUT','" + AllUsers.userID + "')";
                        preparedStatement = conn.prepareStatement(auditQuery);
                        preparedStatement.execute();

                        AllUsers.firstName = resultSet.getString("user_info.user_fname");
                        AllUsers.middleName = resultSet.getString("user_info.user_middle");
                        AllUsers.LastName = resultSet.getString("user_info.user_lname");
                        AllUsers.position = resultSet.getString("users_list.user_position");
                        AllUsers.userID = resultSet.getString("users_list.user_ID");

                        auditQuery = "INSERT INTO `audit_trail`(`user_ID`,`user_position`, `Action`,`TRANSACT/PRODUCT_ID`) VALUES ('" + AllUsers.userID + "','" + AllUsers.position + "','LOGGED IN','" + AllUsers.userID + "')";

                        preparedStatement = conn.prepareStatement(auditQuery);
                        preparedStatement.execute();

                        JOptionPane.showMessageDialog(this, "Successfully Logged In as " + AllUsers.position);
                        jDialog.setVisible(false);
                        openSaleReport();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect ID or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
                    txtUser.setText("");
                    txtPassword.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Incorrect ID or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
