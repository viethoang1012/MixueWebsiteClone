package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.JobApplicationRepository;
import com.gdu_springboot.spring_boot_demo.model.JobApplication;
import com.gdu_springboot.spring_boot_demo.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {
    private JobApplicationRepository jobApplicationRepository;
    private StaffService staffService;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, StaffService staffService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.staffService = staffService;
    }

    @Override
    public List<JobApplication> findAll() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public List<JobApplication> findByLocationId(Long locationId) {
        return jobApplicationRepository.findByLocationId(locationId);
    }

    @Override
    public JobApplication findById(Long id) {
        return jobApplicationRepository.findById(id).orElse(null);
    }

    @Override
    public JobApplication save(JobApplication jobApplication) {
        // Đảm bảo các trường bắt buộc được thiết lập
        if (jobApplication.getCreatedAt() == null) {
            jobApplication.setCreatedAt(LocalDateTime.now());
        }
        
        // Đảm bảo trạng thái mặc định
        jobApplication.setRead(false);
        jobApplication.setApproved(false);
        
        // Lưu đơn ứng tuyển
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public void deleteById(Long id) {
        jobApplicationRepository.deleteById(id);
    }

    @Override
    public List<JobApplication> findUnreadApplications() {
        return jobApplicationRepository.findByIsReadFalse();
    }

    @Override
    public Long countUnreadApplications() {
        return jobApplicationRepository.countUnreadApplications();
    }

    @Override
    public void markAsRead(Long id) {
        JobApplication jobApplication = findById(id);
        if (jobApplication != null) {
            jobApplication.setRead(true);
            jobApplicationRepository.save(jobApplication);
        }
    }

    @Override
    public void approveApplication(Long id) {
        JobApplication jobApplication = findById(id);
        if (jobApplication != null) {
            // Đánh dấu đơn ứng tuyển là đã duyệt và đã đọc
            jobApplication.setApproved(true);
            jobApplication.setRead(true);
            jobApplicationRepository.save(jobApplication);
            
            // Tạo nhân viên mới từ đơn ứng tuyển
            Staff staff = new Staff(jobApplication);
            staffService.save(staff);
            
            // Đã bỏ chức năng gửi email theo yêu cầu
        }
    }
}
