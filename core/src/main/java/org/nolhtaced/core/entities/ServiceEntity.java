package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "services", schema = "bikes_app")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "price")
    private Float price;

    @OneToMany(mappedBy = "serviceEntity")
    private Set<RepairServiceEntity> repairServiceEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "serviceEntity")
    private Set<TransactionServiceEntity> transactionServiceEntities = new LinkedHashSet<>();

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

    public Set<RepairServiceEntity> getRepairServices() {
        return repairServiceEntities;
    }

    public void setRepairServices(Set<RepairServiceEntity> repairServiceEntities) {
        this.repairServiceEntities = repairServiceEntities;
    }

    public Set<TransactionServiceEntity> getTransactionServices() {
        return transactionServiceEntities;
    }

    public void setTransactionServices(Set<TransactionServiceEntity> transactionServiceEntities) {
        this.transactionServiceEntities = transactionServiceEntities;
    }

}