package com.example.demo.controller;

import com.example.demo.model.persistent.ResourceCategroy;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.ResourceCategroyService;
import com.github.pagehelper.PageInfo;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/resourceCategroy")
public class ResourceCategroyController {
    private final ResourceCategroyService resourceCategroyService;

    public ResourceCategroyController(ResourceCategroyService resourceCategroyService) {
        this.resourceCategroyService = resourceCategroyService;
    }

    @PostMapping
    public Result createResource(@RequestBody ResourceCategroy resourceCategroy) {
        val resourceByName = resourceCategroyService.getResourceCategroyByName(resourceCategroy.getName());
        if (resourceByName != null) {
            return Result.failure("创建失败，分类已存在");
        }
        try {
            resourceCategroyService.createResourceCategroy(resourceCategroy);
            return Result.success("创建资源分类成功", resourceCategroyService.getResourceCategroyByName(resourceCategroy.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                          @RequestParam(name = "name", required = false) String name) {
        if (page == null || page < 1) {
            page = 1;
        }
        val resourceCategroy = new ResourceCategroy().setName(name);
        List<ResourceCategroy> list = resourceCategroyService.getList(resourceCategroy, page, pageSize);
        PageInfo<ResourceCategroy> resourcePageInfo = new PageInfo<>(list);
        return BaseListResult.success(list, resourcePageInfo.getTotal());
    }

    @GetMapping("/{id}")
    public Result getResourceCategroy(@PathVariable Long id) {
        try {
            return Result.success("获取资源分类成功", resourceCategroyService.getResourceCategroyById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    @PutMapping
    public Result updateResourceCategroy(@RequestBody ResourceCategroy resourceCategroy) {
        try {
            return Result.success("更新资源分类成功", resourceCategroyService.updateResourceCategroy(resourceCategroy));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteResourceCategroy(@PathVariable("id") Long id) {
        try {
            resourceCategroyService.deleteResourceCategroy(id);
            return Result.success("删除资源分类成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public Result getALl(){
        return Result.success("获取全部分类列表成功",resourceCategroyService.getAllResourceCategroy());
    }

}
