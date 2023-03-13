package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DynamicQueryDSLTest {

    @Autowired
    private DynamicQueryDSL dynamicQueryDSL;

    @Test
    @DisplayName("QueryDSL 상속/구현 없는 방식 테스트")
    void findProductById() {
        //given
        Long productId = 1L;

        //when
        Products products = dynamicQueryDSL.findProductById(productId);

        //then
        assertEquals(26910, products.getPrice());
    }

    @Test
    @DisplayName("동적 쿼리 BooleanExpression 적용 테스트")
    void findProductBE() {
        //given
        String nameCondition = "MORGAN";
        String BigCategory = "패션";
        String SmallCategory = null;

        //when
        List<Products> productsList = dynamicQueryDSL.findProductsBE(nameCondition, BigCategory, SmallCategory);
        for (Products products : productsList) {
            System.out.println(products.getProductName());
            System.out.println(products.getCategory().getBigCategory());
            System.out.println(products.getCategory().getSmallCategory());
        }

        //then
        assertEquals("[23SS 최신상] MORGAN 뉴 테일러드 원피스", productsList.get(0).getProductName());
    }

    @Test
    @DisplayName("동적 쿼리 BooleanBuilder적용 테스트")
    void findProductBB() {
        //given
        String nameCondition = "MORGAN";
        String BigCategory = "패션";
        String SmallCategory = null;

        //when
        List<Products> productsList = dynamicQueryDSL.findProductsBB(nameCondition, BigCategory, SmallCategory);

        //then
        for (Products products : productsList) {
            System.out.println(products.getProductName());
            System.out.println(products.getCategory().getBigCategory());
            System.out.println(products.getCategory().getSmallCategory());
        }
        assertEquals("[23SS 최신상] MORGAN 뉴 테일러드 원피스", productsList.get(0).getProductName());
    }
}