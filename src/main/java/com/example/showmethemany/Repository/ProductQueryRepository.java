package com.example.showmethemany.Repository;

import com.example.showmethemany.config.SearchCondition;
import com.example.showmethemany.domain.Products;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.example.showmethemany.domain.QProducts.products;

@Repository
//@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public ProductQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    public Page<Products> searchPage(Pageable pageable, SearchCondition searchCondition) {
        JPAQuery<Long> countQuery = queryFactory
                .select(products.count())
                .from(products)
                .where(
                        eqProductName(searchCondition.getProductName()),
                        eqBigCategory(searchCondition.getBigCategory()),
                        eqSmallCategory(searchCondition.getSmallCategory()),
                        eqPrice(searchCondition.getPrice()),
                        eqStock(searchCondition.getStock()),
                        eqOnSale(searchCondition.isOnSale())
                );

        List<Products> content = queryFactory
                .selectFrom(products)
                .where(
                        eqProductName(searchCondition.getProductName()),
                        eqBigCategory(searchCondition.getBigCategory()),
                        eqSmallCategory(searchCondition.getSmallCategory()),
                        eqPrice(searchCondition.getPrice()),
                        eqStock(searchCondition.getStock()),
                        eqOnSale(searchCondition.isOnSale())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(products.id.desc())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqProductName(String productName) {
        if(StringUtils.isEmpty(productName)) {
            return null;
        }
        return products.productName.contains(productName);
    }

    private BooleanExpression eqBigCategory(String bigCategory) {
        if(StringUtils.isEmpty(bigCategory)) {
            return null;
        }
        return products.category.bigCategory.eq(bigCategory);
    }

    private BooleanExpression eqSmallCategory(String smallCategory) {
        if(StringUtils.isEmpty(smallCategory)) {
            return null;
        }
        return products.category.smallCategory.eq(smallCategory);
    }

    private BooleanExpression eqPrice(int price) {
        if(price == 0) {
            return null;
        }
        return products.price.eq(price);
    }

    private BooleanExpression eqStock(int stock) {
        if(stock == 0) {
            return null;
        }
        return products.stock.eq(stock);
    }

    private BooleanExpression eqOnSale(Boolean onSale) {
        if(StringUtils.isEmpty(onSale.toString())) {
            return null;
        }
        return products.onSale.eq(onSale);
    }
}
