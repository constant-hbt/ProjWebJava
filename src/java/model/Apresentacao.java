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
@Table(name = "apresentacao", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Apresentacao.findAll", query = "SELECT a FROM Apresentacao a")})
public class Apresentacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idapresentacao")
    private Integer idapresentacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricaoapresentacao")
    private String descricaoapresentacao;
    @Size(max = 1000)
    @Column(name = "priavaliador")
    private String priavaliador;
    @Size(max = 1000)
    @Column(name = "segavaliador")
    private String segavaliador;
    @JoinColumn(name = "idsubevento", referencedColumnName = "idsubevento")
    @ManyToOne(optional = false)
    private Subeventos subeventos;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Apresentacao() {
    }

    public Apresentacao(Integer idapresentacao) {
        this.idapresentacao = idapresentacao;
    }

    public Apresentacao(Integer idapresentacao, String descricaoapresentacao) {
        this.idapresentacao = idapresentacao;
        this.descricaoapresentacao = descricaoapresentacao;
    }

    public Integer getIdapresentacao() {
        return idapresentacao;
    }

    public void setIdapresentacao(Integer idapresentacao) {
        this.idapresentacao = idapresentacao;
    }

    public String getDescricaoapresentacao() {
        return descricaoapresentacao;
    }

    public void setDescricaoapresentacao(String descricaoapresentacao) {
        this.descricaoapresentacao = descricaoapresentacao;
    }

    public String getPriavaliador() {
        return priavaliador;
    }

    public void setPriavaliador(String priavaliador) {
        this.priavaliador = priavaliador;
    }

    public String getSegavaliador() {
        return segavaliador;
    }

    public void setSegavaliador(String segavaliador) {
        this.segavaliador = segavaliador;
    }

    public Subeventos getSubeventos() {
        return subeventos;
    }

    public void setSubeventos(Subeventos subeventos) {
        this.subeventos = subeventos;
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
        hash += (idapresentacao != null ? idapresentacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apresentacao)) {
            return false;
        }
        Apresentacao other = (Apresentacao) object;
        if ((this.idapresentacao == null && other.idapresentacao != null) || (this.idapresentacao != null && !this.idapresentacao.equals(other.idapresentacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Apresentacao[ idapresentacao=" + idapresentacao + " ]";
    }
    
}
