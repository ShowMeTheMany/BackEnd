package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.*;
import com.example.showmethemany.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;


    @Test
    void 주문_취소_동시성_테스트() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 1; i <= 1000; i++) {
            Long finalI = (long) i + 60831;
            executorService.execute(() -> {
                orderService.deleteTest(finalI);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        Products products = productRepository.findById(1L).get();
        assertThat(products.getStock()).isEqualTo(1000);
    }



    @Transactional
    public void 주문_취소하기(Long orderId) {
        Orders order = orderQueryRepository.findOrderById(orderId);
        재고_상태변경(order.getProducts());
        재고_늘리기(order.getProducts(), order.getProductNum());
        orderRepository.delete(order);
    }

    public void 재고_늘리기(Products products, int quantity){
        products.increaseStock(quantity);
    }

    public void 재고_상태변경(Products products) {
        if (products.getStock() == 0 && products.isOnSale()) {
            products.updateOnSale(false);
        } else if (products.getStock() == 0 && !products.isOnSale()){
            products.updateOnSale(true);
        }
    }
}