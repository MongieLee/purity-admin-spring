package com.example.demo.controller;

import com.example.demo.model.persistent.User;
import com.example.demo.model.persistent.UserBuilder;
import com.example.demo.model.service.result.LoginResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.TokenResult;
import com.example.demo.service.UserService;
import com.example.demo.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录注册模块
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result register(@RequestBody Map<String, String> usernameAndPasswordJson) {
        String username = usernameAndPasswordJson.get("username");
        String password = usernameAndPasswordJson.get("password");
        if (username == null || password == null) {
            return LoginResult.failure("用户名或密码为空");
        }
        if (username.length() < 1 || username.length() > 15) {
            return LoginResult.failure("无效用户名");
        }
        if (password.length() < 1 || password.length() > 15) {
            return LoginResult.failure("无效密码");
        }
        try {
            userService.register(username, password);
            return LoginResult.success("注册成功!");
        } catch (DuplicateKeyException e) {
            return LoginResult.failure("用户已注册");
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public Result loggedInUser(@RequestBody Map<String, Object> usernameAndPasswordJson) {
        String username = usernameAndPasswordJson.get("username").toString();
        String password = usernameAndPasswordJson.get("password").toString();
        User user;
        try {
            User build = UserBuilder.anUser().withUsername(username).withEncryptedPassword(password).build();
            user = userService.login(build);
            System.out.println(user);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return LoginResult.failure(e.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("userId", user.getId().toString());
        return TokenResult.success("登录成功", JWTUtils.generateToken(map));
    }
}