package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Feedback;
import java.util.List;

public interface FeedbackService {
    List<Feedback> findAll();
    Feedback findById(Long id);
    Feedback save(Feedback feedback);
    void deleteById(Long id);
    void markAsRead(Long id);
    long count();
} 