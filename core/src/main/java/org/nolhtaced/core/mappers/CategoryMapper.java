package org.nolhtaced.core.mappers;

import org.nolhtaced.core.entities.CategoryEntity;
import org.nolhtaced.core.models.Category;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class CategoryMapper {
    public static Converter<CategoryEntity, Category> entityToDomain = new Converter<CategoryEntity, Category>() {
        @Override
        public Category convert(MappingContext<CategoryEntity, Category> mappingContext) {
            CategoryEntity categoryEntity = mappingContext.getSource();
            Category category = new Category();

            category.setId(categoryEntity.getId());
            category.setName(categoryEntity.getName());
            category.setTitle(categoryEntity.getTitle());

            return category;
        }
    };

    public static Converter<Category, CategoryEntity> domainToEntity = new Converter<Category, CategoryEntity>() {
        @Override
        public CategoryEntity convert(MappingContext<Category, CategoryEntity> mappingContext) {
            Category category = mappingContext.getSource();
            CategoryEntity categoryEntity = new CategoryEntity();

            categoryEntity.setId(category.getId());
            categoryEntity.setName(category.getName());
            categoryEntity.setTitle(category.getTitle());

            return categoryEntity;
        }
    };
}
