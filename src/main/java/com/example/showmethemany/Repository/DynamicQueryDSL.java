package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.showmethemany.domain.QProducts.products;

@Repository
public class DynamicQueryDSL {

    private final JPAQueryFactory queryFactory;

    public DynamicQueryDSL(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Products findProductById (Long productId) {
        return queryFactory.select(products)
                .from(products)
                .where(products.id.eq(productId))
                .fetchOne();
    }

    // 1. 방법: BooleanBuilder 사용
    public List<Products> findProductsBB (String nameCodition, String bigCategory, String smallCategory) {

        BooleanBuilder builder = new BooleanBuilder();

        if(nameCodition != null) {
            builder.and(products.productName.contains(nameCodition));
        }

        if(bigCategory != null) {
            builder.and(products.bigCategory.eq(bigCategory));
        }

        if(smallCategory != null) {
            builder.and(products.smallCategory.eq(smallCategory));
        }

        return queryFactory.select(products)
                .from(products)
                .where(builder)
                .fetch();
    }

    // 2. 방법: BooleanExpression 사용
    public List<Products> findProductsBE (String nameCodition, String bigCategory, String smallCategory) {
        return queryFactory.select(products)
                .from(products)
                .where(
                        nameEq(nameCodition),
                        bigCateEq(bigCategory),
                        smallCateEq(smallCategory))
                .fetch();
    }

    private BooleanExpression nameEq(String nameCondition) {
        return nameCondition != null ? products.productName.contains(nameCondition) : null;
    }

    private BooleanExpression bigCateEq(String bigCategory) {
        return bigCategory != null ? products.bigCategory.eq(bigCategory) : null;
    }

    private BooleanExpression smallCateEq(String smallCategory) {
        return smallCategory != null ? products.smallCategory.eq(smallCategory) : null;
    }
}
