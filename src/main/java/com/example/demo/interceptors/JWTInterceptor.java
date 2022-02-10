package com.example.demo.interceptors;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");
        try {
            JWTUtil.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "false");
            map.put("msg", "token无效");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
