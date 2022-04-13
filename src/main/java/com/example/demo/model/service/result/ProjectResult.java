package com.example.demo.model.service.result;


import com.example.demo.model.persistent.Project;

import java.util.List;

/**
 * 菜单结果工厂方法
 */
public class ProjectResult {
    public static Result success(String msg, Project project) {
        return new Result(200, true, msg, project);
    }

    public static Result success(String msg, List<Project> menuList) {
        return new Result(200, true, msg, menuList);
    }

    public static Result failure(String msg) {
        return new Result(400, false, null, null);
    }
}
