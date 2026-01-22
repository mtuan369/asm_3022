package com.example.asm.config;

import com.example.asm.interceptor.AuthInterceptor;
import com.example.asm.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor auth;

    @Autowired
    GlobalInterceptor global;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. GLOBAL INTERCEPTOR: Chỉ chạy cho các trang HTML, bỏ qua file tĩnh và trang lỗi
        registry.addInterceptor(global)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/static/**", "/images/**", "/js/**", "/css/**", "/assets/**",
                        "/File.css", "/File.js", // Loại trừ file CSS/JS ở root folder static
                        "/favicon.ico",          // Loại trừ icon trình duyệt
                        "/error"                 // Loại trừ trang báo lỗi (tránh vòng lặp vô tận)
                );

        // 2. AUTH INTERCEPTOR: Bảo vệ các trang cần đăng nhập
        registry.addInterceptor(auth)
                .addPathPatterns("/order/**", "/admin/**", "/account/change-password", "/cart/**");
    }
}