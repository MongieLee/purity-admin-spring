package com.example.demo.controller;

import com.example.demo.converter.p2s.MenuP2SConverter;
import com.example.demo.model.persistent.Menu;
import com.example.demo.model.service.MenuDto;
import com.example.demo.model.service.result.MenuResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.MenuService;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    private final MenuService menuService;
    private final MenuP2SConverter menuP2SConverter;

    public MenuController(MenuService menuService, MenuP2SConverter menuP2SConverter) {
        this.menuService = menuService;
        this.menuP2SConverter = menuP2SConverter;
    }

    @PostMapping
    public Result createMenu(@RequestBody Menu menu) {
        MenuDto menuByName = menuService.getMenuByName(menu.getName());
        if (menuByName != null) {
            return MenuResult.failure("创建失败，菜单已存在");
        }
        try {
            String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            menu.setCreateBy(userName);
            menuService.createMenu(menu);
            return MenuResult.success("创建菜单成功", menuService.getMenuByName(menu.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/tree")
    public Result tree() {
        try {
            return MenuResult.success("获取菜单成功", menuService.tree());
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/tree/{id}")
    public Result tree(@PathVariable("id") Long id) {
        try {
            return MenuResult.success("获取菜单成功", menuService.tree());
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getMenu(@PathVariable Long id) {
        try {
            return MenuResult.success("获取菜单成功", menuService.getMenuById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result updateUser(@PathVariable("id") Long id, @RequestBody Menu menu) {
        try {
            String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            menu.setUpdatedBy(userName);
            return MenuResult.success("更新菜单成功", menuService.updateMenu(id, menu));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteMenu(@PathVariable("id") Long id) {
        try {
            menuService.deleteMenu(id);
            return MenuResult.success("删除菜单成功", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @PostMapping("/moveUp/{id}")
    public Result moveUp(@PathVariable("id") Long id) {
        try {
            menuService.moveUp(menuP2SConverter.reverse().convert(menuService.getMenuById(id)));
            return MenuResult.success("上移成功", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @PostMapping("/moveDown/{id}")
    public Result moveDown(@PathVariable("id") Long id) {
        try {
            menuService.moveDown(menuP2SConverter.reverse().convert(menuService.getMenuById(id)));
            return MenuResult.success("下移成功", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }


    @PostMapping("/moveToStar/{id}")
    public Result moveToStar(@PathVariable("id") Long id) {
        try {
            val byId = menuService.getMenuById(id);
            menuService.moveToStar(menuP2SConverter.reverse().convert(byId));
            return MenuResult.success("已将菜单【" + byId.getName() + "】移至当前层级第一位", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @PostMapping("/moveToEnd/{id}")
    public Result moveToEnd(@PathVariable("id") Long id) {
        try {
            val byId = menuService.getMenuById(id);
            menuService.moveToEnd(menuP2SConverter.reverse().convert(byId));
            return MenuResult.success("已将菜单【" + byId.getName() + "】移至当前层级最后一位", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/test")
    public Result test() {
        menuService.test();
        return null;
    }

    @GetMapping("/getUserMenus/{userId}")
    public Result getUserMenus(@PathVariable("userId") Long userId) {
        System.out.println("用户ID:" + userId);
        return Result.success("获取用户菜单成功", menuService.getUserMenus(userId));
    }
}
