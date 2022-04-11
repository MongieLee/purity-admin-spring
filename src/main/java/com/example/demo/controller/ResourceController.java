package com.example.demo.controller;

import com.example.demo.converter.p2s.MenuP2SConverter;
import com.example.demo.model.presistent.Menu;
import com.example.demo.model.presistent.Resource;
import com.example.demo.model.presistent.User;
import com.example.demo.model.service.MenuDto;
import com.example.demo.model.service.ResourceDto;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.MenuResult;
import com.example.demo.model.service.result.ResourceResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.MenuService;
import com.example.demo.service.ResourceService;
import com.github.pagehelper.PageInfo;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {
    private final MenuService menuService;
    private final MenuP2SConverter menuP2SConverter;
    private final ResourceService resourceService;

    public ResourceController(MenuService menuService, MenuP2SConverter menuP2SConverter, ResourceService resourceService) {
        this.menuService = menuService;
        this.menuP2SConverter = menuP2SConverter;
        this.resourceService = resourceService;
    }

    @PostMapping
    public Result createResource(@RequestBody Resource resource) {
        val resourceByName = resourceService.getResourceByName(resource.getName());
        if (resourceByName != null) {
            return MenuResult.failure("创建失败，资源已存在");
        }
        try {
            resourceService.createResource(resource);
            return ResourceResult.success("创建资源成功", resourceService.getResourceByName(resource.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                          @RequestParam(name = "name", required = false) String name, @RequestParam(name = "url", required = false) String url,
                          @RequestParam(name = "categroyId", required = false) Long categroyId) {
        if (page == null || page < 1) {
            page = 1;
        }
        val resource = new ResourceDto().setName(name).setUrl(url).setCategroyId(categroyId);
        List<ResourceDto> list = resourceService.getList(resource, page, pageSize);

        PageInfo<ResourceDto> resourcePageInfo = new PageInfo<>(list);
        return BaseListResult.success(list, resourcePageInfo.getTotal());
    }

    @GetMapping("/{id}")
    public Result getMenu(@PathVariable Long id) {
        try {
            return ResourceResult.success("获取资源成功", resourceService.getResourceById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @PutMapping
    public Result updateUser(@RequestBody Resource resource) {
        try {
            return ResourceResult.success("更新资源成功", resourceService.updateResource(resource));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteMenu(@PathVariable("id") Long id) {
        try {
            resourceService.deleteResource(id);
            return ResourceResult.success("删除菜单成功", (ResourceDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/getResourceGroup")
    public Result getResourceGroup() {
        List<Resource> allResource = resourceService.getAllResource();
        List<Object> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        allResource.forEach(i -> {
            String categroyId = i.getCategroyId().toString();
            if (map.get(categroyId) == null) {
                List<Resource> objects1 = new ArrayList<>();
                objects1.add(i);
                map.put(categroyId, objects1);
            } else {
                List<Resource> o = (List<Resource>) map.get(categroyId);
                o.add(i);
            }
        });
        System.out.println(map);
        return Result.success("", null);
    }
}
