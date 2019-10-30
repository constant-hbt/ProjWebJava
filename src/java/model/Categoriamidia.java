/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "categoriamidia", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Categoriamidia.findAll", query = "SELECT c FROM Categoriamidia c")})
public class Categoriamidia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcategoriamidia")
    private Integer idcategoriamidia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriamidia")
    private Collection<Midias> midiasCollection;

    public Categoriamidia() {
    }

    public Categoriamidia(Integer idcategoriamidia) {
        this.idcategoriamidia = idcategoriamidia;
    }

    public Categoriamidia(Integer idcategoriamidia, String descricao) {
        this.idcategoriamidia = idcategoriamidia;
        this.descricao = descricao;
    }

    public Integer getIdcategoriamidia() {
        return idcategoriamidia;
    }

    public void setIdcategoriamidia(Integer idcategoriamidia) {
        this.idcategoriamidia = idcategoriamidia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<Midias> getMidiasCollection() {
        return midiasCollection;
    }

    public void setMidiasCollection(Collection<Midias> midiasCollection) {
        this.midiasCollection = midiasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcategoriamidia != null ? idcategoriamidia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriamidia)) {
            return false;
        }
        Categoriamidia other = (Categoriamidia) object;
        if ((this.idcategoriamidia == null && other.idcategoriamidia != null) || (this.idcategoriamidia != null && !this.idcategoriamidia.equals(other.idcategoriamidia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Categoriamidia[ idcategoriamidia=" + idcategoriamidia + " ]";
    }
    
}
