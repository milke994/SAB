/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import com.mysql.jdbc.Statement;
import db.DB;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
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
import operations.GeneralOperations;
import operations.OrderOperations;

/**
 *
 * @author Aleksandar
 */
public class ma130584_orderOperations implements OrderOperations{

    public ma130584_orderOperations() {
    }
    

    @Override
    public int addArticle(int i, int i1, int i2) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Oglas where idArtikal = ?";
        String query2 = "select * from Sadrzi where idPorudzbina = ? and idArtikal = ?";
        String query3 = "insert into Stavka(idProdavnica, idArtikal, idOglas, kolicina, cena) values(?, ?, ?, ?, ?)";
        String query4 = "update Stavka set kolicina = (select kolicina from Stavka where idStavka = ?) + ?,"
                + "cena = (select cena from Stavka where idStavka = ?) + ?";
        String query5 = "insert into Sadrzi(idPorudzbina, idKupac,idStavka, idProdavnica, idArtikal, idOglas) values(?,?,?,?,?,?)";
        String query6 = "select idKupac from Porudzbina where idPorudzbina = ?";
        String query7 = "update Oglas set stanje = (select stanje from Oglas where idArtikal = ?) - ? where idArtikal = ?";
        String query8 = "select popust from Prodavnica where idProdavnica = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i1);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            int count = 0;
            rs1.next();
            count = rs1.getInt("stanje");
            if(count < i2) return -1;
            PreparedStatement ps = connection.prepareStatement(query7);
            ps.setInt(1, i1);
            ps.setInt(2, i2);
            ps.setInt(3, i1);
            ps.executeUpdate();
            
