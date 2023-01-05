package com.example.demo.controller;

import com.example.demo.converter.p2s.MenuP2SConverter;
import com.example.demo.model.persistent.Menu;
import com.example.demo.model.service.MenuDto;
import com.example.demo.model.service.result.JsonResult;
import com.example.demo.service.MenuService;
import com.example.demo.service.UserService;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    private final MenuService menuService;
    private final UserService userService;
    private final MenuP2SConverter menuP2SConverter;

    public MenuController(MenuService menuService, UserService userService, MenuP2SConverter menuP2SConverter) {
        this.menuService = menuService;
        this.userService = userService;
        this.menuP2SConverter = menuP2SConverter;
    }

    @PostMapping
    public JsonResult createMenu(@RequestBody Menu menu) {
        MenuDto menuByName = menuService.getMenuByName(menu.getName());
        if (menuByName != null) {
            return JsonResult.failure("创建失败，菜单已存在");
        }
        try {
            String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            menu.setCreateBy(userName);
            menuService.createMenu(menu);
            return JsonResult.success("创建菜单成功", menuService.getMenuByName(menu.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/tree")
    public JsonResult tree() {
        try {
            return JsonResult.success("获取菜单成功", menuService.tree());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/tree/{id}")
    public JsonResult tree(@PathVariable("id") Long id) {
        return JsonResult.success("获取菜单成功", menuService.tree());
    }

    @GetMapping("/{id}")
    public JsonResult getMenu(@PathVariable Long id) {
        return JsonResult.success("获取菜单成功", menuService.getMenuById(id));
    }

    @PutMapping("/{id}")
    public JsonResult updateUser(@PathVariable("id") Long id, @RequestBody Menu menu) {
        try {
            String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            menu.setUpdatedBy(userName);
            return JsonResult.success("更新菜单成功", menuService.updateMenu(id, menu));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteMenu(@PathVariable("id") Long id) {
        try {
            menuService.deleteMenu(id);
            return JsonResult.success("删除菜单成功", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @PostMapping("/moveUp/{id}")
    public JsonResult moveUp(@PathVariable("id") Long id) {
        try {
            menuService.moveUp(menuP2SConverter.reverse().convert(menuService.getMenuById(id)));
            return JsonResult.success("上移成功", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @PostMapping("/moveDown/{id}")
    public JsonResult moveDown(@PathVariable("id") Long id) {
        try {
            menuService.moveDown(menuP2SConverter.reverse().convert(menuService.getMenuById(id)));
            return JsonResult.success("下移成功", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }


    @PostMapping("/moveToStar/{id}")
    public JsonResult moveToStar(@PathVariable("id") Long id) {
        try {
            val byId = menuService.getMenuById(id);
            menuService.moveToStar(menuP2SConverter.reverse().convert(byId));
            return JsonResult.success("已将菜单【" + byId.getName() + "】移至当前层级第一位", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @PostMapping("/moveToEnd/{id}")
    public JsonResult moveToEnd(@PathVariable("id") Long id) {
        try {
            val byId = menuService.getMenuById(id);
            menuService.moveToEnd(menuP2SConverter.reverse().convert(byId));
            return JsonResult.success("已将菜单【" + byId.getName() + "】移至当前层级最后一位", (MenuDto) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/getUserMenus/{userId}")
    public JsonResult getUserMenus(@PathVariable("userId") Long userId) {
        System.out.println("用户ID:" + userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String name = authentication.getName();
        Collection<? extends GrantedAuthority> a = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        Object details = authentication.getDetails();
        return JsonResult.success("获取用户菜单成功", menuService.getUserMenus(userId));
    }

    @GetMapping("/getUserMenus")
    public JsonResult getSelf() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = userService.getUserByName(name).getId();
        return  getUserMenus(id);
    }
}
