package com.example.showmethemany.controller;

import com.example.showmethemany.Service.ProductService;
import com.example.showmethemany.config.SearchCondition;
import com.example.showmethemany.domain.Products;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
//    @PostMapping(value = "/product/upload")
//    public ResponseEntity<GlobalResponseDto> upload(@RequestBody ProductUploadRequestDto productRequestDto) {
//    }
//
    @GetMapping(value = "/product/inquiry")
    public List<ProductResponseDto> inquiry() {
        return productService.inquiry();
    }
//
//    @DeleteMapping(value = "/product/delete")
//    public ResponseEntity<GlobalResponseDto> deleteProduct(ProductDeleteRequestDto productDeleteRequestDto) {
//    }

    // 상품 필터링 검색
    @GetMapping(value= "/search")
    public Page<Products> searchProducts(final Pageable pageable,
                                         SearchCondition searchCondition) {
        return productService.searchProducts(pageable, searchCondition);
    }

}
