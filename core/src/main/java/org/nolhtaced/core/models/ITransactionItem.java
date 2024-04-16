package org.nolhtaced.core.models;

import org.nolhtaced.core.enumerators.SellableTypeEnum;

public interface ITransactionItem {
    Integer getId();
    void setId(Integer id);
    String getName();
    void setName(String name);
    String getTitle();
    void setTitle(String title);
    Float getQuantity();
    void setQuantity(Float quantity);
    Float getPrice();
    void setPrice(Float price);
    SellableTypeEnum getType();
    void setType(SellableTypeEnum e);
}
