/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Isporuka")
@NamedQueries({
    @NamedQuery(name = "Isporuka.findAll", query = "SELECT i FROM Isporuka i")
    , @NamedQuery(name = "Isporuka.findByIDIsporuka", query = "SELECT i FROM Isporuka i WHERE i.iDIsporuka = :iDIsporuka")
    , @NamedQuery(name = "Isporuka.findByStatus", query = "SELECT i FROM Isporuka i WHERE i.status = :status")
    , @NamedQuery(name = "Isporuka.findByCena", query = "SELECT i FROM Isporuka i WHERE i.cena = :cena")
    , @NamedQuery(name = "Isporuka.findByVreme", query = "SELECT i FROM Isporuka i WHERE i.vreme = :vreme")})
public class Isporuka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDIsporuka")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDIsporuka;
    @Column(name = "Status")
    private Integer status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cena")
    private BigDecimal cena;
    @Column(name = "Vreme")
    @Temporal(TemporalType.DATE)
    private Date vreme;
    @JoinColumn(name = "IDKurira", referencedColumnName = "IDKorisnik")
    @ManyToOne(optional = false)
    private Kurir iDKurira;
    @JoinColumn(name = "IDPaketa", referencedColumnName = "IDPaketa")
    @ManyToOne(optional = false)
    private Paket iDPaketa;
    @JoinColumn(name = "IDPonuda", referencedColumnName = "IDPonuda")
    @ManyToOne(optional = false)
    private Ponuda iDPonuda;

    public Isporuka() {
    }

    public Isporuka(Integer iDIsporuka) {
        this.iDIsporuka = iDIsporuka;
    }

    public Integer getIDIsporuka() {
        return iDIsporuka;
    }

    public void setIDIsporuka(Integer iDIsporuka) {
        this.iDIsporuka = iDIsporuka;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public Kurir getIDKurira() {
        return iDKurira;
    }

    public void setIDKurira(Kurir iDKurira) {
        this.iDKurira = iDKurira;
    }

    public Paket getIDPaketa() {
        return iDPaketa;
    }

    public void setIDPaketa(Paket iDPaketa) {
        this.iDPaketa = iDPaketa;
    }

    public Ponuda getIDPonuda() {
        return iDPonuda;
    }

    public void setIDPonuda(Ponuda iDPonuda) {
        this.iDPonuda = iDPonuda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDIsporuka != null ? iDIsporuka.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Isporuka)) {
            return false;
        }
        Isporuka other = (Isporuka) object;
        if ((this.iDIsporuka == null && other.iDIsporuka != null) || (this.iDIsporuka != null && !this.iDIsporuka.equals(other.iDIsporuka))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Isporuka[ iDIsporuka=" + iDIsporuka + " ]";
    }

}
