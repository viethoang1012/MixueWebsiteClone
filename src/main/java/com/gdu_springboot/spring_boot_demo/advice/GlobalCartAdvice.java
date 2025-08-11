package com.gdu_springboot.spring_boot_demo.advice;

import com.gdu_springboot.spring_boot_demo.service.CartService;
import com.gdu_springboot.spring_boot_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalCartAdvice {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @ModelAttribute("cartItemCount")
    public int getCartItemCount(Principal principal) {
        if (principal == null) return 0;
        Integer userId = userService.getUserIdByUsername(principal.getName());
        if (userId == null) return 0;
        return cartService.getCartByUserId(userId)
                .stream()
                .mapToInt(c -> c.getQuantity() == null ? 0 : c.getQuantity())
                .sum();
    }
}
