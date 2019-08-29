/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import operations.ArticleOperations;
import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksandar
 */
public class ma130584_articleOperations implements ArticleOperations{

    public ma130584_articleOperations() {
    }
    

    @Override
    public int createArticle(int i, String string, int i1) {
        int id = 0;
        Connection connection = DB.getInstance().getConnection();
        String query1 = "select * from Prodavnica where idprodavnica = ?";
        String query2 = "select * from Artikal where naziv = ?";
        String query3 = "select * from Oglas where idProdavnica = ? and idArtikal = ?";
        String query4 = "insert into Oglas values(?,?,?,0)";
        
        try {
            
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, i);
            ResultSet rs = preparedStatement.executeQuery();
            
            if(!rs.next()) return -1;
            
            PreparedStatement statement = connection.prepareStatement(query2);
            statement.setString(1, string);
            ResultSet resultSet = statement.executeQuery();
            
            if(!resultSet.next()){
                String insertQuery = "insert into Artikal values(?)";
                PreparedStatement ps = connection.prepareStatement(insertQuery);
                
                ps.setString(1, string);
                ps.executeUpdate();
            }
            ResultSet res = statement.executeQuery();
            while(res.next()){
                id = res.getInt("IdArtikal");
            }
            
            PreparedStatement ps = connection.prepareStatement(query3);
            ps.setInt(1, i);
            ps.setInt(2, id);
            ResultSet rs1 = ps.executeQuery();
            if(rs1.next()) return -1;
            
            PreparedStatement preparedStatement1 = connection.prepareStatement(query4);
            preparedStatement1.setInt(1, i);
            preparedStatement1.setInt(2, id);
            preparedStatement1.setInt(3, i1);
            preparedStatement1.executeUpdate();
            
            ResultSet rs2 = ps.executeQuery();
            while(rs2.next()){
                return rs2.getInt("idOglas");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_articleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
}
