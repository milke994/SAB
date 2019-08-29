/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.Grad;
import entiteti.Opstina;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operations.DistrictOperations;

/**
 *
 * @author Nemanja
 */
public class dn130496_opstina implements DistrictOperations {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public int insertDistrict(String string, int i, int i1, int i2) {
        int ret = -1;
        Query query = em.createNativeQuery("SELECT * FROM Opstina WHERE naziv ='" + string + "'", Opstina.class);
        List<Opstina> opstine = query.getResultList();
        Query query2 = em.createNativeQuery("SELECT * FROM Grad WHERE idgrad='" + i + "'", Grad.class);
        List<Grad> gradovi = query2.getResultList();

        if (opstine.isEmpty() && !gradovi.isEmpty()) {

            Query query1 = em.createNativeQuery("INSERT INTO [dbo].[Opstina] ([Naziv],[X],[Y],[IDGrad]) VALUES ('" + string + "','" + i1 + "','" + i2 + "','" + gradovi.get(0).getIDGrad() + "')");
            em.getTransaction().begin();
            query1.executeUpdate();
            em.getTransaction().commit();
            opstine = query.getResultList();
            Opstina opstina = opstine.get(0);
            ret = opstina.getIDOpstina();

        }
        return ret;
    }

    @Override
    public int deleteDistricts(String... strings) {
        int ret = 0;
        for (String s : strings) {
            Query query = em.createNativeQuery("SELECT * FROM Opstina WHERE naziv = '" + s + "'", Opstina.class);
            List<Opstina> opstine = query.getResultList();

            if (!opstine.isEmpty()) {
                for (Opstina o : opstine) {
                    em.getTransaction().begin();
                    em.remove(o);
                    em.getTransaction().commit();
                    ret++;
                }
            }
        }

        return ret;

    }

    @Override
    public boolean deleteDistrict(int i) {
        boolean ret = false;

        Query query = em.createNativeQuery("SELECT * FROM Opstina WHERE idopstina = '" + i + "'", Opstina.class);
        List<Opstina> opstine = query.getResultList();

        if (!opstine.isEmpty()) {
            for (Opstina o : opstine) {
                em.getTransaction().begin();
                em.remove(o);
                em.getTransaction().commit();
                ret = true;
            }
        }

        return ret;

    }

    @Override
    public int deleteAllDistrictsFromCity(String string) {
        int ret = 0;
        Query query = em.createNativeQuery("SELECT * FROM Grad WHERE naziv = '" + string + "'", Grad.class);
        List<Grad> gradovi = query.getResultList();
        if (!gradovi.isEmpty()) {

            query = em.createNativeQuery("SELECT * FROM Opstina WHERE idgrad = '" + gradovi.get(0).getIDGrad() + "'", Opstina.class);
            List<Opstina> opstine = query.getResultList();
            if (!opstine.isEmpty()) {
                for (Opstina o : opstine) {
                    em.getTransaction().begin();
                    em.remove(o);
                    em.getTransaction().commit();
                    ret++;
                }
            }
        }
        return ret;
    }

    @Override
    public List<Integer> getAllDistrictsFromCity(int i) {
        List<Integer> ret = new LinkedList<>(); //moze i null da vrati?
        Query query = em.createNativeQuery("SELECT * FROM Opstina WHERE idgrad = '" + i + "'", Opstina.class);
        List<Opstina> opstine = query.getResultList();
        if (!opstine.isEmpty()) {
            for (Opstina o : opstine) {
                ret.add(o.getIDOpstina());
            }

        }
        return ret;
    }

    @Override
    public List<Integer> getAllDistricts() {
        List<Integer> ret = new LinkedList<>();
        Query query = em.createNativeQuery("SELECT * FROM Opstina ", Opstina.class);
        List<Opstina> opstine = query.getResultList();
        if (!opstine.isEmpty()) {
            for (Opstina o : opstine) {
                ret.add(o.getIDOpstina());
            }

        }
        return ret;
    }

}
