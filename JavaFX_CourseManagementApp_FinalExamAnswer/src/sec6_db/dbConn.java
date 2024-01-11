package sec6_db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.logging.*;

import java.sql.Connection;
import java.sql.DriverManager;



/**
 *
 * @author NorhanNasr
 */
public class dbConn {
     public static Connection DBConnection() {
        
         Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
             //cmd :SQLPLUS / AS SYSDBA
             // ALTER USER HR ACCOUNT UNLOCK;
            conn = DriverManager.getConnection("jdbc:oracle:thin:ROOT/2004@localhost:1521/XE");

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
     
        return conn;
    
}
}
