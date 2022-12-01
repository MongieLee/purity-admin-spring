package com.example.demo.controller;

import com.example.demo.converter.p2s.UserP2SConverter;
import com.example.demo.model.persistent.RoleDTO;
import com.example.demo.model.persistent.Role;
import com.example.demo.model.persistent.User;
import com.example.demo.model.persistent.UserDto;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.JsonResult;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageInfo;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserP2SConverter userP2SConverter;

    public UserController(UserService userService, UserP2SConverter userP2SConverter) {
        this.userService = userService;
        this.userP2SConverter = userP2SConverter;
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
        if (bCryptPasswordEncoder.matches(oldPassword,userByName.getEncryptedPassword())) {
            System.out.println("旧密码相同，可以修改");
            userByName.setEncryptedPassword(bCryptPasswordEncoder.encode(newPassword));
            userService.updatePassword(userByName);
            return JsonResult.success("修改密码成功");
        } else {
            System.out.println();
            return JsonResult.failure("修改失败，密码错误");
        }
    }

    ;

    @PutMapping("/{id}")
    public JsonResult updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            User dbUser = userService.getUserById(id);
            if (dbUser == null) {
                return JsonResult.failure("用户不存在");
            }
            dbUser.setAvatar(user.getAvatar());
            return JsonResult.success("更新用户信息成功", userService.updateUser(dbUser));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/list")
    public JsonResult getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                              @RequestParam(name = "username", required = false) String username) {
        if (page < 1) {
            page = 1;
        }
        User user = new User().setUsername(username);
        List<User> list = userService.getList(user, page, pageSize);
        PageInfo<User> userPageInfo = new PageInfo<>(list);
        List<UserDto> result = new ArrayList<>();

        List<Long> userIdList = list.stream().map(User::getId).collect(Collectors.toList());
        List<RoleDTO> roleList = userService.getUserRolesByUserId(userIdList);
        list.forEach(currentUser -> {
//            List<Role> userRoles = userService.getUserRoles(currentUser.getId());
            List<Role> userRoles = roleList.stream().filter(item -> item.getUserId().equals(currentUser.getId()))
                    .map(item -> {
                        Role roleResult = new Role();
                        BeanUtils.copyProperties(item, roleResult);
                        return roleResult;
                    }).collect(Collectors.toList());
            AtomicReference<String> roleNames = new AtomicReference<>();
            List<Long> roleIds = userRoles.stream().map(value -> {
                String name = value.getName();
                roleNames.set(Objects.isNull(roleNames.get()) ? name : roleNames.get() + "，" + name);
                return value.getId();
            }).collect(Collectors.toList());
            UserDto userDto = userP2SConverter.convert(currentUser);
            userDto.setRoleIds(roleIds).setRoleNames(roleNames.get());
            result.add(userDto);
        });
        return BaseListResult.success(result, userPageInfo.getTotal());
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
