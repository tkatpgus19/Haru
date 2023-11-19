package com.ssafy.haru.service;

import com.ssafy.haru.model.dto.Homework;

public interface HomeworkService {
    // 숙제 조회
    Homework selectHomework(String userId, String homeworkDate);

    // 숙제 저장
    boolean saveHomework(Homework homework);

    // 숙제 수정
    boolean updateHomework(Homework homework);

    // 숙제 삭제
    boolean deleteHomework(String userId, String homeworkDate);
}
