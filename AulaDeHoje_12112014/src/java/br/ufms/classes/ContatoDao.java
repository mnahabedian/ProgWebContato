/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hercules Sandim
 */
public class ContatoDao {
    
    /**
     *
     * @return
     */
    public static List<Contato> findAll() {
        ConnectionDB conn = new ConnectionDB();
        List<Contato> contatos = new ArrayList<>();
    
        try {
            String sql =
                    "SELECT * "
                    + "FROM contato ";

            PreparedStatement stmt = conn.getConn().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Contato contato = new Contato(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8));
                contatos.add(contato);
            }
        }
        catch (SQLException e) {
            
        }
        finally
        {
            conn.close();
        }
        
        return contatos;
    }

    public static Contato findById(int id) {
        ConnectionDB conn = new ConnectionDB();
        Contato contato = null;
    
        try {
            String sql =
                    "SELECT * "
                    + "FROM contato "
                    + "WHERE id = ?";

            PreparedStatement stmt = conn.getConn().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                contato = new Contato(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8));
            }
        }
        catch (SQLException e) {
            
        }
        finally
        {
            conn.close();
        }
        
        return contato;
    }

    public static void insert(Contato contato)
    {
        ConnectionDB conn = new ConnectionDB();
    
       try {
            String sql =
                    "INSERT INTO contato "
                    + "(nome, email, cel, operadora_cel, cidade, estado, data_nasc) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.getConn().prepareStatement(sql);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getCel());
            stmt.setString(4, contato.getOperadora_cel());
            stmt.setString(5, contato.getCidade());
            stmt.setString(6, contato.getEstado());
            stmt.setTimestamp(7, contato.getData_nasc());
            
            stmt.execute();
        }
        catch (SQLException e) {
            
        }
        finally
        {
            conn.close();
        }
    }

    public static void update(Contato contato)
    {
        ConnectionDB conn = new ConnectionDB();
    
       try {
            String sql =
                    "UPDATE contato SET "
                    + "nome=?, "
                    + "email=?, "
                    + "cel=?, "
                    + "operadora_cel=?, "
                    + "cidade=?, "
                    + "estado=?, "
                    + "data_nasc=? "
                    + "WHERE id=?";

            PreparedStatement stmt = conn.getConn().prepareStatement(sql);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getCel());
            stmt.setString(4, contato.getOperadora_cel());
            stmt.setString(5, contato.getCidade());
            stmt.setString(6, contato.getEstado());
            stmt.setTimestamp(7, contato.getData_nasc());
            stmt.setInt(8, contato.getId());
            
            stmt.execute();
        }
        catch (SQLException e) {
            
        }
        finally
        {
            conn.close();
        }
    }

    public static void delete(Contato contato)
    {
        ConnectionDB conn = new ConnectionDB();
    
       try {
            String sql =
                    "DELETE "
                    + "FROM contato "
                    + "WHERE id=?";

            PreparedStatement stmt = conn.getConn().prepareStatement(sql);
            stmt.setInt(1, contato.getId());
            
            stmt.execute();
        }
        catch (SQLException e) {
            
        }
        finally
        {
            conn.close();
        }
    }

}

