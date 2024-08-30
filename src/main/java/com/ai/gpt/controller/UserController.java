package com.ai.gpt.controller;

import com.ai.gpt.mapper.CustomerRepository;
import com.ai.gpt.model.Customer;
import com.ai.gpt.model.LoginRequest;
import com.ai.gpt.model.LoginResponse;
import com.ai.gpt.model.ResponseMessage;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/signup")
    ResponseEntity<ResponseMessage> registerUser(@RequestBody Customer customer) {
        final Optional<Customer> byEmail = customerRepository.findByEmail(customer.getEmail());
        final ResponseMessage message = new ResponseMessage();
        if (byEmail.isPresent()) {
            message.setMessage("user already exists");
            return ResponseEntity.badRequest().body(message);
        }

        final String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);
        customer.setRole("ROLE_USER");

        final Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer.getId() == null) {
            message.setMessage("user registration failed");
            return ResponseEntity.internalServerError().body(message);
        }
        message.setMessage("Given user has been registered successfully");
        return ResponseEntity.accepted().body(message);
    }

    @PostMapping("/auth/login")
    ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String jwt = "";
        String status = "unauthenticated";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authResponse = authenticationManager.authenticate(authentication);
        if (authResponse != null && authResponse.isAuthenticated()) {
            String secret = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            status = "authenticated";
            jwt =
                    Jwts.builder().issuer("avk").subject("JWT Token")
                            .claim("username", authResponse.getName())
                            .claim("authorities", authResponse.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                            .issuedAt(new Date())
                            .expiration(new Date((new Date()).getTime() + 2000000))
                            .signWith(secretKey)
                            .compact();
        }
        return ResponseEntity.ok().body(new LoginResponse(status, jwt));
    }
}
