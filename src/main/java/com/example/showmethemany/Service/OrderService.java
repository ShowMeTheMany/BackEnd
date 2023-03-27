package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.*;
import com.example.showmethemany.domain.*;
import com.example.showmethemany.dto.RequestDto.OrderRequestDto;
import com.example.showmethemany.util.TransactionUtil;
import com.example.showmethemany.util.globalResponse.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BasketQueryRepository basketQueryRepository;
    private final BasketRepository basketRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final ProductRepository productRepository;
    private final RedissonClient redissonClient;
    private final TransactionUtil transactionUtil;


    //synchronized 적용
    public synchronized void orderProductSynchronized(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER));
        List<Basket> baskets = basketQueryRepository.findBasketByMemberIdNoneLock(memberId);
        String orderNum = makeOrderNumber();
        LocalDateTime orderTime = makeOrderDataTime();
        for (Basket basket : baskets) {
            Products products = basket.getProducts();
            Orders orders = makeOrderByBuilder(member, orderNum, orderTime, basket, products);
            validateStock(products, basket);
            decreaseProductStock(products, basket.getProductQuantity());
            updateProductStatus(products);
            orderRepository.save(orders);
            productRepository.save(products);
        }
    }

    //배타적 락 적용
    @Transactional
    public void orderProductLock(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER));
        List<Basket> baskets = basketQueryRepository.findBasketByMemberIdWithLock(memberId);
        String orderNum = makeOrderNumber();
        LocalDateTime orderTime = makeOrderDataTime();
        for (Basket basket : baskets) {
            Products products = basket.getProducts();
            Orders orders = makeOrderByBuilder(member, orderNum, orderTime, basket, products);
            validateStock(products, basket);
            decreaseProductStock(products, basket.getProductQuantity());
            updateProductStatus(products);
            orderRepository.save(orders);
            productRepository.save(products);
//            basketRepository.delete(basket);
        }
    }

    //Redisson과 트랜잭션을 이용한 분산락
    public void orderProduct(Long memberId) {
        RLock lock = redissonClient.getLock("orderLock");

        try {
            boolean available = lock.tryLock(300, 300, TimeUnit.SECONDS);

            if (available) {
                TransactionStatus status = transactionUtil.startTransaction();
                try {
                    Member member = memberRepository.findById(memberId).orElseThrow(
                            () -> new CustomException(NOT_FOUND_MEMBER));
                    List<Basket> baskets = basketQueryRepository.findBasketByMemberIdNoneLock(memberId);
                    String orderNum = makeOrderNumber();
                    LocalDateTime orderTime = makeOrderDataTime();
                    for (Basket basket : baskets) {
                        Products products = basket.getProducts();
                        Orders orders = makeOrderByBuilder(member, orderNum, orderTime, basket, products);
                        validateStock(products, basket);
                        decreaseProductStock(products, basket.getProductQuantity());
                        updateProductStatus(products);
                        basketRepository.delete(basket);
                        orderRepository.save(orders);
                    }
                    transactionUtil.transactionCommit(status);
                } catch (Exception e) {
                    transactionUtil.transactionRollback(status);
                    throw e;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }


    // Redisson만 이용해서 분산락 구현
    public void orderProductByRedissonLock(Long memberId) {
        RLock lock = redissonClient.getLock("orderLock");

        try {
            boolean available = lock.tryLock(300, 300, TimeUnit.SECONDS);

            if (available) {
                    Member member = memberRepository.findById(memberId).orElseThrow(
                            () -> new CustomException(NOT_FOUND_MEMBER));
                    List<Basket> baskets = basketQueryRepository.findBasketByMemberIdNoneLock(memberId);
                    String orderNum = makeOrderNumber();
                    LocalDateTime orderTime = makeOrderDataTime();
                    for (Basket basket : baskets) {
                        Products products = basket.getProducts();
                        Orders orders = makeOrderByBuilder(member, orderNum, orderTime, basket, products);
                        validateStock(products, basket);
                        decreaseProductStock(products, basket.getProductQuantity());
                        updateProductStatus(products);
                        basketRepository.delete(basket);
                        orderRepository.save(orders);
                        productRepository.save(products);
                    }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }


    @Transactional
    public void deleteOrder(Long memberId, OrderRequestDto orderRequestDto) {
        List<Orders> orders = orderQueryRepository.findOrderByOrderNum(orderRequestDto.getOrderNum());
        for (Orders order : orders) {
            if (!order.getMember().getId().equals(memberId)){
                throw new CustomException(NOT_EXIST_ORDER);
            }
            orderRepository.delete(order);
            updateProductStatus(order.getProducts());
            increaseProductStock(order.getProducts(), order.getProductQuantity());
        }
    }

    private Orders makeOrderByBuilder(Member member, String orderNum, LocalDateTime orderTime, Basket basket, Products products) {
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

    public void updateProductStatus(Products products) {
        if (products.getStock() == 0 && products.isOnSale()) {
            products.updateOnSale(false);
        } else if (products.getStock() == 0 && !products.isOnSale()){
            products.updateOnSale(true);
        }
    }

    public void validateStock(Products products, Basket basket) {
        if (products.getStock() < basket.getProductQuantity()) {
            throw new CustomException(QUANTITY_OF_ORDERS_EXCEEDS_STOCK);
        }
    }

    public String makeOrderNumber() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime makeOrderDataTime() {
        return LocalDateTime.now();
    }

    public void increaseProductStock(Products products, int quantity){
        products.increaseStock(quantity);
    }

    public void decreaseProductStock(Products products, int quantity){
        products.decreaseStock(quantity);
    }
}
