package org.nolhtaced.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TransactionProductIdEntity implements Serializable {
    private static final long serialVersionUID = 5564455325089987229L;
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TransactionProductIdEntity entity = (TransactionProductIdEntity) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.transactionId, entity.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, transactionId);
    }

}