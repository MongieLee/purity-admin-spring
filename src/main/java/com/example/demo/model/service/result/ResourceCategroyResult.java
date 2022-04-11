package com.example.demo.model.service.result;


import com.example.demo.model.presistent.Resource;
import com.example.demo.model.presistent.ResourceCategroy;
import com.example.demo.model.service.ResourceDto;

import java.util.List;

/**
 * 菜单结果工厂方法
 */
public class ResourceCategroyResult {
    public static Result success(String msg, ResourceCategroy resource) {
        return new Result(200, true, msg, resource);
    }

    public static Result success(String msg, List<Resource> resourceList) {
        return new Result(200, true, msg, resourceList);
    }

    public static Result failure(String msg) {
        return new Result(400, false, msg, null);
    }
}
