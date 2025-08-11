package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.Authorities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthoritiesDAOImp  implements AuthoritiesDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Authorities> findAll() {
        TypedQuery<Authorities> query = entityManager.createQuery("from Authorities ", Authorities.class);
        return query.getResultList();
    }

    @Override
    public Authorities findById(Long id) {
        return entityManager.find(Authorities.class, id);
    }

    @Transactional
    @Override
    public Authorities save(Authorities authorities) {
        Authorities saved = entityManager.merge(authorities);
        return saved;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Authorities authorities= entityManager.find(Authorities.class, id);
        if (authorities != null) {
            entityManager.remove(authorities);
        }
    }


}
