package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Location;
import com.gdu_springboot.spring_boot_demo.dao.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/location")
    public String getAllLocations(Model model) {
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        return "location";
    }

    @GetMapping("/location/{id}")
    public String getLocationDetail(@PathVariable Long id, Model model, 
                                   @RequestParam(required = false) String success,
                                   RedirectAttributes redirectAttributes) {
        Location location = locationRepository.findById(id).orElse(null);
        if (location == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy địa điểm");
            return "redirect:/location";
        }
        
        model.addAttribute("location", location);
        
        // Xử lý thông báo thành công - Ưu tiên từ RedirectAttributes
        if ("true".equals(success) && !model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", "Đơn ứng tuyển của bạn đã được gửi thành công!");
        }
        
        return "location_detail";
    }
}

