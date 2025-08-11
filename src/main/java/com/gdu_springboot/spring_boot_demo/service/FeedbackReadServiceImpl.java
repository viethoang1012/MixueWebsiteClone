package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.FeedbackReadRepository;
import com.gdu_springboot.spring_boot_demo.model.FeedbackRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackReadServiceImpl implements FeedbackReadService {

    private final FeedbackReadRepository feedbackReadRepository;

    @Autowired
    public FeedbackReadServiceImpl(FeedbackReadRepository feedbackReadRepository) {
        this.feedbackReadRepository = feedbackReadRepository;
    }

    @Override
    public List<FeedbackRead> findAll() {
        return feedbackReadRepository.findAll();
    }

    @Override
    public void save(FeedbackRead feedbackRead) {
        feedbackReadRepository.save(feedbackRead);
    }

    @Override
    public void deleteById(Long id) {
        feedbackReadRepository.deleteById(id);
    }
} 