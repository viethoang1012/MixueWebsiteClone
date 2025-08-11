package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.model.Review;
import com.gdu_springboot.spring_boot_demo.model.Users;
import com.gdu_springboot.spring_boot_demo.service.ProductService;
import com.gdu_springboot.spring_boot_demo.service.ReviewService;
import com.gdu_springboot.spring_boot_demo.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {
    
    private ReviewService reviewService;
    private ProductService productService;
    private UserService userService;
    
    @Autowired
    public ReviewController(ReviewService reviewService, ProductService productService, UserService userService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.userService = userService;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    
    // Thêm đánh giá mới cho sản phẩm
    @PostMapping("/review/add")
    public String addReview(@Valid @ModelAttribute("review") Review review, BindingResult bindingResult,
                           @RequestParam("productId") Long productId,
                           Model model, RedirectAttributes redirectAttributes) {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getName().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để gửi đánh giá");
            return "redirect:/detail-products?id=" + productId;
        }
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng kiểm tra lại thông tin đánh giá");
            return "redirect:/detail-products?id=" + productId;
        }
        
        try {
            // Lấy thông tin sản phẩm
            Product product = productService.findById(productId);
            review.setProduct(product);
            
            // Lấy ID người dùng hiện tại (chỉ lưu ID để tham chiếu)
            Users user = userService.findByUsername(authentication.getName());
            if (user != null) {
                review.setUserId(user.getId());
                // Không ghi đè userName và userEmail từ form
            }
            
            // Lưu đánh giá
            reviewService.save(review);
            
            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("successMessage", "Đánh giá của bạn đã được gửi thành công!");
            return "redirect:/detail-products?id=" + productId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi gửi đánh giá. Vui lòng thử lại sau.");
            return "redirect:/detail-products?id=" + productId;
        }
    }
    
    // Hiển thị danh sách đánh giá cho admin
    @GetMapping("/admin/reviews")
    public String listReviews(Model model) {
        List<Review> reviews = reviewService.findUnreadReviews();
        model.addAttribute("reviews", reviews);
        return "admin/reviews/list-reviews";
    }
    
    // Đánh dấu đánh giá đã đọc
    @GetMapping("/admin/reviews/mark-as-read")
    public String markAsRead(@RequestParam("id") Long id) {
        reviewService.markAsRead(id);
        return "redirect:/admin/reviews";
    }
    
    // Xóa đánh giá
    @GetMapping("/admin/reviews/delete")
    public String deleteReview(@RequestParam("id") Long id) {
        reviewService.deleteById(id);
        return "redirect:/admin/reviews";
    }
    
    // Hiển thị tất cả đánh giá của một sản phẩm
    @GetMapping("/admin/reviews/product")
    public String listProductReviews(@RequestParam("productId") Long productId, Model model) {
        List<Review> reviews = reviewService.findByProductId(productId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("productId", productId);
        return "admin/reviews/product-reviews";
    }
    
    // Duyệt bình luận lên trang chủ
    @GetMapping("/admin/reviews/approve")
    public String approveReview(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Review> optionalReview = reviewService.findById(id);
        
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setApproved(true);
            review.setRead(true); // Đánh dấu đã đọc khi duyệt
            reviewService.save(review);
            redirectAttributes.addFlashAttribute("successMessage", "Đã duyệt thành công bình luận");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy bình luận");
        }
        
        return "redirect:/admin/reviews";
    }
}
