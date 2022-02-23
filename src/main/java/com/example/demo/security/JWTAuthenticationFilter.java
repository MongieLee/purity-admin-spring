package com.example.demo.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;

@Component
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        // 相当于匿名访问，配合白名单使用
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        JWTUtils.verify(token);
        DecodedJWT tokenInfo = JWTUtils.getTokenInfo(token);
        String username = tokenInfo.getClaim("username").asString();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, new TreeSet<>());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
        // 将token中获取的用户名封装成UsernamePasswordAuthenticationToken对象
        // 之后交给SecurityContextHolder参数传递authentication对象
        // 后续security就能获取到当前登录的用户信息，完成用户认证
        // 当认证失败的时候会进入AuthenticationEntryPoint，于是我们自定义认证失败返回的数据
    }
}
