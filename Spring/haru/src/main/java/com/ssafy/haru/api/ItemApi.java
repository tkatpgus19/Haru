package com.ssafy.haru.api;

import com.ssafy.haru.model.dto.Item;
import com.ssafy.haru.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@CrossOrigin("*")
@Slf4j
public class ItemApi {
    @Autowired
    ItemService itemService;

    // 전체 아이템 리스트 조회
    @GetMapping("/selectAll")
    public ResponseEntity<List<Item>> selectAll(){
        List<Item> result = itemService.selectAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
