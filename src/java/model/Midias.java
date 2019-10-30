/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "midias", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Midias.findAll", query = "SELECT m FROM Midias m")})
public class Midias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmidia")
    private Integer idmidia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "legenda")
    private String legenda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pathmidia")
    private String pathmidia;
    @JoinColumn(name = "idcategoriamidia", referencedColumnName = "idcategoriamidia")
    @ManyToOne(optional = false)
    private Categoriamidia categoriamidia;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne
    private Eventos eventos;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "idsubevento", referencedColumnName = "idsubevento")
    @ManyToOne
    private Subeventos subeventos;

    public Midias() {
    }

    public Midias(Integer idmidia) {
        this.idmidia = idmidia;
    }

    public Midias(Integer idmidia, String legenda, String pathmidia) {
        this.idmidia = idmidia;
        this.legenda = legenda;
        this.pathmidia = pathmidia;
    }

    public Integer getIdmidia() {
        return idmidia;
    }

    public void setIdmidia(Integer idmidia) {
        this.idmidia = idmidia;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public String getPathmidia() {
        return pathmidia;
    }

    public void setPathmidia(String pathmidia) {
        this.pathmidia = pathmidia;
    }

    public Categoriamidia getCategoriamidia() {
        return categoriamidia;
    }

    public void setCategoriamidia(Categoriamidia categoriamidia) {
        this.categoriamidia = categoriamidia;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Subeventos getSubeventos() {
        return subeventos;
    }

    public void setSubeventos(Subeventos subeventos) {
        this.subeventos = subeventos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmidia != null ? idmidia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Midias)) {
            return false;
        }
        Midias other = (Midias) object;
        if ((this.idmidia == null && other.idmidia != null) || (this.idmidia != null && !this.idmidia.equals(other.idmidia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Midias[ idmidia=" + idmidia + " ]";
    }
    
}
