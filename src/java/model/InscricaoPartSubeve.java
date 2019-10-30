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
@Table(name = "inscricao_part_subeve", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "InscricaoPartSubeve.findAll", query = "SELECT i FROM InscricaoPartSubeve i")})
public class InscricaoPartSubeve implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinscricao")
    private Integer idinscricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @JoinColumn(name = "idparticipante", referencedColumnName = "idparticipante")
    @ManyToOne(optional = false)
    private Participantes participantes;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "idsubevento", referencedColumnName = "idsubevento")
    @ManyToOne(optional = false)
    private Subeventos subeventos;

    public InscricaoPartSubeve() {
    }

    public InscricaoPartSubeve(Integer idinscricao) {
        this.idinscricao = idinscricao;
    }

    public InscricaoPartSubeve(Integer idinscricao, Date datahora) {
        this.idinscricao = idinscricao;
        this.datahora = datahora;
    }

    public Integer getIdinscricao() {
        return idinscricao;
    }

    public void setIdinscricao(Integer idinscricao) {
        this.idinscricao = idinscricao;
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
        hash += (idinscricao != null ? idinscricao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscricaoPartSubeve)) {
            return false;
        }
        InscricaoPartSubeve other = (InscricaoPartSubeve) object;
        if ((this.idinscricao == null && other.idinscricao != null) || (this.idinscricao != null && !this.idinscricao.equals(other.idinscricao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.InscricaoPartSubeve[ idinscricao=" + idinscricao + " ]";
    }
    
}
