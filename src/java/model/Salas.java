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
 * @author henrique
 */
@Entity
@Table(name = "salas", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Salas.findAll", query = "SELECT s FROM Salas s")})
public class Salas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsala")
    private Integer idsala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidadetotal")
    private int capacidadetotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidadeocupada")
    private int capacidadeocupada;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salas")
    private Collection<Subeventos> subeventosCollection;

    public Salas() {
    }

    public Salas(Integer idsala) {
        this.idsala = idsala;
    }

    public Salas(Integer idsala, String descricao, int capacidadetotal, int capacidadeocupada) {
        this.idsala = idsala;
        this.descricao = descricao;
        this.capacidadetotal = capacidadetotal;
        this.capacidadeocupada = capacidadeocupada;
    }

    public Integer getIdsala() {
        return idsala;
    }

    public void setIdsala(Integer idsala) {
        this.idsala = idsala;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidadetotal() {
        return capacidadetotal;
    }

    public void setCapacidadetotal(int capacidadetotal) {
        this.capacidadetotal = capacidadetotal;
    }

    public int getCapacidadeocupada() {
        return capacidadeocupada;
    }

    public void setCapacidadeocupada(int capacidadeocupada) {
        this.capacidadeocupada = capacidadeocupada;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<Subeventos> getSubeventosCollection() {
        return subeventosCollection;
    }

    public void setSubeventosCollection(Collection<Subeventos> subeventosCollection) {
        this.subeventosCollection = subeventosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsala != null ? idsala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salas)) {
            return false;
        }
        Salas other = (Salas) object;
        if ((this.idsala == null && other.idsala != null) || (this.idsala != null && !this.idsala.equals(other.idsala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Salas[ idsala=" + idsala + " ]";
    }
    
}
