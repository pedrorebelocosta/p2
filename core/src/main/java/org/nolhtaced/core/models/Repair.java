package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.RepairStateEnum;
import org.nolhtaced.core.types.Identifiable;

import java.util.List;

public class Repair extends Identifiable<Integer> {
    private Integer bicycleId;
    private RepairStateEnum state;
    private String notes;
    private Integer assignedEmployeeId;
    private List<RepairItem<Product>> productsUsed;
    private List<RepairItem<Service>> servicesUsed;

    public Repair() {
    }

    public Repair(Integer bicycleId, RepairStateEnum state, String notes, Integer assignedEmployeeId, List<RepairItem<Product>> productsUsed, List<RepairItem<Service>> servicesUsed) {
        this.bicycleId = bicycleId;
        this.state = state;
        this.notes = notes;
        this.assignedEmployeeId = assignedEmployeeId;
        this.productsUsed = productsUsed;
        this.servicesUsed = servicesUsed;
    }

    public Repair(Integer id, Integer bicycleId, RepairStateEnum state, String notes,Integer assignedEmployeeId, List<RepairItem<Product>> productsUsed, List<RepairItem<Service>> servicesUsed) {
        super(id);
        this.bicycleId = bicycleId;
        this.state = state;
        this.notes = notes;
        this.assignedEmployeeId = assignedEmployeeId;
        this.productsUsed = productsUsed;
        this.servicesUsed = servicesUsed;
    }

    public Integer getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(Integer bicycleId) {
        this.bicycleId = bicycleId;
    }

    public RepairStateEnum getState() {
        return state;
    }

    public void setState(RepairStateEnum state) {
        this.state = state;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getAssignedEmployeeId() {
        return assignedEmployeeId;
    }

    public void setAssignedEmployeeId(Integer assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }

    public List<RepairItem<Product>> getProductsUsed() {
        return productsUsed;
    }

    public void setProductsUsed(List<RepairItem<Product>> productsUsed) {
        this.productsUsed = productsUsed;
    }

    public List<RepairItem<Service>> getServicesUsed() {
        return servicesUsed;
    }

    public void setServicesUsed(List<RepairItem<Service>> servicesUsed) {
        this.servicesUsed = servicesUsed;
    }
}
