package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.service.JobApplicationService;
import com.gdu_springboot.spring_boot_demo.service.OrderService;
import com.gdu_springboot.spring_boot_demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ControllerAdvice
public class AdminController {
    
    private ReviewService reviewService;
    private JobApplicationService jobApplicationService;
    private OrderService orderService;
    
    @Autowired
    public AdminController(ReviewService reviewService, JobApplicationService jobApplicationService, OrderService orderService) {
        this.reviewService = reviewService;
        this.jobApplicationService = jobApplicationService;
        this.orderService = orderService;
    }
    
    @ModelAttribute
    public void addAttributes(Model model) {
        // Order metrics
        long newOrdersCount = orderService.countPendingOrders();
        long completedOrdersCount = orderService.countCompletedOrders();
        double totalRevenue = orderService.getTotalRevenue();
        
        // Review metrics
        Long goodReviewCount = reviewService.countGoodReviews();
        Long totalReviewCount = reviewService.countAllReviews();
        double goodReviewRate = (totalReviewCount == 0) ? 0 : ((double) goodReviewCount / totalReviewCount) * 100;
        
        // Job application metrics
        Long applicationCount = jobApplicationService.countUnreadApplications();
        
        // Add all metrics to model
        model.addAttribute("newOrdersCount", newOrdersCount);
        model.addAttribute("completedOrdersCount", completedOrdersCount);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("goodReviewRate", String.format("%.2f%%", goodReviewRate));
        model.addAttribute("applicationCount", applicationCount);
    }
    
    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin/dashboard";
    }
}
