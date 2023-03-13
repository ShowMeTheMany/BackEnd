package com.example.showmethemany.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class Category {
    @Column(nullable = false)
    private String bigCategory;

    @Column(nullable = false)
    private String smallCategory;

    public Category(String bigCategory, String smallCategory) {
        this.bigCategory = bigCategory;
        this.smallCategory = smallCategory;
    }
}
