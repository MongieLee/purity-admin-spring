package com.example.demo.service;

import com.example.demo.model.presistent.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(userService.getUserByName(name));
    }
}
