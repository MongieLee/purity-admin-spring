package com.example.demo.model.service.result;

import com.example.demo.model.presistent.User;

/**
 * 用户结果工厂方法
 */
public class UserResult extends Result {
    protected UserResult(String msg) {
        super(ResultEnum.FAILURE, msg, null);
    }

    protected UserResult(User user) {
        super(ResultEnum.SUCCESSFUL, "更新成功", user);
    }

    public static UserResult success(User user) {
        return new UserResult(user);
    }

    public static UserResult success(String msg) {
        return new UserResult(msg);
    }

    public static UserResult failure(String msg) {
        return new UserResult(msg);
    }
}