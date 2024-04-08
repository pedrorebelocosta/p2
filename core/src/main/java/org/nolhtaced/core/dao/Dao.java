package org.nolhtaced.core.dao;

import java.io.Serializable;
import java.util.Optional;
import java.util.List;

public interface Dao<E, K extends Serializable> {
    Optional<E> get(K id);
    Optional<E> getByUniqueAttribute(String attributeName, String value);
    List<E> getAll();
    void save(E entity);
    E update(E entity);
    void delete(E entity);
}
