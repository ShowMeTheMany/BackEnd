package com.example.showmethemany.Repository;

import com.example.showmethemany.domain.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QueryRepositoryTest {

    @Autowired
    private QueryRepository queryRepository;

    @Test
    @DisplayName("QueryDSL 상속/구현 없는 방식 테스트")
    void findById() {
        Long productId = 1L;

        //when
        List<Products> products = queryRepository.findByProductId(productId);

        //then
        assertEquals(26910, products.get(0).getPrice());
    }
}