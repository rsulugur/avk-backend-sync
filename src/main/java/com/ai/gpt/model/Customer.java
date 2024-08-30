package com.ai.gpt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "customer")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotEmpty
    @NotBlank
    @NotNull
    private String email;
    @NotEmpty
    @NotBlank
    @NotNull
    private String password;
    private String role;
    @CreatedDate
    private LocalDateTime creationDate;
}
