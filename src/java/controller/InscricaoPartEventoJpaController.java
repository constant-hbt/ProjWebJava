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
import model.Eventos;
import model.InscricaoPartEvento;
import model.Participantes;
import model.Status;

/**
 *
 * @author henrique
 */
public class InscricaoPartEventoJpaController implements Serializable {

    public InscricaoPartEventoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscricaoPartEvento inscricaoPartEvento) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = inscricaoPartEvento.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                inscricaoPartEvento.setEventos(eventos);
            }
            Participantes participantes = inscricaoPartEvento.getParticipantes();
            if (participantes != null) {
                participantes = em.getReference(participantes.getClass(), participantes.getIdparticipante());
                inscricaoPartEvento.setParticipantes(participantes);
            }
            Status status = inscricaoPartEvento.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                inscricaoPartEvento.setStatus(status);
            }
            em.persist(inscricaoPartEvento);
            if (eventos != null) {
                eventos.getInscricaoPartEventoCollection().add(inscricaoPartEvento);
                eventos = em.merge(eventos);
            }
            if (participantes != null) {
                participantes.getInscricaoPartEventoCollection().add(inscricaoPartEvento);
                participantes = em.merge(participantes);
            }
            if (status != null) {
                status.getInscricaoPartEventoCollection().add(inscricaoPartEvento);
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

    public void edit(InscricaoPartEvento inscricaoPartEvento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InscricaoPartEvento persistentInscricaoPartEvento = em.find(InscricaoPartEvento.class, inscricaoPartEvento.getIdinscricao());
            Eventos eventosOld = persistentInscricaoPartEvento.getEventos();
            Eventos eventosNew = inscricaoPartEvento.getEventos();
            Participantes participantesOld = persistentInscricaoPartEvento.getParticipantes();
            Participantes participantesNew = inscricaoPartEvento.getParticipantes();
            Status statusOld = persistentInscricaoPartEvento.getStatus();
            Status statusNew = inscricaoPartEvento.getStatus();
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                inscricaoPartEvento.setEventos(eventosNew);
            }
            if (participantesNew != null) {
                participantesNew = em.getReference(participantesNew.getClass(), participantesNew.getIdparticipante());
                inscricaoPartEvento.setParticipantes(participantesNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                inscricaoPartEvento.setStatus(statusNew);
            }
            inscricaoPartEvento = em.merge(inscricaoPartEvento);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getInscricaoPartEventoCollection().remove(inscricaoPartEvento);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getInscricaoPartEventoCollection().add(inscricaoPartEvento);
                eventosNew = em.merge(eventosNew);
            }
            if (participantesOld != null && !participantesOld.equals(participantesNew)) {
                participantesOld.getInscricaoPartEventoCollection().remove(inscricaoPartEvento);
                participantesOld = em.merge(participantesOld);
            }
            if (participantesNew != null && !participantesNew.equals(participantesOld)) {
                participantesNew.getInscricaoPartEventoCollection().add(inscricaoPartEvento);
                participantesNew = em.merge(participantesNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getInscricaoPartEventoCollection().remove(inscricaoPartEvento);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getInscricaoPartEventoCollection().add(inscricaoPartEvento);
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
                Integer id = inscricaoPartEvento.getIdinscricao();
                if (findInscricaoPartEvento(id) == null) {
                    throw new NonexistentEntityException("The inscricaoPartEvento with id " + id + " no longer exists.");
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
            InscricaoPartEvento inscricaoPartEvento;
            try {
                inscricaoPartEvento = em.getReference(InscricaoPartEvento.class, id);
                inscricaoPartEvento.getIdinscricao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscricaoPartEvento with id " + id + " no longer exists.", enfe);
            }
            Eventos eventos = inscricaoPartEvento.getEventos();
            if (eventos != null) {
                eventos.getInscricaoPartEventoCollection().remove(inscricaoPartEvento);
                eventos = em.merge(eventos);
            }
            Participantes participantes = inscricaoPartEvento.getParticipantes();
            if (participantes != null) {
                participantes.getInscricaoPartEventoCollection().remove(inscricaoPartEvento);
                participantes = em.merge(participantes);
            }
            Status status = inscricaoPartEvento.getStatus();
            if (status != null) {
                status.getInscricaoPartEventoCollection().remove(inscricaoPartEvento);
                status = em.merge(status);
            }
            em.remove(inscricaoPartEvento);
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

    public List<InscricaoPartEvento> findInscricaoPartEventoEntities() {
        return findInscricaoPartEventoEntities(true, -1, -1);
    }

    public List<InscricaoPartEvento> findInscricaoPartEventoEntities(int maxResults, int firstResult) {
        return findInscricaoPartEventoEntities(false, maxResults, firstResult);
    }

    private List<InscricaoPartEvento> findInscricaoPartEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscricaoPartEvento.class));
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

    public InscricaoPartEvento findInscricaoPartEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscricaoPartEvento.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscricaoPartEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscricaoPartEvento> rt = cq.from(InscricaoPartEvento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
