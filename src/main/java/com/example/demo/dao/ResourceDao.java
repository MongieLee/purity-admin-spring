package com.example.demo.dao;

import com.example.demo.model.persistent.Resource;
import com.example.demo.model.service.ResourceDto;
import com.example.demo.utils.MappingUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单模块数据持久层
 */
@Repository
public class ResourceDao {
    private final SqlSession sqlSession;
    private static final String namespace = "cn.example.demo.Resource.";

    public ResourceDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String getMapperName(String mapperName) {
        return namespace + mapperName;
    }

    public Resource getResourceById(Long id) {
        return sqlSession.selectOne("getResourceById", MappingUtils.asMap("id", id));
    }

    public Resource getResourceByName(String name) {
        return sqlSession.selectOne("getResourceByName", MappingUtils.asMap("name", name));
    }

    public void createResource(Resource Resource) {
        sqlSession.insert("createResource", Resource);
    }

    public List<Resource> getAll() {
        return sqlSession.selectList(getMapperName("getAll"));
    }

    public void updateResource(Resource Resource) {
        sqlSession.update(getMapperName("updateResource"), Resource);
    }

    public void deleteResource(Long id) {
        sqlSession.delete(getMapperName("deleteResource"), MappingUtils.asMap("id", id));
    }

    public List<ResourceDto> getList(ResourceDto resource) {
        System.out.println(resource);
        return sqlSession.selectList(getMapperName("getList"), resource);
    }
}