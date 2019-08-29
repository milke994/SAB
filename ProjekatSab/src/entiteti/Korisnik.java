/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Korisnik")
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k")
    , @NamedQuery(name = "Korisnik.findByIDKorisnik", query = "SELECT k FROM Korisnik k WHERE k.iDKorisnik = :iDKorisnik")
    , @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime")
    , @NamedQuery(name = "Korisnik.findByPrezime", query = "SELECT k FROM Korisnik k WHERE k.prezime = :prezime")
    , @NamedQuery(name = "Korisnik.findByKIme", query = "SELECT k FROM Korisnik k WHERE k.kIme = :kIme")
    , @NamedQuery(name = "Korisnik.findBySifra", query = "SELECT k FROM Korisnik k WHERE k.sifra = :sifra")
    , @NamedQuery(name = "Korisnik.findByJmbg", query = "SELECT k FROM Korisnik k WHERE k.jmbg = :jmbg")
    , @NamedQuery(name = "Korisnik.findByBrPosl", query = "SELECT k FROM Korisnik k WHERE k.brPosl = :brPosl")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKorisnik")
    private Integer iDKorisnik;
    @Size(max = 100)
    @Column(name = "Ime")
    private String ime;
    @Size(max = 100)
    @Column(name = "Prezime")
    private String prezime;
    @Size(max = 100)
    @Column(name = "KIme")
    private String kIme;
    @Size(max = 100)
    @Column(name = "Sifra")
    private String sifra;
    @Size(max = 13)
    @Column(name = "JMBG")
    private String jmbg;
    @Column(name = "BrPosl")
    private Integer brPosl;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Administrator administrator;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Kurir kurir;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKorisnik")
    private List<Paket> paketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKorisnik")
    private List<ZahtevKurira> zahtevKuriraList;

    public Korisnik() {
    }

    public Korisnik(Integer iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    public Integer getIDKorisnik() {
        return iDKorisnik;
    }

    public void setIDKorisnik(Integer iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKIme() {
        return kIme;
    }

    public void setKIme(String kIme) {
        this.kIme = kIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public Integer getBrPosl() {
        return brPosl;
    }

    public void setBrPosl(Integer brPosl) {
        this.brPosl = brPosl;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Kurir getKurir() {
        return kurir;
    }

    public void setKurir(Kurir kurir) {
        this.kurir = kurir;
    }

    public List<Paket> getPaketList() {
        return paketList;
    }

    public void setPaketList(List<Paket> paketList) {
        this.paketList = paketList;
    }

    public List<ZahtevKurira> getZahtevKuriraList() {
        return zahtevKuriraList;
    }

    public void setZahtevKuriraList(List<ZahtevKurira> zahtevKuriraList) {
        this.zahtevKuriraList = zahtevKuriraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKorisnik != null ? iDKorisnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.iDKorisnik == null && other.iDKorisnik != null) || (this.iDKorisnik != null && !this.iDKorisnik.equals(other.iDKorisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ iDKorisnik=" + iDKorisnik + " ]";
    }

}
