package com.example.demo.service;

import com.example.demo.dao.TodayNewsDao;
import com.example.demo.model.persistent.TodayNews;
import com.example.demo.model.queryUtil.TodayNewsListQuery;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodayNewsService {
    private TodayNewsDao todayNewsDao;

    public TodayNewsService(TodayNewsDao todayNewsDao) {
        this.todayNewsDao = todayNewsDao;
    }

    public Result createNews(TodayNews todayNews) {
        todayNewsDao.createNews(todayNews);
        return Result.success("创建资讯成功");
    }

    public Result getList(TodayNewsListQuery query) {
        PageHelper.startPage(query.getPage(), query.getPageSize());
        List<TodayNews> list = todayNewsDao.getList(query);
        return BaseListResult.success(list, new PageInfo<>(list).getTotal());
    }
}
