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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Vozilo")
@NamedQueries({
    @NamedQuery(name = "Vozilo.findAll", query = "SELECT v FROM Vozilo v")
    , @NamedQuery(name = "Vozilo.findByIDVozilo", query = "SELECT v FROM Vozilo v WHERE v.iDVozilo = :iDVozilo")
    , @NamedQuery(name = "Vozilo.findByRegBroj", query = "SELECT v FROM Vozilo v WHERE v.regBroj = :regBroj")
    , @NamedQuery(name = "Vozilo.findByTipGoriva", query = "SELECT v FROM Vozilo v WHERE v.tipGoriva = :tipGoriva")
    , @NamedQuery(name = "Vozilo.findByPotrosnja", query = "SELECT v FROM Vozilo v WHERE v.potrosnja = :potrosnja")})
public class Vozilo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDVozilo")
    private Integer iDVozilo;
    @Size(max = 100)
    @Column(name = "RegBroj")
    private String regBroj;
    @Column(name = "TipGoriva")
    private Integer tipGoriva;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Potrosnja")
    private BigDecimal potrosnja;
    @OneToMany(mappedBy = "iDVozilo")
    private List<Kurir> kurirList;

    public Vozilo() {
    }

    public Vozilo(Integer iDVozilo) {
        this.iDVozilo = iDVozilo;
    }

    public Integer getIDVozilo() {
        return iDVozilo;
    }

    public void setIDVozilo(Integer iDVozilo) {
        this.iDVozilo = iDVozilo;
    }

    public String getRegBroj() {
        return regBroj;
    }

    public void setRegBroj(String regBroj) {
        this.regBroj = regBroj;
    }

    public Integer getTipGoriva() {
        return tipGoriva;
    }

    public void setTipGoriva(Integer tipGoriva) {
        this.tipGoriva = tipGoriva;
    }

    public BigDecimal getPotrosnja() {
        return potrosnja;
    }

    public void setPotrosnja(BigDecimal potrosnja) {
        this.potrosnja = potrosnja;
    }

    public List<Kurir> getKurirList() {
        return kurirList;
    }

    public void setKurirList(List<Kurir> kurirList) {
        this.kurirList = kurirList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDVozilo != null ? iDVozilo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vozilo)) {
            return false;
        }
        Vozilo other = (Vozilo) object;
        if ((this.iDVozilo == null && other.iDVozilo != null) || (this.iDVozilo != null && !this.iDVozilo.equals(other.iDVozilo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Vozilo[ iDVozilo=" + iDVozilo + " ]";
    }

}
