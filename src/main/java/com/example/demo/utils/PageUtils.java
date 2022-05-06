package com.example.demo.utils;

import com.example.demo.model.queryUtil.BaseListQuery;
import com.github.pagehelper.PageHelper;

import java.util.Objects;

/**
 * PageHelper插件工具类
 */
public class PageUtils {
    public static void startPage() {
        BaseQuery baseQuery = buildBaseQuery();
        Integer pageNo = baseQuery.getPageNo();
        Integer pageSize = baseQuery.getPageSize();
        if (pageNo != null && pageSize != null) {
            PageHelper.startPage(pageNo, pageSize);
        }
    }

    private static BaseQuery buildBaseQuery() {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setPageNo(1);
//        RequestContextHolder.getRequestAttributes();
        baseQuery.setPageSize(20);
        return baseQuery;
    }

    /**
     * 处理分页参数页码和页数
     * @param query
     */
    public static void handleQuery(BaseListQuery query) {
        if (Objects.isNull(query.getPage()) || query.getPage() < 1) {
            query.setPage(1);
        }
        if (Objects.isNull(query.getPageSize())) {
            query.setPageSize(10);
        }
    }
}
