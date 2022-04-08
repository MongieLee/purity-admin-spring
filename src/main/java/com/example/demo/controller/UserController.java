package com.example.demo.controller;

import com.example.demo.model.presistent.User;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.LoginResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.UserResult;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public Result getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userByName = userService.getUserByName(userName);
        return LoginResult.success("获取用户信息成功", userByName);
    }

    @PutMapping("/{id}")
    public Result updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            User dbUser = userService.getUserById(id);
            if (dbUser == null) {
                return UserResult.failure("用户不存在");
            }
            dbUser.setAvatar(user.getAvatar());
            return UserResult.success("更新用户信息成功", userService.updateUser(dbUser));
        } catch (Exception e) {
            e.printStackTrace();
            return UserResult.failure(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam(name = "username", required = false) String username) {
        if (page == null || page < 1) {
            page = 1;
        }
        User user = new User().setUsername(username);
        List<User> list = userService.getList(user, page, pageSize);
        PageInfo<User> userPageInfo = new PageInfo<>(list);
        return BaseListResult.success(list, userPageInfo.getTotal());
    }

    @DeleteMapping("/{id}")
    public Result updateUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/status")
    public Result changeStatus(@RequestBody Map<String, Object> statusAndId) {
        byte status = ((Number) statusAndId.get("status")).byteValue();
        Long userId = ((Number) statusAndId.get("userId")).longValue();
        System.out.println(status);
        System.out.println(userId);
        try {
            return userService.changeStatus(status, userId);
        } catch (Exception e) {
            return UserResult.failure(e.getMessage());
        }
    }
}
