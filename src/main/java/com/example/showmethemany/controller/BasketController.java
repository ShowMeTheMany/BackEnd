package com.example.showmethemany.controller;

import com.example.showmethemany.Service.BasketService;
import com.example.showmethemany.dto.RequestDto.BasketRequestDto;
import com.example.showmethemany.dto.RequestDto.SignUpRequestDto;
import com.example.showmethemany.dto.ResponseDto.BasketResponseDto;
import com.example.showmethemany.util.globalResponse.GlobalResponseDto;
import com.example.showmethemany.util.globalResponse.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.showmethemany.util.globalResponse.code.StatusCode.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;

    @GetMapping("/{userId}")
    public List<BasketResponseDto> inquiryBasket(@PathVariable Long userId) {
        return basketService.inquiryBasket(userId);
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<GlobalResponseDto> addBasket(@PathVariable Long userId,
                                                       @PathVariable Long productId,
                                                       @RequestBody BasketRequestDto basketRequestDto) {
        basketService.addBasket(userId, productId, basketRequestDto);
        return ResponseUtil.response(OK);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<GlobalResponseDto> deleteBasket(@PathVariable Long userId,
                                                       @PathVariable Long productId) {
        basketService.deleteBasket(userId, productId);
        return ResponseUtil.response(OK);
    }

    @PatchMapping("/{userId}/{productId}")
    public ResponseEntity<GlobalResponseDto> modifyBasket(@PathVariable Long userId,
                                                          @PathVariable Long productId,
                                                          @RequestBody BasketRequestDto basketRequestDto) {
        basketService.modifyBasket(userId, productId, basketRequestDto);
        return ResponseUtil.response(OK);
    }
}
