package com.example.asm.controller;

import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Product;
import com.example.asm.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ProductRestController {
    @Autowired
    MailerService mailerService;

    @Autowired
    ProductDAO productDAO;

    @PostMapping("/rest/product/share")
    public String shareProduct(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String productIdStr = data.get("id");

        if(email == null || productIdStr == null) return "Lỗi: Thiếu thông tin!";

        // Lấy tên sản phẩm để mail đẹp hơn
        Integer id = Integer.parseInt(productIdStr);
        Product p = productDAO.findById(id).orElse(null);

        if(p != null) {
            mailerService.sendShareEmail(email, productIdStr, p.getName());
            return "Đã gửi mail thành công!";
        }
        return "Sản phẩm không tồn tại!";
    }
}