package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.CustomerDAO;
import com.gdu_springboot.spring_boot_demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerDAO customerDAO;
    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    @Override
    public List<Customer> findAll() {
         return customerDAO.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerDAO.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
         customerDAO.save(customer);
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        customerDAO.deleteById(id);
    }

}
