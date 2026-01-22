package com.example.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminHomeController {

    // Đường dẫn này sẽ chạy khi bạn vào localhost:8080/admin/index
    @RequestMapping("/admin/index")
    public String index() {
        return "admin/index"; // Trả về file giao diện admin/index.html
    }
}