package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByLocationId(Long locationId);
    
    List<JobApplication> findByIsReadFalse();
    
    @Query("SELECT COUNT(j) FROM JobApplication j WHERE j.isRead = false")
    Long countUnreadApplications();
    
    List<JobApplication> findByIsApprovedTrue();
}
