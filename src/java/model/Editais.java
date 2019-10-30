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
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "editais", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Editais.findAll", query = "SELECT e FROM Editais e")})
public class Editais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idedital")
    private Integer idedital;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataabertura")
    @Temporal(TemporalType.DATE)
    private Date dataabertura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataencerramento")
    @Temporal(TemporalType.DATE)
    private Date dataencerramento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "urlarquivo")
    private String urlarquivo;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Editais() {
    }

    public Editais(Integer idedital) {
        this.idedital = idedital;
    }

    public Editais(Integer idedital, String descricao, Date dataabertura, Date dataencerramento, String urlarquivo) {
        this.idedital = idedital;
        this.descricao = descricao;
        this.dataabertura = dataabertura;
        this.dataencerramento = dataencerramento;
        this.urlarquivo = urlarquivo;
    }

    public Integer getIdedital() {
        return idedital;
    }

    public void setIdedital(Integer idedital) {
        this.idedital = idedital;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataabertura() {
        return dataabertura;
    }

    public void setDataabertura(Date dataabertura) {
        this.dataabertura = dataabertura;
    }

    public Date getDataencerramento() {
        return dataencerramento;
    }

    public void setDataencerramento(Date dataencerramento) {
        this.dataencerramento = dataencerramento;
    }

    public String getUrlarquivo() {
        return urlarquivo;
    }

    public void setUrlarquivo(String urlarquivo) {
        this.urlarquivo = urlarquivo;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        hash += (idedital != null ? idedital.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Editais)) {
            return false;
        }
        Editais other = (Editais) object;
        if ((this.idedital == null && other.idedital != null) || (this.idedital != null && !this.idedital.equals(other.idedital))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Editais[ idedital=" + idedital + " ]";
    }
    
}
