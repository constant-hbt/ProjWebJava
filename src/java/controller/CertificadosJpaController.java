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
import model.Certificados;
import model.Eventos;
import model.Participantes;
import model.Status;

/**
 *
 * @author henrique
 */
public class CertificadosJpaController implements Serializable {

    public CertificadosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Certificados certificados) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = certificados.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                certificados.setEventos(eventos);
            }
            Participantes participantes = certificados.getParticipantes();
            if (participantes != null) {
                participantes = em.getReference(participantes.getClass(), participantes.getIdparticipante());
                certificados.setParticipantes(participantes);
            }
            Status status = certificados.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                certificados.setStatus(status);
            }
            em.persist(certificados);
            if (eventos != null) {
                eventos.getCertificadosCollection().add(certificados);
                eventos = em.merge(eventos);
            }
            if (participantes != null) {
                participantes.getCertificadosCollection().add(certificados);
                participantes = em.merge(participantes);
            }
            if (status != null) {
                status.getCertificadosCollection().add(certificados);
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

    public void edit(Certificados certificados) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Certificados persistentCertificados = em.find(Certificados.class, certificados.getIdcertificado());
            Eventos eventosOld = persistentCertificados.getEventos();
            Eventos eventosNew = certificados.getEventos();
            Participantes participantesOld = persistentCertificados.getParticipantes();
            Participantes participantesNew = certificados.getParticipantes();
            Status statusOld = persistentCertificados.getStatus();
            Status statusNew = certificados.getStatus();
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                certificados.setEventos(eventosNew);
            }
            if (participantesNew != null) {
                participantesNew = em.getReference(participantesNew.getClass(), participantesNew.getIdparticipante());
                certificados.setParticipantes(participantesNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                certificados.setStatus(statusNew);
            }
            certificados = em.merge(certificados);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getCertificadosCollection().remove(certificados);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getCertificadosCollection().add(certificados);
                eventosNew = em.merge(eventosNew);
            }
            if (participantesOld != null && !participantesOld.equals(participantesNew)) {
                participantesOld.getCertificadosCollection().remove(certificados);
                participantesOld = em.merge(participantesOld);
            }
            if (participantesNew != null && !participantesNew.equals(participantesOld)) {
                participantesNew.getCertificadosCollection().add(certificados);
                participantesNew = em.merge(participantesNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getCertificadosCollection().remove(certificados);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getCertificadosCollection().add(certificados);
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
                Integer id = certificados.getIdcertificado();
                if (findCertificados(id) == null) {
                    throw new NonexistentEntityException("The certificados with id " + id + " no longer exists.");
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
            Certificados certificados;
            try {
                certificados = em.getReference(Certificados.class, id);
                certificados.getIdcertificado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The certificados with id " + id + " no longer exists.", enfe);
            }
            Eventos eventos = certificados.getEventos();
            if (eventos != null) {
                eventos.getCertificadosCollection().remove(certificados);
                eventos = em.merge(eventos);
            }
            Participantes participantes = certificados.getParticipantes();
            if (participantes != null) {
                participantes.getCertificadosCollection().remove(certificados);
                participantes = em.merge(participantes);
            }
            Status status = certificados.getStatus();
            if (status != null) {
                status.getCertificadosCollection().remove(certificados);
                status = em.merge(status);
            }
            em.remove(certificados);
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

    public List<Certificados> findCertificadosEntities() {
        return findCertificadosEntities(true, -1, -1);
    }

    public List<Certificados> findCertificadosEntities(int maxResults, int firstResult) {
        return findCertificadosEntities(false, maxResults, firstResult);
    }

    private List<Certificados> findCertificadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Certificados.class));
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

    public Certificados findCertificados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Certificados.class, id);
        } finally {
            em.close();
        }
    }

    public int getCertificadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Certificados> rt = cq.from(Certificados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
