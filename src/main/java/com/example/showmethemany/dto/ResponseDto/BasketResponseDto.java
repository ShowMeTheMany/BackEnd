package com.example.showmethemany.dto.ResponseDto;

import lombok.Getter;

@Getter
public class BasketResponseDto {
    private String productName;
    private int productPrice;
    private int productQuantity;

    public BasketResponseDto(String productName, int productPrice, int productQuantity){
        this.productName      = productName;
        this.productPrice     = productPrice;
        this.productQuantity  = productQuantity;
    }
}
