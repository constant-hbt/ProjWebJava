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
import model.Subeventos;
import model.Submissao;
import model.Usuarios;

/**
 *
 * @author henrique
 */
public class SubmissaoJpaController implements Serializable {

    public SubmissaoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Submissao submissao) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subeventos subeventos = submissao.getSubeventos();
            if (subeventos != null) {
                subeventos = em.getReference(subeventos.getClass(), subeventos.getIdsubevento());
                submissao.setSubeventos(subeventos);
            }
            Usuarios usuarios = submissao.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getIdusuario());
                submissao.setUsuarios(usuarios);
            }
            em.persist(submissao);
            if (subeventos != null) {
                subeventos.getSubmissaoCollection().add(submissao);
                subeventos = em.merge(subeventos);
            }
            if (usuarios != null) {
                usuarios.getSubmissaoCollection().add(submissao);
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

    public void edit(Submissao submissao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Submissao persistentSubmissao = em.find(Submissao.class, submissao.getIdsubmissao());
            Subeventos subeventosOld = persistentSubmissao.getSubeventos();
            Subeventos subeventosNew = submissao.getSubeventos();
            Usuarios usuariosOld = persistentSubmissao.getUsuarios();
            Usuarios usuariosNew = submissao.getUsuarios();
            if (subeventosNew != null) {
                subeventosNew = em.getReference(subeventosNew.getClass(), subeventosNew.getIdsubevento());
                submissao.setSubeventos(subeventosNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getIdusuario());
                submissao.setUsuarios(usuariosNew);
            }
            submissao = em.merge(submissao);
            if (subeventosOld != null && !subeventosOld.equals(subeventosNew)) {
                subeventosOld.getSubmissaoCollection().remove(submissao);
                subeventosOld = em.merge(subeventosOld);
            }
            if (subeventosNew != null && !subeventosNew.equals(subeventosOld)) {
                subeventosNew.getSubmissaoCollection().add(submissao);
                subeventosNew = em.merge(subeventosNew);
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                usuariosOld.getSubmissaoCollection().remove(submissao);
                usuariosOld = em.merge(usuariosOld);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                usuariosNew.getSubmissaoCollection().add(submissao);
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
                Integer id = submissao.getIdsubmissao();
                if (findSubmissao(id) == null) {
                    throw new NonexistentEntityException("The submissao with id " + id + " no longer exists.");
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
            Submissao submissao;
            try {
                submissao = em.getReference(Submissao.class, id);
                submissao.getIdsubmissao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The submissao with id " + id + " no longer exists.", enfe);
            }
            Subeventos subeventos = submissao.getSubeventos();
            if (subeventos != null) {
                subeventos.getSubmissaoCollection().remove(submissao);
                subeventos = em.merge(subeventos);
            }
            Usuarios usuarios = submissao.getUsuarios();
            if (usuarios != null) {
                usuarios.getSubmissaoCollection().remove(submissao);
                usuarios = em.merge(usuarios);
            }
            em.remove(submissao);
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

    public List<Submissao> findSubmissaoEntities() {
        return findSubmissaoEntities(true, -1, -1);
    }

    public List<Submissao> findSubmissaoEntities(int maxResults, int firstResult) {
        return findSubmissaoEntities(false, maxResults, firstResult);
    }

    private List<Submissao> findSubmissaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Submissao.class));
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

    public Submissao findSubmissao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Submissao.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubmissaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Submissao> rt = cq.from(Submissao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
