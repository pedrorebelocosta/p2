package org.nolhtaced.core.models;

import org.nolhtaced.core.types.Identifiable;

import java.util.Objects;

public class Product extends Identifiable<Integer> {
    private String name;
    private String title;
    private Float price;
    private Integer categoryId;
    private Integer availableUnits;
    private String imagePath;

    public Product() {
    }

    public Product(String name, String title, Float price, Integer categoryId, Integer availableUnits) {
        this.name = name;
        this.title = title;
        this.price = price;
        this.categoryId = categoryId;
        this.availableUnits = availableUnits;
    }

    public Product(String name, String title, Float price, Integer categoryId, Integer availableUnits, String imagePath) {
        this.name = name;
        this.title = title;
        this.price = price;
        this.categoryId = categoryId;
        this.availableUnits = availableUnits;
        this.imagePath = imagePath;
    }

    public Product(Integer id, String name, String title, Float price, Integer categoryId, Integer availableUnits, String imagePath) {
        this.setId(id);
        this.name = name;
        this.title = title;
        this.price = price;
        this.categoryId = categoryId;
        this.availableUnits = availableUnits;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(Integer availableUnits) {
        this.availableUnits = availableUnits;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String image) {
        this.imagePath = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(title, product.title) && Objects.equals(price, product.price) && Objects.equals(categoryId, product.categoryId) && Objects.equals(availableUnits, product.availableUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, title, price, categoryId, availableUnits, imagePath);
    }
}
