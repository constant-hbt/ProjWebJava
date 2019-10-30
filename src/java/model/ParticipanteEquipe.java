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

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "participante_equipe", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "ParticipanteEquipe.findAll", query = "SELECT p FROM ParticipanteEquipe p")})
public class ParticipanteEquipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpartequipe")
    private Integer idpartequipe;
    @JoinColumn(name = "idequipe", referencedColumnName = "idequipe")
    @ManyToOne(optional = false)
    private Equipes equipes;
    @JoinColumn(name = "idparticipante", referencedColumnName = "idparticipante")
    @ManyToOne(optional = false)
    private Participantes participantes;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;

    public ParticipanteEquipe() {
    }

    public ParticipanteEquipe(Integer idpartequipe) {
        this.idpartequipe = idpartequipe;
    }

    public Integer getIdpartequipe() {
        return idpartequipe;
    }

    public void setIdpartequipe(Integer idpartequipe) {
        this.idpartequipe = idpartequipe;
    }

    public Equipes getEquipes() {
        return equipes;
    }

    public void setEquipes(Equipes equipes) {
        this.equipes = equipes;
    }

    public Participantes getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Participantes participantes) {
        this.participantes = participantes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpartequipe != null ? idpartequipe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticipanteEquipe)) {
            return false;
        }
        ParticipanteEquipe other = (ParticipanteEquipe) object;
        if ((this.idpartequipe == null && other.idpartequipe != null) || (this.idpartequipe != null && !this.idpartequipe.equals(other.idpartequipe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ParticipanteEquipe[ idpartequipe=" + idpartequipe + " ]";
    }
    
}
