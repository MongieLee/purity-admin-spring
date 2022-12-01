package com.example.demo.dao;

import com.example.demo.model.persistent.User;
import com.example.demo.model.service.Account;
import com.example.demo.utils.MappingUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户模块数据持久层
 */
@Repository
public class UserDao {
    private final SqlSession sqlSession;
    private static final String namespace = "cn.example.demo.User.";

    private String getMapperName(String mapperName) {
        return namespace + mapperName;
    }


    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void register(User user) {
        sqlSession.insert("register", user);
    }

    public User findUserById(Long userId) {
        return sqlSession.selectOne("getUserById", MappingUtils.asMap("userId", userId));
    }

    public User findUserByUsername(String username) {
        return sqlSession.selectOne("getUserByUsername", MappingUtils.asMap("username", username));
    }

    public void updateUser(User user) {
        sqlSession.update("updateUser", user);
    }
    public void updatePassword(User user) {
        sqlSession.update("updatePassword", user);
    }

    public List<User> getList(User user) {
        return sqlSession.selectList(getMapperName("getPage"), user);
    }

    public void deleteUser(Long id) {
        sqlSession.delete("deleteUser", MappingUtils.asMap("id", id));
    }

    public void changeStatus(byte status, Long userId) {
        sqlSession.update("changeStatus", MappingUtils.asMap("status", status, "userId", userId));
    }

    public List<Long> findRolesByUserId(@Param("userId") Long userId) {
        return sqlSession.selectList(getMapperName("findRolesByUserId"), userId);
    }
}
