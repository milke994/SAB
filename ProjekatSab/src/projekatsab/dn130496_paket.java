/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import entiteti.Isporuka;
import entiteti.Korisnik;
import entiteti.Kurir;
import entiteti.Opstina;
import entiteti.Paket;
import entiteti.Ponuda;
import java.math.BigDecimal;

import java.util.ArrayList;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operations.PackageOperations;
import operations.PackageOperations.Pair;

/**
 *
 * @author Nemanja
 */
public class dn130496_paket implements PackageOperations {

    EntityManagerFactory emf; //= Persistence.createEntityManagerFactory("ProjekatSabPU")
    EntityManager em; //= emf.createEntityManager();

    public dn130496_paket(EntityManagerFactory emf, EntityManager em) {
        this.em = em;
        this.emf = emf;
    }

    @Override
    public int insertPackage(int i, int i1, String string, int i2, BigDecimal bd) {
        int ret = -1;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();

        query = em.createNativeQuery("SELECT * FROM Opstina WHERE idopstina ='" + i + "'", Opstina.class);
        List<Opstina> opstineP = query.getResultList();
        query = em.createNativeQuery("SELECT * FROM Opstina WHERE idopstina ='" + i1 + "'", Opstina.class);
        List<Opstina> opstineD = query.getResultList();

        if (!korisnici.isEmpty() && !opstineP.isEmpty() && !opstineD.isEmpty() && (i2 == 0 || i2 == 1 || i2 == 2)) {
            bd = bd.setScale(3, 3);

            Paket paket = new Paket();
            paket.setIDKorisnik(korisnici.get(0));
            paket.setODostave(opstineD.get(0));
            paket.setOPreuzimanja(opstineP.get(0));
            paket.setTip(i2);
            paket.setTezina(bd);
            em.getTransaction().begin();
            em.persist(paket);
            em.flush();
            em.getTransaction().commit();

            ret = paket.getIDPaketa();
        }
        return ret;

    }

    @Override
    public int insertTransportOffer(String string, int i, BigDecimal bd) {
        int ret = -1;
        Query q = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = q.getResultList();

        if (!korisnici.isEmpty()) {

            Query query = em.createNativeQuery("SELECT * FROM Kurir WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", Kurir.class);
            List<Kurir> kuriri = query.getResultList();

            query = em.createNativeQuery("SELECT * FROM Paket WHERE idpaketa ='" + i + "'", Paket.class);
            List<Paket> paketi = query.getResultList();

            if (!kuriri.isEmpty() && !paketi.isEmpty()) {
                if (bd.equals(null)) {
                    bd = new BigDecimal("10");
                }
                bd = bd.setScale(3, 3);
                Ponuda ponuda = new Ponuda();
                ponuda.setIDKurir(kuriri.get(0));
                ponuda.setIDPaketa(paketi.get(0));
                ponuda.setProcenat(bd);
                em.getTransaction().begin();
                em.persist(ponuda);
                em.flush();
                em.getTransaction().commit();
                ret = ponuda.getIDPonuda();
            }
        }
        return ret;
    }

    @Override
    public boolean acceptAnOffer(int i) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Ponuda WHERE idponuda ='" + i + "'", Ponuda.class);
        List<Ponuda> ponude = query.getResultList();

        if (!ponude.isEmpty()) {
            query = em.createNativeQuery("SELECT * FROM Ponuda WHERE idponuda<>'" + i + "' AND idpaketa='" + ponude.get(0).getIDPaketa().getIDPaketa() + "'", Ponuda.class);
            List<Ponuda> zaBrisanje = query.getResultList();
            //triger obrisati sve ponude za odredjeni paket
            if (!zaBrisanje.isEmpty()) {

                for (Ponuda p : zaBrisanje) {
                    em.getTransaction().begin();
                    em.remove(p);
                    em.getTransaction().commit();

                }
            }
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);

