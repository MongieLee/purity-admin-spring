package cn.mgl.purity.security;

import cn.mgl.purity.utils.JWTUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 暂时用不到
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        // 生成jwt，放入到请求头中
        ServletOutputStream outputStream = response.getOutputStream();
        request.setAttribute("Authorization", JWTUtils.getTokenInfo("123"));
        outputStream.write("登录成功".getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
