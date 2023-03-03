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
    private long orderNum;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Column(nullable = false)
    private int productNum;

    @Column(nullable = false)
    private int productPrice;

    @Column(nullable = false)
    private boolean orderStatus;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name = "products_id")
    private Products products;
}
