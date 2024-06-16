package org.nolhtaced.core.providers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceProvider {
    private static ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    private static EntityManagerFactory entityManagerFactory;

    private PersistenceProvider() {}

    public static EntityManager getEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();

        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = createEntityManager();
            entityManagerThreadLocal.set(entityManager);
        }

        return entityManager;
    }

    private synchronized static EntityManager createEntityManager() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("bike_shop");
        }
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManagerThreadLocal.remove();
        }
    }

    public synchronized static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }
}
