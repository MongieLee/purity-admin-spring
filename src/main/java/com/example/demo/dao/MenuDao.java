package com.example.demo.dao;

import com.example.demo.model.presistent.Menu;
import com.example.demo.utils.MappingUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单模块数据持久层
 */
@Repository
public class MenuDao {
    private final SqlSession sqlSession;

    public MenuDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Menu getMenuById(Long id) {
        return sqlSession.selectOne("getMenuById", MappingUtils.asMap("id", id));
    }

    public Menu getMenuByName(String name) {
        return sqlSession.selectOne("getMenuByName", MappingUtils.asMap("name", name));
    }

    public void createMenu(Menu menu) {
        sqlSession.insert("createMenu", menu);
    }

    public List<Menu> getAll() {
        return sqlSession.selectList("getAll");
    }

    public void updateMenu(Menu menu) {
        sqlSession.update("updateMenu", menu);
    }

    public void deleteMenu(Long id) {
        sqlSession.delete("deleteMenu", MappingUtils.asMap("id", id));
    }
}