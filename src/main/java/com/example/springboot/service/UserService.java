package com.example.springboot.service;

import com.example.springboot.module.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public User getUserById(int userId);

    boolean addUser(User record);

    List<User> selectUserList();

    List<User> selectUserList(String keywords,Integer pageNum,Integer pageSize);
}
