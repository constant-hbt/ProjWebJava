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
@Table(name = "subeventos", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Subeventos.findAll", query = "SELECT s FROM Subeventos s")
    , @NamedQuery(name = "Subeventos.findByIdsubevento", query = "SELECT s FROM Subeventos s WHERE s.idsubevento = :idsubevento")
    , @NamedQuery(name = "Subeventos.findByNome", query = "SELECT s FROM Subeventos s WHERE s.nome = :nome")
    , @NamedQuery(name = "Subeventos.findByDescricao", query = "SELECT s FROM Subeventos s WHERE s.descricao = :descricao")
    , @NamedQuery(name = "Subeventos.findByDatahorainicio", query = "SELECT s FROM Subeventos s WHERE s.datahorainicio = :datahorainicio")
    , @NamedQuery(name = "Subeventos.findByDatahorafim", query = "SELECT s FROM Subeventos s WHERE s.datahorafim = :datahorafim")
    , @NamedQuery(name = "Subeventos.findByDatafiminsc", query = "SELECT s FROM Subeventos s WHERE s.datafiminsc = :datafiminsc")
    , @NamedQuery(name = "Subeventos.findByDatainicioinsc", query = "SELECT s FROM Subeventos s WHERE s.datainicioinsc = :datainicioinsc")
    , @NamedQuery(name = "Subeventos.findByQtdemin", query = "SELECT s FROM Subeventos s WHERE s.qtdemin = :qtdemin")
    , @NamedQuery(name = "Subeventos.findByQtdemax", query = "SELECT s FROM Subeventos s WHERE s.qtdemax = :qtdemax")
    , @NamedQuery(name = "Subeventos.findByQtdemaxequipes", query = "SELECT s FROM Subeventos s WHERE s.qtdemaxequipes = :qtdemaxequipes")})
public class Subeventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsubevento")
    private Integer idsubevento;
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
    @Size(min = 1, max = 16)
    @Column(name = "datahorainicio")
    private String datahorainicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "datahorafim")
    private String datahorafim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datafiminsc")
    @Temporal(TemporalType.DATE)
    private Date datafiminsc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datainicioinsc")
    @Temporal(TemporalType.DATE)
    private Date datainicioinsc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qtdemin")
    private int qtdemin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qtdemax")
    private int qtdemax;
    @Column(name = "qtdemaxequipes")
    private Integer qtdemaxequipes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subeventos")
    private Collection<InscricaoEquipeSub> inscricaoEquipeSubCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subeventos")
    private Collection<InscricaoPartSubeve> inscricaoPartSubeveCollection;
    @OneToMany(mappedBy = "subeventos")
    private Collection<Midias> midiasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subeventos")
    private Collection<PresencasSub> presencasSubCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subeventos")
    private Collection<PresencasEquipeSub> presencasEquipeSubCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subeventos")
    private Collection<Submissao> submissaoCollection;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "idsala", referencedColumnName = "idsala")
    @ManyToOne(optional = false)
    private Salas salas;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subeventos")
    private Collection<Apresentacao> apresentacaoCollection;

    public Subeventos() {
    }

    public Subeventos(Integer idsubevento) {
        this.idsubevento = idsubevento;
    }

    public Subeventos(Integer idsubevento, String nome, String descricao, String datahorainicio, String datahorafim, Date datafiminsc, Date datainicioinsc, int qtdemin, int qtdemax) {
        this.idsubevento = idsubevento;
        this.nome = nome;
        this.descricao = descricao;
        this.datahorainicio = datahorainicio;
        this.datahorafim = datahorafim;
        this.datafiminsc = datafiminsc;
        this.datainicioinsc = datainicioinsc;
        this.qtdemin = qtdemin;
        this.qtdemax = qtdemax;
    }

    public Integer getIdsubevento() {
        return idsubevento;
    }

    public void setIdsubevento(Integer idsubevento) {
        this.idsubevento = idsubevento;
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

    public String getDatahorainicio() {
        return datahorainicio;
    }

    public void setDatahorainicio(String datahorainicio) {
        this.datahorainicio = datahorainicio;
    }

    public String getDatahorafim() {
        return datahorafim;
    }

    public void setDatahorafim(String datahorafim) {
        this.datahorafim = datahorafim;
    }

    public Date getDatafiminsc() {
        return datafiminsc;
    }

    public void setDatafiminsc(Date datafiminsc) {
        this.datafiminsc = datafiminsc;
    }

    public Date getDatainicioinsc() {
        return datainicioinsc;
    }

    public void setDatainicioinsc(Date datainicioinsc) {
        this.datainicioinsc = datainicioinsc;
    }

    public int getQtdemin() {
        return qtdemin;
    }

    public void setQtdemin(int qtdemin) {
        this.qtdemin = qtdemin;
    }

    public int getQtdemax() {
        return qtdemax;
    }

    public void setQtdemax(int qtdemax) {
        this.qtdemax = qtdemax;
    }

    public Integer getQtdemaxequipes() {
        return qtdemaxequipes;
    }

    public void setQtdemaxequipes(Integer qtdemaxequipes) {
        this.qtdemaxequipes = qtdemaxequipes;
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

    public Collection<PresencasSub> getPresencasSubCollection() {
        return presencasSubCollection;
    }

    public void setPresencasSubCollection(Collection<PresencasSub> presencasSubCollection) {
        this.presencasSubCollection = presencasSubCollection;
    }

    public Collection<PresencasEquipeSub> getPresencasEquipeSubCollection() {
        return presencasEquipeSubCollection;
    }

    public void setPresencasEquipeSubCollection(Collection<PresencasEquipeSub> presencasEquipeSubCollection) {
        this.presencasEquipeSubCollection = presencasEquipeSubCollection;
    }

    public Collection<Submissao> getSubmissaoCollection() {
        return submissaoCollection;
    }

    public void setSubmissaoCollection(Collection<Submissao> submissaoCollection) {
        this.submissaoCollection = submissaoCollection;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public Salas getSalas() {
        return salas;
    }

    public void setSalas(Salas salas) {
        this.salas = salas;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<Apresentacao> getApresentacaoCollection() {
        return apresentacaoCollection;
    }

    public void setApresentacaoCollection(Collection<Apresentacao> apresentacaoCollection) {
        this.apresentacaoCollection = apresentacaoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsubevento != null ? idsubevento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subeventos)) {
            return false;
        }
        Subeventos other = (Subeventos) object;
        if ((this.idsubevento == null && other.idsubevento != null) || (this.idsubevento != null && !this.idsubevento.equals(other.idsubevento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Subeventos[ idsubevento=" + idsubevento + " ]";
    }
    
}
