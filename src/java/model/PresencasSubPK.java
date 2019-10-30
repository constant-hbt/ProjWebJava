/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author henrique
 */
@Embeddable
public class PresencasSubPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idparticipante")
    private int idparticipante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idsubevento")
    private int idsubevento;

    public PresencasSubPK() {
    }

    public PresencasSubPK(int idparticipante, int idsubevento) {
        this.idparticipante = idparticipante;
        this.idsubevento = idsubevento;
    }

    public int getIdparticipante() {
        return idparticipante;
    }

    public void setIdparticipante(int idparticipante) {
        this.idparticipante = idparticipante;
    }

    public int getIdsubevento() {
        return idsubevento;
    }

    public void setIdsubevento(int idsubevento) {
        this.idsubevento = idsubevento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idparticipante;
        hash += (int) idsubevento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencasSubPK)) {
            return false;
        }
        PresencasSubPK other = (PresencasSubPK) object;
        if (this.idparticipante != other.idparticipante) {
            return false;
        }
        if (this.idsubevento != other.idsubevento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PresencasSubPK[ idparticipante=" + idparticipante + ", idsubevento=" + idsubevento + " ]";
    }
    
}
