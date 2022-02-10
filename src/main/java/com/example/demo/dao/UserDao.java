package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {
    private final SqlSession sqlSession;

    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private Map<String, Object> asMap(Object... args) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            result.put(args[i].toString(), args[i + 1]);
        }
        return result;
    }

    public User login(User user) {
        return sqlSession.selectOne("login", user);
    }

    public void register(User user) {
        sqlSession.insert("register", user);
    }

    public User findUserById(Integer userId) {
        return sqlSession.selectOne("getUserById", asMap("userId", userId));
    }

    public User findUserByUsername(String username) {
        return sqlSession.selectOne("getUserByUsername", asMap("username", username));
    }
}