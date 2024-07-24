/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

import Database.*;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.SeekableByteChannel;
import java.security.CodeSource;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class DashBoard extends javax.swing.JFrame implements Runnable{

    /**
     * Creates new form DashBoard
     */
    DatabaseConnection db;
    InventorySystem IS; 
    PDFGenerator PG;
    int SB1WidthP = 286 ;
    boolean SizeCall = false;
    int x = 0;
    int y = 0;
    int maxx=45;
    int maxy=65;
    int FeaturesX = 220;
    int SB1X = 250;
    int SB1Y = 730;
    int SB1Width = 1090;
    int SB1Height = 20;
    int SB2X;
    int SB2Y;
    
    static javax.swing.JPanel mypanel;
    boolean WindowState = true;
    int xMouse=0;
    int yMouse=0;
    int SubF1MP = 0;
    int SubF2MP = 0;
    int SubF3MP = 0;
    int SubF4MP = 0;
    int SubF5MP = 0;
    int SubF6MP = 0;
    int SubF7MP = 0;
    int SubF8MP = 0;
    int SubF10MP = 0;
    int SubFon = 0;
    Customer cm;
    Supplier sp;
    NewEmployee emp;
    UpdateSupplier usp;
    ProductMaster pm;
    DashBoard DB;
    
    String ProductID;
    String PPInvoiceID;
    String SPInvoiceID;
    String SupplierID;
    String CustomerID;
    
    SimpleDateFormat datef = new SimpleDateFormat("YYYY-MMM-dd");
    Date Dt = new Date();
                
    
    JTable PPTInvoiceD;
    JTable SPTInvoiceD;
    JTable VCustomerT;
    JTable VSupplierT;
    DefaultTableModel PPInvoicemodel;
    DefaultTableModel SPInvoicemodel;
    DefaultTableModel VCustomermodel;
    DefaultTableModel VSuppliermodel;
    int Row;
    int datacount = 0;
    String[][] data = new String[3][20];
    String[][] ProductDate = new String[3][20];
    int ProductDateCount = 0;
    
    int VSupplierUCount = 0;
    int VCustomerUCount = 0;
    
    //Stock Panel
    ButtonGroup BG;
    String STSearchBy = "All";
    long STQuantity = 0;
    
    //Pending Report Panel
    JTable PRTSupplierD;
    JTable PRTCustomerD;
    JTable PRTPersonToPayD;
    JTable PRTPersonToReceiveD;
    DefaultTableModel PRTMSuppliermodel;
    DefaultTableModel PRTMCustomermodel;
    DefaultTableModel PRTMPersonToPay;
    DefaultTableModel PRTMPersonToReceive;
    
    NewPerson NP;
    ButtonGroup PR;
    int PRPanels = 0;
    int NewPersonUpdateCount = 0;
    
    //PurchaseInvoiceReport
    JTable PurRTSupplierIR;
    JTable SellRTCustomerIR;
    DefaultTableModel PurRTMSupplierIR;
    DefaultTableModel SellRTMCustomerIR;
    
    int EditPurchaseInvoicecount = 0;
    
    //Home
    DefaultTableModel HTMStock;
    DefaultTableModel HTMStokByExp;
    ButtonGroup HBG;
    
    //Database Backup
    int DatabaseBackupCount = 0;
    
    //EmployeeAuthentication
    JTable EATEmpD;
    DefaultTableModel EATMEmpD;
    int EditEmployeeCount = 0;
    
    //EmployeePermission
    String EmpUserID = "";
    int NewEmployeeCount = 0;
    
    //TransactionReport
    JTable TRTTransaction;
    DefaultTableModel TRTMTransaction;
    
    //ProductMaster
    int ProductMasterCount = 0; 
    
    //Validation
    boolean DBLHomeV = true;
    boolean DBLProductCategoryV = true;
    boolean DBLCustomerV = true;
    boolean DBLSupplierV = true;
    boolean DBLInventoryV = true;
    boolean DBLProductMasterV = true;
    boolean DBLStockV= true;
    boolean DBLReportV = true;
    boolean DBLPendingReportV = true;
    boolean DBLEmployeeV = true;
    boolean DBLSettingV = true;



    boolean DBPCLViewProductV = true;
    boolean DBCLAddCustomerV = true;
    boolean DBCLViewCustomerV = true;
    boolean DBSLAddSupplierV = true;
    boolean DBSLViewSupplierV = true;
    boolean DBILAddProductV = true;
    boolean DBPCLAddProductV = true;
    boolean DBILSellProductV = true;
    boolean DBILPurchaseProductV = true;
    boolean DBPMLProductMasterV = true;
    boolean DBSLStockInV = true;
    boolean DBRLPurchaseReportV = true;
    boolean DBRLSellReportV = true;
    boolean DBRLTransactionReportV = true;
    boolean DBPRLPendingReporetV = true;
    boolean DBPRLNewPersonV = true;
    boolean DBELNewEmployeeV = true;
    boolean DBELEmpAuthenticationV = true;
    boolean DBELEmpPermissionV = true;
    boolean DBSLProfileUpdateV = true;
    boolean DBSLBackupV = true;
    
    
    Pattern pattern = Pattern.compile("[A-Za-z0-9_@%.]+");
    Pattern pattern2 = Pattern.compile("[A-Za-z0-9]+");
    
    String AdminUserID;
    
    //ShopDetails
    Pattern ShopDPattern = Pattern.compile("[0-9]+");
    
    //Terms And Conditions
    Pattern pattern3 = Pattern.compile("[A-Za-z0-9_'. /]+");
    
    //Threads
    Thread t1;
    Thread t2;
    final AtomicBoolean running  = new AtomicBoolean(false);
    String ImageSliderPath = "src\\ProductImages\\";
    String ImageSliderPath2 = "src/ProductImages/";
    public DashBoard(String User,String UserID) throws SQLException {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("InventoryImage.png")));
        CheckPanel();
        IS = new InventorySystem();
        db = new DatabaseConnection(InventorySystem.con);
        PG = new PDFGenerator(InventorySystem.con, this.DB);
        DB = this;
        
        //remove panel
        //jPanel8.setBackground(new Color(240,240,240,155));
        //jPanel9.setBackground(new Color(240,240,240,155));
        Features.remove(SubF1);
        Features.remove(SubF2);
        Features.remove(SubF3);
        Features.remove(SubF4);
        Features.remove(SubF5);
        Features.remove(SubF6);
        Features.remove(SubF7);
        Features.remove(SubF8);
        Features.remove(SubF9);
        Features.remove(SubF10);
        Features.repaint();
        Features.revalidate();
        //remove panel
        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        
        //add panel
        jPanel1.add(Home);
        jPanel1.repaint();
        jPanel1.revalidate();
        
        Vector<String> ProductD = new Vector<String>();
        
        
        HBG = new ButtonGroup();
        HBG.add(HRBBackup);
        HBG.add(HRBCurrent);
        HBG.add(HRBSortByExpDate);
        HRBCurrent.setSelected(true);
        
        HTMStock = (DefaultTableModel)HTStock.getModel();
        ProductD = db.getOStock();
        for(int i=0,j=0;i<ProductD.size()/5;j+=5,i++)
        {
            Object[] obj = {ProductD.get(j),ProductD.get(j+1),ProductD.get(j+2),ProductD.get(j+3),ProductD.get(j+4)};

            HTMStock.addRow(obj);
        }
       
//        ProductDExp = db.getStock(HTExpDNotify);
        
        
//        jPanel1.setSize(1116, 706);
//        SaleProduct.setSize(1116, 706);
//        ProductPurchase.setSize(1116, 706);
//        ProductEntery.setSize(1116, 706);
//        Product.setSize(1116, 706);
//        jPanel4.setSize(1116, 706);;
//        ProductMasters.setSize(1116, 706);
        cm = new Customer(this.DB);
        sp = new Supplier(this.DB);
        pm = new ProductMaster(this.DB);
        emp = new NewEmployee(this.DB);
        NP = new NewPerson(this.DB);
        //usp = new UpdateSupplier(SupplierID, x);
//        jComboBox14.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
//            public void keyReleased(KeyEvent event) {     
//                String pesawat[] = {"Garuda","Lion Air","Lufthansa Air","Batavia Air","Bali Air"};
//                Vector vectorPesawat = new Vector();
//                int a;
//                for(a=0;a<pesawat.length;a++)
//                {
//                        vectorPesawat.add(pesawat[a]);
//                }
//                ComboBoxFilter cl = new ComboBoxFilter(jComboBox14,vectorPesawat);
//                System.out.println(jComboBox14.getEditor().getItem());
//                String text = jComboBox14.getEditor().getItem().toString();
//                cl.keytyped(text);
//                System.out.println(jComboBox14.getEditor().getItem());
//            }
//        });
        
        BG = new ButtonGroup();
        BG.add(SRBBackupStock);
        BG.add(SRBBoth);
        BG.add(SRBCurrentStock);
        SRBBoth.setSelected(true);
        
        PR = new ButtonGroup();
        PR.add(PRRBCS);
        PR.add(PRRBNP);
        PRRBCS.setSelected(true);
          
        
        String AccountUName;
        if(User.equals("Employee"))
        {
            EmpUserID = UserID;
            DBLAccountType.setText("Employee");
            AccountUName = db.getEmpName(UserID);
            ValidateDashBoard();
        }else{
            AdminUserID = UserID;
            DBLAccountType.setText("Admin");
            AccountUName = db.getAdminName(UserID);
        }
        DBLAccountUserName.setText(AccountUName);
        
        
        SupplierNameSorting();
        CustomerNameSorting();
        EmpNameSorting();
        //InvoiceTable
        PPInvoiceTable();
        SPInvoiceTable();
        VCustomerTable();
        VSupplierTable();
        //PendinReport
        PRSupplierTable();
        PRCustomerTable();
        PRPersonToPayTable();
        PRPersonToReceiveTable();    
        //PurchaseReport
        PurRSupplierInvoiceTable();
        PurRCustomerInvoiceTable();
        //EmployeeAuthentication
        EAEmployeeTable();
        //Transaction Table
        TransactionTablle();
        
        //Threads
        t1 = new Thread(this);
        t1.start();
    }

    @Override
    public void run()
    {
        int i=0;
        while(true)
        {
            String Fname = "";
            File f =  new File(ImageSliderPath2);

            for(File file : f.listFiles())
            {
                if(file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG") || file.getName().endsWith(".png") || file.getName().endsWith(".PNG"))
                {
                    Fname = file.getPath();
                    try {
                        ImageIcon icon = new ImageIcon(Fname);
            //            System.out.println(List[i]);
                        Image img1 = icon.getImage().getScaledInstance(510, 540, Image.SCALE_DEFAULT);
                        Image img2 = icon.getImage().getScaledInstance(510, 640, Image.SCALE_DEFAULT);
            //                System.out.println(Fname);

                        if (running.get()) {
                            PELProductImages.setIcon(new ImageIcon(img1));
                        }
                        DBLProductImages.setIcon(new ImageIcon(img2));
                    } catch (Exception e) {
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            i=0;
           
        }
    }
    private DashBoard() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    void TransactionTablle(){
        String[] columnNames = {"User Type", "Name", "Given", "Taken", "Date"};
        Object[][] data = { };

        TRTMTransaction = new DefaultTableModel(data, columnNames);
        this.TRTTransaction = new JTable(TRTMTransaction) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 9) ? Icon.class : Object.class;
          }
        };
        this.TRTTransaction.setPreferredScrollableViewportSize(this.TRTTransaction.getPreferredSize());
        this.TRTTransaction.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.TRTTransaction);
        scrollPane.setBounds(30, 140, 1060, 560);
        TransactionReport.add(scrollPane);
        TransactionTMouseEvent();
    }
    
    void TransactionTMouseEvent()
    {
        TRTTransaction.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = TRTTransaction.rowAtPoint(evt.getPoint());
                int col = TRTTransaction.columnAtPoint(evt.getPoint());
                //String[] columnNames = {  "InvoiceID", "SupplierID", "Date", "Total Amount", "Discount","Paid Amount", "Dues", "Net Amount", "Gross Amount", "Edit"};
                if(col == 9)
                {
//                    if(EditPurchaseInvoicecount == 0)
//                    {
//                        try {
//                            EditPurchaseInvoicecount = 1;
//                            EditPurchaseInvoice EDI = new EditPurchaseInvoice(PurRTMSupplierIR.getValueAt(row, 0).toString(),DB,"PurchaseInvoice");
//                            EDI.setTitle("Edit Invoice "+PurRTMSupplierIR.getValueAt(row, 0).toString()+"");
//                            EDI.setLocation(200, 100);
//                            EDI.setResizable(false);
//                            EDI.setSize(1085, 570);
//                            EDI.setVisible(true);
//                        } catch (SQLException ex) {
////                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
                }
                
                if(col == 0)
                {
                    
//                    try {
//                        try {
//                            PG.SupplierInvoice(PurRTMSupplierIR.getValueAt(row, 0).toString());
//                        } catch (FileNotFoundException ex) {
////                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (BadElementException ex) {
////                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (SQLException ex) {
////                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    } catch (DocumentException ex) {
////                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
////                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
            }
        });
    }
    
    void ValidateDashBoard() throws SQLException{
        
        String MainPermission = "";
        String SubPermission = "";
        
        try {
            MainPermission = db.getMainPermission(EmpUserID);
            SubPermission = db.getSubPermission(EmpUserID);
        } catch (SQLException sQLException) {
        }
        
//        System.out.println(MainPermission);
        String[] MainPerM = MainPermission.split(",");
        String[] SubPerM = SubPermission.split(",");
        for(int i=0;i<MainPerM.length;i++)
        {
//            System.out.println(MainPerM[i]);
            try {
                switch (Integer.parseInt(MainPerM[i])) {
                    case 1:
                        DBLHome.setEnabled(false);
                        DBLHomeV = false;
                        break;
                    case 2:
                        DBLProductCategory.setEnabled(false);
                        DBLProductCategoryV = false;
                        
                        break;
                    case 3:
                        DBLCustomer.setEnabled(false);
                        DBLCustomerV = false;
                        SPBAddCustomer.setEnabled(false);
                        break;
                    case 4:
                        DBLSupplier.setEnabled(false);
                        DBLSupplierV = false;
                        PPBAddSupplier.setEnabled(false);
                        break;
                    case 5:
                        DBLInventory.setEnabled(false);
                        DBLInventoryV = false;
                        break;
                    case 6:
                        DBLProductMaster.setEnabled(false);
                        DBLProductMasterV = false;
                        PPBAddProduct.setEnabled(false);
                        break;
                    case 7:
                        DBLStock.setEnabled(false);
                        DBLStockV = false;
                        break;
                    case 8:
                        DBLReport.setEnabled(false);
                        DBLReportV = false;
                        break;
                    case 9:
                        DBLPendingReport.setEnabled(false);
                        DBLPendingReportV = false;
                        break;
                    case 10:
                        DBLEmployee.setEnabled(false);
                        DBLEmployeeV = false;
                        break;
                    case 11:
                        DBLSetting.setEnabled(false);
                        DBLSettingV = false;
                        break;
                }
            } catch (NumberFormatException numberFormatException) {
            }
        }
        
        for(int i=0;i<SubPerM.length;i++)
        {
//            System.out.println(SubPerM[i]);
            try {
                switch (Integer.parseInt(SubPerM[i])) {
                    case 21:
                        DBPCLViewProduct.setEnabled(false);
                        DBPCLViewProductV = false;
                        break;
                    case 31:
                        DBCLAddCustomer.setEnabled(false);
                        DBCLAddCustomerV = false;
                        SPBAddCustomer.setEnabled(false);
                        break;
                    case 32:
                        DBCLViewCustomer.setEnabled(false);
                        DBCLViewCustomerV = false;
                        break;
                    case 41:
                        DBSLAddSupplier.setEnabled(false);
                        DBSLAddSupplierV = false;
                        PPBAddSupplier.setEnabled(false);
                        break;
                    case 42:
                        DBSLViewSupplier.setEnabled(false);
                        DBSLViewSupplierV = false;
                        break;
                    case 51:
                        DBILAddProduct.setEnabled(false);
                        DBILAddProductV = false;
                        DBPCLAddProduct.setEnabled(false);
                        DBPCLAddProductV = false;
                        break;
                    case 52:
                        DBILSellProduct.setEnabled(false);
                        DBILSellProductV = false;
                        break;
                    case 53:
                        DBILPurchaseProduct.setEnabled(false);
                        DBILPurchaseProductV = false;
                        break;
                    case 61:
                        DBPMLProductMaster.setEnabled(false);
                        DBPMLProductMasterV = false;
                        PPBAddProduct.setEnabled(false);
                        break;
                    case 71:
                        DBSLStockIn.setEnabled(false);
                        DBSLStockInV = false;
                        break;
                    case 81:
                        DBRLPurchaseReport.setEnabled(false);
                        DBRLPurchaseReportV = false;
                        break;
                    case 82:
                        DBRLSellReport.setEnabled(false);
                        DBRLSellReportV = false;
                        break;
                    case 83:
                        EPCBTransactionReport.setSelected(false);
                        break;
                    case 91:
                        DBPRLPendingReporet.setEnabled(false);
                        DBPRLPendingReporetV = false;
                        break;
                    case 92:
                        DBPRLNewPerson.setEnabled(false);
                        DBPRLNewPersonV = false;
                        break;
                    case 101:
                        DBELNewEmployee.setEnabled(false);
                        DBELNewEmployeeV = false;
                        break;
                    case 102:
                        DBELEmpAuthentication.setEnabled(false);
                        DBELEmpAuthenticationV = false;
                        break;
                    case 103:
                        DBELEmpPermission.setEnabled(false);
                        DBELEmpPermissionV = false;
                        break;
                    case 111:
                        DBSLProfileUpdate.setEnabled(false);
                        DBSLProfileUpdateV = false;
                        break;
                    case 112:
                        DBSLBackup.setEnabled(false);
                        DBSLBackupV = false;
                        break;
                }
            } catch (NumberFormatException numberFormatException) {
            }
        }
    }
    
    void EmpNameSorting()
    {
        EACBEmpName.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getEmployeeNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(EACBEmpName, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = EACBEmpName.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    Vector<String> v = new Vector<String>();
                    try {
                        v = db.EAEmployeeD(EACBEmpName.getEditor().getItem().toString().trim());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    EATMEmpD.setRowCount(0);
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    Object[] obj = { v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5),v.get(6),img1};

                    EATMEmpD.addRow(obj);
                }
            }
        });
        EPCBEmpName.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getEmployeeNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(EPCBEmpName, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = EPCBEmpName.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    
                    try {
                        EmpUserID = db.EmployeeUserID(EPCBEmpName.getEditor().getItem().toString().trim());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    EPLSelectedEmp.setText(EPCBEmpName.getEditor().getItem().toString().trim());
                    String MainPermission = "";
                    String SubPermission = "";

                    try {
                        MainPermission = db.getMainPermission(EmpUserID);
                        SubPermission = db.getSubPermission(EmpUserID);
                    } catch (SQLException sQLException) {
                    }
                    
                    EPCBHome.setSelected(false);
                    EPCBProductCategory.setSelected(false);
                    EPCBCustomer.setSelected(false);
                    EPCBSupplier.setSelected(false);
                    EPCBInventory.setSelected(false);
                    EPCBProductMaster.setSelected(false);
                    EPCBStock.setSelected(false);
                    EPCBReport.setSelected(false);
                    EPCBPendingReport.setSelected(false);
                    EPCBEmployee.setSelected(false);
                    EPCBSetting.setSelected(false);


                    EPCBViewProduct.setSelected(false);
                    EPCBAddCustomer.setSelected(false);
                    EPCBViewCustomers.setSelected(false);
                    EPCBAddSupplier.setSelected(false);
                    EPCBViewSuppliers.setSelected(false);
                    EPCBAddProduct.setSelected(false);
                    EPCBSellProduct.setSelected(false);
                    EPCBPurchaseProduct.setSelected(false);
                    EPCBProductMasterSub.setSelected(false);
                    EPCBStockIn.setSelected(false);
                    EPCBPurchaseProduct.setSelected(false);
                    EPCBSellReport.setSelected(false);
                    EPCBPendingReportSub.setSelected(false);
                    EPCBNewPerson.setSelected(false);
                    EPCBNewEmployee.setSelected(false);
                    EPCBEmpAuthentication.setSelected(false);
                    EPCBEmpPermission.setSelected(false);
                    EPCBProfileUpdate.setSelected(false);
                    EPCBBackup.setSelected(false);
                    
            //        System.out.println(MainPermission);
                    String[] MainPerM = MainPermission.split(",");
                    String[] SubPerM = SubPermission.split(",");
                    for(int a=0;a<MainPerM.length;a++)
                    {
            //            System.out.println(MainPerM[i]);
                        try {
                            switch (Integer.parseInt(MainPerM[a])) {
                                case 1:
                                    EPCBHome.setSelected(true);
                                    break;
                                case 2:
                                    EPCBProductCategory.setSelected(true);
                                    break;
                                case 3:
                                    EPCBCustomer.setSelected(true);
                                    break;
                                case 4:
                                    EPCBSupplier.setSelected(true);
                                    break;
                                case 5:
                                    EPCBInventory.setSelected(true);
                                    break;
                                case 6:
                                    EPCBProductMaster.setSelected(true);
                                    break;
                                case 7:
                                    EPCBStock.setSelected(true);
                                    break;
                                case 8:
                                    EPCBReport.setSelected(true);
                                    break;
                                case 9:
                                    EPCBPendingReport.setSelected(true);
                                    break;
                                case 10:
                                    EPCBEmployee.setSelected(true);
                                    break;
                                case 11:
                                    EPCBSetting.setSelected(true);
                                    break;
                            }
                        } catch (NumberFormatException numberFormatException) {
                        }
                    }

                    for(int a=0;a<SubPerM.length;a++)
                    {
            //            System.out.println(SubPerM[i]);
                        try {
                            switch (Integer.parseInt(SubPerM[a])) {
                                case 21:
                                    EPCBViewProduct.setSelected(true);
                                    break;
                                case 31:
                                    EPCBAddCustomer.setSelected(true);
                                    break;
                                case 32:
                                    EPCBViewCustomers.setSelected(true);
                                    break;
                                case 41:
                                    EPCBAddSupplier.setSelected(true);
                                    break;
                                case 42:
                                    EPCBViewSuppliers.setSelected(true);
                                    break;
                                case 51:
                                    EPCBAddProduct.setSelected(true);
                                    break;
                                case 52:
                                    EPCBSellProduct.setSelected(true);
                                    break;
                                case 53:
                                    EPCBPurchaseProduct.setSelected(true);
                                    break;
                                case 61:
                                    EPCBProductMasterSub.setSelected(true);
                                    break;
                                case 71:
                                    EPCBStockIn.setSelected(true);
                                    break;
                                case 81:
                                    EPCBPurchaseProduct.setSelected(true);
                                    break;
                                case 82:
                                    EPCBSellReport.setSelected(true);
                                    break;
                                case 83:
                                    EPCBTransactionReport.setSelected(true);
                                    break;
                                case 91:
                                    EPCBPendingReportSub.setSelected(true);
                                    break;
                                case 92:
                                    EPCBNewPerson.setSelected(true);
                                    break;
                                case 101:
                                    EPCBNewEmployee.setSelected(true);
                                    break;
                                case 102:
                                    EPCBEmpAuthentication.setSelected(true);
                                    break;
                                case 103:
                                    EPCBEmpPermission.setSelected(true);
                                    break;
                                case 111:
                                    EPCBProfileUpdate.setSelected(true);
                                    break;
                                case 112:
                                    EPCBBackup.setSelected(true);
                                    break;
                            }
                        } catch (NumberFormatException numberFormatException) {
                        }
                    }
                    
                }
            }
        });
    }
    
    void PurRSupplierInvoiceTable(){
        String[] columnNames = {"InvoiceID", "SupplierID", "Name", "Date", "Total Amount", "Discount","Paid Amount", "Net Amount", "Gross Amount", "Edit"};
        Object[][] data = { };

        PurRTMSupplierIR = new DefaultTableModel(data, columnNames);
        this.PurRTSupplierIR = new JTable(PurRTMSupplierIR) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 9) ? Icon.class : Object.class;
          }
        };
        this.PurRTSupplierIR.setPreferredScrollableViewportSize(this.PurRTSupplierIR.getPreferredSize());
        this.PurRTSupplierIR.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.PurRTSupplierIR);
        scrollPane.setBounds(20, 170, 1080 , 530);
        PurchaseReport.add(scrollPane);
        PurRSupplierITMouseEvent();
    }
    
    void PurRSupplierITMouseEvent()
    {
        PurRTSupplierIR.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = PurRTSupplierIR.rowAtPoint(evt.getPoint());
                int col = PurRTSupplierIR.columnAtPoint(evt.getPoint());
                //String[] columnNames = {  "InvoiceID", "SupplierID", "Date", "Total Amount", "Discount","Paid Amount", "Dues", "Net Amount", "Gross Amount", "Edit"};
                if(col == 9)
                {
                    if(EditPurchaseInvoicecount == 0)
                    {
                        try {
                            EditPurchaseInvoicecount = 1;
                            EditPurchaseInvoice EDI = new EditPurchaseInvoice(PurRTMSupplierIR.getValueAt(row, 0).toString(),DB,"PurchaseInvoice");
                            EDI.setTitle("Edit Invoice "+PurRTMSupplierIR.getValueAt(row, 0).toString()+"");
                            EDI.setLocation(200, 100);
                            EDI.setResizable(false);
                            EDI.setSize(1085, 570);
                            EDI.setVisible(true);
                        } catch (SQLException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(col == 0)
                {
                    
                    try {
                        try {
                            PG.SupplierInvoice(PurRTMSupplierIR.getValueAt(row, 0).toString());
                        } catch (FileNotFoundException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BadElementException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (DocumentException ex) {
//                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
//                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    void EAEmployeeTable(){
        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit"};
        Object[][] data = { };

        EATMEmpD = new DefaultTableModel(data, columnNames);
        this.EATEmpD = new JTable(EATMEmpD) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 7 || column == 8) ? Icon.class : Object.class;
          }
        };
        this.EATEmpD.setPreferredScrollableViewportSize(this.EATEmpD.getPreferredSize());
        this.EATEmpD.setDefaultEditor(Object.class, null);
        this.EATEmpD.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(this.EATEmpD);
        scrollPane.setBounds(30, 80, 1080, 6200);
        EmployeeAuthentication.add(scrollPane);
        EAEmployeeTMouseEvent();
    }
    
    void EAEmployeeTMouseEvent()
    {
        EATEmpD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = EATEmpD.rowAtPoint(evt.getPoint());
                int col = EATEmpD.columnAtPoint(evt.getPoint());
//                String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit", "Delete"};
                if(col == 7)
                {
                    if(EditEmployeeCount == 0)
                    {
                        try {
                            EditEmployeeCount = 1;
                            UpdateEmployee ue = new UpdateEmployee(EATEmpD.getValueAt(row, 0).toString(),DB);
                            ue.setTitle("Update Employee");
                            ue.setLocation(300, 50);
                            ue.setResizable(false);
                            ue.setSize(465, 650);
                            ue.setVisible(true);
                            ue.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        } catch (SQLException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(col == 1)
                {
                    String Password = "";
                    Password = JOptionPane.showInputDialog(null, "Enter New Password Pattern{[A-Za-z0-9_@%.]}");
                    if((Password != null) && pattern.matcher(Password).matches())
                    {
                        try {
                            db.setEmpPassword(Password,EATEmpD.getValueAt(row, 0).toString());
                            RefreshEAEmployeeTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Enter Right Password");
                    }
                }
                
                if(col == 2)
                {
                    long Salary = 0;
                    try {
                        Salary = Long.parseLong(JOptionPane.showInputDialog(null, "Enter Amount you want to pay to " + EATMEmpD.getValueAt(row, 2).toString() + ""));
                    } catch (HeadlessException headlessException) {
                    } catch (NumberFormatException numberFormatException) {
                        JOptionPane.showMessageDialog(null, "Enter Correct Amount");
                    }
                    
                    if (Salary > 0) {
                        String[] TransactionD = new String[4];
                        TransactionD[0] = EATMEmpD.getValueAt(row, 2).toString();
                        TransactionD[1] = "Employee";
                        TransactionD[2] = "Given";
                        TransactionD[3] = String.valueOf(Salary);
                        try {
                            db.TransactionTable(TransactionD);
                        } catch (SQLException ex) {
//                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                
            }
        });
    }
    
    void PurRCustomerInvoiceTable(){
        String[] columnNames = {"InvoiceID", "CustomerID", "Name",  "Date", "Total Amount", "Discount","Paid Amount", "Net Amount", "Gross Amount", "Edit"};
        Object[][] data = { };

        SellRTMCustomerIR = new DefaultTableModel(data, columnNames);
        this.SellRTCustomerIR = new JTable(SellRTMCustomerIR) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 9) ? Icon.class : Object.class;
          }
        };
        this.SellRTCustomerIR.setPreferredScrollableViewportSize(this.SellRTCustomerIR.getPreferredSize());
        this.SellRTCustomerIR.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.SellRTCustomerIR);
        scrollPane.setBounds(20, 170, 1080 , 530);
        SellReport.add(scrollPane);
        PurRCustomerITMouseEvent();
    }
    
    void PurRCustomerITMouseEvent()
    {
        SellRTCustomerIR.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = SellRTCustomerIR.rowAtPoint(evt.getPoint());
                int col = SellRTCustomerIR.columnAtPoint(evt.getPoint());
                //String[] columnNames = {  "InvoiceID", "SupplierID", "Date", "Total Amount", "Discount","Paid Amount", "Dues", "Net Amount", "Gross Amount", "Edit"};
                if(col == 9)
                {
                    if(EditPurchaseInvoicecount == 0)
                    {
                        try {
                            EditPurchaseInvoicecount = 1;
                            EditPurchaseInvoice EDI = new EditPurchaseInvoice(SellRTMCustomerIR.getValueAt(row, 0).toString(),DB,"SellInvoice");
                            EDI.setTitle("Edit Invoice "+SellRTMCustomerIR.getValueAt(row, 0).toString()+"");
                            EDI.setLocation(200, 100);
                            EDI.setResizable(false);
                            EDI.setSize(1085, 570);
                            EDI.setVisible(true);
                        } catch (SQLException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(col == 0)
                {
                    
                    try {
                        try {
                            PG.CustomerInvoice(SellRTMCustomerIR.getValueAt(row, 0).toString());
                        } catch (FileNotFoundException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BadElementException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
//                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (DocumentException ex) {
//                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
//                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    void PRSupplierTable()
    {
        String[] columnNames = { "SupplierID", "Name", "MobileNo", "Email-ID","PreDues","Edit"};
        Object[][] data = { };

        PRTMSuppliermodel = new DefaultTableModel(data, columnNames);
        this.PRTSupplierD = new JTable(PRTMSuppliermodel) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 5) ? Icon.class : Object.class;
          }
        };
        this.PRTSupplierD.setPreferredScrollableViewportSize(this.PRTSupplierD.getPreferredSize());
        this.PRTSupplierD.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.PRTSupplierD);
        scrollPane.setBounds(20, 60, 1040, 540);
        Pay.add(scrollPane);
        PRSupplierTableMouseEvent();
    }
    void PRSupplierTableMouseEvent()
    {
            PRTSupplierD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = PRTSupplierD.rowAtPoint(evt.getPoint());
                int col = PRTSupplierD.columnAtPoint(evt.getPoint());
                //String[] columnNames = { "SupplierID", "SupplierName", "MobileNo", "Email-ID", "Address","GSTINNo","AadharNo","PreDues","Edit","Delete"};
                if(col == 6)
                {
                    try {
                        db.SupplierActive(PRTMSuppliermodel.getValueAt(row, 0).toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PRTMSuppliermodel.removeRow(row);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getSupplierNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    PRCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
                    PRCBSupplierName.setSelectedIndex(-1);
                }
                if(col == 5)
                { 
                    if(VSupplierUCount == 0)
                    {
                        try {
                            UpdateSupplier USP = new UpdateSupplier(PRTMSuppliermodel.getValueAt(row, 0).toString(),"PendingReports",DB);
                            USP.setTitle("Update Supplier");
                            USP.setLocation(400, 100);
                            USP.setResizable(false);
                            USP.setSize(465, 560);
                            USP.setVisible(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
    void PRCustomerTable()
    {
        String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID","PreDues","Edit"};
        Object[][] data = { };

        PRTMCustomermodel = new DefaultTableModel(data, columnNames);
        this.PRTCustomerD = new JTable(PRTMCustomermodel) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 5) ? Icon.class : Object.class;
          }
        };
        this.PRTCustomerD.setPreferredScrollableViewportSize(this.PRTCustomerD.getPreferredSize());
        this.PRTCustomerD.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.PRTCustomerD);
        scrollPane.setBounds(20, 60, 1040, 540);
        Receive.add(scrollPane);
        PRCustomerTableMouseEvent();
    }
    void PRCustomerTableMouseEvent()
    {
            PRTCustomerD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = PRTCustomerD.rowAtPoint(evt.getPoint());
                int col = PRTCustomerD.columnAtPoint(evt.getPoint());
                //String[] columnNames = { "CustomerID", "CustomerName", "MobileNo", "Email-ID","Dues","Edit","Delete"};
                if(col == 6)
                {
                    try {
                        db.CustomerActive(PRTMCustomermodel.getValueAt(row, 0).toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PRTMCustomermodel.removeRow(row);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getCustomerNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    PRCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
                    PRCBCustomerName.setSelectedIndex(-1);
                }
                
                if(col == 5)
                {
                    if(VCustomerUCount == 0)
                    {
                        try {
                            UpdateCustomer USP = new UpdateCustomer(PRTMCustomermodel.getValueAt(row, 0).toString(),"PendingReports",DB);
                            USP.setTitle("Update Customer");
                            USP.setLocation(400, 100);
                            USP.setResizable(false);
                            USP.setSize(465, 560);
                            USP.setVisible(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
            }
        });
    }
    void PRPersonToPayTable()
    {
        String[] columnNames = { "Name", "MobileNo", "Email-ID","PreDues","Edit","Delete"};
        Object[][] data = { };

        PRTMPersonToPay = new DefaultTableModel(data, columnNames);
        this.PRTPersonToPayD = new JTable(PRTMPersonToPay) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 4 || column == 5) ? Icon.class : Object.class;
          }
        };
        this.PRTPersonToPayD.setPreferredScrollableViewportSize(this.PRTPersonToPayD.getPreferredSize());
        this.PRTPersonToPayD.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.PRTPersonToPayD);
        scrollPane.setBounds(20, 60, 1040, 540);
        PersonPay.add(scrollPane);
        PRPersonToPayTableMouseEvent();
    }
    void PRPersonToPayTableMouseEvent()
    {
            PRTPersonToPayD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = PRTPersonToPayD.rowAtPoint(evt.getPoint());
                int col = PRTPersonToPayD.columnAtPoint(evt.getPoint());
                
                if(col == 5)
                { 
                    int i = 0;
                    i = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete this Person Data");
                    if(i == 0)
                    {
                        try {
                            //System.out.println(PRTMPersonToPay.getValueAt(row, 0).toString());
                            db.RemovePerson(PRTMPersonToPay.getValueAt(row, 0).toString());
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        PRTMPersonToPay.removeRow(row);
                    }
                    
                    
                }
                if(col == 4)
                {
                    if(NewPersonUpdateCount == 0)
                    {
                        try {
                            NewPersonUpdateCount = 1;
                            //System.out.println(PRTMPersonToPay.getValueAt(row, 0).toString());
                            NewPersonUpdate NPU = new NewPersonUpdate(PRTMPersonToPay.getValueAt(row, 0).toString(), "PersonPay", DB);
                            NPU.setLocation(450, 100);
                            NPU.setResizable(false);
                            NPU.setSize(465, 570);
                            NPU.setVisible(true);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            }
        });
    }
    void PRPersonToReceiveTable()
    {
        String[] columnNames = { "Name", "MobileNo", "Email-ID","PreDues","Edit","Delete"};
        Object[][] data = { };

        PRTMPersonToReceive = new DefaultTableModel(data, columnNames);
        this.PRTPersonToReceiveD = new JTable(PRTMPersonToReceive) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 4 || column == 5) ? Icon.class : Object.class;
          }
        };
        this.PRTPersonToReceiveD.setPreferredScrollableViewportSize(this.PRTPersonToReceiveD.getPreferredSize());
        this.PRTPersonToReceiveD.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.PRTPersonToReceiveD);
        scrollPane.setBounds(20, 60, 1040, 540);
        PersonReceive.add(scrollPane);
        PRPersonToReceiveTableMouseEvent();
    }
    void PRPersonToReceiveTableMouseEvent()
    {
            PRTPersonToReceiveD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = PRTPersonToReceiveD.rowAtPoint(evt.getPoint());
                int col = PRTPersonToReceiveD.columnAtPoint(evt.getPoint());
                
                if(col == 5)
                {
                    int i = 0;
                    i = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete this Person Data");
                    if(i == 0)
                    {
                        try {
                            db.RemovePerson(PRTMPersonToReceive.getValueAt(row, 0).toString());
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        PRTMPersonToReceive.removeRow(row);
                    }else{
                        
                    }
                }
                if(col == 4)
                {
                    if(NewPersonUpdateCount == 0)
                    {
                        try {
                            NewPersonUpdateCount = 1;
                            NewPersonUpdate NPU = new NewPersonUpdate(PRTMPersonToReceive.getValueAt(row, 0).toString(), "PersonReceive", DB);
                            NPU.setLocation(450, 100);
                            NPU.setResizable(false);
                            NPU.setSize(465, 570);
                            NPU.setVisible(true);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
    void VSupplierTable()
    {
        String[] columnNames = { "SupplierID", "Name", "MobileNo", "Email-ID","PreDues","Edit","Delete"};
        Object[][] data = { };

        VSuppliermodel = new DefaultTableModel(data, columnNames);
        this.VSupplierT = new JTable(VSuppliermodel) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 5 || column == 6) ? Icon.class : Object.class;
          }
        };
        this.VSupplierT.setPreferredScrollableViewportSize(this.VSupplierT.getPreferredSize());
        this.VSupplierT.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.VSupplierT);
        scrollPane.setBounds(20, 120, 1080 , 580);
        ViewSupplier.add(scrollPane);
        VSupplierTableMouseEvent();
    }
    void VCustomerTable()
    {
        String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID","PreDues","Edit","Delete"};
        Object[][] data = { };

        VCustomermodel = new DefaultTableModel(data, columnNames);
        this.VCustomerT = new JTable(VCustomermodel) {
          @Override
          public Class getColumnClass(int column) {
            return (column ==5 || column == 6) ? Icon.class : Object.class;
          }
        };
        this.VCustomerT.setPreferredScrollableViewportSize(this.VCustomerT.getPreferredSize());
        this.VCustomerT.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.VCustomerT);
        scrollPane.setBounds(20, 120, 1080 , 580);
        ViewCustomer.add(scrollPane);
        VCustomerTableMouseEvent();
    }
    void PPInvoiceTable(){
        String[] columnNames = {  "ProductId", "Category", "Name", "Brand", "Size","UOM", "Quantity", "Price", "GST", "Delete"};
        Object[][] data = { };

        PPInvoicemodel = new DefaultTableModel(data, columnNames);
        this.PPTInvoiceD = new JTable(PPInvoicemodel) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 9) ? Icon.class : Object.class;
          }
        };
        this.PPTInvoiceD.setPreferredScrollableViewportSize(this.PPTInvoiceD.getPreferredSize());
        this.PPTInvoiceD.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.PPTInvoiceD);
        scrollPane.setBounds(20, 280, 1080 , 300);
        ProductPurchase.add(scrollPane);
        InvoiceTableMouseEvent();
    }
    void SPInvoiceTable(){
        String[] columnNames = {  "ProductId", "Category", "Name", "Brand", "Size","UOM", "Quantity", "Price", "GST", "Delete"};
        Object[][] data = { };

        SPInvoicemodel = new DefaultTableModel(data, columnNames);
        this.SPTInvoiceD = new JTable(SPInvoicemodel) {
          @Override
          public Class getColumnClass(int column) {
            return (column == 9) ? Icon.class : Object.class;
          }
        };
        this.SPTInvoiceD.setPreferredScrollableViewportSize(this.SPTInvoiceD.getPreferredSize());
        this.SPTInvoiceD.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(this.SPTInvoiceD);
        scrollPane.setBounds(20, 280, 1080 , 300);
        SellProduct.add(scrollPane);
        SPInvoiceTableMouseEvent();
    }
    void InvoiceTableMouseEvent()
    {
            PPTInvoiceD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = PPTInvoiceD.rowAtPoint(evt.getPoint());
                int col = PPTInvoiceD.columnAtPoint(evt.getPoint());
                if (col == 9) {
                    PPInvoicemodel.removeRow(row);
                    ReCalculatePP(row);
                }else{
                    Row = row;
                }
            }
        });
    }
    void VCustomerTableMouseEvent()
    {
            VCustomerT.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = VCustomerT.rowAtPoint(evt.getPoint());
                int col = VCustomerT.columnAtPoint(evt.getPoint());
                //String[] columnNames = { "CustomerID", "CustomerName", "MobileNo", "Email-ID","Dues","Edit","Delete"};
                if(col == 6)
                {
                    try {
                        db.CustomerActive(VCustomermodel.getValueAt(row, 0).toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    VCustomermodel.removeRow(row);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getCustomerNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    VCCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
                    VCCBCustomerName.setSelectedIndex(-1);
                }
                
                if(col == 5)
                {
                    if(VCustomerUCount == 0)
                    {
                        try {
                            UpdateCustomer USP = new UpdateCustomer(VCustomermodel.getValueAt(row, 0).toString(),"ViewCustomer",DB);
                            USP.setTitle("Update Customer");
                            USP.setLocation(400, 100);
                            USP.setResizable(false);
                            USP.setSize(465, 560);
                            USP.setVisible(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
                
            }
        });
    }
    
    void VSupplierTableMouseEvent()
    {
            VSupplierT.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = VSupplierT.rowAtPoint(evt.getPoint());
                int col = VSupplierT.columnAtPoint(evt.getPoint());
                //String[] columnNames = { "SupplierID", "SupplierName", "MobileNo", "Email-ID", "Address","GSTINNo","AadharNo","PreDues","Edit","Delete"};
                if(col == 6)
                {
                    try {
                        db.SupplierActive(VSuppliermodel.getValueAt(row, 0).toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    VSuppliermodel.removeRow(row);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getSupplierNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    VCCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
                    VCCBSupplierName.setSelectedIndex(-1);
                }
                if(col == 5)
                { 
                    if(VSupplierUCount == 0)
                    {
                        try {
                            UpdateSupplier USP = new UpdateSupplier(VSuppliermodel.getValueAt(row, 0).toString(),"ViewSupplier",DB);
                            USP.setTitle("Update Supplier");
                            USP.setLocation(400, 100);
                            USP.setResizable(false);
                            USP.setSize(465, 560);
                            USP.setVisible(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
    void ReCalculatePP(int row)
    {
        //deleting data
        for(int i=row;i<datacount-1;i++)
        {
            data[0][i] = data[0][i+1];
            data[1][i] = data[1][i+1];
            data[2][i] = data[2][i+1];
        }
        data[0][datacount] = "";
        data[1][datacount] = "";
        data[2][datacount] = "";
        datacount--;
        
        //deleting ProductDate
        for(int i=row;i<ProductDateCount-1;i++)
        {
            ProductDate[0][i] = ProductDate[0][i+1];
            ProductDate[1][i] = ProductDate[1][i+1];
            ProductDate[2][i] = ProductDate[2][i+1];
        }
        ProductDate[0][ProductDateCount] = "";
        ProductDate[1][ProductDateCount] = "";
        ProductDate[2][ProductDateCount] = "";
        ProductDateCount--;
        
        //RECalculating
        double GrossAmount = 0;
        if (datacount != 0) {
            int totalQuantity = 0;
            int totalGST = 0;
            double totalPurchasePrice = 0;
            double TotalAmount = 0;
            for(int i = 0; i<datacount; i++)
            {
                totalPurchasePrice = ((double)totalPurchasePrice + (Float.valueOf(data[1][i])*(double)Integer.parseInt(data[0][i])));
                totalGST = totalGST + Integer.parseInt(data[2][i]);
            }
            double AvgGST = ((double)totalGST)/(double)datacount;
    //        System.out.println("AvgGST = "+AvgGST);
            TotalAmount = totalPurchasePrice + totalPurchasePrice*(AvgGST/100);
    //        System.out.println("TotalAmount = "+TotalAmount+"    NetAmount = "+totalPurchasePrice);

            PPTFNetAmount.setText(String.valueOf(totalPurchasePrice));
            PPTFTotalAmount.setText(String.valueOf(TotalAmount));
            int Discount = Integer.parseInt(PPTFDiscount.getText());
            
            if(Discount<=100 && Discount >=0)
            {
                GrossAmount = (TotalAmount - (TotalAmount*((double)Discount/100)));
            }
//            PPLGrossAmount.setText(String.valueOf(GrossAmount));
            PPLGrossAmount.setText(new DecimalFormat("##.##").format(GrossAmount));
        }else{
            PPTFNetAmount.setText(String.valueOf("00.0"));
            PPTFTotalAmount.setText(String.valueOf("00.0"));
            PPLGrossAmount.setText(String.valueOf("00.0"));
            PPTFDues.setText(String.valueOf("00.0"));
        }
        
        double PaidAmount = 0;
        double PreDues = 0;
        
        try {
            PreDues = Double.valueOf(TFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        try {
            PaidAmount = Double.valueOf(PPTFPaidAmount.getText());
            PPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            PPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        
        Dues = (GrossAmount+PreDues) - PaidAmount;
        
        PPTFDues.setText(new DecimalFormat("##.##").format(Dues));
//        PPTFDues.setText(String.valueOf(Dues));
    }
    void SPInvoiceTableMouseEvent()
    {
            SPTInvoiceD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = SPTInvoiceD.rowAtPoint(evt.getPoint());
                int col = SPTInvoiceD.columnAtPoint(evt.getPoint());
                if (col == 9) {
                    SPInvoicemodel.removeRow(row);
                    ReCalculateSP(row);
                }else{
                    Row = row;
                }
            }
        });
    }
    void ReCalculateSP(int row)
    {
        //deleting data
        for(int i=row;i<datacount-1;i++)
        {
            data[0][i] = data[0][i+1];
            data[1][i] = data[1][i+1];
            data[2][i] = data[2][i+1];
        }
        data[0][datacount] = "";
        data[1][datacount] = "";
        data[2][datacount] = "";
        datacount--;
        
        //RECalculating
        double GrossAmount = 0;
        if (datacount != 0) {
            int totalQuantity = 0;
            int totalGST = 0;
            double totalPurchasePrice = 0;
            double TotalAmount = 0;
            for(int i = 0; i<datacount; i++)
            {
                totalPurchasePrice = ((double)totalPurchasePrice + (Float.valueOf(data[1][i])*(double)Integer.parseInt(data[0][i])));
                totalGST = totalGST + Integer.parseInt(data[2][i]);
            }
            double AvgGST = ((double)totalGST)/(double)datacount;
    //        System.out.println("AvgGST = "+AvgGST);
            TotalAmount = totalPurchasePrice + totalPurchasePrice*(AvgGST/100);
    //        System.out.println("TotalAmount = "+TotalAmount+"    NetAmount = "+totalPurchasePrice);

            SPTFNetAmount.setText(String.valueOf(totalPurchasePrice));
            SPTFTotalAmount.setText(String.valueOf(TotalAmount));
            int Discount = Integer.parseInt(SPTFDiscount.getText());

            if(Discount<=100 && Discount >=0)
            {
                GrossAmount = (TotalAmount - (TotalAmount*((double)Discount/100)));
            }
            SPLGrossAmount.setText(String.valueOf(GrossAmount));
        }else{
            SPTFNetAmount.setText(String.valueOf("00.0"));
            SPTFTotalAmount.setText(String.valueOf("00.0"));
            SPLGrossAmount.setText(String.valueOf("00.0"));
            SPTFDues.setText(String.valueOf("00.0"));
        }
        
        double PaidAmount = 0;
        try {
            PaidAmount = Double.valueOf(SPTFPaidAmount.getText());
            SPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            SPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        double PreDues = 0;
        try {
        PreDues = Double.valueOf(SPTFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(SPLGrossAmount.getText());

        Dues = (GrossAmount+PreDues) - PaidAmount;
        
        SPTFDues.setText(String.valueOf(Dues));
    }
    void SupplierNameSorting()
    {
        PPCBSupplierName.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getSupplierNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(PPCBSupplierName, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = PPCBSupplierName.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    String v[] = null;
                    try {
                        v = db.getSupplierD(PPCBSupplierName.getEditor().getItem().toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    TFMobileNo.setText(v[0]);
                    TFAadharNo.setText(v[1]);
                    TFGSTINNo.setText(v[2]);
                    TFPreDues.setText(v[3]);
                    setSupplierID();
                }
            }
        });
        PPCBSupplierName.getEditor().getEditorComponent().addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                
            }

            @Override
            public void focusLost(FocusEvent event) {
                
                String pesawat[] = null;
                try {
                    pesawat = db.getSupplierNames();
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                Vector vectorPesawat = new Vector();
                int a;
                for (a=0; a < pesawat.length; a++) {
                    vectorPesawat.add(pesawat[a]);
                }
                ComboBoxFilter cl = new ComboBoxFilter(PPCBSupplierName, vectorPesawat);
                //System.out.println(CBSupplierName.getEditor().getItem());
                String text = PPCBSupplierName.getEditor().getItem().toString();
                cl.keytyped(text);
                
                
                
                String v[] = null;
                try {
                    v = db.getSupplierD(PPCBSupplierName.getEditor().getItem().toString());
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                TFMobileNo.setText(v[0]);
                TFAadharNo.setText(v[1]);
                TFGSTINNo.setText(v[2]);
                TFPreDues.setText(v[3]);
                setSupplierID();
                //System.out.println(SupplierID);
            }
        });
        VCCBSupplierName.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getSupplierNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(VCCBSupplierName, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = VCCBSupplierName.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    Vector<String> v = new Vector<String>();
                    try {
                        v = db.VSupplierSorting(VCCBSupplierName.getEditor().getItem().toString().trim());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ImageIcon img2 = new ImageIcon("Delete.png");
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    try {
                        Object[] obj = {v.get(0), v.get(1), v.get(2), v.get(3), v.get(6), v.get(4), v.get(5), v.get(7), img1, img2};
//                    System.out.println(VCustomermodel.getRowCount());
                        VSuppliermodel.setRowCount(0);
                        VSuppliermodel.addRow(obj);
                    } catch (Exception e) {
                    }
                    
                }
            }
        });
        
        PurRCBSupplierNames.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getSupplierNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(PurRCBSupplierNames, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = PurRCBSupplierNames.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    String Name2 = PurRCBSupplierNames.getEditor().getItem().toString();
                    String Name = null;
                    try {
                        Name = db.SupplierID(Name2,null,null);
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int x = 1;
                    x = JOptionPane.showConfirmDialog(null, "Are you want to sort Supplier "+Name2+" Invoice By Date");
                    if(x == 0)
                    {
                        String date1 = null;
                        String date2 = null;
                        try {
                            date1 = IS.dateFormat.format(PurRDCFrom.getDate());
                        } catch (Exception e) {}

                        try {
                            date2 = IS.dateFormat.format(PurRDCTo.getDate());
                        } catch (Exception e) {}

                        try {
                            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                                //Load Table Data
                                //Removing Data From Table
                                PurRTMSupplierIR.setRowCount(0);
                                Vector<String> v1 = new Vector<String>();
                                try {
                                    v1 = db.PurRSupplierInvoiceDbyNameandDate(date1, date2, Name);
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                for (int a = 0, j = 0; j < (v1.size()) / 8; j++, a += 8) {
                                    ImageIcon img1 = new ImageIcon("Edit2.png");
                                    Object[] obj = {v1.get(a), v1.get(a + 1), v1.get(a + 2), v1.get(a + 3), v1.get(a + 4), v1.get(a + 5), v1.get(a + 6), v1.get(a + 7), img1};
                                    
                                    PurRTMSupplierIR.addRow(obj);
                                }
                            }
                        } catch (NullPointerException e) {
                        }
                    }else{
                        //Load Table Data
                            //Removing Data From Table
                            PurRTMSupplierIR.setRowCount(0);
                            Vector<String> v1 = new Vector<String>();
                            try {
                                v1 = db.PurRSupplierInvoiceDbyName(Name);
                            } catch (SQLException ex) {
                                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                            }



                            for(int a=0,j=0; j<(v1.size())/9;j++,a+=9)
                            {
                                ImageIcon img1 = new ImageIcon("Edit2.png");
                                Object[] obj = { v1.get(a),v1.get(a+1),v1.get(a+8),v1.get(a+2),v1.get(a+3),v1.get(a+4),v1.get(a+5),v1.get(a+6),v1.get(a+7),img1};

                                PurRTMSupplierIR.addRow(obj);
                            }
                    }
                    
                }
            }
        });
    }
    
    void VSCheckBoxandTableRefresh()
    {
        String pesawat[] = null;
        try {
            pesawat = db.getSupplierNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        VCCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
        VCCBSupplierName.setSelectedIndex(-1);
        
        //Removing Data From Table
        VSuppliermodel.setRowCount(0);
//        for(int i=0;i<count;i++)
//        count = db.CountCustomers();
        Vector v2 = new Vector();
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.Suppliers();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
            //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "Address","GSTINNo","AadharNo","PreDues","Edit","Delete"};
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1,img2};
            
            VSuppliermodel.addRow(obj); 
        }
    }
     
    void PRCheckBoxandTableRefresh()
    {
        String pesawat[] = null;
        try {
            pesawat = db.getSupplierNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PRCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
        PRCBSupplierName.setSelectedIndex(-1);
        
        //Removing Data From Table
        PRTMSuppliermodel.setRowCount(0);
//        for(int i=0;i<count;i++)
//        count = db.CountCustomers();
        Vector v2 = new Vector();
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.Suppliers();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        PRTMSuppliermodel.setRowCount(0);
        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
            //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "Address","GSTINNo","AadharNo","PreDues","Edit","Delete"};
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};
            
            PRTMSuppliermodel.addRow(obj); 
        }
    }
    
    void CustomerNameSorting()
    {
        SPCBCustomerName.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getCustomerNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(SPCBCustomerName, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = SPCBCustomerName.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    String v[] = null;
                    try {
                        v = db.getCustomerD(SPCBCustomerName.getEditor().getItem().toString().trim());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    SPTFMobileNo.setText(v[0]);
                    SPTFAadharNo.setText(v[1]);
                    SPTFGSTINNo.setText(v[2]);
                    SPTFPreDues.setText(v[3]);
                    setCutomerID();
                }
            }
        });
        SPCBCustomerName.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String pesawat[] = null;
                    try {
                        pesawat = db.getCustomerNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(SPCBCustomerName, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = SPCBCustomerName.getEditor().getItem().toString();
                    cl.keytyped(text);
                    
                    
                    String v[] = null;
                    try {
                        v = db.getCustomerD(SPCBCustomerName.getEditor().getItem().toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    SPTFMobileNo.setText(v[0]);
                    SPTFAadharNo.setText(v[1]);
                    SPTFGSTINNo.setText(v[2]);
                    SPTFPreDues.setText(v[3]);
                    setCutomerID();
                    //System.out.println(CustomerID);
                    
            }
        });
        VCCBCustomerName.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getCustomerNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(VCCBCustomerName, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = VCCBCustomerName.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    Vector<String> v = new Vector<String>();
                    try {
                        v = db.VCustomerSorting(VCCBCustomerName.getEditor().getItem().toString().trim());
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ImageIcon img2 = new ImageIcon("Delete.png");
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    try {
                        Object[] obj = {v.get(0), v.get(1), v.get(2), v.get(3), v.get(6), v.get(4), v.get(5), v.get(7), img1, img2};
//                    System.out.println(VCustomermodel.getRowCount());
                        VCustomermodel.setRowCount(0);
                        VCustomermodel.addRow(obj);
                    } catch (Exception e) {
                    }
                    
                }
            }
        });
        
        SellRCBCustomerNames.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {   
                int i=event.getKeyCode();
                
                if (i == 40 || i == 38) {
                    
                }else{
                    //System.out.println(i);
                    String pesawat[] = null;
                    try {
                        pesawat = db.getCustomerNames();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Vector vectorPesawat = new Vector();
                    int a;
                    for (a = 0; a < pesawat.length; a++) {
                        vectorPesawat.add(pesawat[a]);
                    }
                    ComboBoxFilter cl = new ComboBoxFilter(SellRCBCustomerNames, vectorPesawat);
                    //System.out.println(CBSupplierName.getEditor().getItem());
                    String text = SellRCBCustomerNames.getEditor().getItem().toString();
                    cl.keytyped(text);
                }
                
                i=event.getKeyChar();
                if(i==KeyEvent.VK_ENTER)     //or any Key Constant
                {
                    String Name2 = SellRCBCustomerNames.getEditor().getItem().toString().trim();
                    String Name = null;
                    try {
                        Name = db.CustomerID(Name2,null,null);
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int x = 1;
                    x = JOptionPane.showConfirmDialog(null, "Are you want to sort Customer "+Name2+" Invoice By Date");
                    if(x == 0)
                    {
                        String date1 = null;
                        String date2 = null;
                        try {
                            date1 = IS.dateFormat.format(SellRDCFrom.getDate());
                        } catch (Exception e) {}

                        try {
                            date2 = IS.dateFormat.format(SellRDCTo.getDate());
                        } catch (Exception e) {}

                        try {
                            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                                //Load Table Data
                                //Removing Data From Table
                                SellRTMCustomerIR.setRowCount(0);
                                Vector<String> v1 = new Vector<String>();
                                try {
                                    v1 = db.SellRCustomerInvoiceDbyNameandDate(date1, date2, Name);
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                for (int a = 0, j = 0; j < (v1.size()) / 9; j++, a += 9) {
                                    ImageIcon img1 = new ImageIcon("Edit2.png");
                                    Object[] obj = {v1.get(a), v1.get(a + 1),v1.get(a + 8), v1.get(a + 2), v1.get(a + 3), v1.get(a + 4), v1.get(a + 5), v1.get(a + 6), v1.get(a + 7), img1};
                                    
                                    SellRTMCustomerIR.addRow(obj);
                                }
                            }
                        } catch (NullPointerException e) {
                        }
                    }else{
                        //Load Table Data
                            //Removing Data From Table
                            SellRTMCustomerIR.setRowCount(0);
                            Vector<String> v1 = new Vector<String>();
                            try {
                                v1 = db.SellRCustomerInvoiceDbyName(Name);
                            } catch (SQLException ex) {
                                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                            }



                            for(int a=0,j=0; j<(v1.size())/9;j++,a+=9)
                            {
                                ImageIcon img1 = new ImageIcon("Edit2.png");
                                Object[] obj = { v1.get(a),v1.get(a+1),v1.get(a+8),v1.get(a+2),v1.get(a+3),v1.get(a+4),v1.get(a+5),v1.get(a+6),v1.get(a+7),img1};

                                SellRTMCustomerIR.addRow(obj);
                            }
                    }
                    
                }
            }
        });
    }
    
    void setCutomerID()
    {
        try{
            this.CustomerID = db.CustomerID(SPCBCustomerName.getEditor().getItem().toString().trim(), SPTFMobileNo.getText().trim(),SPTFGSTINNo.getText().trim());
        }catch(NullPointerException e){
            this.CustomerID = "";
        } catch (SQLException ex) {
            System.out.println("Error While Getting CustomerID");
        }
    }
    
    void setSupplierID()
    {
        try{
            this.SupplierID = db.SupplierID(PPCBSupplierName.getEditor().getItem().toString().trim(), TFMobileNo.getText().trim(),TFGSTINNo.getText().trim());
        }catch(NullPointerException e){
            this.SupplierID = "";
        } catch (SQLException ex) {
            System.out.println("Error While Getting SupplierID");
        }
    }
    void VCCheckBoxandtableRefresh()
    {
        String pesawat[] = null;
        try {
            pesawat = db.getCustomerNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        VCCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
        VCCBCustomerName.setSelectedIndex(-1);
        
        //Removing Data From Table
        VCustomermodel.setRowCount(0);
//        for(int i=0;i<count;i++)
//        count = db.CountCustomers();
        Vector v2 = new Vector();
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.Customers();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
        //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "Address","GSTINNo","AadharNo","PreDues","Edit","Delete"};
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1,img2};
            
            VCustomermodel.addRow(obj);
        }
    }
    void PRCheckBoxandtableRefresh()
    {
        String pesawat[] = null;
        try {
            pesawat = db.getCustomerNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PRCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
        PRCBCustomerName.setSelectedIndex(-1);
        
        //Removing Data From Table
        PRTMCustomermodel.setRowCount(0);
//        for(int i=0;i<count;i++)
//        count = db.CountCustomers();
        Vector v2 = new Vector();
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.Customers();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
        //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "Address","GSTINNo","AadharNo","PreDues","Edit","Delete"};
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};
            
            PRTMCustomermodel.addRow(obj);
        }
    }
    void setSupplierName()
    {
        String pesawat[] = null;
        try {
            pesawat = db.getSupplierNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PPCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
        PPCBSupplierName.setSelectedIndex(-1);
    }
    void setCustomerName()
    {
        String pesawat[] = null;
        try {
            pesawat = db.getCustomerNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SPCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
        SPCBCustomerName.setSelectedIndex(-1);
    }
    
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollBar2 = new javax.swing.JScrollBar();
        Features = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        DBLAccountUserName = new javax.swing.JLabel();
        DBLAccountType = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        DBLHome = new javax.swing.JLabel();
        DBLProductCategory = new javax.swing.JLabel();
        DBLCustomer = new javax.swing.JLabel();
        DBLSupplier = new javax.swing.JLabel();
        DBLInventory = new javax.swing.JLabel();
        DBLStock = new javax.swing.JLabel();
        DBLReport = new javax.swing.JLabel();
        DBLPendingReport = new javax.swing.JLabel();
        DBLSetting = new javax.swing.JLabel();
        DBLProductMaster = new javax.swing.JLabel();
        DBLEmployee = new javax.swing.JLabel();
        SubF10 = new javax.swing.JPanel();
        DBSLBackup = new javax.swing.JLabel();
        DBSLProfileUpdate = new javax.swing.JLabel();
        SubF8 = new javax.swing.JPanel();
        DBELEmpPermission = new javax.swing.JLabel();
        DBELNewEmployee = new javax.swing.JLabel();
        DBELEmpAuthentication = new javax.swing.JLabel();
        SubF7 = new javax.swing.JPanel();
        DBPRLPendingReporet = new javax.swing.JLabel();
        DBPRLNewPerson = new javax.swing.JLabel();
        SubF6 = new javax.swing.JPanel();
        DBRLPurchaseReport = new javax.swing.JLabel();
        DBRLSellReport = new javax.swing.JLabel();
        DBRLTransactionReport = new javax.swing.JLabel();
        SubF5 = new javax.swing.JPanel();
        DBSLStockIn = new javax.swing.JLabel();
        SubF9 = new javax.swing.JPanel();
        DBPMLProductMaster = new javax.swing.JLabel();
        SubF4 = new javax.swing.JPanel();
        DBILPurchaseProduct = new javax.swing.JLabel();
        DBILAddProduct = new javax.swing.JLabel();
        DBILSellProduct = new javax.swing.JLabel();
        SubF3 = new javax.swing.JPanel();
        DBSLViewSupplier = new javax.swing.JLabel();
        DBSLAddSupplier = new javax.swing.JLabel();
        SubF2 = new javax.swing.JPanel();
        DBCLAddCustomer = new javax.swing.JLabel();
        DBCLViewCustomer = new javax.swing.JLabel();
        SubF1 = new javax.swing.JPanel();
        DBPCLAddProduct = new javax.swing.JLabel();
        DBPCLViewProduct = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        ViewCustomer = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        VCCBCustomerName = new javax.swing.JComboBox();
        jButton19 = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        ProductPurchase = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        PPBAddSupplier = new javax.swing.JButton();
        PPCBSupplierName = new javax.swing.JComboBox();
        TFMobileNo = new javax.swing.JTextField();
        TFPreDues = new javax.swing.JTextField();
        PPTFINvoiceID = new javax.swing.JTextField();
        PPInvoiceDate = new javax.swing.JTextField();
        TFAadharNo = new javax.swing.JTextField();
        TFGSTINNo = new javax.swing.JTextField();
        PPLProductCategory = new javax.swing.JLabel();
        PPLProductName = new javax.swing.JLabel();
        PPLExpDate = new javax.swing.JLabel();
        PPLQuantity = new javax.swing.JLabel();
        PPLUOM = new javax.swing.JLabel();
        PPLSize = new javax.swing.JLabel();
        PPLPurchaseRate = new javax.swing.JLabel();
        PPLSellingRate = new javax.swing.JLabel();
        PPCBProductCategory = new javax.swing.JComboBox();
        PPCBProductName = new javax.swing.JComboBox();
        PPCBUOM = new javax.swing.JComboBox();
        PPCBSize = new javax.swing.JComboBox();
        PPTFSellingRate = new javax.swing.JTextField();
        PPTFPurchaseRate = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        PPTFTotalAmount = new javax.swing.JTextField();
        PPLDiscount = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        PPTFDiscount = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        PPTFNetAmount = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        PPCBPaymentMode = new javax.swing.JComboBox();
        PPLPaidAmount = new javax.swing.JLabel();
        PPTFPaidAmount = new javax.swing.JTextField();
        PPLDues = new javax.swing.JLabel();
        PPTFDues = new javax.swing.JTextField();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        PPLGrossAmount = new javax.swing.JLabel();
        PPLProductBrand = new javax.swing.JLabel();
        PPCBProductBrand = new javax.swing.JComboBox();
        PPBAddProduct = new javax.swing.JButton();
        PPLMfgDate = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        PPTFQuantity = new javax.swing.JTextField();
        PPDCMfgDate = new com.toedter.calendar.JDateChooser();
        PPDCExpDate = new com.toedter.calendar.JDateChooser();
        PPLGST = new javax.swing.JLabel();
        PPTFGST = new javax.swing.JTextField();
        PPLEXPNotifyDate = new javax.swing.JLabel();
        PPDCEXPNotifyDate = new com.toedter.calendar.JDateChooser();
        jButton31 = new javax.swing.JButton();
        SellProduct = new javax.swing.JPanel();
        SPLAvailableQ = new javax.swing.JLabel();
        SPTFAvailableQ = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        SPLMobileNo = new javax.swing.JLabel();
        SPLPreDues = new javax.swing.JLabel();
        SPLInvoiceNo = new javax.swing.JLabel();
        SPLInvoiceDate = new javax.swing.JLabel();
        SPLAadharNo = new javax.swing.JLabel();
        SPLGSTNo = new javax.swing.JLabel();
        SPBAddCustomer = new javax.swing.JButton();
        SPCBCustomerName = new javax.swing.JComboBox();
        SPTFMobileNo = new javax.swing.JTextField();
        SPTFPreDues = new javax.swing.JTextField();
        SPTFINvoiceID = new javax.swing.JTextField();
        SPInvoiceDate = new javax.swing.JTextField();
        SPTFAadharNo = new javax.swing.JTextField();
        SPTFGSTINNo = new javax.swing.JTextField();
        SPLCustomerType = new javax.swing.JLabel();
        SPCBCustomerType = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        SPLTotalAmount = new javax.swing.JLabel();
        SPTFTotalAmount = new javax.swing.JTextField();
        SPLDiscount = new javax.swing.JLabel();
        LGrossAmount = new javax.swing.JLabel();
        SPTFDiscount = new javax.swing.JTextField();
        SPLNetAmount = new javax.swing.JLabel();
        SPTFNetAmount = new javax.swing.JTextField();
        SPLPaymentMode = new javax.swing.JLabel();
        SPCBPaymentMode = new javax.swing.JComboBox();
        SPLPaidAmount = new javax.swing.JLabel();
        SPTFPaidAmount = new javax.swing.JTextField();
        SPLDues = new javax.swing.JLabel();
        SPTFDues = new javax.swing.JTextField();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        SPLGrossAmount = new javax.swing.JLabel();
        SPLProductCategory = new javax.swing.JLabel();
        SPLProductName = new javax.swing.JLabel();
        SPLQuantity = new javax.swing.JLabel();
        SPLUOM = new javax.swing.JLabel();
        SPLSize = new javax.swing.JLabel();
        SPCBProductCategory = new javax.swing.JComboBox();
        SPCBProductName = new javax.swing.JComboBox();
        SPCBUOM = new javax.swing.JComboBox();
        SPCBSize = new javax.swing.JComboBox();
        jButton4 = new javax.swing.JButton();
        SPLProductBrand = new javax.swing.JLabel();
        SPCBProductBrand = new javax.swing.JComboBox();
        jButton36 = new javax.swing.JButton();
        SPTFQuantity = new javax.swing.JTextField();
        SPLGST = new javax.swing.JLabel();
        SPTFGST = new javax.swing.JTextField();
        jButton37 = new javax.swing.JButton();
        SPLSellingPrice = new javax.swing.JLabel();
        SPTFSellingPrice = new javax.swing.JTextField();
        ProfileUpdate = new javax.swing.JPanel();
        PULCurrentPassword = new javax.swing.JLabel();
        PULAdminID = new javax.swing.JLabel();
        PUTFAdminID = new javax.swing.JTextField();
        PUTFCurrentPassword = new javax.swing.JPasswordField();
        PUTFNewPassword = new javax.swing.JPasswordField();
        PULNewPassword = new javax.swing.JLabel();
        PULConfirmPassword = new javax.swing.JLabel();
        PUTFConfirmPassword = new javax.swing.JPasswordField();
        jButton29 = new javax.swing.JButton();
        PULContactNo = new javax.swing.JLabel();
        PULShopName = new javax.swing.JLabel();
        PULAddress = new javax.swing.JLabel();
        PULNear = new javax.swing.JLabel();
        PULCity = new javax.swing.JLabel();
        PULPinCode = new javax.swing.JLabel();
        PUTFContactNo = new javax.swing.JTextField();
        PUTFShopName = new javax.swing.JTextField();
        PUTFAddress = new javax.swing.JTextField();
        PUTFNear = new javax.swing.JTextField();
        PUTFCity = new javax.swing.JTextField();
        PUTFPinCode = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        PULImage = new javax.swing.JLabel();
        PUTFImage = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        PUTFTermsAndCondition = new javax.swing.JTextField();
        jButton43 = new javax.swing.JButton();
        PUBAdd = new javax.swing.JButton();
        PUTFWebSite = new javax.swing.JTextField();
        PULWebSite = new javax.swing.JLabel();
        PUBUpdate = new javax.swing.JButton();
        PUBAdd2 = new javax.swing.JButton();
        PULogoWidth = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        PULogoPosition = new javax.swing.JSpinner();
        ProductEntery = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        PELProductImages = new javax.swing.JLabel();
        PELProductCategory = new javax.swing.JLabel();
        PELProductName = new javax.swing.JLabel();
        PELSize = new javax.swing.JLabel();
        PELUOM = new javax.swing.JLabel();
        PECBProductCategory = new javax.swing.JComboBox();
        PECBProductName = new javax.swing.JComboBox();
        PECBSize = new javax.swing.JComboBox();
        PECBUOM = new javax.swing.JComboBox();
        PELSellingPrice = new javax.swing.JLabel();
        PETFSellingPrice = new javax.swing.JTextField();
        PELDiscription = new javax.swing.JLabel();
        PELProductBrand = new javax.swing.JLabel();
        PECBProductBrand = new javax.swing.JComboBox();
        PELBarcode = new javax.swing.JLabel();
        PETFBarcode = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        PETADiscription = new javax.swing.JTextArea();
        PELImage = new javax.swing.JLabel();
        PETFImage = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        ViewSupplier34 = new javax.swing.JPanel();
        Home = new javax.swing.JPanel();
        jPanelSlider1 = new diu.swe.habib.JPanelSlider.JPanelSlider();
        StockandExpNotify = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        HTStock = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        HRBCurrent = new javax.swing.JRadioButton();
        HRBBackup = new javax.swing.JRadioButton();
        HRBSortByExpDate = new javax.swing.JRadioButton();
        DBLProductImages = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        ProfitDetails = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DBTMonthSoldProduct = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        DBLMonthPofitRupee = new javax.swing.JLabel();
        DBLMonthPofitPercent = new javax.swing.JLabel();
        DBMCMonthProfit = new com.toedter.calendar.JMonthChooser();
        DBYCYearProfit = new com.toedter.calendar.JYearChooser();
        DBLYearPofitPercent = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        DBLYearPofitRupee = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        PurchaseReport = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        PurRDCFrom = new com.toedter.calendar.JDateChooser();
        PurRDCTo = new com.toedter.calendar.JDateChooser();
        PurRCBSupplierNames = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jButton35 = new javax.swing.JButton();
        TransactionReport = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jButton48 = new javax.swing.JButton();
        TRDCFrom = new com.toedter.calendar.JDateChooser();
        TRDCTo = new com.toedter.calendar.JDateChooser();
        jButton49 = new javax.swing.JButton();
        EmployeePermission = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        EPCBEmpName = new javax.swing.JComboBox();
        EPCBSetting = new javax.swing.JCheckBox();
        EPCBHome = new javax.swing.JCheckBox();
        EPCBProductCategory = new javax.swing.JCheckBox();
        EPCBCustomer = new javax.swing.JCheckBox();
        EPCBSupplier = new javax.swing.JCheckBox();
        EPCBInventory = new javax.swing.JCheckBox();
        EPCBStock = new javax.swing.JCheckBox();
        EPCBReport = new javax.swing.JCheckBox();
        EPCBProductMaster = new javax.swing.JCheckBox();
        EPCBPendingReport = new javax.swing.JCheckBox();
        EPCBEmployee = new javax.swing.JCheckBox();
        EPCBViewProduct = new javax.swing.JCheckBox();
        EPCBAddCustomer = new javax.swing.JCheckBox();
        EPCBAddSupplier = new javax.swing.JCheckBox();
        EPCBViewCustomers = new javax.swing.JCheckBox();
        EPCBViewSuppliers = new javax.swing.JCheckBox();
        EPCBProductMasterSub = new javax.swing.JCheckBox();
        EPCBPurchaseProduct = new javax.swing.JCheckBox();
        EPCBSellProduct = new javax.swing.JCheckBox();
        EPCBAddProduct = new javax.swing.JCheckBox();
        EPCBPurchaseReport = new javax.swing.JCheckBox();
        EPCBSellReport = new javax.swing.JCheckBox();
        EPCBPendingReportSub = new javax.swing.JCheckBox();
        EPCBTransactionReport = new javax.swing.JCheckBox();
        EPCBNewPerson = new javax.swing.JCheckBox();
        EPCBStockIn = new javax.swing.JCheckBox();
        EPCBNewEmployee = new javax.swing.JCheckBox();
        EPCBEmpAuthentication = new javax.swing.JCheckBox();
        EPCBEmpPermission = new javax.swing.JCheckBox();
        EPCBProfileUpdate = new javax.swing.JCheckBox();
        EPCBBackup = new javax.swing.JCheckBox();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        EPLSelectedEmp = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jButton42 = new javax.swing.JButton();
        EmployeeAuthentication = new javax.swing.JPanel();
        EACBEmpName = new javax.swing.JComboBox();
        jLabel39 = new javax.swing.JLabel();
        jButton39 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        PendingReport = new javax.swing.JPanel();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        PRRBCS = new javax.swing.JRadioButton();
        PRRBNP = new javax.swing.JRadioButton();
        PersonReceive = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        PRCBPersonToReceive = new javax.swing.JComboBox();
        jButton40 = new javax.swing.JButton();
        PersonPay = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        PRCBPersonToPay = new javax.swing.JComboBox();
        jButton38 = new javax.swing.JButton();
        Receive = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        PRCBCustomerName = new javax.swing.JComboBox();
        jButton28 = new javax.swing.JButton();
        Pay = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        PRCBSupplierName = new javax.swing.JComboBox();
        jButton27 = new javax.swing.JButton();
        StockIn = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        SStockTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        SCBProductCategory = new javax.swing.JComboBox();
        jLabel90 = new javax.swing.JLabel();
        SCBProductBrand = new javax.swing.JComboBox();
        jLabel91 = new javax.swing.JLabel();
        STFQuantity = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        SCBProductName = new javax.swing.JComboBox();
        jLabel104 = new javax.swing.JLabel();
        SRBCurrentStock = new javax.swing.JRadioButton();
        SRBBackupStock = new javax.swing.JRadioButton();
        SRBBoth = new javax.swing.JRadioButton();
        SellReport = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jButton26 = new javax.swing.JButton();
        SellRDCFrom = new com.toedter.calendar.JDateChooser();
        SellRDCTo = new com.toedter.calendar.JDateChooser();
        SellRCBCustomerNames = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        SPLCustomerType1 = new javax.swing.JLabel();
        SellRCBCustomerType = new javax.swing.JComboBox();
        jButton32 = new javax.swing.JButton();
        ViewSupplier = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        VCCBSupplierName = new javax.swing.JComboBox();
        jButton20 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventory System");
        setBackground(new java.awt.Color(255, 51, 0));
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(700, 400));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jScrollBar1.setBlockIncrement(20);
        jScrollBar1.setMaximum(45);
        jScrollBar1.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        jScrollBar1.setToolTipText("");
        jScrollBar1.setUnitIncrement(10);
        jScrollBar1.setVisibleAmount(5);
        jScrollBar1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                jScrollBar1AdjustmentValueChanged(evt);
            }
        });
        getContentPane().add(jScrollBar1);
        jScrollBar1.setBounds(250, 730, 1090, 20);

        jScrollBar2.setMaximum(65);
        jScrollBar2.setUnitIncrement(10);
        jScrollBar2.setVisibleAmount(5);
        jScrollBar2.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jScrollBar2MouseWheelMoved(evt);
            }
        });
        jScrollBar2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                jScrollBar2AdjustmentValueChanged(evt);
            }
        });
        getContentPane().add(jScrollBar2);
        jScrollBar2.setBounds(1370, 0, 20, 730);

        Features.setBackground(new java.awt.Color(32, 37, 45));
        Features.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(25, 45, 55));
        jPanel7.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-circled-user-male-skin-type-5-60.png"))); // NOI18N
        jPanel7.add(jLabel1);
        jLabel1.setBounds(10, 30, 70, 60);

        DBLAccountUserName.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        DBLAccountUserName.setForeground(new java.awt.Color(255, 255, 255));
        DBLAccountUserName.setText("Aman Manmode");
        jPanel7.add(DBLAccountUserName);
        DBLAccountUserName.setBounds(80, 60, 130, 20);

        DBLAccountType.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        DBLAccountType.setForeground(new java.awt.Color(255, 255, 102));
        DBLAccountType.setText("Admin");
        jPanel7.add(DBLAccountType);
        DBLAccountType.setBounds(80, 40, 100, 20);

        jLabel111.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-shutdown-20.png"))); // NOI18N
        jLabel111.setText(" Logout");
        jLabel111.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel111.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel111MousePressed(evt);
            }
        });
        jPanel7.add(jLabel111);
        jLabel111.setBounds(150, 90, 100, 30);

        Features.add(jPanel7);
        jPanel7.setBounds(0, 0, 250, 120);

        DBLHome.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLHome.setForeground(new java.awt.Color(240, 240, 240));
        DBLHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-home-20.png"))); // NOI18N
        DBLHome.setText("   Home");
        DBLHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLHomeMousePressed(evt);
            }
        });
        Features.add(DBLHome);
        DBLHome.setBounds(10, 140, 90, 21);

        DBLProductCategory.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLProductCategory.setForeground(new java.awt.Color(240, 240, 240));
        DBLProductCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-new-product-20.png"))); // NOI18N
        DBLProductCategory.setText("   Product Category");
        DBLProductCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLProductCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLProductCategoryMousePressed(evt);
            }
        });
        Features.add(DBLProductCategory);
        DBLProductCategory.setBounds(10, 180, 180, 21);

        DBLCustomer.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLCustomer.setForeground(new java.awt.Color(240, 240, 240));
        DBLCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-customer-20 (2).png"))); // NOI18N
        DBLCustomer.setText("   Customer");
        DBLCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLCustomerMousePressed(evt);
            }
        });
        Features.add(DBLCustomer);
        DBLCustomer.setBounds(10, 220, 120, 21);

        DBLSupplier.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLSupplier.setForeground(new java.awt.Color(240, 240, 240));
        DBLSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-supplier-20.png"))); // NOI18N
        DBLSupplier.setText("   Supplier");
        DBLSupplier.setToolTipText("");
        DBLSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLSupplierMousePressed(evt);
            }
        });
        Features.add(DBLSupplier);
        DBLSupplier.setBounds(10, 260, 110, 21);

        DBLInventory.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLInventory.setForeground(new java.awt.Color(240, 240, 240));
        DBLInventory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-warehouse-20 (1).png"))); // NOI18N
        DBLInventory.setText("   Inventory");
        DBLInventory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLInventoryMousePressed(evt);
            }
        });
        Features.add(DBLInventory);
        DBLInventory.setBounds(10, 300, 120, 21);

        DBLStock.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLStock.setForeground(new java.awt.Color(240, 240, 240));
        DBLStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-sell-stock-filled-20.png"))); // NOI18N
        DBLStock.setText("   Stock");
        DBLStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLStockMousePressed(evt);
            }
        });
        Features.add(DBLStock);
        DBLStock.setBounds(10, 380, 120, 21);

        DBLReport.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLReport.setForeground(new java.awt.Color(240, 240, 240));
        DBLReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-edit-graph-report-20.png"))); // NOI18N
        DBLReport.setText("   Report");
        DBLReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLReportMousePressed(evt);
            }
        });
        Features.add(DBLReport);
        DBLReport.setBounds(10, 420, 100, 21);

        DBLPendingReport.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLPendingReport.setForeground(new java.awt.Color(240, 240, 240));
        DBLPendingReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-data-pending-filled-20.png"))); // NOI18N
        DBLPendingReport.setText("   Pending");
        DBLPendingReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLPendingReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLPendingReportMousePressed(evt);
            }
        });
        Features.add(DBLPendingReport);
        DBLPendingReport.setBounds(10, 460, 110, 21);

        DBLSetting.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLSetting.setForeground(new java.awt.Color(240, 240, 240));
        DBLSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-services-20.png"))); // NOI18N
        DBLSetting.setText("   Setting");
        DBLSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLSettingMousePressed(evt);
            }
        });
        Features.add(DBLSetting);
        DBLSetting.setBounds(10, 540, 100, 21);

        DBLProductMaster.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLProductMaster.setForeground(new java.awt.Color(255, 255, 255));
        DBLProductMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-master-20.png"))); // NOI18N
        DBLProductMaster.setText("   Masters");
        DBLProductMaster.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLProductMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLProductMasterMousePressed(evt);
            }
        });
        Features.add(DBLProductMaster);
        DBLProductMaster.setBounds(10, 340, 100, 21);

        DBLEmployee.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBLEmployee.setForeground(new java.awt.Color(240, 240, 240));
        DBLEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-profile-20.png"))); // NOI18N
        DBLEmployee.setText("   Employee");
        DBLEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBLEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBLEmployeeMousePressed(evt);
            }
        });
        Features.add(DBLEmployee);
        DBLEmployee.setBounds(10, 500, 130, 21);

        SubF10.setBackground(new java.awt.Color(54, 65, 79));
        SubF10.setLayout(null);

        DBSLBackup.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBSLBackup.setForeground(new java.awt.Color(255, 255, 255));
        DBSLBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-save-20.png"))); // NOI18N
        DBSLBackup.setText(" Backup");
        DBSLBackup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBSLBackup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBSLBackupMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DBSLBackupMouseReleased(evt);
            }
        });
        SubF10.add(DBSLBackup);
        DBSLBackup.setBounds(40, 50, 100, 30);

        DBSLProfileUpdate.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBSLProfileUpdate.setForeground(new java.awt.Color(255, 255, 255));
        DBSLProfileUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-20.png"))); // NOI18N
        DBSLProfileUpdate.setText(" Profile Update");
        DBSLProfileUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBSLProfileUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBSLProfileUpdateMousePressed(evt);
            }
        });
        SubF10.add(DBSLProfileUpdate);
        DBSLProfileUpdate.setBounds(40, 10, 150, 30);

        Features.add(SubF10);
        SubF10.setBounds(0, 570, 250, 160);

        SubF8.setBackground(new java.awt.Color(54, 65, 79));
        SubF8.setLayout(null);

        DBELEmpPermission.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBELEmpPermission.setForeground(new java.awt.Color(255, 255, 255));
        DBELEmpPermission.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-name-tag-20.png"))); // NOI18N
        DBELEmpPermission.setText(" Emp Permissions");
        DBELEmpPermission.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBELEmpPermission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBELEmpPermissionMousePressed(evt);
            }
        });
        SubF8.add(DBELEmpPermission);
        DBELEmpPermission.setBounds(40, 90, 180, 30);

        DBELNewEmployee.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBELNewEmployee.setForeground(new java.awt.Color(255, 255, 255));
        DBELNewEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-name-tag-20.png"))); // NOI18N
        DBELNewEmployee.setText(" New Employee");
        DBELNewEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBELNewEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBELNewEmployeeMousePressed(evt);
            }
        });
        SubF8.add(DBELNewEmployee);
        DBELNewEmployee.setBounds(40, 10, 150, 30);

        DBELEmpAuthentication.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBELEmpAuthentication.setForeground(new java.awt.Color(255, 255, 255));
        DBELEmpAuthentication.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-name-tag-20.png"))); // NOI18N
        DBELEmpAuthentication.setText(" Emp Authentication");
        DBELEmpAuthentication.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBELEmpAuthentication.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBELEmpAuthenticationMousePressed(evt);
            }
        });
        SubF8.add(DBELEmpAuthentication);
        DBELEmpAuthentication.setBounds(40, 50, 200, 30);

        Features.add(SubF8);
        SubF8.setBounds(0, 530, 250, 200);

        SubF7.setBackground(new java.awt.Color(54, 65, 79));
        SubF7.setLayout(null);

        DBPRLPendingReporet.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBPRLPendingReporet.setForeground(new java.awt.Color(255, 255, 255));
        DBPRLPendingReporet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-invoice-filled-20.png"))); // NOI18N
        DBPRLPendingReporet.setText(" Pending Report");
        DBPRLPendingReporet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBPRLPendingReporet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBPRLPendingReporetMousePressed(evt);
            }
        });
        SubF7.add(DBPRLPendingReporet);
        DBPRLPendingReporet.setBounds(40, 10, 160, 30);

        DBPRLNewPerson.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBPRLNewPerson.setForeground(new java.awt.Color(255, 255, 255));
        DBPRLNewPerson.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-user-20 (1).png"))); // NOI18N
        DBPRLNewPerson.setText(" New Person Entry");
        DBPRLNewPerson.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBPRLNewPerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBPRLNewPersonMousePressed(evt);
            }
        });
        SubF7.add(DBPRLNewPerson);
        DBPRLNewPerson.setBounds(40, 50, 180, 30);

        Features.add(SubF7);
        SubF7.setBounds(0, 490, 250, 240);

        SubF6.setBackground(new java.awt.Color(54, 65, 79));
        SubF6.setLayout(null);

        DBRLPurchaseReport.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBRLPurchaseReport.setForeground(new java.awt.Color(255, 255, 255));
        DBRLPurchaseReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-buy-20.png"))); // NOI18N
        DBRLPurchaseReport.setText(" Purchase Report");
        DBRLPurchaseReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBRLPurchaseReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBRLPurchaseReportMousePressed(evt);
            }
        });
        SubF6.add(DBRLPurchaseReport);
        DBRLPurchaseReport.setBounds(40, 10, 170, 30);

        DBRLSellReport.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBRLSellReport.setForeground(new java.awt.Color(255, 255, 255));
        DBRLSellReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-sell-filled-20.png"))); // NOI18N
        DBRLSellReport.setText(" Sell Report");
        DBRLSellReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBRLSellReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBRLSellReportMousePressed(evt);
            }
        });
        SubF6.add(DBRLSellReport);
        DBRLSellReport.setBounds(40, 50, 130, 30);

        DBRLTransactionReport.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBRLTransactionReport.setForeground(new java.awt.Color(255, 255, 255));
        DBRLTransactionReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-sell-filled-20.png"))); // NOI18N
        DBRLTransactionReport.setText(" Transaction Report");
        DBRLTransactionReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBRLTransactionReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBRLTransactionReportMousePressed(evt);
            }
        });
        SubF6.add(DBRLTransactionReport);
        DBRLTransactionReport.setBounds(40, 90, 190, 30);

        Features.add(SubF6);
        SubF6.setBounds(0, 450, 250, 280);

        SubF5.setBackground(new java.awt.Color(54, 65, 79));
        SubF5.setLayout(null);

        DBSLStockIn.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBSLStockIn.setForeground(new java.awt.Color(255, 255, 255));
        DBSLStockIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-move-stock-20.png"))); // NOI18N
        DBSLStockIn.setText(" Stock In");
        DBSLStockIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBSLStockIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBSLStockInMousePressed(evt);
            }
        });
        SubF5.add(DBSLStockIn);
        DBSLStockIn.setBounds(40, 10, 100, 30);

        Features.add(SubF5);
        SubF5.setBounds(0, 410, 250, 320);

        SubF9.setBackground(new java.awt.Color(54, 65, 79));
        SubF9.setLayout(null);

        DBPMLProductMaster.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBPMLProductMaster.setForeground(new java.awt.Color(255, 255, 255));
        DBPMLProductMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-master-20 (1).png"))); // NOI18N
        DBPMLProductMaster.setText(" Product Master");
        DBPMLProductMaster.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBPMLProductMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBPMLProductMasterMousePressed(evt);
            }
        });
        SubF9.add(DBPMLProductMaster);
        DBPMLProductMaster.setBounds(40, 10, 160, 30);

        Features.add(SubF9);
        SubF9.setBounds(0, 370, 250, 360);

        SubF4.setBackground(new java.awt.Color(54, 65, 79));
        SubF4.setLayout(null);

        DBILPurchaseProduct.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBILPurchaseProduct.setForeground(new java.awt.Color(255, 255, 255));
        DBILPurchaseProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-purchase-order-filled-20.png"))); // NOI18N
        DBILPurchaseProduct.setText(" Purchase");
        DBILPurchaseProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBILPurchaseProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBILPurchaseProductMousePressed(evt);
            }
        });
        SubF4.add(DBILPurchaseProduct);
        DBILPurchaseProduct.setBounds(40, 90, 110, 30);

        DBILAddProduct.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBILAddProduct.setForeground(new java.awt.Color(255, 255, 255));
        DBILAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-add-new-20 (1).png"))); // NOI18N
        DBILAddProduct.setText(" Add Product");
        DBILAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBILAddProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBILAddProductMousePressed(evt);
            }
        });
        SubF4.add(DBILAddProduct);
        DBILAddProduct.setBounds(40, 10, 140, 30);

        DBILSellProduct.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBILSellProduct.setForeground(new java.awt.Color(255, 255, 255));
        DBILSellProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-sell-filled-20.png"))); // NOI18N
        DBILSellProduct.setText(" Sell");
        DBILSellProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBILSellProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBILSellProductMousePressed(evt);
            }
        });
        SubF4.add(DBILSellProduct);
        DBILSellProduct.setBounds(40, 50, 70, 30);

        Features.add(SubF4);
        SubF4.setBounds(0, 330, 250, 400);

        SubF3.setBackground(new java.awt.Color(54, 65, 79));
        SubF3.setLayout(null);

        DBSLViewSupplier.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBSLViewSupplier.setForeground(new java.awt.Color(240, 240, 240));
        DBSLViewSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-supplier-20 (2).png"))); // NOI18N
        DBSLViewSupplier.setText(" View Supplier");
        DBSLViewSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBSLViewSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBSLViewSupplierMousePressed(evt);
            }
        });
        SubF3.add(DBSLViewSupplier);
        DBSLViewSupplier.setBounds(40, 50, 150, 30);

        DBSLAddSupplier.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBSLAddSupplier.setForeground(new java.awt.Color(240, 240, 240));
        DBSLAddSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-add-new-20 (1).png"))); // NOI18N
        DBSLAddSupplier.setText(" Add Supplier");
        DBSLAddSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBSLAddSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBSLAddSupplierMousePressed(evt);
            }
        });
        SubF3.add(DBSLAddSupplier);
        DBSLAddSupplier.setBounds(40, 10, 140, 30);

        Features.add(SubF3);
        SubF3.setBounds(0, 290, 250, 440);

        SubF2.setBackground(new java.awt.Color(54, 65, 79));
        SubF2.setLayout(null);

        DBCLAddCustomer.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBCLAddCustomer.setForeground(new java.awt.Color(240, 240, 240));
        DBCLAddCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-add-new-20 (1).png"))); // NOI18N
        DBCLAddCustomer.setText(" Add Customer");
        DBCLAddCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBCLAddCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBCLAddCustomerMousePressed(evt);
            }
        });
        SubF2.add(DBCLAddCustomer);
        DBCLAddCustomer.setBounds(40, 10, 150, 30);

        DBCLViewCustomer.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBCLViewCustomer.setForeground(new java.awt.Color(240, 240, 240));
        DBCLViewCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-customer-20 (2).png"))); // NOI18N
        DBCLViewCustomer.setText(" View Cutomers");
        DBCLViewCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBCLViewCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBCLViewCustomerMousePressed(evt);
            }
        });
        SubF2.add(DBCLViewCustomer);
        DBCLViewCustomer.setBounds(40, 50, 160, 30);

        Features.add(SubF2);
        SubF2.setBounds(0, 250, 250, 480);

        SubF1.setBackground(new java.awt.Color(54, 65, 79));
        SubF1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        SubF1.setLayout(null);

        DBPCLAddProduct.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBPCLAddProduct.setForeground(new java.awt.Color(240, 240, 240));
        DBPCLAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-add-new-20 (1).png"))); // NOI18N
        DBPCLAddProduct.setText(" Add Product");
        DBPCLAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBPCLAddProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBPCLAddProductMousePressed(evt);
            }
        });
        SubF1.add(DBPCLAddProduct);
        DBPCLAddProduct.setBounds(40, 50, 130, 30);

        DBPCLViewProduct.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        DBPCLViewProduct.setForeground(new java.awt.Color(240, 240, 240));
        DBPCLViewProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-box-20.png"))); // NOI18N
        DBPCLViewProduct.setText(" View Products");
        DBPCLViewProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DBPCLViewProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DBPCLViewProductMousePressed(evt);
            }
        });
        SubF1.add(DBPCLViewProduct);
        DBPCLViewProduct.setBounds(40, 10, 150, 30);

        Features.add(SubF1);
        SubF1.setBounds(0, 210, 250, 520);

        getContentPane().add(Features);
        Features.setBounds(0, 0, 250, 730);

        jPanel1.setBackground(new java.awt.Color(33, 95, 133));
        jPanel1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jPanel1MouseWheelMoved(evt);
            }
        });
        jPanel1.setLayout(null);

        ViewCustomer.setBackground(new java.awt.Color(33, 95, 133));
        ViewCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Customer List", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        ViewCustomer.setLayout(null);

        jPanel11.setBackground(new java.awt.Color(33, 95, 133));
        jPanel11.setLayout(null);

        jLabel106.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setText("Search By :");
        jPanel11.add(jLabel106);
        jLabel106.setBounds(250, 20, 100, 30);

        VCCBCustomerName.setEditable(true);
        VCCBCustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VCCBCustomerNameActionPerformed(evt);
            }
        });
        jPanel11.add(VCCBCustomerName);
        VCCBCustomerName.setBounds(360, 20, 360, 30);

        jButton19.setBackground(new java.awt.Color(231, 76, 60));
        jButton19.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton19.setForeground(new java.awt.Color(51, 51, 51));
        jButton19.setText("Show All");
        jButton19.setRequestFocusEnabled(false);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton19);
        jButton19.setBounds(750, 20, 90, 30);

        ViewCustomer.add(jPanel11);
        jPanel11.setBounds(20, 40, 1080, 70);

        jLabel51.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 102));
        jLabel51.setText("Note :-  You Must Refresh the Page after Editing any Customer Details ");
        ViewCustomer.add(jLabel51);
        jLabel51.setBounds(10, 20, 440, 20);

        jPanel1.add(ViewCustomer);
        ViewCustomer.setBounds(10, 0, 1120, 720);

        ProductPurchase.setBackground(new java.awt.Color(33, 95, 133));
        ProductPurchase.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Purchase", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Georgia", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        ProductPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductPurchaseMouseClicked(evt);
            }
        });
        ProductPurchase.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(33, 95, 133));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setLayout(null);

        jLabel37.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Supplier Name :");
        jPanel2.add(jLabel37);
        jLabel37.setBounds(30, 10, 100, 30);

        jLabel38.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Mobile No :");
        jPanel2.add(jLabel38);
        jLabel38.setBounds(520, 10, 80, 30);

        jLabel45.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Pre Dues :");
        jPanel2.add(jLabel45);
        jLabel45.setBounds(800, 10, 70, 30);

        jLabel46.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Invoice no :");
        jPanel2.add(jLabel46);
        jLabel46.setBounds(50, 70, 80, 30);

        jLabel47.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Invoice Date :");
        jPanel2.add(jLabel47);
        jLabel47.setBounds(250, 70, 90, 30);

        jLabel48.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Aadhar no :");
        jPanel2.add(jLabel48);
        jLabel48.setBounds(520, 70, 90, 30);

        jLabel49.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("GST no :");
        jPanel2.add(jLabel49);
        jLabel49.setBounds(810, 70, 80, 30);

        PPBAddSupplier.setText("+");
        PPBAddSupplier.setRequestFocusEnabled(false);
        PPBAddSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPBAddSupplierActionPerformed(evt);
            }
        });
        jPanel2.add(PPBAddSupplier);
        PPBAddSupplier.setBounds(440, 10, 41, 30);

        PPCBSupplierName.setEditable(true);
        PPCBSupplierName.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPCBSupplierNameActionPerformed(evt);
            }
        });
        jPanel2.add(PPCBSupplierName);
        PPCBSupplierName.setBounds(140, 10, 290, 30);

        TFMobileNo.setEditable(false);
        TFMobileNo.setBackground(new java.awt.Color(255, 255, 255));
        TFMobileNo.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jPanel2.add(TFMobileNo);
        TFMobileNo.setBounds(610, 10, 160, 30);

        TFPreDues.setEditable(false);
        TFPreDues.setBackground(new java.awt.Color(255, 255, 255));
        TFPreDues.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jPanel2.add(TFPreDues);
        TFPreDues.setBounds(880, 10, 80, 30);

        PPTFINvoiceID.setEditable(false);
        PPTFINvoiceID.setBackground(new java.awt.Color(33, 95, 133));
        PPTFINvoiceID.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        PPTFINvoiceID.setForeground(new java.awt.Color(255, 102, 102));
        PPTFINvoiceID.setBorder(null);
        jPanel2.add(PPTFINvoiceID);
        PPTFINvoiceID.setBounds(140, 70, 80, 30);

        PPInvoiceDate.setEditable(false);
        PPInvoiceDate.setBackground(new java.awt.Color(33, 95, 133));
        PPInvoiceDate.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        PPInvoiceDate.setForeground(new java.awt.Color(255, 102, 102));
        PPInvoiceDate.setBorder(null);
        jPanel2.add(PPInvoiceDate);
        PPInvoiceDate.setBounds(360, 70, 120, 30);

        TFAadharNo.setEditable(false);
        TFAadharNo.setBackground(new java.awt.Color(255, 255, 255));
        TFAadharNo.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jPanel2.add(TFAadharNo);
        TFAadharNo.setBounds(610, 70, 160, 30);

        TFGSTINNo.setEditable(false);
        TFGSTINNo.setBackground(new java.awt.Color(255, 255, 255));
        TFGSTINNo.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        TFGSTINNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFGSTINNoActionPerformed(evt);
            }
        });
        jPanel2.add(TFGSTINNo);
        TFGSTINNo.setBounds(880, 70, 120, 30);

        ProductPurchase.add(jPanel2);
        jPanel2.setBounds(20, 40, 1080, 110);

        PPLProductCategory.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLProductCategory.setForeground(new java.awt.Color(255, 255, 255));
        PPLProductCategory.setText("Product Category ");
        ProductPurchase.add(PPLProductCategory);
        PPLProductCategory.setBounds(30, 160, 120, 20);

        PPLProductName.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLProductName.setForeground(new java.awt.Color(255, 255, 255));
        PPLProductName.setText("Product Name ");
        ProductPurchase.add(PPLProductName);
        PPLProductName.setBounds(200, 160, 110, 20);

        PPLExpDate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLExpDate.setForeground(new java.awt.Color(255, 255, 255));
        PPLExpDate.setText("Exp Date");
        ProductPurchase.add(PPLExpDate);
        PPLExpDate.setBounds(200, 220, 80, 20);

        PPLQuantity.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLQuantity.setForeground(new java.awt.Color(255, 255, 255));
        PPLQuantity.setText("Quantity ");
        ProductPurchase.add(PPLQuantity);
        PPLQuantity.setBounds(720, 160, 70, 20);

        PPLUOM.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLUOM.setForeground(new java.awt.Color(255, 255, 255));
        PPLUOM.setText("UOM ");
        ProductPurchase.add(PPLUOM);
        PPLUOM.setBounds(620, 160, 70, 20);

        PPLSize.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLSize.setForeground(new java.awt.Color(255, 255, 255));
        PPLSize.setText("Size ");
        ProductPurchase.add(PPLSize);
        PPLSize.setBounds(520, 160, 60, 20);

        PPLPurchaseRate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLPurchaseRate.setForeground(new java.awt.Color(255, 255, 255));
        PPLPurchaseRate.setText("Purchase Rate ");
        ProductPurchase.add(PPLPurchaseRate);
        PPLPurchaseRate.setBounds(960, 160, 110, 20);

        PPLSellingRate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLSellingRate.setForeground(new java.awt.Color(255, 255, 255));
        PPLSellingRate.setText("Selling Rate ");
        ProductPurchase.add(PPLSellingRate);
        PPLSellingRate.setBounds(820, 160, 110, 20);

        PPCBProductCategory.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBProductCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPCBProductCategoryActionPerformed(evt);
            }
        });
        ProductPurchase.add(PPCBProductCategory);
        PPCBProductCategory.setBounds(30, 180, 140, 30);

        PPCBProductName.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPCBProductNameActionPerformed(evt);
            }
        });
        ProductPurchase.add(PPCBProductName);
        PPCBProductName.setBounds(200, 180, 130, 30);

        PPCBUOM.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBUOM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPCBUOMActionPerformed(evt);
            }
        });
        ProductPurchase.add(PPCBUOM);
        PPCBUOM.setBounds(620, 180, 70, 30);

        PPCBSize.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPCBSizeActionPerformed(evt);
            }
        });
        ProductPurchase.add(PPCBSize);
        PPCBSize.setBounds(520, 180, 70, 30);

        PPTFSellingRate.setEditable(false);
        PPTFSellingRate.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        ProductPurchase.add(PPTFSellingRate);
        PPTFSellingRate.setBounds(820, 180, 110, 30);

        PPTFPurchaseRate.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPTFPurchaseRate.setToolTipText("Per Unit");
        ProductPurchase.add(PPTFPurchaseRate);
        PPTFPurchaseRate.setBounds(960, 180, 120, 30);

        jButton3.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jButton3.setText("ADD");
        jButton3.setRequestFocusEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        ProductPurchase.add(jButton3);
        jButton3.setBounds(620, 240, 70, 30);

        jPanel3.setBackground(new java.awt.Color(33, 95, 133));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setLayout(null);

        jLabel58.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Total Amount");
        jPanel3.add(jLabel58);
        jLabel58.setBounds(190, 10, 90, 20);

        PPTFTotalAmount.setEditable(false);
        PPTFTotalAmount.setBackground(new java.awt.Color(255, 255, 255));
        PPTFTotalAmount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PPTFTotalAmount.setText("00.0");
        jPanel3.add(PPTFTotalAmount);
        PPTFTotalAmount.setBounds(190, 30, 120, 30);

        PPLDiscount.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLDiscount.setForeground(new java.awt.Color(255, 255, 255));
        PPLDiscount.setText("Disc(%)");
        PPLDiscount.setToolTipText("");
        jPanel3.add(PPLDiscount);
        PPLDiscount.setBounds(350, 10, 60, 20);

        jLabel61.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Gross Amount :");
        jPanel3.add(jLabel61);
        jLabel61.setBounds(40, 80, 130, 30);

        PPTFDiscount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PPTFDiscount.setText("0");
        PPTFDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PPTFDiscountKeyReleased(evt);
            }
        });
        jPanel3.add(PPTFDiscount);
        PPTFDiscount.setBounds(350, 30, 70, 30);

        jLabel62.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Net Amount ");
        jPanel3.add(jLabel62);
        jLabel62.setBounds(40, 10, 90, 20);

        PPTFNetAmount.setEditable(false);
        PPTFNetAmount.setBackground(new java.awt.Color(255, 255, 255));
        PPTFNetAmount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PPTFNetAmount.setText("00.0");
        jPanel3.add(PPTFNetAmount);
        PPTFNetAmount.setBounds(40, 30, 110, 30);

        jLabel63.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Payment Mode");
        jPanel3.add(jLabel63);
        jLabel63.setBounds(610, 10, 100, 20);

        PPCBPaymentMode.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBPaymentMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Cheque" }));
        PPCBPaymentMode.setSelectedIndex(-1);
        PPCBPaymentMode.setToolTipText("");
        jPanel3.add(PPCBPaymentMode);
        PPCBPaymentMode.setBounds(610, 30, 130, 30);

        PPLPaidAmount.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLPaidAmount.setForeground(new java.awt.Color(255, 255, 255));
        PPLPaidAmount.setText("Piad Amount");
        jPanel3.add(PPLPaidAmount);
        PPLPaidAmount.setBounds(780, 10, 120, 20);

        PPTFPaidAmount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PPTFPaidAmount.setText("0");
        PPTFPaidAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PPTFPaidAmountKeyReleased(evt);
            }
        });
        jPanel3.add(PPTFPaidAmount);
        PPTFPaidAmount.setBounds(780, 30, 110, 30);

        PPLDues.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLDues.setForeground(new java.awt.Color(255, 255, 255));
        PPLDues.setText("Dues");
        jPanel3.add(PPLDues);
        PPLDues.setBounds(930, 10, 31, 17);

        PPTFDues.setEditable(false);
        PPTFDues.setBackground(new java.awt.Color(255, 255, 255));
        PPTFDues.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PPTFDues.setText("00.0");
        jPanel3.add(PPTFDues);
        PPTFDues.setBounds(930, 30, 110, 30);

        jButton21.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton21.setText("Save");
        jButton21.setRequestFocusEnabled(false);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton21);
        jButton21.setBounds(740, 80, 90, 30);

        jButton22.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton22.setText("Recipt");
        jButton22.setRequestFocusEnabled(false);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton22);
        jButton22.setBounds(610, 80, 100, 30);

        PPLGrossAmount.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        PPLGrossAmount.setForeground(new java.awt.Color(255, 102, 102));
        PPLGrossAmount.setText("00.0");
        PPLGrossAmount.setToolTipText("");
        jPanel3.add(PPLGrossAmount);
        PPLGrossAmount.setBounds(180, 80, 120, 30);

        ProductPurchase.add(jPanel3);
        jPanel3.setBounds(20, 590, 1080, 120);

        PPLProductBrand.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLProductBrand.setForeground(new java.awt.Color(255, 255, 255));
        PPLProductBrand.setText("Product Brand ");
        ProductPurchase.add(PPLProductBrand);
        PPLProductBrand.setBounds(360, 160, 110, 20);

        PPCBProductBrand.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBProductBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPCBProductBrandActionPerformed(evt);
            }
        });
        ProductPurchase.add(PPCBProductBrand);
        PPCBProductBrand.setBounds(360, 180, 130, 30);

        PPBAddProduct.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPBAddProduct.setText("New Product");
        PPBAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPBAddProductActionPerformed(evt);
            }
        });
        ProductPurchase.add(PPBAddProduct);
        PPBAddProduct.setBounds(960, 240, 110, 30);

        PPLMfgDate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLMfgDate.setForeground(new java.awt.Color(255, 255, 255));
        PPLMfgDate.setText("Mfg Date");
        ProductPurchase.add(PPLMfgDate);
        PPLMfgDate.setBounds(30, 220, 80, 20);

        jButton30.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jButton30.setText("Clear");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        ProductPurchase.add(jButton30);
        jButton30.setBounds(720, 240, 70, 30);

        PPTFQuantity.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        ProductPurchase.add(PPTFQuantity);
        PPTFQuantity.setBounds(720, 180, 70, 30);

        PPDCMfgDate.setDateFormatString("dd-MM-yyyy");
        ProductPurchase.add(PPDCMfgDate);
        PPDCMfgDate.setBounds(30, 240, 140, 30);

        PPDCExpDate.setDateFormatString("dd-MM-yyyy");
        PPDCExpDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                PPDCExpDatePropertyChange(evt);
            }
        });
        ProductPurchase.add(PPDCExpDate);
        PPDCExpDate.setBounds(200, 240, 130, 30);

        PPLGST.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLGST.setForeground(new java.awt.Color(255, 255, 255));
        PPLGST.setText("GST(%)");
        ProductPurchase.add(PPLGST);
        PPLGST.setBounds(360, 220, 80, 20);

        PPTFGST.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPTFGST.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PPTFGSTKeyReleased(evt);
            }
        });
        ProductPurchase.add(PPTFGST);
        PPTFGST.setBounds(360, 240, 70, 30);

        PPLEXPNotifyDate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLEXPNotifyDate.setForeground(new java.awt.Color(255, 255, 255));
        PPLEXPNotifyDate.setText("Exp Notify Date :");
        ProductPurchase.add(PPLEXPNotifyDate);
        PPLEXPNotifyDate.setBounds(460, 220, 110, 20);

        PPDCEXPNotifyDate.setDateFormatString("dd-MM-yyyy");
        PPDCEXPNotifyDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                PPDCEXPNotifyDatePropertyChange(evt);
            }
        });
        ProductPurchase.add(PPDCEXPNotifyDate);
        PPDCEXPNotifyDate.setBounds(460, 240, 130, 30);

        jButton31.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jButton31.setText("Refresh");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        ProductPurchase.add(jButton31);
        jButton31.setBounds(820, 240, 80, 30);

        jPanel1.add(ProductPurchase);
        ProductPurchase.setBounds(0, 0, 1120, 720);

        SellProduct.setBackground(new java.awt.Color(33, 95, 133));
        SellProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sell Product", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Georgia", 0, 24), new java.awt.Color(102, 255, 255))); // NOI18N
        SellProduct.setLayout(null);

        SPLAvailableQ.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLAvailableQ.setForeground(new java.awt.Color(51, 255, 51));
        SPLAvailableQ.setText("Avl quantity");
        SellProduct.add(SPLAvailableQ);
        SPLAvailableQ.setBounds(130, 220, 90, 20);

        SPTFAvailableQ.setEditable(false);
        SPTFAvailableQ.setBackground(new java.awt.Color(255, 255, 255));
        SPTFAvailableQ.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        SellProduct.add(SPTFAvailableQ);
        SPTFAvailableQ.setBounds(130, 240, 100, 30);

        jPanel4.setBackground(new java.awt.Color(33, 95, 133));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setLayout(null);

        jLabel50.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Customer Name :");
        jPanel4.add(jLabel50);
        jLabel50.setBounds(208, 0, 110, 20);

        SPLMobileNo.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLMobileNo.setForeground(new java.awt.Color(255, 255, 255));
        SPLMobileNo.setText("Mobile No :");
        jPanel4.add(SPLMobileNo);
        SPLMobileNo.setBounds(520, 20, 80, 30);

        SPLPreDues.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLPreDues.setForeground(new java.awt.Color(255, 255, 255));
        SPLPreDues.setText("Pre Dues :");
        jPanel4.add(SPLPreDues);
        SPLPreDues.setBounds(800, 20, 70, 30);

        SPLInvoiceNo.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLInvoiceNo.setForeground(new java.awt.Color(255, 255, 255));
        SPLInvoiceNo.setText("Invoice no :");
        jPanel4.add(SPLInvoiceNo);
        SPLInvoiceNo.setBounds(20, 70, 80, 30);

        SPLInvoiceDate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLInvoiceDate.setForeground(new java.awt.Color(255, 255, 255));
        SPLInvoiceDate.setText("Invoice Date :");
        jPanel4.add(SPLInvoiceDate);
        SPLInvoiceDate.setBounds(250, 70, 90, 30);

        SPLAadharNo.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLAadharNo.setForeground(new java.awt.Color(255, 255, 255));
        SPLAadharNo.setText("Aadhar no :");
        jPanel4.add(SPLAadharNo);
        SPLAadharNo.setBounds(520, 70, 80, 30);

        SPLGSTNo.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLGSTNo.setForeground(new java.awt.Color(255, 255, 255));
        SPLGSTNo.setText("GST no :");
        jPanel4.add(SPLGSTNo);
        SPLGSTNo.setBounds(810, 70, 60, 30);

        SPBAddCustomer.setText("+");
        SPBAddCustomer.setRequestFocusEnabled(false);
        SPBAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPBAddCustomerActionPerformed(evt);
            }
        });
        jPanel4.add(SPBAddCustomer);
        SPBAddCustomer.setBounds(430, 20, 41, 30);

        SPCBCustomerName.setEditable(true);
        SPCBCustomerName.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBCustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPCBCustomerNameActionPerformed(evt);
            }
        });
        jPanel4.add(SPCBCustomerName);
        SPCBCustomerName.setBounds(210, 20, 210, 30);

        SPTFMobileNo.setEditable(false);
        SPTFMobileNo.setBackground(new java.awt.Color(255, 255, 255));
        SPTFMobileNo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        SPTFMobileNo.setForeground(new java.awt.Color(51, 51, 255));
        jPanel4.add(SPTFMobileNo);
        SPTFMobileNo.setBounds(610, 20, 160, 30);

        SPTFPreDues.setEditable(false);
        SPTFPreDues.setBackground(new java.awt.Color(255, 255, 255));
        SPTFPreDues.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        SPTFPreDues.setForeground(new java.awt.Color(51, 51, 255));
        jPanel4.add(SPTFPreDues);
        SPTFPreDues.setBounds(880, 20, 80, 30);

        SPTFINvoiceID.setEditable(false);
        SPTFINvoiceID.setBackground(new java.awt.Color(33, 95, 133));
        SPTFINvoiceID.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        SPTFINvoiceID.setForeground(new java.awt.Color(255, 102, 102));
        SPTFINvoiceID.setBorder(null);
        jPanel4.add(SPTFINvoiceID);
        SPTFINvoiceID.setBounds(110, 70, 110, 30);

        SPInvoiceDate.setEditable(false);
        SPInvoiceDate.setBackground(new java.awt.Color(33, 95, 133));
        SPInvoiceDate.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        SPInvoiceDate.setForeground(new java.awt.Color(255, 102, 102));
        SPInvoiceDate.setBorder(null);
        jPanel4.add(SPInvoiceDate);
        SPInvoiceDate.setBounds(350, 70, 120, 30);

        SPTFAadharNo.setEditable(false);
        SPTFAadharNo.setBackground(new java.awt.Color(255, 255, 255));
        SPTFAadharNo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        SPTFAadharNo.setForeground(new java.awt.Color(51, 51, 255));
        jPanel4.add(SPTFAadharNo);
        SPTFAadharNo.setBounds(610, 70, 160, 30);

        SPTFGSTINNo.setEditable(false);
        SPTFGSTINNo.setBackground(new java.awt.Color(255, 255, 255));
        SPTFGSTINNo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        SPTFGSTINNo.setForeground(new java.awt.Color(51, 51, 255));
        SPTFGSTINNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPTFGSTINNoActionPerformed(evt);
            }
        });
        jPanel4.add(SPTFGSTINNo);
        SPTFGSTINNo.setBounds(880, 70, 180, 30);

        SPLCustomerType.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLCustomerType.setForeground(new java.awt.Color(255, 255, 255));
        SPLCustomerType.setText("Customer Type :");
        jPanel4.add(SPLCustomerType);
        SPLCustomerType.setBounds(20, 0, 104, 20);

        SPCBCustomerType.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBCustomerType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Retail", "Distributor", "Loyal" }));
        SPCBCustomerType.setSelectedIndex(-1);
        SPCBCustomerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPCBCustomerTypeActionPerformed(evt);
            }
        });
        jPanel4.add(SPCBCustomerType);
        SPCBCustomerType.setBounds(20, 20, 160, 30);

        SellProduct.add(jPanel4);
        jPanel4.setBounds(20, 40, 1080, 110);

        jPanel6.setBackground(new java.awt.Color(33, 95, 133));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setLayout(null);

        SPLTotalAmount.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLTotalAmount.setForeground(new java.awt.Color(255, 255, 255));
        SPLTotalAmount.setText("Total Amount");
        jPanel6.add(SPLTotalAmount);
        SPLTotalAmount.setBounds(190, 10, 90, 20);

        SPTFTotalAmount.setEditable(false);
        SPTFTotalAmount.setBackground(new java.awt.Color(255, 255, 255));
        SPTFTotalAmount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        SPTFTotalAmount.setText("00.0");
        jPanel6.add(SPTFTotalAmount);
        SPTFTotalAmount.setBounds(190, 30, 120, 30);

        SPLDiscount.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLDiscount.setForeground(new java.awt.Color(255, 255, 255));
        SPLDiscount.setText("Disc(%)");
        SPLDiscount.setToolTipText("");
        jPanel6.add(SPLDiscount);
        SPLDiscount.setBounds(350, 10, 60, 20);

        LGrossAmount.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LGrossAmount.setForeground(new java.awt.Color(255, 255, 255));
        LGrossAmount.setText("Gross Amount :");
        jPanel6.add(LGrossAmount);
        LGrossAmount.setBounds(40, 80, 130, 30);

        SPTFDiscount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        SPTFDiscount.setText("0");
        SPTFDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SPTFDiscountKeyReleased(evt);
            }
        });
        jPanel6.add(SPTFDiscount);
        SPTFDiscount.setBounds(350, 30, 70, 30);

        SPLNetAmount.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLNetAmount.setForeground(new java.awt.Color(255, 255, 255));
        SPLNetAmount.setText("Net Amount ");
        jPanel6.add(SPLNetAmount);
        SPLNetAmount.setBounds(40, 10, 90, 20);

        SPTFNetAmount.setEditable(false);
        SPTFNetAmount.setBackground(new java.awt.Color(255, 255, 255));
        SPTFNetAmount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        SPTFNetAmount.setText("00.0");
        jPanel6.add(SPTFNetAmount);
        SPTFNetAmount.setBounds(40, 30, 110, 30);

        SPLPaymentMode.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLPaymentMode.setForeground(new java.awt.Color(255, 255, 255));
        SPLPaymentMode.setText("Payment Mode");
        jPanel6.add(SPLPaymentMode);
        SPLPaymentMode.setBounds(610, 10, 100, 20);

        SPCBPaymentMode.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBPaymentMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Cheque" }));
        SPCBPaymentMode.setSelectedIndex(-1);
        SPCBPaymentMode.setToolTipText("");
        jPanel6.add(SPCBPaymentMode);
        SPCBPaymentMode.setBounds(610, 30, 130, 30);

        SPLPaidAmount.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLPaidAmount.setForeground(new java.awt.Color(255, 255, 255));
        SPLPaidAmount.setText("Piad Amount");
        jPanel6.add(SPLPaidAmount);
        SPLPaidAmount.setBounds(780, 10, 120, 20);

        SPTFPaidAmount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        SPTFPaidAmount.setText("0");
        SPTFPaidAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SPTFPaidAmountKeyReleased(evt);
            }
        });
        jPanel6.add(SPTFPaidAmount);
        SPTFPaidAmount.setBounds(780, 30, 110, 30);

        SPLDues.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLDues.setForeground(new java.awt.Color(255, 255, 255));
        SPLDues.setText("Dues");
        jPanel6.add(SPLDues);
        SPLDues.setBounds(930, 10, 31, 17);

        SPTFDues.setEditable(false);
        SPTFDues.setBackground(new java.awt.Color(255, 255, 255));
        SPTFDues.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        SPTFDues.setText("00.0");
        jPanel6.add(SPTFDues);
        SPTFDues.setBounds(930, 30, 110, 30);

        jButton33.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton33.setText("Save");
        jButton33.setRequestFocusEnabled(false);
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton33);
        jButton33.setBounds(740, 80, 90, 30);

        jButton34.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton34.setText("Recipt");
        jButton34.setRequestFocusEnabled(false);
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton34);
        jButton34.setBounds(610, 80, 100, 30);

        SPLGrossAmount.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        SPLGrossAmount.setForeground(new java.awt.Color(255, 102, 102));
        SPLGrossAmount.setText("00.0");
        SPLGrossAmount.setToolTipText("");
        jPanel6.add(SPLGrossAmount);
        SPLGrossAmount.setBounds(180, 80, 120, 30);

        SellProduct.add(jPanel6);
        jPanel6.setBounds(20, 590, 1080, 120);

        SPLProductCategory.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLProductCategory.setForeground(new java.awt.Color(255, 255, 255));
        SPLProductCategory.setText("Product Category ");
        SellProduct.add(SPLProductCategory);
        SPLProductCategory.setBounds(30, 160, 120, 20);

        SPLProductName.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLProductName.setForeground(new java.awt.Color(255, 255, 255));
        SPLProductName.setText("Product Name ");
        SellProduct.add(SPLProductName);
        SPLProductName.setBounds(260, 160, 100, 20);

        SPLQuantity.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLQuantity.setForeground(new java.awt.Color(255, 255, 255));
        SPLQuantity.setText("Quantity ");
        SellProduct.add(SPLQuantity);
        SPLQuantity.setBounds(30, 220, 70, 20);

        SPLUOM.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLUOM.setForeground(new java.awt.Color(255, 255, 255));
        SPLUOM.setText("UOM ");
        SellProduct.add(SPLUOM);
        SPLUOM.setBounds(850, 160, 70, 20);

        SPLSize.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLSize.setForeground(new java.awt.Color(255, 255, 255));
        SPLSize.setText("Size ");
        SellProduct.add(SPLSize);
        SPLSize.setBounds(710, 160, 60, 20);

        SPCBProductCategory.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBProductCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPCBProductCategoryActionPerformed(evt);
            }
        });
        SellProduct.add(SPCBProductCategory);
        SPCBProductCategory.setBounds(30, 180, 180, 30);

        SPCBProductName.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPCBProductNameActionPerformed(evt);
            }
        });
        SellProduct.add(SPCBProductName);
        SPCBProductName.setBounds(260, 180, 170, 30);

        SPCBUOM.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBUOM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPCBUOMActionPerformed(evt);
            }
        });
        SellProduct.add(SPCBUOM);
        SPCBUOM.setBounds(850, 180, 100, 30);

        SPCBSize.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPCBSizeActionPerformed(evt);
            }
        });
        SellProduct.add(SPCBSize);
        SPCBSize.setBounds(710, 180, 90, 30);

        jButton4.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jButton4.setText("ADD");
        jButton4.setRequestFocusEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        SellProduct.add(jButton4);
        jButton4.setBounds(360, 240, 70, 30);

        SPLProductBrand.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLProductBrand.setForeground(new java.awt.Color(255, 255, 255));
        SPLProductBrand.setText("Product Brand ");
        SellProduct.add(SPLProductBrand);
        SPLProductBrand.setBounds(480, 160, 110, 20);

        SPCBProductBrand.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPCBProductBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPCBProductBrandActionPerformed(evt);
            }
        });
        SellProduct.add(SPCBProductBrand);
        SPCBProductBrand.setBounds(480, 180, 180, 30);

        jButton36.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jButton36.setText("Clear");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        SellProduct.add(jButton36);
        jButton36.setBounds(460, 240, 70, 30);

        SPTFQuantity.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SellProduct.add(SPTFQuantity);
        SPTFQuantity.setBounds(30, 240, 70, 30);

        SPLGST.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLGST.setForeground(new java.awt.Color(255, 255, 255));
        SPLGST.setText("GST(%)");
        SellProduct.add(SPLGST);
        SPLGST.setBounds(260, 220, 70, 20);

        SPTFGST.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SPTFGST.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SPTFGSTKeyReleased(evt);
            }
        });
        SellProduct.add(SPTFGST);
        SPTFGST.setBounds(260, 240, 70, 30);

        jButton37.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jButton37.setText("Refresh");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        SellProduct.add(jButton37);
        jButton37.setBounds(560, 240, 80, 30);

        SPLSellingPrice.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLSellingPrice.setForeground(new java.awt.Color(255, 255, 255));
        SPLSellingPrice.setText("Price :");
        SellProduct.add(SPLSellingPrice);
        SPLSellingPrice.setBounds(1000, 160, 80, 20);

        SPTFSellingPrice.setEditable(false);
        SPTFSellingPrice.setBackground(new java.awt.Color(255, 255, 255));
        SPTFSellingPrice.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SellProduct.add(SPTFSellingPrice);
        SPTFSellingPrice.setBounds(1000, 180, 80, 30);

        jPanel1.add(SellProduct);
        SellProduct.setBounds(0, 0, 1120, 720);

        ProfileUpdate.setBackground(new java.awt.Color(33, 95, 133));
        ProfileUpdate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profile Update", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        ProfileUpdate.setLayout(null);

        PULCurrentPassword.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULCurrentPassword.setForeground(new java.awt.Color(255, 255, 255));
        PULCurrentPassword.setText("Current Password    :");
        ProfileUpdate.add(PULCurrentPassword);
        PULCurrentPassword.setBounds(20, 110, 130, 30);

        PULAdminID.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULAdminID.setForeground(new java.awt.Color(255, 255, 255));
        PULAdminID.setText("AdminID                      :");
        ProfileUpdate.add(PULAdminID);
        PULAdminID.setBounds(20, 50, 130, 30);

        PUTFAdminID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PUTFAdminIDKeyReleased(evt);
            }
        });
        ProfileUpdate.add(PUTFAdminID);
        PUTFAdminID.setBounds(170, 50, 270, 30);

        PUTFCurrentPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PUTFCurrentPasswordKeyReleased(evt);
            }
        });
        ProfileUpdate.add(PUTFCurrentPassword);
        PUTFCurrentPassword.setBounds(170, 110, 270, 30);

        PUTFNewPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PUTFNewPasswordKeyReleased(evt);
            }
        });
        ProfileUpdate.add(PUTFNewPassword);
        PUTFNewPassword.setBounds(170, 170, 270, 30);

        PULNewPassword.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULNewPassword.setForeground(new java.awt.Color(255, 255, 255));
        PULNewPassword.setText("New Password          :");
        ProfileUpdate.add(PULNewPassword);
        PULNewPassword.setBounds(20, 170, 130, 30);

        PULConfirmPassword.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULConfirmPassword.setForeground(new java.awt.Color(255, 255, 255));
        PULConfirmPassword.setText("Confirm Password  :");
        ProfileUpdate.add(PULConfirmPassword);
        PULConfirmPassword.setBounds(20, 230, 130, 30);

        PUTFConfirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PUTFConfirmPasswordActionPerformed(evt);
            }
        });
        PUTFConfirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PUTFConfirmPasswordKeyReleased(evt);
            }
        });
        ProfileUpdate.add(PUTFConfirmPassword);
        PUTFConfirmPassword.setBounds(170, 230, 270, 30);

        jButton29.setBackground(new java.awt.Color(231, 76, 60));
        jButton29.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton29.setText("Save");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        ProfileUpdate.add(jButton29);
        jButton29.setBounds(170, 280, 90, 30);

        PULContactNo.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULContactNo.setForeground(new java.awt.Color(255, 255, 255));
        PULContactNo.setText("Contact No :");
        ProfileUpdate.add(PULContactNo);
        PULContactNo.setBounds(570, 410, 100, 30);

        PULShopName.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULShopName.setForeground(new java.awt.Color(255, 255, 255));
        PULShopName.setText("Shop Name :");
        ProfileUpdate.add(PULShopName);
        PULShopName.setBounds(570, 110, 100, 30);

        PULAddress.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULAddress.setForeground(new java.awt.Color(255, 255, 255));
        PULAddress.setText("Address :");
        ProfileUpdate.add(PULAddress);
        PULAddress.setBounds(570, 170, 100, 30);

        PULNear.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULNear.setForeground(new java.awt.Color(255, 255, 255));
        PULNear.setText("Near :");
        ProfileUpdate.add(PULNear);
        PULNear.setBounds(570, 230, 100, 30);

        PULCity.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULCity.setForeground(new java.awt.Color(255, 255, 255));
        PULCity.setText("City :");
        ProfileUpdate.add(PULCity);
        PULCity.setBounds(570, 290, 100, 30);

        PULPinCode.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULPinCode.setForeground(new java.awt.Color(255, 255, 255));
        PULPinCode.setText("Pin Code :");
        ProfileUpdate.add(PULPinCode);
        PULPinCode.setBounds(570, 350, 100, 30);

        PUTFContactNo.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        ProfileUpdate.add(PUTFContactNo);
        PUTFContactNo.setBounds(700, 410, 370, 30);

        PUTFShopName.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        ProfileUpdate.add(PUTFShopName);
        PUTFShopName.setBounds(700, 110, 370, 30);

        PUTFAddress.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        ProfileUpdate.add(PUTFAddress);
        PUTFAddress.setBounds(700, 170, 370, 30);

        PUTFNear.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        ProfileUpdate.add(PUTFNear);
        PUTFNear.setBounds(700, 230, 370, 30);

        PUTFCity.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        ProfileUpdate.add(PUTFCity);
        PUTFCity.setBounds(700, 290, 370, 30);

        PUTFPinCode.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        ProfileUpdate.add(PUTFPinCode);
        PUTFPinCode.setBounds(700, 350, 370, 30);

        jButton10.setBackground(new java.awt.Color(231, 76, 60));
        jButton10.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton10.setText("Update");
        jButton10.setRequestFocusEnabled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        ProfileUpdate.add(jButton10);
        jButton10.setBounds(700, 520, 100, 30);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Shop Details");
        ProfileUpdate.add(jLabel9);
        jLabel9.setBounds(700, 40, 370, 50);

        PULImage.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PULImage.setForeground(new java.awt.Color(255, 255, 255));
        PULImage.setText("Shop Logo  :");
        ProfileUpdate.add(PULImage);
        PULImage.setBounds(540, 620, 110, 30);

        PUTFImage.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ProfileUpdate.add(PUTFImage);
        PUTFImage.setBounds(660, 620, 430, 30);

        jButton11.setBackground(new java.awt.Color(231, 76, 60));
        jButton11.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton11.setText("Save");
        jButton11.setBorder(null);
        jButton11.setRequestFocusEnabled(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        ProfileUpdate.add(jButton11);
        jButton11.setBounds(660, 660, 90, 30);

        jButton12.setBackground(new java.awt.Color(231, 76, 60));
        jButton12.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton12.setText("Browse");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        ProfileUpdate.add(jButton12);
        jButton12.setBounds(990, 660, 100, 30);

        jLabel10.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Logo-Position :");
        ProfileUpdate.add(jLabel10);
        jLabel10.setBounds(70, 620, 130, 30);

        PUTFTermsAndCondition.setFont(new java.awt.Font("Courier New", 0, 16)); // NOI18N
        ProfileUpdate.add(PUTFTermsAndCondition);
        PUTFTermsAndCondition.setBounds(70, 410, 470, 30);

        jButton43.setBackground(new java.awt.Color(231, 76, 60));
        jButton43.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton43.setText("Clear All");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });
        ProfileUpdate.add(jButton43);
        jButton43.setBounds(180, 460, 120, 30);

        PUBAdd.setBackground(new java.awt.Color(231, 76, 60));
        PUBAdd.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        PUBAdd.setText("Add");
        PUBAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PUBAddActionPerformed(evt);
            }
        });
        ProfileUpdate.add(PUBAdd);
        PUBAdd.setBounds(70, 460, 90, 30);

        PUTFWebSite.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        ProfileUpdate.add(PUTFWebSite);
        PUTFWebSite.setBounds(700, 470, 370, 30);

        PULWebSite.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PULWebSite.setForeground(new java.awt.Color(255, 255, 255));
        PULWebSite.setText("Web Site :");
        ProfileUpdate.add(PULWebSite);
        PULWebSite.setBounds(570, 470, 100, 30);

        PUBUpdate.setBackground(new java.awt.Color(231, 76, 60));
        PUBUpdate.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        PUBUpdate.setText("Update");
        PUBUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PUBUpdateActionPerformed(evt);
            }
        });
        ProfileUpdate.add(PUBUpdate);
        PUBUpdate.setBounds(70, 660, 90, 30);

        PUBAdd2.setBackground(new java.awt.Color(231, 76, 60));
        PUBAdd2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        PUBAdd2.setText("Preview");
        PUBAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PUBAdd2ActionPerformed(evt);
            }
        });
        ProfileUpdate.add(PUBAdd2);
        PUBAdd2.setBounds(170, 660, 120, 30);

        PULogoWidth.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ProfileUpdate.add(PULogoWidth);
        PULogoWidth.setBounds(210, 580, 80, 30);

        jLabel11.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("T&C :");
        ProfileUpdate.add(jLabel11);
        jLabel11.setBounds(20, 410, 50, 30);

        jLabel12.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Logo-Width :");
        ProfileUpdate.add(jLabel12);
        jLabel12.setBounds(70, 580, 130, 30);

        PULogoPosition.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ProfileUpdate.add(PULogoPosition);
        PULogoPosition.setBounds(210, 620, 80, 30);

        jPanel1.add(ProfileUpdate);
        ProfileUpdate.setBounds(0, 0, 1120, 720);

        ProductEntery.setBackground(new java.awt.Color(33, 95, 133));
        ProductEntery.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Entery", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Georgia", 0, 24), new java.awt.Color(102, 255, 255))); // NOI18N
        ProductEntery.setLayout(null);

        jButton1.setBackground(new java.awt.Color(153, 255, 255));
        jButton1.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 204));
        jButton1.setText("ADD");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), java.awt.Color.cyan, new java.awt.Color(51, 255, 255)));
        jButton1.setRequestFocusEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        ProductEntery.add(jButton1);
        jButton1.setBounds(210, 520, 100, 40);

        PELProductImages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Phulka_Roti_big.jpg"))); // NOI18N
        PELProductImages.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ProductEntery.add(PELProductImages);
        PELProductImages.setBounds(580, 50, 510, 540);

        PELProductCategory.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELProductCategory.setForeground(new java.awt.Color(255, 255, 255));
        PELProductCategory.setText("Product Category :");
        ProductEntery.add(PELProductCategory);
        PELProductCategory.setBounds(40, 60, 150, 30);

        PELProductName.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELProductName.setForeground(new java.awt.Color(255, 255, 255));
        PELProductName.setText("Product Name      :");
        ProductEntery.add(PELProductName);
        PELProductName.setBounds(40, 110, 145, 30);

        PELSize.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELSize.setForeground(new java.awt.Color(255, 255, 255));
        PELSize.setText("Size                          :");
        ProductEntery.add(PELSize);
        PELSize.setBounds(40, 210, 150, 30);

        PELUOM.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELUOM.setForeground(new java.awt.Color(255, 255, 255));
        PELUOM.setText("UOM                       :");
        ProductEntery.add(PELUOM);
        PELUOM.setBounds(40, 270, 160, 21);

        PECBProductCategory.setEditable(true);
        PECBProductCategory.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        PECBProductCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        PECBProductCategory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PECBProductCategoryFocusGained(evt);
            }
        });
        PECBProductCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PECBProductCategoryKeyReleased(evt);
            }
        });
        ProductEntery.add(PECBProductCategory);
        PECBProductCategory.setBounds(210, 60, 220, 30);

        PECBProductName.setEditable(true);
        PECBProductName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        PECBProductName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PECBProductNameKeyReleased(evt);
            }
        });
        ProductEntery.add(PECBProductName);
        PECBProductName.setBounds(210, 110, 220, 30);

        PECBSize.setEditable(true);
        PECBSize.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ProductEntery.add(PECBSize);
        PECBSize.setBounds(210, 210, 220, 30);

        PECBUOM.setEditable(true);
        PECBUOM.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ProductEntery.add(PECBUOM);
        PECBUOM.setBounds(210, 260, 220, 30);

        PELSellingPrice.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELSellingPrice.setForeground(new java.awt.Color(255, 255, 255));
        PELSellingPrice.setText("Selling Price          :");
        ProductEntery.add(PELSellingPrice);
        PELSellingPrice.setBounds(40, 310, 160, 30);

        PETFSellingPrice.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ProductEntery.add(PETFSellingPrice);
        PETFSellingPrice.setBounds(210, 310, 140, 30);

        PELDiscription.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELDiscription.setForeground(new java.awt.Color(255, 255, 255));
        PELDiscription.setText("Description           :");
        ProductEntery.add(PELDiscription);
        PELDiscription.setBounds(40, 410, 142, 30);

        PELProductBrand.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELProductBrand.setForeground(new java.awt.Color(255, 255, 255));
        PELProductBrand.setText("Product Brand     :");
        ProductEntery.add(PELProductBrand);
        PELProductBrand.setBounds(40, 160, 142, 30);

        PECBProductBrand.setEditable(true);
        PECBProductBrand.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ProductEntery.add(PECBProductBrand);
        PECBProductBrand.setBounds(210, 160, 220, 30);

        PELBarcode.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELBarcode.setForeground(new java.awt.Color(255, 255, 255));
        PELBarcode.setText("Barcode                  :");
        ProductEntery.add(PELBarcode);
        PELBarcode.setBounds(40, 360, 150, 30);

        PETFBarcode.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ProductEntery.add(PETFBarcode);
        PETFBarcode.setBounds(210, 360, 280, 30);

        PETADiscription.setColumns(20);
        PETADiscription.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        PETADiscription.setLineWrap(true);
        PETADiscription.setRows(5);
        PETADiscription.setTabSize(4);
        jScrollPane1.setViewportView(PETADiscription);

        ProductEntery.add(jScrollPane1);
        jScrollPane1.setBounds(210, 410, 280, 71);

        PELImage.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        PELImage.setForeground(new java.awt.Color(255, 255, 255));
        PELImage.setText("Image  :");
        ProductEntery.add(PELImage);
        PELImage.setBounds(580, 620, 70, 30);

        PETFImage.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        ProductEntery.add(PETFImage);
        PETFImage.setBounds(660, 620, 430, 30);

        jButton2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton2.setText("Browse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        ProductEntery.add(jButton2);
        jButton2.setBounds(990, 660, 100, 30);

        jButton5.setBackground(new java.awt.Color(153, 255, 255));
        jButton5.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 204));
        jButton5.setText("Save");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), java.awt.Color.cyan, new java.awt.Color(51, 255, 255)));
        jButton5.setRequestFocusEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        ProductEntery.add(jButton5);
        jButton5.setBounds(660, 660, 90, 40);

        jPanel1.add(ProductEntery);
        ProductEntery.setBounds(0, 0, 1120, 720);

        ViewSupplier34.setBackground(new java.awt.Color(33, 95, 133));
        ViewSupplier34.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Supplier List", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        ViewSupplier34.setLayout(null);
        jPanel1.add(ViewSupplier34);
        ViewSupplier34.setBounds(0, 0, 1120, 720);

        Home.setBackground(new java.awt.Color(33, 95, 133));
        Home.setLayout(null);

        jPanelSlider1.setBackground(new java.awt.Color(33, 95, 133));

        StockandExpNotify.setBackground(new java.awt.Color(33, 95, 133));
        StockandExpNotify.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Home", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Georgia", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        StockandExpNotify.setLayout(null);

        HTStock.setBackground(new java.awt.Color(240, 240, 240));
        HTStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        HTStock.setForeground(new java.awt.Color(204, 0, 0));
        HTStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProductD", "Category", "Name", "Quantity", "Exp Date"
            }
        ));
        jScrollPane3.setViewportView(HTStock);
        if (HTStock.getColumnModel().getColumnCount() > 0) {
            HTStock.getColumnModel().getColumn(0).setPreferredWidth(2);
            HTStock.getColumnModel().getColumn(1).setPreferredWidth(2);
            HTStock.getColumnModel().getColumn(3).setPreferredWidth(1);
            HTStock.getColumnModel().getColumn(4).setPreferredWidth(1);
        }

        StockandExpNotify.add(jScrollPane3);
        jScrollPane3.setBounds(20, 60, 570, 640);

        jLabel55.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Stock :");
        StockandExpNotify.add(jLabel55);
        jLabel55.setBounds(20, 30, 80, 30);

        HRBCurrent.setBackground(new java.awt.Color(33, 95, 133));
        HRBCurrent.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        HRBCurrent.setForeground(new java.awt.Color(255, 255, 0));
        HRBCurrent.setText("Current");
        HRBCurrent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HRBCurrent.setFocusPainted(false);
        HRBCurrent.setRequestFocusEnabled(false);
        HRBCurrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HRBCurrentActionPerformed(evt);
            }
        });
        StockandExpNotify.add(HRBCurrent);
        HRBCurrent.setBounds(140, 40, 80, 20);

        HRBBackup.setBackground(new java.awt.Color(33, 95, 133));
        HRBBackup.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        HRBBackup.setForeground(new java.awt.Color(255, 255, 0));
        HRBBackup.setText("Backup");
        HRBBackup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HRBBackup.setFocusPainted(false);
        HRBBackup.setRequestFocusEnabled(false);
        HRBBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HRBBackupActionPerformed(evt);
            }
        });
        StockandExpNotify.add(HRBBackup);
        HRBBackup.setBounds(250, 40, 80, 20);

        HRBSortByExpDate.setBackground(new java.awt.Color(33, 95, 133));
        HRBSortByExpDate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        HRBSortByExpDate.setForeground(new java.awt.Color(255, 255, 0));
        HRBSortByExpDate.setText("Sort By Exp Date");
        HRBSortByExpDate.setFocusPainted(false);
        HRBSortByExpDate.setRequestFocusEnabled(false);
        HRBSortByExpDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HRBSortByExpDateActionPerformed(evt);
            }
        });
        StockandExpNotify.add(HRBSortByExpDate);
        HRBSortByExpDate.setBounds(350, 40, 150, 20);

        DBLProductImages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Phulka_Roti_big.jpg"))); // NOI18N
        DBLProductImages.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        StockandExpNotify.add(DBLProductImages);
        DBLProductImages.setBounds(600, 60, 510, 640);

        jButton6.setBackground(new java.awt.Color(255, 0, 0));
        jButton6.setFont(new java.awt.Font("Georgia", 0, 11)); // NOI18N
        jButton6.setText("Next");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.setRequestFocusEnabled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        StockandExpNotify.add(jButton6);
        jButton6.setBounds(1010, 20, 60, 30);

        jButton9.setBackground(new java.awt.Color(255, 0, 0));
        jButton9.setFont(new java.awt.Font("Georgia", 0, 11)); // NOI18N
        jButton9.setText("Refresh");
        jButton9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton9.setRequestFocusEnabled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        StockandExpNotify.add(jButton9);
        jButton9.setBounds(920, 20, 70, 30);

        jPanelSlider1.add(StockandExpNotify, "card2");

        ProfitDetails.setBackground(new java.awt.Color(33, 95, 133));
        ProfitDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Home", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Georgia", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        ProfitDetails.setLayout(null);

        DBTMonthSoldProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProductID", "Category", "Name", "Brand", "Size", "Quantity"
            }
        ));
        jScrollPane2.setViewportView(DBTMonthSoldProduct);

        ProfitDetails.add(jScrollPane2);
        jScrollPane2.setBounds(20, 70, 390, 620);

        jLabel2.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Month Profite :");
        ProfitDetails.add(jLabel2);
        jLabel2.setBounds(430, 70, 100, 30);

        jLabel3.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Product Sold In This Month");
        ProfitDetails.add(jLabel3);
        jLabel3.setBounds(20, 30, 180, 30);

        jButton7.setBackground(new java.awt.Color(255, 0, 0));
        jButton7.setFont(new java.awt.Font("Georgia", 0, 11)); // NOI18N
        jButton7.setText("Back");
        jButton7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton7.setRequestFocusEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        ProfitDetails.add(jButton7);
        jButton7.setBounds(1020, 20, 60, 30);

        jButton8.setBackground(new java.awt.Color(255, 0, 0));
        jButton8.setFont(new java.awt.Font("Georgia", 0, 11)); // NOI18N
        jButton8.setText("Refresh");
        jButton8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton8.setRequestFocusEnabled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        ProfitDetails.add(jButton8);
        jButton8.setBounds(930, 20, 70, 30);

        jLabel4.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("This Year Profite :");
        ProfitDetails.add(jLabel4);
        jLabel4.setBounds(430, 230, 120, 30);

        jLabel5.setBackground(new java.awt.Color(128, 0, 0));
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));
        jLabel5.setOpaque(true);
        ProfitDetails.add(jLabel5);
        jLabel5.setBounds(730, 190, 170, 10);

        DBLMonthPofitRupee.setBackground(new java.awt.Color(255, 51, 51));
        DBLMonthPofitRupee.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        DBLMonthPofitRupee.setForeground(new java.awt.Color(255, 255, 255));
        DBLMonthPofitRupee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DBLMonthPofitRupee.setText("Profit");
        DBLMonthPofitRupee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, null));
        DBLMonthPofitRupee.setOpaque(true);
        ProfitDetails.add(DBLMonthPofitRupee);
        DBLMonthPofitRupee.setBounds(730, 130, 170, 60);

        DBLMonthPofitPercent.setBackground(new java.awt.Color(255, 51, 51));
        DBLMonthPofitPercent.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        DBLMonthPofitPercent.setForeground(new java.awt.Color(255, 255, 255));
        DBLMonthPofitPercent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DBLMonthPofitPercent.setText("Profit");
        DBLMonthPofitPercent.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, null));
        DBLMonthPofitPercent.setOpaque(true);
        ProfitDetails.add(DBLMonthPofitPercent);
        DBLMonthPofitPercent.setBounds(540, 130, 170, 60);

        DBMCMonthProfit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DBMCMonthProfit.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DBMCMonthProfitPropertyChange(evt);
            }
        });
        ProfitDetails.add(DBMCMonthProfit);
        DBMCMonthProfit.setBounds(540, 70, 100, 30);

        DBYCYearProfit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DBYCYearProfit.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DBYCYearProfitPropertyChange(evt);
            }
        });
        ProfitDetails.add(DBYCYearProfit);
        DBYCYearProfit.setBounds(640, 70, 60, 30);

        DBLYearPofitPercent.setBackground(new java.awt.Color(255, 51, 51));
        DBLYearPofitPercent.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        DBLYearPofitPercent.setForeground(new java.awt.Color(255, 255, 255));
        DBLYearPofitPercent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DBLYearPofitPercent.setText("Profit");
        DBLYearPofitPercent.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, null));
        DBLYearPofitPercent.setOpaque(true);
        ProfitDetails.add(DBLYearPofitPercent);
        DBLYearPofitPercent.setBounds(540, 270, 170, 70);

        jLabel6.setBackground(new java.awt.Color(128, 0, 0));
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));
        jLabel6.setOpaque(true);
        ProfitDetails.add(jLabel6);
        jLabel6.setBounds(540, 340, 170, 10);

        jLabel7.setBackground(new java.awt.Color(128, 0, 0));
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));
        jLabel7.setOpaque(true);
        ProfitDetails.add(jLabel7);
        jLabel7.setBounds(540, 190, 170, 10);

        DBLYearPofitRupee.setBackground(new java.awt.Color(255, 51, 51));
        DBLYearPofitRupee.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        DBLYearPofitRupee.setForeground(new java.awt.Color(255, 255, 255));
        DBLYearPofitRupee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DBLYearPofitRupee.setText("Profit");
        DBLYearPofitRupee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, null));
        DBLYearPofitRupee.setOpaque(true);
        ProfitDetails.add(DBLYearPofitRupee);
        DBLYearPofitRupee.setBounds(730, 270, 170, 70);

        jLabel8.setBackground(new java.awt.Color(128, 0, 0));
        jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));
        jLabel8.setOpaque(true);
        ProfitDetails.add(jLabel8);
        jLabel8.setBounds(730, 340, 170, 10);

        jPanelSlider1.add(ProfitDetails, "card3");

        Home.add(jPanelSlider1);
        jPanelSlider1.setBounds(0, 0, 1120, 720);

        jPanel1.add(Home);
        Home.setBounds(0, 0, 1120, 720);

        PurchaseReport.setBackground(new java.awt.Color(33, 95, 133));
        PurchaseReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Purchase Report", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        PurchaseReport.setLayout(null);

        jPanel9.setBackground(new java.awt.Color(33, 95, 133));
        jPanel9.setLayout(null);

        jLabel97.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("From ");
        jPanel9.add(jLabel97);
        jLabel97.setBounds(10, 10, 100, 20);

        jLabel98.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("To");
        jPanel9.add(jLabel98);
        jLabel98.setBounds(210, 10, 120, 20);

        jLabel99.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText("Search By :");
        jPanel9.add(jLabel99);
        jLabel99.setBounds(430, 30, 80, 30);

        jButton25.setBackground(new java.awt.Color(231, 76, 60));
        jButton25.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton25.setText("Show All");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton25);
        jButton25.setBounds(850, 30, 150, 30);

        PurRDCFrom.setDateFormatString("dd-MM-yyyy");
        PurRDCFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                PurRDCFromPropertyChange(evt);
            }
        });
        jPanel9.add(PurRDCFrom);
        PurRDCFrom.setBounds(10, 30, 180, 30);

        PurRDCTo.setDateFormatString("dd-MM-yyyy");
        PurRDCTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                PurRDCToPropertyChange(evt);
            }
        });
        jPanel9.add(PurRDCTo);
        PurRDCTo.setBounds(210, 30, 180, 30);

        PurRCBSupplierNames.setEditable(true);
        PurRCBSupplierNames.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PurRCBSupplierNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurRCBSupplierNamesActionPerformed(evt);
            }
        });
        jPanel9.add(PurRCBSupplierNames);
        PurRCBSupplierNames.setBounds(510, 30, 280, 30);

        jLabel21.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 51, 51));
        jLabel21.setText("*First Select Date");
        jPanel9.add(jLabel21);
        jLabel21.setBounds(10, 80, 140, 20);

        jButton35.setBackground(new java.awt.Color(231, 76, 60));
        jButton35.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton35.setText("Export Table to PDF");
        jButton35.setRequestFocusEnabled(false);
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton35);
        jButton35.setBounds(510, 80, 200, 30);

        PurchaseReport.add(jPanel9);
        jPanel9.setBounds(20, 40, 1080, 120);

        jPanel1.add(PurchaseReport);
        PurchaseReport.setBounds(0, 0, 1120, 720);

        TransactionReport.setBackground(new java.awt.Color(33, 95, 133));
        TransactionReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Purchase Report", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        TransactionReport.setLayout(null);

        jPanel14.setBackground(new java.awt.Color(33, 95, 133));
        jPanel14.setLayout(null);

        jLabel113.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 255, 255));
        jLabel113.setText("From ");
        jPanel14.add(jLabel113);
        jLabel113.setBounds(10, 10, 100, 20);

        jLabel114.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setText("To");
        jPanel14.add(jLabel114);
        jLabel114.setBounds(210, 10, 120, 20);

        jButton48.setBackground(new java.awt.Color(231, 76, 60));
        jButton48.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton48.setText("Show All");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton48);
        jButton48.setBounds(760, 30, 150, 30);

        TRDCFrom.setDateFormatString("dd-MM-yyyy");
        TRDCFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TRDCFromPropertyChange(evt);
            }
        });
        jPanel14.add(TRDCFrom);
        TRDCFrom.setBounds(10, 30, 180, 30);

        TRDCTo.setDateFormatString("dd-MM-yyyy");
        TRDCTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TRDCToPropertyChange(evt);
            }
        });
        jPanel14.add(TRDCTo);
        TRDCTo.setBounds(210, 30, 180, 30);

        jButton49.setBackground(new java.awt.Color(231, 76, 60));
        jButton49.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton49.setText("Export Table to PDF");
        jButton49.setRequestFocusEnabled(false);
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton49);
        jButton49.setBounds(490, 30, 200, 30);

        TransactionReport.add(jPanel14);
        jPanel14.setBounds(20, 40, 1080, 80);

        jPanel1.add(TransactionReport);
        TransactionReport.setBounds(0, 0, 1120, 720);

        EmployeePermission.setBackground(new java.awt.Color(33, 95, 133));
        EmployeePermission.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Permission", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        EmployeePermission.setLayout(null);

        jLabel40.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Empployee Name :");
        EmployeePermission.add(jLabel40);
        jLabel40.setBounds(20, 40, 130, 30);

        EPCBEmpName.setEditable(true);
        EmployeePermission.add(EPCBEmpName);
        EPCBEmpName.setBounds(150, 40, 250, 30);

        EPCBSetting.setBackground(new java.awt.Color(33, 95, 133));
        EPCBSetting.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBSetting.setForeground(new java.awt.Color(255, 255, 51));
        EPCBSetting.setText("Setting");
        EPCBSetting.setToolTipText("");
        EPCBSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBSettingActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBSetting);
        EPCBSetting.setBounds(250, 650, 80, 25);

        EPCBHome.setBackground(new java.awt.Color(33, 95, 133));
        EPCBHome.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBHome.setForeground(new java.awt.Color(255, 255, 51));
        EPCBHome.setText("Home");
        EPCBHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBHomeActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBHome);
        EPCBHome.setBounds(250, 150, 70, 25);

        EPCBProductCategory.setBackground(new java.awt.Color(33, 95, 133));
        EPCBProductCategory.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBProductCategory.setForeground(new java.awt.Color(255, 255, 51));
        EPCBProductCategory.setText("Product Category");
        EPCBProductCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBProductCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBProductCategoryActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBProductCategory);
        EPCBProductCategory.setBounds(250, 200, 140, 25);

        EPCBCustomer.setBackground(new java.awt.Color(33, 95, 133));
        EPCBCustomer.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBCustomer.setForeground(new java.awt.Color(255, 255, 51));
        EPCBCustomer.setText("Customer");
        EPCBCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBCustomerActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBCustomer);
        EPCBCustomer.setBounds(250, 250, 87, 25);

        EPCBSupplier.setBackground(new java.awt.Color(33, 95, 133));
        EPCBSupplier.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBSupplier.setForeground(new java.awt.Color(255, 255, 51));
        EPCBSupplier.setText("Supplier");
        EPCBSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBSupplierActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBSupplier);
        EPCBSupplier.setBounds(250, 300, 80, 25);

        EPCBInventory.setBackground(new java.awt.Color(33, 95, 133));
        EPCBInventory.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBInventory.setForeground(new java.awt.Color(255, 255, 51));
        EPCBInventory.setText("Inventory");
        EPCBInventory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBInventoryActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBInventory);
        EPCBInventory.setBounds(250, 350, 100, 25);

        EPCBStock.setBackground(new java.awt.Color(33, 95, 133));
        EPCBStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBStock.setForeground(new java.awt.Color(255, 255, 51));
        EPCBStock.setText("Stock");
        EPCBStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBStockActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBStock);
        EPCBStock.setBounds(250, 450, 80, 25);

        EPCBReport.setBackground(new java.awt.Color(33, 95, 133));
        EPCBReport.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBReport.setForeground(new java.awt.Color(255, 255, 51));
        EPCBReport.setText("Report");
        EPCBReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBReportActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBReport);
        EPCBReport.setBounds(250, 500, 70, 25);

        EPCBProductMaster.setBackground(new java.awt.Color(33, 95, 133));
        EPCBProductMaster.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBProductMaster.setForeground(new java.awt.Color(255, 255, 51));
        EPCBProductMaster.setText("Product Master");
        EPCBProductMaster.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBProductMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBProductMasterActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBProductMaster);
        EPCBProductMaster.setBounds(250, 400, 130, 25);

        EPCBPendingReport.setBackground(new java.awt.Color(33, 95, 133));
        EPCBPendingReport.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBPendingReport.setForeground(new java.awt.Color(255, 255, 51));
        EPCBPendingReport.setText("Pending Report");
        EPCBPendingReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBPendingReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBPendingReportActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBPendingReport);
        EPCBPendingReport.setBounds(250, 550, 130, 25);

        EPCBEmployee.setBackground(new java.awt.Color(33, 95, 133));
        EPCBEmployee.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBEmployee.setForeground(new java.awt.Color(255, 255, 51));
        EPCBEmployee.setText("Employee");
        EPCBEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBEmployeeActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBEmployee);
        EPCBEmployee.setBounds(250, 600, 100, 25);

        EPCBViewProduct.setBackground(new java.awt.Color(33, 95, 133));
        EPCBViewProduct.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBViewProduct.setForeground(new java.awt.Color(51, 255, 51));
        EPCBViewProduct.setText("View Products");
        EPCBViewProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBViewProduct);
        EPCBViewProduct.setBounds(420, 200, 130, 25);

        EPCBAddCustomer.setBackground(new java.awt.Color(33, 95, 133));
        EPCBAddCustomer.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBAddCustomer.setForeground(new java.awt.Color(51, 255, 51));
        EPCBAddCustomer.setText("Add Customer");
        EPCBAddCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBAddCustomerActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBAddCustomer);
        EPCBAddCustomer.setBounds(420, 250, 120, 25);

        EPCBAddSupplier.setBackground(new java.awt.Color(33, 95, 133));
        EPCBAddSupplier.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBAddSupplier.setForeground(new java.awt.Color(51, 255, 51));
        EPCBAddSupplier.setText("Add Supplier");
        EPCBAddSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBAddSupplier);
        EPCBAddSupplier.setBounds(420, 300, 120, 25);

        EPCBViewCustomers.setBackground(new java.awt.Color(33, 95, 133));
        EPCBViewCustomers.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBViewCustomers.setForeground(new java.awt.Color(51, 255, 51));
        EPCBViewCustomers.setText("View Customers");
        EPCBViewCustomers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBViewCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBViewCustomersActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBViewCustomers);
        EPCBViewCustomers.setBounds(550, 250, 130, 25);

        EPCBViewSuppliers.setBackground(new java.awt.Color(33, 95, 133));
        EPCBViewSuppliers.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBViewSuppliers.setForeground(new java.awt.Color(51, 255, 51));
        EPCBViewSuppliers.setText("View Suppliers");
        EPCBViewSuppliers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBViewSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBViewSuppliersActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBViewSuppliers);
        EPCBViewSuppliers.setBounds(550, 300, 130, 25);

        EPCBProductMasterSub.setBackground(new java.awt.Color(33, 95, 133));
        EPCBProductMasterSub.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBProductMasterSub.setForeground(new java.awt.Color(51, 255, 51));
        EPCBProductMasterSub.setText("Product Master");
        EPCBProductMasterSub.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBProductMasterSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBProductMasterSubActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBProductMasterSub);
        EPCBProductMasterSub.setBounds(420, 400, 130, 25);

        EPCBPurchaseProduct.setBackground(new java.awt.Color(33, 95, 133));
        EPCBPurchaseProduct.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBPurchaseProduct.setForeground(new java.awt.Color(51, 255, 51));
        EPCBPurchaseProduct.setText("Purchase Product");
        EPCBPurchaseProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBPurchaseProduct);
        EPCBPurchaseProduct.setBounds(670, 350, 140, 25);

        EPCBSellProduct.setBackground(new java.awt.Color(33, 95, 133));
        EPCBSellProduct.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBSellProduct.setForeground(new java.awt.Color(51, 255, 51));
        EPCBSellProduct.setText("Sell Product");
        EPCBSellProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBSellProduct);
        EPCBSellProduct.setBounds(550, 350, 110, 25);

        EPCBAddProduct.setBackground(new java.awt.Color(33, 95, 133));
        EPCBAddProduct.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBAddProduct.setForeground(new java.awt.Color(51, 255, 51));
        EPCBAddProduct.setText("Add Product");
        EPCBAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBAddProductActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBAddProduct);
        EPCBAddProduct.setBounds(420, 350, 110, 25);

        EPCBPurchaseReport.setBackground(new java.awt.Color(33, 95, 133));
        EPCBPurchaseReport.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBPurchaseReport.setForeground(new java.awt.Color(51, 255, 51));
        EPCBPurchaseReport.setText("Purchase Report");
        EPCBPurchaseReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBPurchaseReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBPurchaseReportActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBPurchaseReport);
        EPCBPurchaseReport.setBounds(420, 500, 140, 25);

        EPCBSellReport.setBackground(new java.awt.Color(33, 95, 133));
        EPCBSellReport.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBSellReport.setForeground(new java.awt.Color(51, 255, 51));
        EPCBSellReport.setText("Sell Report");
        EPCBSellReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBSellReport);
        EPCBSellReport.setBounds(570, 500, 100, 25);

        EPCBPendingReportSub.setBackground(new java.awt.Color(33, 95, 133));
        EPCBPendingReportSub.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBPendingReportSub.setForeground(new java.awt.Color(51, 255, 51));
        EPCBPendingReportSub.setText("Pending Report");
        EPCBPendingReportSub.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBPendingReportSub);
        EPCBPendingReportSub.setBounds(420, 550, 130, 25);

        EPCBTransactionReport.setBackground(new java.awt.Color(33, 95, 133));
        EPCBTransactionReport.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBTransactionReport.setForeground(new java.awt.Color(51, 255, 51));
        EPCBTransactionReport.setText("Transaction Report");
        EPCBTransactionReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBTransactionReport);
        EPCBTransactionReport.setBounds(680, 500, 160, 25);

        EPCBNewPerson.setBackground(new java.awt.Color(33, 95, 133));
        EPCBNewPerson.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBNewPerson.setForeground(new java.awt.Color(51, 255, 51));
        EPCBNewPerson.setText("New Person");
        EPCBNewPerson.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBNewPerson);
        EPCBNewPerson.setBounds(570, 550, 110, 25);

        EPCBStockIn.setBackground(new java.awt.Color(33, 95, 133));
        EPCBStockIn.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBStockIn.setForeground(new java.awt.Color(51, 255, 51));
        EPCBStockIn.setText("Stock In");
        EPCBStockIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBStockIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBStockInActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBStockIn);
        EPCBStockIn.setBounds(420, 450, 80, 25);

        EPCBNewEmployee.setBackground(new java.awt.Color(33, 95, 133));
        EPCBNewEmployee.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBNewEmployee.setForeground(new java.awt.Color(51, 255, 51));
        EPCBNewEmployee.setText("New Employee");
        EPCBNewEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBNewEmployee);
        EPCBNewEmployee.setBounds(420, 600, 130, 25);

        EPCBEmpAuthentication.setBackground(new java.awt.Color(33, 95, 133));
        EPCBEmpAuthentication.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBEmpAuthentication.setForeground(new java.awt.Color(51, 255, 51));
        EPCBEmpAuthentication.setText("Emp Authentication");
        EPCBEmpAuthentication.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBEmpAuthentication);
        EPCBEmpAuthentication.setBounds(570, 600, 160, 25);

        EPCBEmpPermission.setBackground(new java.awt.Color(33, 95, 133));
        EPCBEmpPermission.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBEmpPermission.setForeground(new java.awt.Color(51, 255, 51));
        EPCBEmpPermission.setText("Emp Permission");
        EPCBEmpPermission.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EPCBEmpPermission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPCBEmpPermissionActionPerformed(evt);
            }
        });
        EmployeePermission.add(EPCBEmpPermission);
        EPCBEmpPermission.setBounds(740, 600, 130, 25);

        EPCBProfileUpdate.setBackground(new java.awt.Color(33, 95, 133));
        EPCBProfileUpdate.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBProfileUpdate.setForeground(new java.awt.Color(51, 255, 51));
        EPCBProfileUpdate.setText("Profile Update");
        EPCBProfileUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBProfileUpdate);
        EPCBProfileUpdate.setBounds(420, 650, 120, 25);

        EPCBBackup.setBackground(new java.awt.Color(33, 95, 133));
        EPCBBackup.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPCBBackup.setForeground(new java.awt.Color(51, 255, 51));
        EPCBBackup.setText("Backup");
        EPCBBackup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmployeePermission.add(EPCBBackup);
        EPCBBackup.setBounds(570, 650, 80, 25);

        jLabel41.setBackground(new java.awt.Color(102, 255, 102));
        jLabel41.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 0));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Sub");
        jLabel41.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel41.setOpaque(true);
        EmployeePermission.add(jLabel41);
        jLabel41.setBounds(400, 110, 60, 30);

        jLabel42.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Permissions :");
        EmployeePermission.add(jLabel42);
        jLabel42.setBounds(20, 100, 130, 30);

        jLabel43.setBackground(new java.awt.Color(102, 255, 102));
        jLabel43.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 0, 0));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Main");
        jLabel43.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel43.setOpaque(true);
        EmployeePermission.add(jLabel43);
        jLabel43.setBounds(230, 110, 60, 30);

        jLabel44.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EmployeePermission.add(jLabel44);
        jLabel44.setBounds(230, 140, 170, 550);

        jLabel54.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EmployeePermission.add(jLabel54);
        jLabel54.setBounds(400, 140, 480, 550);

        EPLSelectedEmp.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        EPLSelectedEmp.setForeground(new java.awt.Color(255, 255, 255));
        EmployeePermission.add(EPLSelectedEmp);
        EPLSelectedEmp.setBounds(730, 40, 290, 30);

        jLabel57.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Selected Employee :");
        EmployeePermission.add(jLabel57);
        jLabel57.setBounds(580, 40, 140, 30);

        jButton42.setBackground(new java.awt.Color(231, 76, 60));
        jButton42.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton42.setText("Save");
        jButton42.setRequestFocusEnabled(false);
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });
        EmployeePermission.add(jButton42);
        jButton42.setBounds(1020, 670, 80, 30);

        jPanel1.add(EmployeePermission);
        EmployeePermission.setBounds(0, 0, 1120, 720);

        EmployeeAuthentication.setBackground(new java.awt.Color(33, 95, 133));
        EmployeeAuthentication.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Authentication", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        EmployeeAuthentication.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        EmployeeAuthentication.setLayout(null);

        EACBEmpName.setEditable(true);
        EACBEmpName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        EmployeeAuthentication.add(EACBEmpName);
        EACBEmpName.setBounds(150, 40, 250, 30);

        jLabel39.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Empployee Name :");
        EmployeeAuthentication.add(jLabel39);
        jLabel39.setBounds(20, 40, 130, 30);

        jButton39.setBackground(new java.awt.Color(231, 76, 60));
        jButton39.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton39.setText("Not Working Employee");
        jButton39.setRequestFocusEnabled(false);
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        EmployeeAuthentication.add(jButton39);
        jButton39.setBounds(640, 40, 220, 30);

        jButton41.setBackground(new java.awt.Color(231, 76, 60));
        jButton41.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton41.setText("Active Employee");
        jButton41.setRequestFocusEnabled(false);
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });
        EmployeeAuthentication.add(jButton41);
        jButton41.setBounds(450, 40, 160, 30);

        jPanel1.add(EmployeeAuthentication);
        EmployeeAuthentication.setBounds(0, 0, 1120, 720);

        PendingReport.setBackground(new java.awt.Color(33, 95, 133));
        PendingReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pending ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        PendingReport.setLayout(null);

        jButton23.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        jButton23.setText("Pay");
        jButton23.setRequestFocusEnabled(false);
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        PendingReport.add(jButton23);
        jButton23.setBounds(280, 30, 90, 30);

        jButton24.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        jButton24.setText("Receive");
        jButton24.setRequestFocusEnabled(false);
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        PendingReport.add(jButton24);
        jButton24.setBounds(400, 30, 90, 30);

        PRRBCS.setBackground(new java.awt.Color(33, 95, 133));
        PRRBCS.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PRRBCS.setForeground(new java.awt.Color(255, 255, 51));
        PRRBCS.setText("Customer/Supplier");
        PRRBCS.setRequestFocusEnabled(false);
        PRRBCS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRRBCSActionPerformed(evt);
            }
        });
        PendingReport.add(PRRBCS);
        PRRBCS.setBounds(50, 20, 150, 30);

        PRRBNP.setBackground(new java.awt.Color(33, 95, 133));
        PRRBNP.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PRRBNP.setForeground(new java.awt.Color(255, 255, 51));
        PRRBNP.setText("New Person");
        PRRBNP.setRequestFocusEnabled(false);
        PRRBNP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRRBNPActionPerformed(evt);
            }
        });
        PendingReport.add(PRRBNP);
        PRRBNP.setBounds(50, 50, 110, 30);

        PersonReceive.setBackground(new java.awt.Color(33, 95, 133));
        PersonReceive.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        PersonReceive.setLayout(null);

        jLabel110.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setText("Person  :");
        PersonReceive.add(jLabel110);
        jLabel110.setBounds(20, 20, 70, 30);

        PRCBPersonToReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRCBPersonToReceiveActionPerformed(evt);
            }
        });
        PersonReceive.add(PRCBPersonToReceive);
        PRCBPersonToReceive.setBounds(100, 20, 240, 30);

        jButton40.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton40.setText("Show All");
        jButton40.setRequestFocusEnabled(false);
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        PersonReceive.add(jButton40);
        jButton40.setBounds(360, 20, 170, 30);

        PendingReport.add(PersonReceive);
        PersonReceive.setBounds(20, 80, 1080, 620);

        PersonPay.setBackground(new java.awt.Color(33, 95, 133));
        PersonPay.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        PersonPay.setLayout(null);

        jLabel105.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(255, 255, 255));
        jLabel105.setText("Person  :");
        PersonPay.add(jLabel105);
        jLabel105.setBounds(20, 20, 70, 30);

        PRCBPersonToPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRCBPersonToPayActionPerformed(evt);
            }
        });
        PersonPay.add(PRCBPersonToPay);
        PRCBPersonToPay.setBounds(100, 20, 240, 30);

        jButton38.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton38.setText("Show All");
        jButton38.setRequestFocusEnabled(false);
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        PersonPay.add(jButton38);
        jButton38.setBounds(360, 20, 170, 30);

        PendingReport.add(PersonPay);
        PersonPay.setBounds(20, 80, 1080, 620);

        Receive.setBackground(new java.awt.Color(33, 95, 133));
        Receive.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        Receive.setLayout(null);

        jLabel103.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 255, 255));
        jLabel103.setText("Customer :");
        Receive.add(jLabel103);
        jLabel103.setBounds(20, 20, 70, 30);

        PRCBCustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRCBCustomerNameActionPerformed(evt);
            }
        });
        Receive.add(PRCBCustomerName);
        PRCBCustomerName.setBounds(100, 20, 240, 30);

        jButton28.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton28.setText("Show All");
        jButton28.setRequestFocusEnabled(false);
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        Receive.add(jButton28);
        jButton28.setBounds(360, 20, 170, 30);

        PendingReport.add(Receive);
        Receive.setBounds(20, 80, 1080, 620);

        Pay.setBackground(new java.awt.Color(33, 95, 133));
        Pay.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        Pay.setLayout(null);

        jLabel36.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText(" Supplier :");
        Pay.add(jLabel36);
        jLabel36.setBounds(20, 20, 70, 30);

        PRCBSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRCBSupplierNameActionPerformed(evt);
            }
        });
        Pay.add(PRCBSupplierName);
        PRCBSupplierName.setBounds(90, 20, 240, 30);

        jButton27.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton27.setText("Show All");
        jButton27.setRequestFocusEnabled(false);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        Pay.add(jButton27);
        jButton27.setBounds(360, 20, 170, 30);

        PendingReport.add(Pay);
        Pay.setBounds(20, 80, 1080, 620);

        jPanel1.add(PendingReport);
        PendingReport.setBounds(0, 0, 1120, 720);

        StockIn.setBackground(new java.awt.Color(33, 95, 133));
        StockIn.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stock In", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(51, 255, 255))); // NOI18N
        StockIn.setForeground(new java.awt.Color(255, 255, 255));
        StockIn.setLayout(null);

        SStockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProductID", "Category", "Name", "Brand", "Size", "UOM", "Quantity", "StockType", "ExpDate"
            }
        ));
        jScrollPane5.setViewportView(SStockTable);

        StockIn.add(jScrollPane5);
        jScrollPane5.setBounds(20, 190, 1080, 520);

        jPanel8.setBackground(new java.awt.Color(33, 95, 133));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jPanel8.setLayout(null);

        jLabel89.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Product Brand :");
        jPanel8.add(jLabel89);
        jLabel89.setBounds(410, 10, 100, 20);

        SCBProductCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCBProductCategoryActionPerformed(evt);
            }
        });
        jPanel8.add(SCBProductCategory);
        SCBProductCategory.setBounds(10, 30, 180, 30);

        jLabel90.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Product Category :");
        jPanel8.add(jLabel90);
        jLabel90.setBounds(10, 10, 120, 20);

        SCBProductBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCBProductBrandActionPerformed(evt);
            }
        });
        jPanel8.add(SCBProductBrand);
        SCBProductBrand.setBounds(410, 30, 180, 30);

        jLabel91.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Qyt :");
        jPanel8.add(jLabel91);
        jLabel91.setBounds(630, 30, 30, 30);
        jPanel8.add(STFQuantity);
        STFQuantity.setBounds(670, 30, 100, 30);

        jButton16.setBackground(new java.awt.Color(231, 76, 60));
        jButton16.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton16.setText("Search By Exp date");
        jButton16.setToolTipText("Show as per Exp Date");
        jButton16.setRequestFocusEnabled(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton16);
        jButton16.setBounds(880, 20, 180, 40);

        jButton17.setBackground(new java.awt.Color(231, 76, 60));
        jButton17.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-add-new-20 (1).png"))); // NOI18N
        jButton17.setToolTipText("Search by Quantity");
        jButton17.setRequestFocusEnabled(false);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton17);
        jButton17.setBounds(770, 30, 40, 30);

        jButton18.setBackground(new java.awt.Color(231, 76, 60));
        jButton18.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton18.setText("Search All");
        jButton18.setToolTipText("Show all Products");
        jButton18.setRequestFocusEnabled(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton18);
        jButton18.setBounds(900, 70, 140, 40);

        SCBProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCBProductNameActionPerformed(evt);
            }
        });
        jPanel8.add(SCBProductName);
        SCBProductName.setBounds(210, 30, 180, 30);

        jLabel104.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 255, 255));
        jLabel104.setText("Product Name :");
        jPanel8.add(jLabel104);
        jLabel104.setBounds(210, 10, 100, 20);

        StockIn.add(jPanel8);
        jPanel8.setBounds(20, 40, 1080, 120);

        SRBCurrentStock.setBackground(new java.awt.Color(33, 95, 133));
        SRBCurrentStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SRBCurrentStock.setForeground(new java.awt.Color(255, 255, 51));
        SRBCurrentStock.setText("Current Stock");
        SRBCurrentStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SRBCurrentStock.setRequestFocusEnabled(false);
        SRBCurrentStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SRBCurrentStockActionPerformed(evt);
            }
        });
        StockIn.add(SRBCurrentStock);
        SRBCurrentStock.setBounds(20, 170, 120, 20);

        SRBBackupStock.setBackground(new java.awt.Color(33, 95, 133));
        SRBBackupStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SRBBackupStock.setForeground(new java.awt.Color(51, 255, 255));
        SRBBackupStock.setText("Backup Stock");
        SRBBackupStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SRBBackupStock.setRequestFocusEnabled(false);
        SRBBackupStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SRBBackupStockActionPerformed(evt);
            }
        });
        StockIn.add(SRBBackupStock);
        SRBBackupStock.setBounds(150, 170, 120, 20);

        SRBBoth.setBackground(new java.awt.Color(33, 95, 133));
        SRBBoth.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SRBBoth.setForeground(new java.awt.Color(51, 255, 51));
        SRBBoth.setText("Both");
        SRBBoth.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SRBBoth.setRequestFocusEnabled(false);
        SRBBoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SRBBothActionPerformed(evt);
            }
        });
        StockIn.add(SRBBoth);
        SRBBoth.setBounds(280, 170, 60, 20);

        jPanel1.add(StockIn);
        StockIn.setBounds(0, 0, 1120, 720);

        SellReport.setBackground(new java.awt.Color(33, 95, 133));
        SellReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sell Report", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        SellReport.setLayout(null);

        jPanel10.setBackground(new java.awt.Color(33, 95, 133));
        jPanel10.setLayout(null);

        jLabel100.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setText("From ");
        jPanel10.add(jLabel100);
        jLabel100.setBounds(10, 10, 100, 20);

        jLabel101.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel101.setText("To");
        jPanel10.add(jLabel101);
        jLabel101.setBounds(210, 10, 120, 20);

        jLabel102.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(255, 255, 255));
        jLabel102.setText("Search By :");
        jPanel10.add(jLabel102);
        jLabel102.setBounds(630, 10, 80, 20);

        jButton26.setBackground(new java.awt.Color(231, 76, 60));
        jButton26.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton26.setText("Show All");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton26);
        jButton26.setBounds(860, 30, 120, 30);

        SellRDCFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                SellRDCFromPropertyChange(evt);
            }
        });
        jPanel10.add(SellRDCFrom);
        SellRDCFrom.setBounds(10, 30, 180, 30);

        SellRDCTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                SellRDCToPropertyChange(evt);
            }
        });
        jPanel10.add(SellRDCTo);
        SellRDCTo.setBounds(210, 30, 180, 30);

        SellRCBCustomerNames.setEditable(true);
        SellRCBCustomerNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SellRCBCustomerNamesActionPerformed(evt);
            }
        });
        jPanel10.add(SellRCBCustomerNames);
        SellRCBCustomerNames.setBounds(630, 30, 150, 30);

        jLabel35.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 51, 51));
        jLabel35.setText("*First Select Date");
        jPanel10.add(jLabel35);
        jLabel35.setBounds(10, 80, 140, 20);

        SPLCustomerType1.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        SPLCustomerType1.setForeground(new java.awt.Color(255, 255, 255));
        SPLCustomerType1.setText("Customer Type :");
        jPanel10.add(SPLCustomerType1);
        SPLCustomerType1.setBounds(450, 10, 104, 20);

        SellRCBCustomerType.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        SellRCBCustomerType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Retail", "Distributor", "Loyal" }));
        SellRCBCustomerType.setSelectedIndex(-1);
        SellRCBCustomerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SellRCBCustomerTypeActionPerformed(evt);
            }
        });
        jPanel10.add(SellRCBCustomerType);
        SellRCBCustomerType.setBounds(450, 30, 160, 30);

        jButton32.setBackground(new java.awt.Color(231, 76, 60));
        jButton32.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton32.setText("Export Table to PDF");
        jButton32.setRequestFocusEnabled(false);
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton32);
        jButton32.setBounds(450, 80, 200, 30);

        SellReport.add(jPanel10);
        jPanel10.setBounds(10, 40, 1080, 120);

        jPanel1.add(SellReport);
        SellReport.setBounds(0, 0, 1120, 720);

        ViewSupplier.setBackground(new java.awt.Color(33, 95, 133));
        ViewSupplier.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Supplier List", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Perpetua", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        ViewSupplier.setLayout(null);

        jPanel12.setBackground(new java.awt.Color(33, 95, 133));
        jPanel12.setLayout(null);

        jLabel107.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(255, 255, 255));
        jLabel107.setText("Search By :");
        jPanel12.add(jLabel107);
        jLabel107.setBounds(230, 20, 100, 30);

        VCCBSupplierName.setEditable(true);
        VCCBSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VCCBSupplierNameActionPerformed(evt);
            }
        });
        jPanel12.add(VCCBSupplierName);
        VCCBSupplierName.setBounds(350, 20, 350, 30);

        jButton20.setBackground(new java.awt.Color(231, 76, 60));
        jButton20.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton20.setText("Show All");
        jButton20.setRequestFocusEnabled(false);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton20);
        jButton20.setBounds(730, 20, 90, 30);

        ViewSupplier.add(jPanel12);
        jPanel12.setBounds(20, 40, 1080, 70);

        jLabel52.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 102));
        jLabel52.setText("Note :-  You Must Refresh the Page after Editing any Supplier Details ");
        ViewSupplier.add(jLabel52);
        jLabel52.setBounds(10, 20, 440, 20);

        jPanel1.add(ViewSupplier);
        ViewSupplier.setBounds(0, 0, 1120, 720);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(250, 0, 1120, 730);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DBLProductCategoryMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLProductCategoryMousePressed
        // TODO add your handling code here:
        if (DBLProductCategoryV) {
            running.set(false);
            if (SubF1MP == 0) {
                if (SubFon == 0) {
                    DBLCustomer.setVisible(false);
                    DBLSupplier.setVisible(false);
                    DBLInventory.setVisible(false);
                    DBLProductMaster.setVisible(false);
                    DBLStock.setVisible(false);
                    DBLReport.setVisible(false);
                    DBLPendingReport.setVisible(false);
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);

                    //add panel
                    Features.add(SubF1);
                    Features.repaint();
                    Features.revalidate();
                    SubF1MP++;
                    SubFon = 1;
                }
            } else {
                
                DBLCustomer.setVisible(true);
                DBLSupplier.setVisible(true);
                DBLInventory.setVisible(true);
                DBLProductMaster.setVisible(true);
                DBLStock.setVisible(true);
                DBLReport.setVisible(true);
                DBLPendingReport.setVisible(true);
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF1);
                Features.repaint();
                Features.revalidate();
                SubF1MP--;
                SubFon = 0;                
            }
        }
        
    }//GEN-LAST:event_DBLProductCategoryMousePressed

    private void DBLCustomerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLCustomerMousePressed
        // TODO add your handling code here:
        
        if (DBLCustomerV) {
            if (SubF2MP == 0) {
                if (SubFon == 0) {
                    
                    DBLSupplier.setVisible(false);
                    DBLInventory.setVisible(false);
                    DBLProductMaster.setVisible(false);
                    DBLStock.setVisible(false);
                    DBLReport.setVisible(false);
                    DBLPendingReport.setVisible(false);
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);

                    //add panel
                    Features.add(SubF2);
                    Features.repaint();
                    Features.revalidate();
                    SubF2MP++;
                    SubFon = 1;
                }
            } else {
                
                DBLSupplier.setVisible(true);
                DBLInventory.setVisible(true);
                DBLProductMaster.setVisible(true);
                DBLStock.setVisible(true);
                DBLReport.setVisible(true);
                DBLPendingReport.setVisible(true);
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF2);
                Features.repaint();
                Features.revalidate();
                SubF2MP--;
                SubFon = 0;
            }
        }
        
    }//GEN-LAST:event_DBLCustomerMousePressed

    private void DBLSupplierMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLSupplierMousePressed
        // TODO add your handling code here:
        
        if (DBLSupplierV) {
            if (SubF3MP == 0) {
                if (SubFon == 0) {
                    
                    DBLInventory.setVisible(false);
                    DBLProductMaster.setVisible(false);
                    DBLStock.setVisible(false);
                    DBLReport.setVisible(false);
                    DBLPendingReport.setVisible(false);
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);

                    //add panel
                    Features.add(SubF3);
                    Features.repaint();
                    Features.revalidate();
                    SubF3MP++;
                    SubFon = 1;
                }
            } else {
                DBLInventory.setVisible(true);
                DBLProductMaster.setVisible(true);
                DBLStock.setVisible(true);
                DBLReport.setVisible(true);
                DBLPendingReport.setVisible(true);
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF3);
                Features.repaint();
                Features.revalidate();
                SubF3MP--;
                SubFon = 0;
            }
        }
        
    }//GEN-LAST:event_DBLSupplierMousePressed

    private void DBLInventoryMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLInventoryMousePressed
        // TODO add your handling code here:
       
        if (DBLInventoryV) {
            running.set(false);
            if (SubF4MP == 0) {
                if (SubFon == 0) {
                    DBLProductMaster.setVisible(false);
                    DBLStock.setVisible(false);
                    DBLReport.setVisible(false);
                    DBLPendingReport.setVisible(false);
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);

                    //add panel
                    Features.add(SubF4);
                    Features.repaint();
                    Features.revalidate();
                    SubF4MP++;
                    SubFon = 1;
                }
            } else {
                DBLProductMaster.setVisible(true);
                DBLStock.setVisible(true);
                DBLReport.setVisible(true);
                DBLPendingReport.setVisible(true);
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF4);
                Features.repaint();
                Features.revalidate();
                SubF4MP--;
                SubFon = 0;
            }
        }
        
    }//GEN-LAST:event_DBLInventoryMousePressed

    private void DBLStockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLStockMousePressed
        // TODO add your handling code here:
        if (DBLStockV) {
            if (SubF5MP == 0) {
                if (SubFon == 0) {
                    DBLReport.setVisible(false);
                    DBLPendingReport.setVisible(false);
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);

                    //add panel
                    Features.add(SubF5);
                    Features.repaint();
                    Features.revalidate();
                    SubF5MP++;
                    SubFon = 1;
                }
            } else {
                DBLReport.setVisible(true);
                DBLPendingReport.setVisible(true);
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF5);
                Features.repaint();
                Features.revalidate();
                SubF5MP--;
                SubFon = 0;
            }
        }
        
    }//GEN-LAST:event_DBLStockMousePressed

    private void DBLReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLReportMousePressed
        // TODO add your handling code here:
        
        if (DBLReportV) {
            if (SubF6MP == 0) {
                if (SubFon == 0) {
                    DBLPendingReport.setVisible(false);
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);

                    //add panel
                    Features.add(SubF6);
                    Features.repaint();
                    Features.revalidate();
                    SubF6MP++;
                    SubFon = 1;
                }
            } else {
                DBLPendingReport.setVisible(true);
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF6);
                Features.repaint();
                Features.revalidate();
                SubF6MP--;
                SubFon = 0;
            }
        }
        
    }//GEN-LAST:event_DBLReportMousePressed

    private void DBLPendingReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLPendingReportMousePressed
        // TODO add your handling code here:
        
        if (DBLPendingReportV) {
            if (SubF7MP == 0) {
                if (SubFon == 0) {
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);

                    //add panel
                    Features.add(SubF7);
                    Features.repaint();
                    Features.revalidate();
                    SubF7MP++;
                    SubFon = 1;
                }
            } else {
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF7);
                Features.repaint();
                Features.revalidate();
                SubF7MP--;
                SubFon = 0;
            }
        }
        
    }//GEN-LAST:event_DBLPendingReportMousePressed

    private void DBLSettingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLSettingMousePressed
        // TODO add your handling code here:
        if (DBLSettingV) {
            if (SubF10MP == 0) {
                if (SubFon == 0) {

                    //add panel
                    Features.add(SubF10);
                    Features.repaint();
                    Features.revalidate();
                    SubF10MP++;
                    SubFon = 1;
                }
            } else {
                
                Features.remove(SubF10);
                Features.repaint();
                Features.revalidate();
                SubF10MP--;
                SubFon = 0;
            }
        }

    }//GEN-LAST:event_DBLSettingMousePressed

    private void DBPCLAddProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBPCLAddProductMousePressed
        // TODO add your handling code here:
        if (DBPCLAddProductV) {
            
            //remove panel
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(ProductEntery);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            try {
                db.setProductCategory(PECBProductCategory);
                db.setProductName(PECBProductName);
                db.setProductBrand(PECBProductBrand);
                db.setProductSizeandUOM(PECBSize,PECBUOM);
                PETFBarcode.setText("");
                PETADiscription.setText("");
                PETFSellingPrice.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
//            System.out.println(running.get());
            if(!running.get())
            {
                running.set(true);
            }
        }
       
    }//GEN-LAST:event_DBPCLAddProductMousePressed

    private void DBPCLViewProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBPCLViewProductMousePressed
        // TODO add your handling code here:
        if (DBPCLViewProductV) {
            running.set(false);
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(EmployeePermission);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            
        }
    }//GEN-LAST:event_DBPCLViewProductMousePressed

    private void DBLHomeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLHomeMousePressed
        // TODO add your handling code here:
        if (DBLHomeV) {
            running.set(false);
            //remove panel
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(Home);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            HRBCurrent.setSelected(true);
            
            Vector<String> ProductD = new Vector<String>();
            HTMStock = (DefaultTableModel) HTStock.getModel();
            try {
                ProductD = db.getOStock();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            HTMStock.setRowCount(0);
            for (int i = 0, j = 0; i < ProductD.size() / 5; j += 5, i++) {
                Object[] obj = {ProductD.get(j), ProductD.get(j + 1), ProductD.get(j + 2), ProductD.get(j + 3), ProductD.get(j + 4)};
                
                HTMStock.addRow(obj);
            }
            
//            Vector<String> ProductD = new Vector<String>();
            jPanelSlider1.nextPanel(10,ProfitDetails, jPanelSlider1.left);
            DefaultTableModel DBTMMonthSoldProduct = (DefaultTableModel) DBTMonthSoldProduct.getModel();
            try {
                ProductD = db.getMonthSoldProduct();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            DBTMMonthSoldProduct.setRowCount(0);
            for (int i = 0, j = 0; i < ProductD.size() / 6; j += 6, i++) {
                Object[] obj = {ProductD.get(j), ProductD.get(j + 1), ProductD.get(j + 2), ProductD.get(j + 3), ProductD.get(j + 4),ProductD.get(j + 5)};

                DBTMMonthSoldProduct.addRow(obj);
            }
            
            int Year = 0;
            int Month = 0;
            try {
                Month = DBMCMonthProfit.getMonth();
                Year = DBYCYearProfit.getYear();
            } catch (Exception e) {
            }
    //        System.out.println(Month+1);
            String Date = Year+"-"+(Month+1)+"-"+"01";

            String[] Profit = new String[4];
            try {
                Profit = db.getMothProfit(Date);
            } catch (SQLException sQLException) {
            }catch (NullPointerException ex) {
                Profit[0] = "";
                Profit[1] = "";
            }
            try {
                int index = 0;
            if(Profit[0].equalsIgnoreCase("") && Profit[2].equalsIgnoreCase(""))
            {
                index = Profit[0].indexOf(".");
                Profit[0] = Profit[0].substring(0, index+3);
                index = Profit[2].indexOf(".");
                Profit[2] = Profit[2].substring(0, index+3);
            }
                DBLMonthPofitPercent.setText(Profit[0] + "%");
                DBLMonthPofitRupee.setText(Profit[1] + "/-");
                DBLYearPofitPercent.setText(Profit[2] + "%");
                DBLYearPofitRupee.setText(Profit[3] + "/-");
            } catch (Exception e) {
            }
            
        }
    }//GEN-LAST:event_DBLHomeMousePressed

    private void DBLProductMasterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLProductMasterMousePressed
        // TODO add your handling code here:
        if (DBLProductMasterV) {
            if (SubF8MP == 0) {
                if (SubFon == 0) {
                    DBLStock.setVisible(false);
                    DBLReport.setVisible(false);
                    DBLPendingReport.setVisible(false);
                    DBLSetting.setVisible(false);
                    DBLEmployee.setVisible(false);
                    //add panel
                    Features.add(SubF9);
                    Features.repaint();
                    Features.revalidate();
                    SubF8MP++;
                    SubFon = 1;
                }
            } else {
                DBLStock.setVisible(true);
                DBLReport.setVisible(true);
                DBLPendingReport.setVisible(true);
                DBLSetting.setVisible(true);
                DBLEmployee.setVisible(true);
                Features.remove(SubF9);
                Features.repaint();
                Features.revalidate();
                SubF8MP--;
                SubFon = 0;
            }
        }
    }//GEN-LAST:event_DBLProductMasterMousePressed

    private void DBPMLProductMasterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBPMLProductMasterMousePressed
        // TODO add your handling code here:
        if (DBPMLProductMasterV) {
            if(ProductMasterCount == 0) {
                pm.setTitle("Product Master");
                pm.setLocation(200, 10);
                pm.setResizable(false);
                pm.setSize(841, 690);
                pm.setVisible(true);
                pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.setEnabled(false);
            }
        }
    }//GEN-LAST:event_DBPMLProductMasterMousePressed

    private void PPBAddSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPBAddSupplierActionPerformed
        // TODO add your handling code here:
        sp.setTitle("Add Customer");
        sp.setLocation(400, 100);
        sp.setResizable(false);
        sp.setSize(465, 539);
        sp.setVisible(true);
        sp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }//GEN-LAST:event_PPBAddSupplierActionPerformed

    private void DBILPurchaseProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBILPurchaseProductMousePressed
        // TODO add your handling code here:
        if ( DBILPurchaseProductV) {
            running.set(false);
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(ProductPurchase);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            for (int i = 0; i <= datacount; i++) {
                data[0][i] = "";                
                data[1][i] = "";
                data[2][i] = "";
            }
            for (int i = 0; i <= ProductDateCount; i++) {
                ProductDate[0][i] = "";
                ProductDate[1][i] = "";
                ProductDate[2][i] = "";
            }
            datacount = 0;
            ProductDateCount = 0;
            //set Supplier Details
            String date = IS.dateFormat.format(IS.dt);
            
            PPInvoiceDate.setText(date);
            
            String pesawat[] = null;
            try {
                pesawat = db.getSupplierNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PPCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
            PPCBSupplierName.setSelectedIndex(-1);
            PPCBSupplierName.setSelectedIndex(-1);
            TFMobileNo.setText("");
            TFAadharNo.setText("");
            TFGSTINNo.setText("");
            TFPreDues.setText("");
            
            PPTFNetAmount.setText("00.0");
            PPTFTotalAmount.setText("00.0");
            PPTFDues.setText("00.0");
            PPLGrossAmount.setText("00.0");
            PPTFDiscount.setText("0");
            PPTFPaidAmount.setText("0");
            PPCBPaymentMode.setSelectedIndex(-1);
            
            try {
                //set Product Details
                db.setProductCategory(PPCBProductCategory);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            PPCBProductName.setEnabled(false);
            PPCBProductBrand.setEnabled(false);
            PPCBSize.setEnabled(false);
            PPCBUOM.setEnabled(false);
            
            try {
                this.PPInvoiceID = db.getInvoiceID();
            } catch (SQLException ex) {
                this.PPInvoiceID = "";
            }
            PPTFINvoiceID.setText(this.PPInvoiceID);
        }
          
        
    }//GEN-LAST:event_DBILPurchaseProductMousePressed
    
    private void DBILSellProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBILSellProductMousePressed
        // TODO add your handling code here:
        if (DBILSellProductV) {
            running.set(false);
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(SellProduct);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            for (int i = 0; i <= datacount; i++) {
                data[0][i] = "";                
                data[1][i] = "";
                data[2][i] = "";
            }
            
            datacount = 0;
            //set Supplier Details
            String date = IS.dateFormat.format(IS.dt);
            
            SPInvoiceDate.setText(date);
            
            String pesawat[] = null;
            try {
                pesawat = db.getCustomerNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SPCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
            SPCBCustomerName.setSelectedIndex(-1);
            
            SPTFMobileNo.setText("");
            SPTFAadharNo.setText("");
            SPTFGSTINNo.setText("");
            SPTFPreDues.setText("");
            
            SPTFNetAmount.setText("00.0");
            SPTFTotalAmount.setText("00.0");
            SPTFDues.setText("00.0");
            SPLGrossAmount.setText("00.0");
            SPTFDiscount.setText("0");
            SPTFPaidAmount.setText("0");
            SPCBPaymentMode.setSelectedIndex(-1);
            
            try {
                //set Product Details
                db.setProductCategory(SPCBProductCategory);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            SPCBProductName.setEnabled(false);
            SPCBProductBrand.setEnabled(false);
            SPCBSize.setEnabled(false);
            SPCBUOM.setEnabled(false);
            
            try {
                this.SPInvoiceID = db.getSellInvoiceID();
            } catch (SQLException ex) {
                this.SPInvoiceID = "";
            }
            SPTFINvoiceID.setText(this.SPInvoiceID);
        }
    }//GEN-LAST:event_DBILSellProductMousePressed

    private void jScrollBar1AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_jScrollBar1AdjustmentValueChanged
        // TODO add your handling code here:
        DashBoard db = this;
        
        CheckPanel();
        x = jScrollBar1.getValue();
        //System.out.println(jScrollBar1.getMinimum()+" "+jScrollBar1.getMaximum());
        //System.out.println("x-axis = "+x);
        try {
            mypanel.setLocation(-x, -y);
        } catch (Exception e) {
            
        }
        
        jScrollBar1.show();
        jScrollBar2.show();
    }//GEN-LAST:event_jScrollBar1AdjustmentValueChanged

    public void Resize()
    {
        DashBoard db = this;
        int height = db.getHeight();
        int width = db.getWidth();
        CheckPanel();
        Features.setLocation(0,0);
        jPanel1.setLocation(250,0);
        try {
            mypanel.setLocation(0, 0);
        } catch (Exception e) {
        }
        SB1X = 250;
        SB1Y = 730;
        SB1Width = 1090;
        SB1Height = 20;
        maxx = 45;
        SB1WidthP = 286 ;
        jScrollBar1.setBounds(250,730,1090, 20);
        jScrollBar1.setMaximum(jScrollBar1.getMaximum()+FeaturesX);
        jScrollBar1.setMinimum(0);
        
    }
    public void Changesize()
    {
        DashBoard db = this;
        int height = db.getHeight();
        int width = db.getWidth();
        CheckPanel();
        Features.setLocation(-FeaturesX,0);
        jPanel1.setLocation((250-FeaturesX),0);
        try {
            mypanel.setLocation(0, 0);
        } catch (Exception e) {
        }
        SB1X = SB1X - FeaturesX;
        SB1Y = 730;
        SB1Width = jScrollBar1.getWidth()-FeaturesX;
        SB1Height = 20;
        SB1WidthP = 66;
        jScrollBar1.setBounds((SB1X-FeaturesX),SB1Y,(jScrollBar1.getWidth()+FeaturesX), jScrollBar1.getHeight());
        maxx = maxx-220;
        jScrollBar1.setMaximum(jScrollBar1.getMaximum()-FeaturesX); 
        jScrollBar1.setMinimum(0);
    }
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        DashBoard db = this;
        int height = db.getHeight();
        int width = db.getWidth();
        if(width <= 850)
        {
            if(SizeCall == false)
            {
                Changesize();
            }
            SizeCall = true;
        }else{
            if(SizeCall == true)
            {
                Resize();
            }
            SizeCall = false;
        }
//        System.out.println(height);
//        System.out.println(width);
//        jScrollBar1.setVisible(true);
//        jScrollBar2.setVisible(true);
        
        jScrollBar1.setBounds(SB1X, height-59, width-SB1WidthP, jScrollBar1.getHeight());
        jScrollBar2.setBounds(width-36, 0, jScrollBar2.getWidth(), height-40);
        int SB1Value = 1366-width;
        int SB2Value = 720-height;
        jScrollBar1.setMaximum(maxx+SB1Value);
        jScrollBar1.setMinimum(0);
        jScrollBar2.setMaximum(maxy+SB2Value);
        jScrollBar2.setMinimum(0);
       
    }//GEN-LAST:event_formComponentResized

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
        //System.out.println(evt.getNewState());
        DashBoard db = this;
        int oldState = evt.getOldState();
        int newState = evt.getNewState();
        int height = db.getHeight();
        int width = db.getWidth();
        if ((oldState & db.MAXIMIZED_BOTH) == 0 && (newState & db.MAXIMIZED_BOTH) != 0) {
            //System.out.println("Frame was maximized");
            jScrollBar1.setVisible(false);
            jScrollBar2.setVisible(false);
            WindowState = true;
            //System.out.println(jScrollBar1.getMinimum()+" "+jScrollBar1.getMaximum());
        } else if ((oldState & db.MAXIMIZED_BOTH) != 0 && (newState & db.MAXIMIZED_BOTH) == 0) {
            //System.out.println("Frame was minimized");
            jScrollBar1.setVisible(true);
            jScrollBar2.setVisible(true);
            WindowState = false;
            int SB1Value = 1366-width;
            int SB2Value = 720-height;
            jScrollBar1.setMaximum(maxx+SB1Value);        
            jScrollBar1.setMinimum(0);
            jScrollBar2.setMaximum(maxy+SB2Value);
            jScrollBar2.setMinimum(0);
            jScrollBar1.setBounds(SB1X, height-59, width-SB1WidthP, jScrollBar1.getHeight());
            jScrollBar2.setBounds(width-36, 0, jScrollBar2.getWidth(), height-40);
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        // TODO add your handling code here:
        DashBoard db = this;
        int height = db.getHeight();
        int width = db.getWidth();
        
        //System.out.println(height);
        //System.out.println(width);
        jScrollBar1.setVisible(true);
        jScrollBar2.setVisible(true);
        jScrollBar1.setBounds(SB1X, height-59, width-SB1WidthP, jScrollBar1.getHeight());
        jScrollBar2.setBounds(width-36, 0, jScrollBar2.getWidth(), height-40);
        
        int SB1Value = 1366-width;
        int SB2Value = 720-height;
        jScrollBar1.setMaximum(maxx+SB1Value);
        jScrollBar1.setMinimum(0);
        jScrollBar2.setMaximum(maxy+SB2Value);
        jScrollBar2.setMinimum(0);
    }//GEN-LAST:event_formComponentMoved

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        DashBoard db = this;
        int oldState = evt.getOldState();
        int newState = evt.getNewState();
        int height = db.getHeight();
        int width = db.getWidth();
        if (WindowState == true) {
            //System.out.println("Frame was maximized");
            jScrollBar1.setVisible(false);
            jScrollBar2.setVisible(false);
            
            //System.out.println(jScrollBar1.getMinimum()+" "+jScrollBar1.getMaximum());
        } else{
            //System.out.println("Frame was minimized");
            jScrollBar1.setVisible(true);
            jScrollBar2.setVisible(true);
            jScrollBar1.setBounds(SB1X, height-59, width-SB1WidthP, jScrollBar1.getHeight());
            jScrollBar2.setBounds(width-36, 0, jScrollBar2.getWidth(), height-40);

            int SB1Value = 1366-width;
            int SB2Value = 720-height;
            jScrollBar1.setMaximum(maxx+SB1Value);
            jScrollBar1.setMinimum(0);
            jScrollBar2.setMaximum(maxy+SB2Value);
            jScrollBar2.setMinimum(0);
        }
    }//GEN-LAST:event_formWindowActivated
    
    public void CheckPanel()
    {
//        System.out.println(SaleProduct.isShowing());
//        System.out.println(StockIn.isShowing());
//        System.out.println(Product.isShowing());
//        System.out.println(ProductEntery.isShowing());
//        System.out.println(ProductMasters.isShowing());
//        System.out.println(ProductPurchase.isShowing());
//        System.out.println(PurchaseReport.isShowing());
        if(SellProduct.isShowing())
        {
            mypanel = SellProduct;
        }else if(StockIn.isShowing()){
            mypanel = StockIn;
        }else if(EmployeePermission.isShowing()){
            mypanel = EmployeePermission;
        }else if(ProductEntery.isShowing()){
            mypanel = ProductEntery;
        }else if(EmployeeAuthentication.isShowing()){
            mypanel = EmployeeAuthentication;
        }else if(ProductPurchase.isShowing()){
            mypanel = ProductPurchase;
        }else if(PurchaseReport.isShowing()){
            mypanel = PurchaseReport;
        }else if(SellReport.isShowing()){
            mypanel = SellReport;
        }else if(ViewCustomer.isShowing()){
            mypanel = ViewCustomer;
        }else if(ViewSupplier34.isShowing()){
            mypanel = ViewSupplier34;
        }else if(Home.isShowing()){
            mypanel = Home;
        }else if(TransactionReport.isShowing()){
            mypanel = TransactionReport;
        }else if(ProfileUpdate.isShowing()){
            mypanel = ProfileUpdate;
        }else{
            mypanel = null;
        }
    }
    private void jScrollBar2AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_jScrollBar2AdjustmentValueChanged
        // TODO add your handling code here:
        DashBoard db = this;
        
        CheckPanel();
        y = jScrollBar2.getValue();
        try {
            mypanel.setLocation(-x, -y);
        } catch (Exception e) {
            
        }
        
        jScrollBar1.show();
        jScrollBar2.show();
    }//GEN-LAST:event_jScrollBar2AdjustmentValueChanged

    private void DBSLAddSupplierMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBSLAddSupplierMousePressed
        // TODO add your handling code here:
        
        if (DBSLAddSupplierV) {
            sp.setTitle("Add Customer");
            sp.setLocation(400, 100);
            sp.setResizable(false);
            sp.setSize(465, 539);
            sp.setVisible(true);
            sp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setEnabled(false);
        }
    }//GEN-LAST:event_DBSLAddSupplierMousePressed

    private void DBCLAddCustomerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBCLAddCustomerMousePressed
        // TODO add your handling code here:
        if (DBCLAddCustomerV) {
            cm.setTitle("Add Customer");
            cm.setLocation(400, 100);
            cm.setResizable(false);
            cm.setSize(465, 539);
            cm.setVisible(true);
            cm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setEnabled(false);
        }
    }//GEN-LAST:event_DBCLAddCustomerMousePressed

    private void DBSLStockInMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBSLStockInMousePressed
        // TODO add your handling code here:
        if (DBSLStockInV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(StockIn);
            jPanel1.repaint();
            jPanel1.revalidate();

            //Stock PCComboBox
            try {
                //set Product Details
                db.setProductCategory(SCBProductCategory);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            SCBProductName.setEnabled(false);
            SCBProductBrand.setEnabled(false);
            
            try {
                db.ProductStockT(SStockTable);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DBSLStockInMousePressed

    private void PPCBProductCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPCBProductCategoryActionPerformed
        try {
            // TODO add your handling code here:
            String ProductCategory = null;
            ProductCategory = PPCBProductCategory.getItemAt(PPCBProductCategory.getSelectedIndex()).toString();
            db.setProductName(PPCBProductName, ProductCategory);
            PPCBProductName.setEnabled(true);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_PPCBProductCategoryActionPerformed

    private void DBRLPurchaseReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBRLPurchaseReportMousePressed
        // TODO add your handling code here:
        if (DBRLPurchaseReportV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(PurchaseReport);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            String pesawat[] = null;
            try {
                pesawat = db.getSupplierNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PurRCBSupplierNames.setModel(new DefaultComboBoxModel(pesawat));
            PurRCBSupplierNames.setSelectedIndex(-1);

            //Load Table Data
            //Removing Data From Table
            PurRTMSupplierIR.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.PurRSupplierInvoiceD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0, j = 0; j < (v1.size()) / 9; j++, i += 9) {
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 8), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), v1.get(i + 7), img1};
                
                PurRTMSupplierIR.addRow(obj);
            }
        }
        
    }//GEN-LAST:event_DBRLPurchaseReportMousePressed

    private void jScrollBar2MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jScrollBar2MouseWheelMoved
        // TODO add your handling code here:
        DashBoard db = this;
        y = jScrollBar2.getValue();
        if (evt.getWheelRotation() < 0) {
            if(y > 0 )
            {
                y = y - 10;
                if(y < 0)
                {
                    y = 0;
                }
                jScrollBar2.setValue(y);
                //System.out.println(y);
            }
        } else {
            int max = jScrollBar2.getMaximum();
            if(y < max )
            {
                y = y + 10;
                if(y > max)
                {
                    y = max;
                }
                //System.out.println(y);
                jScrollBar2.setValue(y);
            }
        }
    
        CheckPanel();
        try {
            mypanel.setLocation(-x, -y);
        } catch (Exception e) {
            
        }
        
        jScrollBar1.show();
        jScrollBar2.show();
    }//GEN-LAST:event_jScrollBar2MouseWheelMoved

    private void jPanel1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jPanel1MouseWheelMoved
        // TODO add your handling code here:
        DashBoard db = this;
        y = jScrollBar2.getValue();
        if (evt.getWheelRotation() < 0) {
            if(y > 0 )
            {
                y = y - 10;
                if(y < 0)
                {
                    y = 0;
                }
                jScrollBar2.setValue(y);
                //System.out.println(y);
            }
        } else {
            int max = jScrollBar2.getMaximum();
            if(y < max )
            {
                y = y + 10;
                if(y > max)
                {
                    y = max;
                }
                //System.out.println(y);
                jScrollBar2.setValue(y);
            }
        }
    
        CheckPanel();
        try {
            mypanel.setLocation(-x, -y);
        } catch (Exception e) {
            
        }
        
        jScrollBar1.show();
        jScrollBar2.show();
    }//GEN-LAST:event_jPanel1MouseWheelMoved

    private void DBRLSellReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBRLSellReportMousePressed
        // TODO add your handling code here:
        if (DBRLSellReportV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(SellReport);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            String pesawat[] = null;
            try {
                pesawat = db.getCustomerNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SellRCBCustomerNames.setModel(new DefaultComboBoxModel(pesawat));
            SellRCBCustomerNames.setSelectedIndex(-1);

            //Load Table Data
            //Removing Data From Table
            SellRTMCustomerIR.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.PurRCustomerInvoiceD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0, j = 0; j < (v1.size()) / 9; j++, i += 9) {
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 8), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), v1.get(i + 7), img1};
                
                SellRTMCustomerIR.addRow(obj);
                
            }
        }
    }//GEN-LAST:event_DBRLSellReportMousePressed

    private void PRCBSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRCBSupplierNameActionPerformed
        // TODO add your handling code here:
        //Removing Data From Table
        PRTMSuppliermodel.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PRSuppliersD(PRCBSupplierName.getItemAt(PRCBSupplierName.getSelectedIndex()).toString());
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException ex)
        {
            
        }



        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};

            PRTMSuppliermodel.addRow(obj);
        }
    }//GEN-LAST:event_PRCBSupplierNameActionPerformed

    private void PRCBCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRCBCustomerNameActionPerformed
        // TODO add your handling code here:
        //Removing Data From Table
        PRTMCustomermodel.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PRCustomersD(PRCBCustomerName.getItemAt(PRCBCustomerName.getSelectedIndex()).toString());
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException ex)
        {
            
        }



        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};

            PRTMCustomermodel.addRow(obj);
        }
        
    }//GEN-LAST:event_PRCBCustomerNameActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        PendingReport.removeAll();
        PendingReport.repaint();
        PendingReport.revalidate();
        
        
        if( PRPanels == 0)
        {
            String pesawat2[] = null;
            try {
                pesawat2 = db.getSupplierNames();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }


            PRCBSupplierName.setModel(new DefaultComboBoxModel(pesawat2));
            PRCBSupplierName.setSelectedIndex(-1);
            
            //Removing Data From Table
            PRTMSuppliermodel.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.PRSuppliersD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }



            for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
            {
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};

                PRTMSuppliermodel.addRow(obj);
            }
            //add panel
            PendingReport.add(Pay);
            PendingReport.add(jButton23);
            PendingReport.add(jButton24);
            PendingReport.add(PRRBCS);
            PendingReport.add(PRRBNP);
            PendingReport.repaint();
            PendingReport.revalidate();
        }else{
            
            String pesawat1[] = null;
            try {
                pesawat1 = db.getPersonsNamesToPay();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            PRCBPersonToPay.setModel(new DefaultComboBoxModel(pesawat1));
            PRCBPersonToPay.setSelectedIndex(-1);
            
            //Removing Data From Table
            PRTMPersonToPay.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.AllPersonToPayD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }



            for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
            {
                ImageIcon img2 = new ImageIcon("Delete.png");
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};

                PRTMPersonToPay.addRow(obj);
            }
            
            //add panel
            PendingReport.add(PersonPay);
            PendingReport.add(jButton23);
            PendingReport.add(jButton24);
            PendingReport.add(PRRBCS);
            PendingReport.add(PRRBNP);
            PendingReport.repaint();
            PendingReport.revalidate();
            
        }
    }//GEN-LAST:event_jButton23ActionPerformed
    void RefreshSupplierName()
    {
        String pesawat2[] = null;
        try {
            pesawat2 = db.getSupplierNames();
        } catch (SQLException ex) {
            //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }


        PRCBSupplierName.setModel(new DefaultComboBoxModel(pesawat2));
        PRCBSupplierName.setSelectedIndex(-1);
        
        //Removing Data From Table
        PRTMSuppliermodel.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PRSuppliersD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }



        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};

            PRTMSuppliermodel.addRow(obj);
        }

    }
    void RefreshCustomerName()
    {
        String pesawat2[] = null;
        try {
            pesawat2 = db.getCustomerNames();
        } catch (SQLException ex) {
            //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }


        PRCBCustomerName.setModel(new DefaultComboBoxModel(pesawat2));
        PRCBCustomerName.setSelectedIndex(-1);
        
        //Removing Data From Table
        PRTMCustomermodel.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PRCustomersD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }



        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};

            PRTMCustomermodel.addRow(obj);
        }
    }
    void RefreshPersonToPay()
    {
        String pesawat1[] = null;
        try {
            pesawat1 = db.getPersonsNamesToPay();
        } catch (SQLException ex) {
            //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        PRCBPersonToPay.setModel(new DefaultComboBoxModel(pesawat1));
        PRCBPersonToPay.setSelectedIndex(-1);
        
        //Removing Data From Table
        PRTMPersonToPay.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.AllPersonToPayD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }



        for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
        {
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};

            PRTMPersonToPay.addRow(obj);
        }
    }
    void RefreshPersonToReceive()
    {
        String pesawat1[] = null;
        try {
            pesawat1 = db.getPersonsNamesToReceive();
        } catch (SQLException ex) {
            //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        PRCBPersonToReceive.setModel(new DefaultComboBoxModel(pesawat1));
        PRCBPersonToReceive.setSelectedIndex(-1);
        
        //Removing Data From Table
        PRTMPersonToReceive.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.AllPersonToReceiveD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }



        for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
        {
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};

            PRTMPersonToReceive.addRow(obj);
        }
    }
    
    
    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        PendingReport.removeAll();
        PendingReport.repaint();
        PendingReport.revalidate();
        
        
        
        if( PRPanels == 0)
        {
            
            
            String pesawat1[] = null;
            try {
                pesawat1 = db.getCustomerNames();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }

            PRCBCustomerName.setModel(new DefaultComboBoxModel(pesawat1));
            PRCBCustomerName.setSelectedIndex(-1);
            
            //Removing Data From Table
            PRTMCustomermodel.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.PRCustomersD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }



            for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
            {
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};

                PRTMCustomermodel.addRow(obj);
            }
            
            
            //add panel
            PendingReport.add(Receive);
            PendingReport.add(jButton23);
            PendingReport.add(jButton24);
            PendingReport.add(PRRBCS);
            PendingReport.add(PRRBNP);
            PendingReport.repaint();
            PendingReport.revalidate();

        }else{
            
            
            String pesawat1[] = null;
            try {
                pesawat1 = db.getPersonsNamesToReceive();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            PRCBPersonToReceive.setModel(new DefaultComboBoxModel(pesawat1));
            PRCBPersonToReceive.setSelectedIndex(-1);
            
            //Removing Data From Table
            PRTMPersonToReceive.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.AllPersonToReceiveD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }



            for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
            {
                ImageIcon img2 = new ImageIcon("Delete.png");
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};

                PRTMPersonToReceive.addRow(obj);
            }
            
            //add panel
            PendingReport.add(PersonReceive);
            PendingReport.add(jButton23);
            PendingReport.add(jButton24);
            PendingReport.add(PRRBCS);
            PendingReport.add(PRRBNP);
            PendingReport.repaint();
            PendingReport.revalidate();
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void DBPRLPendingReporetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBPRLPendingReporetMousePressed
        // TODO add your handling code here:
        
        
        if (DBPRLPendingReporetV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(PendingReport);            
            jPanel1.repaint();
            jPanel1.revalidate();
            PendingReport.removeAll();
            PendingReport.repaint();
            PendingReport.revalidate();

            //add panel
            PendingReport.add(Pay);
            PendingReport.add(jButton23);
            PendingReport.add(jButton24);
            PendingReport.add(PRRBCS);
            PendingReport.add(PRRBNP);
            PendingReport.repaint();
            PendingReport.revalidate();
            
            PRRBCS.setSelected(true);
            String pesawat1[] = null;
            try {
                pesawat1 = db.getCustomerNames();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PRCBCustomerName.setModel(new DefaultComboBoxModel(pesawat1));
            PRCBCustomerName.setSelectedIndex(-1);
            
            String pesawat2[] = null;
            try {
                pesawat2 = db.getSupplierNames();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PRCBSupplierName.setModel(new DefaultComboBoxModel(pesawat2));
            PRCBSupplierName.setSelectedIndex(-1);

            //Removing Data From Table
            PRTMSuppliermodel.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.PRSuppliersD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0, j = 0; j < (v1.size()) / 5; j++, i += 5) {
//            System.out.print(v1.get(i)+"   ||   ");
//            System.out.print(v1.get(i+1)+"   ||   ");
//            System.out.print(v1.get(i+2)+"   ||   ");
//            System.out.print(v1.get(i+3)+"   ||   ");
//            System.out.print(v1.get(i+4)+"   ||   ");
//            System.out.print(v1.get(i+5)+"   ||   ");
//            System.out.print(v1.get(i+6)+"   ||   ");
//            System.out.print(v1.get(i+7));
//            System.out.println();

                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), img1};
                
                PRTMSuppliermodel.addRow(obj);
                
            }
        }
        
    }//GEN-LAST:event_DBPRLPendingReporetMousePressed

    private void TFGSTINNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFGSTINNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFGSTINNoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String[] Product = new String[15];
        
        //Getting Values
        try {
            Product[0] = PPCBProductCategory.getSelectedItem().toString();
        } catch (NullPointerException e) {
            Product[0] = "";
        }
        try {
            Product[1] = PPCBProductName.getSelectedItem().toString();
            } catch (NullPointerException e) {
            Product[1] = "";
        }
        try {
            Product[2] = PPCBProductBrand.getSelectedItem().toString();
        } catch (NullPointerException e) {
            Product[2] = "";
        }
        try {
            Product[5] = PPCBSize.getSelectedItem().toString();
        } catch (NullPointerException e) {
            Product[5] = "";
        }
        try {
            Product[6] = PPCBUOM.getSelectedItem().toString();
        } catch (NullPointerException e) {
            Product[6] = "";
        }
        try {
            Product[7] = IS.dateFormat.format(PPDCExpDate.getDate());
        } catch (NullPointerException e) {
            Product[7] = "";
        }
        try {
            Product[8] = IS.dateFormat.format(PPDCMfgDate.getDate());
        } catch (NullPointerException e) {
            Product[8] = "";
        }
        
        try {
            int Quantity = Integer.parseInt(PPTFQuantity.getText());
            Product[9] = String.valueOf(Quantity);
        } catch (NumberFormatException e) {
            Product[9] = "";
        }
        try {
            long SellingRate = Long.parseLong(PPTFSellingRate.getText());
            Product[4] = String.valueOf(SellingRate);
        } catch (NumberFormatException e) {
            Product[4] = "";
        }
        try {
            double PurchaseRate = Double.valueOf(PPTFPurchaseRate.getText());
            Product[3] = String.valueOf(PurchaseRate);
        } catch (NumberFormatException e) {
            Product[3] = "";
        }
        try{
            int gst = Integer.parseInt(PPTFGST.getText());
            if(gst<=100)
            {
                Product[10] = String.valueOf(gst);
            }else{
                Product[10] = "";
            }
        }catch(NumberFormatException e) {
            Product[10] = "";
        }
        try{
            Product[11] = this.ProductID;
        }catch(NullPointerException e){
            Product[11] = "";
        }
        
        try{
            Product[13] = IS.dateFormat.format(PPDCEXPNotifyDate.getDate());
        }catch(NullPointerException e){
            Product[13] = "";
        }
        for(int i=0;i<Product.length;i++)
        {
            try {
                Product[i] = Product[i].trim();
            } catch (NullPointerException e) {
                Product[i] = "";
            }
        }
        
        //Validation
        if(Product[0] == null || Product[0].equals(""))
        {
            PPLProductCategory.setForeground(Color.red);
        }else if(Product[1] == null || Product[1].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.red);
        }else if(Product[2] == null || Product[2].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.red);
        }else if(Product[5] == null || Product[5].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.red);
        }else if(Product[6] == null || Product[6].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.red);
        }else if(Product[9] == null || Product[9].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.red);
        }else if(Product[3] == null || Product[3].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.WHITE);
            PPLPurchaseRate.setForeground(Color.red);
        }else if(Product[4] == null || Product[4].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.WHITE);
            PPLPurchaseRate.setForeground(Color.WHITE);
            PPLSellingRate.setForeground(Color.red);
        }else if(Product[7] == null || Product[7].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.WHITE);
            PPLPurchaseRate.setForeground(Color.WHITE);
            PPLSellingRate.setForeground(Color.WHITE);
            PPLExpDate.setForeground(Color.red);
        }else if(Product[8] == null || Product[8].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.WHITE);
            PPLPurchaseRate.setForeground(Color.WHITE);
            PPLSellingRate.setForeground(Color.WHITE);
            PPLExpDate.setForeground(Color.WHITE);
            PPLMfgDate.setForeground(Color.red);
        }else if(Product[10] == null || Product[10].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLPurchaseRate.setForeground(Color.WHITE);
            PPLSellingRate.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.WHITE);
            PPLExpDate.setForeground(Color.WHITE);
            PPLMfgDate.setForeground(Color.WHITE);
            PPLGST.setForeground(Color.red);
        }else if(Product[13] == null || Product[13].equals(""))
        {
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLPurchaseRate.setForeground(Color.WHITE);
            PPLSellingRate.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.WHITE);
            PPLExpDate.setForeground(Color.WHITE);
            PPLMfgDate.setForeground(Color.WHITE);
            PPLGST.setForeground(Color.WHITE);
            PPLEXPNotifyDate.setForeground(Color.red);
        }else{
            PPLProductCategory.setForeground(Color.WHITE);
            PPLProductName.setForeground(Color.WHITE);
            PPLProductBrand.setForeground(Color.WHITE);
            PPLPurchaseRate.setForeground(Color.WHITE);
            PPLSellingRate.setForeground(Color.WHITE);
            PPLSize.setForeground(Color.WHITE);
            PPLUOM.setForeground(Color.WHITE);
            PPLQuantity.setForeground(Color.WHITE);
            PPLExpDate.setForeground(Color.WHITE);
            PPLMfgDate.setForeground(Color.WHITE);
            PPLGST.setForeground(Color.WHITE);
            PPLEXPNotifyDate.setForeground(Color.WHITE);
            try {
                boolean StatusP = false;
                try {
                    StatusP = db.PurchaseProductC(this.ProductID);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(StatusP)
                {
                    try{
                        Product[14] = PPTFINvoiceID.getText();
                    }catch(NullPointerException e)
                    {
                        Product[14] = "";
                    }
                    Product[12] = this.SupplierID;

                    ProductDate[0][ProductDateCount] = Product[8];
                    ProductDate[1][ProductDateCount] = Product[7];
                    ProductDate[2][ProductDateCount] = Product[13];
                    ProductDateCount++;
                    /*Load Jtable Column Headings
                     Load JTable*/

                    ImageIcon img = new ImageIcon("Delete.png");
                    Object[] obj = {this.ProductID,Product[0],Product[1],Product[2],Product[5],Product[6],Product[9],Product[3],Product[10],img};
                    if(!this.ProductID.equals("") && !Product[0].equals("") && !Product[1].equals("")  && !Product[2].equals("") && !Product[5].equals("") && !Product[6].equals("") && !Product[9].equals(""))
                    {
                        PPInvoicemodel.addRow(obj);
                        //refresh
                        refreshPurchaseInvoice();

                        SaveIvoiceData(Product,PPTInvoiceD.getRowCount());
                    }
                }else{
                    PPCBProductCategory.setSelectedIndex(-1);
                    PPCBProductName.setSelectedIndex(-1);
                    PPCBProductBrand.setSelectedIndex(-1);
                    PPCBSize.setSelectedIndex(-1);
                    PPCBUOM.setSelectedIndex(-1);
                    PPTFQuantity.setText("");
                    PPTFPurchaseRate.setText("");
                    PPTFSellingRate.setText("");
                    PPTFGST.setText("");
                    PPDCMfgDate.setDate(null);
                    PPDCExpDate.setDate(null);
                    PPDCEXPNotifyDate.setDate(null);
                    PPCBProductName.setEnabled(false);
                    PPCBProductBrand.setEnabled(false);
                    PPCBSize.setEnabled(false);
                    PPCBUOM.setEnabled(false);
                }
            } catch (Exception ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        

        for(int i=0; i<15; i++)
        {
            Product[i] = "";
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed
    void SaveIvoiceData(String str[],int count)
    { 
        data[0][count-1] = str[9];
        data[1][count-1] = str[3];
        data[2][count-1] = str[10];
        datacount++;
        
        
        int totalQuantity = 0;
        int totalGST = 0;
        double totalPurchasePrice = 0;
        double TotalAmount = 0;
        for(int i = 0; i<datacount; i++)
        {
            totalPurchasePrice = ((double)totalPurchasePrice + (Float.valueOf(data[1][i])*(double)Integer.parseInt(data[0][i])));
            totalGST = totalGST + Integer.parseInt(data[2][i]);
        }
        double AvgGST = ((double)totalGST)/(double)datacount;
//        System.out.println("AvgGST = "+AvgGST);
        TotalAmount = totalPurchasePrice + totalPurchasePrice*(AvgGST/100);
//        System.out.println("TotalAmount = "+TotalAmount+"    NetAmount = "+totalPurchasePrice);
        
        PPTFNetAmount.setText(String.valueOf(totalPurchasePrice));
        PPTFTotalAmount.setText(String.valueOf(TotalAmount));
        int Discount = Integer.parseInt(PPTFDiscount.getText());
        double GrossAmount = 0;
        if(Discount<=100 && Discount >=0)
        {
            GrossAmount = (TotalAmount - (TotalAmount*((double)Discount/100)));
        }
        PPLGrossAmount.setText(new DecimalFormat("##.##").format(GrossAmount));
//        PPLGrossAmount.setText(String.valueOf(GrossAmount));
        
        double PaidAmount = 0;
        double PreDues = 0;
        
        try {
            PreDues = Double.valueOf(TFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        try {
            PaidAmount = Double.valueOf(PPTFPaidAmount.getText());
            PPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            PPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        
        Dues = (GrossAmount+PreDues) - PaidAmount;
        
        PPTFDues.setText(new DecimalFormat("##.##").format(Dues));
//        PPTFDues.setText(String.valueOf(Dues));
    }
    
    void SaveSPIvoiceData(String str[],int count)
    {
        data[0][count-1] = str[6];
        data[1][count-1] = str[5];
        data[2][count-1] = str[7];
        datacount++;
        
        int totalQuantity = 0;
        int totalGST = 0;
        double totalPurchasePrice = 0;
        double TotalAmount = 0;
        for(int i = 0; i<datacount; i++)
        {
            totalPurchasePrice = ((double)totalPurchasePrice + (Float.valueOf(data[1][i])*(double)Integer.parseInt(data[0][i])));
            totalGST = totalGST + Integer.parseInt(data[2][i]);
        }
        double AvgGST = ((double)totalGST)/(double)datacount;
//        System.out.println("AvgGST = "+AvgGST);
        TotalAmount = totalPurchasePrice + totalPurchasePrice*(AvgGST/100);
//        System.out.println("TotalAmount = "+TotalAmount+"    NetAmount = "+totalPurchasePrice);
        
        SPTFNetAmount.setText(String.valueOf(totalPurchasePrice));
        SPTFTotalAmount.setText(String.valueOf(TotalAmount));
        int Discount = Integer.parseInt(SPTFDiscount.getText());
        double GrossAmount = 0;
        if(Discount<=100 && Discount >=0)
        {
            GrossAmount = (TotalAmount - (TotalAmount*((double)Discount/100)));
        }
        SPLGrossAmount.setText(String.valueOf(GrossAmount));
        
        double PaidAmount = 0;
        try {
            PaidAmount = Double.valueOf(SPTFPaidAmount.getText());
            SPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            SPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        double PreDues = 0;
        try {
        PreDues = Double.valueOf(SPTFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(SPLGrossAmount.getText());

        Dues = (GrossAmount+PreDues) - PaidAmount;
        
        SPTFDues.setText(String.valueOf(Dues));
    }
    
    public void refreshPurchaseInvoice()
    {
        PPCBProductCategory.setSelectedIndex(-1);
        PPCBProductName.setSelectedIndex(-1);
        PPCBProductBrand.setSelectedIndex(-1);
        PPCBSize.setSelectedIndex(-1);
        PPCBUOM.setSelectedIndex(-1);
        PPTFQuantity.setText("");
        PPTFPurchaseRate.setText("");
        PPTFSellingRate.setText("");
        PPTFGST.setText("");
        PPDCMfgDate.setDate(null);
        PPDCExpDate.setDate(null);
        PPDCEXPNotifyDate.setDate(null);
        PPCBProductName.setEnabled(false);
        PPCBProductBrand.setEnabled(false);
        PPCBSize.setEnabled(false);
        PPCBUOM.setEnabled(false);
    }
    
    public void refreshSellInvoice()
    {
        SPCBProductCategory.setSelectedIndex(-1);
        SPCBProductName.setSelectedIndex(-1);
        SPCBProductBrand.setSelectedIndex(-1);
        SPCBSize.setSelectedIndex(-1);
        SPCBUOM.setSelectedIndex(-1);
        SPTFQuantity.setText("");
        SPTFAvailableQ.setText("");
        SPTFSellingPrice.setText("");
        SPTFGST.setText("");
        SPCBProductName.setEnabled(false);
        SPCBProductBrand.setEnabled(false);
        SPCBSize.setEnabled(false);
        SPCBUOM.setEnabled(false);
    }
    
    private void PPBAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPBAddProductActionPerformed
        // TODO add your handling code here:
        pm.setTitle("Product Master");
        pm.setLocation(200,10);
        pm.setResizable(false);
        pm.setSize(841, 690);
        pm.setVisible(true);
        pm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_PPBAddProductActionPerformed

    private void PPCBProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPCBProductNameActionPerformed
        String ProductName = null;
        String ProductCategory = null;
        try {
            // TODO add your handling code here:
            ProductCategory = PPCBProductCategory.getItemAt(PPCBProductCategory.getSelectedIndex()).toString();
            ProductName = PPCBProductName.getItemAt(PPCBProductName.getSelectedIndex()).toString();
            db.setProductBrand(PPCBProductBrand, ProductCategory, ProductName);
            PPCBProductBrand.setEnabled(true);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_PPCBProductNameActionPerformed

    private void PPCBProductBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPCBProductBrandActionPerformed
        // TODO add your handling code here:
        String ProductName = null;
        String ProductCategory = null;
        String ProductBrand = null;
        try {
            // TODO add your handling code here:
            ProductCategory = PPCBProductCategory.getItemAt(PPCBProductCategory.getSelectedIndex()).toString();
            ProductName = PPCBProductName.getItemAt(PPCBProductName.getSelectedIndex()).toString();
            ProductBrand = PPCBProductBrand.getItemAt(PPCBProductBrand.getSelectedIndex()).toString();
            db.setProductID(PPCBSize, PPCBUOM, ProductCategory, ProductName, ProductBrand);
            PPCBSize.setEnabled(true);
            PPCBUOM.setEnabled(true);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_PPCBProductBrandActionPerformed

    private void PPCBUOMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPCBUOMActionPerformed
        // TODO add your handling code here:
         String ProductName = null;
        String ProductCategory = null;
        String ProductBrand = null;
        String Size = null;
        String UOM = null;
        try {
            // TODO add your handling code here:
            ProductCategory = PPCBProductCategory.getItemAt(PPCBProductCategory.getSelectedIndex()).toString();
            ProductName = PPCBProductName.getItemAt(PPCBProductName.getSelectedIndex()).toString();
            ProductBrand = PPCBProductBrand.getItemAt(PPCBProductBrand.getSelectedIndex()).toString();
            Size = PPCBSize.getItemAt(PPCBSize.getSelectedIndex()).toString();
            UOM = PPCBUOM.getItemAt(PPCBUOM.getSelectedIndex()).toString();
            this.ProductID = db.setSellingPrice_getProductID(PPTFSellingRate, ProductCategory, ProductName, ProductBrand, Size, UOM);
            //System.out.println(this.ProductID);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_PPCBUOMActionPerformed

    private void PPCBSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPCBSizeActionPerformed
        // TODO add your handling code here:
        
        String ProductName = null;
        String ProductCategory = null;
        String ProductBrand = null;
        String Size = null;
        String UOM = null;
        try {
            // TODO add your handling code here:
            ProductCategory = PPCBProductCategory.getItemAt(PPCBProductCategory.getSelectedIndex()).toString();
            ProductName = PPCBProductName.getItemAt(PPCBProductName.getSelectedIndex()).toString();
            ProductBrand = PPCBProductBrand.getItemAt(PPCBProductBrand.getSelectedIndex()).toString();
            Size = PPCBSize.getItemAt(PPCBSize.getSelectedIndex()).toString();
            UOM = PPCBUOM.getItemAt(PPCBUOM.getSelectedIndex()).toString();
            this.ProductID = db.setSellingPrice_getProductID(PPTFSellingRate, ProductCategory, ProductName, ProductBrand, Size, UOM);
            //System.out.println(this.ProductID);
            
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_PPCBSizeActionPerformed

    private void PPDCEXPNotifyDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_PPDCEXPNotifyDatePropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_PPDCEXPNotifyDatePropertyChange

    private void PPDCExpDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_PPDCExpDatePropertyChange
        // TODO add your handling code here:
            String date = null;
            
            try {
                date = IS.dateFormat.format(PPDCExpDate.getDate());
            } catch (Exception e) {}
            
            //System.out.println(date);
            
            try {
                if (Integer.parseInt(date.substring(5, 7)) < 1) {
                    date = String.valueOf(Integer.parseInt(date.substring(0, 4)) - 1) + "-" + "12" + "-" + "01";
                    //System.out.println("True");
                } else {
                    date = date.substring(0, 5) + String.valueOf(Integer.parseInt(date.substring(5, 7)) - 1) + "-" + "01";
                    //System.out.println("false");
                }
            } catch (Exception e) {
            }
            Date date2 = null; 
            try {
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (Exception ex) {
                
            }
            PPDCEXPNotifyDate.setDate(date2);
            //System.out.println("Done as you said Boss");
    }//GEN-LAST:event_PPDCExpDatePropertyChange

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        try {
            PPInvoiceID = db.getInvoiceID();
        } catch (SQLException ex) {
            this.PPInvoiceID = "";
        }
            
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        ProductDateCount = 0;
        
        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        
        //add panel
        jPanel1.add(ProductPurchase);
        jPanel1.repaint();
        jPanel1.revalidate();
        
        //set Supplier Details
        String date = IS.dateFormat.format(IS.dt);
        
        PPInvoiceDate.setText(date);
        
        String pesawat[] = null;
        try {
            pesawat = db.getSupplierNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PPCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
        PPCBSupplierName.setSelectedIndex(-1);
        TFMobileNo.setText("");
        TFAadharNo.setText("");
        TFGSTINNo.setText("");
        TFPreDues.setText("");
        
        PPTFNetAmount.setText("00.0");
        PPTFTotalAmount.setText("00.0");
        PPTFDues.setText("00.0");
        PPLGrossAmount.setText("00.0");
        PPTFDiscount.setText("0");
        PPTFPaidAmount.setText("0");
        PPCBPaymentMode.setSelectedIndex(-1);
        
        try {
            //set Product Details
            db.setProductCategory(PPCBProductCategory);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        PPCBProductName.setEnabled(false);
        PPCBProductBrand.setEnabled(false);
        PPCBSize.setEnabled(false);
        PPCBUOM.setEnabled(false);
        
        try {
            PPInvoiceID = db.getInvoiceID();
        } catch (SQLException ex) {
            PPInvoiceID = "";
        }
        
        PPTFINvoiceID.setText(this.PPInvoiceID);
        
        PPInvoicemodel.setRowCount(0);
        for(int i=0; i<=datacount ;i++)
        {
          data[0][i] = "";    
          data[1][i] = "";
          data[2][i] = "";
        }
        for(int i=0; i<=ProductDateCount ;i++)
        {
            ProductDate[0][i] = "";
            ProductDate[1][i] = "";
            ProductDate[2][i] = "";
        }
        datacount = 0;
        ProductDateCount = 0;
    }//GEN-LAST:event_jButton31ActionPerformed

    private void ProductPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductPurchaseMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductPurchaseMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void PPTFDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PPTFDiscountKeyReleased
        // TODO add your handling code here:
        String dis = PPTFDiscount.getText();
        int Discount = 0;
        try {
            int temp = Integer.parseInt(dis);
            try {
                if (Integer.parseInt(dis) != 100) {
                    if (dis.length() > 2) {
                        PPTFDiscount.setText(dis.substring(0, 2));
                        dis = dis.substring(0, 2);
                    }
                } else {
                    dis = "100";
                }
                try {
                    Discount = Integer.parseInt(dis);
                    PPLDiscount.setForeground(Color.WHITE);
                } catch (NumberFormatException numberFormatException) {
                    Discount = 0;
                    PPLDiscount.setForeground(Color.red);
                }
            } catch (NumberFormatException numberFormatException) {
                Discount = 0;
                PPLDiscount.setForeground(Color.red);
            } 
        } catch (Exception e) {
            PPLDiscount.setForeground(Color.red);
        }
        
        double GrossAmount = 0;
        double TotalAmount = 0;
        
        TotalAmount = Double.parseDouble(PPTFTotalAmount.getText());
        
        GrossAmount = TotalAmount - (TotalAmount*((double)Discount/100));
        
//        PPLGrossAmount.setText(String.valueOf(GrossAmount));
        PPLGrossAmount.setText(new DecimalFormat("##.##").format(GrossAmount));
        
        double PaidAmount = 0;
        double PreDues = 0;
        
        try {
            PreDues = Double.valueOf(TFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        try {
            PaidAmount = Double.valueOf(PPTFPaidAmount.getText());
            PPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            PPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        
        Dues = (GrossAmount+PreDues) - PaidAmount;
        PPTFDues.setText(new DecimalFormat("##.##").format(Dues));
//        PPTFDues.setText(String.valueOf(Dues));
    }//GEN-LAST:event_PPTFDiscountKeyReleased

    private void PPTFPaidAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PPTFPaidAmountKeyReleased
        // TODO add your handling code here:
        double PaidAmount = 0;
        try {
            PaidAmount = Double.valueOf(PPTFPaidAmount.getText());
            PPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            PPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        double GrossAmount = 0;
        double PreDues = 0;
        try {
            PreDues = Double.valueOf(TFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(PPLGrossAmount.getText());
        
        Dues = (GrossAmount+PreDues) - PaidAmount;
        
        PPTFDues.setText(new DecimalFormat("##.##").format(Dues));
//        PPTFDues.setText(String.valueOf(Dues));
    }//GEN-LAST:event_PPTFPaidAmountKeyReleased

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        double paidAmount = 0;
        try {
            paidAmount = Double.valueOf(PPTFPaidAmount.getText());
        } catch (NumberFormatException numberFormatException) {
            paidAmount = 0;
        }
        
        double Dues = 0;
        double GrossAmount = 0;
        double PreDues = 0;
        try {
            PreDues = Double.valueOf(TFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(PPLGrossAmount.getText());
        
        Dues = (GrossAmount+PreDues) - paidAmount;
        
        
        
        if(paidAmount <= (GrossAmount+PreDues) && paidAmount > 0)
        {
            boolean Status = false;
            try {
                //To Check Wheater invoice is already Saved
                Status = db.StatusOfPurchaseInvoice(this.PPInvoiceID);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(Status)
            {
                String ProductC = null;
                boolean StatusP = false;
                if (this.SupplierID == null || this.SupplierID.equals("")) {
                    JOptionPane.showMessageDialog(null, "Select the Supplier");
                }else{
                    for (int i = 0; i < PPTInvoiceD.getRowCount(); i++) {
                        ProductC = String.valueOf(PPTInvoiceD.getValueAt(i, 0));
                        try {
                            StatusP = db.PurchaseProductC(ProductC);
                            if(StatusP == false)
                            {
                                break;
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if(StatusP)
                {
                    String[] Product = new String[22];
                    if (this.SupplierID == null || this.SupplierID.equals("")) {
                        JOptionPane.showMessageDialog(null, "Select the Supplier");
                    }else{
                        for (int i = 0; i < PPTInvoiceD.getRowCount(); i++) {
                            Product[0] = String.valueOf(PPTInvoiceD.getValueAt(i, 0));
                            Product[1] = String.valueOf(PPTInvoiceD.getValueAt(i, 1));
                            Product[2] = String.valueOf(PPTInvoiceD.getValueAt(i, 2));
                            Product[3] = String.valueOf(PPTInvoiceD.getValueAt(i, 3));
                            Product[4] = String.valueOf(PPTInvoiceD.getValueAt(i, 4));
                            Product[5] = String.valueOf(PPTInvoiceD.getValueAt(i, 5));
                            Product[6] = String.valueOf(PPTInvoiceD.getValueAt(i, 6));
                            Product[7] = String.valueOf(PPTInvoiceD.getValueAt(i, 7));
                            Product[8] = String.valueOf(PPTInvoiceD.getValueAt(i, 8));

                            try {
                                Product[9] = PPTFNetAmount.getText();
                                Product[10] = PPTFTotalAmount.getText();
                                int Discount = Integer.parseInt(PPTFDiscount.getText());
                                Product[11] = PPTFDiscount.getText();
                                double PaidAmount = Double.valueOf(PPTFPaidAmount.getText());
                                Product[13] = PPTFPaidAmount.getText();
                                Product[12] = PPCBPaymentMode.getSelectedItem().toString();
                                Product[14] = PPTFDues.getText();
                                Product[15] = PPLGrossAmount.getText();
                                Product[16] = ProductDate[0][i];
                                Product[17] = ProductDate[1][i];
                                Product[18] = ProductDate[2][i];
                                Product[19] = this.SupplierID;
                                Product[20] = this.PPInvoiceID;
                                Product[21] = TFPreDues.getText();
            //                    System.out.print(Product[16] + " " + Product[17] + " " + Product[18]);
            //                    System.out.println("");
                                try {
                                    db.PurchaseProduct(Product);
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }
//                                try {
//                                    db.StockManage(Product);
//                                } catch (SQLException ex) {
//                                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                                }
                                try {
                                    db.PurchaseInvoice(Product);
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Error : Value Missing(eg. Discount, PaidAmount)");
                            }                

                        }

                        try {
                            db.InvoiceTable(Product);
                            String[] TransactionD = new String[4];
                            
                            TransactionD[0] = db.getSupplierName(this.SupplierID);
                            TransactionD[1] = "Supplier";
                            TransactionD[2] = "Given";
                            TransactionD[3] = PPTFPaidAmount.getText();
                            db.TransactionTable(TransactionD);
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        JOptionPane.showMessageDialog(null, "Invoice Successfully Saved");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Already Invoice Saved Successfully");
            }
        }else{
            if(paidAmount < 0)
            {
                JOptionPane.showMessageDialog(null, "Paid Amount is Wrong");
            }else{
                JOptionPane.showMessageDialog(null, "Paid Amount is More");
            }
            
        }
        
    }//GEN-LAST:event_jButton21ActionPerformed

    private void PPCBSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPCBSupplierNameActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_PPCBSupplierNameActionPerformed

    private void PPTFGSTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PPTFGSTKeyReleased
        // TODO add your handling code here:
        String gst = PPTFGST.getText();
        int GST = 0;
        try {
            int temp = Integer.parseInt(gst);
            try {
                if (Integer.parseInt(gst) != 100) {
                    if (gst.length() > 2) {
                        PPTFGST.setText(gst.substring(0, 2));
                        gst = gst.substring(0, 2);
                    }
                } else {
                    gst = "100";
                }
                try {
                    GST = Integer.parseInt(gst);
                    PPLGST.setForeground(Color.WHITE);
                } catch (NumberFormatException numberFormatException) {
                    GST = 0;
                    PPLGST.setForeground(Color.red);
                }
            } catch (NumberFormatException numberFormatException) {
                GST = 0;
                PPLGST.setForeground(Color.red);
            } 
        } catch (Exception e) {
            PPLGST.setForeground(Color.red);
        }
    }//GEN-LAST:event_PPTFGSTKeyReleased

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton22ActionPerformed

    private void SPBAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPBAddCustomerActionPerformed
        // TODO add your handling code here:
        cm.setTitle("Add Customer");
        cm.setLocation(400, 100);
        cm.setResizable(false);
        cm.setSize(465, 539);
        cm.setVisible(true);
        cm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_SPBAddCustomerActionPerformed

    private void SPCBCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPCBCustomerNameActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_SPCBCustomerNameActionPerformed

    private void SPTFGSTINNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPTFGSTINNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SPTFGSTINNoActionPerformed

    private void SPCBCustomerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPCBCustomerTypeActionPerformed
        // TODO add your handling code here:
        String CustomerType = SPCBCustomerType.getSelectedItem().toString();
        if(CustomerType.equals("Retail"))
        {
            SPCBCustomerName.setEnabled(false);
            SPBAddCustomer.setEnabled(false);
            SPTFMobileNo.setEnabled(false);
            SPTFGSTINNo.setEnabled(false);
            SPTFPreDues.setEnabled(false);
            SPTFAadharNo.setEnabled(false);
        }else{
            SPCBCustomerName.setEnabled(true);
            SPBAddCustomer.setEnabled(true);
            SPTFMobileNo.setEnabled(true);
            SPTFGSTINNo.setEnabled(true);
            SPTFPreDues.setEnabled(true);
            SPTFAadharNo.setEnabled(true);
        }
    }//GEN-LAST:event_SPCBCustomerTypeActionPerformed

    private void SPTFDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPTFDiscountKeyReleased
        // TODO add your handling code here:
        String dis = SPTFDiscount.getText();
        int Discount = 0;
        try {
            int temp = Integer.parseInt(dis);
            try {
                if (Integer.parseInt(dis) != 100) {
                    if (dis.length() > 2) {
                        SPTFDiscount.setText(dis.substring(0, 2));
                        dis = dis.substring(0, 2);
                    }
                } else {
                    dis = "100";
                }
                try {
                    Discount = Integer.parseInt(dis);
                    SPLDiscount.setForeground(Color.WHITE);
                } catch (NumberFormatException numberFormatException) {
                    Discount = 0;
                    SPLDiscount.setForeground(Color.red);
                }
            } catch (NumberFormatException numberFormatException) {
                Discount = 0;
                SPLDiscount.setForeground(Color.red);
            } 
        } catch (Exception e) {
            SPLDiscount.setForeground(Color.red);
        }
        
        double GrossAmount = 0;
        double TotalAmount = 0;
        
        TotalAmount = Double.parseDouble(SPTFTotalAmount.getText());
        
        GrossAmount = TotalAmount - (TotalAmount*((double)Discount/100));
        
        SPLGrossAmount.setText(String.valueOf(GrossAmount));
        
        double PaidAmount = 0;
        try {
            PaidAmount = Double.valueOf(SPTFPaidAmount.getText());
            SPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            SPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        double PreDues = 0;
        try {
        PreDues = Double.valueOf(SPTFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(SPLGrossAmount.getText());

        Dues = (GrossAmount+PreDues) - PaidAmount;
        
        SPTFDues.setText(String.valueOf(Dues));
    }//GEN-LAST:event_SPTFDiscountKeyReleased

    private void SPTFPaidAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPTFPaidAmountKeyReleased
        // TODO add your handling code here:
        double PaidAmount = 0;
        try {
            PaidAmount = Double.valueOf(SPTFPaidAmount.getText());
            SPLPaidAmount.setForeground(Color.WHITE);
        } catch (NumberFormatException numberFormatException) {
            SPLPaidAmount.setForeground(Color.red);
            PaidAmount = 0;
        }
        
        double Dues = 0;
        double GrossAmount = 0;
        double PreDues = 0;
        try {
        PreDues = Double.valueOf(SPTFPreDues.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(SPLGrossAmount.getText());

        Dues = (GrossAmount+PreDues) - PaidAmount;
        
        SPTFDues.setText(String.valueOf(Dues));
    }//GEN-LAST:event_SPTFPaidAmountKeyReleased

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        
        String PaymentMode = null;
        try {
            PaymentMode = SPCBPaymentMode.getSelectedItem().toString();
        } catch (Exception e) {
            PaymentMode = "";
        }
        if(!PaymentMode.equals(""))
        {
            double paidAmount = 0;
            try {
                paidAmount = Double.valueOf(SPTFPaidAmount.getText());
            } catch (NumberFormatException numberFormatException) {
                paidAmount = 0;
            }

            double Dues = 0;
            double GrossAmount = 0;
            double PreDues = 0;
            try {
                PreDues = Double.valueOf(SPTFPreDues.getText());
            } catch (NumberFormatException numberFormatException) {
            }
            GrossAmount = Double.parseDouble(SPLGrossAmount.getText());

            Dues = (GrossAmount+PreDues) - paidAmount;



            if(paidAmount <= (GrossAmount+PreDues) && paidAmount >= 0)
            {    
                boolean Status = false;
                    try {
                        // TODO add your handling code here:
                        Status = db.StatusOfSellInvoice(this.SPInvoiceID);
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(Status)
                    {

                        String CustomerType = SPCBCustomerType.getSelectedItem().toString();
                        if(CustomerType.equals("Distributor") || CustomerType.equals("Loyal"))
                        {
                            String[] Product = new String[19];
                            if (this.CustomerID == null || this.CustomerID.equals("")) {
                                JOptionPane.showMessageDialog(null, "Select the Customer");
                            }else{
                                for (int i = 0; i < SPTInvoiceD.getRowCount(); i++) {
                                    Product[0] = String.valueOf(SPTInvoiceD.getValueAt(i, 0));
                                    Product[1] = String.valueOf(SPTInvoiceD.getValueAt(i, 1));
                                    Product[2] = String.valueOf(SPTInvoiceD.getValueAt(i, 2));
                                    Product[3] = String.valueOf(SPTInvoiceD.getValueAt(i, 3));
                                    Product[4] = String.valueOf(SPTInvoiceD.getValueAt(i, 4));
                                    Product[5] = String.valueOf(SPTInvoiceD.getValueAt(i, 5));
                                    Product[6] = String.valueOf(SPTInvoiceD.getValueAt(i, 6));
                                    Product[7] = String.valueOf(SPTInvoiceD.getValueAt(i, 7));
                                    Product[8] = String.valueOf(SPTInvoiceD.getValueAt(i, 8));

                                    try {
                                        Product[9] = SPTFNetAmount.getText();
                                        Product[10] = SPTFTotalAmount.getText();
                                        int Discount = Integer.parseInt(SPTFDiscount.getText());
                                        Product[11] = SPTFDiscount.getText();
                                        double PaidAmount = Double.valueOf(SPTFPaidAmount.getText());
                                        Product[13] = SPTFPaidAmount.getText();
                                        Product[12] = SPCBPaymentMode.getSelectedItem().toString();
                                        Product[14] = SPTFDues.getText();
                                        Product[15] = SPLGrossAmount.getText();
                                        Product[16] = this.CustomerID;
                                        Product[17] = this.SPInvoiceID;
                                        Product[18] = SPTFPreDues.getText();
                    //                    System.out.print(Product[16] + " " + Product[17] + " " + Product[18]);
                    //                    System.out.println("");
                                        try {
                                            db.SellProduct(Product);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        try {
                                            db.SellInvoice(Product);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        try {
                                            db.InsertNewProduct(Product);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } catch (NumberFormatException e) {
                                        JOptionPane.showMessageDialog(null, "Error : Value Missing(eg. Discount, PaidAmount)");
                                    }                

                                }

                                try {
                                    db.SellInvoiceTable(Product);
                                    String[] TransactionD = new String[4];
                                    TransactionD[0] = "Retailer";
                                    TransactionD[1] = "Retailer";
                                    TransactionD[2] = "Taken";
                                    TransactionD[3] = SPTFPaidAmount.getText();
                                    db.TransactionTable(TransactionD);
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }else{
                                String[] Product = new String[19];
                                for (int i = 0; i < SPTInvoiceD.getRowCount(); i++) {
                                    Product[0] = String.valueOf(SPTInvoiceD.getValueAt(i, 0));
                                    Product[1] = String.valueOf(SPTInvoiceD.getValueAt(i, 1));
                                    Product[2] = String.valueOf(SPTInvoiceD.getValueAt(i, 2));
                                    Product[3] = String.valueOf(SPTInvoiceD.getValueAt(i, 3));
                                    Product[4] = String.valueOf(SPTInvoiceD.getValueAt(i, 4));
                                    Product[5] = String.valueOf(SPTInvoiceD.getValueAt(i, 5));
                                    Product[6] = String.valueOf(SPTInvoiceD.getValueAt(i, 6));
                                    Product[7] = String.valueOf(SPTInvoiceD.getValueAt(i, 7));
                                    Product[8] = String.valueOf(SPTInvoiceD.getValueAt(i, 8));

                                    try {
                                        Product[9] = SPTFNetAmount.getText();
                                        Product[10] = SPTFTotalAmount.getText();
                                        int Discount = Integer.parseInt(SPTFDiscount.getText());
                                        Product[11] = SPTFDiscount.getText();
                                        double PaidAmount = Double.valueOf(SPTFPaidAmount.getText());
                                        Product[13] = SPTFPaidAmount.getText();
                                        Product[12] = SPCBPaymentMode.getSelectedItem().toString();
                                        Product[14] = SPTFDues.getText();
                                        Product[15] = SPLGrossAmount.getText();
                                        Product[16] = "Retailer";
                                        Product[17] = this.SPInvoiceID;
                                        Product[18] = "0.00";
//                                        System.out.print(Product[16] + " " + Product[17] + " " + Product[18]);
//                                        System.out.println("");
                                        try {
                                            db.SellProduct(Product);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                        }
    
                                        try {
                                            db.SellInvoice(Product);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        try {
                                            db.InsertNewProduct(Product);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } catch (NumberFormatException e) {
                                        JOptionPane.showMessageDialog(null, "Error : Value Missing(eg. Discount, PaidAmount)");
                                    }                

                                }

                                try {
                                    db.SellInvoiceTable(Product);
                                    String[] TransactionD = new String[4];
                                    TransactionD[0] = db.getCustomerName(this.CustomerID);
                                    TransactionD[1] = "Customer";
                                    TransactionD[2] = "Taken";
                                    TransactionD[3] = SPTFPaidAmount.getText();
                                    db.TransactionTable(TransactionD);
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                JOptionPane.showMessageDialog(null, "Invoice Successfully Saved");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Already Invoice Saved Successfully");
                    }
                }else{
                    if(paidAmount < 0)
                    {
                        JOptionPane.showMessageDialog(null, "Paid Amount is Wrong");
                    }else{
                        JOptionPane.showMessageDialog(null, "Paid Amount is More");
                    }

                }
        }
    }//GEN-LAST:event_jButton33ActionPerformed
    
    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton34ActionPerformed

    private void SPCBProductCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPCBProductCategoryActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            String ProductCategory = null;
            ProductCategory = SPCBProductCategory.getItemAt(SPCBProductCategory.getSelectedIndex()).toString();
            db.setProductName(SPCBProductName, ProductCategory);
            SPCBProductName.setEnabled(true);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_SPCBProductCategoryActionPerformed

    private void SPCBProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPCBProductNameActionPerformed
        // TODO add your handling code here:
        String ProductName = null;
        String ProductCategory = null;
        try {
            // TODO add your handling code here:
            ProductCategory = SPCBProductCategory.getItemAt(SPCBProductCategory.getSelectedIndex()).toString();
            ProductName = SPCBProductName.getItemAt(SPCBProductName.getSelectedIndex()).toString();
            db.setProductBrand(SPCBProductBrand, ProductCategory, ProductName);
            SPCBProductBrand.setEnabled(true);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_SPCBProductNameActionPerformed

    private void SPCBUOMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPCBUOMActionPerformed
        // TODO add your handling code here:
        String ProductName = null;
        String ProductCategory = null;
        String ProductBrand = null;
        String Size = null;
        String UOM = null;
        try {
            // TODO add your handling code here:
            ProductCategory = SPCBProductCategory.getItemAt(SPCBProductCategory.getSelectedIndex()).toString();
            ProductName = SPCBProductName.getItemAt(SPCBProductName.getSelectedIndex()).toString();
            ProductBrand = SPCBProductBrand.getItemAt(SPCBProductBrand.getSelectedIndex()).toString();
            Size = SPCBSize.getItemAt(SPCBSize.getSelectedIndex()).toString();
            UOM = SPCBUOM.getItemAt(SPCBUOM.getSelectedIndex()).toString();
            this.ProductID = db.setAvailableQ_getProductID(SPTFAvailableQ, SPTFSellingPrice, ProductCategory, ProductName, ProductBrand, Size, UOM);
            //System.out.println(this.ProductID);
            
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_SPCBUOMActionPerformed

    private void SPCBSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPCBSizeActionPerformed
        // TODO add your handling code here:
        String ProductName = null;
        String ProductCategory = null;
        String ProductBrand = null;
        String Size = null;
        String UOM = null;
        try {
            // TODO add your handling code here:
            ProductCategory = SPCBProductCategory.getItemAt(SPCBProductCategory.getSelectedIndex()).toString();
            ProductName = SPCBProductName.getItemAt(SPCBProductName.getSelectedIndex()).toString();
            ProductBrand = SPCBProductBrand.getItemAt(SPCBProductBrand.getSelectedIndex()).toString();
            Size = SPCBSize.getItemAt(SPCBSize.getSelectedIndex()).toString();
            UOM = SPCBUOM.getItemAt(SPCBUOM.getSelectedIndex()).toString();
            this.ProductID = db.setAvailableQ_getProductID(SPTFAvailableQ, SPTFSellingPrice, ProductCategory, ProductName, ProductBrand, Size, UOM);
            //System.out.println(this.ProductID);
            
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_SPCBSizeActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int AvailableQ = Integer.parseInt(SPTFAvailableQ.getText());
        int PurchaseQ = 0;
        try {
            PurchaseQ = Integer.parseInt(SPTFQuantity.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        if (PurchaseQ <= AvailableQ) 
        {
            String[] Product = new String[10];

            //Getting Values
            try {
                Product[0] = SPCBProductCategory.getSelectedItem().toString();
            } catch (NullPointerException e) {
                Product[0] = "";
            }
            try {
                Product[1] = SPCBProductName.getSelectedItem().toString();
            } catch (NullPointerException e) {
                Product[1] = "";
            }
            try {
                Product[2] = SPCBProductBrand.getSelectedItem().toString();
            } catch (NullPointerException e) {
                Product[2] = "";
            }
            try {
                Product[3] = SPCBSize.getSelectedItem().toString();
            } catch (NullPointerException e) {
                Product[3] = "";
            }
            try {
                Product[4] = SPCBUOM.getSelectedItem().toString();
            } catch (NullPointerException e) {
                Product[4] = "";
            }
            try {
                Product[5] = SPTFSellingPrice.getText();
            } catch (NullPointerException e) {
                Product[5] = "";
            }
            
            try {
                int Quantity = Integer.parseInt(SPTFQuantity.getText());
                Product[6] = String.valueOf(Quantity);
            } catch (NumberFormatException e) {
                Product[6] = "";
            }
            
            try {
                int gst = Integer.parseInt(SPTFGST.getText());
                if (gst <= 100) {
                    Product[7] = String.valueOf(gst);
                } else {
                    Product[7] = "";
                }
            } catch (NumberFormatException e) {
                Product[7] = "";
            }
            try {
                Product[8] = this.ProductID;
            } catch (NullPointerException e) {
                Product[8] = "";
            }
            
            for (int i = 0; i < Product.length; i++) {
                try {
                    Product[i] = Product[i].trim();
                } catch (NullPointerException e) {
                    Product[i] = "";
                }
            }

            //Validation
            if (Product[0] == null || Product[0].equals("")) {
                SPLProductCategory.setForeground(Color.red);
            } else if (Product[1] == null || Product[1].equals("")) {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.red);
            } else if (Product[2] == null || Product[2].equals("")) {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.WHITE);
                SPLProductBrand.setForeground(Color.red);
            } else if (Product[3] == null || Product[3].equals("")) {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.WHITE);
                SPLProductBrand.setForeground(Color.WHITE);
                SPLSize.setForeground(Color.red);
            } else if (Product[4] == null || Product[4].equals("")) {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.WHITE);
                SPLProductBrand.setForeground(Color.WHITE);
                SPLSize.setForeground(Color.WHITE);
                SPLUOM.setForeground(Color.red);
            } else if (Product[5] == null || Product[5].equals("")) {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.WHITE);
                SPLProductBrand.setForeground(Color.WHITE);
                SPLSize.setForeground(Color.WHITE);
                SPLUOM.setForeground(Color.WHITE);
                SPLSellingPrice.setForeground(Color.red);
            } else if (Product[6] == null || Product[6].equals("")) {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.WHITE);
                SPLProductBrand.setForeground(Color.WHITE);
                SPLSize.setForeground(Color.WHITE);
                SPLUOM.setForeground(Color.WHITE);
                SPLSellingPrice.setForeground(Color.WHITE);
                SPLQuantity.setForeground(Color.red);
            } else if (Product[7] == null || Product[7].equals("")) {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.WHITE);
                SPLProductBrand.setForeground(Color.WHITE);
                SPLSize.setForeground(Color.WHITE);
                SPLUOM.setForeground(Color.WHITE);
                SPLSellingPrice.setForeground(Color.WHITE);
                SPLQuantity.setForeground(Color.WHITE);
                SPLGST.setForeground(Color.red);
            } else {
                SPLProductCategory.setForeground(Color.WHITE);
                SPLProductName.setForeground(Color.WHITE);
                SPLProductBrand.setForeground(Color.WHITE);
                SPLSize.setForeground(Color.WHITE);
                SPLUOM.setForeground(Color.WHITE);
                SPLSellingPrice.setForeground(Color.WHITE);
                SPLQuantity.setForeground(Color.WHITE);
                SPLGST.setForeground(Color.WHITE);
                
                try {

                    /*Load Jtable Column Headings
                     Load JTable*/
                    ImageIcon img = new ImageIcon("Delete.png");
                    Object[] obj = {this.ProductID, Product[0], Product[1], Product[2], Product[3], Product[4], Product[6], Product[5], Product[7], img};
                    if (!this.ProductID.equals("") && !Product[0].equals("") && !Product[1].equals("") && !Product[2].equals("") && !Product[3].equals("") && !Product[4].equals("") && !Product[5].equals("") && !Product[6].equals("") && !Product[7].equals("")) {
                        SPInvoicemodel.addRow(obj);
                        //refresh
                        refreshSellInvoice();
                        
                        SaveSPIvoiceData(Product, SPTInvoiceD.getRowCount());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for (int i = 0; i < 10; i++) {
                Product[i] = "";
            }
        }else{
            JOptionPane.showMessageDialog(null, "Purchase Quantity is More Than Available Quantity");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void SPCBProductBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPCBProductBrandActionPerformed
        // TODO add your handling code here:
        String ProductName = null;
        String ProductCategory = null;
        String ProductBrand = null;
        try {
            // TODO add your handling code here:
            ProductCategory = SPCBProductCategory.getItemAt(SPCBProductCategory.getSelectedIndex()).toString();
            ProductName = SPCBProductName.getItemAt(SPCBProductName.getSelectedIndex()).toString();
            ProductBrand = SPCBProductBrand.getItemAt(SPCBProductBrand.getSelectedIndex()).toString();
            db.setProductID(SPCBSize, SPCBUOM, ProductCategory, ProductName, ProductBrand);
            SPCBSize.setEnabled(true);
            SPCBUOM.setEnabled(true);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_SPCBProductBrandActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton36ActionPerformed

    private void SPTFGSTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPTFGSTKeyReleased
        // TODO add your handling code here:
        String gst = SPTFGST.getText();
        int GST = 0;
        try {
            int temp = Integer.parseInt(gst);
            try {
                if (Integer.parseInt(gst) != 100) {
                    if (gst.length() > 2) {
                        SPTFGST.setText(gst.substring(0, 2));
                        gst = gst.substring(0, 2);
                    }
                } else {
                    gst = "100";
                }
                try {
                    GST = Integer.parseInt(gst);
                    SPLGST.setForeground(Color.WHITE);
                } catch (NumberFormatException numberFormatException) {
                    GST = 0;
                    SPLGST.setForeground(Color.red);
                }
            } catch (NumberFormatException numberFormatException) {
                GST = 0;
                SPLGST.setForeground(Color.red);
            } 
        } catch (Exception e) {
            SPLGST.setForeground(Color.red);
        }
    }//GEN-LAST:event_SPTFGSTKeyReleased

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        
        //add panel
        jPanel1.add(SellProduct);
        jPanel1.repaint();
        jPanel1.revalidate();
        
        SPInvoicemodel.setRowCount(0);
        for(int i=0; i<=datacount ;i++)
        {
          data[0][i] = "";    
          data[1][i] = "";
          data[2][i] = "";
        }
        
        datacount = 0;
        //set Supplier Details
        String date = IS.dateFormat.format(IS.dt);
        
        SPInvoiceDate.setText(date);
        
        String pesawat[] = null;
        try {
            pesawat = db.getCustomerNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SPCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
        SPCBCustomerName.setSelectedIndex(-1);
        
        SPTFMobileNo.setText("");
        SPTFAadharNo.setText("");
        SPTFGSTINNo.setText("");
        SPTFPreDues.setText("");
        
        SPTFNetAmount.setText("00.0");
        SPTFTotalAmount.setText("00.0");
        SPTFDues.setText("00.0");
        SPLGrossAmount.setText("00.0");
        SPTFDiscount.setText("0");
        SPTFPaidAmount.setText("0");
        SPCBPaymentMode.setSelectedIndex(-1);
        
        try {
            //set Product Details
            db.setProductCategory(SPCBProductCategory);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        SPCBProductName.setEnabled(false);
        SPCBProductBrand.setEnabled(false);
        SPCBSize.setEnabled(false);
        SPCBUOM.setEnabled(false);
        
        try {
            SPInvoiceID = db.getInvoiceID();
        } catch (SQLException ex) {
            this.SPInvoiceID = "";
        }
        SPLInvoiceNo.setText(SPInvoiceID);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void DBCLViewCustomerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBCLViewCustomerMousePressed
        // TODO add your handling code here:
        if (DBCLViewCustomerV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(ViewCustomer);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            String pesawat[] = null;
            try {
                pesawat = db.getCustomerNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            VCCBCustomerName.setModel(new DefaultComboBoxModel(pesawat));
            VCCBCustomerName.setSelectedIndex(-1);

            //Removing Data From Table
            VCustomermodel.setRowCount(0);
            Vector v2 = new Vector();
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.Customers();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0, j = 0; j < (v1.size()) / 5; j++, i += 5) {
                //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "PreDues", "Edit", "Delete"};
                ImageIcon img2 = new ImageIcon("Delete.png");
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), img1, img2};
                
                VCustomermodel.addRow(obj);
            }
        }
//        
    }//GEN-LAST:event_DBCLViewCustomerMousePressed
    
    void VCShowAllCustomer()
    {
        Vector v2 = new Vector();
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.Customers();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Clear Table
        VCustomermodel.setRowCount(0);
        
        //Addind New Data to Table
        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
        //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "PreDues", "Edit", "Delete"};
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1,img2};
            
            VCustomermodel.addRow(obj);
        }
    }
    
    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        Vector v2 = new Vector();
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.Suppliers();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Removing Data From Table
        VSuppliermodel.setRowCount(0);
        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
        {
            //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "PreDues", "Edit", "Delete"};
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1,img2};
            
            VSuppliermodel.addRow(obj); 
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void DBSLViewSupplierMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBSLViewSupplierMousePressed
        // TODO add your handling code here:
        if (DBSLViewSupplierV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(ViewSupplier);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            String pesawat[] = null;
            try {
                pesawat = db.getSupplierNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            VCCBSupplierName.setModel(new DefaultComboBoxModel(pesawat));
            VCCBSupplierName.setSelectedIndex(-1);

            //Removing Data From Table
            VSuppliermodel.setRowCount(0);
//        for(int i=0;i<count;i++)
//        count = db.CountCustomers();
            Vector v2 = new Vector();
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.Suppliers();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0, j = 0; j < (v1.size()) / 5; j++, i += 5) {
                //String[] columnNames = { "CustomerID", "Name", "MobileNo", "Email-ID", "PreDues", "Edit", "Delete"};
                ImageIcon img2 = new ImageIcon("Delete.png");
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), img1, img2};
                
                VSuppliermodel.addRow(obj);                
            }
        }
    }//GEN-LAST:event_DBSLViewSupplierMousePressed

    private void SCBProductCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCBProductCategoryActionPerformed
        // TODO add your handling code here:
        //jPanel8.setBackground(new Color(240,240,240,155));
        try {
            // TODO add your handling code here:
            String ProductCategory = null;
            ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
            db.setProductName(SCBProductName, ProductCategory);
            SCBProductName.setEnabled(true);
            db.ProductStockTable1(SStockTable,ProductCategory);
            SRBBoth.setSelected(true);
            STSearchBy = "ProductCategory";
        } catch (Exception ex) {
            
        }
        
        
    }//GEN-LAST:event_SCBProductCategoryActionPerformed

    private void SCBProductBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCBProductBrandActionPerformed
        // TODO add your handling code here:
        //jPanel8.setBackground(new Color(240,240,240,155));
        try {
            db.ProductStockTable3(SStockTable,SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString(),SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString(),SCBProductBrand.getItemAt(SCBProductBrand.getSelectedIndex()).toString());
            SRBBoth.setSelected(true);
            STSearchBy = "ProductBrand";
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_SCBProductBrandActionPerformed

    private void SCBProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCBProductNameActionPerformed
        // TODO add your handling code here:
        String ProductName = null;
        String ProductCategory = null;
        try {
            // TODO add your handling code here:
            ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
            ProductName = SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString();
            db.setProductBrand(SCBProductBrand, ProductCategory, ProductName);
            SCBProductBrand.setEnabled(true);
            db.ProductStockTable2(SStockTable,ProductCategory,ProductName);
            SRBBoth.setSelected(true);
            STSearchBy = "ProductName";
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_SCBProductNameActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        try {
            // TODO add your handling code here:
            db.ProductStockTable(SStockTable,Long.parseLong(STFQuantity.getText()));
            SRBBoth.setSelected(true);
            STQuantity = Long.parseLong(STFQuantity.getText());
            STSearchBy = "Quantity";
        } catch (SQLException ex) {
            
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        try {
            // TODO add your handling code here:
            db.ProductStockT(SStockTable);
            SRBBoth.setSelected(true);
            STSearchBy = "All";
        } catch (SQLException ex) {
            
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void SRBCurrentStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SRBCurrentStockActionPerformed
        // TODO add your handling code here:
        
        if(STSearchBy.equals("All"))
        {
           try {
                // TODO add your handling code here:
                db.ProductStockTCS(SStockTable);
            } catch (SQLException ex) {

            }
        }else if(STSearchBy.equals("ProductBrand"))
        {
            try {
                db.ProductStockTable3CS(SStockTable,SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString(),SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString(),SCBProductBrand.getItemAt(SCBProductBrand.getSelectedIndex()).toString());
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("ProductName"))
        {
            String ProductName = null;
            String ProductCategory = null;
            try {
                // TODO add your handling code here:
                ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
                ProductName = SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString();
                db.ProductStockTable2CS(SStockTable,ProductCategory,ProductName);
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("ProductCategory"))
        {
            try {
                // TODO add your handling code here:
                String ProductCategory = null;
                ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
                db.ProductStockTable1CS(SStockTable,ProductCategory);
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("Quantity"))
        {
            //System.out.println("1");
            try {
                // TODO add your handling code here:
                db.ProductStockTableCS(SStockTable,STQuantity);
            } catch (SQLException ex) {

            }
        }else if(STSearchBy.equals("ExpDate"))
        {
            String date = datef.format(Dt);
            try {
                if (Integer.parseInt(date.substring(5, 7)) < 1) {
                    date = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 1) + "-" + "12" + "-" + "01";
                    //System.out.println("True");
                } else {
                    date = date.substring(0, 5) + String.valueOf(Integer.parseInt(date.substring(5, 7)) + 1) + "-" + "01";
                    //System.out.println("false");
                }
            } catch (Exception e) {
            }
            Date date2 = null; 
            try {
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (Exception ex) {

            }
            try {
                // TODO add your handling code here:
                db.ProductStockTableByExpCS(SStockTable,date);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SRBCurrentStockActionPerformed

    private void SRBBackupStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SRBBackupStockActionPerformed
        // TODO add your handling code here:
            
        if(STSearchBy.equals("All"))
        {
           try {
                // TODO add your handling code here:
                db.ProductStockTBS(SStockTable);
            } catch (SQLException ex) {

            }
        }else if(STSearchBy.equals("ProductBrand"))
        {
            try {
                db.ProductStockTable3NS(SStockTable,SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString(),SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString(),SCBProductBrand.getItemAt(SCBProductBrand.getSelectedIndex()).toString());
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("ProductName"))
        {
            String ProductName = null;
            String ProductCategory = null;
            try {
                // TODO add your handling code here:
                ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
                ProductName = SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString();
                db.ProductStockTable2NS(SStockTable,ProductCategory,ProductName);
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("ProductCategory"))
        {
            try {
                // TODO add your handling code here:
                String ProductCategory = null;
                ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
                db.ProductStockTable1NS(SStockTable,ProductCategory);
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("Quantity"))
        {
            //System.out.println("2");
            try {
                // TODO add your handling code here:
                db.ProductStockTableNS(SStockTable,STQuantity);
            } catch (SQLException ex) {

            }
        }else if(STSearchBy.equals("ExpDate"))
        {
            String date = datef.format(Dt);
            try {
                if (Integer.parseInt(date.substring(5, 7)) < 1) {
                    date = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 1) + "-" + "12" + "-" + "01";
                    //System.out.println("True");
                } else {
                    date = date.substring(0, 5) + String.valueOf(Integer.parseInt(date.substring(5, 7)) + 1) + "-" + "01";
                    //System.out.println("false");
                }
            } catch (Exception e) {
            }
            Date date2 = null; 
            try {
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (Exception ex) {

            }
            try {
                // TODO add your handling code here:
                db.ProductStockTableByExpNS(SStockTable,date);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SRBBackupStockActionPerformed

    private void SRBBothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SRBBothActionPerformed
        // TODO add your handling code here:
            
        if(STSearchBy.equals("All"))
        {
           try {
                // TODO add your handling code here:
                db.ProductStockT(SStockTable);
            } catch (SQLException ex) {

            }
        }else if(STSearchBy.equals("ProductBrand"))
        {
            try {
                db.ProductStockTable3(SStockTable,SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString(),SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString(),SCBProductBrand.getItemAt(SCBProductBrand.getSelectedIndex()).toString());
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("ProductName"))
        {
            String ProductName = null;
            String ProductCategory = null;
            try {
                // TODO add your handling code here:
                ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
                ProductName = SCBProductName.getItemAt(SCBProductName.getSelectedIndex()).toString();
                db.ProductStockTable2(SStockTable,ProductCategory,ProductName);
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("ProductCategory"))
        {
            try {
                // TODO add your handling code here:
                String ProductCategory = null;
                ProductCategory = SCBProductCategory.getItemAt(SCBProductCategory.getSelectedIndex()).toString();
                db.ProductStockTable1(SStockTable,ProductCategory);
            } catch (Exception ex) {

            }
        }else if(STSearchBy.equals("Quantity"))
        {
            //System.out.println("3");
            try {
                // TODO add your handling code here:
                db.ProductStockTable(SStockTable,STQuantity);
            } catch (SQLException ex) {

            }
        }else if(STSearchBy.equals("ExpDate"))
        {
            try {
                String date = datef.format(Dt);
                try {
                    if (Integer.parseInt(date.substring(5, 7)) < 1) {
                        date = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 1) + "-" + "12" + "-" + "01";
                        //System.out.println("True");
                    } else {
                        date = date.substring(0, 5) + String.valueOf(Integer.parseInt(date.substring(5, 7)) + 1) + "-" + "01";
                        //System.out.println("false");
                    }
                } catch (Exception e) {
                }
                Date date2 = null; 
                try {
                    date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (Exception ex) {

                }
                // TODO add your handling code here:
                db.ProductStockTableByExp(SStockTable,date);
            } catch (SQLException ex) {

            }
        }
    }//GEN-LAST:event_SRBBothActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        String date = datef.format(Dt);
        try {
            if (Integer.parseInt(date.substring(5, 7)) < 1) {
                date = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 1) + "-" + "12" + "-" + "01";
                //System.out.println("True");
            } else {
                date = date.substring(0, 5) + String.valueOf(Integer.parseInt(date.substring(5, 7)) + 1) + "-" + "01";
                //System.out.println("false");
            }
        } catch (Exception e) {
        }
        Date date2 = null; 
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception ex) {

        }
        try {
            // TODO add your handling code here:
            db.ProductStockTableByExp(SStockTable,date);
            STSearchBy = "ExpDate";
        } catch (SQLException ex) {
            //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
//        //Removing Data From Table
//        PRTMCustomermodel.setRowCount(0);
//        Vector<String> v1 = new Vector<String>();
//        try {
//            v1 = db.PRCustomersD();
//        } catch (SQLException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//
//        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
//        {
//            ImageIcon img1 = new ImageIcon("Edit2.png");
//            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};
//
//            PRTMCustomermodel.addRow(obj);
//        }
        RefreshCustomerName();
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
//        //Removing Data From Table
//        PRTMSuppliermodel.setRowCount(0);
//        Vector<String> v1 = new Vector<String>();
//        try {
//            v1 = db.PRSuppliersD();
//        } catch (SQLException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//
//        for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
//        {
//            ImageIcon img1 = new ImageIcon("Edit2.png");
//            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};
//
//            PRTMSuppliermodel.addRow(obj);
//        }
        RefreshSupplierName();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void DBPRLNewPersonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBPRLNewPersonMousePressed
        // TODO add your handling code here:
        if (DBPRLNewPersonV) {
            NP.setTitle("New Person Entry");
            NP.setLocation(450, 100);
            NP.setResizable(false);
            NP.setSize(465, 540);
            NP.setVisible(true);
            NP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            DB.setEnabled(false);
        }
    }//GEN-LAST:event_DBPRLNewPersonMousePressed

    private void PRRBNPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRRBNPActionPerformed
        // TODO add your handling code here:
        if(PRRBNP.isSelected())
        {
            PendingReport.removeAll();
            PendingReport.repaint();
            PendingReport.revalidate();
            PRPanels = 1;
           
            String pesawat1[] = null;
            try {
                pesawat1 = db.getPersonsNamesToPay();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            PRCBPersonToPay.setModel(new DefaultComboBoxModel(pesawat1));
            PRCBPersonToPay.setSelectedIndex(-1);
            
            //Removing Data From Table
            PRTMPersonToPay.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.AllPersonToPayD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }



            for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
            {
                ImageIcon img2 = new ImageIcon("Delete.png");
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};

                PRTMPersonToPay.addRow(obj);
            }
            //add panel
            PendingReport.add(PersonPay);
            PendingReport.add(jButton23);
            PendingReport.add(jButton24);
            PendingReport.add(PRRBCS);
            PendingReport.add(PRRBNP);
            PendingReport.repaint();
            PendingReport.revalidate();
        }
    }//GEN-LAST:event_PRRBNPActionPerformed

    private void PRCBPersonToPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRCBPersonToPayActionPerformed
        // TODO add your handling code here:
        //Removing Data From Table
        PRTMPersonToPay.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PersonToPayD(PRCBPersonToPay.getItemAt(PRCBPersonToPay.getSelectedIndex()).toString());
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException ex)
        {
            
        }



        for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
        {
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};

            PRTMPersonToPay.addRow(obj);
        }
    }//GEN-LAST:event_PRCBPersonToPayActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
//        //Removing Data From Table
//        PRTMPersonToPay.setRowCount(0);
//        Vector<String> v1 = new Vector<String>();
//        try {
//            v1 = db.AllPersonToPayD();
//        } catch (SQLException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//
//        for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
//        {
//            ImageIcon img2 = new ImageIcon("Delete.png");
//            ImageIcon img1 = new ImageIcon("Edit2.png");
//            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};
//
//            PRTMPersonToPay.addRow(obj);
//        }
        RefreshPersonToPay();
    }//GEN-LAST:event_jButton38ActionPerformed

    private void PRCBPersonToReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRCBPersonToReceiveActionPerformed
        // TODO add your handling code here:
        //Removing Data From Table
        PRTMPersonToReceive.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PersonToReceiveD(PRCBPersonToReceive.getItemAt(PRCBPersonToReceive.getSelectedIndex()).toString());
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException ex)
        {
            
        }



        for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
        {
            ImageIcon img2 = new ImageIcon("Delete.png");
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};

            PRTMPersonToReceive.addRow(obj);
        }
    }//GEN-LAST:event_PRCBPersonToReceiveActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
//        //Removing Data From Table
//        PRTMPersonToReceive.setRowCount(0);
//        Vector<String> v1 = new Vector<String>();
//        try {
//            v1 = db.AllPersonToReceiveD();
//        } catch (SQLException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//
//
//        for(int i=0,j=0; j<(v1.size())/4;j++,i+=4)
//        {
//            ImageIcon img2 = new ImageIcon("Delete.png");
//            ImageIcon img1 = new ImageIcon("Edit2.png");
//            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),img1,img2};
//
//            PRTMPersonToReceive.addRow(obj);
//        }
        RefreshPersonToReceive();
    }//GEN-LAST:event_jButton40ActionPerformed

    private void PRRBCSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRRBCSActionPerformed
        // TODO add your handling code here:
        if(PRRBCS.isSelected())
        {
            PendingReport.removeAll();
            PendingReport.repaint();
            PendingReport.revalidate();
            PRPanels = 0;
            
            
            String pesawat2[] = null;
            try {
                pesawat2 = db.getSupplierNames();
            } catch (SQLException ex) {
                //Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }


            PRCBSupplierName.setModel(new DefaultComboBoxModel(pesawat2));
            PRCBSupplierName.setSelectedIndex(-1);
            
            //Removing Data From Table
            PRTMSuppliermodel.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.PRSuppliersD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }



            for(int i=0,j=0; j<(v1.size())/5;j++,i+=5)
            {
    //            System.out.print(v1.get(i)+"   ||   ");
    //            System.out.print(v1.get(i+1)+"   ||   ");
    //            System.out.print(v1.get(i+2)+"   ||   ");
    //            System.out.print(v1.get(i+3)+"   ||   ");
    //            System.out.print(v1.get(i+4)+"   ||   ");
    //            System.out.print(v1.get(i+5)+"   ||   ");
    //            System.out.print(v1.get(i+6)+"   ||   ");
    //            System.out.print(v1.get(i+7));
    //            System.out.println();

                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),img1};

                PRTMSuppliermodel.addRow(obj);

            }
            //add panel
            PendingReport.add(Pay);
            PendingReport.add(jButton23);
            PendingReport.add(jButton24);
            PendingReport.add(PRRBCS);
            PendingReport.add(PRRBNP);
            PendingReport.repaint();
            PendingReport.revalidate();
        }
    }//GEN-LAST:event_PRRBCSActionPerformed

    private void VCCBSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VCCBSupplierNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VCCBSupplierNameActionPerformed

    private void PurRDCFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_PurRDCFromPropertyChange
        // TODO add your handling code here:
        String date1 = null;
        String date2 = null;
        try {
            date1 = IS.dateFormat.format(PurRDCFrom.getDate());
        } catch (Exception e) {}
        
        try {
            date2 = IS.dateFormat.format(PurRDCTo.getDate());
        } catch (Exception e) {}
        
        try {
            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                //Load Table Data
                //Removing Data From Table
                PurRTMSupplierIR.setRowCount(0);
                Vector<String> v1 = new Vector<String>();
                try {
                    v1 = db.PurRSupplierInvoiceDbyDate(date1, date2);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0, j = 0; j < (v1.size()) / 9; j++, i += 9) {
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    Object[] obj = {v1.get(i), v1.get(i + 1),v1.get(i + 8), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), v1.get(i + 7), img1};
                    
                    PurRTMSupplierIR.addRow(obj);
                }
            }
        } catch (NullPointerException e) {
        }
        
        
    }//GEN-LAST:event_PurRDCFromPropertyChange

    private void PurRDCToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_PurRDCToPropertyChange
        // TODO add your handling code here:
        String date1 = null;
        String date2 = null;
        try {
            date1 = IS.dateFormat.format(PurRDCFrom.getDate());
        } catch (Exception e) {}
        
        try {
            date2 = IS.dateFormat.format(PurRDCTo.getDate());
        } catch (Exception e) {}
        
        try {
            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                //Load Table Data
                //Removing Data From Table
                PurRTMSupplierIR.setRowCount(0);
                Vector<String> v1 = new Vector<String>();
                try {
                    v1 = db.PurRSupplierInvoiceDbyDate(date1, date2);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0, j = 0; j < (v1.size()) / 9; j++, i += 9) {
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    Object[] obj = {v1.get(i), v1.get(i + 1),v1.get(i + 8), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), v1.get(i + 7), img1};
                    
                    PurRTMSupplierIR.addRow(obj);
                }
            }
        } catch (NullPointerException e) {
        }
        
        
    }//GEN-LAST:event_PurRDCToPropertyChange

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        //Load Table Data
        //Removing Data From Table
        PurRTMSupplierIR.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PurRSupplierInvoiceD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }



        for (int i = 0, j = 0; j < (v1.size()) / 9; j++, i += 9) 
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = {v1.get(i), v1.get(i + 1),v1.get(i + 8), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), v1.get(i + 7), img1};

            PurRTMSupplierIR.addRow(obj);
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void PurRCBSupplierNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurRCBSupplierNamesActionPerformed
        // TODO add your handling code her
    }//GEN-LAST:event_PurRCBSupplierNamesActionPerformed

    private void SellRDCFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_SellRDCFromPropertyChange
        // TODO add your handling code here:
        String date1 = null;
        String date2 = null;
        try {
            date1 = IS.dateFormat.format(SellRDCFrom.getDate());
        } catch (Exception e) {}
        
        try {
            date2 = IS.dateFormat.format(SellRDCTo.getDate());
        } catch (Exception e) {}
        
        try {
            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                //Load Table Data
                //Removing Data From Table
                SellRTMCustomerIR.setRowCount(0);
                Vector<String> v1 = new Vector<String>();
                try {
                    v1 = db.SellRCustomerInvoiceDbyDate(date1, date2);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0, j = 0; j < (v1.size()) / 9; j++, i += 9) {
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    Object[] obj = {v1.get(i), v1.get(i + 1),v1.get(i + 8), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), v1.get(i + 7), img1};
                    
                    SellRTMCustomerIR.addRow(obj);
                }
            }
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_SellRDCFromPropertyChange

    private void SellRDCToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_SellRDCToPropertyChange
        // TODO add your handling code here:
        String date1 = null;
        String date2 = null;
        try {
            date1 = IS.dateFormat.format(SellRDCFrom.getDate());
        } catch (Exception e) {}
        
        try {
            date2 = IS.dateFormat.format(SellRDCTo.getDate());
        } catch (Exception e) {}
        
        try {
            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                //Load Table Data
                //Removing Data From Table
                SellRTMCustomerIR.setRowCount(0);
                Vector<String> v1 = new Vector<String>();
                try {
                    v1 = db.SellRCustomerInvoiceDbyDate(date1, date2);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0, j = 0; j < (v1.size()) / 9; j++, i += 9) {
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    Object[] obj = {v1.get(i), v1.get(i + 1),v1.get(i + 8), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), v1.get(i + 7), img1};
                    
                    SellRTMCustomerIR.addRow(obj);
                }
            }
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_SellRDCToPropertyChange

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        //Load Table Data
        //Removing Data From Table
        SellRTMCustomerIR.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.PurRCustomerInvoiceD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }



        for(int i=0,j=0; j<(v1.size())/9;j++,i+=9)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i + 8),v1.get(i+2),v1.get(i+3),v1.get(i+4),v1.get(i+5),v1.get(i+6),v1.get(i+7),img1};

            SellRTMCustomerIR.addRow(obj);

        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void SellRCBCustomerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SellRCBCustomerTypeActionPerformed
        // TODO add your handling code here:
        String CustomerType = SellRCBCustomerType.getSelectedItem().toString().trim();
        
        if(CustomerType.equals("Retail"))
        {
            SellRCBCustomerNames.setEnabled(false);
            String Name2 = SellRCBCustomerType.getEditor().getItem().toString().trim();
//            String Name = null;
//            try {
//                Name = db.CustomerID(Name2,null,null);
//            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//            }
            int x = 1;
            x = JOptionPane.showConfirmDialog(null, "Are you want to sort Retailer Invoice By Date");
            if(x == 0)
            {
                String date1 = null;
                String date2 = null;
                try {
                    date1 = IS.dateFormat.format(SellRDCFrom.getDate());
                } catch (Exception e) {}

                try {
                    date2 = IS.dateFormat.format(SellRDCTo.getDate());
                } catch (Exception e) {}

                try {
                    if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                        //Load Table Data
                        //Removing Data From Table
                        SellRTMCustomerIR.setRowCount(0);
                        Vector<String> v1 = new Vector<String>();
                        try {
                            v1 = db.SellRCustomerInvoiceDbyNameandDate(date1, date2, "Retailer");
                        } catch (SQLException ex) {
                            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        for (int a = 0, j = 0; j < (v1.size()) / 9; j++, a += 9) {
                            ImageIcon img1 = new ImageIcon("Edit2.png");
                            Object[] obj = {v1.get(a), v1.get(a + 1),v1.get(a + 8), v1.get(a + 2), v1.get(a + 3), v1.get(a + 4), v1.get(a + 5), v1.get(a + 6), v1.get(a + 7), img1};

                            SellRTMCustomerIR.addRow(obj);
                        }
                    }
                } catch (NullPointerException e) {
                }
            }else{
                //Load Table Data
                    //Removing Data From Table
                    SellRTMCustomerIR.setRowCount(0);
                    Vector<String> v1 = new Vector<String>();
                    try {
                        v1 = db.SellRCustomerInvoiceDbyName("Retailer");
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }



                    for(int a=0,j=0; j<(v1.size())/9;j++,a+=9)
                    {
                        ImageIcon img1 = new ImageIcon("Edit2.png");
                        Object[] obj = { v1.get(a),v1.get(a+1),v1.get(a + 8),v1.get(a+2),v1.get(a+3),v1.get(a+4),v1.get(a+5),v1.get(a+6),v1.get(a+7),img1};

                        SellRTMCustomerIR.addRow(obj);
                    }
            }
        }else{
            SellRCBCustomerNames.setEnabled(true);
        }
    }//GEN-LAST:event_SellRCBCustomerTypeActionPerformed

    private void SellRCBCustomerNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SellRCBCustomerNamesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SellRCBCustomerNamesActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        int rowcount = SellRTMCustomerIR.getRowCount();
        Vector<String> InvoiceD = new Vector<String>();
        for(int i=0,j=0;i<rowcount;i++,j+=8)
        {
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 0).toString());
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 1).toString());
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 2).toString());
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 3).toString());
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 4).toString());
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 5).toString());
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 6).toString());
            InvoiceD.add(SellRTMCustomerIR.getValueAt(i, 7).toString());
        }
        try {
            try {
                PG.CustomerPR(InvoiceD, rowcount);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        int rowcount = PurRTMSupplierIR.getRowCount();
        Vector<String> InvoiceD = new Vector<String>();
        for(int i=0,j=0;i<rowcount;i++,j+=8)
        {
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 0).toString());
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 1).toString());
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 2).toString());
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 3).toString());
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 4).toString());
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 5).toString());
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 6).toString());
            InvoiceD.add(PurRTMSupplierIR.getValueAt(i, 7).toString());
        }
        try {
            try {
                PG.SupplierPR(InvoiceD, rowcount);
            } catch (FileNotFoundException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton35ActionPerformed

    private void HRBCurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HRBCurrentActionPerformed
        // TODO add your handling code here:
        Vector<String> ProductD = new Vector<String>();
        
        try {
            ProductD = db.getOStock();
        } catch (SQLException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        HTMStock.setRowCount(0);
        for(int i=0,j=0;i<ProductD.size()/5;j+=5,i++)
        {
            Object[] obj = {ProductD.get(j),ProductD.get(j+1),ProductD.get(j+2),ProductD.get(j+3),ProductD.get(j+4)};

            HTMStock.addRow(obj);
        }
    }//GEN-LAST:event_HRBCurrentActionPerformed

    private void HRBBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HRBBackupActionPerformed
        // TODO add your handling code here:
        Vector<String> ProductD = new Vector<String>();
        try {
            ProductD = db.getNStock();
        } catch (SQLException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        HTMStock.setRowCount(0);
        for(int i=0,j=0;i<ProductD.size()/5;j+=5,i++)
        {
            Object[] obj = {ProductD.get(j),ProductD.get(j+1),ProductD.get(j+2),ProductD.get(j+3),ProductD.get(j+4)};

            HTMStock.addRow(obj);
        }
    }//GEN-LAST:event_HRBBackupActionPerformed

    private void HRBSortByExpDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HRBSortByExpDateActionPerformed
        // TODO add your handling code here:
        Vector<String> ProductD = new Vector<String>();
        HTMStock.setRowCount(0);
        try {
            ProductD = db.getExpNotify();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0,j=0;i<ProductD.size()/5;j+=5,i++)
        {
            Object[] obj = {ProductD.get(j),ProductD.get(j+1),ProductD.get(j+2),ProductD.get(j+3),ProductD.get(j+4)};

            HTMStock.addRow(obj);
        }
    }//GEN-LAST:event_HRBSortByExpDateActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        VCShowAllCustomer();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void VCCBCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VCCBCustomerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VCCBCustomerNameActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
//        System.out.println("inventorysystem.DashBoard.formWindowClosing()");
//        LoginPage lp = null;
//        try {
//            lp = new LoginPage();
//        } catch (InterruptedException ex) {
//            System.out.println(ex.getMessage());
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        lp.setSize(1366, 728);
//        lp.setVisible(true);
//        lp.setLocationRelativeTo(null);
//        lp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void jLabel111MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel111MousePressed
        // TODO add your handling code here:
        LoginPage lp = null;
        try {
            lp = new LoginPage();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        lp.setSize(1366, 728);
        lp.setVisible(true);
        lp.setLocationRelativeTo(null);
        lp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel111MousePressed

    private void DBELNewEmployeeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBELNewEmployeeMousePressed
       // TODO add your handling code here:
        if (DBELNewEmployeeV) {
            if(NewEmployeeCount == 0) {
                emp.setTitle("Add Employee");
                emp.setLocation(400, 100);
                emp.setResizable(false);
                emp.setSize(465, 570);
                emp.setVisible(true);
                emp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.setEnabled(false);
            }
        }
    }//GEN-LAST:event_DBELNewEmployeeMousePressed

    private void DBLEmployeeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBLEmployeeMousePressed
        // TODO add your handling code here:
        if (DBLEmployeeV) {
            if (SubF8MP == 0) {
                if (SubFon == 0) {
                    DBLSetting.setVisible(false);

                    //add panel
                    Features.add(SubF8);
                    Features.repaint();
                    Features.revalidate();
                    SubF8MP++;
                    SubFon = 1;
                }
            } else {
                DBLSetting.setVisible(true);
                Features.remove(SubF8);
                Features.repaint();
                Features.revalidate();
                SubF8MP--;
                SubFon = 0;
            }
        }
    }//GEN-LAST:event_DBLEmployeeMousePressed

    private void DBSLBackupMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBSLBackupMousePressed
        // TODO add your handling code here:
        if (DBSLBackupV) {
            if (DatabaseBackupCount == 0) {
                DataBaseBackup DBB = new DataBaseBackup(this.DB);
                DBB.setTitle("Database Backup");
                DBB.setSize(580, 230);
                DBB.setLocation(450, 200);
                DBB.setVisible(true);
                DBB.setResizable(false);                
                DB.setEnabled(false);
            }
        }
    }//GEN-LAST:event_DBSLBackupMousePressed

    private void DBELEmpAuthenticationMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBELEmpAuthenticationMousePressed
        // TODO add your handling code here:
        if (DBELEmpAuthenticationV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(EmployeeAuthentication);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            String pesawat[] = null;
            try {
                pesawat = db.getEmployeeNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            EACBEmpName.setModel(new DefaultComboBoxModel(pesawat));
            EACBEmpName.setSelectedIndex(-1);

            //Load Table Data
            //Removing Data From Table
            EATMEmpD.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.EAEmployeeD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }

//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit"};
            for (int i = 0, j = 0; j < (v1.size()) / 7; j++, i += 7) {
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4), v1.get(i + 5), v1.get(i + 6), img1};
                
                EATMEmpD.addRow(obj);
                
            }
        }
    }//GEN-LAST:event_DBELEmpAuthenticationMousePressed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        //Load Table Data
        //Removing Data From Table
        EATMEmpD.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.EANWEmployeeD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit"};
        for(int i=0,j=0; j<(v1.size())/7;j++,i+=7)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");

            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),v1.get(i+5),v1.get(i+6),img1};

            EATMEmpD.addRow(obj);

        }
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
        //Load Table Data
        //Removing Data From Table
        EATMEmpD.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.EAEmployeeD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit", "Delete"};

        for(int i=0,j=0; j<(v1.size())/7;j++,i+=7)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),v1.get(i+5),v1.get(i+6),img1};

            EATMEmpD.addRow(obj);

        }
    }//GEN-LAST:event_jButton41ActionPerformed

    private void EPCBSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBSettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBSettingActionPerformed

    private void EPCBProductCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBProductCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBProductCategoryActionPerformed

    private void EPCBCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBCustomerActionPerformed

    private void EPCBSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBSupplierActionPerformed

    private void EPCBInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBInventoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBInventoryActionPerformed

    private void EPCBStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBStockActionPerformed

    private void EPCBReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBReportActionPerformed

    private void EPCBProductMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBProductMasterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBProductMasterActionPerformed

    private void EPCBPendingReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBPendingReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBPendingReportActionPerformed

    private void EPCBEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBEmployeeActionPerformed

    private void EPCBHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBHomeActionPerformed

    private void EPCBAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBAddCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBAddCustomerActionPerformed

    private void EPCBViewCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBViewCustomersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBViewCustomersActionPerformed

    private void EPCBProductMasterSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBProductMasterSubActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBProductMasterSubActionPerformed

    private void EPCBStockInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBStockInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBStockInActionPerformed

    private void EPCBViewSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBViewSuppliersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBViewSuppliersActionPerformed

    private void EPCBEmpPermissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBEmpPermissionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBEmpPermissionActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        String SubPermission = "";
        String MainPermission = "";
        
        //Main Permission
        if(EPCBHome.isSelected())
        {
            MainPermission = MainPermission + "1,";
        }
        if(EPCBProductCategory.isSelected())
        {
            MainPermission = MainPermission+"2,";
        }
        if(EPCBCustomer.isSelected())
        {
            MainPermission = MainPermission+"3,";
        }
        if(EPCBSupplier.isSelected())
        {
            MainPermission = MainPermission+"4,";
        }
        if(EPCBInventory.isSelected())
        {
            MainPermission = MainPermission+"5,";
        }
        if(EPCBProductMaster.isSelected())
        {
            MainPermission = MainPermission+"6,";
        }
        if(EPCBStock.isSelected())
        {
            MainPermission = MainPermission+"7,";
        }
        if(EPCBReport.isSelected())
        {
            MainPermission = MainPermission+"8,";
        }
        if(EPCBPendingReport.isSelected())
        {
            MainPermission = MainPermission+"9,";
        }
        if(EPCBEmployee.isSelected())
        {
            MainPermission = MainPermission+"10,";
        }
        if(EPCBSetting.isSelected())
        {
            MainPermission = MainPermission+"11,";
        }
        
        
        //Sub Permission
        if(!EPCBHome.isSelected())
        {
            
        }
        if(!EPCBProductCategory.isSelected())
        {
            if(EPCBViewProduct.isSelected())
            {
                SubPermission = SubPermission + "21,";
            }
        }
        if(!EPCBCustomer.isSelected())
        {
            if(EPCBAddCustomer.isSelected())
            {
                SubPermission = SubPermission + "31,";
            }
            if(EPCBViewCustomers.isSelected())
            {
                SubPermission = SubPermission + "32,";
            }
        }
        if(!EPCBSupplier.isSelected())
        {
            if(EPCBAddSupplier.isSelected())
            {
                SubPermission = SubPermission + "41,";
            }
            if(EPCBViewSuppliers.isSelected())
            {
                SubPermission = SubPermission + "42,";
            }
        }
        if(!EPCBInventory.isSelected())
        {
            if(EPCBAddProduct.isSelected())
            {
                SubPermission = SubPermission + "51,";
            }
            if(EPCBSellProduct.isSelected())
            {
                SubPermission = SubPermission + "52,";
            }
            if(EPCBPurchaseProduct.isSelected())
            {
                SubPermission = SubPermission + "53,";
            }
        }
        if(!EPCBProductMaster.isSelected())
        {
            if(EPCBProductMasterSub.isSelected())
            {
                SubPermission = SubPermission + "61,";
            }
        }
        if(!EPCBStock.isSelected())
        {
            if(EPCBStockIn.isSelected())
            {
                SubPermission = SubPermission + "71,";
            }
        }
        if(!EPCBReport.isSelected())
        {
            if(EPCBPurchaseReport.isSelected())
            {
                SubPermission = SubPermission + "81,";
            }
            if(EPCBSellReport.isSelected())
            {
                SubPermission = SubPermission + "82,";
            }
            if(EPCBTransactionReport.isSelected())
            {
                SubPermission = SubPermission + "83,";
            }
        }
        if(!EPCBPendingReport.isSelected())
        {
            if(EPCBPendingReportSub.isSelected())
            {
                SubPermission = SubPermission + "91,";
            }
            if(EPCBNewPerson.isSelected())
            {
                SubPermission = SubPermission + "92,";
            }
        }
        if(!EPCBEmployee.isSelected())
        {
            if(EPCBNewEmployee.isSelected())
            {
                SubPermission = SubPermission + "101,";
            }
            if(EPCBEmpAuthentication.isSelected())
            {
                SubPermission = SubPermission + "102,";
            }
            if(EPCBEmpPermission.isSelected())
            {
                SubPermission = SubPermission + "103,";
            }
        }
        if(!EPCBSetting.isSelected())
        {
            if(EPCBProfileUpdate.isSelected())
            {
                SubPermission = SubPermission + "111,";
            }
            if(EPCBBackup.isSelected())
            {
                SubPermission = SubPermission + "112,";
            }
        }
//        System.out.println(MainPermission.chars());

        int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to Save");
        
        if (x == 0) {
            if (!this.EmpUserID.equals("")) {
                try {
                    MainPermission = MainPermission.substring(0, MainPermission.length() - 1);
                    SubPermission = SubPermission.substring(0, SubPermission.length() - 1);
                } catch (Exception e) {
                }
                
                try {
                    db.EmployeePermissionUpdate(MainPermission, SubPermission, this.EmpUserID);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Select Employee");
            }
        }
    }//GEN-LAST:event_jButton42ActionPerformed

    private void DBELEmpPermissionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBELEmpPermissionMousePressed
        // TODO add your handling code here:
        if (DBELEmpPermissionV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(EmployeePermission);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            String pesawat[] = null;
            try {
                pesawat = db.getEmployeeNames();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            EPCBEmpName.setModel(new DefaultComboBoxModel(pesawat));
            EPCBEmpName.setSelectedIndex(-1);
            
            this.EmpUserID = "";
            
            EPCBHome.setSelected(false);
            EPCBProductCategory.setSelected(false);
            EPCBCustomer.setSelected(false);
            EPCBSupplier.setSelected(false);
            EPCBInventory.setSelected(false);
            EPCBProductMaster.setSelected(false);
            EPCBStock.setSelected(false);
            EPCBReport.setSelected(false);
            EPCBPendingReport.setSelected(false);
            EPCBEmployee.setSelected(false);
            EPCBSetting.setSelected(false);


            EPCBViewProduct.setSelected(false);
            EPCBAddCustomer.setSelected(false);
            EPCBViewCustomers.setSelected(false);
            EPCBAddSupplier.setSelected(false);
            EPCBViewSuppliers.setSelected(false);
            EPCBAddProduct.setSelected(false);
            EPCBSellProduct.setSelected(false);
            EPCBPurchaseProduct.setSelected(false);
            EPCBProductMasterSub.setSelected(false);
            EPCBStockIn.setSelected(false);
            EPCBPurchaseProduct.setSelected(false);
            EPCBSellReport.setSelected(false);
            EPCBPendingReportSub.setSelected(false);
            EPCBNewPerson.setSelected(false);
            EPCBNewEmployee.setSelected(false);
            EPCBEmpAuthentication.setSelected(false);
            EPCBEmpPermission.setSelected(false);
            EPCBProfileUpdate.setSelected(false);
            EPCBBackup.setSelected(false);
        }
    }//GEN-LAST:event_DBELEmpPermissionMousePressed

    private void EPCBAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBAddProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBAddProductActionPerformed

    private void EPCBPurchaseReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPCBPurchaseReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPCBPurchaseReportActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // TODO add your handling code here:
        //Load Table Data
        //Removing Data From Table
        TRTMTransaction.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.RTransactionD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0, j = 0; j < (v1.size()) / 5; j++, i += 5) {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4)};

            TRTMTransaction.addRow(obj);

        }
    }//GEN-LAST:event_jButton48ActionPerformed

    private void TRDCFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TRDCFromPropertyChange
        // TODO add your handling code here:
        String date1 = null;
        String date2 = null;
        try {
            date1 = IS.dateFormat.format(TRDCFrom.getDate());
        } catch (Exception e) {}
        
        try {
            date2 = IS.dateFormat.format(TRDCTo.getDate());
        } catch (Exception e) {}
        
        try {
            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                //Load Table Data
                //Removing Data From Table
                TRTMTransaction.setRowCount(0);
                Vector<String> v1 = new Vector<String>();
                try {
                    v1 = db.RTransactionD(date1, date2);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0, j = 0; j < (v1.size()) / 5; j++, i += 5) {
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4)};

                    TRTMTransaction.addRow(obj);
                }
            }
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_TRDCFromPropertyChange

    private void TRDCToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TRDCToPropertyChange
        // TODO add your handling code here:
        String date1 = null;
        String date2 = null;
        try {
            date1 = IS.dateFormat.format(TRDCFrom.getDate());
        } catch (Exception e) {}
        
        try {
            date2 = IS.dateFormat.format(TRDCTo.getDate());
        } catch (Exception e) {}
        
        try {
            if (!date1.equals("") && date1 != null && !date2.equals("") && date2 != null) {
                //Load Table Data
                //Removing Data From Table
                TRTMTransaction.setRowCount(0);
                Vector<String> v1 = new Vector<String>();
                try {
                    v1 = db.RTransactionD(date1, date2);
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0, j = 0; j < (v1.size()) / 5; j++, i += 5) {
                    ImageIcon img1 = new ImageIcon("Edit2.png");
                    Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4)};

                    TRTMTransaction.addRow(obj);
                }
            }
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_TRDCToPropertyChange

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        // TODO add your handling code here:
        int rowcount = TRTMTransaction.getRowCount();
        try {
            try {
                PG.TransactionDR(TRTMTransaction.getValueAt(0, 4).toString(), TRTMTransaction.getValueAt(rowcount-1, 4).toString(), rowcount);
            } catch (FileNotFoundException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton49ActionPerformed

    private void DBRLTransactionReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBRLTransactionReportMousePressed
        // TODO add your handling code here:
        if (DBRLTransactionReportV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(TransactionReport);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            //Load Table Data
            //Removing Data From Table
            TRTMTransaction.setRowCount(0);
            Vector<String> v1 = new Vector<String>();
            try {
                v1 = db.RTransactionD();
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0, j = 0; j < (v1.size()) / 5; j++, i += 5) {
                ImageIcon img1 = new ImageIcon("Edit2.png");
                Object[] obj = {v1.get(i), v1.get(i + 1), v1.get(i + 2), v1.get(i + 3), v1.get(i + 4)};
                
                TRTMTransaction.addRow(obj);
                
            }
        }
    }//GEN-LAST:event_DBRLTransactionReportMousePressed

    private void PECBProductCategoryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PECBProductCategoryFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_PECBProductCategoryFocusGained

    private void PECBProductCategoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PECBProductCategoryKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_PECBProductCategoryKeyReleased

    private void PECBProductNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PECBProductNameKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_PECBProductNameKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String[] ProductM = new String[8];
        ProductM[0] = PECBProductCategory.getEditor().getItem().toString();
        ProductM[1] = PECBProductName.getEditor().getItem().toString();
        ProductM[2] = PECBProductBrand.getEditor().getItem().toString();
        int temp = 0;
        try {
            temp = Integer.parseInt(PECBSize.getEditor().getItem().toString());
            ProductM[3] = String.valueOf(temp);
        } catch (Exception e) {
            ProductM[3] = "";
        }
        ProductM[4] = PECBUOM.getEditor().getItem().toString();;
        try {
            temp = Integer.parseInt(PETFSellingPrice.getText());
            ProductM[5] = String.valueOf(temp);
        } catch (Exception e) {
            ProductM[5] = "";
        }
        try {
            ProductM[6] = PETADiscription.getText();
        } catch (NullPointerException e) {
           ProductM[6] = ""; 
        }
        long barcode=0; 
        try {
            barcode = Long.parseLong(PETFBarcode.getText());
            ProductM[7] = String.valueOf(barcode);
        } catch (Exception e) {
            ProductM[7] = "";
        }
        
        for(int i=0;i<ProductM.length;i++)
        {
            ProductM[i].trim();
        }
        
        if(ProductM[0] == null || ProductM[0].equals(""))
        {
            PELProductCategory.setForeground(Color.red);
        }else if(ProductM[1] == null || ProductM[1].equals(""))
        {
            PELProductCategory.setForeground(Color.WHITE);
            PELProductName.setForeground(Color.red);
        }else if(ProductM[2] == null || ProductM[2].equals(""))
        {
            PELProductCategory.setForeground(Color.WHITE);
            PELProductName.setForeground(Color.WHITE);
            PELProductBrand.setForeground(Color.red);
        }else if(ProductM[3] == null || ProductM[3].equals(""))
        {
            PELProductCategory.setForeground(Color.WHITE);
            PELProductName.setForeground(Color.WHITE);
            PELProductBrand.setForeground(Color.WHITE);
            PELSize.setForeground(Color.red);
        }else if(ProductM[4] == null || ProductM[4].equals(""))
        {
            PELProductCategory.setForeground(Color.WHITE);
            PELProductName.setForeground(Color.WHITE);
            PELProductBrand.setForeground(Color.WHITE);
            PELSize.setForeground(Color.WHITE);
            PELUOM.setForeground(Color.red);
        }else if(ProductM[5] == null || ProductM[5].equals(""))
        {
            PELProductCategory.setForeground(Color.WHITE);
            PELProductName.setForeground(Color.WHITE);
            PELProductBrand.setForeground(Color.WHITE);
            PELSize.setForeground(Color.WHITE);
            PELUOM.setForeground(Color.WHITE);
            PELSellingPrice.setForeground(Color.red);
            
        }else if(ProductM[7].length() != 13)
        {
            PELProductCategory.setForeground(Color.WHITE);
            PELProductName.setForeground(Color.WHITE);
            PELProductBrand.setForeground(Color.WHITE);
            PELSize.setForeground(Color.WHITE);
            PELUOM.setForeground(Color.WHITE);
            PELSellingPrice.setForeground(Color.WHITE);
            PELBarcode.setForeground(Color.red);
            
        }else{
            PELProductCategory.setForeground(Color.WHITE);
            PELProductName.setForeground(Color.WHITE);
            PELProductBrand.setForeground(Color.WHITE);
            PELSize.setForeground(Color.WHITE);
            PELUOM.setForeground(Color.WHITE);
            PELSellingPrice.setForeground(Color.WHITE);
            PELBarcode.setForeground(Color.WHITE);
            
            try {
                db.ProductMaster(ProductM);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                db.setProductCategory(PECBProductCategory);
                db.setProductName(PECBProductName);
                db.setProductBrand(PECBProductBrand);
                db.setProductSizeandUOM(PECBSize,PECBUOM);
                PETFBarcode.setText("");
                PETADiscription.setText("");
                PETFSellingPrice.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void DBILAddProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBILAddProductMousePressed
        // TODO add your handling code here:
        if (DBPCLAddProductV) {
            
            //remove panel
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(ProductEntery);
            jPanel1.repaint();
            jPanel1.revalidate();
            
            try {
                db.setProductCategory(PECBProductCategory);
                db.setProductName(PECBProductName);
                db.setProductBrand(PECBProductBrand);
                db.setProductSizeandUOM(PECBSize,PECBUOM);
                PETFBarcode.setText("");
                PETADiscription.setText("");
                PETFSellingPrice.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
//            System.out.println(running.get());
            if(!running.get())
            {
                running.set(true);
            }
        }
    }//GEN-LAST:event_DBILAddProductMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser js = new JFileChooser();
        String txtbackuppath = null;
        js.setFileSelectionMode(JFileChooser.CUSTOM_DIALOG);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg","JPG","*png","PNG");
        js.setFileFilter(filter);
        int r = js.showSaveDialog(DB);
        
        if(r == JFileChooser.APPROVE_OPTION)
        {
            txtbackuppath = js.getSelectedFile().getAbsolutePath();
            txtbackuppath = txtbackuppath.replace("\\", "/");
            PETFImage.setText(txtbackuppath);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
//        try {
//            // TODO add your handling code here:
//            db.InserNewProductImage(PETFImage.getText().trim());
//        } catch (SQLException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        String Fname = null;
        File f2 = null;
        File f1 = null;
        BufferedImage image = null;
        
        Fname = PETFImage.getText().trim();
        String temp = "";
        try {
            temp = Fname.substring(Fname.length() - 4, Fname.length());
        } catch (Exception e) {
            temp = "";
        }
//        System.out.println(temp);
        if(temp.equals(".jpg") || temp.equals(".JPG") || temp.equals(".png") || temp.equals(".PNG"))
        {
            try {
                f1 = new File(Fname);
                image = ImageIO.read(f1);
            } catch (IOException ex) {
                System.out.println("Error: "+ex);
            }

//            int count = 0;
//            File f =  new File("E:/java/aman/aman2/InventorySystem/src/ProductImages/");
//            for(File file : f.listFiles())
//            {
//                if(file.isFile() && file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG") || file.getName().endsWith(".png") || file.getName().endsWith(".PNG"))
//                {
//                    count++;
//                }
//            }
//            System.out.println("Count : "+count);
//             write image
            try{
                
                String ImageName  = JOptionPane.showInputDialog(null, "Enter Valid Image Name");
                if((ImageName != null) && pattern2.matcher(ImageName).matches())
                {
                    String str = ""+ImageName+".jpg";
                    f2 = new File(ImageSliderPath+ImageName+temp+"");
//                    try {
//                        // TODO add your handling code here:
//                        db.InserNewProductImage(str);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    if(temp.endsWith(".jpg"))
                    {
                        ImageIO.write(image, "jpg", f2);
                    }else if(temp.equals(".JPG")){
                        ImageIO.write(image, "JPG", f2);
                    }else if(temp.equals(".png")){
                        ImageIO.write(image, "png", f2);
                    }else{
                        ImageIO.write(image, "PNG", f2);
                    }
                    PETFImage.setText("");
                }else{
                    JOptionPane.showMessageDialog(null, "Enter Valid Name!");
                }
            }catch(IOException e){
//              System.out.println("Error: "+e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please Choose Image!");
        }
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:#
        Vector<String> ProductD = new Vector<String>();
        jPanelSlider1.nextPanel(10,ProfitDetails, jPanelSlider1.left);
        DefaultTableModel DBTMMonthSoldProduct = (DefaultTableModel) DBTMonthSoldProduct.getModel();
        try {
            ProductD = db.getMonthSoldProduct();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBTMMonthSoldProduct.setRowCount(0);
        for (int i = 0, j = 0; i < ProductD.size() / 6; j += 6, i++) {
            Object[] obj = {ProductD.get(j), ProductD.get(j + 1), ProductD.get(j + 2), ProductD.get(j + 3), ProductD.get(j + 4),ProductD.get(j + 5)};

            DBTMMonthSoldProduct.addRow(obj);
        }
        String date = IS.dateFormat.format(IS.dt);
        
        DBMCMonthProfit.setMonth((Integer.parseInt(date.substring(5, 7)))-1);
        int Year = 0;
        int Month = 0;
        try {
            Month = DBMCMonthProfit.getMonth();
            Year = DBYCYearProfit.getYear();
        } catch (Exception e) {
        }
//        System.out.println(Month+1);
        String Date = Year+"-"+(Month+1)+"-"+"01";
        
        String[] Profit = new String[4];
        try {
            Profit = db.getMothProfit(Date);
        } catch (SQLException sQLException) {
        }catch (NullPointerException ex) {
            Profit[0] = "";
            Profit[1] = "";
        }
        try {
            int index = 0;
            if(Profit[0].equalsIgnoreCase("") && Profit[2].equalsIgnoreCase(""))
            {
                index = Profit[0].indexOf(".");
                Profit[0] = Profit[0].substring(0, index+3);
                index = Profit[2].indexOf(".");
                Profit[2] = Profit[2].substring(0, index+3);
            }
            DBLMonthPofitPercent.setText(Profit[0] + "%");
            DBLMonthPofitRupee.setText(Profit[1] + "/-");
            DBLYearPofitPercent.setText(Profit[2] + "%");
            DBLYearPofitRupee.setText(Profit[3] + "/-");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jPanelSlider1.nextPanel(10,StockandExpNotify, jPanelSlider1.right);
        HRBCurrent.setSelected(true);
            
        Vector<String> ProductD = new Vector<String>();
        HTMStock = (DefaultTableModel) HTStock.getModel();
        try {
            ProductD = db.getOStock();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        HTMStock.setRowCount(0);
        for (int i = 0, j = 0; i < ProductD.size() / 5; j += 5, i++) {
            Object[] obj = {ProductD.get(j), ProductD.get(j + 1), ProductD.get(j + 2), ProductD.get(j + 3), ProductD.get(j + 4)};

            HTMStock.addRow(obj);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        HRBCurrent.setSelected(true);
            
        Vector<String> ProductD = new Vector<String>();
        HTMStock = (DefaultTableModel) HTStock.getModel();
        try {
            ProductD = db.getOStock();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        HTMStock.setRowCount(0);
        for (int i = 0, j = 0; i < ProductD.size() / 5; j += 5, i++) {
            Object[] obj = {ProductD.get(j), ProductD.get(j + 1), ProductD.get(j + 2), ProductD.get(j + 3), ProductD.get(j + 4)};

            HTMStock.addRow(obj);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        Vector<String> ProductD = new Vector<String>();
        jPanelSlider1.nextPanel(10,ProfitDetails, jPanelSlider1.left);
        DefaultTableModel DBTMMonthSoldProduct = (DefaultTableModel) DBTMonthSoldProduct.getModel();
        try {
            ProductD = db.getMonthSoldProduct();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBTMMonthSoldProduct.setRowCount(0);
        for (int i = 0, j = 0; i < ProductD.size() / 6; j += 6, i++) {
            Object[] obj = {ProductD.get(j), ProductD.get(j + 1), ProductD.get(j + 2), ProductD.get(j + 3), ProductD.get(j + 4),ProductD.get(j + 5)};

            DBTMMonthSoldProduct.addRow(obj);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void PUTFConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PUTFConfirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PUTFConfirmPasswordActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        String[] AdminD = new String[5];
        
        try {
            AdminD[0] = PUTFAdminID.getText();
        } catch (Exception e) {
            AdminD[0] = "";
        }
        try {
        AdminD[1] = PUTFCurrentPassword.getText();
        } catch (Exception e) {
            AdminD[1] = "";
        }
        try {
        AdminD[2] = PUTFNewPassword.getText();
        } catch (Exception e) {
            AdminD[2] = "";
        }
        try {
        AdminD[3] = PUTFConfirmPassword.getText();
        } catch (Exception e) {
            AdminD[3] = "";
        }
        
        AdminD[4] = AdminUserID;
        
        if(AdminD[0].equals(""))
        {
            PULAdminID.setForeground(Color.red);
        }else if(AdminD[1].equals(""))
        {
            PULAdminID.setForeground(Color.WHITE);
            PULCurrentPassword.setForeground(Color.red);
        }else if(!AdminD[2].equals(AdminD[3]) || AdminD[2].equals("") || AdminD[3].equals(""))
        {
            PULAdminID.setForeground(Color.WHITE);
            PULCurrentPassword.setForeground(Color.WHITE);
            PULNewPassword.setForeground(Color.red);
            PULConfirmPassword.setForeground(Color.red);
        }else{
            PULAdminID.setForeground(Color.WHITE);
            PULCurrentPassword.setForeground(Color.WHITE);
            PULNewPassword.setForeground(Color.WHITE);
            PULConfirmPassword.setForeground(Color.WHITE);
                
            try {
                db.UpdateAdminD(AdminD);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jButton29ActionPerformed

    private void PUTFAdminIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PUTFAdminIDKeyReleased
        // TODO add your handling code here:
        String name = PUTFAdminID.getText();
        boolean valid = (name != null) && pattern2.matcher(name).matches();
        if(!valid)
        {
            try {
                name = name.substring(0, name.length() - 1);
            } catch (Exception e) {
            }
            PUTFAdminID.setText(name);
        }
    }//GEN-LAST:event_PUTFAdminIDKeyReleased

    private void PUTFCurrentPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PUTFCurrentPasswordKeyReleased
        // TODO add your handling code here:
        String name = PUTFCurrentPassword.getText();
        boolean valid = (name != null) && pattern.matcher(name).matches();
        if(!valid)
        {
            try {
                name = name.substring(0, name.length() - 1);
            } catch (Exception e) {
            }
            PUTFCurrentPassword.setText(name);
        }
    }//GEN-LAST:event_PUTFCurrentPasswordKeyReleased

    private void PUTFNewPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PUTFNewPasswordKeyReleased
        // TODO add your handling code here:
        String name = PUTFNewPassword.getText();
        boolean valid = (name != null) && pattern.matcher(name).matches();
        if(!valid)
        {
            try {
                name = name.substring(0, name.length() - 1);
            } catch (Exception e) {
            }
            PUTFNewPassword.setText(name);
        }
    }//GEN-LAST:event_PUTFNewPasswordKeyReleased

    private void PUTFConfirmPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PUTFConfirmPasswordKeyReleased
        // TODO add your handling code here:
        String name = PUTFConfirmPassword.getText();
        boolean valid = (name != null) && pattern.matcher(name).matches();
        if(!valid)
        {
            try {
                name = name.substring(0, name.length() - 1);
            } catch (Exception e) {
            }
            PUTFConfirmPassword.setText(name);
        }
    }//GEN-LAST:event_PUTFConfirmPasswordKeyReleased

    private void DBMCMonthProfitPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DBMCMonthProfitPropertyChange
        // TODO add your handling code here:
        int Year = 0;
        int Month = 0;
        try {
            Month = DBMCMonthProfit.getMonth();
            Year = DBYCYearProfit.getYear();
        } catch (Exception e) {
        }
//        System.out.println(Month+1);
        String Date = Year+"-"+(Month+1)+"-"+"01";

        String[] Profit = new String[4];
        try {
            Profit = db.getMothProfit(Date);
        } catch (SQLException sQLException) {
        }catch (NullPointerException ex) {
            Profit[0] = "";
            Profit[1] = "";
        }
        try {
            int index = 0;
            if(!Profit[0].equals("") && !Profit[2].equals(""))
            {
                index = Profit[0].indexOf(".");
                Profit[0] = Profit[0].substring(0, index+3);
                index = Profit[2].indexOf(".");
                Profit[2] = Profit[2].substring(0, index+3);
            }
        } catch (Exception e) {
        }
        try {
            DBLMonthPofitPercent.setText(Profit[0] + "%");
            DBLMonthPofitRupee.setText(Profit[1] + "/-");
            DBLYearPofitPercent.setText(Profit[2] + "%");
            DBLYearPofitRupee.setText(Profit[3] + "/-");
        } catch (Exception e) {
        }
        
        
    }//GEN-LAST:event_DBMCMonthProfitPropertyChange

    private void DBYCYearProfitPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DBYCYearProfitPropertyChange
        // TODO add your handling code here:
        int Year = 0;
        int Month = 0;
        try {
            Month = DBMCMonthProfit.getMonth();
            Year = DBYCYearProfit.getYear();
        } catch (Exception e) {
        }
//        System.out.println(Month+1);
        String Date = Year+"-"+(Month+1)+"-"+"01";

        String[] Profit = new String[4];
        try {
            Profit = db.getMothProfit(Date);
        } catch (SQLException sQLException) {
        }catch (NullPointerException ex) {
            Profit[0] = "";
            Profit[1] = "";
        }
        try {
            int index = 0;
            if(!Profit[0].equals("") && !Profit[2].equals(""))
            {
                index = Profit[0].indexOf(".");
                Profit[0] = Profit[0].substring(0, index+3);
                index = Profit[2].indexOf(".");
                Profit[2] = Profit[2].substring(0, index+3);
            }
        } catch (Exception e) {
        }
        try {
            DBLMonthPofitPercent.setText(Profit[0] + "%");
            DBLMonthPofitRupee.setText(Profit[1] + "/-");
            DBLYearPofitPercent.setText(Profit[2] + "%");
            DBLYearPofitRupee.setText(Profit[3] + "/-");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_DBYCYearProfitPropertyChange

    private void DBSLProfileUpdateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBSLProfileUpdateMousePressed
        // TODO add your handling code here:
        if (DBSLProfileUpdateV) {
            jPanel1.removeAll();
            jPanel1.repaint();
            jPanel1.revalidate();

            //add panel
            jPanel1.add(ProfileUpdate);            
            jPanel1.repaint();
            jPanel1.revalidate();
            
            String[] shopdetails = new String[6];
            try {
                shopdetails = db.ShopsDetail();
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PUTFShopName.setText(shopdetails[0]);
            PUTFAddress.setText(shopdetails[1]);
            PUTFNear.setText(shopdetails[2]);
            PUTFCity.setText(shopdetails[3]);
            PUTFPinCode.setText(shopdetails[4]);
            PUTFContactNo.setText(shopdetails[5]);
            PUTFWebSite.setText(shopdetails[6]);
            int[] logodetails = null;
            try {
                logodetails = db.getLogoDetails();
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                SpinnerModel model1 = new SpinnerNumberModel(logodetails[0], 0, 300, 1);
                SpinnerModel model2 = new SpinnerNumberModel(logodetails[1], 0, 300, 1);
                PULogoWidth.setModel(model1);
                PULogoPosition.setModel(model2);
            } catch (Exception e) {
            }
            
        }
    }//GEN-LAST:event_DBSLProfileUpdateMousePressed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        String[] ShopDetails = new String[7];
        
        ShopDetails[0] = PUTFShopName.getText();
        ShopDetails[1] = PUTFAddress.getText();
        ShopDetails[2] = PUTFNear.getText();
        ShopDetails[3] = PUTFCity.getText();
        ShopDetails[4] = PUTFPinCode.getText();
        ShopDetails[5] = PUTFContactNo.getText();
        ShopDetails[6] = PUTFWebSite.getText();
        
        if(ShopDetails[0] == null || ShopDetails[0].equals("")) {
            PULShopName.setForeground(Color.red);
        }else if(ShopDetails[1] == null || ShopDetails[1].equals("")) {
            PULShopName.setForeground(Color.WHITE);
            PULAddress.setForeground(Color.red);
        }else if(ShopDetails[2] == null || ShopDetails[2].equals("")) {
            PULShopName.setForeground(Color.white);
            PULAddress.setForeground(Color.white);
            PULNear.setForeground(Color.red);
        }else if(ShopDetails[3] == null || ShopDetails[3].equals("")) {
            PULShopName.setForeground(Color.white);
            PULAddress.setForeground(Color.white);
            PULNear.setForeground(Color.white);
            PULCity.setForeground(Color.red);
        }else if((ShopDetails[4] == null) || !ShopDPattern.matcher(ShopDetails[4]).matches()) {
            PULShopName.setForeground(Color.white);
            PULAddress.setForeground(Color.white);
            PULNear.setForeground(Color.white);
            PULCity.setForeground(Color.white);
            PULPinCode.setForeground(Color.red);
        }else if((ShopDetails[5] == null) || !ShopDPattern.matcher(ShopDetails[5]).matches()) {
            PULShopName.setForeground(Color.white);
            PULAddress.setForeground(Color.white);
            PULNear.setForeground(Color.white);
            PULCity.setForeground(Color.white);
            PULPinCode.setForeground(Color.white);
            PULContactNo.setForeground(Color.red);
        }else if((ShopDetails[6] == null) || ShopDetails.equals("")) {
            PULShopName.setForeground(Color.white);
            PULAddress.setForeground(Color.white);
            PULNear.setForeground(Color.white);
            PULCity.setForeground(Color.white);
            PULPinCode.setForeground(Color.white);
            PULContactNo.setForeground(Color.white);
            PULWebSite.setForeground(Color.red);
        }else {
            PULShopName.setForeground(Color.white);
            PULAddress.setForeground(Color.white);
            PULNear.setForeground(Color.white);
            PULCity.setForeground(Color.white);
            PULPinCode.setForeground(Color.white);
            PULContactNo.setForeground(Color.white);
            PULWebSite.setForeground(Color.white);
            
            try {
                db.ShopDetails(ShopDetails);
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        String Fname = null;
        File f2 = null;
        File f1 = null;
        BufferedImage image = null;
        
        Fname = PUTFImage.getText().trim();
        String temp = "";
        try {
            temp = Fname.substring(Fname.length() - 4, Fname.length());
        } catch (Exception e) {
            temp = "";
        }
//        System.out.println(temp);
        if(temp.equals(".jpg") || temp.equals(".JPG") || temp.equals(".png") || temp.equals(".PNG"))
        {
            try {
                f1 = new File(Fname);
                image = ImageIO.read(f1);
            } catch (IOException ex) {
                System.out.println("Error: "+ex);
            }
            try{
                f2 = new File("src/Images/Shop-Logo.png");
                if(temp.endsWith(".jpg"))
                {
                    ImageIO.write(image, "jpg", f2);
                }else if(temp.equals(".JPG")){
                    ImageIO.write(image, "JPG", f2);
                }else if(temp.equals(".png")){
                    ImageIO.write(image, "png", f2);
                }else{
                    ImageIO.write(image, "PNG", f2);
                }
                PUTFImage.setText("");
                
            }catch(IOException e){
//              System.out.println("Error: "+e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please Choose Image!");
        }
        JOptionPane.showMessageDialog(null, "Logo Saved Successfully!");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        JFileChooser js = new JFileChooser();
        String txtbackuppath = null;
        js.setFileSelectionMode(JFileChooser.CUSTOM_DIALOG);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg","JPG","*png","PNG");
        js.setFileFilter(filter);
        int r = js.showSaveDialog(DB);
        
        if(r == JFileChooser.APPROVE_OPTION)
        {
            txtbackuppath = js.getSelectedFile().getAbsolutePath();
            txtbackuppath = txtbackuppath.replace("\\", "/");
            PUTFImage.setText(txtbackuppath);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear T&C.");
        if(i > 0) {
            try {
                // TODO add your handling code here:
                db.Clear_Terms_Conditions();
            } catch (SQLException ex) {
    //            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton43ActionPerformed

    private void PUBAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PUBAddActionPerformed
        // TODO add your handling code here:
        String TermsCondition = PUTFTermsAndCondition.getText();
        if(TermsCondition != null && pattern3.matcher(TermsCondition).matches()) {
            try {
                db.Terms_Conditions(TermsCondition);
                PUTFTermsAndCondition.setText("");
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Enter The Valid Terms and Conditions.");
        }
    }//GEN-LAST:event_PUBAddActionPerformed

    private void PUBUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PUBUpdateActionPerformed
        // TODO add your handling code here:
        try {
            Integer width = new Integer((int) PULogoWidth.getModel().getValue());
            Integer position = new Integer((int) PULogoPosition.getModel().getValue());
            db.LogoDetails(width, position);
        } catch (NumberFormatException e) {
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PUBUpdateActionPerformed

    private void PUBAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PUBAdd2ActionPerformed
        
        // TODO add your handling code here:
        try {
            try {
                
                Integer width = new Integer((int) PULogoWidth.getModel().getValue());
                Integer position = new Integer((int) PULogoPosition.getModel().getValue());
                PG.TemporaryInvoice(width,position);
            } catch (NumberFormatException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PUBAdd2ActionPerformed

    private void DBSLBackupMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBSLBackupMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_DBSLBackupMouseReleased
    
    void RefreshEAEmployeeTable()
    {
        String pesawat[] = null;
        try {
            pesawat = db.getEmployeeNames();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        EACBEmpName.setModel(new DefaultComboBoxModel(pesawat));
        EACBEmpName.setSelectedIndex(-1);
        
        //Load Table Data
        //Removing Data From Table
        EATMEmpD.setRowCount(0);
        Vector<String> v1 = new Vector<String>();
        try {
            v1 = db.EAEmployeeD();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit", "Delete"};

        for(int i=0,j=0; j<(v1.size())/7;j++,i+=7)
        {
            ImageIcon img1 = new ImageIcon("Edit2.png");
            Object[] obj = { v1.get(i),v1.get(i+1),v1.get(i+2),v1.get(i+3),v1.get(i+4),v1.get(i+5),v1.get(i+6),img1};

            EATMEmpD.addRow(obj);

        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DBCLAddCustomer;
    private javax.swing.JLabel DBCLViewCustomer;
    private javax.swing.JLabel DBELEmpAuthentication;
    private javax.swing.JLabel DBELEmpPermission;
    private javax.swing.JLabel DBELNewEmployee;
    private javax.swing.JLabel DBILAddProduct;
    private javax.swing.JLabel DBILPurchaseProduct;
    private javax.swing.JLabel DBILSellProduct;
    private javax.swing.JLabel DBLAccountType;
    private javax.swing.JLabel DBLAccountUserName;
    private javax.swing.JLabel DBLCustomer;
    private javax.swing.JLabel DBLEmployee;
    private javax.swing.JLabel DBLHome;
    private javax.swing.JLabel DBLInventory;
    private javax.swing.JLabel DBLMonthPofitPercent;
    private javax.swing.JLabel DBLMonthPofitRupee;
    private javax.swing.JLabel DBLPendingReport;
    private javax.swing.JLabel DBLProductCategory;
    private javax.swing.JLabel DBLProductImages;
    private javax.swing.JLabel DBLProductMaster;
    private javax.swing.JLabel DBLReport;
    private javax.swing.JLabel DBLSetting;
    private javax.swing.JLabel DBLStock;
    private javax.swing.JLabel DBLSupplier;
    private javax.swing.JLabel DBLYearPofitPercent;
    private javax.swing.JLabel DBLYearPofitRupee;
    private com.toedter.calendar.JMonthChooser DBMCMonthProfit;
    private javax.swing.JLabel DBPCLAddProduct;
    private javax.swing.JLabel DBPCLViewProduct;
    private javax.swing.JLabel DBPMLProductMaster;
    private javax.swing.JLabel DBPRLNewPerson;
    private javax.swing.JLabel DBPRLPendingReporet;
    private javax.swing.JLabel DBRLPurchaseReport;
    private javax.swing.JLabel DBRLSellReport;
    private javax.swing.JLabel DBRLTransactionReport;
    private javax.swing.JLabel DBSLAddSupplier;
    private javax.swing.JLabel DBSLBackup;
    private javax.swing.JLabel DBSLProfileUpdate;
    private javax.swing.JLabel DBSLStockIn;
    private javax.swing.JLabel DBSLViewSupplier;
    private javax.swing.JTable DBTMonthSoldProduct;
    private com.toedter.calendar.JYearChooser DBYCYearProfit;
    private javax.swing.JComboBox EACBEmpName;
    private javax.swing.JCheckBox EPCBAddCustomer;
    private javax.swing.JCheckBox EPCBAddProduct;
    private javax.swing.JCheckBox EPCBAddSupplier;
    private javax.swing.JCheckBox EPCBBackup;
    private javax.swing.JCheckBox EPCBCustomer;
    private javax.swing.JCheckBox EPCBEmpAuthentication;
    private javax.swing.JComboBox EPCBEmpName;
    private javax.swing.JCheckBox EPCBEmpPermission;
    private javax.swing.JCheckBox EPCBEmployee;
    private javax.swing.JCheckBox EPCBHome;
    private javax.swing.JCheckBox EPCBInventory;
    private javax.swing.JCheckBox EPCBNewEmployee;
    private javax.swing.JCheckBox EPCBNewPerson;
    private javax.swing.JCheckBox EPCBPendingReport;
    private javax.swing.JCheckBox EPCBPendingReportSub;
    private javax.swing.JCheckBox EPCBProductCategory;
    private javax.swing.JCheckBox EPCBProductMaster;
    private javax.swing.JCheckBox EPCBProductMasterSub;
    private javax.swing.JCheckBox EPCBProfileUpdate;
    private javax.swing.JCheckBox EPCBPurchaseProduct;
    private javax.swing.JCheckBox EPCBPurchaseReport;
    private javax.swing.JCheckBox EPCBReport;
    private javax.swing.JCheckBox EPCBSellProduct;
    private javax.swing.JCheckBox EPCBSellReport;
    private javax.swing.JCheckBox EPCBSetting;
    private javax.swing.JCheckBox EPCBStock;
    private javax.swing.JCheckBox EPCBStockIn;
    private javax.swing.JCheckBox EPCBSupplier;
    private javax.swing.JCheckBox EPCBTransactionReport;
    private javax.swing.JCheckBox EPCBViewCustomers;
    private javax.swing.JCheckBox EPCBViewProduct;
    private javax.swing.JCheckBox EPCBViewSuppliers;
    private javax.swing.JLabel EPLSelectedEmp;
    private javax.swing.JPanel EmployeeAuthentication;
    private javax.swing.JPanel EmployeePermission;
    private javax.swing.JPanel Features;
    private javax.swing.JRadioButton HRBBackup;
    private javax.swing.JRadioButton HRBCurrent;
    private javax.swing.JRadioButton HRBSortByExpDate;
    private javax.swing.JTable HTStock;
    private javax.swing.JPanel Home;
    private javax.swing.JLabel LGrossAmount;
    private javax.swing.JComboBox PECBProductBrand;
    public javax.swing.JComboBox PECBProductCategory;
    private javax.swing.JComboBox PECBProductName;
    private javax.swing.JComboBox PECBSize;
    private javax.swing.JComboBox PECBUOM;
    private javax.swing.JLabel PELBarcode;
    private javax.swing.JLabel PELDiscription;
    private javax.swing.JLabel PELImage;
    private javax.swing.JLabel PELProductBrand;
    private javax.swing.JLabel PELProductCategory;
    private javax.swing.JLabel PELProductImages;
    private javax.swing.JLabel PELProductName;
    private javax.swing.JLabel PELSellingPrice;
    private javax.swing.JLabel PELSize;
    private javax.swing.JLabel PELUOM;
    private javax.swing.JTextArea PETADiscription;
    private javax.swing.JTextField PETFBarcode;
    private javax.swing.JTextField PETFImage;
    private javax.swing.JTextField PETFSellingPrice;
    private javax.swing.JButton PPBAddProduct;
    private javax.swing.JButton PPBAddSupplier;
    private javax.swing.JComboBox PPCBPaymentMode;
    private javax.swing.JComboBox PPCBProductBrand;
    private javax.swing.JComboBox PPCBProductCategory;
    private javax.swing.JComboBox PPCBProductName;
    private javax.swing.JComboBox PPCBSize;
    private javax.swing.JComboBox PPCBSupplierName;
    private javax.swing.JComboBox PPCBUOM;
    private com.toedter.calendar.JDateChooser PPDCEXPNotifyDate;
    private com.toedter.calendar.JDateChooser PPDCExpDate;
    private com.toedter.calendar.JDateChooser PPDCMfgDate;
    private javax.swing.JTextField PPInvoiceDate;
    private javax.swing.JLabel PPLDiscount;
    private javax.swing.JLabel PPLDues;
    private javax.swing.JLabel PPLEXPNotifyDate;
    private javax.swing.JLabel PPLExpDate;
    private javax.swing.JLabel PPLGST;
    private javax.swing.JLabel PPLGrossAmount;
    private javax.swing.JLabel PPLMfgDate;
    private javax.swing.JLabel PPLPaidAmount;
    private javax.swing.JLabel PPLProductBrand;
    private javax.swing.JLabel PPLProductCategory;
    private javax.swing.JLabel PPLProductName;
    private javax.swing.JLabel PPLPurchaseRate;
    private javax.swing.JLabel PPLQuantity;
    private javax.swing.JLabel PPLSellingRate;
    private javax.swing.JLabel PPLSize;
    private javax.swing.JLabel PPLUOM;
    private javax.swing.JTextField PPTFDiscount;
    private javax.swing.JTextField PPTFDues;
    private javax.swing.JTextField PPTFGST;
    private javax.swing.JTextField PPTFINvoiceID;
    private javax.swing.JTextField PPTFNetAmount;
    private javax.swing.JTextField PPTFPaidAmount;
    private javax.swing.JTextField PPTFPurchaseRate;
    private javax.swing.JTextField PPTFQuantity;
    private javax.swing.JTextField PPTFSellingRate;
    private javax.swing.JTextField PPTFTotalAmount;
    private javax.swing.JComboBox PRCBCustomerName;
    private javax.swing.JComboBox PRCBPersonToPay;
    private javax.swing.JComboBox PRCBPersonToReceive;
    private javax.swing.JComboBox PRCBSupplierName;
    private javax.swing.JRadioButton PRRBCS;
    private javax.swing.JRadioButton PRRBNP;
    private javax.swing.JButton PUBAdd;
    private javax.swing.JButton PUBAdd2;
    private javax.swing.JButton PUBUpdate;
    private javax.swing.JLabel PULAddress;
    private javax.swing.JLabel PULAdminID;
    private javax.swing.JLabel PULCity;
    private javax.swing.JLabel PULConfirmPassword;
    private javax.swing.JLabel PULContactNo;
    private javax.swing.JLabel PULCurrentPassword;
    private javax.swing.JLabel PULImage;
    private javax.swing.JLabel PULNear;
    private javax.swing.JLabel PULNewPassword;
    private javax.swing.JLabel PULPinCode;
    private javax.swing.JLabel PULShopName;
    private javax.swing.JLabel PULWebSite;
    private javax.swing.JSpinner PULogoPosition;
    private javax.swing.JSpinner PULogoWidth;
    private javax.swing.JTextField PUTFAddress;
    private javax.swing.JTextField PUTFAdminID;
    private javax.swing.JTextField PUTFCity;
    private javax.swing.JPasswordField PUTFConfirmPassword;
    private javax.swing.JTextField PUTFContactNo;
    private javax.swing.JPasswordField PUTFCurrentPassword;
    private javax.swing.JTextField PUTFImage;
    private javax.swing.JTextField PUTFNear;
    private javax.swing.JPasswordField PUTFNewPassword;
    private javax.swing.JTextField PUTFPinCode;
    private javax.swing.JTextField PUTFShopName;
    private javax.swing.JTextField PUTFTermsAndCondition;
    private javax.swing.JTextField PUTFWebSite;
    private javax.swing.JPanel Pay;
    private javax.swing.JPanel PendingReport;
    private javax.swing.JPanel PersonPay;
    private javax.swing.JPanel PersonReceive;
    private javax.swing.JPanel ProductEntery;
    private javax.swing.JPanel ProductPurchase;
    private javax.swing.JPanel ProfileUpdate;
    private javax.swing.JPanel ProfitDetails;
    private javax.swing.JComboBox PurRCBSupplierNames;
    private com.toedter.calendar.JDateChooser PurRDCFrom;
    private com.toedter.calendar.JDateChooser PurRDCTo;
    private javax.swing.JPanel PurchaseReport;
    private javax.swing.JPanel Receive;
    private javax.swing.JComboBox SCBProductBrand;
    private javax.swing.JComboBox SCBProductCategory;
    private javax.swing.JComboBox SCBProductName;
    private javax.swing.JButton SPBAddCustomer;
    private javax.swing.JComboBox SPCBCustomerName;
    private javax.swing.JComboBox SPCBCustomerType;
    private javax.swing.JComboBox SPCBPaymentMode;
    private javax.swing.JComboBox SPCBProductBrand;
    private javax.swing.JComboBox SPCBProductCategory;
    private javax.swing.JComboBox SPCBProductName;
    private javax.swing.JComboBox SPCBSize;
    private javax.swing.JComboBox SPCBUOM;
    private javax.swing.JTextField SPInvoiceDate;
    private javax.swing.JLabel SPLAadharNo;
    private javax.swing.JLabel SPLAvailableQ;
    private javax.swing.JLabel SPLCustomerType;
    private javax.swing.JLabel SPLCustomerType1;
    private javax.swing.JLabel SPLDiscount;
    private javax.swing.JLabel SPLDues;
    private javax.swing.JLabel SPLGST;
    private javax.swing.JLabel SPLGSTNo;
    private javax.swing.JLabel SPLGrossAmount;
    private javax.swing.JLabel SPLInvoiceDate;
    private javax.swing.JLabel SPLInvoiceNo;
    private javax.swing.JLabel SPLMobileNo;
    private javax.swing.JLabel SPLNetAmount;
    private javax.swing.JLabel SPLPaidAmount;
    private javax.swing.JLabel SPLPaymentMode;
    private javax.swing.JLabel SPLPreDues;
    private javax.swing.JLabel SPLProductBrand;
    private javax.swing.JLabel SPLProductCategory;
    private javax.swing.JLabel SPLProductName;
    private javax.swing.JLabel SPLQuantity;
    private javax.swing.JLabel SPLSellingPrice;
    private javax.swing.JLabel SPLSize;
    private javax.swing.JLabel SPLTotalAmount;
    private javax.swing.JLabel SPLUOM;
    private javax.swing.JTextField SPTFAadharNo;
    private javax.swing.JTextField SPTFAvailableQ;
    private javax.swing.JTextField SPTFDiscount;
    private javax.swing.JTextField SPTFDues;
    private javax.swing.JTextField SPTFGST;
    private javax.swing.JTextField SPTFGSTINNo;
    private javax.swing.JTextField SPTFINvoiceID;
    private javax.swing.JTextField SPTFMobileNo;
    private javax.swing.JTextField SPTFNetAmount;
    private javax.swing.JTextField SPTFPaidAmount;
    private javax.swing.JTextField SPTFPreDues;
    private javax.swing.JTextField SPTFQuantity;
    private javax.swing.JTextField SPTFSellingPrice;
    private javax.swing.JTextField SPTFTotalAmount;
    private javax.swing.JRadioButton SRBBackupStock;
    private javax.swing.JRadioButton SRBBoth;
    private javax.swing.JRadioButton SRBCurrentStock;
    private javax.swing.JTable SStockTable;
    private javax.swing.JTextField STFQuantity;
    private javax.swing.JPanel SellProduct;
    private javax.swing.JComboBox SellRCBCustomerNames;
    private javax.swing.JComboBox SellRCBCustomerType;
    private com.toedter.calendar.JDateChooser SellRDCFrom;
    private com.toedter.calendar.JDateChooser SellRDCTo;
    private javax.swing.JPanel SellReport;
    private javax.swing.JPanel StockIn;
    private javax.swing.JPanel StockandExpNotify;
    private javax.swing.JPanel SubF1;
    private javax.swing.JPanel SubF10;
    private javax.swing.JPanel SubF2;
    private javax.swing.JPanel SubF3;
    private javax.swing.JPanel SubF4;
    private javax.swing.JPanel SubF5;
    private javax.swing.JPanel SubF6;
    private javax.swing.JPanel SubF7;
    private javax.swing.JPanel SubF8;
    private javax.swing.JPanel SubF9;
    private javax.swing.JTextField TFAadharNo;
    private javax.swing.JTextField TFGSTINNo;
    private javax.swing.JTextField TFMobileNo;
    private javax.swing.JTextField TFPreDues;
    private com.toedter.calendar.JDateChooser TRDCFrom;
    private com.toedter.calendar.JDateChooser TRDCTo;
    private javax.swing.JPanel TransactionReport;
    private javax.swing.JComboBox VCCBCustomerName;
    private javax.swing.JComboBox VCCBSupplierName;
    private javax.swing.JPanel ViewCustomer;
    private javax.swing.JPanel ViewSupplier;
    private javax.swing.JPanel ViewSupplier34;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private diu.swe.habib.JPanelSlider.JPanelSlider jPanelSlider1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables

    
}
