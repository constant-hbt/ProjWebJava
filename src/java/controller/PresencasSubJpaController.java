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
import model.Participantes;
import model.PresencasSub;
import model.PresencasSubPK;
import model.Subeventos;

/**
 *
 * @author henrique
 */
public class PresencasSubJpaController implements Serializable {

    public PresencasSubJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PresencasSub presencasSub) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (presencasSub.getPresencasSubPK() == null) {
            presencasSub.setPresencasSubPK(new PresencasSubPK());
        }
        presencasSub.getPresencasSubPK().setIdsubevento(presencasSub.getSubeventos().getIdsubevento());
        presencasSub.getPresencasSubPK().setIdparticipante(presencasSub.getParticipantes().getIdparticipante());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Participantes participantes = presencasSub.getParticipantes();
            if (participantes != null) {
                participantes = em.getReference(participantes.getClass(), participantes.getIdparticipante());
                presencasSub.setParticipantes(participantes);
            }
            Subeventos subeventos = presencasSub.getSubeventos();
            if (subeventos != null) {
                subeventos = em.getReference(subeventos.getClass(), subeventos.getIdsubevento());
                presencasSub.setSubeventos(subeventos);
            }
            em.persist(presencasSub);
            if (participantes != null) {
                participantes.getPresencasSubCollection().add(presencasSub);
                participantes = em.merge(participantes);
            }
            if (subeventos != null) {
                subeventos.getPresencasSubCollection().add(presencasSub);
                subeventos = em.merge(subeventos);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPresencasSub(presencasSub.getPresencasSubPK()) != null) {
                throw new PreexistingEntityException("PresencasSub " + presencasSub + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PresencasSub presencasSub) throws NonexistentEntityException, RollbackFailureException, Exception {
        presencasSub.getPresencasSubPK().setIdsubevento(presencasSub.getSubeventos().getIdsubevento());
        presencasSub.getPresencasSubPK().setIdparticipante(presencasSub.getParticipantes().getIdparticipante());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PresencasSub persistentPresencasSub = em.find(PresencasSub.class, presencasSub.getPresencasSubPK());
            Participantes participantesOld = persistentPresencasSub.getParticipantes();
            Participantes participantesNew = presencasSub.getParticipantes();
            Subeventos subeventosOld = persistentPresencasSub.getSubeventos();
            Subeventos subeventosNew = presencasSub.getSubeventos();
            if (participantesNew != null) {
                participantesNew = em.getReference(participantesNew.getClass(), participantesNew.getIdparticipante());
                presencasSub.setParticipantes(participantesNew);
            }
            if (subeventosNew != null) {
                subeventosNew = em.getReference(subeventosNew.getClass(), subeventosNew.getIdsubevento());
                presencasSub.setSubeventos(subeventosNew);
            }
            presencasSub = em.merge(presencasSub);
            if (participantesOld != null && !participantesOld.equals(participantesNew)) {
                participantesOld.getPresencasSubCollection().remove(presencasSub);
                participantesOld = em.merge(participantesOld);
            }
            if (participantesNew != null && !participantesNew.equals(participantesOld)) {
                participantesNew.getPresencasSubCollection().add(presencasSub);
                participantesNew = em.merge(participantesNew);
            }
            if (subeventosOld != null && !subeventosOld.equals(subeventosNew)) {
                subeventosOld.getPresencasSubCollection().remove(presencasSub);
                subeventosOld = em.merge(subeventosOld);
            }
            if (subeventosNew != null && !subeventosNew.equals(subeventosOld)) {
                subeventosNew.getPresencasSubCollection().add(presencasSub);
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
                PresencasSubPK id = presencasSub.getPresencasSubPK();
                if (findPresencasSub(id) == null) {
                    throw new NonexistentEntityException("The presencasSub with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PresencasSubPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PresencasSub presencasSub;
            try {
                presencasSub = em.getReference(PresencasSub.class, id);
                presencasSub.getPresencasSubPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presencasSub with id " + id + " no longer exists.", enfe);
            }
            Participantes participantes = presencasSub.getParticipantes();
            if (participantes != null) {
                participantes.getPresencasSubCollection().remove(presencasSub);
                participantes = em.merge(participantes);
            }
            Subeventos subeventos = presencasSub.getSubeventos();
            if (subeventos != null) {
                subeventos.getPresencasSubCollection().remove(presencasSub);
                subeventos = em.merge(subeventos);
            }
            em.remove(presencasSub);
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

    public List<PresencasSub> findPresencasSubEntities() {
        return findPresencasSubEntities(true, -1, -1);
    }

    public List<PresencasSub> findPresencasSubEntities(int maxResults, int firstResult) {
        return findPresencasSubEntities(false, maxResults, firstResult);
    }

    private List<PresencasSub> findPresencasSubEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PresencasSub.class));
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

    public PresencasSub findPresencasSub(PresencasSubPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PresencasSub.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresencasSubCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PresencasSub> rt = cq.from(PresencasSub.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
