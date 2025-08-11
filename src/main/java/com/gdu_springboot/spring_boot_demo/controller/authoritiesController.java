package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Authorities;
import com.gdu_springboot.spring_boot_demo.model.Customer;
import com.gdu_springboot.spring_boot_demo.model.Users;
import com.gdu_springboot.spring_boot_demo.service.AuthoritiesService;
import com.gdu_springboot.spring_boot_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authorities")
public class authoritiesController {
    private AuthoritiesService authoritiesService;
    private UserService userService;
    @Autowired
    public authoritiesController(AuthoritiesService authoritiesService, UserService userService) {
        this.authoritiesService = authoritiesService;
        this.userService = userService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @GetMapping("/list-authorities")
    public String list(Model model) {
        List<Authorities> authorities = authoritiesService.findAll();
        model.addAttribute("authorities",authorities);
        return "system/authorities/list-authorities";
    }
    @GetMapping("/authorities-form")
    public String showAuthoritiesForm(Model model) {
        model.addAttribute("authorities", new Authorities());
        model.addAttribute("users", userService.findAll());
        return "system/authorities/authorities-form";
    }
    @PostMapping("/save")
    public String save(@Valid Authorities authorities, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return authorities.getId() == null ? 
                   "system/authorities/authorities-form" : 
                   "system/authorities/authorities-form-update";
        }
        try {
            authoritiesService.save(authorities);
            return "redirect:/authorities/list-authorities";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi lưu quyền: " + e.getMessage());
            model.addAttribute("users", userService.findAll());
            return authorities.getId() == null ? 
                   "system/authorities/authorities-form" : 
                   "system/authorities/authorities-form-update";
        }
    }
    @GetMapping("/authorities-form-update")
    public String formUpdate(@RequestParam("id") Long id, Model model) {
        Authorities authorities = authoritiesService.findById(id);
        model.addAttribute("authorities", authorities);
        model.addAttribute("users", userService.findAll());
        return "system/authorities/authorities-form-update";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, Model model) {
        try {
            authoritiesService.deleteById(id);
            return "redirect:/authorities/list-authorities";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi xóa quyền: " + e.getMessage());
            return "redirect:/authorities/list-authorities";
        }
    }
}
