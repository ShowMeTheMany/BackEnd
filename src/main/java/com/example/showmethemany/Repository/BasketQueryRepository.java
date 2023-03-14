package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Basket;
import com.example.showmethemany.domain.Products;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import static com.example.showmethemany.domain.QBasket.basket;
import static com.example.showmethemany.domain.QProducts.products;

@Repository
public class BasketQueryRepository {
    private final JPQLQueryFactory queryFactory;
    public BasketQueryRepository(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<Basket> findBasketByMemberId (Long memberId) {
        return queryFactory.select(basket)
                .from(basket)
                .join(basket.products).fetchJoin()
                .where(basket.member.id.eq(memberId))
                .fetch();
    }

    public Products findProductsById (Long productsId) {
        return queryFactory.select(products)
                .from(products)
                .where(products.id.eq(productsId))
                .fetchOne();
    }
}
