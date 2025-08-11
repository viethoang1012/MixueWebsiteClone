package com.gdu_springboot.spring_boot_demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import com.gdu_springboot.spring_boot_demo.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ProductDAOImp implements ProductDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery("from Product", Product.class);
        return query.getResultList();
    }
    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }
    @Override
    @Transactional
    public Product save(Product product) {
        Product saved = entityManager.merge(product);
        return saved;
    }
    @Override
    @Transactional
    public void deleteById(Long id) {
      Product product= entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    
    }
    
    @Override
    public List<Product> findByCategory(String category) {
        TypedQuery<Product> query = entityManager.createQuery(
            "FROM Product WHERE category = :category", Product.class);
        query.setParameter("category", category);
        return query.getResultList();
    }
}
