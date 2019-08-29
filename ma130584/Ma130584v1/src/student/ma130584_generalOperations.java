/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.sql.Connection;
import operations.GeneralOperations;
import db.*;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksandar
 */
public class ma130584_generalOperations implements GeneralOperations{
    
    public static Calendar c;

    public ma130584_generalOperations() {
    }
    
    

    @Override
    public void setInitialTime(Calendar clndr) {
        c = clndr;
    }

    @Override
    public Calendar time(int i) {
        c.add(Calendar.DAY_OF_MONTH, i);
        return c;
    }

    @Override
    public Calendar getCurrentTime() {
        return c;
    }

    @Override
    public void eraseAll() {
        Connection connection = DB.getInstance().getConnection();
        String query = "{ call spObrisiPodatke }";
        try {
            CallableStatement cs = connection.prepareCall(query);
            cs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ma130584_generalOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
