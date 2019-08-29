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
@Table(name = "Ponuda")
@NamedQueries({
    @NamedQuery(name = "Ponuda.findAll", query = "SELECT p FROM Ponuda p")
    , @NamedQuery(name = "Ponuda.findByIDPonuda", query = "SELECT p FROM Ponuda p WHERE p.iDPonuda = :iDPonuda")
    , @NamedQuery(name = "Ponuda.findByProcenat", query = "SELECT p FROM Ponuda p WHERE p.procenat = :procenat")})
public class Ponuda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPonuda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDPonuda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Procenat")
    private BigDecimal procenat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPonuda")
    private List<Isporuka> isporukaList;
    @JoinColumn(name = "IDKurir", referencedColumnName = "IDKorisnik")
    @ManyToOne(optional = false)
    private Kurir iDKurir;
    @JoinColumn(name = "IDPaketa", referencedColumnName = "IDPaketa")
    @ManyToOne(optional = false)
    private Paket iDPaketa;

    public Ponuda() {
    }

    public Ponuda(Integer iDPonuda) {
        this.iDPonuda = iDPonuda;
    }

    public Integer getIDPonuda() {
        return iDPonuda;
    }

    public void setIDPonuda(Integer iDPonuda) {
        this.iDPonuda = iDPonuda;
    }

    public BigDecimal getProcenat() {
        return procenat;
    }

    public void setProcenat(BigDecimal procenat) {
        this.procenat = procenat;
    }

    public List<Isporuka> getIsporukaList() {
        return isporukaList;
    }

    public void setIsporukaList(List<Isporuka> isporukaList) {
        this.isporukaList = isporukaList;
    }

    public Kurir getIDKurir() {
        return iDKurir;
    }

    public void setIDKurir(Kurir iDKurir) {
        this.iDKurir = iDKurir;
    }

    public Paket getIDPaketa() {
        return iDPaketa;
    }

    public void setIDPaketa(Paket iDPaketa) {
        this.iDPaketa = iDPaketa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPonuda != null ? iDPonuda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ponuda)) {
            return false;
        }
        Ponuda other = (Ponuda) object;
        if ((this.iDPonuda == null && other.iDPonuda != null) || (this.iDPonuda != null && !this.iDPonuda.equals(other.iDPonuda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Ponuda[ iDPonuda=" + iDPonuda + " ]";
    }

}
