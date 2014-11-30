/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.controller;

import br.ufms.facom.progweb.syscontato.controller.exceptions.IllegalOrphanException;
import br.ufms.facom.progweb.syscontato.controller.exceptions.NonexistentEntityException;
import br.ufms.facom.progweb.syscontato.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.ufms.facom.progweb.syscontato.model.Contato;
import br.ufms.facom.progweb.syscontato.model.Operadora;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author joshua
 */
public class OperadoraJpaController implements Serializable {

    public OperadoraJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operadora operadora) throws RollbackFailureException, Exception {
        if (operadora.getContatoList() == null) {
            operadora.setContatoList(new ArrayList<Contato>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Contato> attachedContatoList = new ArrayList<Contato>();
            for (Contato contatoListContatoToAttach : operadora.getContatoList()) {
                contatoListContatoToAttach = em.getReference(contatoListContatoToAttach.getClass(), contatoListContatoToAttach.getIdContato());
                attachedContatoList.add(contatoListContatoToAttach);
            }
            operadora.setContatoList(attachedContatoList);
            em.persist(operadora);
            for (Contato contatoListContato : operadora.getContatoList()) {
                Operadora oldIdOperadoraOfContatoListContato = contatoListContato.getOperadora();
                contatoListContato.setOperadora(operadora);
                contatoListContato = em.merge(contatoListContato);
                if (oldIdOperadoraOfContatoListContato != null) {
                    oldIdOperadoraOfContatoListContato.getContatoList().remove(contatoListContato);
                    oldIdOperadoraOfContatoListContato = em.merge(oldIdOperadoraOfContatoListContato);
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

    public void edit(Operadora operadora) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Operadora persistentOperadora = em.find(Operadora.class, operadora.getIdOperadora());
            List<Contato> contatoListOld = persistentOperadora.getContatoList();
            List<Contato> contatoListNew = operadora.getContatoList();
            List<String> illegalOrphanMessages = null;
            for (Contato contatoListOldContato : contatoListOld) {
                if (!contatoListNew.contains(contatoListOldContato)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contato " + contatoListOldContato + " since its idOperadora field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Contato> attachedContatoListNew = new ArrayList<Contato>();
            for (Contato contatoListNewContatoToAttach : contatoListNew) {
                contatoListNewContatoToAttach = em.getReference(contatoListNewContatoToAttach.getClass(), contatoListNewContatoToAttach.getIdContato());
                attachedContatoListNew.add(contatoListNewContatoToAttach);
            }
            contatoListNew = attachedContatoListNew;
            operadora.setContatoList(contatoListNew);
            operadora = em.merge(operadora);
            for (Contato contatoListNewContato : contatoListNew) {
                if (!contatoListOld.contains(contatoListNewContato)) {
                    Operadora oldIdOperadoraOfContatoListNewContato = contatoListNewContato.getOperadora();
                    contatoListNewContato.setOperadora(operadora);
                    contatoListNewContato = em.merge(contatoListNewContato);
                    if (oldIdOperadoraOfContatoListNewContato != null && !oldIdOperadoraOfContatoListNewContato.equals(operadora)) {
                        oldIdOperadoraOfContatoListNewContato.getContatoList().remove(contatoListNewContato);
                        oldIdOperadoraOfContatoListNewContato = em.merge(oldIdOperadoraOfContatoListNewContato);
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
                Integer id = operadora.getIdOperadora();
                if (findOperadora(id) == null) {
                    throw new NonexistentEntityException("The operadora with id " + id + " no longer exists.");
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
            Operadora operadora;
            try {
                operadora = em.getReference(Operadora.class, id);
                operadora.getIdOperadora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operadora with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Contato> contatoListOrphanCheck = operadora.getContatoList();
            for (Contato contatoListOrphanCheckContato : contatoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Operadora (" + operadora + ") cannot be destroyed since the Contato " + contatoListOrphanCheckContato + " in its contatoList field has a non-nullable idOperadora field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(operadora);
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

    public List<Operadora> findOperadoraEntities() {
        return findOperadoraEntities(true, -1, -1);
    }

    public List<Operadora> findOperadoraEntities(int maxResults, int firstResult) {
        return findOperadoraEntities(false, maxResults, firstResult);
    }

    private List<Operadora> findOperadoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operadora.class));
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

    public Operadora findOperadora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operadora.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperadoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operadora> rt = cq.from(Operadora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
