package com.example.springboot.service.impl;

import com.example.springboot.mapper.UserDao;
import com.example.springboot.module.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    public User getUserById(int userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    public boolean addUser(User record){
        boolean result = false;
        try {
            userDao.insertSelective(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<User> selectUserList() {
        return userDao.selectUserList();
    }

    @Override
    public List<User> selectUserList(String keywords, Integer pageNum, Integer pageSize) {
        return userDao.selectUserList();
    }

}
