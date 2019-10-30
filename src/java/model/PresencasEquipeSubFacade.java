/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author henrique
 */
@Stateless
public class PresencasEquipeSubFacade extends AbstractFacade<PresencasEquipeSub> {

    @PersistenceContext(unitName = "ProjWebHPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PresencasEquipeSubFacade() {
        super(PresencasEquipeSub.class);
    }
    
}
