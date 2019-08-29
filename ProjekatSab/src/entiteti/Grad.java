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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nemanja
 */
@Entity
@Table(name = "Grad")
@NamedQueries({
    @NamedQuery(name = "Grad.findAll", query = "SELECT g FROM Grad g")
    , @NamedQuery(name = "Grad.findByIDGrad", query = "SELECT g FROM Grad g WHERE g.iDGrad = :iDGrad")
    , @NamedQuery(name = "Grad.findByNaziv", query = "SELECT g FROM Grad g WHERE g.naziv = :naziv")
    , @NamedQuery(name = "Grad.findByPostBr", query = "SELECT g FROM Grad g WHERE g.postBr = :postBr")})
public class Grad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDGrad")
    private Integer iDGrad;
    @Size(max = 100)
    @Column(name = "Naziv")
    private String naziv;
    @Size(max = 100)
    @Column(name = "PostBr")
    private String postBr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDGrad")
    private List<Opstina> opstinaList;

    public Grad() {
    }

    public Grad(Integer iDGrad) {
        this.iDGrad = iDGrad;
    }

    public Integer getIDGrad() {
        return iDGrad;
    }

    public void setIDGrad(Integer iDGrad) {
        this.iDGrad = iDGrad;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPostBr() {
        return postBr;
    }

    public void setPostBr(String postBr) {
        this.postBr = postBr;
    }

    public List<Opstina> getOpstinaList() {
        return opstinaList;
    }

    public void setOpstinaList(List<Opstina> opstinaList) {
        this.opstinaList = opstinaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGrad != null ? iDGrad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grad)) {
            return false;
        }
        Grad other = (Grad) object;
        if ((this.iDGrad == null && other.iDGrad != null) || (this.iDGrad != null && !this.iDGrad.equals(other.iDGrad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Grad[ iDGrad=" + iDGrad + " ]";
    }

}
