package com.gdu_springboot.spring_boot_demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Tên không được để trống")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 18, message = "Tuổi phải từ 18 trở lên")
    @Max(value = 60, message = "Tuổi không được vượt quá 60")
    @Column(name = "age")
    private Integer age;

    @NotNull(message = "Năm sinh không được để trống")
    @Column(name = "birth_year")
    private Integer birthYear;

    @NotEmpty(message = "Giới tính không được để trống")
    @Column(name = "gender")
    private String gender;

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email phải đúng định dạng")
    @Column(name = "email")
    private String email;

    @Column(name = "citizen_id_front")
    private String citizenIdFront;

    @Column(name = "citizen_id_back")
    private String citizenIdBack;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "is_read", columnDefinition = "boolean default false")
    private boolean isRead;

    @Column(name = "is_approved", columnDefinition = "boolean default false")
    private boolean isApproved;

    // Constructors
    public JobApplication() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
        this.isApproved = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCitizenIdFront() {
        return citizenIdFront;
    }

    public void setCitizenIdFront(String citizenIdFront) {
        this.citizenIdFront = citizenIdFront;
    }

    public String getCitizenIdBack() {
        return citizenIdBack;
    }

    public void setCitizenIdBack(String citizenIdBack) {
        this.citizenIdBack = citizenIdBack;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
