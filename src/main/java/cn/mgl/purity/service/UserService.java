package cn.mgl.purity.service;

import cn.mgl.purity.dao.RoleDao;
import cn.mgl.purity.dao.UserDao;
import cn.mgl.purity.exception.ServiceBusinessException;
import cn.mgl.purity.model.persistent.RoleDTO;
import cn.mgl.purity.model.persistent.Role;
import cn.mgl.purity.model.persistent.User;
import cn.mgl.purity.model.persistent.UserDto;
import cn.mgl.purity.model.service.Account;
import cn.mgl.purity.model.service.result.JsonResult;
import cn.mgl.purity.utils.JWTUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
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
        userDao.update(user);
        return userDao.getUserById(user.getId());
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用戶名
     * @return 用户信息
     */
    public User getUserByName(String username) {
        return userDao.getUserByUsername(username);
    }

    /**
     * 根据用户id查询用户
     *
     * @param id 用戶Id
     * @return 用户信息
     */
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    /**
     * 使用账号进行登录
     *
     * @param user 用戶实体
     * @return 用户信息
     */
    public JsonResult login(User user) {
        User dbUser = getUserByName(user.getUsername());
        String loginUsername = user.getUsername();
        if (Objects.isNull(dbUser)) {
            throw new ServiceBusinessException("用户【" + loginUsername + "】不存在");
        }
        if (!bCryptPasswordEncoder.matches(user.getEncryptedPassword(), dbUser.getEncryptedPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        if (!dbUser.getStatus()) {
            throw new ServiceBusinessException("登录失败，账号被封禁，请联系管理员");
        }

        // 往redis中存refreshToken
//        return JsonResult.success("登录成功", cacheInRedis(dbUser));
        Map<String, String> map = new HashMap<>();
        map.put("username", dbUser.getUsername());
        map.put("userId", dbUser.getId().toString());
        HashMap<String, Object> stringObjectHashMap = JWTUtils.generateToken(map);
        return JsonResult.success("登录成功", stringObjectHashMap);
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

    public static String getRandomChineseCharacters(int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    /**
     * 用户注册
     *
     * @param account 账号密码
     * @return 注册结果
     */
    public JsonResult register(Account account) {
        userDao.register(new User().setUsername(account.getUsername())
                        .setStatus(account.getStatus() != null ? account.getStatus() : true)
                        .setNickname(getRandomChineseCharacters(4))
                        .setEncryptedPassword(bCryptPasswordEncoder.encode(account.getPassword()))
//                    .setUserType(UserTypeEnum.DEFAULT_USER)
        );
        return JsonResult.success("注册成功!");
    }

    /**
     * 获取用户列表
     *
     * @param user     用户实体
     * @param page     第几页
     * @param pageSize 每页多少条
     * @return 用户列表结果
     */
    public List<UserDto> getList(User user, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return userDao.getList(user);
    }

    /**
     * 删除用户
     *
     * @param id 用户Id
     * @return JSON响应实体
     */
    public JsonResult deleteUser(Long id) {
        User userById = userDao.getUserById(id);
        if (userById == null) {
            return JsonResult.failure("用户不存在");
        }
        userDao.deleteUser(id);
        return JsonResult.success("删除成功");
    }

    /**
     * 改变用户状态，传入1表示改为正常，传入0表示封号
     *
     * @param status 状态码
     * @param userId 用户ID
     * @return JSON响应实体
     */
    public JsonResult changeStatus(byte status, Long userId) {
        User userById = userDao.getUserById(userId);
        if (userById == null) {
            return JsonResult.failure("用户不存在");
        }
        userDao.changeStatus(status, userId);
        return JsonResult.success("用户【" + userById.getUsername() + "】账号状态修改成功");
    }

    public List<Role> getUserRoles(Long userId) {
//        List<Long> roleIds = userDao.findRolesByUserId(userId);
//        List<Role> roles = new ArrayList<>();
//        roleIds.forEach(roleId -> {
//            Role roleById = roleDao.getById(roleId);
//            roles.add(roleById);
//        });
//        return roles;
        return null;
    }

    public List<RoleDTO> getUserRolesByUserId(List<Long> userIds) {
//        return roleDao.getUserRolesByUserId(userIds);
        return null;
    }


    public void updatePassword(User user) {
        userDao.updatePassword(user);
    }

    public JsonResult refreshToken(String s) {
        return JsonResult.success("刷新成功", null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user,ArrayList<Long> roleIds) {
        User userByUsername = userDao.getUserByUsername(user.getUsername());
        if (userByUsername != null) {
            throw new ServiceBusinessException("创建用户失败，用户已存在");
        }
        userDao.register(user);
        userDao.bindRole(user.getId(),roleIds);
    }

    public void bindRole(Long userId, ArrayList<Long> roleIds) {
        userDao.bindRole(userId, roleIds);
    }

    public void updateRole(Long userId, ArrayList<Long> roleIds) {
        userDao.updateRole(userId, roleIds);
    }
    public void cleanRole(Long userId) {
        userDao.cleanRole(userId);
    }
}
