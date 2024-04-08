package org.nolhtaced.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RepairServiceIdEntity implements Serializable {
    private static final long serialVersionUID = 1809469844143104174L;
    @Column(name = "repair_id", nullable = false)
    private Integer repairId;

    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RepairServiceIdEntity entity = (RepairServiceIdEntity) o;
        return Objects.equals(this.repairId, entity.repairId) &&
                Objects.equals(this.serviceId, entity.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repairId, serviceId);
    }

}