/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;


import Database.DatabaseConnection;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfTemplate;
import java.awt.Color;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class PDFGenerator {
    
    
    CallableStatement cstmt;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date Dt = new Date();
    Paragraph pg;
    Document doc;
    PdfWriter pw;
    PdfContentByte canvas;
    DatabaseConnection db;
    DashBoard DB;
    
    
    //File Saving Location
    String FolderPath = "";
    
    String SupplierInvoicePath1 = "src//PDF//SupplierReport//";
    String SupplierInvoicePath2 = "E:\\aman\\aman2\\InventorySystem\\src\\PDF\\SupplierReport\\";
    
    String TransactionDRPath1 = "src//PDF//TransactionDReport//";
    String TransactionDRPath2 = "E:\\java\\aman\\aman2\\InventorySystem\\src\\PDF\\TransactionDReport\\";
    //Create Custom Font
    Font TimsRomen = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    Font InvoiceHeading = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    Font InvoiceHeading2 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD,BaseColor.CYAN);
    Font tabletitle = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
    Font tableHeading = new Font(Font.FontFamily.COURIER, 9, Font.BOLD);
    Font tabledata = new Font(Font.FontFamily.COURIER, 8, Font.NORMAL);
    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    Font SanSerif = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 12, Font.NORMAL, BaseColor.BLACK);
    public PDFGenerator(Connection con,DashBoard DB) throws SQLException {
        db = new DatabaseConnection(InventorySystem.con);
        this.DB = DB;
        try {
            doc = new Document(PageSize.A4);
        } catch (Exception e) {
        }
        pg = new Paragraph();
        
        
    }
     
    public void CheckMainFolder() throws DocumentException, FileNotFoundException, IOException, SQLException {
        String PDFPath = null;
        try {
            PDFPath = db.getPDFPaths();
        }catch (NullPointerException ex) {
        }catch (SQLException sQLException) {
        }
        
        
        if(PDFPath == null) {
            JOptionPane.showMessageDialog(null, "Select Folder to Store PDF.");
            JFileChooser js = new JFileChooser();
            String txtpdfpath = null;
            js.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int r = js.showSaveDialog(DB);

            if(r == JFileChooser.APPROVE_OPTION)
            {
                txtpdfpath = js.getSelectedFile().getAbsolutePath();
                txtpdfpath = txtpdfpath.replace("\\", "//");
                db.setPDFPaths(txtpdfpath);
            }
        }else {
                File dir = new File(PDFPath);// Tests whether the directory denoted by this abstract pathname exists.
                boolean exists = dir.exists();
                if(!exists) {
                    JOptionPane.showMessageDialog(null, "Old folder no longer exist. Select any other folder.");
                    JFileChooser js = new JFileChooser();
                    String txtpdfpath = null;
                    js.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int r = js.showSaveDialog(DB);

                    if(r == JFileChooser.APPROVE_OPTION)
                    {
                        txtpdfpath = js.getSelectedFile().getAbsolutePath();
                        txtpdfpath = txtpdfpath.replace("\\", "//");
                        db.setPDFPaths(txtpdfpath);
                    }
                }
        }
    }
    public String CheckFolders(String Folder) throws DocumentException, FileNotFoundException, IOException, SQLException {
        CheckMainFolder();
        String PDFPath = "";
        try {
            PDFPath = db.getPDFPaths();
        }catch (NullPointerException ex) {
        }catch (SQLException sQLException) {
        }
        
        File dir = new File(PDFPath+"//"+Folder);// Tests whether the directory denoted by this abstract pathname exists.
        boolean exists = dir.exists();
        if(!exists) {
            Path path = Paths.get(PDFPath+"//"+Folder);
            //if directory exists?
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                }catch (IOException e) {
                    //fail to create directory
                    JOptionPane.showMessageDialog(null, "Failed to create directory!");
                }
            }
        }
        return PDFPath;
    }
    public void CustomerPR(Vector<String> InvoiceD,int rowcount) throws DocumentException, FileNotFoundException, IOException, SQLException
    {
        FolderPath = CheckFolders("SellInvoiceReport");

        doc = new Document(PageSize.A4);
        String date = dateFormat.format(Dt);
        try {
            pw = PdfWriter.getInstance(doc, new FileOutputStream(FolderPath+"//SellInvoiceReport//CustomerInvoice"+date+".pdf"));
        } catch (FileNotFoundException fileNotFoundException) {
        } catch (DocumentException documentException) {
        }
        doc.setMargins(2, 2, 2, 2);
        doc.setMarginMirroring(false);
        doc.open();

        try {
            canvas = pw.getDirectContent();
        } catch (Exception e) {
        }
        Paragraph para;
        para = new Paragraph(new Chunk("   Date : "+date+"", SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);


        Image img;
        img = Image.getInstance("src//Images//Shop-Logo.png");
        int[] logodetails = null;
        try {
            logodetails = db.getLogoDetails();
        } catch (SQLException sQLException) {
        }

        img.setAbsolutePosition(200+logodetails[1],780);
        img.scaleAbsolute(logodetails[0], 55);
        doc.add(img);
//        cell.setImage(img);
//        table.setWidths(new int[]{30});
//        cell.setFixedHeight(80);
//        table.addCell(cell);
//        table.set
//        doc.add(table);

        PdfPTable  table = new PdfPTable(8);

        PdfPCell cell = new PdfPCell(new Paragraph("List of Invoices",tabletitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        BaseColor clr = new BaseColor(126, 126, 126);
        cell.setBackgroundColor(clr);
        cell.setColspan(8);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("InvoiceID",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Customer",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Date",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Total Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Net Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Discount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Paid Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Gross Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        String CustomerName = null;

        for(int i=0,j=0;j<rowcount;j++,i+=8)
        {
            table.addCell(new Paragraph(InvoiceD.get(i),tableHeading));
            CustomerName = db.getCustomerName(InvoiceD.get(i+1));
            if(CustomerName == null)
            {
                CustomerName = "Retailer";
            }
            table.addCell(new Paragraph(CustomerName,tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+2),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+3),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+6),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+4),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+5),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+7),tableHeading));
        }


        doc.add( Chunk.NEWLINE );
        doc.add( Chunk.NEWLINE );
        table.setTotalWidth(PageSize.A4.getWidth()-40);
        table.setLockedWidth(true);
        doc.add(table);
        doc.close();
        try {
            pw.close();
        } catch (Exception e) {
        }
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+FolderPath+"//SellInvoiceReport//CustomerInvoice"+date+".pdf");
        
    }
    
    public void SupplierPR(Vector<String> InvoiceD,int rowcount) throws DocumentException, FileNotFoundException, IOException, SQLException
    {
        FolderPath = CheckFolders("PurchaseInvoiceReport");
        doc = new Document(PageSize.A4);
        String date = dateFormat.format(Dt);
        try {
            pw = PdfWriter.getInstance(doc, new FileOutputStream(FolderPath+"//PurchaseInvoiceReport//SupplierInvoice"+date+".pdf"));
        } catch (FileNotFoundException fileNotFoundException) {
        } catch (DocumentException documentException) {
        }
        doc.setMargins(2, 2, 2, 2);
        doc.setMarginMirroring(false);
        doc.open();
        try {
            canvas = pw.getDirectContent();
        } catch (Exception e) {
        }
        Paragraph para = new Paragraph();
        para = new Paragraph(new Chunk("   Date : "+date+"", SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        
        Image img;
        img = Image.getInstance("src//Images//Shop-Logo.png");
        int[] logodetails = null;
        try {
            logodetails = db.getLogoDetails();
        } catch (SQLException sQLException) {
        }
        
        img.setAbsolutePosition(200+logodetails[1],780);
        img.scaleAbsolute(logodetails[0], 55);
        doc.add(img);
//        cell.setImage(img);
//        table.setWidths(new int[]{30});
//        cell.setFixedHeight(80);
//        table.addCell(cell);
//        table.set
//        doc.add(table);
        
        PdfPTable  table = new PdfPTable(8);
        
//        Paragraph tabletitle = new Paragraph("List of Invoices");
//        tabletitle.setFont(TimsRomen2);
//        tabletitle.setAlignment(Paragraph.ALIGN_CENTER);
        
//        cell.addElement(tabletitle);
        PdfPCell cell = new PdfPCell(new Paragraph("List of Invoices",tabletitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        BaseColor clr = new BaseColor(126, 126, 126);
        cell.setBackgroundColor(clr);
        cell.setColspan(8);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("InvoiceID",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Supplier",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Date",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Total Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Net Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Discount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Paid Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Gross Amount",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        String CustomerName = null;
        
        for(int i=0,j=0;j<rowcount;j++,i+=8)
        {
            table.addCell(new Paragraph(InvoiceD.get(i),tableHeading));
            CustomerName = db.getSupplierName(InvoiceD.get(i+1));
            table.addCell(new Paragraph(CustomerName,tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+2),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+3),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+6),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+4),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+5),tableHeading));
            table.addCell(new Paragraph(InvoiceD.get(i+7),tableHeading));
        }
        
        
        doc.add( Chunk.NEWLINE );
        doc.add( Chunk.NEWLINE );
        table.setTotalWidth(PageSize.A4.getWidth()-40);
        table.setLockedWidth(true);
        doc.add(table);
        doc.close();
        try {
            pw.close();
        } catch (Exception e) {
        }
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+FolderPath+"//PurchaseInvoiceReport//SupplierInvoice"+date+".pdf");
    }
    
    public void SupplierInvoice(String InvoiceID) throws IOException, BadElementException, DocumentException, SQLException
    {
        FolderPath = CheckFolders("CustomerReport");
        doc = new Document(PageSize.A4);
        try {
            pw = PdfWriter.getInstance(doc, new FileOutputStream(FolderPath+"//CustomerReport//"+InvoiceID+".pdf"));
        } catch (FileNotFoundException | DocumentException fileNotFoundException) {
        }
        doc.setMargins(2, 2, 2, 2);
        doc.setMarginMirroring(false);
        doc.open();
        
        Image img;
        img = Image.getInstance("src//Images//Shop-Logo.png");
        int[] logodetails = null;
        try {
            logodetails = db.getLogoDetails();
        } catch (SQLException sQLException) {
        }
        
        img.setAbsolutePosition(200+logodetails[1],780);
        img.scaleAbsolute(logodetails[0], 55);
        doc.add(img);
        
        BaseColor clr = new BaseColor(228, 50, 44);
        Font InvoiceHeading = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font InvoiceHeading2 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD,BaseColor.CYAN);
        Font SanSerif = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 14, Font.BOLD, clr);
        
        Paragraph para;
        para = new Paragraph(new Chunk("   INVOICE No. "+InvoiceID+"", SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        String date = dateFormat.format(Dt);
        para = new Paragraph(new Chunk("   Date : "+date+"", this.SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        
        //shop details
        String[] shopdetails = new String[6];
        shopdetails = db.ShopsDetail();
        
        String[] SupplierD = db.getSupplierDForBill(InvoiceID);
        
        float[] width = {2,5,2,5};
        PdfPTable  table = new PdfPTable(width);
        
        Font From = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,BaseColor.BLACK);
        Font Content = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font Content2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Paragraph("From :",From));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setRowspan(6);
        cell.setBorder(0);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(shopdetails[0],Content));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("To :",From));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setRowspan(6);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(SupplierD[0],Content));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        table.addCell(cell);
        
        
        for(int i=0;i<6;i++)
        {
            cell = new PdfPCell(new Paragraph(shopdetails[i+1],Content2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(SupplierD[i+1],Content2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);
        }
        
        
        doc.add( Chunk.NEWLINE );
        doc.add( Chunk.NEWLINE );
        table.setTotalWidth(PageSize.A4.getWidth()-40);
        table.setLockedWidth(true);
        doc.add(table);
        
        float[] width2 = {1,5,2,2,2,1,1,2};
        PdfPTable table2 = new PdfPTable(width2);
        
        Font PEHeading = new Font(Font.FontFamily.COURIER, 8, Font.BOLD,BaseColor.WHITE);
        Font PEContent = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font PDContent2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        
        clr = new BaseColor(4, 177, 85);
        String[] PEHeadings = {"Sr.no.","Product","Price","Qty.","SubT.","CGST","SGST","Totals"};
        
        
        for(int i=0;i<PEHeadings.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PEHeadings[i],PEHeading));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(0);
            table2.addCell(cell);
        }
        
        
