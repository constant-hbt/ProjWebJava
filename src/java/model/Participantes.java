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

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "participantes", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Participantes.findAll", query = "SELECT p FROM Participantes p")})
public class Participantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idparticipante")
    private Integer idparticipante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantes")
    private Collection<InscricaoPartSubeve> inscricaoPartSubeveCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantes")
    private Collection<PresencasSub> presencasSubCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantes")
    private Collection<InscricaoPartEvento> inscricaoPartEventoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantes")
    private Collection<PresencasEve> presencasEveCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantes")
    private Collection<Certificados> certificadosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantes")
    private Collection<ParticipanteEquipe> participanteEquipeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantes")
    private Collection<Crachas> crachasCollection;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Participantes() {
    }

    public Participantes(Integer idparticipante) {
        this.idparticipante = idparticipante;
    }

    public Integer getIdparticipante() {
        return idparticipante;
    }

    public void setIdparticipante(Integer idparticipante) {
        this.idparticipante = idparticipante;
    }

    public Collection<InscricaoPartSubeve> getInscricaoPartSubeveCollection() {
        return inscricaoPartSubeveCollection;
    }

    public void setInscricaoPartSubeveCollection(Collection<InscricaoPartSubeve> inscricaoPartSubeveCollection) {
        this.inscricaoPartSubeveCollection = inscricaoPartSubeveCollection;
    }

    public Collection<PresencasSub> getPresencasSubCollection() {
        return presencasSubCollection;
    }

    public void setPresencasSubCollection(Collection<PresencasSub> presencasSubCollection) {
        this.presencasSubCollection = presencasSubCollection;
    }

    public Collection<InscricaoPartEvento> getInscricaoPartEventoCollection() {
        return inscricaoPartEventoCollection;
    }

    public void setInscricaoPartEventoCollection(Collection<InscricaoPartEvento> inscricaoPartEventoCollection) {
        this.inscricaoPartEventoCollection = inscricaoPartEventoCollection;
    }

    public Collection<PresencasEve> getPresencasEveCollection() {
        return presencasEveCollection;
    }

    public void setPresencasEveCollection(Collection<PresencasEve> presencasEveCollection) {
        this.presencasEveCollection = presencasEveCollection;
    }

    public Collection<Certificados> getCertificadosCollection() {
        return certificadosCollection;
    }

    public void setCertificadosCollection(Collection<Certificados> certificadosCollection) {
        this.certificadosCollection = certificadosCollection;
    }

    public Collection<ParticipanteEquipe> getParticipanteEquipeCollection() {
        return participanteEquipeCollection;
    }

    public void setParticipanteEquipeCollection(Collection<ParticipanteEquipe> participanteEquipeCollection) {
        this.participanteEquipeCollection = participanteEquipeCollection;
    }

    public Collection<Crachas> getCrachasCollection() {
        return crachasCollection;
    }

    public void setCrachasCollection(Collection<Crachas> crachasCollection) {
        this.crachasCollection = crachasCollection;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparticipante != null ? idparticipante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participantes)) {
            return false;
        }
        Participantes other = (Participantes) object;
        if ((this.idparticipante == null && other.idparticipante != null) || (this.idparticipante != null && !this.idparticipante.equals(other.idparticipante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Participantes[ idparticipante=" + idparticipante + " ]";
    }
    
}
