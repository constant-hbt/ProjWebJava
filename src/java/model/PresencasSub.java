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
@Table(name = "presencas_sub", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "PresencasSub.findAll", query = "SELECT p FROM PresencasSub p")})
public class PresencasSub implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresencasSubPK presencasSubPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @JoinColumn(name = "idparticipante", referencedColumnName = "idparticipante", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Participantes participantes;
    @JoinColumn(name = "idsubevento", referencedColumnName = "idsubevento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Subeventos subeventos;

    public PresencasSub() {
    }

    public PresencasSub(PresencasSubPK presencasSubPK) {
        this.presencasSubPK = presencasSubPK;
    }

    public PresencasSub(PresencasSubPK presencasSubPK, Date datahora) {
        this.presencasSubPK = presencasSubPK;
        this.datahora = datahora;
    }

    public PresencasSub(int idparticipante, int idsubevento) {
        this.presencasSubPK = new PresencasSubPK(idparticipante, idsubevento);
    }

    public PresencasSubPK getPresencasSubPK() {
        return presencasSubPK;
    }

    public void setPresencasSubPK(PresencasSubPK presencasSubPK) {
        this.presencasSubPK = presencasSubPK;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Participantes getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Participantes participantes) {
        this.participantes = participantes;
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
        hash += (presencasSubPK != null ? presencasSubPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencasSub)) {
            return false;
        }
        PresencasSub other = (PresencasSub) object;
        if ((this.presencasSubPK == null && other.presencasSubPK != null) || (this.presencasSubPK != null && !this.presencasSubPK.equals(other.presencasSubPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PresencasSub[ presencasSubPK=" + presencasSubPK + " ]";
    }
    
}
