/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Equipes;
import model.PresencasEquipeSub;
import model.PresencasEquipeSubPK;
import model.Subeventos;

/**
 *
 * @author henrique
 */
public class PresencasEquipeSubJpaController implements Serializable {

    public PresencasEquipeSubJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PresencasEquipeSub presencasEquipeSub) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (presencasEquipeSub.getPresencasEquipeSubPK() == null) {
            presencasEquipeSub.setPresencasEquipeSubPK(new PresencasEquipeSubPK());
        }
        presencasEquipeSub.getPresencasEquipeSubPK().setIdequipe(presencasEquipeSub.getEquipes().getIdequipe());
        presencasEquipeSub.getPresencasEquipeSubPK().setIdsubevento(presencasEquipeSub.getSubeventos().getIdsubevento());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipes equipes = presencasEquipeSub.getEquipes();
            if (equipes != null) {
                equipes = em.getReference(equipes.getClass(), equipes.getIdequipe());
                presencasEquipeSub.setEquipes(equipes);
            }
            Subeventos subeventos = presencasEquipeSub.getSubeventos();
            if (subeventos != null) {
                subeventos = em.getReference(subeventos.getClass(), subeventos.getIdsubevento());
                presencasEquipeSub.setSubeventos(subeventos);
            }
            em.persist(presencasEquipeSub);
            if (equipes != null) {
                equipes.getPresencasEquipeSubCollection().add(presencasEquipeSub);
                equipes = em.merge(equipes);
            }
            if (subeventos != null) {
                subeventos.getPresencasEquipeSubCollection().add(presencasEquipeSub);
                subeventos = em.merge(subeventos);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPresencasEquipeSub(presencasEquipeSub.getPresencasEquipeSubPK()) != null) {
                throw new PreexistingEntityException("PresencasEquipeSub " + presencasEquipeSub + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PresencasEquipeSub presencasEquipeSub) throws NonexistentEntityException, RollbackFailureException, Exception {
        presencasEquipeSub.getPresencasEquipeSubPK().setIdequipe(presencasEquipeSub.getEquipes().getIdequipe());
        presencasEquipeSub.getPresencasEquipeSubPK().setIdsubevento(presencasEquipeSub.getSubeventos().getIdsubevento());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PresencasEquipeSub persistentPresencasEquipeSub = em.find(PresencasEquipeSub.class, presencasEquipeSub.getPresencasEquipeSubPK());
            Equipes equipesOld = persistentPresencasEquipeSub.getEquipes();
            Equipes equipesNew = presencasEquipeSub.getEquipes();
            Subeventos subeventosOld = persistentPresencasEquipeSub.getSubeventos();
            Subeventos subeventosNew = presencasEquipeSub.getSubeventos();
            if (equipesNew != null) {
                equipesNew = em.getReference(equipesNew.getClass(), equipesNew.getIdequipe());
                presencasEquipeSub.setEquipes(equipesNew);
            }
            if (subeventosNew != null) {
                subeventosNew = em.getReference(subeventosNew.getClass(), subeventosNew.getIdsubevento());
                presencasEquipeSub.setSubeventos(subeventosNew);
            }
            presencasEquipeSub = em.merge(presencasEquipeSub);
            if (equipesOld != null && !equipesOld.equals(equipesNew)) {
                equipesOld.getPresencasEquipeSubCollection().remove(presencasEquipeSub);
                equipesOld = em.merge(equipesOld);
            }
            if (equipesNew != null && !equipesNew.equals(equipesOld)) {
                equipesNew.getPresencasEquipeSubCollection().add(presencasEquipeSub);
                equipesNew = em.merge(equipesNew);
            }
            if (subeventosOld != null && !subeventosOld.equals(subeventosNew)) {
                subeventosOld.getPresencasEquipeSubCollection().remove(presencasEquipeSub);
                subeventosOld = em.merge(subeventosOld);
            }
            if (subeventosNew != null && !subeventosNew.equals(subeventosOld)) {
                subeventosNew.getPresencasEquipeSubCollection().add(presencasEquipeSub);
                subeventosNew = em.merge(subeventosNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PresencasEquipeSubPK id = presencasEquipeSub.getPresencasEquipeSubPK();
                if (findPresencasEquipeSub(id) == null) {
                    throw new NonexistentEntityException("The presencasEquipeSub with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PresencasEquipeSubPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PresencasEquipeSub presencasEquipeSub;
            try {
                presencasEquipeSub = em.getReference(PresencasEquipeSub.class, id);
                presencasEquipeSub.getPresencasEquipeSubPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presencasEquipeSub with id " + id + " no longer exists.", enfe);
            }
            Equipes equipes = presencasEquipeSub.getEquipes();
            if (equipes != null) {
                equipes.getPresencasEquipeSubCollection().remove(presencasEquipeSub);
                equipes = em.merge(equipes);
            }
            Subeventos subeventos = presencasEquipeSub.getSubeventos();
            if (subeventos != null) {
                subeventos.getPresencasEquipeSubCollection().remove(presencasEquipeSub);
                subeventos = em.merge(subeventos);
            }
            em.remove(presencasEquipeSub);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PresencasEquipeSub> findPresencasEquipeSubEntities() {
        return findPresencasEquipeSubEntities(true, -1, -1);
    }

    public List<PresencasEquipeSub> findPresencasEquipeSubEntities(int maxResults, int firstResult) {
        return findPresencasEquipeSubEntities(false, maxResults, firstResult);
    }

    private List<PresencasEquipeSub> findPresencasEquipeSubEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PresencasEquipeSub.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PresencasEquipeSub findPresencasEquipeSub(PresencasEquipeSubPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PresencasEquipeSub.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresencasEquipeSubCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PresencasEquipeSub> rt = cq.from(PresencasEquipeSub.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
