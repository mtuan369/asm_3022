package com.example.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminHomeController {

    @RequestMapping({"/admin/index", "/admin/"})
    public String index() {
        return "admin/home/index";
    }
}