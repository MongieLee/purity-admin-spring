package com.example.demo.model.service.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回结果基本类
 */
@Data
@Accessors(chain = true)
public class Result {
    ResultEnum status;
    String msg;
    Object data;

    public Result(ResultEnum status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}