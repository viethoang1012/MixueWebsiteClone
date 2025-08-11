package com.gdu_springboot.spring_boot_demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // Đảm bảo truy vấn đúng với cấu trúc bảng của bạn
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select username, password, enabled from users where username = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select username, authority from authorities where username = ?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/default/**", "/webjars/**").permitAll()
                .requestMatchers("/", "/index", "/about", "/contact").permitAll()
                .requestMatchers("/detail-products").permitAll()
                .requestMatchers("/location", "/location/**").permitAll()
                .requestMatchers("/job-application/submit").permitAll()
                .requestMatchers("/review/add").authenticated()
                .requestMatchers("/shop", "/shop/**", "/cart", "/cart/**").hasAnyRole("USER", "ADMIN", "SYSTEM")
                .requestMatchers("/products/**").hasAnyRole("ADMIN", "SYSTEM")
                .requestMatchers("/admin/job-applications/**").hasAnyRole("ADMIN", "SYSTEM")
                .requestMatchers("/admin/**", "/customers/**").hasRole("ADMIN")
                .requestMatchers("/system/feedback/**").hasRole("SYSTEM")
                .requestMatchers("/system/**", "/users/**", "/authorities/**").hasRole("SYSTEM")
                .requestMatchers("/checkout", "/checkout/**").hasAnyRole("USER", "ADMIN", "SYSTEM")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/authenticatedTheUser")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(configurer -> configurer
                .accessDeniedPage("/admin")
            );
    
        http.csrf(csrf -> csrf.disable());
    
        return http.build();
    }
}

// ... existing code ...