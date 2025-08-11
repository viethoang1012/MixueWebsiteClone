package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.JobApplication;
import com.gdu_springboot.spring_boot_demo.model.Location;
import com.gdu_springboot.spring_boot_demo.service.JobApplicationService;
import com.gdu_springboot.spring_boot_demo.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
public class JobApplicationController {
    
    private JobApplicationService jobApplicationService;
    private LocationService locationService;
    
    @Autowired
    public JobApplicationController(JobApplicationService jobApplicationService, LocationService locationService) {
        this.jobApplicationService = jobApplicationService;
        this.locationService = locationService;
    }
    
    // Xử lý gửi đơn ứng tuyển
    @PostMapping("/job-application/submit")
    public String submitApplication(@ModelAttribute JobApplication jobApplication,
                               @RequestParam("locationId") Long locationId,
                               @RequestParam("citizenIdFrontFile") MultipartFile citizenIdFrontFile,
                               @RequestParam("citizenIdBackFile") MultipartFile citizenIdBackFile,
                               RedirectAttributes redirectAttributes) {
    try {
        // Tạo thư mục lưu trữ ảnh nếu chưa tồn tại
        File uploadDir = new File("src/main/resources/static/images/citizen_id/");
        if (!uploadDir.exists()) {
            System.out.println("Tạo thư mục lưu trữ ảnh: " + uploadDir.mkdirs());
        }
        
        // Lấy thông tin địa điểm
        Location location = locationService.getLocationById(locationId).orElse(null);
        if (location == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy địa điểm");
            return "redirect:/location";
        }
        jobApplication.setLocation(location);
        
        // Xử lý upload ảnh CMND/CCCD mặt trước
        if (!citizenIdFrontFile.isEmpty()) {
            String uploadDirPath = "src/main/resources/static/images/citizen_id/";
            String fileName = UUID.randomUUID().toString() + "_" + citizenIdFrontFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDirPath);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            try (InputStream inputStream = citizenIdFrontFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                jobApplication.setCitizenIdFront("/images/citizen_id/" + fileName);
            }
        }
        
        // Xử lý upload ảnh CMND/CCCD mặt sau
        if (!citizenIdBackFile.isEmpty()) {
            String uploadDirPath = "src/main/resources/static/images/citizen_id/";
            String fileName = UUID.randomUUID().toString() + "_" + citizenIdBackFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDirPath);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            try (InputStream inputStream = citizenIdBackFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                jobApplication.setCitizenIdBack("/images/citizen_id/" + fileName);
            }
        }
        
        // Lưu đơn ứng tuyển
        jobApplicationService.save(jobApplication);
        
        // Chỉ sử dụng một cách để hiển thị thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Đơn ứng tuyển của bạn đã được gửi thành công!");
        return "redirect:/location/" + locationId;
        
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
        return "redirect:/location/" + locationId;
    }
}
    
    // Hiển thị danh sách đơn ứng tuyển cho admin
    @GetMapping("/admin/job-applications")
    public String listApplications(Model model) {
        System.out.println("===== Bắt đầu xử lý listApplications() =====");
        System.out.println("Kiểm tra quyền truy cập của người dùng hiện tại");
        
        try {
            // Kiểm tra kết nối cơ sở dữ liệu trước khi truy vấn
            try {
                Long unreadCount = jobApplicationService.countUnreadApplications();
                System.out.println("Kết nối cơ sở dữ liệu thành công. Số đơn chưa đọc: " + unreadCount);
            } catch (Exception dbException) {
                System.err.println("Lỗi kết nối cơ sở dữ liệu: " + dbException.getMessage());
                dbException.printStackTrace(); // In stack trace để debug
                model.addAttribute("errorMessage", "Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra cấu hình và thử lại sau.");
                return "admin/job-applications/list-applications";
            }
            
            List<JobApplication> unreadApplications = jobApplicationService.findUnreadApplications();
            List<JobApplication> allApplications = jobApplicationService.findAll();
            
            System.out.println("Số lượng đơn chưa đọc: " + unreadApplications.size());
            System.out.println("Số lượng tất cả đơn: " + allApplications.size());
            
            // In chi tiết từng đơn ứng tuyển để debug
            System.out.println("Chi tiết các đơn ứng tuyển:");
            for (JobApplication app : allApplications) {
                System.out.println("ID: " + app.getId() + ", Tên: " + app.getFullName() + ", Email: " + app.getEmail() + ", Đã đọc: " + app.isRead() + ", Đã duyệt: " + app.isApproved());
            }
            
            // Thêm logging chi tiết về model attributes
            System.out.println("Thêm unreadApplications vào model: " + (unreadApplications != null ? unreadApplications.size() : "null"));
            System.out.println("Thêm allApplications vào model: " + (allApplications != null ? allApplications.size() : "null"));
            
            // Luôn thêm danh sách vào model, ngay cả khi trống
            model.addAttribute("unreadApplications", unreadApplications);
            model.addAttribute("allApplications", allApplications);
            
            // Kiểm tra xem có đơn ứng tuyển nào không
            if (allApplications.isEmpty()) {
                System.out.println("Không có đơn ứng tuyển nào trong cơ sở dữ liệu!");
                // Thêm thông báo vào model để hiển thị trong view
                model.addAttribute("infoMessage", "Không có đơn ứng tuyển nào trong cơ sở dữ liệu!");
            }
            
            System.out.println("===== Kết thúc xử lý listApplications() =====");
            return "admin/job-applications/list-applications";
        } catch (Exception e) {
            System.err.println("Lỗi khi xử lý listApplications(): " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            return "admin/job-applications/list-applications";
        }
    }
    
    // Đánh dấu đơn ứng tuyển đã đọc
    @GetMapping("/admin/job-applications/mark-as-read")
    public String markAsRead(@RequestParam("id") Long id) {
        jobApplicationService.markAsRead(id);
        return "redirect:/admin/job-applications";
    }
    
    // Xóa đơn ứng tuyển
    @GetMapping("/admin/job-applications/delete")
    public String deleteApplication(@RequestParam("id") Long id) {
        jobApplicationService.deleteById(id);
        return "redirect:/admin/job-applications";
    }
    
    // Duyệt đơn ứng tuyển và gửi email
    @GetMapping("/admin/job-applications/approve")
    public String approveApplication(@RequestParam("id") Long id) {
        jobApplicationService.approveApplication(id);
        return "redirect:/admin/job-applications";
    }
}
