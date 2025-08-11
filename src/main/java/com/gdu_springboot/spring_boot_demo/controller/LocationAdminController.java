package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Location;
import com.gdu_springboot.spring_boot_demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/locations")
public class LocationAdminController {

    private LocationService locationService;
    
    @Autowired
    public LocationAdminController(LocationService locationService) {
        this.locationService = locationService;
    }
    
    // Hiển thị danh sách địa điểm
    @GetMapping("/list-locations")
    public String listLocations(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        return "admin/locations/list-locations";
    }
    
    // Hiển thị form thêm địa điểm
    @GetMapping("/location-form")
    public String showLocationForm(Model model) {
        model.addAttribute("location", new Location());
        return "admin/locations/location-form";
    }
    
    // Hiển thị form cập nhật địa điểm
    @GetMapping("/location-form-update")
    public String showLocationFormUpdate(@RequestParam("id") Long id, Model model) {
        Optional<Location> location = locationService.getLocationById(id);
        if (location.isPresent()) {
            model.addAttribute("location", location.get());
            return "admin/locations/location-form-update";
        }
        return "redirect:/locations/list-locations";
    }
    
    // Lưu địa điểm (thêm hoặc cập nhật)
    @PostMapping("/save")
    public String save(@ModelAttribute Location location, 
                      @RequestParam("imageFile") MultipartFile imageFile) {
        // Xử lý upload ảnh nếu có
        if (!imageFile.isEmpty()) {
            try {
                // Tạo tên file ngẫu nhiên để tránh trùng lặp
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("src/main/resources/static/uploads/locations");
                
                // Tạo thư mục nếu chưa tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Lưu file
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath);
                
                // Cập nhật đường dẫn ảnh trong đối tượng location
                location.setImage("/uploads/locations/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Lưu location vào database
        locationService.saveLocation(location);
        
        return "redirect:/locations/list-locations";
    }
    
    // Xóa địa điểm
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        locationService.deleteLocation(id);
        return "redirect:/locations/list-locations";
    }
}
