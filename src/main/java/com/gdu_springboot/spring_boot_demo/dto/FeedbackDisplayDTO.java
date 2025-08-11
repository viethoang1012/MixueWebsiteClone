package com.gdu_springboot.spring_boot_demo.dto;

import com.gdu_springboot.spring_boot_demo.model.Feedback;
import com.gdu_springboot.spring_boot_demo.model.FeedbackRead;

import java.time.LocalDateTime;

public class FeedbackDisplayDTO {
    private Long id;
    private String name;
    private String email;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;

    public FeedbackDisplayDTO(Feedback feedback) {
        this.id = feedback.getId();
        this.name = feedback.getName();
        this.email = feedback.getEmail();
        this.message = feedback.getMessage();
        this.createdAt = feedback.getCreatedAt();
        this.isRead = false;
    }

    public FeedbackDisplayDTO(FeedbackRead feedbackRead) {
        this.id = feedbackRead.getId();
        this.name = feedbackRead.getName();
        this.email = feedbackRead.getEmail();
        this.message = feedbackRead.getMessage();
        this.createdAt = feedbackRead.getCreatedAt();
        this.isRead = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
} 