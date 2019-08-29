/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma130584v1;

import java.math.BigDecimal;
import java.math.BigInteger;
import operations.*;
import org.junit.Test;
import tests.TestHandler;
import tests.TestRunner;

import java.util.Calendar;
import student.ma130584_articleOperations;
import student.ma130584_buyerOperations;
import student.ma130584_cityOperations;
import student.ma130584_generalOperations;
import student.ma130584_orderOperations;
import student.ma130584_shopOperations;
import student.ma130584_transactionOperations;

/**
 *
 * @author Aleksandar
 */
public class studentMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArticleOperations articleOperations = new ma130584_articleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new ma130584_buyerOperations();
        CityOperations cityOperations = new ma130584_cityOperations();
        GeneralOperations generalOperations = new ma130584_generalOperations();
        OrderOperations orderOperations = new ma130584_orderOperations();
        ShopOperations shopOperations = new ma130584_shopOperations();
        TransactionOperations transactionOperations = new ma130584_transactionOperations();

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2010, Calendar.JANUARY, 01);


        Calendar c2 = Calendar.getInstance();
        c2.clear();
        c2.set(2010, Calendar.JANUARY, 01);

        if(c.equals(c2)) System.out.println("jednako");
        else System.out.println("nije jednako");

        TestHandler.createInstance(
                articleOperations,
                buyerOperations,
                cityOperations,
                generalOperations,
                orderOperations,
                shopOperations,
                transactionOperations
        );

        TestRunner.runTests();
    }
    
}
