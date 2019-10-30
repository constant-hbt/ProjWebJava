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
import model.Organizadores;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Submissao;
import model.Editais;
import model.Apresentacao;
import model.Participantes;
import model.Usuarios;

/**
 *
 * @author henrique
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws RollbackFailureException, Exception {
        if (usuarios.getOrganizadoresCollection() == null) {
            usuarios.setOrganizadoresCollection(new ArrayList<Organizadores>());
        }
        if (usuarios.getSubmissaoCollection() == null) {
            usuarios.setSubmissaoCollection(new ArrayList<Submissao>());
        }
        if (usuarios.getEditaisCollection() == null) {
            usuarios.setEditaisCollection(new ArrayList<Editais>());
        }
        if (usuarios.getApresentacaoCollection() == null) {
            usuarios.setApresentacaoCollection(new ArrayList<Apresentacao>());
        }
        if (usuarios.getParticipantesCollection() == null) {
            usuarios.setParticipantesCollection(new ArrayList<Participantes>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status status = usuarios.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                usuarios.setStatus(status);
            }
            Collection<Organizadores> attachedOrganizadoresCollection = new ArrayList<Organizadores>();
            for (Organizadores organizadoresCollectionOrganizadoresToAttach : usuarios.getOrganizadoresCollection()) {
                organizadoresCollectionOrganizadoresToAttach = em.getReference(organizadoresCollectionOrganizadoresToAttach.getClass(), organizadoresCollectionOrganizadoresToAttach.getIdorganizador());
                attachedOrganizadoresCollection.add(organizadoresCollectionOrganizadoresToAttach);
            }
            usuarios.setOrganizadoresCollection(attachedOrganizadoresCollection);
            Collection<Submissao> attachedSubmissaoCollection = new ArrayList<Submissao>();
            for (Submissao submissaoCollectionSubmissaoToAttach : usuarios.getSubmissaoCollection()) {
                submissaoCollectionSubmissaoToAttach = em.getReference(submissaoCollectionSubmissaoToAttach.getClass(), submissaoCollectionSubmissaoToAttach.getIdsubmissao());
                attachedSubmissaoCollection.add(submissaoCollectionSubmissaoToAttach);
            }
            usuarios.setSubmissaoCollection(attachedSubmissaoCollection);
            Collection<Editais> attachedEditaisCollection = new ArrayList<Editais>();
            for (Editais editaisCollectionEditaisToAttach : usuarios.getEditaisCollection()) {
                editaisCollectionEditaisToAttach = em.getReference(editaisCollectionEditaisToAttach.getClass(), editaisCollectionEditaisToAttach.getIdedital());
                attachedEditaisCollection.add(editaisCollectionEditaisToAttach);
            }
            usuarios.setEditaisCollection(attachedEditaisCollection);
            Collection<Apresentacao> attachedApresentacaoCollection = new ArrayList<Apresentacao>();
            for (Apresentacao apresentacaoCollectionApresentacaoToAttach : usuarios.getApresentacaoCollection()) {
                apresentacaoCollectionApresentacaoToAttach = em.getReference(apresentacaoCollectionApresentacaoToAttach.getClass(), apresentacaoCollectionApresentacaoToAttach.getIdapresentacao());
                attachedApresentacaoCollection.add(apresentacaoCollectionApresentacaoToAttach);
            }
            usuarios.setApresentacaoCollection(attachedApresentacaoCollection);
            Collection<Participantes> attachedParticipantesCollection = new ArrayList<Participantes>();
            for (Participantes participantesCollectionParticipantesToAttach : usuarios.getParticipantesCollection()) {
                participantesCollectionParticipantesToAttach = em.getReference(participantesCollectionParticipantesToAttach.getClass(), participantesCollectionParticipantesToAttach.getIdparticipante());
                attachedParticipantesCollection.add(participantesCollectionParticipantesToAttach);
            }
            usuarios.setParticipantesCollection(attachedParticipantesCollection);
            em.persist(usuarios);
            if (status != null) {
                status.getUsuariosCollection().add(usuarios);
                status = em.merge(status);
            }
            for (Organizadores organizadoresCollectionOrganizadores : usuarios.getOrganizadoresCollection()) {
                Usuarios oldUsuariosOfOrganizadoresCollectionOrganizadores = organizadoresCollectionOrganizadores.getUsuarios();
                organizadoresCollectionOrganizadores.setUsuarios(usuarios);
                organizadoresCollectionOrganizadores = em.merge(organizadoresCollectionOrganizadores);
                if (oldUsuariosOfOrganizadoresCollectionOrganizadores != null) {
                    oldUsuariosOfOrganizadoresCollectionOrganizadores.getOrganizadoresCollection().remove(organizadoresCollectionOrganizadores);
                    oldUsuariosOfOrganizadoresCollectionOrganizadores = em.merge(oldUsuariosOfOrganizadoresCollectionOrganizadores);
                }
            }
            for (Submissao submissaoCollectionSubmissao : usuarios.getSubmissaoCollection()) {
                Usuarios oldUsuariosOfSubmissaoCollectionSubmissao = submissaoCollectionSubmissao.getUsuarios();
                submissaoCollectionSubmissao.setUsuarios(usuarios);
                submissaoCollectionSubmissao = em.merge(submissaoCollectionSubmissao);
                if (oldUsuariosOfSubmissaoCollectionSubmissao != null) {
                    oldUsuariosOfSubmissaoCollectionSubmissao.getSubmissaoCollection().remove(submissaoCollectionSubmissao);
                    oldUsuariosOfSubmissaoCollectionSubmissao = em.merge(oldUsuariosOfSubmissaoCollectionSubmissao);
                }
            }
            for (Editais editaisCollectionEditais : usuarios.getEditaisCollection()) {
                Usuarios oldUsuariosOfEditaisCollectionEditais = editaisCollectionEditais.getUsuarios();
                editaisCollectionEditais.setUsuarios(usuarios);
                editaisCollectionEditais = em.merge(editaisCollectionEditais);
                if (oldUsuariosOfEditaisCollectionEditais != null) {
                    oldUsuariosOfEditaisCollectionEditais.getEditaisCollection().remove(editaisCollectionEditais);
                    oldUsuariosOfEditaisCollectionEditais = em.merge(oldUsuariosOfEditaisCollectionEditais);
                }
            }
            for (Apresentacao apresentacaoCollectionApresentacao : usuarios.getApresentacaoCollection()) {
                Usuarios oldUsuariosOfApresentacaoCollectionApresentacao = apresentacaoCollectionApresentacao.getUsuarios();
                apresentacaoCollectionApresentacao.setUsuarios(usuarios);
                apresentacaoCollectionApresentacao = em.merge(apresentacaoCollectionApresentacao);
                if (oldUsuariosOfApresentacaoCollectionApresentacao != null) {
                    oldUsuariosOfApresentacaoCollectionApresentacao.getApresentacaoCollection().remove(apresentacaoCollectionApresentacao);
                    oldUsuariosOfApresentacaoCollectionApresentacao = em.merge(oldUsuariosOfApresentacaoCollectionApresentacao);
                }
            }
            for (Participantes participantesCollectionParticipantes : usuarios.getParticipantesCollection()) {
                Usuarios oldUsuariosOfParticipantesCollectionParticipantes = participantesCollectionParticipantes.getUsuarios();
                participantesCollectionParticipantes.setUsuarios(usuarios);
                participantesCollectionParticipantes = em.merge(participantesCollectionParticipantes);
                if (oldUsuariosOfParticipantesCollectionParticipantes != null) {
                    oldUsuariosOfParticipantesCollectionParticipantes.getParticipantesCollection().remove(participantesCollectionParticipantes);
                    oldUsuariosOfParticipantesCollectionParticipantes = em.merge(oldUsuariosOfParticipantesCollectionParticipantes);
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

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdusuario());
            Status statusOld = persistentUsuarios.getStatus();
            Status statusNew = usuarios.getStatus();
            Collection<Organizadores> organizadoresCollectionOld = persistentUsuarios.getOrganizadoresCollection();
            Collection<Organizadores> organizadoresCollectionNew = usuarios.getOrganizadoresCollection();
            Collection<Submissao> submissaoCollectionOld = persistentUsuarios.getSubmissaoCollection();
            Collection<Submissao> submissaoCollectionNew = usuarios.getSubmissaoCollection();
            Collection<Editais> editaisCollectionOld = persistentUsuarios.getEditaisCollection();
            Collection<Editais> editaisCollectionNew = usuarios.getEditaisCollection();
            Collection<Apresentacao> apresentacaoCollectionOld = persistentUsuarios.getApresentacaoCollection();
            Collection<Apresentacao> apresentacaoCollectionNew = usuarios.getApresentacaoCollection();
            Collection<Participantes> participantesCollectionOld = persistentUsuarios.getParticipantesCollection();
            Collection<Participantes> participantesCollectionNew = usuarios.getParticipantesCollection();
            List<String> illegalOrphanMessages = null;
            for (Organizadores organizadoresCollectionOldOrganizadores : organizadoresCollectionOld) {
                if (!organizadoresCollectionNew.contains(organizadoresCollectionOldOrganizadores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Organizadores " + organizadoresCollectionOldOrganizadores + " since its usuarios field is not nullable.");
                }
            }
            for (Submissao submissaoCollectionOldSubmissao : submissaoCollectionOld) {
                if (!submissaoCollectionNew.contains(submissaoCollectionOldSubmissao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Submissao " + submissaoCollectionOldSubmissao + " since its usuarios field is not nullable.");
                }
            }
            for (Editais editaisCollectionOldEditais : editaisCollectionOld) {
                if (!editaisCollectionNew.contains(editaisCollectionOldEditais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Editais " + editaisCollectionOldEditais + " since its usuarios field is not nullable.");
                }
            }
            for (Apresentacao apresentacaoCollectionOldApresentacao : apresentacaoCollectionOld) {
                if (!apresentacaoCollectionNew.contains(apresentacaoCollectionOldApresentacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Apresentacao " + apresentacaoCollectionOldApresentacao + " since its usuarios field is not nullable.");
                }
            }
            for (Participantes participantesCollectionOldParticipantes : participantesCollectionOld) {
                if (!participantesCollectionNew.contains(participantesCollectionOldParticipantes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participantes " + participantesCollectionOldParticipantes + " since its usuarios field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                usuarios.setStatus(statusNew);
            }
            Collection<Organizadores> attachedOrganizadoresCollectionNew = new ArrayList<Organizadores>();
            for (Organizadores organizadoresCollectionNewOrganizadoresToAttach : organizadoresCollectionNew) {
                organizadoresCollectionNewOrganizadoresToAttach = em.getReference(organizadoresCollectionNewOrganizadoresToAttach.getClass(), organizadoresCollectionNewOrganizadoresToAttach.getIdorganizador());
                attachedOrganizadoresCollectionNew.add(organizadoresCollectionNewOrganizadoresToAttach);
            }
            organizadoresCollectionNew = attachedOrganizadoresCollectionNew;
            usuarios.setOrganizadoresCollection(organizadoresCollectionNew);
            Collection<Submissao> attachedSubmissaoCollectionNew = new ArrayList<Submissao>();
            for (Submissao submissaoCollectionNewSubmissaoToAttach : submissaoCollectionNew) {
                submissaoCollectionNewSubmissaoToAttach = em.getReference(submissaoCollectionNewSubmissaoToAttach.getClass(), submissaoCollectionNewSubmissaoToAttach.getIdsubmissao());
                attachedSubmissaoCollectionNew.add(submissaoCollectionNewSubmissaoToAttach);
            }
            submissaoCollectionNew = attachedSubmissaoCollectionNew;
            usuarios.setSubmissaoCollection(submissaoCollectionNew);
            Collection<Editais> attachedEditaisCollectionNew = new ArrayList<Editais>();
            for (Editais editaisCollectionNewEditaisToAttach : editaisCollectionNew) {
                editaisCollectionNewEditaisToAttach = em.getReference(editaisCollectionNewEditaisToAttach.getClass(), editaisCollectionNewEditaisToAttach.getIdedital());
                attachedEditaisCollectionNew.add(editaisCollectionNewEditaisToAttach);
            }
            editaisCollectionNew = attachedEditaisCollectionNew;
            usuarios.setEditaisCollection(editaisCollectionNew);
            Collection<Apresentacao> attachedApresentacaoCollectionNew = new ArrayList<Apresentacao>();
            for (Apresentacao apresentacaoCollectionNewApresentacaoToAttach : apresentacaoCollectionNew) {
                apresentacaoCollectionNewApresentacaoToAttach = em.getReference(apresentacaoCollectionNewApresentacaoToAttach.getClass(), apresentacaoCollectionNewApresentacaoToAttach.getIdapresentacao());
                attachedApresentacaoCollectionNew.add(apresentacaoCollectionNewApresentacaoToAttach);
            }
            apresentacaoCollectionNew = attachedApresentacaoCollectionNew;
            usuarios.setApresentacaoCollection(apresentacaoCollectionNew);
            Collection<Participantes> attachedParticipantesCollectionNew = new ArrayList<Participantes>();
            for (Participantes participantesCollectionNewParticipantesToAttach : participantesCollectionNew) {
                participantesCollectionNewParticipantesToAttach = em.getReference(participantesCollectionNewParticipantesToAttach.getClass(), participantesCollectionNewParticipantesToAttach.getIdparticipante());
                attachedParticipantesCollectionNew.add(participantesCollectionNewParticipantesToAttach);
            }
            participantesCollectionNew = attachedParticipantesCollectionNew;
            usuarios.setParticipantesCollection(participantesCollectionNew);
            usuarios = em.merge(usuarios);
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getUsuariosCollection().remove(usuarios);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getUsuariosCollection().add(usuarios);
                statusNew = em.merge(statusNew);
            }
            for (Organizadores organizadoresCollectionNewOrganizadores : organizadoresCollectionNew) {
                if (!organizadoresCollectionOld.contains(organizadoresCollectionNewOrganizadores)) {
                    Usuarios oldUsuariosOfOrganizadoresCollectionNewOrganizadores = organizadoresCollectionNewOrganizadores.getUsuarios();
                    organizadoresCollectionNewOrganizadores.setUsuarios(usuarios);
                    organizadoresCollectionNewOrganizadores = em.merge(organizadoresCollectionNewOrganizadores);
                    if (oldUsuariosOfOrganizadoresCollectionNewOrganizadores != null && !oldUsuariosOfOrganizadoresCollectionNewOrganizadores.equals(usuarios)) {
                        oldUsuariosOfOrganizadoresCollectionNewOrganizadores.getOrganizadoresCollection().remove(organizadoresCollectionNewOrganizadores);
                        oldUsuariosOfOrganizadoresCollectionNewOrganizadores = em.merge(oldUsuariosOfOrganizadoresCollectionNewOrganizadores);
                    }
                }
            }
            for (Submissao submissaoCollectionNewSubmissao : submissaoCollectionNew) {
                if (!submissaoCollectionOld.contains(submissaoCollectionNewSubmissao)) {
                    Usuarios oldUsuariosOfSubmissaoCollectionNewSubmissao = submissaoCollectionNewSubmissao.getUsuarios();
                    submissaoCollectionNewSubmissao.setUsuarios(usuarios);
                    submissaoCollectionNewSubmissao = em.merge(submissaoCollectionNewSubmissao);
                    if (oldUsuariosOfSubmissaoCollectionNewSubmissao != null && !oldUsuariosOfSubmissaoCollectionNewSubmissao.equals(usuarios)) {
                        oldUsuariosOfSubmissaoCollectionNewSubmissao.getSubmissaoCollection().remove(submissaoCollectionNewSubmissao);
                        oldUsuariosOfSubmissaoCollectionNewSubmissao = em.merge(oldUsuariosOfSubmissaoCollectionNewSubmissao);
                    }
                }
            }
            for (Editais editaisCollectionNewEditais : editaisCollectionNew) {
                if (!editaisCollectionOld.contains(editaisCollectionNewEditais)) {
                    Usuarios oldUsuariosOfEditaisCollectionNewEditais = editaisCollectionNewEditais.getUsuarios();
                    editaisCollectionNewEditais.setUsuarios(usuarios);
                    editaisCollectionNewEditais = em.merge(editaisCollectionNewEditais);
                    if (oldUsuariosOfEditaisCollectionNewEditais != null && !oldUsuariosOfEditaisCollectionNewEditais.equals(usuarios)) {
                        oldUsuariosOfEditaisCollectionNewEditais.getEditaisCollection().remove(editaisCollectionNewEditais);
                        oldUsuariosOfEditaisCollectionNewEditais = em.merge(oldUsuariosOfEditaisCollectionNewEditais);
                    }
                }
            }
            for (Apresentacao apresentacaoCollectionNewApresentacao : apresentacaoCollectionNew) {
                if (!apresentacaoCollectionOld.contains(apresentacaoCollectionNewApresentacao)) {
                    Usuarios oldUsuariosOfApresentacaoCollectionNewApresentacao = apresentacaoCollectionNewApresentacao.getUsuarios();
                    apresentacaoCollectionNewApresentacao.setUsuarios(usuarios);
                    apresentacaoCollectionNewApresentacao = em.merge(apresentacaoCollectionNewApresentacao);
                    if (oldUsuariosOfApresentacaoCollectionNewApresentacao != null && !oldUsuariosOfApresentacaoCollectionNewApresentacao.equals(usuarios)) {
                        oldUsuariosOfApresentacaoCollectionNewApresentacao.getApresentacaoCollection().remove(apresentacaoCollectionNewApresentacao);
                        oldUsuariosOfApresentacaoCollectionNewApresentacao = em.merge(oldUsuariosOfApresentacaoCollectionNewApresentacao);
                    }
                }
            }
            for (Participantes participantesCollectionNewParticipantes : participantesCollectionNew) {
                if (!participantesCollectionOld.contains(participantesCollectionNewParticipantes)) {
                    Usuarios oldUsuariosOfParticipantesCollectionNewParticipantes = participantesCollectionNewParticipantes.getUsuarios();
                    participantesCollectionNewParticipantes.setUsuarios(usuarios);
                    participantesCollectionNewParticipantes = em.merge(participantesCollectionNewParticipantes);
                    if (oldUsuariosOfParticipantesCollectionNewParticipantes != null && !oldUsuariosOfParticipantesCollectionNewParticipantes.equals(usuarios)) {
                        oldUsuariosOfParticipantesCollectionNewParticipantes.getParticipantesCollection().remove(participantesCollectionNewParticipantes);
                        oldUsuariosOfParticipantesCollectionNewParticipantes = em.merge(oldUsuariosOfParticipantesCollectionNewParticipantes);
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
                Integer id = usuarios.getIdusuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Organizadores> organizadoresCollectionOrphanCheck = usuarios.getOrganizadoresCollection();
            for (Organizadores organizadoresCollectionOrphanCheckOrganizadores : organizadoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Organizadores " + organizadoresCollectionOrphanCheckOrganizadores + " in its organizadoresCollection field has a non-nullable usuarios field.");
            }
            Collection<Submissao> submissaoCollectionOrphanCheck = usuarios.getSubmissaoCollection();
            for (Submissao submissaoCollectionOrphanCheckSubmissao : submissaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Submissao " + submissaoCollectionOrphanCheckSubmissao + " in its submissaoCollection field has a non-nullable usuarios field.");
            }
            Collection<Editais> editaisCollectionOrphanCheck = usuarios.getEditaisCollection();
            for (Editais editaisCollectionOrphanCheckEditais : editaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Editais " + editaisCollectionOrphanCheckEditais + " in its editaisCollection field has a non-nullable usuarios field.");
            }
            Collection<Apresentacao> apresentacaoCollectionOrphanCheck = usuarios.getApresentacaoCollection();
            for (Apresentacao apresentacaoCollectionOrphanCheckApresentacao : apresentacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Apresentacao " + apresentacaoCollectionOrphanCheckApresentacao + " in its apresentacaoCollection field has a non-nullable usuarios field.");
            }
            Collection<Participantes> participantesCollectionOrphanCheck = usuarios.getParticipantesCollection();
            for (Participantes participantesCollectionOrphanCheckParticipantes : participantesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Participantes " + participantesCollectionOrphanCheckParticipantes + " in its participantesCollection field has a non-nullable usuarios field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Status status = usuarios.getStatus();
            if (status != null) {
                status.getUsuariosCollection().remove(usuarios);
                status = em.merge(status);
            }
            em.remove(usuarios);
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

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
