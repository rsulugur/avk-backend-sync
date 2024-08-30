package com.ai.gpt.scrapper;

import com.ai.gpt.model.Product;
import reactor.core.publisher.Flux;

public interface Scrapper {
    Flux<Product> crawl(final String itemName);
}