//        String[] PEContents = { "1","ProductName","120.00","10","1200.00","5","5","1400.00"};
       
        String[] PEContents = db.GetPProductDforPDF(InvoiceID);
        clr = new BaseColor(253, 254, 212);
        
        for(int j=0,a=1;j<PEContents.length;j+=7,a++)
        {
            cell = new PdfPCell(new Paragraph(String.valueOf(a),PEContent));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    //        cell.setBorder(0);
            table2.addCell(cell);
            for(int i=1;i<PEHeadings.length;i++)
            {
                cell = new PdfPCell(new Paragraph(PEContents[(j+(i-1))],PEContent));
                cell.setBackgroundColor(clr);
                cell.setBorder(8);
                cell.setBorderColor(BaseColor.BLACK);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        //        cell.setBorder(0);
                table2.addCell(cell);
            }
        }
        doc.add( Chunk.NEWLINE );
        table2.setTotalWidth(PageSize.A4.getWidth()-40);
        table2.setLockedWidth(true);
        doc.add(table2);
        
        float[] width3 = {1,1,1,1,1,1,1,1,1};
        PdfPTable table3 = new PdfPTable(width3);
        
        Font PPDetail = new Font(Font.FontFamily.COURIER, 8, Font.BOLD,BaseColor.WHITE);
        Font PPDContent = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font PPDContent2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        
        clr = new BaseColor(4, 177, 85);
        String[] PPDetails = {"Net Amount","Total Amount","Discount","Gross Amount","Payment Mode","Paid Amount","Balanced","Pre Due","Total Due"};
        for(int i=0;i<PPDetails.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PPDetails[i],PEHeading));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(0);
            table3.addCell(cell);
        }
        
