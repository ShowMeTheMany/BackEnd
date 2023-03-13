package com.example.showmethemany.dto.ResponseDto;

import com.example.showmethemany.domain.Products;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class ProductResponseDto {
    private String productName;
    private String bigCategory;
    private String smallCategory;
    private int price;
    private int stock;
    private boolean onSale;

    public ProductResponseDto(Products products) {
        this.productName = products.getProductName();
        this.bigCategory = products.getCategory().getBigCategory();
        this.smallCategory = products.getCategory().getSmallCategory();
        this.price = products.getPrice();
        this.stock = products.getStock();
        this.onSale = products.isOnSale();
    }
}
