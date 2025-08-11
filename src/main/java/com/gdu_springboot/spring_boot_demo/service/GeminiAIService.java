package com.gdu_springboot.spring_boot_demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class GeminiAIService {
    // Thay YOUR_API_KEY bằng API Key thật của bạn
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyCidGQaF4CU7UZHmtmO0MmWfXcLsu5qQkA";
    private final RestTemplate restTemplate = new RestTemplate();

    public String askGemini(String question) {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(Map.of("text", question)));
        requestBody.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, entity, Map.class);

        // Thêm log để debug
        System.out.println("[Gemini] Status code: " + response.getStatusCode());
        System.out.println("[Gemini] Body: " + response.getBody());

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Object candidatesObj = response.getBody().get("candidates");
            if (candidatesObj instanceof List<?> candidates && !candidates.isEmpty()) {
                Object contentObj = ((Map<?, ?>) candidates.get(0)).get("content");
                if (contentObj instanceof Map<?, ?> contentMap) {
                    Object partsObj = contentMap.get("parts");
                    if (partsObj instanceof List<?> parts && !parts.isEmpty()) {
                        Object textObj = ((Map<?, ?>) parts.get(0)).get("text");
                        if (textObj instanceof String text && !text.isEmpty()) {
                            return text;
                        }
                    }
                }
            }
            // Nếu Gemini trả về lỗi hoặc không có câu trả lời
            Object errorObj = response.getBody().get("error");
            if (errorObj != null) {
                return "Lỗi Gemini: " + errorObj.toString();
            }
        } else if (response.getBody() != null && response.getBody().get("error") != null) {
            return "Lỗi Gemini: " + response.getBody().get("error").toString();
        }
        return "Xin lỗi, AI Gemini hiện không trả lời được. Vui lòng thử lại sau!";
    }
} 