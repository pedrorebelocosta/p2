package org.nolhtaced.core.models;

import org.nolhtaced.core.types.Identifiable;

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
}
