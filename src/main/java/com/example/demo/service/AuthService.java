package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserServiceImpl userService;

    public AuthService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public Optional<User> getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(userService.getUserByName(name));
    }
}
