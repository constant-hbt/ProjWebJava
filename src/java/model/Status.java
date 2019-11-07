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
@Table(name = "status", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s")
    , @NamedQuery(name = "Status.findByIdstatus", query = "SELECT s FROM Status s WHERE s.idstatus = :idstatus")
    , @NamedQuery(name = "Status.findByDescricao", query = "SELECT s FROM Status s WHERE s.descricao = :descricao")})
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idstatus")
    private Integer idstatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<InscricaoEquipeSub> inscricaoEquipeSubCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<InscricaoPartSubeve> inscricaoPartSubeveCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Midias> midiasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<InscricaoPartEvento> inscricaoPartEventoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Certificados> certificadosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Eventos> eventosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Patrocinios> patrociniosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Usuarios> usuariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<ParticipanteEquipe> participanteEquipeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Salas> salasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Equipes> equipesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Subeventos> subeventosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Editais> editaisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Patrocinioimagens> patrocinioimagensCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Crachas> crachasCollection;

    public Status() {
    }

    public Status(Integer idstatus) {
        this.idstatus = idstatus;
    }

    public Status(Integer idstatus, String descricao) {
        this.idstatus = idstatus;
        this.descricao = descricao;
    }

    public Integer getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(Integer idstatus) {
        this.idstatus = idstatus;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<InscricaoEquipeSub> getInscricaoEquipeSubCollection() {
        return inscricaoEquipeSubCollection;
    }

    public void setInscricaoEquipeSubCollection(Collection<InscricaoEquipeSub> inscricaoEquipeSubCollection) {
        this.inscricaoEquipeSubCollection = inscricaoEquipeSubCollection;
    }

    public Collection<InscricaoPartSubeve> getInscricaoPartSubeveCollection() {
        return inscricaoPartSubeveCollection;
    }

    public void setInscricaoPartSubeveCollection(Collection<InscricaoPartSubeve> inscricaoPartSubeveCollection) {
        this.inscricaoPartSubeveCollection = inscricaoPartSubeveCollection;
    }

    public Collection<Midias> getMidiasCollection() {
        return midiasCollection;
    }

    public void setMidiasCollection(Collection<Midias> midiasCollection) {
        this.midiasCollection = midiasCollection;
    }

    public Collection<InscricaoPartEvento> getInscricaoPartEventoCollection() {
        return inscricaoPartEventoCollection;
    }

    public void setInscricaoPartEventoCollection(Collection<InscricaoPartEvento> inscricaoPartEventoCollection) {
        this.inscricaoPartEventoCollection = inscricaoPartEventoCollection;
    }

    public Collection<Certificados> getCertificadosCollection() {
        return certificadosCollection;
    }

    public void setCertificadosCollection(Collection<Certificados> certificadosCollection) {
        this.certificadosCollection = certificadosCollection;
    }

    public Collection<Eventos> getEventosCollection() {
        return eventosCollection;
    }

    public void setEventosCollection(Collection<Eventos> eventosCollection) {
        this.eventosCollection = eventosCollection;
    }

    public Collection<Patrocinios> getPatrociniosCollection() {
        return patrociniosCollection;
    }

    public void setPatrociniosCollection(Collection<Patrocinios> patrociniosCollection) {
        this.patrociniosCollection = patrociniosCollection;
    }

    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    public Collection<ParticipanteEquipe> getParticipanteEquipeCollection() {
        return participanteEquipeCollection;
    }

    public void setParticipanteEquipeCollection(Collection<ParticipanteEquipe> participanteEquipeCollection) {
        this.participanteEquipeCollection = participanteEquipeCollection;
    }

    public Collection<Salas> getSalasCollection() {
        return salasCollection;
    }

    public void setSalasCollection(Collection<Salas> salasCollection) {
        this.salasCollection = salasCollection;
    }

    public Collection<Equipes> getEquipesCollection() {
        return equipesCollection;
    }

    public void setEquipesCollection(Collection<Equipes> equipesCollection) {
        this.equipesCollection = equipesCollection;
    }

    public Collection<Subeventos> getSubeventosCollection() {
        return subeventosCollection;
    }

    public void setSubeventosCollection(Collection<Subeventos> subeventosCollection) {
        this.subeventosCollection = subeventosCollection;
    }

    public Collection<Editais> getEditaisCollection() {
        return editaisCollection;
    }

    public void setEditaisCollection(Collection<Editais> editaisCollection) {
        this.editaisCollection = editaisCollection;
    }

    public Collection<Patrocinioimagens> getPatrocinioimagensCollection() {
        return patrocinioimagensCollection;
    }

    public void setPatrocinioimagensCollection(Collection<Patrocinioimagens> patrocinioimagensCollection) {
        this.patrocinioimagensCollection = patrocinioimagensCollection;
    }

    public Collection<Crachas> getCrachasCollection() {
        return crachasCollection;
    }

    public void setCrachasCollection(Collection<Crachas> crachasCollection) {
        this.crachasCollection = crachasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idstatus != null ? idstatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.idstatus == null && other.idstatus != null) || (this.idstatus != null && !this.idstatus.equals(other.idstatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Status[ idstatus=" + idstatus + " ]";
    }
    
}
