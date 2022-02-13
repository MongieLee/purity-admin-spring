package com.example.demo.controller;

import com.example.demo.model.presistent.User;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.LoginResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.UserResult;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/info")
    public Result getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userByName = userService.getUserByName(userName);
        return LoginResult.success("获取用户信息成功", userByName);
    }

    @PutMapping("/{id}")
    public UserResult updateUser(@PathVariable("id") String id, @RequestBody User user) {
        System.out.println(id);
        try {
            User dbUser = userService.getUserById(id);
            if (dbUser == null) {
                return UserResult.failure("用户不存在");
            }
            dbUser.setAvatar(user.getAvatar());
            return UserResult.success(userService.updateUser(dbUser));
        } catch (Exception e) {
            e.printStackTrace();
            return UserResult.failure(e.getMessage());
        }
    }

    @GetMapping("/users")
    public Object updateUser(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize
            , @RequestParam(name = "username", required = false) String username) {
        if (page == null || page < 1) {
            page = 1;
        }
        User user = new User().setUsername(username);
        List<User> list = userService.getList(user, page, pageSize);
        PageInfo<User> userPageInfo = new PageInfo<>(list);
        return BaseListResult.success(list, userPageInfo.getTotal());
    }

    @DeleteMapping("/{id}")
    public Object updateUser(@PathVariable("id") Integer id) {
        return userService.deleteUser(id);
    }
}
