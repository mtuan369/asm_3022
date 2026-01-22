package com.example.asm.controller.admin;

import com.example.asm.dao.CategoryDAO;
import com.example.asm.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryAController {
    @Autowired
    CategoryDAO dao;

    @RequestMapping("/admin/categories/index")
    public String index(Model model) {
        Category item = new Category();
        model.addAttribute("item", item);
        model.addAttribute("items", dao.findAll());
        return "admin/categories/index";
    }

    @RequestMapping("/admin/categories/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Category item = dao.findById(id).get();
        model.addAttribute("item", item);
        model.addAttribute("items", dao.findAll());
        return "admin/categories/index";
    }

    @RequestMapping("/admin/categories/create")
    public String create(Category item) {
        dao.save(item);
        return "redirect:/admin/categories/index";
    }

    @RequestMapping("/admin/categories/update")
    public String update(Category item) {
        dao.save(item);
        return "redirect:/admin/categories/edit/" + item.getId();
    }

    @RequestMapping("/admin/categories/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        dao.deleteById(id);
        return "redirect:/admin/categories/index";
    }
}