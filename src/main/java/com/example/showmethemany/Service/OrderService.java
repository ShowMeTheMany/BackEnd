package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.*;
import com.example.showmethemany.domain.*;
import com.example.showmethemany.util.globalResponse.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.BAD_REQUEST;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BasketQueryRepository basketQueryRepository;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final EntityManagerFactory emf;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void orderProduct(Long memberId) {
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



    public synchronized void orderProduct() {
        EntityManager entityManager = emf.createEntityManager();;
        Products products = basketQueryRepository.findProductsById(1L);
        System.out.println("재고수량 = " + products.getStock());
        products.updateStock(1);
        productRepository.save(products);
        System.out.println("재고수량 = " + products.getStock());
    }
}
