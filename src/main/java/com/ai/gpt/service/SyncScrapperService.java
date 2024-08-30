package com.ai.gpt.service;

import com.ai.gpt.model.AuditLog;
import com.ai.gpt.model.Product;
import com.ai.gpt.scrapper.AmazonScrapper;
import com.ai.gpt.scrapper.Scrapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SyncScrapperService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncScrapperService.class);

    private final AuditService auditService;
    private final AmazonScrapper amazonScrapper;
    //    private final EbayScrapper ebayScrapper;
    private final OpenAIService openAIService;

    public Flux<Product> scrapProductDetails(final String productName) {
        final List<Scrapper> availableScrappers = List.of(amazonScrapper);
        final Map<String, Integer> consideredCount = new HashMap<>();
        final Long startTime = System.currentTimeMillis();
        final List<Flux<Product>> products = new ArrayList<>();

        return Flux.fromIterable(availableScrappers)
                .flatMap(scrapper -> scrapper.crawl(productName));

//        for (Scrapper scrapper : availableScrappers) {
//            LOGGER.info("Service: Started Fetching from Scrapper {}", scrapper.getClass().getSimpleName());
//            final Flux<Product> crawledProducts =
//            products.addAll(crawledProducts);
//            consideredCount.put(scrapper.getClass().getSimpleName(), crawledProducts.size());
//        }

//        final Long endTime = System.currentTimeMillis();
//        persistAuditRecord(endTime - startTime, productName, consideredCount);
//        LOGGER.info("Completed Scrapping Products");
//        return products;
    }

    private void persistAuditRecord(Long timeTaken, String searchKey, Map<String, Integer> consideredCount) {
        LOGGER.debug("Service: Persisting Audit Record in DB Started");
        AuditLog audit = AuditLog.builder().results(consideredCount).timeTaken(timeTaken).searchQuery(searchKey).build();
        auditService.save(audit);
        LOGGER.debug("Service: Persisting Audit Record Completed");
    }
}
