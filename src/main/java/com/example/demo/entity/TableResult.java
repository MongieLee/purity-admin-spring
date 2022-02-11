package com.example.demo.entity;

import java.util.List;

public class TableResult extends BaseListResult {
    protected TableResult(ResultEnum status, String msg, Object data) {
        super(status, msg, data);
    }

    public static <T> TableResult success(Integer pageNo, Integer pageSize, Long total, List<T> records) {
        return new TableResult(Result.ResultEnum.SUCCESSFUL, "请求成功", records);
    }
}
