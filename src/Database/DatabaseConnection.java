/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import inventorysystem.InventorySystem;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author user
 */
//call
public class DatabaseConnection {
    
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs = null;
    ResultSet rs2 = null;
    Statement stmt = null;
    InventorySystem IS; 
    CallableStatement cstmt;
    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    Date Dt = new Date();
    public DatabaseConnection(Connection con) throws SQLException {
        this.con = con;
        stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //System.out.println("Constructor ");
    }
    public void Supplier(String[] SupplierD) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into Supplier(SupplierName,MobileNo,EmailId,Address,GSTINNo,AadharNo,SupplierID) values(?,?,?,?,?,?,?)");
        
        
        rs = stmt.executeQuery("select count(SupplierName) from Supplier");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        String SupplierID = "ISSUP"+String.valueOf(count);
        pst.setString(1,SupplierD[0]);
        pst.setString(2,SupplierD[1]);
        pst.setString(3,SupplierD[2]);
        pst.setString(4,SupplierD[3]);
        pst.setString(5,SupplierD[4]);
        pst.setString(6,SupplierD[5]);
        pst.setString(7,SupplierID);
        pst.executeUpdate();
    }
    
    public void SupplierUpdate(String[] SupplierD) throws SQLException
    {       
        pst = (PreparedStatement)con.prepareStatement("update Supplier set SupplierName=?, MobileNo=?, EmailId=?, Address=?, GSTINNo=?, AadharNo=?,Dues = ? where SupplierID = '"+SupplierD[6]+"'");
        
        pst.setString(1,SupplierD[0]);
        pst.setString(2,SupplierD[1]);
        pst.setString(3,SupplierD[2]);
        pst.setString(4,SupplierD[3]);
        pst.setString(5,SupplierD[4]);
        pst.setString(6,SupplierD[5]);
        pst.setDouble(7,Double.valueOf(SupplierD[7]));
        pst.executeUpdate();
    }
    
    public void CustomerUpdate(String[] CustomerD) throws SQLException
    {    
        pst = (PreparedStatement)con.prepareStatement("update Customer set CustomerName=?, MobileNo=?, EmailId=?, Address=?, GSTINNo=?, AadharNo=?,Dues = ? where customerid = '"+CustomerD[6]+"'");
        
        pst.setString(1,CustomerD[0]);
        pst.setString(2,CustomerD[1]);
        pst.setString(3,CustomerD[2]);
        pst.setString(4,CustomerD[3]);
        pst.setString(5,CustomerD[4]);
        pst.setString(6,CustomerD[5]);
        pst.setDouble(7,Double.valueOf(CustomerD[7]));
        pst.executeUpdate();
    }
    
    public String[] getSupplierNames() throws SQLException
    {
        int size=0;
        int i=-1;
        rs = stmt.executeQuery("select SupplierName from Supplier where Active = true");
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(Exception ex) {

        }
        String temp[] = new String[size];
        while(rs.next()){
            ++i;
            temp[i] = rs.getString(1);
        }
            
        return temp;
    }
    public String[] getCustomerNames() throws SQLException
    {
        int size=0;
        int i=-1;
        rs = stmt.executeQuery("select CustomerName from Customer where Active = true");
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(Exception ex) {

        }
        String temp[] = new String[size];
        while(rs.next()){
            ++i;
            temp[i] = rs.getString(1);
        }
            
        return temp;
    }
    public String[] getPersonsNamesToPay() throws SQLException
    {
        int size=0;
        int i=-1;
        rs = stmt.executeQuery("select PersonName from NewPerson where ToPay = true and ToReceive = false ORDER BY Amount DESC ");
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(Exception ex) {

        }
        String temp[] = new String[size];
        while(rs.next()){
            ++i;
            temp[i] = rs.getString(1);
        }
            
        return temp;
    }
    public String[] getPersonsNamesToReceive() throws SQLException
    {
        int size=0;
        int i=-1;
        rs = stmt.executeQuery("select PersonName from NewPerson where ToPay = false and ToReceive = true ORDER BY Amount DESC ");
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(Exception ex) {

        }
        String temp[] = new String[size];
        while(rs.next()){
            ++i;
            temp[i] = rs.getString(1);
        }
            
        return temp;
    }
    public String[] getSupplierD(String SupplierName) throws SQLException
    {
        rs = stmt.executeQuery("select MobileNo,AadharNo,GSTINNo,Dues from Supplier where SupplierName = '"+SupplierName+"'");
        String temp[] = new String[4];
        while(rs.next()){
            temp[0] = rs.getString(1);
            temp[1] = rs.getString(2);
            temp[2] = rs.getString(3);
            temp[3] = rs.getString(4);
        }
            
        return temp;
    }
    public String[] getSupplierDforUpdate(String SupplierID) throws SQLException
    {
        rs = stmt.executeQuery("select SupplierName,MobileNo,AadharNo,Address,GSTINNo,AadharNo,Dues from Supplier where SupplierID = '"+SupplierID+"'");
        String temp[] = new String[7];
        while(rs.next()){
            temp[0] = rs.getString(1);
            temp[1] = rs.getString(2);
            temp[2] = rs.getString(3);
            temp[3] = rs.getString(4);
            temp[4] = rs.getString(5);
            temp[5] = rs.getString(6);
            temp[6] = rs.getString(7);
        }
            
        return temp;
    }
    public String[] getCustomerD(String CustomerName) throws SQLException
    {
        rs = stmt.executeQuery("select MobileNo,AadharNo,GSTINNo,Dues from Customer where CustomerName = '"+CustomerName+"'");
        String temp[] = new String[4];
        while(rs.next()){
            temp[0] = rs.getString(1);
            temp[1] = rs.getString(2);
            temp[2] = rs.getString(3);
            temp[3] = rs.getString(4);
        }
            
        return temp;
    }
    
    public String[] getCustomerDforUpdate(String CustomerID) throws SQLException
    {
        rs = stmt.executeQuery("select CustomerName,MobileNo,AadharNo,Address,GSTINNo,AadharNo,Dues from Customer where CustomerID = '"+CustomerID+"'");
        String temp[] = new String[7];
        while(rs.next()){
            temp[0] = rs.getString(1);
            temp[1] = rs.getString(2);
            temp[2] = rs.getString(3);
            temp[3] = rs.getString(4);
            temp[4] = rs.getString(5);
            temp[5] = rs.getString(6);
            temp[6] = rs.getString(7);
        }      
        return temp;
    }
    
    public void Product(String Product[]) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into Product(ProductName,ProductCategory,ProductBrand,ProductPurchasePrice,ProductSellingPrice,ProductWeight,ProductUOM,ProductExp,ProductMfg,ProductQuantity) values(?,?,?,?,?,?,?,?,?,?)");
        pst.setString(1,Product[0]);
        pst.setString(2,Product[1]);
        pst.setString(3,Product[2]);
        pst.setString(4,Product[3]);
        pst.setString(5,Product[4]);
        pst.setString(6,Product[5]);
        pst.setString(7,Product[6]);
        pst.setString(8,Product[7]);
        pst.setString(9,Product[8]);
        pst.setString(10,Product[9]);
        pst.executeUpdate();
    }
    
    public void ProductMaster(String ProductM[]) throws SQLException
    {
//        rs = stmt.executeQuery("SELECT count(ProductID) FROM Product where ProductCategory = '"+ProductM[0]+"' and ProductName = '"+ProductM[1]+"' and ProductBrand = '"+ProductM[2]+"' and ProductWeight = '"+Integer.parseInt(ProductM[3])+"' and ProductUOM = '"+ProductM[4]+"'");
        pst = (PreparedStatement)con.prepareStatement("SELECT count(ProductID) FROM Product where ProductCategory = ? and ProductName = ? and ProductBrand = ? and ProductWeight = ? and ProductUOM = ?");
        pst.setString(1, ProductM[0]);
        pst.setString(2, ProductM[1]);
        pst.setString(3, ProductM[2]);
        pst.setInt(4, Integer.parseInt(ProductM[3]));
        pst.setString(5, ProductM[4]);
        rs = pst.executeQuery();
        int count1 = 0;
        while (rs.next()) {
            count1 = rs.getInt(1);
        }
        if (count1 == 0) {      
            //Entery For Old Stock
            pst = (PreparedStatement) con.prepareStatement("insert into Product(ProductId,Barcode,ProductName,ProductCategory,ProductBrand,ProductWeight,ProductUOM,ProductSellingPrice,ProductDescription,ProductExp,ProductMfg,Status) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            rs = stmt.executeQuery("SELECT ProductID FROM Product ORDER BY id DESC LIMIT 1;");
            long count = 0;
            String IDNo = null;
            while (rs.next()) {
                IDNo = rs.getString(1);
            }
            try {
                IDNo = IDNo.substring(6, IDNo.length());
                
            } catch (Exception e) {
                IDNo = null;
            }
            
            try {
                count = Long.parseLong(IDNo);
            } catch (NumberFormatException numberFormatException) {
                count = 0;
            }
            String ProductID = "ISProd" + String.valueOf(count + 1);
         
            pst.setString(1, ProductID);
            pst.setLong(2, Long.parseLong(ProductM[7]));
            pst.setString(3, ProductM[1]);
            pst.setString(4, ProductM[0]);
            pst.setString(5, ProductM[2]);
            pst.setInt(6, Integer.parseInt(ProductM[3]));
            pst.setString(7, ProductM[4]);
            pst.setInt(8, Integer.parseInt(ProductM[5]));
            pst.setString(9, ProductM[6]);
            pst.setDate(10, null);
            pst.setDate(11, null);
            pst.setString(12, "old");
            pst.executeUpdate();

            //Entery For New Stock
            pst = (PreparedStatement) con.prepareStatement("insert into Product(ProductId,Barcode,ProductName,ProductCategory,ProductBrand,ProductWeight,ProductUOM,ProductSellingPrice,ProductDescription,ProductExp,ProductMfg,Status) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            
            pst.setString(1, ProductID);
            pst.setLong(2, Long.parseLong(ProductM[7]));
            pst.setString(3, ProductM[1]);
            pst.setString(4, ProductM[0]);
            pst.setString(5, ProductM[2]);
            pst.setInt(6, Integer.parseInt(ProductM[3]));
            pst.setString(7, ProductM[4]);
            pst.setInt(8, Integer.parseInt(ProductM[5]));
            pst.setString(9, ProductM[6]);
            pst.setDate(10, null);
            pst.setDate(11, null);
            pst.setString(12, "new");
            pst.executeUpdate();
            
            //Entery for Stock
            pst = (PreparedStatement) con.prepareStatement("insert into Stock(ProductID,AvailableStock) values(?,?)");
            
            pst.setString(1, ProductID);
            pst.setInt(2, 0);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product Added Successfully!");
        }else{
            JOptionPane.showMessageDialog(null, "You Already have this Product");
        }
    }
    public void ProductMasterUpdate(String[] ProductMU) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("update Product set ProductID = ?,Barcode = ?,ProductName = ?,ProductCategory = ?,ProductBrand = ?,ProductWeight = ?,ProductUOM = ?,ProductSellingPrice = ?,ProductDescription = ?,ProductExp = ?,ProductMfg = ? where ProductID = '"+ProductMU[8]+"'");
        
        pst.setString(1,ProductMU[8]);
        pst.setLong(2,Long.parseLong(ProductMU[7]));
        pst.setString(3,ProductMU[1]);
        pst.setString(4,ProductMU[0]);
        pst.setString(5,ProductMU[2]);
        pst.setInt(6,Integer.parseInt(ProductMU[3]));
        pst.setString(7,ProductMU[4]);
        pst.setInt(8,Integer.parseInt(ProductMU[5]));
        pst.setString(9,ProductMU[6]);
        pst.setDate(10,null);
        pst.setDate(11,null);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Product Updated Successfully!");
    }
    public void ProductMT(javax.swing.JTable PMT) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status from Product";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockT(javax.swing.JTable PMT) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable(javax.swing.JTable PMT,long Quantity) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductQuantity >= "+Quantity+"";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTableCS(javax.swing.JTable PMT,long Quantity) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductQuantity >= "+Quantity+" and status = 'old'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTableNS(javax.swing.JTable PMT,long Quantity) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductQuantity >= "+Quantity+" and status = 'old'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable3(javax.swing.JTable PMT,String ProductCategory,String ProductName,String ProductBrand) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and ProductBrand = '"+ProductBrand+"' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable3CS(javax.swing.JTable PMT,String ProductCategory,String ProductName,String ProductBrand) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and ProductBrand = '"+ProductBrand+"' and status = 'old' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable3NS(javax.swing.JTable PMT,String ProductCategory,String ProductName,String ProductBrand) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and ProductBrand = '"+ProductBrand+"' and status = 'new' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable2(javax.swing.JTable PMT,String ProductCategory,String ProductName) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable2CS(javax.swing.JTable PMT,String ProductCategory,String ProductName) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and status = 'old' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable2NS(javax.swing.JTable PMT,String ProductCategory,String ProductName) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and status = 'new' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable1(javax.swing.JTable PMT,String ProductCategory) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable1CS(javax.swing.JTable PMT,String ProductCategory) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and status = 'old' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTable1NS(javax.swing.JTable PMT,String ProductCategory) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where ProductCategory = '"+ProductCategory+"' and status = 'new' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    public void ProductMTBS(javax.swing.JTable PMT) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status from Product where status = 'new'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    public void ProductMTCS(javax.swing.JTable PMT) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status from Product where status = 'old'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTBS(javax.swing.JTable PMT) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status from Product where status = 'new' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    public void ProductStockTCS(javax.swing.JTable PMT) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status from Product where status = 'old' and ProductQuantity > 0";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTableByExp(javax.swing.JTable PMT,String StartDate) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where EXPNotifyDate >= '"+StartDate+"' and EXPNotifyDate is not null and ProductQuantity>0 ORDER BY EXPNotifyDate ASC";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTableByExpCS(javax.swing.JTable PMT,String StartDate) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where EXPNotifyDate >= '"+StartDate+"' and EXPNotifyDate is not null and status = 'old' and ProductQuantity>0 ORDER BY EXPNotifyDate ASC";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductStockTableByExpNS(javax.swing.JTable PMT,String StartDate) throws SQLException
    {
        javax.swing.JTable ProductMasterTable = PMT;
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity,Status,EXPNotifyDate from Product where EXPNotifyDate >= '"+StartDate+"' and EXPNotifyDate is not null and status = 'new' and ProductQuantity>0 ORDER BY EXPNotifyDate ASC";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        ProductMasterTable.setModel(DbUtils.resultSetToTableModel(rs));
    }
    public void ProductD(String ProductID) throws SQLException
    {
        //System.out.println(ProductID);
        String sql = "Delete from product where ProductID = '"+ProductID+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Product Deleted Successfully!");
    }
    public String[] GetProductD(String ProductID) throws SQLException
    {
        rs = stmt.executeQuery("select ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductSellingPrice,Barcode,ProductDescription from product where ProductID = '"+ProductID+"'");
        String temp[] = new String[8];
        while(rs.next()){
            temp[0] = rs.getString(1);
            temp[1] = rs.getString(2);
            temp[2] = rs.getString(3);
            temp[3] = String.valueOf(rs.getInt(4));
            temp[4] = rs.getString(5);
            temp[5] = String.valueOf(rs.getInt(6));
            temp[6] = String.valueOf(rs.getLong(7));
            temp[7] = rs.getString(8);
        }
            
        return temp;
    }
    public void setProductCategory(JComboBox ProductCategory) throws SQLException
    {
        rs = stmt.executeQuery("select ProductCategory from product where status = 'old'");
        Vector<String> v = new Vector<String>();
        while(rs.next()){
            v.add(rs.getString(1));
        }
        
        Vector<String> v2 =  new Vector<String>();
        for(int i=0;i<v.size();i++)
        {
            int comp=0;
            comp = compareName(v2, v.get(i));
            if(comp == 0)
            {
                v2.add(v.get(i));
            }    
            
        }
        ProductCategory.setModel(new DefaultComboBoxModel(v2));
        ProductCategory.setSelectedIndex(-1);
    }
    
    int compareName(Vector<String> v2,String v)
    {
        int j=0;
        for(int i=0;i<v2.size();i++)
        {
            if(v.equals(v2.get(i)))
            {
                j=1;
                break;
            }
        }
        return j;
    }
    public void setProductName(JComboBox ProductName, String ProductCategory) throws SQLException
    {
//        rs = stmt.executeQuery("select ProductName from product where ProductCategory = '"+ProductCategory+"' and status = 'old'");
        pst = (PreparedStatement)con.prepareStatement("select ProductName from product where ProductCategory = ? and status = 'old'");
        pst.setString(1, ProductCategory);
        rs = pst.executeQuery();
        Vector v = new Vector();
        while(rs.next()){
            v.add(rs.getString(1));
        }
        ProductName.setModel(new DefaultComboBoxModel(v));
        ProductName.setSelectedIndex(-1);
    }
    public void setProductName(JComboBox ProductName) throws SQLException
    {
        rs = stmt.executeQuery("select ProductName from product where status = 'old'");
        Vector<String> v = new Vector<String>();
        while(rs.next()){
            v.add(rs.getString(1));
        }
        
        Vector<String> v2 =  new Vector<String>();
        for(int i=0;i<v.size();i++)
        {
            int comp=0;
            comp = compareName(v2, v.get(i));
            if(comp == 0)
            {
                v2.add(v.get(i));
            }    
            
        }
        ProductName.setModel(new DefaultComboBoxModel(v));
        ProductName.setSelectedIndex(-1);
    }
    public void setProductBrand(JComboBox ProductBrand, String ProductCategory, String ProductName) throws SQLException
    {
//        rs = stmt.executeQuery("select ProductBrand from product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and status = 'old'");
        pst = (PreparedStatement)con.prepareStatement("select ProductBrand from product where ProductCategory = ? and ProductName = ? and status = 'old'");
        pst.setString(1, ProductCategory);
        pst.setString(2, ProductName);
        rs = pst.executeQuery();
        Vector v = new Vector();
        while(rs.next()){
            v.add(rs.getString(1));
        }
        ProductBrand.setModel(new DefaultComboBoxModel(v));
        ProductBrand.setSelectedIndex(-1);
    }
    public void setProductBrand(JComboBox ProductBrand) throws SQLException
    {
        rs = stmt.executeQuery("select ProductBrand from product where status = 'old'");
        Vector<String> v = new Vector<String>();
        while(rs.next()){
            v.add(rs.getString(1));
        }
        
        Vector<String> v2 =  new Vector<String>();
        for(int i=0;i<v.size();i++)
        {
            int comp=0;
            comp = compareName(v2, v.get(i));
            if(comp == 0)
            {
                v2.add(v.get(i));
            }    
            
        }
        ProductBrand.setModel(new DefaultComboBoxModel(v));
        ProductBrand.setSelectedIndex(-1);
    }
    public void setProductID(JComboBox Size, JComboBox UOM, String ProductCategory, String ProductName,String ProductBrand) throws SQLException
    {
//        rs = stmt.executeQuery("select ProductWeight,ProductUOM from product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and ProductBrand = '"+ProductBrand+"' and status = 'old'");
        pst = (PreparedStatement)con.prepareStatement("select ProductWeight,ProductUOM from product where ProductCategory = ? and ProductName = ? and ProductBrand = ? and status = 'old'");
        pst.setString(1, ProductCategory);
        pst.setString(2, ProductName);
        pst.setString(3, ProductBrand);
        rs = pst.executeQuery();
        Vector v1 = new Vector();
        Vector v2 = new Vector();
        while(rs.next()){
            v1.add(rs.getString(1));
            v2.add(rs.getString(2));
        }
        Size.setModel(new DefaultComboBoxModel(v1));
        Size.setSelectedIndex(-1);
        UOM.setModel(new DefaultComboBoxModel(v2));
        UOM.setSelectedIndex(-1);
        
    }
    public void setProductSizeandUOM(JComboBox Size, JComboBox UOM) throws SQLException
    {
        rs = stmt.executeQuery("select ProductWeight,ProductUOM from product where status = 'old'");
        Vector<String> v1 = new Vector<String>();
        Vector<String> v2 = new Vector<String>();
        while(rs.next()){
            v1.add(rs.getString(1));
            v2.add(rs.getString(2));
        }
        
        Vector<String> v3 =  new Vector<String>();
        Vector<String> v4 =  new Vector<String>();
        for(int i=0;i<v1.size();i++)
        {
            int comp=0;
            comp = compareName(v3, v1.get(i));
            if(comp == 0)
            {
                v3.add(v1.get(i));
            }    
            
        }
        for(int i=0;i<v2.size();i++)
        {
            int comp=0;
            comp = compareName(v4, v2.get(i));
            if(comp == 0)
            {
                v4.add(v2.get(i));
            }    
            
        }
        
        Size.setModel(new DefaultComboBoxModel(v3));
        Size.setSelectedIndex(-1);
        UOM.setModel(new DefaultComboBoxModel(v4));
        UOM.setSelectedIndex(-1);
        
    }
    public String setSellingPrice_getProductID(JTextField SellingPrice, String ProductCategory, String ProductName,String ProductBrand,String Size, String UOM) throws SQLException
    {
//        rs = stmt.executeQuery("select ProductSellingPrice,ProductID from product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and ProductBrand = '"+ProductBrand+"' and ProductWeight = '"+Size+"' and ProductUOM = '"+UOM+"' and status = 'old'");
        pst = (PreparedStatement)con.prepareStatement("select ProductSellingPrice,ProductID from product where ProductCategory = ? and ProductName = ? and ProductBrand = ? and ProductWeight = ? and ProductUOM = ? and status = 'old'");
        pst.setString(1, ProductCategory);
        pst.setString(2, ProductName);
        pst.setString(3, ProductBrand);
        pst.setString(4, Size);
        pst.setString(5, UOM);
        rs = pst.executeQuery();
        String SP = null;
        String ProductID = null;
        while(rs.next()){
            SP = rs.getString(1);
            ProductID = rs.getString(2);
        }
        SellingPrice.setText(SP);
        return ProductID;
        
    }
    public String setAvailableQ_getProductID(JTextField AvailableQ, JTextField SellingPrice, String ProductCategory, String ProductName,String ProductBrand,String Size, String UOM) throws SQLException
    {
//        rs = stmt.executeQuery("select ProductID, ProductSellingPrice from product where ProductCategory = '"+ProductCategory+"' and ProductName = '"+ProductName+"' and ProductBrand = '"+ProductBrand+"' and ProductWeight = '"+Size+"' and ProductUOM = '"+UOM+"' and status = 'old'");
        pst = (PreparedStatement)con.prepareStatement("select ProductID,ProductSellingPrice from product where ProductCategory = ? and ProductName = ? and ProductBrand = ? and ProductWeight = ? and ProductUOM = ? and status = 'old'");
        pst.setString(1, ProductCategory);
        pst.setString(2, ProductName);
        pst.setString(3, ProductBrand);
        pst.setString(4, Size);
        pst.setString(5, UOM);
        rs = pst.executeQuery();
        String AQ = null;
        String ProductID = null;
        String price = null;
        while(rs.next()){
            ProductID = rs.getString(1);
            price = rs.getString(2);
        }
        rs = stmt.executeQuery("select AvailableStock from Stock where ProductID = '"+ProductID+"'");
        while(rs.next()){
            AQ = rs.getString(1);
        }
        AvailableQ.setText(AQ);
        SellingPrice.setText(price);
        return ProductID;
        
    }
    public void PurchaseProduct(String[] PurchaseE) throws SQLException
    {
        rs = stmt.executeQuery("select count(ProductID) from Product where ProductID = '"+PurchaseE[0]+"' and status = 'old' and ProductQuantity>0");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        
        if(count == 0)
        {
            pst = (PreparedStatement)con.prepareStatement("update Product set ProductExp = ?, ProductMfg = ?, EXPNotifyDate = ?,ProductQuantity = ?,ProductPurchasePRICE = ? where ProductID = '"+PurchaseE[0]+"' and status = 'old'");

            pst.setString(1,PurchaseE[17]);
            pst.setString(2,PurchaseE[16]);
            pst.setString(3,PurchaseE[18]);
            pst.setDouble(5, Double.valueOf(PurchaseE[7]));
            pst.setInt(4, Integer.parseInt(PurchaseE[6]));
            pst.executeUpdate();
        }else{
            pst = (PreparedStatement)con.prepareStatement("update Product set ProductExp = ?, ProductMfg = ?, EXPNotifyDate = ?,ProductQuantity = ?,ProductPurchasePRICE = ? where ProductID = '"+PurchaseE[0]+"' and status = 'new'");

            pst.setString(1,PurchaseE[17]);
            pst.setString(2,PurchaseE[16]);
            pst.setString(3,PurchaseE[18]);
            pst.setDouble(5, Double.valueOf(PurchaseE[7]));
            pst.setInt(4, Integer.parseInt(PurchaseE[6]));
            pst.executeUpdate();
        }
         
    }
    
    
    
    public boolean PurchaseProductC(String PurchaseE) throws SQLException
    {
        
        rs = stmt.executeQuery("select ProductQuantity from Product where ProductID = '"+PurchaseE+"' and status = 'new'");
        int count2 = 0;
        while(rs.next()){
            count2 = rs.getInt(1);
        }
        if(count2 == 0)
        {
            return true;
        }else{
            rs = stmt.executeQuery("select ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM from Product where ProductID = '"+PurchaseE+"' and status = 'new'");
            String ProductCategory = null;
            String ProductName = null;
            String ProductBrand = null;
            String ProductSize = null;
            String ProductUOM = null;
            while(rs.next()){
                ProductCategory = rs.getString(1);
                ProductName = rs.getString(2);
                ProductBrand = rs.getString(3);
                ProductSize = rs.getString(4);
                ProductUOM = rs.getString(5);
            }
            JOptionPane.showMessageDialog(null, "You Already have a BackUp Stock of "+ProductCategory+" "+ProductName+" "+ProductSize+" "+ProductUOM+" of Brand "+ProductBrand+" in your Inventory");
            return false;
        }  
    }
    
    public void SellProduct(String[] SaleE) throws SQLException
    {
        String query = "{ call SellE(?,?) }";
        
        cstmt = con.prepareCall(query);
        cstmt.setInt(1,Integer.parseInt(SaleE[6]));
        cstmt.setString(2,SaleE[0]);
        cstmt.executeQuery();
    }
    
//    public void PurchaseInvoiceUpdate(String[] PurchaseE) throws SQLException
//    {
//        rs = stmt.executeQuery("select count(ProductID) from Product where ProductID = '"+PurchaseE[0]+"' and status = 'new' and ProductQuantity>=0");
//        int count = 0;
//        while(rs.next()){
//            count = rs.getInt(1);
//        }
//        
//        if(count == 0)
//        {
//            pst = (PreparedStatement)con.prepareStatement("update Product set ProductExp = ?, ProductMfg = ?, EXPNotifyDate = ?,ProductQuantity = ?,ProductPurchasePRICE = ? where ProductID = '"+PurchaseE[0]+"' and status = 'old'");
//
//            pst.setString(1,PurchaseE[7]);
//            pst.setString(2,PurchaseE[8]);
//            pst.setString(3,PurchaseE[13]);
//            pst.setDouble(4, Double.valueOf(PurchaseE[3]));
//            pst.setInt(5, Integer.parseInt(PurchaseE[9]));
//            pst.executeUpdate();
//        }else{
//            pst = (PreparedStatement)con.prepareStatement("update Product set ProductExp = ?, ProductMfg = ?, EXPNotifyDate = ?,ProductQuantity = ?,ProductPurchasePRICE = ? where ProductID = '"+PurchaseE[0]+"' and status = 'new'");
//
//            pst.setString(1,PurchaseE[7]);
//            pst.setString(2,PurchaseE[8]);
//            pst.setString(3,PurchaseE[13]);
//            pst.setDouble(4, Double.valueOf(PurchaseE[3]));
//            pst.setInt(5, Integer.parseInt(PurchaseE[9]));
//            pst.executeUpdate();
//        }
//        
//    }
    public String SupplierID(String SupplierName, String MobileNo, String GSTINNo) throws SQLException
    {
        String SupplierID = null;
        if (MobileNo != null && GSTINNo != null) {
            rs = stmt.executeQuery("select SupplierID from Supplier where SupplierName = '" + SupplierName + "' and MobileNo = '" + MobileNo + "' and GSTINNo = '" + GSTINNo + "'");
            SupplierID = null;
            while (rs.next()) {
                SupplierID = rs.getString(1);
            }
        }else{
            rs = stmt.executeQuery("select SupplierID from Supplier where SupplierName = '" + SupplierName + "'");
            SupplierID = null;
            while (rs.next()) {
                SupplierID = rs.getString(1);
            }
        }
//        System.out.println(SupplierID);
        return SupplierID;
    }
    public String CustomerID(String CustomerName, String MobileNo, String GSTINNo) throws SQLException
    {
        
        String CustomerID = null;
        if (MobileNo == null && GSTINNo == null) {
            rs = stmt.executeQuery("select CustomerID from Customer where CustomerName = '" + CustomerName + "'");
            CustomerID = null;
            while (rs.next()) {
                CustomerID = rs.getString(1);
            }
        } else {
            rs = stmt.executeQuery("select CustomerID from Customer where CustomerName = '" + CustomerName + "' and MobileNo = '" + MobileNo + "' and GSTINNo = '" + GSTINNo + "'");
            CustomerID = null;
            while (rs.next()) {
                CustomerID = rs.getString(1);
            }
        }
        return CustomerID;
    }
    public String getInvoiceID() throws SQLException
    {
        String InvoiceID = null;
        long Count = 0;
        rs = stmt.executeQuery("select Count(ID) from InvoiceTable");
        while(rs.next()){
            Count = rs.getInt(1);
        }
        InvoiceID = "INVOID" + String.valueOf(Count+1);
        return InvoiceID;
    }
    
    public String getSellInvoiceID() throws SQLException
    {
        String InvoiceID = null;
        long Count = 0;
        rs = stmt.executeQuery("select Count(ID) from SellInvoiceTable");
        while(rs.next()){
            Count = rs.getInt(1);
        }
        InvoiceID = "SINVOID" + String.valueOf(Count+1);
        return InvoiceID;
    }
    
    public void InvoiceDetail(javax.swing.JTable InvoiceDT) throws SQLException
    {
        String sql = "Select ProductID,ProductCategory,ProductName,ProductBrand,ProductWeight,ProductUOM,ProductQuantity from Product,PurchaseInvoice";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        InvoiceDT.setModel(DbUtils.resultSetToTableModel(rs));
    }
    
    public void PurchaseInvoice(String[] PurchaseE) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into purchaseinvoice (invoiceID, ProductID, SupplierID, DateofInvoice, ProductPrice, ProductQuantity, GST) values(?,?,?,?,?,?,?)");
        
        try {
            String date = dateFormat.format(Dt);
            pst.setString(1, PurchaseE[20]);
            pst.setString(2, PurchaseE[0]);
            pst.setString(3, PurchaseE[19]);
            pst.setString(4, date);
            pst.setDouble(5, Double.valueOf(PurchaseE[7]));
            pst.setInt(6, Integer.parseInt(PurchaseE[6]));
            pst.setInt(7, Integer.parseInt(PurchaseE[8]));
        } catch (SQLException sQLException) {
            System.out.println("Error While Setting Invoice Table Values");
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Error While Setting Invoice Table Values");
        }
        try {
            pst.executeUpdate();
        } catch (SQLException sQLException) {
            System.out.println("Error While Firing Invoice Table Update Query");
        }
    }
    
    public void PurchaseInvoiceUpdate(Vector<String> ProductD,Vector<String> ProductID,String[] OtherD,double Due,String[] PurchaseD) throws SQLException
    {
        String query;
        String query2;
        for(int i=0;i<ProductID.size();i++)
        {
            query = "delete from purchaseinvoice where InvoiceID = '"+OtherD[0]+"' and ProductID = '"+ProductID.get(i).toString()+"'";
            pst = (PreparedStatement) con.prepareStatement(query);
            // execute the preparedstatement
            pst.execute();
            
            query2 = "{call PurchaseInvoiceEdit(?,?) }";
        
            cstmt = con.prepareCall(query2);
            cstmt.setString(1, ProductID.get(i).toString());
            cstmt.setInt(2,Integer.parseInt(ProductD.get(i).toString()));
            cstmt.executeQuery();
        }
        
        query = "update supplier set dues = "+Double.valueOf(Due)+" where SupplierID = '"+OtherD[1]+"'";
        pst = (PreparedStatement) con.prepareStatement(query);
        // execute the preparedstatement
        pst.execute();
        
        query = "update invoicetable set SupplierID=?, DateofInvoice=?, TotalAmount=?, Discount=?, PaidAmount=?, Dues=?, NetAmount=?, GrossAmount=?, PaymentMode=?,PreDues=? where invoiceID='"+OtherD[0]+"'";
        pst = (PreparedStatement) con.prepareStatement(query);
        // execute the preparedstatement
        String date = dateFormat.format(Dt);
        pst.setString(1, OtherD[1]);
        pst.setString(2, date);
        pst.setDouble(3, Double.valueOf(PurchaseD[1]));
        pst.setDouble(4, Double.valueOf(PurchaseD[2]));
        pst.setDouble(5, Double.valueOf(PurchaseD[3]));
        pst.setDouble(6, Double.valueOf(PurchaseD[5]));
        pst.setDouble(7, Double.valueOf(PurchaseD[0]));
        pst.setDouble(8, Double.valueOf(PurchaseD[6]));
        pst.setString(9, PurchaseD[4]);
        pst.setDouble(10, Double.valueOf(PurchaseD[9]));
        pst.execute();
    }
    
    public void SellInvoiceUpdate(Vector<String> ProductD,Vector<String> ProductID,String[] OtherD,double Due,String[] PurchaseD) throws SQLException
    {
        String query;
        String query2;
        for(int i=0;i<ProductID.size();i++)
        {
            query = "delete from sellinvoice where InvoiceID = '"+OtherD[0]+"' and ProductID = '"+ProductID.get(i).toString()+"'";
            pst = (PreparedStatement) con.prepareStatement(query);
            // execute the preparedstatement
            pst.execute();
            
            query2 = "{ call SellInvoiceEdit(?,?) }";
        
            cstmt = con.prepareCall(query2);
            cstmt.setString(1, ProductID.get(i).toString());
            cstmt.setInt(2,Integer.parseInt(ProductD.get(i).toString()));
            cstmt.executeQuery();
        }
        
        query = "update customer set dues = "+Double.valueOf(Due)+" where CustomerID = '"+OtherD[1]+"'";
        pst = (PreparedStatement) con.prepareStatement(query);
        // execute the preparedstatement
        pst.execute();
        
        query = "update sellinvoicetable set CustomerID=?, DateofInvoice=?, TotalAmount=?, Discount=?, PaidAmount=?, Dues=?, NetAmount=?, GrossAmount=?, PaymentMode=?,PreDues=? where invoiceID='"+OtherD[0]+"'";
        pst = (PreparedStatement) con.prepareStatement(query);
        // execute the preparedstatement
        String date = dateFormat.format(Dt);
        pst.setString(1, OtherD[1]);
        pst.setString(2, date);
        pst.setDouble(3, Double.valueOf(PurchaseD[1]));
        pst.setDouble(4, Double.valueOf(PurchaseD[2]));
        pst.setDouble(5, Double.valueOf(PurchaseD[3]));
        pst.setDouble(6, Double.valueOf(PurchaseD[5]));
        pst.setDouble(7, Double.valueOf(PurchaseD[0]));
        pst.setDouble(8, Double.valueOf(PurchaseD[6]));
        pst.setString(9, PurchaseD[4]);
        pst.setDouble(10, Double.valueOf(PurchaseD[9]));
        pst.execute();
    }
    
    public void SellInvoice(String[] SaleE) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into SellInvoice (invoiceID, ProductID, CustomerID, DateofInvoice, ProductPrice, ProductQuantity, GST) values(?,?,?,?,?,?,?)");
        
        try {
            String date = dateFormat.format(Dt);
            pst.setString(1, SaleE[17]);
            pst.setString(2, SaleE[0]);
            pst.setString(3, SaleE[16]);
            pst.setString(4, date);
            pst.setDouble(5, Double.valueOf(SaleE[7]));
            pst.setInt(6, Integer.parseInt(SaleE[6]));
            pst.setInt(7, Integer.parseInt(SaleE[8]));
        } catch (SQLException sQLException) {
            System.out.println("Error While Setting Invoice Table Values");
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Error While Setting Invoice Table Values");
        }
        try {
            pst.executeUpdate();
        } catch (SQLException sQLException) {
            System.out.println("Error While Firing Invoice Table Update Query");
        }
    }
    
    public void InvoiceTable(String[] PurchaseE) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into invoicetable(invoiceID, SupplierID, DateofInvoice, TotalAmount, Discount, PaidAmount, Dues, NetAmount, GrossAmount, PaymentMode,PreDues) values(?,?,?,?,?,?,?,?,?,?,?)");
        
        String date = dateFormat.format(Dt);
        pst.setString(1, PurchaseE[20]);
        pst.setString(2, PurchaseE[19]);
        pst.setString(3,date);
        pst.setDouble(4,Double.valueOf(PurchaseE[10]));
        pst.setDouble(5,Double.valueOf(PurchaseE[11]));
        pst.setDouble(6,Double.valueOf(PurchaseE[13]));
        pst.setDouble(7,Double.valueOf(PurchaseE[14]));
        pst.setDouble(8,Double.valueOf(PurchaseE[9]));
        pst.setDouble(9,Double.valueOf(PurchaseE[15]));
        pst.setDouble(11,Double.valueOf(PurchaseE[21]));
        pst.setString(10, PurchaseE[12]);
        
        pst.executeUpdate();
        
        pst = (PreparedStatement)con.prepareStatement("update Supplier set Dues=? where SupplierID = '"+PurchaseE[19]+"'");
        pst.setDouble(1,Double.valueOf(PurchaseE[14]));
        pst.executeUpdate();
    }
    
    public void SellInvoiceTable(String[] PurchaseE) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into sellinvoicetable(invoiceID, CustomerID, DateofInvoice, TotalAmount, Discount, PaidAmount, Dues, NetAmount, GrossAmount, PaymentMode,PreDues) values(?,?,?,?,?,?,?,?,?,?,?)");
        
        String date = dateFormat.format(Dt);
        pst.setString(1, PurchaseE[17]);
        pst.setString(2, PurchaseE[16]);
        pst.setString(3,date);
        pst.setDouble(4,Double.valueOf(PurchaseE[10]));
        pst.setDouble(5,Double.valueOf(PurchaseE[11]));
        pst.setDouble(6,Double.valueOf(PurchaseE[13]));
        pst.setDouble(7,Double.valueOf(PurchaseE[14]));
        pst.setDouble(8,Double.valueOf(PurchaseE[9]));
        pst.setDouble(9,Double.valueOf(PurchaseE[15]));
        pst.setString(10, PurchaseE[12]);
        pst.setDouble(11,Double.valueOf(PurchaseE[18]));
        
        pst.executeUpdate();
        
        pst = (PreparedStatement)con.prepareStatement("update Customer set Dues=? where CustomerID = '"+PurchaseE[16]+"'");
        pst.setDouble(1,Double.valueOf(PurchaseE[14])); 
        pst.executeUpdate();
    }
    
