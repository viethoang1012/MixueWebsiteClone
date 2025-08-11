package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.JobApplication;
import java.util.List;

public interface JobApplicationService {
    List<JobApplication> findAll();
    List<JobApplication> findByLocationId(Long locationId);
    JobApplication findById(Long id);
    JobApplication save(JobApplication jobApplication);
    void deleteById(Long id);
    List<JobApplication> findUnreadApplications();
    Long countUnreadApplications();
    void markAsRead(Long id);
    void approveApplication(Long id);
}
