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
import model.Organizadores;
import model.Usuarios;

/**
 *
 * @author henrique
 */
public class OrganizadoresJpaController implements Serializable {

    public OrganizadoresJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Organizadores organizadores) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = organizadores.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                organizadores.setEventos(eventos);
            }
            Usuarios usuarios = organizadores.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getIdusuario());
                organizadores.setUsuarios(usuarios);
            }
            em.persist(organizadores);
            if (eventos != null) {
                eventos.getOrganizadoresCollection().add(organizadores);
                eventos = em.merge(eventos);
            }
            if (usuarios != null) {
                usuarios.getOrganizadoresCollection().add(organizadores);
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

    public void edit(Organizadores organizadores) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Organizadores persistentOrganizadores = em.find(Organizadores.class, organizadores.getIdorganizador());
            Eventos eventosOld = persistentOrganizadores.getEventos();
            Eventos eventosNew = organizadores.getEventos();
            Usuarios usuariosOld = persistentOrganizadores.getUsuarios();
            Usuarios usuariosNew = organizadores.getUsuarios();
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                organizadores.setEventos(eventosNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getIdusuario());
                organizadores.setUsuarios(usuariosNew);
            }
            organizadores = em.merge(organizadores);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getOrganizadoresCollection().remove(organizadores);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getOrganizadoresCollection().add(organizadores);
                eventosNew = em.merge(eventosNew);
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                usuariosOld.getOrganizadoresCollection().remove(organizadores);
                usuariosOld = em.merge(usuariosOld);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                usuariosNew.getOrganizadoresCollection().add(organizadores);
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
                Integer id = organizadores.getIdorganizador();
                if (findOrganizadores(id) == null) {
                    throw new NonexistentEntityException("The organizadores with id " + id + " no longer exists.");
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
            Organizadores organizadores;
            try {
                organizadores = em.getReference(Organizadores.class, id);
                organizadores.getIdorganizador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organizadores with id " + id + " no longer exists.", enfe);
            }
            Eventos eventos = organizadores.getEventos();
            if (eventos != null) {
                eventos.getOrganizadoresCollection().remove(organizadores);
                eventos = em.merge(eventos);
            }
            Usuarios usuarios = organizadores.getUsuarios();
            if (usuarios != null) {
                usuarios.getOrganizadoresCollection().remove(organizadores);
                usuarios = em.merge(usuarios);
            }
            em.remove(organizadores);
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

    public List<Organizadores> findOrganizadoresEntities() {
        return findOrganizadoresEntities(true, -1, -1);
    }

    public List<Organizadores> findOrganizadoresEntities(int maxResults, int firstResult) {
        return findOrganizadoresEntities(false, maxResults, firstResult);
    }

    private List<Organizadores> findOrganizadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Organizadores.class));
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

    public Organizadores findOrganizadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organizadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Organizadores> rt = cq.from(Organizadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
