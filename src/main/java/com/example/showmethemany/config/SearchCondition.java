package com.example.showmethemany.config;

import com.querydsl.core.types.Order;
import lombok.*;

@Data
public class SearchCondition {
    private String productName;
    private String bigCategory;
    private String smallCategory;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean onSale;
    private String orderBy;
    private Order direction;
}
