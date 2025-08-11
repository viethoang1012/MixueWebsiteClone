package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByProductId(Long productId);
    
    List<Review> findByIsReadFalse();
    
    List<Review> findByIsApprovedTrue();
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.rating >= 4")
    long countGoodReviews();
    
    @Query("SELECT COUNT(r) FROM Review r")
    long countAllReviews();
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.isRead = false")
    long countUnreadReviews();
}
