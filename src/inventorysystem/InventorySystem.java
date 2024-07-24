
package inventorysystem;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author user
 */ 
public class InventorySystem extends JFrame{

    /**
     * @param args the command line arguments
     */
    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    Date dt = new Date();
    static Connection con;
    public static void main(String[] args) throws InterruptedException, SQLException {
        try {
            // TODO code application logic here

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(InventorySystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/InventorySystem?user=root&password=root");
        
        DatabaseConnection DB = new DatabaseConnection(con);
//        
//        LoginPage lp = new LoginPage();
//        lp.setSize(1366, 728);
//        lp.setVisible(true);
//        lp.setLocationRelativeTo(null);
//        lp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int days = DB.getTrialDays();
        if(days != 0)
        {
            DashBoard db = null;
            db = new DashBoard("Admin","1");
            db.setSize(1366,728);
            db.setVisible(true);
            db.setExtendedState(db.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            db.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }else {
            
        }
        
    }
    
}
