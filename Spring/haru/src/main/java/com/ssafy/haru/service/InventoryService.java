package com.ssafy.haru.service;

import com.ssafy.haru.model.dto.InventoryItem;

import java.util.List;

public interface InventoryService {

    List<InventoryItem> select(String userId);

    boolean insert(String userId, String itemId);
}
