package com.POS;

import com.userInfo.AllUsers;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 *
 * @author All Open source developers
 * @version 1.0.0.0
 * @since 2014/12/22
 */
/*This PrintSupport java class was implemented to get printout.
 * This class was specially designed to print a JTable content to a paper.
 * Specially this class formatted to print 7cm width paper.
 * Generally for pos thermal printer.
 * Free to customize this source code as you want.
 * Illustration of basic invoice is in this code.
 * demo by gayan liyanaarachchi

 */

public class PrintSupport {

    static JTable itemsTable;
    public static  int total_item_count=0;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss a";
    public  static String[] title = new String[] {"Name","Price","Qty",""};

    public static void setItems(Object[][] printItem){
        DefaultTableModel model = new DefaultTableModel();
        //assume JTable has 4 columns.
        model.addColumn(title[0]);
        model.addColumn(title[1]);
        model.addColumn(title[2]);
        model.addColumn(title[3]);



        int rowCount=printItem.length;

        addToModel(model, printItem, rowCount);


        itemsTable = new JTable(model);
    }

    public static void addToModel(DefaultTableModel model,Object [][]data,int rowCount){
        int count=0;
        while(count < rowCount){
            model.addRow(data[count]);
            count++;
        }
        if(model.getRowCount()!=rowCount)
            addToModel(model, data, rowCount);

        System.out.println("Check Passed.");
    }

    public Object[][] getTableData (JTable table) {
        int itemCount=table.getRowCount();
        System.out.println("Item Count:"+itemCount);

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int nRow = dtm.getRowCount(), nCol =dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        if(itemCount==nRow)                                        //check is there any data loss.
        {
            for (int i = 0 ; i < nRow ; i++){
                for (int j = 0 ; j < nCol ; j++){
                    tableData[i][j] = dtm.getValueAt(i,j);           //pass data into object array.
                }}
            System.out.println("Data check passed");
        }
        else{
            //collecting data again because of data loss.
            getTableData(table);
        }
        return tableData;                                       //return object array with data.
    }

