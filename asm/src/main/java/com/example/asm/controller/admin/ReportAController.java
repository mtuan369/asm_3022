package com.example.asm.controller.admin;

import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class ReportAController {
    @Autowired
    ProductDAO dao;

    @RequestMapping("/admin/report/inventory")
    public String inventory(Model model) {
        List<Report> items = dao.getInventory();
        model.addAttribute("items", items);
        return "admin/report/inventory";
    }
}