//        String[] PPDContents = { "23","234","234","234","234","234","234","324","234"};
        clr = new BaseColor(253, 254, 212);
        String[] PPDContents = db.getPPaymentD(InvoiceID);
        for(int i=0;i<PPDContents.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PPDContents[i],PEContent));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //        cell.setBorder(0);
            table3.addCell(cell);
        }
        
        
        doc.add( Chunk.NEWLINE );
        table3.setTotalWidth(PageSize.A4.getWidth()-40);
        table3.setLockedWidth(true);
        doc.add(table3);
        
        Font TermsC = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 10, Font.UNDERLINE, BaseColor.BLACK);
//        para = new Paragraph(new Chunk("Terms and Condition", TermsC));
//        para.setIndentationLeft(10);
//        para.setAlignment(Paragraph.ALIGN_BOTTOM);
//        doc.add(para);
        
        Font Terms = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 10, Font.NORMAL, BaseColor.BLACK);
//        para = new Paragraph(new Chunk("  1. Good's once sold can't be returned", Terms));
//        para.setIndentationLeft(15);
//        para.setAlignment(Paragraph.ALIGN_LEFT);
//        doc.add(para);
//        para = new Paragraph(new Chunk("  2. Please make Cheque/DD in Favor of ABC Company", Terms));
//        para.setAlignment(Paragraph.ALIGN_LEFT);
//        para.setIndentationLeft(15);
//        doc.add(para);
        
        PdfPTable TandC = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Terms and Condition", TermsC));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        TandC.addCell(cell);
        
        String[] TermsConditions = db.Terms_Conditions();
        
        for(int i=0; i<TermsConditions.length; i++) {
            cell = new PdfPCell(new Paragraph((i+1)+". "+TermsConditions[i],Terms));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setIndent(5);
            cell.setBorder(0);
            TandC.addCell(cell);
        }
        
        TandC.setTotalWidth(doc.right(doc.rightMargin()) - doc.left(doc.leftMargin()));
        TandC.setLockedWidth(true);
        try {
            TandC.writeSelectedRows(0, -1, doc.left(doc.leftMargin()), TandC.getTotalHeight() + doc.bottom(doc.bottomMargin()), pw.getDirectContent());
        } catch (Exception e) {
        }
        
        PdfPTable Sign = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Authorised Signatory", Terms));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        Sign.addCell(cell);
        Sign.setTotalWidth(doc.right(doc.rightMargin()) - doc.left(doc.leftMargin()));
        Sign.setLockedWidth(true);
        try {
            Sign.writeSelectedRows(0, -1, doc.left(doc.leftMargin()) - 20, Sign.getTotalHeight() + doc.bottom(doc.bottomMargin()) + 25, pw.getDirectContent());
        } catch (Exception e) {
        }
        
        
        
        
        doc.close();
        try {
            pw.close();
        } catch (Exception e) {
        }
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+FolderPath+"//CustomerReport//"+InvoiceID+".pdf");
    }
    
    public void CustomerInvoice(String InvoiceID) throws IOException, BadElementException, DocumentException, SQLException
    {
        FolderPath = CheckFolders("SupplierReport");
        doc = new Document(PageSize.A4);
        try {
            pw = PdfWriter.getInstance(doc, new FileOutputStream(FolderPath+"//SupplierReport//"+InvoiceID+".pdf"));
        } catch (FileNotFoundException | DocumentException fileNotFoundException) {
        }
        doc.setMargins(2, 2, 2, 2);
        doc.setMarginMirroring(false);
        doc.open();
        
        Image img;
        img = Image.getInstance("src//Images//Shop-Logo.png");
        int[] logodetails = null;
        try {
            logodetails = db.getLogoDetails();
        } catch (SQLException sQLException) {
        }
        
        img.setAbsolutePosition(200+logodetails[1],780);
        img.scaleAbsolute(logodetails[0], 55);
        doc.add(img);
        
        
        BaseColor clr = new BaseColor(228, 50, 44);
        Font InvoiceHeading = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font InvoiceHeading2 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD,BaseColor.CYAN);
        Font SanSerif = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 14, Font.BOLD, clr);
        
        Paragraph para;
        para = new Paragraph(new Chunk("   INVOICE No. "+InvoiceID+"", SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        String date = dateFormat.format(Dt);
        para = new Paragraph(new Chunk("   Date : "+date+"", this.SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        //shop details
        String[] shopdetails = new String[6];
        shopdetails = db.ShopsDetail();
        
        String CustomerD[] = db.getCustomerDForBill(InvoiceID);
        
        float[] width = {2,5,2,5};
        PdfPTable  table = new PdfPTable(width);
        
        Font From = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,BaseColor.BLACK);
        Font Content = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font Content2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Paragraph("From :",From));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setRowspan(6);
        cell.setBorder(0);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(shopdetails[0],Content));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("To :",From));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setRowspan(6);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(CustomerD[0],Content));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        table.addCell(cell);
        
        
        for(int i=0;i<6;i++)
        {
            cell = new PdfPCell(new Paragraph(shopdetails[i+1],Content2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(CustomerD[i+1],Content2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);
        }
        
        
        doc.add( Chunk.NEWLINE );
        doc.add( Chunk.NEWLINE );
        table.setTotalWidth(PageSize.A4.getWidth()-40);
        table.setLockedWidth(true);
        doc.add(table);
        
        float[] width2 = {1,5,2,2,2,1,1,2};
        PdfPTable table2 = new PdfPTable(width2);
        
        Font PEHeading = new Font(Font.FontFamily.COURIER, 8, Font.BOLD,BaseColor.WHITE);
        Font PEContent = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font PDContent2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        
        clr = new BaseColor(4, 177, 85);
        String[] PEHeadings = {"Sr.no.","Product","Price","Qty.","SubT.","CGST","SGST","Totals"};
        for(int i=0;i<PEHeadings.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PEHeadings[i],PEHeading));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(0);
            table2.addCell(cell);
        }
        
