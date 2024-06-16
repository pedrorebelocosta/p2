package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products", schema = "bikes_app")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "price", nullable = false)
    private Float price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryEntity;

    @Column(name = "available_units", nullable = false)
    private Integer availableUnits;

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "productEntity")
    private Set<RepairProductEntity> repairProductEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productEntity")
    private Set<TransactionProductEntity> transactionProductEntities = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public CategoryEntity getCategory() {
        return categoryEntity;
    }

    public void setCategory(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<RepairProductEntity> getRepairProducts() {
        return repairProductEntities;
    }

    public void setRepairProducts(Set<RepairProductEntity> repairProductEntities) {
        this.repairProductEntities = repairProductEntities;
    }

    public Set<TransactionProductEntity> getTransactionProducts() {
        return transactionProductEntities;
    }

    public void setTransactionProducts(Set<TransactionProductEntity> transactionProductEntities) {
        this.transactionProductEntities = transactionProductEntities;
    }

}