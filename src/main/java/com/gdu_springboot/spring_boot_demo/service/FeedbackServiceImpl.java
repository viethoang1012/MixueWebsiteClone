package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.FeedbackRepository;
import com.gdu_springboot.spring_boot_demo.model.Feedback;
import com.gdu_springboot.spring_boot_demo.model.FeedbackRead;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final FeedbackReadService feedbackReadService;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackReadService feedbackReadService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackReadService = feedbackReadService;
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    @Override
    public Feedback save(Feedback feedback) {
        feedback.setCreatedAt(java.time.LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        Feedback feedback = findById(id);
        if (feedback != null) {
            FeedbackRead feedbackRead = new FeedbackRead();
            feedbackRead.setName(feedback.getName());
            feedbackRead.setEmail(feedback.getEmail());
            feedbackRead.setMessage(feedback.getMessage());
            feedbackRead.setCreatedAt(feedback.getCreatedAt());
            feedbackReadService.save(feedbackRead);
            feedbackRepository.deleteById(id);
        } else {
            throw new RuntimeException("Feedback not found with id: " + id);
        }
    }
    
    @Override
    public long count() {
        return feedbackRepository.count();
    }
} 