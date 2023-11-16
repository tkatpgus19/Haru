package com.example.demo.model.dao;

import com.example.demo.model.dto.User;

public interface UserDao {

    // 정보 추가
    int insert(User user);

    // 정보 조회
    User select(String userId);
}
