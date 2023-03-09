package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.showmethemany.domain.QProducts.products;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Products> findByProductId(Long productId) {
        return queryFactory.select(products)
                .from(products)
                .where(products.id.eq(productId))
                .fetch();
    }
}
