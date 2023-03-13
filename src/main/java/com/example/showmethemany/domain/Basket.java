package com.example.showmethemany.domain;

import com.example.showmethemany.dto.RequestDto.BasketRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BASKET")
@Getter
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int productQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private Products products;

    public Basket(int productNum, Member member, Products products) {
        this.productQuantity = productNum;
        this.member = member;
        this.products = products;
    }

    public void update(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
