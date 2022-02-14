package com.example.demo.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseQuery {
    private Integer pageNo;
    private Integer pageSize;
    private String orderByColumn;
    private String isAsc;
}
