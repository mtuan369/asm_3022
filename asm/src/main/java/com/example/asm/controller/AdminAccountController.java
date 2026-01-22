package com.example.asm.controller;

import com.example.asm.dao.AccountDAO;
import com.example.asm.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/accounts")
public class AdminAccountController {
    @Autowired
    AccountDAO dao;

    // 1. Hiển thị danh sách & Form trống
    @GetMapping("/index")
    public String index(Model model) {
        Account item = new Account();
        item.setPhoto("https://cdn-icons-png.flaticon.com/512/149/149071.png"); // Ảnh mặc định
        model.addAttribute("item", item);

        List<Account> items = dao.findAll();
        model.addAttribute("items", items);

        return "admin/accounts/index";
    }

    // 2. Chức năng EDIT (Đổ dữ liệu lên form)
    @GetMapping("/edit/{username}")
    public String edit(Model model, @PathVariable("username") String username) {
        Account item = dao.findById(username).get();
        model.addAttribute("item", item);
        List<Account> items = dao.findAll();
        model.addAttribute("items", items);
        return "admin/accounts/index";
    }

    // 3. Chức năng CREATE
    @PostMapping("/create")
    public String create(Account item) {
        if(dao.existsById(item.getUsername())){
            // Có thể thêm thông báo lỗi ở đây nếu cần
            return "redirect:/admin/accounts/index";
        }
        dao.save(item);
        return "redirect:/admin/accounts/index";
    }

    // 4. Chức năng UPDATE
    @PostMapping("/update")
    public String update(Account item) {
        if(dao.existsById(item.getUsername())){
            dao.save(item);
        }
        return "redirect:/admin/accounts/edit/" + item.getUsername();
    }

    // 5. Chức năng DELETE
    @GetMapping("/delete/{username}")
    public String delete(@PathVariable("username") String username) {
        dao.deleteById(username);
        return "redirect:/admin/accounts/index";
    }
}