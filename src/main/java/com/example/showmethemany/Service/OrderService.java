package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.*;
import com.example.showmethemany.domain.*;
import com.example.showmethemany.dto.RequestDto.OrderRequestDto;
import com.example.showmethemany.util.globalResponse.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.stereotype.Service;
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


    //synchronized 적용
    public synchronized void orderProductSynchronized(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(BAD_REQUEST));
        List<Basket> baskets = basketQueryRepository.findBasketByMemberIdNoneLock(memberId);
        String orderNum = UUID.randomUUID().toString();
        LocalDateTime orderTime = LocalDateTime.now();
        for (Basket basket : baskets) {
            Orders orders = Orders.builder()
                    .orderNum(orderNum)
                    .orderTime(orderTime)
                    .productNum(basket.getProductQuantity())
                    .productPrice(basket.getProducts().getPrice())
                    .orderStatus(OrderStatus.배송준비)
                    .member(member)
                    .products(basket.getProducts()).build();
            validateStock(basket.getProducts(), basket);
            decreaseProductStock(basket.getProducts(), basket.getProductQuantity());
            updateProductStatus(basket.getProducts());
            orderRepository.save(orders);
            productRepository.save(basket.getProducts());
//            basketRepository.delete(basket);
        }
    }

    //배타적 락 적용
    @Transactional
    public void orderProductLock(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(BAD_REQUEST));
        List<Basket> baskets = basketQueryRepository.findBasketByMemberIdWithLock(memberId);
        String orderNum = UUID.randomUUID().toString();
        LocalDateTime orderTime = LocalDateTime.now();
        for (Basket basket : baskets) {
            Orders orders = Orders.builder()
                    .orderNum(orderNum)
                    .orderTime(orderTime)
                    .productNum(basket.getProductQuantity())
                    .productPrice(basket.getProducts().getPrice())
                    .orderStatus(OrderStatus.배송준비)
                    .member(member)
                    .products(basket.getProducts()).build();
            validateStock(basket.getProducts(), basket);
            decreaseProductStock(basket.getProducts(), basket.getProductQuantity());
            updateProductStatus(basket.getProducts());
            orderRepository.save(orders);
//            basketRepository.delete(basket);
        }
    }

    //Redisson을 이용한 분산락
    public void orderProduct(Long memberId) {
        RLock lock = redissonClient.getLock(String.valueOf(memberId));

        try {
            boolean available = lock.tryLock(180, 180, TimeUnit.SECONDS);

            if (available) {
                Member member = memberRepository.findById(memberId).orElseThrow(
                        () -> new CustomException(BAD_REQUEST));
                List<Basket> baskets = basketQueryRepository.findBasketByMemberIdNoneLock(memberId);
                String orderNum = UUID.randomUUID().toString();
                LocalDateTime orderTime = LocalDateTime.now();
                for (Basket basket : baskets) {
                    Orders orders = Orders.builder()
                            .orderNum(orderNum)
                            .orderTime(orderTime)
                            .productNum(basket.getProductQuantity())
                            .productPrice(basket.getProducts().getPrice())
                            .orderStatus(OrderStatus.배송준비)
                            .member(member)
                            .products(basket.getProducts()).build();
                    validateStock(basket.getProducts(), basket);
                    decreaseProductStock(basket.getProducts(), basket.getProductQuantity());
                    updateProductStatus(basket.getProducts());
                    orderRepository.save(orders);
                    productRepository.save(basket.getProducts());
//                basketRepository.delete(basket);
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
                throw new CustomException(BAD_REQUEST);
            }
            orderRepository.delete(order);
            updateProductStatus(order.getProducts());
            increaseProductStock(order.getProducts(), order.getProductNum());
        }
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
            throw new CustomException(BAD_REQUEST);
        }
    }

    public void increaseProductStock(Products products, int quantity){
        products.increaseStock(quantity);
    }

    public void decreaseProductStock(Products products, int quantity){
        products.decreaseStock(quantity);
    }
}
