package com.example.demo.service;

import com.example.demo.dao.TodayNewsDao;
import com.example.demo.model.queryUtil.TodayNewsQuery;
import com.example.demo.model.service.result.Result;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

@Service
public class TodayNewsService {
    private TodayNewsDao todayNewsDao;

    public Result getList(TodayNewsQuery query) {
        PageHelper.startPage(query.getPage(), query.getPageSize());
        return Result.success("获取列表成功", todayNewsDao.getList(query));
    }
}
