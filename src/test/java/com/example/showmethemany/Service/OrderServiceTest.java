package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.*;
import com.example.showmethemany.domain.*;
import com.example.showmethemany.dto.RequestDto.BasketRequestDto;
import com.example.showmethemany.util.globalResponse.CustomException;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.NOT_FOUND_MEMBER;
import static com.example.showmethemany.util.globalResponse.code.StatusCode.QUANTITY_OF_ORDERS_EXCEEDS_STOCK;
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
    private PlatformTransactionManager transactionManager;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BasketQueryRepository basketQueryRepository;

    @Autowired
    private BasketService basketService;


    @Test
    void 주문_취소_동시성_테스트() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 1; i <= 1000; i++) {
            Long finalI = (long) i + 63831;
            executorService.execute(() -> {
                주문_취소하기_배타락(finalI);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        Products products = productRepository.findById(1L).get();
        assertThat(products.getStock()).isEqualTo(1000);
    }

    @Test
    void 주문하기_동시성_테스트() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 1; i <= 1000; i++) {
            long num = (long)i;
            executorService.execute(() -> {
                orderService.orderProductByRedissonLock(num);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        Products products = productRepository.findById(1L).get();
        assertThat(products.getStock()).isEqualTo(0);
    }

    @Test
    void 장바구니_담기() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 1; i <= 1000; i++) {
            long num = (long)i;
            executorService.execute(() -> {
                BasketRequestDto basketRequestDto = new BasketRequestDto();
                basketRequestDto.setQuantity(1);
                basketService.addBasket(num, 1L, basketRequestDto);
                countDownLatch.countDown();
            });
        }
    }

    public void 주문하기_동시성_제어_분산락(Long memberId) {
        RLock lock = redissonClient.getLock("orderLock");

        try {
            boolean available = lock.tryLock(300, 300, TimeUnit.SECONDS);

            if (available) {
                TransactionStatus status = 트랜잭션_시작하기();
                try {
                    Member member = memberRepository.findById(memberId).orElseThrow(
                            () -> new CustomException(NOT_FOUND_MEMBER));
                    List<Basket> baskets = basketQueryRepository.findBasketByMemberIdNoneLock(memberId);
                    String orderNum = 주문_아이디_만들기();
                    LocalDateTime orderTime = 주문시간_만들기();
                    for (Basket basket : baskets) {
                        Products products = basket.getProducts();
                        Orders orders = 빌더패턴으로_주문_생성(member, orderNum, orderTime, basket, products);
                        재고_검증하기(products, basket);
                        재고_줄이기(products, basket.getProductQuantity());
                        재고_상태_변경(products);
                        orderRepository.save(orders);
                    }
                    트랜잭션_커밋(status);
                } catch (Exception e) {
                    트랜잭션_롤백(status);
                    throw e;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Rollback(false) // 테스트 후 롤백하지 않음
    public void 주문_취소하기_배타락(Long orderId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("testTransaction"); // 트랜잭션 이름 설정
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); // 트랜잭션 전파 속성 설정
        TransactionStatus status = transactionManager.getTransaction(def); // 트랜잭션 시작
        try {
            Orders order = orderQueryRepository.findOrderById(orderId);
            재고_상태변경(order.getProducts());
            재고_늘리기(order.getProducts(), order.getProductQuantity());
            orderRepository.delete(order);
            트랜잭션_커밋(status);
        } catch (Exception e) {
            트랜잭션_롤백(status);
            throw e;
        }
    }

    public void 재고_늘리기(Products products, int quantity){
        products.increaseStock(quantity);
    }

    public void 재고_줄이기(Products products, int quantity){
        products.decreaseStock(quantity);
    }


    public void 재고_상태변경(Products products) {
        if (products.getStock() == 0 && products.isOnSale()) {
            products.updateOnSale(false);
        } else if (products.getStock() == 0 && !products.isOnSale()){
            products.updateOnSale(true);
        }
    }

    public String 주문_아이디_만들기() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime 주문시간_만들기() {
        return LocalDateTime.now();
    }

    private Orders 빌더패턴으로_주문_생성(Member member, String orderNum, LocalDateTime orderTime, Basket basket, Products products) {
        Orders orders = Orders.builder()
                .orderNum(orderNum)
                .orderTime(orderTime)
                .productNum(basket.getProductQuantity())
                .productPrice(products.getPrice())
                .orderStatus(OrderStatus.배송준비)
                .member(member)
                .products(products).build();
        return orders;
    }

    public void 재고_검증하기(Products products, Basket basket) {
        if (products.getStock() < basket.getProductQuantity()) {
            throw new CustomException(QUANTITY_OF_ORDERS_EXCEEDS_STOCK);
        }
    }

    public void 재고_상태_변경(Products products) {
        if (products.getStock() == 0 && products.isOnSale()) {
            products.updateOnSale(false);
        } else if (products.getStock() == 0 && !products.isOnSale()){
            products.updateOnSale(true);
        }
    }

    public TransactionStatus 트랜잭션_시작하기() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("testTransaction"); // 트랜잭션 이름 설정
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); // 트랜잭션 전파 속성 설정
        TransactionStatus status = transactionManager.getTransaction(def); // 트랜잭션 시작

        return status;
    }

    public void 트랜잭션_커밋(TransactionStatus transactionStatus) {
        transactionManager.commit(transactionStatus);
    }

    public void 트랜잭션_롤백(TransactionStatus transactionStatus) {
        transactionManager.rollback(transactionStatus);
    }
}