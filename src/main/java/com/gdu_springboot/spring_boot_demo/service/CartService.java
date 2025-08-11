package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Cart;
import java.util.List;

public interface CartService {
    List<Cart> getCartByUserId(Integer userId);
    void addToCart(Integer userId, Integer productId, Integer quantity);
    void removeFromCart(Integer userId, Integer productId);
    void updateQuantity(Integer userId, Integer productId, int delta);
}
