package com.example.asm.controller.user;

import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Product;
import com.example.asm.service.MailerService; // Import Service của bạn
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductLController {

    @Autowired
    ProductDAO dao;

    @Autowired
    MailerService mailer; // Tiêm Service vào

    // ... Các hàm khác giữ nguyên ...

    @PostMapping("/product/share")
    public String share(
            @RequestParam("id") Integer id,
            @RequestParam("email") String toEmail,
            RedirectAttributes params) {
        try {
            // 1. Lấy thông tin sản phẩm từ ID
            Product product = dao.findById(id).get();

            // 2. Gọi hàm gửi mail từ Service của bạn
            // Lưu ý: Hàm của bạn yêu cầu (String email, String id, String name)
            // nên ta cần ép kiểu id sang String bằng String.valueOf(id)
            mailer.sendShareEmail(toEmail, String.valueOf(id), product.getName());

            params.addFlashAttribute("message", "Đã gửi mail chia sẻ thành công!");
        } catch (Exception e) {
            params.addFlashAttribute("error", "Lỗi gửi mail: " + e.getMessage());
        }

        return "redirect:/product/detail/" + id;
    }
}