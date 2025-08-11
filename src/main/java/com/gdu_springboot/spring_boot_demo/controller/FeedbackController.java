package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.dto.FeedbackDisplayDTO;
import com.gdu_springboot.spring_boot_demo.model.Feedback;
import com.gdu_springboot.spring_boot_demo.model.FeedbackRead;
import com.gdu_springboot.spring_boot_demo.service.FeedbackReadService;
import com.gdu_springboot.spring_boot_demo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/system/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackReadService feedbackReadService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, FeedbackReadService feedbackReadService) {
        this.feedbackService = feedbackService;
        this.feedbackReadService = feedbackReadService;
    }

    @PostMapping("")
    public String submitFeedback(@RequestParam String name, 
                               @RequestParam String email, 
                               @RequestParam String message, 
                               RedirectAttributes redirectAttributes) {
        try {
            Feedback feedback = new Feedback();
            feedback.setName(name);
            feedback.setEmail(email);
            feedback.setMessage(message);
            feedbackService.save(feedback);
            redirectAttributes.addFlashAttribute("successMessage", "Đã gửi phản hồi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi gửi phản hồi: " + e.getMessage());
        }
        return "redirect:/contact";
    }

    @GetMapping("/list-feedbacks")
    public String listFeedbacks(Model model) {
        List<Feedback> unreadFeedbacks = feedbackService.findAll();
        List<FeedbackRead> readFeedbacks = feedbackReadService.findAll();

        model.addAttribute("unreadFeedbacks", unreadFeedbacks);
        model.addAttribute("readFeedbacks", readFeedbacks);

        return "system/feedback/list-feedbacks";
    }

    @GetMapping("/list-feedbacks-read")
    public String listReadFeedbacks(Model model) {
        List<FeedbackRead> readFeedbacks = feedbackReadService.findAll();
        model.addAttribute("readFeedbacks", readFeedbacks);
        return "system/feedback/list-feedbacks-read";
    }

    @GetMapping("/mark-as-read")
    public String markAsRead(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            feedbackService.markAsRead(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã đánh dấu feedback là đã đọc.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi đánh dấu đã đọc: " + e.getMessage());
        }
        return "redirect:/system/feedback/list-feedbacks#read";
    }

    @GetMapping("/delete")
    public String deleteFeedback(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            feedbackService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa feedback chưa đọc thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa feedback: " + e.getMessage());
        }
        return "redirect:/system/feedback/list-feedbacks#unread";
    }

    @GetMapping("/delete-read")
    public String deleteReadFeedback(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            feedbackReadService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa feedback đã đọc thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa feedback đã đọc: " + e.getMessage());
        }
        return "redirect:/system/feedback/list-feedbacks#read";
    }
} 