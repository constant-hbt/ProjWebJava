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
public class PresencasEquipeSubPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idequipe")
    private int idequipe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idsubevento")
    private int idsubevento;

    public PresencasEquipeSubPK() {
    }

    public PresencasEquipeSubPK(int idequipe, int idsubevento) {
        this.idequipe = idequipe;
        this.idsubevento = idsubevento;
    }

    public int getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(int idequipe) {
        this.idequipe = idequipe;
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
        hash += (int) idequipe;
        hash += (int) idsubevento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencasEquipeSubPK)) {
            return false;
        }
        PresencasEquipeSubPK other = (PresencasEquipeSubPK) object;
        if (this.idequipe != other.idequipe) {
            return false;
        }
        if (this.idsubevento != other.idsubevento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PresencasEquipeSubPK[ idequipe=" + idequipe + ", idsubevento=" + idsubevento + " ]";
    }
    
}
