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
import model.Usuarios;
import model.InscricaoPartSubeve;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.PresencasSub;
import model.InscricaoPartEvento;
import model.PresencasEve;
import model.Certificados;
import model.ParticipanteEquipe;
import model.Crachas;
import model.Participantes;

/**
 *
 * @author henrique
 */
public class ParticipantesJpaController implements Serializable {

    public ParticipantesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Participantes participantes) throws RollbackFailureException, Exception {
        if (participantes.getInscricaoPartSubeveCollection() == null) {
            participantes.setInscricaoPartSubeveCollection(new ArrayList<InscricaoPartSubeve>());
        }
        if (participantes.getPresencasSubCollection() == null) {
            participantes.setPresencasSubCollection(new ArrayList<PresencasSub>());
        }
        if (participantes.getInscricaoPartEventoCollection() == null) {
            participantes.setInscricaoPartEventoCollection(new ArrayList<InscricaoPartEvento>());
        }
        if (participantes.getPresencasEveCollection() == null) {
            participantes.setPresencasEveCollection(new ArrayList<PresencasEve>());
        }
        if (participantes.getCertificadosCollection() == null) {
            participantes.setCertificadosCollection(new ArrayList<Certificados>());
        }
        if (participantes.getParticipanteEquipeCollection() == null) {
            participantes.setParticipanteEquipeCollection(new ArrayList<ParticipanteEquipe>());
        }
        if (participantes.getCrachasCollection() == null) {
            participantes.setCrachasCollection(new ArrayList<Crachas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eventos eventos = participantes.getEventos();
            if (eventos != null) {
                eventos = em.getReference(eventos.getClass(), eventos.getIdevento());
                participantes.setEventos(eventos);
            }
            Usuarios usuarios = participantes.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getIdusuario());
                participantes.setUsuarios(usuarios);
            }
            Collection<InscricaoPartSubeve> attachedInscricaoPartSubeveCollection = new ArrayList<InscricaoPartSubeve>();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach : participantes.getInscricaoPartSubeveCollection()) {
                inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach = em.getReference(inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach.getClass(), inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach.getIdinscricao());
                attachedInscricaoPartSubeveCollection.add(inscricaoPartSubeveCollectionInscricaoPartSubeveToAttach);
            }
            participantes.setInscricaoPartSubeveCollection(attachedInscricaoPartSubeveCollection);
            Collection<PresencasSub> attachedPresencasSubCollection = new ArrayList<PresencasSub>();
            for (PresencasSub presencasSubCollectionPresencasSubToAttach : participantes.getPresencasSubCollection()) {
                presencasSubCollectionPresencasSubToAttach = em.getReference(presencasSubCollectionPresencasSubToAttach.getClass(), presencasSubCollectionPresencasSubToAttach.getPresencasSubPK());
                attachedPresencasSubCollection.add(presencasSubCollectionPresencasSubToAttach);
            }
            participantes.setPresencasSubCollection(attachedPresencasSubCollection);
            Collection<InscricaoPartEvento> attachedInscricaoPartEventoCollection = new ArrayList<InscricaoPartEvento>();
            for (InscricaoPartEvento inscricaoPartEventoCollectionInscricaoPartEventoToAttach : participantes.getInscricaoPartEventoCollection()) {
                inscricaoPartEventoCollectionInscricaoPartEventoToAttach = em.getReference(inscricaoPartEventoCollectionInscricaoPartEventoToAttach.getClass(), inscricaoPartEventoCollectionInscricaoPartEventoToAttach.getIdinscricao());
                attachedInscricaoPartEventoCollection.add(inscricaoPartEventoCollectionInscricaoPartEventoToAttach);
            }
            participantes.setInscricaoPartEventoCollection(attachedInscricaoPartEventoCollection);
            Collection<PresencasEve> attachedPresencasEveCollection = new ArrayList<PresencasEve>();
            for (PresencasEve presencasEveCollectionPresencasEveToAttach : participantes.getPresencasEveCollection()) {
                presencasEveCollectionPresencasEveToAttach = em.getReference(presencasEveCollectionPresencasEveToAttach.getClass(), presencasEveCollectionPresencasEveToAttach.getPresencasEvePK());
                attachedPresencasEveCollection.add(presencasEveCollectionPresencasEveToAttach);
            }
            participantes.setPresencasEveCollection(attachedPresencasEveCollection);
            Collection<Certificados> attachedCertificadosCollection = new ArrayList<Certificados>();
            for (Certificados certificadosCollectionCertificadosToAttach : participantes.getCertificadosCollection()) {
                certificadosCollectionCertificadosToAttach = em.getReference(certificadosCollectionCertificadosToAttach.getClass(), certificadosCollectionCertificadosToAttach.getIdcertificado());
                attachedCertificadosCollection.add(certificadosCollectionCertificadosToAttach);
            }
            participantes.setCertificadosCollection(attachedCertificadosCollection);
            Collection<ParticipanteEquipe> attachedParticipanteEquipeCollection = new ArrayList<ParticipanteEquipe>();
            for (ParticipanteEquipe participanteEquipeCollectionParticipanteEquipeToAttach : participantes.getParticipanteEquipeCollection()) {
                participanteEquipeCollectionParticipanteEquipeToAttach = em.getReference(participanteEquipeCollectionParticipanteEquipeToAttach.getClass(), participanteEquipeCollectionParticipanteEquipeToAttach.getIdpartequipe());
                attachedParticipanteEquipeCollection.add(participanteEquipeCollectionParticipanteEquipeToAttach);
            }
            participantes.setParticipanteEquipeCollection(attachedParticipanteEquipeCollection);
            Collection<Crachas> attachedCrachasCollection = new ArrayList<Crachas>();
            for (Crachas crachasCollectionCrachasToAttach : participantes.getCrachasCollection()) {
                crachasCollectionCrachasToAttach = em.getReference(crachasCollectionCrachasToAttach.getClass(), crachasCollectionCrachasToAttach.getIdcracha());
                attachedCrachasCollection.add(crachasCollectionCrachasToAttach);
            }
            participantes.setCrachasCollection(attachedCrachasCollection);
            em.persist(participantes);
            if (eventos != null) {
                eventos.getParticipantesCollection().add(participantes);
                eventos = em.merge(eventos);
            }
            if (usuarios != null) {
                usuarios.getParticipantesCollection().add(participantes);
                usuarios = em.merge(usuarios);
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionInscricaoPartSubeve : participantes.getInscricaoPartSubeveCollection()) {
                Participantes oldParticipantesOfInscricaoPartSubeveCollectionInscricaoPartSubeve = inscricaoPartSubeveCollectionInscricaoPartSubeve.getParticipantes();
                inscricaoPartSubeveCollectionInscricaoPartSubeve.setParticipantes(participantes);
                inscricaoPartSubeveCollectionInscricaoPartSubeve = em.merge(inscricaoPartSubeveCollectionInscricaoPartSubeve);
                if (oldParticipantesOfInscricaoPartSubeveCollectionInscricaoPartSubeve != null) {
                    oldParticipantesOfInscricaoPartSubeveCollectionInscricaoPartSubeve.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeveCollectionInscricaoPartSubeve);
                    oldParticipantesOfInscricaoPartSubeveCollectionInscricaoPartSubeve = em.merge(oldParticipantesOfInscricaoPartSubeveCollectionInscricaoPartSubeve);
                }
            }
            for (PresencasSub presencasSubCollectionPresencasSub : participantes.getPresencasSubCollection()) {
                Participantes oldParticipantesOfPresencasSubCollectionPresencasSub = presencasSubCollectionPresencasSub.getParticipantes();
                presencasSubCollectionPresencasSub.setParticipantes(participantes);
                presencasSubCollectionPresencasSub = em.merge(presencasSubCollectionPresencasSub);
                if (oldParticipantesOfPresencasSubCollectionPresencasSub != null) {
                    oldParticipantesOfPresencasSubCollectionPresencasSub.getPresencasSubCollection().remove(presencasSubCollectionPresencasSub);
                    oldParticipantesOfPresencasSubCollectionPresencasSub = em.merge(oldParticipantesOfPresencasSubCollectionPresencasSub);
                }
            }
            for (InscricaoPartEvento inscricaoPartEventoCollectionInscricaoPartEvento : participantes.getInscricaoPartEventoCollection()) {
                Participantes oldParticipantesOfInscricaoPartEventoCollectionInscricaoPartEvento = inscricaoPartEventoCollectionInscricaoPartEvento.getParticipantes();
                inscricaoPartEventoCollectionInscricaoPartEvento.setParticipantes(participantes);
                inscricaoPartEventoCollectionInscricaoPartEvento = em.merge(inscricaoPartEventoCollectionInscricaoPartEvento);
                if (oldParticipantesOfInscricaoPartEventoCollectionInscricaoPartEvento != null) {
                    oldParticipantesOfInscricaoPartEventoCollectionInscricaoPartEvento.getInscricaoPartEventoCollection().remove(inscricaoPartEventoCollectionInscricaoPartEvento);
                    oldParticipantesOfInscricaoPartEventoCollectionInscricaoPartEvento = em.merge(oldParticipantesOfInscricaoPartEventoCollectionInscricaoPartEvento);
                }
            }
            for (PresencasEve presencasEveCollectionPresencasEve : participantes.getPresencasEveCollection()) {
                Participantes oldParticipantesOfPresencasEveCollectionPresencasEve = presencasEveCollectionPresencasEve.getParticipantes();
                presencasEveCollectionPresencasEve.setParticipantes(participantes);
                presencasEveCollectionPresencasEve = em.merge(presencasEveCollectionPresencasEve);
                if (oldParticipantesOfPresencasEveCollectionPresencasEve != null) {
                    oldParticipantesOfPresencasEveCollectionPresencasEve.getPresencasEveCollection().remove(presencasEveCollectionPresencasEve);
                    oldParticipantesOfPresencasEveCollectionPresencasEve = em.merge(oldParticipantesOfPresencasEveCollectionPresencasEve);
                }
            }
            for (Certificados certificadosCollectionCertificados : participantes.getCertificadosCollection()) {
                Participantes oldParticipantesOfCertificadosCollectionCertificados = certificadosCollectionCertificados.getParticipantes();
                certificadosCollectionCertificados.setParticipantes(participantes);
                certificadosCollectionCertificados = em.merge(certificadosCollectionCertificados);
                if (oldParticipantesOfCertificadosCollectionCertificados != null) {
                    oldParticipantesOfCertificadosCollectionCertificados.getCertificadosCollection().remove(certificadosCollectionCertificados);
                    oldParticipantesOfCertificadosCollectionCertificados = em.merge(oldParticipantesOfCertificadosCollectionCertificados);
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionParticipanteEquipe : participantes.getParticipanteEquipeCollection()) {
                Participantes oldParticipantesOfParticipanteEquipeCollectionParticipanteEquipe = participanteEquipeCollectionParticipanteEquipe.getParticipantes();
                participanteEquipeCollectionParticipanteEquipe.setParticipantes(participantes);
                participanteEquipeCollectionParticipanteEquipe = em.merge(participanteEquipeCollectionParticipanteEquipe);
                if (oldParticipantesOfParticipanteEquipeCollectionParticipanteEquipe != null) {
                    oldParticipantesOfParticipanteEquipeCollectionParticipanteEquipe.getParticipanteEquipeCollection().remove(participanteEquipeCollectionParticipanteEquipe);
                    oldParticipantesOfParticipanteEquipeCollectionParticipanteEquipe = em.merge(oldParticipantesOfParticipanteEquipeCollectionParticipanteEquipe);
                }
            }
            for (Crachas crachasCollectionCrachas : participantes.getCrachasCollection()) {
                Participantes oldParticipantesOfCrachasCollectionCrachas = crachasCollectionCrachas.getParticipantes();
                crachasCollectionCrachas.setParticipantes(participantes);
                crachasCollectionCrachas = em.merge(crachasCollectionCrachas);
                if (oldParticipantesOfCrachasCollectionCrachas != null) {
                    oldParticipantesOfCrachasCollectionCrachas.getCrachasCollection().remove(crachasCollectionCrachas);
                    oldParticipantesOfCrachasCollectionCrachas = em.merge(oldParticipantesOfCrachasCollectionCrachas);
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

    public void edit(Participantes participantes) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Participantes persistentParticipantes = em.find(Participantes.class, participantes.getIdparticipante());
            Eventos eventosOld = persistentParticipantes.getEventos();
            Eventos eventosNew = participantes.getEventos();
            Usuarios usuariosOld = persistentParticipantes.getUsuarios();
            Usuarios usuariosNew = participantes.getUsuarios();
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionOld = persistentParticipantes.getInscricaoPartSubeveCollection();
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionNew = participantes.getInscricaoPartSubeveCollection();
            Collection<PresencasSub> presencasSubCollectionOld = persistentParticipantes.getPresencasSubCollection();
            Collection<PresencasSub> presencasSubCollectionNew = participantes.getPresencasSubCollection();
            Collection<InscricaoPartEvento> inscricaoPartEventoCollectionOld = persistentParticipantes.getInscricaoPartEventoCollection();
            Collection<InscricaoPartEvento> inscricaoPartEventoCollectionNew = participantes.getInscricaoPartEventoCollection();
            Collection<PresencasEve> presencasEveCollectionOld = persistentParticipantes.getPresencasEveCollection();
            Collection<PresencasEve> presencasEveCollectionNew = participantes.getPresencasEveCollection();
            Collection<Certificados> certificadosCollectionOld = persistentParticipantes.getCertificadosCollection();
            Collection<Certificados> certificadosCollectionNew = participantes.getCertificadosCollection();
            Collection<ParticipanteEquipe> participanteEquipeCollectionOld = persistentParticipantes.getParticipanteEquipeCollection();
            Collection<ParticipanteEquipe> participanteEquipeCollectionNew = participantes.getParticipanteEquipeCollection();
            Collection<Crachas> crachasCollectionOld = persistentParticipantes.getCrachasCollection();
            Collection<Crachas> crachasCollectionNew = participantes.getCrachasCollection();
            List<String> illegalOrphanMessages = null;
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionOldInscricaoPartSubeve : inscricaoPartSubeveCollectionOld) {
                if (!inscricaoPartSubeveCollectionNew.contains(inscricaoPartSubeveCollectionOldInscricaoPartSubeve)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoPartSubeve " + inscricaoPartSubeveCollectionOldInscricaoPartSubeve + " since its participantes field is not nullable.");
                }
            }
            for (PresencasSub presencasSubCollectionOldPresencasSub : presencasSubCollectionOld) {
                if (!presencasSubCollectionNew.contains(presencasSubCollectionOldPresencasSub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencasSub " + presencasSubCollectionOldPresencasSub + " since its participantes field is not nullable.");
                }
            }
            for (InscricaoPartEvento inscricaoPartEventoCollectionOldInscricaoPartEvento : inscricaoPartEventoCollectionOld) {
                if (!inscricaoPartEventoCollectionNew.contains(inscricaoPartEventoCollectionOldInscricaoPartEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscricaoPartEvento " + inscricaoPartEventoCollectionOldInscricaoPartEvento + " since its participantes field is not nullable.");
                }
            }
            for (PresencasEve presencasEveCollectionOldPresencasEve : presencasEveCollectionOld) {
                if (!presencasEveCollectionNew.contains(presencasEveCollectionOldPresencasEve)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencasEve " + presencasEveCollectionOldPresencasEve + " since its participantes field is not nullable.");
                }
            }
            for (Certificados certificadosCollectionOldCertificados : certificadosCollectionOld) {
                if (!certificadosCollectionNew.contains(certificadosCollectionOldCertificados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Certificados " + certificadosCollectionOldCertificados + " since its participantes field is not nullable.");
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionOldParticipanteEquipe : participanteEquipeCollectionOld) {
                if (!participanteEquipeCollectionNew.contains(participanteEquipeCollectionOldParticipanteEquipe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParticipanteEquipe " + participanteEquipeCollectionOldParticipanteEquipe + " since its participantes field is not nullable.");
                }
            }
            for (Crachas crachasCollectionOldCrachas : crachasCollectionOld) {
                if (!crachasCollectionNew.contains(crachasCollectionOldCrachas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Crachas " + crachasCollectionOldCrachas + " since its participantes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (eventosNew != null) {
                eventosNew = em.getReference(eventosNew.getClass(), eventosNew.getIdevento());
                participantes.setEventos(eventosNew);
            }
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getIdusuario());
                participantes.setUsuarios(usuariosNew);
            }
            Collection<InscricaoPartSubeve> attachedInscricaoPartSubeveCollectionNew = new ArrayList<InscricaoPartSubeve>();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach : inscricaoPartSubeveCollectionNew) {
                inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach = em.getReference(inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach.getClass(), inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach.getIdinscricao());
                attachedInscricaoPartSubeveCollectionNew.add(inscricaoPartSubeveCollectionNewInscricaoPartSubeveToAttach);
            }
            inscricaoPartSubeveCollectionNew = attachedInscricaoPartSubeveCollectionNew;
            participantes.setInscricaoPartSubeveCollection(inscricaoPartSubeveCollectionNew);
            Collection<PresencasSub> attachedPresencasSubCollectionNew = new ArrayList<PresencasSub>();
            for (PresencasSub presencasSubCollectionNewPresencasSubToAttach : presencasSubCollectionNew) {
                presencasSubCollectionNewPresencasSubToAttach = em.getReference(presencasSubCollectionNewPresencasSubToAttach.getClass(), presencasSubCollectionNewPresencasSubToAttach.getPresencasSubPK());
                attachedPresencasSubCollectionNew.add(presencasSubCollectionNewPresencasSubToAttach);
            }
            presencasSubCollectionNew = attachedPresencasSubCollectionNew;
            participantes.setPresencasSubCollection(presencasSubCollectionNew);
            Collection<InscricaoPartEvento> attachedInscricaoPartEventoCollectionNew = new ArrayList<InscricaoPartEvento>();
            for (InscricaoPartEvento inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach : inscricaoPartEventoCollectionNew) {
                inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach = em.getReference(inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach.getClass(), inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach.getIdinscricao());
                attachedInscricaoPartEventoCollectionNew.add(inscricaoPartEventoCollectionNewInscricaoPartEventoToAttach);
            }
            inscricaoPartEventoCollectionNew = attachedInscricaoPartEventoCollectionNew;
            participantes.setInscricaoPartEventoCollection(inscricaoPartEventoCollectionNew);
            Collection<PresencasEve> attachedPresencasEveCollectionNew = new ArrayList<PresencasEve>();
            for (PresencasEve presencasEveCollectionNewPresencasEveToAttach : presencasEveCollectionNew) {
                presencasEveCollectionNewPresencasEveToAttach = em.getReference(presencasEveCollectionNewPresencasEveToAttach.getClass(), presencasEveCollectionNewPresencasEveToAttach.getPresencasEvePK());
                attachedPresencasEveCollectionNew.add(presencasEveCollectionNewPresencasEveToAttach);
            }
            presencasEveCollectionNew = attachedPresencasEveCollectionNew;
            participantes.setPresencasEveCollection(presencasEveCollectionNew);
            Collection<Certificados> attachedCertificadosCollectionNew = new ArrayList<Certificados>();
            for (Certificados certificadosCollectionNewCertificadosToAttach : certificadosCollectionNew) {
                certificadosCollectionNewCertificadosToAttach = em.getReference(certificadosCollectionNewCertificadosToAttach.getClass(), certificadosCollectionNewCertificadosToAttach.getIdcertificado());
                attachedCertificadosCollectionNew.add(certificadosCollectionNewCertificadosToAttach);
            }
            certificadosCollectionNew = attachedCertificadosCollectionNew;
            participantes.setCertificadosCollection(certificadosCollectionNew);
            Collection<ParticipanteEquipe> attachedParticipanteEquipeCollectionNew = new ArrayList<ParticipanteEquipe>();
            for (ParticipanteEquipe participanteEquipeCollectionNewParticipanteEquipeToAttach : participanteEquipeCollectionNew) {
                participanteEquipeCollectionNewParticipanteEquipeToAttach = em.getReference(participanteEquipeCollectionNewParticipanteEquipeToAttach.getClass(), participanteEquipeCollectionNewParticipanteEquipeToAttach.getIdpartequipe());
                attachedParticipanteEquipeCollectionNew.add(participanteEquipeCollectionNewParticipanteEquipeToAttach);
            }
            participanteEquipeCollectionNew = attachedParticipanteEquipeCollectionNew;
            participantes.setParticipanteEquipeCollection(participanteEquipeCollectionNew);
            Collection<Crachas> attachedCrachasCollectionNew = new ArrayList<Crachas>();
            for (Crachas crachasCollectionNewCrachasToAttach : crachasCollectionNew) {
                crachasCollectionNewCrachasToAttach = em.getReference(crachasCollectionNewCrachasToAttach.getClass(), crachasCollectionNewCrachasToAttach.getIdcracha());
                attachedCrachasCollectionNew.add(crachasCollectionNewCrachasToAttach);
            }
            crachasCollectionNew = attachedCrachasCollectionNew;
            participantes.setCrachasCollection(crachasCollectionNew);
            participantes = em.merge(participantes);
            if (eventosOld != null && !eventosOld.equals(eventosNew)) {
                eventosOld.getParticipantesCollection().remove(participantes);
                eventosOld = em.merge(eventosOld);
            }
            if (eventosNew != null && !eventosNew.equals(eventosOld)) {
                eventosNew.getParticipantesCollection().add(participantes);
                eventosNew = em.merge(eventosNew);
            }
            if (usuariosOld != null && !usuariosOld.equals(usuariosNew)) {
                usuariosOld.getParticipantesCollection().remove(participantes);
                usuariosOld = em.merge(usuariosOld);
            }
            if (usuariosNew != null && !usuariosNew.equals(usuariosOld)) {
                usuariosNew.getParticipantesCollection().add(participantes);
                usuariosNew = em.merge(usuariosNew);
            }
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionNewInscricaoPartSubeve : inscricaoPartSubeveCollectionNew) {
                if (!inscricaoPartSubeveCollectionOld.contains(inscricaoPartSubeveCollectionNewInscricaoPartSubeve)) {
                    Participantes oldParticipantesOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve = inscricaoPartSubeveCollectionNewInscricaoPartSubeve.getParticipantes();
                    inscricaoPartSubeveCollectionNewInscricaoPartSubeve.setParticipantes(participantes);
                    inscricaoPartSubeveCollectionNewInscricaoPartSubeve = em.merge(inscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                    if (oldParticipantesOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve != null && !oldParticipantesOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve.equals(participantes)) {
                        oldParticipantesOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve.getInscricaoPartSubeveCollection().remove(inscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                        oldParticipantesOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve = em.merge(oldParticipantesOfInscricaoPartSubeveCollectionNewInscricaoPartSubeve);
                    }
                }
            }
            for (PresencasSub presencasSubCollectionNewPresencasSub : presencasSubCollectionNew) {
                if (!presencasSubCollectionOld.contains(presencasSubCollectionNewPresencasSub)) {
                    Participantes oldParticipantesOfPresencasSubCollectionNewPresencasSub = presencasSubCollectionNewPresencasSub.getParticipantes();
                    presencasSubCollectionNewPresencasSub.setParticipantes(participantes);
                    presencasSubCollectionNewPresencasSub = em.merge(presencasSubCollectionNewPresencasSub);
                    if (oldParticipantesOfPresencasSubCollectionNewPresencasSub != null && !oldParticipantesOfPresencasSubCollectionNewPresencasSub.equals(participantes)) {
                        oldParticipantesOfPresencasSubCollectionNewPresencasSub.getPresencasSubCollection().remove(presencasSubCollectionNewPresencasSub);
                        oldParticipantesOfPresencasSubCollectionNewPresencasSub = em.merge(oldParticipantesOfPresencasSubCollectionNewPresencasSub);
                    }
                }
            }
            for (InscricaoPartEvento inscricaoPartEventoCollectionNewInscricaoPartEvento : inscricaoPartEventoCollectionNew) {
                if (!inscricaoPartEventoCollectionOld.contains(inscricaoPartEventoCollectionNewInscricaoPartEvento)) {
                    Participantes oldParticipantesOfInscricaoPartEventoCollectionNewInscricaoPartEvento = inscricaoPartEventoCollectionNewInscricaoPartEvento.getParticipantes();
                    inscricaoPartEventoCollectionNewInscricaoPartEvento.setParticipantes(participantes);
                    inscricaoPartEventoCollectionNewInscricaoPartEvento = em.merge(inscricaoPartEventoCollectionNewInscricaoPartEvento);
                    if (oldParticipantesOfInscricaoPartEventoCollectionNewInscricaoPartEvento != null && !oldParticipantesOfInscricaoPartEventoCollectionNewInscricaoPartEvento.equals(participantes)) {
                        oldParticipantesOfInscricaoPartEventoCollectionNewInscricaoPartEvento.getInscricaoPartEventoCollection().remove(inscricaoPartEventoCollectionNewInscricaoPartEvento);
                        oldParticipantesOfInscricaoPartEventoCollectionNewInscricaoPartEvento = em.merge(oldParticipantesOfInscricaoPartEventoCollectionNewInscricaoPartEvento);
                    }
                }
            }
            for (PresencasEve presencasEveCollectionNewPresencasEve : presencasEveCollectionNew) {
                if (!presencasEveCollectionOld.contains(presencasEveCollectionNewPresencasEve)) {
                    Participantes oldParticipantesOfPresencasEveCollectionNewPresencasEve = presencasEveCollectionNewPresencasEve.getParticipantes();
                    presencasEveCollectionNewPresencasEve.setParticipantes(participantes);
                    presencasEveCollectionNewPresencasEve = em.merge(presencasEveCollectionNewPresencasEve);
                    if (oldParticipantesOfPresencasEveCollectionNewPresencasEve != null && !oldParticipantesOfPresencasEveCollectionNewPresencasEve.equals(participantes)) {
                        oldParticipantesOfPresencasEveCollectionNewPresencasEve.getPresencasEveCollection().remove(presencasEveCollectionNewPresencasEve);
                        oldParticipantesOfPresencasEveCollectionNewPresencasEve = em.merge(oldParticipantesOfPresencasEveCollectionNewPresencasEve);
                    }
                }
            }
            for (Certificados certificadosCollectionNewCertificados : certificadosCollectionNew) {
                if (!certificadosCollectionOld.contains(certificadosCollectionNewCertificados)) {
                    Participantes oldParticipantesOfCertificadosCollectionNewCertificados = certificadosCollectionNewCertificados.getParticipantes();
                    certificadosCollectionNewCertificados.setParticipantes(participantes);
                    certificadosCollectionNewCertificados = em.merge(certificadosCollectionNewCertificados);
                    if (oldParticipantesOfCertificadosCollectionNewCertificados != null && !oldParticipantesOfCertificadosCollectionNewCertificados.equals(participantes)) {
                        oldParticipantesOfCertificadosCollectionNewCertificados.getCertificadosCollection().remove(certificadosCollectionNewCertificados);
                        oldParticipantesOfCertificadosCollectionNewCertificados = em.merge(oldParticipantesOfCertificadosCollectionNewCertificados);
                    }
                }
            }
            for (ParticipanteEquipe participanteEquipeCollectionNewParticipanteEquipe : participanteEquipeCollectionNew) {
                if (!participanteEquipeCollectionOld.contains(participanteEquipeCollectionNewParticipanteEquipe)) {
                    Participantes oldParticipantesOfParticipanteEquipeCollectionNewParticipanteEquipe = participanteEquipeCollectionNewParticipanteEquipe.getParticipantes();
                    participanteEquipeCollectionNewParticipanteEquipe.setParticipantes(participantes);
                    participanteEquipeCollectionNewParticipanteEquipe = em.merge(participanteEquipeCollectionNewParticipanteEquipe);
                    if (oldParticipantesOfParticipanteEquipeCollectionNewParticipanteEquipe != null && !oldParticipantesOfParticipanteEquipeCollectionNewParticipanteEquipe.equals(participantes)) {
                        oldParticipantesOfParticipanteEquipeCollectionNewParticipanteEquipe.getParticipanteEquipeCollection().remove(participanteEquipeCollectionNewParticipanteEquipe);
                        oldParticipantesOfParticipanteEquipeCollectionNewParticipanteEquipe = em.merge(oldParticipantesOfParticipanteEquipeCollectionNewParticipanteEquipe);
                    }
                }
            }
            for (Crachas crachasCollectionNewCrachas : crachasCollectionNew) {
                if (!crachasCollectionOld.contains(crachasCollectionNewCrachas)) {
                    Participantes oldParticipantesOfCrachasCollectionNewCrachas = crachasCollectionNewCrachas.getParticipantes();
                    crachasCollectionNewCrachas.setParticipantes(participantes);
                    crachasCollectionNewCrachas = em.merge(crachasCollectionNewCrachas);
                    if (oldParticipantesOfCrachasCollectionNewCrachas != null && !oldParticipantesOfCrachasCollectionNewCrachas.equals(participantes)) {
                        oldParticipantesOfCrachasCollectionNewCrachas.getCrachasCollection().remove(crachasCollectionNewCrachas);
                        oldParticipantesOfCrachasCollectionNewCrachas = em.merge(oldParticipantesOfCrachasCollectionNewCrachas);
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
                Integer id = participantes.getIdparticipante();
                if (findParticipantes(id) == null) {
                    throw new NonexistentEntityException("The participantes with id " + id + " no longer exists.");
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
            Participantes participantes;
            try {
                participantes = em.getReference(Participantes.class, id);
                participantes.getIdparticipante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participantes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<InscricaoPartSubeve> inscricaoPartSubeveCollectionOrphanCheck = participantes.getInscricaoPartSubeveCollection();
            for (InscricaoPartSubeve inscricaoPartSubeveCollectionOrphanCheckInscricaoPartSubeve : inscricaoPartSubeveCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participantes (" + participantes + ") cannot be destroyed since the InscricaoPartSubeve " + inscricaoPartSubeveCollectionOrphanCheckInscricaoPartSubeve + " in its inscricaoPartSubeveCollection field has a non-nullable participantes field.");
            }
            Collection<PresencasSub> presencasSubCollectionOrphanCheck = participantes.getPresencasSubCollection();
            for (PresencasSub presencasSubCollectionOrphanCheckPresencasSub : presencasSubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participantes (" + participantes + ") cannot be destroyed since the PresencasSub " + presencasSubCollectionOrphanCheckPresencasSub + " in its presencasSubCollection field has a non-nullable participantes field.");
            }
            Collection<InscricaoPartEvento> inscricaoPartEventoCollectionOrphanCheck = participantes.getInscricaoPartEventoCollection();
            for (InscricaoPartEvento inscricaoPartEventoCollectionOrphanCheckInscricaoPartEvento : inscricaoPartEventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participantes (" + participantes + ") cannot be destroyed since the InscricaoPartEvento " + inscricaoPartEventoCollectionOrphanCheckInscricaoPartEvento + " in its inscricaoPartEventoCollection field has a non-nullable participantes field.");
            }
            Collection<PresencasEve> presencasEveCollectionOrphanCheck = participantes.getPresencasEveCollection();
            for (PresencasEve presencasEveCollectionOrphanCheckPresencasEve : presencasEveCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participantes (" + participantes + ") cannot be destroyed since the PresencasEve " + presencasEveCollectionOrphanCheckPresencasEve + " in its presencasEveCollection field has a non-nullable participantes field.");
            }
            Collection<Certificados> certificadosCollectionOrphanCheck = participantes.getCertificadosCollection();
            for (Certificados certificadosCollectionOrphanCheckCertificados : certificadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participantes (" + participantes + ") cannot be destroyed since the Certificados " + certificadosCollectionOrphanCheckCertificados + " in its certificadosCollection field has a non-nullable participantes field.");
            }
            Collection<ParticipanteEquipe> participanteEquipeCollectionOrphanCheck = participantes.getParticipanteEquipeCollection();
            for (ParticipanteEquipe participanteEquipeCollectionOrphanCheckParticipanteEquipe : participanteEquipeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participantes (" + participantes + ") cannot be destroyed since the ParticipanteEquipe " + participanteEquipeCollectionOrphanCheckParticipanteEquipe + " in its participanteEquipeCollection field has a non-nullable participantes field.");
            }
            Collection<Crachas> crachasCollectionOrphanCheck = participantes.getCrachasCollection();
            for (Crachas crachasCollectionOrphanCheckCrachas : crachasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participantes (" + participantes + ") cannot be destroyed since the Crachas " + crachasCollectionOrphanCheckCrachas + " in its crachasCollection field has a non-nullable participantes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Eventos eventos = participantes.getEventos();
            if (eventos != null) {
                eventos.getParticipantesCollection().remove(participantes);
                eventos = em.merge(eventos);
            }
            Usuarios usuarios = participantes.getUsuarios();
            if (usuarios != null) {
                usuarios.getParticipantesCollection().remove(participantes);
                usuarios = em.merge(usuarios);
            }
            em.remove(participantes);
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

    public List<Participantes> findParticipantesEntities() {
        return findParticipantesEntities(true, -1, -1);
    }

    public List<Participantes> findParticipantesEntities(int maxResults, int firstResult) {
        return findParticipantesEntities(false, maxResults, firstResult);
    }

    private List<Participantes> findParticipantesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Participantes.class));
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

    public Participantes findParticipantes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Participantes.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipantesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Participantes> rt = cq.from(Participantes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
