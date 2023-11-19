package com.example.demo.model.dao;

import com.example.demo.model.dto.User;

public interface UserDao {

    // 정보 추가
    int insert(User user);

    // 아이디로 정보 조회
    User selectById(String userId);

    // 이메일로 아이디 조회
    String selectByEmail(String userEmail);

    // 계정 탈퇴
    Boolean delete(String userId);

    // 계정 정보 수정
    Boolean update(User user);
}
