package com.example.asm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; // 1. Import này

@SpringBootApplication
@EnableAsync // 2. Bắt buộc thêm dòng này để gửi mail chạy ngầm
public class AsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsmApplication.class, args);
    }
}