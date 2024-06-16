package org.nolhtaced.core.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "repair_products", schema = "bikes_app")
public class RepairProductEntity {
    @EmbeddedId
    private RepairProductIdEntity id;

    @MapsId("repairId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "repair_id", nullable = false)
    private RepairEntity repairEntity;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "quantity", nullable = false)
    private Float quantity;

    public RepairProductIdEntity getId() {
        return id;
    }

    public void setId(RepairProductIdEntity id) {
        this.id = id;
    }

    public RepairEntity getRepair() {
        return repairEntity;
    }

    public void setRepair(RepairEntity repairEntity) {
        this.repairEntity = repairEntity;
    }

    public ProductEntity getProduct() {
        return productEntity;
    }

    public void setProduct(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

}