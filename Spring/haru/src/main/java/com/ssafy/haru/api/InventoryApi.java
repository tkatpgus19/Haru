package com.ssafy.haru.api;

import com.ssafy.haru.model.dto.InventoryItem;
import com.ssafy.haru.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin("*")
@Slf4j
public class InventoryApi {

    @Autowired
    InventoryService inventoryService;

    // 인벤토리 조회
    @GetMapping("/select")
    public ResponseEntity<List<InventoryItem>> select(@RequestParam String userId){
        List<InventoryItem> result = inventoryService.select(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 인벤토리 아이템 추가
    @GetMapping("/insert")
    public ResponseEntity<Boolean> insert(@RequestParam String userId, @RequestParam String itemId){
        boolean result = inventoryService.insert(userId, itemId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