//    public void StockManage(String[] PurchaseE) throws SQLException
//    {
//        String query = "{ call StockManagement(?,?) }";
//        
//        cstmt = con.prepareCall(query);
//        cstmt.setInt(1,Integer.parseInt(PurchaseE[6]));
//        cstmt.setString(2,PurchaseE[0]);
//        cstmt.executeQuery();
//    }
    public void Customer(String[] CustomerD) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into Customer(CustomerName,MobileNo,EmailId,Address,GSTINNo,AadharNo,CustomerID) values(?,?,?,?,?,?,?)");
        
        
        rs = stmt.executeQuery("select count(CustomerName) from Customer");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        String CustomerID = "ISCUST"+String.valueOf(count+1);
        pst.setString(1,CustomerD[0]);
        pst.setString(2,CustomerD[1]);
        pst.setString(3,CustomerD[2]);
        pst.setString(4,CustomerD[3]);
        pst.setString(5,CustomerD[4]);
        pst.setString(6,CustomerD[5]);
        pst.setString(7,CustomerID);
        pst.executeUpdate();
    }  
    
    public boolean StatusOfSellInvoice(String InvoiceID) throws SQLException
    {
        rs = stmt.executeQuery("select count(InvoiceID) from sellinvoiceTable where InvoiceID='"+InvoiceID+"'");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        if(count > 0)
        {
            return false;
        }else{
            return true;
        }
    }
    
    public boolean StatusOfPurchaseInvoice(String InvoiceID) throws SQLException
    {
        rs = stmt.executeQuery("select count(InvoiceID) from InvoiceTable where InvoiceID='"+InvoiceID+"'");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        if(count > 0)
        {
            return false;
        }else{
            return true;
        }
    }
    
    public int CountCustomers() throws SQLException
    {
        rs = stmt.executeQuery("select count(CustomerID) from Customer where active = true");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        return count;
    }
    
    public Vector<String> Customers() throws SQLException
    {
        rs = stmt.executeQuery("select CustomerID,CustomerName,MobileNo,EmailID,Dues from Customer where active = true");
        Vector<String> v1 = new Vector<String>();
        while(rs.next()){
            v1.add(rs.getString(1));
            v1.add(rs.getString(2));
            v1.add(rs.getString(3));
            v1.add(rs.getString(4));
            v1.add(rs.getString(5));
//            v2.add(v1);
//            
//            v1.clear();
        }
        
        return v1;
    }
    public Vector<String> Suppliers() throws SQLException
    {
        rs = stmt.executeQuery("select SupplierID,SupplierName,MobileNo,EmailID,Dues from Supplier where active = true");
        Vector<String> v1 = new Vector<String>();
        Vector v2 = new Vector();
        while(rs.next()){
            v1.add(rs.getString(1));
            v1.add(rs.getString(2));
            v1.add(rs.getString(3));
            v1.add(rs.getString(4));
            v1.add(rs.getString(5));
//            v2.add(v1);
//            
//            v1.clear();
        }
        
        return v1;
    }
    
    
    public Vector<String> VCustomerSorting(String CustomerName) throws SQLException
    {
        Vector<String> v = new Vector<String>();
        String query = "{ call VCustomerSorting(?) }";
        
        cstmt = con.prepareCall(query);
        cstmt.setString(1,CustomerName);
        rs = cstmt.executeQuery();
        
        while(rs.next()){
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
//            v2.add(v1);
//            
//            v1.clear();
        }
        return v;
    }
    
    public Vector<String> VSupplierSorting(String SupplierName) throws SQLException
    {
        Vector<String> v = new Vector<String>();
        String query = "{ call VSupplierSorting(?) }";
        
        cstmt = con.prepareCall(query);
        cstmt.setString(1,SupplierName);
        rs = cstmt.executeQuery();
        
        while(rs.next()){
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
//            v2.add(v1);
//            
//            v1.clear();
        }
        return v;
    }
    
    public void CustomerActive(String CustomerID) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("Update Customer set Active = ? where CustomerID = '"+CustomerID+"'");
        
        pst.setBoolean(1, false);
        pst.executeUpdate();
    }
    
    public void SupplierActive(String SupplierID) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("Update Supplier set Active = ? where SupplierID = '"+SupplierID+"'");
        
        pst.setBoolean(1, false);
        pst.executeUpdate();
    }
    
    public Vector<String> PRSuppliersD() throws SQLException
    {
        String sql = "select SupplierID,SupplierName,MobileNo,EmailID,Dues from Supplier where active = true";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             v.add(rs.getString(5));
         }
        return v;
    }
    
    public Vector<String> PRCustomersD() throws SQLException
    {
        String sql = "select CustomerID,CustomerName,MobileNo,EmailID,Dues from Customer where active = true";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             v.add(rs.getString(5));
         }
        return v;
    }
    public Vector<String> PRSuppliersD(String SupplierName) throws SQLException
    {
        String sql = "select SupplierID,SupplierName,MobileNo,EmailID,Dues from Supplier where active = true and SupplierName = '"+SupplierName+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             v.add(rs.getString(5));
         }
        return v;
    }
    
    public Vector<String> PRCustomersD(String CustomerName) throws SQLException
    {
        String sql = "select CustomerID,CustomerName,MobileNo,EmailID,Dues from Customer where active = true and CustomerName = '"+CustomerName+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             v.add(rs.getString(5));
         }
        return v;
    }
    
    public void NewPersonD(String[] NewPersonD) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into NewPerson (PersonName,MobileNo,EmailId,Address,Amount,ToPay,ToReceive) values(?,?,?,?,?,?,?)");
        
        boolean ToPay = false;
        boolean ToReceive = false;
        
        if(NewPersonD[5].equals("ToPay"))
        {
            ToPay = true;
        }else{
            ToReceive = true;
        }
            
        pst.setString(1,NewPersonD[0]);
        pst.setString(2,NewPersonD[1]);
        pst.setString(3,NewPersonD[2]);
        pst.setString(4,NewPersonD[3]);
        pst.setDouble(5,Double.valueOf(NewPersonD[4]));
        pst.setBoolean(6,ToPay);
        pst.setBoolean(7,ToReceive);
        pst.executeUpdate();
    }
    public void NewPersonDUpdate(String[] NewPersonD) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("update NewPerson set PersonName=?,MobileNo=?,EmailId=?,Address=?,Amount=?,ToPay=?,ToReceive=? where PersonName = '"+NewPersonD[6]+"'");
        
        boolean ToPay = false;
        boolean ToReceive = false;
        
        if(NewPersonD[5].equals("ToPay"))
        {
            ToPay = true;
        }else{
            ToReceive = true;
        }
            
        pst.setString(1,NewPersonD[0]);
        pst.setString(2,NewPersonD[1]);
        pst.setString(3,NewPersonD[2]);
        pst.setString(4,NewPersonD[3]);
        pst.setDouble(5,Double.valueOf(NewPersonD[4]));
        pst.setBoolean(6,ToPay);
        pst.setBoolean(7,ToReceive);
        pst.executeUpdate();
    }
    
    public int NewPersonName(String NewPersonName) throws SQLException
    {
        rs = stmt.executeQuery("select count(PersonName) from NewPerson where PersonName = '"+NewPersonName+"'");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        
        return count;
    }
    
    public Vector<String> AllPersonToPayD() throws SQLException
    {
        Vector<String> PersonD = new Vector<String>();
        
        rs = stmt.executeQuery("select PersonName,MobileNo,EmailID,Amount from NewPerson where ToPay = true and ToReceive = false ORDER BY Amount DESC");
        while(rs.next()){
            PersonD.add(rs.getString(1));
            PersonD.add(rs.getString(2));
            PersonD.add(rs.getString(3));
            PersonD.add(rs.getString(4));
        }
        
        return PersonD;
    }
    public Vector<String> PersonToPayD(String PersonName) throws SQLException
    {
        Vector<String> PersonD = new Vector<String>();
        
        rs = stmt.executeQuery("select PersonName,MobileNo,EmailID,Amount from NewPerson where ToPay = true and ToReceive = false and PersonName = '"+PersonName+"' ORDER BY Amount DESC");
        while(rs.next()){
            PersonD.add(rs.getString(1));
            PersonD.add(rs.getString(2));
            PersonD.add(rs.getString(3));
            PersonD.add(rs.getString(4));
        }
        
        return PersonD;
    }
    public Vector<String> PersonToPayDforUpdate(String PersonName) throws SQLException
    {
        Vector<String> PersonD = new Vector<String>();
        
        rs = stmt.executeQuery("select PersonName,MobileNo,EmailID,Address,Amount from NewPerson where ToPay = true and ToReceive = false and PersonName = '"+PersonName+"' ORDER BY Amount DESC");
        while(rs.next()){
            PersonD.add(rs.getString(1));
            PersonD.add(rs.getString(2));
            PersonD.add(rs.getString(3));
            PersonD.add(rs.getString(4));
            PersonD.add(rs.getString(5));
        }
        
        return PersonD;
    }
    public Vector<String> AllPersonToReceiveD() throws SQLException
    {
        Vector<String> PersonD = new Vector<String>();
        
        rs = stmt.executeQuery("select PersonName,MobileNo,EmailID,Amount from NewPerson where ToPay = false and ToReceive = true ORDER BY Amount DESC");
        while(rs.next()){
            PersonD.add(rs.getString(1));
            PersonD.add(rs.getString(2));
            PersonD.add(rs.getString(3));
            PersonD.add(rs.getString(4));
        }
        
        return PersonD;
    }
    
    public Vector<String> PersonToReceiveD(String PersonName) throws SQLException
    {
        Vector<String> PersonD = new Vector<String>();
        
        rs = stmt.executeQuery("select PersonName,MobileNo,EmailID,Amount from NewPerson where ToPay = false and ToReceive = true and PersonName = '"+PersonName+"' ORDER BY Amount DESC");
        while(rs.next()){
            PersonD.add(rs.getString(1));
            PersonD.add(rs.getString(2));
            PersonD.add(rs.getString(3));
            PersonD.add(rs.getString(4));
        }
        
        return PersonD;
    }
    
    public Vector<String> PersonToReceiveDforUpdate(String PersonName) throws SQLException
    {
        Vector<String> PersonD = new Vector<String>();
        
        rs = stmt.executeQuery("select PersonName,MobileNo,EmailID,Address,Amount from NewPerson where ToPay = false and ToReceive = true and PersonName = '"+PersonName+"' ORDER BY Amount DESC");
        
        while(rs.next()){
            PersonD.add(rs.getString(1));
            PersonD.add(rs.getString(2));
            PersonD.add(rs.getString(3));
            PersonD.add(rs.getString(4));
            PersonD.add(rs.getString(5));
        }
        
        return PersonD;
    }
    
    public void RemovePerson(String PersonName) throws SQLException
    {
        
        String query = "delete from NewPerson where PersonName = '"+PersonName+"'";
        pst = (PreparedStatement) con.prepareStatement(query);
        // execute the preparedstatement
        pst.execute();
        
    }
    
    public void TransactionTable(String[] TransactionD) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into Transactiontable(Name, UserType, Given, Taken, DateofTransaction) values(?,?,?,?,?)");
        
        double given;
        double taken;
        if(TransactionD[2].equals("Taken"))
        {
            taken = Double.valueOf(TransactionD[3]);
            given = 0;
        }else{
            given = Double.valueOf(TransactionD[3]);
            taken = 0;
        }
        String date = dateFormat.format(Dt);
        pst.setString(1, TransactionD[0]);
        pst.setString(2,TransactionD[1]);
        pst.setDouble(3,given);
        pst.setDouble(4,taken);
        pst.setString(5,date);
        
        pst.executeUpdate();
    }
    
    public String getSupplierName(String SupplierID) throws SQLException
    {
        String SupplierName = null;
        
        rs = stmt.executeQuery("select SupplierName from Supplier where SupplierID = '"+SupplierID+"'");
        while(rs.next()){
            SupplierName = rs.getString(1);
        }
        return SupplierName;
    }
    
    public String getCustomerName(String CustomerID) throws SQLException
    {
        String CustomerName = null;
        
        rs = stmt.executeQuery("select CustomerName from Customer where CustomerID = '"+CustomerID+"'");
        while(rs.next()){
            CustomerName = rs.getString(1);
        }
        return CustomerName;
    }
    
    public Vector<String> PurRSupplierInvoiceD() throws SQLException
    {
        String sql = "select InvoiceID, SupplierID ,DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from InvoiceTable";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2)); 
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            sql = "select SupplierName from Supplier where SupplierID = '"+rs.getString(2)+"'";
            pst = (PreparedStatement)con.prepareStatement(sql);
            rs2 = pst.executeQuery();
            while(rs2.next())
            {
                v.add(rs2.getString(1));
            }
         }
        
        
        return v;
    }
    
    public Vector<String> PurRSupplierInvoiceDbyDate(String From,String To) throws SQLException
    {
        String sql = "select InvoiceID, SupplierID, DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from InvoiceTable where dateofinvoice between '"+From+"' and '"+To+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            sql = "select SupplierName from Supplier where SupplierID = '"+rs.getString(2)+"'";
            pst = (PreparedStatement)con.prepareStatement(sql);
            rs2 = pst.executeQuery();
            while(rs2.next())
            {
               v.add(rs2.getString(1));
            }
         }
        return v;
    }
    
    public Vector<String> SellRCustomerInvoiceDbyDate(String From,String To) throws SQLException
    {
        String sql = "select InvoiceID, CustomerID, DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from SellInvoiceTable where dateofinvoice between '"+From+"' and '"+To+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            if (rs.getString(2).equals("Retailer")) {
                v.add("Retailer");
            } else {
               sql = "select CustomerName from Customer where CustomerID = '" + rs.getString(2) + "'";
               pst = (PreparedStatement) con.prepareStatement(sql);
               rs2 = pst.executeQuery();
               while (rs2.next()) {
                   v.add(rs2.getString(1));
               }
            }
         }
        return v;
    }
    
    public Vector<String> PurRSupplierInvoiceDbyName(String SupplierID) throws SQLException
    {
        String sql = "select InvoiceID, SupplierID, DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from InvoiceTable where SupplierID = '"+SupplierID+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            sql = "select SupplierName from Supplier where SupplierID = '"+rs.getString(2)+"'";
            pst = (PreparedStatement)con.prepareStatement(sql);
            rs2 = pst.executeQuery();
            while(rs2.next())
            {
               v.add(rs2.getString(1));
            }
         }
        return v;
    }
    
    public Vector<String> SellRCustomerInvoiceDbyName(String CustomerID) throws SQLException
    {
        String sql = "select InvoiceID, CustomerID, DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from SellInvoiceTable where CustomerID = '"+CustomerID+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            if (rs.getString(2).equals("Retailer")) {
                v.add("Retailer");
            } else {
               sql = "select CustomerName from Customer where CustomerID = '" + rs.getString(2) + "'";
               pst = (PreparedStatement) con.prepareStatement(sql);
               rs2 = pst.executeQuery();
               while (rs2.next()) {
                   v.add(rs2.getString(1));
               }
            }
            
         }
        return v;
    }
    
    public Vector<String> PurRSupplierInvoiceDbyNameandDate(String From,String To,String SupplierID) throws SQLException
    {
        String sql = "select InvoiceID, SupplierID, DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from InvoiceTable where SupplierID = '"+SupplierID+"' and dateofinvoice between '"+From+"' and '"+To+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            sql = "select SupplierName from Supplier where SupplierID = '"+rs.getString(2)+"'";
            pst = (PreparedStatement)con.prepareStatement(sql);
            rs2 = pst.executeQuery();
            while(rs2.next())
            {
               v.add(rs2.getString(1));
            }
         }
        return v;
    }
    
    public Vector<String> SellRCustomerInvoiceDbyNameandDate(String From,String To,String CustomerID) throws SQLException
    {
        String sql = "select InvoiceID, CustomerID, DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from SellInvoiceTable where CustomerID = '"+CustomerID+"' and dateofinvoice between '"+From+"' and '"+To+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            if (rs.getString(2).equals("Retailer")) {
                v.add("Retailer");
            } else {
               sql = "select CustomerName from Customer where CustomerID = '" + rs.getString(2) + "'";
               pst = (PreparedStatement) con.prepareStatement(sql);
               rs2 = pst.executeQuery();
               while (rs2.next()) {
                   v.add(rs2.getString(1));
               }
            }
         }
        return v;
    }
    
    public Vector<String> PurRCustomerInvoiceD() throws SQLException
    {
        String sql = "select InvoiceID, CustomerID, DateofInvoice, TotalAmount, Discount,PaidAmount, NetAmount, GrossAmount from SellInvoiceTable";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            v.add(rs.getString(8));
            if (rs.getString(2).equals("Retailer")) {
                v.add("Retailer");
            } else {
               sql = "select CustomerName from Customer where CustomerID = '" + rs.getString(2) + "'";
               pst = (PreparedStatement) con.prepareStatement(sql);
               rs2 = pst.executeQuery();
               while (rs2.next()) {
                   v.add(rs2.getString(1));
               }
            }
         }
        return v;
    }
    
    public Vector<String> EditInvoicePurchaseD(String InvoiceID) throws SQLException    {
        String sql = "select SupplierID, DateofInvoice, TotalAmount, Discount, PaidAmount, Dues, NetAmount, paymentmode, GrossAmount, PreDues from InvoiceTable where InvoiceID = '"+InvoiceID+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             v.add(rs.getString(5));
             v.add(rs.getString(6));
             v.add(rs.getString(7));
             v.add(rs.getString(8));
             v.add(rs.getString(9));
             v.add(rs.getString(10));
         }
        return v;
    }
    
    public Vector<String> EditInvoiceSellD(String InvoiceID) throws SQLException    {
        String sql = "select CustomerID, DateofInvoice, TotalAmount, Discount, PaidAmount, Dues, NetAmount, paymentmode, GrossAmount, PreDues from sellInvoiceTable where InvoiceID = '"+InvoiceID+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             v.add(rs.getString(5));
             v.add(rs.getString(6));
             v.add(rs.getString(7));
             v.add(rs.getString(8));
             v.add(rs.getString(9));
             v.add(rs.getString(10));
//             System.out.println(v);
         }
        return v;
    }
    
    public Vector<String> EditPInvoiceProductD(String InvoiceID) throws SQLException    {
        String sql = "select ProductID, ProductPrice, ProductQuantity, GST from PurchaseInvoice where InvoiceID = '"+InvoiceID+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             
             
         }
