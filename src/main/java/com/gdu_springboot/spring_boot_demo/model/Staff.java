package com.gdu_springboot.spring_boot_demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Integer age;
    private Integer birthYear;
    private String gender;
    private String email;
    private String citizenIdFront;
    private String citizenIdBack;
    private LocalDateTime hireDate;
    
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    // Constructors
    public Staff() {
        this.hireDate = LocalDateTime.now();
    }
    
    // Constructor từ JobApplication
    public Staff(JobApplication application) {
        this.fullName = application.getFullName();
        this.age = application.getAge();
        this.birthYear = application.getBirthYear();
        this.gender = application.getGender();
        this.email = application.getEmail();
        this.citizenIdFront = application.getCitizenIdFront();
        this.citizenIdBack = application.getCitizenIdBack();
        this.location = application.getLocation();
        this.hireDate = LocalDateTime.now();
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

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}