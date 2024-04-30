package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.AppointmentStateEnum;
import org.nolhtaced.core.enumerators.AppointmentTypeEnum;
import org.nolhtaced.core.types.Identifiable;

import java.time.Instant;
import java.time.LocalDate;

public class Appointment extends Identifiable<Integer> {
    private LocalDate createdAt;
    private Instant scheduleDate;
    private AppointmentTypeEnum type;
    private Integer customerId;
    private Integer requesterId;
    private String customerNotes;
    private String employeeNotes;
    private AppointmentStateEnum state;

    public Appointment() {
    }

    public Appointment(LocalDate createdAt, Instant scheduleDate, AppointmentTypeEnum type, Integer customerId, Integer requesterId, String customerNotes, String employeeNotes, AppointmentStateEnum state) {
        this.createdAt = createdAt;
        this.scheduleDate = scheduleDate;
        this.type = type;
        this.customerId = customerId;
        this.requesterId = requesterId;
        this.customerNotes = customerNotes;
        this.employeeNotes = employeeNotes;
        this.state = state;
    }

    public Appointment(Integer id, LocalDate createdAt, Instant scheduleDate, AppointmentTypeEnum type, Integer customerId, Integer requesterId, String customerNotes, String employeeNotes, AppointmentStateEnum state) {
        super(id);
        this.createdAt = createdAt;
        this.scheduleDate = scheduleDate;
        this.type = type;
        this.customerId = customerId;
        this.requesterId = requesterId;
        this.customerNotes = customerNotes;
        this.employeeNotes = employeeNotes;
        this.state = state;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Instant scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public AppointmentTypeEnum getType() {
        return type;
    }

    public void setType(AppointmentTypeEnum type) {
        this.type = type;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getEmployeeNotes() {
        return employeeNotes;
    }

    public void setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
    }

    public AppointmentStateEnum getState() {
        return state;
    }

    public void setState(AppointmentStateEnum state) {
        this.state = state;
    }
}