    public static PageFormat getPageFormat(PrinterJob pj){
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double middleHeight =total_item_count*1.0;  //dynamic----->change with the row count of JTable
        double headerHeight = 5.0;                  //fixed----->but can be mod
        double footerHeight = 5.0;                  //fixed----->but can be mod

        double width = convert_CM_To_PPI(7);      //printer know only point per inch.default value is 72ppi
        double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(
                convert_CM_To_PPI(0.25),
                convert_CM_To_PPI(0.5),
                width - convert_CM_To_PPI(0.35),
                height - convert_CM_To_PPI(3));   //define boarder size after that print area width is about 180 points

        pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        return pf;
    }


    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 250d;
    }

    public static String now() {
//get current date and time as a String output
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());

    }

    public static class MyPrintable implements Printable {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat,
                         int pageIndex) throws PrinterException {
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;


                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY());
                Font font = new Font("Monospaced",Font.PLAIN,7);
                g2d.setFont(font);


                try {
	        	/*
                         * Draw Image*
                           assume that printing receipt has logo on top
                         * that logo image is in .gif format .png also support
                         * image resolution is width 100px and height 50px
                         * image located in root--->image folder
                         */
                    int x=20 ;                                        //print start at 100 on x-axis
                    int y=10;                                          //print start at 10 on y-axis
                    int imageWidth=100;
                    int imageHeight=50;
                    BufferedImage read = ImageIO.read(new File("images/SystemIcon.jpg"));
                    g2d.drawImage(read,x,y,imageWidth,imageHeight,null);         //draw image
                    g2d.drawLine(1, y+60, 180, y+60);                          //draw line
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try{
                    /*Draw Header*/
                    int y=80;
                    g2d.drawString("Ephraim Shop", 35,y);
                    g2d.drawString("Shop of all Trades", 20,y+10);
                    g2d.drawString("Purok 4 McArthur Highway", 20,y+20);
                    g2d.drawString(" Sto. Domingo,Minalin,Pampanga", 3,y+30);


                    //shift a line by adding 10 to y value
                    g2d.drawString(now(), 5, y+40);
                    g2d.drawString("TIN: Y70-023-784-000",5,y+50);//print date
                    g2d.drawString("Cashier ID: "+AllUsers.userID, 5, y+60);
                    g2d.drawString("Transact. ID: "+AllUsers.transactionID,5,y+70);

                    font = new Font("Monospaced",Font.PLAIN,10);
                    g2d.setFont(font);
                    g2d.drawString("Official Receipt",5,y+80);
                    font = new Font("Monospaced",Font.PLAIN,7);
                    g2d.setFont(font);

                    /*Draw Columns*/
                    g2d.drawLine(1, y+90, 180, y+90);
                    g2d.drawString(title[0], 1 ,y+100);
                    g2d.drawString(title[1], 100 ,y+100);
                    g2d.drawString(title[2], 70 ,y+100);
                    g2d.drawLine(1, y+110, 180, y+110);

                    int cH = 0;
                    TableModel mod = itemsTable.getModel();
                    double price;
                    for(int i = 0;i < mod.getRowCount() ; i++){
                        /*Assume that all parameters are in string data type for this situation
                         * All other primitive data types are accepted.
                         */
                        String itemName = mod.getValueAt(i, 1).toString();
                        price = Double.parseDouble(mod.getValueAt(i, 2).toString());
                        int intPrice = (int) price;
                        String quantity = mod.getValueAt(i, 3).toString();

                        cH = (y+120) + (10*i);                             //shifting drawing line

                        g2d.drawString(itemName,1, cH);
                        g2d.drawString(String.valueOf(intPrice) , 100, cH);
                        g2d.drawString(quantity , 70, cH);

                    }

                    /*Footer*/
                    g2d.drawString("Purchased Item/s: "+AllUsers.purchasedItem,1,cH+20);
                    g2d.drawLine(1, cH+30, 180, cH+30);


                    g2d.drawString("Subtotal",1,cH+40);
                    g2d.drawString(AllUsers.subTotal,100,cH+40);
                    g2d.drawString("VAT(12%)",1,cH+50);
                    g2d.drawString(AllUsers.vat,100,cH+50);
                    g2d.drawString("Discount",1,cH+60);
                    g2d.drawString(AllUsers.discount,100,cH+60);
                    g2d.setFont(new Font("Monospaced",Font.BOLD,7));
                    g2d.drawString("TOTAL",1,cH+70);
                    g2d.drawString(AllUsers.grandTotal,100,cH+70);
                    g2d.setFont(font);
                    g2d.drawString("Cash",1,cH+80);
                    g2d.drawString(AllUsers.cash,100,cH+80);
                    g2d.setFont(new Font("Monospaced",Font.BOLD,7));
                    g2d.drawString("Change",1,cH+90);
                    g2d.drawString(AllUsers.change,100,cH+90);
                    g2d.setFont(font);
                    g2d.drawString("Customer Name: ",1, cH+110);
                    g2d.drawString(AllUsers.custName,1, cH+120);
                    g2d.drawString("Customer Address: ",1, cH+130);
                    g2d.drawString(AllUsers.custAddress,1, cH+140);

                    font = new Font("Arial",Font.PLAIN,9) ;                  //changed font size
                    g2d.setFont(font);

                    g2d.drawString("Thank You Come Again",20, cH+210);
                    g2d.drawString("All rights Reserved 2022-2023",10, cH+225);

                    font = new Font("Arial",Font.PLAIN,7) ;                  //changed font size
                    g2d.setFont(font);
                    g2d.drawString("Ephraim Contact No.: 09208540449",10,cH+245);
                    g2d.drawString("Email: ephraimshop@gmail.com", 10,cH+255);
                    g2d.drawLine(1, cH+265, 180, cH+265);
                    BufferedImage read = ImageIO.read(new File("images/transactionBarcode/"+AllUsers.transactionID+".png"));
                    g2d.drawImage(read,-10,cH+275,150,60,null);
                    g2d.drawLine(1, cH+350, 180, cH+350);


                    //end of the receipt
                }
                catch(Exception r){
                    r.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

}