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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Opstina")
@NamedQueries({
    @NamedQuery(name = "Opstina.findAll", query = "SELECT o FROM Opstina o")
    , @NamedQuery(name = "Opstina.findByIDOpstina", query = "SELECT o FROM Opstina o WHERE o.iDOpstina = :iDOpstina")
    , @NamedQuery(name = "Opstina.findByNaziv", query = "SELECT o FROM Opstina o WHERE o.naziv = :naziv")
    , @NamedQuery(name = "Opstina.findByX", query = "SELECT o FROM Opstina o WHERE o.x = :x")
    , @NamedQuery(name = "Opstina.findByY", query = "SELECT o FROM Opstina o WHERE o.y = :y")})
public class Opstina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDOpstina")
    private Integer iDOpstina;
    @Size(max = 100)
    @Column(name = "Naziv")
    private String naziv;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "X")
    private BigDecimal x;
    @Column(name = "Y")
    private BigDecimal y;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oPreuzimanja")
    private List<Paket> paketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oDostave")
    private List<Paket> paketList1;
    @JoinColumn(name = "IDGrad", referencedColumnName = "IDGrad")
    @ManyToOne(optional = false)
    private Grad iDGrad;

    public Opstina() {
    }

    public Opstina(Integer iDOpstina) {
        this.iDOpstina = iDOpstina;
    }

    public Integer getIDOpstina() {
        return iDOpstina;
    }

    public void setIDOpstina(Integer iDOpstina) {
        this.iDOpstina = iDOpstina;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public List<Paket> getPaketList() {
        return paketList;
    }

    public void setPaketList(List<Paket> paketList) {
        this.paketList = paketList;
    }

    public List<Paket> getPaketList1() {
        return paketList1;
    }

    public void setPaketList1(List<Paket> paketList1) {
        this.paketList1 = paketList1;
    }

    public Grad getIDGrad() {
        return iDGrad;
    }

    public void setIDGrad(Grad iDGrad) {
        this.iDGrad = iDGrad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDOpstina != null ? iDOpstina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opstina)) {
            return false;
        }
        Opstina other = (Opstina) object;
        if ((this.iDOpstina == null && other.iDOpstina != null) || (this.iDOpstina != null && !this.iDOpstina.equals(other.iDOpstina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Opstina[ iDOpstina=" + iDOpstina + " ]";
    }

}