//        String[] PEContents = { "1","ProductName","120.00","10","1200.00","5","5","1400.00"};
        clr = new BaseColor(253, 254, 212);
        String[] PEContents = db.GetSProductDforPDF(InvoiceID);
        clr = new BaseColor(253, 254, 212);
        
        for(int j=0,a=1;j<PEContents.length;j+=7,a++)
        {
            cell = new PdfPCell(new Paragraph(String.valueOf(a),PEContent));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    //        cell.setBorder(0);
            table2.addCell(cell);
            for(int i=1;i<PEHeadings.length;i++)
            {
                cell = new PdfPCell(new Paragraph(PEContents[(j+(i-1))],PEContent));
                cell.setBackgroundColor(clr);
                cell.setBorder(8);
                cell.setBorderColor(BaseColor.BLACK);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        //        cell.setBorder(0);
                table2.addCell(cell);
            }
        }
        doc.add( Chunk.NEWLINE );
        table2.setTotalWidth(PageSize.A4.getWidth()-40);
        table2.setLockedWidth(true);
        doc.add(table2);
        
        float[] width3 = {1,1,1,1,1,1,1,1,1};
        PdfPTable table3 = new PdfPTable(width3);
        
        Font PPDetail = new Font(Font.FontFamily.COURIER, 8, Font.BOLD,BaseColor.WHITE);
        Font PPDContent = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font PPDContent2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        
        clr = new BaseColor(4, 177, 85);
        String[] PPDetails = {"Net Amount","Total Amount","Discount","Gross Amount","Payment Mode","Paid Amount","Balanced","Pre Due","Total Due"};
        for(int i=0;i<PPDetails.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PPDetails[i],PEHeading));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(0);
            table3.addCell(cell);
        }
        
