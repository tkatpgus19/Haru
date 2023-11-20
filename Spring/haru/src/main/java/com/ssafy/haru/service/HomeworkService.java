package com.ssafy.haru.service;

import com.ssafy.haru.model.dto.Homework;

public interface HomeworkService {
    // 숙제 조회
    Homework select(String userId, String homeworkDate);

    // 숙제 저장
    boolean insert(Homework homework);

    // 숙제 수정
    boolean update(Homework homework);

    // 숙제 삭제
    boolean delete(String userId, String homeworkDate);
}
