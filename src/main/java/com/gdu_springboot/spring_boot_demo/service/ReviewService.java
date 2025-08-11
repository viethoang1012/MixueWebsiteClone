package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> findAll();
    
    // Dashboard metrics
    long countGoodReviews();
    long countAllReviews();
    long countUnreadReviews();
    List<Review> findByProductId(Long productId);
    Optional<Review> findById(Long id);
    Review save(Review review);
    void deleteById(Long id);
    List<Review> findUnreadReviews();
    void markAsRead(Long id);
    List<Review> findApprovedReviews();
}
