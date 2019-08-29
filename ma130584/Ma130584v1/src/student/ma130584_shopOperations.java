/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.ShopOperations;

/**
 *
 * @author Aleksandar
 */
public class ma130584_shopOperations implements ShopOperations{

    @Override
    public int createShop(String string, String string1) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Prodavnica where naziv = ?";
        String query2 = "select * from Grad where naziv = ?";
        String query3 = "insert into Prodavnica values(?,?,0,0)";
        int idGrad = 0;
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setString(1, string);
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setString(1, string1);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return -1;
            while(rs2.next()){
                idGrad = rs2.getInt("idGrad");
            }
            PreparedStatement ps3 = connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
            ps3.setString(1, string);
            ps3.setInt(2, idGrad);
            ps3.executeUpdate();
            ResultSet rs4 = ps3.getGeneratedKeys();
            while(rs4.next()){
                return rs4.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public int setCity(int i, String string) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Prodavnica where idProdavnica = ?";
        String query2 = "select * from Grad where naziv = ?";
        String query3 = "update Prodavnica set idGrad = ? where idProdavnica = ?";
        int idGrad = 0;
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setString(1, string);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return -1;
            while(rs2.next()){
                idGrad = rs2.getInt("idGrad");
            }
            
            PreparedStatement ps3 = connection.prepareStatement(query3);
            ps3.setInt(1, idGrad);
            ps3.setInt(2, i);
            ps3.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getCity(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idGrad from Prodavnica where idProdavnica = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            while(rs1.next()){
                return rs1.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int setDiscount(int i, int i1) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Prodavnica where idProdavnica = ?";
        String query2 = "update Prodavnica set popust = ? where idProdavnica = ?";
        if(i1 < 0) return -1;
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i1);
            ps2.setInt(2, i);
            ps2.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return -1;
    }

    @Override
    public int increaseArticleCount(int i, int i1) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Oglas where idArtikal = ?";
        String query2 = "update Oglas set stanje = (select stanje from Oglas where idArtikal = ?) + ? where idArtikal = ?";
        String query3 = "select stanje from Oglas where idArtikal = ?";
        
        if(i1 <= 0) return -1;
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i);
            ps2.setInt(2, i1);
            ps2.setInt(3, i);
            ps2.executeUpdate();
            
            PreparedStatement ps3 = connection.prepareStatement(query3);
            ps3.setInt(1, i);
            ResultSet rs3 = ps3.executeQuery();
            int result = -1;
            while(rs3.next()){
                result = rs3.getInt("stanje");
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public List<Integer> getArticles(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idArtikal from Oglas where idProdavnica = ?";
        List<Integer> articles = new ArrayList<>();
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return null;
            
            while(rs1.next()){
                articles.add(rs1.getInt(1));
            }
            return articles;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getDiscount(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select popust from Prodavnica where idProdavnica = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            
            while(rs1.next()){
                return rs1.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getArticleCount(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select stanje from Oglas where idArtikal = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            int count = -1;
            while(rs1.next()){
                count = rs1.getInt("stanje");
            }
            return count;
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_shopOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}
