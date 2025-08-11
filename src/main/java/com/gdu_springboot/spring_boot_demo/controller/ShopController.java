package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ShopController {
    private final ProductService productService;

    @Autowired
    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(required = false) String category, Model model) {
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productService.findAll().stream()
                    .filter(p -> category.equals(p.getCategory()))
                    .collect(Collectors.toList());
        } else {
            products = productService.findAll();
        }
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "shop";
    }
} 