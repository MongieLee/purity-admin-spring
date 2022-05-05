package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.persistent.User;
import com.example.demo.model.service.Account;
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
import org.springframework.validation.annotation.Validated;
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
    private final static String REFRESH_TOKEN = "refresh_token";

    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final RedisTemplate<String, Object> redisTemplate;

    public AuthController(UserService userService, JWTUtils jwtUtils, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/register")
    public Result register(@RequestBody @Validated Account account) {
        try {
            userService.register(account);
            return LoginResult.success("注册成功!");
        } catch (DuplicateKeyException e) {
            return LoginResult.failure("用户已注册");
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public Result loggedInUser(@RequestBody @Validated Account account) {
        String username = account.getUsername();
        User dbUser;
        dbUser = userService.login(new User().setUsername(account.getUsername()).setEncrypted_password(account.getPassword()));
        if (!dbUser.getStatus()) {
            return Result.failure("登录失败，账号被封禁，请联系管理员");
        }

        // 往redis中存refreshToken
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("userId", dbUser.getId().toString());
        HashMap<String, Object> stringObjectHashMap = JWTUtils.generateToken(map);
        HashOperations<String, Object, Object> redisHashMap = redisTemplate.opsForHash();
        redisHashMap.put(REFRESH_TOKEN, dbUser.getId().toString(), stringObjectHashMap.get(REFRESH_TOKEN));
        return TokenResult.success("登录成功", stringObjectHashMap);
    }

    @PostMapping("/refreshToken")
    @ResponseBody
    public Result refreshToken(@RequestBody Map<String, String> refreshTokenObj) {
        String refreshToken = refreshTokenObj.get("refresh_token");
        String grantType = refreshTokenObj.get("grant_type");
        Map<String, String> map = new HashMap<>();
        System.out.println(grantType + ":<==grantType");
        System.out.println(refreshToken + ":<==refreshToken");
        if (!Objects.isNull(grantType) && grantType.equals(GrantType.REFRESH_TOKEN.getType())) {
            try {
                JWTUtils.verify(refreshToken);
                DecodedJWT decode = JWT.decode(refreshToken);
                String userId = decode.getClaim("userId").asString();
                String username = decode.getClaim("username").asString();
                map.put("userId", userId);
                map.put("username", username);
                HashOperations<String, Object, Object> redisHashMap = redisTemplate.opsForHash();
                redisTemplate.opsForValue().set("cacheTime", 1000);
//                if (redisHashMap.hasKey("refresh_token", userId)) {
//                    String redisRefreshToken = redisHashMap.get(REFRESH_TOKEN, userId).toString();
//                    if (!redisRefreshToken.equals(refreshToken)) {
//                        return Result.failure("refresh_token已被使用过，刷新token失败");
//                    }
//                };
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
