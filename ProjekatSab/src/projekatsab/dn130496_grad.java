
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.Grad;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operations.CityOperations;

/**
 *
 * @author Nemanja
 */
public class dn130496_grad implements CityOperations {

    EntityManagerFactory emf;
    EntityManager em;

    public dn130496_grad() {
        emf = Persistence.createEntityManagerFactory("ProjekatSabPU");
        em = emf.createEntityManager();
    }

    @Override
    public int insertCity(String string, String string1) {
        int ret = -1;
        Query query = em.createNativeQuery("SELECT * FROM Grad WHERE naziv ='" + string + "' OR postbr = '" + string1 + "'", Grad.class);
        List<Grad> gradovi = query.getResultList();

        if (gradovi.isEmpty()) {

            Query query1 = em.createNativeQuery("INSERT INTO [dbo].[Grad] ([Naziv],[PostBr]) VALUES ('" + string + "','" + string1 + "') ");

            em.getTransaction().begin();
            query1.executeUpdate();
            em.getTransaction().commit();
            gradovi = query.getResultList();
            ret = gradovi.get(0).getIDGrad();
        }
        return ret;

    }

    @Override
    public int deleteCity(String... strings) {
        int br = 0;
        for (String s : strings) {
            Query query = em.createNativeQuery("SELECT * FROM Grad WHERE naziv ='" + s + "'", Grad.class);
            List<Grad> gradovi = query.getResultList();

            if (!gradovi.isEmpty()) {
                for (Grad g : gradovi) {
                    em.getTransaction().begin();
                    em.remove(g);
                    em.getTransaction().commit();
                    br++;

                }
            }
        }

        return br;
    }

    @Override
    public boolean deleteCity(int i) {
        Query query = em.createNativeQuery("SELECT * FROM Grad WHERE idgrad='" + i + "'", Grad.class);
        List<Grad> gradovi = query.getResultList();
        boolean ret = false;
        if (!gradovi.isEmpty()) {
            for (Grad g : gradovi) {
                em.getTransaction().begin();
                em.remove(g);
                em.getTransaction().commit();

            }
            ret = true;
        }
        return ret;

    }

    @Override
    public List<Integer> getAllCities() {
        Query query = em.createNativeQuery("SELECT * FROM Grad ", Grad.class);
        List<Grad> gradovi = query.getResultList();
        List<Integer> ret = new LinkedList<Integer>();;
        if (!gradovi.isEmpty()) {

            for (Grad g : gradovi) {
                ret.add(g.getIDGrad());
            }

        }
        return ret;
    }

}
