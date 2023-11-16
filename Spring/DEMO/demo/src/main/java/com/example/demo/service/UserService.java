package com.example.demo.service;

import com.example.demo.model.dto.User;

public interface UserService {
    // 회원가입
    boolean join(User user);

    // 로그인
    User login(String userId, String userPassword);
}
