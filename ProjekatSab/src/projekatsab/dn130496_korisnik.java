/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.Administrator;
import entiteti.Korisnik;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operations.UserOperations;

/**
 *
 * @author Nemanja
 */
public class dn130496_korisnik implements UserOperations {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public boolean insertUser(String string, String string1, String string2, String string3) {
        boolean ret = false;

        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();
        /*|| !Character.isUpperCase(string1.charAt(0)) || !Character.isUpperCase(string2.charAt(0)) || !(proveriPass(string3))  Provera pass*/
        if (!korisnici.isEmpty() || !Character.isUpperCase(string1.charAt(0)) || !Character.isUpperCase(string2.charAt(0)) || !(proveriPass(string3))) {
            ret = false;
        } else {
            Query query1 = em.createNativeQuery("INSERT INTO [dbo].[Korisnik] ([Ime],[Prezime],[KIme],[Sifra],[BrPosl]) VALUES ('" + string1 + "','" + string2 + "','" + string + "','" + string3 + "','" + 0 + "')");
            em.getTransaction().begin();
            int uspeh = query1.executeUpdate();
            em.getTransaction().commit();
            if (uspeh != 0) {
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public int declareAdmin(String string) {
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();
        int ret = 3;
        if (korisnici.isEmpty()) {
            ret = 2;
        } else {
            query = em.createNativeQuery("SELECT * FROM Administrator WHERE  idkorisnik = '" + korisnici.get(0).getIDKorisnik() + "'", Administrator.class);
            List<Administrator> administratori = query.getResultList();

            if (!administratori.isEmpty()) {
                ret = 1;
            } else {
                query = em.createNativeQuery("INSERT INTO [dbo].[Administrator] ([IDKorisnik]) VALUES ('" + korisnici.get(0).getIDKorisnik() + "')");
                em.getTransaction().begin();
                query.executeUpdate();
                em.getTransaction().commit();
                ret = 0;
            }
        }

        return ret;
    }

    @Override
    public Integer getSentPackages(String... strings) {
        Query query;
        Integer ret = null;
        int br = 0, i = -1;

        for (String s : strings) {
            query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + s + "'", Korisnik.class);
            List<Korisnik> korisnici = query.getResultList();
            if (!korisnici.isEmpty()) {
                if (korisnici.get(0).getBrPosl() != null) {
                    br += korisnici.get(0).getBrPosl();
                    i = 0;
                }

            }

        }
        if (i != -1) {
            ret = new Integer(br);
        }
        return ret;
    }

    @Override
    public int deleteUsers(String... strings) {
        int ret = 0;
        Query query;
        for (String s : strings) {
            query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + s + "'", Korisnik.class);
            List<Korisnik> korisnici = query.getResultList();

            if (!korisnici.isEmpty()) {
                for (Korisnik k : korisnici) {
                    em.getTransaction().begin();
                    em.remove(k);
                    em.getTransaction().commit();
                    ret++;

                }
            }
        }

        return ret;
    }

    @Override
    public List<String> getAllUsers() {
        List<String> ret = new LinkedList<String>();
        Query query = em.createNativeQuery("SELECT * FROM Korisnik ", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();

        if (!korisnici.isEmpty()) {

            for (Korisnik k : korisnici) {
                ret.add(k.getKIme());
            }

        }
        return ret;
    }

    public boolean proveriPass(String password) {

        if (password.length() < 8) {
            return false;
        }

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) {
                numCount++;
            } else if (is_Letter(ch)) {
                charCount++;
            } else {
                return false;
            }
        }

        return (charCount >= 1 && numCount >= 1);
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

}
