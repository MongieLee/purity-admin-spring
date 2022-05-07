package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.persistent.User;
import com.example.demo.model.service.Account;
import com.example.demo.model.service.RefreshToken;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JWTUtils;
import com.example.demo.valid.AccountModelValid;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

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
    private final AuthService authService;

    public AuthController(UserService userService, JWTUtils jwtUtils, RedisTemplate<String, Object> redisTemplate, AuthService authService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.redisTemplate = redisTemplate;
        this.authService = authService;
    }

    @PostMapping("/register")
    public Result register(@RequestBody @Validated(AccountModelValid.Register.class) Account account) {
        return userService.register(account);
    }

    @PostMapping("/login")
    @ResponseBody
    public Result loggedInUser(@RequestBody @Validated(AccountModelValid.Login.class) Account account) {
        return userService.login(new User().setUsername(account.getUsername()).setEncryptedPassword(account.getPassword()));
    }

    @PostMapping("/refreshToken")
    @ResponseBody
    public Result refreshToken(@RequestBody @Validated RefreshToken refreshTokenObj) {
        return authService.refreshToken(refreshTokenObj);
    }
}
