package org.nolhtaced.core.mappers;

import org.nolhtaced.core.dao.Dao;
import org.nolhtaced.core.dao.DaoImpl;
import org.nolhtaced.core.entities.CategoryEntity;
import org.nolhtaced.core.entities.ProductEntity;
import org.nolhtaced.core.models.Product;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ProductMapper {
    private static final Dao<CategoryEntity, Integer> categoryDao = new DaoImpl<>(CategoryEntity.class);
    public static Converter<ProductEntity, Product> entityToDomain = new Converter<ProductEntity, Product>() {
        @Override
        public Product convert(MappingContext<ProductEntity, Product> mappingContext) {
            ProductEntity productEntity = mappingContext.getSource();
            Product product = new Product();

            product.setId(productEntity.getId());
            product.setCategoryId(productEntity.getCategory().getId());
            product.setName(productEntity.getName());
            product.setTitle(productEntity.getTitle());
            product.setPrice(productEntity.getPrice());
            product.setAvailableUnits(productEntity.getAvailableUnits());
            product.setImagePath(productEntity.getImagePath());

            return product;
        }
    };

    public static Converter<Product, ProductEntity> domainToEntity = new Converter<Product, ProductEntity>() {
        @Override
        public ProductEntity convert(MappingContext<Product, ProductEntity> mappingContext) {
            Product product = mappingContext.getSource();
            ProductEntity productEntity = new ProductEntity();

            CategoryEntity categoryEntity = categoryDao.get(product.getCategoryId()).orElseThrow();

            productEntity.setId(product.getId());
            productEntity.setCategory(categoryEntity);
            productEntity.setName(product.getName());
            productEntity.setTitle(product.getTitle());
            productEntity.setPrice(product.getPrice());
            productEntity.setAvailableUnits(product.getAvailableUnits());
            productEntity.setImagePath(product.getImagePath());

            return productEntity;
        }
    };
}
