package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> findAll();
    Customer findById(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);
}
