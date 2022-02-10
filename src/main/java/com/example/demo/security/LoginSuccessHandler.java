package com.example.demo.security;

import com.example.demo.utils.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        // 生成jwt，放入到请求头中
        ServletOutputStream outputStream = response.getOutputStream();
        request.setAttribute("Authorization", JWTUtil.getTokenInfo("123"));
        outputStream.write(567);
        outputStream.flush();
        outputStream.close();
    }
}
