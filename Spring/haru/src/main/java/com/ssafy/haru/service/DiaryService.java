package com.ssafy.haru.service;

import com.ssafy.haru.model.dto.Diary;

public interface DiaryService {
    // 일기 조회
    Diary selectDiary(String userId, String diaryDate);

    // 일기 저장
    boolean saveDiary(Diary diary);

    // 일기 수정
    boolean updateDiary(Diary diary);

    // 일기 삭제
    boolean deleteDiary(String userId, String diaryDate);
}
