package com.gdu_springboot.spring_boot_demo.service;

import java.util.List;

import com.gdu_springboot.spring_boot_demo.model.Product;

import jakarta.transaction.Transactional;

public interface ProductService {
List<Product> findAll();
Product findById(Long id);
@Transactional
Product save(Product product);
@Transactional
void deleteById(Long id);
List<Product> findByCategory(String category);
}