package com.thesis;

import com.userInfo.AllUsers;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashBoardPanel extends JPanel {
    JPanel panelRegistered,panelStock,panelChart,panelBestSeller,panelRevenue,panelSales;
    JPanel panelNorth,panelSouth,panelWest,panelEast,panelCenter;
    JLabel lblUser,lblPosition,lblClock,lblDate;

    JTextArea txtNotification;
//    JTable tblProducts;
    JScrollPane scrollPane;
//    String[] tableHead = {"Total Stock","Quantity Type"};
//    DefaultTableModel defaultTableModel = new DefaultTableModel(null,tableHead);
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Date date = new Date();
    Font font = new Font("Poppins SemiBold",Font.PLAIN,15);


    public DashBoardPanel(){
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(202,170,86));
        this.setBorder(new LineBorder(Color.BLACK,5,false));
        this.setPreferredSize(new Dimension(1116,638));


        panelNorth = new JPanel();
        panelNorth.setLayout(new BorderLayout());
        panelNorth.setPreferredSize(new Dimension(this.getPreferredSize().width,100));
        panelNorth.setBackground(this.getBackground());
        add(panelNorth,BorderLayout.NORTH);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2,2,4,4));
        panel1.setBackground(this.getBackground());
        panel1.setPreferredSize(new Dimension(100,panelNorth.getPreferredSize().height));
        panelNorth.add(panel1,BorderLayout.WEST);


        JPanel panel2 = new JPanel();
        panel2.setBackground(this.getBackground());
        panel1.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setBackground(this.getBackground());
        panel1.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(2,2,10,10));
        panel4.setPreferredSize(new Dimension(500,100));
        panel4.setBackground(this.getBackground());
        panelNorth.add(panel4,BorderLayout.CENTER);

        lblUser = new JLabel("Good Day! "+ AllUsers.firstName+ " "+AllUsers.middleName+" "+AllUsers.LastName);
        lblUser.setFont(font);
        lblUser.setHorizontalAlignment(JLabel.LEFT);
        lblUser.setPreferredSize(new Dimension(300,50));
        panel4.add(lblUser);

        lblClock = new JLabel();
        lblClock.setFont(font);
        lblClock.setPreferredSize(new Dimension(150,50));
        panel4.add(lblClock);

        lblPosition = new JLabel("Position: "+AllUsers.position);
        lblPosition.setFont(font);
        lblPosition.setHorizontalAlignment(JLabel.LEFT);
        lblPosition.setPreferredSize(new Dimension(300,50));
        panel4.add(lblPosition);

        Format format = new SimpleDateFormat("MMMM dd, yyyy");
        String s = format.format(date);
        lblDate = new JLabel();
        lblDate.setText(s);
        lblDate.setFont(font);
        lblDate.setVerticalAlignment(JLabel.TOP);
        lblDate.setHorizontalTextPosition(JLabel.LEFT);
        lblDate.setPreferredSize(new Dimension(300,50));
        panel4.add(lblDate);

        JPanel panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(100,100));
        panel5.setLayout(new GridBagLayout());
        panel5.setBackground(this.getBackground());
        panelNorth.add(panel5,BorderLayout.EAST);

        panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension(50,this.getPreferredSize().height));
        panelWest.setBackground(this.getBackground());
        this.add(panelWest,BorderLayout.WEST);

        panelEast = new JPanel();
        panelEast.setPreferredSize(new Dimension(50,this.getPreferredSize().height));
        panelEast.setBackground(this.getBackground());
        this.add(panelEast,BorderLayout.EAST);

        panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(this.getPreferredSize().width,50));
        panelSouth.setBackground(this.getBackground());
        this.add(panelSouth,BorderLayout.SOUTH);

        panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(this.getPreferredSize().width-200,this.getPreferredSize().height-200));
        panelCenter.setLayout(new GridLayout(2,3,30,30));
        panelCenter.setBackground(this.getBackground());
        this.add(panelCenter,BorderLayout.CENTER);

        Font titleFont = font;
        Font txtFont = new Font("Poppins SemiBold",Font.BOLD,40);
        panelRegistered = new JPanel(new BorderLayout());
        panelRegistered.setBorder(new LineBorder(Color.BLACK,1,false));
        panelCenter.add(panelRegistered);

        JLabel lblRegistered = new JLabel();
        lblRegistered.setText("TOTAL PRODUCT REGISTERED");
        lblRegistered.setOpaque(true);
        lblRegistered.setBackground(new Color(102,153,255));
        lblRegistered.setPreferredSize(new Dimension(panelRegistered.getPreferredSize().width,40));
        lblRegistered.setFont(titleFont);
        lblRegistered.setHorizontalTextPosition(SwingConstants.CENTER);
        lblRegistered.setHorizontalAlignment(SwingConstants.CENTER);
        panelRegistered.add(lblRegistered,BorderLayout.NORTH);

        JLabel lblRegisteredCount = new JLabel();
        totalProduct(lblRegisteredCount);
        lblRegisteredCount.setOpaque(true);
        lblRegisteredCount.setBackground(new Color(0,153,204));
        lblRegisteredCount.setPreferredSize(new Dimension(panelRegistered.getPreferredSize().width,panelRegistered.getPreferredSize().height-lblRegistered.getPreferredSize().height));
        lblRegisteredCount.setFont(txtFont);
        lblRegisteredCount.setHorizontalTextPosition(SwingConstants.CENTER);
        lblRegisteredCount.setHorizontalAlignment(SwingConstants.CENTER);
        panelRegistered.add(lblRegisteredCount,BorderLayout.CENTER);

        panelSales = new JPanel(new BorderLayout());
        panelSales.setBorder(new LineBorder(Color.BLACK,1,false));
        panelCenter.add(panelSales);

        JLabel lblSales = new JLabel();
        lblSales.setText("TOTAL SALES TODAY");
        lblSales.setOpaque(true);
        lblSales.setBackground(new Color(239, 175, 133));
        lblSales.setPreferredSize(new Dimension(panelSales.getPreferredSize().width,40));
        lblSales.setFont(titleFont);
        lblSales.setHorizontalTextPosition(SwingConstants.CENTER);
        lblSales.setHorizontalAlignment(SwingConstants.CENTER);
        panelSales.add(lblSales,BorderLayout.NORTH);

        JLabel lblSalesCount = new JLabel();
        totalSalesToday(lblSalesCount);
        lblSalesCount.setOpaque(true);
        lblSalesCount.setBackground(new Color(222, 133, 55));
        lblSalesCount.setPreferredSize(new Dimension(panelSales.getPreferredSize().width,panelSales.getPreferredSize().height-lblRegistered.getPreferredSize().height));
        lblSalesCount.setFont(txtFont);
        lblSalesCount.setHorizontalTextPosition(SwingConstants.CENTER);
        lblSalesCount.setHorizontalAlignment(SwingConstants.CENTER);
        panelSales.add(lblSalesCount,BorderLayout.CENTER);


        panelStock = new JPanel(new BorderLayout());
        panelStock.setBorder(new LineBorder(Color.BLACK,1,false));
        panelCenter.add(panelStock);

        JLabel lblStock = new JLabel();
        lblStock.setText("PRODUCT NOTIFICATION");
        lblStock.setOpaque(true);
        lblStock.setBackground(new Color(204,204,204));
        lblStock.setPreferredSize(new Dimension(panelStock.getPreferredSize().width,40));
        lblStock.setFont(titleFont);
        lblStock.setHorizontalTextPosition(SwingConstants.CENTER);
        lblStock.setHorizontalAlignment(SwingConstants.CENTER);
        panelStock.add(lblStock,BorderLayout.NORTH);

