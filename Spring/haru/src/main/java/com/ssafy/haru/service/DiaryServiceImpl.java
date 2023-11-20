package com.ssafy.haru.service;

import com.ssafy.haru.model.dao.DiaryDao;
import com.ssafy.haru.model.dto.Diary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiaryDao diaryDao;

    @Override
    public Diary select(String userId, String diaryDate) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("diaryDate", diaryDate);
        return diaryDao.select(map);
    }

    @Override
    public boolean insert(Diary diary) {
        return diaryDao.insert(diary);
    }

    @Override
    public boolean update(Diary diary) {
        return diaryDao.update(diary);
    }

    @Override
    public boolean delete(String userId, String diaryDate) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("diaryDate", diaryDate);
        return diaryDao.delete(map);
    }


}
