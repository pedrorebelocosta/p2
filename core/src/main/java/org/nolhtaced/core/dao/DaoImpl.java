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

public class DaoImpl<E, K extends Serializable> implements Dao<E, K>{
    protected final EntityManager em = PersistenceProvider.getEntityManager();
    protected final Class<E> type;

    public DaoImpl(Class<E> type) {
        this.type = type;
    }

    @Override
    public Optional<E> get(K id) {
        return Optional.ofNullable(em.find(type, id));
    }

    @Override
    public Optional<E> getByUniqueAttribute(String attributeName, String value) {
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
            Logger.getAnonymousLogger().log(Level.INFO, "an exception was thrown " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<E> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(type);
        Root<E> rootEntry = cq.from(type);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public void save(E entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public E update(E entity) {
        em.getTransaction().begin();
        E obj = em.merge(entity);
        em.getTransaction().commit();
        return obj;
    }

    @Override
    public void delete(E entity) throws PersistenceException {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }
}
