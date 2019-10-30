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
import model.Status;
import model.InscricaoEquipeSub;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Equipes;
import model.PresencasEquipeSub;
import model.ParticipanteEquipe;

/**
 *
 * @author henrique
 */
public class EquipesJpaController implements Serializable {

    public EquipesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipes equipes) throws RollbackFailureException, Exception {
        if (equipes.getInscricaoEquipeSubCollection() == null) {
            equipes.setInscricaoEquipeSubCollection(new ArrayList<InscricaoEquipeSub>());
        }
        if (equipes.getPresencasEquipeSubCollection() == null) {
            equipes.setPresencasEquipeSubCollection(new ArrayList<PresencasEquipeSub>());
        }
        if (equipes.getParticipanteEquipeCollection() == null) {
            equipes.setParticipanteEquipeCollection(new ArrayList<ParticipanteEquipe>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status status = equipes.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                equipes.setStatus(status);
            }
            Collection<InscricaoEquipeSub> attachedInscricaoEquipeSubCollection = new ArrayList<InscricaoEquipeSub>();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach : equipes.getInscricaoEquipeSubCollection()) {
                inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach = em.getReference(inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach.getClass(), inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach.getIdequipesub());
                attachedInscricaoEquipeSubCollection.add(inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach);
            }
            equipes.setInscricaoEquipeSubCollection(attachedInscricaoEquipeSubCollection);
            Collection<PresencasEquipeSub> attachedPresencasEquipeSubCollection = new ArrayList<PresencasEquipeSub>();
            for (PresencasEquipeSub presencasEquipeSubCollectionPresencasEquipeSubToAttach : equipes.getPresencasEquipeSubCollection()) {
                presencasEquipeSubCollectionPresencasEquipeSubToAttach = em.getReference(presencasEquipeSubCollectionPresencasEquipeSubToAttach.getClass(), presencasEquipeSubCollectionPresencasEquipeSubToAttach.getPresencasEquipeSubPK());
                attachedPresencasEquipeSubCollection.add(presencasEquipeSubCollectionPresencasEquipeSubToAttach);
            }
            equipes.setPresencasEquipeSubCollection(attachedPresencasEquipeSubCollection);
            Collection<ParticipanteEquipe> attachedParticipanteEquipeCollection = new ArrayList<ParticipanteEquipe>();
            for (ParticipanteEquipe participanteEquipeCollectionParticipanteEquipeToAttach : equipes.getParticipanteEquipeCollection()) {
                participanteEquipeCollectionParticipanteEquipeToAttach = em.getReference(participanteEquipeCollectionParticipanteEquipeToAttach.getClass(), participanteEquipeCollectionParticipanteEquipeToAttach.getIdpartequipe());
                attachedParticipanteEquipeCollection.add(participanteEquipeCollectionParticipanteEquipeToAttach);
            }
            equipes.setParticipanteEquipeCollection(attachedParticipanteEquipeCollection);
            em.persist(equipes);
            if (status != null) {
                status.getEquipesCollection().add(equipes);
                status = em.merge(status);
            }
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionInscricaoEquipeSub : equipes.getInscricaoEquipeSubCollection()) {
                Equipes oldEquipesOfInscricaoEquipeSubCollectionInscricaoEquipeSub = inscricaoEquipeSubCollectionInscricaoEquipeSub.getEquipes();
                inscricaoEquipeSubCollectionInscricaoEquipeSub.setEquipes(equipes);
                inscricaoEquipeSubCollectionInscricaoEquipeSub = em.merge(inscricaoEquipeSubCollectionInscricaoEquipeSub);
                if (oldEquipesOfInscricaoEquipeSubCollectionInscricaoEquipeSub != null) {
                    oldEquipesOfInscricaoEquipeSubCollectionInscricaoEquipeSub.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSubCollectionInscricaoEquipeSub);
                    oldEquipesOfInscricaoEquipeSubCollectionInscricaoEquipeSub = em.merge(oldEquipesOfInscricaoEquipeSubCollectionInscricaoEquipeSub);
                }
            }
            for (PresencasEquipeSub presencasEquipeSubCollectionPresencasEquipeSub : equipes.getPresencasEquipeSubCollection()) {
                Equipes oldEquipesOfPresencasEquipeSubCollectionPresencasEquipeSub = presencasEquipeSubCollectionPresencasEquipeSub.getEquipes();
                presencasEquipeSubCollectionPresencasEquipeSub.setEquipes(equipes);
                presencasEquipeSubCollectionPresencasEquipeSub = em.merge(presencasEquipeSubCollectionPresencasEquipeSub);
                if (oldEquipesOfPresencasEquipeSubCollectionPresencasEquipeSub != null) {
                    oldEquipesOfPresencasEquipeSubCollectionPresencasEquipeSub.getPresencasEquipeSubCollection().remove(presencasEquipeSubCollectionPresencasEquipeSub);
                    oldEquipesOfPresencasEquipeSubCollectionPresencasEquipeSub = em.merge(oldEquipesOfPresencasEquipeSubCollectionPresencasEquipeSub);
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionParticipanteEquipe : equipes.getParticipanteEquipeCollection()) {
                Equipes oldEquipesOfParticipanteEquipeCollectionParticipanteEquipe = participanteEquipeCollectionParticipanteEquipe.getEquipes();
                participanteEquipeCollectionParticipanteEquipe.setEquipes(equipes);
                participanteEquipeCollectionParticipanteEquipe = em.merge(participanteEquipeCollectionParticipanteEquipe);
                if (oldEquipesOfParticipanteEquipeCollectionParticipanteEquipe != null) {
                    oldEquipesOfParticipanteEquipeCollectionParticipanteEquipe.getParticipanteEquipeCollection().remove(participanteEquipeCollectionParticipanteEquipe);
                    oldEquipesOfParticipanteEquipeCollectionParticipanteEquipe = em.merge(oldEquipesOfParticipanteEquipeCollectionParticipanteEquipe);
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

    public void edit(Equipes equipes) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipes persistentEquipes = em.find(Equipes.class, equipes.getIdequipe());
            Status statusOld = persistentEquipes.getStatus();
            Status statusNew = equipes.getStatus();
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionOld = persistentEquipes.getInscricaoEquipeSubCollection();
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionNew = equipes.getInscricaoEquipeSubCollection();
            Collection<PresencasEquipeSub> presencasEquipeSubCollectionOld = persistentEquipes.getPresencasEquipeSubCollection();
            Collection<PresencasEquipeSub> presencasEquipeSubCollectionNew = equipes.getPresencasEquipeSubCollection();
            Collection<ParticipanteEquipe> participanteEquipeCollectionOld = persistentEquipes.getParticipanteEquipeCollection();
            Collection<ParticipanteEquipe> participanteEquipeCollectionNew = equipes.getParticipanteEquipeCollection();
            List<String> illegalOrphanMessages = null;
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionOldInscricaoEquipeSub : inscricaoEquipeSubCollectionOld) {
                if (!inscricaoEquipeSubCollectionNew.contains(inscricaoEquipeSubCollectionOldInscricaoEquipeSub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoEquipeSub " + inscricaoEquipeSubCollectionOldInscricaoEquipeSub + " since its equipes field is not nullable.");
                }
            }
            for (PresencasEquipeSub presencasEquipeSubCollectionOldPresencasEquipeSub : presencasEquipeSubCollectionOld) {
                if (!presencasEquipeSubCollectionNew.contains(presencasEquipeSubCollectionOldPresencasEquipeSub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencasEquipeSub " + presencasEquipeSubCollectionOldPresencasEquipeSub + " since its equipes field is not nullable.");
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionOldParticipanteEquipe : participanteEquipeCollectionOld) {
                if (!participanteEquipeCollectionNew.contains(participanteEquipeCollectionOldParticipanteEquipe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipanteEquipe " + participanteEquipeCollectionOldParticipanteEquipe + " since its equipes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                equipes.setStatus(statusNew);
            }
            Collection<InscricaoEquipeSub> attachedInscricaoEquipeSubCollectionNew = new ArrayList<InscricaoEquipeSub>();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach : inscricaoEquipeSubCollectionNew) {
                inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach = em.getReference(inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach.getClass(), inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach.getIdequipesub());
                attachedInscricaoEquipeSubCollectionNew.add(inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach);
            }
            inscricaoEquipeSubCollectionNew = attachedInscricaoEquipeSubCollectionNew;
            equipes.setInscricaoEquipeSubCollection(inscricaoEquipeSubCollectionNew);
            Collection<PresencasEquipeSub> attachedPresencasEquipeSubCollectionNew = new ArrayList<PresencasEquipeSub>();
            for (PresencasEquipeSub presencasEquipeSubCollectionNewPresencasEquipeSubToAttach : presencasEquipeSubCollectionNew) {
                presencasEquipeSubCollectionNewPresencasEquipeSubToAttach = em.getReference(presencasEquipeSubCollectionNewPresencasEquipeSubToAttach.getClass(), presencasEquipeSubCollectionNewPresencasEquipeSubToAttach.getPresencasEquipeSubPK());
                attachedPresencasEquipeSubCollectionNew.add(presencasEquipeSubCollectionNewPresencasEquipeSubToAttach);
            }
            presencasEquipeSubCollectionNew = attachedPresencasEquipeSubCollectionNew;
            equipes.setPresencasEquipeSubCollection(presencasEquipeSubCollectionNew);
            Collection<ParticipanteEquipe> attachedParticipanteEquipeCollectionNew = new ArrayList<ParticipanteEquipe>();
            for (ParticipanteEquipe participanteEquipeCollectionNewParticipanteEquipeToAttach : participanteEquipeCollectionNew) {
                participanteEquipeCollectionNewParticipanteEquipeToAttach = em.getReference(participanteEquipeCollectionNewParticipanteEquipeToAttach.getClass(), participanteEquipeCollectionNewParticipanteEquipeToAttach.getIdpartequipe());
                attachedParticipanteEquipeCollectionNew.add(participanteEquipeCollectionNewParticipanteEquipeToAttach);
            }
            participanteEquipeCollectionNew = attachedParticipanteEquipeCollectionNew;
            equipes.setParticipanteEquipeCollection(participanteEquipeCollectionNew);
            equipes = em.merge(equipes);
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getEquipesCollection().remove(equipes);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getEquipesCollection().add(equipes);
                statusNew = em.merge(statusNew);
            }
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionNewInscricaoEquipeSub : inscricaoEquipeSubCollectionNew) {
                if (!inscricaoEquipeSubCollectionOld.contains(inscricaoEquipeSubCollectionNewInscricaoEquipeSub)) {
                    Equipes oldEquipesOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub = inscricaoEquipeSubCollectionNewInscricaoEquipeSub.getEquipes();
                    inscricaoEquipeSubCollectionNewInscricaoEquipeSub.setEquipes(equipes);
                    inscricaoEquipeSubCollectionNewInscricaoEquipeSub = em.merge(inscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                    if (oldEquipesOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub != null && !oldEquipesOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub.equals(equipes)) {
                        oldEquipesOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                        oldEquipesOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub = em.merge(oldEquipesOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                    }
                }
            }
            for (PresencasEquipeSub presencasEquipeSubCollectionNewPresencasEquipeSub : presencasEquipeSubCollectionNew) {
                if (!presencasEquipeSubCollectionOld.contains(presencasEquipeSubCollectionNewPresencasEquipeSub)) {
                    Equipes oldEquipesOfPresencasEquipeSubCollectionNewPresencasEquipeSub = presencasEquipeSubCollectionNewPresencasEquipeSub.getEquipes();
                    presencasEquipeSubCollectionNewPresencasEquipeSub.setEquipes(equipes);
                    presencasEquipeSubCollectionNewPresencasEquipeSub = em.merge(presencasEquipeSubCollectionNewPresencasEquipeSub);
                    if (oldEquipesOfPresencasEquipeSubCollectionNewPresencasEquipeSub != null && !oldEquipesOfPresencasEquipeSubCollectionNewPresencasEquipeSub.equals(equipes)) {
                        oldEquipesOfPresencasEquipeSubCollectionNewPresencasEquipeSub.getPresencasEquipeSubCollection().remove(presencasEquipeSubCollectionNewPresencasEquipeSub);
                        oldEquipesOfPresencasEquipeSubCollectionNewPresencasEquipeSub = em.merge(oldEquipesOfPresencasEquipeSubCollectionNewPresencasEquipeSub);
                    }
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionNewParticipanteEquipe : participanteEquipeCollectionNew) {
                if (!participanteEquipeCollectionOld.contains(participanteEquipeCollectionNewParticipanteEquipe)) {
                    Equipes oldEquipesOfParticipanteEquipeCollectionNewParticipanteEquipe = participanteEquipeCollectionNewParticipanteEquipe.getEquipes();
                    participanteEquipeCollectionNewParticipanteEquipe.setEquipes(equipes);
                    participanteEquipeCollectionNewParticipanteEquipe = em.merge(participanteEquipeCollectionNewParticipanteEquipe);
                    if (oldEquipesOfParticipanteEquipeCollectionNewParticipanteEquipe != null && !oldEquipesOfParticipanteEquipeCollectionNewParticipanteEquipe.equals(equipes)) {
                        oldEquipesOfParticipanteEquipeCollectionNewParticipanteEquipe.getParticipanteEquipeCollection().remove(participanteEquipeCollectionNewParticipanteEquipe);
                        oldEquipesOfParticipanteEquipeCollectionNewParticipanteEquipe = em.merge(oldEquipesOfParticipanteEquipeCollectionNewParticipanteEquipe);
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
                Integer id = equipes.getIdequipe();
                if (findEquipes(id) == null) {
                    throw new NonexistentEntityException("The equipes with id " + id + " no longer exists.");
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
            Equipes equipes;
            try {
                equipes = em.getReference(Equipes.class, id);
                equipes.getIdequipe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionOrphanCheck = equipes.getInscricaoEquipeSubCollection();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionOrphanCheckInscricaoEquipeSub : inscricaoEquipeSubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipes (" + equipes + ") cannot be destroyed since the InscricaoEquipeSub " + inscricaoEquipeSubCollectionOrphanCheckInscricaoEquipeSub + " in its inscricaoEquipeSubCollection field has a non-nullable equipes field.");
            }
            Collection<PresencasEquipeSub> presencasEquipeSubCollectionOrphanCheck = equipes.getPresencasEquipeSubCollection();
            for (PresencasEquipeSub presencasEquipeSubCollectionOrphanCheckPresencasEquipeSub : presencasEquipeSubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipes (" + equipes + ") cannot be destroyed since the PresencasEquipeSub " + presencasEquipeSubCollectionOrphanCheckPresencasEquipeSub + " in its presencasEquipeSubCollection field has a non-nullable equipes field.");
            }
            Collection<ParticipanteEquipe> participanteEquipeCollectionOrphanCheck = equipes.getParticipanteEquipeCollection();
            for (ParticipanteEquipe participanteEquipeCollectionOrphanCheckParticipanteEquipe : participanteEquipeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipes (" + equipes + ") cannot be destroyed since the ParticipanteEquipe " + participanteEquipeCollectionOrphanCheckParticipanteEquipe + " in its participanteEquipeCollection field has a non-nullable equipes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Status status = equipes.getStatus();
            if (status != null) {
                status.getEquipesCollection().remove(equipes);
                status = em.merge(status);
            }
            em.remove(equipes);
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

    public List<Equipes> findEquipesEntities() {
        return findEquipesEntities(true, -1, -1);
    }

    public List<Equipes> findEquipesEntities(int maxResults, int firstResult) {
        return findEquipesEntities(false, maxResults, firstResult);
    }

    private List<Equipes> findEquipesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipes.class));
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

    public Equipes findEquipes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipes.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipes> rt = cq.from(Equipes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
