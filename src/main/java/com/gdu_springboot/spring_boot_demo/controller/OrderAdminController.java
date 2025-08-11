package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Order;
import com.gdu_springboot.spring_boot_demo.model.OrderItem;
import com.gdu_springboot.spring_boot_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderAdminController {
    
    private OrderService orderService;
    
    @Autowired
    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    // Hiển thị danh sách đơn hàng
    @GetMapping("/list-orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "admin/orders/list-orders";
    }
    
    // Hiển thị chi tiết đơn hàng
    @GetMapping("/order-detail")
    public String showOrderDetail(@RequestParam("id") Integer id, Model model) {
        Order order = orderService.findById(id);
        if (order != null) {
            List<OrderItem> orderItems = orderService.findOrderItemsByOrderId(id);
            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
            return "admin/orders/order-detail";
        }
        return "redirect:/orders/list-orders";
    }
    
    // Cập nhật trạng thái đơn hàng
    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam("id") Integer id, 
                                  @RequestParam("status") String status) {
        Order order = orderService.findById(id);
        if (order != null) {
            orderService.updateOrderStatus(id, status);
        }
        return "redirect:/orders/order-detail?id=" + id;
    }
    
    // Xóa đơn hàng
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        orderService.deleteOrder(id);
        return "redirect:/orders/list-orders";
    }
}