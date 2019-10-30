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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "equipes", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Equipes.findAll", query = "SELECT e FROM Equipes e")})
public class Equipes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idequipe")
    private Integer idequipe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 250)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idlider")
    private long idlider;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipes")
    private Collection<InscricaoEquipeSub> inscricaoEquipeSubCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipes")
    private Collection<PresencasEquipeSub> presencasEquipeSubCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipes")
    private Collection<ParticipanteEquipe> participanteEquipeCollection;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;

    public Equipes() {
    }

    public Equipes(Integer idequipe) {
        this.idequipe = idequipe;
    }

    public Equipes(Integer idequipe, String nome, long idlider) {
        this.idequipe = idequipe;
        this.nome = nome;
        this.idlider = idlider;
    }

    public Integer getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(Integer idequipe) {
        this.idequipe = idequipe;
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

    public long getIdlider() {
        return idlider;
    }

    public void setIdlider(long idlider) {
        this.idlider = idlider;
    }

    public Collection<InscricaoEquipeSub> getInscricaoEquipeSubCollection() {
        return inscricaoEquipeSubCollection;
    }

    public void setInscricaoEquipeSubCollection(Collection<InscricaoEquipeSub> inscricaoEquipeSubCollection) {
        this.inscricaoEquipeSubCollection = inscricaoEquipeSubCollection;
    }

    public Collection<PresencasEquipeSub> getPresencasEquipeSubCollection() {
        return presencasEquipeSubCollection;
    }

    public void setPresencasEquipeSubCollection(Collection<PresencasEquipeSub> presencasEquipeSubCollection) {
        this.presencasEquipeSubCollection = presencasEquipeSubCollection;
    }

    public Collection<ParticipanteEquipe> getParticipanteEquipeCollection() {
        return participanteEquipeCollection;
    }

    public void setParticipanteEquipeCollection(Collection<ParticipanteEquipe> participanteEquipeCollection) {
        this.participanteEquipeCollection = participanteEquipeCollection;
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
        hash += (idequipe != null ? idequipe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipes)) {
            return false;
        }
        Equipes other = (Equipes) object;
        if ((this.idequipe == null && other.idequipe != null) || (this.idequipe != null && !this.idequipe.equals(other.idequipe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Equipes[ idequipe=" + idequipe + " ]";
    }
    
}
