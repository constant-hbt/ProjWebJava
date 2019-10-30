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
import model.InscricaoEquipeSub;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.InscricaoPartSubeve;
import model.Midias;
import model.Certificados;
import model.Eventos;
import model.Patrocinios;
import model.Usuarios;
import model.ParticipanteEquipe;
import model.Salas;
import model.Equipes;
import model.Subeventos;
import model.Editais;
import model.Patrocinioimagens;
import model.Crachas;
import model.Status;

/**
 *
 * @author henrique
 */
public class StatusJpaController implements Serializable {

    public StatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Status status) throws RollbackFailureException, Exception {
        if (status.getInscricaoEquipeSubCollection() == null) {
            status.setInscricaoEquipeSubCollection(new ArrayList<InscricaoEquipeSub>());
        }
        if (status.getInscricaoPartSubeveCollection() == null) {
            status.setInscricaoPartSubeveCollection(new ArrayList<InscricaoPartSubeve>());
        }
        if (status.getMidiasCollection() == null) {
            status.setMidiasCollection(new ArrayList<Midias>());
        }
        if (status.getCertificadosCollection() == null) {
            status.setCertificadosCollection(new ArrayList<Certificados>());
        }
        if (status.getEventosCollection() == null) {
            status.setEventosCollection(new ArrayList<Eventos>());
        }
        if (status.getPatrociniosCollection() == null) {
            status.setPatrociniosCollection(new ArrayList<Patrocinios>());
        }
        if (status.getUsuariosCollection() == null) {
            status.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        if (status.getParticipanteEquipeCollection() == null) {
            status.setParticipanteEquipeCollection(new ArrayList<ParticipanteEquipe>());
        }
        if (status.getSalasCollection() == null) {
            status.setSalasCollection(new ArrayList<Salas>());
        }
        if (status.getEquipesCollection() == null) {
            status.setEquipesCollection(new ArrayList<Equipes>());
        }
        if (status.getSubeventosCollection() == null) {
            status.setSubeventosCollection(new ArrayList<Subeventos>());
        }
        if (status.getEditaisCollection() == null) {
            status.setEditaisCollection(new ArrayList<Editais>());
        }
        if (status.getPatrocinioimagensCollection() == null) {
            status.setPatrocinioimagensCollection(new ArrayList<Patrocinioimagens>());
        }
        if (status.getCrachasCollection() == null) {
            status.setCrachasCollection(new ArrayList<Crachas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<InscricaoEquipeSub> attachedInscricaoEquipeSubCollection = new ArrayList<InscricaoEquipeSub>();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach : status.getInscricaoEquipeSubCollection()) {
                inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach = em.getReference(inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach.getClass(), inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach.getIdequipesub());
                attachedInscricaoEquipeSubCollection.add(inscricaoEquipeSubCollectionInscricaoEquipeSubToAttach);
            }
            status.setInscricaoEquipeSubCollection(attachedInscricaoEquipeSubCollection);
            Collection<InscricaoPartSubeve> attachedInscricaoPartSubeveCollection = new ArrayList<InscricaoPartSubeve>();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach : status.getInscricaoPartSubeveCollection()) {
                inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach = em.getReference(inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach.getClass(), inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach.getIdinscricao());
                attachedInscricaoPartSubeveCollection.add(inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach);
            }
            status.setInscricaoPartSubeveCollection(attachedInscricaoPartSubeveCollection);
            Collection<Midias> attachedMidiasCollection = new ArrayList<Midias>();
            for (Midias midiasCollectionMidiasToAttach : status.getMidiasCollection()) {
                midiasCollectionMidiasToAttach = em.getReference(midiasCollectionMidiasToAttach.getClass(), midiasCollectionMidiasToAttach.getIdmidia());
                attachedMidiasCollection.add(midiasCollectionMidiasToAttach);
            }
            status.setMidiasCollection(attachedMidiasCollection);
            Collection<Certificados> attachedCertificadosCollection = new ArrayList<Certificados>();
            for (Certificados certificadosCollectionCertificadosToAttach : status.getCertificadosCollection()) {
                certificadosCollectionCertificadosToAttach = em.getReference(certificadosCollectionCertificadosToAttach.getClass(), certificadosCollectionCertificadosToAttach.getIdcertificado());
                attachedCertificadosCollection.add(certificadosCollectionCertificadosToAttach);
            }
            status.setCertificadosCollection(attachedCertificadosCollection);
            Collection<Eventos> attachedEventosCollection = new ArrayList<Eventos>();
            for (Eventos eventosCollectionEventosToAttach : status.getEventosCollection()) {
                eventosCollectionEventosToAttach = em.getReference(eventosCollectionEventosToAttach.getClass(), eventosCollectionEventosToAttach.getIdevento());
                attachedEventosCollection.add(eventosCollectionEventosToAttach);
            }
            status.setEventosCollection(attachedEventosCollection);
            Collection<Patrocinios> attachedPatrociniosCollection = new ArrayList<Patrocinios>();
            for (Patrocinios patrociniosCollectionPatrociniosToAttach : status.getPatrociniosCollection()) {
                patrociniosCollectionPatrociniosToAttach = em.getReference(patrociniosCollectionPatrociniosToAttach.getClass(), patrociniosCollectionPatrociniosToAttach.getIdpatrocinio());
                attachedPatrociniosCollection.add(patrociniosCollectionPatrociniosToAttach);
            }
            status.setPatrociniosCollection(attachedPatrociniosCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : status.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getIdusuario());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            status.setUsuariosCollection(attachedUsuariosCollection);
            Collection<ParticipanteEquipe> attachedParticipanteEquipeCollection = new ArrayList<ParticipanteEquipe>();
            for (ParticipanteEquipe participanteEquipeCollectionParticipanteEquipeToAttach : status.getParticipanteEquipeCollection()) {
                participanteEquipeCollectionParticipanteEquipeToAttach = em.getReference(participanteEquipeCollectionParticipanteEquipeToAttach.getClass(), participanteEquipeCollectionParticipanteEquipeToAttach.getIdpartequipe());
                attachedParticipanteEquipeCollection.add(participanteEquipeCollectionParticipanteEquipeToAttach);
            }
            status.setParticipanteEquipeCollection(attachedParticipanteEquipeCollection);
            Collection<Salas> attachedSalasCollection = new ArrayList<Salas>();
            for (Salas salasCollectionSalasToAttach : status.getSalasCollection()) {
                salasCollectionSalasToAttach = em.getReference(salasCollectionSalasToAttach.getClass(), salasCollectionSalasToAttach.getIdsala());
                attachedSalasCollection.add(salasCollectionSalasToAttach);
            }
            status.setSalasCollection(attachedSalasCollection);
            Collection<Equipes> attachedEquipesCollection = new ArrayList<Equipes>();
            for (Equipes equipesCollectionEquipesToAttach : status.getEquipesCollection()) {
                equipesCollectionEquipesToAttach = em.getReference(equipesCollectionEquipesToAttach.getClass(), equipesCollectionEquipesToAttach.getIdequipe());
                attachedEquipesCollection.add(equipesCollectionEquipesToAttach);
            }
            status.setEquipesCollection(attachedEquipesCollection);
            Collection<Subeventos> attachedSubeventosCollection = new ArrayList<Subeventos>();
            for (Subeventos subeventosCollectionSubeventosToAttach : status.getSubeventosCollection()) {
                subeventosCollectionSubeventosToAttach = em.getReference(subeventosCollectionSubeventosToAttach.getClass(), subeventosCollectionSubeventosToAttach.getIdsubevento());
                attachedSubeventosCollection.add(subeventosCollectionSubeventosToAttach);
            }
            status.setSubeventosCollection(attachedSubeventosCollection);
            Collection<Editais> attachedEditaisCollection = new ArrayList<Editais>();
            for (Editais editaisCollectionEditaisToAttach : status.getEditaisCollection()) {
                editaisCollectionEditaisToAttach = em.getReference(editaisCollectionEditaisToAttach.getClass(), editaisCollectionEditaisToAttach.getIdedital());
                attachedEditaisCollection.add(editaisCollectionEditaisToAttach);
            }
            status.setEditaisCollection(attachedEditaisCollection);
            Collection<Patrocinioimagens> attachedPatrocinioimagensCollection = new ArrayList<Patrocinioimagens>();
            for (Patrocinioimagens patrocinioimagensCollectionPatrocinioimagensToAttach : status.getPatrocinioimagensCollection()) {
                patrocinioimagensCollectionPatrocinioimagensToAttach = em.getReference(patrocinioimagensCollectionPatrocinioimagensToAttach.getClass(), patrocinioimagensCollectionPatrocinioimagensToAttach.getIdpatrocinioimagens());
                attachedPatrocinioimagensCollection.add(patrocinioimagensCollectionPatrocinioimagensToAttach);
            }
            status.setPatrocinioimagensCollection(attachedPatrocinioimagensCollection);
            Collection<Crachas> attachedCrachasCollection = new ArrayList<Crachas>();
            for (Crachas crachasCollectionCrachasToAttach : status.getCrachasCollection()) {
                crachasCollectionCrachasToAttach = em.getReference(crachasCollectionCrachasToAttach.getClass(), crachasCollectionCrachasToAttach.getIdcracha());
                attachedCrachasCollection.add(crachasCollectionCrachasToAttach);
            }
            status.setCrachasCollection(attachedCrachasCollection);
            em.persist(status);
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionInscricaoEquipeSub : status.getInscricaoEquipeSubCollection()) {
                Status oldStatusOfInscricaoEquipeSubCollectionInscricaoEquipeSub = inscricaoEquipeSubCollectionInscricaoEquipeSub.getStatus();
                inscricaoEquipeSubCollectionInscricaoEquipeSub.setStatus(status);
                inscricaoEquipeSubCollectionInscricaoEquipeSub = em.merge(inscricaoEquipeSubCollectionInscricaoEquipeSub);
                if (oldStatusOfInscricaoEquipeSubCollectionInscricaoEquipeSub != null) {
                    oldStatusOfInscricaoEquipeSubCollectionInscricaoEquipeSub.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSubCollectionInscricaoEquipeSub);
                    oldStatusOfInscricaoEquipeSubCollectionInscricaoEquipeSub = em.merge(oldStatusOfInscricaoEquipeSubCollectionInscricaoEquipeSub);
                }
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionInscricaoPartSubeve : status.getInscricaoPartSubeveCollection()) {
                Status oldStatusOfInscricaoPartSubeveCollectionInscricaoPartSubeve = inscricaoPartSubeveCollectionInscricaoPartSubeve.getStatus();
                inscricaoPartSubeveCollectionInscricaoPartSubeve.setStatus(status);
                inscricaoPartSubeveCollectionInscricaoPartSubeve = em.merge(inscricaoPartSubeveCollectionInscricaoPartSubeve);
                if (oldStatusOfInscricaoPartSubeveCollectionInscricaoPartSubeve != null) {
                    oldStatusOfInscricaoPartSubeveCollectionInscricaoPartSubeve.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeveCollectionInscricaoPartSubeve);
                    oldStatusOfInscricaoPartSubeveCollectionInscricaoPartSubeve = em.merge(oldStatusOfInscricaoPartSubeveCollectionInscricaoPartSubeve);
                }
            }
            for (Midias midiasCollectionMidias : status.getMidiasCollection()) {
                Status oldStatusOfMidiasCollectionMidias = midiasCollectionMidias.getStatus();
                midiasCollectionMidias.setStatus(status);
                midiasCollectionMidias = em.merge(midiasCollectionMidias);
                if (oldStatusOfMidiasCollectionMidias != null) {
                    oldStatusOfMidiasCollectionMidias.getMidiasCollection().remove(midiasCollectionMidias);
                    oldStatusOfMidiasCollectionMidias = em.merge(oldStatusOfMidiasCollectionMidias);
                }
            }
            for (Certificados certificadosCollectionCertificados : status.getCertificadosCollection()) {
                Status oldStatusOfCertificadosCollectionCertificados = certificadosCollectionCertificados.getStatus();
                certificadosCollectionCertificados.setStatus(status);
                certificadosCollectionCertificados = em.merge(certificadosCollectionCertificados);
                if (oldStatusOfCertificadosCollectionCertificados != null) {
                    oldStatusOfCertificadosCollectionCertificados.getCertificadosCollection().remove(certificadosCollectionCertificados);
                    oldStatusOfCertificadosCollectionCertificados = em.merge(oldStatusOfCertificadosCollectionCertificados);
                }
            }
            for (Eventos eventosCollectionEventos : status.getEventosCollection()) {
                Status oldStatusOfEventosCollectionEventos = eventosCollectionEventos.getStatus();
                eventosCollectionEventos.setStatus(status);
                eventosCollectionEventos = em.merge(eventosCollectionEventos);
                if (oldStatusOfEventosCollectionEventos != null) {
                    oldStatusOfEventosCollectionEventos.getEventosCollection().remove(eventosCollectionEventos);
                    oldStatusOfEventosCollectionEventos = em.merge(oldStatusOfEventosCollectionEventos);
                }
            }
            for (Patrocinios patrociniosCollectionPatrocinios : status.getPatrociniosCollection()) {
                Status oldStatusOfPatrociniosCollectionPatrocinios = patrociniosCollectionPatrocinios.getStatus();
                patrociniosCollectionPatrocinios.setStatus(status);
                patrociniosCollectionPatrocinios = em.merge(patrociniosCollectionPatrocinios);
                if (oldStatusOfPatrociniosCollectionPatrocinios != null) {
                    oldStatusOfPatrociniosCollectionPatrocinios.getPatrociniosCollection().remove(patrociniosCollectionPatrocinios);
                    oldStatusOfPatrociniosCollectionPatrocinios = em.merge(oldStatusOfPatrociniosCollectionPatrocinios);
                }
            }
            for (Usuarios usuariosCollectionUsuarios : status.getUsuariosCollection()) {
                Status oldStatusOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getStatus();
                usuariosCollectionUsuarios.setStatus(status);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldStatusOfUsuariosCollectionUsuarios != null) {
                    oldStatusOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldStatusOfUsuariosCollectionUsuarios = em.merge(oldStatusOfUsuariosCollectionUsuarios);
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionParticipanteEquipe : status.getParticipanteEquipeCollection()) {
                Status oldStatusOfParticipanteEquipeCollectionParticipanteEquipe = participanteEquipeCollectionParticipanteEquipe.getStatus();
                participanteEquipeCollectionParticipanteEquipe.setStatus(status);
                participanteEquipeCollectionParticipanteEquipe = em.merge(participanteEquipeCollectionParticipanteEquipe);
                if (oldStatusOfParticipanteEquipeCollectionParticipanteEquipe != null) {
                    oldStatusOfParticipanteEquipeCollectionParticipanteEquipe.getParticipanteEquipeCollection().remove(participanteEquipeCollectionParticipanteEquipe);
                    oldStatusOfParticipanteEquipeCollectionParticipanteEquipe = em.merge(oldStatusOfParticipanteEquipeCollectionParticipanteEquipe);
                }
            }
            for (Salas salasCollectionSalas : status.getSalasCollection()) {
                Status oldStatusOfSalasCollectionSalas = salasCollectionSalas.getStatus();
                salasCollectionSalas.setStatus(status);
                salasCollectionSalas = em.merge(salasCollectionSalas);
                if (oldStatusOfSalasCollectionSalas != null) {
                    oldStatusOfSalasCollectionSalas.getSalasCollection().remove(salasCollectionSalas);
                    oldStatusOfSalasCollectionSalas = em.merge(oldStatusOfSalasCollectionSalas);
                }
            }
            for (Equipes equipesCollectionEquipes : status.getEquipesCollection()) {
                Status oldStatusOfEquipesCollectionEquipes = equipesCollectionEquipes.getStatus();
                equipesCollectionEquipes.setStatus(status);
                equipesCollectionEquipes = em.merge(equipesCollectionEquipes);
                if (oldStatusOfEquipesCollectionEquipes != null) {
                    oldStatusOfEquipesCollectionEquipes.getEquipesCollection().remove(equipesCollectionEquipes);
                    oldStatusOfEquipesCollectionEquipes = em.merge(oldStatusOfEquipesCollectionEquipes);
                }
            }
            for (Subeventos subeventosCollectionSubeventos : status.getSubeventosCollection()) {
                Status oldStatusOfSubeventosCollectionSubeventos = subeventosCollectionSubeventos.getStatus();
                subeventosCollectionSubeventos.setStatus(status);
                subeventosCollectionSubeventos = em.merge(subeventosCollectionSubeventos);
                if (oldStatusOfSubeventosCollectionSubeventos != null) {
                    oldStatusOfSubeventosCollectionSubeventos.getSubeventosCollection().remove(subeventosCollectionSubeventos);
                    oldStatusOfSubeventosCollectionSubeventos = em.merge(oldStatusOfSubeventosCollectionSubeventos);
                }
            }
            for (Editais editaisCollectionEditais : status.getEditaisCollection()) {
                Status oldStatusOfEditaisCollectionEditais = editaisCollectionEditais.getStatus();
                editaisCollectionEditais.setStatus(status);
                editaisCollectionEditais = em.merge(editaisCollectionEditais);
                if (oldStatusOfEditaisCollectionEditais != null) {
                    oldStatusOfEditaisCollectionEditais.getEditaisCollection().remove(editaisCollectionEditais);
                    oldStatusOfEditaisCollectionEditais = em.merge(oldStatusOfEditaisCollectionEditais);
                }
            }
            for (Patrocinioimagens patrocinioimagensCollectionPatrocinioimagens : status.getPatrocinioimagensCollection()) {
                Status oldStatusOfPatrocinioimagensCollectionPatrocinioimagens = patrocinioimagensCollectionPatrocinioimagens.getStatus();
                patrocinioimagensCollectionPatrocinioimagens.setStatus(status);
                patrocinioimagensCollectionPatrocinioimagens = em.merge(patrocinioimagensCollectionPatrocinioimagens);
                if (oldStatusOfPatrocinioimagensCollectionPatrocinioimagens != null) {
                    oldStatusOfPatrocinioimagensCollectionPatrocinioimagens.getPatrocinioimagensCollection().remove(patrocinioimagensCollectionPatrocinioimagens);
                    oldStatusOfPatrocinioimagensCollectionPatrocinioimagens = em.merge(oldStatusOfPatrocinioimagensCollectionPatrocinioimagens);
                }
            }
            for (Crachas crachasCollectionCrachas : status.getCrachasCollection()) {
                Status oldStatusOfCrachasCollectionCrachas = crachasCollectionCrachas.getStatus();
                crachasCollectionCrachas.setStatus(status);
                crachasCollectionCrachas = em.merge(crachasCollectionCrachas);
                if (oldStatusOfCrachasCollectionCrachas != null) {
                    oldStatusOfCrachasCollectionCrachas.getCrachasCollection().remove(crachasCollectionCrachas);
                    oldStatusOfCrachasCollectionCrachas = em.merge(oldStatusOfCrachasCollectionCrachas);
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

    public void edit(Status status) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status persistentStatus = em.find(Status.class, status.getIdstatus());
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionOld = persistentStatus.getInscricaoEquipeSubCollection();
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionNew = status.getInscricaoEquipeSubCollection();
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionOld = persistentStatus.getInscricaoPartSubeveCollection();
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionNew = status.getInscricaoPartSubeveCollection();
            Collection<Midias> midiasCollectionOld = persistentStatus.getMidiasCollection();
            Collection<Midias> midiasCollectionNew = status.getMidiasCollection();
            Collection<Certificados> certificadosCollectionOld = persistentStatus.getCertificadosCollection();
            Collection<Certificados> certificadosCollectionNew = status.getCertificadosCollection();
            Collection<Eventos> eventosCollectionOld = persistentStatus.getEventosCollection();
            Collection<Eventos> eventosCollectionNew = status.getEventosCollection();
            Collection<Patrocinios> patrociniosCollectionOld = persistentStatus.getPatrociniosCollection();
            Collection<Patrocinios> patrociniosCollectionNew = status.getPatrociniosCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentStatus.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = status.getUsuariosCollection();
            Collection<ParticipanteEquipe> participanteEquipeCollectionOld = persistentStatus.getParticipanteEquipeCollection();
            Collection<ParticipanteEquipe> participanteEquipeCollectionNew = status.getParticipanteEquipeCollection();
            Collection<Salas> salasCollectionOld = persistentStatus.getSalasCollection();
            Collection<Salas> salasCollectionNew = status.getSalasCollection();
            Collection<Equipes> equipesCollectionOld = persistentStatus.getEquipesCollection();
            Collection<Equipes> equipesCollectionNew = status.getEquipesCollection();
            Collection<Subeventos> subeventosCollectionOld = persistentStatus.getSubeventosCollection();
            Collection<Subeventos> subeventosCollectionNew = status.getSubeventosCollection();
            Collection<Editais> editaisCollectionOld = persistentStatus.getEditaisCollection();
            Collection<Editais> editaisCollectionNew = status.getEditaisCollection();
            Collection<Patrocinioimagens> patrocinioimagensCollectionOld = persistentStatus.getPatrocinioimagensCollection();
            Collection<Patrocinioimagens> patrocinioimagensCollectionNew = status.getPatrocinioimagensCollection();
            Collection<Crachas> crachasCollectionOld = persistentStatus.getCrachasCollection();
            Collection<Crachas> crachasCollectionNew = status.getCrachasCollection();
            List<String> illegalOrphanMessages = null;
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionOldInscricaoEquipeSub : inscricaoEquipeSubCollectionOld) {
                if (!inscricaoEquipeSubCollectionNew.contains(inscricaoEquipeSubCollectionOldInscricaoEquipeSub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoEquipeSub " + inscricaoEquipeSubCollectionOldInscricaoEquipeSub + " since its status field is not nullable.");
                }
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionOldInscricaoPartSubeve : inscricaoPartSubeveCollectionOld) {
                if (!inscricaoPartSubeveCollectionNew.contains(inscricaoPartSubeveCollectionOldInscricaoPartSubeve)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoPartSubeve " + inscricaoPartSubeveCollectionOldInscricaoPartSubeve + " since its status field is not nullable.");
                }
            }
            for (Midias midiasCollectionOldMidias : midiasCollectionOld) {
                if (!midiasCollectionNew.contains(midiasCollectionOldMidias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Midias " + midiasCollectionOldMidias + " since its status field is not nullable.");
                }
            }
            for (Certificados certificadosCollectionOldCertificados : certificadosCollectionOld) {
                if (!certificadosCollectionNew.contains(certificadosCollectionOldCertificados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Certificados " + certificadosCollectionOldCertificados + " since its status field is not nullable.");
                }
            }
            for (Eventos eventosCollectionOldEventos : eventosCollectionOld) {
                if (!eventosCollectionNew.contains(eventosCollectionOldEventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Eventos " + eventosCollectionOldEventos + " since its status field is not nullable.");
                }
            }
            for (Patrocinios patrociniosCollectionOldPatrocinios : patrociniosCollectionOld) {
                if (!patrociniosCollectionNew.contains(patrociniosCollectionOldPatrocinios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patrocinios " + patrociniosCollectionOldPatrocinios + " since its status field is not nullable.");
                }
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its status field is not nullable.");
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionOldParticipanteEquipe : participanteEquipeCollectionOld) {
                if (!participanteEquipeCollectionNew.contains(participanteEquipeCollectionOldParticipanteEquipe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipanteEquipe " + participanteEquipeCollectionOldParticipanteEquipe + " since its status field is not nullable.");
                }
            }
            for (Salas salasCollectionOldSalas : salasCollectionOld) {
                if (!salasCollectionNew.contains(salasCollectionOldSalas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Salas " + salasCollectionOldSalas + " since its status field is not nullable.");
                }
            }
            for (Equipes equipesCollectionOldEquipes : equipesCollectionOld) {
                if (!equipesCollectionNew.contains(equipesCollectionOldEquipes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipes " + equipesCollectionOldEquipes + " since its status field is not nullable.");
                }
            }
            for (Subeventos subeventosCollectionOldSubeventos : subeventosCollectionOld) {
                if (!subeventosCollectionNew.contains(subeventosCollectionOldSubeventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subeventos " + subeventosCollectionOldSubeventos + " since its status field is not nullable.");
                }
            }
            for (Editais editaisCollectionOldEditais : editaisCollectionOld) {
                if (!editaisCollectionNew.contains(editaisCollectionOldEditais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Editais " + editaisCollectionOldEditais + " since its status field is not nullable.");
                }
            }
            for (Patrocinioimagens patrocinioimagensCollectionOldPatrocinioimagens : patrocinioimagensCollectionOld) {
                if (!patrocinioimagensCollectionNew.contains(patrocinioimagensCollectionOldPatrocinioimagens)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patrocinioimagens " + patrocinioimagensCollectionOldPatrocinioimagens + " since its status field is not nullable.");
                }
            }
            for (Crachas crachasCollectionOldCrachas : crachasCollectionOld) {
                if (!crachasCollectionNew.contains(crachasCollectionOldCrachas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Crachas " + crachasCollectionOldCrachas + " since its status field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<InscricaoEquipeSub> attachedInscricaoEquipeSubCollectionNew = new ArrayList<InscricaoEquipeSub>();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach : inscricaoEquipeSubCollectionNew) {
                inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach = em.getReference(inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach.getClass(), inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach.getIdequipesub());
                attachedInscricaoEquipeSubCollectionNew.add(inscricaoEquipeSubCollectionNewInscricaoEquipeSubToAttach);
            }
            inscricaoEquipeSubCollectionNew = attachedInscricaoEquipeSubCollectionNew;
            status.setInscricaoEquipeSubCollection(inscricaoEquipeSubCollectionNew);
            Collection<InscricaoPartSubeve> attachedInscricaoPartSubeveCollectionNew = new ArrayList<InscricaoPartSubeve>();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach : inscricaoPartSubeveCollectionNew) {
                inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach = em.getReference(inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach.getClass(), inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach.getIdinscricao());
                attachedInscricaoPartSubeveCollectionNew.add(inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach);
            }
            inscricaoPartSubeveCollectionNew = attachedInscricaoPartSubeveCollectionNew;
            status.setInscricaoPartSubeveCollection(inscricaoPartSubeveCollectionNew);
            Collection<Midias> attachedMidiasCollectionNew = new ArrayList<Midias>();
            for (Midias midiasCollectionNewMidiasToAttach : midiasCollectionNew) {
                midiasCollectionNewMidiasToAttach = em.getReference(midiasCollectionNewMidiasToAttach.getClass(), midiasCollectionNewMidiasToAttach.getIdmidia());
                attachedMidiasCollectionNew.add(midiasCollectionNewMidiasToAttach);
            }
            midiasCollectionNew = attachedMidiasCollectionNew;
            status.setMidiasCollection(midiasCollectionNew);
            Collection<Certificados> attachedCertificadosCollectionNew = new ArrayList<Certificados>();
            for (Certificados certificadosCollectionNewCertificadosToAttach : certificadosCollectionNew) {
                certificadosCollectionNewCertificadosToAttach = em.getReference(certificadosCollectionNewCertificadosToAttach.getClass(), certificadosCollectionNewCertificadosToAttach.getIdcertificado());
                attachedCertificadosCollectionNew.add(certificadosCollectionNewCertificadosToAttach);
            }
            certificadosCollectionNew = attachedCertificadosCollectionNew;
            status.setCertificadosCollection(certificadosCollectionNew);
            Collection<Eventos> attachedEventosCollectionNew = new ArrayList<Eventos>();
            for (Eventos eventosCollectionNewEventosToAttach : eventosCollectionNew) {
                eventosCollectionNewEventosToAttach = em.getReference(eventosCollectionNewEventosToAttach.getClass(), eventosCollectionNewEventosToAttach.getIdevento());
                attachedEventosCollectionNew.add(eventosCollectionNewEventosToAttach);
            }
            eventosCollectionNew = attachedEventosCollectionNew;
            status.setEventosCollection(eventosCollectionNew);
            Collection<Patrocinios> attachedPatrociniosCollectionNew = new ArrayList<Patrocinios>();
            for (Patrocinios patrociniosCollectionNewPatrociniosToAttach : patrociniosCollectionNew) {
                patrociniosCollectionNewPatrociniosToAttach = em.getReference(patrociniosCollectionNewPatrociniosToAttach.getClass(), patrociniosCollectionNewPatrociniosToAttach.getIdpatrocinio());
                attachedPatrociniosCollectionNew.add(patrociniosCollectionNewPatrociniosToAttach);
            }
            patrociniosCollectionNew = attachedPatrociniosCollectionNew;
            status.setPatrociniosCollection(patrociniosCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getIdusuario());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            status.setUsuariosCollection(usuariosCollectionNew);
            Collection<ParticipanteEquipe> attachedParticipanteEquipeCollectionNew = new ArrayList<ParticipanteEquipe>();
            for (ParticipanteEquipe participanteEquipeCollectionNewParticipanteEquipeToAttach : participanteEquipeCollectionNew) {
                participanteEquipeCollectionNewParticipanteEquipeToAttach = em.getReference(participanteEquipeCollectionNewParticipanteEquipeToAttach.getClass(), participanteEquipeCollectionNewParticipanteEquipeToAttach.getIdpartequipe());
                attachedParticipanteEquipeCollectionNew.add(participanteEquipeCollectionNewParticipanteEquipeToAttach);
            }
            participanteEquipeCollectionNew = attachedParticipanteEquipeCollectionNew;
            status.setParticipanteEquipeCollection(participanteEquipeCollectionNew);
            Collection<Salas> attachedSalasCollectionNew = new ArrayList<Salas>();
            for (Salas salasCollectionNewSalasToAttach : salasCollectionNew) {
                salasCollectionNewSalasToAttach = em.getReference(salasCollectionNewSalasToAttach.getClass(), salasCollectionNewSalasToAttach.getIdsala());
                attachedSalasCollectionNew.add(salasCollectionNewSalasToAttach);
            }
            salasCollectionNew = attachedSalasCollectionNew;
            status.setSalasCollection(salasCollectionNew);
            Collection<Equipes> attachedEquipesCollectionNew = new ArrayList<Equipes>();
            for (Equipes equipesCollectionNewEquipesToAttach : equipesCollectionNew) {
                equipesCollectionNewEquipesToAttach = em.getReference(equipesCollectionNewEquipesToAttach.getClass(), equipesCollectionNewEquipesToAttach.getIdequipe());
                attachedEquipesCollectionNew.add(equipesCollectionNewEquipesToAttach);
            }
            equipesCollectionNew = attachedEquipesCollectionNew;
            status.setEquipesCollection(equipesCollectionNew);
            Collection<Subeventos> attachedSubeventosCollectionNew = new ArrayList<Subeventos>();
            for (Subeventos subeventosCollectionNewSubeventosToAttach : subeventosCollectionNew) {
                subeventosCollectionNewSubeventosToAttach = em.getReference(subeventosCollectionNewSubeventosToAttach.getClass(), subeventosCollectionNewSubeventosToAttach.getIdsubevento());
                attachedSubeventosCollectionNew.add(subeventosCollectionNewSubeventosToAttach);
            }
            subeventosCollectionNew = attachedSubeventosCollectionNew;
            status.setSubeventosCollection(subeventosCollectionNew);
            Collection<Editais> attachedEditaisCollectionNew = new ArrayList<Editais>();
            for (Editais editaisCollectionNewEditaisToAttach : editaisCollectionNew) {
                editaisCollectionNewEditaisToAttach = em.getReference(editaisCollectionNewEditaisToAttach.getClass(), editaisCollectionNewEditaisToAttach.getIdedital());
                attachedEditaisCollectionNew.add(editaisCollectionNewEditaisToAttach);
            }
            editaisCollectionNew = attachedEditaisCollectionNew;
            status.setEditaisCollection(editaisCollectionNew);
            Collection<Patrocinioimagens> attachedPatrocinioimagensCollectionNew = new ArrayList<Patrocinioimagens>();
            for (Patrocinioimagens patrocinioimagensCollectionNewPatrocinioimagensToAttach : patrocinioimagensCollectionNew) {
                patrocinioimagensCollectionNewPatrocinioimagensToAttach = em.getReference(patrocinioimagensCollectionNewPatrocinioimagensToAttach.getClass(), patrocinioimagensCollectionNewPatrocinioimagensToAttach.getIdpatrocinioimagens());
                attachedPatrocinioimagensCollectionNew.add(patrocinioimagensCollectionNewPatrocinioimagensToAttach);
            }
            patrocinioimagensCollectionNew = attachedPatrocinioimagensCollectionNew;
            status.setPatrocinioimagensCollection(patrocinioimagensCollectionNew);
            Collection<Crachas> attachedCrachasCollectionNew = new ArrayList<Crachas>();
            for (Crachas crachasCollectionNewCrachasToAttach : crachasCollectionNew) {
                crachasCollectionNewCrachasToAttach = em.getReference(crachasCollectionNewCrachasToAttach.getClass(), crachasCollectionNewCrachasToAttach.getIdcracha());
                attachedCrachasCollectionNew.add(crachasCollectionNewCrachasToAttach);
            }
            crachasCollectionNew = attachedCrachasCollectionNew;
            status.setCrachasCollection(crachasCollectionNew);
            status = em.merge(status);
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionNewInscricaoEquipeSub : inscricaoEquipeSubCollectionNew) {
                if (!inscricaoEquipeSubCollectionOld.contains(inscricaoEquipeSubCollectionNewInscricaoEquipeSub)) {
                    Status oldStatusOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub = inscricaoEquipeSubCollectionNewInscricaoEquipeSub.getStatus();
                    inscricaoEquipeSubCollectionNewInscricaoEquipeSub.setStatus(status);
                    inscricaoEquipeSubCollectionNewInscricaoEquipeSub = em.merge(inscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                    if (oldStatusOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub != null && !oldStatusOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub.equals(status)) {
                        oldStatusOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub.getInscricaoEquipeSubCollection().remove(inscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                        oldStatusOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub = em.merge(oldStatusOfInscricaoEquipeSubCollectionNewInscricaoEquipeSub);
                    }
                }
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionNewInscricaoPartSubeve : inscricaoPartSubeveCollectionNew) {
                if (!inscricaoPartSubeveCollectionOld.contains(inscricaoPartSubeveCollectionNewInscricaoPartSubeve)) {
                    Status oldStatusOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve = inscricaoPartSubeveCollectionNewInscricaoPartSubeve.getStatus();
                    inscricaoPartSubeveCollectionNewInscricaoPartSubeve.setStatus(status);
                    inscricaoPartSubeveCollectionNewInscricaoPartSubeve = em.merge(inscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                    if (oldStatusOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve != null && !oldStatusOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve.equals(status)) {
                        oldStatusOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                        oldStatusOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve = em.merge(oldStatusOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                    }
                }
            }
            for (Midias midiasCollectionNewMidias : midiasCollectionNew) {
                if (!midiasCollectionOld.contains(midiasCollectionNewMidias)) {
                    Status oldStatusOfMidiasCollectionNewMidias = midiasCollectionNewMidias.getStatus();
                    midiasCollectionNewMidias.setStatus(status);
                    midiasCollectionNewMidias = em.merge(midiasCollectionNewMidias);
                    if (oldStatusOfMidiasCollectionNewMidias != null && !oldStatusOfMidiasCollectionNewMidias.equals(status)) {
                        oldStatusOfMidiasCollectionNewMidias.getMidiasCollection().remove(midiasCollectionNewMidias);
                        oldStatusOfMidiasCollectionNewMidias = em.merge(oldStatusOfMidiasCollectionNewMidias);
                    }
                }
            }
            for (Certificados certificadosCollectionNewCertificados : certificadosCollectionNew) {
                if (!certificadosCollectionOld.contains(certificadosCollectionNewCertificados)) {
                    Status oldStatusOfCertificadosCollectionNewCertificados = certificadosCollectionNewCertificados.getStatus();
                    certificadosCollectionNewCertificados.setStatus(status);
                    certificadosCollectionNewCertificados = em.merge(certificadosCollectionNewCertificados);
                    if (oldStatusOfCertificadosCollectionNewCertificados != null && !oldStatusOfCertificadosCollectionNewCertificados.equals(status)) {
                        oldStatusOfCertificadosCollectionNewCertificados.getCertificadosCollection().remove(certificadosCollectionNewCertificados);
                        oldStatusOfCertificadosCollectionNewCertificados = em.merge(oldStatusOfCertificadosCollectionNewCertificados);
                    }
                }
            }
            for (Eventos eventosCollectionNewEventos : eventosCollectionNew) {
                if (!eventosCollectionOld.contains(eventosCollectionNewEventos)) {
                    Status oldStatusOfEventosCollectionNewEventos = eventosCollectionNewEventos.getStatus();
                    eventosCollectionNewEventos.setStatus(status);
                    eventosCollectionNewEventos = em.merge(eventosCollectionNewEventos);
                    if (oldStatusOfEventosCollectionNewEventos != null && !oldStatusOfEventosCollectionNewEventos.equals(status)) {
                        oldStatusOfEventosCollectionNewEventos.getEventosCollection().remove(eventosCollectionNewEventos);
                        oldStatusOfEventosCollectionNewEventos = em.merge(oldStatusOfEventosCollectionNewEventos);
                    }
                }
            }
            for (Patrocinios patrociniosCollectionNewPatrocinios : patrociniosCollectionNew) {
                if (!patrociniosCollectionOld.contains(patrociniosCollectionNewPatrocinios)) {
                    Status oldStatusOfPatrociniosCollectionNewPatrocinios = patrociniosCollectionNewPatrocinios.getStatus();
                    patrociniosCollectionNewPatrocinios.setStatus(status);
                    patrociniosCollectionNewPatrocinios = em.merge(patrociniosCollectionNewPatrocinios);
                    if (oldStatusOfPatrociniosCollectionNewPatrocinios != null && !oldStatusOfPatrociniosCollectionNewPatrocinios.equals(status)) {
                        oldStatusOfPatrociniosCollectionNewPatrocinios.getPatrociniosCollection().remove(patrociniosCollectionNewPatrocinios);
                        oldStatusOfPatrociniosCollectionNewPatrocinios = em.merge(oldStatusOfPatrociniosCollectionNewPatrocinios);
                    }
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Status oldStatusOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getStatus();
                    usuariosCollectionNewUsuarios.setStatus(status);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldStatusOfUsuariosCollectionNewUsuarios != null && !oldStatusOfUsuariosCollectionNewUsuarios.equals(status)) {
                        oldStatusOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldStatusOfUsuariosCollectionNewUsuarios = em.merge(oldStatusOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionNewParticipanteEquipe : participanteEquipeCollectionNew) {
                if (!participanteEquipeCollectionOld.contains(participanteEquipeCollectionNewParticipanteEquipe)) {
                    Status oldStatusOfParticipanteEquipeCollectionNewParticipanteEquipe = participanteEquipeCollectionNewParticipanteEquipe.getStatus();
                    participanteEquipeCollectionNewParticipanteEquipe.setStatus(status);
                    participanteEquipeCollectionNewParticipanteEquipe = em.merge(participanteEquipeCollectionNewParticipanteEquipe);
                    if (oldStatusOfParticipanteEquipeCollectionNewParticipanteEquipe != null && !oldStatusOfParticipanteEquipeCollectionNewParticipanteEquipe.equals(status)) {
                        oldStatusOfParticipanteEquipeCollectionNewParticipanteEquipe.getParticipanteEquipeCollection().remove(participanteEquipeCollectionNewParticipanteEquipe);
                        oldStatusOfParticipanteEquipeCollectionNewParticipanteEquipe = em.merge(oldStatusOfParticipanteEquipeCollectionNewParticipanteEquipe);
                    }
                }
            }
            for (Salas salasCollectionNewSalas : salasCollectionNew) {
                if (!salasCollectionOld.contains(salasCollectionNewSalas)) {
                    Status oldStatusOfSalasCollectionNewSalas = salasCollectionNewSalas.getStatus();
                    salasCollectionNewSalas.setStatus(status);
                    salasCollectionNewSalas = em.merge(salasCollectionNewSalas);
                    if (oldStatusOfSalasCollectionNewSalas != null && !oldStatusOfSalasCollectionNewSalas.equals(status)) {
                        oldStatusOfSalasCollectionNewSalas.getSalasCollection().remove(salasCollectionNewSalas);
                        oldStatusOfSalasCollectionNewSalas = em.merge(oldStatusOfSalasCollectionNewSalas);
                    }
                }
            }
            for (Equipes equipesCollectionNewEquipes : equipesCollectionNew) {
                if (!equipesCollectionOld.contains(equipesCollectionNewEquipes)) {
                    Status oldStatusOfEquipesCollectionNewEquipes = equipesCollectionNewEquipes.getStatus();
                    equipesCollectionNewEquipes.setStatus(status);
                    equipesCollectionNewEquipes = em.merge(equipesCollectionNewEquipes);
                    if (oldStatusOfEquipesCollectionNewEquipes != null && !oldStatusOfEquipesCollectionNewEquipes.equals(status)) {
                        oldStatusOfEquipesCollectionNewEquipes.getEquipesCollection().remove(equipesCollectionNewEquipes);
                        oldStatusOfEquipesCollectionNewEquipes = em.merge(oldStatusOfEquipesCollectionNewEquipes);
                    }
                }
            }
            for (Subeventos subeventosCollectionNewSubeventos : subeventosCollectionNew) {
                if (!subeventosCollectionOld.contains(subeventosCollectionNewSubeventos)) {
                    Status oldStatusOfSubeventosCollectionNewSubeventos = subeventosCollectionNewSubeventos.getStatus();
                    subeventosCollectionNewSubeventos.setStatus(status);
                    subeventosCollectionNewSubeventos = em.merge(subeventosCollectionNewSubeventos);
                    if (oldStatusOfSubeventosCollectionNewSubeventos != null && !oldStatusOfSubeventosCollectionNewSubeventos.equals(status)) {
                        oldStatusOfSubeventosCollectionNewSubeventos.getSubeventosCollection().remove(subeventosCollectionNewSubeventos);
                        oldStatusOfSubeventosCollectionNewSubeventos = em.merge(oldStatusOfSubeventosCollectionNewSubeventos);
                    }
                }
            }
            for (Editais editaisCollectionNewEditais : editaisCollectionNew) {
                if (!editaisCollectionOld.contains(editaisCollectionNewEditais)) {
                    Status oldStatusOfEditaisCollectionNewEditais = editaisCollectionNewEditais.getStatus();
                    editaisCollectionNewEditais.setStatus(status);
                    editaisCollectionNewEditais = em.merge(editaisCollectionNewEditais);
                    if (oldStatusOfEditaisCollectionNewEditais != null && !oldStatusOfEditaisCollectionNewEditais.equals(status)) {
                        oldStatusOfEditaisCollectionNewEditais.getEditaisCollection().remove(editaisCollectionNewEditais);
                        oldStatusOfEditaisCollectionNewEditais = em.merge(oldStatusOfEditaisCollectionNewEditais);
                    }
                }
            }
            for (Patrocinioimagens patrocinioimagensCollectionNewPatrocinioimagens : patrocinioimagensCollectionNew) {
                if (!patrocinioimagensCollectionOld.contains(patrocinioimagensCollectionNewPatrocinioimagens)) {
                    Status oldStatusOfPatrocinioimagensCollectionNewPatrocinioimagens = patrocinioimagensCollectionNewPatrocinioimagens.getStatus();
                    patrocinioimagensCollectionNewPatrocinioimagens.setStatus(status);
                    patrocinioimagensCollectionNewPatrocinioimagens = em.merge(patrocinioimagensCollectionNewPatrocinioimagens);
                    if (oldStatusOfPatrocinioimagensCollectionNewPatrocinioimagens != null && !oldStatusOfPatrocinioimagensCollectionNewPatrocinioimagens.equals(status)) {
                        oldStatusOfPatrocinioimagensCollectionNewPatrocinioimagens.getPatrocinioimagensCollection().remove(patrocinioimagensCollectionNewPatrocinioimagens);
                        oldStatusOfPatrocinioimagensCollectionNewPatrocinioimagens = em.merge(oldStatusOfPatrocinioimagensCollectionNewPatrocinioimagens);
                    }
                }
            }
            for (Crachas crachasCollectionNewCrachas : crachasCollectionNew) {
                if (!crachasCollectionOld.contains(crachasCollectionNewCrachas)) {
                    Status oldStatusOfCrachasCollectionNewCrachas = crachasCollectionNewCrachas.getStatus();
                    crachasCollectionNewCrachas.setStatus(status);
                    crachasCollectionNewCrachas = em.merge(crachasCollectionNewCrachas);
                    if (oldStatusOfCrachasCollectionNewCrachas != null && !oldStatusOfCrachasCollectionNewCrachas.equals(status)) {
                        oldStatusOfCrachasCollectionNewCrachas.getCrachasCollection().remove(crachasCollectionNewCrachas);
                        oldStatusOfCrachasCollectionNewCrachas = em.merge(oldStatusOfCrachasCollectionNewCrachas);
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
                Integer id = status.getIdstatus();
                if (findStatus(id) == null) {
                    throw new NonexistentEntityException("The status with id " + id + " no longer exists.");
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
            Status status;
            try {
                status = em.getReference(Status.class, id);
                status.getIdstatus();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The status with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscricaoEquipeSub> inscricaoEquipeSubCollectionOrphanCheck = status.getInscricaoEquipeSubCollection();
            for (InscricaoEquipeSub inscricaoEquipeSubCollectionOrphanCheckInscricaoEquipeSub : inscricaoEquipeSubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the InscricaoEquipeSub " + inscricaoEquipeSubCollectionOrphanCheckInscricaoEquipeSub + " in its inscricaoEquipeSubCollection field has a non-nullable status field.");
            }
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionOrphanCheck = status.getInscricaoPartSubeveCollection();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionOrphanCheckInscricaoPartSubeve : inscricaoPartSubeveCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the InscricaoPartSubeve " + inscricaoPartSubeveCollectionOrphanCheckInscricaoPartSubeve + " in its inscricaoPartSubeveCollection field has a non-nullable status field.");
            }
            Collection<Midias> midiasCollectionOrphanCheck = status.getMidiasCollection();
            for (Midias midiasCollectionOrphanCheckMidias : midiasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Midias " + midiasCollectionOrphanCheckMidias + " in its midiasCollection field has a non-nullable status field.");
            }
            Collection<Certificados> certificadosCollectionOrphanCheck = status.getCertificadosCollection();
            for (Certificados certificadosCollectionOrphanCheckCertificados : certificadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Certificados " + certificadosCollectionOrphanCheckCertificados + " in its certificadosCollection field has a non-nullable status field.");
            }
            Collection<Eventos> eventosCollectionOrphanCheck = status.getEventosCollection();
            for (Eventos eventosCollectionOrphanCheckEventos : eventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Eventos " + eventosCollectionOrphanCheckEventos + " in its eventosCollection field has a non-nullable status field.");
            }
            Collection<Patrocinios> patrociniosCollectionOrphanCheck = status.getPatrociniosCollection();
            for (Patrocinios patrociniosCollectionOrphanCheckPatrocinios : patrociniosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Patrocinios " + patrociniosCollectionOrphanCheckPatrocinios + " in its patrociniosCollection field has a non-nullable status field.");
            }
            Collection<Usuarios> usuariosCollectionOrphanCheck = status.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable status field.");
            }
            Collection<ParticipanteEquipe> participanteEquipeCollectionOrphanCheck = status.getParticipanteEquipeCollection();
            for (ParticipanteEquipe participanteEquipeCollectionOrphanCheckParticipanteEquipe : participanteEquipeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the ParticipanteEquipe " + participanteEquipeCollectionOrphanCheckParticipanteEquipe + " in its participanteEquipeCollection field has a non-nullable status field.");
            }
            Collection<Salas> salasCollectionOrphanCheck = status.getSalasCollection();
            for (Salas salasCollectionOrphanCheckSalas : salasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Salas " + salasCollectionOrphanCheckSalas + " in its salasCollection field has a non-nullable status field.");
            }
            Collection<Equipes> equipesCollectionOrphanCheck = status.getEquipesCollection();
            for (Equipes equipesCollectionOrphanCheckEquipes : equipesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Equipes " + equipesCollectionOrphanCheckEquipes + " in its equipesCollection field has a non-nullable status field.");
            }
            Collection<Subeventos> subeventosCollectionOrphanCheck = status.getSubeventosCollection();
            for (Subeventos subeventosCollectionOrphanCheckSubeventos : subeventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Subeventos " + subeventosCollectionOrphanCheckSubeventos + " in its subeventosCollection field has a non-nullable status field.");
            }
            Collection<Editais> editaisCollectionOrphanCheck = status.getEditaisCollection();
            for (Editais editaisCollectionOrphanCheckEditais : editaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Editais " + editaisCollectionOrphanCheckEditais + " in its editaisCollection field has a non-nullable status field.");
            }
            Collection<Patrocinioimagens> patrocinioimagensCollectionOrphanCheck = status.getPatrocinioimagensCollection();
            for (Patrocinioimagens patrocinioimagensCollectionOrphanCheckPatrocinioimagens : patrocinioimagensCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Patrocinioimagens " + patrocinioimagensCollectionOrphanCheckPatrocinioimagens + " in its patrocinioimagensCollection field has a non-nullable status field.");
            }
            Collection<Crachas> crachasCollectionOrphanCheck = status.getCrachasCollection();
            for (Crachas crachasCollectionOrphanCheckCrachas : crachasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Status (" + status + ") cannot be destroyed since the Crachas " + crachasCollectionOrphanCheckCrachas + " in its crachasCollection field has a non-nullable status field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(status);
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

    public List<Status> findStatusEntities() {
        return findStatusEntities(true, -1, -1);
    }

    public List<Status> findStatusEntities(int maxResults, int firstResult) {
        return findStatusEntities(false, maxResults, firstResult);
    }

    private List<Status> findStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Status.class));
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

    public Status findStatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Status.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Status> rt = cq.from(Status.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
