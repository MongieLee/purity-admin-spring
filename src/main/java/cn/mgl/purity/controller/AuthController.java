package cn.mgl.purity.controller;

import cn.mgl.purity.model.persistent.User;
import cn.mgl.purity.model.service.Account;
import cn.mgl.purity.model.service.RefreshToken;
import cn.mgl.purity.model.service.result.JsonResult;
import cn.mgl.purity.service.AuthService;
import cn.mgl.purity.service.UserService;
import cn.mgl.purity.utils.JWTUtils;
import cn.mgl.purity.valid.AccountModelValid;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 注册账号
     *
     * @param account 账号名和密码
     * @return 结果
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public JsonResult register(@RequestBody @Validated(AccountModelValid.Register.class) Account account) {
        return userService.register(account);
    }

    /**
     * 用户登录，返回access_token和refresh_token，以及access_token的过期时间
     *
     * @param account
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public JsonResult loggedInUser(@RequestBody @Validated(AccountModelValid.Login.class) Account account) {
        return userService.login(new User().setUsername(account.getUsername()).setEncryptedPassword(account.getPassword()));
    }

    /**
     * 刷新access_token
     * @param refreshTokenObj
     * @return
     */
    @ApiOperation(value = "用户刷新JWT凭证")
    @PostMapping("/refreshToken")
    public JsonResult refreshToken(@RequestBody @Validated RefreshToken refreshTokenObj) {
        return authService.refreshToken(refreshTokenObj);
    }
}
