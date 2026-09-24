package com.example.inventory_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/hello").permitAll()
                        .requestMatchers("/api/products/**").authenticated()
                        .requestMatchers("/api/users/**").authenticated()
                )
                .httpBasic(httpBasic -> {})
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService user = new InMemoryUserDetailsManager(
                User.withUsername("testuser")
                        .password("{noop}test123")
                        .roles("USER")
                        .build()
        );

        return user;
    }

}
