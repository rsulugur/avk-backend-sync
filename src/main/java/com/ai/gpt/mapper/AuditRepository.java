package com.ai.gpt.mapper;

import com.ai.gpt.model.AuditLog;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@JaversSpringDataAuditable
public interface AuditRepository extends MongoRepository<AuditLog, String> {

    Optional<List<AuditLog>> findAllByCreatedBy(final String createdBy, Pageable pageable);
}
