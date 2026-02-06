package com.example.asm.controller;

import com.example.asm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {
    @Autowired
    CartService cart;

    @RequestMapping("/cart/view")
    public String view(Model model) {
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    // 1. NÚT "THÊM VÀO GIỎ" (Ở lại trang & mở Mini Cart)
    @RequestMapping("/cart/add/{id}")
    public String add(@PathVariable("id") Integer id,
                      @RequestParam(value = "size", required = false, defaultValue = "") String size,
                      @RequestParam(value = "color", required = false, defaultValue = "") String color,
                      HttpServletRequest request) {

        cart.add(id, size, color); // Thêm vào giỏ

        // Lấy URL hiện tại để reload lại đúng trang đó
        String referer = request.getHeader("Referer");
        if(referer == null || !referer.contains("/product/")) {
            referer = "/product/list";
        }

        // Thêm tham số ?openCart=true để JS bên layout/index.html tự động mở Mini Cart
        return "redirect:" + referer + (referer.contains("?") ? "&" : "?") + "openCart=true";
    }

    // 2. NÚT "MUA NGAY" (Chuyển thẳng đến trang Thanh toán)
    @RequestMapping("/cart/buy/{id}")
    public String buy(@PathVariable("id") Integer id,
                      @RequestParam(value = "size", required = false, defaultValue = "") String size,
                      @RequestParam(value = "color", required = false, defaultValue = "") String color) {

        cart.add(id, size, color); // Thêm vào giỏ trước
        return "redirect:/order/checkout"; // Chuyển hướng ngay lập tức
    }

    @RequestMapping("/cart/remove/{id}")
    public String remove(@PathVariable("id") Integer id, HttpServletRequest request) {
        cart.remove(id);

        // Xóa xong thì load lại trang cũ và mở lại Cart cho khách thấy
        String referer = request.getHeader("Referer");
        if(referer == null) referer = "/product/list";
        return "redirect:" + referer + (referer.contains("?") ? "&" : "?") + "openCart=true";
    }

    @RequestMapping("/cart/update")
    public String update(@RequestParam("id") Integer id, @RequestParam("qty") Integer qty) {
        cart.update(id, qty);
        return "redirect:/cart/view";
    }

    @RequestMapping("/cart/clear")
    public String clear() {
        cart.clear();
        return "redirect:/cart/view";
    }
}