package com.gdu_springboot.spring_boot_demo.service;


import com.gdu_springboot.spring_boot_demo.model.Authorities;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AuthoritiesService {
    List<Authorities> findAll();
    Authorities findById(Long id);
    @Transactional
    Authorities save(Authorities authorities);
    @Transactional
    void deleteById(Long id);
}

