package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类
 */
public class JWTUtils {
    private final static String SIGN = "baiz!QWE@#$";

    /**
     * 生成token
     *
     * @param map JWT的payload自定义参数设置
     * @return token 令牌
     */
    public static String generateToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); // 7天过期
        JWTCreator.Builder builder = JWT.create();
        map.forEach((key, value) -> builder.withClaim(key, value));
        builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SIGN)); // 指定令牌过期时间
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SIGN));
        return token;
    }

    /**
     * 校验token令牌合法性
     *
     * @param token 令牌
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    /**
     * 获取token信息
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        return verify;
    }
}
