package com.gdu_springboot.spring_boot_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class Login {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping({"index"})
    public String mainPage() {
        return "index";
    }

    // Xóa hoặc comment mapping này vì đã được xử lý trong AdminController
    // @GetMapping("/admin") 
    // public String admin(){
    //     return "admin";
    // }
    
    @GetMapping("/system")
    public String system(){
        return "system/dashboard";
    }
}
