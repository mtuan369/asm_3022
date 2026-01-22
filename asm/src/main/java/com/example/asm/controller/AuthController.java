package com.example.asm.controller;

import com.example.asm.dao.AccountDAO;
import com.example.asm.entity.Account;
import com.example.asm.service.SessionService;
import com.example.asm.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    AccountDAO dao;

    @Autowired
    SessionService session;

    @GetMapping("/auth/login")
    public String login() {
        return "account/login";
    }

    @PostMapping("/auth/login")
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password) {
        try {
            Account user = dao.findById(username).get();
            if(!user.getPassword().equals(password)) {
                model.addAttribute("message", "Sai mật khẩu!");
            } else {
                session.set("user", user); // Lưu user vào session
                model.addAttribute("message", "Đăng nhập thành công!");
                // Nếu là admin thì chuyển qua trang admin, ngược lại về trang chủ
                if(user.getAdmin()) return "redirect:/admin/index";
                else return "redirect:/";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Tài khoản không tồn tại!");
        }
        return "account/login";
    }

    @RequestMapping("/auth/logoff")
    public String logoff() {
        session.remove("user");
        return "redirect:/";
    }
}