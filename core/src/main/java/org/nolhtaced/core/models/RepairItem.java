package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.SellableTypeEnum;
import org.nolhtaced.core.types.Identifiable;

public class RepairItem<T extends Identifiable<Integer>> implements IRepairItem {
    private Integer id;
    private Float quantity;
    private SellableTypeEnum type;

    public RepairItem(T item, Float quantity) {
        this.id = item.getId();
        this.quantity = quantity;
    }

    public RepairItem(T item, Float quantity, SellableTypeEnum type) {
        this.id = item.getId();
        this.quantity = quantity;
        this.type = type;
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

    @Override
    public SellableTypeEnum getType() {
        return type;
    }

    @Override
    public void setType(SellableTypeEnum type) {
        this.type = type;
    }
}
