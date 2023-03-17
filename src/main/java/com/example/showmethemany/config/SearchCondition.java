package com.example.showmethemany.config;

import lombok.*;

@Data
public class SearchCondition {
    private String productName;
    private String bigCategory;
    private String smallCategory;
    private int price;
    private int stock;
    private boolean onSale;
}
