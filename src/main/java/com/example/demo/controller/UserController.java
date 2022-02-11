package com.example.demo.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.demo.entity.LoginResult;
import com.example.demo.entity.TableResult;
import com.example.demo.entity.User;
import com.example.demo.entity.UserResult;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserServiceImpl userService;
    private final AuthService authService;

    public UserController(UserServiceImpl userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/user/getUserInfo")
    public LoginResult getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User userByName = userService.getUserByName(userName);
        return LoginResult.success("获取用户信息成功", userByName);
    }

    @PutMapping("/user/{id}")
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

    @PostMapping("/user/users")
    public Object updateUser(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        List<User> list = userService.getList(new User(), page, pageSize);
        PageInfo<User> userPageInfo = new PageInfo<>(list);
        return TableResult.success(userPageInfo.getPageNum(), userPageInfo.getPageSize(), userPageInfo.getTotal(), list);
    }

    @DeleteMapping("/user/{id}")
    public Object updateUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return UserResult.success("删除成功");
    }
}
