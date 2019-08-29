/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.Grad;
import entiteti.Korisnik;
import entiteti.Kurir;
import entiteti.Vozilo;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operations.CourierOperations;

/**
 *
 * @author Nemanja
 */
public class dn130496_kurir implements CourierOperations {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public boolean insertCourier(String string, String string1) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();
        Query query1 = em.createNativeQuery("SELECT * FROM Vozilo WHERE regbroj ='" + string1 + "'", Vozilo.class);
        List<Vozilo> vozila = query1.getResultList();

        if (!korisnici.isEmpty() && !vozila.isEmpty()) {
            query1 = em.createNativeQuery("SELECT * FROM Kurir WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", Kurir.class);
            List<Kurir> kuriri = query1.getResultList();
            if (kuriri.isEmpty()) {
                Query query2 = em.createNativeQuery("INSERT INTO [dbo].[Kurir] ([IDKorisnik],[IDVozilo],[Profit],[BrIsporuka],[Status]) VALUES ('" + korisnici.get(0).getIDKorisnik() + "','" + vozila.get(0).getIDVozilo() + "','" + 0 + "','" + 0 + "','" + 0 + "')");
                em.getTransaction().begin();
                query2.executeUpdate();
                em.getTransaction().commit();
                ret = true;
            }

        }

        return ret;
    }

    @Override
    public boolean deleteCourier(String string) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();
        if (!korisnici.isEmpty()) {
            query = em.createNativeQuery("SELECT * FROM Kurir WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", Kurir.class);
            List<Kurir> kuriri = query.getResultList();
            if (!kuriri.isEmpty()) {
                em.getTransaction().begin();
                em.remove(kuriri.get(0));
                em.getTransaction().commit();
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public List<String> getCouriersWithStatus(int i) {
        List<String> ret = new LinkedList<String>();
        Query query = em.createNativeQuery("SELECT * FROM Kurir WHERE status ='" + i + "'", Kurir.class);
        List<Kurir> kuriri = query.getResultList();

        if (!kuriri.isEmpty()) {
            for (Kurir k : kuriri) {
                query = em.createNativeQuery("SELECT * FROM Korisnik WHERE idkorisnik='" + k.getIDKorisnik() + "'", Korisnik.class);
                List<Korisnik> korisnici = query.getResultList();
                ret.add(korisnici.get(0).getKIme());
            }
        }
        return ret;
    }

    @Override
    public List<String> getAllCouriers() {
        List<String> ret = new LinkedList<String>();
        Query query = em.createNativeQuery("SELECT * FROM Kurir ", Kurir.class);
        List<Kurir> kuriri = query.getResultList();

        if (!kuriri.isEmpty()) {
            for (Kurir k : kuriri) {
                query = em.createNativeQuery("SELECT * FROM Korisnik WHERE idkorisnik='" + k.getIDKorisnik() + "'", Korisnik.class);
                List<Korisnik> korisnici = query.getResultList();
                ret.add(korisnici.get(0).getKIme());
                //sortirati
            }

        }

        return ret;
    }

    @Override
    public BigDecimal getAverageCourierProfit(int i) {
//        em.clear();
        BigDecimal ret = new BigDecimal("0"), profit = new BigDecimal("0"), br = new BigDecimal("0");

        Query query = em.createNativeQuery("SELECT * FROM Kurir WHERE brisporuka >='" + i + "'", Kurir.class);
        List<Kurir> kuriri = query.getResultList();

        if (!kuriri.isEmpty()) {

            for (Kurir k : kuriri) {
                em.refresh(k);
                BigDecimal bd = k.getProfit();
                bd.setScale(10, 3);
                profit = profit.add(bd);

                br = br.add(new BigDecimal("1"));
            }
            if (!profit.equals(new BigDecimal("0")) && !br.equals(new BigDecimal("0"))) {

                ret = profit.divide(br);
            }
        }

        return ret;
    }

}
