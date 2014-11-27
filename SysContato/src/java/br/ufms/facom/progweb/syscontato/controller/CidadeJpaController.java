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
        if (cidade.getContatoList() == null) {
            cidade.setContatoList(new ArrayList<Contato>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estado idEstado = cidade.getEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                cidade.setEstado(idEstado);
            }
            List<Contato> attachedContatoList = new ArrayList<Contato>();
            for (Contato contatoListContatoToAttach : cidade.getContatoList()) {
                contatoListContatoToAttach = em.getReference(contatoListContatoToAttach.getClass(), contatoListContatoToAttach.getIdContato());
                attachedContatoList.add(contatoListContatoToAttach);
            }
            cidade.setContatoList(attachedContatoList);
            em.persist(cidade);
            if (idEstado != null) {
                idEstado.getCidadeList().add(cidade);
                idEstado = em.merge(idEstado);
            }
            for (Contato contatoListContato : cidade.getContatoList()) {
                Cidade oldIdCidadeOfContatoListContato = contatoListContato.getCidade();
                contatoListContato.setCidade(cidade);
                contatoListContato = em.merge(contatoListContato);
                if (oldIdCidadeOfContatoListContato != null) {
                    oldIdCidadeOfContatoListContato.getContatoList().remove(contatoListContato);
                    oldIdCidadeOfContatoListContato = em.merge(oldIdCidadeOfContatoListContato);
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
            Estado idEstadoOld = persistentCidade.getEstado();
            Estado idEstadoNew = cidade.getEstado();
            List<Contato> contatoListOld = persistentCidade.getContatoList();
            List<Contato> contatoListNew = cidade.getContatoList();
            List<String> illegalOrphanMessages = null;
            for (Contato contatoListOldContato : contatoListOld) {
                if (!contatoListNew.contains(contatoListOldContato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contato " + contatoListOldContato + " since its idCidade field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                cidade.setEstado(idEstadoNew);
            }
            List<Contato> attachedContatoListNew = new ArrayList<Contato>();
            for (Contato contatoListNewContatoToAttach : contatoListNew) {
                contatoListNewContatoToAttach = em.getReference(contatoListNewContatoToAttach.getClass(), contatoListNewContatoToAttach.getIdContato());
                attachedContatoListNew.add(contatoListNewContatoToAttach);
            }
            contatoListNew = attachedContatoListNew;
            cidade.setContatoList(contatoListNew);
            cidade = em.merge(cidade);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getCidadeList().remove(cidade);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getCidadeList().add(cidade);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (Contato contatoListNewContato : contatoListNew) {
                if (!contatoListOld.contains(contatoListNewContato)) {
                    Cidade oldIdCidadeOfContatoListNewContato = contatoListNewContato.getCidade();
                    contatoListNewContato.setCidade(cidade);
                    contatoListNewContato = em.merge(contatoListNewContato);
                    if (oldIdCidadeOfContatoListNewContato != null && !oldIdCidadeOfContatoListNewContato.equals(cidade)) {
                        oldIdCidadeOfContatoListNewContato.getContatoList().remove(contatoListNewContato);
                        oldIdCidadeOfContatoListNewContato = em.merge(oldIdCidadeOfContatoListNewContato);
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
            List<Contato> contatoListOrphanCheck = cidade.getContatoList();
            for (Contato contatoListOrphanCheckContato : contatoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cidade (" + cidade + ") cannot be destroyed since the Contato " + contatoListOrphanCheckContato + " in its contatoList field has a non-nullable idCidade field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estado idEstado = cidade.getEstado();
            if (idEstado != null) {
                idEstado.getCidadeList().remove(cidade);
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
