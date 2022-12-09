package com.example.demo.dao;

import com.example.demo.model.persistent.User;
import com.example.demo.model.persistent.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户模块数据持久层
 */
@Repository
public interface UserDao {

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    int register(User user);

    /**
     * 根据id查询用户
     *
     * @param userId
     */
    User getUserById(Long userId);

    /**
     * 根据用户名查询id
     *
     * @param username
     */
    User getUserByUsername(String username);

    int update(User user);

    int updatePassword(User user);

    /**
     * 获取用户列表
     *
     * @param user
     * @return
     */
    List<UserDto> getList(User user);

    /**
     * 根据部门id获取用户列表
     *
     * @param depId 部门id
     * @return
     */
    List<User> getUsersOfDep(Long depId);

    /**
     * @param id
     * @return
     */
    int deleteUser(Long id);

    /**
     * 更改用户状态
     *
     * @param status 目标状态
     * @param userId 用户id
     * @return {int}
     */
    int changeStatus(byte status, Long userId);

    /**
     * 根据用户id获取用户相关权限
     *
     * @param userId
     * @return void
     */
    void findRolesByUserId(Long userId);

    /**
     * 用户绑定角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    int bindRole(Long userId, ArrayList<Long> roleIds);


    /**
     * 更新角色
     * @param userId
     * @param roleId
     * @return
     */
    int updateRole(Long userId, ArrayList<Long> roleId);

    int cleanRole(Long userId);
}
