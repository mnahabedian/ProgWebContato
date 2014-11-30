/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufms.facom.progweb.syscontato.controller;

import br.ufms.facom.progweb.syscontato.controller.exceptions.NonexistentEntityException;
import br.ufms.facom.progweb.syscontato.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.ufms.facom.progweb.syscontato.model.Operadora;
import br.ufms.facom.progweb.syscontato.model.Cidade;
import br.ufms.facom.progweb.syscontato.model.Contato;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author joshua
 */
public class ContatoJpaController implements Serializable {

    public ContatoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contato contato) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Operadora idOperadora = contato.getOperadora();
            if (idOperadora != null) {
                idOperadora = em.getReference(idOperadora.getClass(), idOperadora.getIdOperadora());
                contato.setOperadora(idOperadora);
            }
            Cidade idCidade = contato.getCidade();
            if (idCidade != null) {
                idCidade = em.getReference(idCidade.getClass(), idCidade.getIdCidade());
                contato.setCidade(idCidade);
            }
            em.persist(contato);
            if (idOperadora != null) {
                idOperadora.getContatoList().add(contato);
                idOperadora = em.merge(idOperadora);
            }
            if (idCidade != null) {
                idCidade.getContatoList().add(contato);
                idCidade = em.merge(idCidade);
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

    public void edit(Contato contato) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contato persistentContato = em.find(Contato.class, contato.getIdContato());
            Operadora idOperadoraOld = persistentContato.getOperadora();
            Operadora idOperadoraNew = contato.getOperadora();
            Cidade idCidadeOld = persistentContato.getCidade();
            Cidade idCidadeNew = contato.getCidade();
            if (idOperadoraNew != null) {
                idOperadoraNew = em.getReference(idOperadoraNew.getClass(), idOperadoraNew.getIdOperadora());
                contato.setOperadora(idOperadoraNew);
            }
            if (idCidadeNew != null) {
                idCidadeNew = em.getReference(idCidadeNew.getClass(), idCidadeNew.getIdCidade());
                contato.setCidade(idCidadeNew);
            }
            contato = em.merge(contato);
            if (idOperadoraOld != null && !idOperadoraOld.equals(idOperadoraNew)) {
                idOperadoraOld.getContatoList().remove(contato);
                idOperadoraOld = em.merge(idOperadoraOld);
            }
            if (idOperadoraNew != null && !idOperadoraNew.equals(idOperadoraOld)) {
                idOperadoraNew.getContatoList().add(contato);
                idOperadoraNew = em.merge(idOperadoraNew);
            }
            if (idCidadeOld != null && !idCidadeOld.equals(idCidadeNew)) {
                idCidadeOld.getContatoList().remove(contato);
                idCidadeOld = em.merge(idCidadeOld);
            }
            if (idCidadeNew != null && !idCidadeNew.equals(idCidadeOld)) {
                idCidadeNew.getContatoList().add(contato);
                idCidadeNew = em.merge(idCidadeNew);
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
                Integer id = contato.getIdContato();
                if (findContato(id) == null) {
                    throw new NonexistentEntityException("The contato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contato contato;
            try {
                contato = em.getReference(Contato.class, id);
                contato.getIdContato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contato with id " + id + " no longer exists.", enfe);
            }
            Operadora idOperadora = contato.getOperadora();
            if (idOperadora != null) {
                idOperadora.getContatoList().remove(contato);
                idOperadora = em.merge(idOperadora);
            }
            Cidade idCidade = contato.getCidade();
            if (idCidade != null) {
                idCidade.getContatoList().remove(contato);
                idCidade = em.merge(idCidade);
            }
            em.remove(contato);
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

    public List<Contato> findContatoEntities() {
        return findContatoEntities(true, -1, -1);
    }

    public List<Contato> findContatoEntities(int maxResults, int firstResult) {
        return findContatoEntities(false, maxResults, firstResult);
    }

    private List<Contato> findContatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contato.class));
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

    public Contato findContato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contato> rt = cq.from(Contato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
