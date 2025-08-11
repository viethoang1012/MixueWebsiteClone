package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Users;
import com.gdu_springboot.spring_boot_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping
    public String processSignup(Users user) {
        // Kiểm tra xem username đã tồn tại chưa
        if (userService.findByUsername(user.getUsername()) != null) {
            return "redirect:/signup?error=Username already exists";
        }

        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // Kích hoạt tài khoản

        // Lưu người dùng mới
        userService.save(user);

        return "redirect:/login?success=Registration successful";
    }
}
