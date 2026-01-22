package com.example.asm.controller;

import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductDAO dao;

    // 1. Sửa Long id -> Integer id
    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Product item = dao.findById(id).get();
        model.addAttribute("item", item);
        return "product/detail";
    }

    // 2. Sửa Optional<Long> cid -> Optional<Integer> cid
    @RequestMapping("/product/list")
    public String list(Model model,
                       @RequestParam("cid") Optional<Integer> cid,
                       @RequestParam("keywords") Optional<String> kw) {

        if (cid.isPresent()) {
            List<Product> list = dao.findByCategoryId(cid.get());
            model.addAttribute("items", list);
        } else if (kw.isPresent()) {
            List<Product> list = dao.findByKeywords("%" + kw.get() + "%");
            model.addAttribute("items", list);
        } else {
            List<Product> list = dao.findAll();
            model.addAttribute("items", list);
        }

        return "product/list";
    }
}