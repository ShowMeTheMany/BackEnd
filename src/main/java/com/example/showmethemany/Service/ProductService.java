package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.ProductQueryRepository;
import com.example.showmethemany.Repository.ProductRepository;
import com.example.showmethemany.config.SearchCondition;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
