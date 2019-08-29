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
import operations.CityOperations;

/**
 *
 * @author Aleksandar
 */
public class ma130584_cityOperations implements CityOperations{

    public ma130584_cityOperations() {
    }

    @Override
    public int createCity(String string) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Grad where naziv = ?";
        String query2 = "insert into Grad values(?)";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setString(1, string);
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, string);
            ps2.executeUpdate();
            ResultSet resultSet = ps2.getGeneratedKeys();
            while(resultSet.next()){
                return resultSet.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_cityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Integer> getCities() {
        Connection connection = DB.getInstance().getConnection();
        String query = "select * from Grad";
        List<Integer> cities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                cities.add(rs.getInt("idGrad"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_cityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return cities;
    }

    @Override
    public int connectCities(int i, int i1, int i2) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Grad where idGrad = ?";
        String query2 = "select * from Linija where (idGrad1 = ? and idGrad2 = ?) or (idGrad1 = ? and idGrad2 = ?)";
        String query3 = "insert into Linija values(?,?,?)";
        
        try {
            
            if(i == i1) return -1;
            
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.next()) return -1;
            
            PreparedStatement ps2 = connection.prepareStatement(query1);
            ps2.setInt(1, i1);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.next()) return -1;
            
            PreparedStatement ps3 = connection.prepareStatement(query2);
            ps3.setInt(1, i);
            ps3.setInt(2, i1);
            ps3.setInt(3, i1);
            ps3.setInt(4, i);
            ResultSet rs3 = ps3.executeQuery();
            if(rs3.next()) return -1;
            
            PreparedStatement ps5 = connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
            ps5.setInt(1, i);
            ps5.setInt(2, i1);
            ps5.setInt(3, i2);
            ps5.executeUpdate();
            ResultSet rs5 = ps5.getGeneratedKeys();
            while(rs5.next()){
                return rs5.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_cityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Integer> getConnectedCities(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Linija where idGrad1 = ? or idGrad2 = ?";
        List<Integer> cities = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query1);
            ps.setInt(1, i);
            ps.setInt(2, i);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getInt("idGrad1") == i){
                    cities.add(rs.getInt("idGrad2"));
                } else {
                    cities.add(rs.getInt("idGrad1"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_cityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cities;
    }

    @Override
    public List<Integer> getShops(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Grad where idGrad = ?";
        String query2 = "select * from Prodavnica where idGrad = ?";
        List<Integer> shops = new ArrayList<>();
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.next()) return null;
            
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return null;
            while(rs2.next()){
                shops.add(rs2.getInt("idProdavnica"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_cityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return shops;
    }
    
}
