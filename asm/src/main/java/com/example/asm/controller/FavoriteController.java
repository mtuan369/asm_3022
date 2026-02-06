package com.example.asm.controller;

import com.example.asm.dao.FavoriteDAO;
import com.example.asm.dao.ProductDAO;
import com.example.asm.entity.Account;
import com.example.asm.entity.Favorite;
import com.example.asm.entity.Product;
import com.example.asm.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Controller
public class FavoriteController {
    @Autowired FavoriteDAO favDAO;
    @Autowired ProductDAO proDAO;
    @Autowired SessionService session;

    // 1. Xem danh sách yêu thích
    @GetMapping("/favorite/list")
    public String list(Model model) {
        Account user = session.get("user");
        if (user == null) return "redirect:/auth/login";

        model.addAttribute("favorites", favDAO.findByUsername(user.getUsername()));
        return "product/favorite"; // Bạn cần tạo file html này
    }

    // 2. Thêm/Bỏ yêu thích (Toggle)
    @GetMapping("/favorite/toggle/{id}")
    public String toggle(@PathVariable("id") Integer id) {
        Account user = session.get("user");
        if (user == null) return "redirect:/auth/login";

        Favorite exist = favDAO.findByUserAndProduct(user.getUsername(), id);
        if (exist != null) {
            favDAO.delete(exist); // Đã like thì xóa (Un-like)
        } else {
            Favorite f = new Favorite();
            f.setAccount(user);
            f.setProduct(proDAO.findById(id).get());
            f.setLikeDate(new Date());
            favDAO.save(f); // Chưa like thì thêm
        }
        // Quay lại trang trước đó
        return "redirect:/product/list";
    }
}