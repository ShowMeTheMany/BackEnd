package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Basket;
import com.example.showmethemany.domain.Member;
import com.example.showmethemany.domain.Orders;
import com.example.showmethemany.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByMember(Member member);
    List<Basket> findByMemberId(Long memberId);
    Optional<Basket> findByMemberAndProducts(Member member, Products products);
}
