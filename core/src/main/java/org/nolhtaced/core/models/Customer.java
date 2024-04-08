package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.UserRoleEnum;

import java.time.LocalDate;
import java.util.List;

public class Customer extends User {
    private Float discountRate;
    private String address;
    private Integer vatNo;
    private List<Bicycle> bicycles;
    private List<Transaction> transactions;

    public Customer() {
    }

    public Customer(String username, String password, String firstName, String lastName, LocalDate dateOfBirth, Boolean active, Float discountRate, String address, Integer vatNo, List<Bicycle> bicycles, List<Transaction> transactions) {
        super(username, password, firstName, lastName, dateOfBirth, active, UserRoleEnum.CUSTOMER);
        this.discountRate = discountRate;
        this.address = address;
        this.vatNo = vatNo;
        this.bicycles = bicycles;
        this.transactions = transactions;
    }

    public Customer(Integer id, String username, String password, String firstName, String lastName, LocalDate dateOfBirth, Boolean active, Float discountRate, String address, Integer vatNo, List<Bicycle> bicycles, List<Transaction> transactions) {
        super(id, username, password, firstName, lastName, dateOfBirth, active, UserRoleEnum.CUSTOMER);
        this.discountRate = discountRate;
        this.address = address;
        this.vatNo = vatNo;
        this.bicycles = bicycles;
        this.transactions = transactions;
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

    public List<Bicycle> getBicycles() {
        return bicycles;
    }

    public void setBicycles(List<Bicycle> bicycles) {
        this.bicycles = bicycles;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Customer{" +
                super.toString() +
                ", discountRate=" + discountRate +
                ", address='" + address + '\'' +
                ", vatNo=" + vatNo +
                ", bicycles=" + bicycles +
                '}';
    }
}
