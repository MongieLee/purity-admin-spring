package com.example.demo.model.service.result;


import com.example.demo.model.service.MenuDto;

import java.util.List;

/**
 * 菜单结果工厂方法
 */
public class MenuResult {
    public static Result success(String msg, MenuDto menu) {
        return new Result(200, true, msg, menu);
    }

    public static Result success(String msg, List<MenuDto> menuList) {
        return new Result(200, true, msg, menuList);
    }

    public static Result failure(String msg) {
        return new Result(400, false, msg, null);
    }
}
