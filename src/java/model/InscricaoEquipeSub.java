/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
 * @author henrique
 */
@Entity
@Table(name = "inscricao_equipe_sub", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "InscricaoEquipeSub.findAll", query = "SELECT i FROM InscricaoEquipeSub i")})
public class InscricaoEquipeSub implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idequipesub")
    private Integer idequipesub;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @JoinColumn(name = "idequipe", referencedColumnName = "idequipe")
    @ManyToOne(optional = false)
    private Equipes equipes;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "idsubevento", referencedColumnName = "idsubevento")
    @ManyToOne(optional = false)
    private Subeventos subeventos;

    public InscricaoEquipeSub() {
    }

    public InscricaoEquipeSub(Integer idequipesub) {
        this.idequipesub = idequipesub;
    }

    public InscricaoEquipeSub(Integer idequipesub, Date datahora) {
        this.idequipesub = idequipesub;
        this.datahora = datahora;
    }

    public Integer getIdequipesub() {
        return idequipesub;
    }

    public void setIdequipesub(Integer idequipesub) {
        this.idequipesub = idequipesub;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Equipes getEquipes() {
        return equipes;
    }

    public void setEquipes(Equipes equipes) {
        this.equipes = equipes;
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
        hash += (idequipesub != null ? idequipesub.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscricaoEquipeSub)) {
            return false;
        }
        InscricaoEquipeSub other = (InscricaoEquipeSub) object;
        if ((this.idequipesub == null && other.idequipesub != null) || (this.idequipesub != null && !this.idequipesub.equals(other.idequipesub))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.InscricaoEquipeSub[ idequipesub=" + idequipesub + " ]";
    }
    
}
