package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "repairs", schema = "bikes_app")
public class RepairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bicycle_id", nullable = false)
    private CustomerBicycleEntity bicycle;

    @Column(name = "current_state", nullable = false, length = Integer.MAX_VALUE)
    private String currentState;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id")
    private EmployeeEntity assignedEmployeeEntity;

    @OneToMany(mappedBy = "repairEntity", cascade = CascadeType.MERGE)
    private Set<RepairProductEntity> repairProductEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "repairEntity", cascade = CascadeType.MERGE)
    private Set<RepairServiceEntity> repairServiceEntities = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerBicycleEntity getBicycle() {
        return bicycle;
    }

    public void setBicycle(CustomerBicycleEntity bicycle) {
        this.bicycle = bicycle;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public EmployeeEntity getAssignedEmployee() {
        return assignedEmployeeEntity;
    }

    public void setAssignedEmployee(EmployeeEntity assignedEmployeeEntity) {
        this.assignedEmployeeEntity = assignedEmployeeEntity;
    }

    public Set<RepairProductEntity> getRepairProducts() {
        return repairProductEntities;
    }

    public void setRepairProducts(Set<RepairProductEntity> repairProductEntities) {
        this.repairProductEntities = repairProductEntities;
    }

    public Set<RepairServiceEntity> getRepairServices() {
        return repairServiceEntities;
    }

    public void setRepairServices(Set<RepairServiceEntity> repairServiceEntities) {
        this.repairServiceEntities = repairServiceEntities;
    }
}