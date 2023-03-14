package com.example.showmethemany.controller;

import com.example.showmethemany.Service.ProductService;
import com.example.showmethemany.dto.RequestDto.ProductDeleteRequestDto;
import com.example.showmethemany.dto.ResponseDto.ProductResponseDto;
import com.example.showmethemany.dto.RequestDto.ProductUploadRequestDto;
import com.example.showmethemany.util.globalResponse.GlobalResponseDto;
import com.example.showmethemany.util.globalResponse.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.JpaBootstrapSensitive;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.OK;

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

    @GetMapping(value = "/products")
    public ResponseEntity<GlobalResponseDto> findAllProducts(@PageableDefault(size = 100) Pageable pageable,
                                                             @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                             @RequestParam(value = "minPrice", required = false) Long minPrice,
                                                             @RequestParam(value = "maxPrice", required = false) Long maxPrice,
                                                             @RequestParam(value = "stock", required = false) String stock,
                                                             @RequestParam(value = "sorting", required = false) String sorting)
    {
        System.out.println("categoryId: " + categoryId);
        System.out.println("minPrice: " + minPrice);
        System.out.println("maxPrice = " + maxPrice);
        System.out.println("stock = " + stock);
        System.out.println("sorting = " + sorting);

        productService.findAllProducts(pageable, categoryId, minPrice, maxPrice, stock, sorting);
        return ResponseUtil.response(OK);
    }
}
