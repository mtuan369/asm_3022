package com.example.asm.controller;

import com.example.asm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    CartService cart;

    @RequestMapping("/cart/view")
    public String view(Model model) {
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    // --- SỬA LỖI: Long -> Integer ---
    @RequestMapping("/cart/add/{id}")
    public String add(@PathVariable("id") Integer id) { // <--- Đã sửa thành Integer
        cart.add(id);
        return "redirect:/cart/view";
    }

    // --- SỬA LỖI: Long -> Integer ---
    @RequestMapping("/cart/remove/{id}")
    public String remove(@PathVariable("id") Integer id) { // <--- Đã sửa thành Integer
        cart.remove(id);
        return "redirect:/cart/view";
    }

    // --- SỬA LỖI: Long -> Integer ---
    @RequestMapping("/cart/update")
    public String update(@RequestParam("id") Integer id, @RequestParam("qty") Integer qty) { // <--- Đã sửa thành Integer
        cart.update(id, qty);
        return "redirect:/cart/view";
    }

    @RequestMapping("/cart/clear")
    public String clear() {
        cart.clear();
        return "redirect:/cart/view";
    }
}