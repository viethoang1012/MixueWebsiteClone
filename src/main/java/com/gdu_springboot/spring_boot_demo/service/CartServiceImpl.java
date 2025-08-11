package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.model.Cart;
import com.gdu_springboot.spring_boot_demo.dao.CartRepository;
import com.gdu_springboot.spring_boot_demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Override
    public List<Cart> getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void addToCart(Integer userId, Integer productId, Integer quantity) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        Cart existingCart = cartList.stream()
            .filter(c -> c.getProductId().equals(productId))
            .findFirst()
            .orElse(null);
        Product product = productService.findById(Long.valueOf(productId));
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            existingCart.setTotalPrice(BigDecimal.valueOf(product.getPrice() * existingCart.getQuantity()));
            cartRepository.save(existingCart);
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId.longValue());
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cart.setTotalPrice(BigDecimal.valueOf(product.getPrice() * quantity));
            cartRepository.save(cart);
        }
    }

    @Override
    public void removeFromCart(Integer userId, Integer productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }

    @Override
    public void updateQuantity(Integer userId, Integer productId, int delta) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cart != null) {
            int newQuantity = cart.getQuantity() + delta;
            if (newQuantity <= 0) {
                cartRepository.delete(cart); // Xóa nếu về 0 hoặc nhỏ hơn
            } else {
                Product product = productService.findById(Long.valueOf(productId));
                cart.setQuantity(newQuantity);
                cart.setTotalPrice(BigDecimal.valueOf(product.getPrice() * newQuantity));
                cartRepository.save(cart);
            }
        }
    }
}
