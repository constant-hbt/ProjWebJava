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
@Table(name = "organizadores", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Organizadores.findAll", query = "SELECT o FROM Organizadores o")})
public class Organizadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorganizador")
    private Integer idorganizador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "funcao")
    private String funcao;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Organizadores() {
    }

    public Organizadores(Integer idorganizador) {
        this.idorganizador = idorganizador;
    }

    public Organizadores(Integer idorganizador, String funcao) {
        this.idorganizador = idorganizador;
        this.funcao = funcao;
    }

    public Integer getIdorganizador() {
        return idorganizador;
    }

    public void setIdorganizador(Integer idorganizador) {
        this.idorganizador = idorganizador;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
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
        hash += (idorganizador != null ? idorganizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organizadores)) {
            return false;
        }
        Organizadores other = (Organizadores) object;
        if ((this.idorganizador == null && other.idorganizador != null) || (this.idorganizador != null && !this.idorganizador.equals(other.idorganizador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Organizadores[ idorganizador=" + idorganizador + " ]";
    }
    
}
