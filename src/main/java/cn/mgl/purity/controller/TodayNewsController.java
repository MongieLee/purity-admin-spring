package cn.mgl.purity.controller;

import cn.mgl.purity.model.persistent.TodayNews;
import cn.mgl.purity.model.queryUtil.TodayNewsListQuery;
import cn.mgl.purity.model.service.result.JsonResult;
import cn.mgl.purity.service.TodayNewsService;
import cn.mgl.purity.utils.ContentUtils;
import cn.mgl.purity.utils.PageUtils;
import cn.mgl.purity.valid.TodayNewsModelValid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 今日资讯模块
 */
@Api(tags = "今日资讯模块")
@RestController
@RequestMapping("/api/v1/todayNews")
public class TodayNewsController {
    private final TodayNewsService todayNewsService;

    public TodayNewsController(TodayNewsService todayNewsService) {
        this.todayNewsService = todayNewsService;
    }

    /**
     * 创建资讯
     *
     * @param todayNews TodayNews实体
     * @return 结果
     */
    @ApiOperation(value = "创建今日资讯")
    @PostMapping
    public JsonResult createNews(@RequestBody @Validated(TodayNewsModelValid.Create.class) @ApiParam(value = "TodayNews实体类", required = true) TodayNews todayNews) {
        todayNews.setCreatedBy(ContentUtils.getCurrentAccessUsername());
        return todayNewsService.createNews(todayNews);
    }

    /**
     * 更新资讯内容，如果是已发布状态，会将状态恢复成未发布
     *
     * @param todayNews TodayNews实体
     * @return 结果
     */
    @ApiOperation("更新资讯")
    @PutMapping
    public JsonResult updateNews(@RequestBody @Validated(TodayNewsModelValid.Update.class) @ApiParam(value = "TodayNews实体类", required = true) TodayNews todayNews) {
        return todayNewsService.updateNews(todayNews);
    }

    /**
     * 将资讯改为发布状态，供客户端展示
     *
     * @param id 资讯id
     * @return 结果
     */
    @ApiOperation("发布资讯")
    @ApiImplicitParam(name = "id", value = "资讯Id", paramType = "path", required = true)
    @PostMapping("/publish/{id}")
    public JsonResult publish(@PathVariable Long id, HttpServletRequest request) {
        return todayNewsService.publish(id);
    }

    /**
     * 取消发布资讯状态
     *
     * @param id 资讯id
     * @return 结果
     */
    @ApiOperation("取消发布资讯")
    @PostMapping("/cancelPublish/{id}")
    public JsonResult cancelPublish(@PathVariable("id") @ApiParam(value = "资讯Id", required = true) Long id) {
        return todayNewsService.cancelPublish(id);
    }

    /**
     * 根据资讯id删除资讯
     *
     * @param id
     * @return
     */
    @ApiOperation("删除资讯")
    @DeleteMapping("/{id}")
    public JsonResult deleteNews(@PathVariable("id") @ApiParam(value = "资讯Id", required = true) Long id) {
        return todayNewsService.deleteNews(id);
    }

    /**
     * 根据资讯Id获取资讯内容
     *
     * @param id 资讯id
     * @return 单个TodayNews实体结果
     */
    @ApiOperation("根据资讯Id获取资讯内容")
    @GetMapping("/{id}")
    public JsonResult getNewsById(@PathVariable("id") @ApiParam(value = "资讯Id", required = true) Long id) {
        return todayNewsService.getNewsById(id);
    }

    /**
     * 根据分页获取列表数据
     *
     * @param query 分页参数
     * @return 列表结果
     */
    @ApiOperation("获取资讯列表（分页）")
    @GetMapping("/list")
    public JsonResult getList(@ApiParam(value = "分页查询参数") TodayNewsListQuery query) {
        PageUtils.handleQuery(query);
        return todayNewsService.getList(query);
    }

    /**
     * 按分页查询条件导出excel文件
     *
     * @param query
     * @return 包含文件地址结果
     * @throws FileNotFoundException
     */
    @ApiOperation("导出excel")
    @GetMapping("/export")
    public JsonResult exportExcelData(@ApiParam(value = "分页查询参数") TodayNewsListQuery query) throws FileNotFoundException {
        PageUtils.handleQuery(query);
        return todayNewsService.exportData(query);
    }

    /**
     * 传入Excel文件，文件内容需要符合TodayNews实体
     *
     * @param file excel文件
     * @return 结果
     */
    @ApiOperation("批量导入excel数据")
    @PostMapping("/batchImport")
    public JsonResult batchInsert(@RequestParam("FormFile") @ApiParam(value = "Excel文件", required = true) MultipartFile file) throws IOException {
        return todayNewsService.batchInsert(file);
    }
}
