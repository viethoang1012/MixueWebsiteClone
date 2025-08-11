package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.Authorities;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AuthoritiesDAO {
    List<Authorities> findAll();

    Authorities findById(Long id);

    @Transactional
    Authorities save(Authorities authorities);


    @Transactional
    void deleteById(Long id);
}
