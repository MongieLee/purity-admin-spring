package com.example.demo.configuration;

import com.example.demo.security.JWTAuthenticationFilter;
import com.example.demo.security.LoginFailureHandler;
import com.example.demo.security.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Security配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(LoginFailureHandler loginFailureHandler, LoginSuccessHandler loginSuccessHandler
            , AccessDeniedHandler jwtAccessDeniedHandler, AuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许跨域
        http.cors();

        // 关闭csrf跨域攻击防御
        http.csrf().disable();

        // 表单登录配置
        // http.formLogin()
        //        .successHandler(loginSuccessHandler)
        //        .failureHandler(loginFailureHandler);

        // 禁用session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 配置白名单
        http.authorizeRequests()
                .antMatchers("/uploadFiles/**", "/api/v1/auth/**", "/swagger-ui.html", "/webjars/**", "/error/**"
                        , "/swagger-resources/**", "/swagger-ui.html/**", "/v2/**", "/csrf").permitAll()
                .anyRequest().authenticated();

        http.addFilter(authenticationFilter());

        // 处理无权限的处理
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter authenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(authenticationManager());
    }
}
