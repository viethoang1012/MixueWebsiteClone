package com.gdu_springboot.spring_boot_demo.dao;

import com.gdu_springboot.spring_boot_demo.model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImp implements UserDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Users> findAll() {
        TypedQuery<Users> query = entityManager.createQuery("from Users ", Users.class);
        return query.getResultList();
    }
    @Override
    public Users findById(Long id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    @Transactional
    public Users save(Users user) {
        Users saved = entityManager.merge(user);
        return saved;
    }
    @Override
    public Users findByUsername(String username) {
        TypedQuery<Users> query = entityManager.createQuery("from Users where username = :username", Users.class);
        query.setParameter("username", username);
        List<Users> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }
    @Override
    @Transactional
    public void deleteById(Long id) {
        Users users= entityManager.find(Users.class, id);
        if (users != null) {
            entityManager.remove(users);
        }
    }

}
