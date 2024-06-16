package org.nolhtaced.core.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.nolhtaced.core.providers.PersistenceProvider;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoImpl<E, K extends Serializable> implements Dao<E, K> {
    private final Class<E> type;

    public DaoImpl(Class<E> type) {
        this.type = type;
    }

    @Override
    public Optional<E> get(K id) {
        EntityManager em = PersistenceProvider.getEntityManager();
        try {
            return Optional.ofNullable(em.find(type, id));
        } finally {
            PersistenceProvider.closeEntityManager();
        }
    }

    @Override
    public Optional<E> getByUniqueAttribute(String attributeName, String value) {
        EntityManager em = PersistenceProvider.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(type);
            Root<E> rootEntry = cq.from(type);
            cq.where(cb.equal(rootEntry.get(attributeName), value));
            CriteriaQuery<E> record = cq.select(rootEntry);
            TypedQuery<E> query = em.createQuery(record);

            try {
                E result = query.getSingleResult();
                return Optional.ofNullable(result);
            } catch (NoResultException e) {
                Logger.getAnonymousLogger().log(Level.INFO, "No result found for getByUniqueAttribute: " + e.getMessage());
                return Optional.empty();
            }
        } finally {
            PersistenceProvider.closeEntityManager();
        }
    }

    @Override
    public List<E> getAll() {
        EntityManager em = PersistenceProvider.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(type);
            Root<E> rootEntry = cq.from(type);
            CriteriaQuery<E> all = cq.select(rootEntry);
            TypedQuery<E> allQuery = em.createQuery(all);
            return allQuery.getResultList();
        } finally {
            PersistenceProvider.closeEntityManager();
        }
    }

    @Override
    public void save(E entity) {
        EntityManager em = PersistenceProvider.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error occurred in save: " + e.getMessage());
            throw e;
        } finally {
            PersistenceProvider.closeEntityManager();
        }
    }

    @Override
    public E update(E entity) {
        EntityManager em = PersistenceProvider.getEntityManager();
        try {
            em.getTransaction().begin();
            E obj = em.merge(entity);
            em.getTransaction().commit();
            return obj;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error occurred in update: " + e.getMessage());
            throw e;
        } finally {
            PersistenceProvider.closeEntityManager();
        }
    }

    @Override
    public void delete(E entity) throws PersistenceException {
        EntityManager em = PersistenceProvider.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error occurred in delete: " + e.getMessage());
            throw e;
        } finally {
            PersistenceProvider.closeEntityManager();
        }
    }
}
