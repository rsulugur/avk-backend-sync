package com.ai.gpt.mapper;

import com.ai.gpt.model.Product;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface ProductMapper {
    //    @Insert("INSERT INTO products (PRODUCT_NAME, PRODUCT_URL, PRODUCT_PRICE, PRODUCT_DESCRIPTION, CREATED_AT) VALUES (#{productName}, #{productUrl}, #{productPrice}, #{productDescription}, #{createdAt})")
    void insertProduct(Product product);
}

