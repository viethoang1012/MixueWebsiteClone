package com.gdu_springboot.spring_boot_demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.service.ProductService;

import jakarta.validation.Valid;
import java.nio.file.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list-products")
    public String list(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/list-products";
    }

    @GetMapping("/product-form")
    public String productForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin/products/product-form";
    }

    @PostMapping("/save")
    public String save(@Valid Product product, BindingResult bindingResult, Model model,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Vui lòng kiểm tra lại thông tin sản phẩm");
            return product.getId() == null ?
                    "admin/products/product-form" :
                    "admin/products/product-form-update";
        }
        try {
            // Xử lý upload file nếu có
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = org.springframework.util.StringUtils.cleanPath(imageFile.getOriginalFilename());
                String uploadDir = "C:/mixue-uploads/images/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                imageFile.transferTo(filePath.toFile());
                product.setImageUrl("/images/" + fileName);
            } else if (product.getId() != null) {
                Product oldProduct = productService.findById(product.getId());
                product.setImageUrl(oldProduct.getImageUrl());
            }

            // Đảm bảo category không null
            if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Loại sản phẩm không được để trống");
                return product.getId() == null ?
                        "admin/products/product-form" :
                        "admin/products/product-form-update";
            }

            productService.save(product);
            model.addAttribute("successMessage", "Lưu sản phẩm thành công");
            return "redirect:/products/list-products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi lưu sản phẩm: " + e.getMessage());
            return product.getId() == null ?
                    "admin/products/product-form" :
                    "admin/products/product-form-update";
        }
    }

    @GetMapping("/product-form-update")
    public String formUpdate(@RequestParam("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/products/product-form-update";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id, Model model) {
        try {
            productService.deleteById(id);
            return "redirect:/products/list-products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi xóa sản phẩm: " + e.getMessage());
            return "redirect:/products/list-products";
        }
    }
}

