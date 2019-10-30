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
import model.Crachas;
import model.Eventos;
import model.Participantes;
import model.Status;

/**
 *
 * @author henrique
 */
public class CrachasJpaController implements Serializable {

    public CrachasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Crachas crachas) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = crachas.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                crachas.setEventos(eventos);
            }
            Participantes participantes = crachas.getParticipantes();
            if (participantes != null) {
                participantes = em.getReference(participantes.getClass(), participantes.getIdparticipante());
                crachas.setParticipantes(participantes);
            }
            Status status = crachas.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                crachas.setStatus(status);
            }
            em.persist(crachas);
            if (eventos != null) {
                eventos.getCrachasCollection().add(crachas);
                eventos = em.merge(eventos);
            }
            if (participantes != null) {
                participantes.getCrachasCollection().add(crachas);
                participantes = em.merge(participantes);
            }
            if (status != null) {
                status.getCrachasCollection().add(crachas);
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

    public void edit(Crachas crachas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Crachas persistentCrachas = em.find(Crachas.class, crachas.getIdcracha());
            Eventos eventosOld = persistentCrachas.getEventos();
            Eventos eventosNew = crachas.getEventos();
            Participantes participantesOld = persistentCrachas.getParticipantes();
            Participantes participantesNew = crachas.getParticipantes();
            Status statusOld = persistentCrachas.getStatus();
            Status statusNew = crachas.getStatus();
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                crachas.setEventos(eventosNew);
            }
            if (participantesNew != null) {
                participantesNew = em.getReference(participantesNew.getClass(), participantesNew.getIdparticipante());
                crachas.setParticipantes(participantesNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                crachas.setStatus(statusNew);
            }
            crachas = em.merge(crachas);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getCrachasCollection().remove(crachas);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getCrachasCollection().add(crachas);
                eventosNew = em.merge(eventosNew);
            }
            if (participantesOld != null && !participantesOld.equals(participantesNew)) {
                participantesOld.getCrachasCollection().remove(crachas);
                participantesOld = em.merge(participantesOld);
            }
            if (participantesNew != null && !participantesNew.equals(participantesOld)) {
                participantesNew.getCrachasCollection().add(crachas);
                participantesNew = em.merge(participantesNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getCrachasCollection().remove(crachas);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getCrachasCollection().add(crachas);
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
                Integer id = crachas.getIdcracha();
                if (findCrachas(id) == null) {
                    throw new NonexistentEntityException("The crachas with id " + id + " no longer exists.");
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
            Crachas crachas;
            try {
                crachas = em.getReference(Crachas.class, id);
                crachas.getIdcracha();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crachas with id " + id + " no longer exists.", enfe);
            }
            Eventos eventos = crachas.getEventos();
            if (eventos != null) {
                eventos.getCrachasCollection().remove(crachas);
                eventos = em.merge(eventos);
            }
            Participantes participantes = crachas.getParticipantes();
            if (participantes != null) {
                participantes.getCrachasCollection().remove(crachas);
                participantes = em.merge(participantes);
            }
            Status status = crachas.getStatus();
            if (status != null) {
                status.getCrachasCollection().remove(crachas);
                status = em.merge(status);
            }
            em.remove(crachas);
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

    public List<Crachas> findCrachasEntities() {
        return findCrachasEntities(true, -1, -1);
    }

    public List<Crachas> findCrachasEntities(int maxResults, int firstResult) {
        return findCrachasEntities(false, maxResults, firstResult);
    }

    private List<Crachas> findCrachasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Crachas.class));
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

    public Crachas findCrachas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Crachas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCrachasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Crachas> rt = cq.from(Crachas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
