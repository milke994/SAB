/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.*;
import entiteti.Korisnik;
import entiteti.Kurir;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import operations.GeneralOperations;

/**
 *
 * @author Nemanja
 */
public class dn130496_genOp implements GeneralOperations {

    EntityManagerFactory emf;
    EntityManager em;

    public dn130496_genOp() {
        emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
        em = emf.createEntityManager();
    }

    @Override
    public void eraseAll() {

//        Query query = em.createNativeQuery("SELECT * FROM Isporuka ", Isporuka.class);
//        List<Isporuka> isporuke = query.getResultList();
//
//        if (!isporuke.isEmpty()) {
//            for (Isporuka i : isporuke) {
//                em.getTransaction().begin();
//                em.remove(i);
//                em.getTransaction().commit();
//
//            }
//
//        }
//        query = em.createNativeQuery("SELECT * FROM ZahtevKurira ", ZahtevKurira.class);
//        List<ZahtevKurira> zahtevi = query.getResultList();
//
//        if (!zahtevi.isEmpty()) {
//            for (ZahtevKurira z : zahtevi) {
//                em.getTransaction().begin();
//                em.remove(z);
//                em.getTransaction().commit();
//
//            }
//
//        }
//
//        query = em.createNativeQuery("SELECT * FROM Korisnik ", Korisnik.class);
//        List<Korisnik> korisnici = query.getResultList();
//
//        if (!korisnici.isEmpty()) {
//            for (Korisnik k : korisnici) {
//                em.getTransaction().begin();
//                em.remove(k);
//                em.getTransaction().commit();
//
//            }
//
//        }
//        query = em.createNativeQuery("SELECT * FROM Kurir ", Kurir.class);
//        List<Kurir> kuriri = query.getResultList();
//
//        if (!kuriri.isEmpty()) {
//            for (Kurir k : kuriri) {
//                em.getTransaction().begin();
//                em.remove(k);
//                em.getTransaction().commit();
//
//            }
//
//        }
//
//        query = em.createNativeQuery("SELECT * FROM Opstina ", Opstina.class);
//        List<Opstina> opstine = query.getResultList();
//
//        if (!opstine.isEmpty()) {
//            for (Opstina o : opstine) {
//                em.getTransaction().begin();
//                em.remove(o);
//                em.getTransaction().commit();
//
//            }
//
//        }
//        query = em.createNativeQuery("SELECT * FROM Grad ", Grad.class);
//        List<Grad> gradovi = query.getResultList();
//
//        if (!gradovi.isEmpty()) {
//            for (Grad g : gradovi) {
//                em.getTransaction().begin();
//                em.remove(g);
//                em.getTransaction().commit();
//
//            }
//
//        }
//        query = em.createNativeQuery("SELECT * FROM Ponuda ", Ponuda.class);
//        List<Ponuda> ponude = query.getResultList();
//
//        if (!ponude.isEmpty()) {
//            for (Ponuda p : ponude) {
//                em.getTransaction().begin();
//                em.remove(p);
//                em.getTransaction().commit();
//
//            }
//
//        }
//        query = em.createNativeQuery("SELECT * FROM Paket ", Paket.class);
//        List<Paket> paketi = query.getResultList();
//
//        if (!paketi.isEmpty()) {
//            for (Paket p : paketi) {
//                em.getTransaction().begin();
//                em.remove(p);
//                em.getTransaction().commit();
//
//            }
//
//        }
//
//        query = em.createNativeQuery("SELECT * FROM Vozilo ", Vozilo.class);
//        List<Vozilo> vozila = query.getResultList();
//
//        if (!vozila.isEmpty()) {
//            for (Vozilo v : vozila) {
//                em.getTransaction().begin();
//                em.remove(v);
//                em.getTransaction().commit();
//
//            }
//
//        }
//
//        query = em.createNativeQuery("SELECT * FROM Administrator ", Administrator.class);
//        List<Administrator> administratori = query.getResultList();
//
//        if (!administratori.isEmpty()) {
//            for (Administrator a : administratori) {
//                em.getTransaction().begin();
//                em.remove(a);
//                em.getTransaction().commit();
//
//            }
//
//        }
        StoredProcedureQuery query2 = em.createStoredProcedureQuery("eraseAll");

        em.getTransaction().begin();

        query2.execute();
        em.getTransaction().commit();
    }

}
