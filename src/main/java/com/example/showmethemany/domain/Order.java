package com.example.showmethemany.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDER")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)               // 연관된 이미지 파일 정보, cascade로 함께 삭제되도록 설정
    private List<Product> productList = new ArrayList<>();

    @Column(nullable = false)
    private long orderNum;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Column(nullable = false)
    private long productNum;

    @Column(nullable = false)
    private long productPrice;

    @Column(nullable = false)
    private boolean orderStatus;
}
