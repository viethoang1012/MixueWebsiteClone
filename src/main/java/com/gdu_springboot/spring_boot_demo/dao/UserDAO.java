package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.Users;

import java.util.List;

public interface UserDAO {
    List<Users> findAll();
    Users findById(Long id);
//    Users findByUsername(String username);
    Users save(Users user);
    void deleteById(Long id);
    Users findByUsername(String username);
}
