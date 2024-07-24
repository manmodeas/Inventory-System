/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

import Database.DatabaseConnection;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author admin
 */
public class EditPurchaseInvoice extends javax.swing.JFrame {

    /**
     * Creates new form EditPurchaseInvoice
     */
    
    DatabaseConnection db;
    DashBoard DB;
    
    JTable PPTInvoiceD;
    JTable SPTInvoiceD;
    
    DefaultTableModel PPInvoicemodel;
    DefaultTableModel SPInvoicemodel;
    
    int Row;
    int datacount = 0;
    String[][] data = new String[3][20];
    
    Vector<String> v1 = new Vector<String>();
    Vector<String> v2= new Vector<String>();
    Vector<String> v3= new Vector<String>();
    
    Vector<String> RemovedProductID= new Vector<String>();
    Vector<String> RemovedProductOtherD= new Vector<String>();
    
    String InvoiceID;
    String SupplierID;
    String CustomerID;
    String Who;
    
    double PPaidAmount;
    public EditPurchaseInvoice(String InvoiceID,DashBoard DB,String Who)throws SQLException {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("InventoryImage.png")));
        db = new DatabaseConnection(InventorySystem.con);
        this.DB = DB;
        this.InvoiceID = InvoiceID;
        PPInvoiceTable();
        
        
        this.Who = Who;
        if (Who.equals("PurchaseInvoice")) {
            try {
                v1 = db.EditInvoicePurchaseD(InvoiceID);
            } catch (SQLException sQLException) {
            }
            try {
                v2 = db.EditPInvoiceProductD(InvoiceID);
            } catch (SQLException sQLException) {
            }
            
            for (int i = 0, j = 0; i < (v2.size()) / 4; i++, j += 4) {
                v3 = db.EditInvoiceProductDetails(v2.get(j));
                ImageIcon img = new ImageIcon("Delete.png");
                Object[] obj = {v2.get(j), v3.get(0), v3.get(1), v3.get(2), v3.get(3), v3.get(4), v2.get(2 + j), v2.get(1 + j), v2.get(3 + j), img};
                PPInvoicemodel.addRow(obj);
                data[0][i] = v2.get(2 + j).toString();
                data[1][i] = v2.get(1 + j).toString();
                data[2][i] = v2.get(3 + j).toString();
                datacount++;
                v3.clear();
            }
            
            PPTFNetAmount.setText(v1.get(6));
            PPTFTotalAmount.setText(v1.get(2));
            
            PPTFDiscount.setText(v1.get(3).substring(0, v1.get(3).toString().length() - 3));
            PPTFPaidAmount.setText(v1.get(4));
            PPaidAmount = Double.valueOf(v1.get(4));
            PPTFDues.setText(v1.get(5));
            PPLGrossAmount.setText(v1.get(8));
            if (v1.get(7).equals("Cash")) {
                PPCBPaymentMode.setSelectedIndex(0);
            } else {
                PPCBPaymentMode.setSelectedIndex(1);
            }
            SupplierID = v1.get(0);
        }else if(Who.equals("SellInvoice")){
//            System.out.println(InvoiceID);
            try {
                v1 = db.EditInvoiceSellD(InvoiceID);
            } catch (SQLException sQLException) {
            }
            try {
                v2 = db.EditSInvoiceProductD(InvoiceID);
            } catch (SQLException sQLException) {
            }
            for (int i = 0, j = 0; i < (v2.size()) / 4; i++, j += 4) {
                v3 = db.EditInvoiceProductDetails(v2.get(j));
                ImageIcon img = new ImageIcon("Delete.png");
                Object[] obj = {v2.get(j), v3.get(0), v3.get(1), v3.get(2), v3.get(3), v3.get(4), v2.get(2 + j), v2.get(1 + j), v2.get(3 + j), img};
                PPInvoicemodel.addRow(obj);
                data[0][i] = v2.get(2 + j).toString();
                data[1][i] = v2.get(1 + j).toString();
                data[2][i] = v2.get(3 + j).toString();
                datacount++;
                v3.clear();
            }
            
            PPTFNetAmount.setText(v1.get(6));
            PPTFTotalAmount.setText(v1.get(2));
            
            PPTFDiscount.setText(v1.get(3).substring(0, v1.get(3).toString().length() - 3));
            PPTFPaidAmount.setText(v1.get(4));
            PPaidAmount = Double.valueOf(v1.get(4));
            PPTFDues.setText(v1.get(5));
            PPLGrossAmount.setText(v1.get(8));
            if (v1.get(7).equals("Cash")) {
                PPCBPaymentMode.setSelectedIndex(0);
            } else {
                PPCBPaymentMode.setSelectedIndex(1);
            }
            CustomerID = v1.get(0);
        }
        
    }
    
    public EditPurchaseInvoice()
    {
        
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
        scrollPane.setBounds(10, 10, 1060, 390);
        EditInvoice.add(scrollPane);
        InvoiceTableMouseEvent();
    }
    
    void InvoiceTableMouseEvent()
    {
            PPTInvoiceD.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = PPTInvoiceD.rowAtPoint(evt.getPoint());
                int col = PPTInvoiceD.columnAtPoint(evt.getPoint());
                int i = 1;
                i = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this Product");
                if(i == 0)
                {
                    if (col == 9) {
                        RemovedProductID.add((String)PPInvoicemodel.getValueAt(row, 0)); 
                        RemovedProductOtherD.add((String)PPInvoicemodel.getValueAt(row, 6));
                        PPInvoicemodel.removeRow(row);
                        ReCalculatePP(row);
                    }else{
                        Row = row;
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
            PPLGrossAmount.setText(String.valueOf(GrossAmount));
        }else{
            PPTFNetAmount.setText(String.valueOf("00.0"));
            PPTFTotalAmount.setText(String.valueOf("00.0"));
            PPLGrossAmount.setText(String.valueOf("00.0"));
            PPTFDues.setText(String.valueOf("00.0"));
        }
        
        double PaidAmount = 0;
        double PreDues = 0;
        
        try {
            PreDues = Double.valueOf(v1.get(9));
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
        
        PPTFDues.setText(String.valueOf(Dues));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        EditInvoice = new javax.swing.JPanel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        EditInvoice.setBackground(new java.awt.Color(33, 95, 133));
        EditInvoice.setLayout(null);

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
        jLabel63.setBounds(590, 10, 100, 20);

        PPCBPaymentMode.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        PPCBPaymentMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Cheque" }));
        PPCBPaymentMode.setSelectedIndex(-1);
        PPCBPaymentMode.setToolTipText("");
        jPanel3.add(PPCBPaymentMode);
        PPCBPaymentMode.setBounds(590, 30, 130, 30);

        PPLPaidAmount.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLPaidAmount.setForeground(new java.awt.Color(255, 255, 255));
        PPLPaidAmount.setText("Piad Amount");
        jPanel3.add(PPLPaidAmount);
        PPLPaidAmount.setBounds(760, 10, 120, 20);

        PPTFPaidAmount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PPTFPaidAmount.setText("0");
        PPTFPaidAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PPTFPaidAmountKeyReleased(evt);
            }
        });
        jPanel3.add(PPTFPaidAmount);
        PPTFPaidAmount.setBounds(760, 30, 110, 30);

        PPLDues.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        PPLDues.setForeground(new java.awt.Color(255, 255, 255));
        PPLDues.setText("Dues");
        jPanel3.add(PPLDues);
        PPLDues.setBounds(910, 10, 31, 17);

        PPTFDues.setEditable(false);
        PPTFDues.setBackground(new java.awt.Color(255, 255, 255));
        PPTFDues.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        PPTFDues.setText("00.0");
        jPanel3.add(PPTFDues);
        PPTFDues.setBounds(910, 30, 110, 30);

        jButton21.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton21.setText("Update");
        jButton21.setRequestFocusEnabled(false);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton21);
        jButton21.setBounds(720, 80, 90, 30);

        jButton22.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jButton22.setText("Recipt");
        jButton22.setRequestFocusEnabled(false);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton22);
        jButton22.setBounds(590, 80, 100, 30);

        PPLGrossAmount.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        PPLGrossAmount.setForeground(new java.awt.Color(255, 102, 102));
        PPLGrossAmount.setText("00.0");
        PPLGrossAmount.setToolTipText("");
        jPanel3.add(PPLGrossAmount);
        PPLGrossAmount.setBounds(180, 80, 120, 30);

        EditInvoice.add(jPanel3);
        jPanel3.setBounds(10, 410, 1060, 120);

        getContentPane().add(EditInvoice);
        EditInvoice.setBounds(0, 0, 1080, 540);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

        PPLGrossAmount.setText(String.valueOf(GrossAmount));

        double PaidAmount = 0;
        double PreDues = 0;

        try {
            PreDues = Double.valueOf(v1.get(9));
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

        PPTFDues.setText(String.valueOf(Dues));
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
            PreDues = Double.valueOf(v1.get(9));
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(PPLGrossAmount.getText());

        Dues = (GrossAmount+PreDues) - PaidAmount;

        PPTFDues.setText(String.valueOf(Dues));
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
            PreDues = Double.valueOf(v1.get(9));
        } catch (NumberFormatException numberFormatException) {
        }
        GrossAmount = Double.parseDouble(PPLGrossAmount.getText());

        Dues = (GrossAmount+PreDues) - paidAmount;

        int x = 1;
        String[] PurchaseD = new String[10];
        x = JOptionPane.showConfirmDialog(null, "Are you sur you want to update Invoice "+this.InvoiceID+"");
        if (Who.equals("PurchaseInvoice")) {
            String[] otherD = new String[2];
            if (x == 0) {
                PurchaseD[0] = PPTFNetAmount.getText();
                PurchaseD[1] = PPTFTotalAmount.getText();
                int Discount = Integer.parseInt(PPTFDiscount.getText());
                PurchaseD[2] = PPTFDiscount.getText();
                double PaidAmount = Double.valueOf(PPTFPaidAmount.getText());
                PurchaseD[3] = PPTFPaidAmount.getText();
                PurchaseD[4] = PPCBPaymentMode.getSelectedItem().toString();
                PurchaseD[5] = PPTFDues.getText();
                PurchaseD[6] = PPLGrossAmount.getText();
                PurchaseD[7] = this.SupplierID;
                PurchaseD[8] = InvoiceID;
                PurchaseD[9] = v1.get(9);
                
                otherD[0] = InvoiceID;
                otherD[1] = SupplierID;
                double LPaidAmount = Double.valueOf(PPTFPaidAmount.getText());
                try {
                    db.PurchaseInvoiceUpdate(RemovedProductOtherD, RemovedProductID, otherD, Dues, PurchaseD);
                    String[] TransactionD = new String[4];
                    TransactionD[0] = db.getSupplierName(SupplierID);
                    TransactionD[1] = "Supplier";
                    TransactionD[2] = "Taken";
                    TransactionD[3] = String.valueOf(PPaidAmount - LPaidAmount);
                    db.TransactionTable(TransactionD);
                } catch (SQLException ex) {
                    Logger.getLogger(EditPurchaseInvoice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            String[] otherD = new String[2];
            if (x == 0) {
                PurchaseD[0] = PPTFNetAmount.getText();
                PurchaseD[1] = PPTFTotalAmount.getText();
                int Discount = Integer.parseInt(PPTFDiscount.getText());
                PurchaseD[2] = PPTFDiscount.getText();
                double PaidAmount = Double.valueOf(PPTFPaidAmount.getText());
                PurchaseD[3] = PPTFPaidAmount.getText();
                PurchaseD[4] = PPCBPaymentMode.getSelectedItem().toString();
                PurchaseD[5] = PPTFDues.getText();
                PurchaseD[6] = PPLGrossAmount.getText();
                PurchaseD[7] = this.SupplierID;
                PurchaseD[8] = InvoiceID;
                PurchaseD[9] = v1.get(9);
                
                otherD[0] = InvoiceID;
                otherD[1] = CustomerID;
                double LPaidAmount = Double.valueOf(PPTFPaidAmount.getText());
                try {
                    String[] TransactionD = new String[4];
                    String CustomerName = db.getCustomerName(CustomerID);
                    if (CustomerName.equals("") || CustomerName == null) {
                        TransactionD[0] = "Retailer";
                    } else {
                        TransactionD[0] = CustomerName;
                    }
                    TransactionD[1] = "Customer";
                    TransactionD[2] = "Given";
                    TransactionD[3] = String.valueOf(PPaidAmount - LPaidAmount);
                    db.TransactionTable(TransactionD);
                    db.SellInvoiceUpdate(RemovedProductOtherD, RemovedProductID, otherD, Dues, PurchaseD);
                } catch (SQLException ex) {
                    Logger.getLogger(EditPurchaseInvoice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton22ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        DB.EditPurchaseInvoicecount = 0;
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
            java.util.logging.Logger.getLogger(EditPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditPurchaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditPurchaseInvoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EditInvoice;
    private javax.swing.JComboBox PPCBPaymentMode;
    private javax.swing.JLabel PPLDiscount;
    private javax.swing.JLabel PPLDues;
    private javax.swing.JLabel PPLGrossAmount;
    private javax.swing.JLabel PPLPaidAmount;
    private javax.swing.JTextField PPTFDiscount;
    private javax.swing.JTextField PPTFDues;
    private javax.swing.JTextField PPTFNetAmount;
    private javax.swing.JTextField PPTFPaidAmount;
    private javax.swing.JTextField PPTFTotalAmount;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
