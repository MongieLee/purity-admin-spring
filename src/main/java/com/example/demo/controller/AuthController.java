package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.persistent.User;
import com.example.demo.model.persistent.UserBuilder;
import com.example.demo.model.service.result.LoginResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.TokenResult;
import com.example.demo.service.UserService;
import com.example.demo.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 登录注册模块
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final RedisTemplate<String, Object> redisTemplate;

    public AuthController(UserService userService, JWTUtils jwtUtils, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.redisTemplate = redisTemplate;
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

    @PostMapping("/refreshToken")
    @ResponseBody
    public Result refreshToken(@RequestBody Map<String, String> refreshTokenObj) {
        String refreshToken = refreshTokenObj.get("refresh_token");
        String grantType = refreshTokenObj.get("grant_type");
        Map<String, String> map = new HashMap<>();
        String REFRESH_TOKEN = "refresh_token";
        if (!Objects.isNull(grantType) && grantType.equals(GrantType.REFRESH_TOKEN.getType())) {
            try {
                JWTUtils.verify(refreshToken);
                DecodedJWT decode = JWT.decode(refreshToken);
                String userId = decode.getClaim("userId").asString();
                String username = decode.getClaim("username").asString();
                map.put("userId", userId);
                map.put("username", username);
                HashOperations<String, Object, Object> redisHashMap = redisTemplate.opsForHash();
                if(redisHashMap.hasKey("refresh_token",userId)){
                    String redisRefreshToken = redisHashMap.get(REFRESH_TOKEN, userId).toString();
                    if(!redisRefreshToken.equals(refreshToken)){
                        return Result.failure("refresh_token无效");
                    }
                };
                HashMap<String, Object> tokenResult = JWTUtils.generateToken(map);
                redisHashMap.put("refresh_token", userId, tokenResult.get(REFRESH_TOKEN));
                return Result.success("刷新令牌成功", tokenResult);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure("refresh_token无效");
            }
        }
        return Result.failure("grant_type类型错误");
    }
}

enum GrantType {
    TOKEN("token"),
    REFRESH_TOKEN("refresh_token");
    private final String type;

    GrantType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}