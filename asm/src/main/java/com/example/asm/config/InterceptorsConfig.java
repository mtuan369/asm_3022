package com.example.asm.config;

import com.example.asm.interceptor.AuthInterceptor;
import com.example.asm.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    @Autowired
    GlobalInterceptor globalInterceptor;

    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. Global Interceptor (Load Menu): Loại bỏ file tĩnh để không spam DB
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/rest/**", "/admin/**", "/assets/**", "/images/**", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg");

        // 2. Auth Interceptor (Bảo mật): Loại bỏ file tĩnh
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/account/change-password", "/account/edit-profile", "/order/**", "/admin/**")
                .excludePathPatterns("/assets/**", "/admin/assets/**", "/images/**");
    }
}