package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.showmethemany.domain.QProducts.products;

@Repository
public class QueryRepository {

    private final JPAQueryFactory queryFactory;

    public QueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<Products> findByProductId (Long productId) {
        return queryFactory.select(products)
                .from(products)
                .where(products.id.eq(productId))
                .fetch();
    }
}
