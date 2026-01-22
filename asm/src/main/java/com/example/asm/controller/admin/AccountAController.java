package com.example.asm.controller.admin;

import com.example.asm.dao.AccountDAO;
import com.example.asm.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AccountAController {
    @Autowired
    AccountDAO dao;

    @RequestMapping("/admin/accounts/index")
    public String index(Model model) {
        Account item = new Account();
        model.addAttribute("item", item);
        List<Account> items = dao.findAll();
        model.addAttribute("items", items);
        return "admin/accounts/index";
    }

    @RequestMapping("/admin/accounts/edit/{username}")
    public String edit(Model model, @PathVariable("username") String username) {
        Account item = dao.findById(username).get();
        model.addAttribute("item", item);
        List<Account> items = dao.findAll();
        model.addAttribute("items", items);
        return "admin/accounts/index";
    }

    @RequestMapping("/admin/accounts/create")
    public String create(Account item) {
        dao.save(item);
        return "redirect:/admin/accounts/index";
    }

    @RequestMapping("/admin/accounts/update")
    public String update(Account item) {
        dao.save(item);
        return "redirect:/admin/accounts/edit/" + item.getUsername();
    }

    @RequestMapping("/admin/accounts/delete/{username}")
    public String delete(@PathVariable("username") String username) {
        dao.deleteById(username);
        return "redirect:/admin/accounts/index";
    }
}