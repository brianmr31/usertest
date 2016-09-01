/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list.screen.kosong;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author brian
 */
public class connect {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/usertest";
    
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "toor";
    public Connection conn = null;
    public Statement stmt = null;
    
    public connect(){
        
        try{
           //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");

           //STEP 3: Open a connection
           System.out.println("Connecting to database...");
           this.conn = DriverManager.getConnection(DB_URL,USER,PASS);

           //STEP 4: Execute a query
           System.out.println("Creating statement...");
           this.stmt = this.conn.createStatement();
           
           
        }catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }
    }
    
    public List selectUser(){
        List<String> dataTable = new ArrayList<String>() ;
        String[] data =  new String[2];
        int i = 0;
        String sql = "select nama, email from user";
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while(rs.next()){
                data[0] = rs.getString("nama");
                data[1] = rs.getString("email");
                
                //Display values
                System.out.print("ID: " + data[0]);
                System.out.print(", Age: " + data[1]);
                System.out.println();
                dataTable.add(0,data[0]);
                dataTable.add(1,data[1]);
                //data[i][1] = email ;
                i++;
            } } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataTable;
    }
    
    public void insert(String nama ,String hp ,String email ,String alamat){
        int max1 = 0;
        String sqlinsertU = "insert into user values (null,'"+nama+"','"+hp+"','"+email+"');";
        try {
            this.stmt.executeUpdate(sqlinsertU);

        } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("select max(id) from user;");
        } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while(rs.next()){
              max1 = rs.getInt("max(id)");
              System.out.println(max1);
            } } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlinsertA = "insert into alamat values (null,'"+alamat+"','"+String.valueOf(max1)+"')";
        try {
           this.stmt.executeUpdate(sqlinsertA);
        } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List search(String nama){
        List<String> dataTable = new ArrayList<String>() ;
        String[] data =  new String[2];
        int i = 0;
        String sql = "select nama,email from user where nama like '%"+nama+"%';";
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while(rs.next()){
                data[0] = rs.getString("nama");
                data[1] = rs.getString("email");
                
                //Display values
                System.out.print("ID: " + data[0]);
                System.out.print(", Age: " + data[1]);
                System.out.println();
                dataTable.add(0,data[0]);
                dataTable.add(1,data[1]);
                //data[i][1] = email ;
                i++;
            } } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataTable;
    }
    
    public void close(){
           try{
              if(this.stmt!=null)
                 this.stmt.close();
           }catch(SQLException se2){
           }// nothing we can do
           try{
              if(this.conn!=null)
                 this.conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }//end finally try
    }
    
}
