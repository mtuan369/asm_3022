package com.example.asm.controller;

import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ProductDAO pdao;

    @RequestMapping({"/", "/home/index"})
    public String home(Model model) {
        // Lấy toàn bộ sản phẩm hiển thị ra trang chủ
        List<Product> list = pdao.findAll();
        model.addAttribute("items", list);
        return "home/index";
    }

    @RequestMapping("/home/about")
    public String about() {
        return "home/about";
    }
}