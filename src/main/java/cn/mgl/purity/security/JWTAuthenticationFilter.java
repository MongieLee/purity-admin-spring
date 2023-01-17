package cn.mgl.purity.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import cn.mgl.purity.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeSet;

// @Component
// 如果将过滤器注册成Bean，会执行两次，原因是被Spring容器管理，假如了Servlet的filter中，Security先执行过滤器，通过了就会再执行一次Servlet的过滤器
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");

        // 没有token相当于匿名访问，配合白名单使用，如果是白名单，则直接放行，如果不是白名单，则拒绝访问
        if (Objects.isNull(token)) {
            chain.doFilter(request, response);
            return;
        }
        token = token.substring(7);
        try {
            JWTUtils.verify(token);
        } catch (JWTVerificationException e) {
            // 由于全局异常只能捕获Controller中抛出的异常
            // 此处将请求转发到异常处理器中再抛出，来达到实现被全局异常处理器命中的效果
            request.setAttribute("filter.error", e);
            request.getRequestDispatcher("/error/throwEx").forward(request, response);
        }
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
