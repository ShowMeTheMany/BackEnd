package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("QueryDSL CustomRepository 상속 방식 테스트")
    void findByProductId() {
        //given
        Long productId = 1L;

        //when
        List<Products> products = productRepository.findByProductId(productId);

        //then
        assertEquals(26910, products.get(0).getPrice());
    }
}