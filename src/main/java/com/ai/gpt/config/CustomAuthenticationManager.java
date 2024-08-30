package com.ai.gpt.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomAuthenticationManager {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomerDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {
        CustomerAuthenticationProvider provider = new CustomerAuthenticationProvider(passwordEncoder, userDetailsService);
        ProviderManager manager = new ProviderManager(provider);
        manager.setEraseCredentialsAfterAuthentication(false);
        return manager;
    }
}
