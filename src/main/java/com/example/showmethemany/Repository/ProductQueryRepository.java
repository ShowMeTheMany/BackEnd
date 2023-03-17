package com.example.showmethemany.Repository;

import com.example.showmethemany.config.SearchCondition;
import com.example.showmethemany.domain.Orders;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
                        eqPrice(searchCondition.getMinPrice(), searchCondition.getMaxPrice()),
                        eqOnSale(searchCondition.getOnSale())
                );

        List<Products> content = queryFactory
                .selectFrom(products)
                .where(
                        eqProductName(searchCondition.getProductName()),
                        eqBigCategory(searchCondition.getBigCategory()),
                        eqSmallCategory(searchCondition.getSmallCategory()),
                        eqPrice(searchCondition.getMinPrice(), searchCondition.getMaxPrice()),
                        eqOnSale(searchCondition.getOnSale())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(productOrderBy(searchCondition.getOrderBy(), searchCondition.getDirection()))
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> productOrderBy(String orderBy, Order direction) {
        switch (orderBy != null ? orderBy : "null") {
            case "productName":
                return new OrderSpecifier<>(direction, products.productName);
            case "price":
                return new OrderSpecifier<>(direction, products.price);
            case "null":
            default:
                return new OrderSpecifier<>(direction, products.id);
        }
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

    private BooleanExpression eqPrice(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        } else if (minPrice != null && maxPrice == null) {
            return products.price.goe(minPrice);
        } else if (minPrice == null && maxPrice != null) {
            return products.price.loe(maxPrice);
        }
        return products.price.between(minPrice, maxPrice);
//        return products.price.eq(price);
    }

    private BooleanExpression eqOnSale(Boolean onSale) {
        if (onSale == null) {
            return null;
        }
        return products.onSale.eq(onSale);
    }
}
