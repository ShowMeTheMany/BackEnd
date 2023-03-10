package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Test
    @DisplayName("QueryDSL 상속/구현 없는 방식 테스트")
    void findById() {
        //given
        Long productId = 1L;

        //when
        Products products = productRepository.findById(productId);

        //then
        assertEquals(26910, products.getPrice());
    }
}