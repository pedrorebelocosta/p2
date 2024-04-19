package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.RepairStateEnum;
import org.nolhtaced.core.types.Identifiable;

import java.util.List;
import java.util.Objects;

public class Repair extends Identifiable<Integer> {
    private Integer bicycleId;
    private RepairStateEnum state;
    private String notes;
    private Integer assignedEmployeeId;
    private List<IRepairItem> sellablesUsed;

    public Repair() {
    }

    public Repair(Integer bicycleId, RepairStateEnum state, String notes, Integer assignedEmployeeId, List<IRepairItem> sellablesUsed) {
        this.bicycleId = bicycleId;
        this.state = state;
        this.notes = notes;
        this.assignedEmployeeId = assignedEmployeeId;
        this.sellablesUsed = sellablesUsed;
    }

    public Repair(Integer id, Integer bicycleId, RepairStateEnum state, String notes, Integer assignedEmployeeId, List<IRepairItem> sellablesUsed) {
        super(id);
        this.bicycleId = bicycleId;
        this.state = state;
        this.notes = notes;
        this.assignedEmployeeId = assignedEmployeeId;
        this.sellablesUsed = sellablesUsed;
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

    public List<IRepairItem> getSellablesUsed() {
        return sellablesUsed;
    }

    public void setSellablesUsed(List<IRepairItem> sellablesUsed) {
        this.sellablesUsed = sellablesUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repair repair = (Repair) o;
        return Objects.equals(bicycleId, repair.bicycleId) && state == repair.state && Objects.equals(notes, repair.notes) && Objects.equals(assignedEmployeeId, repair.assignedEmployeeId) && Objects.equals(sellablesUsed, repair.sellablesUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bicycleId, state, notes, assignedEmployeeId, sellablesUsed);
    }
}
