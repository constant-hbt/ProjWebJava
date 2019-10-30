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
@Table(name = "presencas_eve", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "PresencasEve.findAll", query = "SELECT p FROM PresencasEve p")})
public class PresencasEve implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresencasEvePK presencasEvePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "idparticipante", referencedColumnName = "idparticipante", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Participantes participantes;

    public PresencasEve() {
    }

    public PresencasEve(PresencasEvePK presencasEvePK) {
        this.presencasEvePK = presencasEvePK;
    }

    public PresencasEve(PresencasEvePK presencasEvePK, Date datahora) {
        this.presencasEvePK = presencasEvePK;
        this.datahora = datahora;
    }

    public PresencasEve(int idparticipante, int idevento) {
        this.presencasEvePK = new PresencasEvePK(idparticipante, idevento);
    }

    public PresencasEvePK getPresencasEvePK() {
        return presencasEvePK;
    }

    public void setPresencasEvePK(PresencasEvePK presencasEvePK) {
        this.presencasEvePK = presencasEvePK;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public Participantes getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Participantes participantes) {
        this.participantes = participantes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (presencasEvePK != null ? presencasEvePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencasEve)) {
            return false;
        }
        PresencasEve other = (PresencasEve) object;
        if ((this.presencasEvePK == null && other.presencasEvePK != null) || (this.presencasEvePK != null && !this.presencasEvePK.equals(other.presencasEvePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PresencasEve[ presencasEvePK=" + presencasEvePK + " ]";
    }
    
}
