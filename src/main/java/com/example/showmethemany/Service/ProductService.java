package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.ProductQueryRepository;
import com.example.showmethemany.Repository.ProductRepository;
import com.example.showmethemany.config.SearchCondition;
import com.example.showmethemany.domain.Event;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import com.example.showmethemany.util.globalResponse.CustomException;
import com.example.showmethemany.util.globalResponse.code.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;



    public List<ProductResponseDto> inquiry() {
        List<Products> productsList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Products products : productsList) {
            ProductResponseDto productResponseDto = new ProductResponseDto(products);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    // 상품 필터링 검색
    public Page<Products> searchProducts(Pageable pageable, SearchCondition searchCondition) {
        return productQueryRepository.searchPage(pageable, searchCondition);
    }

    // 프로덕트에 할인율 적용하는 로직
    @Transactional
    public void updateProducts(List<Long> productsIdList, Event event){
        for (Long Id : productsIdList){
            Products products = productRepository.findById(Id).orElseThrow(
                    () -> new CustomException(StatusCode.BAD_REQUEST)
            );
            products.updateProductsEventId(event);
            products.updateProductsDiscount(products.getPrice()*((1-(event.getDiscountRate()/100))));
        }
    }
}
