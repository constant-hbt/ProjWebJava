/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
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
import model.InscricaoEquipeSub;
import model.Status;
import model.Subeventos;

/**
 *
 * @author henrique
 */
public class InscricaoEquipeSubJpaController implements Serializable {

    public InscricaoEquipeSubJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscricaoEquipeSub inscricaoEquipeSub) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipes equipes = inscricaoEquipeSub.getEquipes();
            if (equipes != null) {
                equipes = em.getReference(equipes.getClass(), equipes.getIdequipe());
                inscricaoEquipeSub.setEquipes(equipes);
            }
            Status status = inscricaoEquipeSub.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                inscricaoEquipeSub.setStatus(status);
            }
            Subeventos subeventos = inscricaoEquipeSub.getSubeventos();
            if (subeventos != null) {
                subeventos = em.getReference(subeventos.getClass(), subeventos.getIdsubevento());
                inscricaoEquipeSub.setSubeventos(subeventos);
            }
            em.persist(inscricaoEquipeSub);
            if (equipes != null) {
                equipes.getInscricaoEquipeSubCollection().add(inscricaoEquipeSub);
                equipes = em.merge(equipes);
            }
            if (status != null) {
                status.getInscricaoEquipeSubCollection().add(inscricaoEquipeSub);
                status = em.merge(status);
            }
            if (subeventos != null) {
                subeventos.getInscricaoEquipeSubCollection().add(inscricaoEquipeSub);
                subeventos = em.merge(subeventos);
            }
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

    public void edit(InscricaoEquipeSub inscricaoEquipeSub) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InscricaoEquipeSub persistentInscricaoEquipeSub = em.find(InscricaoEquipeSub.class, inscricaoEquipeSub.getIdequipesub());
            Equipes equipesOld = persistentInscricaoEquipeSub.getEquipes();
            Equipes equipesNew = inscricaoEquipeSub.getEquipes();
            Status statusOld = persistentInscricaoEquipeSub.getStatus();
            Status statusNew = inscricaoEquipeSub.getStatus();
            Subeventos subeventosOld = persistentInscricaoEquipeSub.getSubeventos();
            Subeventos subeventosNew = inscricaoEquipeSub.getSubeventos();
            if (equipesNew != null) {
                equipesNew = em.getReference(equipesNew.getClass(), equipesNew.getIdequipe());
                inscricaoEquipeSub.setEquipes(equipesNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                inscricaoEquipeSub.setStatus(statusNew);
            }
            if (subeventosNew != null) {
                subeventosNew = em.getReference(subeventosNew.getClass(), subeventosNew.getIdsubevento());
                inscricaoEquipeSub.setSubeventos(subeventosNew);
            }
            inscricaoEquipeSub = em.merge(inscricaoEquipeSub);
            if (equipesOld != null && !equipesOld.equals(equipesNew)) {
                equipesOld.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSub);
                equipesOld = em.merge(equipesOld);
            }
            if (equipesNew != null && !equipesNew.equals(equipesOld)) {
                equipesNew.getInscricaoEquipeSubCollection().add(inscricaoEquipeSub);
                equipesNew = em.merge(equipesNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSub);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getInscricaoEquipeSubCollection().add(inscricaoEquipeSub);
                statusNew = em.merge(statusNew);
            }
            if (subeventosOld != null && !subeventosOld.equals(subeventosNew)) {
                subeventosOld.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSub);
                subeventosOld = em.merge(subeventosOld);
            }
            if (subeventosNew != null && !subeventosNew.equals(subeventosOld)) {
                subeventosNew.getInscricaoEquipeSubCollection().add(inscricaoEquipeSub);
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
                Integer id = inscricaoEquipeSub.getIdequipesub();
                if (findInscricaoEquipeSub(id) == null) {
                    throw new NonexistentEntityException("The inscricaoEquipeSub with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InscricaoEquipeSub inscricaoEquipeSub;
            try {
                inscricaoEquipeSub = em.getReference(InscricaoEquipeSub.class, id);
                inscricaoEquipeSub.getIdequipesub();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscricaoEquipeSub with id " + id + " no longer exists.", enfe);
            }
            Equipes equipes = inscricaoEquipeSub.getEquipes();
            if (equipes != null) {
                equipes.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSub);
                equipes = em.merge(equipes);
            }
            Status status = inscricaoEquipeSub.getStatus();
            if (status != null) {
                status.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSub);
                status = em.merge(status);
            }
            Subeventos subeventos = inscricaoEquipeSub.getSubeventos();
            if (subeventos != null) {
                subeventos.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSub);
                subeventos = em.merge(subeventos);
            }
            em.remove(inscricaoEquipeSub);
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

    public List<InscricaoEquipeSub> findInscricaoEquipeSubEntities() {
        return findInscricaoEquipeSubEntities(true, -1, -1);
    }

    public List<InscricaoEquipeSub> findInscricaoEquipeSubEntities(int maxResults, int firstResult) {
        return findInscricaoEquipeSubEntities(false, maxResults, firstResult);
    }

    private List<InscricaoEquipeSub> findInscricaoEquipeSubEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscricaoEquipeSub.class));
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

    public InscricaoEquipeSub findInscricaoEquipeSub(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscricaoEquipeSub.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscricaoEquipeSubCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscricaoEquipeSub> rt = cq.from(InscricaoEquipeSub.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
