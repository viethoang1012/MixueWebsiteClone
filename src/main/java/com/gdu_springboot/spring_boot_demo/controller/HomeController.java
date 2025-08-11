package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.model.Review;
import com.gdu_springboot.spring_boot_demo.service.ProductService;
import com.gdu_springboot.spring_boot_demo.service.ReviewService;
import com.gdu_springboot.spring_boot_demo.dao.LocationRepository;
import com.gdu_springboot.spring_boot_demo.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.gdu_springboot.spring_boot_demo.model.Feedback;
import com.gdu_springboot.spring_boot_demo.service.FeedbackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    private ProductService productService;
    private ReviewService reviewService;
    private FeedbackService feedbackService;
    
    @Autowired
    public HomeController(ProductService productService, ReviewService reviewService, FeedbackService feedbackService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.feedbackService = feedbackService;
    }

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        
        // Lấy danh sách bình luận đã được duyệt
        List<Review> approvedReviews = reviewService.findApprovedReviews();
        model.addAttribute("approvedReviews", approvedReviews);
        
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @PostMapping("/contact")
    public String handleContactForm(@RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("message") String message,
                                    RedirectAttributes redirectAttributes) {
        try {
            Feedback feedback = new Feedback();
            feedback.setName(name);
            feedback.setEmail(email);
            feedback.setMessage(message);
            feedbackService.save(feedback);
            redirectAttributes.addFlashAttribute("successMessage", "Cảm ơn bạn đã gửi phản hồi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã có lỗi xảy ra. Vui lòng thử lại.");
        }
        return "redirect:/contact";
    }

    
    @GetMapping("detail-products")
    public String detailProduct(@RequestParam("id") Long id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        
        // Lấy danh sách đánh giá của sản phẩm
        List<Review> reviews = reviewService.findByProductId(id);
        model.addAttribute("reviews", reviews);
        
        // Thêm đối tượng Review mới cho form
        model.addAttribute("review", new Review());
        
        return "detail-products";
    }
}

