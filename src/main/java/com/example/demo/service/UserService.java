package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    User login(User user);

    void register(String username, String password);

    List<User> getList(User user,Integer page,Integer pageSize);
}