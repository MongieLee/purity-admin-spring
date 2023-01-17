package cn.mgl.purity.controller;

import cn.mgl.purity.converter.p2s.MenuP2SConverter;
import cn.mgl.purity.model.persistent.Resource;
import cn.mgl.purity.model.service.ResourceDto;
import cn.mgl.purity.model.service.result.BaseListResult;
import cn.mgl.purity.model.service.result.JsonResult;
import cn.mgl.purity.service.MenuService;
import cn.mgl.purity.service.ResourceService;
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
    public JsonResult createResource(@RequestBody Resource resource) {
        val resourceByName = resourceService.getResourceByName(resource.getName());
        if (resourceByName != null) {
            return JsonResult.failure("创建失败，资源已存在");
        }
        try {
            resourceService.createResource(resource);
            return JsonResult.success("创建资源成功", resourceService.getResourceByName(resource.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/list")
    public JsonResult getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
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
    public JsonResult getMenu(@PathVariable Long id) {
        try {
            return JsonResult.success("获取资源成功", resourceService.getResourceById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @PutMapping
    public JsonResult updateUser(@RequestBody Resource resource) {
        try {
            return JsonResult.success("更新资源成功", resourceService.updateResource(resource));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteMenu(@PathVariable("id") Long id) {
        try {
            resourceService.deleteResource(id);
            return JsonResult.success("删除菜单成功", (ResourceDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/getResourceGroup")
    public JsonResult getResourceGroup() {
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
        return JsonResult.success("", null);
    }
}
