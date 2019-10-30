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
import model.Midias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.InscricaoPartEvento;
import model.PresencasEve;
import model.Certificados;
import model.Organizadores;
import model.Patrocinios;
import model.Subeventos;
import model.Editais;
import model.Crachas;
import model.Eventos;
import model.Participantes;

/**
 *
 * @author henrique
 */
public class EventosJpaController implements Serializable {

    public EventosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Eventos eventos) throws RollbackFailureException, Exception {
        if (eventos.getMidiasCollection() == null) {
            eventos.setMidiasCollection(new ArrayList<Midias>());
        }
        if (eventos.getInscricaoPartEventoCollection() == null) {
            eventos.setInscricaoPartEventoCollection(new ArrayList<InscricaoPartEvento>());
        }
        if (eventos.getPresencasEveCollection() == null) {
            eventos.setPresencasEveCollection(new ArrayList<PresencasEve>());
        }
        if (eventos.getCertificadosCollection() == null) {
            eventos.setCertificadosCollection(new ArrayList<Certificados>());
        }
        if (eventos.getOrganizadoresCollection() == null) {
            eventos.setOrganizadoresCollection(new ArrayList<Organizadores>());
        }
        if (eventos.getPatrociniosCollection() == null) {
            eventos.setPatrociniosCollection(new ArrayList<Patrocinios>());
        }
        if (eventos.getSubeventosCollection() == null) {
            eventos.setSubeventosCollection(new ArrayList<Subeventos>());
        }
        if (eventos.getEditaisCollection() == null) {
            eventos.setEditaisCollection(new ArrayList<Editais>());
        }
        if (eventos.getCrachasCollection() == null) {
            eventos.setCrachasCollection(new ArrayList<Crachas>());
        }
        if (eventos.getParticipantesCollection() == null) {
            eventos.setParticipantesCollection(new ArrayList<Participantes>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Status status = eventos.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getIdstatus());
                eventos.setStatus(status);
            }
            Collection<Midias> attachedMidiasCollection = new ArrayList<Midias>();
            for (Midias midiasCollectionMidiasToAttach : eventos.getMidiasCollection()) {
                midiasCollectionMidiasToAttach = em.getReference(midiasCollectionMidiasToAttach.getClass(), midiasCollectionMidiasToAttach.getIdmidia());
                attachedMidiasCollection.add(midiasCollectionMidiasToAttach);
            }
            eventos.setMidiasCollection(attachedMidiasCollection);
            Collection<InscricaoPartEvento> attachedInscricaoPartEventoCollection = new ArrayList<InscricaoPartEvento>();
            for (InscricaoPartEvento inscricaoPartEventoCollectionInscricaoPartEventoToAttach : eventos.getInscricaoPartEventoCollection()) {
                inscricaoPartEventoCollectionInscricaoPartEventoToAttach = em.getReference(inscricaoPartEventoCollectionInscricaoPartEventoToAttach.getClass(), inscricaoPartEventoCollectionInscricaoPartEventoToAttach.getIdinscricao());
                attachedInscricaoPartEventoCollection.add(inscricaoPartEventoCollectionInscricaoPartEventoToAttach);
            }
            eventos.setInscricaoPartEventoCollection(attachedInscricaoPartEventoCollection);
            Collection<PresencasEve> attachedPresencasEveCollection = new ArrayList<PresencasEve>();
            for (PresencasEve presencasEveCollectionPresencasEveToAttach : eventos.getPresencasEveCollection()) {
                presencasEveCollectionPresencasEveToAttach = em.getReference(presencasEveCollectionPresencasEveToAttach.getClass(), presencasEveCollectionPresencasEveToAttach.getPresencasEvePK());
                attachedPresencasEveCollection.add(presencasEveCollectionPresencasEveToAttach);
            }
            eventos.setPresencasEveCollection(attachedPresencasEveCollection);
            Collection<Certificados> attachedCertificadosCollection = new ArrayList<Certificados>();
            for (Certificados certificadosCollectionCertificadosToAttach : eventos.getCertificadosCollection()) {
                certificadosCollectionCertificadosToAttach = em.getReference(certificadosCollectionCertificadosToAttach.getClass(), certificadosCollectionCertificadosToAttach.getIdcertificado());
                attachedCertificadosCollection.add(certificadosCollectionCertificadosToAttach);
            }
            eventos.setCertificadosCollection(attachedCertificadosCollection);
            Collection<Organizadores> attachedOrganizadoresCollection = new ArrayList<Organizadores>();
            for (Organizadores organizadoresCollectionOrganizadoresToAttach : eventos.getOrganizadoresCollection()) {
                organizadoresCollectionOrganizadoresToAttach = em.getReference(organizadoresCollectionOrganizadoresToAttach.getClass(), organizadoresCollectionOrganizadoresToAttach.getIdorganizador());
                attachedOrganizadoresCollection.add(organizadoresCollectionOrganizadoresToAttach);
            }
            eventos.setOrganizadoresCollection(attachedOrganizadoresCollection);
            Collection<Patrocinios> attachedPatrociniosCollection = new ArrayList<Patrocinios>();
            for (Patrocinios patrociniosCollectionPatrociniosToAttach : eventos.getPatrociniosCollection()) {
                patrociniosCollectionPatrociniosToAttach = em.getReference(patrociniosCollectionPatrociniosToAttach.getClass(), patrociniosCollectionPatrociniosToAttach.getIdpatrocinio());
                attachedPatrociniosCollection.add(patrociniosCollectionPatrociniosToAttach);
            }
            eventos.setPatrociniosCollection(attachedPatrociniosCollection);
            Collection<Subeventos> attachedSubeventosCollection = new ArrayList<Subeventos>();
            for (Subeventos subeventosCollectionSubeventosToAttach : eventos.getSubeventosCollection()) {
                subeventosCollectionSubeventosToAttach = em.getReference(subeventosCollectionSubeventosToAttach.getClass(), subeventosCollectionSubeventosToAttach.getIdsubevento());
                attachedSubeventosCollection.add(subeventosCollectionSubeventosToAttach);
            }
            eventos.setSubeventosCollection(attachedSubeventosCollection);
            Collection<Editais> attachedEditaisCollection = new ArrayList<Editais>();
            for (Editais editaisCollectionEditaisToAttach : eventos.getEditaisCollection()) {
                editaisCollectionEditaisToAttach = em.getReference(editaisCollectionEditaisToAttach.getClass(), editaisCollectionEditaisToAttach.getIdedital());
                attachedEditaisCollection.add(editaisCollectionEditaisToAttach);
            }
            eventos.setEditaisCollection(attachedEditaisCollection);
            Collection<Crachas> attachedCrachasCollection = new ArrayList<Crachas>();
            for (Crachas crachasCollectionCrachasToAttach : eventos.getCrachasCollection()) {
                crachasCollectionCrachasToAttach = em.getReference(crachasCollectionCrachasToAttach.getClass(), crachasCollectionCrachasToAttach.getIdcracha());
                attachedCrachasCollection.add(crachasCollectionCrachasToAttach);
            }
            eventos.setCrachasCollection(attachedCrachasCollection);
            Collection<Participantes> attachedParticipantesCollection = new ArrayList<Participantes>();
            for (Participantes participantesCollectionParticipantesToAttach : eventos.getParticipantesCollection()) {
                participantesCollectionParticipantesToAttach = em.getReference(participantesCollectionParticipantesToAttach.getClass(), participantesCollectionParticipantesToAttach.getIdparticipante());
                attachedParticipantesCollection.add(participantesCollectionParticipantesToAttach);
            }
            eventos.setParticipantesCollection(attachedParticipantesCollection);
            em.persist(eventos);
            if (status != null) {
                status.getEventosCollection().add(eventos);
                status = em.merge(status);
            }
            for (Midias midiasCollectionMidias : eventos.getMidiasCollection()) {
                Eventos oldEventosOfMidiasCollectionMidias = midiasCollectionMidias.getEventos();
                midiasCollectionMidias.setEventos(eventos);
                midiasCollectionMidias = em.merge(midiasCollectionMidias);
                if (oldEventosOfMidiasCollectionMidias != null) {
                    oldEventosOfMidiasCollectionMidias.getMidiasCollection().remove(midiasCollectionMidias);
                    oldEventosOfMidiasCollectionMidias = em.merge(oldEventosOfMidiasCollectionMidias);
                }
            }
            for (InscricaoPartEvento inscricaoPartEventoCollectionInscricaoPartEvento : eventos.getInscricaoPartEventoCollection()) {
                Eventos oldEventosOfInscricaoPartEventoCollectionInscricaoPartEvento = inscricaoPartEventoCollectionInscricaoPartEvento.getEventos();
                inscricaoPartEventoCollectionInscricaoPartEvento.setEventos(eventos);
                inscricaoPartEventoCollectionInscricaoPartEvento = em.merge(inscricaoPartEventoCollectionInscricaoPartEvento);
                if (oldEventosOfInscricaoPartEventoCollectionInscricaoPartEvento != null) {
                    oldEventosOfInscricaoPartEventoCollectionInscricaoPartEvento.getInscricaoPartEventoCollection().remove(inscricaoPartEventoCollectionInscricaoPartEvento);
                    oldEventosOfInscricaoPartEventoCollectionInscricaoPartEvento = em.merge(oldEventosOfInscricaoPartEventoCollectionInscricaoPartEvento);
                }
            }
            for (PresencasEve presencasEveCollectionPresencasEve : eventos.getPresencasEveCollection()) {
                Eventos oldEventosOfPresencasEveCollectionPresencasEve = presencasEveCollectionPresencasEve.getEventos();
                presencasEveCollectionPresencasEve.setEventos(eventos);
                presencasEveCollectionPresencasEve = em.merge(presencasEveCollectionPresencasEve);
                if (oldEventosOfPresencasEveCollectionPresencasEve != null) {
                    oldEventosOfPresencasEveCollectionPresencasEve.getPresencasEveCollection().remove(presencasEveCollectionPresencasEve);
                    oldEventosOfPresencasEveCollectionPresencasEve = em.merge(oldEventosOfPresencasEveCollectionPresencasEve);
                }
            }
            for (Certificados certificadosCollectionCertificados : eventos.getCertificadosCollection()) {
                Eventos oldEventosOfCertificadosCollectionCertificados = certificadosCollectionCertificados.getEventos();
                certificadosCollectionCertificados.setEventos(eventos);
                certificadosCollectionCertificados = em.merge(certificadosCollectionCertificados);
                if (oldEventosOfCertificadosCollectionCertificados != null) {
                    oldEventosOfCertificadosCollectionCertificados.getCertificadosCollection().remove(certificadosCollectionCertificados);
                    oldEventosOfCertificadosCollectionCertificados = em.merge(oldEventosOfCertificadosCollectionCertificados);
                }
            }
            for (Organizadores organizadoresCollectionOrganizadores : eventos.getOrganizadoresCollection()) {
                Eventos oldEventosOfOrganizadoresCollectionOrganizadores = organizadoresCollectionOrganizadores.getEventos();
                organizadoresCollectionOrganizadores.setEventos(eventos);
                organizadoresCollectionOrganizadores = em.merge(organizadoresCollectionOrganizadores);
                if (oldEventosOfOrganizadoresCollectionOrganizadores != null) {
                    oldEventosOfOrganizadoresCollectionOrganizadores.getOrganizadoresCollection().remove(organizadoresCollectionOrganizadores);
                    oldEventosOfOrganizadoresCollectionOrganizadores = em.merge(oldEventosOfOrganizadoresCollectionOrganizadores);
                }
            }
            for (Patrocinios patrociniosCollectionPatrocinios : eventos.getPatrociniosCollection()) {
                Eventos oldEventosOfPatrociniosCollectionPatrocinios = patrociniosCollectionPatrocinios.getEventos();
                patrociniosCollectionPatrocinios.setEventos(eventos);
                patrociniosCollectionPatrocinios = em.merge(patrociniosCollectionPatrocinios);
                if (oldEventosOfPatrociniosCollectionPatrocinios != null) {
                    oldEventosOfPatrociniosCollectionPatrocinios.getPatrociniosCollection().remove(patrociniosCollectionPatrocinios);
                    oldEventosOfPatrociniosCollectionPatrocinios = em.merge(oldEventosOfPatrociniosCollectionPatrocinios);
                }
            }
            for (Subeventos subeventosCollectionSubeventos : eventos.getSubeventosCollection()) {
                Eventos oldEventosOfSubeventosCollectionSubeventos = subeventosCollectionSubeventos.getEventos();
                subeventosCollectionSubeventos.setEventos(eventos);
                subeventosCollectionSubeventos = em.merge(subeventosCollectionSubeventos);
                if (oldEventosOfSubeventosCollectionSubeventos != null) {
                    oldEventosOfSubeventosCollectionSubeventos.getSubeventosCollection().remove(subeventosCollectionSubeventos);
                    oldEventosOfSubeventosCollectionSubeventos = em.merge(oldEventosOfSubeventosCollectionSubeventos);
                }
            }
            for (Editais editaisCollectionEditais : eventos.getEditaisCollection()) {
                Eventos oldEventosOfEditaisCollectionEditais = editaisCollectionEditais.getEventos();
                editaisCollectionEditais.setEventos(eventos);
                editaisCollectionEditais = em.merge(editaisCollectionEditais);
                if (oldEventosOfEditaisCollectionEditais != null) {
                    oldEventosOfEditaisCollectionEditais.getEditaisCollection().remove(editaisCollectionEditais);
                    oldEventosOfEditaisCollectionEditais = em.merge(oldEventosOfEditaisCollectionEditais);
                }
            }
            for (Crachas crachasCollectionCrachas : eventos.getCrachasCollection()) {
                Eventos oldEventosOfCrachasCollectionCrachas = crachasCollectionCrachas.getEventos();
                crachasCollectionCrachas.setEventos(eventos);
                crachasCollectionCrachas = em.merge(crachasCollectionCrachas);
                if (oldEventosOfCrachasCollectionCrachas != null) {
                    oldEventosOfCrachasCollectionCrachas.getCrachasCollection().remove(crachasCollectionCrachas);
                    oldEventosOfCrachasCollectionCrachas = em.merge(oldEventosOfCrachasCollectionCrachas);
                }
            }
            for (Participantes participantesCollectionParticipantes : eventos.getParticipantesCollection()) {
                Eventos oldEventosOfParticipantesCollectionParticipantes = participantesCollectionParticipantes.getEventos();
                participantesCollectionParticipantes.setEventos(eventos);
                participantesCollectionParticipantes = em.merge(participantesCollectionParticipantes);
                if (oldEventosOfParticipantesCollectionParticipantes != null) {
                    oldEventosOfParticipantesCollectionParticipantes.getParticipantesCollection().remove(participantesCollectionParticipantes);
                    oldEventosOfParticipantesCollectionParticipantes = em.merge(oldEventosOfParticipantesCollectionParticipantes);
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

    public void edit(Eventos eventos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos persistentEventos = em.find(Eventos.class, eventos.getIdevento());
            Status statusOld = persistentEventos.getStatus();
            Status statusNew = eventos.getStatus();
            Collection<Midias> midiasCollectionOld = persistentEventos.getMidiasCollection();
            Collection<Midias> midiasCollectionNew = eventos.getMidiasCollection();
            Collection<InscricaoPartEvento> inscricaoPartEventoCollectionOld = persistentEventos.getInscricaoPartEventoCollection();
            Collection<InscricaoPartEvento> inscricaoPartEventoCollectionNew = eventos.getInscricaoPartEventoCollection();
            Collection<PresencasEve> presencasEveCollectionOld = persistentEventos.getPresencasEveCollection();
            Collection<PresencasEve> presencasEveCollectionNew = eventos.getPresencasEveCollection();
            Collection<Certificados> certificadosCollectionOld = persistentEventos.getCertificadosCollection();
            Collection<Certificados> certificadosCollectionNew = eventos.getCertificadosCollection();
            Collection<Organizadores> organizadoresCollectionOld = persistentEventos.getOrganizadoresCollection();
            Collection<Organizadores> organizadoresCollectionNew = eventos.getOrganizadoresCollection();
            Collection<Patrocinios> patrociniosCollectionOld = persistentEventos.getPatrociniosCollection();
            Collection<Patrocinios> patrociniosCollectionNew = eventos.getPatrociniosCollection();
            Collection<Subeventos> subeventosCollectionOld = persistentEventos.getSubeventosCollection();
            Collection<Subeventos> subeventosCollectionNew = eventos.getSubeventosCollection();
            Collection<Editais> editaisCollectionOld = persistentEventos.getEditaisCollection();
            Collection<Editais> editaisCollectionNew = eventos.getEditaisCollection();
            Collection<Crachas> crachasCollectionOld = persistentEventos.getCrachasCollection();
            Collection<Crachas> crachasCollectionNew = eventos.getCrachasCollection();
            Collection<Participantes> participantesCollectionOld = persistentEventos.getParticipantesCollection();
            Collection<Participantes> participantesCollectionNew = eventos.getParticipantesCollection();
            List<String> illegalOrphanMessages = null;
            for (InscricaoPartEvento inscricaoPartEventoCollectionOldInscricaoPartEvento : inscricaoPartEventoCollectionOld) {
                if (!inscricaoPartEventoCollectionNew.contains(inscricaoPartEventoCollectionOldInscricaoPartEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoPartEvento " + inscricaoPartEventoCollectionOldInscricaoPartEvento + " since its eventos field is not nullable.");
                }
            }
            for (PresencasEve presencasEveCollectionOldPresencasEve : presencasEveCollectionOld) {
                if (!presencasEveCollectionNew.contains(presencasEveCollectionOldPresencasEve)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencasEve " + presencasEveCollectionOldPresencasEve + " since its eventos field is not nullable.");
                }
            }
            for (Certificados certificadosCollectionOldCertificados : certificadosCollectionOld) {
                if (!certificadosCollectionNew.contains(certificadosCollectionOldCertificados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Certificados " + certificadosCollectionOldCertificados + " since its eventos field is not nullable.");
                }
            }
            for (Organizadores organizadoresCollectionOldOrganizadores : organizadoresCollectionOld) {
                if (!organizadoresCollectionNew.contains(organizadoresCollectionOldOrganizadores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Organizadores " + organizadoresCollectionOldOrganizadores + " since its eventos field is not nullable.");
                }
            }
            for (Patrocinios patrociniosCollectionOldPatrocinios : patrociniosCollectionOld) {
                if (!patrociniosCollectionNew.contains(patrociniosCollectionOldPatrocinios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patrocinios " + patrociniosCollectionOldPatrocinios + " since its eventos field is not nullable.");
                }
            }
            for (Subeventos subeventosCollectionOldSubeventos : subeventosCollectionOld) {
                if (!subeventosCollectionNew.contains(subeventosCollectionOldSubeventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Subeventos " + subeventosCollectionOldSubeventos + " since its eventos field is not nullable.");
                }
            }
            for (Editais editaisCollectionOldEditais : editaisCollectionOld) {
                if (!editaisCollectionNew.contains(editaisCollectionOldEditais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Editais " + editaisCollectionOldEditais + " since its eventos field is not nullable.");
                }
            }
            for (Crachas crachasCollectionOldCrachas : crachasCollectionOld) {
                if (!crachasCollectionNew.contains(crachasCollectionOldCrachas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Crachas " + crachasCollectionOldCrachas + " since its eventos field is not nullable.");
                }
            }
            for (Participantes participantesCollectionOldParticipantes : participantesCollectionOld) {
                if (!participantesCollectionNew.contains(participantesCollectionOldParticipantes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participantes " + participantesCollectionOldParticipantes + " since its eventos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getIdstatus());
                eventos.setStatus(statusNew);
            }
            Collection<Midias> attachedMidiasCollectionNew = new ArrayList<Midias>();
            for (Midias midiasCollectionNewMidiasToAttach : midiasCollectionNew) {
                midiasCollectionNewMidiasToAttach = em.getReference(midiasCollectionNewMidiasToAttach.getClass(), midiasCollectionNewMidiasToAttach.getIdmidia());
                attachedMidiasCollectionNew.add(midiasCollectionNewMidiasToAttach);
            }
            midiasCollectionNew = attachedMidiasCollectionNew;
            eventos.setMidiasCollection(midiasCollectionNew);
            Collection<InscricaoPartEvento> attachedInscricaoPartEventoCollectionNew = new ArrayList<InscricaoPartEvento>();
            for (InscricaoPartEvento inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach : inscricaoPartEventoCollectionNew) {
                inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach = em.getReference(inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach.getClass(), inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach.getIdinscricao());
                attachedInscricaoPartEventoCollectionNew.add(inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach);
            }
            inscricaoPartEventoCollectionNew = attachedInscricaoPartEventoCollectionNew;
            eventos.setInscricaoPartEventoCollection(inscricaoPartEventoCollectionNew);
            Collection<PresencasEve> attachedPresencasEveCollectionNew = new ArrayList<PresencasEve>();
            for (PresencasEve presencasEveCollectionNewPresencasEveToAttach : presencasEveCollectionNew) {
                presencasEveCollectionNewPresencasEveToAttach = em.getReference(presencasEveCollectionNewPresencasEveToAttach.getClass(), presencasEveCollectionNewPresencasEveToAttach.getPresencasEvePK());
                attachedPresencasEveCollectionNew.add(presencasEveCollectionNewPresencasEveToAttach);
            }
            presencasEveCollectionNew = attachedPresencasEveCollectionNew;
            eventos.setPresencasEveCollection(presencasEveCollectionNew);
            Collection<Certificados> attachedCertificadosCollectionNew = new ArrayList<Certificados>();
            for (Certificados certificadosCollectionNewCertificadosToAttach : certificadosCollectionNew) {
                certificadosCollectionNewCertificadosToAttach = em.getReference(certificadosCollectionNewCertificadosToAttach.getClass(), certificadosCollectionNewCertificadosToAttach.getIdcertificado());
                attachedCertificadosCollectionNew.add(certificadosCollectionNewCertificadosToAttach);
            }
            certificadosCollectionNew = attachedCertificadosCollectionNew;
            eventos.setCertificadosCollection(certificadosCollectionNew);
            Collection<Organizadores> attachedOrganizadoresCollectionNew = new ArrayList<Organizadores>();
            for (Organizadores organizadoresCollectionNewOrganizadoresToAttach : organizadoresCollectionNew) {
                organizadoresCollectionNewOrganizadoresToAttach = em.getReference(organizadoresCollectionNewOrganizadoresToAttach.getClass(), organizadoresCollectionNewOrganizadoresToAttach.getIdorganizador());
                attachedOrganizadoresCollectionNew.add(organizadoresCollectionNewOrganizadoresToAttach);
            }
            organizadoresCollectionNew = attachedOrganizadoresCollectionNew;
            eventos.setOrganizadoresCollection(organizadoresCollectionNew);
            Collection<Patrocinios> attachedPatrociniosCollectionNew = new ArrayList<Patrocinios>();
            for (Patrocinios patrociniosCollectionNewPatrociniosToAttach : patrociniosCollectionNew) {
                patrociniosCollectionNewPatrociniosToAttach = em.getReference(patrociniosCollectionNewPatrociniosToAttach.getClass(), patrociniosCollectionNewPatrociniosToAttach.getIdpatrocinio());
                attachedPatrociniosCollectionNew.add(patrociniosCollectionNewPatrociniosToAttach);
            }
            patrociniosCollectionNew = attachedPatrociniosCollectionNew;
            eventos.setPatrociniosCollection(patrociniosCollectionNew);
            Collection<Subeventos> attachedSubeventosCollectionNew = new ArrayList<Subeventos>();
            for (Subeventos subeventosCollectionNewSubeventosToAttach : subeventosCollectionNew) {
                subeventosCollectionNewSubeventosToAttach = em.getReference(subeventosCollectionNewSubeventosToAttach.getClass(), subeventosCollectionNewSubeventosToAttach.getIdsubevento());
                attachedSubeventosCollectionNew.add(subeventosCollectionNewSubeventosToAttach);
            }
            subeventosCollectionNew = attachedSubeventosCollectionNew;
            eventos.setSubeventosCollection(subeventosCollectionNew);
            Collection<Editais> attachedEditaisCollectionNew = new ArrayList<Editais>();
            for (Editais editaisCollectionNewEditaisToAttach : editaisCollectionNew) {
                editaisCollectionNewEditaisToAttach = em.getReference(editaisCollectionNewEditaisToAttach.getClass(), editaisCollectionNewEditaisToAttach.getIdedital());
                attachedEditaisCollectionNew.add(editaisCollectionNewEditaisToAttach);
            }
            editaisCollectionNew = attachedEditaisCollectionNew;
            eventos.setEditaisCollection(editaisCollectionNew);
            Collection<Crachas> attachedCrachasCollectionNew = new ArrayList<Crachas>();
            for (Crachas crachasCollectionNewCrachasToAttach : crachasCollectionNew) {
                crachasCollectionNewCrachasToAttach = em.getReference(crachasCollectionNewCrachasToAttach.getClass(), crachasCollectionNewCrachasToAttach.getIdcracha());
                attachedCrachasCollectionNew.add(crachasCollectionNewCrachasToAttach);
            }
            crachasCollectionNew = attachedCrachasCollectionNew;
            eventos.setCrachasCollection(crachasCollectionNew);
            Collection<Participantes> attachedParticipantesCollectionNew = new ArrayList<Participantes>();
            for (Participantes participantesCollectionNewParticipantesToAttach : participantesCollectionNew) {
                participantesCollectionNewParticipantesToAttach = em.getReference(participantesCollectionNewParticipantesToAttach.getClass(), participantesCollectionNewParticipantesToAttach.getIdparticipante());
                attachedParticipantesCollectionNew.add(participantesCollectionNewParticipantesToAttach);
            }
            participantesCollectionNew = attachedParticipantesCollectionNew;
            eventos.setParticipantesCollection(participantesCollectionNew);
            eventos = em.merge(eventos);
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getEventosCollection().remove(eventos);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getEventosCollection().add(eventos);
                statusNew = em.merge(statusNew);
            }
            for (Midias midiasCollectionOldMidias : midiasCollectionOld) {
                if (!midiasCollectionNew.contains(midiasCollectionOldMidias)) {
                    midiasCollectionOldMidias.setEventos(null);
                    midiasCollectionOldMidias = em.merge(midiasCollectionOldMidias);
                }
            }
            for (Midias midiasCollectionNewMidias : midiasCollectionNew) {
                if (!midiasCollectionOld.contains(midiasCollectionNewMidias)) {
                    Eventos oldEventosOfMidiasCollectionNewMidias = midiasCollectionNewMidias.getEventos();
                    midiasCollectionNewMidias.setEventos(eventos);
                    midiasCollectionNewMidias = em.merge(midiasCollectionNewMidias);
                    if (oldEventosOfMidiasCollectionNewMidias != null && !oldEventosOfMidiasCollectionNewMidias.equals(eventos)) {
                        oldEventosOfMidiasCollectionNewMidias.getMidiasCollection().remove(midiasCollectionNewMidias);
                        oldEventosOfMidiasCollectionNewMidias = em.merge(oldEventosOfMidiasCollectionNewMidias);
                    }
                }
            }
            for (InscricaoPartEvento inscricaoPartEventoCollectionNewInscricaoPartEvento : inscricaoPartEventoCollectionNew) {
                if (!inscricaoPartEventoCollectionOld.contains(inscricaoPartEventoCollectionNewInscricaoPartEvento)) {
                    Eventos oldEventosOfInscricaoPartEventoCollectionNewInscricaoPartEvento = inscricaoPartEventoCollectionNewInscricaoPartEvento.getEventos();
                    inscricaoPartEventoCollectionNewInscricaoPartEvento.setEventos(eventos);
                    inscricaoPartEventoCollectionNewInscricaoPartEvento = em.merge(inscricaoPartEventoCollectionNewInscricaoPartEvento);
                    if (oldEventosOfInscricaoPartEventoCollectionNewInscricaoPartEvento != null && !oldEventosOfInscricaoPartEventoCollectionNewInscricaoPartEvento.equals(eventos)) {
                        oldEventosOfInscricaoPartEventoCollectionNewInscricaoPartEvento.getInscricaoPartEventoCollection().remove(inscricaoPartEventoCollectionNewInscricaoPartEvento);
                        oldEventosOfInscricaoPartEventoCollectionNewInscricaoPartEvento = em.merge(oldEventosOfInscricaoPartEventoCollectionNewInscricaoPartEvento);
                    }
                }
            }
            for (PresencasEve presencasEveCollectionNewPresencasEve : presencasEveCollectionNew) {
                if (!presencasEveCollectionOld.contains(presencasEveCollectionNewPresencasEve)) {
                    Eventos oldEventosOfPresencasEveCollectionNewPresencasEve = presencasEveCollectionNewPresencasEve.getEventos();
                    presencasEveCollectionNewPresencasEve.setEventos(eventos);
                    presencasEveCollectionNewPresencasEve = em.merge(presencasEveCollectionNewPresencasEve);
                    if (oldEventosOfPresencasEveCollectionNewPresencasEve != null && !oldEventosOfPresencasEveCollectionNewPresencasEve.equals(eventos)) {
                        oldEventosOfPresencasEveCollectionNewPresencasEve.getPresencasEveCollection().remove(presencasEveCollectionNewPresencasEve);
                        oldEventosOfPresencasEveCollectionNewPresencasEve = em.merge(oldEventosOfPresencasEveCollectionNewPresencasEve);
                    }
                }
            }
            for (Certificados certificadosCollectionNewCertificados : certificadosCollectionNew) {
                if (!certificadosCollectionOld.contains(certificadosCollectionNewCertificados)) {
                    Eventos oldEventosOfCertificadosCollectionNewCertificados = certificadosCollectionNewCertificados.getEventos();
                    certificadosCollectionNewCertificados.setEventos(eventos);
                    certificadosCollectionNewCertificados = em.merge(certificadosCollectionNewCertificados);
                    if (oldEventosOfCertificadosCollectionNewCertificados != null && !oldEventosOfCertificadosCollectionNewCertificados.equals(eventos)) {
                        oldEventosOfCertificadosCollectionNewCertificados.getCertificadosCollection().remove(certificadosCollectionNewCertificados);
                        oldEventosOfCertificadosCollectionNewCertificados = em.merge(oldEventosOfCertificadosCollectionNewCertificados);
                    }
                }
            }
            for (Organizadores organizadoresCollectionNewOrganizadores : organizadoresCollectionNew) {
                if (!organizadoresCollectionOld.contains(organizadoresCollectionNewOrganizadores)) {
                    Eventos oldEventosOfOrganizadoresCollectionNewOrganizadores = organizadoresCollectionNewOrganizadores.getEventos();
                    organizadoresCollectionNewOrganizadores.setEventos(eventos);
                    organizadoresCollectionNewOrganizadores = em.merge(organizadoresCollectionNewOrganizadores);
                    if (oldEventosOfOrganizadoresCollectionNewOrganizadores != null && !oldEventosOfOrganizadoresCollectionNewOrganizadores.equals(eventos)) {
                        oldEventosOfOrganizadoresCollectionNewOrganizadores.getOrganizadoresCollection().remove(organizadoresCollectionNewOrganizadores);
                        oldEventosOfOrganizadoresCollectionNewOrganizadores = em.merge(oldEventosOfOrganizadoresCollectionNewOrganizadores);
                    }
                }
            }
            for (Patrocinios patrociniosCollectionNewPatrocinios : patrociniosCollectionNew) {
                if (!patrociniosCollectionOld.contains(patrociniosCollectionNewPatrocinios)) {
                    Eventos oldEventosOfPatrociniosCollectionNewPatrocinios = patrociniosCollectionNewPatrocinios.getEventos();
                    patrociniosCollectionNewPatrocinios.setEventos(eventos);
                    patrociniosCollectionNewPatrocinios = em.merge(patrociniosCollectionNewPatrocinios);
                    if (oldEventosOfPatrociniosCollectionNewPatrocinios != null && !oldEventosOfPatrociniosCollectionNewPatrocinios.equals(eventos)) {
                        oldEventosOfPatrociniosCollectionNewPatrocinios.getPatrociniosCollection().remove(patrociniosCollectionNewPatrocinios);
                        oldEventosOfPatrociniosCollectionNewPatrocinios = em.merge(oldEventosOfPatrociniosCollectionNewPatrocinios);
                    }
                }
            }
            for (Subeventos subeventosCollectionNewSubeventos : subeventosCollectionNew) {
                if (!subeventosCollectionOld.contains(subeventosCollectionNewSubeventos)) {
                    Eventos oldEventosOfSubeventosCollectionNewSubeventos = subeventosCollectionNewSubeventos.getEventos();
                    subeventosCollectionNewSubeventos.setEventos(eventos);
                    subeventosCollectionNewSubeventos = em.merge(subeventosCollectionNewSubeventos);
                    if (oldEventosOfSubeventosCollectionNewSubeventos != null && !oldEventosOfSubeventosCollectionNewSubeventos.equals(eventos)) {
                        oldEventosOfSubeventosCollectionNewSubeventos.getSubeventosCollection().remove(subeventosCollectionNewSubeventos);
                        oldEventosOfSubeventosCollectionNewSubeventos = em.merge(oldEventosOfSubeventosCollectionNewSubeventos);
                    }
                }
            }
            for (Editais editaisCollectionNewEditais : editaisCollectionNew) {
                if (!editaisCollectionOld.contains(editaisCollectionNewEditais)) {
                    Eventos oldEventosOfEditaisCollectionNewEditais = editaisCollectionNewEditais.getEventos();
                    editaisCollectionNewEditais.setEventos(eventos);
                    editaisCollectionNewEditais = em.merge(editaisCollectionNewEditais);
                    if (oldEventosOfEditaisCollectionNewEditais != null && !oldEventosOfEditaisCollectionNewEditais.equals(eventos)) {
                        oldEventosOfEditaisCollectionNewEditais.getEditaisCollection().remove(editaisCollectionNewEditais);
                        oldEventosOfEditaisCollectionNewEditais = em.merge(oldEventosOfEditaisCollectionNewEditais);
                    }
                }
            }
            for (Crachas crachasCollectionNewCrachas : crachasCollectionNew) {
                if (!crachasCollectionOld.contains(crachasCollectionNewCrachas)) {
                    Eventos oldEventosOfCrachasCollectionNewCrachas = crachasCollectionNewCrachas.getEventos();
                    crachasCollectionNewCrachas.setEventos(eventos);
                    crachasCollectionNewCrachas = em.merge(crachasCollectionNewCrachas);
                    if (oldEventosOfCrachasCollectionNewCrachas != null && !oldEventosOfCrachasCollectionNewCrachas.equals(eventos)) {
                        oldEventosOfCrachasCollectionNewCrachas.getCrachasCollection().remove(crachasCollectionNewCrachas);
                        oldEventosOfCrachasCollectionNewCrachas = em.merge(oldEventosOfCrachasCollectionNewCrachas);
                    }
                }
            }
            for (Participantes participantesCollectionNewParticipantes : participantesCollectionNew) {
                if (!participantesCollectionOld.contains(participantesCollectionNewParticipantes)) {
                    Eventos oldEventosOfParticipantesCollectionNewParticipantes = participantesCollectionNewParticipantes.getEventos();
                    participantesCollectionNewParticipantes.setEventos(eventos);
                    participantesCollectionNewParticipantes = em.merge(participantesCollectionNewParticipantes);
                    if (oldEventosOfParticipantesCollectionNewParticipantes != null && !oldEventosOfParticipantesCollectionNewParticipantes.equals(eventos)) {
                        oldEventosOfParticipantesCollectionNewParticipantes.getParticipantesCollection().remove(participantesCollectionNewParticipantes);
                        oldEventosOfParticipantesCollectionNewParticipantes = em.merge(oldEventosOfParticipantesCollectionNewParticipantes);
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
                Integer id = eventos.getIdevento();
                if (findEventos(id) == null) {
                    throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.");
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
            Eventos eventos;
            try {
                eventos = em.getReference(Eventos.class, id);
                eventos.getIdevento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscricaoPartEvento> inscricaoPartEventoCollectionOrphanCheck = eventos.getInscricaoPartEventoCollection();
            for (InscricaoPartEvento inscricaoPartEventoCollectionOrphanCheckInscricaoPartEvento : inscricaoPartEventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the InscricaoPartEvento " + inscricaoPartEventoCollectionOrphanCheckInscricaoPartEvento + " in its inscricaoPartEventoCollection field has a non-nullable eventos field.");
            }
            Collection<PresencasEve> presencasEveCollectionOrphanCheck = eventos.getPresencasEveCollection();
            for (PresencasEve presencasEveCollectionOrphanCheckPresencasEve : presencasEveCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the PresencasEve " + presencasEveCollectionOrphanCheckPresencasEve + " in its presencasEveCollection field has a non-nullable eventos field.");
            }
            Collection<Certificados> certificadosCollectionOrphanCheck = eventos.getCertificadosCollection();
            for (Certificados certificadosCollectionOrphanCheckCertificados : certificadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Certificados " + certificadosCollectionOrphanCheckCertificados + " in its certificadosCollection field has a non-nullable eventos field.");
            }
            Collection<Organizadores> organizadoresCollectionOrphanCheck = eventos.getOrganizadoresCollection();
            for (Organizadores organizadoresCollectionOrphanCheckOrganizadores : organizadoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Organizadores " + organizadoresCollectionOrphanCheckOrganizadores + " in its organizadoresCollection field has a non-nullable eventos field.");
            }
            Collection<Patrocinios> patrociniosCollectionOrphanCheck = eventos.getPatrociniosCollection();
            for (Patrocinios patrociniosCollectionOrphanCheckPatrocinios : patrociniosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Patrocinios " + patrociniosCollectionOrphanCheckPatrocinios + " in its patrociniosCollection field has a non-nullable eventos field.");
            }
            Collection<Subeventos> subeventosCollectionOrphanCheck = eventos.getSubeventosCollection();
            for (Subeventos subeventosCollectionOrphanCheckSubeventos : subeventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Subeventos " + subeventosCollectionOrphanCheckSubeventos + " in its subeventosCollection field has a non-nullable eventos field.");
            }
            Collection<Editais> editaisCollectionOrphanCheck = eventos.getEditaisCollection();
            for (Editais editaisCollectionOrphanCheckEditais : editaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Editais " + editaisCollectionOrphanCheckEditais + " in its editaisCollection field has a non-nullable eventos field.");
            }
            Collection<Crachas> crachasCollectionOrphanCheck = eventos.getCrachasCollection();
            for (Crachas crachasCollectionOrphanCheckCrachas : crachasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Crachas " + crachasCollectionOrphanCheckCrachas + " in its crachasCollection field has a non-nullable eventos field.");
            }
            Collection<Participantes> participantesCollectionOrphanCheck = eventos.getParticipantesCollection();
            for (Participantes participantesCollectionOrphanCheckParticipantes : participantesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Participantes " + participantesCollectionOrphanCheckParticipantes + " in its participantesCollection field has a non-nullable eventos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Status status = eventos.getStatus();
            if (status != null) {
                status.getEventosCollection().remove(eventos);
                status = em.merge(status);
            }
            Collection<Midias> midiasCollection = eventos.getMidiasCollection();
            for (Midias midiasCollectionMidias : midiasCollection) {
                midiasCollectionMidias.setEventos(null);
                midiasCollectionMidias = em.merge(midiasCollectionMidias);
            }
            em.remove(eventos);
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

    public List<Eventos> findEventosEntities() {
        return findEventosEntities(true, -1, -1);
    }

    public List<Eventos> findEventosEntities(int maxResults, int firstResult) {
        return findEventosEntities(false, maxResults, firstResult);
    }

    private List<Eventos> findEventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Eventos.class));
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

    public Eventos findEventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Eventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Eventos> rt = cq.from(Eventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
