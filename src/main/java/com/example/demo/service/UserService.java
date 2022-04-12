package com.example.demo.service;

import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.RoleDTO;
import com.example.demo.model.presistent.Role;
import com.example.demo.model.presistent.User;
import com.example.demo.model.presistent.UserBuilder;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.UserResult;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 更新用户
     *
     * @param user 用戶实体
     * @return 用户信息
     */
    public User updateUser(User user) {
        userDao.updateUser(user);
        return userDao.findUserById(user.getId());
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用戶名
     * @return 用户信息
     */
    public User getUserByName(String username) {
        return userDao.findUserByUsername(username);
    }

    /**
     * 根据用户id查询用户
     *
     * @param id 用戶Id
     * @return 用户信息
     */
    public User getUserById(Long id) {
        return userDao.findUserById(id);
    }

    /**
     * 使用账号进行登录
     *
     * @param user 用戶实体
     * @return 用户信息
     */
    public User login(User user) {
        User targetUser = getUserByName(user.getUsername());
        if (targetUser == null) {
            throw new UsernameNotFoundException("用户" + user.getUsername() + "不存在");
        }
        if (!bCryptPasswordEncoder.matches(user.getEncryptedPassword(), targetUser.getEncryptedPassword())) {
            throw new BadCredentialsException("账号或密码错误");
        }
        return user;
    }

    /**
     * 用户注册
     *
     * @param username          账号
     * @param encryptedPassword 密码
     */
    public void register(String username, String encryptedPassword) {
        User registerUser = UserBuilder.anUser()
                .withUsername(username)
                .withEncryptedPassword(bCryptPasswordEncoder.encode(encryptedPassword))
                .build();
        userDao.register(registerUser);
    }

    /**
     * 获取用户列表
     *
     * @param user     用户实体
     * @param page     第几页
     * @param pageSize 每页多少条
     * @return 用户列表结果
     */
    public List<User> getList(User user, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return userDao.getList(user);
    }

    /**
     * 删除用户
     *
     * @param id 用户Id
     * @return JSON响应实体
     */
    public Result deleteUser(Long id) {
        User userById = userDao.findUserById(id);
        if (userById == null) {
            return UserResult.failure("用户不存在");
        }
        userDao.deleteUser(id);
        return UserResult.success("删除成功");
    }

    /**
     * 改变用户状态，传入1表示改为正常，传入0表示封号
     *
     * @param status 状态码
     * @param userId 用户ID
     * @return JSON响应实体
     */
    public Result changeStatus(byte status, Long userId) {
        User userById = userDao.findUserById(userId);
        if (userById == null) {
            return UserResult.failure("用户不存在");
        }
        userDao.changeStatus(status, userId);
        return UserResult.success("用户【" + userById.getUsername() + "】账号状态修改成功");
    }

    public List<Role> getUserRoles(Long userId) {
        List<Long> roleIds = userDao.findRolesByUserId(userId);
        List<Role> roles = new ArrayList<>();
        roleIds.forEach(roleId->{
            val roleById = roleDao.getRoleById(roleId);
            roles.add(roleById);
        });
        return roles;
    }

    public List<RoleDTO> getUserRolesByUserId(List<Long> userIds) {
        return roleDao.getUserRolesByUserId(userIds);
    }


}