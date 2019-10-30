/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Midias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Categoriamidia;

/**
 *
 * @author henrique
 */
public class CategoriamidiaJpaController implements Serializable {

    public CategoriamidiaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoriamidia categoriamidia) throws RollbackFailureException, Exception {
        if (categoriamidia.getMidiasCollection() == null) {
            categoriamidia.setMidiasCollection(new ArrayList<Midias>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Midias> attachedMidiasCollection = new ArrayList<Midias>();
            for (Midias midiasCollectionMidiasToAttach : categoriamidia.getMidiasCollection()) {
                midiasCollectionMidiasToAttach = em.getReference(midiasCollectionMidiasToAttach.getClass(), midiasCollectionMidiasToAttach.getIdmidia());
                attachedMidiasCollection.add(midiasCollectionMidiasToAttach);
            }
            categoriamidia.setMidiasCollection(attachedMidiasCollection);
            em.persist(categoriamidia);
            for (Midias midiasCollectionMidias : categoriamidia.getMidiasCollection()) {
                Categoriamidia oldCategoriamidiaOfMidiasCollectionMidias = midiasCollectionMidias.getCategoriamidia();
                midiasCollectionMidias.setCategoriamidia(categoriamidia);
                midiasCollectionMidias = em.merge(midiasCollectionMidias);
                if (oldCategoriamidiaOfMidiasCollectionMidias != null) {
                    oldCategoriamidiaOfMidiasCollectionMidias.getMidiasCollection().remove(midiasCollectionMidias);
                    oldCategoriamidiaOfMidiasCollectionMidias = em.merge(oldCategoriamidiaOfMidiasCollectionMidias);
                }
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

    public void edit(Categoriamidia categoriamidia) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoriamidia persistentCategoriamidia = em.find(Categoriamidia.class, categoriamidia.getIdcategoriamidia());
            Collection<Midias> midiasCollectionOld = persistentCategoriamidia.getMidiasCollection();
            Collection<Midias> midiasCollectionNew = categoriamidia.getMidiasCollection();
            List<String> illegalOrphanMessages = null;
            for (Midias midiasCollectionOldMidias : midiasCollectionOld) {
                if (!midiasCollectionNew.contains(midiasCollectionOldMidias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Midias " + midiasCollectionOldMidias + " since its categoriamidia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Midias> attachedMidiasCollectionNew = new ArrayList<Midias>();
            for (Midias midiasCollectionNewMidiasToAttach : midiasCollectionNew) {
                midiasCollectionNewMidiasToAttach = em.getReference(midiasCollectionNewMidiasToAttach.getClass(), midiasCollectionNewMidiasToAttach.getIdmidia());
                attachedMidiasCollectionNew.add(midiasCollectionNewMidiasToAttach);
            }
            midiasCollectionNew = attachedMidiasCollectionNew;
            categoriamidia.setMidiasCollection(midiasCollectionNew);
            categoriamidia = em.merge(categoriamidia);
            for (Midias midiasCollectionNewMidias : midiasCollectionNew) {
                if (!midiasCollectionOld.contains(midiasCollectionNewMidias)) {
                    Categoriamidia oldCategoriamidiaOfMidiasCollectionNewMidias = midiasCollectionNewMidias.getCategoriamidia();
                    midiasCollectionNewMidias.setCategoriamidia(categoriamidia);
                    midiasCollectionNewMidias = em.merge(midiasCollectionNewMidias);
                    if (oldCategoriamidiaOfMidiasCollectionNewMidias != null && !oldCategoriamidiaOfMidiasCollectionNewMidias.equals(categoriamidia)) {
                        oldCategoriamidiaOfMidiasCollectionNewMidias.getMidiasCollection().remove(midiasCollectionNewMidias);
                        oldCategoriamidiaOfMidiasCollectionNewMidias = em.merge(oldCategoriamidiaOfMidiasCollectionNewMidias);
                    }
                }
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
                Integer id = categoriamidia.getIdcategoriamidia();
                if (findCategoriamidia(id) == null) {
                    throw new NonexistentEntityException("The categoriamidia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoriamidia categoriamidia;
            try {
                categoriamidia = em.getReference(Categoriamidia.class, id);
                categoriamidia.getIdcategoriamidia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriamidia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Midias> midiasCollectionOrphanCheck = categoriamidia.getMidiasCollection();
            for (Midias midiasCollectionOrphanCheckMidias : midiasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoriamidia (" + categoriamidia + ") cannot be destroyed since the Midias " + midiasCollectionOrphanCheckMidias + " in its midiasCollection field has a non-nullable categoriamidia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoriamidia);
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

    public List<Categoriamidia> findCategoriamidiaEntities() {
        return findCategoriamidiaEntities(true, -1, -1);
    }

    public List<Categoriamidia> findCategoriamidiaEntities(int maxResults, int firstResult) {
        return findCategoriamidiaEntities(false, maxResults, firstResult);
    }

    private List<Categoriamidia> findCategoriamidiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoriamidia.class));
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

    public Categoriamidia findCategoriamidia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoriamidia.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriamidiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoriamidia> rt = cq.from(Categoriamidia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
