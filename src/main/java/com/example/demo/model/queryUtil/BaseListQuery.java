package com.example.demo.model.queryUtil;

import lombok.Data;

/**
 * 列表查询
 */
@Data
public class BaseListQuery {
    private Integer page;
    private Integer pageSize;
    private String orderByColumn;
    private Boolean isAsc;
}
