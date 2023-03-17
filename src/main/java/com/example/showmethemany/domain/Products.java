package com.example.showmethemany.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
@Getter
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Embedded
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private int price;

    @Column
    @ColumnDefault("0")
    private int discount;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean onSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVNET_ID")
    private Event event;

    public void increaseStock (int quantity) {
        this.stock += quantity;
    }

    public void decreaseStock (int quantity) {
        this.stock -= quantity;
    }

    public void updateOnSale (boolean onSale) {
        this.onSale = onSale;
    }
}
