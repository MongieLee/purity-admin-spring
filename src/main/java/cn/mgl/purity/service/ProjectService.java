package cn.mgl.purity.service;

import cn.mgl.purity.dao.ProjectDao;
import cn.mgl.purity.model.persistent.Project;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProjectService {

    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public List<Project> getList(Project project,Integer page,Integer pageSize)
    {
        PageHelper.startPage(page,pageSize);
        return projectDao.getList(project);
    }

    public Project updateProject(Long id, Project project) {
        Project projectById = getProjectById(id);
        if (projectById == null) {
            throw new RuntimeException("项目不存在");
        }
        projectDao.updateProject(project.setId(id));
        return getProjectById(id);
    }

    public void deleteProject(Long id) {
        Project projectById = getProjectById(id);
        if (projectById == null) {
            throw new RuntimeException("项目不存在");
        }
        projectDao.deleteProject(id);
    }

    public Project createProject(Project project) {
        projectDao.createProject(project);
        return getProjectByName(project.getName());
    }

    public Project finProjectById(Long id){
        return projectDao.getProjectById(id);
    }

    public Project getProjectById(Long id) {
        Project projectById = projectDao.getProjectById(id);
        return projectById;
    }

    public Project getProjectByName(String name) {
        Project projectByName = projectDao.getProjectByName(name);
        return projectByName;
    }
}
