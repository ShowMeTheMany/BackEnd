package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Basket;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.LockModeType;
import java.util.List;
import static com.example.showmethemany.domain.QBasket.basket;

@Repository
public class BasketQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public BasketQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Basket> findBasketByMemberId(Long memberId) {
        return jpaQueryFactory.select(basket)
                .from(basket)
                .join(basket.products).fetchJoin()
                .where(basket.member.id.eq(memberId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetch();
    }

}
