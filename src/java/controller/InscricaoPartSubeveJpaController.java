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
import model.InscricaoPartSubeve;
import model.Participantes;
import model.Status;
import model.Subeventos;

/**
 *
 * @author henrique
 */
public class InscricaoPartSubeveJpaController implements Serializable {

    public InscricaoPartSubeveJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscricaoPartSubeve inscricaoPartSubeve) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Participantes participantes = inscricaoPartSubeve.getParticipantes();
            if (participantes != null) {
                participantes = em.getReference(participantes.getClass(), participantes.getIdparticipante());
                inscricaoPartSubeve.setParticipantes(participantes);
            }
            Status status = inscricaoPartSubeve.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                inscricaoPartSubeve.setStatus(status);
            }
            Subeventos subeventos = inscricaoPartSubeve.getSubeventos();
            if (subeventos != null) {
                subeventos = em.getReference(subeventos.getClass(), subeventos.getIdsubevento());
                inscricaoPartSubeve.setSubeventos(subeventos);
            }
            em.persist(inscricaoPartSubeve);
            if (participantes != null) {
                participantes.getInscricaoPartSubeveCollection().add(inscricaoPartSubeve);
                participantes = em.merge(participantes);
            }
            if (status != null) {
                status.getInscricaoPartSubeveCollection().add(inscricaoPartSubeve);
                status = em.merge(status);
            }
            if (subeventos != null) {
                subeventos.getInscricaoPartSubeveCollection().add(inscricaoPartSubeve);
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

    public void edit(InscricaoPartSubeve inscricaoPartSubeve) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InscricaoPartSubeve persistentInscricaoPartSubeve = em.find(InscricaoPartSubeve.class, inscricaoPartSubeve.getIdinscricao());
            Participantes participantesOld = persistentInscricaoPartSubeve.getParticipantes();
            Participantes participantesNew = inscricaoPartSubeve.getParticipantes();
            Status statusOld = persistentInscricaoPartSubeve.getStatus();
            Status statusNew = inscricaoPartSubeve.getStatus();
            Subeventos subeventosOld = persistentInscricaoPartSubeve.getSubeventos();
            Subeventos subeventosNew = inscricaoPartSubeve.getSubeventos();
            if (participantesNew != null) {
                participantesNew = em.getReference(participantesNew.getClass(), participantesNew.getIdparticipante());
                inscricaoPartSubeve.setParticipantes(participantesNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                inscricaoPartSubeve.setStatus(statusNew);
            }
            if (subeventosNew != null) {
                subeventosNew = em.getReference(subeventosNew.getClass(), subeventosNew.getIdsubevento());
                inscricaoPartSubeve.setSubeventos(subeventosNew);
            }
            inscricaoPartSubeve = em.merge(inscricaoPartSubeve);
            if (participantesOld != null && !participantesOld.equals(participantesNew)) {
                participantesOld.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeve);
                participantesOld = em.merge(participantesOld);
            }
            if (participantesNew != null && !participantesNew.equals(participantesOld)) {
                participantesNew.getInscricaoPartSubeveCollection().add(inscricaoPartSubeve);
                participantesNew = em.merge(participantesNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeve);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getInscricaoPartSubeveCollection().add(inscricaoPartSubeve);
                statusNew = em.merge(statusNew);
            }
            if (subeventosOld != null && !subeventosOld.equals(subeventosNew)) {
                subeventosOld.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeve);
                subeventosOld = em.merge(subeventosOld);
            }
            if (subeventosNew != null && !subeventosNew.equals(subeventosOld)) {
                subeventosNew.getInscricaoPartSubeveCollection().add(inscricaoPartSubeve);
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
                Integer id = inscricaoPartSubeve.getIdinscricao();
                if (findInscricaoPartSubeve(id) == null) {
                    throw new NonexistentEntityException("The inscricaoPartSubeve with id " + id + " no longer exists.");
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
            InscricaoPartSubeve inscricaoPartSubeve;
            try {
                inscricaoPartSubeve = em.getReference(InscricaoPartSubeve.class, id);
                inscricaoPartSubeve.getIdinscricao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscricaoPartSubeve with id " + id + " no longer exists.", enfe);
            }
            Participantes participantes = inscricaoPartSubeve.getParticipantes();
            if (participantes != null) {
                participantes.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeve);
                participantes = em.merge(participantes);
            }
            Status status = inscricaoPartSubeve.getStatus();
            if (status != null) {
                status.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeve);
                status = em.merge(status);
            }
            Subeventos subeventos = inscricaoPartSubeve.getSubeventos();
            if (subeventos != null) {
                subeventos.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeve);
                subeventos = em.merge(subeventos);
            }
            em.remove(inscricaoPartSubeve);
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

    public List<InscricaoPartSubeve> findInscricaoPartSubeveEntities() {
        return findInscricaoPartSubeveEntities(true, -1, -1);
    }

    public List<InscricaoPartSubeve> findInscricaoPartSubeveEntities(int maxResults, int firstResult) {
        return findInscricaoPartSubeveEntities(false, maxResults, firstResult);
    }

    private List<InscricaoPartSubeve> findInscricaoPartSubeveEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscricaoPartSubeve.class));
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

    public InscricaoPartSubeve findInscricaoPartSubeve(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscricaoPartSubeve.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscricaoPartSubeveCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscricaoPartSubeve> rt = cq.from(InscricaoPartSubeve.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
