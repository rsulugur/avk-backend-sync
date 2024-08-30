package com.ai.gpt.scrapper;

import com.ai.gpt.model.Product;
import com.ai.gpt.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class AmazonScrapper implements Scrapper {
    static final String PRODUCT_URL = "https://www.amazon.com/s?k={productName}";
    private static final Logger logger = LoggerFactory.getLogger(AmazonScrapper.class);

    @Override
    public Flux<Product> crawl(String productName) {
        final String formattedUrl = PRODUCT_URL.replace("{productName}", productName);
        logger.info("Scrapper: Started Amazon Scrapping on URL {}", formattedUrl);

        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            ChromeDriver webDriver = new ChromeDriver(options);
            webDriver.get(formattedUrl);
            final List<WebElement> elements = webDriver.findElements(By.cssSelector("div.puis-card-container"));

            return Flux.fromIterable(elements)
                    .map(webElement -> {
                        final Product prod = new Product();
                        try {
                            WebElement textElement = webElement.findElement(new By.ByXPath(".//div[@data-cy='title-recipe']"));
                            prod.setName(textElement.getText());

                            WebElement priceElement = webElement.findElement(new By.ByXPath(".//span[@class='a-price-whole']"));
                            prod.setPrice(AppUtils.convertPrice(priceElement.getText()));

                            WebElement productURL = webElement.findElement(By.cssSelector("a.a-link-normal"));
                            prod.setUrl(productURL.getAttribute("href"));

                            WebElement productImage = webElement.findElement(By.cssSelector("img.s-image"));
                            prod.setImage(productImage.getAttribute("src"));

                            WebElement productRatings = webElement.findElement(By.cssSelector("span.a-icon-alt"));
                            prod.setRatings(productRatings.getText());

                            return prod;
                        } catch (NoSuchElementException ex) {
                            logger.warn("Unable to Scrap Product {}", ex.getMessage());
                            return prod;
                        }
                    })
                    .filter(Product::isValid)
                    .take(15)
                    .doFinally(signalType -> {
//                        webDriver.close();
//                        webDriver.quit();
                    });
        } catch (WebDriverException ex) {
            logger.error("Unable to initiate Product Scrapper due to {}", ex.getRawMessage());
            return Flux.empty();
        }
    }
}