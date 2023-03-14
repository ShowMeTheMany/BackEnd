package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.ProductRepository;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductFilterResponseDto;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import com.example.showmethemany.util.globalResponse.CustomException;
import com.example.showmethemany.util.globalResponse.code.StatusCode;
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


    public List<ProductResponseDto> inquiry() {
        List<Products> productsList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Products products : productsList) {
            ProductResponseDto productResponseDto = new ProductResponseDto(products);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    public Page<ProductFilterResponseDto> findAllProducts(Pageable pageable, Long categoryId, Long minPrice, Long maxPrice, String stock, String sorting) {
        System.out.println("=====상품조회=====");
        Page<ProductFilterResponseDto> page = productRepository.mainFilter(pageable, categoryId, minPrice, maxPrice, stock, sorting);
        if (page.getNumberOfElements() == 0) throw new CustomException(StatusCode.EMPTY_RESULT_EXCEPTION);
        return page;
    }
}
