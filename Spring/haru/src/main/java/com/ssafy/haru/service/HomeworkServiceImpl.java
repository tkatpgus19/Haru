package com.ssafy.haru.service;

import com.ssafy.haru.model.dao.HomeworkDao;
import com.ssafy.haru.model.dto.Homework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HomeworkDao homeworkDao;

    @Override
    public Homework selectHomework(String userId, String homeworkDate) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("homeworkDate", homeworkDate);
        return homeworkDao.select(map);
    }

    @Override
    public boolean saveHomework(Homework homework) {
        return homeworkDao.insert(homework);
    }

    @Override
    public boolean updateHomework(Homework homework) {
        return homeworkDao.update(homework);
    }

    @Override
    public boolean deleteHomework(String userId, String homeworkDate) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("homeworkDate", homeworkDate);
        return homeworkDao.delete(map);
    }
}
