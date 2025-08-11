package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    
    long countByStatus(String status);
    
    @Query("SELECT SUM(totalPrice) FROM Order")
    BigDecimal getTotalRevenue();
    
    List<Order> findByUserId(Integer userId);
    List<Order> findByUserIdAndUserRole(Integer userId, String userRole);
}
