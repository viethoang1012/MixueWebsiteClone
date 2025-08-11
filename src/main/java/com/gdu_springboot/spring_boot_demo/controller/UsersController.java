package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.model.Users;
import com.gdu_springboot.spring_boot_demo.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")

public class UsersController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UsersController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @GetMapping("/list-users")
    public String list(Model model) {
        List<Users> users = userService.findAll();
        model.addAttribute("users",users);
        return "system/users/list-users";
    }
    @GetMapping("/user-form")
    public String userForm(Model model) {
        Users user = new Users();
        model.addAttribute("user",user);
        return "system/users/user-form";
    }
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") Users users, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (users.getId() == null) {
                return "system/users/user-form"; // Form thêm mới
            } else {
                return "system/users/user-form-update"; // Form cập nhật
            }
        }
        if (users.getPassword() != null && !users.getPassword().isEmpty()) {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
        } else {
            Users existingUser = userService.findById(users.getId());
            if (existingUser != null) {
                users.setPassword(existingUser.getPassword());
            }
        }
        userService.save(users);
        return "redirect:/users/list-users";
    }
    @GetMapping("/user-form-update")
    public String formUpdate(@RequestParam("id") Long id, Model model) {
        Users users = userService.findById(id);
        model.addAttribute("user", users);
        return "system/users/user-form-update";
    }
    @GetMapping("/toggle-enabled")
    @Transactional
    public String toggleEnabled(@RequestParam("id") Long id, Model model) {
        try {
            Users user = userService.findById(id);
            if (user == null) {
                model.addAttribute("errorMessage", "Không tìm thấy người dùng với ID: " + id);
                return "redirect:/users/list-users";
            }

            // Đảo ngược trạng thái enabled
            user.setEnabled(!user.isEnabled());
            userService.save(user);

            model.addAttribute("successMessage", "Cập nhật trạng thái người dùng thành công.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật trạng thái người dùng. Vui lòng thử lại.");
        }
        return "redirect:/users/list-users";
    }
    // Xóa khách hàng
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, Model model) {
        try {
            userService.deleteById(id);
            return "redirect:/users/list-users";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi xóa người dùng: " + e.getMessage());
            return "redirect:/users/list-users";
        }
    }

}