            Isporuka isporuka = new Isporuka();
            isporuka.setIDPonuda(ponude.get(0));
            isporuka.setStatus(1);
            isporuka.setIDKurira(ponude.get(0).getIDKurir());
            isporuka.setIDPaketa(ponude.get(0).getIDPaketa());
            isporuka.setVreme(date);
            em.getTransaction().begin();
            em.persist(isporuka);
            em.getTransaction().commit();
            ret = true;
        }
        return ret;
    }

    @Override
    public List<Integer> getAllOffers() {
        List<Integer> ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Ponuda ", Ponuda.class);
        List<Ponuda> ponude = query.getResultList();

        if (!ponude.isEmpty()) {
            ret = new LinkedList<>();
            for (Ponuda p : ponude) {
                ret.add(p.getIDPonuda());
            }

        }
        return ret;
    }

    @Override
    public List<Pair<Integer, BigDecimal>> getAllOffersForPackage(int i) {

        List<Pair<Integer, BigDecimal>> ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Ponuda WHERE idpaketa='" + i + "'", Ponuda.class);
        List<Ponuda> ponude = query.getResultList();

        if (!ponude.isEmpty()) {

            ret = new ArrayList<>();

            for (Ponuda p : ponude) {

                ret.add(new dn130496_pairImpl(p.getIDPonuda(), p.getProcenat()));
            }

        }
        return ret;

    }

    @Override
    public boolean deletePackage(int i) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Paket WHERE idpaketa='" + i + "'", Paket.class);
        List<Paket> paketi = query.getResultList();
        Query query1 = em.createNativeQuery("SELECT * FROM Isporuka WHERE idpaketa='" + i + "'", Isporuka.class);
        List<Isporuka> isporuke = query1.getResultList();
        Query query3 = em.createNativeQuery("SELECT * FROM Ponuda WHERE idpaketa='" + i + "'", Ponuda.class);
        List<Ponuda> ponude = query3.getResultList();
        if (!paketi.isEmpty()) {
            if (!isporuke.isEmpty()) {
                em.getTransaction().begin();
                em.remove(isporuke.get(0));
                em.getTransaction().commit();
            }
            if (!ponude.isEmpty()) {
                em.getTransaction().begin();
                em.remove(ponude.get(0));
                em.getTransaction().commit();
            }
            em.getTransaction().begin();
            em.remove(paketi.get(0));
            em.getTransaction().commit();
            ret = true;
        }
        return ret;

    }

    @Override
    public boolean changeWeight(int i, BigDecimal bd) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Paket WHERE idpaketa ='" + i + "'", Paket.class);
        List<Paket> paketi = query.getResultList();
        if (!paketi.isEmpty()) {
            Paket p = paketi.get(0);
            em.getTransaction().begin();
            bd = bd.setScale(3, 3);
            p.setTezina(bd);
            em.persist(p);
            em.getTransaction().commit();
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean changeType(int i, int i1) {
        boolean ret = false;
        Query query = em.createNativeQuery("SELECT * FROM Paket WHERE idpaketa ='" + i + "'", Paket.class);
        List<Paket> paketi = query.getResultList();
        if (!paketi.isEmpty() && (i1 == 0 || i == 1 || i == 2)) {
            Paket p = paketi.get(0);
            em.getTransaction().begin();
            p.setTip(i1);
            em.persist(p);
            em.getTransaction().commit();
            ret = true;
        }
        return ret;
    }

    @Override
    public Integer getDeliveryStatus(int i) {
        Integer ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idpaketa ='" + i + "'", Isporuka.class);
        List<Isporuka> isporuke = query.getResultList();

        if (!isporuke.isEmpty()) {
            ret = isporuke.get(0).getStatus();
        }

        return ret;
    }

    @Override
    public BigDecimal getPriceOfDelivery(int i) {
        BigDecimal ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idpaketa ='" + i + "'", Isporuka.class);
        List<Isporuka> isporuke = query.getResultList();

        if (!isporuke.isEmpty()) {
            ret = isporuke.get(0).getCena();
        }

        return ret;
    }

    @Override
    public Date getAcceptanceTime(int i) {
        Date ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idpaketa ='" + i + "'", Isporuka.class);
        List<Isporuka> isporuke = query.getResultList();

        if (!isporuke.isEmpty()) {

            ret = (Date) isporuke.get(0).getVreme();
        }

        return ret;
    }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int i) {
        List<Integer> ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Paket WHERE tip ='" + i + "'", Paket.class);
        List<Paket> paketi = query.getResultList();
        if (!paketi.isEmpty()) {
            ret = new LinkedList<>();
            for (Paket p : paketi) {
                ret.add(p.getIDPaketa());
            }

        }
        return ret;

    }

    @Override
    public List<Integer> getAllPackages() {
        List<Integer> ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Paket ", Paket.class);
        List<Paket> paketi = query.getResultList();
        if (!paketi.isEmpty()) {
            ret = new LinkedList<>();
            for (Paket p : paketi) {
                ret.add(p.getIDPaketa());
            }

        }
        return ret;
    }

    @Override
    public List<Integer> getDrive(String string) {
        List<Integer> ret = null;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();

        if (!korisnici.isEmpty()) {
            query = em.createNativeQuery("SELECT * FROM Kurir WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", Kurir.class);
            List<Kurir> kuriri = query.getResultList();
            if (!kuriri.isEmpty()) {
                query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idkurira = '" + kuriri.get(0).getIDKorisnik() + "' AND status=" + 2 + "'", Isporuka.class);
                List<Isporuka> isporuke = query.getResultList();

                for (Isporuka i : isporuke) {
                    ret.add(i.getIDPaketa().getIDPaketa());
                }
            }

        }

        return ret;
    }

    @Override
    public int driveNextPackage(String string) {
        int ret = -2;
        boolean vozi = false;
        Query query = em.createNativeQuery("SELECT * FROM Korisnik WHERE kime ='" + string + "'", Korisnik.class);
        List<Korisnik> korisnici = query.getResultList();
        if (!korisnici.isEmpty()) {
            query = em.createNativeQuery("SELECT * FROM Kurir WHERE idkorisnik ='" + korisnici.get(0).getIDKorisnik() + "'", Kurir.class);
            List<Kurir> kuriri = query.getResultList();

            if (!kuriri.isEmpty()) {
                //ne vozi
                if (kuriri.get(0).getStatus() == 0) {
                    query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idkurira = '" + kuriri.get(0).getIDKorisnik() + "' AND status='" + 1 + "'", Isporuka.class);
                    List<Isporuka> isporuke = query.getResultList();
                    if (!isporuke.isEmpty()) {
                        em.getTransaction().begin();
                        Isporuka isporuka = isporuke.get(0);
                        Paket p = isporuka.getIDPaketa();
                        double distanca = distanca(p.getOPreuzimanja().getX().intValue(), p.getODostave().getX().intValue(), p.getOPreuzimanja().getY().intValue(), p.getODostave().getY().intValue());

                        isporuka.setStatus(3);
                        isporuka.setCena(dohCenu(p.getTip(), p.getTezina(), distanca, isporuka.getIDPonuda().getProcenat()).setScale(10, 3));
                        Kurir kurir = kuriri.get(0);
                        kurir.setStatus(1);
                        int i = kurir.getBrIsporuka();
                        i++;
                        kurir.setBrIsporuka(i);

                        em.persist(kurir);
                        em.persist(isporuka);

                        em.getTransaction().commit();

                        ret = isporuka.getIDPaketa().getIDPaketa();

                        query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idkurira = '" + kuriri.get(0).getIDKorisnik() + "' AND status='" + 1 + "'", Isporuka.class);
                        isporuke = query.getResultList();
                        if (!isporuke.isEmpty()) {
                            em.getTransaction().begin();
                            isporuka = isporuke.get(0);

                            isporuka.setStatus(2);
                            em.persist(isporuka);
                            em.getTransaction().commit();
                        } else {
                            vozi = true;
                        }

                    } else {
                        ret = -1;
                    }

                }//vozi trenutno
                else {
                    query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idkurira = '" + kuriri.get(0).getIDKorisnik() + "' AND status='" + 2 + "'", Isporuka.class);
                    List<Isporuka> isporuke = query.getResultList();
                    if (!isporuke.isEmpty()) {
                        em.getTransaction().begin();
                        Isporuka isporuka = isporuke.get(0);

                        Paket p = isporuka.getIDPaketa();
                        double distanca = distanca(p.getOPreuzimanja().getX().intValue(), p.getODostave().getX().intValue(), p.getOPreuzimanja().getY().intValue(), p.getODostave().getY().intValue());

                        isporuka.setStatus(3);
                        isporuka.setCena(dohCenu(p.getTip(), p.getTezina(), distanca, isporuka.getIDPonuda().getProcenat()).setScale(10, 3));
                        Kurir kurir = kuriri.get(0);
                        int i = kurir.getBrIsporuka();
                        i++;
                        kurir.setBrIsporuka(i);

                        em.persist(kurir);
                        em.persist(isporuka);

                        em.getTransaction().commit();

                        ret = isporuka.getIDPaketa().getIDPaketa();

                        query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idkurira = '" + kuriri.get(0).getIDKorisnik() + "' AND status='" + 1 + "'", Isporuka.class);
                        isporuke = query.getResultList();
                        if (!isporuke.isEmpty()) {
                            isporuka = isporuke.get(0);
                            em.getTransaction().begin();
                            isporuka.setStatus(2);
                            em.persist(isporuka);
                            em.getTransaction().commit();
                        } else {
                            vozi = true;

                        }
                    } else {
                        ret = -1;
                        vozi = true;
                    }
                }
                if (vozi) {
                    query = em.createNativeQuery("SELECT * FROM Isporuka WHERE idkurira = '" + kuriri.get(0).getIDKorisnik() + "' AND status='" + 3 + "'", Isporuka.class);
                    List<Isporuka> isporuke = query.getResultList();
                    if (!isporuke.isEmpty()) {

                        double profit = 0;
                        int cenaGoriva = 0;
                        for (Isporuka i : isporuke) {
                            switch (i.getIDKurira().getIDVozilo().getTipGoriva()) {
                                case 0:
                                    cenaGoriva = 15;
                                    break;
                                case 1:
                                    cenaGoriva = 32;
                                    break;
                                case 2:
                                    cenaGoriva = 36;
                                    break;
                            }

                            double rastojanje = distanca(i.getIDPaketa().getOPreuzimanja().getX().intValue(), i.getIDPaketa().getOPreuzimanja().getY().intValue(), i.getIDPaketa().getODostave().getX().intValue(), i.getIDPaketa().getODostave().getY().intValue());
                            double potrosnja = cenaGoriva * rastojanje * i.getIDKurira().getIDVozilo().getPotrosnja().doubleValue();
                            profit = profit + i.getCena().doubleValue() - potrosnja;

                        }
                        em.getTransaction().begin();
                        Kurir kurir = kuriri.get(0);
                        BigDecimal bd = kurir.getProfit();
                        bd = bd.add(new BigDecimal(profit));
                        kurir.setProfit(bd.setScale(10, 3));
                        kurir.setStatus(0);

                        em.persist(kurir);

                        em.getTransaction().commit();
                        em.clear();
                    }

                }
            }
        }
        return ret;
    }

    private double distanca(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static BigDecimal dohCenu(int type, BigDecimal weight, double distance, BigDecimal percentage) {
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

    }

}
