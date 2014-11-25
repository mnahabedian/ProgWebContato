/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Hercules Sandim
 */
public class UsuarioDao {
    
    public static Usuario getUsuarioByLoginAndPassword(String login, String password)
    {
        Usuario user=null;
        ConnectionDB conn = new ConnectionDB();
    
        try {
            String sql = "SELECT id, nome, tipo FROM usuario WHERE login='"+login+"' "
                    + " and senha='"+password+"'";

            PreparedStatement stmt = conn.getConn().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
    
            if(rs.next())
                user= new Usuario(rs.getInt(1), login, password, rs.getString(2),  rs.getString(3));
            
        }
        catch (SQLException e) {
            
        }
        finally
        {
            conn.close();
        }
        
        return user;

    }
}

