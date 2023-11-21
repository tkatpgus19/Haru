package com.ssafy.haru.service;

import com.ssafy.haru.model.dao.UserDao;
import com.ssafy.haru.model.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public boolean join(User user) {
        boolean result = false;
        if (userDao.selectById(user.getUserId()) == null) {
            result = userDao.insert(user);
        }
        return result;
    }

    @Override
    public User login(String userId, String userPassword) {
        User user = userDao.selectById(userId);
        if (user != null && user.getUserPassword().equals(userPassword)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean isUsed(String userId) {
        User user = userDao.selectById(userId);
        return user != null;
    }

    @Override
    public String findId(String userEmail) {
        return userDao.selectByEmail(userEmail);
    }

    @Override
    public boolean unregister(String userId) {
        return userDao.delete(userId);
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean matchPassword(String userId, String userPassword) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userPassword", userPassword);
        User user = userDao.selectByPassword(map);
        if(user != null){
            return true;
        }
        return false;
    }
}
