/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Kurir")
@NamedQueries({
    @NamedQuery(name = "Kurir.findAll", query = "SELECT k FROM Kurir k")
    , @NamedQuery(name = "Kurir.findByBrIsporuka", query = "SELECT k FROM Kurir k WHERE k.brIsporuka = :brIsporuka")
    , @NamedQuery(name = "Kurir.findByProfit", query = "SELECT k FROM Kurir k WHERE k.profit = :profit")
    , @NamedQuery(name = "Kurir.findByStatus", query = "SELECT k FROM Kurir k WHERE k.status = :status")
    , @NamedQuery(name = "Kurir.findByIDKorisnik", query = "SELECT k FROM Kurir k WHERE k.iDKorisnik = :iDKorisnik")})
public class Kurir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "BrIsporuka")
    private Integer brIsporuka;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Profit")
    private BigDecimal profit;
    @Column(name = "Status")
    private Integer status;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKorisnik")
    private Integer iDKorisnik;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKurira")
    private List<Isporuka> isporukaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKurir")
    private List<Ponuda> ponudaList;
    @JoinColumn(name = "IDKorisnik", referencedColumnName = "IDKorisnik", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Korisnik korisnik;
    @JoinColumn(name = "IDVozilo", referencedColumnName = "IDVozilo")
    @ManyToOne
    private Vozilo iDVozilo;

    public Kurir() {
    }

    public Kurir(Integer iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    public Integer getBrIsporuka() {
        return brIsporuka;
    }

    public void setBrIsporuka(Integer brIsporuka) {
        this.brIsporuka = brIsporuka;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIDKorisnik() {
        return iDKorisnik;
    }

    public void setIDKorisnik(Integer iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    public List<Isporuka> getIsporukaList() {
        return isporukaList;
    }

    public void setIsporukaList(List<Isporuka> isporukaList) {
        this.isporukaList = isporukaList;
    }

    public List<Ponuda> getPonudaList() {
        return ponudaList;
    }

    public void setPonudaList(List<Ponuda> ponudaList) {
        this.ponudaList = ponudaList;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Vozilo getIDVozilo() {
        return iDVozilo;
    }

    public void setIDVozilo(Vozilo iDVozilo) {
        this.iDVozilo = iDVozilo;
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
        if (!(object instanceof Kurir)) {
            return false;
        }
        Kurir other = (Kurir) object;
        if ((this.iDKorisnik == null && other.iDKorisnik != null) || (this.iDKorisnik != null && !this.iDKorisnik.equals(other.iDKorisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Kurir[ iDKorisnik=" + iDKorisnik + " ]";
    }

}
