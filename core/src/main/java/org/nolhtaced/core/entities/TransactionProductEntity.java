package org.nolhtaced.core.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_products", schema = "bikes_app")
public class TransactionProductEntity {
    @EmbeddedId
    private TransactionProductIdEntity id;

    @MapsId("transactionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionEntity transaction;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    public TransactionProductIdEntity getId() {
        return id;
    }

    public void setId(TransactionProductIdEntity id) {
        this.id = id;
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transaction) {
        this.transaction = transaction;
    }

    public ProductEntity getProduct() {
        return productEntity;
    }

    public void setProduct(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }
}