package com.ai.gpt.config;

import com.ai.gpt.mapper.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Configuration
@AllArgsConstructor
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return customerRepository.findByEmail(email).map(customer -> {
            return new User(
                    customer.getEmail(),
                    customer.getPassword(),
                    List.of(new SimpleGrantedAuthority(customer.getRole()))
            );
        }).orElse(null);
    }
}
