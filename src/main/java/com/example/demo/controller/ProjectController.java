package com.example.demo.controller;

import com.example.demo.model.persistent.Project;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.ProjectService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单模块
 */
@RestController
@RequestMapping("/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Result createProject(@RequestBody Project project) {
        System.out.println(project.toString());
        try {
            Project projectByName = projectService.getProjectByName(project.getName());
            if (projectByName != null) {
                return Result.failure("创建失败，项目已存在");
            }
            projectService.createProject(project);
            return Result.success("创建项目成功", projectService.getProjectByName(project.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    @GetMapping
    public Result getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
                          @RequestParam(name = "name", required = false) String name, @RequestParam(name = "userId",required = false) Long userId) {
        if (page == null || page < 1) {
            page = 1;
        }
        Project project = new Project().setName(name).setUserId(userId);
        List<Project> list = projectService.getList(project, page, pageSize);
        PageInfo<Project> projectPageInfo = new PageInfo<>(list);
        return BaseListResult.success(list, projectPageInfo.getTotal());
    }

    @GetMapping("/{id}")
    public Result getProject(@PathVariable Long id) {
        try {
            return Result.success("获取项目成功", projectService.getProjectById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result updateProject(@PathVariable("id") Long id, @RequestBody Project project) {
        try {
            return Result.success("更新项目成功", projectService.updateProject(id, project));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteProject(@PathVariable("id") Long id) {
        try {
            projectService.deleteProject(id);
            return Result.success("删除项目成功", (Project) null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }
}
