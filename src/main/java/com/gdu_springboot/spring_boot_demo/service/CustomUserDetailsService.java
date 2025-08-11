//package com.gdu_springboot.spring_boot_demo.service;
//
//import com.gdu_springboot.spring_boot_demo.model.Users;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = userService.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Người dùng không tồn tại: " + username);
//        }
//
//        Query query = entityManager.createNativeQuery("SELECT authority FROM authorities WHERE userId = :userId");
//        query.setParameter("userId", user.getId());
//        List<String> authorities = query.getResultList();
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (String authority : authorities) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
//        }
//
//        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, grantedAuthorities);
//    }
//}