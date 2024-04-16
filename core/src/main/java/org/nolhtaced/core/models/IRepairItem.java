package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.SellableTypeEnum;

public interface IRepairItem {
    Integer getId();
    void setId(Integer id);
    Float getQuantity();
    void setQuantity(Float quantity);
    SellableTypeEnum getType();
    void setType(SellableTypeEnum e);
}
