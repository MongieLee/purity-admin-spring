package com.example.demo.controller;

import com.example.demo.model.presistent.Menu;
import com.example.demo.model.service.MenuDto;
import com.example.demo.model.service.result.MenuResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.MenuService;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public Result createMenu(@RequestBody Menu menu) {
        MenuDto menuByName = menuService.getMenuByName(menu.getName());
        if (menuByName != null) {
            return MenuResult.failure("创建失败，菜单已存在");
        }
        try {
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
}
