package com.gdu_springboot.spring_boot_demo.dao;

import java.util.List;

import com.gdu_springboot.spring_boot_demo.model.Product;

public interface ProductDAO {
    List<Product> findAll();
    List<Product> findByCategory(String category);
    Product findById(Long id);
    Product save(Product product);
    void deleteById(Long id);   
}
