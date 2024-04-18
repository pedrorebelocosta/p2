package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.SellableTypeEnum;

public interface IRepairItem {
    Integer getId();
    void setId(Integer id);
    String getName();
    void setName(String name);
    String getTitle();
    void setTitle(String title);
    Float getQuantity();
    void setQuantity(Float quantity);
    SellableTypeEnum getType();
    void setType(SellableTypeEnum e);
}
