package com.example.demo.dao;

import com.example.demo.model.presistent.User;
import com.example.demo.utils.MappingUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户模块数据持久层
 */
@Repository
public class UserDao {
    private final SqlSession sqlSession;

    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void register(User user) {
        sqlSession.insert("register", user);
    }

    public User findUserById(String userId) {
        return sqlSession.selectOne("getUserById", MappingUtils.asMap("userId", userId));
    }

    public User findUserByUsername(String username) {
        return sqlSession.selectOne("getUserByUsername", MappingUtils.asMap("username", username));
    }

    public void updateUser(User user) {
        sqlSession.update("updateUser", user);
    }

    public List<User> getList(User user) {
        return sqlSession.selectList("getPage", user);
    }

    public void deleteUser(Integer id) {
        sqlSession.delete("deleteUser", MappingUtils.asMap("id", id));
    }
}