package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "customer_bicycles", schema = "bikes_app")
public class CustomerBicycleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerEntity;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "model", nullable = false, length = 256)
    private String model;

    @Column(name = "brand", nullable = false, length = 256)
    private String brand;

    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<RepairEntity> repairEntities = new LinkedHashSet<>();

    @Column(name = "type", nullable = false, length = Integer.MAX_VALUE)
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customerEntity;
    }

    public void setCustomer(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<RepairEntity> getRepairs() {
        return repairEntities;
    }

    public void setRepairs(Set<RepairEntity> repairEntities) {
        this.repairEntities = repairEntities;
    }
}
