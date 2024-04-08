package org.nolhtaced.core.services;

import jakarta.persistence.PersistenceException;
import org.hibernate.ObjectDeletedException;
import org.nolhtaced.core.NolhtacedSession;
import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CategoryEntity;
import org.nolhtaced.core.entities.ProductEntity;
import org.nolhtaced.core.exceptions.CategoryNotFoundException;
import org.nolhtaced.core.exceptions.ObjectStillReferencedException;
import org.nolhtaced.core.exceptions.ProductNotFoundException;
import org.nolhtaced.core.models.Product;
import org.nolhtaced.core.utilities.ImageUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService extends BaseService {
    private final Dao<ProductEntity, Integer> productDao;
    private final Dao<CategoryEntity, Integer> categoryDao;

    public ProductService(NolhtacedSession session) {
        super(session);
        this.productDao = new DaoImpl<>(ProductEntity.class);
        this.categoryDao = new DaoImpl<>(CategoryEntity.class);
    }

    public void create(Product product) throws CategoryNotFoundException {
        ProductEntity productEntity = this.mapper.map(product, ProductEntity.class);
        Optional<CategoryEntity> categoryEntity = categoryDao.get(product.getCategoryId());

        if (categoryEntity.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        productDao.save(productEntity);
    }

    public void addImageToProduct(Integer productId, byte[] imageBytes) throws ProductNotFoundException {
        ProductEntity productEntity = productDao.get(productId).orElseThrow(ProductNotFoundException::new);

        try {
            String imagePath = ImageUtil.saveImage(imageBytes);
            productEntity.setImagePath(imagePath);
            productDao.update(productEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Product get(Integer id) throws ProductNotFoundException {
        Optional<ProductEntity> productEntity = productDao.get(id);

        if (productEntity.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return mapper.map(productEntity.get(), Product.class);
    }

    public Product getByName(String name) throws ProductNotFoundException {
        Optional<ProductEntity> productEntity = productDao.getByUniqueAttribute("name", name);

        if (productEntity.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return mapper.map(productEntity.get(), Product.class);
    }

    public List<Product> getAll() {
        return productDao.getAll().stream().map(
                product -> mapper.map(product, Product.class)
        ).collect(Collectors.toList());
    }

    public void update(Product product) throws ProductNotFoundException, CategoryNotFoundException {
        Optional<ProductEntity> productFromDao = productDao.get(product.getId());
        Optional<CategoryEntity> categoryEntity = categoryDao.get(product.getCategoryId());

        if (productFromDao.isEmpty()) {
            throw new ProductNotFoundException();
        }

        if (categoryEntity.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        ProductEntity productEntity = this.mapper.map(product, ProductEntity.class);
        productDao.update(productEntity);
    }

    public void delete(Integer id) throws ProductNotFoundException, ObjectStillReferencedException {
        Optional<ProductEntity> productEntity = productDao.get(id);

        if (productEntity.isEmpty()) {
            throw new ProductNotFoundException();
        }

        try {
            productDao.delete(productEntity.get());
        } catch (PersistenceException e) {
            throw new ObjectStillReferencedException();
        }
    }
}
