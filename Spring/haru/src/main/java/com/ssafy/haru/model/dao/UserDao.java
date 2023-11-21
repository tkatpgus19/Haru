package com.ssafy.haru.model.dao;

import com.ssafy.haru.model.dto.User;

import java.util.Map;

public interface UserDao {

    // 정보 추가
    boolean insert(User user);

    // 아이디로 정보 조회
    User selectById(String userId);

    // 이메일로 정보 조회
    String selectByEmail(String userEmail);

    // 정보 삭제
    Boolean delete(String userId);

    // 정보 수정
    Boolean update(User user);

    // 비밀번호 일치 조회
    User selectByPassword(Map<String, String> map);
}
