/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.controller;

import br.ufms.facom.progweb.syscontato.controller.exceptions.IllegalOrphanException;
import br.ufms.facom.progweb.syscontato.controller.exceptions.NonexistentEntityException;
import br.ufms.facom.progweb.syscontato.controller.exceptions.RollbackFailureException;
import br.ufms.facom.progweb.syscontato.model.Cidade;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.ufms.facom.progweb.syscontato.model.Estado;
import br.ufms.facom.progweb.syscontato.model.Contato;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author joshua
 */
public class CidadeJpaController implements Serializable {

    public CidadeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cidade cidade) throws RollbackFailureException, Exception {
        if (cidade.getContatoSet() == null) {
            cidade.setContatoSet(new HashSet<Contato>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estado idEstado = cidade.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                cidade.setIdEstado(idEstado);
            }
            Set<Contato> attachedContatoSet = new HashSet<Contato>();
            for (Contato contatoSetContatoToAttach : cidade.getContatoSet()) {
                contatoSetContatoToAttach = em.getReference(contatoSetContatoToAttach.getClass(), contatoSetContatoToAttach.getIdContato());
                attachedContatoSet.add(contatoSetContatoToAttach);
            }
            cidade.setContatoSet(attachedContatoSet);
            em.persist(cidade);
            if (idEstado != null) {
                idEstado.getCidadeSet().add(cidade);
                idEstado = em.merge(idEstado);
            }
            for (Contato contatoSetContato : cidade.getContatoSet()) {
                Cidade oldIdCidadeOfContatoSetContato = contatoSetContato.getIdCidade();
                contatoSetContato.setIdCidade(cidade);
                contatoSetContato = em.merge(contatoSetContato);
                if (oldIdCidadeOfContatoSetContato != null) {
                    oldIdCidadeOfContatoSetContato.getContatoSet().remove(contatoSetContato);
                    oldIdCidadeOfContatoSetContato = em.merge(oldIdCidadeOfContatoSetContato);
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

    public void edit(Cidade cidade) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cidade persistentCidade = em.find(Cidade.class, cidade.getIdCidade());
            Estado idEstadoOld = persistentCidade.getIdEstado();
            Estado idEstadoNew = cidade.getIdEstado();
            Set<Contato> contatoSetOld = persistentCidade.getContatoSet();
            Set<Contato> contatoSetNew = cidade.getContatoSet();
            List<String> illegalOrphanMessages = null;
            for (Contato contatoSetOldContato : contatoSetOld) {
                if (!contatoSetNew.contains(contatoSetOldContato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contato " + contatoSetOldContato + " since its idCidade field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                cidade.setIdEstado(idEstadoNew);
            }
            Set<Contato> attachedContatoSetNew = new HashSet<Contato>();
            for (Contato contatoSetNewContatoToAttach : contatoSetNew) {
                contatoSetNewContatoToAttach = em.getReference(contatoSetNewContatoToAttach.getClass(), contatoSetNewContatoToAttach.getIdContato());
                attachedContatoSetNew.add(contatoSetNewContatoToAttach);
            }
            contatoSetNew = attachedContatoSetNew;
            cidade.setContatoSet(contatoSetNew);
            cidade = em.merge(cidade);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getCidadeSet().remove(cidade);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getCidadeSet().add(cidade);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (Contato contatoSetNewContato : contatoSetNew) {
                if (!contatoSetOld.contains(contatoSetNewContato)) {
                    Cidade oldIdCidadeOfContatoSetNewContato = contatoSetNewContato.getIdCidade();
                    contatoSetNewContato.setIdCidade(cidade);
                    contatoSetNewContato = em.merge(contatoSetNewContato);
                    if (oldIdCidadeOfContatoSetNewContato != null && !oldIdCidadeOfContatoSetNewContato.equals(cidade)) {
                        oldIdCidadeOfContatoSetNewContato.getContatoSet().remove(contatoSetNewContato);
                        oldIdCidadeOfContatoSetNewContato = em.merge(oldIdCidadeOfContatoSetNewContato);
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
                Integer id = cidade.getIdCidade();
                if (findCidade(id) == null) {
                    throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.");
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
            Cidade cidade;
            try {
                cidade = em.getReference(Cidade.class, id);
                cidade.getIdCidade();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Contato> contatoSetOrphanCheck = cidade.getContatoSet();
            for (Contato contatoSetOrphanCheckContato : contatoSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cidade (" + cidade + ") cannot be destroyed since the Contato " + contatoSetOrphanCheckContato + " in its contatoSet field has a non-nullable idCidade field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estado idEstado = cidade.getIdEstado();
            if (idEstado != null) {
                idEstado.getCidadeSet().remove(cidade);
                idEstado = em.merge(idEstado);
            }
            em.remove(cidade);
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

    public List<Cidade> findCidadeEntities() {
        return findCidadeEntities(true, -1, -1);
    }

    public List<Cidade> findCidadeEntities(int maxResults, int firstResult) {
        return findCidadeEntities(false, maxResults, firstResult);
    }

    private List<Cidade> findCidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cidade.class));
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

    public Cidade findCidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getCidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cidade> rt = cq.from(Cidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
