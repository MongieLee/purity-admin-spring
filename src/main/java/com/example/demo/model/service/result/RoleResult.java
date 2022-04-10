package com.example.demo.model.service.result;


import com.example.demo.model.presistent.Role;
import com.example.demo.model.service.MenuDto;

import java.util.List;

/**
 * 菜单结果工厂方法
 */
public class RoleResult {
    public static Result success(String msg, Role role) {
        return new Result(200, true, msg, role);
    }
    public static Result success(String msg, List<Long> menus) {
        return new Result(200, true, msg, menus);
    }

    public static Result success(String msg) {
        return new Result(200, true, msg, null);
    }

    public static Result failure(String msg) {
        return new Result(400, false, msg, null);
    }
}