//        String[] PPDContents = { "23","234","234","234","234","234","234","324","234"};
        clr = new BaseColor(253, 254, 212);
        
        String[] PPDContents = db.getSPaymentD(InvoiceID);
        for(int i=0;i<PPDetails.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PPDContents[i],PEContent));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //        cell.setBorder(0);
            table3.addCell(cell);
        }
        
        
        doc.add( Chunk.NEWLINE );
        table3.setTotalWidth(PageSize.A4.getWidth()-40);
        table3.setLockedWidth(true);
        doc.add(table3);
        
        Font TermsC = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 10, Font.UNDERLINE, BaseColor.BLACK);
//        para = new Paragraph(new Chunk("Terms and Condition", TermsC));
//        para.setIndentationLeft(10);
//        para.setAlignment(Paragraph.ALIGN_BOTTOM);
//        doc.add(para);
        
        Font Terms = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 10, Font.NORMAL, BaseColor.BLACK);
//        para = new Paragraph(new Chunk("  1. Good's once sold can't be returned", Terms));
//        para.setIndentationLeft(15);
//        para.setAlignment(Paragraph.ALIGN_LEFT);
//        doc.add(para);
//        para = new Paragraph(new Chunk("  2. Please make Cheque/DD in Favor of ABC Company", Terms));
//        para.setAlignment(Paragraph.ALIGN_LEFT);
//        para.setIndentationLeft(15);
//        doc.add(para);
        
        // Adding Terms And Condition
        PdfPTable TandC = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Terms and Condition", TermsC));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        TandC.addCell(cell);
        
        
        String[] TermsConditions = db.Terms_Conditions();
        
        for(int i=0; i<TermsConditions.length; i++) {
            cell = new PdfPCell(new Paragraph((i+1)+". "+TermsConditions[i],Terms));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setIndent(5);
            cell.setBorder(0);
            TandC.addCell(cell);
        }
        
        TandC.setTotalWidth(doc.right(doc.rightMargin()) - doc.left(doc.leftMargin()));
        TandC.setLockedWidth(true);
        try {
            TandC.writeSelectedRows(0, -1, doc.left(doc.leftMargin()), TandC.getTotalHeight() + doc.bottom(doc.bottomMargin()), pw.getDirectContent());
        } catch (Exception e) {
        }
        
        PdfPTable Sign = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Authorised Signatory", Terms));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        Sign.addCell(cell);
        Sign.setTotalWidth(doc.right(doc.rightMargin()) - doc.left(doc.leftMargin()));
        Sign.setLockedWidth(true);
        try {
            Sign.writeSelectedRows(0, -1, doc.left(doc.leftMargin()) - 20, Sign.getTotalHeight() + doc.bottom(doc.bottomMargin()) + 25, pw.getDirectContent());
        } catch (Exception e) {
        }
        
        
        
        
        doc.close();
        try {
            pw.close();
        } catch (Exception e) {
        }
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+FolderPath+"//SupplierReport//"+InvoiceID+".pdf");
    }
    
    public void TransactionDR(String From,String To,int rowcount) throws DocumentException, FileNotFoundException, IOException, SQLException
    {
        doc = new Document(PageSize.A4);
        String date = dateFormat.format(Dt);
        try {
            pw = PdfWriter.getInstance(doc, new FileOutputStream("Transaction"+date+".pdf"));
        } catch (FileNotFoundException fileNotFoundException) {
        } catch (DocumentException documentException) {
        }
        doc.setMargins(2, 2, 2, 2);
        doc.setMarginMirroring(false);
        doc.open();
        
        try {
            canvas = pw.getDirectContent();
        } catch (Exception e) {
        }
        Paragraph para;
        para = new Paragraph(new Chunk("   Date : "+date+"", SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        
        Image img;
        img = Image.getInstance("src//Images//Shop-Logo.png");
        
        int[] logodetails = null;
        try {
            logodetails = db.getLogoDetails();
        } catch (SQLException sQLException) {
        }
        
        img.setAbsolutePosition(200+logodetails[1],780);
        img.scaleAbsolute(logodetails[0], 55);
        doc.add(img);
//        cell.setImage(img);
//        table.setWidths(new int[]{30});
//        cell.setFixedHeight(80);
//        table.addCell(cell);
//        table.set
//        doc.add(table);
        
        PdfPTable  table = new PdfPTable(5);
        
        PdfPCell cell = new PdfPCell(new Paragraph("Transaction Details",tabletitle));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        BaseColor clr = new BaseColor(126, 126, 126);
        cell.setBackgroundColor(clr);
        cell.setColspan(8);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("User Type",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Name",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Given",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Taken",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Date",tableHeading));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        clr = new BaseColor(109, 185, 147);
        cell.setBackgroundColor(clr);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        
        Vector<String> TransactionD = new Vector<String>();
        try {
            TransactionD = db.RTransactionD(From,To);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i=0,j=0;j<rowcount;j++,i+=5)
        {
            table.addCell(new Paragraph(TransactionD.get(i),tableHeading));
            table.addCell(new Paragraph(TransactionD.get(i+1),tableHeading));
            table.addCell(new Paragraph(TransactionD.get(i+2),tableHeading));
            table.addCell(new Paragraph(TransactionD.get(i+3),tableHeading));
            table.addCell(new Paragraph(TransactionD.get(i+4),tableHeading));

        }
        
        
        doc.add( Chunk.NEWLINE );
        doc.add( Chunk.NEWLINE );
        table.setTotalWidth(PageSize.A4.getWidth()-40);
        table.setLockedWidth(true);
        doc.add(table);
        doc.close();
        try {
            pw.close();
        } catch (Exception e) {
        }
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"Transaction"+date+".pdf");
    }
    
    public void TemporaryInvoice(int LogoWidth, int LogoPosition) throws IOException, BadElementException, DocumentException, SQLException
    {
        FolderPath = CheckFolders("TempPDF");
        doc = new Document(PageSize.A4);
        try {
            pw = PdfWriter.getInstance(doc, new FileOutputStream(FolderPath+"//TempPDF//TemporaryInvoice.pdf"));
        } catch (FileNotFoundException | DocumentException fileNotFoundException) {
        }
        doc.setMargins(2, 2, 2, 2);
        doc.setMarginMirroring(false);
        doc.open();
        
        Image img;
        img = Image.getInstance("src//Images//Shop-Logo.png");
        img.setAbsolutePosition(200+LogoPosition,780);
        img.scaleAbsolute(LogoWidth, 55);
        doc.add(img);
        
        BaseColor clr = new BaseColor(228, 50, 44);
        Font InvoiceHeading = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font InvoiceHeading2 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD,BaseColor.CYAN);
        Font SanSerif = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 14, Font.BOLD, clr);
        
        Paragraph para;
        para = new Paragraph(new Chunk("   INVOICE No. Temp Invoice", SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        String date = dateFormat.format(Dt);
        para = new Paragraph(new Chunk("   Date : "+date+"", this.SanSerif));
        para.setAlignment(Paragraph.ALIGN_LEFT);
        doc.add(para);
        
        
        //shop details
        
        
        float[] width = {2,5,2,5};
        PdfPTable  table = new PdfPTable(width);
        
        Font From = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,BaseColor.BLACK);
        Font Content = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font Content2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Paragraph("From :",From));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setRowspan(6);
        cell.setBorder(0);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("OwnerName",Content));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("To :",From));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setRowspan(6);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("CustomerName",Content));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        table.addCell(cell);
        
        
        for(int i=0;i<6;i++)
        {
            cell = new PdfPCell(new Paragraph("Other Details",Content2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Other Details",Content2));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            table.addCell(cell);
        }
        
        
        doc.add( Chunk.NEWLINE );
        doc.add( Chunk.NEWLINE );
        table.setTotalWidth(PageSize.A4.getWidth()-40);
        table.setLockedWidth(true);
        doc.add(table);
        
        float[] width2 = {1,5,2,2,2,1,1,2};
        PdfPTable table2 = new PdfPTable(width2);
        
        Font PEHeading = new Font(Font.FontFamily.COURIER, 8, Font.BOLD,BaseColor.WHITE);
        Font PEContent = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font PDContent2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        
        clr = new BaseColor(4, 177, 85);
        String[] PEHeadings = {"Sr.no.","Product","Price","Qty.","SubT.","CGST","SGST","Totals"};
        
        
        for(int i=0;i<PEHeadings.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PEHeadings[i],PEHeading));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(0);
            table2.addCell(cell);
        }
        
        
