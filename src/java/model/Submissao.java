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
@Table(name = "submissao", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Submissao.findAll", query = "SELECT s FROM Submissao s")})
public class Submissao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsubmissao")
    private Integer idsubmissao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricaosubmissao")
    private String descricaosubmissao;
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

    public Submissao() {
    }

    public Submissao(Integer idsubmissao) {
        this.idsubmissao = idsubmissao;
    }

    public Submissao(Integer idsubmissao, String descricaosubmissao) {
        this.idsubmissao = idsubmissao;
        this.descricaosubmissao = descricaosubmissao;
    }

    public Integer getIdsubmissao() {
        return idsubmissao;
    }

    public void setIdsubmissao(Integer idsubmissao) {
        this.idsubmissao = idsubmissao;
    }

    public String getDescricaosubmissao() {
        return descricaosubmissao;
    }

    public void setDescricaosubmissao(String descricaosubmissao) {
        this.descricaosubmissao = descricaosubmissao;
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
        hash += (idsubmissao != null ? idsubmissao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Submissao)) {
            return false;
        }
        Submissao other = (Submissao) object;
        if ((this.idsubmissao == null && other.idsubmissao != null) || (this.idsubmissao != null && !this.idsubmissao.equals(other.idsubmissao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Submissao[ idsubmissao=" + idsubmissao + " ]";
    }
    
}
