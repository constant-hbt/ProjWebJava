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
import model.Eventos;
import model.Status;
import model.Patrocinioimagens;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Patrocinios;

/**
 *
 * @author henrique
 */
public class PatrociniosJpaController implements Serializable {

    public PatrociniosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patrocinios patrocinios) throws RollbackFailureException, Exception {
        if (patrocinios.getPatrocinioimagensCollection() == null) {
            patrocinios.setPatrocinioimagensCollection(new ArrayList<Patrocinioimagens>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = patrocinios.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                patrocinios.setEventos(eventos);
            }
            Status status = patrocinios.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                patrocinios.setStatus(status);
            }
            Collection<Patrocinioimagens> attachedPatrocinioimagensCollection = new ArrayList<Patrocinioimagens>();
            for (Patrocinioimagens patrocinioimagensCollectionPatrocinioimagensToAttach : patrocinios.getPatrocinioimagensCollection()) {
                patrocinioimagensCollectionPatrocinioimagensToAttach = em.getReference(patrocinioimagensCollectionPatrocinioimagensToAttach.getClass(), patrocinioimagensCollectionPatrocinioimagensToAttach.getIdpatrocinioimagens());
                attachedPatrocinioimagensCollection.add(patrocinioimagensCollectionPatrocinioimagensToAttach);
            }
            patrocinios.setPatrocinioimagensCollection(attachedPatrocinioimagensCollection);
            em.persist(patrocinios);
            if (eventos != null) {
                eventos.getPatrociniosCollection().add(patrocinios);
                eventos = em.merge(eventos);
            }
            if (status != null) {
                status.getPatrociniosCollection().add(patrocinios);
                status = em.merge(status);
            }
            for (Patrocinioimagens patrocinioimagensCollectionPatrocinioimagens : patrocinios.getPatrocinioimagensCollection()) {
                Patrocinios oldPatrociniosOfPatrocinioimagensCollectionPatrocinioimagens = patrocinioimagensCollectionPatrocinioimagens.getPatrocinios();
                patrocinioimagensCollectionPatrocinioimagens.setPatrocinios(patrocinios);
                patrocinioimagensCollectionPatrocinioimagens = em.merge(patrocinioimagensCollectionPatrocinioimagens);
                if (oldPatrociniosOfPatrocinioimagensCollectionPatrocinioimagens != null) {
                    oldPatrociniosOfPatrocinioimagensCollectionPatrocinioimagens.getPatrocinioimagensCollection().remove(patrocinioimagensCollectionPatrocinioimagens);
                    oldPatrociniosOfPatrocinioimagensCollectionPatrocinioimagens = em.merge(oldPatrociniosOfPatrocinioimagensCollectionPatrocinioimagens);
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

    public void edit(Patrocinios patrocinios) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Patrocinios persistentPatrocinios = em.find(Patrocinios.class, patrocinios.getIdpatrocinio());
            Eventos eventosOld = persistentPatrocinios.getEventos();
            Eventos eventosNew = patrocinios.getEventos();
            Status statusOld = persistentPatrocinios.getStatus();
            Status statusNew = patrocinios.getStatus();
            Collection<Patrocinioimagens> patrocinioimagensCollectionOld = persistentPatrocinios.getPatrocinioimagensCollection();
            Collection<Patrocinioimagens> patrocinioimagensCollectionNew = patrocinios.getPatrocinioimagensCollection();
            List<String> illegalOrphanMessages = null;
            for (Patrocinioimagens patrocinioimagensCollectionOldPatrocinioimagens : patrocinioimagensCollectionOld) {
                if (!patrocinioimagensCollectionNew.contains(patrocinioimagensCollectionOldPatrocinioimagens)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patrocinioimagens " + patrocinioimagensCollectionOldPatrocinioimagens + " since its patrocinios field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                patrocinios.setEventos(eventosNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                patrocinios.setStatus(statusNew);
            }
            Collection<Patrocinioimagens> attachedPatrocinioimagensCollectionNew = new ArrayList<Patrocinioimagens>();
            for (Patrocinioimagens patrocinioimagensCollectionNewPatrocinioimagensToAttach : patrocinioimagensCollectionNew) {
                patrocinioimagensCollectionNewPatrocinioimagensToAttach = em.getReference(patrocinioimagensCollectionNewPatrocinioimagensToAttach.getClass(), patrocinioimagensCollectionNewPatrocinioimagensToAttach.getIdpatrocinioimagens());
                attachedPatrocinioimagensCollectionNew.add(patrocinioimagensCollectionNewPatrocinioimagensToAttach);
            }
            patrocinioimagensCollectionNew = attachedPatrocinioimagensCollectionNew;
            patrocinios.setPatrocinioimagensCollection(patrocinioimagensCollectionNew);
            patrocinios = em.merge(patrocinios);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getPatrociniosCollection().remove(patrocinios);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getPatrociniosCollection().add(patrocinios);
                eventosNew = em.merge(eventosNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getPatrociniosCollection().remove(patrocinios);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getPatrociniosCollection().add(patrocinios);
                statusNew = em.merge(statusNew);
            }
            for (Patrocinioimagens patrocinioimagensCollectionNewPatrocinioimagens : patrocinioimagensCollectionNew) {
                if (!patrocinioimagensCollectionOld.contains(patrocinioimagensCollectionNewPatrocinioimagens)) {
                    Patrocinios oldPatrociniosOfPatrocinioimagensCollectionNewPatrocinioimagens = patrocinioimagensCollectionNewPatrocinioimagens.getPatrocinios();
                    patrocinioimagensCollectionNewPatrocinioimagens.setPatrocinios(patrocinios);
                    patrocinioimagensCollectionNewPatrocinioimagens = em.merge(patrocinioimagensCollectionNewPatrocinioimagens);
                    if (oldPatrociniosOfPatrocinioimagensCollectionNewPatrocinioimagens != null && !oldPatrociniosOfPatrocinioimagensCollectionNewPatrocinioimagens.equals(patrocinios)) {
                        oldPatrociniosOfPatrocinioimagensCollectionNewPatrocinioimagens.getPatrocinioimagensCollection().remove(patrocinioimagensCollectionNewPatrocinioimagens);
                        oldPatrociniosOfPatrocinioimagensCollectionNewPatrocinioimagens = em.merge(oldPatrociniosOfPatrocinioimagensCollectionNewPatrocinioimagens);
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
                Integer id = patrocinios.getIdpatrocinio();
                if (findPatrocinios(id) == null) {
                    throw new NonexistentEntityException("The patrocinios with id " + id + " no longer exists.");
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
            Patrocinios patrocinios;
            try {
                patrocinios = em.getReference(Patrocinios.class, id);
                patrocinios.getIdpatrocinio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patrocinios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Patrocinioimagens> patrocinioimagensCollectionOrphanCheck = patrocinios.getPatrocinioimagensCollection();
            for (Patrocinioimagens patrocinioimagensCollectionOrphanCheckPatrocinioimagens : patrocinioimagensCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Patrocinios (" + patrocinios + ") cannot be destroyed since the Patrocinioimagens " + patrocinioimagensCollectionOrphanCheckPatrocinioimagens + " in its patrocinioimagensCollection field has a non-nullable patrocinios field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Eventos eventos = patrocinios.getEventos();
            if (eventos != null) {
                eventos.getPatrociniosCollection().remove(patrocinios);
                eventos = em.merge(eventos);
            }
            Status status = patrocinios.getStatus();
            if (status != null) {
                status.getPatrociniosCollection().remove(patrocinios);
                status = em.merge(status);
            }
            em.remove(patrocinios);
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

    public List<Patrocinios> findPatrociniosEntities() {
        return findPatrociniosEntities(true, -1, -1);
    }

    public List<Patrocinios> findPatrociniosEntities(int maxResults, int firstResult) {
        return findPatrociniosEntities(false, maxResults, firstResult);
    }

    private List<Patrocinios> findPatrociniosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patrocinios.class));
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

    public Patrocinios findPatrocinios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patrocinios.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatrociniosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patrocinios> rt = cq.from(Patrocinios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
