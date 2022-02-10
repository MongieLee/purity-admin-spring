package com.example.demo.entity;

public class TokenResult extends Result {
    protected TokenResult(ResultEnum status, String msg, Object data) {
        super(status, msg, data);
    }

    public static TokenResult success(String msg, Object obj) {
        return new TokenResult(Result.ResultEnum.SUCCESSFUL, msg, obj);
    }
}
