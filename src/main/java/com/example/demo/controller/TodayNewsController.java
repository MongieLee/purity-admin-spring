package com.example.demo.controller;

import com.example.demo.dao.TodayNewsDao;
import com.example.demo.model.persistent.TodayNews;
import com.example.demo.model.queryUtil.TodayNewsQuery;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.TodayNewsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/todayNews")
public class TodayNewsController {
    private final TodayNewsDao todayNewsDao;
    private final TodayNewsService todayNewsService;

    public TodayNewsController(TodayNewsDao todayNewsDao, TodayNewsService todayNewsService) {
        this.todayNewsDao = todayNewsDao;
        this.todayNewsService = todayNewsService;
    }

    @PostMapping
    public Result createNews(@RequestBody TodayNews todayNews) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        todayNews.setCreatedBy(username);
        todayNewsDao.createNews(todayNews);
        return Result.success("创建成功", null);
    }

    @PutMapping
    public Result updateNews(@RequestBody TodayNews todayNews) {
        Long id = todayNews.getId();
        TodayNews newsById = todayNewsDao.getNewsById(id);
        if (Objects.isNull(newsById)) {
            return Result.failure("目标资讯不存在");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        todayNews.setId(id).setUpdatedBy(userName);
        todayNewsDao.updateNews(todayNews);
        return Result.success("更新资讯成功");
    }

    @PostMapping("/publish/{id}")
    public Result publish(@PathVariable Long id) {
        TodayNews newsById = todayNewsDao.getNewsById(id);
        if (Objects.isNull(newsById)) {
            return Result.failure("目标资讯不存在");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        todayNewsDao.publishNews(id, userName);
        return Result.success("发布成功");
    }

    @PostMapping("/cancelPublish/{id}")
    public Result cancelPublish(@PathVariable("id") Long id) {
        TodayNews newsById = todayNewsDao.getNewsById(id);
        if (Objects.isNull(newsById)) {
            return Result.failure("目标资讯不存在");
        }
        todayNewsDao.cancelPublishNews(id);
        return Result.success("取消发布成功");
    }

    @DeleteMapping("/{id}")
    public Result deleteNews(@PathVariable("id") Long id) {
        TodayNews newsById = todayNewsDao.getNewsById(id);
        if (Objects.isNull(newsById)) {
            return Result.failure("目标资讯不存在");
        }
        todayNewsDao.deleteNews(id);
        return Result.success("删除资讯成功");
    }

    @GetMapping("/{id}")
    public Result getNewsById(@PathVariable("id") Long id) {
        TodayNews newsById = todayNewsDao.getNewsById(id);
        if (Objects.isNull(newsById)) {
            return Result.failure("目标资讯不存在");
        }
        return Result.success("获取成功", newsById);
    }

    @GetMapping("/list")
    public Result getList(TodayNewsQuery query) {
        if (Objects.isNull(query.getPage()) || query.getPage() < 1) {
            query.setPage(1);
        }
        if (Objects.isNull(query.getPageSize())) {
            query.setPageSize(10);
        }
        return todayNewsService.getList(query);
    }
}
