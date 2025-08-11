package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Staff;
import com.gdu_springboot.spring_boot_demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class StaffController {
    
    private StaffService staffService;
    
    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }
    
    @GetMapping("/staff")
    public String listStaff(Model model) {
        List<Staff> staffList = staffService.findAll();
        model.addAttribute("staffList", staffList);
        return "admin/staff/list-staff";
    }
    
    @GetMapping("/staff/delete")
    public String deleteStaff(@RequestParam("id") Long id) {
        staffService.deleteById(id);
        return "redirect:/admin/staff";
    }
    
    @GetMapping("/staff/location")
    public String listStaffByLocation(@RequestParam("locationId") Long locationId, Model model) {
        List<Staff> staffList = staffService.findByLocationId(locationId);
        model.addAttribute("staffList", staffList);
        model.addAttribute("locationId", locationId);
        return "admin/staff/location-staff";
    }
}