//        String[] PEContents = { "1","ProductName","120.00","10","1200.00","5","5","1400.00"};
       
        
        clr = new BaseColor(253, 254, 212);
        
        for(int j=0,a=1;j<8;j+=7,a++)
        {
            cell = new PdfPCell(new Paragraph(String.valueOf(a),PEContent));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    //        cell.setBorder(0);
            table2.addCell(cell);
            for(int i=1;i<PEHeadings.length;i++)
            {
                cell = new PdfPCell(new Paragraph("Data",PEContent));
                cell.setBackgroundColor(clr);
                cell.setBorder(8);
                cell.setBorderColor(BaseColor.BLACK);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        //        cell.setBorder(0);
                table2.addCell(cell);
            }
        }
        doc.add( Chunk.NEWLINE );
        table2.setTotalWidth(PageSize.A4.getWidth()-40);
        table2.setLockedWidth(true);
        doc.add(table2);
        
        float[] width3 = {1,1,1,1,1,1,1,1,1};
        PdfPTable table3 = new PdfPTable(width3);
        
        Font PPDetail = new Font(Font.FontFamily.COURIER, 8, Font.BOLD,BaseColor.WHITE);
        Font PPDContent = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.MAGENTA);
        Font PPDContent2 = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL,BaseColor.BLACK);
        
        clr = new BaseColor(4, 177, 85);
        String[] PPDetails = {"Net Amount","Total Amount","Discount","Gross Amount","Payment Mode","Paid Amount","Balanced","Pre Due","Total Due"};
        for(int i=0;i<PPDetails.length;i++)
        {
            cell = new PdfPCell(new Paragraph(PPDetails[i],PEHeading));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(0);
            table3.addCell(cell);
        }
        
