package com.example.showmethemany.dto.ResponseDto;

import lombok.Getter;

@Getter
public class ProductFilterResponseDto {

    private Long productId;
    private String title;
    private String photo;
    private Long price;
    private String stock;

    public ProductFilterResponseDto(Long productId, String title, String photo, Long price, String stock) {
        this.productId = productId;
        this.title = title;
        this.photo = photo;
        this.price = price;
        this.stock = stock;
    }
}
