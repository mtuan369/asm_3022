package com.example.asm.controller.admin;

import com.example.asm.dao.OrderDAO;
import com.example.asm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders")
public class OrderAController {
    @Autowired
    OrderDAO dao;

    @RequestMapping("/index")
    public String index(Model model) {
        // Lấy danh sách đơn hàng, sắp xếp ID giảm dần (Mới nhất lên đầu)
        model.addAttribute("items", dao.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "admin/orders/index";
    }

    // --- [CẬP NHẬT] HÀM XỬ LÝ TRẠNG THÁI ---
    @GetMapping("/update-status/{id}/{status}")
    public String updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        Order order = dao.findById(id).get();

        // Status 1: Admin xác nhận & Giao shipper
        // Status 4: Admin báo đã giao xong -> Chờ khách xác nhận
        // Status 3: Hủy đơn
        order.setStatus(status);

        dao.save(order);
        return "redirect:/admin/orders/index";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
            dao.deleteById(id);
        } catch (Exception e) {
            System.out.println("Không thể xóa đơn hàng đang có dữ liệu chi tiết!");
        }
        return "redirect:/admin/orders/index";
    }
}