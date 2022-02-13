package com.example.demo.model.service.result;

import com.example.demo.model.presistent.User;

/**
 * 登录结果工厂方法
 */
public class LoginResult {
    public static Result success(String msg) {
        return new Result(ResultEnum.SUCCESSFUL, msg, null);
    }

    public static Result success(String msg, User user) {
        return new Result(ResultEnum.SUCCESSFUL, msg, user);
    }

    public static Result failure(String msg) {
        return new Result(ResultEnum.FAILURE, msg, null);
    }
}
