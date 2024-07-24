/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

import Database.DatabaseConnection;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PRODIGY
 */
public class ProductMaster1 extends javax.swing.JFrame {

    /**
     * Creates new form ProductMaster1
     */
    String ProductID = null;
    DatabaseConnection db;
    DashBoard DB;
    ButtonGroup BG;
    Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
    Pattern pattern2 = Pattern.compile("[0-9]+");
    Pattern pattern3 = Pattern.compile("[A-Z0-9]+");
    public ProductMaster1(DashBoard DB) throws SQLException {
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
        
    }
    public ProductMaster1() {
        initComponents();
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

        jPanel1 = new javax.swing.JPanel();
        LProductBrand = new javax.swing.JLabel();
        CBProductBrand = new javax.swing.JComboBox<>();
        TFBarcode = new javax.swing.JTextField();
        TFDiscription = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        LSellingPrice = new javax.swing.JLabel();
        LProductName = new javax.swing.JLabel();
        CBProductName = new javax.swing.JComboBox<>();
        LUOM = new javax.swing.JLabel();
        CBUOM = new javax.swing.JComboBox<>();
        LProductCategory = new javax.swing.JLabel();
        CBProductCategory = new javax.swing.JComboBox<>();
        LSize = new javax.swing.JLabel();
        CBSize = new javax.swing.JComboBox<>();
        LBarcode = new javax.swing.JLabel();
        LDiscription = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        PMRBCurrentStock = new javax.swing.JRadioButton();
        PMRBBackupStock = new javax.swing.JRadioButton();
        PMRBBoth = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductMasterTable = new javax.swing.JTable();
        TFSellingPrice = new javax.swing.JTextField();
        LProductID = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(841, 663));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(33, 95, 133));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Master", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        LProductBrand.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LProductBrand.setForeground(new java.awt.Color(255, 255, 255));
        LProductBrand.setText("Product  Brand    ");

        CBProductBrand.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBProductBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBProductBrandActionPerformed(evt);
            }
        });

        TFBarcode.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N

        TFDiscription.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N

        jButton13.setText("ADD");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        LSellingPrice.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LSellingPrice.setForeground(new java.awt.Color(255, 255, 255));
        LSellingPrice.setText("Selling Price");

        LProductName.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LProductName.setForeground(new java.awt.Color(255, 255, 255));
        LProductName.setText("Product Name    ");

        CBProductName.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBProductNameActionPerformed(evt);
            }
        });

        LUOM.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LUOM.setForeground(new java.awt.Color(255, 255, 255));
        LUOM.setText("UOM");

        CBUOM.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBUOM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBUOMActionPerformed(evt);
            }
        });

        LProductCategory.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LProductCategory.setForeground(new java.awt.Color(255, 255, 255));
        LProductCategory.setText("Product Category ");

        CBProductCategory.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBProductCategory.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                CBProductCategoryFocusGained(evt);
            }
        });
        CBProductCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBProductCategoryActionPerformed(evt);
            }
        });
        CBProductCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CBProductCategoryKeyReleased(evt);
            }
        });

        LSize.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LSize.setForeground(new java.awt.Color(255, 255, 255));
        LSize.setText("Size");

        CBSize.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        CBSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBSizeActionPerformed(evt);
            }
        });

        LBarcode.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LBarcode.setForeground(new java.awt.Color(255, 255, 255));
        LBarcode.setText("Barcode              ");

        LDiscription.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        LDiscription.setForeground(new java.awt.Color(255, 255, 255));
        LDiscription.setText("Description           ");

        jButton14.setText("Update");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton7.setText("Delete");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Clear");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton8.setText("Referesh");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        PMRBCurrentStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PMRBCurrentStock.setForeground(new java.awt.Color(255, 255, 0));
        PMRBCurrentStock.setText("Current Stock");
        PMRBCurrentStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PMRBCurrentStockActionPerformed(evt);
            }
        });

        PMRBBackupStock.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PMRBBackupStock.setForeground(new java.awt.Color(51, 255, 255));
        PMRBBackupStock.setText("Backup Stock");
        PMRBBackupStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PMRBBackupStockActionPerformed(evt);
            }
        });

        PMRBBoth.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PMRBBoth.setForeground(new java.awt.Color(51, 255, 51));
        PMRBBoth.setText("Both");
        PMRBBoth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PMRBBothActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Product ID : ");

        jLabel2.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        ProductMasterTable.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        ProductMasterTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductMasterTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ProductMasterTable);

        TFSellingPrice.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N

        LProductID.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        LProductID.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LUOM, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBUOM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(LProductCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBProductCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LSize, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CBSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LProductBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CBProductBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(LSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TFSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(PMRBCurrentStock, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(PMRBBackupStock)
                                        .addGap(44, 44, 44)
                                        .addComponent(PMRBBoth, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(79, 79, 79)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(LDiscription, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TFDiscription, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TFBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 789, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(LProductCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CBProductCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(LSize, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CBSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(LProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CBProductName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(LUOM, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CBUOM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(LProductBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CBProductBrand, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TFSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TFDiscription, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LDiscription, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PMRBCurrentStock)
                    .addComponent(PMRBBackupStock)
                    .addComponent(PMRBBoth)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CBProductBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBProductBrandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBProductBrandActionPerformed

    private void CBProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBProductNameActionPerformed

    private void CBUOMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBUOMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBUOMActionPerformed

    private void CBProductCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBProductCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBProductCategoryActionPerformed

    private void CBSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBSizeActionPerformed

    private void CBProductCategoryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CBProductCategoryFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_CBProductCategoryFocusGained

    private void CBProductCategoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CBProductCategoryKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_CBProductCategoryKeyReleased

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
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(ProductMaster1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductMaster1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductMaster1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductMaster1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductMaster1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBProductBrand;
    private javax.swing.JComboBox<String> CBProductCategory;
    private javax.swing.JComboBox<String> CBProductName;
    private javax.swing.JComboBox<String> CBSize;
    private javax.swing.JComboBox<String> CBUOM;
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
    private javax.swing.JTextField TFBarcode;
    private javax.swing.JTextField TFDiscription;
    private javax.swing.JTextField TFSellingPrice;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