//        System.out.println(v);
        return v;
    }
    
    public Vector<String> EditSInvoiceProductD(String InvoiceID) throws SQLException    {
        String sql = "select ProductID, ProductPrice, ProductQuantity, GST from sellInvoice where InvoiceID = '"+InvoiceID+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
//        System.out.println(rs);
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
//             System.out.println(v);
             
         }
//        System.out.println(v);
        return v;
    }
    
    public Vector<String> EditInvoiceProductDetails(String ProductID) throws SQLException    {
        String sql = "select ProductCategory, ProductName, ProductBrand, ProductWeight, ProductUOM from Product where ProductID = '"+ProductID+"' and status = 'old'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
             v.add(rs.getString(1));
             v.add(rs.getString(2));
             v.add(rs.getString(3));
             v.add(rs.getString(4));
             v.add(rs.getString(5));
//             System.out.println(v.get(0)+v.get(1)+v.get(2)+v.get(3)+v.get(4));
         }
        return v;
    }
    
    public String[] GetPProductDforPDF(String InvoiceID) throws SQLException
    {
        rs = stmt.executeQuery("select Count(*) from purchaseinvoice where InvoiceID = '"+InvoiceID+"'");
        int count = 0 ;
        while(rs.next()){
            count = rs.getInt(1);
        }
        String[] ProductD = new String[count*4];
        String[] ProductN = new String[count*3];
        int i = 0;
        rs = stmt.executeQuery("select ProductID,ProductPrice,ProductQuantity,GST from purchaseinvoice where InvoiceID = '"+InvoiceID+"'");
        while(rs.next()){
            ProductD[i] = rs.getString(1);
            ProductD[i+1] = rs.getString(2);
            ProductD[i+2] = rs.getString(3);
            ProductD[i+3] = rs.getString(4);
            i+=4;
        }      
            
        int a=0;
        int j=0;
        for(i=0,j=0;i<ProductD.length/4;i++,j+=4)
        {
            rs = stmt.executeQuery("select ProductName,ProductWeight,ProductUOM from product where ProductID = '"+ProductD[j]+"'");
            
            while(rs.next()){
                ProductN[a] = rs.getString(1);
                ProductN[a+1] = rs.getString(2);
                ProductN[a+2] = rs.getString(3);
                
            }
            a+=3;
        }
        double PPrice = 0;
        int Quantity = 0;
        int GST = 0;
        double SubTotal = 0;
        double Total = 0;
        String ProductName;
        String[] FProductD = new String[7];
        for(i=0,a=0,j=0;j<ProductD.length/4;j++,i+=4,a+=3)
        {
            ProductName = ProductN[a]+" ("+ProductN[a+1]+ProductN[a+2]+")";
            FProductD[0] = (ProductName);
            FProductD[1] = (ProductD[i+1]);
//            System.out.println(FProductD[1]);
            FProductD[2] = (ProductD[i+2]);
//            System.out.println(FProductD[2]);
            PPrice = Double.valueOf(ProductD[i+1]);
            Quantity = Integer.parseInt(ProductD[i+2]);
            SubTotal = PPrice * (double)Quantity;
            FProductD[3] = (String.valueOf(String.format("%.2f", SubTotal)));
//            System.out.println(FProductD[3]);
            GST = Integer.parseInt(ProductD[i+3]);
            FProductD[4] = (String.valueOf((float)(GST/2)));
//            System.out.println(FProductD[4]);
            FProductD[5] = (String.valueOf((float)(GST/2)));
//            System.out.println(FProductD[5]);
            Total = (((double)GST/100)*SubTotal) + SubTotal;
            FProductD[6] = (String.valueOf(String.format("%.2f", Total)));
//            System.out.println(FProductD[6]);
        }
        return FProductD;
    }
    
    public String[] GetSProductDforPDF(String InvoiceID) throws SQLException
    {
        rs = stmt.executeQuery("select Count(*) from sellinvoice where InvoiceID = '"+InvoiceID+"'");
        int count = 0 ;
        while(rs.next()){
            count = rs.getInt(1);
        }
        String[] ProductD = new String[count*4];
        String[] ProductN = new String[count*3];
        int i = 0;
        rs = stmt.executeQuery("select ProductID,ProductPrice,ProductQuantity,GST from sellinvoice where InvoiceID = '"+InvoiceID+"'");
        while(rs.next()){
            ProductD[i] = rs.getString(1);
            ProductD[i+1] = rs.getString(2);
            ProductD[i+2] = rs.getString(3);
            ProductD[i+3] = rs.getString(4);
            i+=4;
        }      
            
        int a=0;
        int j=0;
        for(i=0,j=0;i<ProductD.length/4;i++,j+=4)
        {
            rs = stmt.executeQuery("select ProductName,ProductWeight,ProductUOM from product where ProductID = '"+ProductD[j]+"'");
            
            while(rs.next()){
                ProductN[a] = rs.getString(1);
                ProductN[a+1] = rs.getString(2);
                ProductN[a+2] = rs.getString(3);
                
            }
            a+=3;
        }
        double PPrice = 0;
        int Quantity = 0;
        int GST = 0;
        double SubTotal = 0;
        double Total = 0;
        String ProductName;
        String[] FProductD = new String[7];
        for(i=0,a=0,j=0;j<ProductD.length/4;j++,i+=4,a+=3)
        {
            ProductName = ProductN[a]+" ("+ProductN[a+1]+ProductN[a+2]+")";
            FProductD[0] = (ProductName);
            FProductD[1] = (ProductD[i+1]);
//            System.out.println(FProductD[1]);
            FProductD[2] = (ProductD[i+2]);
//            System.out.println(FProductD[2]);
            PPrice = Double.valueOf(ProductD[i+1]);
            Quantity = Integer.parseInt(ProductD[i+2]);
            SubTotal = PPrice * (double)Quantity;
            FProductD[3] = (String.valueOf(String.format("%.2f", SubTotal)));
//            System.out.println(FProductD[3]);
            GST = Integer.parseInt(ProductD[i+3]);
            FProductD[4] = (String.valueOf((float)(GST/2)));
//            System.out.println(FProductD[4]);
            FProductD[5] = (String.valueOf((float)(GST/2)));
//            System.out.println(FProductD[5]);
            Total = (((double)GST/100)*SubTotal) + SubTotal;
            FProductD[6] = (String.valueOf(String.format("%.2f", Total)));
//            System.out.println(FProductD[6]);
        }
        return FProductD;
    }
    
    public String[] getSPaymentD(String InvoiceID) throws SQLException
    {
        String[] PaymentD = new String[9];
        rs = stmt.executeQuery("select NetAmount,TotalAmount,Discount,GrossAmount,PaymentMode,PaidAmount,Dues,PreDues from SellInvoiceTable where InvoiceID = '"+InvoiceID+"'");
        int count = 0 ;
        while(rs.next()){
            PaymentD[0] = rs.getString(1);
            PaymentD[1] = rs.getString(2);
            PaymentD[2] = rs.getString(3);
            PaymentD[3] = rs.getString(4);
            PaymentD[4] = rs.getString(5);
            PaymentD[5] = rs.getString(6);
            double GA = Double.valueOf(rs.getString(4));
            double PA = Double.valueOf(rs.getString(6));
            double B = GA - PA;
            PaymentD[6] = String.format("%.2f", B);
            PaymentD[7] = rs.getString(8);
            PaymentD[8] = rs.getString(7);
        }
        
        return PaymentD;
    }
    
    public String[] getPPaymentD(String InvoiceID) throws SQLException
    {
        String[] PaymentD = new String[9];
        rs = stmt.executeQuery("select NetAmount,TotalAmount,Discount,GrossAmount,PaymentMode,PaidAmount,Dues,PreDues from invoicetable where InvoiceID = '"+InvoiceID+"'");
        int count = 0 ;
        while(rs.next()){
            PaymentD[0] = rs.getString(1);
            PaymentD[1] = rs.getString(2);
            PaymentD[2] = rs.getString(3);
            PaymentD[3] = rs.getString(4);
            PaymentD[4] = rs.getString(5);
            PaymentD[5] = rs.getString(6);
            double GA = Double.valueOf(rs.getString(4));
            double PA = Double.valueOf(rs.getString(6));
            double B = GA - PA;
            PaymentD[6] = String.format("%.2f",B);
            PaymentD[7] = rs.getString(8);
            PaymentD[8] = rs.getString(7);
        }
        
        return PaymentD;
    }
    
    public Vector<String> getOStock() throws SQLException
    {
        Vector<String> ProductD = new Vector<String>();
        rs = stmt.executeQuery("select ProductID,ProductCategory,ProductName,ProductWeight,ProductUOM,ProductQuantity,EXPNotifyDate from Product where Status = 'old' and ProductQuantity is not null ORDER BY ProductQuantity ASC");
        while(rs.next())
        {
            ProductD.add(rs.getString(1));
            ProductD.add(rs.getString(2));
            ProductD.add(rs.getString(3)+"("+rs.getString(4)+rs.getString(5)+")");
            ProductD.add(rs.getString(6));
            ProductD.add(rs.getString(7));
        }
        return ProductD;
    }
    
    public Vector<String> getNStock() throws SQLException
    {
        Vector<String> ProductD = new Vector<String>();
        rs = stmt.executeQuery("select ProductID,ProductCategory,ProductName,ProductWeight,ProductUOM,ProductQuantity,EXPNotifyDate from Product where Status = 'new' and ProductQuantity is not null ORDER BY ProductQuantity ASC");
        while(rs.next())
        {
            ProductD.add(rs.getString(1));
            ProductD.add(rs.getString(2));
            ProductD.add(rs.getString(3)+"("+rs.getString(4)+rs.getString(5)+")");
            ProductD.add(rs.getString(6));
            ProductD.add(rs.getString(7));
        }
        return ProductD;
    }
    
    public Vector<String> getExpNotify() throws SQLException
    {
        Vector<String> ProductD = new Vector<String>();
        rs = stmt.executeQuery("select ProductID,ProductCategory,ProductName,ProductWeight,ProductUOM,ProductQuantity,EXPNotifyDate from Product where ProductQuantity is not null and  EXPNotifyDate is not null and ProductQuantity>0 ORDER BY EXPNotifyDate ASC");
        while(rs.next())
        {
            ProductD.add(rs.getString(1));
            ProductD.add(rs.getString(2));
            ProductD.add(rs.getString(3)+"("+rs.getString(4)+rs.getString(5)+")");
            ProductD.add(rs.getString(6));
            ProductD.add(rs.getString(7));
        }
        return ProductD;
    }
    
    public Vector<String> PerDaySell() throws SQLException
    {
        Vector<String> ProductD = new Vector<String>();
        String date = dateFormat.format(Dt);
        rs = stmt.executeQuery("select ProductID,ProductQuantity from SellInvoice where DateofInvoice = '"+date+"'");
        while(rs.next())
        {
            ProductD.add(rs.getString(1));
            ProductD.add(rs.getString(2));
            ProductD.add(rs.getString(3)+"("+rs.getString(4)+rs.getString(5)+")");              
            ProductD.add(rs.getString(6));
            ProductD.add(rs.getString(7));
        }
        return ProductD;
    }
    
    public Vector<String> PerDayPurchase() throws SQLException
    {
        Vector<String> ProductD = new Vector<String>();
        rs = stmt.executeQuery("select ProductID,ProductCategory,ProductName,ProductWeight,ProductUOM,ProductQuantity,EXPNotifyDate from Product where ProductQuantity is not null and  EXPNotifyDate is not null and ProductQuantity>0 ORDER BY EXPNotifyDate ASC");
        while(rs.next())
        {
            ProductD.add(rs.getString(1));
            ProductD.add(rs.getString(2));
            ProductD.add(rs.getString(3)+"("+rs.getString(4)+rs.getString(5)+")");
            ProductD.add(rs.getString(6));
            ProductD.add(rs.getString(7));
        }
        return ProductD;
    }
    
    public String AdminUserIDandPassword(String UserID) throws SQLException
    {
        String Password = "";
        rs = stmt.executeQuery("select UserID,Password from Admin where UserID = '"+UserID+"'");
        while(rs.next())
        {
            Password = rs.getString(1);
        }
        return Password;

    }
    
    public int NewEmployeeName(String Name) throws SQLException
    {
        int count = 0;
        rs = stmt.executeQuery("select count(EmpName) from Employee where EmpName = '"+Name+"'");
        while(rs.next())
        {
            count = rs.getInt(1);
        }
        return count;
    }
    
    public void NewEmployeeEntry(String EmpD[]) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into Employee(EmpName,DateofBirth,DateofJoining,MobileNo,EmailID,Address,Salary,EmpID,Password) values(?,?,?,?,?,?,?,?,?)");
        
        rs = stmt.executeQuery("select count(EmpName) from Employee");
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        
        String EMPID = "ISEMP"+String.valueOf(++count);
        
        pst.setString(1,EmpD[0]);
        pst.setString(2,EmpD[5]);
        pst.setString(3,EmpD[6]);
        pst.setString(4,EmpD[1]);
        pst.setString(5,EmpD[2]);
        pst.setString(6,EmpD[3]);
        pst.setDouble(7,Double.valueOf(EmpD[4]));
        pst.setString(8,EMPID);
        pst.setString(9,EMPID+"123");
        pst.executeUpdate();
        
        pst = (PreparedStatement)con.prepareStatement("insert into EmployeePermission(EmpID) values(?)");
        pst.setString(1,EMPID+"123");
        pst.executeUpdate();
    }
    
    public void UpdateEmployeeEntry(String EmpD[]) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("Update Employee set EmpName=?,DateofBirth=?,DateofJoining=?,MobileNo=?,EmailID=?,Address=?,Salary=?,ActiveEmp=? where EmpID = '"+EmpD[8]+"'");
        
        pst.setString(1,EmpD[0]);
        pst.setString(2,EmpD[5]);
        pst.setString(3,EmpD[6]);
        pst.setString(4,EmpD[1]);
        pst.setString(5,EmpD[2]);
        pst.setString(6,EmpD[3]);
        pst.setDouble(7,Double.valueOf(EmpD[4]));
        pst.setBoolean(8,Boolean.valueOf(EmpD[7]));
        pst.executeUpdate();
    }
    
    public Vector<String> UpdateEmployee(String EmpID) throws SQLException
    {
        Vector<String> v = new Vector<String>();
        rs = stmt.executeQuery("select EmpName,DateofBirth,DateofJoining,MobileNo,EmailID,Address,Salary,ActiveEmp from Employee where EmpID = '"+EmpID+"'");
        int count = 0;
        while(rs.next()){
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
            if(rs.getBoolean(8))
            {
                v.add("ActiveEmployee");
            }else{
                v.add("NotWorking");
            }
        }
        
        
        return v;
    }
    
    public String[] getEmployeeNames() throws SQLException
    {
        Vector<String> v = new Vector<String>();
        rs = stmt.executeQuery("select EmpName from Employee where ActiveEmp = true");
        int i = 0;
        int size = 0;
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(Exception ex) {

        }
//        System.out.println(size);
        String temp[] = new String[size];
        while(rs.next()){   
            temp[i] = rs.getString(1);
            ++i;
        }
        
        return temp;
    }
    
    public Vector<String> EAEmployeeD() throws SQLException
    {
        Vector<String> v = new Vector<String>();
//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit", "Delete"};
        rs = stmt.executeQuery("select EmpID,Password,EmpName,MobileNo,EmailID,Salary,DateofJoining from Employee where ActiveEmp = true");
        int count = 0;
        while(rs.next()){
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
        }
        
        return v;
    }
    
    public Vector<String> EAEmployeeD(String EmpName) throws SQLException
    {
        Vector<String> v = new Vector<String>();
//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit", "Delete"};
        rs = stmt.executeQuery("select EmpID,Password,EmpName,MobileNo,EmailID,Salary,DateofJoining from Employee where EmpName = '"+EmpName+"'");
        int count = 0;
        while(rs.next()){
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
        }
        
        return v;
    }
    
    public Vector<String> EANWEmployeeD() throws SQLException
    {
        Vector<String> v = new Vector<String>();
//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit", "Delete"};
        rs = stmt.executeQuery("select EmpID,Password,EmpName,MobileNo,EmailID,Salary,DateofJoining from Employee where ActiveEmp = false");
        int count = 0;
        while(rs.next()){
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            v.add(rs.getString(6));
            v.add(rs.getString(7));
        }
        
        return v;
    }
    
    public void EmployeePermissionUpdate(String MainPermission,String SubPermission,String EmpID) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("Update EmployeePermission set MainPermission=?,SubPermission=? where EmpID = '"+EmpID+"'");
        
        pst.setString(1,MainPermission);
        pst.setString(2,SubPermission);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Saved Successfully!");
    }
    
    public String EmployeeUserID(String EmpName) throws SQLException
    {
        String UserID = "";
//        String[] columnNames = {"EmpID", "Password", "Name", "Mobile No", "EmailID", "Salary","Date of Joining", "Edit", "Delete"};
        rs = stmt.executeQuery("select EmpID from Employee where EmpName = '"+EmpName+"'");
        while(rs.next()){
            UserID = rs.getString(1);
        }
        
        return UserID;
    }
    
    
    public String getMainPermission(String EmpID) throws SQLException
    {
        String MainPermission = "";
        rs = stmt.executeQuery("select MainPermission from employeepermission where EmpID = '"+EmpID+"'");
        while(rs.next()){
            MainPermission = rs.getString(1);
        }
        
        return MainPermission;
    }
    
    public String getSubPermission(String EmpID) throws SQLException
    {
        String SubPermission = "";
        rs = stmt.executeQuery("select SubPermission from employeepermission where EmpID = '"+EmpID+"'");
        while(rs.next()){
            SubPermission = rs.getString(1);
        }
        
        return SubPermission;
    }
    
    public void setEmpPassword(String Password,String EmpID) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("Update Employee set Password=? where EmpID = '"+EmpID+"'");
        
        pst.setString(1,Password);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Saved Successfully!");
    }
    
    public String getEmpPassword(String EmpID) throws SQLException
    {
        String Password = "";
        rs = stmt.executeQuery("select Password from Employee where EmpID = '"+EmpID+"' and ActiveEmp = true");
        while(rs.next()){
            Password = rs.getString(1);
        }
        
        return Password;
    }
    
    public String getEmpName(String EmpID) throws SQLException
    {
        String Name = "";
        rs = stmt.executeQuery("select EmpName from Employee where EmpID = '"+EmpID+"'");
        while(rs.next()){
            Name = rs.getString(1);
        }
        
        return Name;
    }
    
    public String getAdminName(String AdminID) throws SQLException
    {
        String Name = "";
        rs = stmt.executeQuery("select Name from admin where UserID = '"+AdminID+"'");
        while(rs.next()){
            Name = rs.getString(1);
        }
        
        return Name;
    }
    
    public Vector<String> RTransactionD() throws SQLException
    {
        String sql = "select UserType,Name,Given,Taken,Dateoftransaction from Transactiontable";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
         }
        return v;
    }
    public Vector<String> RTransactionD(String From,String To) throws SQLException
    {
        String sql = "select UserType,Name,Given,Taken,Dateoftransaction from Transactiontable where dateoftransaction between '"+From+"' and '"+To+"'";
        pst = (PreparedStatement)con.prepareStatement(sql);
        rs = pst.executeQuery();
        Vector<String> v = new Vector<String>();
        while(rs.next())
         {
            v.add(rs.getString(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
         }
        return v;
    }
    
    public void InserNewProductImage(String ImageName) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into ProductImages(Images) values(?)");
        
        pst.setString(1,ImageName);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Saved Successfully!");
    }
    
    public Vector<String> getMonthSoldProduct() throws SQLException
    {
        Vector<String> ProductD = new Vector<String>();
        rs = stmt.executeQuery("select ProductID,Category,Name,Brand,size,Quantity from monthsoldproduct");
        while(rs.next())
        {
            ProductD.add(rs.getString(1));
            ProductD.add(rs.getString(2));
            ProductD.add(rs.getString(3));
            ProductD.add(rs.getString(4));
            ProductD.add(rs.getString(5));
            ProductD.add(rs.getString(6));
        }
        return ProductD;
    }
    
    public void InsertNewProduct(String[] ProductD) throws SQLException
    {
        String DBMonth = null;
        String PMonth = dateFormat.format(Dt);
        String ProductID = ProductD[0];
        String PCategory = ProductD[1];
        String PName = ProductD[2];
        String PBrand = ProductD[3];
        String PSize = ProductD[4]+ProductD[5];
        double PPPrice = 0;
        double PSPrice = 0;
        String MonthProfitDate = null;
        rs = stmt.executeQuery("select ProductPurchasePrice,ProductSellingPrice from product where ProductID = '"+ProductID+"' and status = 'old'");
        while(rs.next())
        {
            PPPrice = Double.valueOf(rs.getString(1));
            PSPrice = Double.valueOf(rs.getString(2));
        }
        
        int PQuantity = Integer.parseInt(ProductD[6]);
        pst = (PreparedStatement)con.prepareStatement("update MonthSoldProduct set Category=?,Name=?,Brand=?,Size=?,Quantity=?,Date=?,ProductPPrice=?,ProductSPrice=?,TotalProfitRupee=?,TotalProfitPercent=? where ProductID = '"+ProductD[0]+"'");
        rs = stmt.executeQuery("select Date from monthsoldproduct");
        while(rs.next())
        {
            DBMonth = rs.getString(1);
            break;
        }
        MonthProfitDate = DBMonth;
        try {
            DBMonth = DBMonth.substring(5, 7);
            PMonth =  PMonth.substring(5, 7);
        } catch (Exception e) {
            DBMonth = "123";
            PMonth = "456";
            
        }
        if(DBMonth.equals(PMonth))
        {
//            System.out.println("Month Checked");
            int count = 0;
            rs = stmt.executeQuery("select count(ProductID) from monthsoldproduct where ProductID = '"+ProductID+"'");
            while(rs.next())
            {
                count = rs.getInt(1);
            }
            if(count == 0)
            {
                pst = (PreparedStatement)con.prepareStatement("insert into MonthSoldProduct(ProductID,Category,Name,Brand,Size,Quantity,Date,ProductPPrice,ProductSPrice,TotalProfitRupee,TotalProfitPercent) values(?,?,?,?,?,?,?,?,?,?,?)");
                String Date = dateFormat.format(Dt);
                pst.setString(1,ProductID);
                pst.setString(2,PCategory);
                pst.setString(3,PName);
                pst.setString(4,PBrand);
                pst.setString(5,PSize);
                pst.setInt(6,PQuantity);
                pst.setString(7,Date);
                double TotalAmount1 = 0;
                double TotalAmount2 = 0;
                double ProfitRuppe = 0;
                float ProfitPercent = 0;
                TotalAmount1 = PSPrice*PQuantity;
                TotalAmount2 = PPPrice*PQuantity;
                ProfitRuppe = TotalAmount1 - TotalAmount2;
                ProfitPercent = (float) ((ProfitRuppe/TotalAmount2)*100);
                pst.setDouble(8,PPPrice);
                pst.setDouble(9,PSPrice);
                pst.setDouble(10,ProfitRuppe);
                pst.setFloat(11,ProfitPercent);
                pst.executeUpdate();
            }else{
                count = 0;
                rs = stmt.executeQuery("select Quantity from monthsoldproduct where ProductID = '"+ProductID+"'");
                while(rs.next())
                {
                    count = rs.getInt(1);
                }
                PQuantity += count;
                String Date = dateFormat.format(Dt);
                pst.setString(1,PCategory);
                pst.setString(2,PName);
                pst.setString(3,PBrand);
                pst.setString(4,PSize);
                pst.setInt(5,PQuantity);
                pst.setString(6,Date);
                double TotalAmount1 = 0;
                double TotalAmount2 = 0;
                double ProfitRuppe = 0;
                float ProfitPercent = 0;
                TotalAmount1 = PSPrice*PQuantity;
                TotalAmount2 = PPPrice*PQuantity;
                ProfitRuppe = TotalAmount1 - TotalAmount2;
                ProfitPercent = (float) ((ProfitRuppe/TotalAmount2)*100);
                pst.setDouble(7,PPPrice);
                pst.setDouble(8,PSPrice);
                pst.setDouble(9,ProfitRuppe);
                pst.setFloat(10,ProfitPercent);
                pst.executeUpdate();
            }
        }else{
            
            if(!DBMonth.equals("123"))
            {
                rs = stmt.executeQuery("select ProductID,ProductPPrice,ProductSPrice,TotalProfitRupee,TotalProfitPercent from monthsoldproduct");
                while(rs.next())
                {
                    pst = (PreparedStatement) con.prepareStatement("insert into monthprofit(ProductID,ProductPPrice,ProductSPrice,ProfitRupee,ProfitPercent,MonthOfYear) values(?,?,?,?,?,?)");
                    String Date = dateFormat.format(Dt);
                    MonthProfitDate = MonthProfitDate.substring(0, MonthProfitDate.length() - 2);
                    MonthProfitDate = MonthProfitDate + "01";
                    pst.setString(1, rs.getString(1));
                    pst.setDouble(2, Double.valueOf(rs.getString(2)));
                    pst.setDouble(3, Double.valueOf(rs.getString(3)));
                    pst.setDouble(4, Double.valueOf(rs.getString(4)));
                    pst.setFloat(5, Float.valueOf(rs.getString(5)));
                    pst.setString(6, MonthProfitDate);
                    pst.executeUpdate();
                }
            
            }
            
            
            stmt.executeUpdate("Truncate Table monthsoldproduct");
            pst = (PreparedStatement)con.prepareStatement("insert into monthsoldproduct(ProductID,Category,Name,Brand,Size,Quantity,Date,ProductPPrice,ProductSPrice,TotalProfitRupee,TotalProfitPercent) values(?,?,?,?,?,?,?,?,?,?,?)");
            String Date = dateFormat.format(Dt);
            pst.setString(1,ProductID);
            pst.setString(2,PCategory);
            pst.setString(3,PName);
            pst.setString(4,PBrand);
            pst.setString(5,PSize);
            pst.setInt(6,PQuantity);
            pst.setString(7,Date);
            double TotalAmount1 = 0;
            double TotalAmount2 = 0;
            double ProfitRuppe = 0;
            float ProfitPercent = 0;
            TotalAmount1 = PSPrice*PQuantity;
            TotalAmount2 = PPPrice*PQuantity;
            ProfitRuppe = TotalAmount1 - TotalAmount2;
            ProfitPercent = (float) ((ProfitRuppe/TotalAmount2)*100);
            pst.setDouble(8,PPPrice);
            pst.setDouble(9,PSPrice);
            pst.setDouble(10,ProfitRuppe);
            pst.setFloat(11,ProfitPercent);
            pst.executeUpdate();
        }
    }
    
    public void UpdateAdminD(String[] AdminD) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("update admin set UserID=?,Password=? where UserID = '"+AdminD[5]+"'");
        
        pst.setString(1,AdminD[0]);
        pst.setString(2,AdminD[2]);
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Saved Successfully!");
    }
    
    public String[] getMothProfit(String Date) throws SQLException
    {
        String[] Profit = new String[4];
        double MonthProfitRupee = 0;
        double YearProfitRupee = 0;
        float MonthProfitPercent = 0;
        float YearProfitPercent = 0;
        int count = 0;
        rs = stmt.executeQuery("select ProfitRupee,ProfitPercent from monthprofit where MonthOfYear = '"+Date+"'");
        while(rs.next())
        {
            MonthProfitRupee += rs.getDouble(1);
            MonthProfitPercent += rs.getFloat(2);
            count++;
        }
        
        if (count != 0) {
            MonthProfitPercent = MonthProfitPercent / count;
        }
        
        Date = Date.substring(0, 5)+"01-01";
//        System.out.println(Date);
        
        String Date2 = Date.substring(0, 5)+"12-01";
//        System.out.println(Date2);
        count = 0;
        rs = stmt.executeQuery("select ProfitRupee,ProfitPercent from monthprofit where MonthOfYear between '"+Date+"' and '"+Date2+"' ");
        while(rs.next())
        {
            YearProfitRupee += rs.getDouble(1);
            YearProfitPercent += rs.getFloat(2);
            count++;
        }
        if (count != 0) {
            YearProfitPercent = YearProfitPercent / count;
        }
        
        Profit[0] = String.valueOf(MonthProfitPercent);
        Profit[1] = String.valueOf(MonthProfitRupee);
        Profit[2] = String.valueOf(YearProfitPercent);
        Profit[3] = String.valueOf(YearProfitRupee);

        return Profit;
    }
    
    public void ShopDetails(String[] ShopDetails) throws SQLException
    {
        try {
            stmt.executeUpdate("TRUNCATE table shopdetails");
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error While Deleteing Previous Data...");
        }
        
        pst = (PreparedStatement)con.prepareStatement("insert into shopdetails(ShopName,Address,Near,City,Pincode,ContactNo,WebSite) values(?,?,?,?,?,?,?)");
        pst.setString(1,ShopDetails[0]);
        pst.setString(2,ShopDetails[1]);
        pst.setString(3,ShopDetails[2]);
        pst.setString(4,ShopDetails[3]);
        pst.setString(5,ShopDetails[4]);
        pst.setString(6,ShopDetails[5]);
        pst.setString(7,ShopDetails[6]);
        int i = pst.executeUpdate();
        if(i > 0) {
            JOptionPane.showMessageDialog(null, "Updated Successfully!");
        }else{
            JOptionPane.showMessageDialog(null, "Update Failed!");
        }
     }
    
    public String[] ShopsDetail() throws SQLException
    {
        String[] ShopDetails = new String[7];
        
        rs = stmt.executeQuery("select ShopName,Address,Near,City,Pincode,ContactNo,WebSite from shopdetails");
        while(rs.next())
        {
            ShopDetails[0] = rs.getString(1);
            ShopDetails[1] = rs.getString(2);
            ShopDetails[2] = rs.getString(3);
            ShopDetails[3] = rs.getString(4);
            ShopDetails[4] = rs.getString(5);
            ShopDetails[5] = rs.getString(6);
            ShopDetails[6] = rs.getString(7);
        }
        return ShopDetails;
     }
    
    public void Terms_Conditions(String TermsConditions) throws SQLException
    {   
        pst = (PreparedStatement)con.prepareStatement("insert into termsandconditions(TAndC) values(?)");
        pst.setString(1,TermsConditions);
        int i = pst.executeUpdate();
        if(i > 0) {
            JOptionPane.showMessageDialog(null, "Added Successfully!");
        }else{
            JOptionPane.showMessageDialog(null, "Failed!");
        }
     }
    
    public void Clear_Terms_Conditions() throws SQLException
    {
        try {
            stmt.executeUpdate("TRUNCATE table termsandconditions");
            JOptionPane.showMessageDialog(null, "Clear Successfully!");
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error While Deleteing Terms & Conditions...");
        }
        
     }
    
    public String[] Terms_Conditions() throws SQLException
    {
        
        int i = 0,size = 0;
        rs = stmt.executeQuery("select TAndC from termsandconditions");
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(Exception ex) {

        }
        String[] TermsConditions = new String[size];
        while(rs.next())
        {
            TermsConditions[i] = rs.getString(1);
            i++;
        }
        return TermsConditions;
     }
    
    public String[] getCustomerDForBill(String InvoiceID) throws SQLException
    {
        rs = stmt.executeQuery("select CustomerID from sellinvoice where InvoiceID = '"+InvoiceID+"'");
        String CustomerID = "";
        while(rs.next()) {
            CustomerID = rs.getString(1);
        }
        rs = stmt.executeQuery("select CustomerName,Address,GSTINNo,MobileNo from customer where CustomerID = '"+CustomerID+"'");
        String temp[] = new String[7];
        while(rs.next()){
            temp[0] = rs.getString(1);
            temp[1] = rs.getString(2);
            temp[2] = rs.getString(3);
            temp[3] = rs.getString(4);
        }
        temp[4] = "";
        temp[5] = "";
        temp[6] = "";
        return temp;
    }
    
    public String[] getSupplierDForBill(String InvoiceID) throws SQLException
    {
        rs = stmt.executeQuery("select SupplierID from purchaseinvoice where InvoiceID = '"+InvoiceID+"'");
        String SupplierID = "";
        while(rs.next()) {
            SupplierID = rs.getString(1);
        }
        rs = stmt.executeQuery("select SupplierName,Address,GSTINNo,MobileNo from supplier where SupplierID = '"+SupplierID+"'");
        String temp[] = new String[7];
        while(rs.next()){
            temp[0] = rs.getString(1);
            temp[1] = rs.getString(2);
            temp[2] = rs.getString(3);
            temp[3] = rs.getString(4);
        }
        temp[4] = "";
        temp[5] = "";
        temp[6] = "";
        return temp;
    }
    
    public void LogoDetails(int width, int position) throws SQLException
    {
        try {
            stmt.executeUpdate("TRUNCATE table logodetails");
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error While Deleteing Previous Values...");
        }
        
        pst = (PreparedStatement)con.prepareStatement("insert into logodetails(width,position) values(?,?)");
        pst.setInt(1,width);
        pst.setInt(2,position);
        int i = pst.executeUpdate();
        if(i > 0) {
            JOptionPane.showMessageDialog(null, "Updated Successfully!");
        }else{
            JOptionPane.showMessageDialog(null, "Update Failed!");
        }
     }
    
    public int[] getLogoDetails() throws SQLException
    {
        int[] logodetails = new int[2];
        rs = stmt.executeQuery("select Width,Position from logodetails");
        while(rs.next()){
            logodetails[0] = rs.getInt(1);
            logodetails[1] = rs.getInt(2);
        }
        return logodetails;
    }
    
    public String getPDFPaths() throws SQLException
    {
        String PDFPath = null;
        rs = stmt.executeQuery("select PDFloc from pdfpath");
        while(rs.next()){
            try {
                PDFPath = rs.getString(1);
            } catch (NullPointerException ex) {
            }
        }
        return PDFPath;
    }
    
    public void setPDFPaths(String PDFpath) throws SQLException
    {
        pst = (PreparedStatement)con.prepareStatement("insert into pdfpath(PDFloc) values(?)");
        pst.setString(1,PDFpath);
        int i = pst.executeUpdate();
        if(i > 0) {
            JOptionPane.showMessageDialog(null, "Path Selected Successfully!");
        }else{
            JOptionPane.showMessageDialog(null, "Path Selection Failed!");
        }
    }
    
    public int getTrialDays() throws SQLException
    {
        int trialdays = 0;
        String PresentDate = "";
        String date = dateFormat.format(Dt);
        rs = stmt.executeQuery("select days,PresentDate from trialdays");
        while(rs.next()){
            try {
                trialdays = rs.getInt(1);
                PresentDate = rs.getString(2);
            } catch (NullPointerException ex) {
            }
        }
        
        if(PresentDate == null) {
            pst = (PreparedStatement)con.prepareStatement("update trialdays set PresentDate=? where id = 1");
            pst.setString(1,date);
            pst.executeUpdate();
        }
        rs = stmt.executeQuery("select days,PresentDate from trialdays");
        while(rs.next()){
            try {
                trialdays = rs.getInt(1);
                PresentDate = rs.getString(2);
            } catch (NullPointerException ex) {
            }
        }
        if(date.equals(PresentDate)) {
            return trialdays;
        }else{
            if(trialdays == 0) {
                return trialdays;
            }else {
                pst = (PreparedStatement)con.prepareStatement("update trialdays set days=?, PresentDate=? where id = 1");
                pst.setInt(1,(trialdays-1));
                pst.setString(2, date);
                pst.executeUpdate();
                return trialdays-1;
            }
        }
        
    }
}

