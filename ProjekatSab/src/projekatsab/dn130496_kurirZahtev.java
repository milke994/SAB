/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.Korisnik;
import entiteti.Vozilo;
import entiteti.ZahtevKurira;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import operations.CourierOperations;
import operations.CourierRequestOperation;

/**
 *
 * @author Nemanja
 */
public class dn130496_kurirZahtev implements CourierRequestOperation {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public boolean insertCourierRequest(String string, String string1) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();

//id korisnika unique
        if (!korisnici.isEmpty()) {
            query = em.createNativeQuery("SELECT * FROM ZahtevKurira WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", ZahtevKurira.class);
            List<ZahtevKurira> zahtevi = query.getResultList();
            if (zahtevi.isEmpty()) {
                StoredProcedureQuery query2 = em.createStoredProcedureQuery("insertu");
                int idK = korisnici.get(0).getIDKorisnik();

                em.getTransaction().begin();
                query2.registerStoredProcedureParameter("idKorisnik", int.class, ParameterMode.IN);
                query2.registerStoredProcedureParameter("regBroj", String.class, ParameterMode.IN);
                query2.setParameter("idKorisnik", idK);
                query2.setParameter("regBroj", string1);
                query2.execute();
                em.getTransaction().commit();
                ret = true;
            }
        }

        return ret;
    }

    @Override
    public boolean deleteCourierRequest(String string) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();
        if (!korisnici.isEmpty()) {
            query = em.createNativeQuery("SELECT * FROM ZahtevKurira WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", ZahtevKurira.class);
            List<ZahtevKurira> zahtevi = query.getResultList();
            if (!zahtevi.isEmpty()) {
                for (ZahtevKurira z : zahtevi) {
                    em.getTransaction().begin();
                    em.remove(z);
                    em.getTransaction().commit();
                    ret = true;
                }

            }
        }
        return ret;
    }

    @Override
    public boolean changeVehicleInCourierRequest(String string, String string1) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();

        if (!korisnici.isEmpty()) {

            query = em.createNativeQuery("SELECT * FROM ZahtevKurira WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", ZahtevKurira.class);
            List<ZahtevKurira> zahtevi = query.getResultList();
            if (!zahtevi.isEmpty()) {
                for (ZahtevKurira z : zahtevi) {
                    em.getTransaction().begin();
                    z.setRegBroj(string1);
                    em.getTransaction().commit();
                    ret = true;
                }

            }
        }
        return ret;
    }

    @Override
    public List<String> getAllCourierRequests() {
        List<String> ret = new LinkedList<String>();

        Query query = em.createNativeQuery("SELECT * FROM ZahtevKurira ", ZahtevKurira.class);
        List<ZahtevKurira> zahtevi = query.getResultList();

        if (!zahtevi.isEmpty()) {
            for (ZahtevKurira z : zahtevi) {
                query = em.createNativeQuery("SELECT * FROM Korisnik WHERE idkorisnik ='" + z.getIDKorisnik() + "'", Korisnik.class);
                List<Korisnik> korisnici = query.getResultList();
                if (!korisnici.isEmpty()) {
                    ret.add(korisnici.get(0).getKIme());

                }
            }

        }
        return ret;
    }

    @Override
    public boolean grantRequest(String string) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();
        if (!korisnici.isEmpty()) {

            query = em.createNativeQuery("SELECT * FROM ZahtevKurira WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", ZahtevKurira.class);
            List<ZahtevKurira> zahtevi = query.getResultList();
            if (!zahtevi.isEmpty()) {
                query = em.createNativeQuery("SELECT * FROM Vozilo WHERE regbroj ='" + zahtevi.get(0).getRegBroj() + "'", Vozilo.class);
                List<Vozilo> vozila = query.getResultList();
                if (!vozila.isEmpty()) {
                    dn130496_kurir kurir = new dn130496_kurir();
                    kurir.insertCourier(string, zahtevi.get(0).getRegBroj());
                    ret = true;
                }

            }
        }

        return ret;
    }
}
