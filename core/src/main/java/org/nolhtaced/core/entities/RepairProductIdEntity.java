package org.nolhtaced.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RepairProductIdEntity implements Serializable {
    private static final long serialVersionUID = -7345057831071456475L;
    @Column(name = "repair_id", nullable = false)
    private Integer repairId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RepairProductIdEntity entity = (RepairProductIdEntity) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.repairId, entity.repairId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, repairId);
    }

}