package com.ai.gpt.controller;

import com.ai.gpt.mapper.AuditRepository;
import com.ai.gpt.model.AuditLog;
import com.ai.gpt.model.Product;
import com.ai.gpt.service.OpenAIService;
import com.ai.gpt.service.SyncScrapperService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final SyncScrapperService syncService;
    private OpenAIService openAIService;
    private AuditRepository auditRepository;

    @GetMapping("/ai/generate")
    @Tag(name = "ChatGPT Controller", description = "API for invoking OpenAI with Custom Search Parameters")
    public String generate(@RequestParam(required = true) String message) {
        LOGGER.debug("EndPoint:- Generating OpenAI with Custom Search Parameters");
        return openAIService.queryFeatures(message);
    }

    @GetMapping("/v1/fetch/products")
    @Tag(name = "Product Scrapping Controller", description = "API for initiating and scraping products")
    public Flux<Product> getProducts(@RequestParam(required = true) String searchKey) {
        LOGGER.debug("EndPoint:- API for initiating and scraping products");
        return syncService.scrapProductDetails(searchKey);
    }

    @GetMapping("/v1/recent")
    @Tag(name = "Product Scrapping Controller", description = "API for fetching the Recent Audits")
    public List<AuditLog> fetchRecentProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LOGGER.debug("EndPoint:- API for fetching the Recent Audits");
        final Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        final Pageable pageable = PageRequest.of(0, 10, sort);
        return auditRepository.findAllByCreatedBy(username, pageable).orElse(new ArrayList<>());
    }
}
