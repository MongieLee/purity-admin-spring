package com.example.demo.controller;

import com.example.demo.model.persistent.Project;
import com.example.demo.model.service.result.BaseListResult;
import com.example.demo.model.service.result.JsonResult;
import com.example.demo.service.ProjectService;
import com.github.pagehelper.PageInfo;
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
    public JsonResult createProject(@RequestBody Project project) {
        System.out.println(project.toString());
        try {
            Project projectByName = projectService.getProjectByName(project.getName());
            if (projectByName != null) {
                return JsonResult.failure("创建失败，项目已存在");
            }
            projectService.createProject(project);
            return JsonResult.success("创建项目成功", projectService.getProjectByName(project.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @GetMapping
    public JsonResult getList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
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
    public JsonResult getProject(@PathVariable Long id) {
        try {
            return JsonResult.success("获取项目成功", projectService.getProjectById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public JsonResult updateProject(@PathVariable("id") Long id, @RequestBody Project project) {
        try {
            return JsonResult.success("更新项目成功", projectService.updateProject(id, project));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteProject(@PathVariable("id") Long id) {
        try {
            projectService.deleteProject(id);
            return JsonResult.success("删除项目成功", (Project) null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
    }
}
