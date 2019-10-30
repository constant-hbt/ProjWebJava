/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "eventos", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Eventos.findAll", query = "SELECT e FROM Eventos e")})
public class Eventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idevento")
    private Integer idevento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "local")
    private String local;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datainicio")
    @Temporal(TemporalType.DATE)
    private Date datainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datafim")
    @Temporal(TemporalType.DATE)
    private Date datafim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datainicioinsc")
    @Temporal(TemporalType.DATE)
    private Date datainicioinsc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datafiminsc")
    @Temporal(TemporalType.DATE)
    private Date datafiminsc;
    @OneToMany(mappedBy = "eventos")
    private Collection<Midias> midiasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<InscricaoPartEvento> inscricaoPartEventoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<PresencasEve> presencasEveCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Certificados> certificadosCollection;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Organizadores> organizadoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Patrocinios> patrociniosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Subeventos> subeventosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Editais> editaisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Crachas> crachasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Participantes> participantesCollection;

    public Eventos() {
    }

    public Eventos(Integer idevento) {
        this.idevento = idevento;
    }

    public Eventos(Integer idevento, String nome, String descricao, String local, Date datainicio, Date datafim, Date datainicioinsc, Date datafiminsc) {
        this.idevento = idevento;
        this.nome = nome;
        this.descricao = descricao;
        this.local = local;
        this.datainicio = datainicio;
        this.datafim = datafim;
        this.datainicioinsc = datainicioinsc;
        this.datafiminsc = datafiminsc;
    }

    public Integer getIdevento() {
        return idevento;
    }

    public void setIdevento(Integer idevento) {
        this.idevento = idevento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Date getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(Date datainicio) {
        this.datainicio = datainicio;
    }

    public Date getDatafim() {
        return datafim;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public Date getDatainicioinsc() {
        return datainicioinsc;
    }

    public void setDatainicioinsc(Date datainicioinsc) {
        this.datainicioinsc = datainicioinsc;
    }

    public Date getDatafiminsc() {
        return datafiminsc;
    }

    public void setDatafiminsc(Date datafiminsc) {
        this.datafiminsc = datafiminsc;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<Organizadores> getOrganizadoresCollection() {
        return organizadoresCollection;
    }

    public void setOrganizadoresCollection(Collection<Organizadores> organizadoresCollection) {
        this.organizadoresCollection = organizadoresCollection;
    }

    public Collection<Patrocinios> getPatrociniosCollection() {
        return patrociniosCollection;
    }

    public void setPatrociniosCollection(Collection<Patrocinios> patrociniosCollection) {
        this.patrociniosCollection = patrociniosCollection;
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

    public Collection<Crachas> getCrachasCollection() {
        return crachasCollection;
    }

    public void setCrachasCollection(Collection<Crachas> crachasCollection) {
        this.crachasCollection = crachasCollection;
    }

    public Collection<Participantes> getParticipantesCollection() {
        return participantesCollection;
    }

    public void setParticipantesCollection(Collection<Participantes> participantesCollection) {
        this.participantesCollection = participantesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idevento != null ? idevento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventos)) {
            return false;
        }
        Eventos other = (Eventos) object;
        if ((this.idevento == null && other.idevento != null) || (this.idevento != null && !this.idevento.equals(other.idevento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Eventos[ idevento=" + idevento + " ]";
    }
    
}
