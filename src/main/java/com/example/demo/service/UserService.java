package com.example.demo.service;

import com.example.demo.dao.RoleDao;
import com.example.demo.dao.UserDao;
import com.example.demo.exception.CustomException;
import com.example.demo.model.persistent.RoleDTO;
import com.example.demo.model.persistent.Role;
import com.example.demo.model.persistent.User;
import com.example.demo.model.service.Account;
import com.example.demo.model.service.result.Result;
import com.example.demo.model.service.result.TokenResult;
import com.example.demo.model.service.result.UserResult;
import com.example.demo.utils.JWTUtils;
import com.github.pagehelper.PageHelper;
import lombok.val;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserService {
    private final static String REFRESH_TOKEN = "refresh_token";

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserService(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder, RedisTemplate<String, Object> redisTemplate) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.redisTemplate = redisTemplate;
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
    public Result login(User user) {
        User dbUser = getUserByName(user.getUsername());
        String loginUsername = user.getUsername();
        if (Objects.isNull(dbUser)) {
            throw new CustomException("用户【" + loginUsername + "】不存在");
        }
        if (!dbUser.getStatus()) {
            throw new CustomException("登录失败，账号被封禁，请联系管理员");
        }
        if (!bCryptPasswordEncoder.matches(user.getEncryptedPassword(), dbUser.getEncryptedPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        // 往redis中存refreshToken
        return TokenResult.success("登录成功", cacheInRedis(dbUser));
    }

    /**
     * 传入用户，生成token，refreshToken，以及缓存到redis中
     *
     * @param dbUser 用户实体
     * @return 包含token，refreshToken，token过期时间的结果集
     */
    private HashMap<String, Object> cacheInRedis(User dbUser) {
        String dbId = dbUser.getId().toString();
        Map<String, String> map = new HashMap<>();
        map.put("username", dbUser.getUsername());
        map.put("userId", dbId);
        HashMap<String, Object> stringObjectHashMap = JWTUtils.generateToken(map);
        HashOperations<String, Object, Object> redisHashMap = redisTemplate.opsForHash();
        redisHashMap.put(REFRESH_TOKEN, dbId, stringObjectHashMap.get(REFRESH_TOKEN));
        return stringObjectHashMap;
    }


    /**
     * 用户注册
     *
     * @param account 账号密码
     * @return 注册结果
     */
    public Result register(Account account) {
        try {
            userDao.register(new User().setUsername(account.getUsername()).setEncryptedPassword(bCryptPasswordEncoder.encode(account.getPassword())));
            return Result.success("注册成功!");
        } catch (DuplicateKeyException e) {
            return Result.failure("用户已注册");
        }
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
        roleIds.forEach(roleId -> {
            val roleById = roleDao.getRoleById(roleId);
            roles.add(roleById);
        });
        return roles;
    }

    public List<RoleDTO> getUserRolesByUserId(List<Long> userIds) {
        return roleDao.getUserRolesByUserId(userIds);
    }


}