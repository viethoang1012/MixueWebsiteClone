package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.CustomerDAO;
import com.gdu_springboot.spring_boot_demo.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);
}
