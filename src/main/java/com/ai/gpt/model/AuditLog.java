package com.ai.gpt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "audit")
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String searchQuery;
    private Long timeTaken;
    private Map<String, Integer> results;
    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private String createdBy;
}