            int idProdavnica = rs1.getInt("idProdavnica");
            PreparedStatement pss = connection.prepareStatement(query8);
            pss.setInt(1, idProdavnica);
            ResultSet rss = pss.executeQuery();
            rss.next();
            float cena = rs1.getFloat("cena") * i2;
            int idOglas = rs1.getInt("idOglas");
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, i);
            ps2.setInt(2, i1);
            ResultSet rs2 = ps2.executeQuery();
            int idStavka = -1;
            while(rs2.next()){
                idStavka = rs2.getInt("idStavka");
            }
            if(idStavka == -1){
                PreparedStatement ps3 = connection.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
                ps3.setInt(1, idProdavnica);
                ps3.setInt(2, i1);
                ps3.setInt(3, idOglas);
                ps3.setInt(4, i2);
                ps3.setFloat(5, cena);
                ps3.executeUpdate();
                ResultSet rs4 = ps3.getGeneratedKeys();
                while(rs4.next()){
                    idStavka = rs4.getInt(1);
                }
                PreparedStatement ps6 = connection.prepareStatement(query6);
                ps6.setInt(1, i);
                ResultSet rs6 = ps6.executeQuery();
                int idKupac = -1;
                while(rs6.next()){
                    idKupac = rs6.getInt("idKupac");
                }
                if(idKupac == -1) return -1;
                PreparedStatement ps5 = connection.prepareStatement(query5);
                ps5.setInt(1, i);
                ps5.setInt(2, idKupac);
                ps5.setInt(3, idStavka);
                ps5.setInt(4, idProdavnica);
                ps5.setInt(5, i1);
                ps5.setInt(6, idOglas);
                ps5.executeUpdate();
            } else {
                PreparedStatement ps4 = connection.prepareStatement(query4);
                ps4.setInt(1, idStavka);
                ps4.setInt(2, i2);
                ps4.setInt(3, idStavka);
                ps4.setFloat(4, cena);
                ps4.executeUpdate();
            }
            return idStavka;
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return -1;
    }

    @Override
    public int removeArticle(int i, int i1) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idStavka from Sadrzi where idPorudzbina = ? and idArtikal = ?";
        String query2 = "delete from Sadrzi where idStavka = ? and idProdavnica = ?";
        String query3 = "delete from Stavka where idStavka = ?";
        String query4 = "update Oglas set stanje = (select stanje from Oglas where idArtikal = ?) + ? where idArtikal = ?";
        String query5 = "select kolicina from Stavka where idStavka = ? and idArtikal = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ps1.setInt(2, i1);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            rs1.next();
            int idStavka = rs1.getInt("idStavka");
            PreparedStatement ps4 = connection.prepareStatement(query5);
            ps4.setInt(1, idStavka);
            ps4.setInt(2, i1);
            ResultSet rs2 = ps4.executeQuery();
            if(!rs2.isBeforeFirst()) return -1;
            rs2.next();
            int kolicina = rs2.getInt("kolicina");
            PreparedStatement ps5 = connection.prepareStatement(query4);
            ps5.setInt(1, i1);
            ps5.setInt(2, kolicina);
            ps5.setInt(3, i1);
            ps5.executeUpdate();
            PreparedStatement ps3 = connection.prepareStatement(query3);
            ps3.setInt(1, idStavka);
            ps3.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, idStavka);
            ps2.setInt(2, i);
            ps2.executeUpdate();
            return 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<Integer> getItems(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idStavka from Sadrzi where idPorudzbina = ?";
        List<Integer> resultList = new ArrayList<>();
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return null;
            int idStavka;
            while(rs1.next()){
                idStavka = rs1.getInt("idStavka");
                resultList.add(idStavka);
            }
            return resultList;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int completeOrder(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Porudzbina where idPorudzbina = ?";
        String query2 = "select * from Kupac where idKupac = ?";
        String query5 = "update Porudzbina set ukupnaCena = ? where idPorudzbina = ?";
        String query3 = "update Kupac set iznos = (select iznos from kupac where idKupac = ?) - ? where idKupac = ?";
        String query4 = "update Porudzbina set stanjePorudzbine = 'sent', datumPorudzbine = ? where idPorudzbina = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            PreparedStatement ps5 = connection.prepareStatement(query5);
            ps5.setBigDecimal(1, this.getPrice(i));
            ps5.setInt(2, i);
            ps5.executeUpdate();
            rs1 = ps1.executeQuery();
            rs1.next();
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, rs1.getInt("idKupac"));
            ResultSet rs2 = ps2.executeQuery();
            if(!rs2.isBeforeFirst()) return -1;
            rs2.next();
            if(rs2.getBigDecimal("iznos").compareTo(rs1.getBigDecimal("ukupnaCena")) < 0){
                return -1;
            }
            PreparedStatement ps3 = connection.prepareStatement(query3);
            ps3.setInt(1, rs1.getInt("idKupac"));
            ps3.setBigDecimal(2, rs1.getBigDecimal("ukupnaCena"));
            ps3.setInt(3, rs1.getInt("idKupac"));
            ps3.executeUpdate();
            
            PreparedStatement ps4 = connection.prepareStatement(query4);
            java.util.Date d = ma130584_generalOperations.c.getTime();
            Date date = new Date(d.getTime());
            ps4.setDate(1, date);
            ps4.setInt(2, i);
            ps4.executeUpdate();
            
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public BigDecimal getPrice(int i){
        Connection connection = DB.getInstance().getConnection();
        String query1 = "{ call SP_FINAL_PRICE (?,?) }";
        try {
            CallableStatement cs1 = connection.prepareCall(query1);
            cs1.setInt(1, i);
            cs1.registerOutParameter(2, java.sql.Types.DOUBLE);
            cs1.execute();
            double rez = cs1.getDouble(2);
            BigDecimal bigDecimal = new BigDecimal(rez);
            return bigDecimal;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public BigDecimal getFinalPrice(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "{ call SP_FINAL_PRICE (?,?) }";
        String query2 = "select StanjePorudzbine from Porudzbina where idPorudzbina = ?";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query2);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return new BigDecimal(-1);
            rs1.next();
            String stanje = rs1.getString("StanjePorudzbine");
            if(stanje.equals("created")){
                return new BigDecimal(-1);
            }
            CallableStatement cs1 = connection.prepareCall(query1);
            cs1.setInt(1, i);
            cs1.registerOutParameter(2, java.sql.Types.DOUBLE);
            cs1.execute();
            double rez = cs1.getDouble(2);
            BigDecimal bigDecimal = new BigDecimal(rez);
            bigDecimal = bigDecimal.multiply(new BigDecimal(1.0));
            bigDecimal.setScale(3);
            return bigDecimal;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new BigDecimal(-1);
    }

    @Override
    public BigDecimal getDiscountSum(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select idStavka from Sadrzi where idPorudzbina = ?";
        String query2 = "select cena from Stavka where idStavka = ?";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            BigDecimal result = new BigDecimal(BigInteger.ZERO);
            while(rs1.next()){
                PreparedStatement ps2 = connection.prepareStatement(query2);
                ps2.setInt(1, rs1.getInt("idStavka"));
                ResultSet rs2 = ps2.executeQuery();
                rs2.next();
                result = result.add(rs2.getBigDecimal("cena"));
            }
            result = result.subtract(this.getPrice(i));
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BigDecimal(-1);
    }

    @Override
    public String getState(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query6 = "select stanjePorudzbine from Porudzbina where idPorudzbina = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query6);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst()) return null;
            rs.next();
            String stanje = rs.getString("stanjePorudzbine");
            return stanje;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Calendar getSentTime(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query6 = "select datumPorudzbine from Porudzbina where idPorudzbina = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query6);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst()) return null;
            rs.next();
            Date date = rs.getDate("datumPorudzbine");
            if(date == null) return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Calendar getRecievedTime(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query6 = "select datumStizanja from Porudzbina where idPorudzbina = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query6);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst()) return null;
            rs.next();
            Date date = rs.getDate("datumStizanja");
            if(date == null) return null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getBuyer(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query6 = "select idKupac from Porudzbina where idPorudzbina = ?";
        
        try {
            PreparedStatement ps1 = connection.prepareStatement(query6);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            rs1.next();
            int idKupac = rs1.getInt("idKupac");
            return idKupac;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int getLocation(int i) {
        Connection connection = DB.getInstance().getConnection();
        String query6 = "select idGrad from Porudzbina where idPorudzbina = ?";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query6);
            ps1.setInt(1, i);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.isBeforeFirst()) return -1;
            rs1.next();
            int idGrad = rs1.getInt("idGrad");
            return idGrad;
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_orderOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}
