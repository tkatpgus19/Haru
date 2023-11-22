package com.ssafy.haru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private int itemId;
    private String itemType;
    private String itemSource;
    private int itemPrice;

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemType='" + itemType + '\'' +
                ", itemName='" + itemSource + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
