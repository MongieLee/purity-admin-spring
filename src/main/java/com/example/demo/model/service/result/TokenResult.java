package com.example.demo.model.service.result;

/**
 * token结果工厂方法
 */
public class TokenResult {
    public static Result success(String msg, Object obj) {
        return new Result(200, true, msg, obj);
    }
}
