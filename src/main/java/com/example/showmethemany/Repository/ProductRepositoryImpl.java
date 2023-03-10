package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.example.showmethemany.domain.QProducts.products;

@Repository
public class ProductRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Products findById (Long productId) {
        return queryFactory.select(products)
                .from(products)
                .where(products.id.eq(productId))
                .fetchOne();
    }
}
