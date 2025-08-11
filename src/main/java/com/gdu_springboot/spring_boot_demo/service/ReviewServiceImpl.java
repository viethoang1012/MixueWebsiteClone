    package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.ReviewRepository;
import com.gdu_springboot.spring_boot_demo.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public long countGoodReviews() {
        return reviewRepository.countGoodReviews();
    }

    @Override
    public long countAllReviews() {
        return reviewRepository.countAllReviews();
    }

    @Override
    public long countUnreadReviews() {
        return reviewRepository.countUnreadReviews();
    }

    @Override
    public List<Review> findByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> findUnreadReviews() {
        return reviewRepository.findByIsReadFalse();
    }

    @Override
    public void markAsRead(Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        optionalReview.ifPresent(review -> {
            review.setRead(true);
            reviewRepository.save(review);
        });
    }

    @Override
    public List<Review> findApprovedReviews() {
        return reviewRepository.findByIsApprovedTrue();
    }
}
