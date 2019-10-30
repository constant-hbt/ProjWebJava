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
import model.Apresentacao;
import model.Subeventos;
import model.Usuarios;

/**
 *
 * @author henrique
 */
public class ApresentacaoJpaController implements Serializable {

    public ApresentacaoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Apresentacao apresentacao) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subeventos subeventos = apresentacao.getSubeventos();
            if (subeventos != null) {
                subeventos = em.getReference(subeventos.getClass(), subeventos.getIdsubevento());
                apresentacao.setSubeventos(subeventos);
            }
            Usuarios usuarios = apresentacao.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getIdusuario());
                apresentacao.setUsuarios(usuarios);
            }
            em.persist(apresentacao);
            if (subeventos != null) {
                subeventos.getApresentacaoCollection().add(apresentacao);
                subeventos = em.merge(subeventos);
            }
            if (usuarios != null) {
                usuarios.getApresentacaoCollection().add(apresentacao);
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

    public void edit(Apresentacao apresentacao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Apresentacao persistentApresentacao = em.find(Apresentacao.class, apresentacao.getIdapresentacao());
            Subeventos subeventosOld = persistentApresentacao.getSubeventos();
            Subeventos subeventosNew = apresentacao.getSubeventos();
            Usuarios usuariosOld = persistentApresentacao.getUsuarios();
            Usuarios usuariosNew = apresentacao.getUsuarios();
            if (subeventosNew != null) {
                subeventosNew = em.getReference(subeventosNew.getClass(), subeventosNew.getIdsubevento());
                apresentacao.setSubeventos(subeventosNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getIdusuario());
                apresentacao.setUsuarios(usuariosNew);
            }
            apresentacao = em.merge(apresentacao);
            if (subeventosOld != null && !subeventosOld.equals(subeventosNew)) {
                subeventosOld.getApresentacaoCollection().remove(apresentacao);
                subeventosOld = em.merge(subeventosOld);
            }
            if (subeventosNew != null && !subeventosNew.equals(subeventosOld)) {
                subeventosNew.getApresentacaoCollection().add(apresentacao);
                subeventosNew = em.merge(subeventosNew);
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                usuariosOld.getApresentacaoCollection().remove(apresentacao);
                usuariosOld = em.merge(usuariosOld);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                usuariosNew.getApresentacaoCollection().add(apresentacao);
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
                Integer id = apresentacao.getIdapresentacao();
                if (findApresentacao(id) == null) {
                    throw new NonexistentEntityException("The apresentacao with id " + id + " no longer exists.");
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
            Apresentacao apresentacao;
            try {
                apresentacao = em.getReference(Apresentacao.class, id);
                apresentacao.getIdapresentacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apresentacao with id " + id + " no longer exists.", enfe);
            }
            Subeventos subeventos = apresentacao.getSubeventos();
            if (subeventos != null) {
                subeventos.getApresentacaoCollection().remove(apresentacao);
                subeventos = em.merge(subeventos);
            }
            Usuarios usuarios = apresentacao.getUsuarios();
            if (usuarios != null) {
                usuarios.getApresentacaoCollection().remove(apresentacao);
                usuarios = em.merge(usuarios);
            }
            em.remove(apresentacao);
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

    public List<Apresentacao> findApresentacaoEntities() {
        return findApresentacaoEntities(true, -1, -1);
    }

    public List<Apresentacao> findApresentacaoEntities(int maxResults, int firstResult) {
        return findApresentacaoEntities(false, maxResults, firstResult);
    }

    private List<Apresentacao> findApresentacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apresentacao.class));
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

    public Apresentacao findApresentacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apresentacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getApresentacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apresentacao> rt = cq.from(Apresentacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
