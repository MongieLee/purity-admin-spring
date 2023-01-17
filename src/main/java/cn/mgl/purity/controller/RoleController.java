package cn.mgl.purity.controller;

import cn.mgl.purity.dao.RoleDao;
import cn.mgl.purity.model.persistent.Role;
import cn.mgl.purity.model.persistent.RoleDTO;
import cn.mgl.purity.model.persistent.User;
import cn.mgl.purity.model.service.result.BaseListResult;
import cn.mgl.purity.model.service.result.JsonResult;
import cn.mgl.purity.service.RoleService;
import cn.mgl.purity.service.UserService;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;
    private final RoleDao roleDao;
    private final UserService userService;

    public RoleController(RoleService roleService, RoleDao roleDao, UserService userService) {
        this.roleService = roleService;
        this.roleDao = roleDao;
        this.userService = userService;
    }

    @PostMapping
    public JsonResult createRole(@RequestBody RoleDTO role) {
        Role roleByName = roleService.getRoleByName(role.getName());
        if (roleByName != null) {
            return JsonResult.failure("创建失败，角色已存在");
        }
        roleService.createRole(role);
        return JsonResult.success("创建角色成功", roleService.getRoleByName(role.getName()));
    }

    @GetMapping("/{id}")
    public JsonResult getRole(@PathVariable Long id) {
        return JsonResult.success("获取角色成功", roleService.getRoleById(id));
    }

    @GetMapping("/getAll")
    public JsonResult getAll() {
        return JsonResult.success("", roleService.getAllRoles());
    }

    @GetMapping("/list")
    public JsonResult getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                              @RequestParam(name = "name", required = false) String name) {
        if (page < 1) {
            page = 1;
        }
        List<RoleDTO> roleList = roleService.getList(new Role().setName(name), page, pageSize);
        PageInfo<RoleDTO> rolePageInfo = new PageInfo<>(roleList);
        return BaseListResult.success(roleList, rolePageInfo.getTotal());
    }

    @PutMapping
    public JsonResult updateRole(@RequestBody RoleDTO roleDTO) {
        return JsonResult.success("更新角色成功", roleService.updateRole(roleDTO));
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteMenu(@PathVariable("id") Long id) {
        try {
            roleService.deleteRole(id);
            return JsonResult.success("删除角色成功", (Role) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    /**
     * 保存角色权限
     *
     * @param roleMenuObj
     * @return
     */
    @PostMapping("/saveRoleMenus")
    public JsonResult saveRole(@RequestBody TempRoleMenu roleMenuObj) {
        List<Long> roleMenus = roleMenuObj.getRoleMenus();
        Role roleById = roleDao.getById(roleMenuObj.getRoleId());
        if (roleById == null) {
            return JsonResult.failure("角色不存在！");
        }
        roleDao.clearMenus(roleMenuObj.getRoleId());
        if (roleMenuObj.getRoleMenus() != null && roleMenuObj.getRoleMenus().size() > 0) {
            roleDao.saveRoleMenus(roleMenuObj);
        }
        return JsonResult.success("保存成功", null);
    }

    @GetMapping("/getRoleMenus/{roleId}")
    public JsonResult getRoleMenus(@PathVariable("roleId") Long roleId) {
        Role roleById = roleDao.getById(roleId);
        if (roleById == null) {
            return JsonResult.failure("角色不存在！");
        }


//            val roleMenus = roleDao.getRoleMenus(roleId);

//        return Result.success("获取角色菜单成功",
//                roleMenus.stream().map(RoleMenuRel::getMenuId).collect(Collectors.toList()));
        return JsonResult.success("");
    }

    @GetMapping("/getMenusByUserInfo")
    public JsonResult getMenusByUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userByName = userService.getUserByName(userName);
        roleService.getMenuByUser(userByName);
        return JsonResult.success("获取用户信息成功", userByName);
    }


    @PostMapping("/bindRoles")
    public JsonResult bindRoles(@RequestBody UserController.UserIdRoles userIdRoles) {
        System.out.println("userIdRoles");
        System.out.println(userIdRoles);
//        roleDao.cleanRolesByUserId(userIdRoles.getUserId());
//        if (userIdRoles.getRoleIds().equals(null) || userIdRoles.getRoleIds().size() > 0) {
//            roleDao.bindRolesByUserId(userIdRoles);
//        }
        return JsonResult.success("分配角色成功", null);
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
