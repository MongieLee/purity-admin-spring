package com.example.demo.controller;

import com.example.demo.dao.RoleDao;
import com.example.demo.model.presistent.Role;
import com.example.demo.model.presistent.RoleMenuRel;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.MenuResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.RoleResult;
import com.example.demo.service.RoleService;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;
    private final RoleDao roleDao;

    public RoleController(RoleService roleService, RoleDao roleDao) {
        this.roleService = roleService;
        this.roleDao = roleDao;
    }

    @PostMapping
    public Result createRole(@RequestBody Role role) {
        val roleByName = roleService.getRoleByName(role.getName());
        if (roleByName != null) {
            return RoleResult.failure("创建失败，角色已存在");
        }
        try {
            roleService.createRole(role);
            return RoleResult.success("创建角色成功", roleService.getRoleByName(role.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getRole(@PathVariable Long id) {
        try {
            return RoleResult.success("获取角色成功", roleService.getRoleById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                          @RequestParam(name = "name", required = false) String name) {
        if (page < 1) {
            page = 1;
        }
        val role = new Role().setName(name);
        val list = roleService.getList(role, page, pageSize);
        PageInfo<Role> rolePageInfo = new PageInfo<>(list);
        return BaseListResult.success(list, rolePageInfo.getTotal());
    }

    @PutMapping
    public Result updateRole(@RequestBody Role role) {
        try {
            return RoleResult.success("更新角色成功", roleService.updateRole(role));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteMenu(@PathVariable("id") Long id) {
        try {
            roleService.deleteRole(id);
            return RoleResult.success("删除角色成功", (Role) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @PostMapping("/saveRoleMenus")
    public Result saveRole(@RequestBody TempRoleMenu roleMenuObj) {
        List<Long> roleMenus = roleMenuObj.getRoleMenus();
        try {
            val roleById = roleDao.getRoleById(roleMenuObj.getRoleId());
            if (roleById == null) {
                return MenuResult.failure("角色不存在！");
            }
            roleDao.clearMenus(roleMenuObj.getRoleId());
            if (roleMenuObj.getRoleMenus() != null && roleMenuObj.getRoleMenus().size() > 0){
                roleDao.saveRoleMenus(roleMenuObj);
            }
            return RoleResult.success("保存成功", (Role) null);
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @GetMapping("/getRoleMenus/{roleId}")
    public Result getRoleMenus(@PathVariable("roleId") Long roleId) {
        try {
            val roleById = roleDao.getRoleById(roleId);
            if (roleById == null) {
                return MenuResult.failure("角色不存在！");
            }
            val roleMenus = roleDao.getRoleMenus(roleId);

            return RoleResult.success("获取角色菜单成功",
                    roleMenus.stream().map(RoleMenuRel::getMenuId).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return MenuResult.failure(e.getMessage());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static
    class TempRoleMenu {
        private Long roleId;
        private ArrayList<Long> roleMenus;
    }

}
