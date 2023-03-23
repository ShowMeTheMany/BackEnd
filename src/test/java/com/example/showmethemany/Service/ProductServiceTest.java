package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.ProductQueryRepository;
import com.example.showmethemany.config.SearchCondition;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import com.querydsl.core.types.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductQueryRepository queryRepository;

    @Test
    @DisplayName("Page 테스트")
    void 상품_조회하기() {

        //given
        SearchCondition condition = new SearchCondition();
        condition.setProductName(null);
        condition.setBigCategory(null);
        condition.setSmallCategory(null);
        condition.setMinPrice(null);
        condition.setMaxPrice(null);
        condition.setOnSale(null);
        condition.setOrderBy("price");
        condition.setDirection(Order.ASC);

        //when
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Products> results = queryRepository.searchPage(pageRequest, condition);
        List<ProductResponseDto> responseDto = new ArrayList<>();
        for (Products result : results) {
            ProductResponseDto p = new ProductResponseDto(result);
            responseDto.add(p);
        }

        //then
        System.out.println("Total Page :" + results.getSize());
        System.out.println("Total Count :" + results.getTotalElements());
        System.out.println("Page Number :" + results.getNumber());
        System.out.println("has next page? :" + results.hasNext());
        System.out.println("first page? :" + results.isFirst());
        System.out.println("=============================");
        for (ProductResponseDto productResponseDto : responseDto) {
            System.out.println(productResponseDto.getProductName());
            System.out.println(productResponseDto.getBigCategory());
            System.out.println(productResponseDto.getSmallCategory());
            System.out.println(productResponseDto.getPrice());
            System.out.println(productResponseDto.isOnSale());
        }
        /*
        [한여름] 산리오캐릭터즈 스팽글 별 형광펜 6색 시나모롤 쿠로미 마이멜로디 선택가능
        문구/오피스
        필기류
        4500
        10
        true
         */
//        assertEquals("[한여름] 산리오캐릭터즈 스팽글 별 형광펜 6색 시나모롤 쿠로미 마이멜로디 선택가능", responseDto.get(0).getProductName());
//        assertEquals("문구/오피스", responseDto.get(0).getBigCategory());
//        assertEquals("필기류", responseDto.get(0).getSmallCategory());
//        assertEquals(4500, responseDto.get(0).getPrice());
//        assertEquals(true, responseDto.get(0).isOnSale());
    }
}