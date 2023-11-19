package com.ssafy.haru.service;

import com.ssafy.haru.model.dto.User;

public interface UserService {
    // 회원 가입
    boolean join(User user);

    // 로그인
    User login(String userId, String userPassword);

    // 아이디 중복 체크
    boolean isUsed(String userId);

    // 아이디 조회
    String findId(String userEmail);

    // 계정 탈퇴
    boolean unregister(String userId);

    // 계정 정보 수정
    boolean update(User user);
}
