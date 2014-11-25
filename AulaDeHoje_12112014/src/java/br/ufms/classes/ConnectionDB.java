/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hercules Sandim
 */
public class ConnectionDB {
    private Connection conn;
    
    public ConnectionDB() {
        try
        {
//            Class.forName("org.postgresql.Driver");
//            conn = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost/app_simples","postgres","hercules");
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/contato","joshua","j0sh42");

        }
        catch(ClassNotFoundException | SQLException ex)
        {            
            ex.printStackTrace();
        }
    
    }

    public Connection getConn() {
        return conn;
    }
    
    public void close()
    {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
