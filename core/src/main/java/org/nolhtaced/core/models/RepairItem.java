package org.nolhtaced.core.models;

import org.nolhtaced.core.types.Identifiable;

public class RepairItem<T extends Identifiable<Integer>> {
    private Integer id;
    private Float quantity;
    private final T item;

    public RepairItem(T item, Float quantity) {
        this.id = item.getId();
        this.quantity = quantity;
        this.item = item;
    }

    public T getItem() {
        return this.item;
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
}
