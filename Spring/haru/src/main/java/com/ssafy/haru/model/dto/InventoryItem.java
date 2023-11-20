package com.ssafy.haru.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItem {
    private String userId;
    private int itemId;
    private String itemName;
    private String itemType;

    @Override
    public String toString() {
        return "InventoryItem{" +
                "userId='" + userId + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                '}';
    }
}
