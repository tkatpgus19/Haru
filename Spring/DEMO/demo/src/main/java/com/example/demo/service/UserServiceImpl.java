package com.example.demo.service;


import com.example.demo.model.dao.UserDao;
import com.example.demo.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public boolean join(User user) {
        int result = userDao.insert(user);
        return result == 1;
    }

    @Override
    public User login(String userId, String userPassword) {
        User user = userDao.select(userId);
        if(user != null && user.getUserPassword().equals(userPassword)){
            return user;
        }
        return null;
    }
}
