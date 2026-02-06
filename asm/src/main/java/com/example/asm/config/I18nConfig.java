package com.example.asm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class I18nConfig implements WebMvcConfigurer {

    // 1. Khai báo nguồn tài liệu (File messages)
    @Bean("messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:i18n/messages"); // Tên file gốc
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    // 2. Xác định ngôn ngữ hiện tại (Lưu vào Cookie để nhớ lâu)
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver("language_cookie");
        resolver.setDefaultLocale(new Locale("vi")); // Mặc định là Tiếng Việt
        resolver.setCookieMaxAge(Duration.ofDays(30)); // Nhớ trong 30 ngày
        return resolver;
    }

    // 3. Bộ đón chặn tham số ?lang=vi hoặc ?lang=en
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Tham số trên URL
        return lci;
    }

    // 4. Đăng ký Interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}