package com.example.demo.controller;

import com.example.demo.model.persistent.Department;
import com.example.demo.model.service.result.JsonResult;
import com.example.demo.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author MongieLee
 * @version 1.0
 * @date 2022/12/1 14:45
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @ApiOperation("新增部门")
    public JsonResult insert(@RequestBody Department dep) {
        return JsonResult.success("新增成功", departmentService.insert(dep));
    }

    @PutMapping
    @ApiOperation("更新部门")
    public JsonResult update(@RequestBody Department dep) {
        return JsonResult.success("更新成功", departmentService.update(dep));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除部门")
    public JsonResult delete(@PathVariable Long id) {
        departmentService.delete(id);
        return JsonResult.success("删除成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取部门信息")
    public JsonResult getById(@PathVariable Long id) {
        return JsonResult.success("获取部门成功", departmentService.getById(id));
    }

    @GetMapping
    @ApiOperation("获取全部部门列表")
    public JsonResult getAll() {
        return JsonResult.success("获取部门列表成功", departmentService.getAll());
    }

    @GetMapping("/tree")
    @ApiOperation("获取部门树")
    public JsonResult getTree() {
        return JsonResult.success("获取部门树成功", departmentService.getTree());
    }
}
