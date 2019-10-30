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
import model.ParticipanteEquipe;
import model.Participantes;
import model.Status;

/**
 *
 * @author henrique
 */
public class ParticipanteEquipeJpaController implements Serializable {

    public ParticipanteEquipeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParticipanteEquipe participanteEquipe) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipes equipes = participanteEquipe.getEquipes();
            if (equipes != null) {
                equipes = em.getReference(equipes.getClass(), equipes.getIdequipe());
                participanteEquipe.setEquipes(equipes);
            }
            Participantes participantes = participanteEquipe.getParticipantes();
            if (participantes != null) {
                participantes = em.getReference(participantes.getClass(), participantes.getIdparticipante());
                participanteEquipe.setParticipantes(participantes);
            }
            Status status = participanteEquipe.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                participanteEquipe.setStatus(status);
            }
            em.persist(participanteEquipe);
            if (equipes != null) {
                equipes.getParticipanteEquipeCollection().add(participanteEquipe);
                equipes = em.merge(equipes);
            }
            if (participantes != null) {
                participantes.getParticipanteEquipeCollection().add(participanteEquipe);
                participantes = em.merge(participantes);
            }
            if (status != null) {
                status.getParticipanteEquipeCollection().add(participanteEquipe);
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

    public void edit(ParticipanteEquipe participanteEquipe) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ParticipanteEquipe persistentParticipanteEquipe = em.find(ParticipanteEquipe.class, participanteEquipe.getIdpartequipe());
            Equipes equipesOld = persistentParticipanteEquipe.getEquipes();
            Equipes equipesNew = participanteEquipe.getEquipes();
            Participantes participantesOld = persistentParticipanteEquipe.getParticipantes();
            Participantes participantesNew = participanteEquipe.getParticipantes();
            Status statusOld = persistentParticipanteEquipe.getStatus();
            Status statusNew = participanteEquipe.getStatus();
            if (equipesNew != null) {
                equipesNew = em.getReference(equipesNew.getClass(), equipesNew.getIdequipe());
                participanteEquipe.setEquipes(equipesNew);
            }
            if (participantesNew != null) {
                participantesNew = em.getReference(participantesNew.getClass(), participantesNew.getIdparticipante());
                participanteEquipe.setParticipantes(participantesNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                participanteEquipe.setStatus(statusNew);
            }
            participanteEquipe = em.merge(participanteEquipe);
            if (equipesOld != null && !equipesOld.equals(equipesNew)) {
                equipesOld.getParticipanteEquipeCollection().remove(participanteEquipe);
                equipesOld = em.merge(equipesOld);
            }
            if (equipesNew != null && !equipesNew.equals(equipesOld)) {
                equipesNew.getParticipanteEquipeCollection().add(participanteEquipe);
                equipesNew = em.merge(equipesNew);
            }
            if (participantesOld != null && !participantesOld.equals(participantesNew)) {
                participantesOld.getParticipanteEquipeCollection().remove(participanteEquipe);
                participantesOld = em.merge(participantesOld);
            }
            if (participantesNew != null && !participantesNew.equals(participantesOld)) {
                participantesNew.getParticipanteEquipeCollection().add(participanteEquipe);
                participantesNew = em.merge(participantesNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getParticipanteEquipeCollection().remove(participanteEquipe);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getParticipanteEquipeCollection().add(participanteEquipe);
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
                Integer id = participanteEquipe.getIdpartequipe();
                if (findParticipanteEquipe(id) == null) {
                    throw new NonexistentEntityException("The participanteEquipe with id " + id + " no longer exists.");
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
            ParticipanteEquipe participanteEquipe;
            try {
                participanteEquipe = em.getReference(ParticipanteEquipe.class, id);
                participanteEquipe.getIdpartequipe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participanteEquipe with id " + id + " no longer exists.", enfe);
            }
            Equipes equipes = participanteEquipe.getEquipes();
            if (equipes != null) {
                equipes.getParticipanteEquipeCollection().remove(participanteEquipe);
                equipes = em.merge(equipes);
            }
            Participantes participantes = participanteEquipe.getParticipantes();
            if (participantes != null) {
                participantes.getParticipanteEquipeCollection().remove(participanteEquipe);
                participantes = em.merge(participantes);
            }
            Status status = participanteEquipe.getStatus();
            if (status != null) {
                status.getParticipanteEquipeCollection().remove(participanteEquipe);
                status = em.merge(status);
            }
            em.remove(participanteEquipe);
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

    public List<ParticipanteEquipe> findParticipanteEquipeEntities() {
        return findParticipanteEquipeEntities(true, -1, -1);
    }

    public List<ParticipanteEquipe> findParticipanteEquipeEntities(int maxResults, int firstResult) {
        return findParticipanteEquipeEntities(false, maxResults, firstResult);
    }

    private List<ParticipanteEquipe> findParticipanteEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParticipanteEquipe.class));
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

    public ParticipanteEquipe findParticipanteEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParticipanteEquipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipanteEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParticipanteEquipe> rt = cq.from(ParticipanteEquipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
