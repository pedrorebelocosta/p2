package org.nolhtaced.core.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "repair_services", schema = "bikes_app")
public class RepairServiceEntity {
    @EmbeddedId
    private RepairServiceIdEntity id;

    @MapsId("repairId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "repair_id", nullable = false)
    private RepairEntity repairEntity;

    @MapsId("serviceId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity serviceEntity;

    @Column(name = "quantity", nullable = false)
    private Float quantity;

    public RepairServiceIdEntity getId() {
        return id;
    }

    public void setId(RepairServiceIdEntity id) {
        this.id = id;
    }

    public RepairEntity getRepair() {
        return repairEntity;
    }

    public void setRepair(RepairEntity repairEntity) {
        this.repairEntity = repairEntity;
    }

    public ServiceEntity getService() {
        return serviceEntity;
    }

    public void setService(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

}