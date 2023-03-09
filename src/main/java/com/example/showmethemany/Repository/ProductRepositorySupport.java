package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.showmethemany.domain.QProducts.products;

@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;
    public ProductRepositorySupport(JPAQueryFactory queryFactory) {
        super(Products.class);
        this.queryFactory = queryFactory;
    }

    public List<Products> findByAll () {
        return queryFactory.select(products)
                .from(products)
                .fetch();
    }

    public List<Products> findByProductId (Long productId) {
        return queryFactory.select(products)
                .from(products)
                .where(products.id.eq(productId))
                .fetch();
    }
}
