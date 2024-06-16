package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "transactions", schema = "bikes_app")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeEntity;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.MERGE)
    private Set<TransactionProductEntity> transactionProductEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.MERGE)
    private Set<TransactionServiceEntity> transactionServiceEntities = new LinkedHashSet<>();

    @Column(name = "transaction_state", nullable = false, length = Integer.MAX_VALUE)
    private String transactionState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public CustomerEntity getCustomer() {
        return customerEntity;
    }

    public void setCustomer(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public EmployeeEntity getEmployee() {
        return employeeEntity;
    }

    public void setEmployee(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public Set<TransactionProductEntity> getTransactionProducts() {
        return transactionProductEntities;
    }

    public void setTransactionProducts(Set<TransactionProductEntity> transactionProductEntities) {
        this.transactionProductEntities = transactionProductEntities;
    }

    public Set<TransactionServiceEntity> getTransactionServices() {
        return transactionServiceEntities;
    }

    public void setTransactionServices(Set<TransactionServiceEntity> transactionServiceEntities) {
        this.transactionServiceEntities = transactionServiceEntities;
    }

    public String getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(String transactionState) {
        this.transactionState = transactionState;
    }
}