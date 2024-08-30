package com.ai.gpt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    //    private Long id;
    private String name;
    private String image;
    private String ratings;
    private Float price;
    private String url;

    @JsonIgnore
    public boolean isValid() {
        return name != null && price != null && url != null && ratings != null;
    }
}

