package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Basket;
import com.example.showmethemany.domain.Products;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;
import static com.example.showmethemany.domain.QBasket.basket;
import static com.example.showmethemany.domain.QProducts.products;

@Repository
public class BasketQueryRepository {
    private final JPQLQueryFactory queryFactory;
    private final JPAQueryFactory jpaQueryFactory;

    public BasketQueryRepository(JPQLQueryFactory queryFactory, JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = queryFactory;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Basket> findBasketByMemberId (Long memberId) {
        return jpaQueryFactory.select(basket)
                .from(basket)
                .join(basket.products).fetchJoin()
                .where(basket.member.id.eq(memberId))
//                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetch();
    }

    public Products findProductsById (Long productsId) {
        return queryFactory.select(products)
                .from(products)
                .where(products.id.eq(productsId))
                .fetchOne();
    }
}
