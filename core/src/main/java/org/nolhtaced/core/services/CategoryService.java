package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.nolhtaced.core.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CategoryEntity;
import org.nolhtaced.core.exceptions.CategoryNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryService extends BaseService {
    private final Dao<CategoryEntity, Integer> categoryDao;

    public CategoryService(NolhtacedSession session) {
        super(session);
        this.categoryDao = new DaoImpl<>(CategoryEntity.class);
    }

    public void create(Category category) {
        CategoryEntity categoryEntity = this.mapper.map(category, CategoryEntity.class);
        categoryDao.save(categoryEntity);
    }

    public Category get(Integer id) throws CategoryNotFoundException {
        Optional<CategoryEntity> categoryEntity = categoryDao.get(id);

        if (categoryEntity.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        return mapper.map(categoryEntity.get(), Category.class);
    }

    public List<Category> getAll() {
        return categoryDao.getAll().stream().map(
                category -> mapper.map(category, Category.class)
        ).collect(Collectors.toList());
    }

    public void update(Category category) throws CategoryNotFoundException {
        Optional<CategoryEntity> categoryFromDao = categoryDao.get(category.getId());

        if (categoryFromDao.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        CategoryEntity categoryEntity = this.mapper.map(category, CategoryEntity.class);
        categoryDao.update(categoryEntity);
    }

    public void delete(Integer id) throws CategoryNotFoundException, ObjectStillReferencedException {
        Optional<CategoryEntity> categoryEntity = categoryDao.get(id);

        if (categoryEntity.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        try {
            categoryDao.delete(categoryEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
