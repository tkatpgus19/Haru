package com.ssafy.haru.service;

import com.ssafy.haru.model.dto.Item;

import java.util.List;

public interface ItemService {

    // 전체 아이템 리스트 조회
    List<Item> selectAll();
}
