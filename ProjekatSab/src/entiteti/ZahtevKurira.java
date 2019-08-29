/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "ZahtevKurira")
@NamedQueries({
    @NamedQuery(name = "ZahtevKurira.findAll", query = "SELECT z FROM ZahtevKurira z")
    , @NamedQuery(name = "ZahtevKurira.findByIDZKurira", query = "SELECT z FROM ZahtevKurira z WHERE z.iDZKurira = :iDZKurira")
    , @NamedQuery(name = "ZahtevKurira.findByRegBroj", query = "SELECT z FROM ZahtevKurira z WHERE z.regBroj = :regBroj")})
public class ZahtevKurira implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDZKurira")
    private Integer iDZKurira;
    @Size(max = 100)
    @Column(name = "RegBroj")
    private String regBroj;
    @JoinColumn(name = "IDKorisnik", referencedColumnName = "IDKorisnik")
    @ManyToOne(optional = false)
    private Korisnik iDKorisnik;

    public ZahtevKurira() {
    }

    public ZahtevKurira(Integer iDZKurira) {
        this.iDZKurira = iDZKurira;
    }

    public Integer getIDZKurira() {
        return iDZKurira;
    }

    public void setIDZKurira(Integer iDZKurira) {
        this.iDZKurira = iDZKurira;
    }

    public String getRegBroj() {
        return regBroj;
    }

    public void setRegBroj(String regBroj) {
        this.regBroj = regBroj;
    }

    public Korisnik getIDKorisnik() {
        return iDKorisnik;
    }

    public void setIDKorisnik(Korisnik iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDZKurira != null ? iDZKurira.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZahtevKurira)) {
            return false;
        }
        ZahtevKurira other = (ZahtevKurira) object;
        if ((this.iDZKurira == null && other.iDZKurira != null) || (this.iDZKurira != null && !this.iDZKurira.equals(other.iDZKurira))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.ZahtevKurira[ iDZKurira=" + iDZKurira + " ]";
    }

}
