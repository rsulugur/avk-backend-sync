//package com.ai.gpt.scrapper;
//
//import com.ai.gpt.model.Product;
//import com.ai.gpt.utils.AppUtils;
//import lombok.AllArgsConstructor;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverException;
//import org.openqa.selenium.WebElement;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Objects;
//
//@Component
//@AllArgsConstructor
//public class EbayScrapper implements Scrapper {
//    static final String PRODUCT_URL = "https://www.ebay.com/sch/i.html?&LH_BIN=1&_nkw={productName}";
//    private static final Logger logger = LoggerFactory.getLogger(EbayScrapper.class);
//    private final WebDriver webDriver;
//
//    @Override
//    public List<Product> crawl(String productName, Product product) {
//        final String formattedUrl = PRODUCT_URL.replace("{productName}", productName);
//        logger.info("Scrapper: Started Ebay Scrapping on URL {}", formattedUrl);
//
//        try {
//            webDriver.get(formattedUrl);
//            final List<WebElement> elements = webDriver.findElements(By.cssSelector("li.s-item.s-item__pl-on-bottom"));
//
//            final List<Product.ProductLink> productLinks = elements.stream().map(webElement -> {
//                        try {
//                            Product.ProductLink productLink = new Product.ProductLink();
//                            WebElement textElement = webElement.findElement(By.cssSelector(".s-item__title"));
//                            productLink.setDetailedName(textElement.getText());
//
//                            WebElement priceElement = webElement.findElement(By.cssSelector(".s-item__price"));
//                            productLink.setPrice(AppUtils.convertPrice(priceElement.getText()));
//
//                            WebElement productURL = webElement.findElement(By.cssSelector(".s-item__link"));
//                            productLink.setUrl(AppUtils.shortenURL(productURL.getAttribute("href")));
//
//                            return productLink;
//                        } catch (NoSuchElementException ex) {
//                            logger.warn("Unable to Scrap Product {}", ex.getMessage());
//                            return null;
//                        }
//                    })
//                    .filter(Objects::nonNull)
//                    .filter(Product.ProductLink::isValid)
//                    .limit(5)
//                    .toList();
//            product.getLinks().addAll(productLinks);
//        } catch (WebDriverException ex) {
//            logger.error("Unable to initiate Product Scrapper due to {}", ex.getRawMessage());
//        }
//        return product;
//    }
//}
