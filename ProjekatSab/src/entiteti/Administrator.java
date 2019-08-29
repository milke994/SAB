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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Administrator")
@NamedQueries({
    @NamedQuery(name = "Administrator.findAll", query = "SELECT a FROM Administrator a")
    , @NamedQuery(name = "Administrator.findByIDKorisnik", query = "SELECT a FROM Administrator a WHERE a.iDKorisnik = :iDKorisnik")})
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKorisnik")
    private Integer iDKorisnik;
    @JoinColumn(name = "IDKorisnik", referencedColumnName = "IDKorisnik", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Korisnik korisnik;

    public Administrator() {
    }

    public Administrator(Integer iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    public Integer getIDKorisnik() {
        return iDKorisnik;
    }

    public void setIDKorisnik(Integer iDKorisnik) {
        this.iDKorisnik = iDKorisnik;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
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
        if (!(object instanceof Administrator)) {
            return false;
        }
        Administrator other = (Administrator) object;
        if ((this.iDKorisnik == null && other.iDKorisnik != null) || (this.iDKorisnik != null && !this.iDKorisnik.equals(other.iDKorisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Administrator[ iDKorisnik=" + iDKorisnik + " ]";
    }

}