//        String[] PPDContents = { "23","234","234","234","234","234","234","324","234"};
        clr = new BaseColor(253, 254, 212);
        for(int i=0;i<9;i++)
        {
            cell = new PdfPCell(new Paragraph("Data",PEContent));
            cell.setBackgroundColor(clr);
            cell.setBorder(8);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //        cell.setBorder(0);
            table3.addCell(cell);
        }
        
        
        doc.add( Chunk.NEWLINE );
        table3.setTotalWidth(PageSize.A4.getWidth()-40);
        table3.setLockedWidth(true);
        doc.add(table3);
        
        Font TermsC = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 10, Font.UNDERLINE, BaseColor.BLACK);
//        para = new Paragraph(new Chunk("Terms and Condition", TermsC));
//        para.setIndentationLeft(10);
//        para.setAlignment(Paragraph.ALIGN_BOTTOM);
//        doc.add(para);
        
        Font Terms = FontFactory.getFont("./src/Font/SansSerif.ttf",null, BaseFont.EMBEDDED, 10, Font.NORMAL, BaseColor.BLACK);
//        para = new Paragraph(new Chunk("  1. Good's once sold can't be returned", Terms));
//        para.setIndentationLeft(15);
//        para.setAlignment(Paragraph.ALIGN_LEFT);
//        doc.add(para);
//        para = new Paragraph(new Chunk("  2. Please make Cheque/DD in Favor of ABC Company", Terms));
//        para.setAlignment(Paragraph.ALIGN_LEFT);
//        para.setIndentationLeft(15);
//        doc.add(para);
        
        PdfPTable TandC = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Terms and Condition", TermsC));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        TandC.addCell(cell);
        
        String[] TermsConditions = db.Terms_Conditions();
        
        for(int i=0; i<TermsConditions.length; i++) {
            cell = new PdfPCell(new Paragraph((i+1)+". "+TermsConditions[i],Terms));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setIndent(5);
            cell.setBorder(0);
            TandC.addCell(cell);
        }
        
        TandC.setTotalWidth(doc.right(doc.rightMargin()) - doc.left(doc.leftMargin()));
        TandC.setLockedWidth(true);
        try {
            TandC.writeSelectedRows(0, -1, doc.left(doc.leftMargin()), TandC.getTotalHeight() + doc.bottom(doc.bottomMargin()), pw.getDirectContent());
        } catch (Exception e) {
        }
        
        PdfPTable Sign = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Authorised Signatory", Terms));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        Sign.addCell(cell);
        Sign.setTotalWidth(doc.right(doc.rightMargin()) - doc.left(doc.leftMargin()));
        Sign.setLockedWidth(true);
        try {
            Sign.writeSelectedRows(0, -1, doc.left(doc.leftMargin()) - 20, Sign.getTotalHeight() + doc.bottom(doc.bottomMargin()) + 25, pw.getDirectContent());
        } catch (Exception e) {
        }
        
        
        
        
        doc.close();
        try {
            pw.close();
        } catch (Exception e) {
        }
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+FolderPath+"//TempPDF//TemporaryInvoice.pdf");
    }
}
