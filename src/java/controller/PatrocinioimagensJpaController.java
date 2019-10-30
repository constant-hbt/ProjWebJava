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
import model.Patrocinioimagens;
import model.Patrocinios;
import model.Status;

/**
 *
 * @author henrique
 */
public class PatrocinioimagensJpaController implements Serializable {

    public PatrocinioimagensJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patrocinioimagens patrocinioimagens) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Patrocinios patrocinios = patrocinioimagens.getPatrocinios();
            if (patrocinios != null) {
                patrocinios = em.getReference(patrocinios.getClass(), patrocinios.getIdpatrocinio());
                patrocinioimagens.setPatrocinios(patrocinios);
            }
            Status status = patrocinioimagens.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                patrocinioimagens.setStatus(status);
            }
            em.persist(patrocinioimagens);
            if (patrocinios != null) {
                patrocinios.getPatrocinioimagensCollection().add(patrocinioimagens);
                patrocinios = em.merge(patrocinios);
            }
            if (status != null) {
                status.getPatrocinioimagensCollection().add(patrocinioimagens);
                status = em.merge(status);
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

    public void edit(Patrocinioimagens patrocinioimagens) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Patrocinioimagens persistentPatrocinioimagens = em.find(Patrocinioimagens.class, patrocinioimagens.getIdpatrocinioimagens());
            Patrocinios patrociniosOld = persistentPatrocinioimagens.getPatrocinios();
            Patrocinios patrociniosNew = patrocinioimagens.getPatrocinios();
            Status statusOld = persistentPatrocinioimagens.getStatus();
            Status statusNew = patrocinioimagens.getStatus();
            if (patrociniosNew != null) {
                patrociniosNew = em.getReference(patrociniosNew.getClass(), patrociniosNew.getIdpatrocinio());
                patrocinioimagens.setPatrocinios(patrociniosNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                patrocinioimagens.setStatus(statusNew);
            }
            patrocinioimagens = em.merge(patrocinioimagens);
            if (patrociniosOld != null && !patrociniosOld.equals(patrociniosNew)) {
                patrociniosOld.getPatrocinioimagensCollection().remove(patrocinioimagens);
                patrociniosOld = em.merge(patrociniosOld);
            }
            if (patrociniosNew != null && !patrociniosNew.equals(patrociniosOld)) {
                patrociniosNew.getPatrocinioimagensCollection().add(patrocinioimagens);
                patrociniosNew = em.merge(patrociniosNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getPatrocinioimagensCollection().remove(patrocinioimagens);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getPatrocinioimagensCollection().add(patrocinioimagens);
                statusNew = em.merge(statusNew);
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
                Integer id = patrocinioimagens.getIdpatrocinioimagens();
                if (findPatrocinioimagens(id) == null) {
                    throw new NonexistentEntityException("The patrocinioimagens with id " + id + " no longer exists.");
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
            Patrocinioimagens patrocinioimagens;
            try {
                patrocinioimagens = em.getReference(Patrocinioimagens.class, id);
                patrocinioimagens.getIdpatrocinioimagens();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patrocinioimagens with id " + id + " no longer exists.", enfe);
            }
            Patrocinios patrocinios = patrocinioimagens.getPatrocinios();
            if (patrocinios != null) {
                patrocinios.getPatrocinioimagensCollection().remove(patrocinioimagens);
                patrocinios = em.merge(patrocinios);
            }
            Status status = patrocinioimagens.getStatus();
            if (status != null) {
                status.getPatrocinioimagensCollection().remove(patrocinioimagens);
                status = em.merge(status);
            }
            em.remove(patrocinioimagens);
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

    public List<Patrocinioimagens> findPatrocinioimagensEntities() {
        return findPatrocinioimagensEntities(true, -1, -1);
    }

    public List<Patrocinioimagens> findPatrocinioimagensEntities(int maxResults, int firstResult) {
        return findPatrocinioimagensEntities(false, maxResults, firstResult);
    }

    private List<Patrocinioimagens> findPatrocinioimagensEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patrocinioimagens.class));
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

    public Patrocinioimagens findPatrocinioimagens(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patrocinioimagens.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatrocinioimagensCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patrocinioimagens> rt = cq.from(Patrocinioimagens.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
