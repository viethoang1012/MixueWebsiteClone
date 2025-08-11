package com.gdu_springboot.spring_boot_demo.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}

