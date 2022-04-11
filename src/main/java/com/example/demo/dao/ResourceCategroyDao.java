package com.example.demo.dao;

import com.example.demo.model.presistent.Resource;
import com.example.demo.model.presistent.ResourceCategroy;
import com.example.demo.utils.MappingUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源分类模块数据持久层
 */
@Repository
public class ResourceCategroyDao {
    private final SqlSession sqlSession;
    private static final String namespace = "cn.example.demo.ResourceCategroy.";

    public ResourceCategroyDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String getMapperName(String mapperName) {
        return namespace + mapperName;
    }

    public ResourceCategroy getResourceCategroyById(Long id) {
        return sqlSession.selectOne("getResourceCategroyById", MappingUtils.asMap("id", id));
    }

    public ResourceCategroy getResourceCategroyByName(String name) {
        return sqlSession.selectOne("getResourceCategroyByName", MappingUtils.asMap("name", name));
    }

    public void createResourceCategroy(ResourceCategroy resourceCategroy) {
        sqlSession.insert(getMapperName("createResourceCategroy"), resourceCategroy);
    }

    public List<ResourceCategroy> getAll() {
        return sqlSession.selectList(getMapperName("getAll"));
    }

    public void updateResourceCategroy(ResourceCategroy resourceCategroy) {
        sqlSession.update(getMapperName("updateResourceCategroy"), resourceCategroy);
    }

    public void deleteResourceCategroy(Long id) {
        sqlSession.delete(getMapperName("deleteResourceCategroy"), MappingUtils.asMap("id", id));
    }

    public List<ResourceCategroy> getList(ResourceCategroy resourceCategroy) {
        return sqlSession.selectList(getMapperName("getList"), resourceCategroy);
    }
}