package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.presistent.User;
import com.example.demo.model.presistent.UserBuilder;
import com.example.demo.model.service.result.UserResult;
import com.github.pagehelper.PageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return 用户信息
     */
    public User updateUser(User user) {
        userDao.updateUser(user);
        return userDao.findUserById(user.getId());
    }

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return 用户信息
     */
    public User getUserByName(String username) {
        return userDao.findUserByUsername(username);
    }

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return 用户信息
     */
    public User getUserById(String id) {
        return userDao.findUserById(id);
    }

    /**
     * 使用账号进行登录
     *
     * @param user
     * @return 用户信息
     */
    public User login(User user) {
        User targetUser = getUserByName(user.getUsername());
        if (targetUser == null) {
            throw new UsernameNotFoundException(user.getUsername() + "不存在");
        }
        if (!bCryptPasswordEncoder.matches(user.getEncryptedPassword(), targetUser.getEncryptedPassword())) {
            throw new BadCredentialsException("账号名或密码错误");
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
     * @param user
     * @param page
     * @param pageSize
     * @return
     */
    public List<User> getList(User user, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> list = userDao.getList(user);
        return list;
    }

    /**
     * 删除用户
     *
     * @param id 用户Id
     * @return
     */
    public UserResult deleteUser(Integer id) {
        User userById = userDao.findUserById(id.toString());
        if (userById == null) {
            return UserResult.failure("用户不存在");
        }
        userDao.deleteUser(id);
        return UserResult.success("删除成功");
    }
}