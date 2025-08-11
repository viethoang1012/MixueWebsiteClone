package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Cart;
import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.model.Order;
import com.gdu_springboot.spring_boot_demo.model.OrderItem;
import com.gdu_springboot.spring_boot_demo.service.CartService;
import com.gdu_springboot.spring_boot_demo.service.ProductService;
import com.gdu_springboot.spring_boot_demo.service.UserService;
import com.gdu_springboot.spring_boot_demo.service.OrderService;
import com.gdu_springboot.spring_boot_demo.dao.OrderRepository;
import com.gdu_springboot.spring_boot_demo.dao.OrderItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        System.out.println("Vao duoc controller /cart");
        System.out.println("PRINCIPAL: " + (principal != null ? principal.getName() : "null"));
        Integer userId = getUserId(principal);
        List<Cart> cartList = (userId != null) ? cartService.getCartByUserId(userId) : List.of();
    
        List<CartItemView> cartItems = cartList.stream().map(cart -> {
            Product product = productService.findById(Long.valueOf(cart.getProductId()));
            return new CartItemView(product != null ? product : new Product(), cart.getQuantity());
        }).collect(Collectors.toList());
    
        double totalPrice = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        
        // Lấy lịch sử đơn hàng nếu người dùng đã đăng nhập
        if (userId != null) {
            // Lấy thông tin quyền của người dùng đang đăng nhập
            String currentUserRole = "ROLE_USER"; // Mặc định là ROLE_USER
            if (principal != null) {
                org.springframework.security.core.Authentication authentication = 
                    (org.springframework.security.core.Authentication) principal;
                if (authentication != null) {
                    currentUserRole = authentication.getAuthorities().stream()
                        .findFirst()
                        .map(a -> a.getAuthority())
                        .orElse("ROLE_USER");
                }
            }
            
            // Chỉ lấy đơn hàng của người dùng với vai trò hiện tại
            List<Order> filteredOrders = orderRepository.findByUserIdAndUserRole(userId, currentUserRole);
            
            List<OrderHistoryView> orderHistoryViews = new ArrayList<>();
            
            for (Order order : filteredOrders) {
                List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
                List<OrderItemView> orderItemViews = new ArrayList<>();
                
                for (OrderItem item : orderItems) {
                    Product product = productService.findById(Long.valueOf(item.getProductId()));
                    orderItemViews.add(new OrderItemView(product, item));
                }
                
                orderHistoryViews.add(new OrderHistoryView(order, orderItemViews));
            }
            
            model.addAttribute("orderHistory", orderHistoryViews);
        }
        
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Integer productId,
                            @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        Integer userId = getUserId(principal);
        if (userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để thêm vào giỏ hàng!");
            return "redirect:/cart";
        }
        if (quantity < 1) quantity = 1;
        if (quantity > 10) quantity = 10;
        cartService.addToCart(userId, productId, quantity);
        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm vào giỏ hàng!");
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Integer productId,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        Integer userId = getUserId(principal);
        if (userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để xóa sản phẩm!");
            return "redirect:/cart";
        }
        cartService.removeFromCart(userId, productId);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sản phẩm khỏi giỏ hàng!");
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("productId") Integer productId,
                             @RequestParam("action") String action,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        Integer userId = getUserId(principal);
        if (userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để cập nhật giỏ hàng!");
            return "redirect:/cart";
        }
        if ("increase".equals(action)) {
            cartService.updateQuantity(userId, productId, 1); // tăng 1
        } else if ("decrease".equals(action)) {
            cartService.updateQuantity(userId, productId, -1); // giảm 1
        }
        return "redirect:/cart";
    }

    @GetMapping("/test-auth")
    @ResponseBody
    public String testAuth(Principal principal, org.springframework.security.core.Authentication authentication) {
        StringBuilder sb = new StringBuilder();
        sb.append("Principal: ").append(principal != null ? principal.getName() : "null").append("<br>");
        sb.append("Authorities: ");
        if (authentication != null) {
            authentication.getAuthorities().forEach(a -> sb.append(a.getAuthority()).append(" "));
        }
        return sb.toString();
    }

    @ModelAttribute("cartItemCount")
    public int getCartItemCount(Principal principal) {
        if (principal == null) return 0;
        Integer userId = getUserId(principal);
        if (userId == null) return 0;
        // Lấy danh sách cart của user và tính tổng quantity
        return cartService.getCartByUserId(userId)
                .stream()
                .mapToInt(Cart::getQuantity)
                .sum();
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        Integer userId = getUserId(principal);
        if (userId == null) return "redirect:/login";
        List<Cart> cartList = cartService.getCartByUserId(userId);
        double totalPrice = cartList.stream().mapToDouble(c -> c.getTotalPrice().doubleValue()).sum();
        double deliveryFee = 18000; // Có thể cho cố định hoặc cấu hình
        double discount = 0; // Có thể tính toán thêm
        double finalTotal = totalPrice + deliveryFee - discount;

        List<CartItemView> cartItems = cartList.stream().map(cart -> {
            Product product = productService.findById(Long.valueOf(cart.getProductId()));
            return new CartItemView(product != null ? product : new Product(), cart.getQuantity());
        }).collect(Collectors.toList());
        model.addAttribute("cartItems", cartItems);

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("discount", discount);
        model.addAttribute("finalTotal", finalTotal);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@RequestParam String name,
                             @RequestParam String phone,
                             @RequestParam(required = false) String deliveryInstructions,
                             @RequestParam String address,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        Integer userId = getUserId(principal);
        if (userId == null) return "redirect:/login";
        
        // Lấy thông tin quyền của người dùng
        String userRole = "ROLE_USER"; // Mặc định là ROLE_USER
        if (principal != null) {
            org.springframework.security.core.Authentication authentication = 
                (org.springframework.security.core.Authentication) principal;
            if (authentication != null) {
                userRole = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority())
                    .orElse("ROLE_USER");
            }
        }
        
        List<Cart> cartList = cartService.getCartByUserId(userId);
        double totalPrice = cartList.stream().mapToDouble(c -> c.getTotalPrice().doubleValue()).sum();
        double deliveryFee = 18000;
        double discount = 0;
        double finalTotal = totalPrice + deliveryFee - discount;
    
        // Lưu đơn hàng vào bảng orders với thông tin quyền và địa chỉ
        orderService.createOrder(userId, userRole, name, phone, deliveryInstructions, address, finalTotal, cartList);
    
        // Xóa giỏ hàng sau khi đặt hàng
        cartList.forEach(c -> cartService.removeFromCart(userId, c.getProductId()));
    
        redirectAttributes.addFlashAttribute("successMessage", "Đặt hàng thành công!");
        return "redirect:/cart";
    }

    public static class CartItemView {
        private Product product;
        private int quantity;

        public CartItemView(Product product, int quantity) {
            this.product = product != null ? product : new Product();
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
    }

    private Integer getUserId(Principal principal) {
        return principal != null ? userService.getUserIdByUsername(principal.getName()) : null;
    }

    // Inner class for order history view
    public static class OrderHistoryView {
        private Order order;
        private List<OrderItemView> items;
        
        public OrderHistoryView(Order order, List<OrderItemView> items) {
            this.order = order;
            this.items = items;
        }
        
        public Order getOrder() { return order; }
        public List<OrderItemView> getItems() { return items; }
    }
    
    public static class OrderItemView {
        private Product product;
        private OrderItem orderItem;
        
        public OrderItemView(Product product, OrderItem orderItem) {
            this.product = product != null ? product : new Product();
            this.orderItem = orderItem;
        }
        
        public Product getProduct() { return product; }
        public OrderItem getOrderItem() { return orderItem; }
    }
}