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
import br.ufms.facom.progweb.syscontato.model.Cidade;
import br.ufms.facom.progweb.syscontato.model.Estado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author joshua
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws RollbackFailureException, Exception {
        if (estado.getCidadeList() == null) {
            estado.setCidadeList(new ArrayList<Cidade>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Cidade> attachedCidadeList = new ArrayList<Cidade>();
            for (Cidade cidadeListCidadeToAttach : estado.getCidadeList()) {
                cidadeListCidadeToAttach = em.getReference(cidadeListCidadeToAttach.getClass(), cidadeListCidadeToAttach.getIdCidade());
                attachedCidadeList.add(cidadeListCidadeToAttach);
            }
            estado.setCidadeList(attachedCidadeList);
            em.persist(estado);
            for (Cidade cidadeListCidade : estado.getCidadeList()) {
                Estado oldIdEstadoOfCidadeListCidade = cidadeListCidade.getEstado();
                cidadeListCidade.setEstado(estado);
                cidadeListCidade = em.merge(cidadeListCidade);
                if (oldIdEstadoOfCidadeListCidade != null) {
                    oldIdEstadoOfCidadeListCidade.getCidadeList().remove(cidadeListCidade);
                    oldIdEstadoOfCidadeListCidade = em.merge(oldIdEstadoOfCidadeListCidade);
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

    public void edit(Estado estado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estado persistentEstado = em.find(Estado.class, estado.getIdEstado());
            List<Cidade> cidadeListOld = persistentEstado.getCidadeList();
            List<Cidade> cidadeListNew = estado.getCidadeList();
            List<String> illegalOrphanMessages = null;
            for (Cidade cidadeListOldCidade : cidadeListOld) {
                if (!cidadeListNew.contains(cidadeListOldCidade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cidade " + cidadeListOldCidade + " since its idEstado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cidade> attachedCidadeListNew = new ArrayList<Cidade>();
            for (Cidade cidadeListNewCidadeToAttach : cidadeListNew) {
                cidadeListNewCidadeToAttach = em.getReference(cidadeListNewCidadeToAttach.getClass(), cidadeListNewCidadeToAttach.getIdCidade());
                attachedCidadeListNew.add(cidadeListNewCidadeToAttach);
            }
            cidadeListNew = attachedCidadeListNew;
            estado.setCidadeList(cidadeListNew);
            estado = em.merge(estado);
            for (Cidade cidadeListNewCidade : cidadeListNew) {
                if (!cidadeListOld.contains(cidadeListNewCidade)) {
                    Estado oldIdEstadoOfCidadeListNewCidade = cidadeListNewCidade.getEstado();
                    cidadeListNewCidade.setEstado(estado);
                    cidadeListNewCidade = em.merge(cidadeListNewCidade);
                    if (oldIdEstadoOfCidadeListNewCidade != null && !oldIdEstadoOfCidadeListNewCidade.equals(estado)) {
                        oldIdEstadoOfCidadeListNewCidade.getCidadeList().remove(cidadeListNewCidade);
                        oldIdEstadoOfCidadeListNewCidade = em.merge(oldIdEstadoOfCidadeListNewCidade);
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
                Integer id = estado.getIdEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cidade> cidadeListOrphanCheck = estado.getCidadeList();
            for (Cidade cidadeListOrphanCheckCidade : cidadeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Cidade " + cidadeListOrphanCheckCidade + " in its cidadeList field has a non-nullable idEstado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estado);
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

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
