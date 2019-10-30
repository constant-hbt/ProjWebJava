/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Status;
import model.Subeventos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Salas;

/**
 *
 * @author henrique
 */
public class SalasJpaController implements Serializable {

    public SalasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salas salas) throws RollbackFailureException, Exception {
        if (salas.getSubeventosCollection() == null) {
            salas.setSubeventosCollection(new ArrayList<Subeventos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status status = salas.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                salas.setStatus(status);
            }
            Collection<Subeventos> attachedSubeventosCollection = new ArrayList<Subeventos>();
            for (Subeventos subeventosCollectionSubeventosToAttach : salas.getSubeventosCollection()) {
                subeventosCollectionSubeventosToAttach = em.getReference(subeventosCollectionSubeventosToAttach.getClass(), subeventosCollectionSubeventosToAttach.getIdsubevento());
                attachedSubeventosCollection.add(subeventosCollectionSubeventosToAttach);
            }
            salas.setSubeventosCollection(attachedSubeventosCollection);
            em.persist(salas);
            if (status != null) {
                status.getSalasCollection().add(salas);
                status = em.merge(status);
            }
            for (Subeventos subeventosCollectionSubeventos : salas.getSubeventosCollection()) {
                Salas oldSalasOfSubeventosCollectionSubeventos = subeventosCollectionSubeventos.getSalas();
                subeventosCollectionSubeventos.setSalas(salas);
                subeventosCollectionSubeventos = em.merge(subeventosCollectionSubeventos);
                if (oldSalasOfSubeventosCollectionSubeventos != null) {
                    oldSalasOfSubeventosCollectionSubeventos.getSubeventosCollection().remove(subeventosCollectionSubeventos);
                    oldSalasOfSubeventosCollectionSubeventos = em.merge(oldSalasOfSubeventosCollectionSubeventos);
                }
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

    public void edit(Salas salas) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salas persistentSalas = em.find(Salas.class, salas.getIdsala());
            Status statusOld = persistentSalas.getStatus();
            Status statusNew = salas.getStatus();
            Collection<Subeventos> subeventosCollectionOld = persistentSalas.getSubeventosCollection();
            Collection<Subeventos> subeventosCollectionNew = salas.getSubeventosCollection();
            List<String> illegalOrphanMessages = null;
            for (Subeventos subeventosCollectionOldSubeventos : subeventosCollectionOld) {
                if (!subeventosCollectionNew.contains(subeventosCollectionOldSubeventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subeventos " + subeventosCollectionOldSubeventos + " since its salas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                salas.setStatus(statusNew);
            }
            Collection<Subeventos> attachedSubeventosCollectionNew = new ArrayList<Subeventos>();
            for (Subeventos subeventosCollectionNewSubeventosToAttach : subeventosCollectionNew) {
                subeventosCollectionNewSubeventosToAttach = em.getReference(subeventosCollectionNewSubeventosToAttach.getClass(), subeventosCollectionNewSubeventosToAttach.getIdsubevento());
                attachedSubeventosCollectionNew.add(subeventosCollectionNewSubeventosToAttach);
            }
            subeventosCollectionNew = attachedSubeventosCollectionNew;
            salas.setSubeventosCollection(subeventosCollectionNew);
            salas = em.merge(salas);
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getSalasCollection().remove(salas);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getSalasCollection().add(salas);
                statusNew = em.merge(statusNew);
            }
            for (Subeventos subeventosCollectionNewSubeventos : subeventosCollectionNew) {
                if (!subeventosCollectionOld.contains(subeventosCollectionNewSubeventos)) {
                    Salas oldSalasOfSubeventosCollectionNewSubeventos = subeventosCollectionNewSubeventos.getSalas();
                    subeventosCollectionNewSubeventos.setSalas(salas);
                    subeventosCollectionNewSubeventos = em.merge(subeventosCollectionNewSubeventos);
                    if (oldSalasOfSubeventosCollectionNewSubeventos != null && !oldSalasOfSubeventosCollectionNewSubeventos.equals(salas)) {
                        oldSalasOfSubeventosCollectionNewSubeventos.getSubeventosCollection().remove(subeventosCollectionNewSubeventos);
                        oldSalasOfSubeventosCollectionNewSubeventos = em.merge(oldSalasOfSubeventosCollectionNewSubeventos);
                    }
                }
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
                Integer id = salas.getIdsala();
                if (findSalas(id) == null) {
                    throw new NonexistentEntityException("The salas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Salas salas;
            try {
                salas = em.getReference(Salas.class, id);
                salas.getIdsala();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Subeventos> subeventosCollectionOrphanCheck = salas.getSubeventosCollection();
            for (Subeventos subeventosCollectionOrphanCheckSubeventos : subeventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Salas (" + salas + ") cannot be destroyed since the Subeventos " + subeventosCollectionOrphanCheckSubeventos + " in its subeventosCollection field has a non-nullable salas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Status status = salas.getStatus();
            if (status != null) {
                status.getSalasCollection().remove(salas);
                status = em.merge(status);
            }
            em.remove(salas);
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

    public List<Salas> findSalasEntities() {
        return findSalasEntities(true, -1, -1);
    }

    public List<Salas> findSalasEntities(int maxResults, int firstResult) {
        return findSalasEntities(false, maxResults, firstResult);
    }

    private List<Salas> findSalasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salas.class));
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

    public Salas findSalas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salas.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salas> rt = cq.from(Salas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
