package com.example.showmethemany.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNum;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Column(nullable = false)
    private int productNum;

    @Column(nullable = false)
    private int productPrice;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private Products products;

    public Orders(String orderNum, LocalDateTime orderTime, int productNum, int productPrice, OrderStatus orderStatus, Member member, Products products) {
        this.orderNum = orderNum;
        this.orderTime = orderTime;
        this.productNum = productNum;
        this.productPrice = productPrice;
        this.orderStatus = orderStatus;
        this.member = member;
        this.products = products;
    }
}
