package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User login(User user);

    void register(String username, String password);
}