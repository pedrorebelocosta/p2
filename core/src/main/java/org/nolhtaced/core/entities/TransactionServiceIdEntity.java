package org.nolhtaced.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TransactionServiceIdEntity implements Serializable {
    private static final long serialVersionUID = -3817160997513593160L;
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;

    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
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
        TransactionServiceIdEntity entity = (TransactionServiceIdEntity) o;
        return Objects.equals(this.serviceId, entity.serviceId) &&
                Objects.equals(this.transactionId, entity.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, transactionId);
    }

}