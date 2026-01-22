package com.example.asm.controller;

import com.example.asm.dao.AccountDAO;
import com.example.asm.entity.Account;
import com.example.asm.service.ParamService;
import com.example.asm.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class AccountController {
    @Autowired
    AccountDAO dao;

    @Autowired
    SessionService session;

    @Autowired
    ParamService paramService;

    // 1. Đăng ký tài khoản
    @GetMapping("/account/sign-up")
    public String signUp(Model model) {
        model.addAttribute("user", new Account());
        return "account/register";
    }

    @PostMapping("/account/sign-up")
    public String signUp(Model model, Account user,
                         @RequestParam("photo_file") MultipartFile file) {
        try {
            if(!file.isEmpty()) {
                File savedFile = paramService.save(file, "/images/");
                user.setPhoto(savedFile.getName());
            } else {
                user.setPhoto("user.png");
            }
            user.setActivated(true);
            user.setAdmin(false);
            dao.save(user);
            model.addAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "account/login";
        } catch (Exception e) {
            model.addAttribute("message", "Tên đăng nhập đã tồn tại!");
            return "account/register";
        }
    }

    // 2. Cập nhật hồ sơ
    @GetMapping("/account/edit-profile")
    public String editProfile(Model model) {
        Account user = session.get("user");
        model.addAttribute("user", user);
        return "account/profile";
    }

    @PostMapping("/account/edit-profile")
    public String editProfile(Model model, Account user,
                              @RequestParam("photo_file") MultipartFile file) {
        if(!file.isEmpty()) {
            File savedFile = paramService.save(file, "/images/");
            user.setPhoto(savedFile.getName());
        }
        dao.save(user);
        session.set("user", user); // Cập nhật lại session
        model.addAttribute("message", "Cập nhật hồ sơ thành công!");
        return "account/profile";
    }

    // 3. Đổi mật khẩu
    @GetMapping("/account/change-password")
    public String changePassword() {
        return "account/change-password";
    }

    @PostMapping("/account/change-password")
    public String changePassword(Model model,
                                 @RequestParam("password") String password,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword) {
        Account user = session.get("user");
        if(!user.getPassword().equals(password)) {
            model.addAttribute("message", "Mật khẩu hiện tại không đúng!");
        } else if(!newPassword.equals(confirmPassword)) {
            model.addAttribute("message", "Xác nhận mật khẩu không trùng khớp!");
        } else {
            user.setPassword(newPassword);
            dao.save(user);
            model.addAttribute("message", "Đổi mật khẩu thành công!");
        }
        return "account/change-password";
    }
}