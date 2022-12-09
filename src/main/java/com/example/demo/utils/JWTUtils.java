package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JWTUtils {
    private final static String SIGN = "baiz!QWE@#$";

    /**
     * 生成token
     *
     * @param map JWT的payload自定义参数设置
     * @return token 令牌
     */
    public static HashMap<String, Object> generateToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        // 7天过期
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("token", builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SIGN)));
        instance.add(Calendar.DATE, 1);
        objectObjectHashMap.put("refresh_token", builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SIGN)));
        objectObjectHashMap.put("expires", instance.getTimeInMillis());
        return objectObjectHashMap;
    }

    /**
     * 校验token令牌合法性
     *
     * @param token 令牌
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    public static String refreshToken(String token, Date refreshDate) {
        String result = null;
        return result;
    }

    public static Boolean verifyRefreshToken(String refreshToken) {
        JWT.require(Algorithm.HMAC256(SIGN)).build().verify(refreshToken);
        return false;
    }

    /**
     * 获取token信息
     */
    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
}
