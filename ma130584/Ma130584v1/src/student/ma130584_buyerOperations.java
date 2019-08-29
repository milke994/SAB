/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import db.DB;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.BuyerOperations;

/**
 *
 * @author Aleksandar
 */
public class ma130584_buyerOperations implements BuyerOperations{

    public ma130584_buyerOperations() {
    }
    
    

    @Override
    public int createBuyer(String string, int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Grad where idGrad = ?";
        String query3 = "select * from Kupac where naziv = ?";
        String query2 = "insert into Kupac values(?,0,?)";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            
            PreparedStatement ps3 = connection.prepareStatement(query3);
            ps3.setString(1, string);
            ResultSet rs3 = ps3.executeQuery();
            if(rs3.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, string);
            ps2.setInt(2, i);
            ps2.executeUpdate();
            ResultSet rs2 = ps2.getGeneratedKeys();
            while(rs2.next()){
                return rs2.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_buyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int setCity(int i, int i1) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Kupac where idKupac = ?";
        String query2 = "select * from Grad where idGrad = ?";
        String query3 = "update Kupac set idGrad = ? where idKupac = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i1);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return -1;
            
            PreparedStatement ps3 = connection.prepareStatement(query3);
            ps3.setInt(1, i1);
            ps3.setInt(2, i);
            ps3.executeUpdate();
            return 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_buyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    @Override
    public int getCity(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Kupac where idKupac = ?";
        String query2 = "select idGrad from Kupac where idKupac = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs = ps1.executeQuery();
            if(!rs.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i);
            ResultSet rs2 = ps2.executeQuery();
            while(rs2.next()){
                return rs2.getInt("idGrad");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_buyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public BigDecimal increaseCredit(int i, BigDecimal bd) {
        Connection connection = DB.getInstance().getConnection();
        BigDecimal credit = bd;
        String query1 = "select * from Kupac where idKupac = ?";
        String query2 = "update Kupac set iznos = ? where idKupac = ?";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()){
                credit = credit.add(rs1.getBigDecimal("iznos"));
            }
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setBigDecimal(1, credit);
            ps2.setInt(2, i);
            ps2.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_buyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return credit;
    }

    @Override
    public int createOrder(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Kupac where idKupac = ?";
        String query2 = "insert into Porudzbina values(?, 0, null, 'created', null, null)";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            ps2.setInt(1, i);
            ps2.executeUpdate();
            ResultSet rs = ps2.getGeneratedKeys();
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_buyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return -1;
    }

    @Override
    public List<Integer> getOrders(int i) {
        Connection connection = DB.getInstance().getConnection();
        List<Integer> orders = new ArrayList<>();
        String query1 = "select idPorudzbina from Porudzbina where idKupac = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs = ps1.executeQuery();
            
            while(rs.next()){
                orders.add(rs.getInt("idPorudzbina"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_buyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }

    @Override
    public BigDecimal getCredit(int i) {
        Connection connection = DB.getInstance().getConnection();
        BigDecimal bd = new BigDecimal(-1);
        String query1 = "select iznos from Kupac where idKupac = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()) bd = rs1.getBigDecimal("iznos");
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_buyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bd;
    }
    
}
