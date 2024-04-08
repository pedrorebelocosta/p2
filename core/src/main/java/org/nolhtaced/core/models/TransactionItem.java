package org.nolhtaced.core.models;

import org.nolhtaced.core.types.Identifiable;

public class TransactionItem<T extends Identifiable<Integer>> {
    private Integer id;
    private Float quantity;
    private Float price;
    private final T item;

    public TransactionItem(T item, Float quantity, Float price) {
        this.id = item.getId();
        this.quantity = quantity;
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public T getItem() {
        return item;
    }
}
