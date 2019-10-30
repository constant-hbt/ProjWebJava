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
import model.Editais;
import model.Eventos;
import model.Status;
import model.Usuarios;

/**
 *
 * @author henrique
 */
public class EditaisJpaController implements Serializable {

    public EditaisJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Editais editais) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = editais.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                editais.setEventos(eventos);
            }
            Status status = editais.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                editais.setStatus(status);
            }
            Usuarios usuarios = editais.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getIdusuario());
                editais.setUsuarios(usuarios);
            }
            em.persist(editais);
            if (eventos != null) {
                eventos.getEditaisCollection().add(editais);
                eventos = em.merge(eventos);
            }
            if (status != null) {
                status.getEditaisCollection().add(editais);
                status = em.merge(status);
            }
            if (usuarios != null) {
                usuarios.getEditaisCollection().add(editais);
                usuarios = em.merge(usuarios);
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

    public void edit(Editais editais) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Editais persistentEditais = em.find(Editais.class, editais.getIdedital());
            Eventos eventosOld = persistentEditais.getEventos();
            Eventos eventosNew = editais.getEventos();
            Status statusOld = persistentEditais.getStatus();
            Status statusNew = editais.getStatus();
            Usuarios usuariosOld = persistentEditais.getUsuarios();
            Usuarios usuariosNew = editais.getUsuarios();
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                editais.setEventos(eventosNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                editais.setStatus(statusNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getIdusuario());
                editais.setUsuarios(usuariosNew);
            }
            editais = em.merge(editais);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getEditaisCollection().remove(editais);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getEditaisCollection().add(editais);
                eventosNew = em.merge(eventosNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getEditaisCollection().remove(editais);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getEditaisCollection().add(editais);
                statusNew = em.merge(statusNew);
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                usuariosOld.getEditaisCollection().remove(editais);
                usuariosOld = em.merge(usuariosOld);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                usuariosNew.getEditaisCollection().add(editais);
                usuariosNew = em.merge(usuariosNew);
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
                Integer id = editais.getIdedital();
                if (findEditais(id) == null) {
                    throw new NonexistentEntityException("The editais with id " + id + " no longer exists.");
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
            Editais editais;
            try {
                editais = em.getReference(Editais.class, id);
                editais.getIdedital();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The editais with id " + id + " no longer exists.", enfe);
            }
            Eventos eventos = editais.getEventos();
            if (eventos != null) {
                eventos.getEditaisCollection().remove(editais);
                eventos = em.merge(eventos);
            }
            Status status = editais.getStatus();
            if (status != null) {
                status.getEditaisCollection().remove(editais);
                status = em.merge(status);
            }
            Usuarios usuarios = editais.getUsuarios();
            if (usuarios != null) {
                usuarios.getEditaisCollection().remove(editais);
                usuarios = em.merge(usuarios);
            }
            em.remove(editais);
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

    public List<Editais> findEditaisEntities() {
        return findEditaisEntities(true, -1, -1);
    }

    public List<Editais> findEditaisEntities(int maxResults, int firstResult) {
        return findEditaisEntities(false, maxResults, firstResult);
    }

    private List<Editais> findEditaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Editais.class));
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

    public Editais findEditais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Editais.class, id);
        } finally {
            em.close();
        }
    }

    public int getEditaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Editais> rt = cq.from(Editais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
