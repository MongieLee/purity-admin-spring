package com.example.demo.controller;

import com.example.demo.model.presistent.Project;
import com.example.demo.model.service.result.MenuResult;
import com.example.demo.model.service.result.ProjectResult;
import com.example.demo.model.service.result.Result;
import com.example.demo.service.ProjectService;
import org.springframework.web.bind.annotation.*;

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
        try {
            Project projectByName = projectService.getProjectByName(project.getName());
            if (projectByName != null) {
                return ProjectResult.failure("创建失败，项目已存在");
            }
            projectService.createProject(project);
            return ProjectResult.success("创建项目成功", projectService.getProjectByName(project.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return ProjectResult.failure(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getProject(@PathVariable Long id) {
        try {
            return ProjectResult.success("获取项目成功", projectService.getProjectById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ProjectResult.failure(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result updateProject(@PathVariable("id") Long id, @RequestBody Project project) {
        try {
            return ProjectResult.success("更新项目成功", projectService.updateProject(id, project));
        } catch (Exception e) {
            e.printStackTrace();
            return ProjectResult.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteProject(@PathVariable("id") Long id) {
        try {
            projectService.deleteProject(id);
            return ProjectResult.success("删除项目成功", (Project) null);
        } catch (Exception e) {
            e.printStackTrace();
            return ProjectResult.failure(e.getMessage());
        }
    }
}
