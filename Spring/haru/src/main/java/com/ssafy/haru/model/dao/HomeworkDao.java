package com.ssafy.haru.model.dao;

import com.ssafy.haru.model.dto.Homework;

import java.util.Map;

public interface HomeworkDao {
    // 아이디, 날짜로 숙제 조회
    Homework select(Map<String, String> map);

    // 숙제 저장
    boolean insert(Homework homework);

    // 숙제 수정
    boolean update(Homework homework);

    // 숙제 삭제
    boolean delete(Map<String, String> map);
}
