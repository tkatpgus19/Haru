package com.ssafy.haru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItem {
    private String userId;
    private int itemId;
    private String itemSource;
    private String itemType;

    @Override
    public String toString() {
        return "InventoryItem{" +
                "userId='" + userId + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemSource + '\'' +
                ", itemType='" + itemType + '\'' +
                '}';
    }
}
