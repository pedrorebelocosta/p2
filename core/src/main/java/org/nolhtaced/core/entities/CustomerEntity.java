package org.nolhtaced.core.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "customers", schema = "bikes_app")
public class CustomerEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    // https://stackoverflow.com/questions/25090486/java-spring-hibernate-not-updating-entities
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "discount_rate", nullable = false)
    private Float discountRate;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "vat_no")
    private Integer vatNo;

    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.MERGE)
    private Set<CustomerBicycleEntity> customerBicycles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customerEntity")
    private Set<TransactionEntity> transactions = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Float getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Float discountRate) {
        this.discountRate = discountRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVatNo() {
        return vatNo;
    }

    public void setVatNo(Integer vatNo) {
        this.vatNo = vatNo;
    }

    public Set<CustomerBicycleEntity> getCustomerBicycles() {
        return customerBicycles;
    }

    public Set<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", userEntity=" + userEntity +
                ", discountRate=" + discountRate +
                ", address='" + address + '\'' +
                ", vatNo=" + vatNo +
                ", customerBicycles=" + customerBicycles +
                ", transactions=" + transactions +
                '}';
    }
}