package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.TransactionStateEnum;
import org.nolhtaced.core.types.Identifiable;

import java.time.LocalDate;
import java.util.List;

public class Transaction extends Identifiable<Integer> {
    private Integer customerId;
    private Integer employeeId;
    private Float totalAmount;
    private LocalDate createdAt;
    private TransactionStateEnum state;
    private List<ITransactionItem> items;

    public Transaction() {
    }

    public Transaction(Integer customerId, Integer employeeId, Float totalAmount, LocalDate createdAt, TransactionStateEnum state, List<ITransactionItem> items) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.state = state;
        this.items = items;
    }

    public Transaction(Integer id, Integer customerId, Integer employeeId, Float totalAmount, LocalDate createdAt, TransactionStateEnum state, List<ITransactionItem> items) {
        super(id);
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.state = state;
        this.items = items;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public TransactionStateEnum getState() {
        return state;
    }

    public void setState(TransactionStateEnum state) {
        this.state = state;
    }

    public List<ITransactionItem> getItems() {
        return items;
    }

    public void setItems(List<ITransactionItem> items) {
        this.items = items;
    }
}
