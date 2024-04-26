package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "appointments", schema = "bikes_app")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "type", length = 2)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private UserEntity requester;

    @Column(name = "customer_notes", length = Integer.MAX_VALUE)
    private String customerNotes;

    @Column(name = "employee_notes", length = Integer.MAX_VALUE)
    private String employeeNotes;

    @Column(name = "state", length = Integer.MAX_VALUE)
    private String state;

    @Column(name = "schedule_date", nullable = false)
    private Instant scheduleDate;

    public Instant getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Instant scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public UserEntity getRequester() {
        return requester;
    }

    public void setRequester(UserEntity requester) {
        this.requester = requester;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}