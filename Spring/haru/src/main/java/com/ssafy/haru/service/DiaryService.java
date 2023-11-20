package com.ssafy.haru.service;

import com.ssafy.haru.model.dto.Diary;

public interface DiaryService {
    // 일기 조회
    Diary select(String userId, String diaryDate);

    // 일기 저장
    boolean insert(Diary diary);

    // 일기 수정
    boolean update(Diary diary);

    // 일기 삭제
    boolean delete(String userId, String diaryDate);
}