//        tblProducts = new JTable(defaultTableModel) {
//            public boolean editCellAt(int row, int column, java.util.EventObject e) {
//                return false;
//            }
//        };
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        tblProducts.getColumnModel().getColumn(0).setPreferredWidth(100);
//        tblProducts.getColumnModel().getColumn(1).setPreferredWidth(100);
//        tblProducts.setRowHeight(60);
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        for (int i = 0; i < tblProducts.getColumnModel().getColumnCount(); i++) {
//            tblProducts.getColumnModel().getColumn(i).setResizable(false);
//            tblProducts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
//        }
//        tblProducts.getTableHeader().setReorderingAllowed(false);
//        tblProducts.setFont(font);
//        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        txtNotification = new JTextArea();
        txtNotification.setFont(new Font("Arial",Font.BOLD,13));
        txtNotification.append("\tLOW STOCK\n");
        txtNotification.setText(getNotification());
        txtNotification.setEditable(false);
        txtNotification.setLineWrap(true);
        txtNotification.setWrapStyleWord(true);
        txtNotification.setBorder(BorderFactory.createCompoundBorder(txtNotification.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scrollPane = new JScrollPane(txtNotification,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(true);
        scrollPane.setBackground(new Color(150,150,150));
//        getModel();
//        showDataInTable();
        panelStock.add(scrollPane);

        panelRevenue = new JPanel(new BorderLayout());
        panelRevenue.setBorder(new LineBorder(Color.BLACK,1,false));
        panelCenter.add(panelRevenue);

        JLabel lblRevenue = new JLabel();
        lblRevenue.setText("TOTAL DAILY REVENUE ");
        lblRevenue.setOpaque(true);
        lblRevenue.setBackground(new Color(255, 102, 102));
        lblRevenue.setPreferredSize(new Dimension(panelRevenue.getPreferredSize().width,40));
        lblRevenue.setFont(titleFont);
        lblRevenue.setHorizontalTextPosition(SwingConstants.CENTER);
        lblRevenue.setHorizontalAlignment(SwingConstants.CENTER);
        panelRevenue.add(lblRevenue,BorderLayout.NORTH);
        showLineChart();

        JButton btnLineChart = new JButton("View Chart");
        btnLineChart.setFont(font);
        btnLineChart.setPreferredSize(new Dimension(panelRevenue.getPreferredSize().width,30));
        btnLineChart.setFocusable(false);
        btnLineChart.addActionListener(e -> showLineChartForFrame());
        panelRevenue.add(btnLineChart,BorderLayout.SOUTH);


        panelChart = new JPanel(new BorderLayout());
        panelChart.setBorder(new LineBorder(Color.BLACK,1,false));
        panelCenter.add(panelChart);

        JLabel lblChart = new JLabel();
        lblChart.setText("PRODUCT SALES CHART");
        lblChart.setOpaque(true);
        lblChart.setBackground(new Color(153,255,204));
        lblChart.setPreferredSize(new Dimension(panelChart.getPreferredSize().width,40));
        lblChart.setFont(titleFont);
        lblChart.setHorizontalTextPosition(SwingConstants.CENTER);
        lblChart.setHorizontalAlignment(SwingConstants.CENTER);
        panelChart.add(lblChart,BorderLayout.NORTH);
        showBarChart();

        JButton btnBarChart = new JButton("View Chart");
        btnBarChart.setFont(font);
        btnBarChart.setPreferredSize(new Dimension(panelChart.getPreferredSize().width,30));
        btnBarChart.setFocusable(false);
        btnBarChart.addActionListener(e -> showBarChartForFrame());
        panelChart.add(btnBarChart,BorderLayout.SOUTH);

        panelBestSeller = new JPanel(new BorderLayout());
        panelBestSeller.setBorder(new LineBorder(Color.BLACK,1,false));
        panelCenter.add(panelBestSeller);

        JLabel lblBestSeller = new JLabel();
        lblBestSeller.setText("5 BEST SELLER PRODUCTS OF THE MONTH");
        lblBestSeller.setOpaque(true);
        lblBestSeller.setBackground(new Color(255, 227, 126));
        lblBestSeller.setPreferredSize(new Dimension(panelBestSeller.getPreferredSize().width,40));
        lblBestSeller.setFont(titleFont);
        lblBestSeller.setHorizontalTextPosition(SwingConstants.CENTER);
        lblBestSeller.setHorizontalAlignment(SwingConstants.CENTER);
        panelBestSeller.add(lblBestSeller,BorderLayout.NORTH);
        showPieChart();

        JButton btnPieChart = new JButton("View Chart");
        btnPieChart.setFont(font);
        btnPieChart.setPreferredSize(new Dimension(panelBestSeller.getPreferredSize().width,30));
        btnPieChart.setFocusable(false);
        btnPieChart.addActionListener(e -> showPieChartForFrame());
        panelBestSeller.add(btnPieChart,BorderLayout.SOUTH);
        clock();
    }
    private void clock() {
        Thread clock = new Thread(() -> {
            try {
                for (;;) {
                    Date date = new Date();
                    Format formatter = new SimpleDateFormat("hh:mm:ss a");
                    String s = formatter.format(date);
                    lblClock.setText(s);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MainMenuForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        clock.start();
    }
    public void showPieChart(){

        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset( );
        connection = ConnectDatabase.connectDB();
        String query = "SELECT transaction_list.Product_Name, sum(transaction_list.Product_Quantity) as totalSale, transactionReport.Transaction_Date from transaction_list inner join transactionReport on transactionReport.Transaction_ID = transaction_list.Transaction_ID where transactionReport.transaction_Date BETWEEN DATE_ADD(CURRENT_DATE, INTERVAL -1 MONTH) and CURRENT_DATE group by transaction_list.Product_Name ORDER BY totalSale DESC LIMIT 5;";
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                barDataset.setValue(resultSet.getString("Product_Name"), resultSet.getDouble("totalSale"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //create chart
        JFreeChart pieChart = ChartFactory.createPieChart(null,barDataset, false,true,false);//explain
        PiePlot piePlot =(PiePlot) pieChart.getPlot();

        //changing pie chart blocks colors
        piePlot.setSectionPaint(0, new Color(255,255,102));
        piePlot.setSectionPaint(1, new Color(102,255,102));
        piePlot.setSectionPaint(2, new Color(255,102,153));
        piePlot.setSectionPaint(3, new Color(0,204,204));
        piePlot.setBackgroundPaint(Color.white);

        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(pieChart);
        panelBestSeller.add(barChartPanel, BorderLayout.CENTER);
        panelBestSeller.validate();
    }

    public void showLineChart(){
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        connection = ConnectDatabase.connectDB();
        String query = "SELECT SUM(Transaction_Total) AS totalRevenue, transaction_Date as transactionDate FROM transactionReport GROUP BY `Transaction_Date` ORDER BY Transaction_Date ASC LIMIT 7;";
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                dataset.setValue(resultSet.getDouble("totalRevenue"), "Revenue", resultSet.getString("transactionDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //create chart
        JFreeChart lineChart = ChartFactory.createLineChart(null,"Daily","Revenue",
                dataset, PlotOrientation.VERTICAL, false,true,false);

        //create plot object
        CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();

        lineCategoryPlot.setBackgroundPaint(Color.white);
        lineCategoryPlot.setRangeCrosshairVisible(true);
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();

        Color lineChartColor = new Color(204,0,51);
        lineRenderer.setSeriesPaint(0, lineChartColor);

        //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(lineChart);

        panelRevenue.add(lineChartPanel, BorderLayout.CENTER);
        panelRevenue.validate();
    }
    private void showBarChart(){
        connection = ConnectDatabase.connectDB();

        try {
            String totalProductQuery = "SELECT transaction_list.Product_Name, sum(transaction_list.Product_Quantity) as totalSale, transactionReport.Transaction_Date from transaction_list inner join transactionReport on transactionReport.Transaction_ID = transaction_list.Transaction_ID group by transaction_list.Product_Name limit 5";
            assert connection != null;
            preparedStatement = connection.prepareStatement(totalProductQuery);
            resultSet = preparedStatement.executeQuery();
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            while(resultSet.next()){
                defaultCategoryDataset.setValue(resultSet.getDouble("totalSale"),"Sales",resultSet.getString("Product_Name"));
            }
            JFreeChart jFreeChart = ChartFactory.createBarChart(null,"Products","Top Sales",defaultCategoryDataset, PlotOrientation.VERTICAL,true,true,false);
            CategoryPlot categoryPlot = jFreeChart.getCategoryPlot();
            categoryPlot.setRangeGridlinePaint(Color.BLUE);

            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color clr3 = new Color(204,0,51);
            renderer.setSeriesPaint(0,clr3);

            ChartPanel chartPanel = new ChartPanel(jFreeChart);
            panelChart.add(chartPanel,BorderLayout.CENTER);
            panelChart.validate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void totalProduct(JLabel lbl){
        connection = ConnectDatabase.connectDB();
        try {
            String totalProductQuery = "Select count(Product_ID) as ID from products";
            assert connection != null;
            preparedStatement = connection.prepareStatement(totalProductQuery);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                lbl.setText(resultSet.getString("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
//    public void getModel(){
//        DefaultTableModel tableModel = (DefaultTableModel) tblProducts.getModel();
//        tableModel.setRowCount(0);
//    }
//    private void showDataInTable(){
//        ArrayList<TotalStock> productList = getProductList();
//        defaultTableModel.setRowCount(0);
//        Object[] column = new Object[2];
//        for(TotalStock listProduct:productList){
//            column[0] = listProduct.getTotalStock();
//            column[1] = listProduct.getQuantityType();
//            defaultTableModel.addRow(column);
//        }
//    }
//    public ArrayList<TotalStock> getProductList(){
//        ArrayList<TotalStock> productList = new ArrayList<>();
//        Connection con = ConnectDatabase.connectDB();
//        String productQuery = "Select sum(Product_Quantity) as TotalStock,Product_Quantity_Type from products group by Product_Quantity_Type;";
//        Statement statement;
//        ResultSet resultSet;
//        try {
//            assert con != null;
//            statement = con.createStatement();
//            resultSet = statement.executeQuery(productQuery);
//            TotalStock productsLists;
//            while(resultSet.next()){
//                productsLists = new TotalStock(resultSet.getDouble("TotalStock"),resultSet.getString("Product_Quantity_Type"));
//                productList.add(productsLists);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return productList;
//    }

    private void totalSalesToday(JLabel lbl){

        connection = ConnectDatabase.connectDB();
        try {
            String totalProductQuery = "SELECT count(`Transaction_ID`) as totalSales FROM transactionReport where transaction_date = current_date GROUP by `Transaction_Date`;";
            assert connection != null;
            preparedStatement = connection.prepareStatement(totalProductQuery);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                lbl.setText(resultSet.getString("totalSales"));
            }else{
                lbl.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void showPieChartForFrame(){

        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset( );
        connection = ConnectDatabase.connectDB();
        String query = "SELECT transaction_list.Product_Name, sum(transaction_list.Product_Quantity) as totalSale, transactionReport.Transaction_Date from transaction_list inner join transactionReport on transactionReport.Transaction_ID = transaction_list.Transaction_ID where transactionReport.transaction_Date BETWEEN DATE_ADD(CURRENT_DATE, INTERVAL -1 MONTH) and CURRENT_DATE  group by transaction_list.Product_Name;";
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                barDataset.setValue(resultSet.getString("Product_Name"), resultSet.getDouble("totalSale"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //create chart
        JFreeChart pieChart = ChartFactory.createPieChart(null,barDataset, false,true,false);//explain
        PiePlot piePlot =(PiePlot) pieChart.getPlot();

        //changing pie chart blocks colors
        piePlot.setSectionPaint(0, new Color(255,255,102));
        piePlot.setSectionPaint(1, new Color(102,255,102));
        piePlot.setSectionPaint(2, new Color(255,102,153));
        piePlot.setSectionPaint(3, new Color(23,123,204));
        piePlot.setSectionPaint(4, new Color(112,234,204));
        piePlot.setSectionPaint(5, new Color(232,204,21));
        piePlot.setSectionPaint(6, new Color(255,100,20));
        piePlot.setSectionPaint(7, new Color(150,150,255));


        piePlot.setBackgroundPaint(Color.white);

        //create chartPanel to display chart(graph)
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        JDialog dialog = new JDialog();
        dialog.setTitle("Best Seller Products");
        dialog.add(pieChartPanel);
        dialog.setSize(800,500);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);

    }
    public void showLineChartForFrame() {
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        connection = ConnectDatabase.connectDB();
        String query = "SELECT SUM(Transaction_Total) AS totalRevenue, transaction_Date as transactionDate FROM transactionReport GROUP BY `Transaction_Date` ORDER BY Transaction_Date ASC;";
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataset.setValue(resultSet.getDouble("totalRevenue"), "Revenue", resultSet.getString("transactionDate"));
            }


            //create chart
            JFreeChart lineChart = ChartFactory.createLineChart("Daily Revenue", "Daily", "Revenue",
                    dataset, PlotOrientation.VERTICAL, false, true, false);

            //create plot object
            CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();

            lineCategoryPlot.setBackgroundPaint(Color.white);
            lineCategoryPlot.setRangeCrosshairVisible(true);


            LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
            lineCategoryPlot.setRenderer(lineRenderer);
            Color lineChartColor = new Color(204, 0, 51);
            lineRenderer.setSeriesPaint(0, lineChartColor);

            //create chartPanel to display chart(graph)
            ChartPanel lineChartPanel = new ChartPanel(lineChart);

            JDialog dialog = new JDialog();
            dialog.setTitle("Revenue Title");
            dialog.add(lineChartPanel);
            dialog.setSize(800, 500);
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showBarChartForFrame(){
        connection = ConnectDatabase.connectDB();

        try {
            String totalProductQuery = "SELECT transaction_list.Product_Name, sum(transaction_list.Product_Quantity) as totalSale, transactionReport.Transaction_Date from transaction_list inner join transactionReport on transactionReport.Transaction_ID = transaction_list.Transaction_ID group by transaction_list.Product_Name";
            assert connection != null;
            preparedStatement = connection.prepareStatement(totalProductQuery);
            resultSet = preparedStatement.executeQuery();
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            while(resultSet.next()){
                defaultCategoryDataset.setValue(resultSet.getDouble("totalSale"),"Sales",resultSet.getString("Product_Name"));
            }
            JFreeChart jFreeChart = ChartFactory.createBarChart(null,"Products","Total Sales",defaultCategoryDataset, PlotOrientation.VERTICAL,true,true,false);
            CategoryPlot categoryPlot = jFreeChart.getCategoryPlot();
            categoryPlot.setRangeGridlinePaint(Color.BLUE);

            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color clr3 = new Color(204,0,51);
            renderer.setSeriesPaint(0,clr3);

            ChartPanel chartPanel = new ChartPanel(jFreeChart);
           JDialog dialog = new JDialog();
            dialog.setTitle("Total Product Sales");
            dialog.add(chartPanel);
            dialog.setSize(800,500);
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            try {
                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getNotification(){
        try{

            double stock;
            String productName;
            String notificationQuery = "Select * from products";
            Connection connection = ConnectDatabase.connectDB();
            ResultSet resultSet;
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(notificationQuery);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                stock = resultSet.getDouble("Product_Quantity");
                productName = resultSet.getString("Product_name");
                int wholeStock = (int) stock;
                if (stock <= 3 && stock >0){
                    txtNotification.append("The Product \""+productName+"\" has "+wholeStock+" available stock only\n");
                } else if (stock == 0) {
                    txtNotification.append("The Product \""+productName+"\" is out of stock\n");
                }
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }



        return txtNotification.getText();
    }

}
