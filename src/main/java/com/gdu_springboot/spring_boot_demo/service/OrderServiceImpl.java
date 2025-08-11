package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.OrderRepository;
import com.gdu_springboot.spring_boot_demo.dao.OrderItemRepository;
import com.gdu_springboot.spring_boot_demo.model.Cart;
import com.gdu_springboot.spring_boot_demo.model.Order;
import com.gdu_springboot.spring_boot_demo.model.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public void createOrder(Integer userId, String userRole, String name, String phone, String deliveryInstructions, String address, double total, List<Cart> cartList) {
        Order order = new Order();
        order.setUserId(userId);
        order.setUserRole(userRole);
        order.setReceiverName(name);
        order.setReceiverPhone(phone);
        order.setDeliveryInstructions(deliveryInstructions);
        order.setAddress(address);
        order.setTotalPrice(BigDecimal.valueOf(total));
        order.setStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        order = orderRepository.save(order);

        for (Cart cart : cartList) {
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(cart.getProductId());
            item.setQuantity(cart.getQuantity());
            item.setUnitPrice(cart.getTotalPrice().divide(BigDecimal.valueOf(cart.getQuantity())));
            orderItemRepository.save(item);
        }
    }

    @Override
    public long countPendingOrders() {
        return orderRepository.countByStatus("Pending");
    }

    @Override
    public long countCompletedOrders() {
        return orderRepository.countByStatus("Completed");
    }

    @Override
    public double getTotalRevenue() {
        return orderRepository.getTotalRevenue().doubleValue();
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    @Override
    public void deleteOrder(Integer orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        orderItemRepository.deleteAll(orderItems);
        orderRepository.deleteById(orderId);
    }
}
