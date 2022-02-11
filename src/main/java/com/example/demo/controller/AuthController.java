package com.example.demo.controller;

import com.example.demo.entity.LoginResult;
import com.example.demo.entity.TokenResult;
import com.example.demo.entity.User;
import com.example.demo.entity.UserBuilder;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.utils.JWTUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public LoginResult register(@RequestBody Map<String, String> usernameAndPasswordJson) {
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
            return LoginResult.success("注册成功!", false);
        } catch (DuplicateKeyException e) {
            return LoginResult.failure("用戶已注冊");
        }
    }

//    @PostMapping("/login")
//    public Map<String, Object> login(User user) {
//        Map<String, Object> result = new HashMap<>();
//        log.info("用户名: [{}]", user.getUsername());
//        log.info("密码: [{}]", user.getEncryptedPassword());
//        try {
//            User userDB = userService.login(user);
//            Map<String, String> map = new HashMap<>();//用来存放payload
//            map.put("id", userDB.getId());
//            map.put("username", userDB.getUsername());
//            String token = JWTUtil.generateToken(map);
//            result.put("state", true);
//            result.put("msg", "登录成功!!!");
//            result.put("token", token); //成功返回token信息
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.put("state", "false");
//            result.put("msg", e.getMessage());
//        }
//        return result;
//    }

    @PostMapping("/login")
    @ResponseBody
    public Object loggedInUser(@RequestBody Map<String, Object> usernameAndPasswordJson) throws JsonProcessingException {
        String username = usernameAndPasswordJson.get("username").toString();
        String password = usernameAndPasswordJson.get("password").toString();
        User user;
        try {
            User build = UserBuilder.anUser().withUsername(username).withEncryptedPassword(password).build();
            user = userService.login(build);
            System.out.println(user);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure(e.getMessage());
        } catch (BadCredentialsException e) {
            return LoginResult.failure(e.getMessage());
        }
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//        try {
//            authenticationManager.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(token);
//            return LoginResult.success("登录成功", userService.getUserByName(username));
//        } catch (BadCredentialsException e) {
//            return LoginResult.failure("密码不正确");
//        }
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        JWTUtils.generateToken(map);

        String token = JWTUtils.generateToken(map);
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        return TokenResult.success("登录成功", result);
    }


    @PostMapping("/test")
    public Map<String, Object> test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("msg", "请求成功");
        return map;
    }
}