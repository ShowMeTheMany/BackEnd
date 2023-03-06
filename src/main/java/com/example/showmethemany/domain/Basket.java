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
    private int productNum;

    @Column(nullable = false)
    private int productPrice;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name = "products_id")
    private Products products;

    public Basket(int productNum, Member member, Products products) {
        this.productNum = productNum;
        this.productPrice = products.getPrice();
        this.member = member;
        this.products = products;
    }

    public void update(int productNum) {
        this.productNum = productNum;
    }
}
