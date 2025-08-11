package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.UserDAO;
import com.gdu_springboot.spring_boot_demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private UserDAO userDAO;
    @Autowired
    public void UserServiceImp(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @Override
    public List<Users> findAll() {
        return userDAO.findAll();
    }
    @Override
    public Users findById(Long id) {
        return userDAO.findById(id);
    }
    @Override
    public Users save(Users users) {
        userDAO.save(users);
        return users;
    }
    @Override
    public Users findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }
    @Override
    public Integer getUserIdByUsername(String username) {
        Users user = userDAO.findByUsername(username);
        return user != null ? user.getId().intValue() : null;
    }
}
