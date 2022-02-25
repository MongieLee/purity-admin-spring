package com.example.demo.dao;

import com.example.demo.model.presistent.Menu;
import com.example.demo.model.presistent.Project;
import com.example.demo.utils.MappingUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单模块数据持久层
 */
@Repository
public class ProjectDao {
    private final SqlSession sqlSession;

    public ProjectDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Project getProjectById(Long id) {
        return sqlSession.selectOne("getProjectById", MappingUtils.asMap("id", id));
    }

    public Project getProjectByName(String name) {
        return sqlSession.selectOne("getProjectByName", MappingUtils.asMap("name", name));
    }

    public void createProject(Project project) {
        sqlSession.insert("createProject", project);
    }

    public List<Project> getAll() {
        return sqlSession.selectList("getAll");
    }

    public void updateProject(Project project) {
        sqlSession.update("updateProject", project);
    }

    public void deleteProject(Long id) {
        sqlSession.delete("deleteProject", MappingUtils.asMap("id", id));
    }
}