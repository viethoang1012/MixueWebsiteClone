package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Cart;
import com.gdu_springboot.spring_boot_demo.model.Order;
import com.gdu_springboot.spring_boot_demo.model.OrderItem;
import java.util.List;

public interface OrderService {
    void createOrder(Integer userId, String userRole, String name, String phone, String deliveryInstructions, String address, double total, List<Cart> cartList);
    
    // Dashboard metrics
    long countPendingOrders();
    long countCompletedOrders();
    double getTotalRevenue();
    
    // Order management
    List<Order> findAll();
    Order findById(Integer id);
    List<OrderItem> findOrderItemsByOrderId(Integer orderId);
    void updateOrderStatus(Integer orderId, String status);
    void deleteOrder(Integer orderId);
}
