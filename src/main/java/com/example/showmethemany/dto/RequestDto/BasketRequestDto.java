package com.example.showmethemany.dto.RequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketRequestDto {
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;
}
