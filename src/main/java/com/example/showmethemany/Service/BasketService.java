package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.BasketQueryRepository;
import com.example.showmethemany.Repository.BasketRepository;
import com.example.showmethemany.Repository.MemberRepository;
import com.example.showmethemany.Repository.ProductRepository;
import com.example.showmethemany.domain.Basket;
import com.example.showmethemany.domain.Member;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.RequestDto.BasketRequestDto;
import com.example.showmethemany.dto.ResponseDto.BasketResponseDto;
import com.example.showmethemany.util.globalResponse.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketService {
    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final BasketQueryRepository basketQueryRepository;

    // 장바구니 조회
    @Transactional
    public List<BasketResponseDto> inquiryBasket(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );
        // Basket과 Products 한번에 조회
        List<Basket> baskets = basketQueryRepository.findBasketByMemberIdNoneLock(member.getId());
        List<BasketResponseDto> basketResponseDtoList = new ArrayList<>();
        for (Basket basket : baskets) {
            BasketResponseDto basketResponseDto = new BasketResponseDto(basket.getProducts().getProductName(), basket.getProducts().getPrice(), basket.getProductQuantity());
            basketResponseDtoList.add(basketResponseDto);
        }
        return basketResponseDtoList;
    }

    // 장바구니 추가
    @Transactional
    public void addBasket(Long userId, Long productId, BasketRequestDto basketRequestDto) {
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );

        Products products = productRepository.findById(productId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );

        if (basketRepository.findByMemberAndProducts(member, products).isPresent()) {
            throw new CustomException(BAD_REQUEST);
        }

        Basket basket = new Basket(basketRequestDto.getQuantity(), member, products);
        basketRepository.save(basket);
    }

    @Transactional
    public void deleteBasket(Long userId, Long productId) {
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );
        Products products = productRepository.findById(productId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );
        Basket basket = basketRepository.findByMemberAndProducts(member, products).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );
        basketRepository.delete(basket);
    }

    @Transactional
    public void modifyBasket(Long userId, Long productId, BasketRequestDto basketRequestDto) {
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );
        Products products = productRepository.findById(productId).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );
        Basket basket = basketRepository.findByMemberAndProducts(member, products).orElseThrow(
                () -> new CustomException(BAD_REQUEST)
        );
        basket.update(basketRequestDto.getQuantity());
    }
}