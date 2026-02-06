package com.example.asm.controller;

import com.example.asm.dao.AccountDAO;
import com.example.asm.entity.Account;
import com.example.asm.service.ParamService;
import com.example.asm.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    JavaMailSender mailSender; // Sử dụng thư viện gửi mail trực tiếp của Spring

    // ============================================================
    // 1. ĐĂNG KÝ TÀI KHOẢN
    // ============================================================
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
                user.setPhoto("user.png"); // Ảnh mặc định
            }
            user.setActivated(true);
            user.setAdmin(false);
            dao.save(user);
            model.addAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "account/login"; // Chuyển về trang login (đảm bảo file login nằm đúng chỗ)
        } catch (Exception e) {
            model.addAttribute("message", "Tên đăng nhập đã tồn tại!");
            return "account/register";
        }
    }

    // ============================================================
    // 2. CẬP NHẬT HỒ SƠ
    // ============================================================
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
        session.set("user", user);
        model.addAttribute("message", "Cập nhật hồ sơ thành công!");
        return "account/profile";
    }

    // ============================================================
    // 3. ĐỔI MẬT KHẨU
    // ============================================================
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

    // ============================================================
    // 4. QUÊN MẬT KHẨU (Đã sửa logic chuẩn)
    // ============================================================
    @GetMapping("/account/forgot-password")
    public String forgotForm() {
        return "account/forgot-password"; // Trỏ đúng về file html bạn đã tạo
    }

    @PostMapping("/account/forgot-password")
    public String forgotSend(Model model, @RequestParam("email") String email) {
        try {
            // 1. Tìm user theo email (Dùng hàm trong DAO)
            Account user = dao.findByEmail(email);

            if (user != null) {
                // 2. Tạo mật khẩu mới ngẫu nhiên (6 ký tự)
                String newPass = Integer.toHexString(email.hashCode()).substring(0, 6);

                // 3. Lưu mật khẩu mới vào DB
                user.setPassword(newPass);
                dao.save(user);

                // 4. Gửi mail thông báo
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("J5 Shop - Khôi phục mật khẩu");
                message.setText("Chào bạn,\n\nMật khẩu mới của bạn là: " + newPass + "\n\nVui lòng đăng nhập và đổi lại mật khẩu ngay.");

                mailSender.send(message);

                model.addAttribute("message", "Mật khẩu mới đã được gửi vào email: " + email);
            } else {
                model.addAttribute("error", "Email này chưa đăng ký tài khoản nào!");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi gửi mail: " + e.getMessage());
        }
        return "account/forgot-password";
    }
}