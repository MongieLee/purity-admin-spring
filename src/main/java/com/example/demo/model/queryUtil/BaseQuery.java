package com.example.demo.model.queryUtil;

import lombok.Data;

@Data
public class BaseQuery {
    private Integer page;
    private Integer pageSize;
    private String orderByColumn;
    private Boolean isAsc;
}
