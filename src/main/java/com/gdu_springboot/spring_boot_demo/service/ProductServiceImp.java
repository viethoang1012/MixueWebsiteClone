package com.gdu_springboot.spring_boot_demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu_springboot.spring_boot_demo.dao.ProductDAO;
import com.gdu_springboot.spring_boot_demo.model.Product;
@Service
public class ProductServiceImp implements ProductService {
    private ProductDAO productDAO;
    @Autowired
    public ProductServiceImp(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }
    @Override
    public Product findById(Long id) {
        return productDAO.findById(id);
    }
    @Override
    public Product save(Product product) {
         productDAO.save(product);
         return product;
    }
    @Override
    public void deleteById(Long id) {
        productDAO.deleteById(id);
    }
    
    @Override
    public List<Product> findByCategory(String category) {
        return productDAO.findByCategory(category);
    }
}
