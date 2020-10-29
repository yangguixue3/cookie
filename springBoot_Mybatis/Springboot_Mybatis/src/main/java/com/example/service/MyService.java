package com.example.service;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    UserMapper userMapper;


    public User getUser(int id) {
        return userMapper.getUser(id);
    }
}
