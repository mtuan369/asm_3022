package com.example.asm.controller.admin;

import com.example.asm.dao.CategoryDAO;
import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductAController {
    @Autowired
    ProductDAO dao;

    @Autowired
    CategoryDAO cdao;

    @RequestMapping("/admin/products/index")
    public String index(Model model) {
        Product item = new Product();
        model.addAttribute("item", item);
        model.addAttribute("items", dao.findAll());
        model.addAttribute("categories", cdao.findAll());
        return "admin/products/index";
    }

    // --- SỬA LỖI Ở ĐÂY (Long -> Integer) ---
    @RequestMapping("/admin/products/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) { // <--- Sửa Long thành Integer
        Product item = dao.findById(id).get();
        model.addAttribute("item", item);
        model.addAttribute("items", dao.findAll());
        model.addAttribute("categories", cdao.findAll());
        return "admin/products/index";
    }

    @RequestMapping("/admin/products/create")
    public String create(Product item) {
        dao.save(item);
        return "redirect:/admin/products/index";
    }

    @RequestMapping("/admin/products/update")
    public String update(Product item) {
        dao.save(item);
        return "redirect:/admin/products/edit/" + item.getId();
    }

    // --- SỬA THÊM Ở ĐÂY NẾU CÓ (Long -> Integer) ---
    @RequestMapping("/admin/products/delete/{id}")
    public String delete(@PathVariable("id") Integer id) { // <--- Sửa Long thành Integer
        dao.deleteById(id);
        return "redirect:/admin/products/index";
    }
}