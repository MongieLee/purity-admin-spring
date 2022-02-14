package com.example.demo.model.service.result;

import com.example.demo.model.presistent.User;

/**
 * 用户结果工厂方法
 */
public class UserResult {
    public static Result success(User user) {
        return new Result(ResultEnum.SUCCESSFUL, null, user);
    }

    public static Result success(String msg) {
        return new Result(ResultEnum.SUCCESSFUL, msg, null);
    }

    public static Result failure(String msg) {
        return new Result(ResultEnum.FAILURE, msg, null);
    }
}