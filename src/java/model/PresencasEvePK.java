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
public class PresencasEvePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idparticipante")
    private int idparticipante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idevento")
    private int idevento;

    public PresencasEvePK() {
    }

    public PresencasEvePK(int idparticipante, int idevento) {
        this.idparticipante = idparticipante;
        this.idevento = idevento;
    }

    public int getIdparticipante() {
        return idparticipante;
    }

    public void setIdparticipante(int idparticipante) {
        this.idparticipante = idparticipante;
    }

    public int getIdevento() {
        return idevento;
    }

    public void setIdevento(int idevento) {
        this.idevento = idevento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idparticipante;
        hash += (int) idevento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencasEvePK)) {
            return false;
        }
        PresencasEvePK other = (PresencasEvePK) object;
        if (this.idparticipante != other.idparticipante) {
            return false;
        }
        if (this.idevento != other.idevento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PresencasEvePK[ idparticipante=" + idparticipante + ", idevento=" + idevento + " ]";
    }
    
}
