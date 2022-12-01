package com.example.demo.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.example.demo.configuration.EnvConfig;
import com.example.demo.dao.TodayNewsDao;
import com.example.demo.model.persistent.TodayNews;
import com.example.demo.model.queryUtil.TodayNewsListQuery;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.JsonResult;
import com.example.demo.utils.ContentUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TodayNewsService {
    private TodayNewsDao todayNewsDao;
    private EnvConfig envConfig;

    public TodayNewsService(TodayNewsDao todayNewsDao, EnvConfig envConfig) {
        this.todayNewsDao = todayNewsDao;
        this.envConfig = envConfig;
    }

    public JsonResult createNews(TodayNews todayNews) {
        todayNewsDao.createNews(todayNews);
        return JsonResult.success("创建资讯成功", todayNewsDao.getNewsById(todayNews.getId()));
    }

    public JsonResult getList(TodayNewsListQuery query) {
        PageHelper.startPage(query.getPage(), query.getPageSize());
        List<TodayNews> list = todayNewsDao.getList(query);
        return BaseListResult.success(list, new PageInfo<>(list).getTotal());
    }

    public JsonResult updateNews(TodayNews todayNews) {
        Long id = todayNews.getId();
        if (Objects.isNull(todayNewsDao.getNewsById(id))) {
            return JsonResult.failure("目标资讯不存在");
        }
        todayNews.setUpdatedBy(ContentUtils.getCurrentAccessUsername());
        todayNewsDao.updateNews(todayNews);
        return JsonResult.success("更新资讯成功", todayNewsDao.getNewsById(todayNews.getId()));
    }

    public JsonResult getNewsById(Long id) {
        TodayNews dbNews = todayNewsDao.getNewsById(id);
        if (Objects.isNull(dbNews)) {
            return JsonResult.failure("目标资讯不存在");
        }
        return JsonResult.success("获取成功", dbNews);
    }

    /**
     * 按分页查询条件导出excel文件
     *
     * @param query 今日资讯查询参数
     * @return 响应结果
     * @throws FileNotFoundException
     */
    public JsonResult exportData(TodayNewsListQuery query) throws FileNotFoundException {
        String fileName = System.currentTimeMillis() + ".xlsx";
        File exportFolder = new File(envConfig.getExportFilePath());
        if (!exportFolder.exists()) {
            exportFolder.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(exportFolder.getAbsolutePath() + File.separator + fileName);
        EasyExcel.write(fileOutputStream, TodayNews.class).sheet("模板")
                .doWrite(() -> {
                    PageHelper.startPage(query.getPage(), query.getPageSize());
                    return todayNewsDao.getList(query);
                });
        Map<String, String> pathResult = new HashMap<>();
        pathResult.put("path", envConfig.getExportFolder() + fileName);
        return JsonResult.success("导出成功", pathResult);
    }

    public JsonResult batchInsert(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), TodayNews.class, new PageReadListener<TodayNews>(dataList -> {
            for (TodayNews todayNews : dataList) {
                todayNews.setCreatedBy(ContentUtils.getCurrentAccessUsername());
                todayNewsDao.createNews(todayNews);
            }
        })).sheet().doRead();
        return JsonResult.success("导入成功");
    }

    public JsonResult deleteNews(Long id) {
        TodayNews dbNews = todayNewsDao.getNewsById(id);
        if (Objects.isNull(dbNews)) {
            return JsonResult.failure("目标资讯不存在");
        }
        todayNewsDao.deleteNews(id);
        return JsonResult.success("删除资讯成功");
    }

    public JsonResult cancelPublish(Long id) {
        TodayNews dbNews = todayNewsDao.getNewsById(id);
        if (Objects.isNull(dbNews)) {
            return JsonResult.failure("目标资讯不存在");
        }
        if (!dbNews.getIsPublish()) {
            return JsonResult.failure("资讯已是未发布状态，请勿重复操作");
        }
        todayNewsDao.cancelPublishNews(id);
        return JsonResult.success("取消发布成功");
    }

    public JsonResult publish(Long id) {
        TodayNews dbNews = todayNewsDao.getNewsById(id);
        if (Objects.isNull(dbNews)) {
            return JsonResult.failure("目标资讯不存在");
        }
        checkDbTargetExistById(id);
        if (dbNews.getIsPublish()) {
            return JsonResult.failure("资讯已是发布状态，请勿重复操作");
        }
        todayNewsDao.publishNews(id, ContentUtils.getCurrentAccessUsername());
        return JsonResult.success("发布成功");
    }

    public Boolean checkDbTargetExistById(Long id) {
        return Objects.nonNull(todayNewsDao.getNewsById(id));
    }
}
