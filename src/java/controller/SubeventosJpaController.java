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
import model.Eventos;
import model.Salas;
import model.Status;
import model.InscricaoEquipeSub;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.InscricaoPartSubeve;
import model.Midias;
import model.PresencasSub;
import model.PresencasEquipeSub;
import model.Submissao;
import model.Apresentacao;
import model.Subeventos;

/**
 *
 * @author henrique
 */
public class SubeventosJpaController implements Serializable {

    public SubeventosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subeventos subeventos) throws RollbackFailureException, Exception {
        if (subeventos.getInscricaoEquipeSubCollection() == null) {
            subeventos.setInscricaoEquipeSubCollection(new ArrayList<InscricaoEquipeSub>());
        }
        if (subeventos.getInscricaoPartSubeveCollection() == null) {
            subeventos.setInscricaoPartSubeveCollection(new ArrayList<InscricaoPartSubeve>());
        }
        if (subeventos.getMidiasCollection() == null) {
            subeventos.setMidiasCollection(new ArrayList<Midias>());
        }
        if (subeventos.getPresencasSubCollection() == null) {
            subeventos.setPresencasSubCollection(new ArrayList<PresencasSub>());
        }
        if (subeventos.getPresencasEquipeSubCollection() == null) {
            subeventos.setPresencasEquipeSubCollection(new ArrayList<PresencasEquipeSub>());
        }
        if (subeventos.getSubmissaoCollection() == null) {
            subeventos.setSubmissaoCollection(new ArrayList<Submissao>());
        }
        if (subeventos.getApresentacaoCollection() == null) {
            subeventos.setApresentacaoCollection(new ArrayList<Apresentacao>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = subeventos.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                subeventos.setEventos(eventos);
            }
            Salas salas = subeventos.getSalas();
            if (salas != null) {
                salas = em.getReference(salas.getClass(), salas.getIdsala());
                subeventos.setSalas(salas);
            }
            Status status = subeventos.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                subeventos.setStatus(status);
            }
            Collection<InscricaoEquipeSub> attachedInscricaoEquipeSubCollection = new ArrayList<InscricaoEquipeSub>();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach : subeventos.getInscricaoEquipeSubCollection()) {
                inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach = em.getReference(inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach.getClass(), inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach.getIdequipesub());
                attachedInscricaoEquipeSubCollection.add(inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach);
            }
            subeventos.setInscricaoEquipeSubCollection(attachedInscricaoEquipeSubCollection);
            Collection<InscricaoPartSubeve> attachedInscricaoPartSubeveCollection = new ArrayList<InscricaoPartSubeve>();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach : subeventos.getInscricaoPartSubeveCollection()) {
                inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach = em.getReference(inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach.getClass(), inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach.getIdinscricao());
                attachedInscricaoPartSubeveCollection.add(inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach);
            }
            subeventos.setInscricaoPartSubeveCollection(attachedInscricaoPartSubeveCollection);
            Collection<Midias> attachedMidiasCollection = new ArrayList<Midias>();
            for (Midias midiasCollectionMidiasToAttach : subeventos.getMidiasCollection()) {
                midiasCollectionMidiasToAttach = em.getReference(midiasCollectionMidiasToAttach.getClass(), midiasCollectionMidiasToAttach.getIdmidia());
                attachedMidiasCollection.add(midiasCollectionMidiasToAttach);
            }
            subeventos.setMidiasCollection(attachedMidiasCollection);
            Collection<PresencasSub> attachedPresencasSubCollection = new ArrayList<PresencasSub>();
            for (PresencasSub presencasSubCollectionPresencasSubToAttach : subeventos.getPresencasSubCollection()) {
                presencasSubCollectionPresencasSubToAttach = em.getReference(presencasSubCollectionPresencasSubToAttach.getClass(), presencasSubCollectionPresencasSubToAttach.getPresencasSubPK());
                attachedPresencasSubCollection.add(presencasSubCollectionPresencasSubToAttach);
            }
            subeventos.setPresencasSubCollection(attachedPresencasSubCollection);
            Collection<PresencasEquipeSub> attachedPresencasEquipeSubCollection = new ArrayList<PresencasEquipeSub>();
            for (PresencasEquipeSub presencasEquipeSubCollectionPresencasEquipeSubToAttach : subeventos.getPresencasEquipeSubCollection()) {
                presencasEquipeSubCollectionPresencasEquipeSubToAttach = em.getReference(presencasEquipeSubCollectionPresencasEquipeSubToAttach.getClass(), presencasEquipeSubCollectionPresencasEquipeSubToAttach.getPresencasEquipeSubPK());
                attachedPresencasEquipeSubCollection.add(presencasEquipeSubCollectionPresencasEquipeSubToAttach);
            }
            subeventos.setPresencasEquipeSubCollection(attachedPresencasEquipeSubCollection);
            Collection<Submissao> attachedSubmissaoCollection = new ArrayList<Submissao>();
            for (Submissao submissaoCollectionSubmissaoToAttach : subeventos.getSubmissaoCollection()) {
                submissaoCollectionSubmissaoToAttach = em.getReference(submissaoCollectionSubmissaoToAttach.getClass(), submissaoCollectionSubmissaoToAttach.getIdsubmissao());
                attachedSubmissaoCollection.add(submissaoCollectionSubmissaoToAttach);
            }
            subeventos.setSubmissaoCollection(attachedSubmissaoCollection);
            Collection<Apresentacao> attachedApresentacaoCollection = new ArrayList<Apresentacao>();
            for (Apresentacao apresentacaoCollectionApresentacaoToAttach : subeventos.getApresentacaoCollection()) {
                apresentacaoCollectionApresentacaoToAttach = em.getReference(apresentacaoCollectionApresentacaoToAttach.getClass(), apresentacaoCollectionApresentacaoToAttach.getIdapresentacao());
                attachedApresentacaoCollection.add(apresentacaoCollectionApresentacaoToAttach);
            }
            subeventos.setApresentacaoCollection(attachedApresentacaoCollection);
            em.persist(subeventos);
            if (eventos != null) {
                eventos.getSubeventosCollection().add(subeventos);
                eventos = em.merge(eventos);
            }
            if (salas != null) {
                salas.getSubeventosCollection().add(subeventos);
                salas = em.merge(salas);
            }
            if (status != null) {
                status.getSubeventosCollection().add(subeventos);
                status = em.merge(status);
            }
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionInscricaoEquipeSub : subeventos.getInscricaoEquipeSubCollection()) {
                Subeventos oldSubeventosOfInscricaoEquipeSubCollectionInscricaoEquipeSub = inscricaoEquipeSubCollectionInscricaoEquipeSub.getSubeventos();
                inscricaoEquipeSubCollectionInscricaoEquipeSub.setSubeventos(subeventos);
                inscricaoEquipeSubCollectionInscricaoEquipeSub = em.merge(inscricaoEquipeSubCollectionInscricaoEquipeSub);
                if (oldSubeventosOfInscricaoEquipeSubCollectionInscricaoEquipeSub != null) {
                    oldSubeventosOfInscricaoEquipeSubCollectionInscricaoEquipeSub.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSubCollectionInscricaoEquipeSub);
                    oldSubeventosOfInscricaoEquipeSubCollectionInscricaoEquipeSub = em.merge(oldSubeventosOfInscricaoEquipeSubCollectionInscricaoEquipeSub);
                }
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionInscricaoPartSubeve : subeventos.getInscricaoPartSubeveCollection()) {
                Subeventos oldSubeventosOfInscricaoPartSubeveCollectionInscricaoPartSubeve = inscricaoPartSubeveCollectionInscricaoPartSubeve.getSubeventos();
                inscricaoPartSubeveCollectionInscricaoPartSubeve.setSubeventos(subeventos);
                inscricaoPartSubeveCollectionInscricaoPartSubeve = em.merge(inscricaoPartSubeveCollectionInscricaoPartSubeve);
                if (oldSubeventosOfInscricaoPartSubeveCollectionInscricaoPartSubeve != null) {
                    oldSubeventosOfInscricaoPartSubeveCollectionInscricaoPartSubeve.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeveCollectionInscricaoPartSubeve);
                    oldSubeventosOfInscricaoPartSubeveCollectionInscricaoPartSubeve = em.merge(oldSubeventosOfInscricaoPartSubeveCollectionInscricaoPartSubeve);
                }
            }
            for (Midias midiasCollectionMidias : subeventos.getMidiasCollection()) {
                Subeventos oldSubeventosOfMidiasCollectionMidias = midiasCollectionMidias.getSubeventos();
                midiasCollectionMidias.setSubeventos(subeventos);
                midiasCollectionMidias = em.merge(midiasCollectionMidias);
                if (oldSubeventosOfMidiasCollectionMidias != null) {
                    oldSubeventosOfMidiasCollectionMidias.getMidiasCollection().remove(midiasCollectionMidias);
                    oldSubeventosOfMidiasCollectionMidias = em.merge(oldSubeventosOfMidiasCollectionMidias);
                }
            }
            for (PresencasSub presencasSubCollectionPresencasSub : subeventos.getPresencasSubCollection()) {
                Subeventos oldSubeventosOfPresencasSubCollectionPresencasSub = presencasSubCollectionPresencasSub.getSubeventos();
                presencasSubCollectionPresencasSub.setSubeventos(subeventos);
                presencasSubCollectionPresencasSub = em.merge(presencasSubCollectionPresencasSub);
                if (oldSubeventosOfPresencasSubCollectionPresencasSub != null) {
                    oldSubeventosOfPresencasSubCollectionPresencasSub.getPresencasSubCollection().remove(presencasSubCollectionPresencasSub);
                    oldSubeventosOfPresencasSubCollectionPresencasSub = em.merge(oldSubeventosOfPresencasSubCollectionPresencasSub);
                }
            }
            for (PresencasEquipeSub presencasEquipeSubCollectionPresencasEquipeSub : subeventos.getPresencasEquipeSubCollection()) {
                Subeventos oldSubeventosOfPresencasEquipeSubCollectionPresencasEquipeSub = presencasEquipeSubCollectionPresencasEquipeSub.getSubeventos();
                presencasEquipeSubCollectionPresencasEquipeSub.setSubeventos(subeventos);
                presencasEquipeSubCollectionPresencasEquipeSub = em.merge(presencasEquipeSubCollectionPresencasEquipeSub);
                if (oldSubeventosOfPresencasEquipeSubCollectionPresencasEquipeSub != null) {
                    oldSubeventosOfPresencasEquipeSubCollectionPresencasEquipeSub.getPresencasEquipeSubCollection().remove(presencasEquipeSubCollectionPresencasEquipeSub);
                    oldSubeventosOfPresencasEquipeSubCollectionPresencasEquipeSub = em.merge(oldSubeventosOfPresencasEquipeSubCollectionPresencasEquipeSub);
                }
            }
            for (Submissao submissaoCollectionSubmissao : subeventos.getSubmissaoCollection()) {
                Subeventos oldSubeventosOfSubmissaoCollectionSubmissao = submissaoCollectionSubmissao.getSubeventos();
                submissaoCollectionSubmissao.setSubeventos(subeventos);
                submissaoCollectionSubmissao = em.merge(submissaoCollectionSubmissao);
                if (oldSubeventosOfSubmissaoCollectionSubmissao != null) {
                    oldSubeventosOfSubmissaoCollectionSubmissao.getSubmissaoCollection().remove(submissaoCollectionSubmissao);
                    oldSubeventosOfSubmissaoCollectionSubmissao = em.merge(oldSubeventosOfSubmissaoCollectionSubmissao);
                }
            }
            for (Apresentacao apresentacaoCollectionApresentacao : subeventos.getApresentacaoCollection()) {
                Subeventos oldSubeventosOfApresentacaoCollectionApresentacao = apresentacaoCollectionApresentacao.getSubeventos();
                apresentacaoCollectionApresentacao.setSubeventos(subeventos);
                apresentacaoCollectionApresentacao = em.merge(apresentacaoCollectionApresentacao);
                if (oldSubeventosOfApresentacaoCollectionApresentacao != null) {
                    oldSubeventosOfApresentacaoCollectionApresentacao.getApresentacaoCollection().remove(apresentacaoCollectionApresentacao);
                    oldSubeventosOfApresentacaoCollectionApresentacao = em.merge(oldSubeventosOfApresentacaoCollectionApresentacao);
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

    public void edit(Subeventos subeventos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subeventos persistentSubeventos = em.find(Subeventos.class, subeventos.getIdsubevento());
            Eventos eventosOld = persistentSubeventos.getEventos();
            Eventos eventosNew = subeventos.getEventos();
            Salas salasOld = persistentSubeventos.getSalas();
            Salas salasNew = subeventos.getSalas();
            Status statusOld = persistentSubeventos.getStatus();
            Status statusNew = subeventos.getStatus();
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionOld = persistentSubeventos.getInscricaoEquipeSubCollection();
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionNew = subeventos.getInscricaoEquipeSubCollection();
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionOld = persistentSubeventos.getInscricaoPartSubeveCollection();
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionNew = subeventos.getInscricaoPartSubeveCollection();
            Collection<Midias> midiasCollectionOld = persistentSubeventos.getMidiasCollection();
            Collection<Midias> midiasCollectionNew = subeventos.getMidiasCollection();
            Collection<PresencasSub> presencasSubCollectionOld = persistentSubeventos.getPresencasSubCollection();
            Collection<PresencasSub> presencasSubCollectionNew = subeventos.getPresencasSubCollection();
            Collection<PresencasEquipeSub> presencasEquipeSubCollectionOld = persistentSubeventos.getPresencasEquipeSubCollection();
            Collection<PresencasEquipeSub> presencasEquipeSubCollectionNew = subeventos.getPresencasEquipeSubCollection();
            Collection<Submissao> submissaoCollectionOld = persistentSubeventos.getSubmissaoCollection();
            Collection<Submissao> submissaoCollectionNew = subeventos.getSubmissaoCollection();
            Collection<Apresentacao> apresentacaoCollectionOld = persistentSubeventos.getApresentacaoCollection();
            Collection<Apresentacao> apresentacaoCollectionNew = subeventos.getApresentacaoCollection();
            List<String> illegalOrphanMessages = null;
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionOldInscricaoEquipeSub : inscricaoEquipeSubCollectionOld) {
                if (!inscricaoEquipeSubCollectionNew.contains(inscricaoEquipeSubCollectionOldInscricaoEquipeSub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoEquipeSub " + inscricaoEquipeSubCollectionOldInscricaoEquipeSub + " since its subeventos field is not nullable.");
                }
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionOldInscricaoPartSubeve : inscricaoPartSubeveCollectionOld) {
                if (!inscricaoPartSubeveCollectionNew.contains(inscricaoPartSubeveCollectionOldInscricaoPartSubeve)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoPartSubeve " + inscricaoPartSubeveCollectionOldInscricaoPartSubeve + " since its subeventos field is not nullable.");
                }
            }
            for (PresencasSub presencasSubCollectionOldPresencasSub : presencasSubCollectionOld) {
                if (!presencasSubCollectionNew.contains(presencasSubCollectionOldPresencasSub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencasSub " + presencasSubCollectionOldPresencasSub + " since its subeventos field is not nullable.");
                }
            }
            for (PresencasEquipeSub presencasEquipeSubCollectionOldPresencasEquipeSub : presencasEquipeSubCollectionOld) {
                if (!presencasEquipeSubCollectionNew.contains(presencasEquipeSubCollectionOldPresencasEquipeSub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencasEquipeSub " + presencasEquipeSubCollectionOldPresencasEquipeSub + " since its subeventos field is not nullable.");
                }
            }
            for (Submissao submissaoCollectionOldSubmissao : submissaoCollectionOld) {
                if (!submissaoCollectionNew.contains(submissaoCollectionOldSubmissao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Submissao " + submissaoCollectionOldSubmissao + " since its subeventos field is not nullable.");
                }
            }
            for (Apresentacao apresentacaoCollectionOldApresentacao : apresentacaoCollectionOld) {
                if (!apresentacaoCollectionNew.contains(apresentacaoCollectionOldApresentacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Apresentacao " + apresentacaoCollectionOldApresentacao + " since its subeventos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                subeventos.setEventos(eventosNew);
            }
            if (salasNew != null) {
                salasNew = em.getReference(salasNew.getClass(), salasNew.getIdsala());
                subeventos.setSalas(salasNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                subeventos.setStatus(statusNew);
            }
            Collection<InscricaoEquipeSub> attachedInscricaoEquipeSubCollectionNew = new ArrayList<InscricaoEquipeSub>();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach : inscricaoEquipeSubCollectionNew) {
                inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach = em.getReference(inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach.getClass(), inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach.getIdequipesub());
                attachedInscricaoEquipeSubCollectionNew.add(inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach);
            }
            inscricaoEquipeSubCollectionNew = attachedInscricaoEquipeSubCollectionNew;
            subeventos.setInscricaoEquipeSubCollection(inscricaoEquipeSubCollectionNew);
            Collection<InscricaoPartSubeve> attachedInscricaoPartSubeveCollectionNew = new ArrayList<InscricaoPartSubeve>();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach : inscricaoPartSubeveCollectionNew) {
                inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach = em.getReference(inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach.getClass(), inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach.getIdinscricao());
                attachedInscricaoPartSubeveCollectionNew.add(inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach);
            }
            inscricaoPartSubeveCollectionNew = attachedInscricaoPartSubeveCollectionNew;
            subeventos.setInscricaoPartSubeveCollection(inscricaoPartSubeveCollectionNew);
            Collection<Midias> attachedMidiasCollectionNew = new ArrayList<Midias>();
            for (Midias midiasCollectionNewMidiasToAttach : midiasCollectionNew) {
                midiasCollectionNewMidiasToAttach = em.getReference(midiasCollectionNewMidiasToAttach.getClass(), midiasCollectionNewMidiasToAttach.getIdmidia());
                attachedMidiasCollectionNew.add(midiasCollectionNewMidiasToAttach);
            }
            midiasCollectionNew = attachedMidiasCollectionNew;
            subeventos.setMidiasCollection(midiasCollectionNew);
            Collection<PresencasSub> attachedPresencasSubCollectionNew = new ArrayList<PresencasSub>();
            for (PresencasSub presencasSubCollectionNewPresencasSubToAttach : presencasSubCollectionNew) {
                presencasSubCollectionNewPresencasSubToAttach = em.getReference(presencasSubCollectionNewPresencasSubToAttach.getClass(), presencasSubCollectionNewPresencasSubToAttach.getPresencasSubPK());
                attachedPresencasSubCollectionNew.add(presencasSubCollectionNewPresencasSubToAttach);
            }
            presencasSubCollectionNew = attachedPresencasSubCollectionNew;
            subeventos.setPresencasSubCollection(presencasSubCollectionNew);
            Collection<PresencasEquipeSub> attachedPresencasEquipeSubCollectionNew = new ArrayList<PresencasEquipeSub>();
            for (PresencasEquipeSub presencasEquipeSubCollectionNewPresencasEquipeSubToAttach : presencasEquipeSubCollectionNew) {
                presencasEquipeSubCollectionNewPresencasEquipeSubToAttach = em.getReference(presencasEquipeSubCollectionNewPresencasEquipeSubToAttach.getClass(), presencasEquipeSubCollectionNewPresencasEquipeSubToAttach.getPresencasEquipeSubPK());
                attachedPresencasEquipeSubCollectionNew.add(presencasEquipeSubCollectionNewPresencasEquipeSubToAttach);
            }
            presencasEquipeSubCollectionNew = attachedPresencasEquipeSubCollectionNew;
            subeventos.setPresencasEquipeSubCollection(presencasEquipeSubCollectionNew);
            Collection<Submissao> attachedSubmissaoCollectionNew = new ArrayList<Submissao>();
            for (Submissao submissaoCollectionNewSubmissaoToAttach : submissaoCollectionNew) {
                submissaoCollectionNewSubmissaoToAttach = em.getReference(submissaoCollectionNewSubmissaoToAttach.getClass(), submissaoCollectionNewSubmissaoToAttach.getIdsubmissao());
                attachedSubmissaoCollectionNew.add(submissaoCollectionNewSubmissaoToAttach);
            }
            submissaoCollectionNew = attachedSubmissaoCollectionNew;
            subeventos.setSubmissaoCollection(submissaoCollectionNew);
            Collection<Apresentacao> attachedApresentacaoCollectionNew = new ArrayList<Apresentacao>();
            for (Apresentacao apresentacaoCollectionNewApresentacaoToAttach : apresentacaoCollectionNew) {
                apresentacaoCollectionNewApresentacaoToAttach = em.getReference(apresentacaoCollectionNewApresentacaoToAttach.getClass(), apresentacaoCollectionNewApresentacaoToAttach.getIdapresentacao());
                attachedApresentacaoCollectionNew.add(apresentacaoCollectionNewApresentacaoToAttach);
            }
            apresentacaoCollectionNew = attachedApresentacaoCollectionNew;
            subeventos.setApresentacaoCollection(apresentacaoCollectionNew);
            subeventos = em.merge(subeventos);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getSubeventosCollection().remove(subeventos);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getSubeventosCollection().add(subeventos);
                eventosNew = em.merge(eventosNew);
            }
            if (salasOld != null && !salasOld.equals(salasNew)) {
                salasOld.getSubeventosCollection().remove(subeventos);
                salasOld = em.merge(salasOld);
            }
            if (salasNew != null && !salasNew.equals(salasOld)) {
                salasNew.getSubeventosCollection().add(subeventos);
                salasNew = em.merge(salasNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getSubeventosCollection().remove(subeventos);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getSubeventosCollection().add(subeventos);
                statusNew = em.merge(statusNew);
            }
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionNewInscricaoEquipeSub : inscricaoEquipeSubCollectionNew) {
                if (!inscricaoEquipeSubCollectionOld.contains(inscricaoEquipeSubCollectionNewInscricaoEquipeSub)) {
                    Subeventos oldSubeventosOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub = inscricaoEquipeSubCollectionNewInscricaoEquipeSub.getSubeventos();
                    inscricaoEquipeSubCollectionNewInscricaoEquipeSub.setSubeventos(subeventos);
                    inscricaoEquipeSubCollectionNewInscricaoEquipeSub = em.merge(inscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                    if (oldSubeventosOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub != null && !oldSubeventosOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub.equals(subeventos)) {
                        oldSubeventosOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                        oldSubeventosOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub = em.merge(oldSubeventosOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                    }
                }
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionNewInscricaoPartSubeve : inscricaoPartSubeveCollectionNew) {
                if (!inscricaoPartSubeveCollectionOld.contains(inscricaoPartSubeveCollectionNewInscricaoPartSubeve)) {
                    Subeventos oldSubeventosOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve = inscricaoPartSubeveCollectionNewInscricaoPartSubeve.getSubeventos();
                    inscricaoPartSubeveCollectionNewInscricaoPartSubeve.setSubeventos(subeventos);
                    inscricaoPartSubeveCollectionNewInscricaoPartSubeve = em.merge(inscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                    if (oldSubeventosOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve != null && !oldSubeventosOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve.equals(subeventos)) {
                        oldSubeventosOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                        oldSubeventosOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve = em.merge(oldSubeventosOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                    }
                }
            }
            for (Midias midiasCollectionOldMidias : midiasCollectionOld) {
                if (!midiasCollectionNew.contains(midiasCollectionOldMidias)) {
                    midiasCollectionOldMidias.setSubeventos(null);
                    midiasCollectionOldMidias = em.merge(midiasCollectionOldMidias);
                }
            }
            for (Midias midiasCollectionNewMidias : midiasCollectionNew) {
                if (!midiasCollectionOld.contains(midiasCollectionNewMidias)) {
                    Subeventos oldSubeventosOfMidiasCollectionNewMidias = midiasCollectionNewMidias.getSubeventos();
                    midiasCollectionNewMidias.setSubeventos(subeventos);
                    midiasCollectionNewMidias = em.merge(midiasCollectionNewMidias);
                    if (oldSubeventosOfMidiasCollectionNewMidias != null && !oldSubeventosOfMidiasCollectionNewMidias.equals(subeventos)) {
                        oldSubeventosOfMidiasCollectionNewMidias.getMidiasCollection().remove(midiasCollectionNewMidias);
                        oldSubeventosOfMidiasCollectionNewMidias = em.merge(oldSubeventosOfMidiasCollectionNewMidias);
                    }
                }
            }
            for (PresencasSub presencasSubCollectionNewPresencasSub : presencasSubCollectionNew) {
                if (!presencasSubCollectionOld.contains(presencasSubCollectionNewPresencasSub)) {
                    Subeventos oldSubeventosOfPresencasSubCollectionNewPresencasSub = presencasSubCollectionNewPresencasSub.getSubeventos();
                    presencasSubCollectionNewPresencasSub.setSubeventos(subeventos);
                    presencasSubCollectionNewPresencasSub = em.merge(presencasSubCollectionNewPresencasSub);
                    if (oldSubeventosOfPresencasSubCollectionNewPresencasSub != null && !oldSubeventosOfPresencasSubCollectionNewPresencasSub.equals(subeventos)) {
                        oldSubeventosOfPresencasSubCollectionNewPresencasSub.getPresencasSubCollection().remove(presencasSubCollectionNewPresencasSub);
                        oldSubeventosOfPresencasSubCollectionNewPresencasSub = em.merge(oldSubeventosOfPresencasSubCollectionNewPresencasSub);
                    }
                }
            }
            for (PresencasEquipeSub presencasEquipeSubCollectionNewPresencasEquipeSub : presencasEquipeSubCollectionNew) {
                if (!presencasEquipeSubCollectionOld.contains(presencasEquipeSubCollectionNewPresencasEquipeSub)) {
                    Subeventos oldSubeventosOfPresencasEquipeSubCollectionNewPresencasEquipeSub = presencasEquipeSubCollectionNewPresencasEquipeSub.getSubeventos();
                    presencasEquipeSubCollectionNewPresencasEquipeSub.setSubeventos(subeventos);
                    presencasEquipeSubCollectionNewPresencasEquipeSub = em.merge(presencasEquipeSubCollectionNewPresencasEquipeSub);
                    if (oldSubeventosOfPresencasEquipeSubCollectionNewPresencasEquipeSub != null && !oldSubeventosOfPresencasEquipeSubCollectionNewPresencasEquipeSub.equals(subeventos)) {
                        oldSubeventosOfPresencasEquipeSubCollectionNewPresencasEquipeSub.getPresencasEquipeSubCollection().remove(presencasEquipeSubCollectionNewPresencasEquipeSub);
                        oldSubeventosOfPresencasEquipeSubCollectionNewPresencasEquipeSub = em.merge(oldSubeventosOfPresencasEquipeSubCollectionNewPresencasEquipeSub);
                    }
                }
            }
            for (Submissao submissaoCollectionNewSubmissao : submissaoCollectionNew) {
                if (!submissaoCollectionOld.contains(submissaoCollectionNewSubmissao)) {
                    Subeventos oldSubeventosOfSubmissaoCollectionNewSubmissao = submissaoCollectionNewSubmissao.getSubeventos();
                    submissaoCollectionNewSubmissao.setSubeventos(subeventos);
                    submissaoCollectionNewSubmissao = em.merge(submissaoCollectionNewSubmissao);
                    if (oldSubeventosOfSubmissaoCollectionNewSubmissao != null && !oldSubeventosOfSubmissaoCollectionNewSubmissao.equals(subeventos)) {
                        oldSubeventosOfSubmissaoCollectionNewSubmissao.getSubmissaoCollection().remove(submissaoCollectionNewSubmissao);
                        oldSubeventosOfSubmissaoCollectionNewSubmissao = em.merge(oldSubeventosOfSubmissaoCollectionNewSubmissao);
                    }
                }
            }
            for (Apresentacao apresentacaoCollectionNewApresentacao : apresentacaoCollectionNew) {
                if (!apresentacaoCollectionOld.contains(apresentacaoCollectionNewApresentacao)) {
                    Subeventos oldSubeventosOfApresentacaoCollectionNewApresentacao = apresentacaoCollectionNewApresentacao.getSubeventos();
                    apresentacaoCollectionNewApresentacao.setSubeventos(subeventos);
                    apresentacaoCollectionNewApresentacao = em.merge(apresentacaoCollectionNewApresentacao);
                    if (oldSubeventosOfApresentacaoCollectionNewApresentacao != null && !oldSubeventosOfApresentacaoCollectionNewApresentacao.equals(subeventos)) {
                        oldSubeventosOfApresentacaoCollectionNewApresentacao.getApresentacaoCollection().remove(apresentacaoCollectionNewApresentacao);
                        oldSubeventosOfApresentacaoCollectionNewApresentacao = em.merge(oldSubeventosOfApresentacaoCollectionNewApresentacao);
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
                Integer id = subeventos.getIdsubevento();
                if (findSubeventos(id) == null) {
                    throw new NonexistentEntityException("The subeventos with id " + id + " no longer exists.");
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
            Subeventos subeventos;
            try {
                subeventos = em.getReference(Subeventos.class, id);
                subeventos.getIdsubevento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subeventos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionOrphanCheck = subeventos.getInscricaoEquipeSubCollection();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionOrphanCheckInscricaoEquipeSub : inscricaoEquipeSubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subeventos (" + subeventos + ") cannot be destroyed since the InscricaoEquipeSub " + inscricaoEquipeSubCollectionOrphanCheckInscricaoEquipeSub + " in its inscricaoEquipeSubCollection field has a non-nullable subeventos field.");
            }
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionOrphanCheck = subeventos.getInscricaoPartSubeveCollection();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionOrphanCheckInscricaoPartSubeve : inscricaoPartSubeveCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subeventos (" + subeventos + ") cannot be destroyed since the InscricaoPartSubeve " + inscricaoPartSubeveCollectionOrphanCheckInscricaoPartSubeve + " in its inscricaoPartSubeveCollection field has a non-nullable subeventos field.");
            }
            Collection<PresencasSub> presencasSubCollectionOrphanCheck = subeventos.getPresencasSubCollection();
            for (PresencasSub presencasSubCollectionOrphanCheckPresencasSub : presencasSubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subeventos (" + subeventos + ") cannot be destroyed since the PresencasSub " + presencasSubCollectionOrphanCheckPresencasSub + " in its presencasSubCollection field has a non-nullable subeventos field.");
            }
            Collection<PresencasEquipeSub> presencasEquipeSubCollectionOrphanCheck = subeventos.getPresencasEquipeSubCollection();
            for (PresencasEquipeSub presencasEquipeSubCollectionOrphanCheckPresencasEquipeSub : presencasEquipeSubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subeventos (" + subeventos + ") cannot be destroyed since the PresencasEquipeSub " + presencasEquipeSubCollectionOrphanCheckPresencasEquipeSub + " in its presencasEquipeSubCollection field has a non-nullable subeventos field.");
            }
            Collection<Submissao> submissaoCollectionOrphanCheck = subeventos.getSubmissaoCollection();
            for (Submissao submissaoCollectionOrphanCheckSubmissao : submissaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subeventos (" + subeventos + ") cannot be destroyed since the Submissao " + submissaoCollectionOrphanCheckSubmissao + " in its submissaoCollection field has a non-nullable subeventos field.");
            }
            Collection<Apresentacao> apresentacaoCollectionOrphanCheck = subeventos.getApresentacaoCollection();
            for (Apresentacao apresentacaoCollectionOrphanCheckApresentacao : apresentacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subeventos (" + subeventos + ") cannot be destroyed since the Apresentacao " + apresentacaoCollectionOrphanCheckApresentacao + " in its apresentacaoCollection field has a non-nullable subeventos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Eventos eventos = subeventos.getEventos();
            if (eventos != null) {
                eventos.getSubeventosCollection().remove(subeventos);
                eventos = em.merge(eventos);
            }
            Salas salas = subeventos.getSalas();
            if (salas != null) {
                salas.getSubeventosCollection().remove(subeventos);
                salas = em.merge(salas);
            }
            Status status = subeventos.getStatus();
            if (status != null) {
                status.getSubeventosCollection().remove(subeventos);
                status = em.merge(status);
            }
            Collection<Midias> midiasCollection = subeventos.getMidiasCollection();
            for (Midias midiasCollectionMidias : midiasCollection) {
                midiasCollectionMidias.setSubeventos(null);
                midiasCollectionMidias = em.merge(midiasCollectionMidias);
            }
            em.remove(subeventos);
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

    public List<Subeventos> findSubeventosEntities() {
        return findSubeventosEntities(true, -1, -1);
    }

    public List<Subeventos> findSubeventosEntities(int maxResults, int firstResult) {
        return findSubeventosEntities(false, maxResults, firstResult);
    }

    private List<Subeventos> findSubeventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subeventos.class));
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

    public Subeventos findSubeventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subeventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubeventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subeventos> rt = cq.from(Subeventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
