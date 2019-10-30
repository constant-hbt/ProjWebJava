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
import model.Categoriamidia;
import model.Eventos;
import model.Midias;
import model.Status;
import model.Subeventos;

/**
 *
 * @author henrique
 */
public class MidiasJpaController implements Serializable {

    public MidiasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Midias midias) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoriamidia categoriamidia = midias.getCategoriamidia();
            if (categoriamidia != null) {
                categoriamidia = em.getReference(categoriamidia.getClass(), categoriamidia.getIdcategoriamidia());
                midias.setCategoriamidia(categoriamidia);
            }
            Eventos eventos = midias.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                midias.setEventos(eventos);
            }
            Status status = midias.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                midias.setStatus(status);
            }
            Subeventos subeventos = midias.getSubeventos();
            if (subeventos != null) {
                subeventos = em.getReference(subeventos.getClass(), subeventos.getIdsubevento());
                midias.setSubeventos(subeventos);
            }
            em.persist(midias);
            if (categoriamidia != null) {
                categoriamidia.getMidiasCollection().add(midias);
                categoriamidia = em.merge(categoriamidia);
            }
            if (eventos != null) {
                eventos.getMidiasCollection().add(midias);
                eventos = em.merge(eventos);
            }
            if (status != null) {
                status.getMidiasCollection().add(midias);
                status = em.merge(status);
            }
            if (subeventos != null) {
                subeventos.getMidiasCollection().add(midias);
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

    public void edit(Midias midias) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Midias persistentMidias = em.find(Midias.class, midias.getIdmidia());
            Categoriamidia categoriamidiaOld = persistentMidias.getCategoriamidia();
            Categoriamidia categoriamidiaNew = midias.getCategoriamidia();
            Eventos eventosOld = persistentMidias.getEventos();
            Eventos eventosNew = midias.getEventos();
            Status statusOld = persistentMidias.getStatus();
            Status statusNew = midias.getStatus();
            Subeventos subeventosOld = persistentMidias.getSubeventos();
            Subeventos subeventosNew = midias.getSubeventos();
            if (categoriamidiaNew != null) {
                categoriamidiaNew = em.getReference(categoriamidiaNew.getClass(), categoriamidiaNew.getIdcategoriamidia());
                midias.setCategoriamidia(categoriamidiaNew);
            }
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                midias.setEventos(eventosNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                midias.setStatus(statusNew);
            }
            if (subeventosNew != null) {
                subeventosNew = em.getReference(subeventosNew.getClass(), subeventosNew.getIdsubevento());
                midias.setSubeventos(subeventosNew);
            }
            midias = em.merge(midias);
            if (categoriamidiaOld != null && !categoriamidiaOld.equals(categoriamidiaNew)) {
                categoriamidiaOld.getMidiasCollection().remove(midias);
                categoriamidiaOld = em.merge(categoriamidiaOld);
            }
            if (categoriamidiaNew != null && !categoriamidiaNew.equals(categoriamidiaOld)) {
                categoriamidiaNew.getMidiasCollection().add(midias);
                categoriamidiaNew = em.merge(categoriamidiaNew);
            }
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getMidiasCollection().remove(midias);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getMidiasCollection().add(midias);
                eventosNew = em.merge(eventosNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getMidiasCollection().remove(midias);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getMidiasCollection().add(midias);
                statusNew = em.merge(statusNew);
            }
            if (subeventosOld != null && !subeventosOld.equals(subeventosNew)) {
                subeventosOld.getMidiasCollection().remove(midias);
                subeventosOld = em.merge(subeventosOld);
            }
            if (subeventosNew != null && !subeventosNew.equals(subeventosOld)) {
                subeventosNew.getMidiasCollection().add(midias);
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
                Integer id = midias.getIdmidia();
                if (findMidias(id) == null) {
                    throw new NonexistentEntityException("The midias with id " + id + " no longer exists.");
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
            Midias midias;
            try {
                midias = em.getReference(Midias.class, id);
                midias.getIdmidia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The midias with id " + id + " no longer exists.", enfe);
            }
            Categoriamidia categoriamidia = midias.getCategoriamidia();
            if (categoriamidia != null) {
                categoriamidia.getMidiasCollection().remove(midias);
                categoriamidia = em.merge(categoriamidia);
            }
            Eventos eventos = midias.getEventos();
            if (eventos != null) {
                eventos.getMidiasCollection().remove(midias);
                eventos = em.merge(eventos);
            }
            Status status = midias.getStatus();
            if (status != null) {
                status.getMidiasCollection().remove(midias);
                status = em.merge(status);
            }
            Subeventos subeventos = midias.getSubeventos();
            if (subeventos != null) {
                subeventos.getMidiasCollection().remove(midias);
                subeventos = em.merge(subeventos);
            }
            em.remove(midias);
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

    public List<Midias> findMidiasEntities() {
        return findMidiasEntities(true, -1, -1);
    }

    public List<Midias> findMidiasEntities(int maxResults, int firstResult) {
        return findMidiasEntities(false, maxResults, firstResult);
    }

    private List<Midias> findMidiasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Midias.class));
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

    public Midias findMidias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Midias.class, id);
        } finally {
            em.close();
        }
    }

    public int getMidiasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Midias> rt = cq.from(Midias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
