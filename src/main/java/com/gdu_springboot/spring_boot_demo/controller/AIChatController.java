package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai-chat")
public class AIChatController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String question = payload.getOrDefault("question", "").toLowerCase();
        List<Product> products = productService.findAll();
        List<Product> suggestions = new ArrayList<>();
        StringBuilder answer = new StringBuilder();

        // Nâng cao: Nếu hỏi về "rẻ" hoặc "dưới 20k" thì ưu tiên sản phẩm rẻ nhất trước
        if (question.contains("rẻ") || question.contains("dưới 20k")) {
            // Nếu hỏi "kem rẻ" thì lọc category kem trước
            List<Product> filtered = products;
            if (question.contains("kem")) {
                filtered = products.stream().filter(p -> p.getCategory().toLowerCase().contains("kem")).collect(Collectors.toList());
            }
            filtered = filtered.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
            suggestions.addAll(filtered);
        } else if (question.contains("đắt")) {
            // Nếu hỏi "đắt" thì trả về sản phẩm đắt nhất trước
            List<Product> filtered = products;
            if (question.contains("kem")) {
                filtered = products.stream().filter(p -> p.getCategory().toLowerCase().contains("kem")).collect(Collectors.toList());
            }
            filtered = filtered.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());
            suggestions.addAll(filtered);
        }

        // Rule: Ưu tiên tìm theo tên sản phẩm
        for (Product p : products) {
            if (question.contains(p.getName().toLowerCase())) {
                if (!suggestions.contains(p)) suggestions.add(p);
            }
        }
        // Rule: Tìm theo từ khóa vị (xoài, dâu, socola, ngọt, mịn, kem, trà sữa...)
        String[] keywords = {"xoài", "dâu", "socola", "ngọt", "mịn", "kem", "trà sữa", "trà", "đào", "việt quất", "khoai môn", "đào cam sả", "sữa tươi", "đào", "chocolate", "vani", "trân châu", "thạch dừa"};
        for (String kw : keywords) {
            if (question.contains(kw)) {
                for (Product p : products) {
                    if (p.getName().toLowerCase().contains(kw) ||
                        (p.getDescription() != null && p.getDescription().toLowerCase().contains(kw)) ||
                        (p.getContent() != null && p.getContent().toLowerCase().contains(kw))) {
                        if (!suggestions.contains(p)) suggestions.add(p);
                    }
                }
            }
        }
        
        // Rule: Nếu hỏi "ngọt mịn màng dễ uống" thì ưu tiên các sản phẩm có mô tả "ngọt", "mịn", "dễ uống"
        if (question.contains("ngọt") && (question.contains("mịn") || question.contains("dễ uống"))) {
            for (Product p : products) {
                if ((p.getDescription() != null && (p.getDescription().toLowerCase().contains("ngọt") || p.getDescription().toLowerCase().contains("mịn"))) ||
                    (p.getContent() != null && (p.getContent().toLowerCase().contains("ngọt") || p.getContent().toLowerCase().contains("mịn")))) {
                    if (!suggestions.contains(p)) suggestions.add(p);
                }
            }
        }
        // Nếu có gợi ý
        if (!suggestions.isEmpty()) {
            answer.append("Bạn nên thử những món sau:<br>");
            for (Product p : suggestions.stream().limit(5).collect(Collectors.toList())) {
                answer.append("<b>").append(p.getName()).append("</b> - ")
                      .append(p.getDescription() != null ? p.getDescription() : "")
                      .append("<br><a href='/detail-products?id=").append(p.getId()).append("'>Xem chi tiết</a><br><br>");
            }
        } else {
            answer.append("Xin lỗi, mình chưa tìm được món phù hợp. Bạn có thể thử <a href='/'>xem menu Mixue</a>!");
        }
        return Map.of("answer", answer.toString());
    }
}
