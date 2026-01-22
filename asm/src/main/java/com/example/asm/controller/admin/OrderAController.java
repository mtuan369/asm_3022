package com.example.asm.controller.admin;

import com.example.asm.dao.OrderDAO;
import com.example.asm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderAController {
    @Autowired
    OrderDAO dao;

    // Xem danh sách đơn hàng
    @RequestMapping("/admin/orders/index")
    public String index(Model model) {
        model.addAttribute("items", dao.findAll());
        return "admin/orders/index";
    }

    // Xóa đơn hàng (Lưu ý: Cần xử lý ràng buộc khóa ngoại trong DB hoặc Cascade)
    @RequestMapping("/admin/orders/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            dao.deleteById(id);
        } catch (Exception e) {
            // Xử lý lỗi nếu không xóa được do ràng buộc khóa ngoại
            System.out.println("Không xóa được đơn hàng: " + e.getMessage());
        }
        return "redirect:/admin/orders/index";
    }
}