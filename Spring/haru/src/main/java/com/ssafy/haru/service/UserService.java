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

    // 비밀번호 일치여부 조회
    User matchPassword(String userId, String userPassword);

    // 사용자 하트 수 수정
    boolean updateHeart(String userId, String userHeart);

    // 사용자 프로필 수정
    boolean updateImage(String userId, String userImg);

    // 사용자 프로필 조회
    User getImage(String userId);
}
