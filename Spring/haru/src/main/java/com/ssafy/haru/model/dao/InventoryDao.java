package com.ssafy.haru.model.dao;

import com.ssafy.haru.model.dto.InventoryItem;

import java.util.List;
import java.util.Map;

public interface InventoryDao {
    // 인벤토리 조회
    List<InventoryItem> select(String userId);

    // 인벤토리에 아이템 추가
    boolean insert(Map<String, String> map);
}
