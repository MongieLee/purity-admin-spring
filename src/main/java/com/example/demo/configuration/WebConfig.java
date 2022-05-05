package com.example.demo.configuration;

import com.example.demo.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域相关配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
//                .maxAge(3600);
//    }

    /**
     * JWT拦截器，使用拦截器会影响执行执行顺序导致addCorsMappings配置跨域失效，需要把跨域配置写再corsFilter中
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .excludePathPatterns("/api/v1/auth/**", "/error", "/uploadFiles/**", "/error/**",
                        "/swagger-ui.html", "/csrf", "/webjars/**", "/swagger-resources/**", "/swagger-ui.html/**", "/v2/*");
    }
}
