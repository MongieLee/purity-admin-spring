package com.example.demo.utils;

import com.github.pagehelper.PageHelper;
import org.springframework.web.context.request.RequestContextHolder;

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
}
