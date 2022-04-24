package com.example.demo.model.service.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回结果基本类
 */
@Data
@Accessors(chain = true)
public class Result {
    String msg;
    Object data;
    Boolean success;
    Integer code;

    public Result(Integer code, Boolean success, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    static public Result success(String msg, Object data) {
        return new Result(200, true, msg, data);
    }

    static public Result failure(String msg) {
        return new Result(400, false, msg, null);
    }

    static public Result failure(String msg, Object data) {
        return new Result(400, false, msg, data);
    }
}