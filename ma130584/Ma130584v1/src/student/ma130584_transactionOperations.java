/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import db.DB;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.TransactionOperations;

/**
 *
 * @author Aleksandar
 */
public class ma130584_transactionOperations implements TransactionOperations{

    public ma130584_transactionOperations() {
    }
    
    

    @Override
    public BigDecimal getBuyerTransactionsAmmount(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select iznos from Transakcija where idKupac = ?";
        String query2 = "select * from Kupac where idKupac = ?";
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        try {
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return new BigDecimal(-1);
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                result = result.add(rs1.getBigDecimal("iznos"));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1);
    }

    @Override
    public BigDecimal getShopTransactionsAmmount(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select iznos from TransakcijaProdavnica where idProdavnica = ?";
        String query2 = "select * from Prodavnica where idProdavnica = ?";
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        try {
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return null;
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                result = result.add(rs1.getBigDecimal("iznos"));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Integer> getTransationsForBuyer(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idTransakcija from Transakcija where idKupac = ?";
        List<Integer> resultList = new ArrayList<>();
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return null;
            while(rs1.next()){
                resultList.add(rs1.getInt("idTransakcija"));
            }
            return resultList;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getTransactionForBuyersOrder(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idStavka from Sadrzi where idPorudzbina = ?";
        String query2 = "select idTransakcija from Transakcija where idStavka = ?";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            rs1.next();
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(i, rs1.getInt("idStavka"));
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return -1;
            rs2.next();
            return rs2.getInt("idTransakcija");
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getTransactionForShopAndOrder(int i, int i1) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Porudzbina where idPorudzbina = ?";
        String query2 = "select idTransakcijaProdavnica from TransakcijaProdavnica where idPorudzbina = ? and idProdavnica = ?";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i);
            ps2.setInt(2, i1);
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return -1;
            rs2.next();
            return rs2.getInt("idTransakcijaProdavnica");
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Integer> getTransationsForShop(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idTransakcijaProdavnica from TransakcijaProdavnica where idProdavnica = ?";
        List<Integer> resultList = new ArrayList<>();
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return null;
            while(rs1.next()){
                resultList.add(rs1.getInt("idTransakcijaProdavnica"));
            }
            return resultList;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Calendar getTimeOfExecution(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select datum from Transakcija where idTransakcija = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return null;
            rs1.next();
            Date date = rs1.getDate("datum");
            Calendar result = Calendar.getInstance();
            result.clear();
            result.setTime(date);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BigDecimal getAmmountThatBuyerPayedForOrder(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idStavka from Sadrzi where idPorudzbina = ?";
        String query2 = "select iznos from Transakcija where idStavka = ?";
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return new BigDecimal(BigInteger.ZERO);
            
            while(rs1.next()){
                PreparedStatement ps2 = connection.prepareStatement(query2);
                ps2.setInt(1, rs1.getInt("idStavka"));
                ResultSet rs2 = ps2.executeQuery();
                while(rs2.next()){
                    result = result.add(rs2.getBigDecimal("iznos"));
                }
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(BigInteger.ZERO);
    }

    @Override
    public BigDecimal getAmmountThatShopRecievedForOrder(int i, int i1) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select iznos from TransakcijaProdavnica where idPorudzbina = ? and idProdavnica = ?";
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ps1.setInt(2, i1);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                result = result.add(rs1.getBigDecimal("iznos"));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new BigDecimal(BigInteger.ZERO);
    }

    @Override
    public BigDecimal getTransactionAmount(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select iznos from Transakcija where idTransakcija = ?";
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                result = result.add(rs1.getBigDecimal("iznos"));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(BigInteger.ZERO);
    }

    @Override
    public BigDecimal getSystemProfit() {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select profitSistema from TransakcijaProdavnica";
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                result = result.add(rs1.getBigDecimal("profitSistema"));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_transactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(BigInteger.ZERO);
    }
    
}
