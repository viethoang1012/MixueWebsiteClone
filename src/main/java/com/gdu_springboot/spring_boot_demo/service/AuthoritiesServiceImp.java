
package com.gdu_springboot.spring_boot_demo.service;

import com.gdu_springboot.spring_boot_demo.dao.AuthoritiesDAO;
import com.gdu_springboot.spring_boot_demo.model.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AuthoritiesServiceImp implements AuthoritiesService {
    private AuthoritiesDAO authoritiesDAO;

    @Autowired
    public AuthoritiesServiceImp(AuthoritiesDAO authoritiesDAO) {
        this.authoritiesDAO = authoritiesDAO;
    }

    @Override
    public List<Authorities> findAll() {
        return authoritiesDAO.findAll();
    }

    @Override
    public Authorities findById(Long id) {
        return authoritiesDAO.findById(id);
    }

    @Override
    public Authorities save(Authorities authorities) {
        authoritiesDAO.save(authorities);
        return authorities;
    }
    @Override
    public void deleteById(Long id) {
        authoritiesDAO.deleteById(id);
    }



}
