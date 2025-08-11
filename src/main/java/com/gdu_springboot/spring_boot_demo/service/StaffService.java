package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Staff;
import java.util.List;

public interface StaffService {
    List<Staff> findAll();
    List<Staff> findByLocationId(Long locationId);
    Staff findById(Long id);
    Staff save(Staff staff);
    void deleteById(Long id);
}