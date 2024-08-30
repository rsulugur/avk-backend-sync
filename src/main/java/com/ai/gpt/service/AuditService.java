package com.ai.gpt.service;

import com.ai.gpt.mapper.AuditRepository;
import com.ai.gpt.model.AuditLog;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuditService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditService.class);

    private final AuditRepository repository;

    public void save(AuditLog model) {
        repository.save(model);
    }

    public void update(AuditLog model) {
        repository.save(model);
    }

    public List<AuditLog> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }
}
