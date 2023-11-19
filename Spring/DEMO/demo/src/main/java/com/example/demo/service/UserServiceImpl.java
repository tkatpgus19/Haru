package com.example.demo.service;


import com.example.demo.model.dao.UserDao;
import com.example.demo.model.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public boolean join(User user) {
        int result = 0;
        if (userDao.selectById(user.getUserId()) == null) {
            result = userDao.insert(user);
            logger.warn(String.valueOf(result));
        }
        return result == 1;
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


}
