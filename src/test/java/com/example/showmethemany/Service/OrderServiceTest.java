package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.BasketQueryRepository;
import com.example.showmethemany.Repository.MemberRepository;
import com.example.showmethemany.Repository.OrderRepository;
import com.example.showmethemany.Repository.ProductRepository;
import com.example.showmethemany.domain.*;
import com.example.showmethemany.util.globalResponse.CustomException;
import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.BAD_REQUEST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketQueryRepository basketQueryRepository;


    public class CountDownLatchT {
        int count = 1;
        public void call() {
            System.out.println("count = " + this.count++);
        }
    }

    @Test
    void 동시성_테스트() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            executorService.execute(() -> {
//                상품_구매하기();
                주문하기(1L);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        Products products = productRepository.findById(1L).get();
        assertThat(products.getStock()).isEqualTo(0);
    }


    public synchronized void 주문하기(Long memberId) {
        System.out.println("=========================");
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(BAD_REQUEST));
        System.out.println("=========================");
        List<Basket> baskets = basketQueryRepository.findBasketByMemberId(memberId);
        System.out.println("=========================");
        String orderNum = UUID.randomUUID().toString();
        LocalDateTime orderTime = LocalDateTime.now();
        for (Basket basket : baskets) {
            Orders orders = new Orders(orderNum, orderTime, basket.getProductQuantity(), basket.getProducts().getPrice(), OrderStatus.배송준비, member, basket.getProducts());
            if (orders.getProducts().getStock() < basket.getProductQuantity()) {
                throw new CustomException(BAD_REQUEST);
            }
            orders.getProducts().updateStock(basket.getProductQuantity());
            orderRepository.save(orders);
            productRepository.save(orders.getProducts());
//            basketRepository.delete(basket);
        }
        System.out.println("=========================");
    }

    public synchronized void 상품_구매하기() {
        System.out.println("==================== 제품 조회 갑니다");
        Products products = basketQueryRepository.findProductsById(1L);
        System.out.println("==================== 제품 조회 끝낫습니다. 수량은 " + products.getStock());
        products.updateStock(1);
        System.out.println("==================== 업데이트 쿼리 나가냐?");
        productRepository.save(products);
        System.out.println("==================== 업데이트 쿼리 나가냐?");
    }
}