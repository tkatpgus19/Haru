package com.ssafy.haru.model.dao;

import com.ssafy.haru.model.dto.Diary;

import java.util.Map;

public interface DiaryDao {
    // 아이디, 날짜로 작성글 조회
    Diary select(Map<String, String> map);

    // 작성글 저장
    boolean insert(Diary diary);

    // 작성글 수정
    boolean update(Diary diary);

    // 작성글 삭제
    boolean delete(Map<String, String> map);
}
