/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import operations.CityOperations;
import operations.CourierOperations;
import operations.CourierRequestOperation;
import operations.DistrictOperations;
import operations.GeneralOperations;
import operations.PackageOperations;
import operations.UserOperations;
import operations.VehicleOperations;
import org.junit.Assert;
import tests.TestHandler;
import tests.TestRunner;

/**
 *
 * @author Nemanja
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
        EntityManager em = emf.createEntityManager();
        CityOperations cityOperations = new dn130496_grad(); // Change this to your implementation.
        DistrictOperations districtOperations = new dn130496_opstina(); // Do it for all classes.
        CourierOperations courierOperations = new dn130496_kurir(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new dn130496_kurirZahtev();
        GeneralOperations generalOperations = new dn130496_genOp();
        UserOperations userOperations = new dn130496_korisnik();
        VehicleOperations vehicleOperations = new dn130496_vozilo();
        PackageOperations packageOperations = new dn130496_paket(emf, em); //pakett(); //

        TestHandler.createInstance(
                cityOperations,
                courierOperations,
                courierRequestOperation,
                districtOperations,
                generalOperations,
                userOperations,
                vehicleOperations,
                packageOperations);

        TestRunner.runTests();
//        generalOperations.eraseAll();
//
//        String courierLastName = "Ckalja";
//        String courierFirstName = "Pero";
//        String courierUsername = "perkan";
//        String password = "sabi20181";
//
//        System.out.print(userOperations.insertUser(courierUsername, courierFirstName, courierLastName, password) + "\n");
//
//        String licencePlate = "BG323WE";
//        int fuelType = 0;
//        BigDecimal fuelConsumption = new BigDecimal(8.3D);
//
//        System.out.print(vehicleOperations.insertVehicle(licencePlate, fuelType, fuelConsumption) + "\n");
//
//        System.out.print(courierRequestOperation.insertCourierRequest(courierUsername, licencePlate) + "\n");
//
//        System.out.print(courierRequestOperation.grantRequest(courierUsername) + "\n");
//
//        System.out.print(courierOperations.getAllCouriers().contains(courierUsername) + "\n");
//
//        String senderUsername = "masa";
//        String senderFirstName = "Masana";
//        String senderLastName = "Leposava";
//        password = "lepasampasta1";
//
//        System.out.print(userOperations.insertUser(senderUsername, senderFirstName, senderLastName, password) + "\n");
//
//        int cityId = cityOperations.insertCity("Novo Milosevo", "21234");
//
//        int cordXd1 = 10;
//        int cordYd1 = 2;
//
//        int districtIdOne = districtOperations.insertDistrict("Novo Milosevo", cityId, cordXd1, cordYd1);
//
//        int cordXd2 = 2;
//        int cordYd2 = 10;
//
//        int districtIdTwo = districtOperations.insertDistrict("Vojinovica", cityId, cordXd2, cordYd2);
//
//        int type1 = 0;
//        BigDecimal weight1 = new BigDecimal(123);
//
//        int packageId1 = packageOperations.insertPackage(districtIdOne, districtIdTwo, courierUsername, type1, weight1);
//
//        BigDecimal packageOnePrice = getPackagePrice(type1, weight1, euclidean(cordXd1, cordYd1, cordXd2, cordYd2), new BigDecimal(5));
//
//        int offerId = packageOperations.insertTransportOffer(courierUsername, packageId1, new BigDecimal(5));
//        System.out.println(offerId);
//
//        packageOperations.acceptAnOffer(offerId);
//
//        int type2 = 1;
//        BigDecimal weight2 = new BigDecimal(321);
//
//        int packageId2 = packageOperations.insertPackage(districtIdTwo, districtIdOne, courierUsername, type2, weight2);
//
//        BigDecimal packageTwoPrice = getPackagePrice(type2, weight2, euclidean(cordXd1, cordYd1, cordXd2, cordYd2), new BigDecimal(5));
//
//        offerId = packageOperations.insertTransportOffer(courierUsername, packageId2, new BigDecimal(5));
//        packageOperations.acceptAnOffer(offerId);
//
//        int type3 = 1;
//        BigDecimal weight3 = new BigDecimal(222);
//
//        int packageId3 = packageOperations.insertPackage(districtIdTwo, districtIdOne, courierUsername, type3, weight3);
//
//        BigDecimal packageThreePrice = getPackagePrice(type3, weight3, euclidean(cordXd1, cordYd1, cordXd2, cordYd2), new BigDecimal(5));
//
//        offerId = packageOperations.insertTransportOffer(courierUsername, packageId3, new BigDecimal(5));
//        packageOperations.acceptAnOffer(offerId);
//
//        System.out.print(packageOperations.getDeliveryStatus(packageId1) + " treba 1\n");//treba1
//
//        //packageId1
//        System.out.print(packageOperations.driveNextPackage(courierUsername) + "\n");
//        System.out.print(packageId1 + "\n");
//
//        System.out.print(packageOperations.getDeliveryStatus(packageId1) + " treba 3\n");
//
//        System.out.print(packageOperations.getDeliveryStatus(packageId2) + " treba 2 \n");
//
//        System.out.print(packageId2 + " " + packageOperations.driveNextPackage(courierUsername) + "\n");
//
//        System.out.print(packageOperations.getDeliveryStatus(packageId2) + " treba 3 \n");
//
//        System.out.print(packageOperations.getDeliveryStatus(packageId3) + " treba 2 \n");
//
//        System.out.print(packageId3 + " " + packageOperations.driveNextPackage(courierUsername) + "\n");
//
//        System.out.print(packageOperations.getDeliveryStatus(packageId3) + " treba 3 \n");
//
//        BigDecimal gain = packageOnePrice.add(packageTwoPrice).add(packageThreePrice);
//        BigDecimal loss = new BigDecimal(euclidean(cordXd1, cordYd1, cordXd2, cordYd2) * 4.0D * 15.0D).multiply(fuelConsumption);
//
//        System.out.print(gain + "  \n");
//        System.out.print(loss + "  \n");
//
//        BigDecimal actual = courierOperations.getAverageCourierProfit(0);
//        System.out.print(actual + " pros prof \n");
//
//        int novo = gain.subtract(loss).compareTo(actual.multiply(new BigDecimal(1.05D)));
//        System.out.print(novo + " novo \n");
//        int novo2 = gain.subtract(loss).compareTo(actual.multiply(new BigDecimal(0.95D)));
//        System.out.print(novo2 + " novo \n");
    }

    private static double euclidean(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static BigDecimal getPackagePrice(int type, BigDecimal weight, double distance, BigDecimal percentage) {
        percentage = percentage.divide(new BigDecimal(100));
        switch (type) {
            case 0:
                return new BigDecimal(10.0D * distance).multiply(percentage.add(new BigDecimal(1)));
            case 1:
                return new BigDecimal((25.0D + weight.doubleValue() * 100.0D) * distance).multiply(percentage.add(new BigDecimal(1)));
            case 2:
                return new BigDecimal((75.0D + weight.doubleValue() * 600.0D) * distance).multiply(percentage.add(new BigDecimal(1)));
        }
        return null;
//    }
    }
}
