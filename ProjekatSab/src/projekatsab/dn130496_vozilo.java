/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.Grad;
import entiteti.Vozilo;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operations.VehicleOperations;

/**
 *
 * @author Nemanja
 */
public class dn130496_vozilo implements VehicleOperations {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public boolean insertVehicle(String string, int i, BigDecimal bd) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Vozilo WHERE regbroj ='" + string + "'", Vozilo.class);
        List<Vozilo> vozila = query.getResultList();

        if (vozila.isEmpty() && (i == 0 || i == 1 || i == 2)) {
            //proveriti tip goriva
            bd = bd.setScale(3, 3);
            query = em.createNativeQuery("INSERT INTO [dbo].[Vozilo] ([RegBroj],[TipGoriva],[Potrosnja]) VALUES ('" + string + "','" + i + "','" + bd + "')");
            em.getTransaction().begin();
            query.executeUpdate();
            em.getTransaction().commit();
            ret = true;

        }
        return ret;
    }

    @Override
    public int deleteVehicles(String... strings) {
        int ret = 0;
        for (String s : strings) {
            Query query = em.createNativeQuery("SELECT * FROM Vozilo WHERE regbroj ='" + s + "'", Vozilo.class);
            List<Vozilo> vozila = query.getResultList();

            if (!vozila.isEmpty()) {
                for (Vozilo v : vozila) {
                    em.getTransaction().begin();
                    em.remove(v);
                    em.getTransaction().commit();
                    ret++;

                }
            }
        }
        return ret;
    }

    @Override
    public List<String> getAllVehichles() {

        Query query = em.createNativeQuery("SELECT * FROM Vozilo ", Vozilo.class);
        List<Vozilo> vozila = query.getResultList();
        List<String> ret = new LinkedList<String>();
        if (!vozila.isEmpty()) {

            for (Vozilo v : vozila) {
                ret.add(v.getRegBroj());
            }

        }
        return ret;
    }

    @Override
    public boolean changeFuelType(String string, int i) {
        Query query = em.createNativeQuery("SELECT * FROM Vozilo WHERE regbroj ='" + string + "'", Vozilo.class);
        List<Vozilo> vozila = query.getResultList();
        boolean ret = false;
        if (!vozila.isEmpty() && (i == 0 || i == 1 || i == 2)) {
            for (Vozilo v : vozila) {
                em.getTransaction().begin();
                v.setTipGoriva(i);

                em.persist(v);
                em.getTransaction().commit();
                ret = true;

            }
        }

        return ret;
    }

    @Override
    public boolean changeConsumption(String string, BigDecimal bd) {
        Query query = em.createNativeQuery("SELECT * FROM Vozilo WHERE regbroj ='" + string + "'", Vozilo.class);
        List<Vozilo> vozila = query.getResultList();
        boolean ret = false;
        if (!vozila.isEmpty()) {
            for (Vozilo v : vozila) {
                em.getTransaction().begin();
                bd = bd.setScale(3, 3);
                v.setPotrosnja(bd);

                em.persist(v);
                em.getTransaction().commit();
                ret = true;

            }
        }

        return ret;
    }

}
