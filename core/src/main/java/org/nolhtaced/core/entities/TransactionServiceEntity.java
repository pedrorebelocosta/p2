package org.nolhtaced.core.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_services", schema = "bikes_app")
public class TransactionServiceEntity {
    @EmbeddedId
    private TransactionServiceIdEntity id;

    @MapsId("transactionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionEntity transaction;

    @MapsId("serviceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity serviceEntity;

    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @Column(name = "price", nullable = false)
    private Float price;

    public TransactionServiceIdEntity getId() {
        return id;
    }

    public void setId(TransactionServiceIdEntity id) {
        this.id = id;
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transaction) {
        this.transaction = transaction;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}