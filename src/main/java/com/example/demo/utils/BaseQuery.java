package com.example.demo.utils;


import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseQuery {
    @NotNull
    private Integer pageNo;
    @NotNull
    private Integer pageSize;
    private String orderByColumn;
    private String isAsc;
}
