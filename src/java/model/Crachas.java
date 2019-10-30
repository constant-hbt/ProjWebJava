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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henrique
 */
@Entity
@Table(name = "crachas", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Crachas.findAll", query = "SELECT c FROM Crachas c")})
public class Crachas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcracha")
    private Integer idcracha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "idparticipante", referencedColumnName = "idparticipante")
    @ManyToOne(optional = false)
    private Participantes participantes;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;

    public Crachas() {
    }

    public Crachas(Integer idcracha) {
        this.idcracha = idcracha;
    }

    public Crachas(Integer idcracha, String url) {
        this.idcracha = idcracha;
        this.url = url;
    }

    public Integer getIdcracha() {
        return idcracha;
    }

    public void setIdcracha(Integer idcracha) {
        this.idcracha = idcracha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcracha != null ? idcracha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crachas)) {
            return false;
        }
        Crachas other = (Crachas) object;
        if ((this.idcracha == null && other.idcracha != null) || (this.idcracha != null && !this.idcracha.equals(other.idcracha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Crachas[ idcracha=" + idcracha + " ]";
    }
    
}
