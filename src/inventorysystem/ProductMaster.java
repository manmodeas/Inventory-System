/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

import Database.DatabaseConnection;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;

/**
 *
 * @author user
 */
public class ProductMaster extends javax.swing.JFrame {

    /**
     * Creates new form ProductMaster
     */
    String ProductID = null;
    DatabaseConnection db;
    DashBoard DB;
    ButtonGroup BG;
    Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
    Pattern pattern2 = Pattern.compile("[0-9]+");
    Pattern pattern3 = Pattern.compile("[A-Z0-9]+");
    public ProductMaster(DashBoard DB) throws SQLException {
        initComponents();
        this.DB = DB;
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("InventoryImage.png")));
        db = new DatabaseConnection(InventorySystem.con);
        db.ProductMT(ProductMasterTable);
        db.setProductCategory(CBProductCategory);
        db.setProductName(CBProductName);
        db.setProductBrand(CBProductBrand);
        db.setProductSizeandUOM(CBSize,CBUOM);
        BG = new ButtonGroup();
        BG.add(PMRBCurrentStock);
        BG.add(PMRBBoth);
        BG.add(PMRBBackupStock);
        PMRBBoth.setSelected(true);
        
//        DB.ProductMasterCount = 1;
//        KeyEvent();
        
    }

    private ProductMaster() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    void KeyEvent()
    {
        CBProductCategory.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent event) {     
               int keycode = event.getKeyCode();
               int KeyE = event.getKeyChar();
               
                String Category = CBProductCategory.getEditor().getItem().toString();
                boolean valid = (Category != null) && pattern.matcher(Category).matches();
                if(!valid)
                {
                    try {
                        Category = Category.substring(0, Category.length() - 1);
                    } catch (Exception e) {
                    }
                    CBProductCategory.getEditor().setItem(Category);
                }
//               if(KeyE == KeyEvent.VK_SHIFT)
//                {
//                    if(keycode == 9) { 
//
//                        //shift was down when tab was pressed
//                        CBProductName.transferFocus();
//                    }
//                    
//                }else{
//                   if(keycode== 9) {
//                       CBProductName.setFocusable(true);
//                    } 
//               }
            }
        });
        CBProductName.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent event) {     
               int keycode = event.getKeyCode();
               String Name = CBProductName.getEditor().getItem().toString();
                boolean valid = (Name != null) && pattern.matcher(Name).matches();
                if(!valid)
                {
                    try {
                        Name = Name.substring(0, Name.length() - 1);
                    } catch (Exception e) {
                    }
                    CBProductName.getEditor().setItem(Name);
                }
//                if(keycode == 9) { 
//                    //shift was down when tab was pressed
//                    CBProductName.requestFocusInWindow();
//                }
            }
        });
        CBProductBrand.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
        @Override
         public void keyReleased(KeyEvent event) {     
               int keycode = event.getKeyCode();
               String Brand = CBProductBrand.getEditor().getItem().toString();
                boolean valid = (Brand != null) && pattern.matcher(Brand).matches();
                if(!valid)
                {
                    try {
                        Brand = Brand.substring(0, Brand.length() - 1);
                    } catch (Exception e) {
                    }
                    CBProductBrand.getEditor().setItem(Brand);
                }
//                if(keycode == 9) { 
//                    //shift was down when tab was pressed
//                    CBSize.requestFocus();
//                }
            }
        });
        
        CBSize.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent event) {     
               int keycode = event.getKeyCode();
               String Size = CBSize.getEditor().getItem().toString();
                boolean valid = (Size != null) && pattern2.matcher(Size).matches();
                if(!valid)
                {
                    try {
                        Size = Size.substring(0, Size.length() - 1);
                    } catch (Exception e) {
                    }
                    CBSize.getEditor().setItem(Size);
                }
//                if(keycode == 9) { 
//                    //shift was down when tab was pressed
//                    CBUOM.requestFocus();
//                }
            }
        });
        CBUOM.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent event) {     
               int keycode = event.getKeyCode();
               String UOM = CBUOM.getEditor().getItem().toString();
                boolean valid = (UOM != null) && pattern.matcher(UOM).matches();
                if(!valid)
                {
                    try {
                        UOM = UOM.substring(0, UOM.length() - 1);
                    } catch (Exception e) {
                    }
                    CBUOM.getEditor().setItem(UOM);
                }
//                if(keycode == 9) { 
//                    //shift was down when tab was pressed
//                    TFSellingPrice.requestFocus();
//                }
            }
        });
        TFSellingPrice.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent event) {     
               int keycode = event.getKeyCode();
               String SellingPrice = TFSellingPrice.getText();
                boolean valid = (SellingPrice != null) && pattern2.matcher(SellingPrice).matches();
                if(!valid)
                {
                    try {
                        SellingPrice = SellingPrice.substring(0, SellingPrice.length() - 1);
                    } catch (Exception e) {
                    }
                    TFSellingPrice.setText(SellingPrice);
                }
//                if(keycode == 9) { 
//                    //shift was down when tab was pressed
//                    TFBarcode.requestFocus();
//                }
            }
        });
        TFBarcode.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent event) {     
               int keycode = event.getKeyCode();
               String Barcode = TFBarcode.getText();
                boolean valid = (Barcode != null) && pattern2.matcher(Barcode).matches();
                if(!valid)
                {
                    try {
                        Barcode = Barcode.substring(0, Barcode.length() - 1);
                    } catch (Exception e) {
                    }
                    TFBarcode.setText(Barcode);
                }
//                if(keycode == 9) { 
//                    //shift was down when tab was pressed
//                    TFDiscription.requestFocus();
//                }
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ProductMasters = new javax.swing.JPanel();
        LProductCategory = new javax.swing.JLabel();
        LProductName = new javax.swing.JLabel();
        LSize = new javax.swing.JLabel();
        LUOM = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        CBProductCategory = new javax.swing.JComboBox();
        CBProductName = new javax.swing.JComboBox();
        CBSize = new javax.swing.JComboBox();
        CBUOM = new javax.swing.JComboBox();
        LSellingPrice = new javax.swing.JLabel();
        TFSellingPrice = new javax.swing.JTextField();
        LDiscription = new javax.swing.JLabel();
        TFDiscription = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ProductMasterTable = new javax.swing.JTable();
        LProductBrand = new javax.swing.JLabel();
        CBProductBrand = new javax.swing.JComboBox();
        LBarcode = new javax.swing.JLabel();
        TFBarcode = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        LProductID = new javax.swing.JLabel();
        PMRBBoth = new javax.swing.JRadioButton();
        PMRBCurrentStock = new javax.swing.JRadioButton();
        PMRBBackupStock = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        ProductMasters.setBackground(new java.awt.Color(33, 95, 133));
        ProductMasters.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Master", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Batang", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        ProductMasters.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ProductMasters.setLayout(null);

        LProductCategory.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LProductCategory.setForeground(new java.awt.Color(255, 255, 255));
        LProductCategory.setText("Product Category :");
        ProductMasters.add(LProductCategory);
        LProductCategory.setBounds(20, 30, 150, 30);

        LProductName.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LProductName.setForeground(new java.awt.Color(255, 255, 255));
        LProductName.setText("Product Name      :");
        ProductMasters.add(LProductName);
        LProductName.setBounds(20, 70, 149, 30);

        LSize.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LSize.setForeground(new java.awt.Color(255, 255, 255));
        LSize.setText("Size                          :");
        ProductMasters.add(LSize);
        LSize.setBounds(420, 30, 150, 30);

        LUOM.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LUOM.setForeground(new java.awt.Color(255, 255, 255));
        LUOM.setText("UOM                       :");
        ProductMasters.add(LUOM);
        LUOM.setBounds(420, 70, 160, 30);

        jButton7.setBackground(new java.awt.Color(231, 76, 60));
        jButton7.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton7.setText("Delete");
        jButton7.setRequestFocusEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        ProductMasters.add(jButton7);
        jButton7.setBounds(360, 240, 90, 30);

        jButton8.setBackground(new java.awt.Color(231, 76, 60));
        jButton8.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton8.setText("Refresh");
        jButton8.setRequestFocusEnabled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        ProductMasters.add(jButton8);
        jButton8.setBounds(600, 240, 90, 30);

        CBProductCategory.setEditable(true);
        CBProductCategory.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBProductCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        CBProductCategory.setFocusTraversalPolicy(CBProductName.getFocusTraversalPolicy());
        CBProductCategory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                CBProductCategoryFocusGained(evt);
            }
        });
        CBProductCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                KeyReleased(evt);
            }
        });
        ProductMasters.add(CBProductCategory);
        CBProductCategory.setBounds(190, 30, 220, 30);

        CBProductName.setEditable(true);
        CBProductName.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBProductName.setNextFocusableComponent(CBProductBrand);
        CBProductName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CBProductNameKeyReleased(evt);
            }
        });
        ProductMasters.add(CBProductName);
        CBProductName.setBounds(190, 70, 220, 30);

        CBSize.setEditable(true);
        CBSize.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBSize.setNextFocusableComponent(CBUOM);
        ProductMasters.add(CBSize);
        CBSize.setBounds(590, 30, 220, 30);

        CBUOM.setEditable(true);
        CBUOM.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBUOM.setNextFocusableComponent(TFSellingPrice);
        ProductMasters.add(CBUOM);
        CBUOM.setBounds(590, 70, 220, 30);

        LSellingPrice.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LSellingPrice.setForeground(new java.awt.Color(255, 255, 255));
        LSellingPrice.setText("Selling Price          :");
        ProductMasters.add(LSellingPrice);
        LSellingPrice.setBounds(420, 110, 160, 30);

        TFSellingPrice.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        TFSellingPrice.setNextFocusableComponent(TFBarcode);
        ProductMasters.add(TFSellingPrice);
        TFSellingPrice.setBounds(590, 110, 140, 30);

        LDiscription.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LDiscription.setForeground(new java.awt.Color(255, 255, 255));
        LDiscription.setText("Description           :");
        ProductMasters.add(LDiscription);
        LDiscription.setBounds(20, 190, 150, 30);

        TFDiscription.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        ProductMasters.add(TFDiscription);
        TFDiscription.setBounds(190, 190, 540, 30);

        jButton13.setBackground(new java.awt.Color(231, 76, 60));
        jButton13.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton13.setText("Add");
        jButton13.setRequestFocusEnabled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        ProductMasters.add(jButton13);
        jButton13.setBounds(130, 240, 90, 30);

        jButton14.setBackground(new java.awt.Color(231, 76, 60));
        jButton14.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton14.setText("Update");
        jButton14.setRequestFocusEnabled(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        ProductMasters.add(jButton14);
        jButton14.setBounds(250, 240, 80, 30);

        ProductMasterTable.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        ProductMasterTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        ProductMasterTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductMasterTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(ProductMasterTable);

        ProductMasters.add(jScrollPane2);
        jScrollPane2.setBounds(20, 300, 800, 350);

        LProductBrand.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LProductBrand.setForeground(new java.awt.Color(255, 255, 255));
        LProductBrand.setText("Product  Brand      :");
        ProductMasters.add(LProductBrand);
        LProductBrand.setBounds(20, 110, 156, 30);

        CBProductBrand.setEditable(true);
        CBProductBrand.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBProductBrand.setNextFocusableComponent(CBSize);
        ProductMasters.add(CBProductBrand);
        CBProductBrand.setBounds(190, 110, 220, 30);

        LBarcode.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LBarcode.setForeground(new java.awt.Color(255, 255, 255));
        LBarcode.setText("Barcode                  :");
        ProductMasters.add(LBarcode);
        LBarcode.setBounds(20, 150, 150, 30);

        TFBarcode.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        TFBarcode.setNextFocusableComponent(LDiscription);
        ProductMasters.add(TFBarcode);
        TFBarcode.setBounds(190, 150, 330, 30);

        jButton9.setBackground(new java.awt.Color(231, 76, 60));
        jButton9.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton9.setText("Clear");
        jButton9.setRequestFocusEnabled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        ProductMasters.add(jButton9);
        jButton9.setBounds(480, 240, 90, 30);

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ProductID :");
        ProductMasters.add(jLabel1);
        jLabel1.setBounds(640, 280, 80, 20);

        LProductID.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        LProductID.setForeground(new java.awt.Color(255, 255, 51));
        ProductMasters.add(LProductID);
        LProductID.setBounds(720, 280, 100, 20);

        PMRBBoth.setBackground(new java.awt.Color(33, 95, 133));
        PMRBBoth.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PMRBBoth.setForeground(new java.awt.Color(51, 255, 51));
        PMRBBoth.setText("Both");
        PMRBBoth.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PMRBBoth.setRequestFocusEnabled(false);
        PMRBBoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PMRBBothActionPerformed(evt);
            }
        });
        ProductMasters.add(PMRBBoth);
        PMRBBoth.setBounds(280, 280, 60, 20);

        PMRBCurrentStock.setBackground(new java.awt.Color(255, 255, 255));
        PMRBCurrentStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PMRBCurrentStock.setForeground(new java.awt.Color(255, 255, 51));
        PMRBCurrentStock.setText("Current Stock");
        PMRBCurrentStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PMRBCurrentStock.setRequestFocusEnabled(false);
        PMRBCurrentStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PMRBCurrentStockActionPerformed(evt);
            }
        });
        ProductMasters.add(PMRBCurrentStock);
        PMRBCurrentStock.setBounds(20, 280, 120, 20);

        PMRBBackupStock.setBackground(new java.awt.Color(33, 95, 133));
        PMRBBackupStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PMRBBackupStock.setForeground(new java.awt.Color(51, 255, 255));
        PMRBBackupStock.setText("Backup Stock");
        PMRBBackupStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PMRBBackupStock.setRequestFocusEnabled(false);
        PMRBBackupStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PMRBBackupStockActionPerformed(evt);
            }
        });
        ProductMasters.add(PMRBBackupStock);
        PMRBBackupStock.setBounds(150, 280, 120, 20);

        getContentPane().add(ProductMasters);
        ProductMasters.setBounds(0, 0, 840, 660);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            // TODO add your handling code here:
            db.ProductD(ProductID);
            try {
                // TODO add your handling code here:
                db.ProductMT(ProductMasterTable);
                db.setProductCategory(CBProductCategory);
                db.setProductName(CBProductName);
                db.setProductBrand(CBProductBrand);
                db.setProductSizeandUOM(CBSize,CBUOM);
            } catch (SQLException ex) {
                Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        String[] ProductM = new String[8];
        ProductM[0] = CBProductCategory.getEditor().getItem().toString();
        ProductM[1] = CBProductName.getEditor().getItem().toString();
        ProductM[2] = CBProductBrand.getEditor().getItem().toString();
        int temp = 0;
        try {
            temp = Integer.parseInt(CBSize.getEditor().getItem().toString());
            ProductM[3] = String.valueOf(temp);
        } catch (Exception e) {
            ProductM[3] = "";
        }
        ProductM[4] = CBUOM.getEditor().getItem().toString();;
        try {
            temp = Integer.parseInt(TFSellingPrice.getText());
            ProductM[5] = String.valueOf(temp);
        } catch (Exception e) {
            ProductM[5] = "";
        }
        try {
            ProductM[6] = TFDiscription.getText();
        } catch (NullPointerException e) {
           ProductM[6] = ""; 
        }
        long barcode=0; 
        try {
            barcode = Long.parseLong(TFBarcode.getText());
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
            LProductCategory.setForeground(Color.red);
        }else if(ProductM[1] == null || ProductM[1].equals(""))
        {
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.red);
        }else if(ProductM[2] == null || ProductM[2].equals(""))
        {
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.WHITE);
            LProductBrand.setForeground(Color.red);
        }else if(ProductM[3] == null || ProductM[3].equals(""))
        {
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.WHITE);
            LProductBrand.setForeground(Color.WHITE);
            LSize.setForeground(Color.red);
        }else if(ProductM[4] == null || ProductM[4].equals(""))
        {
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.WHITE);
            LProductBrand.setForeground(Color.WHITE);
            LSize.setForeground(Color.WHITE);
            LUOM.setForeground(Color.red);
        }else if(ProductM[5] == null || ProductM[5].equals(""))
        {
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.WHITE);
            LProductBrand.setForeground(Color.WHITE);
            LSize.setForeground(Color.WHITE);
            LUOM.setForeground(Color.WHITE);
            LSellingPrice.setForeground(Color.red);
            
        }else if(ProductM[7].length() != 13)
        {
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.WHITE);
            LProductBrand.setForeground(Color.WHITE);
            LSize.setForeground(Color.WHITE);
            LUOM.setForeground(Color.WHITE);
            LSellingPrice.setForeground(Color.WHITE);
            LBarcode.setForeground(Color.red);
            
        }else{
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.WHITE);
            LProductBrand.setForeground(Color.WHITE);
            LSize.setForeground(Color.WHITE);
            LUOM.setForeground(Color.WHITE);
            LSellingPrice.setForeground(Color.WHITE);
            LBarcode.setForeground(Color.WHITE);
            
            try {
                db.ProductMaster(ProductM);
                try {
                    db.ProductMT(ProductMasterTable); 
                    db.setProductCategory(CBProductCategory);
                    db.setProductName(CBProductName);
                    db.setProductBrand(CBProductBrand);
                    db.setProductSizeandUOM(CBSize,CBUOM);
                    TFBarcode.setText("");
                    TFDiscription.setText("");
                    TFSellingPrice.setText("");
                } catch (SQLException ex) {
                    Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        ((JTextField)CBProductCategory.getEditor().getEditorComponent()).setText("");
        ((JTextField)CBProductName.getEditor().getEditorComponent()).setText("");
        ((JTextField)CBProductBrand.getEditor().getEditorComponent()).setText("");
        ((JTextField)CBSize.getEditor().getEditorComponent()).setText("");
        ((JTextField)CBUOM.getEditor().getEditorComponent()).setText("");
        TFBarcode.setText("");
        TFDiscription.setText("");
        TFSellingPrice.setText("");
        try {
            // TODO add your handling code here:
            db.ProductMT(ProductMasterTable);
            db.setProductCategory(CBProductCategory);
            db.setProductName(CBProductName);
            db.setProductBrand(CBProductBrand);
            db.setProductSizeandUOM(CBSize,CBUOM);
        } catch (SQLException ex) {
            Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void ProductMasterTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductMasterTableMouseClicked
        // TODO add your handling code here:
        int i = ProductMasterTable.getSelectedRow();
        
        DefaultTableModel model = (DefaultTableModel)ProductMasterTable.getModel();
        try {
            ProductID = model.getValueAt(i, 0).toString();
        } catch (Exception e) {
            ProductID = "";
        }
        LProductID.setText(ProductID);
        String[] ProductD = new String[8];
        
        try {
            ProductD = db.GetProductD(ProductID);
        } catch (SQLException ex) {
            Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((JTextField)CBProductCategory.getEditor().getEditorComponent()).setText(ProductD[0]);
        ((JTextField)CBProductName.getEditor().getEditorComponent()).setText(ProductD[1]);
        ((JTextField)CBProductBrand.getEditor().getEditorComponent()).setText(ProductD[2]);
        ((JTextField)CBSize.getEditor().getEditorComponent()).setText(ProductD[3]);
        ((JTextField)CBUOM.getEditor().getEditorComponent()).setText(ProductD[4]);
        TFSellingPrice.setText(ProductD[5]);
        TFBarcode.setText(ProductD[6]);
        TFDiscription.setText(ProductD[7]);
        
    }//GEN-LAST:event_ProductMasterTableMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            // TODO add your handling code here:
            db.ProductMT(ProductMasterTable);
            db.setProductCategory(CBProductCategory);
            db.setProductName(CBProductName);
            db.setProductBrand(CBProductBrand);
            db.setProductSizeandUOM(CBSize,CBUOM);
            TFBarcode.setText("");
            TFDiscription.setText("");
            TFSellingPrice.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        String[] ProductMU = new String[9];
        ProductMU[0] = CBProductCategory.getEditor().getItem().toString();
        ProductMU[1] = CBProductName.getEditor().getItem().toString();
        ProductMU[2] = CBProductBrand.getEditor().getItem().toString();
        int temp = 0;
        try {
            temp = Integer.parseInt(CBSize.getEditor().getItem().toString());
            ProductMU[3] = String.valueOf(temp);
        } catch (Exception e) {
            ProductMU[3] = "";
        }
        ProductMU[4] = CBUOM.getEditor().getItem().toString();;
        try {
            temp = Integer.parseInt(TFSellingPrice.getText());
            ProductMU[5] = String.valueOf(temp);
        } catch (Exception e) {
            ProductMU[5] = "";
        }
        ProductMU[6] = TFDiscription.getText();
        long barcode=0; 
        try {
            barcode = Long.parseLong(TFBarcode.getText());
            ProductMU[7] = String.valueOf(barcode);
        } catch (Exception e) {
            ProductMU[7] = "";
        }
        ProductMU[8] = ProductID;
        
        for(int i=0;i<ProductMU.length;i++)
        {
            ProductMU[i].trim();
        }
        
        if(ProductMU[0] == null || ProductMU[0].equals(""))
        {
            LProductCategory.setForeground(Color.red);
        }else if(ProductMU[1] == null || ProductMU[1].equals(""))
        {
            LProductCategory.setForeground(Color.WHITE);
            LProductName.setForeground(Color.red);
        }else if(ProductMU[2] == null || ProductMU[2].equals(""))
        {
            LProductName.setForeground(Color.WHITE);
            LProductBrand.setForeground(Color.red);
        }else if(ProductMU[3] == null || ProductMU[3].equals(""))
        {
            LProductBrand.setForeground(Color.WHITE);
            LSize.setForeground(Color.red);
        }else if(ProductMU[4] == null || ProductMU[4].equals(""))
        {
            LSize.setForeground(Color.WHITE);
            LUOM.setForeground(Color.red);
        }else if(ProductMU[5] == null || ProductMU[5].equals(""))
        {
            LUOM.setForeground(Color.WHITE);
            LSellingPrice.setForeground(Color.red);
            
        }else if(ProductMU[7].length() != 13)
        {
            LSellingPrice.setForeground(Color.WHITE);
            LBarcode.setForeground(Color.red);
            
        }else{
            LBarcode.setForeground(Color.WHITE);
            
            try {
                db.ProductMasterUpdate(ProductMU);
                try {
                    db.ProductMT(ProductMasterTable);
                    db.setProductCategory(CBProductCategory);
                    db.setProductName(CBProductName);
                    db.setProductBrand(CBProductBrand);
                    db.setProductSizeandUOM(CBSize,CBUOM);
                } catch (SQLException ex) {
                    Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }//GEN-LAST:event_jButton14ActionPerformed
    
    private void KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_KeyReleased

    private void CBProductNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CBProductNameKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_CBProductNameKeyReleased

    private void CBProductCategoryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CBProductCategoryFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_CBProductCategoryFocusGained

    private void PMRBBothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PMRBBothActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            db.ProductMT(ProductMasterTable);
        } catch (SQLException ex) {
            Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PMRBBothActionPerformed

    private void PMRBCurrentStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PMRBCurrentStockActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            db.ProductMTCS(ProductMasterTable);
        } catch (SQLException ex) {
            Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PMRBCurrentStockActionPerformed

    private void PMRBBackupStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PMRBBackupStockActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            db.ProductMTBS(ProductMasterTable);
        } catch (SQLException ex) {
            Logger.getLogger(ProductMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PMRBBackupStockActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        DB.ProductMasterCount = 0;
        DB.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(ProductMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductMaster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CBProductBrand;
    public javax.swing.JComboBox CBProductCategory;
    private javax.swing.JComboBox CBProductName;
    private javax.swing.JComboBox CBSize;
    private javax.swing.JComboBox CBUOM;
    private javax.swing.JLabel LBarcode;
    private javax.swing.JLabel LDiscription;
    private javax.swing.JLabel LProductBrand;
    private javax.swing.JLabel LProductCategory;
    private javax.swing.JLabel LProductID;
    private javax.swing.JLabel LProductName;
    private javax.swing.JLabel LSellingPrice;
    private javax.swing.JLabel LSize;
    private javax.swing.JLabel LUOM;
    private javax.swing.JRadioButton PMRBBackupStock;
    private javax.swing.JRadioButton PMRBBoth;
    private javax.swing.JRadioButton PMRBCurrentStock;
    private javax.swing.JTable ProductMasterTable;
    private javax.swing.JPanel ProductMasters;
    private javax.swing.JTextField TFBarcode;
    private javax.swing.JTextField TFDiscription;
    private javax.swing.JTextField TFSellingPrice;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
