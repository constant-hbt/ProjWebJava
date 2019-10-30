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
@Table(name = "patrocinioimagens", catalog = "proj_web", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Patrocinioimagens.findAll", query = "SELECT p FROM Patrocinioimagens p")})
public class Patrocinioimagens implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpatrocinioimagens")
    private Integer idpatrocinioimagens;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pathimagem")
    private String pathimagem;
    @JoinColumn(name = "idpatrocinio", referencedColumnName = "idpatrocinio")
    @ManyToOne(optional = false)
    private Patrocinios patrocinios;
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;

    public Patrocinioimagens() {
    }

    public Patrocinioimagens(Integer idpatrocinioimagens) {
        this.idpatrocinioimagens = idpatrocinioimagens;
    }

    public Patrocinioimagens(Integer idpatrocinioimagens, String pathimagem) {
        this.idpatrocinioimagens = idpatrocinioimagens;
        this.pathimagem = pathimagem;
    }

    public Integer getIdpatrocinioimagens() {
        return idpatrocinioimagens;
    }

    public void setIdpatrocinioimagens(Integer idpatrocinioimagens) {
        this.idpatrocinioimagens = idpatrocinioimagens;
    }

    public String getPathimagem() {
        return pathimagem;
    }

    public void setPathimagem(String pathimagem) {
        this.pathimagem = pathimagem;
    }

    public Patrocinios getPatrocinios() {
        return patrocinios;
    }

    public void setPatrocinios(Patrocinios patrocinios) {
        this.patrocinios = patrocinios;
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
        hash += (idpatrocinioimagens != null ? idpatrocinioimagens.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patrocinioimagens)) {
            return false;
        }
        Patrocinioimagens other = (Patrocinioimagens) object;
        if ((this.idpatrocinioimagens == null && other.idpatrocinioimagens != null) || (this.idpatrocinioimagens != null && !this.idpatrocinioimagens.equals(other.idpatrocinioimagens))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Patrocinioimagens[ idpatrocinioimagens=" + idpatrocinioimagens + " ]";
    }
    
}
