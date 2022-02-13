package com.example.demo.model.service.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 列表结果工厂方法
 */
@Data
@Accessors(chain = true)
public class BaseListResult {
    private Long total;
    private List records;

    public BaseListResult(Long total, List records) {
        this.total = total;
        this.records = records;
    }

    public static Result success(List records, Long total) {
        return new Result(ResultEnum.SUCCESSFUL, "获取列表成功", new BaseListResult(total, records));
    }
}
