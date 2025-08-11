package com.gdu_springboot.spring_boot_demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "tên không được để trống")
    @Size(max = 50, message = "tên không được vượt quá 50 ký tự")
    private String username;
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải đúng định dạng")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@gmail\\.com$", message = "Email phải có đuôi @gmail.com")
    private String email;
    @NotNull(message = "Trạng thái enabled không được để trống")
    private boolean enabled;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    public Users( String username, String email, String password, Boolean enabled) {
        this.enabled = enabled;
        this.username = username;
        this.email = email;
        this.password = password;
    }



    @JsonCreator

    public Users() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
