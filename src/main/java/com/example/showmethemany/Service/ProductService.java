package com.example.showmethemany.Service;

import com.example.showmethemany.Repository.ProductRepository;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
