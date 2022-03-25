package com.example.demo.model.service.result;

import com.example.demo.model.presistent.User;

/**
 * 登录结果工厂方法
 */
public class LoginResult {
    public static Result success(String msg) {
        return new Result(200, true, msg, null);
    }

    public static Result success(String msg, User user) {
        return new Result(200, true, msg, user);
    }

    public static Result failure(String msg) {
        return new Result(400, false, msg, null);
    }
}
