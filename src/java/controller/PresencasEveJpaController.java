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
import model.Eventos;
import model.Participantes;
import model.PresencasEve;
import model.PresencasEvePK;

/**
 *
 * @author henrique
 */
public class PresencasEveJpaController implements Serializable {

    public PresencasEveJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PresencasEve presencasEve) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (presencasEve.getPresencasEvePK() == null) {
            presencasEve.setPresencasEvePK(new PresencasEvePK());
        }
        presencasEve.getPresencasEvePK().setIdparticipante(presencasEve.getParticipantes().getIdparticipante());
        presencasEve.getPresencasEvePK().setIdevento(presencasEve.getEventos().getIdevento());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = presencasEve.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                presencasEve.setEventos(eventos);
            }
            Participantes participantes = presencasEve.getParticipantes();
            if (participantes != null) {
                participantes = em.getReference(participantes.getClass(), participantes.getIdparticipante());
                presencasEve.setParticipantes(participantes);
            }
            em.persist(presencasEve);
            if (eventos != null) {
                eventos.getPresencasEveCollection().add(presencasEve);
                eventos = em.merge(eventos);
            }
            if (participantes != null) {
                participantes.getPresencasEveCollection().add(presencasEve);
                participantes = em.merge(participantes);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPresencasEve(presencasEve.getPresencasEvePK()) != null) {
                throw new PreexistingEntityException("PresencasEve " + presencasEve + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PresencasEve presencasEve) throws NonexistentEntityException, RollbackFailureException, Exception {
        presencasEve.getPresencasEvePK().setIdparticipante(presencasEve.getParticipantes().getIdparticipante());
        presencasEve.getPresencasEvePK().setIdevento(presencasEve.getEventos().getIdevento());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PresencasEve persistentPresencasEve = em.find(PresencasEve.class, presencasEve.getPresencasEvePK());
            Eventos eventosOld = persistentPresencasEve.getEventos();
            Eventos eventosNew = presencasEve.getEventos();
            Participantes participantesOld = persistentPresencasEve.getParticipantes();
            Participantes participantesNew = presencasEve.getParticipantes();
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                presencasEve.setEventos(eventosNew);
            }
            if (participantesNew != null) {
                participantesNew = em.getReference(participantesNew.getClass(), participantesNew.getIdparticipante());
                presencasEve.setParticipantes(participantesNew);
            }
            presencasEve = em.merge(presencasEve);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getPresencasEveCollection().remove(presencasEve);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getPresencasEveCollection().add(presencasEve);
                eventosNew = em.merge(eventosNew);
            }
            if (participantesOld != null && !participantesOld.equals(participantesNew)) {
                participantesOld.getPresencasEveCollection().remove(presencasEve);
                participantesOld = em.merge(participantesOld);
            }
            if (participantesNew != null && !participantesNew.equals(participantesOld)) {
                participantesNew.getPresencasEveCollection().add(presencasEve);
                participantesNew = em.merge(participantesNew);
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
                PresencasEvePK id = presencasEve.getPresencasEvePK();
                if (findPresencasEve(id) == null) {
                    throw new NonexistentEntityException("The presencasEve with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PresencasEvePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PresencasEve presencasEve;
            try {
                presencasEve = em.getReference(PresencasEve.class, id);
                presencasEve.getPresencasEvePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presencasEve with id " + id + " no longer exists.", enfe);
            }
            Eventos eventos = presencasEve.getEventos();
            if (eventos != null) {
                eventos.getPresencasEveCollection().remove(presencasEve);
                eventos = em.merge(eventos);
            }
            Participantes participantes = presencasEve.getParticipantes();
            if (participantes != null) {
                participantes.getPresencasEveCollection().remove(presencasEve);
                participantes = em.merge(participantes);
            }
            em.remove(presencasEve);
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

    public List<PresencasEve> findPresencasEveEntities() {
        return findPresencasEveEntities(true, -1, -1);
    }

    public List<PresencasEve> findPresencasEveEntities(int maxResults, int firstResult) {
        return findPresencasEveEntities(false, maxResults, firstResult);
    }

    private List<PresencasEve> findPresencasEveEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PresencasEve.class));
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

    public PresencasEve findPresencasEve(PresencasEvePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PresencasEve.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresencasEveCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PresencasEve> rt = cq.from(PresencasEve.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
