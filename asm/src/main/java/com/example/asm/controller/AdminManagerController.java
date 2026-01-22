package com.example.asm.controller;

import com.example.asm.dao.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminManagerController {

    @Autowired
    OrderDAO orderDAO;

    // --- XÓA HÀM listAccounts Ở ĐÂY VÌ ĐÃ CÓ AdminAccountController XỬ LÝ RỒI ---

    // Chỉ giữ lại hàm báo cáo này
    @GetMapping("/report/revenue")
    public String reportRevenue(Model model) {
        List<Object[]> data = orderDAO.getRevenueByMonth();
        model.addAttribute("revenueData", data);
        return "admin/report/revenue";
    }
}