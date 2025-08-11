package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Users;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    List<Users> findAll();

    Users findById(Long id);
    Users findByUsername(String username);
    @Transactional
    Users save(Users users);
    @Transactional
    void deleteById(Long id);

    Integer getUserIdByUsername(String username);
}
