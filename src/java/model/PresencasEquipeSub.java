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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "presencas_equipe_sub", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "PresencasEquipeSub.findAll", query = "SELECT p FROM PresencasEquipeSub p")})
public class PresencasEquipeSub implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresencasEquipeSubPK presencasEquipeSubPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @JoinColumn(name = "idequipe", referencedColumnName = "idequipe", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Equipes equipes;
    @JoinColumn(name = "idsubevento", referencedColumnName = "idsubevento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Subeventos subeventos;

    public PresencasEquipeSub() {
    }

    public PresencasEquipeSub(PresencasEquipeSubPK presencasEquipeSubPK) {
        this.presencasEquipeSubPK = presencasEquipeSubPK;
    }

    public PresencasEquipeSub(PresencasEquipeSubPK presencasEquipeSubPK, Date datahora) {
        this.presencasEquipeSubPK = presencasEquipeSubPK;
        this.datahora = datahora;
    }

    public PresencasEquipeSub(int idequipe, int idsubevento) {
        this.presencasEquipeSubPK = new PresencasEquipeSubPK(idequipe, idsubevento);
    }

    public PresencasEquipeSubPK getPresencasEquipeSubPK() {
        return presencasEquipeSubPK;
    }

    public void setPresencasEquipeSubPK(PresencasEquipeSubPK presencasEquipeSubPK) {
        this.presencasEquipeSubPK = presencasEquipeSubPK;
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

    public Subeventos getSubeventos() {
        return subeventos;
    }

    public void setSubeventos(Subeventos subeventos) {
        this.subeventos = subeventos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (presencasEquipeSubPK != null ? presencasEquipeSubPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencasEquipeSub)) {
            return false;
        }
        PresencasEquipeSub other = (PresencasEquipeSub) object;
        if ((this.presencasEquipeSubPK == null && other.presencasEquipeSubPK != null) || (this.presencasEquipeSubPK != null && !this.presencasEquipeSubPK.equals(other.presencasEquipeSubPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PresencasEquipeSub[ presencasEquipeSubPK=" + presencasEquipeSubPK + " ]";
    }
    
}
