package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.FeedbackRead;

import java.util.List;

public interface FeedbackReadService {
    List<FeedbackRead> findAll();
    void save(FeedbackRead feedbackRead);
    void deleteById(Long id);
} 