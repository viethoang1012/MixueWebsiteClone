package com.gdu_springboot.spring_boot_demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Long userId; // Thay đổi từ Integer sang Long

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    // Getter và Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Long getUserId() { return userId; } // Thay đổi kiểu trả về
    public void setUserId(Long userId) { this.userId = userId; } // Thay đổi kiểu tham số
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
