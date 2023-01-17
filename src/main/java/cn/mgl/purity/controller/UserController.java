package cn.mgl.purity.controller;

import cn.mgl.purity.converter.p2s.UserP2SConverter;
import cn.mgl.purity.model.persistent.User;
import cn.mgl.purity.model.persistent.UserDto;
import cn.mgl.purity.model.service.Account;
import cn.mgl.purity.model.service.result.BaseListResult;
import cn.mgl.purity.model.service.result.JsonResult;
import cn.mgl.purity.service.UserService;
import cn.mgl.purity.valid.AccountModelValid;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserP2SConverter userP2SConverter;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserService userService, UserP2SConverter userP2SConverter, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.userP2SConverter = userP2SConverter;
        this.encoder = encoder;
    }

    @PostMapping
    public JsonResult createUser(@Validated(AccountModelValid.Create.class) @RequestBody Account account) {
        User user = new User()
                .setDeptId(account.getDeptId())
                .setUsername(account.getUsername())
                .setNickname(account.getNickname())
                .setEncryptedPassword(encoder.encode(account.getPassword()))
                .setStatus(account.getStatus());
        userService.createUser(user,account.getRoleIds());
        return JsonResult.success("创建用户成功");
    }


    @PutMapping
    public JsonResult updateUser(@Validated @RequestBody User user) {
        User dbUser = userService.getUserById(user.getId());
        if (dbUser == null) {
            return JsonResult.failure("用户不存在");
        }
        userService.cleanRole(user.getId());
        dbUser.setUsername(user.getUsername()).setNickname(user.getNickname()).setStatus(user.getStatus()).setDeptId(user.getDeptId());
        userService.bindRole(user.getId(), null);
        return JsonResult.success("更新用户信息成功", userService.updateUser(dbUser));
    }

    @GetMapping("/info")
    public JsonResult getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userByName = userService.getUserByName(userName);
        return JsonResult.success("获取用户信息成功", userByName);
    }

    @GetMapping("/{id}")
    public JsonResult getUserInfo(@PathVariable Long id) {
        return JsonResult.success("获取用户信息成功", userService.getUserById(id));
    }

    @PutMapping("/updatePassword")
    public JsonResult updatePassword(@RequestBody Map mimas, @Autowired BCryptPasswordEncoder bCryptPasswordEncoder) {
        String newPassword = (String) mimas.get("newPassword");
        String oldPassword = (String) mimas.get("oldPassword");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User userByName = userService.getUserByName(username);
        if (bCryptPasswordEncoder.matches(oldPassword, userByName.getEncryptedPassword())) {
            System.out.println("旧密码相同，可以修改");
            userByName.setEncryptedPassword(bCryptPasswordEncoder.encode(newPassword));
            userService.updatePassword(userByName);
            return JsonResult.success("修改密码成功");
        } else {
            System.out.println();
            return JsonResult.failure("修改失败，密码错误");
        }
    }


    @GetMapping
    public JsonResult getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                              @RequestParam(name = "username", required = false) String username,
                              @RequestParam(required = false) Boolean status,
                              @RequestParam(name = "deptId", required = false) Long deptId) {
        if (page < 1) {
            page = 1;
        }
        User user = new User().setUsername(username).setDeptId(deptId).setStatus(status);
        List<UserDto> list = userService.getList(user, page, pageSize);
        PageInfo<UserDto> userPageInfo = new PageInfo<>(list);
        return BaseListResult.success(list, userPageInfo.getTotal());
    }

    @DeleteMapping("/{id}")
    public JsonResult updateUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/status")
    public JsonResult changeStatus(@RequestBody Map<String, Object> statusAndId) {
        Byte status = ((Number) statusAndId.get("status")).byteValue();
        Long userId = ((Number) statusAndId.get("userId")).longValue();
        System.out.println(status);
        System.out.println(userId);
        try {
            return userService.changeStatus(status, userId);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class UserIdRoles {
        private Long userId;
        private List<Long> roleIds;
    }

}
