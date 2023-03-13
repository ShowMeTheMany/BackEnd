package com.example.showmethemany.controller;

import com.example.showmethemany.Service.ProductService;
import com.example.showmethemany.dto.RequestDto.ProductDeleteRequestDto;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import com.example.showmethemany.dto.RequestDto.ProductUploadRequestDto;
import com.example.showmethemany.util.globalResponse.GlobalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
