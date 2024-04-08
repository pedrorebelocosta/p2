package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories", schema = "bikes_app")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @OneToMany(mappedBy = "categoryEntity")
    private Set<ProductEntity> productEntities = new LinkedHashSet<>();

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

    public Set<ProductEntity> getProducts() {
        return productEntities;
    }

    public void setProducts(Set<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

}