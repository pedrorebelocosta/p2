package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.SellableTypeEnum;

public class TransactionItem implements ITransactionItem {
    private Integer id;
    private String name;
    private String title;
    private Float quantity;
    private Float price;
    private SellableTypeEnum type;

    public TransactionItem() {
    }

    public TransactionItem(Integer id, String name, String title, Float quantity, Float price, SellableTypeEnum type) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public SellableTypeEnum getType() {
        return type;
    }

    @Override
    public void setType(SellableTypeEnum type) {
        this.type = type;
    }
}
