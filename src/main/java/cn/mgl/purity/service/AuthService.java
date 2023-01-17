package cn.mgl.purity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import cn.mgl.purity.model.persistent.User;
import cn.mgl.purity.model.service.RefreshToken;
import cn.mgl.purity.model.service.result.JsonResult;
import cn.mgl.purity.utils.JWTUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(userService.getUserByName(name));
    }

    public JsonResult refreshToken(RefreshToken refreshTokenObj) {
        String refreshToken = refreshTokenObj.getRefreshToken();
        String type = refreshTokenObj.getTokenGrantType().getType();
        JWTUtils.verify(refreshToken);
        Map<String, String> map = new HashMap<>();
        DecodedJWT decode = JWT.decode(refreshToken);
        String userId = decode.getClaim("userId").asString();
        String username = decode.getClaim("username").asString();
        map.put("userId", userId);
        map.put("username", username);
        HashMap<String, Object> tokenResult = JWTUtils.generateToken(map);

//        HashOperations<String, Object, Object> redisHashMap = redisTemplate.opsForHash();
//        if (redisHashMap.hasKey("refresh_token", userId)) {
//            log.info("redis已有改值");
//            String redisRefreshToken = redisHashMap.get(REFRESH_TOKEN, userId).toString();
//            if (!redisRefreshToken.equals(refreshToken)) {
//                return Result.failure("refresh_token已被使用过，刷新token失败");
//            }
//        }
//        // 第一次则直接添加
//        redisHashMap.put(REFRESH_TOKEN, userId, tokenResult.get(REFRESH_TOKEN));
        return JsonResult.success("刷新令牌成功", tokenResult);
    }
}
