package com.ssafy.haru.service;

import com.ssafy.haru.model.dao.InventoryDao;
import com.ssafy.haru.model.dto.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryDao inventoryDao;

    @Override
    public List<InventoryItem> select(String userId) {
        return inventoryDao.select(userId);
    }

    @Override
    public boolean insert(String userId, String itemId) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("itemId", itemId);
        return inventoryDao.insert(map);
    }
}
