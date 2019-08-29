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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Paket")
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p")
    , @NamedQuery(name = "Paket.findByIDPaketa", query = "SELECT p FROM Paket p WHERE p.iDPaketa = :iDPaketa")
    , @NamedQuery(name = "Paket.findByTip", query = "SELECT p FROM Paket p WHERE p.tip = :tip")
    , @NamedQuery(name = "Paket.findByTezina", query = "SELECT p FROM Paket p WHERE p.tezina = :tezina")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPaketa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDPaketa;
    @Column(name = "Tip")
    private Integer tip;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Tezina")
    private BigDecimal tezina;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPaketa")
    private List<Isporuka> isporukaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPaketa")
    private List<Ponuda> ponudaList;
    @JoinColumn(name = "IDKorisnik", referencedColumnName = "IDKorisnik")
    @ManyToOne(optional = false)
    private Korisnik iDKorisnik;
    @JoinColumn(name = "OPreuzimanja", referencedColumnName = "IDOpstina")
    @ManyToOne(optional = false)
    private Opstina oPreuzimanja;
    @JoinColumn(name = "ODostave", referencedColumnName = "IDOpstina")
    @ManyToOne(optional = false)
    private Opstina oDostave;

    public Paket() {
    }

    public Paket(Integer iDPaketa) {
        this.iDPaketa = iDPaketa;
    }

    public Integer getIDPaketa() {
        return iDPaketa;
    }

    public void setIDPaketa(Integer iDPaketa) {
        this.iDPaketa = iDPaketa;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public BigDecimal getTezina() {
        return tezina;
    }

    public void setTezina(BigDecimal tezina) {
        this.tezina = tezina;
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

    public Korisnik getIDKorisnik() {
        return iDKorisnik;
    }

    public void setIDKorisnik(Korisnik iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    public Opstina getOPreuzimanja() {
        return oPreuzimanja;
    }

    public void setOPreuzimanja(Opstina oPreuzimanja) {
        this.oPreuzimanja = oPreuzimanja;
    }

    public Opstina getODostave() {
        return oDostave;
    }

    public void setODostave(Opstina oDostave) {
        this.oDostave = oDostave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPaketa != null ? iDPaketa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.iDPaketa == null && other.iDPaketa != null) || (this.iDPaketa != null && !this.iDPaketa.equals(other.iDPaketa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Paket[ iDPaketa=" + iDPaketa + " ]";
    }

}
